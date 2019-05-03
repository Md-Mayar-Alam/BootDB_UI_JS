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

import com.model.UserRegistration;
import com.security.model.UserContext;
import com.service.UserRegistrationService;

/*
 * 
 * @author Md. Mayar Alam
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
		
		return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("Inside CustomAuthenticationProvider supports");
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
