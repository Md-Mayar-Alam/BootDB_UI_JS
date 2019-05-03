package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.domain.LoginRequest;

@Controller
public class LoginPageController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginPage(Model model) {
		model.addAttribute("loginRequest", new LoginRequest());
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value="/api/auth/login", method=RequestMethod.POST)
	public String securedLogin() {
		System.out.println("Inside securedLogin");
		return "Hello from secured login";
	}
}
