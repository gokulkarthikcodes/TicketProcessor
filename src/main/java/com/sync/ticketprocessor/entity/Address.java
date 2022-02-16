package com.sync.ticketprocessor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

	private String streetLine1;
	private String streetLine2;
	private String city;
	private String state;
	private String country;
	private String zipCode;

}
