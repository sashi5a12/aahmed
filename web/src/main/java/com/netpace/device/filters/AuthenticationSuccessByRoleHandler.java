package com.netpace.device.filters;

import com.netpace.device.services.UtilitiesService;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.MenuInfo;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccessByRoleHandler
        implements AuthenticationSuccessHandler {

    private static final Log log =
            LogFactory.getLog(AuthenticationSuccessByRoleHandler.class);
    @Autowired
    private UtilitiesService utilitiesService;
    @Autowired
    private VAPUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) 
            throws
            IOException, ServletException {

        if (request.getSession().getAttribute(
                VAPConstants.MENU_INFORMATION) == null) {

            log.debug("Creating menu");
            
            List<MenuInfo> menu = utilitiesService.getMenuInformation(
                    VAPSecurityManager.getAuthenticationToken());

            request.getSession().setAttribute(
                    VAPConstants.MENU_INFORMATION, menu);
        }
        
        userDetailsService.updateUserLastLoginDate(VAPSecurityManager.getAuthenticationToken());
        
        log.debug("Redirecting to login redirect controller");
        response.sendRedirect(
                request.getContextPath() + "/secure/login/redirect.do");
    }
}
