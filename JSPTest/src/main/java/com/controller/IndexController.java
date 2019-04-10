package com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Product;
import com.services.ProductService;

@Controller
public class IndexController {

	@Autowired
	ProductService productService;
	
	@ResponseBody
	@RequestMapping("/hello")
	public String getMessage() {
		return "Hello World";
	}
	
	@RequestMapping("/welcome")
	public String getIndexPage() {
		Optional<Product> productOp = productService.findProductById(1001);
		Product product = productOp.get();
		System.out.println("Product Id "+product.getProductId());
		
		return "welcomepage";
	}
}
