package com.netpace.aims.controller.contracts;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/amendmentsEdit"                
 *                scope="request" 
 *				  name="AmendmentForm"
 *				  validate="true"
 *                input="/contracts/amendmentCreateUpdate.jsp"  
 * @struts.action-forward name="view" path="/contracts/amendmentsInfoView.jsp" 
 * @author Rizwan Qazi
 */
public class AmendmentsEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(AmendmentsEditAction.class.getName());

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
        Long contract_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("amendment_id"), "0"));
        AmendmentForm amendmentForm = (AmendmentForm) form;
        Collection AimsAmendments = null;
        String forward = "";
        int PAGE_LENGTH = 10;
        int pageNo = 1;

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit") || taskname.equalsIgnoreCase("create"))
        {

            ContractsManager.createUpdateAmendments(
                new Long(StringFuncs.initializeStringGetParam(amendmentForm.getAmendmentId(), "0")),
                amendmentForm.getAmendmentTitle(),
                amendmentForm.getAmendmentVersion(),
                amendmentForm.getAmendmentStatus(),
                amendmentForm.getAmendmentExpiryDate(),
                amendmentForm.getAmendmentDocument(),
                amendmentForm.getComments(),
                user_name);

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
            forward = "view";
        }

        return mapping.findForward(forward);
    }
}
