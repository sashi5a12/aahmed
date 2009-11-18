package com.netpace.vzdn.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.INewsLetterDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnNewsletterEmailLog;
import com.netpace.vzdn.service.INewsLetterService;

public class NewsLetterServiceImpl implements INewsLetterService {
	
	private static Logger log = Logger.getLogger(NewsLetterServiceImpl.class);
	
	private INewsLetterDao<VzdnNewsletterEmailLog, Integer> newsLetterDao;

	public INewsLetterDao<VzdnNewsletterEmailLog, Integer> getNewsLetterDao() {
		return newsLetterDao;
	}

	public void setNewsLetterDao(INewsLetterDao<VzdnNewsletterEmailLog, Integer> newsLetterDao) {
		this.newsLetterDao = newsLetterDao;
	}
	
	public void saveNewsLetter(VzdnNewsletterEmailLog newsletterEmailLog, byte[] content) throws Exception{
		Transaction transaction = null;
		try{
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();	
			
			session.save(newsletterEmailLog);			
			session.flush();
			transaction.commit();
		    
			//update NewsLetterLog to upload attachment
			newsLetterDao.updateNewsLetterLog(newsletterEmailLog, content);
			
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
	
	public String[] getVerifySubscribedUsers(String[] emailAddress) throws Exception{
		
		String[] verifiedEmailAddress = new String[emailAddress.length];
		
		for(int index=0; index<emailAddress.length; index++){
			String email = emailAddress[index].trim();
			if(newsLetterDao.verifyEmailAddress(email)){
				if(!isEmailExistAlready(verifiedEmailAddress, email )){
					verifiedEmailAddress[index] = email;
				}
			}			
		}
		
		return verifiedEmailAddress;		
	}
	
	private boolean isEmailExistAlready(String[] emailAddressList, String emailToCheck){
		
		boolean result = false;
		
		for(String emailAddress : emailAddressList){
			if(emailAddress != null && emailAddress.equals(emailToCheck)){
				result = true;
				break;
			}
		}		
		return result;		
	}

	
}
