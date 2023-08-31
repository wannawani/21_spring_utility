package com.spring.utility.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tiles/member")
public class MemberController {
	
	@GetMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("/detail")
	public String memberDetail() {
		return "member/detail";
	}
	
	@GetMapping("/cart")
	public ModelAndView cart() {
		return new ModelAndView("member/cart");
	}
	
	@GetMapping("/myPage")
	public ModelAndView myPage() {
		return new ModelAndView("member/myPage");
	}
	
}
