package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.dto.ProcessDTO;
import com.sync.ticketprocessor.entity.Process;

import java.util.List;

public interface ProcessService {

    Process getProcessByName(String processName);


    Process getProcessById(Integer processId);

    List<Process> getProcessByCreatedBy(String createdBy);

    List<Process> getAllProcess();

    Process saveProcess(ProcessDTO processDTO);

    Boolean deleteByIdAndCreatedBy(ProcessDTO processDTO);

    Process updateProcess(ProcessDTO processDTO);
}
