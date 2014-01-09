package com.netpace.device.controllers;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.annotation.utils.RoleUtil;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.ActivationType;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.services.UserManagementService;
import com.netpace.device.services.UserService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.services.VAPUserDetailsService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.VAPSecurityManager;
import com.netpace.device.vo.AccountRegistration;
import com.netpace.device.vo.AccountRegistrationValidator;
import com.netpace.device.vo.GenericListForm;
import com.netpace.device.vo.InvitePartnerUserVO;
import com.netpace.device.vo.InvitePartnerUserValidator;
import com.netpace.device.vo.InviteUserVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.PartnerUserVO;
import com.netpace.device.vo.PartnerUserValidator;
import com.netpace.device.vo.UserInfo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Noorain
 */
@Controller
public class PartnerUserManagementController {

    private static final Log log = LogFactory.getLog(PartnerUserManagementController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private UtilitiesService utilitiesService;
    @Autowired
    private VAPUserDetailsService userDetailsService;
    @Resource(name = "invitePartnerUserValidator")
    private InvitePartnerUserValidator invitePartnerUserValidator;
    @Resource(name = "accountRegistrationValidator")
    private AccountRegistrationValidator accountRegistrationValidator;
    @Resource(name = "partnerUserValidator")
    private PartnerUserValidator partnerUserValidator;
    @RequestMapping("/secure/company/manageusers.do")
    public ModelAndView manageUsers(HttpServletRequest request,
            @ModelAttribute(value = "genericListForm") GenericListForm<PartnerUserVO> genericListForm) {
        ModelAndView mav = new ModelAndView("/secure/partnerusermanagement/manageusers.jsp");
        PageModel<PartnerUserVO> pageModel;

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();
        log.debug("Get all company users.");

        pageModel = genericListForm.getPageModel();
        pageModel.setPageSize(applicationPropertiesService.defaultPageSize());
        pageModel = companyService.getAllPartnerUsers(
                loggedInUser, genericListForm.getPageModel());
        genericListForm.setPageModel(pageModel);
        mav.addObject("genericListForm", genericListForm);

        if (StringUtils.isNotEmpty(request.getParameter("EMAIL_ADDRESS"))
                && StringUtils.isNotEmpty(request.getParameter("PARTNER_USER_INVITATION_SENT"))) {

            mav.addObject("EMAIL_ADDRESS", request.getParameter("EMAIL_ADDRESS"));
            mav.addObject("PARTNER_USER_INVITATION_SENT", "msg.partner.user.invitation.send");

        } else if (StringUtils.isNotEmpty(request.getParameter("PARTNER_USER_UPDATED"))) {
            mav.addObject("PARTNER_USER_UPDATED", "msg.partner.user.update");

        } else if (StringUtils.isNotEmpty(request.getParameter("MPOC_FULL_NAME"))) {

            mav.addObject("MPOC_FULL_NAME", request.getParameter("MPOC_FULL_NAME"));

            if (StringUtils.isNotEmpty(request.getParameter("PARTNER_USER_CANNOT_EDIT_MPOC"))) {
                mav.addObject("PARTNER_USER_CANNOT_EDIT_MPOC", "msg.partner.user.cannot.edit.mpoc");
            } else if (StringUtils.isNotEmpty(request.getParameter("PARTNER_USER_CANNOT_DELETE_MPOC"))) {
                mav.addObject("PARTNER_USER_CANNOT_DELETE_MPOC", "msg.partner.user.cannot.delete.mpoc");
            }

        } else if (StringUtils.isNotEmpty(request.getParameter("PARTNER_USER_DELETED"))) {

            mav.addObject("PARTNER_USER_DELETED", "msg.partner.user.delete");
        }
        return mav;
    }

    @RequestMapping("/secure/company/acceptrequesttojoincompany.do")
    public ModelAndView acceptRequestToJoinCompany(HttpServletRequest request,
            @RequestParam(value = "id") Integer offerId) {
        ModelAndView mav = new ModelAndView("redirect:/secure/company/manageusers.do");

        log.debug("Accept request to join company.");

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        companyService.acceptRequestToJoinCompany(loggedInUser, offerId);

        return mav;
    }

    @RequestMapping("/secure/company/rejectrequesttojoincompany.do")
    public ModelAndView rejectRequestToJoinCompany(HttpServletRequest request,
            @RequestParam(value = "id") Integer offerId) {
        ModelAndView mav = new ModelAndView("redirect:/secure/company/manageusers.do");

        log.debug("Reject request to join company.");

        VAPUserDetails loggedInUser = VAPSecurityManager.getAuthenticationToken();

        companyService.rejectRequestToJoinCompany(loggedInUser, offerId);

        return mav;
    }

    @RequestMapping("/secure/company/inviteuserview.do")
    public ModelAndView inviteUserView(HttpServletRequest request,
            boolean retainModel) {

        InvitePartnerUserVO invitePartnerUserVO;
        ModelAndView mav = new ModelAndView("/secure/partnerusermanagement/inviteuser.jsp");

        log.debug("Invite Partner User View");

        if (!retainModel) {
            invitePartnerUserVO = new InvitePartnerUserVO();
            mav.addObject("invitePartnerUserVO", invitePartnerUserVO);
        }
        return mav;
    }

    @RequestMapping("/secure/company/inviteuser.do")
    public ModelAndView inviteUser(HttpServletRequest request,
            @ModelAttribute(value = "invitePartnerUserVO") InvitePartnerUserVO invitePartnerUserVO,
            BindingResult bindingResult) {

        VAPUserDetails loggedInUser;

        ModelAndView mav = new ModelAndView("redirect:/secure/company/manageusers.do");

        log.debug("Invite Partner User");

        try {
            loggedInUser = VAPSecurityManager.getAuthenticationToken();

            invitePartnerUserVO.setCompanyDomain(loggedInUser.getCompanyDomain());

            invitePartnerUserValidator.validate(invitePartnerUserVO, bindingResult);

            if (bindingResult.hasErrors()) {
                mav.addObject("invitePartnerUserVO", invitePartnerUserVO);
                return inviteUserView(request, true);
            }

            String baseUrl = VAPUtils.getFormattedURL(request.getScheme(), request.getServerName(),
                    request.getServerPort(), request.getContextPath(), "/user/invitation.do");
            String invitationURL = baseUrl + "?" + AccountRegistrationController.PARAM_AUTH_CODE + "=";

            companyService.inviteUser(invitePartnerUserVO, invitationURL, loggedInUser);

            mav.addObject("EMAIL_ADDRESS", invitePartnerUserVO.getEmailAddress());
            mav.addObject("PARTNER_USER_INVITATION_SENT", "msg.partner.user.invitation.send");
        } catch (BusinessRuleException bre) {
            bindingResult.reject(bre.getBusinessRule().getMessage(), bre.getBusinessRule().getMessage());
            return inviteUserView(request, true);
        }
        return mav;
    }

    @RequestMapping("/secure/company/edituserview.do")
    public ModelAndView editUserView(HttpServletRequest request,
            @RequestParam("id") Integer userId, boolean retainModel) {

        VAPUserDetails loggedInUser;

        UserInfo editPartnerUserVO;
        ModelAndView mav = new ModelAndView("/secure/partnerusermanagement/edituser.jsp");

        log.debug("Edit Partner User View");

        loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.getId().equals(userId)) {
            log.debug("Logged in partner user: '" + loggedInUser.getUsername()
                    + "' tried to edit itself maliciously, hence redirecting to partner users list view");

            mav.setViewName("redirect:/secure/company/manageusers.do");
            return mav;
        }

        if (!retainModel) {
            try {
                UserInfo userInfo = userService.getPartnerUserById(
                        loggedInUser.getCompanyId(), userId);

                if (RoleUtil.isRoleFound(userInfo.getRoles(),
                        VAPConstants.ROLE_MPOC, loggedInUser)) {

                    mav.setViewName("redirect:/secure/company/manageusers.do");

                    mav.addObject("MPOC_FULL_NAME", userInfo.getFullName());
                    mav.addObject("PARTNER_USER_CANNOT_EDIT_MPOC",
                            "msg.partner.user.cannot.edit.mpoc");
                    return mav;
                }
                editPartnerUserVO = userService.getPartnerUserById(
                        loggedInUser.getCompanyId(), userId);
                mav.addObject("editPartnerUserVO", editPartnerUserVO);

            } catch (BusinessRuleException bre) {

                log.debug(bre.getBusinessRule().getMessage());
                log.debug("Logged in partner user: '"
                        + loggedInUser.getUsername()
                        + "' tried to edit user with id: '" + userId
                        + "' of other company maliciously");

                mav.setViewName("redirect:/secure/company/manageusers.do");
            }
        }
        return mav;
    }

    @RequestMapping("/secure/company/deleteuser.do")
    public ModelAndView deleteUser(HttpServletRequest request,
            @RequestParam("id") Integer userId) {

        VAPUserDetails loggedInUser;

        ModelAndView mav = new ModelAndView("redirect:/secure/company/manageusers.do");

        log.debug("Delete non MPOC Partner User");

        loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.getId().equals(userId)) {
            log.debug("Logged in partner user: '"
                    + loggedInUser.getUsername()
                    + "' tried to delete itself maliciously, hence redirecting to partner users list view");
            return mav;
        }

        try {
            UserInfo userInfo = userService.getPartnerUserById(
                    loggedInUser.getCompanyId(), userId);

            if (RoleUtil.isRoleFound(userInfo.getRoles(),
                    VAPConstants.ROLE_MPOC, loggedInUser)) {

                mav.addObject("MPOC_FULL_NAME", userInfo.getFullName());
                mav.addObject("PARTNER_USER_CANNOT_DELETE_MPOC",
                        "msg.partner.user.cannot.delete.mpoc");
                return mav;
            }
            companyService.deleteUser(userId, loggedInUser);
            mav.addObject("PARTNER_USER_DELETED", "msg.partner.user.delete");

        } catch (BusinessRuleException bre) {
            log.debug("Logged in partner user: '" + loggedInUser.getUsername()
                    + "' tried to delete user with id: '" + userId
                    + "' of other company maliciously");
        }
        return mav;
    }

    @RequestMapping("/secure/company/edituser.do")
    public ModelAndView editUser(HttpServletRequest request,
            @ModelAttribute(value = "editPartnerUserVO") UserInfo editPartnerUserVO,
            BindingResult bindingResult) {

        VAPUserDetails loggedInUser;

        ModelAndView mav = new ModelAndView("redirect:/secure/company/manageusers.do");

        log.debug("Edit Partner User");

        loggedInUser = VAPSecurityManager.getAuthenticationToken();

        if (loggedInUser.getId().equals(editPartnerUserVO.getId())) {
            log.debug("Logged in partner user: '" + loggedInUser.getUsername()
                    + "' tried to edit itself maliciously");
            return mav;
        }

        try {
            UserInfo userInfo = userService.getPartnerUserById(
                    loggedInUser.getCompanyId(), editPartnerUserVO.getId());

            if (RoleUtil.isRoleFound(userInfo.getRoles(),
                    VAPConstants.ROLE_MPOC, loggedInUser)) {
                log.debug("Logged in partner user: '" + loggedInUser.getUsername()
                        + "' tried to edit user with id: '" + editPartnerUserVO.getId()
                        + "' of other company maliciously");
                return mav;
            }
            partnerUserValidator.validate(editPartnerUserVO, bindingResult);
            
            if (bindingResult.hasErrors()) {
                mav.addObject("editPartnerUserVO", editPartnerUserVO);
                return editUserView(request, editPartnerUserVO.getId(), true);
            }
            companyService.editUser(editPartnerUserVO, loggedInUser);

            mav.addObject("PARTNER_USER_UPDATED", "msg.partner.user.update");

        } catch (BusinessRuleException bre) {
            bindingResult.reject(bre.getBusinessRule().getMessage(), bre.getBusinessRule().getMessage());
            return editUserView(request, editPartnerUserVO.getId(), true);
        }
        return mav;
    }

    /**
     * Render accept partner's user invitation view
     *
     * @param request
     * @param retainModel if true, do not set model object
     *
     * @return
     */
    @RequestMapping("/user/acceptinvitationview.do")
    public ModelAndView acceptUserInvitationView(HttpServletRequest request,
            boolean retainModel) {
        ModelAndView mav = new ModelAndView("/usermanagement/user/activateuser.jsp");

        log.debug("Accept Partner's User Invitation View");

        try {
            if (!retainModel) {

                InviteUserVO inviteUserVO = userManagementService.getUserInvite(
                        request.getParameter("activationCode"),
                        ActivationType.OFFER_TO_JOIN_COMPANY.getLabel());

                if (inviteUserVO != null) {
                    AccountRegistration accountRegistration = new AccountRegistration();
                    accountRegistration.setCountry(VAPConstants.DEFAULT_COUNTRY);
                    accountRegistration.setActivationCode(request.getParameter("activationCode"));
                    accountRegistration.setFullName(inviteUserVO.getFullName());
                    accountRegistration.setEmailAddress(inviteUserVO.getEmailAddress());
                    accountRegistration.setCompanyDomain(inviteUserVO.getCompanyDomain());
                    mav.addObject("accountForm", accountRegistration);
                }
            }
        } catch (BusinessRuleException bre) {
            log.debug(bre.getBusinessRule().getMessage());
            mav.setViewName("redirect:/activationerror.do");
        }
        return mav;
    }

    /**
     * Accept the partner's user invitation
     *
     * @param request
     * @param accountRegistration
     * @param bindingResult
     *
     * @return
     */
    @RequestMapping("/user/acceptinvitation.do")
    public ModelAndView acceptUserInvitation(HttpServletRequest request,
            @ModelAttribute(value = "accountForm") AccountRegistration accountRegistration,
            BindingResult bindingResult) {
        
        ModelAndView mav = new ModelAndView("redirect:/secure/login/redirect.do");

        log.debug("Accept Partner's User Invitation");

        try {
            accountRegistrationValidator.validate(accountRegistration, bindingResult);

            if (bindingResult.hasErrors()) {
                mav.addObject("accountForm", accountRegistration);
                return acceptUserInvitationView(request, true);
            }
            accountRegistration.setActivationType(ActivationType.OFFER_TO_JOIN_COMPANY.getLabel());
            userManagementService.activateInviteUser(accountRegistration);
            VAPSecurityManager.doLogin(request, userDetailsService, utilitiesService, accountRegistration.getUserName());

        } catch (BusinessRuleException bre) {

            bindingResult.reject(bre.getBusinessRule().getMessage(), bre.getBusinessRule().getMessage());
            return acceptUserInvitationView(request, true);
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
                || uri.indexOf("edituserview.do") != -1
                || uri.indexOf("edituser.do") != -1) {

            SortedMap<String, String> countryList =
                    applicationPropertiesService.propertiesByType(
                    ApplicationPropertyType.COUNTRIES);

            map.put("countryList", countryList);
        }
        return map;
    }
}
