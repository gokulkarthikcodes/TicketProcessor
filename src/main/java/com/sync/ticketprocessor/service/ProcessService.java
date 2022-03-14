package com.sync.ticketprocessor.service;

import com.sync.ticketprocessor.dto.ProcessDTO;

import java.util.List;

public interface ProcessService {

    List<ProcessDTO> getMyProcesses(String createdBy);

    ProcessDTO createProcess(ProcessDTO processDTO);

    Boolean deleteProcess(ProcessDTO processDTO);

    ProcessDTO updateProcess(ProcessDTO processDTO);
}
