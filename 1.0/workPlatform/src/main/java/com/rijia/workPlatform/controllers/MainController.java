package com.rijia.workPlatform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/test")
	@ResponseBody
	public ModelAndView index() {
		return new ModelAndView("test");
	}

}
