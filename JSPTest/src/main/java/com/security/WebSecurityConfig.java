package com.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.JwtAuthenticationProvider;
import com.security.jwt.JwtHeaderTokenExtractor;
import com.security.jwt.JwtTokenAuthenticationProcessingFilter;
import com.security.jwt.SkipPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthenticationSuccessHandler successHandler;
	@Autowired
	AuthenticationFailureHandler failureHandler;
	@Autowired
	JwtHeaderTokenExtractor tokenExtractor;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	JwtAuthenticationProvider jwtAuthenticationProvider;
	
	public static final String AUTHENTICATION_HEADER_NAME= "Authorization";
	public static final String AUTHENTICATION_URL= "/api/auth/login";
	public static final String REFRESH_TOKEN_URL= "/api/auth/token";
	public static final String API_ROOT_URL= "/api/**";
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("Inside WebSecurityConfig configure(AuthenticationManagerBuilder auth)");
		auth.authenticationProvider(customAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
		System.out.println("JwtAuthenticationProvider "+jwtAuthenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("Inside WebSecurityConfig configure(HttpSecurity http)");
		List<String> permitAllEndpointList= Arrays.asList(
					AUTHENTICATION_URL,
					REFRESH_TOKEN_URL
				);
		
		http.
			csrf().disable()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
				.antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))	//to save allocation cost we are using toArray(new String[permitAllEndpointList.size()]))
				.permitAll()
			.and()
				.authorizeRequests()
				.antMatchers(API_ROOT_URL)
				.authenticated()
			.and()
				.formLogin()
				.permitAll()
			.and()
				.addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildCustomLoginAuthenticationProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
		
			/*http.formLogin()
				.loginPage("/login")
			.and()
				.logout()
					.logoutSuccessUrl("/welcome");
					
			.and()
				.formLogin()
				.permitAll()
					*/
	}
	
	protected CustomLoginAuthenticationProcessingFilter buildCustomLoginAuthenticationProcessingFilter(String authenticationUrl) {
		System.out.println("Inside WebSecurityConfig buildCustomLoginAuthenticationProcessingFilter");
		CustomLoginAuthenticationProcessingFilter loginFilter= new CustomLoginAuthenticationProcessingFilter(authenticationUrl, successHandler, failureHandler, objectMapper);
		loginFilter.setAuthenticationManager(authenticationManager);
		return loginFilter;
	}

	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) {
		System.out.println("Inside WebSecurityConfig buildJwtTokenAuthenticationProcessingFilter");
		SkipPathRequestMatcher matcher= new SkipPathRequestMatcher(pathsToSkip, pattern);
		JwtTokenAuthenticationProcessingFilter tokenFilter= new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
		tokenFilter.setAuthenticationManager(authenticationManager);
		return tokenFilter;
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		System.out.println("Inside WebSecurityConfig authenticationManagerBean");
		return super.authenticationManagerBean();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		System.out.println("Inside WebSecurityConfig bCryptPasswordEncoder");
		return new BCryptPasswordEncoder();
	}
}
