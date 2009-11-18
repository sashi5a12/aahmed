package com.netpace.aims.controller.devices;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsIndFocusManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/indFocus"                
 *                scope="request" 
 *				  name="IndFocusEditForm"
 *				  validate="false"
 *                input="/masters/indFocusView.jsp"               
 * @struts.action-forward name="view" path="/masters/indFocusView.jsp"
 * @struts.action-exception key="error.masters.IndFocus.integrity" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class IndFocusAction extends BaseAction
{

    static Logger log = Logger.getLogger(IndFocusAction.class.getName());

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
        HttpSession session = request.getSession();
        String currUser = "rqazi";
        String forward = "";

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("view"))
        {

            Collection AimsIndFocuses = AimsIndFocusManager.getIndFocuses();
            log.debug("The size of the set is " + AimsIndFocuses.size());
            session.setAttribute("AimsIndFocuses", AimsIndFocuses);

            forward = "view";
        }

        if (taskname.equalsIgnoreCase("delete"))
        {

            int delCount = AimsIndFocusManager.deleteIndustryfocus(Integer.parseInt(request.getParameter("industryId")));

            Collection AimsIndFocuses = AimsIndFocusManager.getIndFocuses();
            log.debug("The size of the set is " + AimsIndFocuses.size());
            request.setAttribute("AimsIndFocuses", AimsIndFocuses);

            forward = "view";
        }

        return mapping.findForward(forward);
    }
}
