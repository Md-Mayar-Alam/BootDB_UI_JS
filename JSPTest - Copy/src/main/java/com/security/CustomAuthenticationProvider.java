package com.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.model.UserRegistration;
import com.security.model.UserContext;
import com.service.UserRegistrationService;

/**
 * This class is responsible for verifying the username and credentials from the DB.
 * It also contains the supports method which is used to call the authenticate method
 * of this class on the basis of object passed as an argument in authenticate method
 * 
 * @author Mohammad Mayar Alam
 * @see com.security.CustomLoginAuthenticationProcessingFilter
 */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	/*@Autowired
	public CustomAuthenticationProvider(UserRegistrationService userRegistrationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder= bCryptPasswordEncoder;
		this.userRegistrationService= userRegistrationService;
	}*/
	
	/**
	 * Method responsible for verifying the username and password from the DB.
	 * The Authentication object as parameter in this method is of UsernamePasswordAuthenticationToken
	 * which contains our username and password and the same will be used to verify the details from the DB.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("Inside CustomAuthenticationProvider authenticate");
		Assert.notNull(authentication, "No authentication data provided");
		
		String username= (String)authentication.getPrincipal();
		String password= (String)authentication.getCredentials();
		
		UserRegistration userRegistration= userRegistrationService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found "+ username));
		
		if(!bCryptPasswordEncoder.matches(password, userRegistration.getPassword())) {
			throw new BadCredentialsException("Authentication Failed. Password not valid");
		}
		
		if(userRegistration.getUser().getUserRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
		
		List<GrantedAuthority> authorities= userRegistration.getUser().getUserRoles().stream()
										.map(authority -> new SimpleGrantedAuthority(authority.getRole().getUserRole()))
										.collect(Collectors.toList());
		
		UserContext userContext= UserContext.create(userRegistration.getUsername(), authorities);
		
		/**
		 * we are providing here userContext as principal in UsernamePasswordAuthenticationToken
		 * constructor because at the time of token creation we are checking for the username
		 * and authorities of customer.
		 * See Reference:- 
		 * createAccessToken(UserContext userContext) method of JwtTokenFactory and
		 * checkUserContext method of JwtTokenFactory
		 * 
		 * also if the user is successfully authenticated then this userContext which we are setting 
		 * here will be used in the class CustomAuthenticationSuccessHandler
		 * onAuthenticationSuccess() method for creating accessToken and refreshToken
		 */
		return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
	}
	
	/**
	 * This method is called to verify that which AuthenticationProvider should be used 
	 * on the basis of object passed as an argument in authenticate() of  corresponding attemptAuthentication()
	 * for ex: in this case where the user is already authenticated then the 
	 * attemptAuthentication() of JwtTokenAuthenticationProcessingFilter Class will be called 
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("Inside CustomAuthenticationProvider supports");
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
