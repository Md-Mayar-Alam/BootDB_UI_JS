package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.User;
import com.repository.DatabaseUserRepository;

@Service
public class DatabaseUserService {
	
	private final DatabaseUserRepository databaseUserRepository;
	
	@Autowired
	public DatabaseUserService(DatabaseUserRepository databaseUserRepository) {
		this.databaseUserRepository= databaseUserRepository;
	}
	
	public Optional<User> findByUserId(Long userId){
		return databaseUserRepository.findById(userId);
	}
}
