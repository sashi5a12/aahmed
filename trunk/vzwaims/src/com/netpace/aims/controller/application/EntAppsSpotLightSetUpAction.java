package com.netpace.aims.controller.application;

import org.apache.struts.action.*;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.application.EntAppsSpotlightsManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.security.*;

import java.util.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class allows the user to proceed to the Add/Update spotlights functionality.
 *
 * @struts.action path="/entAppsSpotLightSetup"  
 *                name="EntAppsSpotLightForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdateCaseStudy" path="/application/caseStudyCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateSalesPresentation" path="/application/salesPresentationCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateSalesCollateral" path="/application/salesCollateralCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateTestimony" path="/application/testimonyCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateWhitePaper" path="/application/whitePaperCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateSuccessStory" path="/application/successStoryCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateDemo" path="/application/demoCreateUpdate.jsp" 
 * @struts.action-forward name="createUpdateSalesPartner" path="/application/salesPartnerCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateQRG" path="/application/qrgCreateUpdate.jsp" 
  * @author Ahson Imtiaz
 */
public class EntAppsSpotLightSetUpAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(EntAppsSpotLightSetUpAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {      
    	
      EntAppsSpotLightForm frm = (EntAppsSpotLightForm) form;
      Long enterpriseAppsId = frm.getEnterpriseAppsId();
      
      log.debug("before check **************** " + AimsEntAppsManager.checkAccessToSpotlight(enterpriseAppsId.toString()) );
      if (enterpriseAppsId == null || !AimsEntAppsManager.checkAccessToSpotlight(enterpriseAppsId.toString()) )  {
      		throw new AimsSecurityException(); 
      }
      log.debug("After check **************** " );
      
      String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));   
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = user.getAimsAllianc();
		Collection AimsSpotlightTypes = null;
		
        if (user_type.equalsIgnoreCase("V"))
        {		
            throw new AimsSecurityException(); 
        } 

		AimsSpotlightTypes = EntAppsSpotlightsManager.getSpotlightTypes();	

		request.setAttribute("AimsSpotlightTypes", AimsSpotlightTypes);


		String forward = "";
		int spotlightTypeId = StringFuncs.initializeIntGetParam(request.getParameter("spotLightTypeId"), 0);



		if (taskname.equalsIgnoreCase("createForm"))
		{	

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, 
                                                                AimsSecurityManager.INSERT))) 
            {
                throw new AimsSecurityException(); 
            } 
            
			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_CASE_STUDY))
			{
				forward = "createUpdateCaseStudy"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SALES_PRESENTATION))
			{
				forward = "createUpdateSalesPresentation"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SALES_COLLATERAL))
			{
				forward = "createUpdateSalesCollateral"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_TESTIMONY))
			{
				forward = "createUpdateTestimony"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_WHITE_PAPER))
			{
				forward = "createUpdateWhitePaper"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SUCCESS_STORY))
			{
				forward = "createUpdateSuccessStory"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_DEMO))
			{
				forward = "createUpdateDemo"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SALES_PARTNERS))
			{
				forward = "createUpdateSalesPartner"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_QRG))
			{
				forward = "createUpdateQRG"; 
			}
		 		 
		}

		if (taskname.equalsIgnoreCase("editForm"))
		{

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, 
                                                                AimsSecurityManager.UPDATE))) 
            {
                throw new AimsSecurityException(); 
            } 

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_CASE_STUDY))
			{
				forward = "createUpdateCaseStudy"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SALES_PRESENTATION))
			{
				forward = "createUpdateSalesPresentation"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SALES_COLLATERAL))
			{
				forward = "createUpdateSalesCollateral"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_TESTIMONY))
			{
				forward = "createUpdateTestimony"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_WHITE_PAPER))
			{
				forward = "createUpdateWhitePaper"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SUCCESS_STORY))
			{
				forward = "createUpdateSuccessStory"; 
			}
		 		 
			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_DEMO))
			{
				forward = "createUpdateDemo"; 
			}		
            
			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_SALES_PARTNERS))
			{
				forward = "createUpdateSalesPartner"; 
			}

			if (spotlightTypeId == Integer.parseInt(AimsConstants.SPOTLIGHT_QRG))
			{
				forward = "createUpdateQRG"; 
			}
		} 

		return mapping.findForward(forward);
    }
}
