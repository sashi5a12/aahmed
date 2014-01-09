package com.netpace.notification.services.impl;

import com.netpace.device.annotation.EnableNotification;
import com.netpace.device.dao.UserDao;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.notification.dao.EventDao;
import com.netpace.notification.entities.Event;
import com.netpace.notification.entities.Notification;
import com.netpace.notification.entities.Placeholder;
import com.netpace.notification.services.util.ETDConverter;
import com.netpace.notification.vo.EventVO;
import com.netpace.notification.vo.PlaceholderVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("eventService")
public class EventServiceImpl extends AbstractEventServiceImpl {

    private final static Log log = LogFactory.getLog(EventServiceImpl.class);
    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;

    /**
     * get list of all events
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventVO> getAllEvents() {
        List<EventVO> list = new ArrayList<EventVO>();
        List<Event> events = null;

        events = eventDao.getAll();
        for (Event event : events) {
            list.add(ETDConverter.convert(event, new EventVO()));
        }

        return list;
    }

    /**
     * 
     * @param eventTitle mandatory parameter for the event to be raised
     * @param propertiesMap mandatory parameter containing event placeholder values
     * @param emailAddresses optional parameter if provided notification email addresses will be bypass
     * @param emailAddressMPOC optional parameter if provided notifications with ROLE_MPOC will use it
     * @param partnerEmails optional parameter if provided notifications with ROLE_PARTNER_USER will use it
     * @param loggedInUserName optional parameter if provided 
     */
    @EnableNotification
    @Async
    @Override
    @Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
    public void raiseEvent(String eventTitle, Map<String, String> propertiesMap, String emailAddresses, List<String> partnerEmails, String emailAddressMPOC, String loggedInUserName) {
        Event event = null;
        String fromAddress;
        boolean processNotificaitonsEagerly = false;
        
        log.info("Raising Event: eventTitle [" + eventTitle + "]");
        try{
            
        event = eventDao.getEventByName(eventTitle);
        
        if (event == null) {
            log.error("Event Not Found");
            return;
        }

        fromAddress = applicationPropertiesService.defaultNotificationEmailFromAddress();
        processNotificaitonsEagerly = applicationPropertiesService.processNotificaitonsEagerly();
        
        for (Notification notification : event.getNotifications()) {
            if (notification.isActive()) {
                notify(notification, eventTitle, event.getPlaceholders(), propertiesMap, fromAddress, emailAddresses, 
                        partnerEmails, emailAddressMPOC, processNotificaitonsEagerly);
            }
        }
        }catch(Exception e){
            log.error("Error raising event: eventTitle["+eventTitle+"]", e);
        }
    }
    

    @Override
    @Transactional(readOnly = true)
    public List<PlaceholderVO> getEventPlaceholders(Integer eventId) {
        List<PlaceholderVO> list = new ArrayList<PlaceholderVO>();
        Set<Placeholder> placeholders;
        Event event;

        event = eventDao.getEventById(eventId);
        if (event != null && event.getPlaceholders() != null) {

            placeholders = event.getPlaceholders();
            for (Placeholder placeholder : placeholders) {
                list.add(ETDConverter.convert(placeholder, new PlaceholderVO()));
            }
        }

        return list;
    }
}
