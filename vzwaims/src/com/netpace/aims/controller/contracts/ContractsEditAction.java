package com.netpace.aims.controller.contracts;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/contractsEdit"                
 *                scope="request" 
 *				  name="ContractForm"
 *				  validate="true"
 *                input="/contracts/contractCreateUpdate.jsp"  
 * @struts.action-forward name="view" path="/contracts/contractsInfoView.jsp" 
 * @author Rizwan Qazi
 */
public class ContractsEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(ContractsEditAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTRACTS);
        ContractForm contractForm = (ContractForm)form;
        
       // String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        
        String taskname = "";
    	if((contractForm.getTaskName() != null) && (contractForm.getTaskName() != ""))
		{
			if(contractForm.getTaskName().equalsIgnoreCase("createForm"))
			{
				 taskname = "create";
			}
			else if(contractForm.getTaskName().equalsIgnoreCase("editForm"))
			{
				 taskname = "edit";
			}
			else if(contractForm.getTaskName().equalsIgnoreCase("view"))
			{
				 taskname = "view";
			}
		}		

        if (taskname.equalsIgnoreCase("edit"))
        {
            if (!(AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.UPDATE)))
            {
                throw new AimsSecurityException();
            }
        }

        if (taskname.equalsIgnoreCase("create"))
        {
            if (!(AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.INSERT)))
            {
                throw new AimsSecurityException();
            }
        }

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long contract_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("contract_id"), "0"));
        //ContractForm contractForm = (ContractForm) form;
        Collection AimsContracts = null;
        String forward = "";
        int PAGE_LENGTH = user.getRowsLength().intValue(); //;
        int pageNo = 1;

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit") || taskname.equalsIgnoreCase("create"))
        {

            try {
				ContractsManager.createUpdateContracts(
				    new Long(StringFuncs.initializeStringGetParam(contractForm.getContractId(), "0")),
				    contractForm.getContractTitle(),
				    contractForm.getContractVersion(),
				    contractForm.getContractStatus(),
				    contractForm.getContractExpiryDate(),
                    contractForm.getContractDocumentTempFileId(),
				    contractForm.getContractHtmlDocumentTempFileId(),                    
                    contractForm.getClickThroughContract(),                    
                    contractForm.getIfContractNegotiable(),
				    contractForm.getComments(),
				    new Long(contractForm.getPlatformId()),
				    contractForm.getAmendmentIds(),
				    contractForm.getAmendmentTitle(),
				    contractForm.getAmendmentVersion(),
				    contractForm.getAmendmentStatus(),
				    contractForm.getAmendmentExpiryDate(),
				    contractForm.getAmendmentDocument(),
				    contractForm.getAmendmentComments(),
				    contractForm.getIsBoboContract(),
				    user_name);

                //after save, //set temp file ids to 0
                contractForm.setContractDocumentTempFileId(new Long(0));
                contractForm.setContractHtmlDocumentTempFileId(new Long(0));

                if (taskname.equalsIgnoreCase("create")) {
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.ContractForm.create.success",
                            StringFuncs.NullValueReplacement(contractForm.getContractTitle())));
                    saveMessages(request, messages);
                }
                else if (taskname.equalsIgnoreCase("edit")) {
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.ContractForm.edit.success", 
                            StringFuncs.NullValueReplacement(contractForm.getContractTitle())));
                    saveMessages(request, messages);
                }

                int rows = ContractsManager.getContractsCount(contractForm.getSelectedFilterValue(), "");
				int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

				if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
				{
				    pageNo = 1;
				    pmax = 1;
				}

				AimsContracts = ContractsManager.getContracts(user_type, PAGE_LENGTH, pageNo,"contract.contractTitle","asc",contractForm.getSelectedFilterValue(), "");
				this.setFilterString(request,contractForm);
				request.setAttribute("AimsContracts", AimsContracts);
				request.setAttribute("page_id", new Integer(pageNo));
				request.setAttribute("page_max", new Integer(pmax));
				request.setAttribute("sortBy", "1");
				request.setAttribute("sortOrder", "asc");

				forward = "view";
			} catch (UniqueConstraintException e) {
				ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(e.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return mapping.getInputForward();				
			}
        }

        return mapping.findForward(forward);
    }
    private void setFilterString(HttpServletRequest request, ContractForm contactForm) {
        StringBuffer value = new StringBuffer();

        for (int i=0; i<contactForm.getSelectedFilterValue().length; i++) {
        	if (contactForm.getSelectedFilterValue()[i].equalsIgnoreCase("SHOW_ALL")){
        		value.append("Showing All");
        		break;
        	}
        	else if (i != (contactForm.getSelectedFilterValue().length-1)){
        		value.append(contactForm.getContractStatus(contactForm.getSelectedFilterValue()[i])).append(", ");
        	}
        	else {
        		value.append(contactForm.getContractStatus(contactForm.getSelectedFilterValue()[i]));
        	}
        }
        request.setAttribute("filterValue", value.toString());
    }    
}
