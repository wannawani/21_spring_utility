package com.spring.utility.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tiles/member")
public class MemberController_answer_sample {
	
	@GetMapping("/login_answer_sample")
	public String login() {
		return "member/login_answer_sample";
	}
	
	@GetMapping("/detail_answer_sample")
	public String memberDetail() {
		return "member/detail_answer_sample";
	}
	
	@GetMapping("/cart_answer_sample")
	public ModelAndView cart() {
		return new ModelAndView("member/cart_answer_sample");
	}
	
	@GetMapping("/myPage_answer_sample")
	public ModelAndView myPage() {
		return new ModelAndView("member/myPage_answer_sample");
	}
	
}
