package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import com.sync.ticketprocessor.conversion.Converter;
import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.entity.Customer;
import com.sync.ticketprocessor.exception.RecordAlreadyExistsException;
import com.sync.ticketprocessor.exception.RecordNotFoundException;
import com.sync.ticketprocessor.repository.CustomerRepository;
import com.sync.ticketprocessor.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Resource
    CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getMyCustomers(String createdBy) {
        List<Customer> customers = null;
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        try {
            customers = customerRepository.findByCreatedBy(createdBy);
            customers.stream().forEach(customer -> customerDTOList.add(Converter.convertCustomerFromEntityToDTO(customer)));
        } catch (Exception e) {
            logger.error("Error saving customer", e);
        }
        return customerDTOList;
    }

    @Override
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        validateCustomerForSave(customerDTO);
        Customer customer = Converter.convertCustomerFromDTOToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return Converter.convertCustomerFromEntityToDTO(customer);
    }

    @Override
    @Transactional
    public Boolean deleteCustomer(CustomerDTO customerDTO) {
        validateCustomerForDelete(customerDTO);
        Customer customer = customerRepository.deleteByIdAndCreatedBy(customerDTO.getId(), customerDTO.getCreatedBy());
        return null != customer;
    }

    public void validateCustomerForDelete(CustomerDTO customerDTO) {
        Customer existing = customerRepository.findByIdAndCreatedBy(customerDTO.getId(), customerDTO.getCreatedBy());
        if(null == existing)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + customerDTO.getCompanyName());
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        validateCustomerForUpdate(customerDTO);
        Customer customer = Converter.convertCustomerFromDTOToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return Converter.convertCustomerFromEntityToDTO(customer);
    }

    @Override
    public boolean checkUniqueCompanyName(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByCompanyNameAndCreatedBy(customerDTO.getCompanyName(), customerDTO.getCreatedBy());
        return null == customer;
    }

    @Override
    public boolean checkUniquePrimaryContactNumber(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByPrimaryContactNumberAndCreatedBy(customerDTO.getPrimaryContactNumber(), customerDTO.getCreatedBy());
        return null == customer;
    }

    @Override
    public boolean checkUniqueGST(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByGstAndCreatedBy(customerDTO.getGst(), customerDTO.getCreatedBy());
        return null == customer;
    }

    @Override
    public boolean checkUniqueEmailId(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByEmailIdAndCreatedBy(customerDTO.getEmailId(), customerDTO.getCompanyName());
        return null == customer;
    }

    private boolean validateCustomerForUpdate(CustomerDTO customerDTO) {
        boolean isUnique = validateUpdateValuesForUniqueNess(customerDTO);
        if (!isUnique)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + customerDTO.getCompanyName() + ConstantsUtil.SPACE + customerDTO.getPrimaryContactNumber() + ConstantsUtil.SPACE + customerDTO.getGst() + ConstantsUtil.SPACE + customerDTO.getEmailId());

        boolean isExists = validateIfUpdateRecordStillExists(customerDTO);
        if (!isExists)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + customerDTO.getCompanyName());

        return true;
    }

    private boolean validateUpdateValuesForUniqueNess(CustomerDTO customerDTO) {
        String paramCompanyName = ConstantsUtil.getDefaultArgument(customerDTO.getCompanyName());
        String paramEmailId = ConstantsUtil.getDefaultArgument(customerDTO.getEmailId());
        String paramGST = ConstantsUtil.getDefaultArgument(customerDTO.getGst());
        String paramPrimaryContactNumber = ConstantsUtil.getDefaultArgument(customerDTO.getPrimaryContactNumber());

        List<Customer> existingList = customerRepository.findCustomers(customerDTO.getCreatedBy(), paramCompanyName, paramPrimaryContactNumber, paramGST, paramEmailId);
        return existingList.isEmpty() || existingList.get(0).getId().equals(customerDTO.getId());
    }


    private boolean validateIfUpdateRecordStillExists(CustomerDTO customerDTO) {
        Customer current = customerRepository.findByIdAndCreatedBy(customerDTO.getId(), customerDTO.getCreatedBy());
        return null != current;
    }

    private boolean validateCustomerForSave(CustomerDTO customerDTO) {
        boolean isUnique = validateSaveValuesForUniqueNess(customerDTO);
        if (!isUnique)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + customerDTO.getCompanyName() + ConstantsUtil.SPACE + customerDTO.getPrimaryContactNumber() + ConstantsUtil.SPACE + customerDTO.getGst() + ConstantsUtil.SPACE + customerDTO.getEmailId());
        return true;
    }

    private boolean validateSaveValuesForUniqueNess(CustomerDTO customerDTO) {
        String paramCompanyName = ConstantsUtil.getDefaultArgument(customerDTO.getCompanyName());
        String paramEmailId = ConstantsUtil.getDefaultArgument(customerDTO.getEmailId());
        String paramGST = ConstantsUtil.getDefaultArgument(customerDTO.getGst());
        String paramPrimaryContactNumber = ConstantsUtil.getDefaultArgument(customerDTO.getPrimaryContactNumber());

        List<Customer> existingList = customerRepository.findCustomers(customerDTO.getCreatedBy(), paramCompanyName, paramPrimaryContactNumber, paramGST, paramEmailId);
        return existingList.isEmpty();
    }

}
