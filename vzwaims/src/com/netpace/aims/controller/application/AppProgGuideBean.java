package com.netpace.aims.controller.application;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class AppProgGuideBean extends com.netpace.aims.model.BaseValueObject implements Serializable {

	private java.lang.String appsId;

	private java.lang.String appTitle;
	
	private java.lang.String allianceId;
	
    private java.lang.String allianceName;

    private java.lang.String platformName;

    private java.lang.String appSubmittedDate;

    private java.lang.String appPhaseName;

    private java.lang.String progGuideName;
    
    private java.lang.String appCategoryName;
	
    private java.lang.String appSubCategoryName;
    
	/**
	 * @return Returns the allianceName.
	 */
	public java.lang.String getAllianceName() {
		return allianceName;
	}
	/**
	 * @param allianceName The allianceName to set.
	 */
	public void setAllianceName(java.lang.String allianceName) {
		this.allianceName = allianceName;
	}
	/**
	 * @return Returns the appCategoryName.
	 */
	public java.lang.String getAppCategoryName() {
		return appCategoryName;
	}
	/**
	 * @param appCategoryName The appCategoryName to set.
	 */
	public void setAppCategoryName(java.lang.String appCategoryName) {
		this.appCategoryName = appCategoryName;
	}
	/**
	 * @return Returns the appPhaseName.
	 */
	public java.lang.String getAppPhaseName() {
		return appPhaseName;
	}
	/**
	 * @param appPhaseName The appPhaseName to set.
	 */
	public void setAppPhaseName(java.lang.String appPhaseName) {
		this.appPhaseName = appPhaseName;
	}
	/**
	 * @return Returns the appsId.
	 */
	public java.lang.String getAppsId() {
		return appsId;
	}
	/**
	 * @param appsId The appsId to set.
	 */
	public void setAppsId(java.lang.String appsId) {
		this.appsId = appsId;
	}
	/**
	 * @return Returns the appSubCategoryName.
	 */
	public java.lang.String getAppSubCategoryName() {
		return appSubCategoryName;
	}
	/**
	 * @param appSubCategoryName The appSubCategoryName to set.
	 */
	public void setAppSubCategoryName(java.lang.String appSubCategoryName) {
		this.appSubCategoryName = appSubCategoryName;
	}
	/**
	 * @return Returns the appSubmittedDate.
	 */
	public java.lang.String getAppSubmittedDate() {
		return appSubmittedDate;
	}
	/**
	 * @param appSubmittedDate The appSubmittedDate to set.
	 */
	public void setAppSubmittedDate(java.lang.String appSubmittedDate) {
		this.appSubmittedDate = appSubmittedDate;
	}
	/**
	 * @return Returns the appTitle.
	 */
	public java.lang.String getAppTitle() {
		return appTitle;
	}
	/**
	 * @param appTitle The appTitle to set.
	 */
	public void setAppTitle(java.lang.String appTitle) {
		this.appTitle = appTitle;
	}
	/**
	 * @return Returns the platformName.
	 */
	public java.lang.String getPlatformName() {
		return platformName;
	}
	/**
	 * @param platformName The platformName to set.
	 */
	public void setPlatformName(java.lang.String platformName) {
		this.platformName = platformName;
	}
	/**
	 * @return Returns the progGuideName.
	 */
	public java.lang.String getProgGuideName() {
		return progGuideName;
	}
	/**
	 * @param progGuideName The progGuideName to set.
	 */
	public void setProgGuideName(java.lang.String progGuideName) {
		this.progGuideName = progGuideName;
	}


	/**
	 * @return Returns the allianceId.
	 */
	public java.lang.String getAllianceId() {
		return allianceId;
	}
	/**
	 * @param allianceId The allianceId to set.
	 */
	public void setAllianceId(java.lang.String allianceId) {
		this.allianceId = allianceId;
	}
}