package com.sync.ticketprocessor.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "vendor")
@Getter
@Setter
public class Vendor extends Auditable{

    private String vendorName;
    private String primaryContactNumber;
    private String secContactNumber;
    private String emailId;
    private Address address;
    private List<String> allocatedProcesses;
}
