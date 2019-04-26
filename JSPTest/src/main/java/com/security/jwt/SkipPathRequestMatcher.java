package com.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

public class SkipPathRequestMatcher implements RequestMatcher{

	private final OrRequestMatcher matchers;
	private final RequestMatcher processingMatcher;
	
	public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
		System.out.println("Inside SkipPathRequestMatcher constructor");
		Assert.notNull(pathsToSkip);
		
		List<RequestMatcher> matchersList= pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
		
		matchers= new OrRequestMatcher(matchersList);
		
		processingMatcher= new AntPathRequestMatcher(processingPath);
	}
	
	public boolean matches(HttpServletRequest request) {
		System.out.println("Inside SkipPathRequestMatcher matches");
		if(matchers.matches(request)) {
			return false;
		}
		return processingMatcher.matches(request) ? true : false;
	}
}
