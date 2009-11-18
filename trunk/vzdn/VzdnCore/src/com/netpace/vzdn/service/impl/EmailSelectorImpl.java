package com.netpace.vzdn.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.netpace.vzdn.dao.IEventsDao;
import com.netpace.vzdn.dao.INotificationDAO;
import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.dao.impl.EventsDAO;
import com.netpace.vzdn.dao.impl.NotificationDAO;
import com.netpace.vzdn.model.NotificationEmails;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEventLite;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEventLite;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipientsLite;
import com.netpace.vzdn.service.EmailSelector;
import com.netpace.vzdn.util.StringFuncs;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;


public class EmailSelectorImpl implements EmailSelector {
	
	private IEventsDao<VzdnEventLite, Integer> eventsDao;
	private INotificationDAO<VzdnEventNotifications, Integer> notificationsDao;
	private ISysRoleDao roleDao;
	
	public EmailSelectorImpl(IEventsDao eventsDao, INotificationDAO notificationDao, ISysRoleDao roleDao){
		this.eventsDao = eventsDao;
		this.notificationsDao = notificationDao;
		this.roleDao = roleDao;
	}
	
	public List<NotificationEmails> getAciveEmailsForEvent(VzdnEventLite event) throws Exception{
		List<NotificationEmails> notifEmailsList = new ArrayList<NotificationEmails>();
		List<VzdnEventNotifications> notifications = eventsDao.getNotificationsOnEvent(event.getEventId());
		//List<String> allEmails = new ArrayList<String>();
		for(VzdnEventNotifications notif :  notifications){
			VzdnEmailMessages emailMessage = notificationsDao.getEmailMessdageOnNotificationId(notif.getNotificationId());
			List<String> notifEmails = getActiveEmailsForNotification(notif);
			NotificationEmails ntfEmails = new NotificationEmails();
			
			ntfEmails.setNotification(notif);
			ntfEmails.setEmails(notifEmails);
			ntfEmails.setEmailMessage(emailMessage);
			
			notifEmailsList.add(ntfEmails);
			//allEmails = (List<String>)merge(allEmails, notifEmails);			
		}
		return notifEmailsList;		
	}
	
	public List<String> getActiveEmailsForNotification(VzdnEventNotifications notification) throws Exception{
		List<String> adhocEmails = getAdhocEmailsForNotification(notification);
		List<String> regularEmails = getRegularEmailsForNotification(notification);
		List<String> allSendables = (List<String>)merge(adhocEmails,regularEmails);
		return allSendables;
	}
	
	public List<String> getAdhocEmailsForNotification(VzdnEventNotifications notification) throws Exception{
		List<VzdnNotifAdHocRecipients> adhocList = notificationsDao.getAdHocRecipientsOnNotifId(notification.getNotificationId());		
		//String adhocEmailsStr = getDummyAdhocEmails(notification);		
		//String[] adhocEmails = StringFuncs.tokenize(adhocEmailsStr,",");
		List<String> adhocEmailsSet = getEmailList(adhocList);
		return adhocEmailsSet;
	}
	
	public List<String> getRegularEmailsForNotification(VzdnEventNotifications notification) throws Exception{
		List<VzdnSysRoles> roles = notificationsDao.getRoleIdsOnNotifId(notification.getNotificationId());
		//Set<VzdnSysRoles> roles = getDummyRoles(notification);
		List<VzdnUsers> allUsersInRoles = getUsersInRoles(roles);
		List<VzdnUsers> optOutUsers = notificationsDao.getOptOutUsersOnNotificationId(notification.getNotificationId());
		//Set<VzdnUsers> optOutUsers = getDummyOptOuts(notification);
		List<VzdnUsers> actualUsers = removeOptOutUsers(allUsersInRoles, optOutUsers);
		return getEmails(actualUsers);
	}
	
	private List<String> getEmails(List<VzdnUsers> users){
		List<String> emails = new ArrayList<String>(users.size());
		for(VzdnUsers user : users){
			emails.add(user.getUserName());
		}
		return emails;
	}
	
	private List<VzdnUsers> getUsersInRoles(List<VzdnSysRoles> roles) throws Exception{
		List<VzdnUsers> allUsersInRoles = new ArrayList<VzdnUsers>();
		for(VzdnSysRoles role : roles){
			List<VzdnUsers> users = roleDao.getUsersInRole(role.getRoleId());
			//Set<VzdnUsers> users = getDummyUsersInRole(role);
			allUsersInRoles = (List<VzdnUsers>)merge(allUsersInRoles, users);
		}
		return allUsersInRoles;
	}
	
	private List<VzdnUsers> removeOptOutUsers(List<VzdnUsers> allUsers, List<VzdnUsers> usersToRemove){
		for(VzdnUsers user : usersToRemove){
			allUsers.remove(user);
		}
		return allUsers;
	}
	
	private List merge(List list1, List list2){
		for(Object user : list2){
			if(!list1.contains(user))
				list1.add(user);
		}
		return list1;
	}
	
	private List<String> getEmailList(List<VzdnNotifAdHocRecipients> list){
		List<String> emailList = new ArrayList<String>();
		for(VzdnNotifAdHocRecipients rec : list){
			if(!emailList.contains(rec))
				emailList.add(rec.getEmailAddress());
		}
		return emailList;
	}

	public IEventsDao<VzdnEventLite, Integer> getEventsDao() {
		return eventsDao;
	}

	public INotificationDAO<VzdnEventNotifications, Integer> getNotificationsDao() {
		return notificationsDao;
	}

	public void setNotificationsDao(
			INotificationDAO<VzdnEventNotifications, Integer> notificationsDao) {
		this.notificationsDao = notificationsDao;
	}

	public void setEventsDao(IEventsDao<VzdnEventLite, Integer> eventsDao) {
		this.eventsDao = eventsDao;
	}
	
	public ISysRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(ISysRoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
