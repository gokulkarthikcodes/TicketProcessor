package com.sync.ticketprocessor.service;

import java.util.List;

import com.sync.ticketprocessor.entity.Customer;

public interface CustomerService {

	Customer createCustomer(Customer customer);

	List<Customer> getMyCustomers(String userId);

	Boolean deleteCustomer(String userId, String companyId);
}
