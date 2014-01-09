package com.netpace.device.controllers;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.AccountRegistrationBusinessRule;
import com.netpace.device.enums.ActivationType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.RegistrationService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.AccountRegistration;
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
public class AccountRegistrationController {

    private static final Log log = LogFactory.getLog(AccountRegistrationController.class);
    @Resource(name = "registrationService")
    private RegistrationService registrationService;
    @Resource(name = "accountRegistrationValidator")
    private Validator accountRegistrationValidator;
    @Autowired
    private UtilitiesService utilitiesService;
    @Autowired
    private VAPUserDetailsService userDetailsService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    public static final String PARAM_AUTH_CODE = "authCode";

    @RequestMapping(value = "/registration.do", method = RequestMethod.GET)
    public ModelAndView registerAccount(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("devRegistration/accountInfoAdd.jsp");

        try {
            VAPUserDetails loggedInUser =
                    VAPSecurityManager.getAuthenticationToken();
            if (loggedInUser != null) {
                mav.setViewName("redirect:/secure/login/redirect.do");
                return mav;
            }
        } catch (ClassCastException cce) {
            log.debug("Registration page");
        }

        log.debug("Registration display page");
        String expired = request.getParameter("expired");
        String logout = request.getParameter("logout");
        if (expired != null) {
            mav.addObject("expired", Boolean.TRUE);
        }
        if (logout != null) {
            request.getSession().invalidate();
        }
        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setCountry(VAPConstants.DEFAULT_COUNTRY);
        mav.addObject("accountForm", accountRegistration);

        return mav;
    }

    @RequestMapping(value = "/processRegistration.do")
    public ModelAndView processRegistration(@ModelAttribute(value = "accountForm") AccountRegistration accountRegistration,
            BindingResult bindingResult, HttpServletRequest request) {
        log.debug("process Registration for :" + accountRegistration);

        ModelAndView mav = new ModelAndView();
        accountRegistrationValidator.validate(accountRegistration, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Form contain errors ");
            mav.setViewName("devRegistration/accountInfoAdd.jsp");
            mav.addObject("accountForm", accountRegistration);
        } else {
            try {
                String baseUrl = VAPUtils.getFormattedURL(request.getScheme(), request.getServerName(),
                        request.getServerPort(), request.getContextPath(), "/activation.do");
                String activationURL = baseUrl + "?" + PARAM_AUTH_CODE + "=";

                registrationService.registerAccount(accountRegistration, activationURL);
                mav.addObject("userEmail", accountRegistration.getEmailAddress());
                mav.addObject("userName", accountRegistration.getUserName());
                mav.setViewName("redirect:/processRegistrationSuccess.do");
            } catch (BusinessRuleException bre) {

                if (bre.getBusinessRule() == UserValidationBusinessRule.COMPANY_DOMAIN_IS_BLOCKED) {
                    bindingResult.reject(
                            "msg.partner.company.domain.block",
                            new String[]{"Company Domain"},
                            "msg.partner.company.domain.block");
                } else {
                    bindingResult.reject(
                            bre.getBusinessRule().getMessage(),
                            bre.getBusinessRule().getMessage());
                }
                mav.setViewName("devRegistration/accountInfoAdd.jsp");
                mav.addObject("accountForm", accountRegistration);
            }
        }
        log.debug("process Registration for completed");
        return mav;
    }

    @RequestMapping(value = "/processRegistrationSuccess.do")
    public ModelAndView processRegistrationSuccess(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("devRegistration/accountRegistrationEmail.jsp");

        log.debug("process Registration successful :");

        return mav;
    }

    @RequestMapping(value = "/activation.do", method = RequestMethod.GET)
    public ModelAndView activateAccount(@RequestParam(PARAM_AUTH_CODE) String activationCode, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        try {
            AccountRegistration account = registrationService.activateRegisteredAccount(activationCode);

            // login user
            VAPSecurityManager.doLogin(request, userDetailsService, utilitiesService, account.getUserName());
            //check is company registered or not
            mav.setViewName("redirect:/company/registercompany.do?activationSuccessful=true");

        } catch (BusinessRuleException bre) {
            if (bre.getBusinessRule() == AccountRegistrationBusinessRule.ACCOUNT_ACTIVATION_CODE_NOT_FOUND) {
                mav.setViewName("redirect:/registration.do?expired");
            }
        }
        return mav;
    }

    @RequestMapping(value = "/admin/invitation.do", method = RequestMethod.GET)
    public ModelAndView adminInvitation(@RequestParam(PARAM_AUTH_CODE) String activationCode,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            registrationService.activateRegisteredAccount(
                    activationCode,
                    ActivationType.OFFER_TO_JOIN_AS_ADMIN.getLabel());

            mav.addObject("activationCode", activationCode);
            mav.setViewName("redirect:/admin/acceptinvitationview.do");
        } catch (BusinessRuleException bre) {
            log.debug(bre.getBusinessRule().getMessage());
            mav.setViewName("redirect:/activationerror.do");
        }
        return mav;
    }

    @RequestMapping(value = "/user/invitation.do", method = RequestMethod.GET)
    public ModelAndView userInvitation(@RequestParam(PARAM_AUTH_CODE) String activationCode,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            registrationService.activateRegisteredAccount(
                    activationCode,
                    ActivationType.OFFER_TO_JOIN_COMPANY.getLabel());

            mav.addObject("activationCode", activationCode);
            mav.setViewName("redirect:/user/acceptinvitationview.do");
        } catch (BusinessRuleException bre) {
            log.debug(bre.getBusinessRule().getMessage());
            mav.setViewName("redirect:/activationerror.do");
        }
        return mav;
    }

    @RequestMapping(value = "/activationerror.do", method = RequestMethod.GET)
    public ModelAndView activationError(HttpServletRequest request) {
        log.debug("Activation error page");
        ModelAndView mav = new ModelAndView("/activationerror.jsp");
        return mav;
    }

    @ModelAttribute("populatedFormElements")
    public Map<String, Map<String, String>> populateFormListElements(
            HttpServletRequest request) {

        Map<String, Map<String, String>> map =
                new HashMap<String, Map<String, String>>();

        String uri = request.getRequestURI();
        if (uri.indexOf("registration.do") != -1
                || uri.indexOf("processRegistration.do") != -1
                || uri.indexOf("acceptinvitationview.do") != -1
                || uri.indexOf("acceptinvitation.do") != -1) {

            SortedMap<String, String> countryList =
                    applicationPropertiesService.propertiesByType(
                    ApplicationPropertyType.COUNTRIES);

            map.put("countryList", countryList);
        }
        return map;
    }
}
