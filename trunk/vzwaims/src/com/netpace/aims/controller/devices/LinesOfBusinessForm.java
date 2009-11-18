package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsLinesOfBusinessManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.masters.AimsLinesOfBusiness;

/**
 * @struts.form name="LinesOfBusinessForm"
 */
public class LinesOfBusinessForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(LinesOfBusinessForm.class.getName());

    /** identifier field */
    private java.lang.Long lineOfBusinessId;

    /** persistent field */
    private java.lang.String lineOfBusinessName;

    /** nullable persistent field */
    private java.lang.String lineOfBusinessDesc;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    public java.lang.Long getLineOfBusinessId()
    {
        return this.lineOfBusinessId;
    }

    public void setLineOfBusinessId(java.lang.Long lineOfBusinessId)
    {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public java.lang.String getLineOfBusinessName()
    {
        return this.lineOfBusinessName;
    }

    public void setLineOfBusinessName(java.lang.String lineOfBusinessName)
    {
        this.lineOfBusinessName = lineOfBusinessName;
    }

    public java.lang.String getLineOfBusinessDesc()
    {
        return this.lineOfBusinessDesc;
    }

    public void setLineOfBusinessDesc(java.lang.String lineOfBusinessDesc)
    {
        this.lineOfBusinessDesc = lineOfBusinessDesc;
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

        AimsLinesOfBusiness aimsLinesOfBusiness = null;
        HttpSession session = request.getSession();

        if (request.getParameter("task").equalsIgnoreCase("editForm"))
        {

            try
            {
                aimsLinesOfBusiness = AimsLinesOfBusinessManager.getLineOfBusiness(Integer.parseInt(request.getParameter("linesOfBusinessId")));
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

            if (aimsLinesOfBusiness != null)
            {
                session.setAttribute("AimsLinesOfBusiness", aimsLinesOfBusiness);
                this.lineOfBusinessId = aimsLinesOfBusiness.getLineOfBusinessId();
                this.lineOfBusinessName = aimsLinesOfBusiness.getLineOfBusinessName();
                this.lineOfBusinessDesc = aimsLinesOfBusiness.getLineOfBusinessDesc();
            }
        }
    }

}
