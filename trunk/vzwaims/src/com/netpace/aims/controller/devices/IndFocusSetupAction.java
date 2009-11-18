package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * @struts.action path="/indFocusSetup"                
 *                scope="request" 
 *				  name="IndFocusEditForm"
 *				  validate="false"
 * @struts.action-forward name="editForm" path="/masters/indFocusEditForm.jsp"
 * @struts.action-forward name="createForm" path="/masters/indFocusCreate.jsp"
 * @author Rizwan Qazi 
 */
public class IndFocusSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(IndFocusSetupAction.class.getName());

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a 
     * User object if the user is valid. It then moves the user to his logged in page.
     * 
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.INDUSTRY_FOCUS);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession(true);
        String currUser = "rqazi";
        String forward = "";

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("createForm"))
        {
            forward = "createForm";
        }

        if (taskname.equalsIgnoreCase("editForm"))
        {
            forward = "editForm";
        }

        return mapping.findForward(forward);
    }
}
