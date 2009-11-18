package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.alliance.*;

import com.netpace.aims.controller.BaseAction;

import java.util.*;


/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceSLCaseStudiesSetup"  
 *                name="AllianceCaseStudyForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdate" path="/alliance/caseStudySLCreateUpdate.jsp"
 * @author Rizwan Qazi
 */
public class AllianceSLCaseStudiesSetUpAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceSLCaseStudiesSetUpAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = user.getAimsAllianc();
		String forward = "";
		Collection AimsSpotlightTypes = null;

		int spotlightTypeId = StringFuncs.initializeIntGetParam(request.getParameter("spotLightTypeId"), 0);

		AimsSpotlightTypes = AllianceSpotlightsManager.getSpotlightTypes(alliance_id, user_type);	

		request.setAttribute("AimsSpotlightTypes", AimsSpotlightTypes);

		if (taskname.equalsIgnoreCase("createForm"))
		{	
			forward = "createUpdate";       
		} 


		if (taskname.equalsIgnoreCase("editForm"))
		{
			forward = "createUpdate";      		
		} 

		return mapping.findForward(forward);
    }
}
