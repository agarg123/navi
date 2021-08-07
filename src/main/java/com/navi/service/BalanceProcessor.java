package com.navi.service;

import com.navi.models.Bank;
import com.navi.models.Ledger;
import com.navi.models.LoanApplication;
import com.navi.models.User;
import com.navi.repository.BankRepository;
import com.navi.repository.LedgerRepository;
import com.navi.repository.LoanApplicationRepository;
import com.navi.repository.UserRepository;
import com.navi.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("balanceProcessor")
@Slf4j
public class BalanceProcessor extends ParsedResponse{

    @Override
    public String getType() {
        return Constants.COMMAND_TYPE_BALANCE;
    }

    private String bankName;

    private String borrowerName;

    private Integer emiNo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    LoanApplicationRepository loanApplicationRepository;

    @Autowired
    LedgerService ledgerService;

    @Autowired
    LedgerRepository ledgerRepository;

    @Autowired
    Validator validator;


    @Override
    public void setParams(Matcher matcher) throws Exception{
        clearData();
        int paramIndex = 1;
        try {
            while (matcher.find()) {
                switch (paramIndex) {
                    case 1 : bankName = matcher.group(); break;
                    case 2 : borrowerName = matcher.group(); break;
                    case 3 : emiNo = validator.validateEmiNo(matcher.group()); break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + paramIndex);
                }
                paramIndex++;
            }
        } catch (Exception e) {
            throw new Exception("Failed to parse BALANCE command for value : " + matcher.group() + " " + e.getMessage());
        }
    }

    @Override
    public void processInput() throws Exception{
        Bank bank = bankRepository.findByBankName(bankName);
        User user = userRepository.findByName(borrowerName);
        LoanApplication loanApplication = null;
        if (bank == null || user == null) {
            throw new Exception("Unknown user " + borrowerName + " or bank " + bankName);
        } else {
            loanApplication = loanApplicationRepository.findByUserIdAndBankId(user.getId(), bank.getId());
            if (loanApplication == null) {
                throw new Exception("No loan exists for user " + borrowerName + " with bank " + bankName);
            }
        }
        Ledger nthEmi = ledgerRepository.findTopByLoanApplicationIdAndPaidEmiCountEqualsOrderByTxnNoDesc(loanApplication.getId(), emiNo);
        Double amountPaid = nthEmi == null ? loanApplication.getTotalAmount() : loanApplication.getTotalAmount() - nthEmi.getOutstandingBalance();
        Integer emisLeft = nthEmi == null ? 0 : nthEmi.getEmisLeft();
        log.info("{} {} {} {}", bank.getBankName(), user.getName(), amountPaid, emisLeft);

    }

    private void clearData() {
        bankName = null;
        borrowerName = null;
        emiNo = null;
    }
}
