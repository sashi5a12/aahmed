package com.netpace.aims.controller.devices;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="ProgLangEditForm"
 */
public class ProgLangEditForm extends BaseValidatorForm
{

    /** identifier field */
    private java.lang.String langId;

    /** persistent field */
    private java.lang.String langName;

    /** nullable persistent field */
    private java.lang.String langDesc;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    private String[] platform;

    private String[] allplatforms;

    public java.lang.String getLangId()
    {
        return this.langId;
    }

    public void setLangId(java.lang.String langId)
    {
        this.langId = langId;
    }

    public java.lang.String getLangName()
    {
        return this.langName;
    }

    public void setLangName(java.lang.String langName)
    {
        this.langName = langName;
    }

    public java.lang.String getLangDesc()
    {
        return this.langDesc;
    }

    public void setLangDesc(java.lang.String langDesc)
    {
        this.langDesc = langDesc;
    }

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

    public String[] getPlatform()
    {
        return this.platform;
    }

    public void setPlatform(String[] platform)
    {
        this.platform = platform;
    }

    public String[] getAllplatforms()
    {
        return this.allplatforms;
    }

    public void setAllplatforms(String[] allplatforms)
    {
        this.allplatforms = allplatforms;
    }

}
