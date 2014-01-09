package com.netpace.device.services.impl;

import com.netpace.device.annotation.utils.ETDConverter;
import com.netpace.device.dao.CompanyDao;
import com.netpace.device.dao.GenericReadOnlyDao;
import com.netpace.device.dao.PaggedResult;
import com.netpace.device.dao.UserActivationDao;
import com.netpace.device.dao.UserDao;
import com.netpace.device.dao.UserRoleDao;
import com.netpace.device.entities.*;
import com.netpace.device.enums.ActivationType;
import com.netpace.device.enums.EventType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.UserManagementService;
import com.netpace.device.utils.UniqueStringGenerator;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.AccountRegistration;
import com.netpace.device.vo.ActivateAdminVO;
import com.netpace.device.vo.InviteUserVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.device.vo.UserInfo;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.services.EventService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

    private final static Log log = LogFactory.getLog(UserManagementServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserActivationDao userActivationDao;
    @Autowired
    private EventService eventService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GenericReadOnlyDao<SystemRole, Integer> systemRoleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    @Transactional(readOnly = true)
    public List<UserInfo> getUserslist(UserInfo userInfo, Integer start, Integer limit) {
        List<UserInfo> list = new ArrayList<UserInfo>();
        List<User> users;

        users = userDao.getUsersList(start, limit);
        for (User user : users) {
            list.add(ETDConverter.convert(user));
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getUsersCount(UserInfo userInfo) {
        Long count;

        count = userDao.getUsersCount();

        return count;
    }

    /**
     * Invite new user identified by inviteUserVO
     *
     * @param inviteUserVO
     * @param userInfo
     * @throws BusinessRuleException
     */
    @Transactional
    @Override
    public void inviteUser(InviteUserVO inviteUserVO, String invitationURL, VAPUserDetails loggedInUser) throws BusinessRuleException {
        UserActivation userActivation;
        User existingUser;
        String activationCode;
        Map<String, String> eventParams;

        log.debug("Checking admin user email for duplicate, if any then don't allow invite.");
        
        existingUser = userDao.getUserByEmailAddress(inviteUserVO.getEmailAddress());
        
        if(existingUser != null) {
            log.debug("Duplicate admin user email address " + existingUser.getEmailAddress());
            throw new BusinessRuleException(UserValidationBusinessRule.EMAIL_USER_EXISTS);
        }
        
        log.debug("Removing duplicate invite user record with admin type if any.");
        
        userActivationDao.removeDuplicateInviteUserRecord(
                inviteUserVO.getEmailAddress(), ActivationType.OFFER_TO_JOIN_AS_ADMIN.getLabel());

        log.debug("Entering inviteUser: emailAddress[" + inviteUserVO.getEmailAddress() + "]");

        // populate activation bean
        userActivation = ETDConverter.convert(inviteUserVO, new UserActivation());

        // generating new activation code
        activationCode = UniqueStringGenerator.generateUniqueString();
        log.debug("activation code generated: emailAddress[" + inviteUserVO.getEmailAddress() + "], activationCode[" + activationCode + "]");
        userActivation.setActivationCode(activationCode);

        // set activation type to Admin and populate audit fields
//        userActivation.setActivationType(UserType.ADMIN.toString());
        userActivation.setActivationType(ActivationType.OFFER_TO_JOIN_AS_ADMIN.getLabel());
        userActivation.populatedAuditFields(loggedInUser.getUsername());

        // add user activation
        userActivationDao.add(userActivation);
        log.debug("temp user added");

        // Raise User Registration Event
        eventParams = new HashMap<String, String>();
        String invitationLink = invitationURL + activationCode;
        eventParams.put(VAPConstants.PLACEHOLDER_INVITATION_LINK, invitationLink);
        eventParams.put(VAPConstants.PLACEHOLDER_FULL_NAME, userActivation.getFullName());
        eventParams.put(VAPConstants.PLACEHOLDER_INVITEE_FULLNAME, loggedInUser.getFullName());
        eventParams.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.VERIZON_USER_INVITED_EVENT.toString(), eventParams, userActivation.getEmailAddress(), null, null, loggedInUser.getUsername());
    }

    /**
     * get user invitation by activation code
     *
     * @param activationCode
     * @return
     * @throws BusinessRuleException
     */
    @Transactional(readOnly = true)
    @Override
    public InviteUserVO getUserInvite(String activationCode) throws BusinessRuleException {
        UserActivation existingActivation;
        InviteUserVO inviteUserVO;

        log.debug("Getting User Invite: activationCode[" + activationCode + "]");

        userActivationDao.removeExpiredRecords();
        log.debug("Expired (5 days old) temp user(s) removed");

        existingActivation = userActivationDao.searchByActivationCode(activationCode);
        if (existingActivation == null) {
            log.debug("User with activationCode not found: activationCode[" + activationCode + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.ACTIVATIONCODE_TEMP_USER_NOT_EXISTS);
        }

        if (StringUtils.contains(existingActivation.getUserRoles(), VAPConstants.ROLE_PARTNER_USER)) {
            throw new BusinessRuleException(UserValidationBusinessRule.ACTIVATION_TYPE_ACCOUNT);
        }

        inviteUserVO = ETDConverter.convert(existingActivation, new InviteUserVO());

        return inviteUserVO;
    }

    /**
     * get user invitation by activation code and activation type
     *
     * @param activationCode
     * @param activationType
     * @return
     * @throws BusinessRuleException
     */
    @Transactional(readOnly = true)
    @Override
    public InviteUserVO getUserInvite(
            String activationCode, String activationType) throws BusinessRuleException {
        UserActivation existingActivation;
        InviteUserVO inviteUserVO;

        log.debug("Getting User Invite: activationCode[" + activationCode + "]");

        userActivationDao.removeExpiredRecords();
        log.debug("Expired (5 days old) temp user(s) removed");

        existingActivation = userActivationDao.searchByActivationCodeAndType(activationCode, activationType);
        if (existingActivation == null) {
            log.debug("User with activationCode not found: activationCode[" + activationCode + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.ACTIVATIONCODE_TEMP_USER_NOT_EXISTS);
        }

        inviteUserVO = ETDConverter.convert(existingActivation, new InviteUserVO());

        return inviteUserVO;
    }

    /**
     * activate new user with accountRegistration details
     *
     * @param accountRegistration
     * @throws BusinessRuleException
     */
    @Transactional
    @Override
    public void activateInviteUser(AccountRegistration accountRegistration) throws BusinessRuleException {
        UserActivation existingActivation;
        User existingUser, freshUser;
        String encodedPassword;

        log.debug("Activating User: activationCode[" + accountRegistration.getActivationCode() + "]");

        userActivationDao.removeExpiredRecords();
        log.debug("Expired (5 days old) temp user(s) removed");

        existingActivation = userActivationDao.searchByActivationCodeAndType(
                accountRegistration.getActivationCode(),
                ActivationType.OFFER_TO_JOIN_COMPANY.getLabel());
        if (existingActivation == null) {
            log.debug("User with activationCode not found: activationCode[" + accountRegistration.getActivationCode() + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.ACTIVATIONCODE_TEMP_USER_NOT_EXISTS);
        }

        String userName = accountRegistration.getUserName();
        existingUser = userDao.getUserByUserName(userName);
        if (existingUser != null) {
            log.debug("user with userName exists: userName[" + userName + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.USERNAME_USER_EXISTS);
        }

        existingUser = userDao.getUserByEmailAddress(accountRegistration.getEmailAddress());
        if (existingUser != null) {
            log.debug("user with emailAddress exists: emailAddress[" + accountRegistration.getEmailAddress() + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.EMAIL_USER_EXISTS);
        }

        freshUser = new User();
        freshUser = ETDConverter.convert(accountRegistration, freshUser);
        // for partner user set email address and company domain from existing activation
        freshUser.setEmailAddress(existingActivation.getEmailAddress());
        freshUser.setCompanyDomain(existingActivation.getCompanyDomain());

        encodedPassword = passwordEncoder.encodePassword(accountRegistration.getPassword(), null);

        log.debug("password encoded: emailAddress[" + accountRegistration.getEmailAddress() + "], encodedPassword[" + encodedPassword + "]");

        freshUser.setPassword(encodedPassword);
        freshUser.setCompany(companyDao.getCompanyByDomainName(freshUser.getCompanyDomain()));
        freshUser.populatedAuditFields(accountRegistration.getUserName());
        freshUser.setEnable(true);
        
        userDao.add(freshUser);

        freshUser.setUserRoles(createUserRoles(freshUser, VAPConstants.ROLE_PARTNER_USER));

        // Remove the temp user
        userActivationDao.remove(existingActivation.getUserId());
    }

    /**
     * activate new user with activateAdminVO details
     *
     * @param activateAdminVO
     * @throws BusinessRuleException
     */
    @Transactional
    @Override
    public void activateInviteAdmin(ActivateAdminVO activateAdminVO) throws BusinessRuleException {
        UserActivation existingActivation;
        User existingUser, freshUser;
        String encodedPassword;

        log.debug("Activating User: activationCode[" + activateAdminVO.getActivationCode() + "]");

        userActivationDao.removeExpiredRecords();
        log.debug("Expired (5 days old) temp user(s) removed");

        existingActivation = userActivationDao.searchByActivationCodeAndType(
                activateAdminVO.getActivationCode(),
                activateAdminVO.getActivationType());
        if (existingActivation == null) {
            log.debug("User with activationCode not found: activationCode[" + activateAdminVO.getActivationCode() + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.ACTIVATIONCODE_TEMP_USER_NOT_EXISTS);
        }

        String userName = activateAdminVO.getUserName();
        existingUser = userDao.getUserByUserName(userName);
        if (existingUser != null) {
            log.debug("user with userName exists: userName[" + userName + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.USERNAME_USER_EXISTS);
        }

        existingUser = userDao.getUserByEmailAddress(activateAdminVO.getEmailAddress());
        if (existingUser != null) {
            log.debug("user with emailAddress exists: emailAddress[" + activateAdminVO.getEmailAddress() + "]");
            throw new BusinessRuleException(UserValidationBusinessRule.EMAIL_USER_EXISTS);
        }

        freshUser = new User();
        freshUser = ETDConverter.convert(activateAdminVO, freshUser);
        // for admin set email address from existing activation
        freshUser.setEmailAddress(existingActivation.getEmailAddress());

        encodedPassword = passwordEncoder.encodePassword(activateAdminVO.getPassword(), null);
        log.debug("password encoded: emailAddress[" + activateAdminVO.getEmailAddress() + "], encodedPassword[" + encodedPassword + "]");
        freshUser.setPassword(encodedPassword);

        // Set company to null as of admin user
        freshUser.setCompany(null);

        freshUser.populatedAuditFields(activateAdminVO.getUserName());
        freshUser.setEnable(true);
        userDao.add(freshUser);

        List<String> rolesList = Arrays.asList(StringUtils.split(existingActivation.getUserRoles(), ","));

        freshUser.setUserRoles(createUserRoles(freshUser, rolesList));

        // Remove the temp user
        userActivationDao.remove(existingActivation.getUserId());
    }

    private Set<UserRole> createUserRoles(User user, Object rolesList) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("rolesList", rolesList);
        List<SystemRole> systemRoles = systemRoleDao.findByNamedQuery("findSystemRoles", queryParams);

        Set<UserRole> userRoles = new HashSet<UserRole>();
        for (SystemRole systemRole : systemRoles) {
            UserRole userRole = new UserRole();
            userRole.setSystemRole(systemRole);
            userRole.setUser(user);
            userRole.populatedAuditFields(user.getUserName());
            userRoles.add(userRole);
        }
        return userRoles;
    }

    /**
     * Soft delete (in-active) existing user with email address and activate new
     * user
     *
     * @param activateAdminVO
     * @throws BusinessRuleException
     */
    @Transactional
    @Override
    public void reActivateAdmin(ActivateAdminVO activateAdminVO) throws BusinessRuleException {
        User existingUser;
        Company company;

        // soft delete existing user
        existingUser = userDao.getUserByEmailAddress(activateAdminVO.getEmailAddress());
        if (existingUser != null) {
            log.debug("user with emailAddress exists: emailAddress[" + activateAdminVO.getEmailAddress() + "]");
            existingUser.setActive(false);
            userDao.update(existingUser);
        }

        if (existingUser != null && existingUser.getCompany() != null) {
            company = existingUser.getCompany();
            company.setActive(false);
            companyDao.update(company);
        }

        // activate new user
        activateInviteAdmin(activateAdminVO);

    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<UserInfo> getPagedList(PageModel<UserInfo> pageModel) {
        PaggedResult<User> paggedResult;
        List<UserInfo> records = new ArrayList<UserInfo>();
        UserInfo userInfoVO;

        // Get PaggedResult
        paggedResult = userDao.getPaggedUsers(pageModel);

        // Populate PageModel
        pageModel.setTotalRecords(paggedResult.getRecords());
        for (User user : paggedResult.getList()) {
            userInfoVO = ETDConverter.convert(user, new UserInfo());
            records.add(userInfoVO);
        }
        pageModel.setRecords(records);

        return pageModel;
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<UserInfo> getUsersList(UserInfo loggedUser, PageModel<UserInfo> pageModel) {
        List<User> usersList;
        List<UserInfo> records = new ArrayList<UserInfo>();
        UserInfo userInfoVO;

        usersList = userDao.getUsersList(pageModel);

        // Populate PageModel
        pageModel.setTotalRecords(usersList.size());
        for (User user : usersList) {
            userInfoVO = ETDConverter.convert(user, new UserInfo());
            records.add(userInfoVO);
        }
        pageModel.setRecords(records);
        return pageModel;
    }

    @Override
    @Transactional
    public void editAdminUser(UserInfo userInfo, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User user = userDao.getAdminUserByIdForEdit(userInfo.getId());

        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.ADMIN_USER_NOT_EXISTS);
        }

        user = ETDConverter.convertAdminUserForEdit(userInfo, user);

        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);
        
        updateRoles(userInfo, user);
    }

    @Override
    @Transactional
    public void deleteAdminUser(Integer userId, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User user = userDao.getUserById(userId);

        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.ADMIN_USER_NOT_EXISTS);
        }
        user.setActive(false);

        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);
    }
    
    @Transactional
    private void updateRoles(UserInfo userInfo, User user) {
        
        List<String> rolesList = new ArrayList<String>(userInfo.getRoles().size());
        for (SystemRoleVO systemRoleVO : userInfo.getRoles()) {
            rolesList.add(systemRoleVO.getRoleName());
        }
        Set<UserRole> previousUserRoles = user.getUserRoles();
        Set<UserRole> removePreviousUserRoles = new HashSet<UserRole>();
        List<Integer> removeRoles = new ArrayList<Integer>();
        boolean isRoleFound = false;
        for (UserRole userRole : previousUserRoles) {
            isRoleFound = false;
            for (SystemRoleVO systemRoleVO : userInfo.getRoles()) {
                if (userRole.getSystemRole().getTitle().equals(systemRoleVO.getRoleName())) {
                    isRoleFound = true;
                    rolesList.remove(systemRoleVO.getRoleName());
                    break;
                }
            }
            if (!isRoleFound) {
                removeRoles.add(userRole.getId());
                removePreviousUserRoles.add(userRole);
            }
        }
        previousUserRoles.removeAll(removePreviousUserRoles);
        
        for(Integer removeUserRoleId : removeRoles) {
            userRoleDao.remove(removeUserRoleId);
        }
        
        if(!rolesList.isEmpty()) {
            user.setUserRoles(createUserRoles(user, rolesList));
        }
    }
}