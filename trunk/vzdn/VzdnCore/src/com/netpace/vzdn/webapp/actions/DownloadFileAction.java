package com.netpace.vzdn.webapp.actions;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnUsers;

import com.netpace.vzdn.model.VzdnPlaceHolders;

import com.netpace.vzdn.model.VzdnSysRoles;

import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.EmailMessagesService;
import com.netpace.vzdn.service.EventsService;
import com.netpace.vzdn.service.ISysRoleService;
import com.netpace.vzdn.service.NotificationService;
import com.netpace.vzdn.service.PlaceHolderService;
import com.netpace.vzdn.util.StringFuncs;
import com.netpace.vzdn.util.UniqueConstraintException;


import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;

/**
 * @author Ikramullah Khan Action class dealing with Notifications Management
 *          
 */
public class DownloadFileAction extends BaseAction{

	private static Logger log = Logger.getLogger(DownloadFileAction.class);

	private static final long serialVersionUID = 1;
	private EmailMessagesService emailMessagesService;
	private InputStream inputStream;
	//holds the content type of the downloaded file
	private String description;	
	//holds the content size of the downloaded file
	private long size;
	
	private String contentType;
	private String contentDisposition; 
	private String contentLength; 
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String execute(){
		Integer id=null;
		try
		{
			id = Integer.valueOf(getServletRequest().getParameter("id"));
			VzdnEmailMessagesAtt attachment = emailMessagesService.getEmailAttachment(id);
			Blob att = attachment.getAtt();			
			inputStream = att.getBinaryStream();
			this.setDescription(attachment.getAttContentType());
			this.setSize(att.length());
			
			this.setContentDisposition("filename=\"" + attachment.getAttFileName());
			this.setContentLength("" + attachment.getAtt().length());
			this.setContentType(attachment.getAttContentType());
			
		}
		catch(SQLException sqlE){
			log.info("This email has not attachments.");
			return INPUT;
		}
		catch(Exception exp){
			log.error("Some issue getting attachment for email with id="+id);
			return INPUT;
		}
		return "download";
	}
	
	public EmailMessagesService getEmailMessagesService() {
		return emailMessagesService;
	}

	public void setEmailMessagesService(EmailMessagesService emailMessagesService) {
		this.emailMessagesService = emailMessagesService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public DownloadFileAction() {		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
}