package com.netpace.vzdn.webapp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;


import net.sf.hibernate.Hibernate;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;


import java.sql.Blob;
import java.sql.Timestamp;

import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;
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
import com.netpace.vzdn.util.StringFuncs;
import com.netpace.vzdn.util.UniqueConstraintException;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class AddSaveNotificationAction extends BaseAction implements Preparable{

	
	
	private static Logger log = Logger.getLogger(AddSaveNotificationAction.class);

	private static final long serialVersionUID = 1;
	private NotificationService notificationService;	
	private ISysRoleService roleService;
	private EventsService eventService;
	private PlaceHolderService palceHodlerService;
	
	private List<VzdnEventNotifications> notifications;
	
	
	private List<VzdnEvents> events;
	private List<VzdnPlaceHolders> placeHolders;
	private List<VzdnSysRoles> roles;
	
	private VzdnEventNotifications notification;
	
	private VzdnEmailMessages newMessage = new VzdnEmailMessages();	
	//private VzdnEvents newNotification = new VzdnEvents();
	private Integer eventId;
	
	private int[] roleIds;
	private String csvEmails;
	
	
	private File upload;
    private String uploadContentType;
    private String uploadFileName;
    private String fileCaption;
	

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileCaption() {
		return fileCaption;
	}

	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}

	public AddSaveNotificationAction() {
		
	}
	
	public void prepare(){
		notifications = notificationService.getAllNotifications();
		events = eventService.getAll();
		//placeHolders = palceHodlerService.getAll();
		placeHolders = palceHodlerService.getPlaceHoldersOnEventId(getEventId());
		roles = roleService.getAll();
		
	}
	
	public String add(){
		return SUCCESS;
	}
	
	public String addSave()  throws VzdnSecurityException, NotLoggedInException{

		VzdnUsers aimsUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		
		try
		{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
			
			
			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");
			
			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGE_NOTIFICATIONS);
			
			VzdnEmailMessagesAtt emailAttachment = null;
			
			if(null != upload){			
				InputStream inputStream = new FileInputStream(upload);			
				Blob att = Hibernate.createBlob(inputStream);
				System.out.println("Lenght of the attachment is:" + att.length());			
				float attachmentLength = att.length() / VzdnConstants.FILE_SIZE_DIVIDER;
				float allowedFileSize = Float.parseFloat(getText("notifications.allowed.mb.filesize"));
				
				if(attachmentLength > allowedFileSize){
					this.addFieldError("upload", getText("notifications.upload.errormsg"));
					return INPUT;
				}	
				
				emailAttachment = new VzdnEmailMessagesAtt();			
				emailAttachment.setAtt(att);
				emailAttachment.setAttContentType(uploadContentType);
				emailAttachment.setAttFileName(uploadFileName);
				emailAttachment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				emailAttachment.setCreatedBy(loggedInUser.getUserName());								
			}			
			notificationService.saveEventNotification(newMessage, getEventId(), roleIds, csvEmails, aimsUser, emailAttachment);			
			//notificationService.saveEventNotification(newMessage, getEventId(), roleIds, csvEmails, aimsUser);
			this.addActionMessage(getText("notifications.create.successmsg"));
			getSession().setAttribute("successMessage", getText("notifications.create.successmsg"));
		}
		
	
		catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			
			se.printStackTrace();
			throw se;
		}
		
		catch(UniqueConstraintException exp){
			addActionError("Some Problem with creating new notification");
			log.error(exp.getStackTrace());
			return INPUT;
		}
		catch(HibernateException exp){
			addActionError("Some Problem with creating new notification");
			log.error(exp.getStackTrace());
			return INPUT;

		}
		catch(Exception exp){
			addActionError("Some Problem with creating new notification");
			log.error(exp.getStackTrace());
			return INPUT;

		}		
		return SUCCESS;	
	}
	
	public void validate(){
		
		placeHolders = palceHodlerService.getPlaceHoldersOnEventId(getEventId());
		
		if(null == getEventId() || getEventId() == -1)
			addFieldError("eventId","Event Name is a required field!");
		
		if(null == newMessage.getEmailTitle() || "".equals(newMessage.getEmailTitle()))
			addFieldError("newMessage.emailTitle","Email Title is a required field!");
		
		if(null == newMessage.getEmailText() || "".equals(newMessage.getEmailText()))
			addFieldError("newMessage.emailText","Email Content is a required field!");
		
		try{
			if(null != upload){			
				InputStream inputStream = new FileInputStream(upload);			
				Blob att = Hibernate.createBlob(inputStream);
				System.out.println("Lenght of the attachment is:" + att.length());			
				float attachmentLength = att.length() / VzdnConstants.FILE_SIZE_DIVIDER;
				float allowedFileSize = Integer.parseInt(getText("notifications.allowed.mb.filesize"));
				
				if(attachmentLength > allowedFileSize){
					this.addFieldError("upload", getText("notifications.upload.errormsg"));				
				}
			}
		}catch(Exception e){
			log.error("Some problem validating file upload:\n" + e);
			System.out.println("Some problem validating file upload:");
		}
		
		if(newMessage.getEmailTitle().length() > VzdnConstants.EMAIL_TITLE_LENGHT)
			addFieldError("newMessage.emailTitle","Email Title cannot have more than "+ VzdnConstants.EMAIL_TITLE_LENGHT +" characters!");

		if(newMessage.getEmailDesc().length() > VzdnConstants.EMAIL_DESC_LENGHT)
			addFieldError("newMessage.emailDesc","Email Description cannot have more than "+ VzdnConstants.EMAIL_DESC_LENGHT +" characters!");
		
		if(newMessage.getEmailText().length() > VzdnConstants.EMAIL_CONT_LENGHT)
			addFieldError("newMessage.emailText","Email Content cannot have more than "+ VzdnConstants.EMAIL_CONT_LENGHT +" characters!");
		
		if(containsInvalidCharacters(csvEmails))
			addFieldError("csvEmails","Invalid Comma Separated String [" + csvEmails + "]");
		
		String[] emails = StringFuncs.tokenize(csvEmails, ",");
		
		
		
		for(int i=0; i<emails.length; i++){
			if(!StringFuncs.isEmail(emails[i]))
			{
				addFieldError("csvEmails","Invalid Email [" + emails[i] + "]" );
				break;
			}
		}
		
	}
	
	public boolean containsInvalidCharacters(String csv){
		int i=0;
		boolean invalidSeparator=false;
		for(char c : VzdnConstants.INVALID_EMAIL_SEPARATORS){			
			if(csv.contains(Character.toString(c))){
				invalidSeparator = true;
				break;
			}
		}
		return invalidSeparator;
	}
	
	public String changeEvent(){
		Integer selectedEventId = getEventId();
		//VzdnEvents selectedEvent = eventService.getEventOnId(selectedEventId);
		placeHolders = palceHodlerService.getPlaceHoldersOnEventId(selectedEventId);
		return SUCCESS;
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