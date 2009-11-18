package com.netpace.aims.controller.application;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.netpace.aims.util.AimsConstants;

/** @author Ahson Imtiaz */
public class AllianceApplicationDeviceExt implements Serializable {

	private static int autoId;

    /** identifier field */
    private java.lang.Long brewAppsId;
    
    /** identifier field */
    private java.lang.Long deviceId;

    /** nullable persistent field */
    private java.lang.String version;
    
    /** nullable persistent field */
    private java.lang.String title;

    /** nullable persistent field */
    private java.lang.String companyName;
    
    /** nullable persistent field */
    private java.lang.String deviceModel;
    
    private java.lang.Long appStatus;
    
	static
	{
		autoId = 1;
	}

	/** default constructor */
    public AllianceApplicationDeviceExt() {
		autoId++;
    }

    public java.lang.Long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(java.lang.Long deviceId) {
        this.deviceId = deviceId;
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
    
    public java.lang.String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(java.lang.String deviceName) {
        this.deviceModel = deviceName;
    }
    
    public java.lang.Long getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(java.lang.Long input) {
        this.appStatus = input;
    }
    
    public java.lang.String getAppStatusString() {
        
        if (appStatus == null)
        	return "Unknown";
        else if (appStatus.equals(AimsConstants.SUBMISSION_ID))
        	return "Submitted";
        else if (appStatus.equals(AimsConstants.SAVED_ID))
        	return "Saved";
        else if (appStatus.equals(AimsConstants.TESTING_ID))
        	return "Under Testing";
        else if (appStatus.equals(AimsConstants.ASSIGNED_ID))
        	return "Assigned";
        else if (appStatus.equals(AimsConstants.CERTIFICATION_ID))
        	return "Certified";
        else if (appStatus.equals(AimsConstants.ACCEPTANCE_ID))
        	return "Accepted";
        else if (appStatus.equals(AimsConstants.REJECTED_ID))
        	return "Rejected";
        else if (appStatus.equals(AimsConstants.EVALUATED_ID))
        	return "Evaluated";
        else if (appStatus.equals(AimsConstants.SUNSET_ID))
        	return "Sunset";
        
        return "unknown";
    }

    public String getDisplayValue()
    {
    	java.lang.StringBuffer sbReturn  = new java.lang.StringBuffer();
    	sbReturn.append(companyName).append(" * ").append(title).append(" * ").append(deviceModel).append(" * ").append(version);
    	return sbReturn.toString();
    }
    /* Generic Funcitons */
    public String toString() {
        return new ToStringBuilder(this)
            .append("Brew Apps Id", getBrewAppsId())
            .toString();
    }


    public boolean equals(Object other) {
        if ( !(other instanceof AllianceApplicationDeviceExt) ) return false;
        AllianceApplicationDeviceExt castOther = (AllianceApplicationDeviceExt) other;
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
