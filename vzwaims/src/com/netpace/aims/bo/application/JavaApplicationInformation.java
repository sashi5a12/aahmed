package com.netpace.aims.bo.application;

import java.util.Date;

/** This class represents the Applicaiton Information for java application
 * * @author mnauman
 */

public class JavaApplicationInformation {
	
	private ApplicationInformation appinfo ;
	
	private String applicationKeyWord;

	public String getApplicationKeyWord() {
		return applicationKeyWord;
	}

	public void setApplicationKeyWord(String applicationKeyWord) {
		this.applicationKeyWord = applicationKeyWord;
	}

	public void setAppinfo(ApplicationInformation appinfo) {
		this.appinfo = appinfo;
	}

	public Long getAllianceId() {
		return appinfo.getAllianceId();
	}

	public boolean getAllowCreate() {
		return appinfo.getAllowCreate();
	}

	public boolean getAllowDelete() {
		return appinfo.getAllowDelete();
	}

	public boolean getAllowEdit() {
		return appinfo.getAllowEdit();
	}

	public Long getAppsId() {
		return appinfo.getAppsId();
	}

	public String getBrewAppType() {
		return appinfo.getBrewAppType();
	}

	public String getCategoryName() {
		return appinfo.getCategoryName();
	}

	public String getCompanyName() {
		return appinfo.getCompanyName();
	}

	public Date getCreatedDate() {
		return appinfo.getCreatedDate();
	}

	public String getIfOnHold() {
		return appinfo.getIfOnHold();
	}

	public boolean getIsLbs() {
		return appinfo.getIsLbs();
	}

	public Long getPhaseId() {
		return appinfo.getPhaseId();
	}

	public String getPhaseName() {
		return appinfo.getPhaseName();
	}

	public Long getPlatformId() {
		return appinfo.getPlatformId();
	}

	public String getPlatformName() {
		return appinfo.getPlatformName();
	}

	public String getTitle() {
		return appinfo.getTitle();
	}

	public String getUrlSetupAction() {
		return appinfo.getUrlSetupAction();
	}

	public String getVersion() {
		return appinfo.getVersion();
	}

	public void setTitle(String title) {
		appinfo.setTitle(title);
	}
}
