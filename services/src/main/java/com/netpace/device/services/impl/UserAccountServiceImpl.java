/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services.impl;

import com.netpace.device.dao.UserDao;
import com.netpace.device.entities.User;
import com.netpace.device.enums.EventType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.UserAccountService;
import com.netpace.device.utils.UniqueStringGenerator;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.UserInfoResend;
import com.netpace.notification.dao.PlaceholderDao;
import com.netpace.notification.services.EventService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hamza Ghayas
 */
@Service(value = UserAccountService.name)
public class UserAccountServiceImpl implements UserAccountService{
    @Autowired
    private EventService eventService;

    @Autowired
    private UserDao userDao;
    @Autowired
    PlaceholderDao placeholderDao;
    
     private static final Log log = LogFactory.getLog(UserAccountServiceImpl.class);
    
    @Override
    @Transactional
    public void forgotUsername(UserInfoResend userInfoResend) {
        User user;
        Map<String, String> params;

        log.info("process forgot username: emailAddress["+userInfoResend.getEmailAddress()+"]");
        user = userDao.getUserByEmailAddress(userInfoResend.getEmailAddress());
        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.USER_NOT_EXISTS);
        }

        // Raise Forgot Username event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_USERNAME, user.getUserName());
        params.put(VAPConstants.PLACEHOLDER_FULL_NAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.FORGOT_USERNAME_EVENT.toString(), params, user.getEmailAddress(), null, null, null);

    }
    
    @Override
    @Transactional
    public void forgotPassword(UserInfoResend userInfoResend, String resetPasswordURL) {
        User user;
        String resetPasswordToken;
        Map<String, String> params;
        
        log.info("process forgot password: username["+userInfoResend.getUserName()+"]");
        user = userDao.getUserByUserName(userInfoResend.getUserName());
        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.USER_NOT_EXISTS);
        }

        resetPasswordToken = UniqueStringGenerator.generateUniqueString();
        user.setResetPasswordToken(resetPasswordToken);
        userDao.update(user);
        
        // Raise Reset Password event
        params = new HashMap<String, String>();
        String resetPasswordLink = resetPasswordURL + resetPasswordToken;
        params.put(VAPConstants.PLACEHOLDER_RESET_LINK, resetPasswordLink);
        params.put(VAPConstants.PLACEHOLDER_FULL_NAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.FORGOT_PASSWORD_EVENT.toString(), params, user.getEmailAddress(), null, null, null);
    }
    
    @Override
    @Transactional
    public void resetPassword(String password,String resetPasswordToken ){
        Map<String, String> params;
        User user =null;
        user = userDao.getUserByPRToken(resetPasswordToken);

        if(user != null){
            user.setPassword(password);
            userDao.update(user);
            
            // Raise Forgot Password updated event
            params = new HashMap<String, String>();
            params.put(VAPConstants.PLACEHOLDER_FULL_NAME, user.getFullName());
            params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
            eventService.raiseEvent(EventType.FORGOT_PASSWORD_UPDATED.toString(), params, user.getEmailAddress(), null, null, null);
        }
    }
}
