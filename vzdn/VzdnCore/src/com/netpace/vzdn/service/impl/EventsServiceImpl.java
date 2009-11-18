package com.netpace.vzdn.service.impl;

import java.util.List;

import com.netpace.vzdn.dao.IEventsDao;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.service.EventsService;

public class EventsServiceImpl implements EventsService{

	
	private IEventsDao<VzdnEvents,Integer> eventsDao;
	
	
	public IEventsDao<VzdnEvents,Integer> getEventsDao() {
		return eventsDao;
	}


	public void setEventsDao(IEventsDao<VzdnEvents,Integer> eventsDao) {
		this.eventsDao = eventsDao;
	}


	public List<VzdnEvents> getAll(){
		return eventsDao.findAll();
	}
	
	public VzdnEvents getEventOnId(Integer eventId){
		return eventsDao.findById(eventId);
	}
	
}
