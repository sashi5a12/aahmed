package com.netpace.aims.ca.model;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class CachedSuite {	
	private String startTime;
	private String endTime;
	private String dontsend;
	private String fileName;
	private String scpServer;
	private String scpServerUserName;
	private String scpServerPassword;
	private boolean autoGenFile;	
	
	public boolean isAutoGenFile() {
		return autoGenFile;
	}
	public void setAutoGenFile(boolean autoGenFile) {
		this.autoGenFile = autoGenFile;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDontsend() {
		return dontsend;
	}	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setDontsend(String dontsend) {
		this.dontsend = dontsend;
	}
	public String getScpServer() {
		return scpServer;
	}
	public void setScpServer(String scpServer) {
		this.scpServer = scpServer;
	}
	public String getScpServerUserName() {
		return scpServerUserName;
	}
	public void setScpServerUserName(String scpServerUserName) {
		this.scpServerUserName = scpServerUserName;
	}
	public String getScpServerPassword() {
		return scpServerPassword;
	}
	public void setScpServerPassword(String scpServerPassword) {
		this.scpServerPassword = scpServerPassword;
	}
}
