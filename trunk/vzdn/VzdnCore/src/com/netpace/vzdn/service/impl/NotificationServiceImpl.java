package com.netpace.vzdn.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.LockMode;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Blob;
import com.netpace.vzdn.util.UniqueConstraintException;
import com.netpace.vzdn.dao.IEmailMessagesDao;
import com.netpace.vzdn.dao.IEventsDao;
import com.netpace.vzdn.dao.INotificationDAO;
import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.dao.impl.GenericDAO;
import com.netpace.vzdn.dao.impl.UserDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;

import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.EmailMessagesService;
import com.netpace.vzdn.service.NotificationService;
import com.netpace.vzdn.util.DBErrorFinder;
import com.netpace.vzdn.util.StringFuncs;
import com.netpace.vzdn.util.SystemConstants;

/**
 * @author Ikramullah Khan Implementing class for the NotificationService.
 */

public class NotificationServiceImpl implements NotificationService {
	
	

	private INotificationDAO<VzdnEventNotifications, Integer> notificationsDao;
	private IEmailMessagesDao<VzdnEmailMessages, Integer> messagesDao;

	
	private IEventsDao<VzdnEvents, Integer> eventsDao;
	
	private static Logger log = Logger.getLogger(GenericDAO.class);
	private ISysRoleDao roleDao;
	
	public ISysRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(ISysRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<VzdnEventNotifications> getAllNotifications() {
		return notificationsDao.findAll();
	}

	public IEventsDao<VzdnEvents, Integer> getEventsDao() {
		return eventsDao;
	}

	public void setEventsDao(IEventsDao<VzdnEvents, Integer> eventsDao) {
		this.eventsDao = eventsDao;
	}
	
	
	public List<VzdnEventNotifications> search(VzdnEventNotifications criteria){
		return this.notificationsDao.search(criteria);		
	}
	
	public VzdnEmailMessages getEmailMessdageOnNotificationId(Integer notificationId) throws Exception{
		return this.notificationsDao.getEmailMessdageOnNotificationId(notificationId);
	}
		
	

	public void createNewNotification(VzdnEventNotifications notification) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			notificationsDao.save(notification);
			session.flush();
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (null != transaction)
				transaction.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	// must return VzdnNotifications
	public void updateNotification(VzdnEventNotifications notification) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			notificationsDao.update(notification);
			session.flush();
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (null != transaction)
				transaction.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	// must return VzdnNotifications
	public void removeNotification(VzdnEventNotifications notification) throws Exception{
		
		VzdnEmailMessages correspondingEmail = getEmailMessdageOnNotificationId(notification.getNotificationId());		
		Transaction transaction = null;
		String strQuery="";
		Query removeQuery=null;
		
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			
			strQuery =  "delete from VzdnNotifAdHocRecipients where notification_id= :notifId ";			
			removeQuery = session.createQuery(strQuery);
			removeQuery.setInteger("notifId", notification.getNotificationId().intValue());
			removeQuery.executeUpdate();
			session.flush();
			
			
			strQuery =  "delete from VzdnEmailMessagesAtt where emailMessageId= :emailId ";			
			removeQuery = session.createQuery(strQuery);
			
			removeQuery.setInteger("emailId", correspondingEmail.getEmailMessageId());
			removeQuery.executeUpdate();
			session.flush();
			
			
			Set<VzdnUsers> optOutRecipients = null;			
			notification.setOptOutRecipients(optOutRecipients);
			
			notificationsDao.delete(notification);
			messagesDao.delete(notification.getMessage());
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (null != transaction)
				transaction.rollback();
			throw e; 
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
	
	
	
	public List<Integer> getRolesOnNotifId(Integer id) throws Exception{
		List<VzdnSysRoles> roles = notificationsDao.getRoleIdsOnNotifId(id);
		List<Integer> roleIds = new ArrayList<Integer>();		
		
		for(VzdnSysRoles role : roles){
			roleIds.add(role.getRoleId());
		}
		
		return roleIds;
	}
	public String getAdHocRecipientsOnNotifId(Integer id) throws Exception{
		List<VzdnNotifAdHocRecipients> adhocs = notificationsDao.getAdHocRecipientsOnNotifId(id);
		
		String stringArray[] = new String[adhocs.size()];
		
		int i=0;
		for(VzdnNotifAdHocRecipients adhoc: adhocs){
			stringArray[i++] = adhoc.getEmailAddress();
		}
		
		return StringFuncs.ConvertArrToString(stringArray,",");

		
	}
	public VzdnEvents getEventOnNotificationId(Integer id) throws Exception{
		return notificationsDao.getEventOnNotificationId(id);
	}

	

	public VzdnEventNotifications getNotificationById(Integer notificationId) {
		return (VzdnEventNotifications) notificationsDao
				.findById(notificationId);
	}

	public INotificationDAO<VzdnEventNotifications, Integer> getNotificationsDao() {
		return notificationsDao;
	}

	public void setNotificationsDao(
			INotificationDAO<VzdnEventNotifications, Integer> notificationsDao) {
		this.notificationsDao = notificationsDao;
	}

	public void saveEventNotification(VzdnEmailMessages notificationForm, Integer eventId, int[] roleIds, String csvEmails, VzdnUsers aimsUser, VzdnEmailMessagesAtt emailAttachment) 
	throws UniqueConstraintException, HibernateException, Exception
	{
		Session session = null;
		Transaction tx = null;

		try {

			//session = DBHelper.getInstance().getSession();
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();

			VzdnEmailMessages emailMessage = new VzdnEmailMessages();

			emailMessage.setEmailTitle(notificationForm.getEmailTitle());
			emailMessage.setEmailDesc(notificationForm.getEmailDesc());
			emailMessage.setEmailText(notificationForm.getEmailText());
			//emailMessage.setEmailCategory(SystemConstants.Email_Message_Categories[1]);
			emailMessage.setCreatedBy(aimsUser.getFirstName());
			emailMessage.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			emailMessage.setLastUpdatedBy(aimsUser.getFirstName());
			emailMessage.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
			session.save(emailMessage);
			session.flush();

			log.debug(" First Session Flushed : ");
		
			//IKB: disabling the attachment logic for now.
			

			if (emailAttachment != null ) {//&& emailAttachment.getFileSize() > 0) {

				VzdnEmailMessagesAtt emailMsgAtt = new VzdnEmailMessagesAtt();
				emailMsgAtt.setEmailMessageId(emailMessage.getEmailMessageId());

				boolean emailAttachmentPresent = false;
				byte[] buffer = new byte[1];
				buffer[0] = 1;

				log.debug("emailAttachment Name : "	+ emailAttachment.getAttFileName());
				log.debug("emailAttachment Type : "	+ emailAttachment.getAttContentType());
				//log.debug("emailAttachment Size : " + emailAttachment.getAttFileSize());

				emailMsgAtt.setAttContentType(emailAttachment.getAttContentType());
				emailMsgAtt.setAttFileName(emailAttachment.getAttFileName());				
				emailMsgAtt.setAtt(Hibernate.createBlob(emailAttachment.getAtt().getBinaryStream()));				
				
				//emailAttachmentPresent = true;
				//emailAttachment.setEmailMessageId(emailMessage.getEmailMessageId());
				session.save(emailMsgAtt);
				session.flush();
				
				/*session.refresh(emailMsgAtt, org.hibernate.LockMode.UPGRADE);
				if (emailAttachmentPresent)
					LobUtils.writeToOraBlob((Blob) emailMsgAtt.getAtt(),emailAttachment.getAtt().getBinaryStream());					
				
				session.flush();*/				
				/*tx.commit();*/

				/*if (emailAttachmentPresent)
					emailAttachment.destroy();*/

			}
			
			log.debug(" Attachment Saved : ");
			
			VzdnEventNotifications eventNotification = new VzdnEventNotifications();
			eventNotification.setMessage(emailMessage);
			VzdnEvents eventForm = eventsDao.findById(eventId);
			eventNotification.setEvent(eventForm);
			
			eventNotification.setNotificationTitle(emailMessage.getEmailTitle());
			
			eventNotification.setMedia("E");
			eventNotification.setStatus("ACTIVE");
			
			
			//eventNotification.setFromAddress(aimsUser.get .getAimsContact().getEmailAddress());
			//IKB: Not sure if username will always contain the email address of the user or not.			
			eventNotification.setFromAddress(aimsUser.getUserName());

			//IKB: 
			//eventNotification.setIncludeVzwAccountManager(MiscUtils.checkYesNo(notificationForm.getVzwAccountManager()));
			//eventNotification.setIncludeVzwAppsContact(MiscUtils.checkYesNo(notificationForm.getAppManager()));

			
			log.debug(" Second Session Flushed : ");

			Set<VzdnSysRoles> tempRoles = new HashSet<VzdnSysRoles>();
			
			if (roleIds != null) {
				VzdnSysRoles roleLite = null;
				for (int i = 0; roleIds.length > i; i++) {
					roleLite = roleDao.getRolesOnId(roleIds[i]);
					tempRoles.add(roleLite);
					//session.save(roleLite);
				}
				//session.flush();
				//log.debug(" Third Session Flushed : ");
			}			
			
			eventNotification.setRoles(tempRoles);			
			
			session.save(eventNotification);
			session.flush();


			Set<VzdnNotifAdHocRecipients> recipients = new HashSet<VzdnNotifAdHocRecipients>();
			
			
			String[] strArray = StringFuncs.tokenize(csvEmails, ",");

			for (int i = 0; i < strArray.length; i++) {
				VzdnNotifAdHocRecipients recipient = null;				
				recipient = new VzdnNotifAdHocRecipients();
				recipient.setNotification(eventNotification);
				recipient.setEmailAddress(strArray[i]);
				recipients.add(recipient);
				log.debug(" Email Address : " + strArray[i]);
				
			}
			eventNotification.setAdHocRecipients(recipients);

			session.update(eventNotification);
			session.flush();

			
			log.debug(" Fourth Session Flushed : ");

			tx.commit();
			log.debug(" Transaction Committed : ");

		} catch (JDBCException je) {
			if (tx != null) {
				tx.rollback();

			}
			String exMessage = je.getMessage();
			if (DBErrorFinder.searchUniqueConstraintErrors(exMessage,
					SystemConstants.UNIQUE_CONSTRAINT_KEYS,
					new UniqueConstraintException())) {
				throw new UniqueConstraintException();
			} else {
				je.printStackTrace();
				throw new HibernateException(je);
			}
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			he.printStackTrace();
			throw he;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}

		finally {
			session.close();
		}
	}
	
	
	
	
	public void updateEventNotification(VzdnEmailMessages notificationForm, Integer eventId, Integer notificationId, int[] roleIds, String csvEmails, VzdnUsers aimsUser, VzdnEmailMessagesAtt emailAttachment) 
	throws UniqueConstraintException, HibernateException, Exception
	{
		Session session = null;
		Transaction tx = null;

		try {

			//session = DBHelper.getInstance().getSession();
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();

			VzdnEmailMessages emailMessage = notificationForm;

			emailMessage.setLastUpdatedBy(aimsUser.getFirstName());
			emailMessage.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
			session.update(emailMessage);
			session.flush();

			log.debug(" First Session Flushed : ");	
			
			
			VzdnEventNotifications eventNotification = notificationsDao.findById(notificationId);
			
			eventNotification.setMessage(emailMessage);
			
			VzdnEvents eventForm = eventsDao.findById(eventId);
			eventNotification.setEvent(eventForm);
			
			eventNotification.setNotificationTitle(emailMessage.getEmailTitle());
			
			eventNotification.setMedia("E");
			eventNotification.setStatus("ACTIVE");
			
			
			//eventNotification.setFromAddress(aimsUser.get .getAimsContact().getEmailAddress());
			//IKB: Not sure if username will always contain the email address of the user or not.			
			eventNotification.setFromAddress(aimsUser.getUserName());

			//IKB: 
			//eventNotification.setIncludeVzwAccountManager(MiscUtils.checkYesNo(notificationForm.getVzwAccountManager()));
			//eventNotification.setIncludeVzwAppsContact(MiscUtils.checkYesNo(notificationForm.getAppManager()));

			
			log.debug(" Second Session Flushed : ");

			Set<VzdnSysRoles> tempRoles = new HashSet<VzdnSysRoles>();
			
			if (roleIds != null) {
				VzdnSysRoles roleLite = null;
				for (int i = 0; roleIds.length > i; i++) {
					roleLite = roleDao.getRolesOnId(roleIds[i]);
					tempRoles.add(roleLite);
					//session.save(roleLite);
				}
				//session.flush();
				//log.debug(" Third Session Flushed : ");
			}			
			
			
			
			//Set<VzdnNotifAdHocRecipients> recipients = eventNotification.getAdHocRecipients();
			//recipients = null;
			//eventNotification.setAdHocRecipients(recipients);
			
			eventNotification.setRoles(tempRoles);
			session.update(eventNotification);
			session.flush();
			
			if (emailAttachment != null ) {
				
				VzdnEmailMessagesAtt emailMsgAtt = new VzdnEmailMessagesAtt();
				emailMsgAtt.setEmailMessageId(emailMessage.getEmailMessageId());

				boolean emailAttachmentPresent = false;
				byte[] buffer = new byte[1];
				buffer[0] = 1;

				log.debug("emailAttachment Name : "	+ emailAttachment.getAttFileName());
				log.debug("emailAttachment Type : "	+ emailAttachment.getAttContentType());

				emailMsgAtt.setAttContentType(emailAttachment.getAttContentType());
				emailMsgAtt.setAttFileName(emailAttachment.getAttFileName());				
				emailMsgAtt.setAtt(Hibernate.createBlob(emailAttachment.getAtt().getBinaryStream()));			
				
				session.saveOrUpdate(emailMsgAtt);
				session.flush();				

			}
			
			
			String strQuery =  "delete from VzdnNotifAdHocRecipients where notification_id= :notifId ";			
			Query removeQuery = session.createQuery(strQuery);
			removeQuery.setInteger("notifId", eventNotification.getNotificationId().intValue());
			removeQuery.executeUpdate();
			session.flush();
			
			
			Set<VzdnNotifAdHocRecipients> recipients = new HashSet<VzdnNotifAdHocRecipients>();
			
			String[] strArray = StringFuncs.tokenize(csvEmails, ",");
			new HashSet<VzdnNotifAdHocRecipients>();
			for (int i = 0; i < strArray.length; i++) {
				VzdnNotifAdHocRecipients recipient = null;				
				recipient = new VzdnNotifAdHocRecipients();
				recipient.setNotification(eventNotification);
				recipient.setEmailAddress(strArray[i]);
				recipients.add(recipient);
				log.debug(" Email Address : " + strArray[i]);
				
			}
			eventNotification.setAdHocRecipients(recipients);
			session.update(eventNotification);
			session.flush();

			
			log.debug(" Fourth Session Flushed : ");

			tx.commit();
			log.debug(" Transaction Committed : ");

		} catch (JDBCException je) {
			if (tx != null) {
				tx.rollback();

			}
			String exMessage = je.getMessage();
			if (DBErrorFinder.searchUniqueConstraintErrors(exMessage,
					SystemConstants.UNIQUE_CONSTRAINT_KEYS,
					new UniqueConstraintException())) {
				throw new UniqueConstraintException();
			} else {
				je.printStackTrace();
				throw new HibernateException(je);
			}
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			he.printStackTrace();
			throw he;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}

		finally {
			session.close();
		}
	}
	
	public void removeAttachmentOnEmailId(Integer emailId){
		try{
			Session session = HibernateSessionFactory.getSession();
			String strQuery =  "delete from VzdnEmailMessagesAtt where emailMessageId= :emailId ";			
			Query removeQuery = session.createQuery(strQuery);		
			removeQuery.setInteger("emailId", emailId);
			removeQuery.executeUpdate();
			session.flush();
		}
		catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public List<VzdnEmailMessages> getEmailsMessagesOnNotificationList(List<VzdnEventNotifications> notifications) throws Exception{
		List<VzdnEmailMessages> emailMessages = new ArrayList<VzdnEmailMessages>();
		
		for(VzdnEventNotifications notification : notifications){
			VzdnEmailMessages email = getEmailMessageOnNotification(notification);
			emailMessages.add(email);
		}
		return emailMessages;
	}
	
	public VzdnEmailMessages getEmailMessageOnNotification(VzdnEventNotifications notification) throws Exception{
		return notificationsDao.getEmailMessdageOnNotificationId(notification.getNotificationId());
	}
	
		
	public VzdnEventNotifications getNotificationByEmailId(Integer emailId) throws Exception{
		return notificationsDao.getNotificationByEmailId(emailId);
	}

	public IEmailMessagesDao<VzdnEmailMessages, Integer> getMessagesDao() {
		return messagesDao;
	}

	public void setMessagesDao(
			IEmailMessagesDao<VzdnEmailMessages, Integer> messagesDao) {
		this.messagesDao = messagesDao;
	}


}
