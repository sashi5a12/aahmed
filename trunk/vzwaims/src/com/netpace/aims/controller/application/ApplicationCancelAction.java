package com.netpace.aims.controller.application;

import org.apache.struts.action.*;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.*;


/**
 * This class takes care of action invoked on clicking cancel button.
 *											 
 * @struts.action path="/applicationCancel"  
 *                name="ApplicationCancelForm" 
 *                scope="request"
 * @struts.action-forward name="viewAllApps" path="/applicationsViewDelete.do?task=view&amp;app_type=all_apps"
 * @struts.action-forward name="viewMyApps" path="/applicationsViewDelete.do?task=view&amp;app_type=my_apps"
 * @author Adnan Makda
 */
public class ApplicationCancelAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(ApplicationCancelAction.class.getName());
  
		public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

       	if (ApplicationHelper.checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_ALL_APPLICATIONS)) 
					return mapping.findForward("viewAllApps");
				else
					return mapping.findForward("viewMyApps");
        
    }
}

			