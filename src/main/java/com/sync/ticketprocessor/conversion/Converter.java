package com.sync.ticketprocessor.conversion;

import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.dto.ProcessDTO;
import com.sync.ticketprocessor.dto.ProductDTO;
import com.sync.ticketprocessor.dto.VendorDTO;
import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.entity.*;

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

    public static ProcessDTO covertProcessFromEntityToDTO(Process process){
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(process.getId());
        processDTO.setProcessName(process.getProcessName());
        processDTO.setCreatedBy(process.getCreatedBy());
        processDTO.setCreated(process.getCreated());
        processDTO.setUpdatedBy(process.getUpdatedBy());
        processDTO.setUpdated(process.getUpdated());
        return processDTO;
    }

    public static Process convertProcessFromDTOToEntity(ProcessDTO processDTO){
        Process process = new Process();
        process.setId(processDTO.getId());
        process.setProcessName(processDTO.getProcessName());
        process.setCreatedBy(processDTO.getCreatedBy());
        process.setCreated(processDTO.getCreated());
        process.setUpdatedBy(processDTO.getUpdatedBy());
        process.setUpdated(processDTO.getUpdated());
        return process;
    }

    public static Vendor convertVendorFromDTOToEntity(VendorDTO vendorDTO){
        Vendor vendor = new Vendor();
        Address address = new Address();

        vendor.setId(vendorDTO.getId());
        vendor.setUpdated(vendorDTO.getUpdated());
        vendor.setUpdatedBy(vendorDTO.getUpdatedBy());
        vendor.setVendorName(vendorDTO.getVendorName());
        vendor.setPrimaryContactNumber(vendorDTO.getPrimaryContactNumber());
        vendor.setEmailId(vendorDTO.getEmailId());
        vendor.setAllocatedProcesses(vendorDTO.getAllocatedProcesses());
        vendor.setCreatedBy(vendorDTO.getCreatedBy());
        vendor.setCreated(vendorDTO.getCreated());
        vendor.setActive(vendorDTO.isActive());
        address.setStreetLine1(vendorDTO.getStreetLine1());
        address.setStreetLine2(vendorDTO.getStreetLine2());
        address.setCity(vendorDTO.getCity());
        address.setState(vendorDTO.getState());
        address.setPinCode(vendorDTO.getPinCode());
        vendor.setAddress(address);
        return vendor;
    }

    public static VendorDTO convertVendorFromEntityToDTO(Vendor vendor){
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setUpdated(vendor.getUpdated());
        vendorDTO.setUpdatedBy(vendor.getUpdatedBy());
        vendorDTO.setId(vendor.getId());
        vendorDTO.setVendorName(vendor.getVendorName());
        vendorDTO.setPrimaryContactNumber(vendor.getPrimaryContactNumber());
        vendorDTO.setEmailId(vendor.getEmailId());
        vendorDTO.setAllocatedProcesses(vendor.getAllocatedProcesses());
        vendorDTO.setCreatedBy(vendor.getCreatedBy());
        vendorDTO.setCreated(vendor.getCreated());
        vendorDTO.setActive(vendor.isActive());
        vendorDTO.setStreetLine1(vendor.getAddress().getStreetLine1());
        vendorDTO.setStreetLine2(vendor.getAddress().getStreetLine2());
        vendorDTO.setCity(vendor.getAddress().getCity());
        vendorDTO.setState(vendor.getAddress().getState());
        vendorDTO.setPinCode(vendor.getAddress().getPinCode());
        return vendorDTO;
    }

    public static ProductDTO covertProductFromEntityToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setCreatedBy(product.getCreatedBy());
        productDTO.setCreated(product.getCreated());
        productDTO.setUpdatedBy(product.getUpdatedBy());
        productDTO.setUpdated(product.getUpdated());
        return productDTO;
    }

    public static Product convertProductFromDTOToEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setCreatedBy(productDTO.getCreatedBy());
        product.setCreated(productDTO.getCreated());
        product.setUpdatedBy(productDTO.getUpdatedBy());
        product.setUpdated(productDTO.getUpdated());
        return product;
    }
}
