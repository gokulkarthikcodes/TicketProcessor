package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.entity.Address;
import com.sync.ticketprocessor.entity.Customer;
import com.sync.ticketprocessor.repository.CustomerCrudRepository;
import com.sync.ticketprocessor.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

	private boolean validateCustomerForSave(CustomerDTO customerDTO){
		Customer existing = customerCrudRepository.findByIdAndCreatedBy(customerDTO.getId(),customerDTO.getCreatedBy());
		if(null == existing){
			if(customerDTO.getCompanyName().length() <= 3)
				return false;
			if(customerDTO.getPrimaryContactNumber().length() != 10)
				return false;
			if(customerDTO.getGst() != null && customerDTO.getGst().length() != 15)
				return false;
			if(customerDTO.getEmailId().length() <=3)
				return false;
			if(customerDTO.getCity().length() <= 3)
				return false;
			if(customerDTO.getState().length() <=3)
				return false;
			if(customerDTO.getZipCode().length() != 6)
				return false;
		}
		else
			return false;

		return true;

	}

	private Customer convertToEntityForSave(CustomerDTO customerDTO){
          Customer customer = new Customer();
		  Address address = new Address();

		  if (null != customerDTO.getId())
			  customer.setId(customerDTO.getId());
		  customer.setCustomerName(customerDTO.getCustomerName());
		  customer.setCompanyName(customerDTO.getCompanyName());
		  customer.setPrimaryContactNumber(customerDTO.getPrimaryContactNumber());
		  customer.setGst(customerDTO.getGst());
		  customer.setEmailId(customerDTO.getEmailId());
		  customer.setCreatedBy(customerDTO.getCreatedBy());
		  customer.setCreated(customerDTO.getCreated());
		  customer.setActive(customerDTO.isActive());
		  address.setStreetLine1(customerDTO.getStreetLine1());
		  address.setStreetLine2(customerDTO.getStreetLine2());
		  address.setCity(customerDTO.getCity());
		  address.setState(customerDTO.getState());
		  address.setZipCode(customerDTO.getZipCode());
		  customer.setAddress(address);
		  return customer;

	}

	private CustomerDTO convertToDTOFromEntity(Customer customer){
          CustomerDTO customerDTO = new CustomerDTO();
		  customerDTO.setId(customer.getId());
		  customerDTO.setCustomerName(customer.getCustomerName());
		  customerDTO.setCompanyName(customer.getCompanyName());
		  customerDTO.setPrimaryContactNumber(customer.getPrimaryContactNumber());
		  customerDTO.setGst(customer.getGst());
		  customerDTO.setEmailId(customer.getEmailId());
		  customerDTO.setCreatedBy(customer.getCreatedBy());
		  customerDTO.setCreated(customer.getCreated());
		  customerDTO.setActive(customer.isActive());
		  customerDTO.setStreetLine1(customer.getAddress().getStreetLine1());
		  customerDTO.setStreetLine2(customer.getAddress().getStreetLine2());
		  customerDTO.setCity(customer.getAddress().getCity());
		  customerDTO.setState(customer.getAddress().getState());
		  customerDTO.setZipCode(customer.getAddress().getZipCode());
		  return customerDTO;
	}

	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		boolean isValidated = validateCustomerForSave(customerDTO);
		if(isValidated)
		{
			Customer customer = convertToEntityForSave(customerDTO);
			customer = customerCrudRepository.save(customer);
			return convertToDTOFromEntity(customer);
		}
		return null;
	}

	@Override
	public List<CustomerDTO> getMyCustomers(String createdBy) {
		List<Customer> customers = null;
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		try {
			customers = customerCrudRepository.findByCreatedBy(createdBy);
			customers.stream().forEach(customer -> customerDTOList.add(convertToDTOFromEntity(customer)));
		} catch (Exception e) {
			logger.error("Error saving customer", e);
		}
		return customerDTOList;
	}

	@Override
	public Boolean deleteCustomer(String id, String createdBy) {
		Boolean flag = false;
		try {
			Customer customer = customerCrudRepository.deleteByIdAndCreatedBy(id, createdBy);
			if (!ObjectUtils.isEmpty(customer)) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("Error saving customer", e);
		}
		return flag;
	}

	private boolean validateCustomerForUpdate(CustomerDTO customerDTO){
		Customer existing = customerCrudRepository.findByIdAndCreatedBy(customerDTO.getId(),customerDTO.getCreatedBy());
		if(null != existing){
			if(customerDTO.getCompanyName().length() <= 3)
				return false;
			if(customerDTO.getPrimaryContactNumber().length() != 10)
				return false;
			if(customerDTO.getGst() != null && customerDTO.getGst().length() != 15)
				return false;
			if(customerDTO.getEmailId().length() <=3)
				return false;
			if(customerDTO.getCity().length() <= 3)
				return false;
			if(customerDTO.getState().length() <=3)
				return false;
			if(customerDTO.getZipCode().length() != 6)
				return false;
		}
		else
			return false;

		return true;

	}

	@Override
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
		boolean isValidated = validateCustomerForUpdate(customerDTO);
		if(isValidated)
		{
			Customer customer = convertToEntityForSave(customerDTO);
			customer = customerCrudRepository.save(customer);
			return convertToDTOFromEntity(customer);
		}
		return null;
	}
}
