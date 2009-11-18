package com.netpace.vzdn.webapp.actions;


import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.EmailMessagesService;
import com.netpace.vzdn.service.EventsService;
import com.netpace.vzdn.service.NotificationService;
import com.opensymphony.xwork2.Preparable;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnUsers;


/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */

public class SearchNotificationsAction extends BaseAction implements Preparable {

	private static Logger log = Logger.getLogger(SearchNotificationsAction.class);

	private static final long serialVersionUID = 1;
	private NotificationService notificationService;
	private EventsService eventService;
	private List<VzdnEvents> events;
	private EmailMessagesService emailMessagesService;
		
	private List<VzdnEventNotifications> notifications;
	private List<VzdnEmailMessages> emails;
	
	private VzdnEventNotifications notificationToSearch = new VzdnEventNotifications();
	
	public SearchNotificationsAction() {
		
	}
	
	public void prepare(){
		//notifications = notificationService.getAllNotifications();
		events = eventService.getAll();
	}
	
	public String list() throws VzdnSecurityException, NotLoggedInException{
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
						
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_NOTIFICATIONS);
			notifications = notificationService.search(notificationToSearch);
			emails = notificationService.getEmailsMessagesOnNotificationList(notifications);
			return SUCCESS;
		}
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			
			se.printStackTrace();
			throw se;
		}
		
		catch(Exception se){
			log.error("Some Issue with get notifications and emails: SearchNotificationAction.list() method:\n" + se);
			se.printStackTrace();
			return INPUT;
		}
	}


	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	

	public VzdnEventNotifications getNotificationToSearch() {
		return notificationToSearch;
	}

	public void setNotificationToSearch(VzdnEventNotifications notificationToSearch) {
		this.notificationToSearch = notificationToSearch;
	}

	public List<VzdnEventNotifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<VzdnEventNotifications> notifications) {
		this.notifications = notifications;
	}

	public EventsService getEventService() {
		return eventService;
	}

	public void setEventService(EventsService eventService) {
		this.eventService = eventService;
	}

	public List<VzdnEvents> getEvents() {
		return events;
	}

	public void setEvents(List<VzdnEvents> events) {
		this.events = events;
	}

	public EmailMessagesService getEmailMessagesService() {
		return emailMessagesService;
	}

	public void setEmailMessagesService(EmailMessagesService emailMessagesService) {
		this.emailMessagesService = emailMessagesService;
	}

	public List<VzdnEmailMessages> getEmails() {
		return emails;
	}

	public void setEmails(List<VzdnEmailMessages> emails) {
		this.emails = emails;
	}
}