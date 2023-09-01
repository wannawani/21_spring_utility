package com.spring.utility.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bootstrap")
public class BootStrapController {
	
	@GetMapping("/index")
	public String index() {
		return "/bootstrap/index";
	}
	
	@GetMapping("/blog")
	public String blog() {
		return "/bootstrap/blog";
	}
	
	@GetMapping("/checkout")
	public String checkout() {
		return "/bootstrap/checkout";
	}
	
	@GetMapping("/shop")
	public String shop() {
		return "/bootstrap/shop";
	}
}
