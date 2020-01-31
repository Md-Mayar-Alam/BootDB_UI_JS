package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.security.domain.LoginRequest;

@Controller
public class LoginPopupController {
	
	@GetMapping("/loginPage")
	public String loginPopupLink() {
		return "loginPopupLink";
	}

	@GetMapping("/loginPop")
	public String returnLoginPopup(Model model) {
		model.addAttribute("loginRequest", new LoginRequest());
		
		return "loginPopup";
	}
}
