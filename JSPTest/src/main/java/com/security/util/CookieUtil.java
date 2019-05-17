package com.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void create(HttpServletResponse response, String cookieName, String cookievalue,
			Boolean isSecured, Integer maxAge, String domain) {
		Cookie cookie= new Cookie(cookieName, cookievalue);
		//cookie.setSecure(isSecured);	//used for https
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		cookie.setPath("/api");
		response.addCookie(cookie);
	}
	
	
}
