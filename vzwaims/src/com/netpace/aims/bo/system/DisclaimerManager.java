package com.netpace.aims.bo.system;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.system.AimsDisclaimers;
import com.netpace.aims.util.LobUtils;

public class DisclaimerManager {
	private static final Logger log = Logger.getLogger(DisclaimerManager.class.getName());

	public static Collection getDisclaimersList() throws HibernateException {
		log.debug("DisclaimerManager.getDisclaimersList Start:");
		Session session = null;
		Collection collection = null;
		List list=new ArrayList();
		try {
			session = DBHelper.getInstance().getSession();
			collection = session.find("select d.disclaimerId, d.disclaimerName, d.modifiedDate from AimsDisclaimers d order by lower(d.disclaimerName)");			
			if(collection != null ){
				log.debug("No of records returned: " + collection.size());
				for(Iterator itr=collection.iterator(); itr.hasNext();){
					Object[] objects=(Object[])itr.next();
					AimsDisclaimers dis=new AimsDisclaimers();
					dis.setDisclaimerId((Long)objects[0]);
					dis.setDisclaimerName((String)objects[1]);
					dis.setModifiedDate((Date)objects[2]);
					list.add(dis);
				}
			}
		} catch (HibernateException e) {
			log.error(e, e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		log.debug("DisclaimerManager.getDisclaimersList End:");
		return list;
	}
	
	public static AimsDisclaimers getDisclaimerById(Long disclaimerId)throws HibernateException{
		log.debug("DisclaimerManager.getDisclaimerById Start: disclaimerId="+disclaimerId);
		AimsDisclaimers disclaimer=new AimsDisclaimers();
		Session session=null;
		try {
			String queryStr="from AimsDisclaimers d where d.disclaimerId=:id";
			session=DBHelper.getInstance().getSession();
			Collection collection=session.find(queryStr, disclaimerId, new LongType());
						
			for (Iterator iter = collection.iterator(); iter.hasNext();){
				disclaimer=(AimsDisclaimers)iter.next();
			}
			Clob clob=disclaimer.getDisclaimerText();
			if(clob != null){
				disclaimer.setDisclaimerStr(clob.getSubString(1, (int)clob.length()));
			}
			disclaimer.setDisclaimerText(null);			
		}catch (HibernateException e){
			log.error(e,e);
			throw e;
		}catch (SQLException e){
			
		}
		finally {
			if(session != null){
				session.close();
			}
		}
		log.debug("DisclaimerManager.getDisclaimerById End: disclaimerName="+disclaimer.getDisclaimerName());
		return disclaimer;
	}
	
	public static List getUserGuideAndAirTime()throws HibernateException{
		log.debug("DisclaimerManager.getUserGuideAndAirTime Start: ");
		List list=new ArrayList();
		Session session=null;
		try {
			String queryStr="from AimsDisclaimers d where d.disclaimerId in (:ids) order by d.disclaimerId ";
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(queryStr);
			query.setParameterList("ids", new Long[]{ManageApplicationsConstants.USER_GUIDE_DISCLAIMER_ID, ManageApplicationsConstants.AIR_TIME_DISCLAIMER_ID});
			list=query.list();						
		}catch (HibernateException e){
			log.error(e,e);
			throw e;
		}
		finally {
			if(session != null){
				session.close();
			}
		}
		log.debug("DisclaimerManager.getUserGuideAndAirTime End: ");
		return list;
	}
	public static void updateDisclaimer(AimsDisclaimers disclaimer, String disclaimerText)throws HibernateException, Exception{
		log.debug("DisclaimerManager.updateDisclaimer Start: obj="+disclaimer);
		Session session=null;
		Transaction trx=null;
		InputStream inputStream=null;
		try {

			long bytesWrote = 0;
            inputStream = new ByteArrayInputStream(disclaimerText.getBytes());
            
            disclaimer.setDisclaimerText(Hibernate.createClob(" "));
			session=DBHelper.getInstance().getSession();		
			
            trx=session.beginTransaction();
			session.update(disclaimer);
			session.flush();
			session.refresh(disclaimer, LockMode.UPGRADE);
			
			bytesWrote = LobUtils.writeToOraClob((CLOB) disclaimer.getDisclaimerText(), inputStream);			
			
			trx.commit();
			log.debug("bytesWrote: " + bytesWrote);
			
		} catch (HibernateException e) {
			log.error(e,e);
			if(trx != null){
				trx.rollback();
			}
			throw e;			
		} catch (IOException e){
			log.error(e,e);
			if(trx != null){
				trx.rollback();
			}
			throw e;
		} catch (SQLException e){
			log.error(e,e);
			if(trx != null){
				trx.rollback();
			}
			throw e;
		}
		finally{
			if (session != null){
				session.close();
			}
			if (inputStream != null){
				inputStream.close();
			}
		}
		log.debug("DisclaimerManager.updateDisclaimer End:");
	}

}
