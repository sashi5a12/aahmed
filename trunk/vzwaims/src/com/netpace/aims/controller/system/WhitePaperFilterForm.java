package com.netpace.aims.controller.system;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceWhitePaperManager;

/**
 * @struts.form name="WhitePaperFilterForm"
 * @author Ahson Imtiaz.
 */
public class WhitePaperFilterForm extends com.netpace.aims.controller.BaseValidatorForm
{

    static Logger log = Logger.getLogger(WhitePaperFilterForm.class.getName());

    private Long allianceId;
    private String submittedDate;
    private Collection aimsAlliances;
    private String filterField;
    private String filterText;

    /* Public Getter and Setter Functions Starts*/

    /*  */
    public void setAllianceId(Long lngInput)
    {
        this.allianceId = lngInput;
    }

    public Long getAllianceId()
    {
        return this.allianceId;
    }

    /*  */
    public void setSubmittedDate(String strInput)
    {
        this.submittedDate = strInput;
    }

    public String getSubmittedDate()
    {
        return this.submittedDate;
    }

    /*  */
    public void setAimsAlliances(Collection cInput)
    {
        this.aimsAlliances = cInput;
    }

    public Collection getAimsAlliances()
    {
        return this.aimsAlliances;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        log.debug("In RESET White Paper Filter.");
        try
        {
            this.aimsAlliances = AllianceWhitePaperManager.getAimsAlliances();
        }
        catch (HibernateException ex)
        {
            this.aimsAlliances = new HashSet();
            log.debug("Exception in WhitePaperFilterForm Getting Alliances: " + ex);
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        if (!this.isBlankString(this.submittedDate) && !isDate(submittedDate))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.WhitePaperForm.dateNotValid"));

        if (!errors.isEmpty())
        {
            log.debug("Error Found");
            try
            {
                request.setAttribute("AimsWhitePapers", AllianceWhitePaperManager.getAimsAllianceWhitePapersList());
            }
            catch (HibernateException ex)
            {
                log.debug("Exception in WhitePaperFilterForm Getting WhitePapers List: " + ex);
            }
        }
        return errors;

    }
    public String getFilterField()
    {
        return filterField;
    }
    public void setFilterField(String filterField)
    {
        this.filterField = filterField;
    }
    public String getFilterText()
    {
        return filterText;
    }
    public void setFilterText(String filterText)
    {
        this.filterText = filterText;
    }

}
