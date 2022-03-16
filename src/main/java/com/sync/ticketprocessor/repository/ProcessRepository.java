package com.sync.ticketprocessor.repository;

import com.sync.ticketprocessor.entity.Process;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProcessRepository extends MongoRepository<Process,String> {

    @Query("{'createdBy' :?0}")
    List<Process> getProcessByCreatedBy(String createdBy);

    List<Process> findProcessByProcessNameAndCreatedBy(String processName, String createdBy);

    Process save(Process process);

    Process deleteByIdAndCreatedBy(String id, String createdBy);

    @Query("{'processName': ?0, 'createdBy' :?1}")
    Process findProcessByNameAndCreatedBy(String processName, String createdBy);

    @Query("{'id': ?0, 'createdBy' :?1}")
    Process findProcessByIdAndCreatedBy(String id, String createdBy);

    @Query(value = "{ '_id' : {'$in' : ?0 } }")
    List<Process> findAllProcessInIdList(List<String> processIdList);
}

