package com.netpace.aims.controller.system;

import org.apache.log4j.Logger;

/**
 * @struts.form name="EmailGroupDetailsBean"
 */
public class EmailGroupDetailsBean
{

    static Logger log = Logger.getLogger(EmailGroupDetailsBean.class.getName());

    private String emailGroupId;
    private String emailGroupTitle;
    private String emailGroupDesc;
    private String emailGroupType;
    private String lastEmailSentDate;
    private String createdDate;
    private String lastUpdatedDate;
    private String createdBy;
    private String lastUpdatedBy;

    private String task;

    private java.util.List availableContactTypes;
    private java.util.List assignedContactTypes;

    private java.util.List availableContractStatus;
    private java.util.List assignedContractStatus;

    private java.util.List availableContracts;
    private java.util.List assignedContracts;

    private java.util.List availablePlatforms;
    private java.util.List assignedPlatforms;

    public String getEmailGroupId()
    {
        return emailGroupId;
    }

    public void setEmailGroupId(String emailGroupId)
    {
        this.emailGroupId = emailGroupId;
    }

    public String getEmailGroupTitle()
    {
        return emailGroupTitle;
    }

    public void setEmailGroupTitle(String emailGroupTitle)
    {
        this.emailGroupTitle = emailGroupTitle;
    }

    public String getEmailGroupDesc()
    {
        return emailGroupDesc;
    }

    public void setEmailGroupDesc(String emailGroupDesc)
    {
        this.emailGroupDesc = emailGroupDesc;
    }

    public String getEmailGroupType()
    {
        return emailGroupType;
    }

    public void setEmailGroupType(String emailGroupType)
    {
        this.emailGroupType = emailGroupType;
    }

    public String getLastEmailSentDate()
    {
        return lastEmailSentDate;
    }

    public void setLastEmailSentDate(String lastEmailSentDate)
    {
        this.lastEmailSentDate = lastEmailSentDate;
    }

    public java.lang.String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

    public java.lang.String getLastUpdatedBy()
    {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate()
    {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate)
    {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }

    public java.util.List getAvailableContactTypes()
    {
        return this.availableContactTypes;
    }

    public void setAvailableContactTypes(java.util.List availableContactTypes)
    {
        this.availableContactTypes = availableContactTypes;
    }

    public java.util.List getAssignedContactTypes()
    {
        return this.assignedContactTypes;
    }

    public void setAssignedContactTypes(java.util.List assignedContactTypes)
    {
        this.assignedContactTypes = assignedContactTypes;
    }

    public java.util.List getAvailableContractStatus()
    {
        return this.availableContractStatus;
    }

    public void setAvailableContractStatus(java.util.List availableContractStatus)
    {
        this.availableContractStatus = availableContractStatus;
    }

    public java.util.List getAssignedContractStatus()
    {
        return this.assignedContractStatus;
    }

    public void setAssignedContractStatus(java.util.List assignedContractStatus)
    {
        this.assignedContractStatus = assignedContractStatus;
    }

    public java.util.List getAvailableContracts()
    {
        return this.availableContracts;
    }

    public void setAvailableContracts(java.util.List availableContracts)
    {
        this.availableContracts = availableContracts;
    }

    public java.util.List getAssignedContracts()
    {
        return this.assignedContracts;
    }

    public void setAssignedContracts(java.util.List assignedContracts)
    {
        this.assignedContracts = assignedContracts;
    }

    public java.util.List getAvailablePlatforms()
    {
        return this.availablePlatforms;
    }

    public void setAvailablePlatforms(java.util.List availablePlatforms)
    {
        this.availablePlatforms = availablePlatforms;
    }

    public java.util.List getAssignedPlatforms()
    {
        return this.assignedPlatforms;
    }

    public void setAssignedPlatforms(java.util.List assignedPlatforms)
    {
        this.assignedPlatforms = assignedPlatforms;
    }
}
