package com.netpace.aims.controller.system;


import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;
import com.netpace.aims.controller.*;


/**
 * @struts.form name="SystemNotificationForm"
 */

public class SystemNotificationForm extends BaseValidatorForm{

  private java.lang.String createdBy;
  private java.lang.String lastUpdatedBy;
  private String task;

  private String eventName;
  private String lastUpdatedDate;
  private String createdDate;
  private java.util.Collection eventList;
  private String eventId;


  private String systemNotificationId;
  private String subject;
  private String description;
  private String content;
  private java.util.Collection placeHolderList;
  private java.util.Collection roles;
  private String roleId;
  private String roleName;
  private String recipientEmail;
  private Long[] roleIds;
  private Long notificationId;
  private String placeHolderId;
  private String vzwAccountManager;
  private String appManager;
  private String emailMessageId;


static Logger log = Logger.getLogger(SystemNotificationForm.class.getName());
  private org.apache.struts.upload.FormFile emailAttachment;
  private String emailAttachmentName;
  private String emailAttachmentType;


  public java.lang.String getCreatedBy()
  {
    return this.createdBy;
  }

  public void setCreatedBy(java.lang.String createdBy)
  {
    this.createdBy = createdBy;
  }

  public String getCreatedDate()
  {
    return this.createdDate;
  }

  public void setCreatedDate(String createdDate)
  {
    this.createdDate = createdDate;
  }

  public java.lang.String getLastUpdatedBy()
  {
    return this.lastUpdatedBy;
  }

  public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
  {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public String getLastUpdatedDate()
  {
    return this.lastUpdatedDate;
  }

  public void setLastUpdatedDate(String lastUpdatedDate)
  {
    this.lastUpdatedDate = lastUpdatedDate;
  }

  public String getTask()
  {
    return task;
  }

  public void setTask(String task)
  {
    this.task = task;
  }

  public String getEventName() {
    return eventName;
  }
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }
  public String getEventId() {
    return eventId;
  }
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }
  public java.util.Collection getEventList() {
    return eventList;
  }
  public void setEventList(java.util.Collection eventList) {
    this.eventList = eventList;
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {

//    log.debug(" The Task is : : " +  request.getParameter("task") );
//    log.debug(" The Task is by Attribute  : : " +  this.task );

  }


  public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

                 ActionErrors errors	=	new	ActionErrors();

                  if (this.isBlankString(this.subject))
                       errors.add(ActionErrors.GLOBAL_MESSAGE,	new ActionMessage("error.required.subject"));

//                 if ( (( this.emailAttachment != null) && this.emailAttachment.getFileSize() < 1))
//                       errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.required.filenameAndUrl"));

                  if (this.isBlankString(this.eventId) || this.eventId.equals("0"))
                        errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.event"));

                  if (this.isBlankString(this.content) )
                       errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.content"));

                 /*
                  if ( this.roleIds == null || this.roleIds.length < 1 )
                        errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.role"));
                  */

                  return errors;

 }


  public String getSystemNotificationId() {
    return systemNotificationId;
  }
  public void setSystemNotificationId(String systemNotificationId) {
    this.systemNotificationId = systemNotificationId;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public java.util.Collection getPlaceHolderList() {
    return placeHolderList;
  }
  public void setPlaceHolderList(java.util.Collection placeHolderList) {
    this.placeHolderList = placeHolderList;
  }
  public java.util.Collection getRoles() {
    return roles;
  }
  public void setRoles(java.util.Collection roles) {
    this.roles = roles;
  }
  public String getRoleId() {
    return roleId;
  }
  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }
  public String getRoleName() {
    return roleName;
  }
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  public String getRecipientEmail() {
    return recipientEmail;
  }
  public void setRecipientEmail(String recipientEmail) {
    this.recipientEmail = recipientEmail;
  }
  public Long[] getRoleIds() {
    return roleIds;
  }
  public void setRoleIds(Long[] roleIds) {
    this.roleIds = roleIds;
  }

  public Long getNotificationId() {
    return notificationId;
  }
  public void setNotificationId(Long notificationId) {
    this.notificationId = notificationId;
  }
  public String getPlaceHolderId() {
    return placeHolderId;
  }
  public void setPlaceHolderId(String placeHolderId) {
    this.placeHolderId = placeHolderId;
  }
  public String getVzwAccountManager() {
    return vzwAccountManager;
  }
  public void setVzwAccountManager(String vzwAccountManager) {
    this.vzwAccountManager = vzwAccountManager;
  }
  public String getAppManager() {
    return appManager;
  }
  public void setAppManager(String appManager) {
    this.appManager = appManager;
  }
  public String getEmailMessageId() {
    return emailMessageId;
  }
  public void setEmailMessageId(String emailMessageId) {
    this.emailMessageId = emailMessageId;
  }
  public org.apache.struts.upload.FormFile getEmailAttachment() {
    return emailAttachment;
  }
  public void setEmailAttachment(org.apache.struts.upload.FormFile emailAttachment) {
    this.emailAttachment = emailAttachment;
  }
  public String getEmailAttachmentName() {
    return emailAttachmentName;
  }
  public void setEmailAttachmentName(String emailAttachmentName) {
    this.emailAttachmentName = emailAttachmentName;
  }
  public String getEmailAttachmentType() {
    return emailAttachmentType;
  }
  public void setEmailAttachmentType(String emailAttachmentType) {
    this.emailAttachmentType = emailAttachmentType;
  }


}
