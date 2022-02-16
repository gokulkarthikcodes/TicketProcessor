package com.sync.ticketprocessor.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "customer")
@Getter
@Setter
public class Customer extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String customerName;
	private String companyName;
	private String pContactNumber;
	private String sContactNumber;
	private String emailId;
	private Address address;
}
