package com.sync.ticketprocessor.repository;

import com.sync.ticketprocessor.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	List<Customer> findByCreatedBy(String createdBy);

	Customer deleteByIdAndCreatedBy(String companyId, String userId);

	Customer findByIdAndCreatedBy(String companyId, String userId);

	@Query("{'createdBy' : ?0,$or:[{'emailId' : ?4,},{'primaryContactNumber' : ?2},{'companyName' : ?1},{'gst' : ?3}]}")
	List<Customer> findCustomers(String createdBy, String companyName, String primaryContactNumber, String gst, String email);

	Customer findByCompanyNameAndCreatedBy(String companyName, String createdBy);

	Customer findByPrimaryContactNumberAndCreatedBy(String primaryContactNumber, String createdBy);

	Customer findByGstAndCreatedBy(String gst, String createdBy);

	Customer findByEmailIdAndCreatedBy(String emailId, String createdBy);
}
