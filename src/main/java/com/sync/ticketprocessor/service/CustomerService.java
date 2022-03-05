package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.entity.Customer;

import java.util.List;

public interface CustomerService {

	Customer createCustomer(Customer customer);

	CustomerDTO createCustomer(CustomerDTO customerDTO);

	List<CustomerDTO> getMyCustomers(String userId);

	Boolean deleteCustomer(String id, String createdBy);

	CustomerDTO updateCustomer(CustomerDTO customerDTO);
}
