package com.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.WebUtils;

import com.security.WebSecurityConfig;
import com.security.exception.JwtTokenMissingException;
import com.security.model.JwtAuthenticationToken;
import com.security.model.RawAccessJwtToken;
import com.security.util.SecurityConstants;

/**
 * Filter for extracting header from request 
 * and passes it to JwtAuthenticationProvider authenticate()
 * @author Mayar Alam
 */

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

	/**
	 * This method will be called when SkipPathRequestMatcher matches() returns true
	 * means the request should be intercepted.

	 * This method will extract raw access jwt token from header and pass it to the
	 * ProviderManager to verify it.
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter attemptAuthentication");
		
		
		//This will be used when we are sending payload from postman for test but when we are sending 
		//accessToken and refreshToken in cookie
		
		//String tokenPayload= request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
		
		final Cookie accessTokenCookie= WebUtils.getCookie(request, SecurityConstants.AUTHORIZATION.getValue()); 
		
		if(accessTokenCookie == null)throw new JwtTokenMissingException("Jwt token is missing");
		
		final String tokenPayload= accessTokenCookie.getValue();
		
		final RawAccessJwtToken rawToken= new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
		
		/* based on the object of Class provided in the authenticate() the ProviderManager will be decided
		 * in our case for authentication we are providing UsernamePasswordAuthenticationToken then spring
		 * will execute supports() of all ProviderManager we have provided to it, and whose supports() will return true
		 * it will execute authenticate() for the same ProviderManager.
		 * In our case we are providing 2 ProviderManager
		 * 1. CustomAuthenticationProvider
		 * 2. JwtAuthenticationProvider
		 * Note: Both of the ProviderManager are mapped through WebSecurityConfig configure(AuthenticationManagerBuilder)
		 */
		
		/**
		 * regarding of JwtAuthenticationToken class we are using it because 
		 * inside autheticate() we need an authentication object and we are
		 * making our own Authentication object using JwtAuthenticationToken.
		 */
		return getAuthenticationManager().authenticate(new JwtAuthenticationToken(rawToken));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter successfulAuthentication");
		
		/**
		 * The  SecurityContext  and  SecurityContextHolder are two fundamental classes of Spring Security. 
		 * The SecurityContext  is used to store the details of the currently authenticated user, also known as a principle. 
		 * So, if you have to get the username or any other user details, you need to get the SecurityContext first.
		 * This is used very frequently if we want to get the current user in the method which is getting called after authorization.
		 */
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
		
		chain.doFilter(request, response);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Inside JwtTokenAuthenticationProcessingFilter unsuccessfulAuthentication");
		SecurityContextHolder.clearContext();
		failureHandler.onAuthenticationFailure(request, response, failed);
	}
}
