package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.netpace.vzdn.dao.IEmailMessagesDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;

public class EmailMessagesDAO extends GenericDAO<VzdnEmailMessages,Integer> implements IEmailMessagesDao<VzdnEmailMessages,Integer>{
	
	
	private static Logger log = Logger
	.getLogger(EmailMessagesDAO.class);
	
	public EmailMessagesDAO(){
		super(VzdnEmailMessages.class);
	}    	
	
	public VzdnEmailMessagesAtt getEmailAttachment(Integer emailId) throws Exception{		
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select attachment " +
					"from VzdnEmailMessagesAtt attachment " +					
					"where attachment.emailMessageId =:emailId");
			
			searchQuery.setInteger("emailId", emailId.intValue());
			List<VzdnEmailMessagesAtt> attachments = searchQuery.list();
			if(null != attachments && attachments.size() != 0)
				return attachments.get(0);
			else
				return null;
		} 
		catch (Exception re) {			
			//throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + notifId);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}		
		
	}
	
	
}
