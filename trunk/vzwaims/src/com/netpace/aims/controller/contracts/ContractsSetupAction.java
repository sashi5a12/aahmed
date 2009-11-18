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
 * @struts.action path="/contractsSetup"  
 *                name="ContractForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/contracts/contractCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateView" path="/contracts/contractCreateUpdateView.jsp"
 * @struts.action-forward name="createUpdateAllianceView" path="/alliance/contractAllianceView.jsp"
 * @author Rizwan Qazi
 */
public class ContractsSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(ContractsSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	ContractForm contractForm = (ContractForm)form;
        if( request.getParameter("task") != null && !"".equals(request.getParameter("task")) ){
        	contractForm.setTaskName(request.getParameter("task"));
        }
        
        String taskname = contractForm.getTaskName(); //StringFuncs.NullValueReplacement(request.getParameter("task"));
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        String forward = "";

        //Throw AimsSecurityException() if No Privileges
        //Tweak for Alliance Users wanting to see Contracts
        
        if (!taskname.equalsIgnoreCase("editViewForm"))
            checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTRACTS);

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

        if (taskname.equalsIgnoreCase("allianceViewForm"))
        {
            forward = "createUpdateAllianceView";
        }

        return mapping.findForward(forward);
    }
}
