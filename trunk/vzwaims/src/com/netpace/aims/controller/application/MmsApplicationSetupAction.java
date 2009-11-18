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
import com.netpace.aims.bo.application.MmsApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsMmsApp;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of MMS Application.
 *
 * @struts.action path="/mmsApplicationSetup"  
 *                name="MmsApplicationUpdateForm" 
 *                scope="request"
 *                input="/applicationsViewDelete.do"   
 *				  			validate="false"
 * @struts.action-forward name="update" path="/application/mmsApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="mmsView" path="/application/mmsApplicationView.jsp"
 * @struts.action-forward name="processInfoView" path="/application/appProcessInfoView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Adnan Makda
 */
public class MmsApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(MmsApplicationSetupAction.class.getName());

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
        AimsMmsApp aimsMmsApp = null;
        AimsAppCategory aimsAppCategory = null;
        HashMap appMms = null;

        //Get Application Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                appMms = MmsApplicationManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsApp = (AimsApp) appMms.get("AimsApp");
            aimsMmsApp = (AimsMmsApp) appMms.get("AimsMmsApp");
            aimsAppCategory = (AimsAppCategory) appMms.get("AimsAppCategory");
        }

        //Get Form
        MmsApplicationUpdateForm mmsAppUpdForm = (MmsApplicationUpdateForm) form;

        //Common Setup Related Tasks
        ApplicationHelper.setupAction(request, taskname, mmsAppUpdForm, aimsApp, aimsAppCategory, AimsConstants.MMS_PLATFORM_ID, dateFormat);

        mmsAppUpdForm.setSetupURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.mms.app.setup.url"));
        mmsAppUpdForm.setUpdateURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.mms.app.update.url"));
        
        //Set Temp File Ids to Zero
        mmsAppUpdForm.setSampleContentTempFileId(new Long(0));

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
            forward = "update";
        }

        if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {

            //MMS Application Parameters 
            mmsAppUpdForm.setContentProvider(aimsMmsApp.getContentProvider());
            mmsAppUpdForm.setProgramPromoInfo(aimsMmsApp.getProgramPromoInfo());
            mmsAppUpdForm.setSampleContentFileName(aimsMmsApp.getSampleContentFileFileName());
            mmsAppUpdForm.setTargetedLaunchDate(Utility.convertToString(aimsMmsApp.getTargetedLaunchDate(), dateFormat));
            mmsAppUpdForm.setExpectedMsgTraffic(Utility.convertToString(aimsMmsApp.getExpectedMsgTraffic()));
            
            if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
            {
                forward = "update";
            }
            else if (taskname.equalsIgnoreCase("view"))
            {
                if (viewPageToView.equals(""))
                    forward = "mmsView";
                else if (viewPageToView.equals("processingInfo"))
                {
                    mmsAppUpdForm.setCurrentPage("processingInfo");
                    forward = "processInfoView";
                }
            }
        }

        return mapping.findForward(forward);
    }
}
