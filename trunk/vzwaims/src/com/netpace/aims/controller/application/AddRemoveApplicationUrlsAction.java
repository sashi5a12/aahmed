package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsNotificationConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.HashSet;
import java.util.List;
import java.util.Date;

/**
 * This class takes care of action to add remove application urls.
 *
 * @struts.action path="/addRemoveApplicationUrls"
 *                name="AddRemoveApplicationUrlForm"
 *                scope="request"
 *                input="/application/addRemoveApplicationUrls.jsp"
 *				  			validate="true"
 * @struts.action-forward name="addRemoveApplicationUrls" path="/application/addRemoveApplicationUrls.jsp"
 * @author Sajjad Raza
 */
public class AddRemoveApplicationUrlsAction extends BaseAction {
    private static Logger log = Logger.getLogger(AddRemoveApplicationUrlsAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        String forward = "addRemoveApplicationUrls";
        AddRemoveApplicationUrlForm applicationUrlForm = (AddRemoveApplicationUrlForm)form;
        String appsId = request.getParameter("appsId");
        Long appsIdValue = new Long(appsId);
        AimsAppLite appLite = null;
        String [] oldAppNetworkURLs = null;
        HttpSession session = request.getSession();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        ActionErrors errors = new ActionErrors();

        String taskname = "";
        Object o_param;

        o_param = request.getParameter("task");

        if (o_param != null) {
            taskname = request.getParameter("task");
        }
        else {
            taskname = "edit";
        }

        //get application and its urls
        appLite = ApplicationsManagerHelper.getAimsAppLite(appsIdValue);

        if(appLite!=null) {
            oldAppNetworkURLs = AimsApplicationsManager.getAppNetworkUrlValues(appsIdValue);
        }
        else {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.security"));
            saveErrors(request, errors);
            //show error
            log.debug("ERROR: Request to Add/Remove Application urls and application not found. appsId= "+appsIdValue);
            return mapping.findForward(forward);
        }

        //if network usage is not enabled then show error
        if( (appLite.getNetworkUsage() == null)
            ||  !(appLite.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE)) ) {
            //show error
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.usage.disabled"));
            saveErrors(request, errors);
            log.debug("ERROR: Request to Add/Remove Application urls and Network usage is not enabled. appsId= "+appLite.getAppsId());
            return mapping.findForward(forward);
        }

        //check user, alliance or vzw admin can add urls
        if(!currUserType.equals(AimsConstants.VZW_USERTYPE)
            && !currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
            //show error
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.security"));
            saveErrors(request, errors);
            log.debug("ERROR: Request to Add/Remove Application urls and user is not Alliance nor VZW. appsId= "+appLite.getAppsId());
            return mapping.findForward(forward);
        }

        //CHECK ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, "edit", appLite.getAimsPlatformId()))) {
            //show error
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.security"));
            saveErrors(request, errors);
            log.debug("ERROR: Request to Add/Remove Application urls, and user does not have access to this platform. platformId="+appLite.getAimsPlatformId()+", appsId= "+appLite.getAppsId());
            return mapping.findForward(forward);
        }



        if (taskname.equalsIgnoreCase("edit")) {
            applicationUrlForm.setAppsId(appsIdValue);
            applicationUrlForm.setNetworkUsage(appLite.getNetworkUsage());
            //get AimsAppNetworkUrls
            try {
                applicationUrlForm.setApplicationURLs(AimsApplicationsManager.getAppNetworkUrlValues(appsIdValue));
            }
            catch (HibernateException he) {
                he.printStackTrace();
                log.debug("\nError in getting Network URL for application:"+appsIdValue+"\n");
            }
        }

        if (taskname.equalsIgnoreCase("save")) {
            //create List of AimsAppNetworkUrls
            List appNetworkURLList = ApplicationHelper.convertAppNetworkUrlArrayToList(applicationUrlForm.getApplicationURLs());
            ApplicationsManagerHelper.saveAppNetworkUrls(appNetworkURLList, appsIdValue);

            //check if status is not saved, and if app network urls changed, if yes then send notification
            if( (appLite.getAimsLifecyclePhaseId()!=null)
                    && (appLite.getAimsLifecyclePhaseId().longValue() != AimsConstants.SAVED_ID.longValue())) {
                if(ApplicationHelper.checkAppNetworkUrlsChanged(oldAppNetworkURLs, applicationUrlForm.getApplicationURLs())) {
                    log.debug("Application urls changed. appsId= "+appLite.getAppsId());
                    String appTitle = appLite.getTitle();
                    String allianceName = AllianceManager.getAllianceCompanyName(appLite.getAimsAllianceId());
                    allianceName = StringFuncs.NullValueReplacement(allianceName); //if no alliance found set to blank string

                    //send notification
                    AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_APPLICATION_URLS_CHANGED);
                    if (aimsEvent != null) {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, allianceName);
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, appTitle);
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()));
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }
            }//end send notification if urls changed

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.manage.app.network.urls.saved"));
            saveMessages(request, messages);
            return mapping.findForward(forward);
        }

        return mapping.findForward(forward);
    }

}
