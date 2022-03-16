package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.dto.VendorDTO;
import com.sync.ticketprocessor.service.VendorService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import com.sync.ticketprocessor.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping(value = "/vendor")
public class VendorController {

    @Resource
    VendorService vendorService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<VendorDTO>> getMyCustomers(Authentication authentication) {
        List<VendorDTO> vendorDTOList = null;
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        vendorDTOList = vendorService.getMyVendors(userPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(vendorDTOList);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<VendorDTO> createVendor(Authentication authentication,
                                                  @RequestBody VendorDTO vendorDTO) {
        Validator.validateVendorDTO(vendorDTO);
        vendorDTO.createAuditForSave(authentication);
        vendorDTO = vendorService.createVendor(vendorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(vendorDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<VendorDTO> updateVendor(Authentication authentication,
                                                      @RequestBody VendorDTO vendorDTO) {
        Validator.validateVendorDTO(vendorDTO);
        vendorDTO.createAuditForUpdate(authentication);
        vendorDTO = vendorService.updateVendor(vendorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(vendorDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteVendor(@RequestBody VendorDTO vendorDTO) {
        Boolean flag = vendorService.deleteVendor(vendorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }
}
