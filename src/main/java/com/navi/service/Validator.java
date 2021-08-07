package com.navi.service;

import org.springframework.stereotype.Component;

@Component
public class Validator {
    public Double validateAmount(String value) throws Exception{
        try {
            Double amount = Double.parseDouble(value);
            if (amount < 0) {
                throw new Exception("Amount cannot be negative. given : " + value);
            }
            return amount;
        }catch (Exception e) {
            throw new Exception("Failed to parse amount : " + value);
        }
    }

    public Integer validateTenure(String value) throws Exception{
        try {
            Integer tenure = Integer.parseInt(value);
            if (tenure < 0) {
                throw new Exception("Tenure cannot be negative. given : " + value);
            }
            return tenure;
        }catch (Exception e) {
            throw new Exception("Failed to parse tenure : " + value);
        }
    }

    public Double validateRoi(String value) throws Exception{
        try {
            Double roi = Double.parseDouble(value);
            if (roi < 0) {
                throw new Exception("roi cannot be negative. given : " + value);
            }
            return roi;
        }catch (Exception e) {
            throw new Exception("Failed to parse roi : " + value);
        }
    }

    public Integer validateEmiNo(String value) throws Exception {
        try {
            Integer emiNo = Integer.parseInt(value);
            if (emiNo < 0) {
                throw new Exception("Emi no cannot be negative. given : " + value);
            }
            return emiNo;
        }catch (Exception e) {
            throw new Exception("Failed to parse emi no : " + value);
        }
    }
}
