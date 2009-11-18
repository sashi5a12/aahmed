package com.netpace.aims.controller.application;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Ahson Imtiaz */
public class AimsAppsCaseStudyExt implements Serializable {

	private static int autoCaseStudyId;
    /** identifier field */
    private int caseStudyIdHash;

    private java.lang.Long caseStudyId;

    /** nullable persistent field */
    private java.lang.String customerName;

    /** nullable persistent field */
    private java.lang.String problemStatement;

    /** nullable persistent field */
    private java.lang.String businessBenifit;

    /** nullable persistent field */
    private java.lang.String platformName;

    /** nullable persistent field */
    private java.util.Date productionLaunchDate;

    /** nullable persistent field */
    private java.lang.Long numUsers;

    /** nullable persistent field */
    private java.lang.Long numWirelessUsers;

    /** nullable persistent field */
    private java.lang.Long platformId;

	private boolean isNewCaseStudy;

	static
	{
		autoCaseStudyId = 1;
	}

	/** default constructor */
    public AimsAppsCaseStudyExt() {
		isNewCaseStudy = true;
		caseStudyIdHash = autoCaseStudyId++;
    }

    /** minimal constructor */
    public AimsAppsCaseStudyExt(com.netpace.aims.model.application.AimsAppsCaseStudy aimsAppsCaseStudy) {

			caseStudyId = aimsAppsCaseStudy.getCaseStudyId();
            customerName = aimsAppsCaseStudy.getCustomerName();
		    problemStatement = aimsAppsCaseStudy.getProblemStatement();
			businessBenifit = aimsAppsCaseStudy.getBusinessBenifit();
		    productionLaunchDate = aimsAppsCaseStudy.getProductionLaunchDate();
		    numUsers = aimsAppsCaseStudy.getNumUsers();
		    numWirelessUsers = aimsAppsCaseStudy.getNumWirelessUsers();
		    platformId = aimsAppsCaseStudy.getPlatformId();
			isNewCaseStudy = false;
			caseStudyIdHash = autoCaseStudyId++;
    }

    public java.lang.Long getCaseStudyId() {
        return this.caseStudyId;
    }

    public void setCaseStudyId(java.lang.Long caseStudyId) {
        this.caseStudyId = caseStudyId;
    }

    /**/
    public java.lang.Long getCaseStudyIdHash() {
        return new java.lang.Long(this.caseStudyIdHash);
    }

    public void setCaseStudyIdHash(java.lang.Long caseStudyId) {
        this.caseStudyIdHash = caseStudyId.intValue();
    }

    public java.lang.String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(java.lang.String customerName) {
        this.customerName = customerName;
    }

    public java.lang.String getProblemStatement() {
        return this.problemStatement;
    }

    public void setProblemStatement(java.lang.String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public java.lang.String getBusinessBenifit() {
        return this.businessBenifit;
    }

    public void setBusinessBenifit(java.lang.String businessBenifit) {
        this.businessBenifit = businessBenifit;
    }

    public java.util.Date getProductionLaunchDate() {
        return this.productionLaunchDate;
    }

    public void setProductionLaunchDate(java.util.Date productionLaunchDate) {
        this.productionLaunchDate = productionLaunchDate;
    }

    public java.lang.Long getNumUsers() {
        return this.numUsers;
    }

    public void setNumUsers(java.lang.Long numUsers) {
        this.numUsers = numUsers;
    }

    public java.lang.Long getNumWirelessUsers() {
        return this.numWirelessUsers;
    }

    public void setNumWirelessUsers(java.lang.Long numWirelessUsers) {
        this.numWirelessUsers = numWirelessUsers;
    }

    public java.lang.Long getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(java.lang.Long platformId) {
        this.platformId = platformId;
    }

    public java.lang.String getPlatformName() {
        return this.platformName;
    }

    public void setPlatformName(java.lang.String platformName) {
        this.platformName = platformName;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("caseStudyId", getCaseStudyId())
            .toString();
    }

    public boolean getIsNewCaseStudy()
    {
		return isNewCaseStudy;
	}

    public boolean equals(Object other) {
        if ( !(other instanceof AimsAppsCaseStudyExt) ) return false;
        AimsAppsCaseStudyExt castOther = (AimsAppsCaseStudyExt) other;
        return new EqualsBuilder()
            .append(caseStudyIdHash, castOther.caseStudyIdHash)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(caseStudyIdHash)
            .toHashCode();
    }

    public void copy2AimsAppsCaseStudy(com.netpace.aims.model.application.AimsAppsCaseStudy aimsAppsCaseStudy) {

			//aimsAppsCaseStudy.setCaseStudyId(caseStudyId);
            aimsAppsCaseStudy.setCustomerName(customerName);
		    aimsAppsCaseStudy.setProblemStatement(problemStatement);
			aimsAppsCaseStudy.setBusinessBenifit(businessBenifit);
		    aimsAppsCaseStudy.setProductionLaunchDate(productionLaunchDate);
		    aimsAppsCaseStudy.setNumUsers(numUsers);
		    aimsAppsCaseStudy.setNumWirelessUsers(numWirelessUsers);
		    aimsAppsCaseStudy.setPlatformId(platformId);
    }

}
