package com.sync.ticketprocessor.conversion;

import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.entity.Address;
import com.sync.ticketprocessor.entity.Customer;

public class Converter {

    private Converter(){
    }

    public static Customer convertCustomerFromDTOToEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        Address address = new Address();

        customer.setId(customerDTO.getId());
        customer.setUpdated(customerDTO.getUpdated());
        customer.setUpdatedBy(customerDTO.getUpdatedBy());
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
        address.setPinCode(customerDTO.getPinCode());
        customer.setAddress(address);
        return customer;
    }

    public static CustomerDTO convertCustomerFromEntityToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUpdated(customer.getUpdated());
        customerDTO.setUpdatedBy(customer.getUpdatedBy());
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
        customerDTO.setPinCode(customer.getAddress().getPinCode());
        return customerDTO;
    }
}
