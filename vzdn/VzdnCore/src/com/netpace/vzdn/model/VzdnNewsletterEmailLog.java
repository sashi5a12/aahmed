package com.netpace.vzdn.model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnNewsletterEmailLog implements java.io.Serializable {

	private static final long serialVersionUID = 672940497864156756L;
	
	private Integer newsLetterLogId;
	private Integer userId;
	private Timestamp emailDateTime;
	private String emailSubject;
	private Blob emailAttachment;
	private String emailContent;
	private Timestamp createdDate;
	private String createdBy;
	
	private Set<VzdnNewsletterRecieverLog> recieversList = new HashSet<VzdnNewsletterRecieverLog>(0);
	
	
	private String emailAddresses;
	
	public Integer getNewsLetterLogId() {
		return newsLetterLogId;
	}
	public void setNewsLetterLogId(Integer newsLetterLogId) {
		this.newsLetterLogId = newsLetterLogId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Timestamp getEmailDateTime() {
		return emailDateTime;
	}
	public void setEmailDateTime(Timestamp emailDateTime) {
		this.emailDateTime = emailDateTime;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public Blob getEmailAttachment() {
		return emailAttachment;
	}
	public void setEmailAttachment(Blob emailAttachment) {
		this.emailAttachment = emailAttachment;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getEmailAddresses() {
		return emailAddresses;
	}
	public void setEmailAddresses(String emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	public Set<VzdnNewsletterRecieverLog> getRecieversList() {
		return recieversList;
	}
	public void setRecieversList(Set<VzdnNewsletterRecieverLog> recieversList) {
		this.recieversList = recieversList;
	}
	
}