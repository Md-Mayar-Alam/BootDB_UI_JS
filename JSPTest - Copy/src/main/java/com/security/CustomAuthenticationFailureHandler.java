package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.domain.ErrorCode;
import com.security.domain.ErrorResponse;
import com.security.exception.AuthMethodNotSupportedException;
import com.security.exception.JwtExpiredTokenException;

/**
 * This handler is called on unsuccessful authentication of user.
 * This handler is mapped with CustomLoginAuthenticationProcessingFilter in WebSecurityConfig
 * buildCustomLoginAuthenticationProcessingFilter() in which we are passing this hadler as 
 * constructor parameter of CustomLoginAuthenticationProcessingFilter
 * 
 * @author Md Mayar Alam
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	private final ObjectMapper objectMapper;
	
	@Autowired
	public CustomAuthenticationFailureHandler(final ObjectMapper objectMapper) {
		System.out.println("Inside CustomAuthenticationFailureHandler constructor");
		this.objectMapper= objectMapper;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println("Inside CustomAuthenticationFailureHandler onAuthenticationFailure");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		if(exception instanceof BadCredentialsException) {
			objectMapper.writeValue(response.getWriter(), ErrorResponse.of("Invalid username or password", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		}else if(exception instanceof JwtExpiredTokenException) {
			objectMapper.writeValue(response.getWriter(), ErrorResponse.of("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
		}else if(exception instanceof AuthMethodNotSupportedException) {
			objectMapper.writeValue(response.getWriter(), ErrorResponse.of(exception.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
		}
		
		objectMapper.writeValue(response.getWriter(), ErrorResponse.of(exception.getMessage() , ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
	}

}
