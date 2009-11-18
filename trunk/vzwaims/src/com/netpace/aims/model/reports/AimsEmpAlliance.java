package com.netpace.aims.model.reports;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


public class AimsEmpAlliance extends com.netpace.aims.model.BaseValueObject implements Serializable {



    private java.lang.String allianceName;

    private java.lang.String allianceCreatedDate;

    private java.lang.String allianceStatus;

    private java.lang.String cntSubmittedApps;

    private java.lang.String cntLiveApps;

    private java.lang.String platformName;

    private java.lang.String appName;

    private java.lang.String appCreatedDate;

    private java.lang.String appStatus;



    public java.lang.String getAllianceName() {
        return this.allianceName;
    }

    public void setAllianceName(java.lang.String allianceName) {
        this.allianceName = allianceName;
    }

    public java.lang.String getAllianceCreatedDate() {
        return this.allianceCreatedDate;
    }

    public void setAllianceCreatedDate(java.lang.String allianceCreatedDate) {
        this.allianceCreatedDate = allianceCreatedDate;
    }

    public java.lang.String getAllianceStatus() {
        return this.allianceStatus;
    }

    public void setAllianceStatus(java.lang.String allianceStatus) {
        this.allianceStatus = allianceStatus;
    }

    public java.lang.String getCntSubmittedApps() {
        return this.cntSubmittedApps;
    }

    public void setCntSubmittedApps(java.lang.String cntSubmittedApps) {
        this.cntSubmittedApps = cntSubmittedApps;
    }

    public java.lang.String getCntLiveApps() {
        return this.cntLiveApps;
    }

    public void setCntLiveApps(java.lang.String cntLiveApps) {
        this.cntLiveApps = cntLiveApps;
    }

    public java.lang.String getPlatformName() {
        return this.platformName;
    }

    public void setPlatformName(java.lang.String platformName) {
        this.platformName = platformName;
    }

    public java.lang.String getAppName() {
        return this.appName;
    }

    public void setAppName(java.lang.String appName) {
        this.appName = appName;
    }

    public java.lang.String getAppCreatedDate() {
        return this.appCreatedDate;
    }

    public void setAppCreatedDate(java.lang.String appCreatedDate) {
        this.appCreatedDate = appCreatedDate;
    }

    public java.lang.String getAppStatus() {
        return this.appStatus;
    }

    public void setAppStatus(java.lang.String appStatus) {
        this.appStatus = appStatus;
    }



    public String toString() {
        return new ToStringBuilder(this)
            .append("allianceName", getAllianceName())
            .toString();
    }


}
