package com.netpace.vzdn.service;

import java.util.List;

import com.netpace.vzdn.model.VzdnEvents;

public interface EventsService {
	
	public List<VzdnEvents> getAll();
	public VzdnEvents getEventOnId(Integer eventId);
}
