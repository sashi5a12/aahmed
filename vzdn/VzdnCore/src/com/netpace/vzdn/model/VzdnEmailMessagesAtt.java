package com.netpace.vzdn.model;

import java.sql.Blob;
import java.sql.Timestamp;

public class VzdnEmailMessagesAtt implements java.io.Serializable {

	private Integer emailMessageId;
	private Blob att;
	private String attFileName;
	private String attContentType;
	private String createdBy;
	private Timestamp createdDate;

	/** default constructor */
	public VzdnEmailMessagesAtt() {
	}

	/** minimal constructor */
	public VzdnEmailMessagesAtt(Integer emailMessageId) {
		this.emailMessageId = emailMessageId;
	}

	public Integer getEmailMessageId() {
		return this.emailMessageId;
	}

	public void setEmailMessageId(Integer emailMessageId) {
		this.emailMessageId = emailMessageId;
	}

	public Blob getAtt() {
		return this.att;
	}

	public void setAtt(Blob att) {
		this.att = att;
	}

	public String getAttFileName() {
		return this.attFileName;
	}

	public void setAttFileName(String attFileName) {
		this.attFileName = attFileName;
	}

	public String getAttContentType() {
		return this.attContentType;
	}

	public void setAttContentType(String attContentType) {
		this.attContentType = attContentType;
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

}