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
import com.netpace.aims.bo.security.*;

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
 * @struts.action path="/salesPresentationEdit"                
 *                scope="request" 
 *				  name="AllianceSpotLightForm"
 *				  validate="true"
 *                input="/alliance/salesPresentationCreateUpdate.jsp"
 * @struts.action-forward name="view" path="/allianceSpotlights.do?task=view&amp;spotLightTypeId=2" redirect="true"
 * @author Rizwan Qazi
 */

public class SalesPresentationEditAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SalesPresentationEditAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {      

        if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, 
                                                            AimsSecurityManager.UPDATE))) 
        {
            throw new AimsSecurityException(); 
        } 

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));           

		HttpSession session = request.getSession(); 
        AllianceSpotLightForm allianceSpotLightForm = (AllianceSpotLightForm) form;
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		ActionErrors errors = new ActionErrors();		
		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();
		Long user_id = user.getUserId();
		Long spotLightId = null;
		Long spotLightTypeId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("spotLightTypeId"),"0"));

        String forward = "view";
        
        log.debug("Task : " + taskname);			
			 

		if (taskname.equalsIgnoreCase("edit"))
		{
			spotLightId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"),"0"));
				
			try
			{
				
				AllianceSpotlightsManager.UpdateSpotLight(	
															spotLightId,
															allianceSpotLightForm.getSpotlightTypeId(),
															allianceSpotLightForm.getSpotlightName(),
															allianceSpotLightForm.getSpotlightDesc(),															
															allianceSpotLightForm.getSpotlightFile(),
															user_name,
															alliance_id
														 );
			}
			catch (AimsException ae)
			{				

				log.debug("ae.getMessageKey() in Action Class : " +  ae.getMessageKey() );
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));	
				saveErrors(request, errors);
				return mapping.getInputForward();
			}		

			forward = "view";
			
		} 
		
		
		if (taskname.equalsIgnoreCase("create"))
		{
					

			try
			{
				
				AllianceSpotlightsManager.CreateSpotLight(			
															allianceSpotLightForm.getSpotlightTypeId(),
															allianceSpotLightForm.getSpotlightName(),
															allianceSpotLightForm.getSpotlightDesc(),															
															allianceSpotLightForm.getSpotlightFile(),
															user_name,
															alliance_id
														 );
			}
			catch (AimsException ae)
			{				

				log.debug("ae.getMessageKey() in Action Class : " +  ae.getMessageKey() );
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));	
				saveErrors(request, errors);
				return mapping.getInputForward();
			}		

			forward = "view";
			
		} 

		return mapping.findForward(forward);
    }
}
