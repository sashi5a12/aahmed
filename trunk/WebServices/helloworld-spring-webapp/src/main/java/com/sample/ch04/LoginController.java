package com.sample.ch04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = "/singin.do", method = RequestMethod.GET)
    public ModelAndView singIn() {
        ModelAndView mv = new ModelAndView("singin");

        return mv;
    }
}
