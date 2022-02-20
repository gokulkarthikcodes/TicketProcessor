package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.repository.ProcessRepository;
import com.sync.ticketprocessor.service.ProcessService;
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
    public List<Process> getAllProcess(){
        return processRepository.findAll();
    }

    @Override
    public Process saveProcess(Process process) {
        Process existing;
        existing = processRepository.findProcessByOrderAndCreatedBy(process.getProcessOrder(),process.getCreatedBy());
        if(null == existing)
             return processRepository.save(process);
        else
            return null;
    }

    @Override
    public Boolean deleteByProcessNameAndCreatedBy(String processName, String createdBy) {
        Process process;
        process = processRepository.deleteByProcessNameAndCreatedBy(processName,createdBy);
        if(null != process)
            return true;
        else
            return false;
    }

    @Override
    public Boolean updateProcess(Process process) {
        Process existing = processRepository.findProcessByOrderAndCreatedBy(process.getProcessOrder(),process.getCreatedBy());
        if(existing != null)
        {
            if(existing.getProcessOrder() == process.getProcessOrder()){
                return false;
            }
            else{
                processRepository.save(process);
                return true;
            }
        }
        else
            return false;
    }
}
