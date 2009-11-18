package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.INewsLetterDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnNewsletterEmailLog;

public class NewsLetterDao extends GenericDAO<VzdnNewsletterEmailLog, Integer> implements INewsLetterDao<VzdnNewsletterEmailLog, Integer> {
	
	private static Logger log = Logger.getLogger(NewsLetterDao.class);
	
	public NewsLetterDao(){
		super(VzdnNewsletterEmailLog.class);
	}
	
	public boolean verifyEmailAddress(String emailAddress) throws Exception{
		
		Session hibernateSession = null;
		boolean emailVerified = true;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			String sql = "select optoutUser.user_id " +
						 "from vzdn_newsletter_optout_users optoutUser, " +
						 "vzdn_users users " +
						 "WHERE optoutUser.user_id = users.user_id " +
						 "AND users.user_name = :emailAddress ";
			
			Query query = hibernateSession.createSQLQuery(sql).setString("emailAddress", emailAddress);
			List result = query.list();
			
			if(result.size() > 0){
				emailVerified = false;
			}
			
		} catch (Exception re) {			
			//throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + notifId);
			throw re;
		} finally {
			HibernateSessionFactory.closeSession();			
		}	
		
		return emailVerified;
	}
	
	public void updateNewsLetterLog(VzdnNewsletterEmailLog newsletterEmailLog , byte[] content)	throws Exception{

		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		
		String queryString = "update VzdnNewsletterEmailLog set emailAttachment = :attachment where newsLetterLogId = ";
		
		try {	
			Query query = session.createQuery(queryString + newsletterEmailLog.getNewsLetterLogId().toString())
								.setBinary("attachment", content);			
			query.executeUpdate();
			session.flush();
			transaction.commit();						
			
		
		} catch(Exception ex){
			ex.printStackTrace();
			if(null != transaction){
				transaction.rollback();
			}
			throw ex;
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

}
