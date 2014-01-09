package com.netpace.device.controllers;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.annotation.utils.RoleUtil;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.AccountRegistrationBusinessRule;
import com.netpace.device.enums.ActivationType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.SystemRoleService;
import com.netpace.device.services.UserManagementService;
import com.netpace.device.services.UserService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.ActivateAdminVO;
import com.netpace.device.vo.ActivateAdminValidator;
import com.netpace.device.vo.AdminUserValidator;
import com.netpace.device.vo.ChangePasswordVO;
import com.netpace.device.vo.ChangePasswordValidator;
import com.netpace.device.vo.ChangeUserNameVO;
import com.netpace.device.vo.ChangeUsernameValidator;
import com.netpace.device.vo.GenericListForm;
import com.netpace.device.vo.InviteUserVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.ProfileValidator;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.device.vo.UserInfo;
import com.netpace.device.vo.VAPUserDetails;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserManagementController {

    private static final Log log = LogFactory.getLog(UserManagementController.class);
    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    private ApplicationPropertiesService applicationProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private VAPUserDetailsService userDetailsService;
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private UtilitiesService utilitiesService;
    @Resource(name = "profileValidator")
    private ProfileValidator profileValidator;
    @Resource(name = "changeUsernameValidator")
    private ChangeUsernameValidator changeUsernameValidator;
    @Resource(name = "changePasswordValidator")
    private ChangePasswordValidator changePasswordValidator;
    @Resource(name = "activateAdminValidator")
    private ActivateAdminValidator activateAdminValidator;
    @Resource(name = "adminUserValidator")
    private AdminUserValidator adminUserValidator;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SystemRoleVO.class, new SystemRoleVOPropertyEditor(systemRoleService.getAllSystemRoles()));
    }

    /**
     * Get list of users for management
     *
     * @param request
     * @param page
     * @return
     */
    @RequestMapping("/secure/admin/userslist.do")
    public ModelAndView usersList(HttpServletRequest request,
            @ModelAttribute(value = "genericListForm") GenericListForm<UserInfo> genericListForm) {
        ModelAndView mav = new ModelAndView("/secure/usermanagement/userslist.jsp");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        PageModel<UserInfo> pageModel;

        log.debug("Invite User View");

        pageModel = genericListForm.getPageModel();
        pageModel.setPageSize(applicationProperties.defaultPageSize());

        pageModel = userManagementService.getPagedList(genericListForm.getPageModel());

        genericListForm.setPageModel(pageModel);

        mav.addObject("genericListForm", genericListForm);

        if (StringUtils.isNotEmpty(request.getParameter("EMAIL_ADDRESS"))
                && StringUtils.isNotEmpty(request.getParameter("ADMIN_USER_INVITATION_SENT"))) {

            mav.addObject("EMAIL_ADDRESS", request.getParameter("EMAIL_ADDRESS"));
            mav.addObject("ADMIN_USER_INVITATION_SENT", "msg.admin.user.invitation.send");

        } else if (StringUtils.isNotEmpty(request.getParameter("ADMIN_USER_UPDATED"))) {

            mav.addObject("ADMIN_USER_UPDATED", "msg.admin.user.update");

        } else if (StringUtils.isNotEmpty(request.getParameter("ADMIN_USER_DELETED"))) {

            mav.addObject("ADMIN_USER_DELETED", "msg.admin.user.delete");
        }
        return mav;
    }

    /**
     * Render the invite user view
     *
     * @param request
     * @param retainModel if true, do not set model object
     * @return
     */
    @RequestMapping("/secure/admin/inviteuserview.do")
    public ModelAndView inviteUserView(HttpServletRequest request,
            boolean retainModel) {
        InviteUserVO inviteUserVO;
        List<SystemRoleVO> allSystemRoles;
        ModelAndView mav = new ModelAndView("/secure/usermanagement/inviteuser.jsp");

        log.debug("Invite User View");

        if (!retainModel) {
            inviteUserVO = new InviteUserVO();
            mav.addObject("inviteUserVO", inviteUserVO);
        }

        allSystemRoles = systemRoleService.getAllUnhiddenRoles();
        mav.addObject("systemRolesList", allSystemRoles);

        return mav;
    }

    /**
     * Invite user with details filled in inviteUserVO
     *
     * @param request
     * @param inviteUserVO
     * @param bindingResult
     * @return
     */
    @RequestMapping("/secure/admin/inviteuser.do")
    public ModelAndView inviteUser(HttpServletRequest request,
            @Valid @ModelAttribute(value = "inviteUserVO") InviteUserVO inviteUserVO,
            BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView("redirect:/secure/admin/userslist.do");

        log.debug("Invite User");
        try {
            VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

            if (bindingResult.hasErrors()) {
                mav.addObject("inviteUserVO", inviteUserVO);
                return inviteUserView(request, true);
            }

            String baseUrl = VAPUtils.getFormattedURL(request.getScheme(), request.getServerName(),
                    request.getServerPort(), request.getContextPath(), "/admin/invitation.do");
            String invitationURL = baseUrl + "?" + AccountRegistrationController.PARAM_AUTH_CODE + "=";

            userManagementService.inviteUser(inviteUserVO, invitationURL, loggedInUser);
            mav.addObject("EMAIL_ADDRESS", inviteUserVO.getEmailAddress());
            mav.addObject("ADMIN_USER_INVITATION_SENT", "msg.admin.user.invitation.send");
        } catch (BusinessRuleException bre) {
            bindingResult.reject(bre.getBusinessRule().getMessage(), bre.getBusinessRule().getMessage());
            return inviteUserView(request, true);
        }

        return mav;
    }

    /**
     * Render confirmation view for soft delete and re-activate user
     *
     * @param request
     * @param activateAdminVO
     * @param retainModel if true, do not set model object
     * @return
     */
    public ModelAndView activateAdminConfirmView(HttpServletRequest request,
            @Valid @ModelAttribute(value = "activateAdminVO") ActivateAdminVO activateAdminVO,
            boolean retainModel) {
        ModelAndView mav = new ModelAndView("/usermanagement/admin/activateadminconfirm.jsp");

        log.debug("Activate User Confirmation View");

        mav.addObject("activateAdminVO", activateAdminVO);

        return mav;
    }

    /**
     * action to soft delete existing user and re-activate
     *
     * @param request
     * @param confirm if true, soft delete existing user and re-activate
     * @param activateAdminVO
     * @param bindingResult
     * @return
     */
    @RequestMapping("/activateadminconfirm.do")
    public ModelAndView activateAdminConfirm(HttpServletRequest request,
            @RequestParam(value = "confirm", defaultValue = "false") boolean confirm,
            @Valid @ModelAttribute(value = "activateAdminVO") ActivateAdminVO activateAdminVO,
            BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("redirect:/secure/login/redirect.do");

        log.debug("Activate User Confirmed");

        try {
            if (bindingResult.hasErrors()) {
                mav.addObject("activateAdminVO", activateAdminVO);
                //return activateUserView(request, null, true);
            }

            if (confirm) {
                userManagementService.reActivateAdmin(activateAdminVO);
                VAPSecurityManager.doLogin(request, userDetailsService, utilitiesService, activateAdminVO.getUserName());
            } else {
                mav.setViewName("redirect:/signin.do");
            }

        } catch (BusinessRuleException bre) {
            bindingResult.reject(bre.getBusinessRule().getMessage(), bre.getBusinessRule().getMessage());
            //return activateUserView(request, null, true);
        }
        return mav;
    }

    /**
     * Get my profile
     *
     * @return ModelAndView
     */
    @RequestMapping("/secure/profile.do")
    public ModelAndView viewProfile(HttpServletRequest request, boolean retainModel, String action) {

        /**
         * TODO: Need to move this method to UserAccountController later.
         */
        ModelAndView mav = new ModelAndView("/secure/userAccount/myprofile.jsp");


        log.debug("My profile.");

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        UserInfo profile;
        ChangeUserNameVO changeUserNameVO;
        ChangePasswordVO changePasswordVO = new ChangePasswordVO();

        if (!retainModel) {
            profile = userService.getMyProfile(loggedInUser);
            changeUserNameVO = new ChangeUserNameVO();
            changeUserNameVO.setUserName(profile.getUserName());

            mav.addObject("myProfileForm", profile);
            mav.addObject("userNameForm", changeUserNameVO);
        } else if (action.equalsIgnoreCase("updateProfile")) {
            changeUserNameVO = new ChangeUserNameVO();
            changeUserNameVO.setUserName(userService.getMyProfile(loggedInUser).getUserName());
            mav.addObject("userNameForm", changeUserNameVO);

        } else if (action.equalsIgnoreCase("updateUserName")) {
            profile = userService.getMyProfile(loggedInUser);
            mav.addObject("myProfileForm", profile);
            mav.addObject("JUMP_TO", VAPConstants.MY_PROFILE_UPDATE_USERNAME_SECTION);
        } else if (action.equalsIgnoreCase("updatePassword")) {
            profile = userService.getMyProfile(loggedInUser);
            changeUserNameVO = new ChangeUserNameVO();
            changeUserNameVO.setUserName(profile.getUserName());

            mav.addObject("myProfileForm", profile);
            mav.addObject("JUMP_TO", VAPConstants.MY_PROFILE_RESET_PASSWORD_SECTION);
            mav.addObject("userNameForm", changeUserNameVO);
        }
        mav.addObject("changePasswordForm", changePasswordVO);

        if (StringUtils.isNotEmpty(request.getParameter("PROFILE_UPDATED"))) {
            mav.addObject("PROFILE_UPDATED", "msg.profile.update");
        } else if (StringUtils.isNotEmpty(request.getParameter("USERNAME_UPDATED"))) {
            mav.addObject("USERNAME_UPDATED", "msg.username.update");
            mav.addObject("JUMP_TO", VAPConstants.MY_PROFILE_UPDATE_USERNAME_SECTION);
        } else if (StringUtils.isNotEmpty(request.getParameter("PASSWORD_UPDATED"))) {
            mav.addObject("PASSWORD_UPDATED", "msg.password.update");
            mav.addObject("JUMP_TO", VAPConstants.MY_PROFILE_RESET_PASSWORD_SECTION);
        }
        return mav;
    }

    /**
     * User view
     *
     * @return ModelAndView
     */
    @RequestMapping("/secure/userview.do")
    public ModelAndView viewUser(HttpServletRequest request,
            @RequestParam(value = "id") Integer userId) {
        
        ModelAndView mav = new ModelAndView("redirect:/error/error404.do");

        log.debug("User view.");

        UserInfo userInfo = userService.getUserById(userId);
        
        if(userInfo != null) {
            mav.addObject("userInfo", userInfo);
            mav.setViewName("/secure/userAccount/userview.jsp");
        }
        return mav;
    }

    /**
     * Update profile
     *
     * @param myProfile
     * @param request
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/secure/updateprofile.do", method = RequestMethod.POST)
    public ModelAndView updateProfile(HttpServletRequest request,
            @ModelAttribute(value = "myProfileForm") UserInfo myProfile,
            BindingResult bindingResult) {

        /**
         * TODO: Need to move this method to UserAccountController later.
         */
        log.debug("Update my profile");

        ModelAndView mav = new ModelAndView("redirect:/secure/profile.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        profileValidator.validate(myProfile, bindingResult);

        if (bindingResult.hasErrors()) {
            mav.addObject("myProfileForm", myProfile);
            return viewProfile(request, true, "updateProfile");
        } else {
            // do service
            userService.updateMyProfile(myProfile, loggedInUser);
            mav.addObject("myProfileForm", myProfile);
            mav.addObject("PROFILE_UPDATED", "msg.profile.update");
        }

        log.info("My profile updated");

        return mav;
    }

    /**
     * Update Username
     *
     * @param request
     * @param changeUserNameVO
     * @param bindingResult
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/secure/updateusername.do", method = RequestMethod.POST)
    public ModelAndView updateUserName(HttpServletRequest request,
            @ModelAttribute(value = "userNameForm") ChangeUserNameVO changeUserNameVO,
            BindingResult bindingResult) {

        /**
         * TODO: Need to move this method to UserAccountController later.
         */
        log.debug("Update Username");

        ModelAndView mav = new ModelAndView("redirect:/secure/profile.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        changeUsernameValidator.validate(changeUserNameVO, bindingResult);

        if (bindingResult.hasErrors()) {
            mav.addObject("userNameForm", changeUserNameVO);
            return viewProfile(request, true, "updateUserName");
        } else {
            // do service
            try {
                userService.updateUserName(changeUserNameVO, loggedInUser);
                mav.addObject("USERNAME_UPDATED", "msg.username.update");
            } catch (BusinessRuleException bre) {

                if (bre.getBusinessRule().equals(AccountRegistrationBusinessRule.ACCOUNT_USERNAME_EXISTS)) {

                    bindingResult.reject(
                            bre.getBusinessRule().getMessage(),
                            bre.getBusinessRule().getMessage());

                } else if (bre.getBusinessRule().equals(UserValidationBusinessRule.USERNAME_USER_EXISTS)) {

                    bindingResult.rejectValue(
                            "userName", "field.duplicate",
                            new String[]{"New Username"},
                            "Duplicate New Username");
                } else if (bre.getBusinessRule().equals(UserValidationBusinessRule.CHANGE_USERNAME_INVALID_CURRENT_PASSWORD)) {

                    bindingResult.rejectValue(
                            "currentPassword", "msg.currentPassword.incorrect",
                            new String[]{"Current Password"},
                            "Provide correct Current Password");
                } else if (bre.getBusinessRule().equals(UserValidationBusinessRule.COMPANY_USER_IS_SAME)) {

                    bindingResult.rejectValue(
                            "userName", "field.same",
                            new String[]{"New Username"},
                            "New Username is not different!");
                }
                mav.addObject("userNameForm", changeUserNameVO);
                return viewProfile(request, true, "updateUserName");
            }
            mav.addObject("userNameForm", changeUserNameVO);
        }

        log.info("Username updated");

        return mav;
    }

    /**
     * Change password
     *
     * @param request
     * @param changePasswordVO
     * @param bindingResult
     * @return ModelAndView
     */
    @RequestMapping(value = "/secure/updatepassword.do", method = RequestMethod.POST)
    public ModelAndView updatePassword(HttpServletRequest request,
            @ModelAttribute(value = "changePasswordForm") ChangePasswordVO changePasswordVO,
            BindingResult bindingResult) {
        /**
         * TODO: Need to move this method to UserAccountController later.
         */
        log.debug("Change password");

        ModelAndView mav = new ModelAndView("redirect:/secure/profile.do");
        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        changePasswordValidator.validate(changePasswordVO, bindingResult);

        if (bindingResult.hasErrors()) {
            return viewProfile(request, true, "updatePassword");
        } else {
            // do service
            try {
                userService.changePassword(changePasswordVO, loggedInUser);
            } catch (BusinessRuleException bre) {
                bindingResult.rejectValue(
                        "currentPassword", "msg.currentPassword.incorrect",
                        new String[]{"Current Password"},
                        "Provide correct Current Password");

                mav.addObject("changePasswordForm", changePasswordVO);
                return viewProfile(request, true, "updatePassword");
            }
            mav.addObject("changePasswordForm", changePasswordVO);
            mav.addObject("PASSWORD_UPDATED", "msg.password.update");
        }

        log.info("Password changed");

        return mav;
    }

    /**
     * Render accept admin invitation view
     *
     * @param request
     * @param retainModel if true, do not set model object
     *
     * @return
     */
    @RequestMapping("/admin/acceptinvitationview.do")
    public ModelAndView acceptAdminInvitationView(HttpServletRequest request,
            boolean retainModel) {
        ModelAndView mav = new ModelAndView("/usermanagement/admin/activateuser.jsp");

        log.debug("Accept Invitation View");

        try {
            if (!retainModel) {
                InviteUserVO inviteUserVO = userManagementService.getUserInvite(
                        request.getParameter("activationCode"),
                        ActivationType.OFFER_TO_JOIN_AS_ADMIN.getLabel());
                if (inviteUserVO != null) {
                    ActivateAdminVO activateAdminVO = new ActivateAdminVO();
                    activateAdminVO.setActivationCode(request.getParameter("activationCode"));
                    activateAdminVO.setActivationType(ActivationType.OFFER_TO_JOIN_AS_ADMIN.getLabel());
                    activateAdminVO.setFullName(inviteUserVO.getFullName());
                    activateAdminVO.setEmailAddress(inviteUserVO.getEmailAddress());
                    activateAdminVO.setCountry(VAPConstants.DEFAULT_COUNTRY);
                    mav.addObject("activateAdminVO", activateAdminVO);
                }
            }
        } catch (BusinessRuleException bre) {
            log.debug(bre.getBusinessRule().getMessage());
            mav.setViewName("redirect:/activationerror.do");
        }
        return mav;
    }

    /**
     * Accept the admin user invitation
     *
     * @param request
     * @param activateAdminVO
     * @param bindingResult
     *
     * @return
     */
    @RequestMapping("/admin/acceptinvitation.do")
    public ModelAndView acceptAdminInvitation(HttpServletRequest request,
            @ModelAttribute(value = "activateAdminVO") ActivateAdminVO activateAdminVO,
            BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("redirect:/secure/worklist.do");

        log.debug("Accept Invitation");

        try {
            activateAdminValidator.validate(activateAdminVO, bindingResult);

            if (bindingResult.hasErrors()) {
                mav.addObject("activateAdminVO", activateAdminVO);
                return acceptAdminInvitationView(request, true);
            }
            activateAdminVO.setActivationType(ActivationType.OFFER_TO_JOIN_AS_ADMIN.getLabel());
            userManagementService.activateInviteAdmin(activateAdminVO);
            VAPSecurityManager.doLogin(request, userDetailsService, utilitiesService, activateAdminVO.getUserName());

        } catch (BusinessRuleException bre) {

            if (bre.getBusinessRule().equals(UserValidationBusinessRule.EMAIL_USER_EXISTS)) {
                return activateAdminConfirmView(request, activateAdminVO, true);
            } else {
                bindingResult.reject(bre.getBusinessRule().getMessage(), bre.getBusinessRule().getMessage());
                return acceptAdminInvitationView(request, true);
            }
        }
        return mav;
    }

    @RequestMapping("/secure/admin/edituserview.do")
    public ModelAndView editUserView(HttpServletRequest request,
            @RequestParam("id") Integer userId, boolean retainModel) {

        VAPUserDetails loggedInUser;

        UserInfo editAdminUserVO;
        ModelAndView mav = new ModelAndView("/secure/usermanagement/edituser.jsp");

        log.debug("Edit Admin User View");

        loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.getId().equals(userId)) {
            log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                    + "' tried to edit itself maliciously, hence redirecting to admin users list view");

            mav.setViewName("redirect:/secure/admin/userslist.do");
            return mav;
        }
        List<SystemRoleVO> allSystemRoles =
                systemRoleService.getAllUnhiddenRoles();

        if (!retainModel) {
            try {
                UserInfo userInfo = userService.getAdminUserById(userId);

                if (RoleUtil.isRoleFound(
                        userInfo.getRoles(), VAPConstants.ROLE_PARTNER_USER, loggedInUser)) {

                    log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                            + "' tried to edit partner user with id: '" + userId
                            + "' maliciously, hence redirecting to admin users list view");

                    mav.setViewName("redirect:/secure/admin/userslist.do");
                    return mav;
                }
                editAdminUserVO = userService.getAdminUserById(userId);
                mav.addObject("editAdminUserVO", editAdminUserVO);

            } catch (BusinessRuleException bre) {

                log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                        + "' tried to edit partner user with id: '" + userId
                        + "' maliciously, hence redirecting to admin users list view");

                mav.setViewName("redirect:/secure/admin/userslist.do");
            }
        }
        mav.addObject("systemRolesList", allSystemRoles);
        return mav;
    }

    @RequestMapping("/secure/admin/deleteuser.do")
    public ModelAndView deleteUser(HttpServletRequest request,
            @RequestParam("id") Integer userId) {

        VAPUserDetails loggedInUser;

        ModelAndView mav = new ModelAndView("redirect:/secure/admin/userslist.do");

        log.debug("Delete Admin User");

        loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.getId().equals(userId)) {
            log.debug("Logged in admin user: '"
                    + loggedInUser.getUsername()
                    + "' tried to delete itself maliciously, hence redirecting to admin users list view");
            return mav;
        }

        try {
            UserInfo userInfo = userService.getAdminUserById(userId);

            if (RoleUtil.isRoleFound(
                    userInfo.getRoles(), VAPConstants.ROLE_PARTNER_USER, loggedInUser)) {

                log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                        + "' tried to delete partner user with id: '" + userId
                        + "' maliciously, hence redirecting to admin users list view");

                return mav;
            }
            userManagementService.deleteAdminUser(userId, loggedInUser);

            mav.addObject("ADMIN_USER_DELETED", "msg.admin.user.delete");

        } catch (BusinessRuleException bre) {
            log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                    + "' tried to delete partner user with id: '" + userId
                    + "' maliciously");
        }
        return mav;
    }

    @RequestMapping("/secure/admin/edituser.do")
    public ModelAndView editUser(HttpServletRequest request,
            @ModelAttribute(value = "editAdminUserVO") UserInfo editAdminUserVO,
            BindingResult bindingResult) {

        VAPUserDetails loggedInUser;

        ModelAndView mav = new ModelAndView("redirect:/secure/admin/userslist.do");

        log.debug("Edit Admin User");

        loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.getId().equals(editAdminUserVO.getId())) {
            log.debug("Logged in admin user: '"
                    + loggedInUser.getUsername()
                    + "' tried to edit itself maliciously, hence redirecting to admin users list view");
            return mav;
        }

        try {
            UserInfo userInfo = userService.getAdminUserById(editAdminUserVO.getId());

            if (RoleUtil.isRoleFound(userInfo.getRoles(),
                    VAPConstants.ROLE_PARTNER_USER, loggedInUser)) {

                log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                        + "' tried to edit partner user with id: '"
                        + editAdminUserVO.getId()
                        + "' maliciously, hence redirecting to admin users list view");
                return mav;
            }
            adminUserValidator.validate(editAdminUserVO, bindingResult);

            if (bindingResult.hasErrors()) {
                mav.addObject("editAdminUserVO", editAdminUserVO);
                return editUserView(request, editAdminUserVO.getId(), true);
            }
            userManagementService.editAdminUser(editAdminUserVO, loggedInUser);

            mav.addObject("ADMIN_USER_UPDATED", "msg.admin.user.update");

        } catch (BusinessRuleException bre) {
            log.debug("Logged in admin user: '" + loggedInUser.getUsername()
                    + "' tried to edit partner user with id: '"
                    + editAdminUserVO.getId() + "' maliciously");
        }
        return mav;
    }

    @ModelAttribute("populatedFormElements")
    public Map<String, Map<String, String>> populateFormListElements(
            HttpServletRequest request) {

        Map<String, Map<String, String>> map =
                new HashMap<String, Map<String, String>>();

        String uri = request.getRequestURI();

        if (uri.indexOf("acceptinvitationview.do") != -1
                || uri.indexOf("acceptinvitation.do") != -1
                || uri.indexOf("profile.do") != -1
                || uri.indexOf("edituserview.do") != -1
                || uri.indexOf("edituser.do") != -1
                || uri.indexOf("updatepassword.do") != -1
                || uri.indexOf("updateusername.do") != -1) {

            SortedMap<String, String> countryList =
                    applicationPropertiesService.propertiesByType(
                    ApplicationPropertyType.COUNTRIES);

            map.put("countryList", countryList);
        }
        return map;
    }
}
