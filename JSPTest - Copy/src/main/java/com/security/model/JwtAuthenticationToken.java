package com.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
/**
 *  public abstract class AbstractAuthenticationToken extends Object implements Authentication, CredentialsContainer
 *	AbstractAuthenticationToken Base class for Authentication objects.
 *	
 *	JwtAuthenticationToken class will also act as Authentication object
 *	
 *	@see com.security.jwt.JwtTokenAuthenticationProcessingFilter
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 2877954820905567501L;
	private RawAccessJwtToken token;
	private UserContext userContext;
	public JwtAuthenticationToken(RawAccessJwtToken token) {
		super(null);
		this.token= token;
		this.setAuthenticated(false);
		System.out.println("Inside JwtAuthenticationToken(RawAccessJwtToken token) ");
	}
	
	public JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.eraseCredentials();
		this.userContext= userContext;
		super.setAuthenticated(true);
		System.out.println("Inside JwtAuthenticationToken(UserContext userContext, Collection<? extends GrantedAuthority> authorities) ");
	}
	
	@Override
	public void setAuthenticated(boolean authenticated) {
		if(authenticated) {
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}
		
		super.setAuthenticated(false);
		System.out.println("Inside JwtAuthenticationToken setAuthenticated");
	}
	
	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.token= null;
		System.out.println("Inside JwtAuthenticationToken eraseCredentials");
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
