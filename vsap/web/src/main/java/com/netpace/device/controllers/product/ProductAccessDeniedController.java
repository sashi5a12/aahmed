package com.netpace.device.controllers.product;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductAccessDeniedController {

    private static final Log log = LogFactory.getLog(ProductAccessDeniedController.class);

    @RequestMapping(value = "/secure/denied.do", method = RequestMethod.GET)
    public ModelAndView accessDenied(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("/secure/product/accessDenied.jsp");

        String msgKey = request.getParameter("COMPANY_STATUS");
        String verizonAdminEmail = request.getParameter("VERIZON_ADMIN_EMAIL_ADDRESS");

        mv.addObject("COMPANY_STATUS", msgKey);
        mv.addObject("VERIZON_ADMIN_EMAIL_ADDRESS", verizonAdminEmail);

        return mv;
    }
}
