package com.netpace.aims.controller.content;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;

/**
 * @struts.form name="FAQCategoryForm"
 */

public class FAQCategoryForm extends BaseValidatorForm
{

    private String faqCategoryId;

    private String faqCategoryName;

    private String faqCategoryDesc;

    private String createdBy;

    private String createdDate;
    private String task;
    private String platformId;
    private com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO[] platformList;
    private String platformName;
    private java.util.Collection faqCategories;

    public String getCreatedBy()
    {
        return createdBy;
    }
    public String getCreatedDate()
    {
        return createdDate;
    }
    public String getFaqCategoryDesc()
    {
        return faqCategoryDesc;
    }
    public String getFaqCategoryName()
    {
        return faqCategoryName;
    }
    public String getPlatformId()
    {
        return platformId;
    }
    public void setPlatformId(String platformId)
    {
        this.platformId = platformId;
    }
    public void setFaqCategoryName(String faqCategoryName)
    {
        this.faqCategoryName = faqCategoryName;
    }
    public void setFaqCategoryId(String faqCategoryId)
    {
        this.faqCategoryId = faqCategoryId;
    }
    public void setFaqCategoryDesc(String faqCategoryDesc)
    {
        this.faqCategoryDesc = faqCategoryDesc;
    }
    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public String getTask()
    {
        return task;
    }
    public void setTask(String task)
    {
        this.task = task;
    }
    public com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO[] getPlatformList()
    {
        return platformList;
    }
    public void setPlatformList(com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO[] platformList)
    {
        this.platformList = platformList;
    }
    public String getFaqCategoryId()
    {
        return faqCategoryId;
    }
    public String getPlatformName()
    {
        return platformName;
    }
    public void setPlatformName(String platformName)
    {
        this.platformName = platformName;
    }
    public java.util.Collection getFaqCategories()
    {
        return faqCategories;
    }
    public void setFaqCategories(java.util.Collection faqCategories)
    {
        this.faqCategories = faqCategories;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        if (this.isBlankString(this.faqCategoryName))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.faqCategoryName"));

        }
        this.setPlatformList(VOLookupFactory.getInstance().getAimsPlatformList());
        return errors;
    }

}