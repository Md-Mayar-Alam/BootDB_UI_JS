package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Product;
import com.model.User;
import com.service.ProductService;
import com.service.DatabaseUserService;

@Controller
public class IndexController {

	@Autowired
	ProductService productService;
	
	@Autowired
	DatabaseUserService userService;
	
	@ResponseBody
	@RequestMapping("/hello")
	public String getMessage() {
		return "Hello World";
	}
	
	@RequestMapping("/welcome")
	public String getIndexPage() {
		/*Optional<Product> productOp = productService.findProductById(1001);
		Product product = productOp.get();
		System.out.println("Product Id "+product.getProductId());*/
		
		Optional<User> userOptional = userService.findByUserId(1000L);
		User user = userOptional.get();
		System.out.println("UserId "+user.getUserId());
		System.out.println("Username "+user.getRegisterType());
		return "welcomepage";
	}
}
