package com.netpace.device.controllers;

import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.RegistrationService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DealExpriredActivationController {

    private static final Log log =
            LogFactory.getLog(DealExpriredActivationController.class);
    @Resource(name = "registrationService")
    private RegistrationService registrationService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    @RequestMapping("/registration/expired/activations/delete.do")
    public ModelAndView deleteExpiredActivations(HttpServletRequest request) {
        
        log.debug("***** Deleting expired activations start. *****");
        
        ModelAndView mav = new ModelAndView(
                "devRegistration/deleteexpiredactivations.jsp");

        registrationService.deleteExpiredActivations(
                applicationPropertiesService.defaultActivationExpiryDays());

        log.debug("***** Deleting expired activations end. *****");
        
        return mav;
    }
}
