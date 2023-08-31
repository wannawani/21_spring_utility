package com.spring.utility.tiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tiles/board")
public class BoardController_answer_sample {

	@GetMapping("/boardList_answer_sample")
	public String boardList() {
		return "board/boardList_answer_sample";	
	}
	
	@GetMapping("/boardWrite_answer_sample")
	public String boardWrite() {
		return "board/boardWrite_answer_sample";
	}
	
	@GetMapping("/boardUpdate_answer_sample")
	public ModelAndView boardUpdate() {
		return new ModelAndView("board/boardUpdate_answer_sample");
	}
	
	@GetMapping("/boardDelete_answer_sample")
	public ModelAndView boardDelete() {
		return new ModelAndView("board/boardDelete_answer_sample");
	}
	
}
