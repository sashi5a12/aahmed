package com.netpace.aims.controller.devices;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.masters.AimsAllianceLoanDevic;

/** Form will be used for the search filter on Devices on loan list 
 *  related to the Device On Loan Management Module
 * @struts.form name="LoanedDeviceListForm"
/* @author Ahson Imtiaz */

public class LoanDeviceListForm extends BaseValidatorForm implements Serializable
{

    /** nullable persistent field */
    private java.lang.Long deviceId;

    /** nullable persistent field */
    private java.lang.Long allianceId;

    /** nullable persistent field */
    private java.lang.Long loanDeviceId;

    /** nullable persistent field */
    private java.lang.Long aimsContactId;

    /** nullable persistent field */
    private java.lang.Long aimsDeviceToLoanId;

    /** nullable persistent field */
    private java.lang.String allianceName;

    /** nullable persistent field */
    private java.lang.String mtn;

    /** nullable persistent field */
    private java.lang.String allianceMemberName;

    /** nullable persistent field */
    private java.lang.String allianceMemberAddress;

    /** nullable persistent field */
    private java.lang.String allianceMemberTelephone;

    /** nullable persistent field */
    private java.lang.String allianceMemberEmail;

    /** nullable persistent field */
    private java.lang.String allianceCompanyName;

    /** nullable persistent field */
    private java.lang.Long allianceAllianceId;

    /** nullable persistent field */
    private java.lang.String newApplicationName;

    /** nullable persistent field */
    private java.lang.Long newApplicationPlatformId;

    /** nullable persistent field */
    private java.lang.String applicationTitle;

    /** nullable persistent field */
    private java.lang.String esnDec;

    /** nullable persistent field */
    private java.lang.String esn;

    /** nullable persistent field */
    private java.lang.String status;

    /** nullable persistent field */
    private java.lang.String comments;

    /** nullable persistent field */
    private java.lang.String emailSent;

    /** nullable not persistent field */
    private java.lang.String strTask;

    /** nullable not persistent field */
    private java.lang.String filterField;

    /** nullable not persistent field */
    private java.lang.String filterText;

    private java.lang.String[] cLoanDeviceIds;

    /** nullable non-persistent field for holding view pageNo*/
    private java.lang.Long pageNumber;

    /** default constructor */
    public LoanDeviceListForm()
    {}

    public java.lang.Long getDeviceId()
    {
        return this.deviceId;
    }

    public void setDeviceId(java.lang.Long deviceId)
    {
        this.deviceId = deviceId;
    }

    public java.lang.Long getAllianceId()
    {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId)
    {
        this.allianceId = allianceId;
    }

    public java.lang.Long getLoanDeviceId()
    {
        return this.loanDeviceId;
    }

    public void setLoanDeviceId(java.lang.Long loanDeviceId)
    {
        this.loanDeviceId = loanDeviceId;
    }
    /**/
    public java.lang.Long getAimsContactId()
    {
        return this.aimsContactId;
    }

    public void setAimsContactId(java.lang.Long lngId)
    {
        this.aimsContactId = lngId;
    }

    /**/
    public java.lang.Long getAimsDeviceToLoanId()
    {
        return this.aimsDeviceToLoanId;
    }

    public void setAimsDeviceToLoanId(java.lang.Long lngId)
    {
        this.aimsDeviceToLoanId = lngId;
    }
    /**/
    public java.lang.String getAllianceName()
    {
        return this.allianceName;
    }

    public void setAllianceName(java.lang.String allianceName)
    {
        this.allianceName = allianceName;
    }

    public java.lang.String getMtn()
    {
        return this.mtn;
    }

    public void setMtn(java.lang.String mtn)
    {
        this.mtn = mtn;
    }

    public java.lang.String getAllianceMemberName()
    {
        return this.allianceMemberName;
    }

    public void setAllianceMemberName(java.lang.String allianceMemberName)
    {
        this.allianceMemberName = allianceMemberName;
    }

    public java.lang.String getAllianceMemberAddress()
    {
        return this.allianceMemberAddress;
    }

    public void setAllianceMemberAddress(java.lang.String allianceMemberAddress)
    {
        this.allianceMemberAddress = allianceMemberAddress;
    }

    public java.lang.String getAllianceMemberTelephone()
    {
        return this.allianceMemberTelephone;
    }

    public void setAllianceMemberTelephone(java.lang.String allianceMemberTelephone)
    {
        this.allianceMemberTelephone = allianceMemberTelephone;
    }

    public java.lang.String getAllianceMemberEmail()
    {
        return this.allianceMemberEmail;
    }

    public void setAllianceMemberEmail(java.lang.String allianceMemberEmail)
    {
        this.allianceMemberEmail = allianceMemberEmail;
    }

    public java.lang.String getNewApplicationName()
    {
        return this.newApplicationName;
    }

    public void setNewApplicationName(java.lang.String newApplicationName)
    {
        this.newApplicationName = newApplicationName;
    }

    public java.lang.Long getNewApplicationPlatformId()
    {
        return this.newApplicationPlatformId;
    }

    public void setNewApplicationPlatformId(java.lang.Long lngInput)
    {
        this.newApplicationPlatformId = lngInput;
    }

    public java.lang.String getApplicationTitle()
    {
        return this.applicationTitle;
    }

    public void setApplicationTitle(java.lang.String strInput)
    {
        this.applicationTitle = strInput;
    }

    public java.lang.String getEsnDec()
    {
        return this.esnDec;
    }

    public void setEsnDec(java.lang.String esnDec)
    {
        this.esnDec = esnDec;
    }

    public java.lang.String getEsn()
    {
        return this.esn;
    }

    public void setEsn(java.lang.String esn)
    {
        this.esn = esn;
    }

    public java.lang.String getStatus()
    {
        return this.status;
    }

    public void setStatus(java.lang.String status)
    {
        this.status = status;
    }

    public java.lang.String getEmailSent()
    {
        return this.emailSent;
    }

    public void setEmailSent(java.lang.String emailSent)
    {
        this.emailSent = emailSent;
    }

    public java.lang.String[] getLoanDeviceIds()
    {
        return this.cLoanDeviceIds;
    }

    public void setLoanDeviceIds(String[] strInputs)
    {
        this.cLoanDeviceIds = strInputs;
    }

    public java.lang.String getComments()
    {
        return this.comments;
    }

    public void setComments(String strInput)
    {
        this.comments = strInput;
    }
    /**/
    public java.lang.String getTask()
    {
        return this.strTask;
    }

    public void setTask(String strInput)
    {
        this.strTask = strInput;
    }

    /**/
    public java.lang.String getFilterField()
    {
        return this.filterField;
    }

    public void setFilterField(String strInput)
    {
        this.filterField = strInput;
    }

    /**/
    public java.lang.String getFilterText()
    {
        return this.filterText;
    }

    public void setFilterText(String strInput)
    {
        this.filterText = strInput;
    }

    public java.lang.Long getPageNumber()
    {
        return this.pageNumber;
    }

    public void setPageNumber(java.lang.Long pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("loanDeviceId", getDeviceId()).toString();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LoanDeviceListForm))
            return false;
        LoanDeviceListForm castOther = (LoanDeviceListForm) other;
        return new EqualsBuilder().append(this.getDeviceId(), castOther.getDeviceId()).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getDeviceId()).toHashCode();
    }

    /*
    */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        System.out.println("The R E S E T    is called");
        System.out.println("Task in Reset is:" + request.getParameter("task"));
        if (pageNumber == null)
            pageNumber = new Long(0L);
    }

    /*
    */

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();
        AimsAllianceLoanDevic aald = (AimsAllianceLoanDevic) request.getSession().getAttribute("AimsDeviceDetails");

        System.out.println("Task is : " + strTask);

        aald.setEsnDec(esnDec);
        aald.setEsn(esn);
        aald.setMtn(mtn);
        aald.setDeviceToLoanId(aimsDeviceToLoanId);
        aald.setAllianceAllianceId(allianceAllianceId);
        aald.setAllianceCompanyName(allianceCompanyName);
        aald.setAllianceMemberName(allianceMemberName);
        aald.setAllianceMemberAddress(allianceMemberAddress);
        aald.setAllianceMemberTelephone(allianceMemberTelephone);
        aald.setAllianceMemberEmail(allianceMemberEmail);
        aald.setStatus(status);
        aald.setComments(comments);
        
        if (strTask != null && (strTask.equals("create") || strTask.equals("edit") || strTask.equals("refresh")))
        {
            System.out.println("In Validation");

            if (this.isBlankString(esnDec) && this.isBlankString(esn))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceOnLoan.ESNDECORHEX"));

            if (this.isBlankString(mtn))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceOnLoan.Mtn"));

            if (this.isBlankString(allianceCompanyName))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceOnLoan.AllianceName"));

            if (this.isBlankString(allianceMemberName))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceOnLoan.AllianceMemberName"));

            if (!this.isBlankString(allianceMemberEmail))
                if (!this.isValidEmail(allianceMemberEmail))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.DeviceOnLoan.NVAllianceMemberEmail"));
        }

        System.out.println("In Validation Finished ");

        if (!errors.isEmpty())
        {
            strTask = "refresh";
            System.out.println("Found Errors");
        }

        return errors;

    }

    /**
     * @return
     */
    public java.lang.String getAllianceCompanyName()
    {
        return allianceCompanyName;
    }

    /**
     * @param string
     */
    public void setAllianceCompanyName(java.lang.String string)
    {
        allianceCompanyName = string;
    }

    /**
     * @return
     */
    public java.lang.Long getAllianceAllianceId()
    {
        return allianceAllianceId;
    }

    /**
     * @param long1
     */
    public void setAllianceAllianceId(java.lang.Long long1)
    {
        allianceAllianceId = long1;
    }

}