package com.netpace.aims.controller.system;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="CertificationForm"
 */

public class CertificationForm extends BaseValidatorForm
{

    private java.lang.String createdBy;
    private java.lang.String lastUpdatedBy;
    private String createdDate;
    private String lastUpdatedDate;
    private String certId;
    private String certName;
    private String certDesc;
    private String certOrg;
    private String certUrl;
    private String task;

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
    public String getCertId()
    {
        return certId;
    }
    public void setCertId(String certId)
    {
        this.certId = certId;
    }
    public String getCertName()
    {
        return certName;
    }
    public void setCertName(String certName)
    {
        this.certName = certName;
    }
    public String getCertDesc()
    {
        return certDesc;
    }
    public void setCertDesc(String certDesc)
    {
        this.certDesc = certDesc;
    }
    public String getCertOrg()
    {
        return certOrg;
    }
    public void setCertOrg(String certOrg)
    {
        this.certOrg = certOrg;
    }
    public String getCertUrl()
    {
        return certUrl;
    }
    public void setCertUrl(String certUrl)
    {
        this.certUrl = certUrl;
    }
    public String getTask()
    {
        return task;
    }
    public void setTask(String task)
    {
        this.task = task;
    }

}
