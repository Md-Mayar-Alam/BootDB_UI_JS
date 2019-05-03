package com.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.domain.LoginRequest;

public class CustomLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter{
	
	private static Logger logger= LoggerFactory.getLogger(CustomLoginAuthenticationProcessingFilter.class);
	
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	private final ObjectMapper objectMapper;
	
	public CustomLoginAuthenticationProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler authenticationSuccessHandler,
			AuthenticationFailureHandler authenticationFailureHandler, ObjectMapper objectMapper) {
		super(defaultProcessUrl);
		this.authenticationSuccessHandler= authenticationSuccessHandler;
		this.authenticationFailureHandler= authenticationFailureHandler;
		this.objectMapper= objectMapper;
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter constructor");
		logger.debug("Inside CustomLoginAuthenticationProcessingFilter constructor");
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter attemptAuthentication");
		
		/*if(!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
			if(logger.isDebugEnabled()) {
				logger.debug("Authentication method not supported. Request method "+request.getMethod());
			}
			throw new AuthMethodNotSupportedException("Authentication method not supported");
		}*/
		
		LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
		
		if(StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
			throw new AuthenticationServiceException("Username or password not provided");
		}
		
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		
		return this.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter successfulAuthentication");
		authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
		chain.doFilter(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter unsuccessfulAuthentication");
		authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}
}
