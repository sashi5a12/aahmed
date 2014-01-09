package com.netpace.notification.services;

import com.netpace.notification.vo.EventVO;
import com.netpace.notification.vo.PlaceholderVO;
import java.util.List;
import java.util.Map;

public interface EventService {

    public List<EventVO> getAllEvents();

    public List<PlaceholderVO> getEventPlaceholders(Integer eventId);

    public void raiseEvent(String eventTitle, Map<String, String> propertiesMap, String emailAddresses, List<String> partnerEmails, String emailAddressMPOC, String loggedInUserName);

}