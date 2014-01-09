package com.netpace.notification.dao;

import com.netpace.device.dao.GenericDao;
import com.netpace.notification.entities.Notification;
import java.util.List;

public interface NotificationDao extends GenericDao<Notification, Integer> {

    public static final String name = "notificationDao";

    public List<Notification> getNotifications();

    public Notification getNotificationById(Integer notificationId);
    
    public  List<Notification> getNotificationsByEvent(String eventName);
    
    public List<Object[]> getNotificationsByRoleWithOptOutStatus(List<String> userRoles, Integer userId);
    
    public void deleteNotificationRoles(Integer notificationId);
    
    public void deleteNotifAdHocRecipients(Integer notificationId);
    
}
