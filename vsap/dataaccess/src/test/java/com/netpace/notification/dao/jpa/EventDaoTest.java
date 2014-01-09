package com.netpace.notification.dao.jpa;

import com.netpace.commons.AbstractDaoTest;
import com.netpace.notification.dao.EventDao;
import com.netpace.notification.entities.Event;
import com.netpace.notification.entities.Notification;
import com.netpace.notification.entities.NotificationRole;
import java.util.List;
import javax.annotation.Resource;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EventDaoTest extends AbstractDaoTest{
    
    @Resource(name=EventDao.name)
    EventDao eventDao;
    
    public EventDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAll() {
        //testGetEventByName("PARTNER_DEVICE_MARKETING_PENDING");
        
        /*
        testGetEventById(10);
        testGetEventByName("ACCOUNT_REGISTRATION_EVENT");
        testGetAllEvents();
        */
    }

    /**
     * Test of getEventByName method, of class EventDaoJpaImpl.
     */
    public void testGetEventByName(String eventName) {
        System.out.println("getEventByName");

        Event event = eventDao.getEventByName(eventName);
        System.out.println(event.getEventName());
        System.out.println(event.getPlaceholders());
        for(Notification notification: event.getNotifications()){
            for(NotificationRole notificationRole: notification.getNotificationRoles()){
                System.out.println(notificationRole.getSystemRole().getDisplayTitle());
            }
        }
        System.out.println("---");
        assertNotNull(event);
    }

    /**
     * Test of getEventById method, of class EventDaoJpaImpl.
     */
    public void testGetEventById(Integer eventId) {
        System.out.println("getEventById");

        Event result = eventDao.getEventById(eventId);
        assertNotNull(result);
    }

    /**
     * Test of getAllEvents method, of class EventDaoJpaImpl.
     */
    public void testGetAllEvents() {
        System.out.println("getAllEvents");

        List<Event> result = eventDao.getAllEvents();
        assertNotNull(result);
    }

}
