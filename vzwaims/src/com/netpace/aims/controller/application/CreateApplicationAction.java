package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/createApplication"
 *                name="ApplicationsFilterForm"
 *                scope="request"
 *				  validate="false"
 *                input="/application/applicationsViewDelete.jsp"
 * @struts.action-forward name="view" path="/application/createApplication.jsp"
 * @author Adnan Makda.
 */
public class CreateApplicationAction extends BaseAction
{
    static Logger log = Logger.getLogger(CreateApplicationAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String forward = "view";

        //This LINE WILL CHECK FOR ACCESS PERMISSIONS TO THIS FUNCTIONALITY
        checkAccess(request, AimsPrivilegesConstants.MANAGE_CREATE_APPLICATION);

        return mapping.findForward(forward);
    }

}
