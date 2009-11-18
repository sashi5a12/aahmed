package com.netpace.aims.controller.devices;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsLinesOfBusinessManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/linesOfBusinessViewDelete"                
 *                name="LinesOfBusinessForm" 
 *                scope="request"
 *				  validate="false"
 *                input="/masters/linesOfBusinessViewDelete.jsp"               
 * @struts.action-forward name="view" path="/masters/linesOfBusinessViewDelete.jsp"
 * @struts.action-exception key="error.masters.LinesOfBusinessForm.integrity" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Adnan Makda
 */
public class LinesOfBusinessViewDeleteAction extends BaseAction
{

    static Logger log = Logger.getLogger(LinesOfBusinessViewDeleteAction.class.getName());

    /**
     * This method is used to take the user to the appropriate screen
     * related to the LinesOfBusiness. 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.LINES_OF_BUSINESS);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession();
        if (taskname.equalsIgnoreCase("view"))
        {
            Collection aimsLinesOfBusiness = AimsLinesOfBusinessManager.getLinesOfBusiness();
            session.setAttribute("AimsLinesOfBusiness", aimsLinesOfBusiness);
            return mapping.findForward("view");
        }
        if (taskname.equalsIgnoreCase("delete"))
        {
            int delCount = AimsLinesOfBusinessManager.deleteLineOfBusiness(Integer.parseInt(request.getParameter("linesOfBusinessId")));
            Collection aimsLinesOfBusiness = AimsLinesOfBusinessManager.getLinesOfBusiness();
            request.setAttribute("AimsLinesOfBusiness", aimsLinesOfBusiness);
            return mapping.findForward("view");
        }

        return mapping.findForward("view");
    }
}
