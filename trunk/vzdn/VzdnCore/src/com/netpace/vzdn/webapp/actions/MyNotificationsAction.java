package com.netpace.vzdn.webapp.actions;

import java.util.List;
import java.util.SortedSet;
import org.apache.log4j.Logger;
import com.netpace.vzdn.model.MyNotifications;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.EventsService;
import com.netpace.vzdn.service.UsersService;
import com.netpace.vzdn.global.VzdnConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class MyNotificationsAction extends BaseAction implements Preparable{

	
	
	private static Logger log = Logger.getLogger(MyNotificationsAction.class);

	private static final long serialVersionUID = 1;
	private UsersService usersService;	
	private SortedSet<MyNotifications> myNotifications;
	
	private EventsService eventService;	
	private List<VzdnEvents> events;
	private VzdnEventNotifications notificationToSearch = new VzdnEventNotifications();
	
	private Boolean newsLetterStatus;
	
	
	public Boolean getNewsLetterStatus() {
		return newsLetterStatus;
	}

	public void setNewsLetterStatus(Boolean newsLetterStatus) {
		this.newsLetterStatus = newsLetterStatus;
	}

	public MyNotificationsAction() {
		
	}
	
	public void prepare(){
		//notifications = notificationService.getAllNotifications();
		events = eventService.getAll();
	}
	
	public String view(){
		VzdnUsers loggedInUser = (VzdnUsers) getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
		try
		{
			myNotifications = usersService.getUserNotifications(loggedInUser, notificationToSearch);
			newsLetterStatus = usersService.newsLetterStatus(loggedInUser);			
			getSession().setAttribute("previousNotifications", myNotifications);
			getSession().setAttribute("newsLetterOptOutStatus", newsLetterStatus);
			//List<VzdnSysRoles> userRoles = userService.getUserRoles(loggedInUser.getUserId());	
			return SUCCESS;
		}
		catch(Exception exp){
			log.error("MyNotificationAction: Issue with view method:\n" + exp);
			return INPUT;
		}
	}
	
	public UsersService getUsersService() {
		return usersService;
	}


	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}


	public SortedSet<MyNotifications> getMyNotifications() {
		return myNotifications;
	}

	public void setMyNotifications(SortedSet<MyNotifications> myNotifications) {
		this.myNotifications = myNotifications;
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

	public VzdnEventNotifications getNotificationToSearch() {
		return notificationToSearch;
	}

	public void setNotificationToSearch(VzdnEventNotifications notificationToSearch) {
		this.notificationToSearch = notificationToSearch;
	}
}