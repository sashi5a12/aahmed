package com.netpace.aims.controller.alliance;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;



/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceContractSetup"  
 *                name="AllianceContractDetailsForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="update" path="/alliance/allianceContractsInfoUpdate.jsp"
 * @author Rizwan Qazi
 */
public class AllianceContractSetupAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceContractSetupAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
        String forward = "update";


		if (taskname.equalsIgnoreCase("editForm"))
		{
			forward = "update"; 			
		} 



		return mapping.findForward(forward);
    }
}
