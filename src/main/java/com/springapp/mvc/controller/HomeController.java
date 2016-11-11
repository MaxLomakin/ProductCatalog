package com.springapp.mvc.controller;

import org.apache.log4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		logger.debug(this.getClass().getName() + " index()");

		model.addAttribute("message", "Hi, bro");
		return "home";
	}
}