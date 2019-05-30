package com.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.UserRegistration;

@Repository
public interface UserRegistrationRepository extends CrudRepository<UserRegistration, Long>{
	
	public Optional<UserRegistration> findByUsername(String username);
}
