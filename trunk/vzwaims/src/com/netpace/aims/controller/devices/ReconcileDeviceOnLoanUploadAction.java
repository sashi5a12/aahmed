package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsInvalidDataException;
import com.netpace.aims.bo.masters.AimsDevicesOnLoanManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for display of submission form of Brew NSTL Listing.
 *
 * @struts.action path="/reconcileDeviceOnLoanFileUpload"
 *                name="ReconcileDeviceOnLoanFileForm"
 *                scope="request"
 *                input="/masters/reconcileDeviceOnLoanUpload.jsp"
 *				  validate="true"
 * @struts.action-forward name="searchedlist" path="/masters/reconcileDeviceOnLoanUploadResult.jsp"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dataParsingError" type="com.netpace.aims.bo.application.AimsInvalidDataException"
 * @struts.action-exception key="error.ReconcileBrewFileForm.dateError" type="com.netpace.aims.bo.application.AimsFileDateException"
 * @author Adnan Makda
 */
public class ReconcileDeviceOnLoanUploadAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileDeviceOnLoanUploadAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception, AimsInvalidDataException
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.UPLOAD_DEVICE_ON_LOAN_DATA);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        String currLoggedInUserName = user.getUsername();
        ReconcileDeviceOnLoanFileForm deviceOnLoanForm = (ReconcileDeviceOnLoanFileForm) form;

        LoanDevicesCollInfo ldci =
            AimsDevicesOnLoanManager.saveDeviceOnLoanData(
                deviceOnLoanForm.getDeviceId(),
                deviceOnLoanForm.getDeviceOnLoanFileTempFileId(),
                currLoggedInUserName);

        request.setAttribute("AimsLoanDeviceExtz", AimsDevicesOnLoanManager.getDevicesOnLoan(deviceOnLoanForm.getDeviceId()));

        LoanDeviceListForm frm2 = new LoanDeviceListForm();
        frm2.setDeviceId(deviceOnLoanForm.getDeviceId());

        request.setAttribute("AimsLoanedDevices", ldci.getRecords());
        request.setAttribute("LoanedDeviceListForm", frm2);

        ActionMessages amsgs = new ActionMessages();
        amsgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("DeviceOnLoan.InformationUploaded"));
        saveMessages(request, amsgs);

        return mapping.findForward("searchedlist");
    }
}
