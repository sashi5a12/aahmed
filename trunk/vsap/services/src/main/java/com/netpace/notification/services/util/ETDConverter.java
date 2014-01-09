package com.netpace.notification.services.util;

import com.netpace.device.entities.SystemRole;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.notification.entities.Event;
import com.netpace.notification.entities.NotifAdHocRecipient;
import com.netpace.notification.entities.NotifAdHocRecipientId;
import com.netpace.notification.entities.Notification;
import com.netpace.notification.entities.NotificationRole;
import com.netpace.notification.entities.NotificationRoleId;
import com.netpace.notification.entities.Placeholder;
import com.netpace.notification.vo.EventVO;
import com.netpace.notification.vo.NotificationVO;
import com.netpace.notification.vo.PlaceholderVO;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ETDConverter {

    private final static String EMAIL_SEPERATOR = ",";

    /**
     * converts a notification entity to a value object
     *
     * @param notification
     * @param notificationVO
     * @return
     */
    public static NotificationVO convert(Notification notification, NotificationVO notificationVO) {

        notificationVO.setId(notification.getId());
        notificationVO.setTitle(notification.getTitle());
        notificationVO.setDescription(notification.getDescription());
        notificationVO.setEmailContent(notification.getEmailText());
        notificationVO.setEnabled(notification.isActive());
        notificationVO.setEventId(notification.getEvent().getId());
        notificationVO.setEmailPlainText(notification.getEmailPlainText());

        return notificationVO;
    }

    /**
     * converts an event entity to value object
     *
     * @param event
     * @param eventVO
     * @return
     */
    public static EventVO convert(Event event, EventVO eventVO) {

        eventVO.setId(event.getId());
        eventVO.setEventName(event.getEventName());
        eventVO.setEventDesc(event.getEventDesc());

        return eventVO;
    }

    /**
     * converts a placeholder entity to a value object
     *
     * @param placeholder
     * @param placeholderVO
     * @return
     */
    public static PlaceholderVO convert(Placeholder placeholder, PlaceholderVO placeholderVO) {

        placeholderVO.setId(placeholder.getId());
        placeholderVO.setDisplayName(placeholder.getDisplayName());

        return placeholderVO;
    }

    /**
     * converts a SystemRole entity to a value object
     *
     * @param placeholder
     * @param placeholderVO
     * @return
     */
    public static SystemRoleVO convert(SystemRole systemRole, SystemRoleVO systemRoleVO) {

        systemRoleVO.setRoleId(systemRole.getId());
        systemRoleVO.setRoleName(systemRole.getTitle());
        systemRoleVO.setDisplayTitle(systemRole.getDisplayTitle());
        systemRoleVO.setHidden(systemRole.isHidden());

        return systemRoleVO;
    }

    /**
     * converts a notification value object to an entity
     *
     * @param notificationVO
     * @param notification
     * @return
     */
    public static Notification convert(NotificationVO notificationVO, Notification notification) {

        notification.setTitle(notificationVO.getTitle());
        notification.setDescription(notificationVO.getDescription());
        notification.setEmailText(notificationVO.getEmailContent());
        notification.setEmailPlainText(notificationVO.getEmailPlainText());

        Event event = new Event();
        event.setId(notificationVO.getEventId());
        notification.setEvent(event);

        return notification;
    }

    /**
     * converts a list role ids into NotificationRole list
     *
     * @param targetSystemRoles
     * @param notification
     * @return
     */
    public static List<NotificationRole> convert(List<Integer> targetSystemRoles, Notification notification) {
        NotificationRole notificationRole;
        NotificationRoleId notificationRoleId;
        SystemRole systemRole;
        List<NotificationRole> listNotificationRoles = new ArrayList<NotificationRole>();

        for (Integer targetSystemRoleId : targetSystemRoles) {
            notificationRole = new NotificationRole();
            notificationRole.setNotification(notification);

            notificationRoleId = new NotificationRoleId();
            notificationRoleId.setNotificationId(notification.getId());
            notificationRoleId.setRoleId(targetSystemRoleId);
            notificationRole.setId(notificationRoleId);

            systemRole = new SystemRole();
            systemRole.setId(targetSystemRoleId);
            notificationRole.setSystemRole(systemRole);

            //notificationRole.populatedAuditFields(userInfo.getUserName());
            listNotificationRoles.add(notificationRole);
        }
        return listNotificationRoles;
    }

    /**
     * converts a comma separated email addresses string to NotifAdHocRecipient
     * list
     *
     * @param recipientEmailAddresses
     * @param notification
     * @return
     */
    public static List<NotifAdHocRecipient> convert(String recipientEmailAddresses, Notification notification) {
        List<NotifAdHocRecipient> listAdHocRecipients = new ArrayList<NotifAdHocRecipient>();
        String[] adhocrecipients;
        NotifAdHocRecipient adHocRecipient;
        NotifAdHocRecipientId adHocRecipientId;

        adhocrecipients = StringUtils.split(recipientEmailAddresses, EMAIL_SEPERATOR);
        for (String strEmailAddress : adhocrecipients) {
            adHocRecipient = new NotifAdHocRecipient();
            adHocRecipient.setNotification(notification);

            adHocRecipientId = new NotifAdHocRecipientId();
            adHocRecipientId.setNotificationId(notification.getId());
            adHocRecipientId.setEmailAddress(strEmailAddress.trim());
            adHocRecipient.setId(adHocRecipientId);

            //adHocRecipient.populatedAuditFields(userInfo.getUserName());
            listAdHocRecipients.add(adHocRecipient);
        }
        return listAdHocRecipients;
    }
}
