package com.sync.ticketprocessor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private String customerName;
    private String companyName;
    private String primaryContactNumber;
    private String secContactNumber;
    private String emailId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;


}
