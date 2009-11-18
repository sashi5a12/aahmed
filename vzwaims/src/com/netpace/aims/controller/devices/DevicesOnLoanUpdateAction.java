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

import com.netpace.aims.bo.masters.AimsDevicesOnLoanManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsAllianceLoanDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/devicesonloanupdate"                
 *                scope="request"
 *                input="/masters/dolLoanedDeviceCreateUpdate.jsp"               
 *                name="LoanedDeviceListForm"
 *                validate="true"
 * @struts.action-forward name="view" path="/masters/dolLoanedDeviceDetail.jsp"
 * @struts.action-forward name="searchedlist" path="/masters/dolLoanedDevicesList.jsp"
 * @struts.action-exception key="error.generic.database" type="net.sf.hibernate.HibernateException"
 * @author Ahson Imtiaz
 */
public class DevicesOnLoanUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesOnLoanUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.DEVICES_ON_LOAN);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int iPageLength = user.getRowsLength().intValue();
        AimsAllianceLoanDevic aald = (AimsAllianceLoanDevic) session.getAttribute("AimsDeviceDetails");

        /*
        aald.setEsnDec(frm.getEsnDec());
        aald.setEsn(frm.getEsn());
        aald.setAllianceAllianceId(frm.getAllianceAllianceId());
        aald.setDeviceToLoanId(frm.getAimsDeviceToLoanId());
        aald.setMtn(frm.getMtn());
        aald.setAllianceCompanyName(frm.getAllianceCompanyName());
        aald.setAllianceMemberName(frm.getAllianceMemberName());
        aald.setAllianceMemberAddress(frm.getAllianceMemberAddress());
        aald.setAllianceMemberTelephone(frm.getAllianceMemberTelephone());
        aald.setAllianceMemberEmail(frm.getAllianceMemberEmail());
        aald.setStatus(frm.getStatus());
        aald.setComments(frm.getComments());
        */

        AimsDevicesOnLoanManager.saveOrUpdate(aald);

        log.debug("Records Updated for Aims Loan Devices.");

        request.setAttribute("AimsLoanDeviceExtz", AimsDevicesOnLoanManager.getDevicesOnLoan(aald.getAimsDevic().getDeviceId()));

        LoanDeviceListForm frm2 = new LoanDeviceListForm();
        frm2.setDeviceId(aald.getAimsDevic().getDeviceId());
        frm2.setPageNumber(new java.lang.Long(0L));

        LoanDevicesCollInfo ldci = AimsDevicesOnLoanManager.getLoanedDevices(frm2, iPageLength);
        request.setAttribute("AimsLoanedDevices", ldci.getRecords());
        request.setAttribute("TotalPages", new Integer((ldci.getTotalNumberOfRecords().intValue() / iPageLength) + 1));

        request.setAttribute("LoanedDeviceListForm", frm2);

        ActionMessages amsgs = new ActionMessages();
        amsgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("DeviceOnLoan.InformationSaved"));
        saveMessages(request, amsgs);

        return mapping.findForward("searchedlist");

    }
}
