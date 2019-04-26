package com.security;

import java.util.Arrays;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class CustomCorsFilter extends CorsFilter{

	public CustomCorsFilter() {
		super(configurationSource());
		System.out.println("Inside CustomCorsFilter constructor");
	}

	private static UrlBasedCorsConfigurationSource configurationSource() {
		System.out.println("Inside CustomCorsFilter configurationSource");
		CorsConfiguration corsConfig= new CorsConfiguration();
		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedOrigin("*");
		corsConfig.addAllowedHeader("*");
		corsConfig.setMaxAge(36000L);
		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "PUT", "DELETE", "HEAD", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlCorsSource= new UrlBasedCorsConfigurationSource();
		urlCorsSource.registerCorsConfiguration("/api11/**", corsConfig);
		
		return urlCorsSource;
	}
}
