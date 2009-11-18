package com.netpace.aims.bo.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.Utility;

/**
 * Alliance User Notifications Manager.
 * It has static methods for accessing the BO.
 * @author Ahson Imtiaz
 */

public class AimsProfileNotifManager
{

  static Logger log = Logger.getLogger(AimsProfileNotifManager.class.getName());

  /**
   *  This static method save or update the data of notifications for user.
   *
   */
  public static void updateUserNotifications(AimsUser user, Long[] notificationIds) throws HibernateException
  {
		
		if (user == null ) return;
		
      Session session = null;
      Transaction tx = null;
      
      try
      {
         session = DBHelper.getInstance().getSession();
         tx = session.beginTransaction();
			
         session.delete(" from com.netpace.aims.model.events.AimsNotifOptInRecipientsLite anor WHERE anor.userId = " + user.getUserId().toString() );
			session.flush();
			Long lngUserId = user.getUserId();
			if (notificationIds != null)
				for (int iCount = 0 ; iCount < notificationIds.length ; iCount++){
					com.netpace.aims.model.events.AimsNotifOptInRecipientsLite anor = new com.netpace.aims.model.events.AimsNotifOptInRecipientsLite();
					anor.setUserId(lngUserId);
					anor.setNotificationId(notificationIds[iCount]);
					session.save(anor);
				}

         tx.commit();

      }
      catch(HibernateException e)
      {
      	if ( tx != null ) {
      		tx.rollback();
      	}
         e.printStackTrace();
         throw e;
      }

      finally
      {
         session.close();
      }
  }
  /* Method Ends */

  /**
   *  This static method get the data of all notifications avaliable for user.
   *
   */

  public static Collection getAllApplicableUserNotifs(AimsUser user) throws HibernateException
  {

		if (user == null ) return null;
		
      Session session = null;
      Transaction tx = null;
      ArrayList aNotifList = new ArrayList();
      Object[] objValues = null;
      com.netpace.aims.controller.profile.AimsNotificationExt ane = null;
      String userId = user.getUserId().toString();
      
      try
      {
         session              = DBHelper.getInstance().getSession();
         StringBuffer sbQuery = new StringBuffer();
         sbQuery.append(" select distinct notif.notificationId, notif.notificationTitle ")
                .append(" FROM com.netpace.aims.model.events.AimsEventNotificationLite notif, ")
                .append(" com.netpace.aims.model.events.AimsNotificationRole notifrole, ")
                .append(" WHERE ")
                .append(" ( notifrole.roleId IN ( select aur.roleId FROM com.netpace.aims.model.core.AimsUserRole aur WHERE aur.userId =").append(userId).append(" ) ")
                .append("    OR ( upper(nvl(notif.includeVzwAppsContact,'N')) = 'Y' AND 0 < ( select count(app.aimsVzwAppsContactId) from com.netpace.aims.model.application.AimsApp app WHERE app.aimsVzwAppsContactId =").append( Utility.convertToString(user.getAimsContactId()) ).append(" ) ) ")
                .append("    OR (upper(nvl(notif.includeVzwAccountManager,'N')) = 'Y' AND 0 < (select count(alliance.vzwAccountManager) FROM com.netpace.aims.model.core.AimsAllianc alliance WHERE alliance.vzwAccountManager = ").append( userId ).append(" ) ) ")
                .append(" ) ")			
                .append(" AND notifrole.notificationId(+) = notif.notificationId ")
                .append(" AND notif.status = 'ACTIVE' ")
                .append("ORDER BY 1");
					
			//log.debug("****** : Query : **** " + sbQuery.toString() );

			Query query = session.createQuery(sbQuery.toString());
			
			for (Iterator it = query.iterate() ; it.hasNext() ; ) {
				objValues = (Object[]) it.next();
				ane = new com.netpace.aims.controller.profile.AimsNotificationExt();
				ane.setNotificationId((Long)objValues[0]);
				ane.setNotificationTitle((String)objValues[1]);
				aNotifList.add(ane);
			}
			
			log.debug(" No of records going to be returned for all user notifications are : " + aNotifList.size());
			return aNotifList;

      }
      catch(HibernateException e)
      {
         e.printStackTrace();
         throw e;
      }

      finally
      {
         session.close();
      }
      
  }
  /* Method Ends */
  
  
  /**
   *  This static method get the data of all notifications avaliable for user.
   *
   */

  public static Long[] getUserNotifIds(Long userId) throws HibernateException
  {

		if (userId == null ) return null;
		
      Session session = null;
      Transaction tx = null;
      Long[] aNotifList = null;
      Object[] objValues = null;
      com.netpace.aims.controller.profile.AimsNotificationExt ane = null;
      
      try
      {
         session              = DBHelper.getInstance().getSession();
         StringBuffer sbQuery = new StringBuffer();
         sbQuery.append(" select notif.notificationId ")
                .append(" FROM com.netpace.aims.model.events.AimsNotifOptInRecipients notif ")
                .append(" WHERE ")
                .append(" notif.userId =").append(userId.toString())
                .append(" ORDER BY notif.notificationId ");
			
			Query query = session.createQuery(sbQuery.toString());
			
			List rsList = query.list();
			int iLen = rsList.size(), iCount=0;
			
			if (iLen > 0){
					
					aNotifList = new Long[iLen];
					for (Iterator it = rsList.iterator() ; it.hasNext() ; ) {
						aNotifList[iCount++] = (Long) it.next();
					}
			}
			
			log.debug(" No of records going to be returned for User selected notifications are : " + iLen);
			
			return aNotifList;

      }
      catch(HibernateException e)
      {
         e.printStackTrace();
         throw e;
      }

      finally
      {
         session.close();
      }
      
  }
  /* Method Ends */
  

/* Class Ends */
}