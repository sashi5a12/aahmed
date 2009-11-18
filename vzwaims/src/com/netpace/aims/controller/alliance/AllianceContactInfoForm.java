package com.netpace.aims.controller.alliance;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceContactInfoManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.form name="AllianceContactInfoForm"
 */
public class AllianceContactInfoForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(AllianceContactInfoForm.class.getName());

    private java.lang.Long allianceId;
    private java.lang.String companyName;

    private java.lang.String execContactId;
    private java.lang.String busContactId;
    private java.lang.String mktgContactId;
    private java.lang.String techContactId;

    private java.lang.String execContactFirstName;
    private java.lang.String execContactLastName;
    private java.lang.String execContactTitle;
    private java.lang.String execContactPhone;
    private java.lang.String execContactEmailAddress;
    private java.lang.String execContactFax;
    private java.lang.String execContactMobile;
    private java.lang.String busContactFirstName;
    private java.lang.String busContactLastName;
    private java.lang.String busContactTitle;
    private java.lang.String busContactPhone;
    private java.lang.String busContactEmailAddress;
    private java.lang.String busContactFax;
    private java.lang.String busContactMobile;
    private java.lang.String mktgContactFirstName;
    private java.lang.String mktgContactLastName;
    private java.lang.String mktgContactTitle;
    private java.lang.String mktgContactPhone;
    private java.lang.String mktgContactEmailAddress;
    private java.lang.String mktgContactFax;
    private java.lang.String mktgContactMobile;
    private java.lang.String techContactFirstName;
    private java.lang.String techContactLastName;
    private java.lang.String techContactTitle;
    private java.lang.String techContactPhone;
    private java.lang.String techContactEmailAddress;
    private java.lang.String techContactFax;
    private java.lang.String techContactMobile;
    private java.lang.String escalationInstructions;
    private java.util.Collection allContacts;

    public java.lang.Long getAllianceId()
    {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId)
    {
        this.allianceId = allianceId;
    }

    public java.lang.String getCompanyName()
    {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName)
    {
        this.companyName = companyName;
    }

    public java.lang.String getExecContactId()
    {
        return this.execContactId;
    }

    public void setExecContactId(java.lang.String execContactId)
    {
        this.execContactId = execContactId;
    }

    public java.lang.String getBusContactId()
    {
        return this.busContactId;
    }

    public void setBusContactId(java.lang.String busContactId)
    {
        this.busContactId = busContactId;
    }

    public java.lang.String getMktgContactId()
    {
        return this.mktgContactId;
    }

    public void setMktgContactId(java.lang.String mktgContactId)
    {
        this.mktgContactId = mktgContactId;
    }

    public java.lang.String getTechContactId()
    {
        return this.techContactId;
    }

    public void setTechContactId(java.lang.String techContactId)
    {
        this.techContactId = techContactId;
    }

    public java.lang.String getExecContactFirstName()
    {
        return this.execContactFirstName;
    }

    public void setExecContactFirstName(java.lang.String execContactFirstName)
    {
        this.execContactFirstName = execContactFirstName;
    }

    public java.lang.String getExecContactLastName()
    {
        return this.execContactLastName;
    }

    public void setExecContactLastName(java.lang.String execContactLastName)
    {
        this.execContactLastName = execContactLastName;
    }

    public java.lang.String getExecContactTitle()
    {
        return this.execContactTitle;
    }

    public void setExecContactTitle(java.lang.String execContactTitle)
    {
        this.execContactTitle = execContactTitle;
    }

    public java.lang.String getExecContactPhone()
    {
        return this.execContactPhone;
    }

    public void setExecContactPhone(java.lang.String execContactPhone)
    {
        this.execContactPhone = execContactPhone;
    }

    public java.lang.String getExecContactEmailAddress()
    {
        return this.execContactEmailAddress;
    }

    public void setExecContactEmailAddress(java.lang.String execContactEmailAddress)
    {
        this.execContactEmailAddress = execContactEmailAddress;
    }

    public java.lang.String getExecContactFax()
    {
        return this.execContactFax;
    }

    public void setExecContactFax(java.lang.String execContactFax)
    {
        this.execContactFax = execContactFax;
    }

    public java.lang.String getExecContactMobile()
    {
        return this.execContactMobile;
    }

    public void setExecContactMobile(java.lang.String execContactMobile)
    {
        this.execContactMobile = execContactMobile;
    }

    public java.lang.String getBusContactFirstName()
    {
        return this.busContactFirstName;
    }

    public void setBusContactFirstName(java.lang.String busContactFirstName)
    {
        this.busContactFirstName = busContactFirstName;
    }

    public java.lang.String getBusContactLastName()
    {
        return this.busContactLastName;
    }

    public void setBusContactLastName(java.lang.String busContactLastName)
    {
        this.busContactLastName = busContactLastName;
    }

    public java.lang.String getBusContactTitle()
    {
        return this.busContactTitle;
    }

    public void setBusContactTitle(java.lang.String busContactTitle)
    {
        this.busContactTitle = busContactTitle;
    }

    public java.lang.String getBusContactPhone()
    {
        return this.busContactPhone;
    }

    public void setBusContactPhone(java.lang.String busContactPhone)
    {
        this.busContactPhone = busContactPhone;
    }

    public java.lang.String getBusContactEmailAddress()
    {
        return this.busContactEmailAddress;
    }

    public void setBusContactEmailAddress(java.lang.String busContactEmailAddress)
    {
        this.busContactEmailAddress = busContactEmailAddress;
    }

    public java.lang.String getBusContactFax()
    {
        return this.busContactFax;
    }

    public void setBusContactFax(java.lang.String busContactFax)
    {
        this.busContactFax = busContactFax;
    }

    public java.lang.String getBusContactMobile()
    {
        return this.busContactMobile;
    }

    public void setBusContactMobile(java.lang.String busContactMobile)
    {
        this.busContactMobile = busContactMobile;
    }

    public java.lang.String getMktgContactFirstName()
    {
        return this.mktgContactFirstName;
    }

    public void setMktgContactFirstName(java.lang.String mktgContactFirstName)
    {
        this.mktgContactFirstName = mktgContactFirstName;
    }

    public java.lang.String getMktgContactLastName()
    {
        return this.mktgContactLastName;
    }

    public void setMktgContactLastName(java.lang.String mktgContactLastName)
    {
        this.mktgContactLastName = mktgContactLastName;
    }

    public java.lang.String getMktgContactTitle()
    {
        return this.mktgContactTitle;
    }

    public void setMktgContactTitle(java.lang.String mktgContactTitle)
    {
        this.mktgContactTitle = mktgContactTitle;
    }

    public java.lang.String getMktgContactPhone()
    {
        return this.mktgContactPhone;
    }

    public void setMktgContactPhone(java.lang.String mktgContactPhone)
    {
        this.mktgContactPhone = mktgContactPhone;
    }

    public java.lang.String getMktgContactEmailAddress()
    {
        return this.mktgContactEmailAddress;
    }

    public void setMktgContactEmailAddress(java.lang.String mktgContactEmailAddress)
    {
        this.mktgContactEmailAddress = mktgContactEmailAddress;
    }

    public java.lang.String getMktgContactFax()
    {
        return this.mktgContactFax;
    }

    public void setMktgContactFax(java.lang.String mktgContactFax)
    {
        this.mktgContactFax = mktgContactFax;
    }

    public java.lang.String getMktgContactMobile()
    {
        return this.mktgContactMobile;
    }

    public void setMktgContactMobile(java.lang.String mktgContactMobile)
    {
        this.mktgContactMobile = mktgContactMobile;
    }

    public java.lang.String getTechContactFirstName()
    {
        return this.techContactFirstName;
    }

    public void setTechContactFirstName(java.lang.String techContactFirstName)
    {
        this.techContactFirstName = techContactFirstName;
    }

    public java.lang.String getTechContactLastName()
    {
        return this.techContactLastName;
    }

    public void setTechContactLastName(java.lang.String techContactLastName)
    {
        this.techContactLastName = techContactLastName;
    }

    public java.lang.String getTechContactTitle()
    {
        return this.techContactTitle;
    }

    public void setTechContactTitle(java.lang.String techContactTitle)
    {
        this.techContactTitle = techContactTitle;
    }

    public java.lang.String getTechContactPhone()
    {
        return this.techContactPhone;
    }

    public void setTechContactPhone(java.lang.String techContactPhone)
    {
        this.techContactPhone = techContactPhone;
    }

    public java.lang.String getTechContactEmailAddress()
    {
        return this.techContactEmailAddress;
    }

    public void setTechContactEmailAddress(java.lang.String techContactEmailAddress)
    {
        this.techContactEmailAddress = techContactEmailAddress;
    }

    public java.lang.String getTechContactFax()
    {
        return this.techContactFax;
    }

    public void setTechContactFax(java.lang.String techContactFax)
    {
        this.techContactFax = techContactFax;
    }

    public java.lang.String getTechContactMobile()
    {
        return this.techContactMobile;
    }

    public void setTechContactMobile(java.lang.String techContactMobile)
    {
        this.techContactMobile = techContactMobile;
    }

    public java.util.Collection getAllContacts()
    {
        return this.allContacts;
    }

    public void setAllContacts(java.util.Collection allContacts)
    {
        this.allContacts = allContacts;
    }

    public java.lang.String getEscalationInstructions()
    {
        return escalationInstructions;
    }

    public void setEscalationInstructions(java.lang.String string)
    {
        escalationInstructions = string;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        Collection AllianceContactInfo = null;
        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long alliance_id = null;

        if (user_type.equalsIgnoreCase("A"))
        {
            alliance_id = user.getAimsAllianc();
        }

        if (user_type.equalsIgnoreCase("V"))
        {
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        }
    
        Object[] userValues = null;

        if (task_name.equalsIgnoreCase("editForm"))
        {
            try
            {
                AllianceContactInfo = AllianceContactInfoManager.getAllianceContactInfo(alliance_id, user_type);
                for (Iterator iter = AllianceContactInfo.iterator(); iter.hasNext();)
                {
                    userValues = (Object[]) iter.next();

                    this.setAllianceId(alliance_id);

                    this.setExecContactFirstName((String) userValues[0]);
                    this.setExecContactLastName((String) userValues[1]);
                    this.setExecContactTitle((String) userValues[2]);
                    this.setExecContactPhone((String) userValues[3]);
                    this.setExecContactEmailAddress((String) userValues[4]);
                    this.setExecContactFax((String) userValues[5]);
                    this.setExecContactMobile((String) userValues[6]);

                    this.setBusContactFirstName((String) userValues[7]);
                    this.setBusContactLastName((String) userValues[8]);
                    this.setBusContactTitle((String) userValues[9]);
                    this.setBusContactPhone((String) userValues[10]);
                    this.setBusContactEmailAddress((String) userValues[11]);
                    this.setBusContactFax((String) userValues[12]);
                    this.setBusContactMobile((String) userValues[13]);

                    this.setMktgContactFirstName((String) userValues[14]);
                    this.setMktgContactLastName((String) userValues[15]);
                    this.setMktgContactTitle((String) userValues[16]);
                    this.setMktgContactPhone((String) userValues[17]);
                    this.setMktgContactEmailAddress((String) userValues[18]);
                    this.setMktgContactFax((String) userValues[19]);
                    this.setMktgContactMobile((String) userValues[20]);

                    this.setTechContactFirstName((String) userValues[21]);
                    this.setTechContactLastName((String) userValues[22]);
                    this.setTechContactTitle((String) userValues[23]);
                    this.setTechContactPhone((String) userValues[24]);
                    this.setTechContactEmailAddress((String) userValues[25]);
                    this.setTechContactFax((String) userValues[26]);
                    this.setTechContactMobile((String) userValues[27]);

                    this.setExecContactId(StringFuncs.NullValueReplacement((Long) userValues[28]));
                    this.setBusContactId(StringFuncs.NullValueReplacement((Long) userValues[29]));
                    this.setMktgContactId(StringFuncs.NullValueReplacement((Long) userValues[30]));
                    this.setTechContactId(StringFuncs.NullValueReplacement((Long) userValues[31]));

                    this.setCompanyName((String) userValues[32]);
                    this.setEscalationInstructions((String) userValues[33]);
                }

                this.allContacts = AllianceContactInfoManager.getContacts(alliance_id);
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        log.debug("\n\nIn Validate of AllianceContactInfoForm \n\n");

        ActionErrors errors = new ActionErrors();

        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        
        Long alliance_id = new Long(0);
        if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(user_type)){
        	alliance_id = user.getAimsAllianc();
        	this.setAllianceId(alliance_id);
        }else {
        	alliance_id = this.getAllianceId();        	
        }        

        if (task_name.equalsIgnoreCase("edit"))
        {
            log.debug("\n\nIn Validate of AllianceContactInfoForm \n\n The task name is edit!!!");

            if (this.isBlankString(techContactFirstName)
                || this.isBlankString(techContactLastName)
                || this.isBlankString(techContactTitle)
                || this.isBlankString(techContactEmailAddress))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.techContact"));
            }

            if (this.isBlankString(mktgContactFirstName)
                || this.isBlankString(mktgContactLastName)
                || this.isBlankString(mktgContactTitle)
                || this.isBlankString(mktgContactEmailAddress))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.mktgContact"));
            }

            if (this.isBlankString(busContactFirstName)
                || this.isBlankString(busContactLastName)
                || this.isBlankString(busContactTitle)
                || this.isBlankString(busContactEmailAddress))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.busContact"));
            }

            if (this.isBlankString(execContactFirstName)
                || this.isBlankString(execContactLastName)
                || this.isBlankString(execContactTitle)
                || this.isBlankString(execContactEmailAddress))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.execContact"));
            }
            
            if (this.isBlankString(escalationInstructions))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.escalationInstructions"));
            }


            try
            {
                this.allContacts = AllianceContactInfoManager.getContacts(alliance_id);
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

        }
        return errors;
    }

}
