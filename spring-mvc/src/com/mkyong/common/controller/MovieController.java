package com.mkyong.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/movie")
public class MovieController {

	@RequestMapping(value ="{name}", method=RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap map){
		map.addAttribute("movie",name);
		return "list";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String defaultMovie(ModelMap map){
		map.addAttribute("movie","this is default movie");
		return "list";
	}
}
