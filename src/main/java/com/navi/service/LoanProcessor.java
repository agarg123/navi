package com.navi.service;

import com.navi.models.Bank;
import com.navi.models.LoanApplication;
import com.navi.models.User;
import com.navi.repository.BankRepository;
import com.navi.repository.LoanApplicationRepository;
import com.navi.repository.UserRepository;
import com.navi.util.LoanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

import static com.navi.util.Constants.COMMAND_TYPE_LOAN;

@Component("loanProcessor")
@Slf4j
public class LoanProcessor extends ParsedResponse{

    @Override
    public String getType() {
        return COMMAND_TYPE_LOAN;
    }

    private String bankName;

    private String borrowerName;

    private Double principalAmount;

    private Integer tenure;

    private Double roi;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    LoanApplicationRepository loanApplicationRepository;

    @Autowired
    LedgerService ledgerService;

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
                    case 3 : principalAmount = validator.validateAmount(matcher.group()); break;
                    case 4 : tenure = validator.validateTenure(matcher.group());  break;
                    case 5 : roi = validator.validateRoi(matcher.group()); break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + paramIndex);
                }
                paramIndex++;
            }
        } catch (Exception e) {
            throw new Exception("Failed to parse LOAN command for value : " + matcher.group() + " " + e.getMessage());
        }
    }

    @Override
    public void processInput() throws Exception {
        User user = userRepository.findByName(borrowerName);
        if (user == null) {
            user = userRepository.save(User.builder()
                    .name(borrowerName)
                    .build());
        }
        Bank bank = bankRepository.findByBankName(bankName);
        if (bank == null) {
            bank = bankRepository.save(Bank.builder()
                    .bankName(bankName)
                    .build());
        }
        LoanApplication loanApplication = loanApplicationRepository.findByUserIdAndBankId(user.getId(), bank.getId());
        if (loanApplication != null) {
            throw new Exception("Existing loan for user " + borrowerName + " with the bank " + bankName);
        }
        loanApplication = LoanApplication.builder()
                .bankId(bank.getId())
                .userId(user.getId())
                .principalAmount(principalAmount)
                .roi(roi)
                .tenure(tenure)
                .build();
        loanApplication.setInterestAmount(LoanUtils.calculateInterest(loanApplication));
        loanApplication.setTotalAmount(loanApplication.getPrincipalAmount() + loanApplication.getInterestAmount());
        loanApplication.setEmi(Math.ceil(loanApplication.getTotalAmount() / (loanApplication.getTenure() * 12)));
        loanApplication = loanApplicationRepository.save(loanApplication);
        ledgerService.populateLedger(loanApplication);
    }

    private void clearData() {
        bankName = null;
        borrowerName = null;
        principalAmount = null;
        tenure = null;
        roi = null;
    }
}
