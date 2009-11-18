package com.netpace.aims.controller.devices;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;

/** Form will be used for the Email 
 *  related to the Device On Loan Management Module
 * @struts.form name="LoanDeviceEmailForm"
/* @author Ahson Imtiaz */

public class LoanDeviceEmailForm extends BaseValidatorForm implements Serializable
{

    /** nullable persistent field */
    private java.lang.Long deviceId;

    /** nullable persistent field */
    private java.lang.Long loanDeviceId;

    /** nullable persistent field */
    private java.lang.String to;

    /** nullable persistent field */
    private java.lang.String from;

    /** nullable persistent field */
    private java.lang.String subject;

    /** nullable persistent field */
    private java.lang.String message;

    /** default constructor */
    public LoanDeviceEmailForm()
    {}

    public java.lang.Long getDeviceId()
    {
        return this.deviceId;
    }

    public void setDeviceId(java.lang.Long deviceId)
    {
        this.deviceId = deviceId;
    }

    public java.lang.Long getLoanDeviceId()
    {
        return this.loanDeviceId;
    }

    public void setLoanDeviceId(java.lang.Long loanDeviceId)
    {
        this.loanDeviceId = loanDeviceId;
    }

    public java.lang.String getTo()
    {
        return this.to;
    }

    public void setTo(java.lang.String input)
    {
        this.to = input;
    }

    public java.lang.String getFrom()
    {
        return this.from;
    }

    public void setFrom(java.lang.String input)
    {
        this.from = input;
    }

    public java.lang.String getSubject()
    {
        return this.subject;
    }

    public void setSubject(java.lang.String input)
    {
        this.subject = input;
    }

    public java.lang.String getMessage()
    {
        return this.message;
    }

    public void setMessage(java.lang.String input)
    {
        this.message = input;
    }

    /*
    */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        System.out.println("The R E S E T    is called");
    }

    /*
    */

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        System.out.println("In Validation");

        if (!this.isValidEmail(to))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceOnLoan.EmailTo"));

        return errors;

    }

}