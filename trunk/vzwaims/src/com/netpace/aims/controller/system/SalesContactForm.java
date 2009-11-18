package com.netpace.aims.controller.system;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="SalesContactForm"
 */

public class SalesContactForm extends BaseValidatorForm
{

    private java.lang.String createdBy;

    private java.util.Date createdDate;

    private java.lang.String lastUpdatedBy;

    private java.util.Date lastUpdatedDate;
    private String task;
    private String regionName;
    private String firstName;
    private String lastName;
    private String title;
    private String phone;
    private String emailAddress;
    private String salesContactName;
    private String salesContactId;
    private String regionId;
    private com.netpace.aims.dataaccess.valueobjects.AimsRegionVO[] regionList;

    public java.lang.String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy)
    {
        this.createdBy = createdBy;
    }

    public java.util.Date getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate)
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

    public java.util.Date getLastUpdatedDate()
    {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.util.Date lastUpdatedDate)
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
    public String getRegionName()
    {
        return regionName;
    }
    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getEmailAddress()
    {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }
    public String getSalesContactName()
    {
        return salesContactName;
    }
    public void setSalesContactName(String salesContactName)
    {
        this.salesContactName = salesContactName;
    }
    public String getSalesContactId()
    {
        return salesContactId;
    }
    public void setSalesContactId(String salesContactId)
    {
        this.salesContactId = salesContactId;
    }
    public String getRegionId()
    {
        return regionId;
    }
    public void setRegionId(String regionId)
    {
        this.regionId = regionId;
    }
    public com.netpace.aims.dataaccess.valueobjects.AimsRegionVO[] getRegionList()
    {
        return regionList;
    }
    public void setRegionList(com.netpace.aims.dataaccess.valueobjects.AimsRegionVO[] regionList)
    {
        this.regionList = regionList;
    }

}
