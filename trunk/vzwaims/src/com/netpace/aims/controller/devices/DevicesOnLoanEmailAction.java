package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsDevicesOnLoanManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.masters.AimsAllianceLoanDevic;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/devicesonloanemail"                
 *                scope="request" 
 *                input="/sys_admin.jsp"               
 *                name="LoanDeviceEmailForm"
 *                validate="false"
 * @struts.action-forward name="compose" path="/masters/dolComposeEmail.jsp"
 * @author Ahson Imtiaz
 */
public class DevicesOnLoanEmailAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesOnLoanEmailAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.DEVICES_ON_LOAN);

        LoanDeviceEmailForm frm = (LoanDeviceEmailForm) form;
        AimsAllianceLoanDevic aald = AimsDevicesOnLoanManager.getLoanedDeviceDetails(frm.getLoanDeviceId());
        frm.setTo(aald.getAllianceMemberEmail());
        frm.setSubject("ESN-" + aald.getEsn());
        frm.setMessage(
            "Alliance : "
                + aald.getAllianceCompanyName()
                + "\n"
                + "Device : "
                + Utility.getObjectString(aald.getAimsDevic().getDeviceMfgBy())
                + " "
                + aald.getAimsDevic().getDeviceModel()
                + "\nThe device is due on "
                + Utility.convertToString(aald.getDueDate(), "MM/dd/yyyy")
                + ". Please make sure we receive it on or before the due date. ");
        aald.setEmailSent("Y");
        AimsDevicesOnLoanManager.saveOrUpdate(aald);

        return mapping.findForward("compose");
    }
}
