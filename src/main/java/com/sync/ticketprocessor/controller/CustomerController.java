package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.service.CustomerService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.sync.ticketprocessor.constants.ConstantsUtil.CUSTOMER_DETAILS_ERROR;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Resource
	CustomerService customerService;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@PostMapping(value = "/save")
	public ResponseEntity<CustomerDTO> createCustomer(Authentication authentication,
			@Valid @RequestBody CustomerDTO customerDTO) {
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
	        customerDTO.createAuditForSave(userPrincipal.getUsername());
			customerDTO = customerService.createCustomer(customerDTO);
		} catch (Exception e) {
			logger.error(CUSTOMER_DETAILS_ERROR, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<CustomerDTO> updateCustomer(Authentication authentication,
			@Valid @RequestBody CustomerDTO customerDTO) {
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			customerDTO.createAuditForUpdate(userPrincipal.getUsername());
			customerDTO = customerService.updateCustomer(customerDTO);
		} catch (Exception e) {
			logger.error(CUSTOMER_DETAILS_ERROR, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<CustomerDTO>> getMyCustomers(Authentication authentication) {
		List<CustomerDTO> customerDTOList = null;
		try {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			customerDTOList = customerService.getMyCustomers(userPrincipal.getUsername());
		} catch (Exception e) {
			logger.error(CUSTOMER_DETAILS_ERROR, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customerDTOList);
	}

	@PostMapping(value = "/delete")
	public ResponseEntity<Boolean> deleteCustomer(Authentication authentication, @RequestBody CustomerDTO customerDTO) {
		Boolean flag = false;
		try {
			flag = customerService.deleteCustomer(customerDTO.getId(), customerDTO.getCreatedBy());
		} catch (Exception e) {
			logger.error(CUSTOMER_DETAILS_ERROR, e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(flag);
	}
}
