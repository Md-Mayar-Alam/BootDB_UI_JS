package com.security.util;

public enum SecurityConstants {
	DOMAIN("localhost"),
	AUTHORIZATION("Authorization"),
	REFRESH_TOKEN_COOKIE("REFRESH_TOKEN_COOKIE");
	
	private final String value;
	
	SecurityConstants(String value) {
		this.value= value;
	}

	public String getValue() {
		return value;
	}
}
