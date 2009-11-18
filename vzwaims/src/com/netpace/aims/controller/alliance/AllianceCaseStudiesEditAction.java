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

import com.netpace.aims.util.AimsConstants;

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
 * @struts.action path="/allianceCaseStudiesEdit"                
 *                scope="request" 
 *				  name="AllianceCaseStudyForm"
 *				  validate="true"
 *                input="/alliance/caseStudyCreateUpdate.jsp"
 * @struts.action-forward name="view" path="/allianceCaseStudies.do?task=view" redirect="true"
 * @author Rizwan Qazi
 */

public class AllianceCaseStudiesEditAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceCaseStudiesEditAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {      

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));           

		HttpSession session = request.getSession(); 
        AllianceCaseStudyForm allianceCaseStudyForm = (AllianceCaseStudyForm) form;
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		ActionErrors errors = new ActionErrors();		
		
		Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();
		Long user_id = user.getUserId();
		Long caseStudyId = null;

        String forward = "view";
        
        log.debug("Task : " + taskname);			
			 

		if (taskname.equalsIgnoreCase("edit"))
		{
			caseStudyId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("caseStudyId"),"0"));
				
			try
			{
				
				AimsCaseStudiesManager.UpdateCaseStudy	(	
															caseStudyId,
															allianceCaseStudyForm.getCaseStudyName(),
															allianceCaseStudyForm.getCaseStudyDesc(),
															allianceCaseStudyForm.getComments(),
															allianceCaseStudyForm.getCaseStudyDoc(),
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

			forward = "view";
			
		} 
		
		
		if (taskname.equalsIgnoreCase("create"))
		{
				log.debug("Inside create of CreateCaseStudy " );			
			try
			{
				
				AimsCaseStudiesManager.CreateCaseStudy	(																
															allianceCaseStudyForm.getCaseStudyName(),
															allianceCaseStudyForm.getCaseStudyDesc(),
															allianceCaseStudyForm.getComments(),
															allianceCaseStudyForm.getCaseStudyDoc(),
															user_id,
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
