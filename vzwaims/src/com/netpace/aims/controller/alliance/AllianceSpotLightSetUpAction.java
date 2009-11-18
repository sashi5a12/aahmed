package com.netpace.aims.controller.alliance;

import org.apache.struts.action.*;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.security.*;

import java.util.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class allows the user to proceed to the Add/Update Accounts functionality.
 *
 * @struts.action path="/allianceSpotLightSetup"  
 *                name="AllianceSpotLightForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="createUpdateCaseStudy" path="/alliance/caseStudyCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateSalesPresentation" path="/alliance/salesPresentationCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateSalesCollateral" path="/alliance/salesCollateralCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateTestimony" path="/alliance/testimonyCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateWhitePaper" path="/alliance/whitePaperCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateSuccessStory" path="/alliance/successStoryCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateDemo" path="/alliance/demoCreateUpdate.jsp" 
 * @struts.action-forward name="createUpdateSalesPartner" path="/alliance/salesPartnerCreateUpdate.jsp"
 * @struts.action-forward name="createUpdateQRG" path="/alliance/qrgCreateUpdate.jsp" 
 * @author Rizwan Qazi
 */
public class AllianceSpotLightSetUpAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceSpotLightSetUpAction.class.getName());
  

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
		Collection AimsSpotlightTypes = null;

        if (user_type.equalsIgnoreCase("V"))
        {		
            throw new AimsSecurityException(); 
        } 

		AimsSpotlightTypes = AllianceSpotlightsManager.getSpotlightTypes(alliance_id, user_type);	

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
