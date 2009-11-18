package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsDevicesOnLoanManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.MailUtils;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/devicesonloansend"                
 *                scope="request" 
 *                input="/masters/dolComposeEmail.jsp"               
 *                name="LoanDeviceEmailForm"
 *                validate="true"
 * @struts.action-forward name="view" path="/masters/dolLoanedDeviceDetail.jsp"
 * @author Ahson Imtiaz
 */
public class DevicesOnLoanMSendAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesOnLoanEmailAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.DEVICES_ON_LOAN);

        HttpSession session = request.getSession();
        LoanDeviceEmailForm frm = (LoanDeviceEmailForm) form;
        MailUtils.sendMail(frm.getTo(), frm.getFrom(), frm.getSubject(), "aimtiaz@netpace.com", frm.getMessage());
        request.setAttribute("AimsDeviceOnLoan", AimsDevicesOnLoanManager.getLoanedDeviceDetails(frm.getLoanDeviceId()));
        return mapping.findForward("view");
    }
}
