package com.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.security.WebSecurityConfig;
import com.security.model.JwtAuthenticationToken;
import com.security.model.RawAccessJwtToken;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter{

	private final AuthenticationFailureHandler failureHandler;
	private final TokenExtractor tokenExtractor;
	
	public JwtTokenAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler,
			JwtHeaderTokenExtractor tokenExtractor, SkipPathRequestMatcher matcher) {
		super(matcher);
		this.failureHandler= failureHandler;
		this.tokenExtractor= tokenExtractor;
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter constructor");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter attemptAuthentication");
		String tokenPayload= request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
		RawAccessJwtToken rawToken= new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
		
		return getAuthenticationManager().authenticate(new JwtAuthenticationToken(rawToken));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter successfulAuthentication");
		chain.doFilter(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter unsuccessfulAuthentication");
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}
