package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsIndFocusManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.masters.AimsIndustryFocu;

/**
 * @struts.form name="IndFocusEditForm"
 */
public class IndFocusEditForm extends BaseValidatorForm
{

    /** identifier field */
    private java.lang.String industryId;

    /** persistent field */
    private java.lang.String industryName;

    /** nullable persistent field */
    private java.lang.String industryDescription;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    public java.lang.String getIndustryId()
    {
        return this.industryId;
    }

    public void setIndustryId(java.lang.String industryId)
    {
        this.industryId = industryId;
    }

    public java.lang.String getIndustryName()
    {
        return this.industryName;
    }

    public void setIndustryName(java.lang.String industryName)
    {
        this.industryName = industryName;
    }

    public java.lang.String getIndustryDescription()
    {
        return this.industryDescription;
    }

    public void setIndustryDescription(java.lang.String industryDescription)
    {
        this.industryDescription = industryDescription;
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

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        System.out.println("The RESET is called in IndFocusEditForm!");
        AimsIndustryFocu AimsIndFocuses = null;
        HttpSession session = request.getSession();

        if (request.getParameter("task").equalsIgnoreCase("editForm"))
        {

            try
            {
                AimsIndFocuses = AimsIndFocusManager.getIndFocus(Integer.parseInt(request.getParameter("industryId")));
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }
            session.setAttribute("AimsIndFocuses", AimsIndFocuses);

            this.setIndustryId(AimsIndFocuses.getIndustryId().toString());
            this.setIndustryName(AimsIndFocuses.getIndustryName());
            this.setIndustryDescription(AimsIndFocuses.getIndustryDescription());

        }

    }

}
