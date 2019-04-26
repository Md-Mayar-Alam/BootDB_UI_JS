package com.security.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import com.security.exception.JwtExpiredTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class RawAccessJwtToken implements JwtToken{
	private static Logger logger= LoggerFactory.getLogger(RawAccessJwtToken.class);
	
	private final String token;
	
	public RawAccessJwtToken(String token) {
		System.out.println("Inside RawAccessJwtToken constructor");
		this.token= token;
	}
	
	public Jws<Claims> parseClaims(String signingKey){
		System.out.println("Inside RawAccessJwtToken parseClaims");
		try {
			return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
		}catch(UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
			logger.error("Invalid JWT token "+ ex);
			throw new BadCredentialsException("Invalid JWT token "+ex);
		}catch(ExpiredJwtException ex) {
			logger.error("JWT Token is expired "+ex);
			throw new JwtExpiredTokenException(this, "JWT Token is expired", ex);
		}
	}
	
	@Override
	public String getToken() {
		return token;
	}
}
