package com.netpace.notification.services.impl;

import com.netpace.device.dao.UserDao;
import com.netpace.device.entities.User;
import com.netpace.device.enums.NotificationValidationBusinessRule;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.dao.NotificationDao;
import com.netpace.notification.dao.NotificationOptOutDao;
import com.netpace.notification.entities.NotifAdHocRecipient;
import com.netpace.notification.entities.Notification;
import com.netpace.notification.entities.NotificationOptOut;
import com.netpace.notification.entities.NotificationRole;
import com.netpace.notification.services.NotificationService;
import com.netpace.notification.services.util.ETDConverter;
import com.netpace.notification.vo.NotificationOptOutVO;
import com.netpace.notification.vo.NotificationVO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    private final static Log log = LogFactory.getLog(NotificationServiceImpl.class);
    private final static String EMAIL_SEPERATOR = ",";
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private NotificationOptOutDao notificationOptOutDao;
    @Autowired
    private UserDao userDao;

    /**
     *
     * @param loggedInUser
     * @return
     * @throws BusinessRuleException
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificationVO> getAllNotifications(VAPUserDetails loggedInUser) throws BusinessRuleException {

        List<NotificationVO> notificationsList = new ArrayList<NotificationVO>();

        List<Notification> notifications = notificationDao.getNotifications();

        for (Notification notification : notifications) {
            notificationsList.add(ETDConverter.convert(notification, new NotificationVO()));
        }
        return notificationsList;
    }

    /**
     * get the notification by notification id
     *
     * @param notificationId
     */
    @Override
    @Transactional(readOnly = true)
    public NotificationVO getNotification(Integer notificationId) {
        NotificationVO notificationVO = null;
        Notification notification = null;
        List<Integer> targetSystemRoles = new ArrayList<Integer>();

        // Set Notification
        notification = notificationDao.getNotificationById(notificationId);
        notificationVO = ETDConverter.convert(notification, new NotificationVO());

        // Set Recepients Email addresses
        if (notification.getNotifAdHocRecipients() != null && notification.getNotifAdHocRecipients().size() > 0) {
            StringBuilder adhocEmails = new StringBuilder();

            for (NotifAdHocRecipient notifAdHocRecipient : notification.getNotifAdHocRecipients()) {
                adhocEmails.append(notifAdHocRecipient.getId().getEmailAddress().trim()).append(EMAIL_SEPERATOR);
            }
            notificationVO.setRecipientEmailAddresses(StringUtils.removeEnd(adhocEmails.toString(), EMAIL_SEPERATOR));
        }

        if (notification.getNotificationRoles() != null && notification.getNotificationRoles().size() > 0) {
            for (NotificationRole notificationRole : notification.getNotificationRoles()) {
                targetSystemRoles.add(notificationRole.getSystemRole().getId());
            }
        }
        notificationVO.setTargetSystemRoles(targetSystemRoles);

        return notificationVO;
    }

    /**
     * create new event notification
     *
     * @param notificationVO
     */
    @Override
    @Transactional
    public void createNotification(NotificationVO notificationVO, VAPUserDetails loggedInUser) {
        Notification notification;
        LinkedHashSet<NotificationRole> listNotificationRoles = new LinkedHashSet<NotificationRole>();
        LinkedHashSet<NotifAdHocRecipient> listAdHocRecipients = new LinkedHashSet<NotifAdHocRecipient>();

        // Add Notification
        notification = ETDConverter.convert(notificationVO, new Notification());
        notification.populatedAuditFields(loggedInUser.getUsername());
        notificationDao.add(notification);

        // Add Notification Adhoc Email Addresses
        if (StringUtils.isNotBlank(notificationVO.getRecipientEmailAddresses())) {
            listAdHocRecipients = new LinkedHashSet<NotifAdHocRecipient>(
                    ETDConverter.convert(notificationVO.getRecipientEmailAddresses(), notification));
        }
        notification.setNotifAdHocRecipients(listAdHocRecipients);

        // Add Notification target system roles
        if (notificationVO.getTargetSystemRoles() != null && notificationVO.getTargetSystemRoles().size() > 0) {
            listNotificationRoles = new LinkedHashSet<NotificationRole>(
                    ETDConverter.convert(notificationVO.getTargetSystemRoles(), notification));
        }
        notification.setNotificationRoles(listNotificationRoles);
    }

    /**
     * update existing notification details
     *
     * @param notificationVO
     */
    @Override
    @Transactional
    public void updateNotification(NotificationVO notificationVO, VAPUserDetails loggedInUser) {
        Notification notification = null;
        LinkedHashSet<NotificationRole> listNotificationRoles = new LinkedHashSet<NotificationRole>();
        LinkedHashSet<NotifAdHocRecipient> listAdHocRecipients = new LinkedHashSet<NotifAdHocRecipient>();

        // update notification
        notification = notificationDao.getNotificationById(notificationVO.getId());
        notification = ETDConverter.convert(notificationVO, notification);
        notification.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        notificationDao.update(notification);
        
        notificationDao.deleteNotifAdHocRecipients(notificationVO.getId());
        // Add Notification Adhoc Email Addresses
        if (StringUtils.isNotBlank(notificationVO.getRecipientEmailAddresses())) {
            listAdHocRecipients = new LinkedHashSet<NotifAdHocRecipient>(
                    ETDConverter.convert(notificationVO.getRecipientEmailAddresses(), notification));
        }
        notification.setNotifAdHocRecipients(listAdHocRecipients);

        notificationDao.deleteNotificationRoles(notificationVO.getId());
        // Add Notification target system roles
        if (notificationVO.getTargetSystemRoles() != null && notificationVO.getTargetSystemRoles().size() > 0) {
            listNotificationRoles = new LinkedHashSet<NotificationRole>(
                    ETDConverter.convert(notificationVO.getTargetSystemRoles(), notification));
        }
        notification.setNotificationRoles(listNotificationRoles);
    }

    /**
     * disable the notification by making it in-active
     *
     * @param notificationId
     */
    @Override
    @Transactional
    public void deleteNotification(Integer notificationId, VAPUserDetails loggedInUser) {
        Notification notification = null;

        notification = notificationDao.get(notificationId);
        notification.setActive(false);

        notificationDao.update(notification);
    }

    @Override
    public List<NotificationOptOutVO> getNotificationsByRoleWithOptOutStatus(
            VAPUserDetails loggedInUser) {

        List<NotificationOptOutVO> notificationOptOutVOs =
                new ArrayList<NotificationOptOutVO>();

        List<String> roleNames = getRoleNames(loggedInUser.getRoles());

        List<Object[]> notificationsByroleWithOptOutStatusList =
                notificationDao.getNotificationsByRoleWithOptOutStatus(
                roleNames, loggedInUser.getId());

        for (Object[] notificationsByroleWithOptOutStatus
                : notificationsByroleWithOptOutStatusList) {

            NotificationOptOutVO notificationOptOutVO =
                    new NotificationOptOutVO();

            notificationOptOutVO.setNotificationId((Integer) notificationsByroleWithOptOutStatus[VAPConstants.NOTIFICATION_ID]);
            notificationOptOutVO.setTitle((String) notificationsByroleWithOptOutStatus[VAPConstants.NOTIFICATION_TITLE]);
            notificationOptOutVO.setDescription((String) notificationsByroleWithOptOutStatus[VAPConstants.NOTIFICATION_DESCRIPTION]);
            notificationOptOutVO.setNotificationOptOutId(
                    notificationsByroleWithOptOutStatus[VAPConstants.NOTIFICATION_OPTOUT_STATUS] == null
                    ? null
                    : ((Number) notificationsByroleWithOptOutStatus[VAPConstants.NOTIFICATION_OPTOUT_STATUS]).intValue());

            notificationOptOutVOs.add(notificationOptOutVO);
        }

        return notificationOptOutVOs;
    }

    private List<String> getRoleNames(List<SystemRoleVO> roles) {

        List<String> rolesList = new ArrayList<String>();

        for (SystemRoleVO systemRoleVO : roles) {
            rolesList.add(systemRoleVO.getRoleName());
        }
        return rolesList;
    }

    @Override
    @Transactional
    public void subscribeNotification(
            VAPUserDetails loggedInUser, Integer notificationOptOutId) throws BusinessRuleException {
        notificationOptOutDao.remove(notificationOptOutId);
    }

    @Override
    @Transactional
    public void unsubscribeNotification(
            VAPUserDetails loggedInUser, Integer notificationId) throws BusinessRuleException {
        NotificationOptOut notificationOptOut = new NotificationOptOut();

        Notification notification = notificationDao.getNotificationById(notificationId);

        if (null == notification) {
            throw new BusinessRuleException(NotificationValidationBusinessRule.NOTIFICATION_NOT_EXISTS);
        }

        User user = userDao.getUserById(loggedInUser.getId());

        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.USER_NOT_EXISTS);
        }

        notificationOptOut.setNotification(notification);
        notificationOptOut.setUser(user);

        notificationOptOut.populatedAuditFields(loggedInUser.getUsername());

        notificationOptOutDao.add(notificationOptOut);
    }
}
