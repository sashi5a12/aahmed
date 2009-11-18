package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.form name="ChangeAllianceAdminForm"
 */
public class ChangeAllianceAdminForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(ChangeAllianceAdminForm.class.getName());

    private java.util.Collection allAdminUsers;
    private java.lang.Long adminUserId;
    private java.lang.String allianceAdminName;
    private java.lang.String allianceAdminEmailAddress;
    private java.lang.String allianceAdminPhone;

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Long appOwnerAllianceId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getAimsAllianc();

        try
        {
            this.allAdminUsers = AllianceManager.getAllianceUsersWithAdminRole(appOwnerAllianceId);

        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET of ChangeAllianceAdminForm: " + ex);
            ex.printStackTrace();
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        if (!this.isDropDownSelected(this.adminUserId))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.change.alliance.admin.required"));
        }

        return errors;

    }

    public java.lang.String getAllianceAdminEmailAddress()
    {
        return allianceAdminEmailAddress;
    }

    public void setAllianceAdminEmailAddress(java.lang.String allianceAdminEmailAddress)
    {
        this.allianceAdminEmailAddress = allianceAdminEmailAddress;
    }

    public java.lang.String getAllianceAdminPhone()
    {
        return allianceAdminPhone;
    }

    public void setAllianceAdminPhone(java.lang.String allianceAdminPhone)
    {
        this.allianceAdminPhone = allianceAdminPhone;
    }

    public java.lang.String getAllianceAdminName()
    {
        return allianceAdminName;
    }

    public void setAllianceAdminName(java.lang.String allianceAdminName)
    {
        this.allianceAdminName = allianceAdminName;
    }

    public java.lang.Long getAdminUserId()
    {
        return adminUserId;
    }

    public void setAdminUserId(java.lang.Long adminUserId)
    {
        this.adminUserId = adminUserId;
    }

    public java.util.Collection getAllAdminUsers()
    {
        return allAdminUsers;
    }

    public void setAllAdminUsers(java.util.Collection allAdminUsers)
    {
        this.allAdminUsers = allAdminUsers;
    }

}
