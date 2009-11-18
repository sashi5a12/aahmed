package com.netpace.vzdn.webapp.actions;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


import org.apache.log4j.Logger;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnPlaceHolders;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.EventsService;
import com.netpace.vzdn.service.ISysRoleService;
import com.netpace.vzdn.service.NotificationService;
import com.netpace.vzdn.service.PlaceHolderService;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class AddNotificationAction extends BaseAction implements Preparable{

	
	
	private static Logger log = Logger.getLogger(AddNotificationAction.class);

	private static final long serialVersionUID = 1;
	private NotificationService notificationService;	
	private ISysRoleService roleService;
	private EventsService eventService;
	private PlaceHolderService palceHodlerService;
	private Integer eventId;
	
	private List<VzdnEventNotifications> notifications;
	
	
	private List<VzdnEvents> events;
	private List<VzdnPlaceHolders> placeHolders;
	private List<VzdnSysRoles> roles;
	
	private VzdnEventNotifications notification;
	
	private VzdnEmailMessages newMessage = new VzdnEmailMessages();	
	private VzdnEvents newNotification = new VzdnEvents();
	private int[] roleIds;
	private String csvEmails;
	
	public AddNotificationAction() {
		
	}
	
	public void prepare(){
		try{
			notifications = notificationService.getAllNotifications();
			events = eventService.getAll();
			placeHolders = palceHodlerService.getPlaceHoldersOnEventId(newNotification.getEventId());
			roles = roleService.getNonHiddenRoles();
			Collections.sort(roles);
		}
		catch(Exception e){
			log.error("Some Issue with AddNotificationAction.prepare() method\n" + e);
			e.printStackTrace();
		}		
	}
	
	public String add() throws VzdnSecurityException, NotLoggedInException{
		
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_NOTIFICATIONS);
			return SUCCESS;
		}
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			
			se.printStackTrace();
			throw se;
		}
	}
	
	public String changeEvent() throws VzdnSecurityException,  NotLoggedInException{	
		
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		try
		{
			
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_NOTIFICATIONS);
			Integer selectedEventId = getEventId();
			placeHolders = palceHodlerService.getPlaceHoldersOnEventId(selectedEventId);
			return SUCCESS;
		}
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			se.printStackTrace();
			throw se;
		}	
		
	}
	
	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public List<VzdnEventNotifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<VzdnEventNotifications> notifications) {
		this.notifications = notifications;
	}

	public List<VzdnEvents> getEvents() {
		return events;
	}

	public void setEvents(List<VzdnEvents> events) {
		this.events = events;
	}

	public List<VzdnPlaceHolders> getPlaceholders() {
		return placeHolders;
	}

	public void setPlaceholders(List<VzdnPlaceHolders> placeholders) {
		this.placeHolders = placeholders;
	}

	public List<VzdnSysRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<VzdnSysRoles> roles) {
		this.roles = roles;
	}

	public ISysRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(ISysRoleService roleService) {
		this.roleService = roleService;
	}

	public EventsService getEventService() {
		return eventService;
	}

	public void setEventService(EventsService eventService) {
		this.eventService = eventService;
	}

	public VzdnEmailMessages getNewMessage() {
		return newMessage;
	}

	public void setNewMessage(VzdnEmailMessages newMessage) {
		this.newMessage = newMessage;
	}

	public VzdnEvents getNewNotification() {
		return newNotification;
	}

	public void setNewNotification(VzdnEvents newNotification) {
		this.newNotification = newNotification;
	}

	public int[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(int[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getCsvEmails() {
		return csvEmails;
	}

	public void setCsvEmails(String csvEmails) {
		this.csvEmails = csvEmails;
	}

	public PlaceHolderService getPalceHodlerService() {
		return palceHodlerService;
	}

	public void setPalceHodlerService(PlaceHolderService palceHodlerService) {
		this.palceHodlerService = palceHodlerService;
	}

	public List<VzdnPlaceHolders> getPlaceHolders() {
		return placeHolders;
	}

	public void setPlaceHolders(List<VzdnPlaceHolders> placeHolders) {
		this.placeHolders = placeHolders;
	}

	public VzdnEventNotifications getNotification() {
		return notification;
	}

	public void setNotification(VzdnEventNotifications notification) {
		this.notification = notification;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
}