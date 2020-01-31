package com.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * This class will work as the matcher which will be responsible to match the urls that
 * whether a url is permitted or secure.
 * This class is mapped with JwtTokenAuthenticationProcessingFilter in WebSecurityConfig
 * buildJwtTokenAuthenticationProcessingFilter()
 * 
 * @author Md Mayar Alam
 */
public class SkipPathRequestMatcher implements RequestMatcher{

	private final OrRequestMatcher matchers;
	private final RequestMatcher processingMatcher;
	
	@SuppressWarnings("deprecation")
	public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
		System.out.println("Inside SkipPathRequestMatcher constructor");
		Assert.notNull(pathsToSkip);
		
		List<RequestMatcher> matchersList= pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
		
		matchers= new OrRequestMatcher(matchersList);
		
		processingMatcher= new AntPathRequestMatcher(processingPath);
	}
	
	public boolean matches(HttpServletRequest request) {
		System.out.println("Inside SkipPathRequestMatcher matches");
		
		/*
		 * checking that whether the request is to be permitted or not if permitted return false
		 * because matchers contains List of path which is to be permitted
		 */
		if(matchers.matches(request)) {
			return false;
		}
		
		/*
		 * and processingMatcher contains the processingPath i.e the request
		 * which is to be intercepted
		 */
		return processingMatcher.matches(request) ? true : false;
	}
}
