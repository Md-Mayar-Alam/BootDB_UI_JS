package com.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.config.JwtConfig;
import com.security.jwt.JwtTokenFactory;
import com.security.model.JwtToken;
import com.security.model.UserContext;
import com.security.util.CookieUtil;
import com.security.util.SecurityConstants;

/**
 * @author Md Mayar Alam
 * This handler is called on the successful authentication of user.
 * This handler is mapped with CustomLoginAuthenticationProcessingFilter in WebSecurityConfig
 * buildCustomLoginAuthenticationProcessingFilter() in which we are passing this handler to
 * CustomLoginAuthenticationProcessingFilter
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Autowired
	JwtConfig jwtConfig;
	
	private final JwtTokenFactory jwtTokenFactory;
	private final ObjectMapper objectMapper;
	
	/*private static final String DOMAIN= "localhost";
	private static final String ACCESS_TOKEN_COOKIE= "ACCESS_TOKEN_COOKIE";
	private static final String REFRESH_TOKEN_COOKIE= "REFRESH_TOKEN_COOKIE";*/
	
	
	/**
	 * In this class we are going to generate the Access and Refresh Token
	 * for which we will need the object of JwtTokenFactory.
	 * So we are doing here constructor injection for JwtTokenFactory which is 
	 * already annotated with @Component
	 * 
	 * @param objectMapper
	 * @param jwtTokenFactory
	 * @see com.security.jwt.JwtTokenFactory
	 */
	@Autowired
	public CustomAuthenticationSuccessHandler(final ObjectMapper objectMapper, final JwtTokenFactory jwtTokenFactory) {
		System.out.println("Inside CustomAuthenticationSuccessHandler constructor");
		this.jwtTokenFactory= jwtTokenFactory;
		this.objectMapper= objectMapper;
	}
	
	/**
	 * This method is invoked when the user is successfully authenticated by
	 * username and password.
	 * And inside this method we are going to create the AccessToken and RefreshToken.
	 * And will also set it in the HttpOnly Cookie.
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("Inside CustomAuthenticationSuccessHandler onAuthenticationSuccess");
		
		/**
		 * We have set this UserContext as Principal in CustomAuthenticationProvider
		 * authenticate() method in the Principal
		 * return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
		 */
		UserContext userContext= (UserContext)authentication.getPrincipal();
		
		JwtToken accessToken= jwtTokenFactory.createAccessJwtToken(userContext);
		JwtToken refreshToken= jwtTokenFactory.createRefreshToken(userContext);
		
		final String accessTokenString="Bearer_"+accessToken.getToken();
		final String refreshTokenString= refreshToken.getToken();
		
		CookieUtil.create(response, SecurityConstants.AUTHORIZATION.getValue(), accessTokenString, true, jwtConfig.getCookieMaxAge(), SecurityConstants.DOMAIN.getValue());
		CookieUtil.create(response, SecurityConstants.REFRESH_TOKEN_COOKIE.getValue(), refreshTokenString, true, jwtConfig.getCookieMaxAge(), SecurityConstants.DOMAIN.getValue());
		
		
		//code for setting access and refresh token explicitly
		
		/*Map<String, String> tokenMap= new HashMap<>();
		
		tokenMap.put("accessToken", accessToken.getToken());
		tokenMap.put("refreshToken", refreshToken.getToken());
		
		
		
		
		//objectMapper.writeValue(response.getWriter(), tokenMap);
		objectMapper.writeValue(response.getOutputStream(), tokenMap);*/
		
		//Code for testing cookie
		
		/*final Cookie prevCookie= WebUtils.getCookie(request, "test");
		
		
		Cookie cookie= new Cookie("test", "test_cookie");
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		System.out.println("Cookie Added "+cookie.toString());*/
		
		
		response.setStatus(HttpStatus.OK.value());
		//response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		//response.setContentType(MediaType.TEXT_HTML_VALUE);
		clearAuthenticationAttributes(request);
	}

	/**
	 * method to remove temporary authentication related data which
	 * may have been stored in the session while authentication process
	 * @param request
	 */
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session= request.getSession(false);
		
		if(session == null) {
			return;
		}
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
