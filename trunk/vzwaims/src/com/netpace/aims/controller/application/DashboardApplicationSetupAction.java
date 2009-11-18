package com.netpace.aims.controller.application;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsDashboardAppClob;
import com.netpace.aims.model.application.AimsDashboardApps;
import com.netpace.aims.model.application.AimsDashboardChannelIds;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.DBErrorFinder;

/**
 * 
 * @struts.action path="/dashboardApplicationSetup"
 *                name="DashboardApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do" validate="false"
 * @struts.action-forward name="update" path="/application/dashboardApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="dashboardView" path="/application/dashboardApplicationView.jsp"
 * @struts.action-forward name="processInfoView" path="/application/dashboardAppProcessInfoView.jsp"
 * @struts.action-forward name="journalView" path="/application/dashboardJournalView.jsp"
 * @struts.action-forward name="page5View" path="/application/dashboardUserGuideView.jsp"
 * @author Adnan Ahmed
 */
public class DashboardApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(DashboardApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AimsSecurityException, Exception{
    	log.debug("DashboardApplicationSetupAction.execute: Start");
        HttpSession session = request.getSession();
		String dateFormat = this.getResources(request).getMessage("date.format");
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
		String forward = "view";
		String taskname = "";
		String viewPageToView = "";
		Object o_param;

		o_param = request.getParameter("task");

		if (o_param != null) {
			taskname = request.getParameter("task");
			request.setAttribute("task", (String) o_param);
		} else
			return mapping.findForward(forward);

		o_param = request.getParameter("viewPageToView");
		if (o_param != null)
			viewPageToView = request.getParameter("viewPageToView");

		log.debug("taskName: "+taskname);
		
		AimsApp aimsApp = null;
 		AimsDashboardApps dashboardApp = null;
		AimsAppCategory aimsAppCategory = null;
		AimsDashboardChannelIds dashboardChannel=null;
 		HashMap dashboardAppMap = null;
 		AimsDashboardAppClob dashboardClobs = null;
 		DashboardApplicationUpdateForm dashboardForm = (DashboardApplicationUpdateForm) form;
 		
        //Get Application Information
        if (!(taskname.equalsIgnoreCase("create"))) {
			try {
				dashboardAppMap = DashboardApplicationManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
				dashboardForm.setListSelectedDevices(DashboardApplicationManager.getAppDevices(new Long(request.getParameter("appsId"))));
				dashboardClobs = DashboardApplicationManager.getClobs(new Long(request.getParameter("appsId")));
			} catch (AimsException ae) {
				saveErrors(request, DBErrorFinder.populateActionErrors(ae));
				dashboardForm.setCurrentPage("page1");
				DashboardApplicationHelper.prePopulateForm(dashboardForm);
				return mapping.getInputForward();
			}
			aimsApp = (AimsApp) dashboardAppMap.get("AimsApp");
			dashboardApp = (AimsDashboardApps) dashboardAppMap.get("AimsDashboardApp");
			aimsAppCategory = (AimsAppCategory) dashboardAppMap.get("AimsAppCategory");
			dashboardChannel = (AimsDashboardChannelIds) dashboardAppMap.get("AimsDashboardChannel");
		}        

        if (dashboardChannel == null){
        	dashboardChannel=new AimsDashboardChannelIds();
        }
        DashboardApplicationHelper.setupAction(request, taskname, viewPageToView, dashboardForm, aimsApp, dashboardApp, dashboardClobs, dashboardChannel, aimsAppCategory, dateFormat);
        dashboardForm.setSetupURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.dashboard.app.setup.url"));
        dashboardForm.setUpdateURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.dashboard.app.update.url"));
        
        //Start of Delete related Task
        if (taskname.equalsIgnoreCase("delete"))
        {
            try{
                AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);                
                ActionMessages messages = new ActionMessages();
                ActionMessage message = new ActionMessage("message.delete.success");
                messages.add(ActionMessages.GLOBAL_MESSAGE, message);
                saveMessages(request, messages);
            }
            catch (AimsException ae){
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            }
            return mapping.getInputForward();
        }
        // End of Delete related Task

        if (taskname.equalsIgnoreCase("create")){
            forward = "update";
        }

        if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view"))){
            
        	if (taskname.equalsIgnoreCase("edit")){
        		String contentZipFileTmpLoc=(String)session.getAttribute(AimsConstants.SESSION_CONTENT_ZIP_FILE_TMP_LOCATION);
        		String ftpsSendTestFile=(String)session.getAttribute(AimsConstants.SESSION_FTPS_SEND_TEST_FILE);
        		if (StringUtils.isEmpty(contentZipFileTmpLoc) || StringUtils.isEmpty(ftpsSendTestFile)){
        			ConfigEnvProperties conf=ConfigEnvProperties.getInstance();
        			String tmpLocProp=conf.getProperty("dashboard.contentZipFile.local.temp.dir");
        			String sendTestFileProp=conf.getProperty("dashboard.ftps.sendTestFile");
        			session.setAttribute(AimsConstants.SESSION_CONTENT_ZIP_FILE_TMP_LOCATION, tmpLocProp);
        			session.setAttribute(AimsConstants.SESSION_FTPS_SEND_TEST_FILE, sendTestFileProp);
        			File tmpFile=new File(tmpLocProp);
        			if (!tmpFile.exists()){
        				tmpFile.mkdirs();
        			}
        		}
        	}
        	
            if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone"))) {
				forward = "update";
			}
            else if (taskname.equalsIgnoreCase("view")) {
				if (viewPageToView.equals("")){
					forward = "dashboardView";					
				}
				else if (viewPageToView.equals("processingInfo")) {
					forward = "processInfoView";
				} 
				else if (viewPageToView.equals("journal")) {
					forward = "journalView";
				}
				else if (viewPageToView.equals("page5View")) {
					forward = "page5View";
				}
			}
        }

        log.debug("Forward: "+forward);
        log.debug("DashboardApplicationSetupAction.execute: End");
        return mapping.findForward(forward);
    }

}
