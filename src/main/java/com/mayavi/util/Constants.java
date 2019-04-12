package com.mayavi.util;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

public class Constants {
    public static final String DMI_SUBMIT_CONTACT_API_NAME = "submit-contact";
    public static final String DMI_NSDL_CHECK_API_NAME = "dmi-nsdl-check";
    public static final String DMI_QUERY_LEAD_API_NAME = "dmi-query-lead";
    public static final String DMI_CREATE_LEAD_API_NAME = "dmi-create-lead";
    public static final String DMI_CREATE_CONTACT_API_NAME = "dmi-create-contact";
//    public static final String DMI_CIBIL_API_NAME = "dmi-cibil";
    public static final String DMI_QUERY_BANK_DETAIL_API_NAME = "dmi-query-bank-detail";
    public static final String DMI_GET_LEAD_API_NAME = "dmi-get-lead";

    public static final String DMI_SUBMIT_CONTACT_REGEX = ".*select.*id.*from.*Contact.*where.*Lead";
    public static final String DMI_QUERY_BANK_DETAIL_REGEX = ".*select.*id.*from.*Bank_Detail.*where.*Contact";
    public static final String DMI_QUERY_LEAD_DETAIL_REGEX = ".*select.*id.*from.*Lead.*where.*Application_Id";
}
