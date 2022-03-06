package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

	CustomerDTO createCustomer(CustomerDTO customerDTO);

	List<CustomerDTO> getMyCustomers(String userId);

	Boolean deleteCustomer(String id, String createdBy);

	CustomerDTO updateCustomer(CustomerDTO customerDTO);
}
