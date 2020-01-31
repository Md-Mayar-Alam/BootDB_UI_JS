package com.security.model;

import io.jsonwebtoken.Claims;

/**
 * This class is used to represent raw JWT token
 * @author Mohammad Mayar Alam
 * @see com.security.jwt.JwtTokenFactory
 */

public final class AccessJwtToken implements JwtToken{

	private final String rawToken;
	private final Claims claims;
	
	public AccessJwtToken(String rawToken, Claims claims) {
		System.out.println("Inside AccessJwtToken constructor");
		this.rawToken= rawToken;
		this.claims= claims;
	}
	
	@Override
	public String getToken() {
		return this.rawToken;
	}

	public Claims getClaims() {
		return this.claims;
	}
}
