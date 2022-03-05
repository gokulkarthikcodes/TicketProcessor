package com.sync.ticketprocessor.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Getter
@Setter
public class Customer extends Auditable{

	private String customerName;
	private String companyName;
	private String primaryContactNumber;
	private String secContactNumber;
	private String gst;
	private String emailId;
	private Address address;

}
