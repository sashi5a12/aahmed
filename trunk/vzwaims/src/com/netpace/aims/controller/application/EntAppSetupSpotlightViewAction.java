package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsEntAppSpotlightManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;



/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entAppSetupSpotlight"
 *                scope="request"
 *                validate="false"
 * @struts.action-forward name="view" path="/application/entAppSpotlightView.jsp"

 * @author Arsalan Qureshi
 */
public class EntAppSetupSpotlightViewAction extends BaseAction {

	static Logger log= Logger.getLogger(EntAppSetupSpotlightViewAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		log.debug("EntAppSetupSpotlightViewAction.ActionForward : Start");

		if(!(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.JMA_APPLICATION_SPOTLIGHT_VIEW)))
			throw new AimsSecurityException();
			
        EntAppSpotLightBean spotLightInfo = null;
        Long solutionId = new Long(0);
        Long spotLightTypeId = new Long(1);

        if (request.getParameter("solutionId") != null)
            solutionId = new Long((String) request.getParameter("solutionId"));

          if (request.getParameter("spotLightTypeId") != null)
          {
              spotLightTypeId = new Long((String) request.getParameter("spotLightTypeId"));
              request.setAttribute("firstTimeHit", "N");
          }
          else
          {
              //Since 'SpotlightTypeId' is null, it means that the it is a first time hit to the spotlight page.
              request.setAttribute("firstTimeHit", "Y");
          }


        spotLightInfo = AimsEntAppSpotlightManager.getSpotlightInformation(solutionId, spotLightTypeId, true);

        request.setAttribute("spotLightInfo", spotLightInfo);

        request.setAttribute("allIndustryFocus", AimsEntAppSpotlightManager.getIndustryFocusList());
        request.setAttribute("allRegions", AimsEntAppSpotlightManager.getRegionsList());
        request.setAttribute("allMarketSegments", AimsEntAppSpotlightManager.getMarketSegmentsList());

        request.setAttribute("spotLightTypeId", spotLightTypeId);

		if (request.getParameter("marketSegmentId") != null)
			request.setAttribute("marketSegmentId", new Long((String) request.getParameter("marketSegmentId")));

    	
		log.debug("EntAppSetupSpotlightViewAction.ActionForward : End");
		  return mapping.findForward("view");
	}

	
	
}
