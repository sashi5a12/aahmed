package com.netpace.vzdn.model;

import java.io.Serializable;

public class VzdnNewsletterRecieverLog implements Serializable{
	
	private static final long serialVersionUID = 9095923016236250747L;
	
	private Integer newsletterRecieverId;	
	private String emailAddress;
	private VzdnNewsletterEmailLog vzdnNewsletterEmailLog;
	
	public VzdnNewsletterRecieverLog(String email){
		this.emailAddress = email;
	}
	
	public Integer getNewsletterRecieverId() {
		return newsletterRecieverId;
	}
	public void setNewsletterRecieverId(Integer newsletterRecieverId) {
		this.newsletterRecieverId = newsletterRecieverId;
	}	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public VzdnNewsletterEmailLog getVzdnNewsletterEmailLog() {
		return vzdnNewsletterEmailLog;
	}
	public void setVzdnNewsletterEmailLog(
			VzdnNewsletterEmailLog vzdnNewsletterEmailLog) {
		this.vzdnNewsletterEmailLog = vzdnNewsletterEmailLog;
	}
	
}
