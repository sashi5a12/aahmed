package com.netpace.aims.controller.devices;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsProgLangManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.masters.AimsProgrammingLang;

/**
 * @struts.form name="ProgLangForm"
 */
public class ProgLangForm extends BaseValidatorForm
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

    /** persistent field */
    private Set platform;

    /** persistent field */
    private Collection allplatforms;

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

    public java.util.Set getPlatform()
    {
        return this.platform;
    }

    public void setPlatform(java.util.Set platform)
    {
        this.platform = platform;
    }

    public Collection getAllplatforms()
    {
        return this.allplatforms;
    }

    public void setAllplatforms(Collection allplatforms)
    {
        this.allplatforms = allplatforms;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        System.out.println("The RESET is called in ProgLangForm!");

        AimsProgrammingLang AimsProgLang = null;
        Collection AimsPlatforms = null;
        HttpSession session = request.getSession(true);
        Session hibernateSession = null;

        if (request.getParameter("task").equalsIgnoreCase("editForm"))
        {

            try
            {
                AimsProgLang = AimsProgLangManager.getProgLang(Integer.parseInt(request.getParameter("langId")));
                AimsPlatforms = AimsProgLangManager.getPlatforms();
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }

            session.setAttribute("AimsProgLang", AimsProgLang);
            request.setAttribute("AimsProgLang", AimsProgLang);
            request.setAttribute("AimsPlatforms", AimsPlatforms);

        }

        if (request.getParameter("task").equalsIgnoreCase("createForm"))
        {

            try
            {
                AimsPlatforms = AimsProgLangManager.getPlatforms();
            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }

            request.setAttribute("AimsPlatforms", AimsPlatforms);

        }
    }

}
