package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import javax.servlet.http.*;

import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.util.*;


import com.netpace.aims.controller.BaseAction;

import java.util.*;


import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/allianceSpotlights"                
 *                scope="request" 
 *				  name="AllianceForm"
 *				  validate="false"
 *                input="/common/logged-in.jsp"
 * @struts.action-forward name="caseStudyView" path="/alliance/allianceSLCaseStudiesView.jsp" 
 * @author Rizwan Qazi
 */
public class AllianceSpotlightsAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceSpotlightsAction.class.getName());
  
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

        

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
		HttpSession session = request.getSession(); 

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
        Long alliance_id = null;
        String company_name = "";
        String showCreateButton = "Y";
        
        
        if (user_type.equalsIgnoreCase("A"))
        {		
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, 
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }              
            alliance_id = user.getAimsAllianc();               
        } 	

        if (user_type.equalsIgnoreCase("V"))
        {		
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }               
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));            
        } 

        company_name = AllianceManager.getAllianceCompanyName(alliance_id);
        request.setAttribute("companyName", company_name);
		
		Collection AimsSpotlightTypes = null;
		Collection AimsCaseStudies = null;
		Collection AimsSpotLights = null;
		Long spotlightTypeId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("spotLightTypeId"), AimsConstants.SPOTLIGHT_CASE_STUDY));
		Long spotlightId = new Long (StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"), "0"));
		
		int delCount = 0;

        String forward = "";
        

		AimsSpotlightTypes = AllianceSpotlightsManager.getSpotlightTypes(alliance_id, user_type);	

		request.setAttribute("AimsSpotlightTypes", AimsSpotlightTypes);


		if (taskname.equalsIgnoreCase("view"))	{			

            AimsSpotLights = AllianceSpotlightsManager.getSpotLightRecords(alliance_id, user_type, spotlightTypeId);
            request.setAttribute("AimsSpotLights", AimsSpotLights);

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
			{
		
                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLCaseStudiesView.jsp");
                    //forward = "caseStudyView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLCaseStudiesView.jsp");
                    //forward = "vzwCaseStudyView";
                } 
				
			}
			
			
			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
			{


                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLSalesPresentationView.jsp");    
                    //forward = "salesPresentationView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLSalesPresentationView.jsp");
                    //forward = "vzwSalesPresentationView";
                } 
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
			{
				

                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLSalesCollateralView.jsp");
                    //forward = "salesCollateralView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLSalesCollateralView.jsp");
                    //forward = "vzwSalesCollateralView";
                } 
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
			{
			
                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLTestimonyView.jsp");
                    //forward = "testimonyView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLTestimonyView.jsp");
                    //forward = "vzwTestimonyView";
                } 			
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
			{
			
                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLWhitePaperView.jsp");
                    //forward = "whitePaperView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLWhitePaperView.jsp");
                    //forward = "vzwWhitePaperView";
                } 			
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
			{
				
                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLSuccessStoryView.jsp");
                    //forward = "successStoryView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLSuccessStoryView.jsp");
                    //forward = "vzwSuccessStoryView";
                } 				
			}
			
			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
			{
				
                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLDemoView.jsp");
                    //forward = "demoView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLDemoView.jsp");
                    //forward = "vzwDemoView";
                } 				
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
			{
				
                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLSalesPartnerView.jsp");
                    //forward = "demoView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLSalesPartnerView.jsp");
                    //forward = "vzwDemoView";
                } 				
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_QRG)))
			{
				

                if (AimsSpotLights.size() >= 1)
                {
                    showCreateButton = "N";
                }

                request.setAttribute("showCreateButton", showCreateButton);

                if (user_type.equalsIgnoreCase("A"))
                {		
                    return new ActionForward("/alliance/allianceSLQRGView.jsp");
                    //forward = "demoView";             
                } 	

                if (user_type.equalsIgnoreCase("V"))
                {		
                    return new ActionForward("/alliance/vzwAllianceSLQRGView.jsp");
                    //forward = "vzwDemoView";
                } 				
			}
			
		} 

        if (taskname.equalsIgnoreCase("changeStatus") && user_type.equalsIgnoreCase("V"))	{
           
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                AimsSecurityManager.UPDATE))) 
            {
                throw new AimsSecurityException(); 
            }  
            Long spotlight_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"), "0")); 
            String spotlight_status = StringFuncs.NullValueReplacement(request.getParameter("vzwStatus"));
            AllianceSpotlightsManager.changeSpotLightStatus(spotlight_id, spotlight_status, user_id);
			AimsSpotLights = AllianceSpotlightsManager.getSpotLightRecords(alliance_id, user_type, spotlightTypeId);
			request.setAttribute("AimsSpotLights", AimsSpotLights);

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
			{
				return new ActionForward("/alliance/vzwAllianceSLCaseStudiesView.jsp");
                //forward = "vzwCaseStudyView";
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
			{
                return new ActionForward("/alliance/vzwAllianceSLSalesPresentationView.jsp");
                //forward = "vzwSalesPresentationView";
			}


			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
			{
			    return new ActionForward("/alliance/vzwAllianceSLSalesCollateralView.jsp");
                //forward = "vzwSalesCollateralView";
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
			{
				return new ActionForward("/alliance/vzwAllianceSLTestimonyView.jsp");
                //forward = "vzwTestimonyView";			
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
			{
				return new ActionForward("/alliance/vzwAllianceSLWhitePaperView.jsp");
                //forward = "vzwWhitePaperView";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
			{
				return new ActionForward("/alliance/vzwAllianceSLSuccessStoryView.jsp");
                //forward = "vzwSuccessStoryView";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
			{
				return new ActionForward("/alliance/vzwAllianceSLDemoView.jsp");
                //forward = "vzwDemoView";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
			{
				return new ActionForward("/alliance/vzwAllianceSLSalesPartnerView.jsp");
                //forward = "vzwDemoView";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_QRG)))
			{
				return new ActionForward("/alliance/vzwAllianceSLQRGView.jsp");
                //forward = "vzwDemoView";		
			}
        }

		if (taskname.equalsIgnoreCase("delete") && user_type.equalsIgnoreCase("A"))	{	

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, 
                                                                AimsSecurityManager.DELETE)))
            {
                throw new AimsSecurityException(); 
            }                   

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceCaseStudies.do?task=view");
                //forward = "caseStudyDelete";
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
			{

				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SALES_PRESENTATION);
                //forward = "salesPresentationDelete";
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
			{
				
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SALES_COLLATERAL);
                //forward = "salesCollateralDelete";
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_TESTIMONY);
                //forward = "testimonyDelete";			
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_WHITE_PAPER);
                //forward = "whitePaperDelete";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SUCCESS_STORY);
                //forward = "successStoryDelete";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_DEMO);
                //forward = "demoDelete";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SALES_PARTNERS);
                //forward = "successStoryDelete";		
			}

			if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_QRG)))
			{
				delCount = AllianceSpotlightsManager.deleteSpotLight(spotlightId);
				return new ActionForward("/allianceSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_QRG);
                //forward = "demoDelete";		
			}

		} 		
		
		return mapping.findForward(forward);
    }
}
