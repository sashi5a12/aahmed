package com.netpace.vzdn.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.IPlaceHoldersDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnPlaceHolders;
import com.netpace.vzdn.service.PlaceHolderService;

public class PlaceHolderServiceImpl implements PlaceHolderService{

	
	private IPlaceHoldersDao<VzdnPlaceHolders,Integer> placeHolderDao;
	

	public List<VzdnPlaceHolders> getAll(){
		return placeHolderDao.findAll();
	}


	public IPlaceHoldersDao<VzdnPlaceHolders, Integer> getPlaceHolderDao() {
		return placeHolderDao;
	}


	public void setPlaceHolderDao(
		IPlaceHoldersDao<VzdnPlaceHolders, Integer> placeHolderDao) {
		this.placeHolderDao = placeHolderDao;
	}
	
	public List<VzdnPlaceHolders> getPlaceHoldersOnEventId(Integer eventId){
		
		Transaction transaction = null;
		List<VzdnPlaceHolders> placeHolders = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			placeHolders = placeHolderDao.getPlaceHoldersOnEventId(eventId);			
			transaction.commit();
			return placeHolders;
			
		} catch (Exception e) {
			e.printStackTrace();
			if(null!=transaction)
				transaction.rollback();
			return null;
		}
		finally{
			HibernateSessionFactory.closeSession();			
		}		

	}
	
}
