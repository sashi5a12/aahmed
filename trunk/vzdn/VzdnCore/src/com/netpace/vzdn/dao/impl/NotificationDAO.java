package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.netpace.vzdn.dao.INotificationDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.PlaceHoldersNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnPlaceHolders;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;


/**
 * @author Ikramullah Khan NotificationsDAO represents the user object genericly
 *         extending the GeneriDAO. This class also implements INotificationDAO
 *         interface. Any 'User' specific methods must be defined in the
 *         INotificationDAO interace.
 */

public class NotificationDAO extends GenericDAO<VzdnEventNotifications, Integer> implements
		INotificationDAO<VzdnEventNotifications, Integer> {

	private static Logger log = Logger.getLogger(NotificationDAO.class);

	public NotificationDAO() {
		super(VzdnEventNotifications.class);
	}
	
	public List<VzdnSysRoles> getRoleIdsOnNotifId(Integer notifId) throws Exception{
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select roles " +
					"from VzdnSysRoles roles " +
					"join roles.notifications notif " +
					"where notif.notificationId =:notifId");
			
			searchQuery.setInteger("notifId", notifId.intValue());
			List<VzdnSysRoles> roles = searchQuery.list();
			return roles;
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
	
	public List<VzdnNotifAdHocRecipients> getAdHocRecipientsOnNotifId(Integer notifId) throws Exception{
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select adhocs " +
					"from VzdnNotifAdHocRecipients adhocs " +					
					"where adhocs.notification.notificationId =:notifId");
			
			searchQuery.setInteger("notifId", notifId.intValue());
			List<VzdnNotifAdHocRecipients> adhocs = searchQuery.list();
			return adhocs;
		} catch (Exception re) {			
			//throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + notifId);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}		
	}
	public VzdnEvents getEventOnNotificationId(Integer notifId) throws Exception{
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select notif " +
					"from VzdnEventNotifications notif " +					
					"where notif.notificationId =:notifId");
			
			searchQuery.setInteger("notifId", notifId.intValue());
			List<VzdnEventNotifications> notifs = searchQuery.list();
			if(null!=notifs){
				VzdnEventNotifications notif = notifs.get(0);
				VzdnEvents event = notif.getEvent();
				return event;
			}
				
			else
				return null;
			
		} catch (Exception re) {			
			//throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + notifId);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}		
	}
	
	public VzdnEmailMessages getEmailMessdageOnNotificationId(Integer notifId) throws Exception{
		Session hibernateSession = null;		
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select notif " +
					"from VzdnEventNotifications notif " +					
					"where notif.notificationId =:notifId");
			
			searchQuery.setInteger("notifId", notifId.intValue());
			List<VzdnEventNotifications> notifs = searchQuery.list();
			if(null!=notifs){
				VzdnEventNotifications notif = notifs.get(0);
				VzdnEmailMessages emailMessage = notif.getMessage();
				return emailMessage;
			}
				
			else
				return null;
			
		} catch (Exception re) {			
			//throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + notifId);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}
	}
	
	public List<VzdnEventNotifications> search(VzdnEventNotifications criteria){
	
	log.info("List find(VzdnEventNotifications criteria) -- START --");
		List<VzdnEventNotifications> notifications = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {

			DetachedCriteria usersCriteria = DetachedCriteria
					.forClass(VzdnEventNotifications.class);

			if (null != criteria.getEvent()) {

				if (null != criteria.getEvent().getEventId()
						&& criteria.getEvent().getEventId().intValue() != -1)
					usersCriteria.add(Restrictions.eq("event", criteria.getEvent()));
				else {
					//do nothing ... no restrictions on criteria
					//usersCriteria.add(Restrictions.)
					//usersCriteria.add(Restrictions.or(Restrictions.eq("userType", developer), Restrictions.eq("userType",verizon)));					
				}
			}

			if (null != criteria.getNotificationTitle()
					&& !"".equals(criteria.getNotificationTitle()))
				usersCriteria.add(Restrictions.like("notificationTitle", criteria.getNotificationTitle(), MatchMode.ANYWHERE));

			hibernateSession = HibernateSessionFactory.getSession();

			notifications = usersCriteria.getExecutableCriteria(hibernateSession).list();

			log.info("userslist size = " + notifications.size());

			return notifications;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			return null;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}		
	}	
	
	public VzdnEventNotifications getNotificationByEmailId(Integer emailId) throws Exception{
		Session hibernateSession = null;		
		try {
			
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select notif " +
					"from VzdnEventNotifications notif " +
					"join notif.message email " +
					"where email.emailMessageId =:emailId");
			
			searchQuery.setInteger("emailId", emailId.intValue());
			List<VzdnEventNotifications> notifications = searchQuery.list();
			if (null!=notifications)
				return notifications.get(0);
			else 
				return null;
		} catch (Exception re) {			
			//throw new PlaceHoldersNotFoundException("No Place Holders found for event id=" + notifId);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}
	}
	
	public List<VzdnUsers> getOptOutUsersOnNotificationId(Integer notificationId) throws Exception{
		Session hibernateSession = null;		
		try {
			
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select users " +
					"from VzdnUsers users " +
					"join users.notifications notifs " +
					"where notifs.notificationId =:notifId");
			
			searchQuery.setInteger("notifId", notificationId.intValue());
			List<VzdnUsers> optouts = searchQuery.list();			
			return optouts;
		} catch (Exception re) {
			log.error(re);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}
	}
}