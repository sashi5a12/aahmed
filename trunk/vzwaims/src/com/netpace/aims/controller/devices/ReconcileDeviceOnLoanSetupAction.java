package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;


/**
 * This class takes care of action for upload of file for Device on Loan
 *
 * @struts.action path="/deviceOnLoanUpload"
 *                name="ReconcileDeviceOnLoanFileForm"
 *                scope="request"
 *                input="/masters/reconcileDeviceOnLoanUpload.jsp"
 *                validate="false"
 * @struts.action-forward name="upload" path="/masters/reconcileDeviceOnLoanUpload.jsp"
 * @author Adnan Makda
 */
public class ReconcileDeviceOnLoanSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileDeviceOnLoanSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_DEVICE_ON_LOAN_DATA);

		return mapping.findForward("upload");
    }
}
