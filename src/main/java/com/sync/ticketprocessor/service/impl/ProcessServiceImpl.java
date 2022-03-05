package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.dto.ProcessDTO;
import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.repository.ProcessRepository;
import com.sync.ticketprocessor.service.ProcessService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    ProcessRepository processRepository;

    @Override
    public Process getProcessByName(String processName) {
        return processRepository.getProcessByName(processName);
    }

    @Override
    public Process getProcessById(Integer processId) {
        return processRepository.getProcessById(processId);
    }

    @Override
    public List<Process> getProcessByCreatedBy(String createdBy) {
        return processRepository.getProcessByCreatedBy(createdBy);
    }

    @Override
    public List<Process> getAllProcess() {
        return processRepository.findAll();
    }

    @Override
    public Process saveProcess(ProcessDTO processDTO) {
        boolean isValidated = validateForSave(processDTO);
        if (isValidated) {
            Process process = convertToEntityForSave(processDTO);
            return processRepository.save(process);
        } else {
            return null;
        }
    }

    private Process convertToEntityForSave(ProcessDTO processDTO) {
        Process process = new Process();
        process.setProcessName(processDTO.getProcessName());
        process.setCreatedBy(processDTO.getCreatedBy());
        process.setCreated(DateTime.now());
        return process;
    }



    public boolean validateForSave(ProcessDTO processDTO) {
        Process existing = processRepository.findProcessByNameAndCreatedBy(processDTO.getProcessName(), processDTO.getCreatedBy());
        if (null == existing) {
            if (processDTO.getProcessName().length() < 3) return false;
            return processDTO.getCreatedBy() != null;
        } else {
            return false;
        }
    }

    @Override
    public Process updateProcess(ProcessDTO processDTO) {
        boolean isValidated = validateForUpdate(processDTO);
        if (isValidated) {
            Process process = convertToEntityForUpdate(processDTO);
            return processRepository.save(process);
        } else {
            return null;
        }
    }

    public boolean validateForUpdate(ProcessDTO processDTO) {
        Process existing = processRepository.findProcessByIdAndCreatedBy(processDTO.getId(), processDTO.getCreatedBy());
        if (null != existing) { //Process exists in the DB
            processDTO.setCreatedBy(existing.getCreatedBy());
            processDTO.setCreated(existing.getCreated());
            return processDTO.getProcessName().length() >= 3 && processDTO.getUpdatedBy() != null;
        } else {
            return false;
        }
    }

    private Process convertToEntityForUpdate(ProcessDTO processDTO) {
        Process process = new Process();
        process.setId(processDTO.getId());
        process.setCreatedBy(processDTO.getCreatedBy());
        process.setCreated(processDTO.getCreated());
        process.setProcessName(processDTO.getProcessName());
        process.setUpdatedBy(processDTO.getUpdatedBy());
        process.setUpdated(DateTime.now());
        return process;
    }


    @Override
    public Boolean deleteByIdAndCreatedBy(ProcessDTO processDTO) {
        boolean isValidated = validateForDelete(processDTO);
        if(isValidated){
            processRepository.deleteByIdAndCreatedBy(processDTO.getId(), processDTO.getCreatedBy());
            return true;
        }
        return false;
    }

    public boolean validateForDelete(ProcessDTO processDTO){
        Process existing = processRepository.findProcessByIdAndCreatedBy(processDTO.getId(), processDTO.getCreatedBy());
        return existing != null;
    }
}
