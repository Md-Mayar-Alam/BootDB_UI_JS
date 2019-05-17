package com.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.security.config.JwtConfig;
import com.security.model.JwtAuthenticationToken;
import com.security.model.RawAccessJwtToken;
import com.security.model.UserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider{

	private final JwtConfig jwtConfig;
	
	public JwtAuthenticationProvider(JwtConfig jwtConfig) {
		System.out.println("Inside JwtAuthenticationProvider constructor");
		this.jwtConfig= jwtConfig;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("Inside JwtAuthenticationProvider authenticate");
		RawAccessJwtToken rawAccessToken= (RawAccessJwtToken)authentication.getCredentials();
		
		Jws<Claims> jwsClaims= rawAccessToken.parseClaims(jwtConfig.getTokenSigningKey());
		
		String subject= jwsClaims.getBody().getSubject();
		List<String> scopes= jwsClaims.getBody().get("scopes", List.class);
		List<GrantedAuthority> authorities= scopes.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
		
		UserContext userContext= UserContext.create(subject, authorities);
		return new JwtAuthenticationToken(userContext, authorities);
	}
	
	/**
	 * This method is called to verify that which AuthenticationProvider should be used 
	 * on the basis of object passed as an argument in authenticate() of  corresponding attemptAuthentication()
	 * for ex: in this case where the user is already authenticated then the 
	 * attemptAuthentication() of CustomLoginAuthenticationProcessingFilter Class will be called 
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("Inside JwtAuthenticationProvider supports");
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
