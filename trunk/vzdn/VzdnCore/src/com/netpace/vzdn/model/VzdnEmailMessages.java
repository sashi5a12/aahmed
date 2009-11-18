package com.netpace.vzdn.model;

import java.sql.Timestamp;

public class VzdnEmailMessages implements java.io.Serializable, Comparable<VzdnEmailMessages>{

	private Integer emailMessageId;
	private String emailTitle;
	private String emailText;
	private String emailDesc;
	private String emailCategory;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String emailAttachment;
	private String fromAddress;

	/** default constructor */
	public VzdnEmailMessages() {
	}

	public Integer getEmailMessageId() {
		return this.emailMessageId;
	}

	public void setEmailMessageId(Integer emailMessageId) {
		this.emailMessageId = emailMessageId;
	}

	public String getEmailTitle() {
		return this.emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getEmailText() {
		return this.emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	public String getEmailDesc() {
		return this.emailDesc;
	}

	public void setEmailDesc(String emailDesc) {
		this.emailDesc = emailDesc;
	}

	public String getEmailCategory() {
		return this.emailCategory;
	}

	public void setEmailCategory(String emailCategory) {
		this.emailCategory = emailCategory;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getEmailAttachment() {
		return this.emailAttachment;
	}

	public void setEmailAttachment(String emailAttachment) {
		this.emailAttachment = emailAttachment;
	}

	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	
	public int compareTo(VzdnEmailMessages compareWith){
		return this.emailTitle.compareToIgnoreCase(compareWith.getEmailTitle());
	}

	

}