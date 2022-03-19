package com.sync.ticketprocessor.service.impl;

import com.sync.ticketprocessor.constants.ConstantsUtil;
import com.sync.ticketprocessor.conversion.Converter;
import com.sync.ticketprocessor.dto.VendorDTO;
import com.sync.ticketprocessor.entity.Process;
import com.sync.ticketprocessor.entity.Vendor;
import com.sync.ticketprocessor.exception.RecordAlreadyExistsException;
import com.sync.ticketprocessor.exception.RecordNotFoundException;
import com.sync.ticketprocessor.repository.ProcessRepository;
import com.sync.ticketprocessor.repository.VendorRepository;
import com.sync.ticketprocessor.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class VendorServiceImpl implements VendorService {

    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Resource
    VendorRepository vendorRepository;

    @Resource
    ProcessRepository processRepository;

    @Override
    @Transactional
    public List<VendorDTO> getMyVendors(String createdBy) {
        List<Vendor> vendors = null;
        List<VendorDTO> vendorDTOList = new ArrayList<>();
        try {
            vendors = vendorRepository.findByCreatedBy(createdBy);
            vendors.stream().forEach(vendor -> vendorDTOList.add(Converter.convertVendorFromEntityToDTO(vendor)));
        } catch (Exception e) {
            logger.error("Error while fetching the list of vendors", e);
        }
        return vendorDTOList;
    }


    @Override
    @Transactional
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        validateVendorForSave(vendorDTO);
        Vendor vendor = Converter.convertVendorFromDTOToEntity(vendorDTO);
        vendor = vendorRepository.save(vendor);
        return Converter.convertVendorFromEntityToDTO(vendor);
    }

    @Override
    @Transactional
    public Boolean deleteVendor(VendorDTO vendorDTO) {
        validateVendorForDelete(vendorDTO);
        Vendor vendor = vendorRepository.deleteByIdAndCreatedBy(vendorDTO.getId(), vendorDTO.getCreatedBy());
        return null != vendor;
    }

    @Override
    @Transactional
    public VendorDTO updateVendor(VendorDTO vendorDTO) {
        validateVendorForUpdate(vendorDTO);
        Vendor vendor = Converter.convertVendorFromDTOToEntity(vendorDTO);
        vendor = vendorRepository.save(vendor);
        return Converter.convertVendorFromEntityToDTO(vendor);
    }



    private boolean  validateVendorForSave(VendorDTO vendorDTO) {
        boolean isUnique = validateSaveValuesForUniqueNess(vendorDTO);
        if (!isUnique)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + vendorDTO.getVendorName() + ConstantsUtil.SPACE + vendorDTO.getPrimaryContactNumber() + ConstantsUtil.SPACE + vendorDTO.getEmailId());
        List<String> notFoundProcessList = validateAllProcessesById(vendorDTO);
        if(!notFoundProcessList.isEmpty()) {
            String ids = notFoundProcessList.stream()
                    .map(n -> n.toString())
                    .collect(Collectors.joining(","));
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + " Proccess Ids" + ConstantsUtil.SPACE + ":" + ConstantsUtil.SPACE + ids);
        }

        return true;
    }

    private boolean validateSaveValuesForUniqueNess(VendorDTO vendorDTO) {
        String paramVendorName = ConstantsUtil.getDefaultArgument(vendorDTO.getVendorName());
        String paramEmailId = ConstantsUtil.getDefaultArgument(vendorDTO.getEmailId());
        String paramPrimaryContactNumber = ConstantsUtil.getDefaultArgument(vendorDTO.getPrimaryContactNumber());

        List<Vendor> existingList = vendorRepository.findVendors(vendorDTO.getCreatedBy(), paramVendorName, paramPrimaryContactNumber, paramEmailId);
        return existingList.isEmpty();
    }

    private List<String> validateAllProcessesById(VendorDTO vendorDTO) {
        List<String> notFoundProcessList = null;
        List<Process> processList = processRepository.findByProcessNameList(vendorDTO.getAllocatedProcesses());
        Set<String> processNameSet = processList.stream().map(Process::getProcessName).collect(Collectors.toSet());

        notFoundProcessList = vendorDTO.getAllocatedProcesses().stream()
                .filter(processName -> !processNameSet.contains(processName))
                .collect(Collectors.toList());

        return notFoundProcessList;
    }

    public void validateVendorForDelete(VendorDTO vendorDTO) {
        Vendor existing = vendorRepository.findByIdAndCreatedBy(vendorDTO.getId(), vendorDTO.getCreatedBy());
        if(null == existing)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + vendorDTO.getVendorName());
    }

    private boolean validateVendorForUpdate(VendorDTO vendorDTO) {
        boolean isUnique = validateUpdateValuesForUniqueNess(vendorDTO);
        if (!isUnique)
            throw new RecordAlreadyExistsException(ConstantsUtil.RECORD_ALREADY_EXISTS_FOR + ConstantsUtil.SPACE + vendorDTO.getVendorName() + ConstantsUtil.SPACE + vendorDTO.getPrimaryContactNumber() + ConstantsUtil.SPACE + vendorDTO.getEmailId());

        boolean isExists = validateIfUpdateRecordStillExists(vendorDTO);
        if (!isExists)
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + ConstantsUtil.SPACE + vendorDTO.getVendorName());

        List<String> notFoundProcessList = validateAllProcessesById(vendorDTO);
        if(!notFoundProcessList.isEmpty()) {
            String ids = notFoundProcessList.stream()
                    .map(n -> n.toString())
                    .collect(Collectors.joining(","));
            throw new RecordNotFoundException(ConstantsUtil.RECORD_NOT_FOUND_FOR + " Proccess Ids" + ConstantsUtil.SPACE + ":" + ConstantsUtil.SPACE + ids);
        }

        return true;
    }

    private boolean validateUpdateValuesForUniqueNess(VendorDTO vendorDTO) {
        String paramVendorName = ConstantsUtil.getDefaultArgument(vendorDTO.getVendorName());
        String paramEmailId = ConstantsUtil.getDefaultArgument(vendorDTO.getEmailId());
        String paramPrimaryContactNumber = ConstantsUtil.getDefaultArgument(vendorDTO.getPrimaryContactNumber());

        List<Vendor> existingList = vendorRepository.findVendors(vendorDTO.getCreatedBy(), paramVendorName, paramPrimaryContactNumber, paramEmailId);
        return existingList.isEmpty() || existingList.get(0).getId().equals(vendorDTO.getId());
    }

    private boolean validateIfUpdateRecordStillExists(VendorDTO vendorDTO) {
        Vendor current = vendorRepository.findByIdAndCreatedBy(vendorDTO.getId(), vendorDTO.getCreatedBy());
        return null != current;
    }



}
