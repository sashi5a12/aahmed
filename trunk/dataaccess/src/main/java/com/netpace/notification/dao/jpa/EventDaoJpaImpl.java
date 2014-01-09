package com.netpace.notification.dao.jpa;

import com.netpace.device.dao.jpa.GenericDaoJpaImpl;
import com.netpace.notification.dao.EventDao;
import com.netpace.notification.entities.Event;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(value = EventDao.name)
public class EventDaoJpaImpl extends GenericDaoJpaImpl<Event, Integer> implements EventDao {

    private static final Log log = LogFactory.getLog(EventDaoJpaImpl.class);

    public EventDaoJpaImpl() {
        super(Event.class);
    }

    @Override
    public Event getEventById(Integer eventId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("eventId", eventId);

        List<Event> list = findByNamedQuery("findEventById", map);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public Event getEventByName(String eventName) {
        
        Query query = entityManager.createQuery("select event from Event event"
        + " join fetch event.placeholders placeholders"
        + " join fetch event.notifications notifications"
        + " where event.active = '1' and event.eventName = :eventName");
        query.setParameter("eventName", eventName);

        List<Event> list = query.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<Event> getAllEvents() {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Event> list = findByNamedQuery("findAllEvents", map);
        
        return list;
    }
    
}
