package com.netpace.vzdn.webapp.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import com.netpace.vzdn.model.MyNotifications;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.EventsService;
import com.netpace.vzdn.service.NotificationService;
import com.netpace.vzdn.service.UsersService;
import com.netpace.vzdn.global.VzdnConstants;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class SaveMyNotificationsAction extends BaseAction implements Preparable {

	
	
	private static Logger log = Logger.getLogger(SaveMyNotificationsAction.class);

	private static final long serialVersionUID = 1;
	private UsersService usersService;	
	private NotificationService notificationService;
	private Map<Integer, VzdnEventNotifications> selectedNotificationsMap;
	private Boolean optOutNewsLetter;
	
	
	private List<Integer> checkedNotifications = new ArrayList<Integer>();
	
	//private Integer checkedNotifications;
	
	
	private EventsService eventService;	
	private List<VzdnEvents> events;
	private VzdnEventNotifications notificationToSearch = new VzdnEventNotifications();
	
	
	
	public void prepare(){
		//notifications = notificationService.getAllNotifications();
		events = eventService.getAll();
	}
	
	
	public SaveMyNotificationsAction() {
		
	}
	
	
	public String save(){
		try{		
			
			VzdnUsers loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Set<VzdnEventNotifications> affectedNotifications = getAffectedNotifications(this.checkedNotifications);
			Set<MyNotifications> previousNotifications =  (Set<MyNotifications>)getSession().getAttribute("previousNotifications");
			Boolean prevOptOutStatus = (Boolean)getSession().getAttribute("newsLetterOptOutStatus");
			
			if(stateChanged(previousNotifications,affectedNotifications, prevOptOutStatus, optOutNewsLetter)){
				usersService.updateMyNotifications(affectedNotifications, loggedInUser, optOutNewsLetter);							
				getSession().setAttribute("successMessage", getText("notifications.my.successmsg"));				
			}
			else
				getSession().setAttribute("successMessage", getText("notifications.my.nochange"));
			
			getSession().removeAttribute("previousNotifications");
			getSession().removeAttribute("newsLetterOptOutStatus");
			return SUCCESS;
		}
		catch(Exception exp){
			log.error("Issue with saving myNotifications: SaveMyNotificationAction.save():\n" + exp);
			return INPUT;
		}
	}
	
	private boolean stateChanged(Set<MyNotifications> previousNotifications, Set<VzdnEventNotifications> currentNotifications, Boolean prevNewsLetterStatus, Boolean currentNewsLetterStatus){
		boolean changed = false;
		
		//First check if the news letter optout status has been changed.
		if(!currentNewsLetterStatus.equals(previousNotifications)){
			return true; 
		}
		
		
		//Now check if the notification optout statusses have been changed.
		for(VzdnEventNotifications newNotification : currentNotifications){
			boolean foundInOld = isPresentInPrevious(newNotification, previousNotifications);
			if(!foundInOld){
				changed = true;
				break;
			}
		}
		
		int no_of_optouts = countOptOuts(previousNotifications);
		
		if(!changed){
			if(no_of_optouts != currentNotifications.size()){
				changed = true;
			}
		}
		return changed;
	}
	
	private boolean isPresentInPrevious(VzdnEventNotifications newNotification, Set<MyNotifications> previousNotifications){
		boolean present = false;
		for(MyNotifications prevNotification : previousNotifications){
			if(newNotification.equals(prevNotification.getNotification())){
				present = true;
				break;
			}				
		}
		return present;
	}
	
	private int countOptOuts(Set<MyNotifications> previousNotifications){
		int counter=0;
		for(MyNotifications notif : previousNotifications){
			if(notif.getIsOptOut()){
				counter++;
			}
		}
		return counter;
	}
	
	private Set<VzdnEventNotifications> getAffectedNotifications(List<Integer> checkedIds){
		
		Set<VzdnEventNotifications> affectedNotifications = new HashSet<VzdnEventNotifications>();		
		for(Integer in : checkedIds){
			VzdnEventNotifications notif = notificationService.getNotificationById(in);
			affectedNotifications.add(notif);
		}
		return affectedNotifications;
	}
	


	public NotificationService getNotificationService() {
		return notificationService;
	}


	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}


	public Map<Integer, VzdnEventNotifications> getSelectedNotificationsMap() {
		return selectedNotificationsMap;
	}


	public void setSelectedNotificationsMap(
			Map<Integer, VzdnEventNotifications> selectedNotificationsMap) {
		this.selectedNotificationsMap = selectedNotificationsMap;
	}


	public List<Integer> getCheckedNotifications() {
		return checkedNotifications;
	}


	public void setCheckedNotifications(List<Integer> checkedNotifications) {
		this.checkedNotifications = checkedNotifications;
	}


	public UsersService getUsersService() {
		return usersService;
	}


	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
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


	public Boolean getOptOutNewsLetter() {
		return optOutNewsLetter;
	}


	public void setOptOutNewsLetter(Boolean optOutNewsLetter) {
		this.optOutNewsLetter = optOutNewsLetter;
	}
}