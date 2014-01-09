package com.netpace.device.controllers.company;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.CompanyValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyRegistrationService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.services.UserService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.CompanyRegistration;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.ContactVO;
import com.netpace.device.vo.JoinCompanyRequestVO;
import com.netpace.device.vo.VAPUserDetails;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyRegistrationController {

    private static final Log log = LogFactory.getLog(CompanyRegistrationController.class);
    @Autowired
    private CompanyRegistrationService companyRegistrationService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private VAPUserDetailsService userDetailsService;
    @Autowired
    private UtilitiesService utilitiesService;
    private static final String PARTNER_USER_MY_ACCOUNT = "/secure/profile.do";
    @Resource(name = "companyFormValidator")
    private Validator companyFormValidator;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    /**
     * render company registration view
     *
     * @param request
     * @param activationSuccessful
     * @return
     */
    @RequestMapping(value = "/company/registercompany.do", method = RequestMethod.GET)
    public ModelAndView registerCompany(HttpServletRequest request,
            boolean activationSuccessful, CompanyRegistration companyForm,
            boolean retainModel) {
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        CompanyRegistration companyVO;
        ModelAndView mav = new ModelAndView("/companyRegistration/companyInfo.jsp");

        log.info("Starting company registration");

        companyVO = companyRegistrationService.getCompanyByDomainName(loggedInUser.getCompanyDomain());
        if (companyVO != null
                && (companyVO.getId() != null && companyVO.getId() > 0)) {
            mav.setViewName("redirect:/company/asktojoin.do");
            mav.addObject("activationSuccessful", activationSuccessful);
            return mav;
        }

        if (retainModel) {
            companyVO = companyForm;
        } else {
            companyVO = new CompanyRegistration();
            companyVO.setCountry(VAPConstants.DEFAULT_COUNTRY);
            companyVO.setDomainName(loggedInUser.getCompanyDomain());
            companyVO.setUser(userService.getUserById(loggedInUser.getId()));
            companyVO.setSalesContact(new ContactVO());
        }

        mav.addObject("companyForm", companyVO);
        mav.addObject("activationSuccessful", activationSuccessful);

        return mav;
    }

    /**
     * submit company registration
     *
     * @param request
     * @param companyVO
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/company/registercompany.do", method = RequestMethod.POST)
    public ModelAndView registerCompanySubmit(HttpServletRequest request,
            @ModelAttribute(value = "companyForm") CompanyRegistration companyVO,
            BindingResult bindingResult) {
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        ModelAndView mav = new ModelAndView("redirect:/secure/product/new.do");

        log.info("Submiting company registration: domainName[" + companyVO.getDomainName() + "]");

        try {
            companyFormValidator.validate(companyVO, bindingResult);

            if (bindingResult.hasErrors()) {
                return registerCompany(request, Boolean.FALSE, companyVO, Boolean.TRUE);
            }
//            UserInfo usetInfo = userService.getUserById(loggedInUser.getId());              
            // register company
            companyRegistrationService.registerCompany(loggedInUser, companyVO);

            // relogin company user
            VAPSecurityManager.doLogin(request, userDetailsService, utilitiesService, loggedInUser.getUsername(), true);

            log.info("Company registered: domainName[" + companyVO.getDomainName() + "]");

        } catch (BusinessRuleException bre) {
            if (bre.getBusinessRule() == CompanyValidationBusinessRule.DOMAIN_COMPANY_ALREADY_EXISTS) {
                mav = new ModelAndView("redirect:/company/asktojoin.do");
                return mav;
            } else if (bre.getBusinessRule() == CompanyValidationBusinessRule.COMPANY_DOMAIN_IS_BLOCKED) {
                bindingResult.reject(
                        "msg.partner.company.domain.block",
                        new String[]{"Company Domain"},
                        "msg.partner.company.domain.block");
                return registerCompany(request, Boolean.FALSE, companyVO, Boolean.TRUE);
            } else if (bre.getBusinessRule() == CompanyValidationBusinessRule.COMPANY_NAME_IS_DUPLICATE) {
                bindingResult.reject(
                        "msg.partner.company.name.duplicate",
                        new String[]{"Company Name"},
                        "msg.partner.company.name.duplicate");
                return registerCompany(request, Boolean.FALSE, companyVO, Boolean.TRUE);
            }
        }
        return mav;
    }

    /**
     * default view for parnter user
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/company/registration.do", method = RequestMethod.GET)
    public ModelAndView registration(HttpServletRequest request) {
        CompanyRegistration companyVO;
        JoinCompanyRequestVO pendingRequestToJoin;
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        ModelAndView mav = new ModelAndView("redirect:/company/registercompany.do");


        companyVO = companyRegistrationService.getUserCompany(loggedInUser.getId());
        if (companyVO != null) {
            // User part of a company
            mav.setViewName("redirect:" + PARTNER_USER_MY_ACCOUNT);
            return mav;
        }

        pendingRequestToJoin = companyRegistrationService.getPendingRequestToJoin(loggedInUser.getId());
        if (pendingRequestToJoin != null) {
            // User join request pending
            mav.setViewName("redirect:" + PARTNER_USER_MY_ACCOUNT);
            return mav;
        }

        companyVO = companyRegistrationService.getCompanyByDomainName(loggedInUser.getCompanyDomain());
        if (companyVO != null) {
            // Ask user to join company
            mav.setViewName("redirect:/company/asktojoin.do");
            return mav;
        }

        // Redirect user to register new company
        return mav;
    }

    /**
     * ask partner user to join existing company
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/company/asktojoin.do", method = RequestMethod.GET)
    public ModelAndView askToJoin(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/companyRegistration/askToJoin.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        
        if(loggedInUser.getCompanyId() != null){
            mav.setViewName("redirect:/secure/login/redirect.do");
        }else {
            if(companyService.findCompanyIdOfPendingUser(loggedInUser.getId()) != null){
                mav.setViewName("redirect:/secure/login/redirect.do");
            }
        }

        return mav;
    }

    /**
     * submit partner user request to join existing company
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/company/asktojoin.do", method = RequestMethod.POST)
    public ModelAndView requestToJoin(HttpServletRequest request,
            @RequestParam("requestToJoin") boolean requestToJoin) {
        ModelAndView mav = new ModelAndView("redirect:/secure/message.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (requestToJoin) {
            CompanyVO companyVO = companyRegistrationService
                    .requestToJoinCompany(loggedInUser);
            mav.addObject("MESSAGE_KEY", "msg.request.to.join.company.success");
            mav.addObject("COMPANY_NAME", companyVO.getName());
        } else {
            VAPSecurityManager.doLogout(
                    request, userService, loggedInUser.getUsername());
            companyRegistrationService.rejectToJoinCompany(loggedInUser);
            mav.setViewName("redirect:/registration.do");
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
                || uri.indexOf("registercompany.do") != -1) {

            SortedMap<String, String> countryList =
                    applicationPropertiesService.propertiesByType(
                    ApplicationPropertyType.COUNTRIES);

            map.put("countryList", countryList);
        }
        return map;
    }
}
