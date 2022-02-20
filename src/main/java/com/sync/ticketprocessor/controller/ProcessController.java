package com.sync.ticketprocessor.controller;

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

    @GetMapping(value = "/getbyname/{processName}")
    public ResponseEntity<Process> getProcessByName(@PathVariable String processName) {
        Process process = null;
        try {
            process = processService.getProcessByName(processName);
        } catch (Exception e) {
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(process);
    }

    @GetMapping(value = "/getbyid/{processId}")
    public ResponseEntity<Process> getProcessById(@PathVariable Integer processId) {
        Process process = null;
        try {
            process = processService.getProcessById(processId);
        } catch (Exception e) {
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(process);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Process>> getAllProcess() {
        List<Process> processes = null;
        try {
            processes = processService.getAllProcess();
        } catch (Exception e) {
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(processes);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Process> saveProcess(@RequestBody Process process) {
        Process savedProcess = null;
        try {
            savedProcess = processService.saveProcess(process);
        } catch (Exception e){
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(savedProcess);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Boolean> updateProcess(@RequestBody Process process) {
        Boolean updateStatus = false;
        try {
            updateStatus = processService.updateProcess(process);
        } catch (Exception e){
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(updateStatus);
    }

    @GetMapping(value = "/getbycreatedby/{createdBy}")
    public ResponseEntity<List<Process>> getProcessByCreatedBy(@PathVariable String createdBy) {
        List<Process> processes = null;
        try{
            processes = processService.getProcessByCreatedBy(createdBy);
        } catch (Exception e){
            logger.error(PROCESS_DETAILS_ERROR,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(processes);
    }

    @GetMapping(value = "/deletebyname/{processName}")
    public ResponseEntity<Boolean> deleteProcess(Authentication authentication, @PathVariable String processName) {
        Boolean flag = false;
        try {
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            flag = processService.deleteByProcessNameAndCreatedBy(processName,userPrincipal.getUsername());
        } catch (Exception e) {
            logger.error(PROCESS_DETAILS_ERROR, e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(flag);
    }
}
