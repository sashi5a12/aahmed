package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;

import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.core.*;

import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceContractInfoEdit"                
 *                scope="request" 
 *				  name="AllianceContractDetailsForm"
 *				  validate="true"
 *                input="/alliance/allianceContractsInfoUpdate.jsp"
 * @struts.action-forward name="view" path="/allianceContracts.do?task=view" redirect="true"
 * @author Rizwan Qazi
 */

public class AllianceContractInfoEditAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceContractInfoEditAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {      

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task")); 
		String subtaskname =  StringFuncs.NullValueReplacement(request.getParameter("subtask"));

        log.debug("Task : " + taskname);
		log.debug("SubTask : " + subtaskname);

		if (subtaskname.equalsIgnoreCase(AimsConstants.CONTRACT_ACCEPTED))
		{
			subtaskname = AimsConstants.CONTRACT_ACCEPTED;
		}
		if (subtaskname.equalsIgnoreCase(AimsConstants.CONTRACT_DECLINED))
		{
			subtaskname = AimsConstants.CONTRACT_DECLINED;
		}
		if (subtaskname.equalsIgnoreCase(AimsConstants.CONTRACT_REQUEST_CHANGE))
		{
			subtaskname = AimsConstants.CONTRACT_REQUEST_CHANGE;
		}

		HttpSession session = request.getSession(); 
        AllianceContractDetailsForm allianceContractInfoForm = (AllianceContractDetailsForm) form;
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		ActionErrors errors = new ActionErrors();		
		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();
		Long user_id = user.getUserId();

        String forward = "view";
			 

		if (taskname.equalsIgnoreCase("edit"))
		{

			try
			{
				
				log.debug("INSIDE Edit Action EDIT! ");	
				AllianceContractInfoManager.UpdateAllianceContractInfo
																(	
																	alliance_id,
																	new Long(allianceContractInfoForm.getAllianceContractId()),
																	subtaskname,
																	allianceContractInfoForm.getModifiedContDoc(),																	
																	user_id
																);
			}
			catch (AimsException ae)
			{				

				log.debug("ae.getMessageKey() in Action Class : " +  ae.getMessageKey() );
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));	
				saveErrors(request, errors);
				return mapping.getInputForward();
			}		

			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.edit.success"));			
			saveMessages(request, messages);
			forward = "view";
			
		} 
		
		
		return mapping.findForward(forward);
    }
}
