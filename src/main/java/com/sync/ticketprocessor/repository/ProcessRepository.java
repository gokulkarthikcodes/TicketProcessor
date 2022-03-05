package com.sync.ticketprocessor.repository;

import com.sync.ticketprocessor.entity.Process;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProcessRepository extends MongoRepository<Process,String> {

    @Query("{'processName':?0}")
    Process getProcessByName(String processName);

    @Query("{'processId':?0}")
    Process getProcessById(Integer processId);

    @Query("{'createdBy' :?0}")
    List<Process> getProcessByCreatedBy(String createdBy);

    Process save(Process process);

    Process deleteByIdAndCreatedBy(String id, String createdBy);

    @Query("{'processOrder': ?0, 'createdBy' :?1}")
    Process findProcessByOrderAndCreatedBy(Integer processOrder, String createdBy);

    @Query("{'processName': ?0, 'createdBy' :?1}")
    Process findProcessByNameAndCreatedBy(String processName, String createdBy);

    @Query("{'id': ?0, 'createdBy' :?1}")
    Process findProcessByIdAndCreatedBy(String id, String createdBy);
}

