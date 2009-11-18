package com.netpace.aims.controller.devices;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsLinesOfBusinessManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsLinesOfBusiness;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/linesOfBusinessInsUpd"  
 *                name="LinesOfBusinessForm" 
 *                scope="request"				  
 *                input="/masters/linesOfBusinessUpdate.jsp"   
 *				  validate="true"      
 * @struts.action-forward name="view" path="/linesOfBusinessViewDelete.do?task=view" redirect="true"
 * @struts.action-exception key="error.masters.LinesOfBusinessForm.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Rizwan Qazi
 */
public class LinesOfBusinessInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(LinesOfBusinessInsertUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.LINES_OF_BUSINESS);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String forward = "view";

        if (taskname.equalsIgnoreCase("create"))
        {
            LinesOfBusinessForm linesOfBusinessForm = (LinesOfBusinessForm) form;
            AimsLinesOfBusiness aimsLinesOfBusiness = new AimsLinesOfBusiness();
            aimsLinesOfBusiness.setLineOfBusinessName(linesOfBusinessForm.getLineOfBusinessName());
            aimsLinesOfBusiness.setLineOfBusinessDesc(linesOfBusinessForm.getLineOfBusinessDesc());
            aimsLinesOfBusiness.setCreatedBy(currUser);
            aimsLinesOfBusiness.setCreatedDate(new Date());
            aimsLinesOfBusiness.setLastUpdatedBy(currUser);
            aimsLinesOfBusiness.setLastUpdatedDate(new Date());

            AimsLinesOfBusinessManager.saveOrUpdateLineOfBusiness(aimsLinesOfBusiness);
        }

        if (taskname.equalsIgnoreCase("edit"))
        {
            log.debug("Taskname : " + taskname);

            AimsLinesOfBusiness aimsLinesOfBusiness = (AimsLinesOfBusiness) session.getAttribute("AimsLinesOfBusiness");

            LinesOfBusinessForm linesOfBusinessForm = (LinesOfBusinessForm) form;

            aimsLinesOfBusiness.setLineOfBusinessName(linesOfBusinessForm.getLineOfBusinessName());
            aimsLinesOfBusiness.setLineOfBusinessDesc(linesOfBusinessForm.getLineOfBusinessDesc());
            aimsLinesOfBusiness.setLastUpdatedBy(currUser);
            aimsLinesOfBusiness.setLastUpdatedDate(new Date());

            AimsLinesOfBusinessManager.saveOrUpdateLineOfBusiness(aimsLinesOfBusiness);
        }

        return mapping.findForward(forward);
    }
}
