package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.UserRegistration;
import com.repository.UserRegistrationRepository;

@Service
public class UserRegistrationService {

	private final UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	public UserRegistrationService(UserRegistrationRepository userRegistrationRepository) {
		this.userRegistrationRepository= userRegistrationRepository;
	}
	
	public Optional<UserRegistration> getByUsername(String username){
		return userRegistrationRepository.findByUsername(username);
	}
}
