package com.netpace.aims.controller.application;

import java.io.Serializable;
import java.util.*;

/** @author Ahson Imtiaz */
public class BrewNstlDataLinkedDetails implements Serializable {


    /** nullable persistent field */
    private java.lang.String companyName;

    /** nullable persistent field */
    private java.lang.String applicationName;

    /** nullable persistent field */
    private java.lang.String deviceModel;

    /** nullable persistent field */
    private java.lang.String version;
    
	 private java.lang.String combinedId; // app-data-device Ids
	 
	/** default constructor */
    public BrewNstlDataLinkedDetails() {

    }

	 /* */
    public java.lang.String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String input) {
        this.companyName = input;
    }
    
	 /* */    
    public java.lang.String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(java.lang.String input) {
        this.applicationName = input;
    }

	 /* */
    public java.lang.String getDeviceModel() {
        return this.deviceModel;
    }

    public void setDeviceModel(java.lang.String input) {
        this.deviceModel = input;
    }

    /* */
    public java.lang.String getVersion() {
        return this.version;
    }

    public void setVersion(java.lang.String input) {
        this.version = input;
    }
    
	 /* */    
    public java.lang.String getCombinedId() {
        return this.combinedId;
    }

    public void setCombinedId(java.lang.String input) {
        this.combinedId = input;
    }
    
}
