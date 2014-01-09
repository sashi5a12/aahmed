package com.netpace.device.services.impl;

import com.netpace.device.dao.ApplicationPropertiesDao;
import com.netpace.device.dao.GenericDao;
import com.netpace.device.dao.GenericReadOnlyDao;
import com.netpace.device.dao.UserActivationDao;
import com.netpace.device.dao.UserDao;
import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.SystemRole;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserActivation;
import com.netpace.device.entities.UserRole;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.AccountRegistrationBusinessRule;
import com.netpace.device.enums.ActivationType;
import com.netpace.device.enums.CompanyRegistrationBusinessRule;
import com.netpace.device.enums.EventType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.RegistrationService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.utils.UniqueStringGenerator;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.AccountRegistration;
import com.netpace.notification.services.EventService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private final static Log log = LogFactory.getLog(RegistrationServiceImpl.class);
    @Autowired
    private UserActivationDao userActivationDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EventService eventService;
    @Autowired
    private GenericReadOnlyDao<SystemRole, Integer> systemRoleDao;
    @Autowired
    private GenericDao<UserRole, Integer> userRoleDao;
    @Autowired
    private ApplicationPropertiesDao applicationPropertiesDao;
    @Autowired
    private UtilitiesService utilitiesService;

    @Transactional
    @Override
    public void registerAccount(AccountRegistration accountInfo, String activationURL) throws BusinessRuleException {
        log.info(accountInfo.toString());
        // validation of business rules also need to check in user table will do in future currently its testing
        log.debug("Business validation starts");

        log.debug("check domain in block list");
        if (utilitiesService.isDomainBlocked(accountInfo.getCompanyDomain())) {
            log.debug("Domain: '"
                    + accountInfo.getCompanyDomain() + "' is blocked");
            
            throw new BusinessRuleException(
                    UserValidationBusinessRule.COMPANY_DOMAIN_IS_BLOCKED);
        }
        log.debug("search user by user name in user table");
        User user = userDao.getUserByUserName(accountInfo.getUserName());
        if (user != null) {
            log.debug("UserName found in user table");
            throw new BusinessRuleException(AccountRegistrationBusinessRule.ACCOUNT_USERNAME_EXISTS);
        }
        log.debug("search user by email in user table");
        user = userDao.getUserByEmailAddress(accountInfo.getEmailAddress());
        if (user != null) {
            log.debug("EmailAddress found in user table");
            throw new BusinessRuleException(AccountRegistrationBusinessRule.ACCOUNT_EMAIL_EXISTS);
        }

        log.debug("search user by user name in user temp table");
        UserActivation userActivation = userActivationDao.search(accountInfo.getUserName());
        if (userActivation != null) {
            log.debug("UserName found in user temp table");
            throw new BusinessRuleException(AccountRegistrationBusinessRule.ACCOUNT_USERNAME_EXISTS);
        }

        log.debug("search user by email in user temp table");
        userActivation = userActivationDao.searchByEmailAddress(accountInfo.getEmailAddress());
        if (userActivation != null) {
            log.debug("Email found in user temp table, going to remove record");
            userActivationDao.remove(userActivation.getUserId());
        }

        UserActivation userReg = convert(accountInfo);

        // set unique id to user
        log.debug("generating activation code");
        String activationCode = UniqueStringGenerator.generateUniqueString();
        log.debug("generated activation code is :" + activationCode);
        userReg.setActivationCode(activationCode);
        userReg.setActivationType(ActivationType.REGISTER_PARTNER_USER.getLabel());
        // encoding password before save
        userReg.setPassword(passwordEncoder.encodePassword(userReg.getPassword(), null));

        userReg.populatedAuditFields(userReg.getUserName());
        log.debug("adding user in temp table");
        userActivationDao.add(userReg);
        log.debug("creating account registration notification");

        Map<String, String> params = new HashMap<String, String>();
        String activationLink = activationURL + activationCode;
        params.put(VAPConstants.PLACEHOLDER_ACTIVATION_LINK, activationLink);
        params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, userReg.getCompanyDomain());
        params.put(VAPConstants.PLACEHOLDER_FULL_NAME, userReg.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.USER_ACCOUNT_VALIDATION_EVENT.toString(), params, userReg.getEmailAddress(), null, null, null);

    }

    @Transactional
    @Override
    public AccountRegistration activateRegisteredAccount(String activationCode) throws BusinessRuleException {
        UserActivation userActivation = activateRegisteredAccount(
                activationCode, ActivationType.REGISTER_PARTNER_USER.getLabel());

        AccountRegistration accountInfo = convert(userActivation);

        User user = convertUserActivationToUser(userActivation);
        user.setEnable(true);
        user.populatedAuditFields(user.getUserName());
        userDao.add(user);

        // Assigning ROLE_ACCOUNT
        log.info("Assigning role (ROLE_ACCOUNT) to user " + user.getUserName());
        SystemRole sysRole = systemRoleDao.get(VAPConstants.ROLE_PARTNER_USER_ID);
        UserRole ur = new UserRole();
        ur.setSystemRole(sysRole);
        ur.setUser(user);
        ur.populatedAuditFields(user.getUserName());
        userRoleDao.add(ur);

        userActivationDao.remove(userActivation.getUserId());
        return accountInfo;
    }

    @Override
    @Transactional
    public UserActivation activateRegisteredAccount(
            String activationCode,
            String activationType) throws BusinessRuleException {

        userActivationDao.removeExpiredRecords();

        UserActivation userActivation =
                userActivationDao.searchByActivationCodeAndType(
                activationCode, activationType);

        if (userActivation == null) {
            throw new BusinessRuleException(AccountRegistrationBusinessRule.ACCOUNT_ACTIVATION_CODE_NOT_FOUND);
        }
        return userActivation;
    }

    @Override
    @Transactional
    public void deleteExpiredActivations(Integer noOfDays) {
        userActivationDao.removeExpiredRecords(noOfDays);
    }
    
    private User convertUserActivationToUser(UserActivation userActivation) {
        User user = new User();
        user.setCompanyDomain(userActivation.getCompanyDomain());
        user.setEmailAddress(userActivation.getEmailAddress());
        user.setFullName(userActivation.getFullName());
        user.setMobile(userActivation.getMobile());
        user.setPassword(userActivation.getPassword());
        user.setPhone(userActivation.getPhone());
        user.setUserName(userActivation.getUserName());
//        user.setUserType(UserType.PARTNER);
        user.setAddress(userActivation.getAddress());
        user.setCity(userActivation.getCity());
        user.setState(userActivation.getState());
        user.setCountry(userActivation.getCountry());
        user.setPostalCode(userActivation.getPostalCode());
        return user;
    }

    private UserActivation convert(AccountRegistration accountInfo) {
        UserActivation regUser = new UserActivation();
        regUser.setUserName(accountInfo.getUserName());
        regUser.setCompanyDomain(accountInfo.getCompanyDomain());
        regUser.setEmailAddress(accountInfo.getEmailAddress());
        regUser.setFullName(accountInfo.getFullName());
        regUser.setMobile(accountInfo.getMobilePhoneNumber());
        regUser.setPhone(accountInfo.getPhoneNumber());
        regUser.setPassword(accountInfo.getPassword());
        regUser.setAddress(accountInfo.getAddress());
        regUser.setCity(accountInfo.getCity());
        regUser.setState(accountInfo.getState());
        regUser.setCountry(validateAndReturnCompany(accountInfo.getCountry()));
        regUser.setPostalCode(accountInfo.getZip());
        return regUser;
    }

    private AccountRegistration convert(UserActivation account) {
        AccountRegistration regUser = new AccountRegistration();
        regUser.setUserName(account.getUserName());
        regUser.setCompanyDomain(account.getCompanyDomain());
        regUser.setEmailAddress(account.getEmailAddress());
        regUser.setFullName(account.getFullName());
        regUser.setMobilePhoneNumber(account.getMobile());
        regUser.setPhoneNumber(account.getPhone());
        regUser.setPassword(account.getPassword());
        return regUser;
    }

    private String validateAndReturnCompany(String countryKey) {
        ApplicationProperties countryProperty = null;
        try {
            countryProperty = applicationPropertiesDao.getPropertiesByTypeAndKey(ApplicationPropertyType.COUNTRIES, countryKey);
        } catch (Exception e) {
            log.warn("Invalid country information passed", e);
        }
        if (countryProperty == null) {
            throw new BusinessRuleException(CompanyRegistrationBusinessRule.INVALID_COUNTRY);
        }
        return countryProperty.getValue();
    }
    
}
