package com.mkyong.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/welcome")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model){
		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "hello";
	}
	
	@RequestMapping(value="/hello2", method = RequestMethod.GET)
	public String printWelcome2(ModelMap model){
		model.addAttribute("message", "Spring 3 MVC Hello World - 2");
		return "hello";
	}
	
	@RequestMapping(value="/secure/hello2", method = RequestMethod.GET)
	public String secureHelloWorld(ModelMap model){
		model.addAttribute("message", "Spring 3 MVC Secure Hello World");
		return "hello";
	}
}
