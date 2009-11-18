package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/applicationSearch"  
 *                name="ApplicationSearchForm" 
 *                scope="request"
 *                input="/application/applicationSearch.jsp"   
 *                validate="true"
 * @struts.action-forward name="viewAllApps" path="/applicationsViewDelete.do?task=view&amp;app_type=all_apps"
 * @struts.action-forward name="viewMyApps" path="/applicationsViewDelete.do?task=view&amp;app_type=my_apps"
 * @struts.action-forward name="onerror" path="/application/applicationSearch.jsp"
 * @author Adnan Makda
 */
public class ApplicationSearchAction extends BaseAction
{

    static Logger log = Logger.getLogger(ApplicationSearchAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String forward = "viewMyApps";
        String taskname = "";

        Object o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        log.debug("TASK is: " + taskname);

        if (taskname.equalsIgnoreCase("search"))
        {
            log.debug("IN SEARCH ");
            ApplicationSearchForm applicationSearchForm = (ApplicationSearchForm) form;
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_TITLE, applicationSearchForm.getTitle());
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_PLATFORM, applicationSearchForm.getAimsPlatformId());
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_STATUS, applicationSearchForm.getAimsLifecyclePhaseId());
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_CATEGORY, applicationSearchForm.getAimsAppCategoryId());
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_SUB_CATEGORY, applicationSearchForm.getAimsAppSubCategoryId());
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_BREW_APP_TYPE, applicationSearchForm.getBrewAppType());
            session.setAttribute(ManageApplicationsConstants.SESSION_APP_SEARCH_CONTRACT, applicationSearchForm.getAimsContractId());
            session.setAttribute(
                ManageApplicationsConstants.SESSION_APP_SEARCH_DEVICES,
                StringFuncs.ConvertArrToString(applicationSearchForm.getListSelectedDevices(), ","));
            request.setAttribute("ADV_SEARCH", "true");
        }

        if (ApplicationHelper.checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_ALL_APPLICATIONS))
            forward = "viewAllApps";
        else
            forward = "viewMyApps";

        return mapping.findForward(forward);
    }
}