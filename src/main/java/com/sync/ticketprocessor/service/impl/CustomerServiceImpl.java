package com.sync.ticketprocessor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.sync.ticketprocessor.entity.Customer;
import com.sync.ticketprocessor.repository.CustomerCrudRepository;
import com.sync.ticketprocessor.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Resource
	CustomerCrudRepository customerCrudRepository;

	@Override
	@Transactional
	public Customer createCustomer(Customer customerReq) {
		Customer customer = new Customer();
		try {
			customer = customerCrudRepository.save(customerReq);
		} catch (Exception e) {
			logger.error("Error saving customer", e);
		}
		return customer;

	}

	@Override
	public List<Customer> getMyCustomers(String userId) {
		List<Customer> customers = null;
		try {
			customers = customerCrudRepository.findByCreatedBy(userId);
		} catch (Exception e) {
			logger.error("Error saving customer", e);
		}
		return customers;
	}

	@Override
	public Boolean deleteCustomer(String userId, String companyId) {
		Boolean flag = false;
		try {
			Customer customer = customerCrudRepository.deleteByIdAndCreatedBy(companyId, userId);
			if (!ObjectUtils.isEmpty(customer)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("Error saving customer", e);
		}
		return flag;
	}
}
