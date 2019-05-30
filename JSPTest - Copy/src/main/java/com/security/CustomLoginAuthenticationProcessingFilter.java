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

/**
 * @author Md Mayar Alam
 * 
 * This class will be called for authentication purposes of username and password when user login
 * The authentication is based on the defaultProcessUrl which is passed in the constructor
 * and passed to AbstractAuthenticationProcessingFilter constructor.
 * This class should be mapped through WebSecurityConfig configure(HttpSecurity) in which we 
 * add our filter before UsernamePasswordAuthenticationFilter and passes defaultProcessUrl to it.
 */
public class CustomLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter{
	
	private static Logger logger= LoggerFactory.getLogger(CustomLoginAuthenticationProcessingFilter.class);
	
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	
	private final ObjectMapper objectMapper;
	
	/**
	 * @param defaultProcessUrl
	 * @param authenticationSuccessHandler
	 * @param authenticationFailureHandler
	 * @param objectMapper
	 * 
	 * This constructor is called from WebSecurityConfig configure(HttpSecurity) in which we are adding this filter
	 * before UsernamePasswordAuthenticationFilter
	 */
	public CustomLoginAuthenticationProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler authenticationSuccessHandler,
			AuthenticationFailureHandler authenticationFailureHandler, ObjectMapper objectMapper) {
		super(defaultProcessUrl);
		this.authenticationSuccessHandler= authenticationSuccessHandler;
		this.authenticationFailureHandler= authenticationFailureHandler;
		this.objectMapper= objectMapper;
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter constructor");
		logger.debug("Inside CustomLoginAuthenticationProcessingFilter constructor");
	}
	
	/**
	 * This method will be called automatically by the spring framework as we have added the same filter 
	 * before UsernamePasswordAuthenticationFilter for defaultProcessUrl in WebSecurityConfig configure(HttpSecurity)
	 */
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
		
		/* based on the object of Class provided in the authenticate() the ProviderManager will be decided
		 * in our case for authentication we are providing UsernamePasswordAuthenticationToken then spring
		 * will execute supports() of all ProviderManager we have provided to it, and whose supports() will return true
		 * it will execute authenticate() for the same ProviderManager.
		 * In our case we are providing 2 ProviderManager
		 * 1. CustomAuthenticationProvider
		 * 2. JwtAuthenticationProvider
		 * Note: Both of the ProviderManager are mapped through WebSecurityConfig configure(AuthenticationManagerBuilder)
		 */
		return this.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter successfulAuthentication");
		authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);

		//super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Inside CustomLoginAuthenticationProcessingFilter unsuccessfulAuthentication");
		authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}
}
