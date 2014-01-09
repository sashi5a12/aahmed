package com.netpace.device.filters;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.services.ApplicationPropertiesService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.VAPUserDetails;

public class ProductAccessHandler implements HandlerInterceptor {

    private static final Log log = LogFactory.getLog(ProductAccessHandler.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView mv) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("ProductAccessHandler.preHandler Start----------------");
        log.debug("Handler called for URI : " + request.getRequestURI());
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        if (loggedInUser.isPartner()) {
            String verizonAdminEmail = applicationPropertiesService.defaultVerizonAdminEmailAddress();
            Integer companyId = loggedInUser.getCompanyId();

            if (companyId == null) {
                Integer pendingUserCompanyId =
                        companyService.findCompanyIdOfPendingUser(loggedInUser.getId());

                if (pendingUserCompanyId != null) {

                    log.debug("Redirecting user to denied page as it's join request is in pending status.");
                    response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?VERIZON_ADMIN_EMAIL_ADDRESS="
                            + verizonAdminEmail
                            + "&COMPANY_STATUS=msg.company.pending.status");
                    return false;
                } else {
                    //Send the user to the registeration page from here
                    log.debug("There is no company associated with this user. "
                            + "Redirecting user to the company registration page");                    
                    response.sendRedirect(request.getContextPath() + "/company/registercompany.do");
                    return false;
                }
            }

            CompanyVO companyVO = companyService.getCompanyById(companyId);

            if ("NOT_SET".equals(loggedInUser.getCompanyStatus())) {

                // Check for if company is denied
                if (VAPUtils.isCompanyDenied(companyVO)) {
                    response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?VERIZON_ADMIN_EMAIL_ADDRESS="
                            + verizonAdminEmail
                            + "&COMPANY_STATUS=msg.product.access.company.on.hold");
                    return false;
                }

                // Check for if company is suspended
                if (companyVO.isSuspended()) {
                    response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?VERIZON_ADMIN_EMAIL_ADDRESS="
                            + verizonAdminEmail
                            + "&COMPANY_STATUS=msg.product.access.company.is.suspended");
                    return false;
                }

                // Check for if company is approved
                if (companyService.isCompanyApproved(companyId)) {
                    loggedInUser.setCompanyStatus("APPROVED");
                    return true;
                } else {
                    response.sendRedirect(request.getContextPath()
                            + "/secure/denied.do?COMPANY_STATUS=msg.product.access.company.pending.status");
                    return false;
                }
            }
        }
        return true;
    }
}
