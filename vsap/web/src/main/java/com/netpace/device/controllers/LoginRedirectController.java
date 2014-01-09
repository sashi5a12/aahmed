package com.netpace.device.controllers;

import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.VAPUserDetails;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Noorain
 */
@Controller
public class LoginRedirectController {

    private static final Log log =
            LogFactory.getLog(LoginRedirectController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    @RequestMapping("/secure/login/redirect.do")
    public ModelAndView loginRedirect(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();

        log.debug("Entered login redirect.");

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        String allAuthorities = loggedInUser.commaSeparatedRolesList();

        log.debug("Created menu for the user [" + loggedInUser.getUsername()
                + "] user roles [" + allAuthorities + "]");

        if (loggedInUser.isPartner()) {
            if (loggedInUser.getCompanyId() == null) {

                Integer pendingUserCompanyId =
                        companyService.findCompanyIdOfPendingUser(loggedInUser.getId());

                if (pendingUserCompanyId != null) {

                    log.debug("Redirecting user to onhold page as it's company is in pending status.");
                    mav.setViewName("/secure/company/onhold.jsp");

                    mav.addObject("VERIZON_ADMIN_EMAIL_ADDRESS",
                            applicationPropertiesService.defaultVerizonAdminEmailAddress());

                    mav.addObject(
                            "COMPANY_ON_HOLD_OR_SUSPENDED", "msg.company.pending.status");
                } else {
                    log.debug("There is no company associated with this user. "
                            + "Redirecting user to the company registration page");
                    mav.setViewName("redirect:/company/registercompany.do");
                }
                return mav;
            }
            log.debug("Redirecting user to the products list page");
            mav.setViewName("redirect:/secure/worklist.do");
        } else {

            HttpSession session = request.getSession();
            String goTo = (String) session.getAttribute("GO_TO");
            if (StringUtils.isNotEmpty(goTo)) {
                log.debug("Redirecting user to goto page: " + goTo);
                mav.setViewName("redirect:" + goTo);

                session.removeAttribute("GO_TO");
            } else {
                log.debug("Redirecting user to the worklist page");
                mav.setViewName("redirect:/secure/worklist.do");
            }
        }
        return mav;
    }
}
