package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.netpace.vzdn.dao.INewsLetterOptOutDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnNewsLetterOptOutRecipients;
import com.netpace.vzdn.model.VzdnUsers;

public class NewsLetterOptOutDaoImpl extends GenericDAO<VzdnNewsLetterOptOutRecipients, Integer> implements
INewsLetterOptOutDao<VzdnNewsLetterOptOutRecipients, Integer>  {
	
	private static final Logger log = Logger.getLogger(NewsLetterOptOutDaoImpl.class); 
	
	public NewsLetterOptOutDaoImpl(){
		super(VzdnNewsLetterOptOutRecipients.class);
	}
	
	public VzdnNewsLetterOptOutRecipients getRecordByUserId(Integer userId) throws Exception{
		Session hibernateSession = null;
		VzdnUsers object = null;		
		try{
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("select optOut from VzdnNewsLetterOptOutRecipients optOut where vzdnUser.userId =:userId");			
			 
			searchQuery.setInteger("userId", userId);
			 
			List<VzdnNewsLetterOptOutRecipients> statusses = searchQuery.list();
			if (statusses == null || statusses.isEmpty()) {
				return null;	
			} else {
				return statusses.get(0);
			}			
		}
		catch(Exception e){
			log.error("Issue reading newsletter-optout-recipients: " + e.getMessage());
			throw e;
		}
	}
	 
}