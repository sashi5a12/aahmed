package com.netpace.notification.dao;

import com.netpace.device.dao.GenericDao;
import com.netpace.notification.entities.Event;
import java.util.List;

public interface EventDao extends GenericDao<Event, Integer> {

    public static final String name = "eventDao";

    public Event getEventById(Integer eventId);

    public Event getEventByName(String eventName);

    public List<Event> getAllEvents();
}
