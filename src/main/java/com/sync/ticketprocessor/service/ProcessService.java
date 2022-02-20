package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.entity.Process;

import java.util.List;

public interface ProcessService {

    Process getProcessByName(String processName);


    Process getProcessById(Integer processId);

    List<Process> getProcessByCreatedBy(String createdBy);

    List<Process> getAllProcess();

    Process saveProcess(Process process);

    Boolean deleteByProcessNameAndCreatedBy(String processName,String createdBy);

    Boolean updateProcess(Process process);
}
