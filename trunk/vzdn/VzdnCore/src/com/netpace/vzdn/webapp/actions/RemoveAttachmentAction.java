package com.netpace.vzdn.webapp.actions;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.service.EmailMessagesService;
import com.netpace.vzdn.service.NotificationService;

public class RemoveAttachmentAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(RemoveAttachmentAction.class);
	private NotificationService notificationService;
	private EmailMessagesService emailMessagesService;
	private VzdnEmailMessages updateMessage;
	private Integer id;
		
	private String nextAction;
	
	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public String remove(){
		try{
			nextAction = "editNotification.action";//?id=4
			
			id = Integer.valueOf(getServletRequest().getParameter("id"));
			updateMessage = emailMessagesService.getEmailMessageById(id);
			notificationService.removeAttachmentOnEmailId(id);
		}
		catch(Exception ioe){
			ioe.printStackTrace();
			log.error(ioe.getMessage());
			return INPUT;
		}
		return SUCCESS;	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public EmailMessagesService getEmailMessagesService() {
		return emailMessagesService;
	}

	public void setEmailMessagesService(EmailMessagesService emailMessagesService) {
		this.emailMessagesService = emailMessagesService;
	}

	public VzdnEmailMessages getUpdateMessage() {
		return updateMessage;
	}

	public void setUpdateMessage(VzdnEmailMessages updateMessage) {
		this.updateMessage = updateMessage;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
	
	
}
