package com.sync.ticketprocessor.controller;

import com.sync.ticketprocessor.dto.ProcessDTO;
import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.service.ProcessService;
import com.sync.ticketprocessor.service.impl.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sync.ticketprocessor.constants.ConstantsUtil.PROCESS_DETAILS_ERROR;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {

    @Autowired
    ProcessService processService;

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @GetMapping(value = "/all")
    public ResponseEntity<List<Process>> getProcessByCreatedBy(Authentication authentication) {
        List<Process> processes = null;
        try{
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            processes = processService.getProcessByCreatedBy(userPrincipal.getUsername());
        } catch (Exception e){
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(processes);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Process> saveProcess(Authentication authentication, @RequestBody ProcessDTO processDTO) {
        Process savedProcess = null;
        try {
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            processDTO.setCreatedBy(userPrincipal.getUsername());
            savedProcess = processService.saveProcess(processDTO);
        } catch (Exception e){
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedProcess);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Process> updateProcess(Authentication authentication, @RequestBody ProcessDTO processDTO) {
        Process updatedProcess = null;
        try {
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            processDTO.setUpdatedBy(userPrincipal.getUsername());
            updatedProcess = processService.updateProcess(processDTO);
        } catch (Exception e){
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedProcess);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteProcess(Authentication authentication, @RequestBody ProcessDTO processDTO) {
        Boolean flag = false;
        try {
            flag = processService.deleteByIdAndCreatedBy(processDTO);
        } catch (Exception e) {
            logger.error(PROCESS_DETAILS_ERROR, e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }
}
