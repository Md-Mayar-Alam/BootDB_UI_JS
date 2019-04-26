package com.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken{

	private RawAccessJwtToken token;
	private UserContext userContext;
	public JwtAuthenticationToken(RawAccessJwtToken token) {
		super(null);
		this.token= token;
		System.out.println("Inside JwtAuthenticationToken(RawAccessJwtToken token) ");
	}
	
	public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.userContext= userContext;
		System.out.println("Inside JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) ");
	}
	
	@Override
	public Object getCredentials() {
		return token;
	}
	@Override
	public Object getPrincipal() {
		return this.userContext;
	}

}
