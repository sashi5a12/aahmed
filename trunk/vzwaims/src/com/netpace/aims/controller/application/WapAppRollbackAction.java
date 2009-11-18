package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.application.WapRollbackAppInformation;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsWapApp;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.MiscUtils;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/rollbackWapApp"
 *                name="WapAppRollbackFilterForm"
 *                scope="request"
 *			      validate="false"
 *                input="/rollbackWapApp.do?task=view"
 * @struts.action-forward name="view" path="/application/wapAppRollback.jsp"
 * @author Adnan Makda.
 */
public class WapAppRollbackAction extends BaseAction
{
    public static final String FIELD_COMPANY_NAME = "alliance.companyName";
    public static final String FIELD_TITLE = "application.title";
    public static final String FIELD_CREATE_DATE_NAME = "application.submittedDate";
    public static final String FIELD_PHASE_NAME = "lifecycle.phaseName";

    private HashMap fieldMapper = new HashMap();

    static Logger log = Logger.getLogger(WapAppRollbackAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_ROLLBACK_TO_PENDING_DCR);

        fillFieldMapper();

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);

        String forward = "view";
        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        String taskname;
        String appsId = null;

        Object o_param;

        o_param = request.getParameter("task");
        if (o_param != null)
            taskname = request.getParameter("task");
        else
            taskname = "view";

        o_param = request.getParameter("appsId");
        if (o_param != null)
            appsId = request.getParameter("appsId");

        //Get Form
        WapAppRollbackFilterForm wapAppRollbackFilterForm = (WapAppRollbackFilterForm) form;

        if (taskname.equalsIgnoreCase("filterview"))
        {
            wapAppRollbackFilterForm = (WapAppRollbackFilterForm) form;
            request.setAttribute("filter_text", wapAppRollbackFilterForm.getFilterText());
            request.setAttribute("filter_field", wapAppRollbackFilterForm.getFilterField());
            request.setAttribute("sort_field", wapAppRollbackFilterForm.getSortField());
            request.setAttribute("sort_order", wapAppRollbackFilterForm.getSortOrder());
        }

        //Start of 'Move to Pending DCR' related Task
        if (taskname.equalsIgnoreCase("move"))
        {
            if (appsId != null)
            {

                AimsApp aimsApp = null;
                AimsWapApp aimsWapApp = null;
                HashMap appWap = null;

                try
                {
                    appWap = WapApplicationManager.getWapApp(new Long(appsId), null);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    return mapping.getInputForward();
                }

                aimsApp = (AimsApp) appWap.get("AimsApp");
                aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");

                boolean success =
                    WapApplicationManager.moveToPendingDCR(
                        aimsApp.getAppsId(),
                        aimsApp.getAimsAllianceId(),
                        aimsApp.getAimsLifecyclePhaseId().toString(),
                        user.getUsername());

                if (success)
                {
                    AimsEventLite aimsEvent = null;
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_ROLLBACK_TO_STATUS_PENDING_DCR);

                    if (aimsEvent != null)
                    {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
                        AimsAllianc aimsAllianceOfApplication =
                            (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_CONTENT_TYPE,
                            MiscUtils.getWapAppContentType(aimsWapApp.getContentType()));
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }
                else
                {
                    AimsException aimsException = new AimsException("Error in changing application status");
                    aimsException.addException(new RecordNotFoundException(AimsConstants.ERROR_GENERIC_DATABASE_RES_MESSAGE));
                    saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));                    
                }
            }
            return mapping.getInputForward();
        }
        //End of 'Move to Pending DCR' related Task

        o_param = request.getParameter("sort_field");
        if (o_param != null)
        {
            wapAppRollbackFilterForm.setSortField((String) o_param);
            request.setAttribute("sort_field", (String) o_param);
        }

        o_param = request.getParameter("sort_order");
        if (o_param != null)
        {
            wapAppRollbackFilterForm.setSortOrder((String) o_param);
            request.setAttribute("sort_order", (String) o_param);
        }

        o_param = request.getParameter("filter_text");
        if (o_param != null)
        {
            request.setAttribute("filter_text", (String) o_param);
            wapAppRollbackFilterForm.setFilterText((String) o_param);
        }

        o_param = request.getParameter("filter_field");
        if (o_param != null)
        {
            request.setAttribute("filter_field", (String) o_param);
            wapAppRollbackFilterForm.setFilterField((String) o_param);
        }

        o_param = request.getParameter("page_id");
        if (o_param != null)
        {
            try
            {
                pageNo = Integer.parseInt((String) o_param);
            }
            catch (NumberFormatException ex)
            {
                //Ignore
            }
        }

        StringBuffer mainQuery = new StringBuffer();

        mainQuery
            .append(WapApplicationManager.getQueryForColumnsToDisplay())
            .append(WapApplicationManager.getNormalQueryForGetApps())
            .append(
                WapApplicationManager.getFilterQueryForGetApps(
                    getValueFromHash(fieldMapper, wapAppRollbackFilterForm.getFilterField()),
                    wapAppRollbackFilterForm.getFilterText()))
            .append(
                WapApplicationManager.getOrderByQueryForGetApps(
                    getValueFromHash(fieldMapper, wapAppRollbackFilterForm.getSortField()),
                    wapAppRollbackFilterForm.getSortOrder()));

        int rows = WapApplicationManager.getPageCountOfSubmittedDCRApps(mainQuery);

        System.out.println("Total Records from query is: " + rows + ".");
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
        log.debug("Max Page : " + pmax);

        if (pageNo < 1)
            pageNo = 1;
        else if (pageNo > pmax)
            pageNo = pmax;

        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        Collection aimsApps = null;
        Collection aimsApplications = new ArrayList(); //Collection to set in 'Request'

        try
        {
            aimsApps = WapApplicationManager.getSubmittedDCRApps(mainQuery, PAGE_LENGTH, pageNo);
        }
        catch (Exception ex)
        {}

        WapRollbackAppInformation appInfo = null;
        if (aimsApps != null)
        {
            Object[] appValues = null;
            for (Iterator it = aimsApps.iterator(); it.hasNext();)
            {
                appValues = (Object[]) it.next();
                appInfo = new WapRollbackAppInformation();
                appInfo.setAppsId((Long) appValues[0]); // appsId
                appInfo.setCompanyName((String) appValues[1]); // companyName
                appInfo.setTitle((String) appValues[2]); // title
                appInfo.setCreatedDate((Date) appValues[3]); // createdDate
                appInfo.setPhaseId((Long) appValues[4]); // phaseId
                appInfo.setPhaseName((String) appValues[5]); // phaseName
                appInfo.setAllianceId((Long) appValues[6]); // allianceId
                appInfo.setUrlSetupAction(ManageApplicationsConstants.WAP_APPLICATION_SETUP_URL);

                //Adding to Collection
                aimsApplications.add(appInfo);
            }
        }
        request.setAttribute("AimsApplicationsInformation", aimsApplications);

        return mapping.findForward(forward);
    }

    /**
    *   This function fills the Hash, mapping 'Resource Field Constant' with 'DB Field Constant'
    */
    public void fillFieldMapper()
    {
        fieldMapper.put("2", FIELD_COMPANY_NAME);
        fieldMapper.put("4", FIELD_TITLE);
        fieldMapper.put("7", FIELD_CREATE_DATE_NAME);
        fieldMapper.put("9", FIELD_PHASE_NAME);
    }

    /**
    *   This function gets value from HashMap
    */
    public String getValueFromHash(HashMap collection, String key)
    {
        if (collection.containsKey(key))
            return (String) collection.get(key);
        else
            return "";
    }

}
