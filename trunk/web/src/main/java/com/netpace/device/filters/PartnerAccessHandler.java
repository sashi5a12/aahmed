/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.filters;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.VAPUserDetails;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wakram
 */
public class PartnerAccessHandler implements HandlerInterceptor {
    private static final Log log = LogFactory.getLog(PartnerAccessHandler.class);
    
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    
    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {        
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {        
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandler Start----------------");
        log.debug("Handler called for URI : " + request.getRequestURI());
        //company/manageusers.do
                
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        Integer companyId = loggedInUser.getCompanyId();
        String userName = loggedInUser.getUsername();
        
        log.debug("companyId ["+companyId+" userName "+userName+"]");
        
        if (loggedInUser.isPartner()) {
            String verizonAdminEmail = applicationPropertiesService.defaultVerizonAdminEmailAddress();
            
            if (companyId == null) {
                //Checking if this user has a pending request to join a company
                Integer pendingUserCompanyId = companyService.findCompanyIdOfPendingUser(loggedInUser.getId());
                
                if (pendingUserCompanyId != null) {
                    log.debug("Redirecting user to denied page with message that his request to join a company is pending.");
                    response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?VERIZON_ADMIN_EMAIL_ADDRESS="
                            + verizonAdminEmail
                            + "&COMPANY_STATUS=msg.company.pending.status");
                    return false;
                } 
                
                //This user has no pending join request and the company id is null
                //Therefore redirecting user to the registration page                
                log.debug("There is no company associated with this user. "
                            + "Redirecting user to the company registration page");                    
                
                response.sendRedirect(request.getContextPath() + "/company/registercompany.do");
                return false;
            }
            
            CompanyVO companyVO = companyService.getCompanyById(companyId);            
            if (companyVO != null && VAPUtils.isCompanyDenied(companyVO)) {                
                log.debug("Company is denied. Redirecting user to the denied.do");                
                response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?VERIZON_ADMIN_EMAIL_ADDRESS="
                            + verizonAdminEmail
                            + "&COMPANY_STATUS=msg.company.on.hold");
                
                return false;
            }

            if (companyVO != null && companyVO.isSuspended()) {
                log.debug("Company is suspended. Redirecting user to the denied.do");
                response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?VERIZON_ADMIN_EMAIL_ADDRESS="
                            + verizonAdminEmail
                            + "&COMPANY_STATUS=msg.company.is.suspended");
                
                return false;
            }
        }
        
        return true;
    }
}
