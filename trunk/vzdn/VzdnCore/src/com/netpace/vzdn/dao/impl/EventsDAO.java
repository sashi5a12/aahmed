package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.netpace.vzdn.dao.IEventsDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnSysRoles;

public class EventsDAO extends GenericDAO<VzdnEvents,Integer> implements IEventsDao<VzdnEvents,Integer>{
	
	
	private static Logger log = Logger
	.getLogger(EventsDAO.class);
	
	public EventsDAO(){
		super(VzdnEvents.class);
	}
	
	public List<VzdnEventNotifications> getNotificationsOnEvent(Long eventId) throws Exception{
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select notifs " +
					"from VzdnEventNotifications notifs " +					
					"where notifs.event.eventId =:eventId");
			
			searchQuery.setLong("eventId", eventId.longValue());
			List<VzdnEventNotifications> events = searchQuery.list();
			return events;
		} 
		catch (Exception re) {			
			log.error(re);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}
	}
}
