package com.sync.ticketprocessor.dto;

import com.sync.ticketprocessor.entity.Auditable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendorDTO extends Auditable {

    private String vendorName;
    private String primaryContactNumber;
    private String secContactNumber;
    private String emailId;
    private String streetLine1;
    private String streetLine2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private List<String> allocatedProcesses;
}
