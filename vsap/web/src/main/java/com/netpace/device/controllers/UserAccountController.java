package com.netpace.device.controllers;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.UserAccountService;
import com.netpace.device.services.UserService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.UserInfo;
import com.netpace.device.vo.UserInfoResend;
import com.netpace.device.vo.VAPUserDetails;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {

    private static final Log log = LogFactory.getLog(UserAccountController.class);
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    ApplicationPropertiesService applicationPropertiesService;
    @Resource(name = "userInfoResendValidator")
    Validator userInfoResendValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String PARAM_PASSWORD_RESET_TOKEN = "PRT";

    @RequestMapping(value = "/forgotUsername.do", method = RequestMethod.GET)
    public ModelAndView forgotUsername(HttpServletRequest request) {

        UserInfoResend userInfoResend = new UserInfoResend();
        ModelAndView mav = new ModelAndView("userAccount/forgotUsername.jsp");

        log.debug("Forgot username");
        mav.addObject("userInfoResend", userInfoResend);

        return mav;
    }

    @RequestMapping(value = "/forgotPassword.do", method = RequestMethod.GET)
    public ModelAndView forgotPassword(HttpServletRequest request) {

        UserInfoResend userInfoResend = new UserInfoResend();
        ModelAndView mav = new ModelAndView("userAccount/forgotpassword.jsp");

        log.debug("Forgot password");
        mav.addObject("userInfoResend", userInfoResend);

        return mav;
    }

    @RequestMapping(value = "/forgotUsernameSubmit.do")
    public ModelAndView forgotUsernameSubmit(HttpServletRequest request,
            @ModelAttribute(value = "userInfoResend") UserInfoResend userInfoResend, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("userAccount/forgotusernamemessage.jsp");

        // set messageText and field for service
        userInfoResend.setMessageText("Forgot Username");
        userInfoResend.setField("username");
        userInfoResendValidator.validate(userInfoResend, bindingResult);

        if (bindingResult.hasErrors()) {
            mav.setViewName("userAccount/forgotUsername.jsp");
            return mav;
        }

        try {
            userAccountService.forgotUsername(userInfoResend);

        } catch (BusinessRuleException bre) {
            if (bre.getBusinessRule() == UserValidationBusinessRule.USER_NOT_EXISTS) {
                bindingResult.reject("fields.forgot.username.user.not.found", "fields.forgot.username.user.not.found");
                mav.setViewName("userAccount/forgotUsername.jsp");
                return mav;
            }
        }

        mav.addObject("receiver_email_address", userInfoResend.getEmailAddress());
        return mav;
    }

    @RequestMapping(value = "/forgotPasswordSubmit.do")
    public ModelAndView forgotPasswordSubmit(HttpServletRequest request,
            @ModelAttribute(value = "userInfoResend") UserInfoResend userInfoResend, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("userAccount/forgotpasswordmessage.jsp");

        // set messageText and field for service
        userInfoResend.setMessageText("Forgot Password");
        userInfoResend.setField("password");
        userInfoResendValidator.validate(userInfoResend, bindingResult);

        if (bindingResult.hasErrors()) {
            mav.setViewName("userAccount/forgotpassword.jsp");
            return mav;
        }

        try {
            String baseUrl = VAPUtils.getFormattedURL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath(), "/resetPassword.do");
            String resetPasswordURL = baseUrl + "?" + PARAM_PASSWORD_RESET_TOKEN + "=";

            userAccountService.forgotPassword(userInfoResend, resetPasswordURL);

        } catch (BusinessRuleException bre) {
            if (bre.getBusinessRule() == UserValidationBusinessRule.USER_NOT_EXISTS) {
                bindingResult.reject("fields.forgot.password.user.not.found", "fields.forgot.password.user.not.found");
                mav.setViewName("userAccount/forgotpassword.jsp");
                return mav;
            }
        }

        UserInfo userInfo = userService.findUserByName(userInfoResend.getUserName());
        mav.addObject("receiver_email_address", userInfo.getEmailAddress());
        return mav;
    }

    @RequestMapping(value = "/accountVerificationConfirmation.do", method = RequestMethod.GET)
    public String confirmAccount(HttpServletRequest request) {
        return "redirect:signin.do";
    }

    private String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath()
                + "/" + applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "password.reset.link");
    }

    @RequestMapping(value = "/resetPassword.do", method = RequestMethod.GET)
    public ModelAndView resetPassword(HttpServletRequest request) {
        String passwordResetToken = request.getParameter(PARAM_PASSWORD_RESET_TOKEN);
        ModelAndView mav = new ModelAndView();
        UserInfoResend userInfoResendVO = new UserInfoResend();
        UserInfoResend userInfoResend = userService.passwordResetTokenExists(passwordResetToken);
        boolean tokenExists = (userInfoResend != null);
        if (tokenExists) {//|| VAPSecurityManager.isUserAuthenticated())

            userInfoResendVO.setEmailAddress(userInfoResend.getEmailAddress());
            userInfoResendVO.setResetPasswordToken(userInfoResend.getResetPasswordToken());
            mav.setViewName("userAccount/resetPassword.jsp");
        } else {
            if (!tokenExists) {
                userInfoResendVO.setMessageText("The password regeneration token has expired.");
            } else if (!VAPSecurityManager.isUserAuthenticated()) {
                userInfoResendVO.setMessageText("User not logged in.");
            }

            mav.setViewName("userAccount/rp_tokenNotFound.jsp");
        }
        mav.addObject("userInfoResend", userInfoResendVO);
        return mav;
    }

    @RequestMapping(value = "/submitPasswordReset.do")
    public ModelAndView resetPassword(@ModelAttribute(value = "userInfoResend") UserInfoResend userInfoResend, BindingResult bindingResult, HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();
        userInfoResend.setField("passwordReset");
        userInfoResendValidator.validate(userInfoResend, bindingResult);
        if (bindingResult.hasErrors()) {
            mav.setViewName("userAccount/resetPassword.jsp");
            return mav;
        }

        userAccountService.resetPassword(passwordEncoder.encodePassword(userInfoResend.getNewPassword(), null),
                userInfoResend.getResetPasswordToken());

        mav.setViewName("userAccount/resetPasswordSuccess.jsp");
        return mav;
    }

    /*
     * This controller is used by the VSAP CQ5 portal to get the username of 
     * the logged in user
     */
    @RequestMapping(value = "/user/accountInfo.do", method = RequestMethod.GET)
    public @ResponseBody
    String accountInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String userName = null;
        if (auth.getName().equals(VAPConstants.ANONYMOUS_AUTH_NAME)) {
            userName = auth.getName();
        } else {
            VAPUserDetails loggedInUser = (VAPUserDetails) auth.getPrincipal();
            userName = loggedInUser.getUsername();
        }

        return userName;
    }
}
