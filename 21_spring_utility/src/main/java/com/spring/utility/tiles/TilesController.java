package com.spring.utility.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tiles")
public class TilesController {

	@GetMapping("/content1")
	public String content1() {
		//return "tiles/tilesEx/content1"; // (before > jsp로 포워딩) 
		return "/aaa";	//(after > 조립()tile로 포워딩)definition태그의 name 속성과 일치(매핑)
	}
	
	@GetMapping("/content2")
	public ModelAndView content2() {
		ModelAndView mv = new ModelAndView();
		//mv.setViewName("tiles/tilesEx/content2");
		mv.setViewName("/bbb");
		
		return mv;
	}
	
	@GetMapping("/content3")
	public ModelAndView content3() {
		//return new ModelAndView("tiles/tilesEx/content3");
		return new ModelAndView("/ccc");
	}
	
}
