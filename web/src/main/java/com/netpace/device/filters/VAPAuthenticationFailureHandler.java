/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.filters;
       

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 *
 * @author Wakram
 */
public class VAPAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    private final static Log log = LogFactory.getLog(VAPAuthenticationFailureHandler.class);
    
    @Resource(name = "applicationPropertiesService")
    private ApplicationPropertiesService applicationProperties;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest reqeust, HttpServletResponse response, AuthenticationException ae) throws IOException, ServletException {
        
        String redirectURL = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "authentication.failure.redirect.url");
        
        log.info("Authentication failed.... Redirecting user to ["+reqeust.getContextPath()+redirectURL+"]");
                
        response.sendRedirect(reqeust.getContextPath()+redirectURL);
    }
            
    
}
