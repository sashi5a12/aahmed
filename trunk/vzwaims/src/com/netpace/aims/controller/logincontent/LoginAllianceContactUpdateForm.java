package com.netpace.aims.controller.logincontent;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.bo.alliance.AllianceContactInfoManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;

/**
 * @struts.form name="LoginAllianceContactUpdateForm"
 */
public class LoginAllianceContactUpdateForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(LoginAllianceContactUpdateForm.class.getName());

    private String task;

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

    private java.lang.String parentPageType;
    private java.lang.String parentPath;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
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
                he.printStackTrace();
            }
        }//end if editForm

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        log.debug("\n\nIn Validate of LoginAllianceContactUpdateForm \n\n");

        ActionErrors errors = new ActionErrors();

        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        Long alliance_id = user.getAimsAllianc();

        if (task_name.equalsIgnoreCase("update")) {
            log.debug("\n\nIn Validate of AllianceContactInfoForm \n\n The task name is edit!!!");

            if(!(Long.parseLong(StringFuncs.NulltoZeroStringReplacement(this.getExecContactId())) > 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.execContact"));
            }

            if(!(Long.parseLong(StringFuncs.NulltoZeroStringReplacement(this.getBusContactId())) > 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.busContact"));
            }

            if(!(Long.parseLong(StringFuncs.NulltoZeroStringReplacement(this.getMktgContactId())) > 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.mktgContact"));
            }

            if(!(Long.parseLong(StringFuncs.NulltoZeroStringReplacement(this.getTechContactId())) > 0)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContactInfoForm.techContact"));
            }

            try {
                this.allContacts = AllianceContactInfoManager.getContacts(alliance_id);
            }
            catch (HibernateException he) {
                log.debug("A hibernate exception occured!");
                he.printStackTrace();
            }
        }//end if update
        return errors;
    }//end validate

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Long getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Long allianceId) {
        this.allianceId = allianceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExecContactId() {
        return execContactId;
    }

    public void setExecContactId(String execContactId) {
        this.execContactId = execContactId;
    }

    public String getBusContactId() {
        return busContactId;
    }

    public void setBusContactId(String busContactId) {
        this.busContactId = busContactId;
    }

    public String getMktgContactId() {
        return mktgContactId;
    }

    public void setMktgContactId(String mktgContactId) {
        this.mktgContactId = mktgContactId;
    }

    public String getTechContactId() {
        return techContactId;
    }

    public void setTechContactId(String techContactId) {
        this.techContactId = techContactId;
    }

    public String getExecContactFirstName() {
        return execContactFirstName;
    }

    public void setExecContactFirstName(String execContactFirstName) {
        this.execContactFirstName = execContactFirstName;
    }

    public String getExecContactLastName() {
        return execContactLastName;
    }

    public void setExecContactLastName(String execContactLastName) {
        this.execContactLastName = execContactLastName;
    }

    public String getExecContactTitle() {
        return execContactTitle;
    }

    public void setExecContactTitle(String execContactTitle) {
        this.execContactTitle = execContactTitle;
    }

    public String getExecContactPhone() {
        return execContactPhone;
    }

    public void setExecContactPhone(String execContactPhone) {
        this.execContactPhone = execContactPhone;
    }

    public String getExecContactEmailAddress() {
        return execContactEmailAddress;
    }

    public void setExecContactEmailAddress(String execContactEmailAddress) {
        this.execContactEmailAddress = execContactEmailAddress;
    }

    public String getExecContactFax() {
        return execContactFax;
    }

    public void setExecContactFax(String execContactFax) {
        this.execContactFax = execContactFax;
    }

    public String getExecContactMobile() {
        return execContactMobile;
    }

    public void setExecContactMobile(String execContactMobile) {
        this.execContactMobile = execContactMobile;
    }

    public String getBusContactFirstName() {
        return busContactFirstName;
    }

    public void setBusContactFirstName(String busContactFirstName) {
        this.busContactFirstName = busContactFirstName;
    }

    public String getBusContactLastName() {
        return busContactLastName;
    }

    public void setBusContactLastName(String busContactLastName) {
        this.busContactLastName = busContactLastName;
    }

    public String getBusContactTitle() {
        return busContactTitle;
    }

    public void setBusContactTitle(String busContactTitle) {
        this.busContactTitle = busContactTitle;
    }

    public String getBusContactPhone() {
        return busContactPhone;
    }

    public void setBusContactPhone(String busContactPhone) {
        this.busContactPhone = busContactPhone;
    }

    public String getBusContactEmailAddress() {
        return busContactEmailAddress;
    }

    public void setBusContactEmailAddress(String busContactEmailAddress) {
        this.busContactEmailAddress = busContactEmailAddress;
    }

    public String getBusContactFax() {
        return busContactFax;
    }

    public void setBusContactFax(String busContactFax) {
        this.busContactFax = busContactFax;
    }

    public String getBusContactMobile() {
        return busContactMobile;
    }

    public void setBusContactMobile(String busContactMobile) {
        this.busContactMobile = busContactMobile;
    }

    public String getMktgContactFirstName() {
        return mktgContactFirstName;
    }

    public void setMktgContactFirstName(String mktgContactFirstName) {
        this.mktgContactFirstName = mktgContactFirstName;
    }

    public String getMktgContactLastName() {
        return mktgContactLastName;
    }

    public void setMktgContactLastName(String mktgContactLastName) {
        this.mktgContactLastName = mktgContactLastName;
    }

    public String getMktgContactTitle() {
        return mktgContactTitle;
    }

    public void setMktgContactTitle(String mktgContactTitle) {
        this.mktgContactTitle = mktgContactTitle;
    }

    public String getMktgContactPhone() {
        return mktgContactPhone;
    }

    public void setMktgContactPhone(String mktgContactPhone) {
        this.mktgContactPhone = mktgContactPhone;
    }

    public String getMktgContactEmailAddress() {
        return mktgContactEmailAddress;
    }

    public void setMktgContactEmailAddress(String mktgContactEmailAddress) {
        this.mktgContactEmailAddress = mktgContactEmailAddress;
    }

    public String getMktgContactFax() {
        return mktgContactFax;
    }

    public void setMktgContactFax(String mktgContactFax) {
        this.mktgContactFax = mktgContactFax;
    }

    public String getMktgContactMobile() {
        return mktgContactMobile;
    }

    public void setMktgContactMobile(String mktgContactMobile) {
        this.mktgContactMobile = mktgContactMobile;
    }

    public String getTechContactFirstName() {
        return techContactFirstName;
    }

    public void setTechContactFirstName(String techContactFirstName) {
        this.techContactFirstName = techContactFirstName;
    }

    public String getTechContactLastName() {
        return techContactLastName;
    }

    public void setTechContactLastName(String techContactLastName) {
        this.techContactLastName = techContactLastName;
    }

    public String getTechContactTitle() {
        return techContactTitle;
    }

    public void setTechContactTitle(String techContactTitle) {
        this.techContactTitle = techContactTitle;
    }

    public String getTechContactPhone() {
        return techContactPhone;
    }

    public void setTechContactPhone(String techContactPhone) {
        this.techContactPhone = techContactPhone;
    }

    public String getTechContactEmailAddress() {
        return techContactEmailAddress;
    }

    public void setTechContactEmailAddress(String techContactEmailAddress) {
        this.techContactEmailAddress = techContactEmailAddress;
    }

    public String getTechContactFax() {
        return techContactFax;
    }

    public void setTechContactFax(String techContactFax) {
        this.techContactFax = techContactFax;
    }

    public String getTechContactMobile() {
        return techContactMobile;
    }

    public void setTechContactMobile(String techContactMobile) {
        this.techContactMobile = techContactMobile;
    }

    public String getEscalationInstructions() {
        return escalationInstructions;
    }

    public void setEscalationInstructions(String escalationInstructions) {
        this.escalationInstructions = escalationInstructions;
    }

    public Collection getAllContacts() {
        return allContacts;
    }

    public void setAllContacts(Collection allContacts) {
        this.allContacts = allContacts;
    }

    public String getParentPageType() {
        return parentPageType;
    }

    public void setParentPageType(String parentPageType) {
        this.parentPageType = parentPageType;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }
}
