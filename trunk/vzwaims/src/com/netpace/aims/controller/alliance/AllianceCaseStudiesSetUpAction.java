package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceCaseStudiesSetup"  
 *                name="AllianceCaseStudyForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/caseStudyCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateView" path="/alliance/caseStudyCreateUpdateView.jsp"
 * @author Rizwan Qazi
 */
public class AllianceCaseStudiesSetUpAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceCaseStudiesSetUpAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		String forward = "";
		int spotlightTypeId = StringFuncs.initializeIntGetParam(request.getParameter("spotLightTypeId"), 0);

		if (taskname.equalsIgnoreCase("createForm"))
		{	
			forward = "createUpdate";       
		} 


		if (taskname.equalsIgnoreCase("editForm"))
		{
			forward = "createUpdate";      		
		} 

		if (taskname.equalsIgnoreCase("editFormView"))
		{
			forward = "createUpdateView";      		
		} 

		return mapping.findForward(forward);
    }
}
