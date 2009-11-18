package com.netpace.vzdn.dao;

import java.io.Serializable;
import java.util.List;

import com.netpace.vzdn.exceptions.PlaceHoldersNotFoundException;
import com.netpace.vzdn.model.VzdnPlaceHolders;

public interface IPlaceHoldersDao<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public List<VzdnPlaceHolders> getPlaceHoldersOnEventId(Integer eventId) throws PlaceHoldersNotFoundException;
}
