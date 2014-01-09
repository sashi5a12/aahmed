/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.controllers.company;

import com.netpace.device.annotation.utils.RoleUtil;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.CompanyValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.CompanyProfileWithActiveUser;
import com.netpace.device.vo.EditCompanyValidator;
import com.netpace.device.vo.VAPUserDetails;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Wakram To understand this class refer to the Device Accessories
 * /Documents/Design/ Company Edit_View_Processing/Company edit-view flow.jpg
 * flow diagram
 */
@Controller
public class ViewEditCompany {

    private static final Log log = LogFactory.getLog(ViewEditCompany.class);
    @Autowired
    ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    private CompanyService companyService;
    @Resource
    private EditCompanyValidator editCompanyValidator;

    /**
     * This URL is listed in the sub menu of ROLE_PARTNER_USER viewCompany takes
     * the company id of the user and delegates it to the /secure/company.do
     * controller
     *
     * If the company id is null then it means that the user is maliciously
     * trying a URL thru browser and hence shall be shown an Access Denied page
     */
    @RequestMapping("/secure/mycompany.do")
    public ModelAndView viewCompany(HttpServletRequest request, boolean retainModel) {
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        ModelAndView mav = new ModelAndView();

        Integer companyId = loggedInUser.getCompanyId();

        mav.setViewName("redirect:/secure/company.do?companyid=" + companyId);
        return mav;
    }

    /**
     * User comes to this URL thru Manage Partners screen or is delegated from
     * the myCompany.do URL viewEditcompany checks if the logged in user is
     * allowed to edit the company and is redirected to the edit or view page
     * accordingly
     */
    @RequestMapping(value = "/secure/company.do", method = RequestMethod.GET)
    public ModelAndView viewEditcompany(HttpServletRequest request,
            @RequestParam(value = "companyid") int companyId) {

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.isPartner()) {

            if (loggedInUser.getCompanyId() != companyId) {
                log.error("User [" + loggedInUser.getUsername() + "] is trying to maliciously access some other company's info.");
                return new ModelAndView("error/error403.jsp");
            }
        }

        try {
            log.debug("Loading company [" + companyId + "] data");
            CompanyProfileWithActiveUser companyProfile = companyService.getCompanyProfileWithUser(companyId);

            if (RoleUtil.canEditCompany(loggedInUser, applicationPropertiesService)) {
                log.debug("Redirecting to editcompany.do");
                ModelAndView mav = new ModelAndView("/secure/company/vieweditcompany.jsp");
                mav.addObject("companyProfile", companyProfile);
                if (StringUtils.isNotEmpty(request.getParameter("COMPANY_UPDATED"))) {
                    mav.addObject("COMPANY_UPDATED", "msg.company.update");
                }
                if (StringUtils.isNotEmpty(request.getParameter("COMPANY_SUSPENDED"))) {
                    mav.addObject("COMPANY_SUSPENDED", "msg.company.suspend");
                }
                if (StringUtils.isNotEmpty(request.getParameter("COMPANY_UNSUSPENDED"))) {
                    mav.addObject("COMPANY_UNSUSPENDED", "msg.company.unsuspend");
                }
                mav.addObject(
                        "isCompanyApproved",
                        companyService.isCompanyApproved(companyId));
                return mav;
            } else {
                log.debug("Redirecting to viewcompany.do");
                ModelAndView mav = new ModelAndView("/secure/company/viewcompany.jsp");
                mav.addObject("companyProfile", companyProfile);
                if (StringUtils.isNotEmpty(request.getParameter("COMPANY_SUSPENDED"))) {
                    mav.addObject("COMPANY_SUSPENDED", "msg.company.suspend");
                }
                if (StringUtils.isNotEmpty(request.getParameter("COMPANY_UNSUSPENDED"))) {
                    mav.addObject("COMPANY_UNSUSPENDED", "msg.company.unsuspend");
                }
                mav.addObject(
                        "isCompanyApproved",
                        companyService.isCompanyApproved(companyId));
                return mav;
            }
        } catch (RecordNotFoundException rnf) {
            log.error(rnf.getMessage());
        }
        return new ModelAndView("error/error403.jsp");
    }

    /**
     * User comes to this URL thru Manage Partners screen or is delegated from
     * the myCompany.do URL viewEditcompany checks if the logged in user is
     * allowed to edit the company and is redirected to the edit or view page
     * accordingly
     */
    @RequestMapping(value = "/secure/deletecompany.do", method = RequestMethod.GET)
    public ModelAndView deleteCompany(HttpServletRequest request,
            @RequestParam(value = "companyid") int companyId) {

        ModelAndView mav = new ModelAndView("redirect:/secure/managePartners.do");

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (RoleUtil.canDeleteCompany(
                loggedInUser, applicationPropertiesService)) {

            try {
                companyService.deleteCompany(loggedInUser, companyId);
                mav.addObject("COMPANY_DELETED", "msg.company.delete");
            } catch (BusinessRuleException bre) {
                log.debug(bre.getMessage());
                mav.setViewName("error/error403.jsp");
            }
        } else {
            log.debug("Redirecting to access denied page");
            mav.setViewName("error/error403.jsp");
        }
        return mav;
    }

    @RequestMapping(value = "/secure/updatecompany.do", method = RequestMethod.POST)
    public ModelAndView updateCompany(HttpServletRequest request,
            @ModelAttribute(value = "companyProfile") CompanyProfileWithActiveUser companyProfile,
            BindingResult bindingResult) {

        /**
         * This update function has a security issues The companyId is taken on
         * form and is passed thru port params to this function What if user
         * changes the company id in the form and then hits the submit button In
         * this case he will be changing company of somebody else We shall not
         * pass the database pks in the form as hidden variables
         */
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        ModelAndView mav = new ModelAndView(
                "redirect:/secure/company.do?companyid="
                + companyProfile.getCompany().getId());

        if (!RoleUtil.canEditCompany(loggedInUser, applicationPropertiesService)) {
            log.debug("Redirecting to viewcompany.do");
            mav.addObject("companyProfile", companyProfile);
            mav.setViewName("/secure/company/viewcompany.jsp");
            return mav;
        }

        editCompanyValidator.validate(companyProfile, bindingResult);

        if (bindingResult.hasErrors()) {
            return viewEditcompany(request, companyProfile.getCompany().getId());
        }

        try {
            companyService.updateCompanyInfo(loggedInUser, companyProfile);
        } catch (BusinessRuleException bre) {
            if (bre.getBusinessRule() == CompanyValidationBusinessRule.COMPANY_NAME_IS_DUPLICATE) {
                bindingResult.reject(
                        "msg.partner.company.name.duplicate",
                        new String[]{"Company Name"},
                        "msg.partner.company.name.duplicate");
                return viewEditcompany(request, companyProfile.getCompany().getId());
            }
        }

        mav.addObject("companyProfile", companyProfile);
        mav.addObject("COMPANY_UPDATED", "msg.company.update");

        return mav;
    }

    @RequestMapping("/secure/suspendcompany.do")
    public ModelAndView suspendCompany(HttpServletRequest request,
            @RequestParam(value = "companyid") int companyId) {
        ModelAndView mav = new ModelAndView("redirect:/secure/company.do");
        mav.addObject("companyid", companyId);

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (RoleUtil.canSuspendOrUnsuspendCompany(
                loggedInUser, applicationPropertiesService)) {

            try {
                companyService.suspendOrUnsuspendCompany(
                        loggedInUser, companyId, VAPConstants.SUSPEND_COMPANY);
                mav.addObject("COMPANY_SUSPENDED", "msg.company.suspend");
            } catch (BusinessRuleException bre) {
                log.debug(bre.getMessage());
                mav.setViewName("error/error403.jsp");
            }
        } else {
            log.debug("Redirecting to access denied page");
            mav.setViewName("error/error403.jsp");
        }
        return mav;
    }

    @RequestMapping("/secure/unsuspendcompany.do")
    public ModelAndView unsuspendCompany(HttpServletRequest request,
            @RequestParam(value = "companyid") int companyId) {
        ModelAndView mav = new ModelAndView("redirect:/secure/company.do");
        mav.addObject("companyid", companyId);

        VAPUserDetails loggedInUser =
                VAPSecurityManager.getAuthenticationToken();

        if (RoleUtil.canSuspendOrUnsuspendCompany(
                loggedInUser, applicationPropertiesService)) {

            try {
                companyService.suspendOrUnsuspendCompany(
                        loggedInUser, companyId, VAPConstants.UNSUSPEND_COMPANY);
                mav.addObject("COMPANY_UNSUSPENDED", "msg.company.unsuspend");
            } catch (BusinessRuleException bre) {
                log.debug(bre.getMessage());
                mav.setViewName("error/error403.jsp");
            }
        } else {
            log.debug("Redirecting to access denied page");
            mav.setViewName("error/error403.jsp");
        }
        return mav;
    }

    @ModelAttribute("populatedFormElements")
    public Map<String, Map<String, String>> populateFormListElements(
            HttpServletRequest request) {

        Map<String, Map<String, String>> map =
                new HashMap<String, Map<String, String>>();

        String uri = request.getRequestURI();

        if (uri.indexOf("company.do") != -1
                || uri.indexOf("updatecompany.do") != -1) {

            SortedMap<String, String> countryList =
                    applicationPropertiesService.propertiesByType(
                    ApplicationPropertyType.COUNTRIES);

            map.put("countryList", countryList);
        }
        return map;
    }
}
