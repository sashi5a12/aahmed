package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="AllianceMusicEmailForm"
 */
public class AllianceMusicEmailForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(AllianceMusicEmailForm.class.getName());

    private java.lang.Long allianceId;
    private java.lang.String toEmailAddress;
    private java.lang.String fromEmailAddress;
    private java.lang.String emailSubject;
    private java.lang.String task;
    private java.lang.String emailBody;

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {}

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        log.debug("\n\nIn Validate of AllianceMusicEmailForm \n\n");

        ActionErrors errors = new ActionErrors();

        //Check to see if data is compatible with DB fields
        validateToDBFields(errors);

        if (this.isBlankString(this.fromEmailAddress))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.required.fromEmailAddress"));

        if (this.isBlankString(this.toEmailAddress))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.required.toEmailAddress"));

        if (this.isBlankString(this.emailSubject))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.required.emailSubject"));

        if (this.isBlankString(this.emailBody))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.required.emailBody"));

        return errors;
    }

    public void validateToDBFields(ActionErrors errors)
    {

        if ((this.fromEmailAddress != null) && (this.fromEmailAddress.length() > 150))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.length.fromEmailAddress"));

        if ((this.toEmailAddress != null) && (this.toEmailAddress.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.length.toEmailAddress"));

        if ((this.emailSubject != null) && (this.emailSubject.length() > 150))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.length.emailSubject"));

        if ((this.emailBody != null) && (this.emailBody.length() > 3500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceMusicEmailForm.length.emailBody"));

    }

    /**
     * @return
     */
    public java.lang.Long getAllianceId()
    {
        return allianceId;
    }

    /**
     * @param long1
     */
    public void setAllianceId(java.lang.Long long1)
    {
        allianceId = long1;
    }

    /**
     * @return
     */
    public java.lang.String getEmailBody()
    {
        return emailBody;
    }

    /**
     * @return
     */
    public java.lang.String getEmailSubject()
    {
        return emailSubject;
    }

    /**
     * @return
     */
    public java.lang.String getFromEmailAddress()
    {
        return fromEmailAddress;
    }

    /**
     * @return
     */
    public java.lang.String getToEmailAddress()
    {
        return toEmailAddress;
    }

    /**
     * @param string
     */
    public void setEmailBody(java.lang.String string)
    {
        emailBody = string;
    }

    /**
     * @param string
     */
    public void setEmailSubject(java.lang.String string)
    {
        emailSubject = string;
    }

    /**
     * @param string
     */
    public void setFromEmailAddress(java.lang.String string)
    {
        fromEmailAddress = string;
    }

    /**
     * @param string
     */
    public void setToEmailAddress(java.lang.String string)
    {
        toEmailAddress = string;
    }

    /**
     * @return
     */
    public java.lang.String getTask()
    {
        return task;
    }

    /**
     * @param string
     */
    public void setTask(java.lang.String string)
    {
        task = string;
    }

}
