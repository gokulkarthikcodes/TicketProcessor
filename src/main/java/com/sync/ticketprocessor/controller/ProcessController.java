package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.dto.ProcessDTO;
import com.sync.ticketprocessor.service.ProcessService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import com.sync.ticketprocessor.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    ProcessService processService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProcessDTO>> getMyProcesses(Authentication authentication) {
        List<ProcessDTO> processes = null;
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        processes = processService.getMyProcesses(userPrincipal.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(processes);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ProcessDTO> createProcess(Authentication authentication, @RequestBody ProcessDTO processDTO) {
        Validator.validateProcessDTO(processDTO);
        processDTO.createAuditForSave(authentication);
        processDTO = processService.createProcess(processDTO);
        return ResponseEntity.status(HttpStatus.OK).body(processDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<ProcessDTO> updateProcess(Authentication authentication, @RequestBody ProcessDTO processDTO) {
        Validator.validateProcessDTO(processDTO);
        processDTO.createAuditForUpdate(authentication);
        processDTO = processService.updateProcess(processDTO);
        return ResponseEntity.status(HttpStatus.OK).body(processDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteProcess(Authentication authentication, @RequestBody ProcessDTO processDTO) {
        Boolean flag = processService.deleteProcess(processDTO);
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }
}
