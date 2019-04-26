package com.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	
	private static final String XML_HTTP_REQUEST= "XmlHttpRequest";
	private static final String X_REQUESTED_WITH= "X-Requested-With";
	
	private static final String CONTENT_TYPE= "Content-type";
	private static final String CONTENT_TYPE_JSON= "application/json";
	
	public static boolean isAjax(HttpServletRequest request) {
		System.out.println("Inside WebUtil isAjax");
		return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
	}
}
