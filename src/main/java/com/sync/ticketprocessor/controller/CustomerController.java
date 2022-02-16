package com.sync.ticketprocessor.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sync.ticketprocessor.entity.Customer;
import com.sync.ticketprocessor.service.CustomerService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;

@RestController
public class CustomerController {

	@Resource
	CustomerService customerService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> createCustomer(Authentication authentication,
			@Valid @RequestBody Customer customer) {
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			customer.createAudit(userPrincipal.getId(), userPrincipal.getUsername());
			customer = customerService.createCustomer(customer);
		} catch (Exception e) {
			logger.error("Error getting saving customer details", e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customer);
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> updateCustomer(Authentication authentication,
			@Valid @RequestBody Customer customer) {
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			customer.createAudit(userPrincipal.getId(), userPrincipal.getUsername());
			customer = customerService.createCustomer(customer);
		} catch (Exception e) {
			logger.error("Error getting saving customer details", e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customer);
	}

	@RequestMapping(value = "/getMyCustomers", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> getMyCustomers(Authentication authentication) {
		List<Customer> customers = null;
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			customers = customerService.getMyCustomers(userPrincipal.getId());
		} catch (Exception e) {
			logger.error("Error getting saving customer details", e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customers);
	}

	@RequestMapping(value = "/deleteCustomer/{companyId}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> deleteCustomer(Authentication authentication, @PathVariable String companyId) {
		Boolean flag = false;
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			flag = customerService.deleteCustomer(userPrincipal.getId(), companyId);
		} catch (Exception e) {
			logger.error("Error getting saving customer details", e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(flag);
	}

}
