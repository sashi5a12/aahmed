package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.JavaApplicationManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsJavaAppClob;
import com.netpace.aims.model.application.AimsJavaApps;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;

/**
 * This class takes care of action for display of update form of BREW
 * Application.
 * 
 * @struts.action path="/javaApplicationSetup" name="javaApplicationUpdateForm"
 *                scope="request" input="/applicationsViewDelete.do"
 *                validate="false"
 * @struts.action-forward name="update"
 *                        path="/application/javaApplicationUpdate.jsp"
 * @struts.action-forward name="javaView"
 *                        path="/application/javaApplicationView.jsp"
 * @struts.action-forward name="journalView"
 *                        path="/application/javaJournalView.jsp"
 * @struts.action-forward name="processInfoView"
 *                        path="/application/javaAppProcessInfoView.jsp"
 * @struts.action-forward name="page5View"
 *                        path="/application/javaUserGuideView.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="clickThroughScreen"
 *                        path="/allianceClickThroughContracts.do"
 * @struts.action-forward name="createAppScreen" path="/createApplication.do"
 * @author Waseem Akram
 */

public class JavaApplicationSetupAction extends BaseAction {    

    static Logger log = Logger.getLogger(JavaApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String forward = "update";

	HttpSession session = request.getSession();
	String dateFormat = this.getResources(request).getMessage("date.format");
	Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
	Long appUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
	String taskname = "";
	AimsApp aimsApp = null;
	Collection journalEntries = null;

	AimsAppCategory aimsAppCategory = null;
	AimsJavaApps aimsJavaApp = null;

	String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
	String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
	String viewPageToView = "";

	HashMap appJava = null;

	Object o_param;
	o_param = request.getParameter("task");
	if (o_param != null) {
	    taskname = request.getParameter("task");
	    request.setAttribute("task", (String) o_param);
	} else
	    return mapping.findForward("view");

	// CHECK Platform ACCESS
	if (!(ApplicationHelper.checkPlatformAccess(request, taskname, AimsConstants.JAVA_PLATFORM_ID)))
	    throw new AimsSecurityException();

	if (taskname.equalsIgnoreCase("create")) {
	    int numOfAllianceJavaCon = ContractsManager.getContracts(currentUserAllianceId, AimsConstants.JAVA_PLATFORM_ID).size();
	    if (numOfAllianceJavaCon == 0) {
		int numOfJavaCon = ContractsManager.getClickThroughContracts(AimsConstants.JAVA_PLATFORM_ID).size();
		ActionErrors errors = new ActionErrors();
		if (numOfJavaCon > 0) {
		    ActionError error = new ActionError("error.zero.contract.for.alliance", AimsConstants.FILTER_LABEL_JAVA);
		    errors.add(ActionErrors.GLOBAL_ERROR, error);

		    forward = "clickThroughScreen";

		    saveErrors(request, errors);

		    return mapping.findForward(forward);
		} else {
		    ActionError error = new ActionError("error.zero.contract.for.platform", AimsConstants.FILTER_LABEL_JAVA);
		    errors.add(ActionErrors.GLOBAL_ERROR, error);

		    forward = "createAppScreen";

		    saveErrors(request, errors);

		    return mapping.findForward(forward);
		}
	    }
	}

	o_param = request.getParameter("viewPageToView");
	if (o_param != null)
	    viewPageToView = request.getParameter("viewPageToView");

	// On Hold Functionality
	if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
	    if (taskname.equalsIgnoreCase("onHold")) {
		try {
		    AimsApplicationsManager.updateOnHold(new Long(request.getParameter("appsId")), request.getParameter("onHold"), currUser);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    throw ex;
		}
		return mapping.getInputForward();
	    }
	}

	AimsJavaAppClob javaClobs = null;

	// Getting Application Information And Journal Information
	if (!(taskname.equalsIgnoreCase("create"))) {
	    try {
		appJava = JavaApplicationManager.getJavaApp(new Long(request.getParameter("appsId")));
		journalEntries = JavaApplicationManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
		javaClobs = JavaApplicationManager.getClobs(new Long(request.getParameter("appsId")));
	    } catch (AimsException ae) {
		saveErrors(request, DBErrorFinder.populateActionErrors(ae));
		return mapping.getInputForward();
	    }

	    aimsApp = (AimsApp) appJava.get("AimsApp");
	    aimsAppCategory = (AimsAppCategory) appJava.get("AimsAppCategory");
	    aimsJavaApp = (AimsJavaApps) appJava.get("AimsJavaApp");
	}

	// Get Form
	JavaApplicationUpdateForm javaAppUpdForm = (JavaApplicationUpdateForm) form;

	javaAppUpdForm.setRingNumber(aimsApp.getRingTypeId());

	if (aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_2_ID)) {
	    javaAppUpdForm.setRing2App(true);
	    javaAppUpdForm.setRing3App(false);
	} else {
	    javaAppUpdForm.setRing3App(true);
	    javaAppUpdForm.setRing2App(false);
	}
	appUserAllianceId = aimsApp.getAimsAllianceId();

	if (currUserType.equalsIgnoreCase(AimsConstants.VZW_USERTYPE)) {
	    log.debug("loading all java contracts....");
	    // load contracts for vzw user only (to save unnecessary calls to
	    // load contracts)
	    javaAppUpdForm.setAllJavaContracts(ContractsManager.getContractsByPlatform(AimsConstants.JAVA_PLATFORM_ID, AimsConstants.CONTRACT_STATUS_ACTIVE));
	}
	/***********************************************************************
	 * ***** do not load alliance contracts
	 * javaAppUpdForm.setAllJavaContracts(JavaApplicationManager.getContracts(appUserAllianceId));
	 **********************************************************************/

	// Common Setup Related Tasks
	JavaApplicationHelper.setupAction(request, taskname, viewPageToView, javaAppUpdForm, aimsApp, aimsJavaApp, javaClobs, aimsAppCategory, dateFormat);

	// Start of Delete related Task
	if (taskname.equalsIgnoreCase("delete")) {

	    if (ApplicationHelper.checkJavaDeleteAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())) {
		try {
		    AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JAVA_APP_DELETED);

		    if (aimsEvent != null) {
			AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
			aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, new Date());

			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
			aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

			aimsEvent.raiseEvent(aimsEventObject);
		    }

		    AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
		    ActionMessages messages = new ActionMessages();
		    ActionMessage message = new ActionMessage("message.delete.success");
		    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		    saveMessages(request, messages);
		} catch (AimsException ae) {
		    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
		}
	    } else {
		ActionErrors errors = new ActionErrors();
		ActionError error = new ActionError("error.java.app.allianceaction.unauthorized.action");
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		saveErrors(request, errors);
	    }

	    return mapping.getInputForward();
	}
	// End of Delete related Task

	if (taskname.equalsIgnoreCase("create")) {
	    forward = "update";
	}

	if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone"))) {
	    forward = "update";
	} else if (taskname.equalsIgnoreCase("view")) {
	    if (viewPageToView.equals(""))
		forward = "javaView";
	    else if (viewPageToView.equals("processingInfo"))
		forward = "processInfoView";
	    else if (viewPageToView.equals("journal"))
		forward = "journalView";
	    else if (viewPageToView.equals("page5View"))
		forward = "page5View";
	}

	if (!taskname.equalsIgnoreCase("clone"))
	    javaAppUpdForm.setHistory(JavaApplicationHelper.getWorkflowHistory(javaAppUpdForm.getAppsId(), currUserType));

	if (log.isDebugEnabled()) {
	    log.debug("Forward: " + forward);
	    log.debug("JavaApplicationSetupAction.execute: End");
	}

	return mapping.findForward(forward);
    }

}