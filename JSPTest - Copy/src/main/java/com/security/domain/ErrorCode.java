package com.security.domain;

/*
 * Enumeration for Error codes
 * @author Md Mayar Alam
 */

public enum ErrorCode {
	GLOBAL(2),
	AUTHENTICATION(10),
	JWT_TOKEN_EXPIRED(11);
	
	private final int errorCode;
	
	private ErrorCode(int errorCode) {
		this.errorCode= errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
