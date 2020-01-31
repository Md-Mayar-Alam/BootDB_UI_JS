package com.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.security.config.JwtConfig;
import com.security.model.AccessJwtToken;
import com.security.model.JwtToken;
import com.security.model.UserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This class is responsible to create Access and Refresh Token.
 * 
 * @author Mohammad Mayar Alam
 * @see com.security.model.AccessJwtToken
 */

@Component
public class JwtTokenFactory {

	private final JwtConfig jwtConfig;
	
	@Autowired
	public JwtTokenFactory(JwtConfig jwtConfig) {
		System.out.println("Inside JwtTokenFactory constructor");
		this.jwtConfig= jwtConfig;
	}
	
	/**
	 * Factory method for creating JWTAccessToken
	 * To create JWT token we need some informations like username, authorities etc
	 * all that kind of information we are providing in UserContext object
	 * @param userContext
	 * @return com.security.model.AccessJwtToken object
	 */
	public AccessJwtToken createAccessJwtToken(UserContext userContext) {
		System.out.println("Inside JwtTokenFactory createAccessJwtToken");
		checkUserContext(userContext);

		Claims claims= Jwts.claims().setSubject(userContext.getUsername());
		claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()) );
		
		LocalDateTime currentTime= LocalDateTime.now();
		
		String token= Jwts.builder()
							.setClaims(claims)
							.setIssuer(jwtConfig.getTokenIssuer())
							.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
							.setExpiration(Date.from(currentTime
									.plusMinutes(jwtConfig.getTokenExpirationTimeMs())
									.atZone(ZoneId.systemDefault()).toInstant()))
							.signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSigningKey())
							.compact();
		return new AccessJwtToken(token, claims);
	}

	/**
	 * Factory method for creating JWT Refresh Token.
	 * @param userContext
	 * @return com.security.model.JwtToken object
	 */
	public AccessJwtToken createRefreshToken(UserContext userContext) {
		System.out.println("Inside JwtTokenFactory createRefreshToken");
		checkUserContext(userContext);
		
		Claims claims= Jwts.claims().setSubject(userContext.getUsername());
		claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()) );
		
		LocalDateTime currentTime= LocalDateTime.now();
		
		String token= Jwts.builder()
							.setClaims(claims)
							.setIssuer(jwtConfig.getTokenIssuer())
							.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
							.setExpiration(Date.from(currentTime
										.plusMinutes(jwtConfig.getRefreshTokenExpTimeMs())
										.atZone(ZoneId.systemDefault()).toInstant()))
							.signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSigningKey())
							.compact();
		return new AccessJwtToken(token, claims);
	}
	
	/*
	 * method to check username and authorities is valid or not 
	 */
	private void checkUserContext(UserContext userContext) {
		System.out.println("Inside JwtTokenFactory checkUserContext");
		
		/**
		 * IllegalArgumentException should be thrown when an illegal or inappropriate argument
		 * is passed. 
		 */
		if(StringUtils.isBlank(userContext.getUsername())) {
			throw new IllegalArgumentException("Cannot create JWT token without username");
		}
		
		if(userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
			throw new IllegalArgumentException("User doesn't have any authorities");
		}
	}
}
