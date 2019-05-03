package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecuredHelloController {
	
	@ResponseBody
	@RequestMapping(value="/api/securedHello", method=RequestMethod.GET)
	public String securedHello() {
		return "Hello!!! from secured hello";
	}
}
