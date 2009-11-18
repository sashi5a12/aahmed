package com.netpace.aims.dataaccess.valueobjects;

import org.apache.log4j.Logger;

import java.util.Date;

public class AllianceContractInfoVO {

    private static Logger log = Logger.getLogger(AllianceContractInfoVO.class.getName());

    private Long contractId;
    private	Long allianceContractId;
    private String contractTitle;
    private String contractVersion;
    private String clickThroughContract;
    private String contractStatus;
    private Date contractExpDate;
    private Date allianceContractPresentDate;
    private String allianceContractStatus;
    private Date allianceContractAcceptDeclineDate;
    private String acceptDeclineSignName;
    private String acceptDeclineSignTitle;
    private Date allianceContractExpDate;
    private String allianceContractComments;
    private String contractPlatformName;
    private String acceptDeclineFirstName;
    private String acceptDeclineLastName;
    private String vzwAccountManagerFirstName;
    private String vzwAccountManagerLastName;
    private String documentFileName;
    private String completeDocumentFileName;
    private String modifiedContDocFileName;


    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getAllianceContractId() {
        return allianceContractId;
    }

    public void setAllianceContractId(Long allianceContractId) {
        this.allianceContractId = allianceContractId;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
    }
        
    public String getClickThroughContract() {
        return clickThroughContract;
    }

    public void setClickThroughContract(String clickThroughContract) {
        this.clickThroughContract = clickThroughContract;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getContractExpDate() {
        return contractExpDate;
    }

    public void setContractExpDate(Date contractExpDate) {
        this.contractExpDate = contractExpDate;
    }

    public Date getAllianceContractPresentDate() {
        return allianceContractPresentDate;
    }

    public void setAllianceContractPresentDate(Date allianceContractPresentDate) {
        this.allianceContractPresentDate = allianceContractPresentDate;
    }

    public String getAllianceContractStatus() {
        return allianceContractStatus;
    }

    public void setAllianceContractStatus(String allianceContractStatus) {
        this.allianceContractStatus = allianceContractStatus;
    }

    public Date getAllianceContractAcceptDeclineDate() {
        return allianceContractAcceptDeclineDate;
    }

    public void setAllianceContractAcceptDeclineDate(Date allianceContractAcceptDeclineDate) {
        this.allianceContractAcceptDeclineDate = allianceContractAcceptDeclineDate;
    }

    public String getAcceptDeclineSignName() {
        return acceptDeclineSignName;
    }

    public void setAcceptDeclineSignName(String acceptDeclineSignName) {
        this.acceptDeclineSignName = acceptDeclineSignName;
    }

    public String getAcceptDeclineSignTitle() {
        return acceptDeclineSignTitle;
    }

    public void setAcceptDeclineSignTitle(String acceptDeclineSignTitle) {
        this.acceptDeclineSignTitle = acceptDeclineSignTitle;
    }

    public Date getAllianceContractExpDate() {
        return allianceContractExpDate;
    }

    public void setAllianceContractExpDate(Date allianceContractExpDate) {
        this.allianceContractExpDate = allianceContractExpDate;
    }

    public String getAllianceContractComments() {
        return allianceContractComments;
    }

    public void setAllianceContractComments(String allianceContractComments) {
        this.allianceContractComments = allianceContractComments;
    }

    public String getContractPlatformName() {
        return contractPlatformName;
    }

    public void setContractPlatformName(String contractPlatformName) {
        this.contractPlatformName = contractPlatformName;
    }

    public String getAcceptDeclineFirstName() {
        return acceptDeclineFirstName;
    }

    public void setAcceptDeclineFirstName(String acceptDeclineFirstName) {
        this.acceptDeclineFirstName = acceptDeclineFirstName;
    }

    public String getAcceptDeclineLastName() {
        return acceptDeclineLastName;
    }

    public void setAcceptDeclineLastName(String acceptDeclineLastName) {
        this.acceptDeclineLastName = acceptDeclineLastName;
    }

    public String getVzwAccountManagerFirstName() {
        return vzwAccountManagerFirstName;
    }

    public void setVzwAccountManagerFirstName(String vzwAccountManagerFirstName) {
        this.vzwAccountManagerFirstName = vzwAccountManagerFirstName;
    }

    public String getVzwAccountManagerLastName() {
        return vzwAccountManagerLastName;
    }

    public void setVzwAccountManagerLastName(String vzwAccountManagerLastName) {
        this.vzwAccountManagerLastName = vzwAccountManagerLastName;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public String getCompleteDocumentFileName() {
        return completeDocumentFileName;
    }

    public void setCompleteDocumentFileName(String completeDocumentFileName) {
        this.completeDocumentFileName = completeDocumentFileName;
    }

    public String getModifiedContDocFileName() {
        return modifiedContDocFileName;
    }

    public void setModifiedContDocFileName(String modifiedContDocFileName) {
        this.modifiedContDocFileName = modifiedContDocFileName;
    }
}
