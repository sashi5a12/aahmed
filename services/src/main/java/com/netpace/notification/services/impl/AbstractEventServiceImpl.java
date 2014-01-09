/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.notification.services.impl;

import com.netpace.device.dao.UserDao;
import com.netpace.device.entities.SystemRole;
import com.netpace.device.entities.UserRole;
import com.netpace.device.enums.EventType;
import com.netpace.device.utils.VAPConstants;
import com.netpace.notification.entities.NotifAdHocRecipient;
import com.netpace.notification.entities.Notification;
import com.netpace.notification.entities.NotificationRole;
import com.netpace.notification.entities.Placeholder;
import com.netpace.notification.services.EmailQueueService;
import com.netpace.notification.services.EventService;
import com.netpace.notification.vo.EmailMessageVO;
import com.netpace.notification.vo.NotificationVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Wakram
 */
public abstract class AbstractEventServiceImpl implements EventService {

    private final static Log log = LogFactory.getLog(AbstractEventServiceImpl.class);
    @Autowired
    private EmailQueueService emailQueueService;
    @Autowired
    private UserDao userDao;

    protected void notify(Notification notification, String eventName, Set<Placeholder> placeholders, Map<String, String> valuesMap,
            String mailFrom, String toEmailAddresses, List<String> partnerEmails, String emailMPOC, boolean processNotificaitonsEagerly) {
        EmailMessageVO emailMessageVO;
        String emailContent;
        List<String> toAddresses;

        if (notification.getEmailText() != null) {
            emailMessageVO = new EmailMessageVO();
            emailMessageVO.setFromAddress(mailFrom);

            if (EventType.WORKITEM_DELAYED.toString().equals(eventName) && notification.getNotificationRoles() != null) {
                notifyDelayedWorkitems(notification, eventName, placeholders, valuesMap, mailFrom, toEmailAddresses, partnerEmails, emailMPOC, processNotificaitonsEagerly);

            } else {
                if (StringUtils.isNotBlank(toEmailAddresses)) {
                    toAddresses = Arrays.asList(StringUtils.split(toEmailAddresses, ','));
                } else {
                    toAddresses = getActiveEmailsForNotification(notification, valuesMap, partnerEmails, emailMPOC);
                }

                emailMessageVO.setToAddresses(toAddresses);
                emailMessageVO.setSubject(notification.getTitle());

                emailContent = getMergedContent(notification.getEmailText(), placeholders, valuesMap);
                emailMessageVO.setContent(emailContent);

                String emailPlainText = getMergedContent(notification.getEmailPlainText(), placeholders, valuesMap);
                emailMessageVO.setPlainText(emailPlainText);
                
                // Sent the message instantly
                if (processNotificaitonsEagerly) {
                    emailQueueService.queueAndProcessEmail(emailMessageVO);
                } else {
                    emailQueueService.queueEmail(emailMessageVO);
                }
            }
        }
    }

    protected String getMergedContent(String sourceText, Set<Placeholder> placeholders, Map<String, String> valuesMap) {
        String mergedText, find, replace;
        List<String> findList = new ArrayList<String>();
        List<String> replaceList = new ArrayList<String>();

        for (Placeholder placeholder : placeholders) {
            find = placeholder.getDisplayName().trim();
            replace = valuesMap.get(find);

            findList.add("{" + find.toUpperCase() + "}");
            replaceList.add(replace);
        }

        mergedText = StringUtils.replaceEach(sourceText,
                (String[]) findList.toArray(new String[findList.size()]),
                (String[]) replaceList.toArray(new String[replaceList.size()]));

        return mergedText;
    }

    protected List<String> getActiveEmailsForNotification(Notification notification,
            Map<String, String> valuesMap, List<String> partnerEmails, String emailMPOC) {
        List<String> adhocEmails = getAdhocEmailsForNotification(notification);
        List<String> regularEmails = getRegularEmailsForNotification(notification, valuesMap, partnerEmails, emailMPOC);
        List<String> allSendables = (List<String>) merge(adhocEmails, regularEmails);
        return allSendables;
    }

    protected List<String> getAdhocEmailsForNotification(Notification notification) {
        List<NotifAdHocRecipient> adhocList = new ArrayList(notification.getNotifAdHocRecipients());
        List<String> adhocEmailsSet = getEmailList(adhocList);
        return adhocEmailsSet;
    }

    protected List<String> getEmailList(List<NotifAdHocRecipient> list) {
        List<String> emailList = new ArrayList<String>();
        for (NotifAdHocRecipient rec : list) {
            if (!emailList.contains(rec.getId().getEmailAddress())) {
                emailList.add(rec.getId().getEmailAddress().toLowerCase());
            }
        }
        return emailList;
    }

    protected List<String> getRegularEmailsForNotification(Notification notification,
            Map<String, String> valuesMap, List<String> partnerEmails, String emailMPOC) {
        List<String> regularEmails = new ArrayList<String>();
        for (NotificationRole notificationRole : notification.getNotificationRoles()) {
            if (notificationRole.getSystemRole().getTitle().equals(VAPConstants.ROLE_PARTNER_USER)) {
                if(partnerEmails != null){
                    for (String partnerEmailAddress : partnerEmails) {
                        if (!regularEmails.contains(partnerEmailAddress)) {
                            regularEmails.add(partnerEmailAddress);
                        }
                    }
                }
            } else if (notificationRole.getSystemRole().getTitle().equals(VAPConstants.ROLE_MPOC)) {
                if(emailMPOC != null && !regularEmails.contains(emailMPOC)){
                    regularEmails.add(emailMPOC);
                }
            } else {
                for (UserRole userRole : notificationRole.getSystemRole().getUserRoles()) {
                    if (!regularEmails.contains(userRole.getUser().getEmailAddress())) {
                        regularEmails.add(userRole.getUser().getEmailAddress());
                    }
                }
            }
        }
        return regularEmails;
    }

    protected List merge(List list1, List list2) {

        for (Object user : list2) {
            if (!list1.contains(user)) {
                list1.add(user);
            }
        }
        return list1;
    }

    protected void notifyDelayedWorkitems(Notification notification, String eventName, Set<Placeholder> placeholders, Map<String, String> valuesMap,
            String mailFrom, String toEmailAddresses, List<String> partnerEmails, String emailMPOC, boolean processNotificaitonsEagerly) {
        String emailContent, emailPlainText;
        List<String> toAddresses;

        valuesMap.put(VAPConstants.PLACEHOLDER_WORKITEMS_LIST, "");
        for (NotificationRole notificationRole : notification.getNotificationRoles()) {
            
            log.debug("delayed workitem notification configured for role ["+notificationRole.getSystemRole().getTitle()+"]");
            
            if (notificationRole.getSystemRole().getTitle().equals(VAPConstants.ROLE_PARTNER_USER)) {
                
                for (Map.Entry<String, String> entry : valuesMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if (key.contains(VAPConstants.PLACEHOLDER_WORKITEMS_LIST + VAPConstants.ROLE_PARTNER_USER)) {
                        String companyId = key.replaceAll(VAPConstants.PLACEHOLDER_WORKITEMS_LIST + VAPConstants.ROLE_PARTNER_USER, "");
                        toAddresses = userDao.getUserEmailsByCompanyId(Integer.valueOf(companyId));
                        valuesMap.put(VAPConstants.PLACEHOLDER_WORKITEMS_LIST, value);
                        emailContent = getMergedContent(notification.getEmailText(), placeholders, valuesMap);
                        emailPlainText = notification.getEmailPlainText();
                        sendEmail(mailFrom, toAddresses, notification.getTitle(), emailContent, emailPlainText, processNotificaitonsEagerly);
                    }
                }

            } else if (valuesMap != null && valuesMap.get(VAPConstants.PLACEHOLDER_WORKITEMS_LIST + notificationRole.getSystemRole().getTitle()) != null) {
                toAddresses = getSystemRoleEmails(notificationRole.getSystemRole());
                valuesMap.put(VAPConstants.PLACEHOLDER_WORKITEMS_LIST, valuesMap.get(VAPConstants.PLACEHOLDER_WORKITEMS_LIST + notificationRole.getSystemRole().getTitle()));
                emailContent = getMergedContent(notification.getEmailText(), placeholders, valuesMap);
                emailPlainText = notification.getEmailPlainText();
                sendEmail(mailFrom, toAddresses, notification.getTitle(), emailContent, emailPlainText, processNotificaitonsEagerly);
            }

            break;
        }
    }

    public void sendEmail(String mailFrom, List<String> toAddresses, String title, String emailContent, String emailPlainText, boolean processNotificaitonsEagerly) {
        EmailMessageVO emailMessageVO = new EmailMessageVO();

        emailMessageVO.setFromAddress(mailFrom);
        emailMessageVO.setToAddresses(toAddresses);
        emailMessageVO.setSubject(title);
        emailMessageVO.setContent(emailContent);
        emailMessageVO.setPlainText(emailPlainText);

        if (processNotificaitonsEagerly) {
            // Sent the email
            emailQueueService.queueAndProcessEmail(emailMessageVO);
        } else {
            // Queue the email
            emailQueueService.queueEmail(emailMessageVO);
        }
    }

    protected List<String> getSystemRoleEmails(SystemRole systemRole) {
        List<String> systemRoleEmails = new ArrayList<String>();
        for (UserRole userRole : systemRole.getUserRoles()) {
            if (!systemRoleEmails.contains(userRole.getUser().getEmailAddress())) {
                systemRoleEmails.add(userRole.getUser().getEmailAddress());
            }
        }
        return systemRoleEmails;
    }
}
