package com.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.JwtTokenFactory;
import com.security.model.JwtToken;
import com.security.model.UserContext;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private final JwtTokenFactory jwtTokenFactory;
	private final ObjectMapper objectMapper;
	
	@Autowired
	public CustomAuthenticationSuccessHandler(final ObjectMapper objectMapper, final JwtTokenFactory jwtTokenFactory) {
		System.out.println("Inside CustomAuthenticationSuccessHandler constructor");
		this.jwtTokenFactory= jwtTokenFactory;
		this.objectMapper= objectMapper;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("Inside CustomAuthenticationSuccessHandler onAuthenticationSuccess");
		UserContext userContext= (UserContext)authentication.getPrincipal();
		
		JwtToken accessToken= jwtTokenFactory.createAccessJwtToken(userContext);
		JwtToken refreshToken= jwtTokenFactory.createRefreshToken(userContext);
		
		Map<String, String> tokenMap= new HashMap<>();
		
		tokenMap.put("accessToken", accessToken.getToken());
		tokenMap.put("refreshToken", refreshToken.getToken());
		
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		/*objectMapper.writeValue(response.getWriter(), tokenMap);*/
		objectMapper.writeValue(response.getOutputStream(), tokenMap);
		
		clearAuthenticationAttributes(request);
	}

	/*
	 * method to remove temporary authentication related data which
	 * may have been stored in the session while authentication process
	 */
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session= request.getSession(false);
		
		if(session == null) {
			return;
		}
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	
	
}
