package com.navi.util;

import com.navi.models.LoanApplication;

public class LoanUtils {

    public static Double calculateInterest(LoanApplication loanApplication) {
        return (loanApplication.getPrincipalAmount() * loanApplication.getRoi() * loanApplication.getTenure()) / 100;
    }
}
