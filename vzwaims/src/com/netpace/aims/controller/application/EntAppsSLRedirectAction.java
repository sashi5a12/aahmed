package com.netpace.aims.controller.application;

import org.apache.struts.action.*;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;
import com.netpace.aims.controller.BaseAction;

/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entAppsSLRedirect"
 *                name="EntApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do"
 *				  			validate="false"
 * @struts.action-forward name="page1" path="/application/entApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/entApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/entAppProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/entJournal.jsp"
 * @struts.action-forward name="page5" path="/application/entApplicationUpdate3.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="entView" path="/application/entApplicationView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @struts.action-forward name="pageEntBOBO" path="/application/entApplicationUpdateBOBO.jsp"
 * @struts.action-forward name="pageEntLBS" path="/application/entApplicationUpdateLBS.jsp"

 * @author Ahson Imtiaz
 */

public class EntAppsSLRedirectAction extends BaseAction
{

    static Logger log = Logger.getLogger(EntAppsSLRedirectAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        log.debug("In Action Class");
        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String forward = "view";
        String taskname = "edit";
        String pageToView = "";
        Object o_param;

        pageToView = request.getParameter("pageToView");
        if (pageToView == null)
            pageToView = "page1";

        AimsApp aimsApp = null;
        AimsEnterpriseApp aimsEntApp = null;
        AimsAppCategory aimsAppCategory = null;
        HashMap appEnt = null;

        //Get Application Information

        try
        {
            appEnt = AimsEntAppsManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
        }
        catch (AimsException ae)
        {
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            return mapping.getInputForward();
        }
        aimsApp = (AimsApp) appEnt.get("AimsApp");
        aimsEntApp = (AimsEnterpriseApp) appEnt.get("AimsEntApp");
        aimsAppCategory = (AimsAppCategory) appEnt.get("AimsAppCategory");

        //Get Form
        EntApplicationUpdateForm entAppUpdForm = (EntApplicationUpdateForm) form;

        //Common Setup Related Tasks
        ApplicationHelper.setupAction(request, taskname, entAppUpdForm, aimsApp, aimsAppCategory, AimsConstants.ENTERPRISE_PLATFORM_ID, dateFormat);

        entAppUpdForm.setSetupURL(
            this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.enterprise.app.setup.url"));
        entAppUpdForm.setUpdateURL(
            this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.enterprise.app.update.url"));

        entAppUpdForm.setAllCaseStudies(new java.util.HashSet());
        session.setAttribute("AIMS_APP_CASE_STUDIES", entAppUpdForm.getAllCaseStudies());

        entAppUpdForm.setAllianceSponsor(aimsEntApp.getAllianceSponsor());
        entAppUpdForm.setCustSupportPhone(aimsEntApp.getCustSupportPhone());
        entAppUpdForm.setNoOfUsersAccess(Utility.convertToString(aimsEntApp.getNumWirelessUsers()));
        entAppUpdForm.setTotalEndUsers(Utility.convertToString(aimsEntApp.getNumAllUsers()));
        entAppUpdForm.setPlatformDepMode(aimsEntApp.getPlatformDepMode());
        entAppUpdForm.setCustSupportEmail(aimsEntApp.getCustSupportEmail());
        entAppUpdForm.setCustSupportHours(aimsEntApp.getCustSupportHours());
        entAppUpdForm.setFortuneCustomers(aimsEntApp.getFortuneCustomers());
        entAppUpdForm.setOtherIndFocusValue(aimsEntApp.getOtherIndFocusValue());

        entAppUpdForm.setPresentationFileName(aimsEntApp.getPresentationFileName());
        
        //Setting ne variable added for JMA application
        entAppUpdForm.setBoboLetterOfAuthFileName(aimsEntApp.getBoboLetterOfAuthFileName());       
        entAppUpdForm.setDevices(aimsEntApp.getDevices());
        entAppUpdForm.setProductExclusiveToVzw(aimsEntApp.getProductExclusiveToVzw() );
        entAppUpdForm.setProductInformation(aimsEntApp.getProductInformation());
        entAppUpdForm.setBriefDescription(aimsEntApp.getBriefDescription());
        entAppUpdForm.setAdditionalInformation(aimsEntApp.getAdditionalInformation());
        entAppUpdForm.setBoboLetterOfAuthFileName(aimsEntApp.getBoboLetterOfAuthFileName());
        entAppUpdForm.setLbsContractFileName(aimsEntApp.getLbsContractFileName());
        //entAppUpdForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());                     
        entAppUpdForm.setIsGoExclusiveWithVZW(aimsEntApp.getIsGoExclusiveWithVZW());
        entAppUpdForm.setIsProductExeclusiveToVZW(aimsEntApp.getIsProductExeclusiveToVZW());
        entAppUpdForm.setIsProductUseVzwVzNt(aimsEntApp.getIsProductUseVzwVzNt());       
       	entAppUpdForm.setIsProductCertifiedVZW(aimsEntApp.getIsProductCertifiedVZW());      
        entAppUpdForm.setIsProductCertifiedODIProcess(aimsEntApp.getIsProductCertifiedODIProcess());       
        entAppUpdForm.setIsProductCertifiedODIProcess(aimsEntApp.getIsProductCertifiedODIProcess());
        entAppUpdForm.setIsProductRequiedLBS( aimsEntApp.getIsProductRequiedLBS());      
       	entAppUpdForm.setIsOfferBoboServices(aimsEntApp.getIsOfferBoboServices());
       	entAppUpdForm.setIsInterestedInLBS(aimsEntApp.getIsInterestedInLBS());
       	entAppUpdForm.setIsInterestedInBOBO(aimsEntApp.getIsInterestedInBOBO());
        entAppUpdForm.setIsBoboAccept(aimsEntApp.getIsBoboAccept());  
       	entAppUpdForm.setIsLbsAccept(aimsEntApp.getIsLbsAccept());
       	entAppUpdForm.setIsPublished(aimsEntApp.getIsPublished());
        entAppUpdForm.setIsFeatured(aimsEntApp.getIsFeatured());
       	entAppUpdForm.setIsLbsAcceptByAlliance(aimsEntApp.getIsLbsAcceptByAlliance());
       	//Product Information
        entAppUpdForm.setEntProductInfo(AimsEntAppsManager.getProductInformation(aimsEntApp.getEnterpriseAppsId()));
        //Market segment
        entAppUpdForm.setEntMarketSegInfo(AimsEntAppsManager.getMarketSegmentInfo(aimsEntApp.getEnterpriseAppsId()));
        
        if(JMAApplicationHelper.displayTabBOBO(currUserType, aimsApp, aimsEntApp)){
        	entAppUpdForm.setDisplayTabBOBO("Y");
        }
        else{
        	entAppUpdForm.setDisplayTabBOBO("N");
        }
        
        if(JMAApplicationHelper.displayTabLBS(currUserType, aimsEntApp)){
        	entAppUpdForm.setDisplayTabLBS("Y");
        }
        else{
        	entAppUpdForm.setDisplayTabLBS("N");
        }
        entAppUpdForm.setPresentationTempFileId(new Long(0));
        entAppUpdForm.setBoboLetterOfAuthTempFileId(new Long(0));
        entAppUpdForm.setLbsContractTempFileId(new Long(0));
        
      //Setting the Application Status for Page Header
        if ((aimsApp.getAppsId() != null) && (aimsApp.getAppsId().longValue() != 0)) {
            AimsLifecyclePhase aimsPhaseOfApplication =
                (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, aimsApp.getAimsLifecyclePhaseId().toString());
            entAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
        }
        else {
        	entAppUpdForm.setApplicationStatus("NEW");
        }
       
        

        //Set Temp File Ids to Zero
        entAppUpdForm.setPresentationTempFileId(new Long(0));

        Long[] lngVV = null;
        java.util.Set sTemp = null;

        //Solution Components
        lngVV = null;

        sTemp = aimsEntApp.getAimsEntAppsSolComp();
        if (sTemp != null)
        {
            lngVV = new Long[sTemp.size()];
            int iCount = 0;
            for (Iterator it = sTemp.iterator(); it.hasNext();)
            {
                lngVV[iCount++] = ((AimsEntAppSolComp) it.next()).getSolutionComponentId();
            }
        }

        entAppUpdForm.setSolutionComponentId(lngVV);
        session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP_ID_ONLY, lngVV);

        //Solution Components with Description
        Collection allSolutionComponents = AimsEntAppsManager.getAimsSolutionComponents();
        Set solutionComponentSet = aimsEntApp.getAimsEntAppsSolComp();

        if (allSolutionComponents != null)
        {

            String[] solCompName = new String[allSolutionComponents.size()];
            Long[] solCompId = new Long[allSolutionComponents.size()];
            String[] solCompDesc = new String[allSolutionComponents.size()];

            int index = 0;
            for (java.util.Iterator itr = allSolutionComponents.iterator(); itr.hasNext();)
            {
                AimsSolutionComponent asc = (AimsSolutionComponent) itr.next();
                solCompName[index] = asc.getSolutionComponentName();
                solCompId[index] = asc.getSolutionComponentId();
                if ((solutionComponentSet != null) && (solutionComponentSet.size() > 0))
                {
                    for (java.util.Iterator itre = solutionComponentSet.iterator(); itre.hasNext();)
                    {
                        AimsEntAppSolComp aesc = (AimsEntAppSolComp) itre.next();
                        if (aesc.getAimsSolutionComponent().equals(asc))
                            solCompDesc[index] = aesc.getSolutionDescription();
                    }
                }
                index++;
            }
            entAppUpdForm.setSolCompNameWhole(solCompName);
            entAppUpdForm.setSolCompIdWhole(solCompId);
            entAppUpdForm.setSolCompDescWhole(solCompDesc);
        }
        session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP, aimsEntApp.getAimsEntAppsSolComp());

        //Ent App Sub Categories
        lngVV = null;

        sTemp = aimsEntApp.getAimsEntAppsSubCategories();
        if (sTemp != null)
        {
            lngVV = new Long[sTemp.size()];
            int iCount = 0;
            for (Iterator it = sTemp.iterator(); it.hasNext();)
            {
                lngVV[iCount++] = ((AimsAppSubCategory) it.next()).getSubCategoryId();
            }
        }

        entAppUpdForm.setEntAppSubCategoryId(lngVV);
        session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_ENT_APP_SUB_CAT, lngVV);

        //Regions
        lngVV = null;

        sTemp = aimsEntApp.getAimsEntAppsRegion();
        if (sTemp != null)
        {
            lngVV = new Long[sTemp.size()];
            int iCount = 0;
            for (Iterator it = sTemp.iterator(); it.hasNext();)
            {
                lngVV[iCount++] = ((AimsRegion) it.next()).getRegionId();
            }
        }

        entAppUpdForm.setRegionId(lngVV);
        session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_REGIONS_LIST, lngVV);

        //Industry Focuses
        lngVV = null;

        sTemp = aimsEntApp.getAimsEntAppsIndFocus();
        if (sTemp != null)
        {
            lngVV = new Long[sTemp.size()];
            int iCount = 0;
            for (Iterator it = sTemp.iterator(); it.hasNext();)
            {
                lngVV[iCount++] = ((AimsIndustryFocu) it.next()).getIndustryId();
            }
        }

        entAppUpdForm.setIndustryFocusId(lngVV);
        session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_IND_FOCUS_LIST, lngVV);

        //Case Studies					
        entAppUpdForm.setAllCaseStudies(AimsEntAppsManager.getCaseStudies(aimsApp.getAppsId()));
        session.setAttribute("AIMS_APP_CASE_STUDIES", entAppUpdForm.getAllCaseStudies());

        forward = pageToView;

        log.debug("Forward String Before Forwarding: " + forward);
        return mapping.findForward(forward);
    }
}
