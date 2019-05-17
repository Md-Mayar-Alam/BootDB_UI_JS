package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(method=RequestMethod.GET, value="/")
	public String getIndexPage() {
		System.out.println("Inside IndexController getIndexPage");
		return "welcomepage";
	}
}
