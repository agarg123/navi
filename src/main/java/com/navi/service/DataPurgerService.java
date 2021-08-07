package com.navi.service;

import com.navi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataPurgerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LedgerRepository ledgerRepository;

    @Autowired
    PaymentsRepository paymentsRepository;

    @Autowired
    LoanApplicationRepository loanApplicationRepository;

    @Autowired
    BankRepository bankRepository;

    public void purgeAll() {
        userRepository.deleteAll();
        ledgerRepository.deleteAll();
        paymentsRepository.deleteAll();
        loanApplicationRepository.deleteAll();
    }
}
