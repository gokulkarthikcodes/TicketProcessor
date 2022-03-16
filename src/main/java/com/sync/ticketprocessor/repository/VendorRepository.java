package com.sync.ticketprocessor.repository;

import com.sync.ticketprocessor.entity.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VendorRepository extends MongoRepository<Vendor, String> {

    Vendor findByIdAndCreatedBy(String vendorId, String userId);

    List<Vendor> findByCreatedBy(String createdBy);

    Vendor deleteByIdAndCreatedBy(String vendorId, String userId);

    @Query("{'createdBy' : ?0,$or:[{'emailId' : ?3,},{'primaryContactNumber' : ?2},{'vendorName' : ?1}]}")
    List<Vendor> findVendors(String createdBy, String paramVendorName, String paramPrimaryContactNumber, String paramEmailId);


}
