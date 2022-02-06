package com.sync.ticketprocessor.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sync.ticketprocessor.entity.User;

public interface UserCrudRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
