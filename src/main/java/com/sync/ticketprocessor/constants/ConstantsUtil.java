package com.sync.ticketprocessor.constants;

public class ConstantsUtil {

    private ConstantsUtil(){

    }

    public static final String SPACE = " ";
    public static final String CUSTOMER_DETAILS_ERROR = "Error getting saving customer details";
    public static final String PROCESS_DETAILS_ERROR = "Error with process request";
    public static final String INVALID_ARGUMENT_IN_REQUEST = "INVALID ARGUMENT IN REQUEST";
    public static final String RECORD_NOT_FOUND = "RECORD_NOT_FOUND";
    public static final String RECORD_NOT_FOUND_FOR = "RECORD NOT FOUND FOR";
    public static final String RECORD_ALREADY_EXISTS = "RECORD ALREADY EXISTS";
    public static final String RECORD_ALREADY_EXISTS_FOR = "RECORD ALREADY EXISTS FOR";
    public static final String INVALID_EMAIL_ID = "INVALID EMAIL ID";
    public static final String INVALID_PHONE_NUMBER = "INVALID PHONE NUMBER";
    public static final String INVALID_PIN_CODE = "INVALID PIN CODE";
    public static final String INVALID_GST = "INVALID_GST";
    public static final String INVALID_COMPANY_NAME = "INVALID COMPANY NAME";
    public static final String INVALID_CITY = "INVALID CITY";
    public static final String INVALID_STATE = "INVALID_STATE";

    public static String getDefaultArgument(String uniqueArgument){
        if(null == uniqueArgument || uniqueArgument.length() == 0)
            return "d3567187481197c992e6888c2b619287";
        else
            return uniqueArgument;
    }

}
