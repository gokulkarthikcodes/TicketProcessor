package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.dto.CustomerDTO;
import com.sync.ticketprocessor.service.CustomerService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import com.sync.ticketprocessor.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Resource
    CustomerService customerService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CustomerDTO>> getMyCustomers(Authentication authentication) {
        List<CustomerDTO> customerDTOList = null;
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        customerDTOList = customerService.getMyCustomers(userPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOList);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<CustomerDTO> createCustomer(Authentication authentication,
                                                      @RequestBody CustomerDTO customerDTO) {
        Validator.validateCustomerDTO(customerDTO);
        customerDTO.createAuditForSave(authentication);
        customerDTO = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<CustomerDTO> updateCustomer(Authentication authentication,
                                                      @RequestBody CustomerDTO customerDTO) {
        Validator.validateCustomerDTO(customerDTO);
        customerDTO.createAuditForUpdate(authentication);
        customerDTO = customerService.updateCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteCustomer(@RequestBody CustomerDTO customerDTO) {
        Boolean flag = customerService.deleteCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }

    @PostMapping(value="/checkUniqueCompanyName")
    public ResponseEntity<Boolean> checkUniqueCompanyName(Authentication authentication, @RequestBody CustomerDTO customerDTO){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        customerDTO.setCreatedBy(userPrincipal.getUsername());
        boolean flag = customerService.checkUniqueCompanyName(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }

    @PostMapping(value="/checkUniquePrimaryContactNumber")
    public ResponseEntity<Boolean> checkUniquePrimaryContactNumber(Authentication authentication, @RequestBody CustomerDTO customerDTO){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        customerDTO.setCreatedBy(userPrincipal.getUsername());
        boolean flag = customerService.checkUniquePrimaryContactNumber(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }

    @PostMapping(value="/checkUniqueGST")
    public ResponseEntity<Boolean> checkUniqueGST(Authentication authentication, @RequestBody CustomerDTO customerDTO){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        customerDTO.setCreatedBy(userPrincipal.getUsername());
        boolean flag = customerService.checkUniqueGST(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }

    @PostMapping(value="/checkUniqueEmailId")
    public ResponseEntity<Boolean> checkUniqueEmailId(Authentication authentication, @RequestBody CustomerDTO customerDTO){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        customerDTO.setCreatedBy(userPrincipal.getUsername());
        boolean flag = customerService.checkUniqueEmailId(customerDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }
}
