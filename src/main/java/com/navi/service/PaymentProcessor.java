package com.navi.service;

import com.navi.models.*;
import com.navi.repository.BankRepository;
import com.navi.repository.LoanApplicationRepository;
import com.navi.repository.PaymentsRepository;
import com.navi.repository.UserRepository;
import com.navi.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("paymentProcessor")
@Slf4j
public class PaymentProcessor extends ParsedResponse{

    @Override
    public String getType() {
        return Constants.COMMAND_TYPE_PAYMENT;
    }

    private String bankName;

    private String borrowerName;

    private Double amount;

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
    PaymentsRepository paymentsRepository;

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
                    case 3 : amount = validator.validateAmount(matcher.group()); break;
                    case 4 : emiNo = validator.validateEmiNo(matcher.group()); break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + paramIndex);
                }
                paramIndex++;
            }
        } catch (Exception e) {
            throw new Exception("Failed to parse PAYMENT command for value : " + matcher.group() + " " + e.getMessage());
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
        if (emiNo >= loanApplication.getTenure() * 12) {
            throw new Exception("Payment not allowed since loan would have already closed by " + emiNo + " months");
        }
        Payments payments = Payments.builder()
                .amount(amount)
                .loanApplicationId(loanApplication.getId())
                .paymentType(PaymentType.LUMP_SUM.name())
                .paymentReference(String.valueOf(emiNo))
                .build();
        payments = paymentsRepository.save(payments);
        ledgerService.recreateLedger(loanApplication, payments);
    }

    private void clearData() {
        bankName = null;
        borrowerName = null;
        amount = null;
        emiNo = null;
    }
}
