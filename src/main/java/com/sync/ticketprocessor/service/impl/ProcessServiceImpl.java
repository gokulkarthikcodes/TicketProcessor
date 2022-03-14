package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import com.sync.ticketprocessor.conversion.Converter;
import com.sync.ticketprocessor.dto.ProcessDTO;
import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.exception.RecordAlreadyExistsException;
import com.sync.ticketprocessor.exception.RecordNotFoundException;
import com.sync.ticketprocessor.repository.ProcessRepository;
import com.sync.ticketprocessor.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    ProcessRepository processRepository;


    @Override
    public List<ProcessDTO> getMyProcesses(String createdBy) {
        List<Process> processes = null;
        List<ProcessDTO> processDTOList = new ArrayList<>();
        processes = processRepository.getProcessByCreatedBy(createdBy);
        processes.stream().forEach(process -> processDTOList.add(Converter.covertProcessFromEntityToDTO(process)));
        return processDTOList;
    }

    @Override
    public ProcessDTO createProcess(ProcessDTO processDTO) {
        validateProcessForSave(processDTO);
        Process process = Converter.convertProcessFromDTOToEntity(processDTO);
        process = processRepository.save(process);
        return Converter.covertProcessFromEntityToDTO(process);
    }


    @Override
    public ProcessDTO updateProcess(ProcessDTO processDTO) {
        validateProcessForUpdate(processDTO);
        Process process = Converter.convertProcessFromDTOToEntity(processDTO);
        process = processRepository.save(process);
        return Converter.covertProcessFromEntityToDTO(process);
    }

    @Override
    public Boolean deleteProcess(ProcessDTO processDTO) {
        validateProcessForDelete(processDTO);
        Process process = processRepository.deleteByIdAndCreatedBy(processDTO.getId(), processDTO.getCreatedBy());
        return null != process;
    }

    public boolean validateProcessForDelete(ProcessDTO processDTO) {
        Process existing = processRepository.findProcessByIdAndCreatedBy(processDTO.getId(), processDTO.getCreatedBy());
        if(null == existing)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + processDTO.getProcessName());
        return true;
    }

    private void validateProcessForSave(ProcessDTO processDTO) {
        validateSaveValuesForUniqueNess(processDTO);
    }

    private void validateSaveValuesForUniqueNess(ProcessDTO processDTO) {
        Process existing = processRepository.findProcessByNameAndCreatedBy(processDTO.getProcessName(), processDTO.getCreatedBy());
        if(null != existing)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + processDTO.getProcessName());
    }

    public boolean validateProcessForUpdate(ProcessDTO processDTO) {
        boolean isUnique = validateUpdateValuesForUniqueness(processDTO);
        if (!isUnique) {
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + processDTO.getProcessName());
        }
        boolean isExists = validateIfUpdateRecordStillExists(processDTO);
        if(!isExists)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + processDTO.getProcessName());

        return true;
    }

    private boolean validateUpdateValuesForUniqueness(ProcessDTO processDTO) {
        List<Process> existingList = processRepository.findProcessByProcessNameAndCreatedBy(processDTO.getProcessName(), processDTO.getCreatedBy());
        return existingList.isEmpty() || existingList.get(0).getId().equals(processDTO.getId());
    }

    private boolean validateIfUpdateRecordStillExists(ProcessDTO processDTO){
        Process current = processRepository.findProcessByIdAndCreatedBy(processDTO.getId(), processDTO.getCreatedBy());
        return null != current;
    }
}
