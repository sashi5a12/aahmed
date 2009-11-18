package com.netpace.aims.controller.application;

import org.apache.struts.action.*;
import org.apache.log4j.Logger;

import javax.servlet.http.*;
import java.util.*;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.application.EntAppsSpotlightsManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.util.*;
import com.netpace.aims.controller.BaseAction;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/entAppsSpotlights"                
 *                scope="request" 
 *				  name="EntApplicationUpdateForm"
 *				  validate="false"
 *                input="/common/logged-in.jsp"
 * @struts.action-forward name="caseStudyView" path="/application/allianceSLCaseStudiesView.jsp" 
 * @author Ahson Imtiaz
 */
public class EntAppSpotlightsAction extends BaseAction
{

    static Logger log = Logger.getLogger(EntAppSpotlightsAction.class.getName());

    /**
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long enterpriseAppsId = null;
        String company_name = "";
        String showCreateButton = "Y";

        if (user_type.equalsIgnoreCase("A"))
        {
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }
            enterpriseAppsId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("enterpriseAppsId"), "0"));
        }

        if (user_type.equalsIgnoreCase("V"))
        {
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }
            enterpriseAppsId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("enterpriseAppsId"), "0"));
        }

        /* -- -- */
        log.debug("before check **************** " + AimsEntAppsManager.checkAccessToSpotlight(enterpriseAppsId.toString()));
        if (enterpriseAppsId == null || !AimsEntAppsManager.checkAccessToSpotlight(enterpriseAppsId.toString()))
        {
            throw new AimsSecurityException();
        }
        if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE) && !AimsEntAppsManager.checkAccessToSpotlight(currentUserAllianceId,enterpriseAppsId))
        {
        	throw new AimsSecurityException();
        }
        log.debug("After check **************** ");
        /* -- -- */

        String strAppName = EntAppsSpotlightsManager.getEnterpriseAppName(enterpriseAppsId);
        request.setAttribute("companyName", strAppName);

        Collection AimsSpotlightTypes = null;
        Collection AimsCaseStudies = null;
        Collection AimsSpotLights = null;
        Long spotlightTypeId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("spotLightTypeId"), AimsConstants.SPOTLIGHT_CASE_STUDY));
        Long spotlightId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"), "0"));

        int delCount = 0;

        String forward = "";

        AimsSpotlightTypes = EntAppsSpotlightsManager.getSpotlightTypes();
        request.setAttribute("AimsSpotlightTypes", AimsSpotlightTypes);

        if (taskname.equalsIgnoreCase("view"))
        {

            AimsSpotLights = EntAppsSpotlightsManager.getSpotLightRecords(enterpriseAppsId, user_type, spotlightTypeId);
            request.setAttribute("AimsSpotLights", AimsSpotLights);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLCaseStudiesView.jsp");
                    //forward = "caseStudyView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLCaseStudiesView.jsp");
                    //forward = "vzwCaseStudyView";
                }

            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLSalesPresentationView.jsp");
                    //forward = "salesPresentationView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLSalesPresentationView.jsp");
                    //forward = "vzwSalesPresentationView";
                }
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLSalesCollateralView.jsp");
                    //forward = "salesCollateralView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLSalesCollateralView.jsp");
                    //forward = "vzwSalesCollateralView";
                }
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLTestimonyView.jsp");
                    //forward = "testimonyView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLTestimonyView.jsp");
                    //forward = "vzwTestimonyView";
                }
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLWhitePaperView.jsp");
                    //forward = "whitePaperView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLWhitePaperView.jsp");
                    //forward = "vzwWhitePaperView";
                }
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLSuccessStoryView.jsp");
                    //forward = "successStoryView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLSuccessStoryView.jsp");
                    //forward = "vzwSuccessStoryView";
                }
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLDemoView.jsp");
                    //forward = "demoView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLDemoView.jsp");
                    //forward = "vzwDemoView";
                }
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
            {

                if (user_type.equalsIgnoreCase("A"))
                {
                    return new ActionForward("/application/allianceSLSalesPartnerView.jsp");
                    //forward = "demoView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLSalesPartnerView.jsp");
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
                    return new ActionForward("/application/allianceSLQRGView.jsp");
                    //forward = "demoView";             
                }

                if (user_type.equalsIgnoreCase("V"))
                {
                    return new ActionForward("/application/vzwAllianceSLQRGView.jsp");
                    //forward = "vzwDemoView";
                }
            }

        }

        if (taskname.equalsIgnoreCase("changeStatus") && user_type.equalsIgnoreCase("V"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE)))
            {
                throw new AimsSecurityException();
            }
            Long spotlight_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("spotlightId"), "0"));
            String spotlight_status = StringFuncs.NullValueReplacement(request.getParameter("vzwStatus"));
            EntAppsSpotlightsManager.changeSpotLightStatus(spotlight_id, spotlight_status, user_id);
            AimsSpotLights = EntAppsSpotlightsManager.getSpotLightRecords(enterpriseAppsId, user_type, spotlightTypeId);
            request.setAttribute("AimsSpotLights", AimsSpotLights);

            //Raise Event for Status Change
            SpotlightEventHelper.raiseDocumentStatusChangeEvent(enterpriseAppsId, spotlightTypeId, spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
            {
                return new ActionForward("/application/vzwAllianceSLCaseStudiesView.jsp");
                //forward = "vzwCaseStudyView";
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
            {
                return new ActionForward("/application/vzwAllianceSLSalesPresentationView.jsp");
                //forward = "vzwSalesPresentationView";
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
            {
                return new ActionForward("/application/vzwAllianceSLSalesCollateralView.jsp");
                //forward = "vzwSalesCollateralView";
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
            {
                return new ActionForward("/application/vzwAllianceSLTestimonyView.jsp");
                //forward = "vzwTestimonyView";			
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
            {
                return new ActionForward("/application/vzwAllianceSLWhitePaperView.jsp");
                //forward = "vzwWhitePaperView";		
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
            {
                return new ActionForward("/application/vzwAllianceSLSuccessStoryView.jsp");
                //forward = "vzwSuccessStoryView";		
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
            {
                return new ActionForward("/application/vzwAllianceSLDemoView.jsp");
                //forward = "vzwDemoView";		
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
            {
                return new ActionForward("/application/vzwAllianceSLSalesPartnerView.jsp");
                //forward = "vzwDemoView";		
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_QRG)))
            {
                return new ActionForward("/application/vzwAllianceSLQRGView.jsp");
                //forward = "vzwDemoView";		
            }
        }

        if (taskname.equalsIgnoreCase("delete") && user_type.equalsIgnoreCase("A"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SPOTLIGHT_PAGE, AimsSecurityManager.DELETE)))
            {
                throw new AimsSecurityException();
            }

            AimsEntAppsSpotlight aimsEntAppsSpotlight =
                (AimsEntAppsSpotlight) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsEntAppsSpotlight.class, spotlightId.toString());

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_QRG)))
                delCount = EntAppsSpotlightsManager.deleteSpotLight(spotlightId);

            //Raise Event on successful delete
            if (delCount > 0)
            {
                SpotlightEventHelper.raiseDocumentDeleteEvent(
                    aimsEntAppsSpotlight.getEnterpriseAppsId(),
                    aimsEntAppsSpotlight.getSpotLightTypeId(),
                    aimsEntAppsSpotlight.getSpotlightName(),
                    aimsEntAppsSpotlight.getSpotlightFileFileName(),
                    aimsEntAppsSpotlight.getStatus());
            }

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_CASE_STUDY)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_CASE_STUDY);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PRESENTATION)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SALES_PRESENTATION);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_COLLATERAL)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SALES_COLLATERAL);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_TESTIMONY)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_TESTIMONY);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_WHITE_PAPER)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_WHITE_PAPER);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SUCCESS_STORY)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SUCCESS_STORY);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_DEMO)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_DEMO);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_SALES_PARTNERS)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_SALES_PARTNERS);

            if (spotlightTypeId.equals(new Long(AimsConstants.SPOTLIGHT_QRG)))
                return new ActionForward("/entAppsSpotlights.do?task=view&spotLightTypeId=" + AimsConstants.SPOTLIGHT_QRG);

        }

        return mapping.findForward(forward);
    }
}
