package com.netpace.aims.controller.devices;

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
 * @struts.action path="/linesOfBusinessSetup"  
 *                name="LinesOfBusinessForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="update" path="/masters/linesOfBusinessUpdate.jsp"
  * @author Rizwan Qazi
 */
public class LinesOfBusinessSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(LinesOfBusinessSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.LINES_OF_BUSINESS);

        String taskname = request.getParameter("task");
        String forward = "view";

        if (taskname.equalsIgnoreCase("createForm"))
        {
            forward = "update";
        }

        if (taskname.equalsIgnoreCase("editForm"))
        {
            forward = "update";
        }

        return mapping.findForward(forward);
    }
}
