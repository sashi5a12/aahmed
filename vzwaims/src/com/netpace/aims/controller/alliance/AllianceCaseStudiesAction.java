package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import javax.servlet.http.*;

import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.util.*;

import com.netpace.aims.util.AimsConstants;

import com.netpace.aims.controller.BaseAction;

import java.util.*;


import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/allianceCaseStudies"                
 *                scope="request" 
 *				  name="AllianceCaseStudyForm"
 *				  validate="false"
 *                input="/alliance/allianceCaseStudiesView.jsp"  
 * @struts.action-forward name="delete" path="/allianceCaseStudies.do?task=view" redirect="true"
 * @struts.action-forward name="view" path="/alliance/allianceCaseStudiesView.jsp" 
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceCaseStudiesView.jsp" 
 * @author Rizwan Qazi
 */
public class AllianceCaseStudiesAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceCaseStudiesAction.class.getName());
  
    /**
     * This method lets the user View, Edit or Delete a case study.
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
		HttpSession session = request.getSession(); 

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = null;	
        Long case_study_id = null;
        String case_study_status = null;
		Collection AimsCaseStudies = null;
        String forward = "";
        String company_name = "";
		
        
        log.debug("Task : " + taskname);

        if (user_type.equalsIgnoreCase("V"))
        {		
            
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }            
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
            

            if (taskname.equalsIgnoreCase("changeStatus"))
            {
                
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                    AimsSecurityManager.UPDATE))) 
                {
                    throw new AimsSecurityException(); 
                }                 
                case_study_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("case_study_id"), "0")); 
                case_study_status = StringFuncs.NullValueReplacement(request.getParameter("vzwStatus"));
                AimsCaseStudiesManager.changeCaseStudiesStatus(case_study_id, case_study_status, user_id);
            }
            AimsCaseStudies = AimsCaseStudiesManager.getAllianceCaseStudies(alliance_id, user_type);			

            request.setAttribute("AimsCaseStudies", AimsCaseStudies);
            
            forward = "vzwView";
        } 

        if (user_type.equalsIgnoreCase("A"))
        {		
                        
            alliance_id = user.getAimsAllianc();
            if (taskname.equalsIgnoreCase("view"))	{	

                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CASESTUDIES, 
                                                                    AimsSecurityManager.SELECT))) 
                {
                    throw new AimsSecurityException(); 
                }   
                    
                AimsCaseStudies = AimsCaseStudiesManager.getAllianceCaseStudies(alliance_id, user_type);			

                request.setAttribute("AimsCaseStudies", AimsCaseStudies);		
            
                forward = "view";
            } 		

            if (taskname.equalsIgnoreCase("delete"))	{	

                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CASESTUDIES, 
                                                                    AimsSecurityManager.DELETE))) 
                {
                    throw new AimsSecurityException(); 
                }

                int delCount = AimsCaseStudiesManager.deleteCaseStudy(
                                    Integer.parseInt(StringFuncs.initializeStringGetParam(request.getParameter("caseStudyId"),"0"))
                                                                     );
            
                forward = "delete";
            } 
        }
		
        company_name = AllianceManager.getAllianceCompanyName(alliance_id);
        request.setAttribute("companyName", company_name);
        request.setAttribute("alliance_id", alliance_id);
		return mapping.findForward(forward);
    }
}
