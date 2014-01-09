package com.netpace.device.services.impl;

import com.netpace.device.annotation.utils.ETDConverter;
import com.netpace.device.dao.CompanyDao;
import com.netpace.device.dao.JoinCompanyRequestDao;
import com.netpace.device.dao.PaggedResult;
import com.netpace.device.dao.UserActivationDao;
import com.netpace.device.dao.UserDao;
import com.netpace.device.dao.UserRoleDao;
import com.netpace.device.entities.Company;
import com.netpace.device.entities.JoinCompanyRequest;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserActivation;
import com.netpace.device.entities.UserRole;
import com.netpace.device.entities.enums.JoinCompanyStatus;
import com.netpace.device.entities.sort.UserSort;
import com.netpace.device.enums.AccountRegistrationBusinessRule;
import com.netpace.device.enums.EventType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.exceptions.VAPAccessException;
import com.netpace.device.services.UserService;
import com.netpace.device.utils.Page;
import com.netpace.device.utils.PagingOptions;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.ChangePasswordVO;
import com.netpace.device.vo.ChangeUserNameVO;
import com.netpace.device.vo.UserAccessInfo;
import com.netpace.device.vo.UserInfo;
import com.netpace.device.vo.UserInfoResend;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.services.EventService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.NoResultException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Log log = LogFactory.getLog(UserServiceImpl.class);
    @Resource(name = UserDao.name)
    private UserDao userDao;
    @Resource(name = UserActivationDao.name)
    private UserActivationDao userActivationDao;
    @Autowired
    private JoinCompanyRequestDao joinCompanyDao;
    @Resource(name = UserRoleDao.name)
    private UserRoleDao userRoleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EventService eventService;
    @Autowired
    private CompanyDao companyDao;

    @Override
    public UserInfo findUserByName(String userName) {
        User user = userDao.getUserByUserName(userName);
        if (user != null) {
            return ETDConverter.convert(user);
        } else {
            throw new UsernameNotFoundException(userName + " not found.");
        }
    }

    @Override
    @Transactional
    public void releaseAccessToken(VAPUserDetails loggedInUser) {
        if (loggedInUser != null) {
            User user = userDao.getUserByUserName(loggedInUser.getUsername());
            if (user != null) {
                user.setToken(null);
                return;
            }
        }
        throw new VAPAccessException("Invalid AccessToken");
    }

    @Override
    @Transactional
    public UserInfo findUserByEmailAddress(String emailAddress) {
        User user = userDao.getUserByEmailAddress(emailAddress);

        if (user != null) {
            return ETDConverter.convert(user);
        }

        return null;
    }

    public boolean isJoinRequestPending(User user) {
        try {
            JoinCompanyRequest jcr = joinCompanyDao.getRequestByOfferedUserId(user.getId());
            return jcr.getStatus().equals(JoinCompanyStatus.PENDING);
        } catch (NoResultException nre) {
            return false;
        }
    }

    @Override
    public UserInfoResend passwordResetTokenExists(String passResetToken) {
        User user = userDao.getUserByPRToken(passResetToken);
        UserInfoResend userInfoResend = null;
        if (user != null) {
            userInfoResend = ETDConverter.convert(user, new UserInfoResend());
        }
        return userInfoResend;
    }

    @Override
    public UserAccessInfo getAccessInfo(VAPUserDetails loggedInUser) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional
    @Override
    public Page<UserInfo> getList(VAPUserDetails loggedInUser, PagingOptions pagingOptions) {
        UserSort sort = null;
        try {
            sort = UserSort.valueOf(pagingOptions.getSortOrder());
        } catch (IllegalArgumentException iae) {
            log.debug("illegal sort option " + iae);
        }
        log.info("Sorting is " + sort);
        PaggedResult<User> paggedResult = userDao.getUsersList(sort, pagingOptions.isAscending(), pagingOptions.getFirstResult(),
                pagingOptions.getPageSize());
        Page<UserInfo> page = new Page<UserInfo>();
        page.setTotalRecs(paggedResult.getRecords());
        page.setCurrentPage((int) Math.ceil((pagingOptions.getFirstResult() / (pagingOptions.getPageSize() * 1.0))));
        page.setAscending(pagingOptions.isAscending());
        page.setPageSize(pagingOptions.getPageSize());
        page.setTotalPages((int) Math.ceil(page.getTotalRecs() / (page.getPageSize() * 1.0)));
        page.setSortOrder(pagingOptions.getSortOrder());
        if (paggedResult.getList() != null) {
            for (User user : paggedResult.getList()) {
                page.add(ETDConverter.convert(user));
            }
        }
        return page;
    }

    @Override
    @Transactional
    public UserInfo getMyProfile(VAPUserDetails loggedInUser) {
        User user = userDao.getUserByUserName(loggedInUser.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException(loggedInUser.getUsername() + " not found.");
        }
        return ETDConverter.convert(user);
    }

    @Override
    @Transactional
    public void updateMyProfile(UserInfo profile, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User user = null;
        Map<String, String> params;
        user = userDao.getUserById(loggedInUser.getId());

        // Preserve few unchangeable field values 
        String emailAddress = user.getEmailAddress();
        String companyDomain = user.getCompanyDomain();

        user = ETDConverter.convertForUpdate(profile, user);

        /**
         * TODO: Right now email and company is unchangeable. There will be a
         * different process for changing. Remove the following code once the
         * real process is implemented.
         */
        user.setEmailAddress(emailAddress);
        user.setCompanyDomain(companyDomain);

        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);

        // Raise notification event if the user is partner user.
        if (loggedInUser.isPartner()) {

            Company company = companyDao.getCompanyById(loggedInUser.getCompanyId());
            String companyTitle = "";

            if (company != null && company.getName() != null && company.getName().trim().length() > 0) {
                companyTitle = company.getName();
            }
            // Raise partner PARTNER_UPDATED Event
            params = new HashMap<String, String>();

            params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, companyTitle);
            params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, user.getCompanyDomain());
            params.put(VAPConstants.PLACEHOLDER_UPDATED_BY, user.getUserName());
            params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
            eventService.raiseEvent(EventType.PARTNER_UPDATED.toString(), params, user.getEmailAddress(), null, null, loggedInUser.getUsername());
        }
    }

    @Override
    @Transactional
    public void updateUserName(ChangeUserNameVO changeUserNameVO, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User existingUser = null, user = null;
        Map<String, String> params;
        log.debug("search user by user name in user temp table");
        UserActivation userActivation = userActivationDao.search(changeUserNameVO.getUserName());
        if (userActivation != null) {
            log.debug("UserName found in user temp table");
            throw new BusinessRuleException(AccountRegistrationBusinessRule.ACCOUNT_USERNAME_EXISTS);
        }

        user = userDao.getUserById(loggedInUser.getId());

        if (user.getUserName().equals(changeUserNameVO.getUserName())) {
            throw new BusinessRuleException(UserValidationBusinessRule.COMPANY_USER_IS_SAME);
        }

        String encodedCurrentPassword = passwordEncoder.encodePassword(changeUserNameVO.getCurrentPassword(), null);

        if (!encodedCurrentPassword.equals(user.getPassword())) {
            throw new BusinessRuleException(UserValidationBusinessRule.CHANGE_USERNAME_INVALID_CURRENT_PASSWORD);
        }

        existingUser = userDao.getUserByUserName(changeUserNameVO.getUserName());

        if (existingUser != null) {
            log.debug("user with userName exists: userName[" + changeUserNameVO.getUserName() + "]");
            existingUser = null;
            throw new BusinessRuleException(UserValidationBusinessRule.USERNAME_USER_EXISTS);
        }

        user.setUserName(changeUserNameVO.getUserName());

        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);

        //Update the username of logged in user
        loggedInUser.setUsername(user.getUserName());
        
        // Raise admin/partner/user USERNAME_UPDATED Event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_USERNAME, loggedInUser.getUsername());
        params.put(VAPConstants.PLACEHOLDER_FULL_NAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.USERNAME_UPDATED.toString(), params, loggedInUser.getEmailAddress(), null, null, loggedInUser.getUsername());
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordVO changePasswordVO, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User user = null;
        Map<String, String> params;

        user = userDao.getUserById(loggedInUser.getId());

        String encodedCurrentPassword = passwordEncoder.encodePassword(changePasswordVO.getCurrentPassword(), null);

        if (!encodedCurrentPassword.equals(user.getPassword())) {
            throw new BusinessRuleException(UserValidationBusinessRule.CHANGE_PASWORD_INVALID_CURRENT_PASSWORD);
        }
        String encodedCreatePassword = passwordEncoder.encodePassword(changePasswordVO.getCreatePassword(), null);
        user.setPassword(encodedCreatePassword);

        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);

        // Raise admin/partner/user PASSWORD_UPDATED Event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_FULL_NAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PASSWORD_UPDATED.toString(), params, loggedInUser.getEmailAddress(), null, null, loggedInUser.getUsername());
    }

    @Override
    public String[] getUserRoles(Integer userId) {
        String[] userRoleStrArr = null;

        User user = userDao.get(userId);
        Set<UserRole> userRoles = user.getUserRoles();

        if (userRoles != null && userRoles.size() > 0) {
            log.info("Num of assigned roles to user [" + user.getUserName() + "] = " + userRoles.size());
            userRoleStrArr = new String[userRoles.size()];
            int index = 0;
            for (UserRole userRole : userRoles) {
                String role = userRole.getSystemRole().getTitle();
                log.info("ROLE[" + index + "] = " + role);
                userRoleStrArr[index++] = role;
            }
        }

        return userRoleStrArr;
    }

    @Override
    @Transactional
    public List<UserRole> getUserRolesById(Integer userId) {
        List<UserRole> userRole = userRoleDao.getUserRoleByUserId(userId);
        return userRole;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo getUserById(Integer userId) {
        User user = userDao.getUserById(userId);

        UserInfo userInfo = null;

        if (user != null) {
            userInfo = ETDConverter.convert(user);
        }

        return userInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo getPartnerUserById(Integer companyId, Integer userId) throws BusinessRuleException {
        User user = userDao.getPartnerUserByIdForEdit(companyId, userId);

        if (user == null) {
            /*
             * Note: Adding functionality to facilitate the partner to view the 
             * profile of user who requested to join the company as per UAT 
             * requirement.
             */

            /* Functionality start */
            Integer joinUserCompanyId = joinCompanyDao.getCompanyIdByOfferedUserId(userId);

            if (joinUserCompanyId != null && joinUserCompanyId.equals(companyId)) {
                user = userDao.getUserById(userId);
            }
            /* Functionality end */
        }

        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.COMPANY_USER_NOT_EXISTS);
        }

        UserInfo userInfo = ETDConverter.convert(user);
        userInfo.setRoles(ETDConverter.convert(user.getUserRoles()));

        return userInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo getAdminUserById(Integer userId) throws BusinessRuleException {
        User user = userDao.getAdminUserByIdForEdit(userId);

        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.ADMIN_USER_NOT_EXISTS);
        }
        return ETDConverter.convert(user, new UserInfo());
    }
}
