package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.UserRegistration;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long>{
	
	@Query("select u from UserRegistration u join fetch u.user.userRoles r where u.username=:username")
	public Optional<UserRegistration> findByUsername(@Param("username")String username);
}
