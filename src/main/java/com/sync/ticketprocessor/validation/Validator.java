package com.sync.ticketprocessor.validation;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.exception.InputValidationFailedException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {

    private Validator(){

    }

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String phoneRegex = "^[6-9]\\d{9}$";
    private static final String pinCodeRegex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
    private static final String gstRegex = "^([0][1-9]|[1-2][0-9]|[3][0-7])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$";
    private static final String companyNameRegex = "^[.@&]?[a-zA-Z0-9 ]+[ !.@&()]?[ a-zA-Z0-9!()]+";
    private static final String cityRegex = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";
    private static final String stateRegex = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";

    private static boolean validateEmail(String emailAddress) {
        if(null == emailAddress || emailAddress.length() == 0)
            return true;
        boolean check = Pattern.compile(emailRegex)
                .matcher(emailAddress)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_EMAIL_ID);
        return true;
    }

    private static boolean validatePhone(String phoneNumber) {
        if(null == phoneNumber || phoneNumber.length() == 0)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_PHONE_NUMBER);
        boolean check =  Pattern.compile(phoneRegex)
                .matcher(phoneNumber)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_PHONE_NUMBER);
        return true;
    }

    private static boolean validatePinCode(String pinCode) {
        if(null == pinCode || pinCode.length() == 0)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_PIN_CODE);
        boolean check = Pattern.compile(pinCodeRegex)
                .matcher(pinCode)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_PIN_CODE);
        return true;
    }

    private static boolean validateGst(String gst){
        if(null == gst || gst.length() == 0)
            return true;
        boolean check = Pattern.compile(gstRegex)
                .matcher(gst)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_GST);
        return true;
    }

    private static boolean validateCompanyName(String companyName){
        if(null == companyName || companyName.length() == 0)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_COMPANY_NAME);
        boolean check = Pattern.compile(companyNameRegex)
                .matcher(companyName)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_COMPANY_NAME);
        return true;
    }

    private static boolean validateCity(String city){
        if(null == city || city.length() == 0)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_CITY);
        boolean check = Pattern.compile(cityRegex)
                .matcher(city)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_CITY);
        return true;
    }

    private static boolean validateState(String state){
        if(null == state || state.length() == 0)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_STATE);
        boolean check = Pattern.compile(stateRegex)
                .matcher(state)
                .matches();
        if(!check)
            throw new InputValidationFailedException(ConstantsUtil.INVALID_STATE);
        return true;
    }

    public static boolean validateCustomerDTO(CustomerDTO customerDTO){
        validateCompanyName(customerDTO.getCompanyName());
        validatePhone(customerDTO.getPrimaryContactNumber());
        validateGst(customerDTO.getGst());
        validateEmail(customerDTO.getEmailId());
        validateCity(customerDTO.getCity());
        validateState(customerDTO.getState());
        validatePinCode(customerDTO.getPinCode());
        return true;
    }
}
