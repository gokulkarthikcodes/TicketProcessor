package com.sync.ticketprocessor.repository;

import com.sync.ticketprocessor.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {

    @Query("{'createdBy' :?0}")
    List<Product> getProductsByCreatedBy(String createdBy);

    @Query("{'productName': ?0, 'createdBy' :?1}")
    Product findProductsByNameAndCreatedBy(String productName, String createdBy);

    @Query("{'id': ?0, 'createdBy' :?1}")
    Product findProductByIdAndCreatedBy(String id, String createdBy);

    Product deleteByIdAndCreatedBy(String id, String createdBy);

    List<Product> findProductsByProductNameAndCreatedBy(String productName, String createdBy);
}
