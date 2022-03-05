package com.sync.ticketprocessor.repository;

import com.sync.ticketprocessor.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerCrudRepository extends MongoRepository<Customer, String> {

	List<Customer> findByCreatedBy(String createdBy);

	Customer deleteByIdAndCreatedBy(String companyId, String userId);

	Customer findByIdAndCreatedBy(String companyId, String userId);

}
