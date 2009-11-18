package com.netpace.aims.controller.contracts;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/amendments"                
 *                scope="request" 
 *				  name="AmendmentForm"
 *				  validate="false"
 *                input="/contracts/amendmentsInfoView.jsp"  
 * @struts.action-forward name="view" path="/contracts/amendmentsInfoView.jsp" 
 * @struts.action-forward name="delete" path="/contracts/amendmentsInfoView.jsp"
 * @author Rizwan Qazi
 */
public class AmendmentsAction extends BaseAction
{

    static Logger log = Logger.getLogger(AmendmentsAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.AMENDMENTS);

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();

        AmendmentForm amendmentForm = (AmendmentForm) form;
        Collection AimsAmendments = null;
        ActionErrors errors = new ActionErrors();

        String forward = "";
        int PAGE_LENGTH = 10;
        int pageNo = 1;
        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("view"))
        {
            forward = "view";
        }

        if (taskname.equalsIgnoreCase("delete"))
        {

            Long amendment_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("amendment_id"), "0"));

            try
            {
                ContractsManager.deleteAmendment(amendment_id);
            }
            catch (AimsException ae)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
            }
            forward = "delete";
        }

        int rows = ContractsManager.getAmendmentCount();
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        AimsAmendments = ContractsManager.getAmendments(PAGE_LENGTH, pageNo);
        request.setAttribute("AimsAmendments", AimsAmendments);
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        return mapping.findForward(forward);
    }
}
