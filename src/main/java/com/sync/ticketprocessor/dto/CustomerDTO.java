package com.sync.ticketprocessor.dto;

import com.sync.ticketprocessor.entity.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends Auditable {

    private String customerName;
    private String companyName;
    private String primaryContactNumber;
    private String secContactNumber;
    private String gst;
    private String emailId;
    private String streetLine1;
    private String streetLine2;
    private String city;
    private String state;
    private String country;
    private String pinCode;

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerName='" + customerName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", primaryContactNumber='" + primaryContactNumber + '\'' +
                ", secContactNumber='" + secContactNumber + '\'' +
                ", gst='" + gst + '\'' +
                ", emailId='" + emailId + '\'' +
                ", streetLine1='" + streetLine1 + '\'' +
                ", streetLine2='" + streetLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + pinCode + '\'' +
                '}';
    }
}
