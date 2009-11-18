package com.netpace.vzdn.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class DeviceAnyWhere implements java.io.Serializable{
	
	
	
	private Integer id;
	private String emailAddress;
	private Timestamp datetime;
	private String gtmCompanyId;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
	
	public Timestamp getDatetime() {
		return datetime;
	}
	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
	public String getGtmCompanyId() {
		return gtmCompanyId;
	}
	public void setGtmCompanyId(String gtmCompanyId) {
		this.gtmCompanyId = gtmCompanyId;
	}
	
	
	
	
	
	

}
