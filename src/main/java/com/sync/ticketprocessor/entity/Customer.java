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

	@Override
	public String toString() {
		return "Customer{" +
				"customerName='" + customerName + '\'' +
				", companyName='" + companyName + '\'' +
				", primaryContactNumber='" + primaryContactNumber + '\'' +
				", secContactNumber='" + secContactNumber + '\'' +
				", gst='" + gst + '\'' +
				", emailId='" + emailId + '\'' +
				", address=" + address +
				'}';
	}
}
