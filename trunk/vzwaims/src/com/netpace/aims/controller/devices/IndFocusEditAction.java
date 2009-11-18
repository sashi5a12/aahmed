package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsIndFocusManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of adding or updating an Industry focus.
 * If the Industry Focus already exists it throws a UniqueConstraintException.
 *
 * @struts.action path="/indFocusEdit"   
 *                name="IndFocusEditForm" 
 *                scope="request"				  
 *                input="/masters/indFocusEditForm.jsp"
 *                validate="true"  
 * @struts.action-forward name="view" path="/indFocus.do?task=view" redirect="true"
 * @struts.action-exception key="error.masters.IndFocus.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.UniqueConstraintException
 */
public class IndFocusEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(IndFocusEditAction.class.getName());

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a 
     * User object if the user is valid. It then moves the user to his logged in page.
     * 
     * If the username and password does not match it throws 
     * a InvalidUserException. 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.INDUSTRY_FOCUS);

        String taskname = request.getParameter("task");
        String forward = "";
        HttpSession session = request.getSession(true);
        String currUser = "rqazi";

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit"))
        {

            log.debug("This is the all submit image clicked : " + taskname);

            AimsIndustryFocu AimsIndFocus = (AimsIndustryFocu) session.getAttribute("AimsIndFocuses");

            //Long industryId = Long.getLong( request.getParameter("industryId"));
            String industryName = request.getParameter("industryName");
            String industryDescription = request.getParameter("industryDescription");

            AimsIndFocus.setIndustryName(industryName);
            AimsIndFocus.setIndustryDescription(industryDescription);

            log.debug("langName : " + industryName);

            AimsIndFocusManager.saveOrUpdateIndFocus(AimsIndFocus);

            forward = "view";

        }

        if (taskname.equalsIgnoreCase("create"))
        {

            log.debug("This is CREATE : " + taskname);

            String industryName = request.getParameter("industryName");
            String industryDescription = request.getParameter("industryDescription");

            AimsIndustryFocu AimsIndFocus = new AimsIndustryFocu();

            AimsIndFocus.setIndustryName(industryName);
            AimsIndFocus.setIndustryDescription(industryDescription);

            AimsIndFocusManager.saveOrUpdateIndFocus(AimsIndFocus);

            forward = "view";
        }

        return mapping.findForward(forward);

    }
}
