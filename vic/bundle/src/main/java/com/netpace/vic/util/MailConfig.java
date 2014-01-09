package com.netpace.vic.util;

public class MailConfig {

	private String from;
	private String to;
	private String mailServer;
	private String plainContent;
	private String htmlContent;
	private String subject;
	
	public MailConfig(){
		from = VICConstants.MAIL_FROM;
		to =VICConstants.VERIZON_MAIL_TO;
		mailServer = VICConstants.MAIL_SERVER;
	}

	public MailConfig(String from, String to, String mailServer,
			String plainContent, String htmlContent, String subject) {
		
		this.from = from;
		this.to = to;
		this.mailServer = mailServer;
		this.plainContent = plainContent;
		this.htmlContent = htmlContent;
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getPlainContent() {
		return plainContent;
	}

	public void setPlainContent(String plainContent) {
		this.plainContent = plainContent;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

		
}
