package com.netpace.aims.controller.contracts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/amendmentsSetup"  
 *                name="AmendmentForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/contracts/amendmentCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateView" path="/contracts/amendmentCreateUpdateView.jsp"
 * @author Rizwan Qazi
 */
public class AmendmentsSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(AmendmentsSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.AMENDMENTS);

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        String forward = "";

        if (taskname.equalsIgnoreCase("createForm"))
        {
            forward = "createUpdate";
        }

        if (taskname.equalsIgnoreCase("editForm"))
        {
            forward = "createUpdate";
        }

        if (taskname.equalsIgnoreCase("editViewForm"))
        {
            forward = "createUpdateView";
        }

        return mapping.findForward(forward);
    }
}
