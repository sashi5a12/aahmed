package com.netpace.aims.controller.devices;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.alliance.AllianceContactInfoManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.AimsAppsManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.masters.AimsDevicesOnLoanManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsAllianceLoanDevic;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/devicesonloan"                
 *                scope="request" 
 *                input="/sys_admin.jsp"               
 *                name="LoanedDeviceListForm"
 *                validate="false"
 * @struts.action-forward name="create" path="/masters/dolLoanedDeviceCreateUpdate.jsp"
 * @struts.action-forward name="detail" path="/masters/dolLoanedDeviceDetail.jsp"
 * @struts.action-forward name="searchedlist" path="/masters/dolLoanedDevicesList.jsp"
 * @struts.action-forward name="devicelist" path="/masters/dolDeviceList.jsp"
 * @struts.action-forward name="searchoptions" path="/masters/dolLoanDevSearchOpt.jsp"
 * @author Ahson Imtiaz
 */
public class DevicesOnLoanAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesOnLoanAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.DEVICES_ON_LOAN);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int iPageLength = user.getRowsLength().intValue();

        if (null == taskname)
            taskname = "view";
        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("delete")) // Delete the loan device details
        {
            try
            {
                AimsDevicesOnLoanManager.deleteDeviceOnLoan(Integer.parseInt(request.getParameter("loanDeviceId")));
                ActionMessages amsgs = new ActionMessages();
                amsgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("DeviceOnLoan.DeletedRecord"));
                saveMessages(request, amsgs);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            taskname = "viewdevices";
        }

        if (taskname.equalsIgnoreCase("viewApplication")) // View the application
        {
            String strAppsId = (String) request.getParameter("appsId");

            if (strAppsId != null)
            {
                try
                {
                    Integer.parseInt(strAppsId);
                    AimsApp appInfo = AimsAppsManager.getApp(strAppsId);
                    String strURL =
                        ApplicationsManagerHelper.getUrlSetupAction(appInfo.getAimsPlatform().getPlatformId())
                            + "?task=view&appsId="
                            + appInfo.getAppsId().toString();
                    response.sendRedirect(strURL);
                }
                catch (Exception e)
                {
                    log.debug("Invalid Application Id passed to Device On Loan Action to View the application.");
                }
            }

        }

        if (taskname.equalsIgnoreCase("create")) //Display the create/edit screen
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            AimsDevic aimsDevice = AimsDevicesManager.getDevice(frm.getDeviceId().intValue());
            AimsAllianceLoanDevic aimsAllianceLoanDevice = new AimsAllianceLoanDevic();
            aimsAllianceLoanDevice.setAimsDevic(aimsDevice);
            aimsAllianceLoanDevice.setCreatedBy(user.getUsername());
            aimsAllianceLoanDevice.setCreatedDate(new Date());
            aimsAllianceLoanDevice.setLastUpdatedBy(user.getUsername());
            aimsAllianceLoanDevice.setLastUpdatedDate(new Date());
            aimsAllianceLoanDevice.setDeviceId(aimsDevice.getDeviceId());
            session.setAttribute("AimsDeviceDetails", aimsAllianceLoanDevice);
            session.setAttribute("AimsAlliances", AimsDevicesOnLoanManager.getAimsAlliances());
            session.removeAttribute("AllianceApps");
            session.removeAttribute("AimsAllianceContacts");
            session.setAttribute("AimsPlatformc", AimsDevicesOnLoanManager.getAimsPlatforms());
            session.setAttribute("AimsDevices2Loan", AimsDevicesOnLoanManager.getDeviceToLoan(aimsAllianceLoanDevice.getDeviceId()));

            frm.setTask("create");
            //session.removeAttribute("AimsLoanDeviceNewApps");
            return mapping.findForward("create");
        }

        if (taskname.equalsIgnoreCase("edit")) //Display the create/edit screen
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            AimsAllianceLoanDevic aimsAllianceLoanDevice = AimsDevicesOnLoanManager.getLoanedDeviceDetails(frm.getLoanDeviceId());
            aimsAllianceLoanDevice.setLastUpdatedBy(user.getUsername());
            aimsAllianceLoanDevice.setLastUpdatedDate(new Date());
            session.setAttribute("AimsAllianceContacts", AimsApplicationsManager.getContacts(aimsAllianceLoanDevice.getAllianceAllianceId()));
            session.setAttribute("AimsDeviceDetails", aimsAllianceLoanDevice);
            session.setAttribute("AimsAlliances", AimsDevicesOnLoanManager.getAimsAlliances());
            session.setAttribute("AimsPlatformc", AimsDevicesOnLoanManager.getAimsPlatforms());
            session.setAttribute(
                "AimsDevices2Loan",
                AimsDevicesOnLoanManager.getDeviceToLoan(aimsAllianceLoanDevice.getDeviceId(), aimsAllianceLoanDevice.getLoanDeviceId()));
            log.debug(" ID is ******************  " + aimsAllianceLoanDevice.getDeviceToLoanId());
            frm.setAimsDeviceToLoanId(aimsAllianceLoanDevice.getDeviceToLoanId() == null ? new Long(0L) : aimsAllianceLoanDevice.getDeviceToLoanId());

            frm.setTask("edit");
            return mapping.findForward("create");
        }

        if (taskname.equalsIgnoreCase("refresh")) //Called from create/edit screen
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            AimsAllianceLoanDevic aimsAllianceLoanDevice = (AimsAllianceLoanDevic) session.getAttribute("AimsDeviceDetails");
            Long lngAllianceId = frm.getAllianceAllianceId();

            if (lngAllianceId != null && lngAllianceId.intValue() != 0)
            {
                session.setAttribute("AimsAllianceContacts", AllianceContactInfoManager.getContacts(lngAllianceId));
                Collection AllianceCompInfo = AllianceCompInfoManager.getAllianceCompInfo(lngAllianceId, user.getUserType());
                Object[] userValues = null;
                StringBuffer allianceAddress = new StringBuffer();
                for (Iterator iter = AllianceCompInfo.iterator(); iter.hasNext();)
                {
                    userValues = (Object[]) iter.next();
                    if (userValues[4] != null) //Street Address 1
                        allianceAddress.append((String) userValues[4]).append(" ");
                    if (userValues[5] != null) //Street Address 2
                        allianceAddress.append((String) userValues[5]).append(", ");
                    if (userValues[6] != null) //City
                        allianceAddress.append((String) userValues[6]).append(", ");
                    if (userValues[7] != null) //State
                        allianceAddress.append((String) userValues[7]).append(" ");
                    if (userValues[8] != null) //Zip Code
                        allianceAddress.append((String) userValues[8]).append(" ");
                    if (userValues[9] != null) //Country
                        allianceAddress.append((String) userValues[9]).append(" ");
                }
                aimsAllianceLoanDevice.setAllianceMemberAddress(allianceAddress.toString());

            }
            else
            {
                session.removeAttribute("AllianceApps");
                session.removeAttribute("AimsAllianceContacts");
            }

            aimsAllianceLoanDevice.setEsnDec(frm.getEsnDec());
            aimsAllianceLoanDevice.setEsn(frm.getEsn());
            aimsAllianceLoanDevice.setAllianceAllianceId(frm.getAllianceAllianceId());
            aimsAllianceLoanDevice.setAllianceCompanyName(frm.getAllianceCompanyName());
            aimsAllianceLoanDevice.setMtn(frm.getMtn());
            aimsAllianceLoanDevice.setAllianceMemberName(frm.getAllianceMemberName());
            aimsAllianceLoanDevice.setAllianceMemberTelephone(frm.getAllianceMemberTelephone());
            aimsAllianceLoanDevice.setAllianceMemberEmail(frm.getAllianceMemberEmail());
            aimsAllianceLoanDevice.setStatus(frm.getStatus());
            aimsAllianceLoanDevice.setComments(frm.getComments());

            return mapping.findForward("create");
        }

        if (taskname.equalsIgnoreCase("viewdetails")) // Display the details of loan device
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            AimsAllianceLoanDevic aimsAllianceLoanDevice = AimsDevicesOnLoanManager.getLoanedDeviceDetails(frm.getLoanDeviceId());
            request.setAttribute("AimsDeviceOnLoan", aimsAllianceLoanDevice);
            return mapping.findForward("detail");
        }

        // Search screen is called for options to be specified.
        if (taskname.equalsIgnoreCase("dolsearchoptions"))
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            request.setAttribute("AimsLoanDeviceExtz", AimsDevicesOnLoanManager.getDevicesOnLoan(frm.getDeviceId()));
            return mapping.findForward("searchoptions");
        }

        // Drop-down combo is selected and text is provided for filter action.
        if (taskname.equalsIgnoreCase("filterview"))
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;

            if (frm.getFilterField().equals("companyName"))
            {
                frm.setAllianceName(frm.getFilterText());
            }
            else if (frm.getFilterField().equals("esnDec"))
            {
                frm.setEsnDec(frm.getFilterText());
            }
            else if (frm.getFilterField().equals("esn"))
            {
                frm.setEsn(frm.getFilterText());
            }
            else if (frm.getFilterField().equals("mtn"))
            {
                frm.setMtn(frm.getFilterText());
            }

            taskname = "viewdevices";
        }

        // view the devices on loan for specific device Id / this action is also used for advance search results
        if (taskname.equalsIgnoreCase("viewdevices"))
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            request.setAttribute("AimsLoanDeviceExtz", AimsDevicesOnLoanManager.getDevicesOnLoan(frm.getDeviceId()));
            LoanDevicesCollInfo ldci = AimsDevicesOnLoanManager.getLoanedDevices(frm, iPageLength);
            request.setAttribute("AimsLoanedDevices", ldci.getRecords());
            request.setAttribute("TotalPages", new Integer((ldci.getTotalNumberOfRecords().intValue() / iPageLength) + 1));
            return mapping.findForward("searchedlist");
        }

        // Export to excel task

        if (taskname.equalsIgnoreCase("Export"))
        {
            java.util.Date dte = new java.util.Date();
            java.text.SimpleDateFormat dfrmt = new java.text.SimpleDateFormat("MM_dd_yyyy");
            response.reset();
            response.setHeader("Cache-Control", "no-cache");
            java.io.OutputStream ou = response.getOutputStream();
            response.setHeader("Content-Disposition", "filename=Loaned_Devices_" + dfrmt.format(dte) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            AimsDevicesOnLoanManager.writeExcelData(ou);
            ou.flush();
            ou.close();
        }

        if (taskname.equalsIgnoreCase("ExportDevice"))
        {
            LoanDeviceListForm frm = (LoanDeviceListForm) form;
            AimsLoanDevicExt aldext = AimsDevicesOnLoanManager.getDevicesOnLoan(frm.getDeviceId());
            java.util.Date dte = new java.util.Date();
            java.text.SimpleDateFormat dfrmt = new java.text.SimpleDateFormat("MM_dd_yyyy");
            response.reset();
            response.setHeader("Cache-Control", "no-cache");
            java.io.OutputStream ou = response.getOutputStream();
            response.setHeader("Content-Disposition", "filename=DOL_" + aldext.getDeviceModel() + "_" + dfrmt.format(dte) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            AimsDevicesOnLoanManager.writeExcelData(ou, frm.getDeviceId());
            ou.flush();
            ou.close();
        }

        int iPageNo = 1, iPageMax = 1, iTotalRecords = 0;

        try
        {
            if (request.getParameter("pageNo") != null)
            {
                iPageNo = java.lang.Integer.parseInt(request.getParameter("pageNo").toString());
            }
        }
        catch (Exception ex)
        {}

        iTotalRecords = AimsDevicesOnLoanManager.getDevicesOnLoanCount();
        if ((iPageNo < 1) || iPageNo > Math.ceil((iTotalRecords * 1.0 / iPageLength)))
        {
            iPageNo = 1;
        }
        log.debug(" ************** Total Records : " + iTotalRecords);
        Collection AimsDevicesOnLoan = AimsDevicesOnLoanManager.getDevicesOnLoan(iPageLength, iPageNo);

        iPageMax = new Double(Math.ceil(iTotalRecords * 1.0 / iPageLength)).intValue();

        request.setAttribute("pageNo", new Integer(iPageNo));
        request.setAttribute("pageMax", new Integer(iPageMax));
        request.setAttribute("AimsDevicesOnLoan", AimsDevicesOnLoan);
        return mapping.findForward("devicelist");
    }
}
