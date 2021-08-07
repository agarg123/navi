package com.navi.repository;

import com.navi.models.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<Ledger, String> {

    List<Ledger> findByLoanApplicationIdOrderByTxnNoAsc(String loanApplicationId);

    List<Ledger> findByLoanApplicationIdAndPaidEmiCountGreaterThanOrderByTxnNoAsc(String loanApplicationId, Integer emiCount);

    void deleteByIdIn(List<String> ids);

    Ledger findTopByLoanApplicationIdOrderByTxnNoDesc(String loanApplicationId);

//    Ledger findTopByLoanApplicationIdAndPaidEmiCountGreaterThanOrderByTxnNoAsc(String loanApplicationId, Integer emiNo);

    Ledger findTopByLoanApplicationIdAndPaidEmiCountEqualsOrderByTxnNoDesc(String loanApplicationId, Integer emiNo);

}
