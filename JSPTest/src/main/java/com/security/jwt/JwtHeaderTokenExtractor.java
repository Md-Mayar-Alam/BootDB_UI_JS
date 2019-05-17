package com.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor{
	public static String HEADER_PREFIX= "Bearer_";

	@Override
	public String extract(String header) {
		System.out.println("Inside JwtHeaderTokenExtractor extract");
		if(StringUtils.isBlank(header)) {
			throw new AuthorizationServiceException("Authorization header can't be blank");
		}
		
		if(!header.startsWith(HEADER_PREFIX)) {
			throw new AuthorizationServiceException("HEADER_PREFIX is missing");
		}
		
		if(header.length() < HEADER_PREFIX.length()) {
			throw new AuthorizationServiceException("Invalid size of Authorization Header");
		}
		
		return header.substring(HEADER_PREFIX.length(), header.length());
	}
}
