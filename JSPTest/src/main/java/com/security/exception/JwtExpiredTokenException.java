package com.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.security.model.JwtToken;

public class JwtExpiredTokenException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public JwtExpiredTokenException(String msg) {
		super(msg);
	}

	public JwtExpiredTokenException(JwtToken token, String msg, Throwable exception) {
		super(msg, exception);
	}

}
