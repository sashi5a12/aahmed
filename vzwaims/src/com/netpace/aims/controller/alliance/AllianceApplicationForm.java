package com.netpace.aims.controller.alliance;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.Date;
import java.util.Set;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class AllianceApplicationForm {
   
	private	 java.lang.Long 	appsId;
	private	 java.lang.String 	appTitle;
	private	 java.lang.String 	appVersion;
	private	 java.lang.String 	appPlatformName;
	private	 java.lang.String 	appPphaseName;
	private	 java.lang.String 	submittedFirstName;
	private	 java.lang.String 	submittedLastName;
	private	 java.util.Date		submittedDate;
	private  java.lang.String   urlSetupAction;
	private  java.lang.Long 	phaseId;


	public  java.lang.Long  getAppsId() {
        return this.appsId;
    }

    public void setAppsId( java.lang.Long  appsId) {
        this.appsId = appsId;
    }

	public  java.lang.String  getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle( java.lang.String  appTitle) {
        this.appTitle = appTitle;
    }

	public  java.lang.String  getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion( java.lang.String  appVersion) {
        this.appVersion = appVersion;
    }

	public  java.lang.String  getAppPlatformName() {
        return this.appPlatformName;
    }

    public void setAppPlatformName( java.lang.String  appPlatformName) {
        this.appPlatformName = appPlatformName;
    }

	public  java.lang.String  getAppPphaseName() {
        return this.appPphaseName;
    }

    public void setAppPphaseName( java.lang.String  appPphaseName) {
        this.appPphaseName = appPphaseName;
    }

	public  java.lang.String  getSubmittedFirstName() {
        return this.submittedFirstName;
    }

    public void setSubmittedFirstName( java.lang.String  submittedFirstName) {
        this.submittedFirstName = submittedFirstName;
    }

	public  java.lang.String  getSubmittedLastName() {
        return this.submittedLastName;
    }

    public void setSubmittedLastName( java.lang.String  submittedLastName) {
        this.submittedLastName = submittedLastName;
    }

	public  java.util.Date getSubmittedDate() {
        return this.submittedDate;
    }

    public void setSubmittedDate( java.util.Date submittedDate) {
        this.submittedDate = submittedDate;
    }

	public java.lang.String getUrlSetupAction() {
		return urlSetupAction;
	}

	public void setUrlSetupAction(java.lang.String urlSetupAction) {
		this.urlSetupAction = urlSetupAction;
	}

	public java.lang.Long getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(java.lang.Long phaseId) {
		this.phaseId = phaseId;
	}   
    
}

