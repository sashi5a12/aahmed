package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.netpace.vzdn.dao.IPlaceHoldersDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.PlaceHoldersNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnPlaceHolders;
import com.netpace.vzdn.model.VzdnUsers;

public class PlaceHoldersDAO extends GenericDAO<VzdnPlaceHolders, Integer> implements IPlaceHoldersDao<VzdnPlaceHolders, Integer> {

	private static Logger log = Logger.getLogger(PlaceHoldersDAO.class);

	public PlaceHoldersDAO() {
		super(VzdnPlaceHolders.class);
	}

	public List<VzdnPlaceHolders> getPlaceHoldersOnEventId(Integer eventId) throws PlaceHoldersNotFoundException {
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("select placeHolders " +
					"from VzdnPlaceHolders placeHolders " +
					"join placeHolders.events e " +
					"where e.eventId = " + eventId);

			List<VzdnPlaceHolders> placeHolders = searchQuery.list();
			return placeHolders;
		} catch (Exception re) {			
			throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + eventId);			
		}
	}
}
