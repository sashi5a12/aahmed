package com.netpace.vzdn.service;

import java.util.List;

import com.netpace.vzdn.model.VzdnPlaceHolders;

public interface PlaceHolderService {
	
	public List<VzdnPlaceHolders> getAll();
	public List<VzdnPlaceHolders> getPlaceHoldersOnEventId(Integer eventId);
}
