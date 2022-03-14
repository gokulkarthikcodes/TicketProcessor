package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import com.sync.ticketprocessor.conversion.Converter;
import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.entity.Customer;
import com.sync.ticketprocessor.exception.RecordAlreadyExistsException;
import com.sync.ticketprocessor.exception.RecordNotFoundException;
import com.sync.ticketprocessor.repository.CustomerCrudRepository;
import com.sync.ticketprocessor.service.CustomerService;
import com.sync.ticketprocessor.validation.Validator;
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
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        validateCustomerForSave(customerDTO);
        Customer customer = Converter.convertCustomerFromDTOToEntity(customerDTO);
        customer = customerCrudRepository.save(customer);
        return Converter.convertCustomerFromEntityToDTO(customer);
    }

    @Override
    public List<CustomerDTO> getMyCustomers(String createdBy) {
        List<Customer> customers = null;
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        try {
            customers = customerCrudRepository.findByCreatedBy(createdBy);
            customers.stream().forEach(customer -> customerDTOList.add(Converter.convertCustomerFromEntityToDTO(customer)));
        } catch (Exception e) {
            logger.error("Error saving customer", e);
        }
        return customerDTOList;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        validateCustomerForUpdate(customerDTO);
        Customer customer = Converter.convertCustomerFromDTOToEntity(customerDTO);
        customer = customerCrudRepository.save(customer);
        return Converter.convertCustomerFromEntityToDTO(customer);
    }

    private boolean validateCustomerForUpdate(CustomerDTO customerDTO) {

        boolean isUnique = validateUpdateValuesForUniqueNess(customerDTO);
        if (!isUnique)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + customerDTO.getCustomerName() + ConstantsUtil.SPACE + customerDTO.getPrimaryContactNumber() + ConstantsUtil.SPACE + customerDTO.getGst() + ConstantsUtil.SPACE + customerDTO.getEmailId());

        boolean isExists = validateIfUpdateRecordStillExists(customerDTO);
        if (!isExists)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + customerDTO.getCustomerName());

        Validator.validateCustomerDTO(customerDTO);
        return true;
    }

    private boolean validateUpdateValuesForUniqueNess(CustomerDTO customerDTO) {
        String paramCompanyName = ConstantsUtil.getDefaultArgument(customerDTO.getCompanyName());
        String paramEmailId = ConstantsUtil.getDefaultArgument(customerDTO.getEmailId());
        String paramGST = ConstantsUtil.getDefaultArgument(customerDTO.getGst());
        String paramPrimaryContactNumber = ConstantsUtil.getDefaultArgument(customerDTO.getPrimaryContactNumber());

        List<Customer> existingList = customerCrudRepository.findCustomers(customerDTO.getCreatedBy(), paramCompanyName, paramPrimaryContactNumber, paramGST, paramEmailId);
        return existingList.isEmpty() || existingList.get(0).getId().equals(customerDTO.getId());
    }


    private boolean validateIfUpdateRecordStillExists(CustomerDTO customerDTO) {
        Customer existing = customerCrudRepository.findByIdAndCreatedBy(customerDTO.getId(), customerDTO.getCreatedBy());
        return null != existing;
    }

    private boolean validateCustomerForSave(CustomerDTO customerDTO) {
        boolean isUnique = validateSaveValuesForUniqueNess(customerDTO);
        if (!isUnique)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + customerDTO.getCustomerName() + ConstantsUtil.SPACE + customerDTO.getPrimaryContactNumber() + ConstantsUtil.SPACE + customerDTO.getGst() + ConstantsUtil.SPACE + customerDTO.getEmailId());
        Validator.validateCustomerDTO(customerDTO);
        return true;
    }

    private boolean validateSaveValuesForUniqueNess(CustomerDTO customerDTO) {
        String paramCompanyName = ConstantsUtil.getDefaultArgument(customerDTO.getCompanyName());
        String paramEmailId = ConstantsUtil.getDefaultArgument(customerDTO.getEmailId());
        String paramGST = ConstantsUtil.getDefaultArgument(customerDTO.getGst());
        String paramPrimaryContactNumber = ConstantsUtil.getDefaultArgument(customerDTO.getPrimaryContactNumber());

        List<Customer> existingList = customerCrudRepository.findCustomers(customerDTO.getCreatedBy(), paramCompanyName, paramPrimaryContactNumber, paramGST, paramEmailId);
        return existingList.isEmpty();
    }

}
