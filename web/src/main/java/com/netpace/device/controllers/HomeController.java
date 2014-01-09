package com.netpace.device.controllers;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.ServletContextUtil;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.activiti.engine.RuntimeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private static final Log log = LogFactory.getLog(HomeController.class);
    @Autowired
    CompanyService companyService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    ApplicationPropertiesService applicationProperties;
    
    @Autowired
    ServletContext servletContext;

    @RequestMapping("/welcome.do")
    public ModelAndView welcome() {
        String portalHome = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vsap.portal.home");
        ModelAndView mav = new ModelAndView("redirect:" + portalHome);

        return mav;
    }
    
    @RequestMapping("/index.do")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/index.jsp");

        return mav;
    }
    @RequestMapping("/secure/properties/reload.do")
    public ModelAndView refreshApplicationPropertiesCache(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/secure/applicationcache.jsp");

        log.debug("Reloading application properties cache.");
        applicationProperties.refreshPropertiesCache();
        ServletContextUtil.refreshContextAttributes(servletContext, applicationProperties);

        mav.addObject("APPLICATION_CACHE_REFRESHED",
                "msg.application.cache.refresh");

        return mav;
    }

    @RequestMapping("/secure/message.do")
    public ModelAndView showMessage(
            HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/secure/common/messages.jsp");

        String messageKey = request.getParameter("MESSAGE_KEY");

        if (messageKey != null) {
            mav.addObject("MESSAGE_KEY", messageKey);
            if (messageKey.equals("msg.request.to.join.company.success")) {
                String companyName = request.getParameter("COMPANY_NAME");
                mav.addObject("COMPANY_NAME", companyName);
            }
        }
        return mav;
    }

    @RequestMapping("/secure/help.do")
    public ModelAndView help() {
        ModelAndView mav = new ModelAndView("/secure/help.jsp");

        return mav;
    }
    
}
