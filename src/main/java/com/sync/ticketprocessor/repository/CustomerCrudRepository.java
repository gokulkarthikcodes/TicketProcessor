package com.sync.ticketprocessor.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sync.ticketprocessor.entity.Customer;

public interface CustomerCrudRepository extends MongoRepository<Customer, String> {

	List<Customer> findByCreatedBy(String userId);

	Customer deleteByIdAndCreatedBy(String companyId, String userId);

}
