package com.netpace.device.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.VAPUserDetails;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    private static final Log log = LogFactory.getLog(AuthenticationController.class);

    @RequestMapping("/signin.do")
    public ModelAndView signinPage(HttpServletRequest request, @RequestParam(value = "goto", required = false) String goTo) {
        ModelAndView mav = new ModelAndView("signin.jsp");

        try {
            log.debug("goTo: " + goTo);

            if (StringUtils.isNotEmpty(goTo)) {
                request.getSession().setAttribute("GO_TO", URLDecoder.decode(goTo, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e, e);
        }

        try {
            VAPUserDetails loggedInUser =
                    VAPSecurityManager.getAuthenticationToken();
            if (loggedInUser != null) {
                mav.setViewName("redirect:/secure/login/redirect.do");
            }
        } catch (ClassCastException cce) {
            log.debug("Signin display page");
        }
        return mav;
    }

    @RequestMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request) {
        log.debug("Logout user");
        ModelAndView mav = new ModelAndView("redirect:/");
        request.getSession().invalidate();
        return mav;
    }
}
