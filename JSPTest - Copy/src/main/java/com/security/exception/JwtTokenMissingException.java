package com.security.exception;

import io.jsonwebtoken.JwtException;

public class JwtTokenMissingException extends JwtException{
	
	private static final long serialVersionUID = -5369985581097918217L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}

}
