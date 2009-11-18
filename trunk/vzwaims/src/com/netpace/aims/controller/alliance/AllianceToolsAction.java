package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import javax.servlet.http.*;
import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.alliance.*;

import com.netpace.aims.util.AimsConstants;

import com.netpace.aims.controller.BaseAction;

import java.util.*;


import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceTools"                
 *                scope="request" 
 *                input="/alliance/allianceStatusView.jsp"  
 * @struts.action-forward name="view" path="/alliance/allianceToolsView.jsp"
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceToolsView.jsp"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class AllianceToolsAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceToolsAction.class.getName());
  
    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
	 *
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  request.getParameter("task");   
		HttpSession session = request.getSession(); 

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = null;
        String company_name = "";	
        String forward = "";
        
        log.debug("Task : " + taskname);

		if (taskname.equalsIgnoreCase("view"))
		{			
			if (user_type.equalsIgnoreCase("A"))
			{		
				alliance_id = user.getAimsAllianc();
				forward = "view";
			} 	

			if (user_type.equalsIgnoreCase("V"))
			{		
				alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0")); 
				forward = "vzwView";
			}
            company_name = AllianceManager.getAllianceCompanyName(alliance_id);
            request.setAttribute("companyName", company_name);
		} 
		
		

		return mapping.findForward(forward);
    }
}
