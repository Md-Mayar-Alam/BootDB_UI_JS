package com.security.exception;

import org.springframework.security.access.AuthorizationServiceException;

public class JwtTokenMissingException extends AuthorizationServiceException{
	
	private static final long serialVersionUID = -5369985581097918217L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}

}
