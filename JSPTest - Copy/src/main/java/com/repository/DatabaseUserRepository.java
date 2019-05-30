package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.User;

@Repository 
public interface DatabaseUserRepository extends JpaRepository<User, Long>{

	
}
