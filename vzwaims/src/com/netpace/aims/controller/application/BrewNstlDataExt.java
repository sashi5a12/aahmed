package com.netpace.aims.controller.application;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.*;

/** @author Ahson Imtiaz */
public class BrewNstlDataExt implements Serializable {

	private static int autoId;

    /** identifier field */
    private java.lang.Long dataEntryId;

    /** nullable persistent field */
    private java.lang.String developerName;

    /** nullable persistent field */
    private java.lang.String applicationName;

    /** nullable persistent field */
    private java.lang.String handset;

    /** nullable persistent field */
    private java.lang.String version;
    
    /** identifier field */
    private java.lang.Long brewAppsId;
    
    /** identifier field */
    private java.lang.Long brewNstlId;
    
    /** nullable persistent field */
    private java.lang.String title;

    /** nullable persistent field */
    private java.lang.String companyName;
    
    /** nullable persistent field */
    private java.lang.String appVersion;
    
    /** nullable persistent field */
    private java.lang.String appTitle;
    
    /** nullable persistent field */
    private java.lang.String deviceModel;
    
    /** nullable persistent field */
    private Collection supportedDevices;
    
    /** nullable persistent field */
    private Collection possibleMatch;
    
    /** For Linked Record Information */
    private BrewNstlDataLinkedDetails linkedDetails;
    
    /** For Linked Record Information */
    private BrewNstlDataLinkedDetails definedMatch;
    
	static
	{
		autoId = 1;
	}

	/** default constructor */
    public BrewNstlDataExt() {
		autoId++;
    }

    public java.lang.Long getDataEntryId() {
        return this.dataEntryId;
    }

    public void setDataEntryId(java.lang.Long dataEntryId) {
        this.dataEntryId = dataEntryId;
    }

    public java.lang.Long getBrewNstlId() {
        return this.brewNstlId;
    }

    public void setBrewNstlId(java.lang.Long brewNstlId) {
        this.brewNstlId = brewNstlId;
    }

    public java.lang.String getDeveloperName() {
        return this.developerName;
    }

    public void setDeveloperName(java.lang.String developerName) {
        this.developerName = developerName;
    }

    public java.lang.String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(java.lang.String applicationName) {
        this.applicationName = applicationName;
    }

    public java.lang.String getHandset() {
        return this.handset;
    }

    public void setHandset(java.lang.String handset) {
        this.handset = handset;
    }


    public java.lang.String getVersion() {
        return this.version;
    }

    public void setVersion(java.lang.String version) {
        this.version = version;
    }

    public java.lang.Long getBrewAppsId() {
        return this.brewAppsId;
    }

    public void setBrewAppsId(java.lang.Long brewAppsId) {
        this.brewAppsId = brewAppsId;
    }
    
    public java.lang.String getTitle() {
        return this.title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }
    
    public java.lang.String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }
    
    public java.lang.String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(java.lang.String version) {
        this.appVersion = version;
    }

    public java.lang.String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(java.lang.String appTitle) {
        this.appTitle = appTitle;
    }
    
    /**/
    public java.lang.String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(java.lang.String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /**/
    public Collection getSupportedDevices() {
        return this.supportedDevices;
    }

    public void setSupportedDevices(Collection devices) {
        this.supportedDevices = devices;
    }
    
    /**/
    public Collection getPossibleMatch() {
        return this.possibleMatch;
    }

    public void setPossibleMatch(Collection matches) {
    	  if (matches != null && matches.size() > 0)
        	this.possibleMatch = matches;
    }
    
    /**/
    public BrewNstlDataLinkedDetails getLinkedDetails() {
        return this.linkedDetails;
    }

    public void setLinkedDetails(BrewNstlDataLinkedDetails lDetails) {
        this.linkedDetails = lDetails;
    }
    
    /**/
    public BrewNstlDataLinkedDetails getDefinedMatch() {
        return this.definedMatch;
    }

    public void setDefinedMatch(BrewNstlDataLinkedDetails dDetails) {
        this.definedMatch = dDetails;
    }

    /* Generic Funcitons */
    public String toString() {
        return new ToStringBuilder(this)
            .append("Application Name", getApplicationName())
            .toString();
    }


    public boolean equals(Object other) {
        if ( !(other instanceof BrewNstlDataExt) ) return false;
        BrewNstlDataExt castOther = (BrewNstlDataExt) other;
        return new EqualsBuilder()
            .append(autoId, castOther.autoId)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(autoId)
            .toHashCode();
    }

}
