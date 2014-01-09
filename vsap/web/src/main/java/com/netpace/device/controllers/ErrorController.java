package com.netpace.device.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Noorain
 */
@Controller
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/error.do")
    public ModelAndView error() {
        return new ModelAndView("/error/error.jsp");
    }
    
    @RequestMapping("/error400.do")
    public ModelAndView error400() {
        return new ModelAndView("/error/error400.jsp");
    }
    
    @RequestMapping("/error403.do")
    public ModelAndView error403() {
        return new ModelAndView("/error/error403.jsp");
    }
    
    @RequestMapping("/error404.do")
    public ModelAndView error404() {
        return new ModelAndView("/error/error404.jsp");
    }
    
    @RequestMapping("/error405.do")
    public ModelAndView error405() {
        return new ModelAndView("/error/error405.jsp");
    }
    
    @RequestMapping("/error500.do")
    public ModelAndView error500() {
        return new ModelAndView("/error/error500.jsp");
    }
}
