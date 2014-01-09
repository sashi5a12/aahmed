package com.netpace.notification.dao.jpa;

import com.netpace.commons.AbstractDaoTest;
import com.netpace.notification.dao.NotificationDao;
import com.netpace.notification.entities.Notification;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.junit.Assert.*;
import org.junit.Test;

public class NotificationDaoTest extends AbstractDaoTest{
    
    private final static Log log = LogFactory.getLog(NotificationDaoTest.class);
    
    @Resource(name=NotificationDao.name)
    NotificationDao notificationDao;
    
    /**
     * Test of getNotifications method, of class NotificationDaoJpaImpl.
     */
    @Test
    public void testAll() {
        //testGetNotifications();
        //testGetNotificationById(1);
        testgetNotificationsByRoleWithOptOutStatus();
    }

    public void testGetNotifications() {
        System.out.println("getNotifications test");
        List<Notification> result = notificationDao.getNotifications();
        
        assertNotNull(result);
    }
    
    public void testGetNotificationById(Integer notificationId) {
        System.out.println("getNotificationById test");

        Notification result = notificationDao.getNotificationById(notificationId);
        assertNotNull(result);
    }
    
    public void testgetNotificationsByRoleWithOptOutStatus() {
        System.out.println("getNotificationsByRoleWithOptOutStatus test");
        
        List<String> userRoles = new ArrayList<String>();
        userRoles.add("ROLE_PARTNER_USER");
        userRoles.add("ROLE_MPOC");
        Integer userId=15;
        
        List<Object[]> notifications = notificationDao.getNotificationsByRoleWithOptOutStatus(userRoles, userId);
        
        for ( Object[] obj : notifications ) {
            log.debug("notification_id : " + (Integer)obj[0]);
            log.debug("notification_title : " + (String)obj[1]);
            log.debug("notification_description : " + (String)obj[2]);
            String optOutStatus = "Is opt out";
            if (obj[3] == null) {
                optOutStatus = "Is not opt out";
            }
            log.debug("Opt Out Status : " + optOutStatus);
        }
        
        
        assertNotNull(notifications);
    }
    
}
