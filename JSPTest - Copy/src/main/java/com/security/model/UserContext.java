package com.security.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {

	private final String username;
	private final List<GrantedAuthority> authorities;
	
	private UserContext(String username, List<GrantedAuthority> authorities) {
		System.out.println("Inside UserContext constructor");
		this.username= username;
		this.authorities= authorities;
	}
	
	public static UserContext create(String username, List<GrantedAuthority> authorities) {
		System.out.println("Inside UserContext create");
		if(StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("username is blank: "+username);
		}
		return new UserContext(username, authorities);
	}

	public String getUsername() {
		return username;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
}
