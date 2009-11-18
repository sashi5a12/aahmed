package com.netpace.aims.controller.application;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.application.SmsApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsSmsApp;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of SMS Application.
 *
 * @struts.action path="/smsApplicationSetup"   
 *                name="SmsApplicationUpdateForm" 
 *                scope="request"
 *                input="/applicationsViewDelete.do"   
 *				  			validate="false"
 * @struts.action-forward name="update" path="/application/smsApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="appView" path="/application/smsApplicationView.jsp"
 * @struts.action-forward name="processInfoView" path="/application/appProcessInfoView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Adnan Makda
 */
public class SmsApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(SmsApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String forward = "view";
        String taskname = "";
        String viewPageToView = "";
        Object o_param;

        o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        o_param = request.getParameter("viewPageToView");
        if (o_param != null)
            viewPageToView = request.getParameter("viewPageToView");

        if (taskname.equalsIgnoreCase("onHold"))
        {
            try
            {
                AimsApplicationsManager.updateOnHold(new Long(request.getParameter("appsId")), request.getParameter("onHold"), currUser);
            }
            catch (Exception ex)
            {
                //Log or display error
                //saveErrors(request, DBErrorFinder.populateActionErrors(ae));						
            }
            return mapping.getInputForward();
        }

        AimsApp aimsApp = null;
        AimsSmsApp aimsSmsApp = null;
        AimsAppCategory aimsAppCategory = null;
        HashMap appSms = null;

        //Get Application Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                appSms = SmsApplicationManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsApp = (AimsApp) appSms.get("AimsApp");
            aimsSmsApp = (AimsSmsApp) appSms.get("AimsSmsApp");
            aimsAppCategory = (AimsAppCategory) appSms.get("AimsAppCategory");
        }

        //Get Form
        SmsApplicationUpdateForm smsAppUpdForm = (SmsApplicationUpdateForm) form;

        //Common Setup Related Tasks
        ApplicationHelper.setupAction(request, taskname, smsAppUpdForm, aimsApp, aimsAppCategory, AimsConstants.SMS_PLATFORM_ID, dateFormat);

        smsAppUpdForm.setSetupURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.sms.app.setup.url"));
        smsAppUpdForm.setUpdateURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.sms.app.update.url"));

        //Set Temp File Ids to Zero
        smsAppUpdForm.setMessageFlowTempFileId(new Long(0));

        //Start of Delete related Task
        if (taskname.equalsIgnoreCase("delete"))
        {
            try
            {
                AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            }
            return mapping.getInputForward();
        }
        //End of Delete related Task

        if (taskname.equalsIgnoreCase("create"))
        {
            smsAppUpdForm.setCampaignClassification("N");
            forward = "update";
        }

        if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {
            //SMS Application Parameters 
            smsAppUpdForm.setCampaignClassification(aimsSmsApp.getCampaignClassification());
            smsAppUpdForm.setCarrierSupport(aimsSmsApp.getCarrierSupport());
            smsAppUpdForm.setIfVzwDirectConn(aimsSmsApp.getIfVzwDirectConn());
            smsAppUpdForm.setConnType(aimsSmsApp.getConnType());
            smsAppUpdForm.setContentProvider(aimsSmsApp.getContentProvider());
            //DUPLICATE://smsAppUpdForm.setCampaignDesc(aimsSmsApp.getCampaignDesc());
            smsAppUpdForm.setCampaignPromoInfo(aimsSmsApp.getCampaignPromoInfo());
            smsAppUpdForm.setShortCodesReqd(aimsSmsApp.getShortCodesReqd());

            smsAppUpdForm.setWholesalePrice(Utility.convertToString(aimsSmsApp.getWholesalePrice()));
            smsAppUpdForm.setRetailPrice(Utility.convertToString(aimsSmsApp.getRetailPrice()));
            smsAppUpdForm.setAvgMsgsPerSec(Utility.convertToString(aimsSmsApp.getAvgMsgsPerSec()));
            smsAppUpdForm.setPeakMsgsPerSec(Utility.convertToString(aimsSmsApp.getPeakMsgsPerSec()));

            smsAppUpdForm.setCampaignStartDate(Utility.convertToString(aimsSmsApp.getCampaignStartDate(), dateFormat));
            smsAppUpdForm.setCampaignEndDate(Utility.convertToString(aimsSmsApp.getCampaignEndDate(), dateFormat));

            //Set File Name
            smsAppUpdForm.setMessageFlowFileName(aimsSmsApp.getMessageFlowFileName());

            if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
                forward = "update";
            else if (taskname.equalsIgnoreCase("view"))
            {
                if (viewPageToView.equals(""))
                    forward = "appView";
                else if (viewPageToView.equals("processingInfo"))
                {
                    smsAppUpdForm.setCurrentPage("processingInfo");
                    forward = "processInfoView";
                }
            }

        }

        return mapping.findForward(forward);
    }

}
