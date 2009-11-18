package com.netpace.aims.controller.application;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.system.DisclaimerManager;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsBrewApp;
import com.netpace.aims.model.application.AimsBrewAppClob;
import com.netpace.aims.model.application.AimsBrewMessagingDetails;
import com.netpace.aims.model.application.AimsLbsAutodeskPhase;
import com.netpace.aims.model.application.AimsLbsGeoService;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.model.system.AimsDisclaimers;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/brewApplicationSetup"
 *                name="BrewApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do"
 *                          validate="false"
 * @struts.action-forward name="update" path="/application/brewApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="brewView" path="/application/brewApplicationView.jsp"
 * @struts.action-forward name="processInfoView" path="/application/appProcessInfoView.jsp"
 * @struts.action-forward name="evaluationInfoView" path="/application/brewEvaluationView.jsp"
 * @struts.action-forward name="userGuideView" path="/application/brewUserGuideView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Adnan Makda
 */
public class BrewApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(BrewApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws AimsSecurityException, Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();               
        String forward = "view";
        String taskname = "";
        String viewPageToView = "";
        String isConcept = "N";
        Object o_param;

        o_param = request.getParameter("task");

        log.debug(o_param);
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        o_param = request.getParameter("viewPageToView");
        if (o_param != null)
            viewPageToView = request.getParameter("viewPageToView");

        o_param = request.getParameter("isConcept");
        if (o_param != null)
        {
            isConcept = request.getParameter("isConcept");
            //The following line has been added to restrict the creating of concepts. 01/10/2005
            throw new AimsSecurityException();
        }

        if (taskname.equalsIgnoreCase("onHold"))
        {
            try
            {
                AimsApplicationsManager.updateOnHold(new Long(request.getParameter("appsId")), request.getParameter("onHold"), currUser);
            }
            catch (Exception ex)
            {
                //Log or display error
                //saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            }
            return mapping.getInputForward();
        }

        AimsApp aimsApp = null;
        AimsBrewApp aimsBrewApp = null;
        AimsAppCategory aimsAppCategory = null;
        HashMap appBrew = null;
        AimsBrewAppClob brewClobs = null;
        AimsBrewMessagingDetails messagingDetail=null;

        if (session.getAttribute(AimsConstants.VALIDATION_DATE_FOR_ZON_AUTOMATION) == null){
            CommonProperties props=CommonProperties.getInstance();
            String dateStr=props.getProperty("brew.validationDate.forZonAutomationBeforeLive");
            Date validationDateForZonAutomation=Utility.convertToDate(dateStr, dateFormat);
            session.setAttribute(AimsConstants.VALIDATION_DATE_FOR_ZON_AUTOMATION, validationDateForZonAutomation);        	
        }
        
        //Get Application Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                appBrew = BrewApplicationManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
                brewClobs = BrewApplicationManager.getBrewClob(new Long(request.getParameter("appsId")));
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsApp = (AimsApp) appBrew.get("AimsApp");

            aimsBrewApp = (AimsBrewApp) appBrew.get("AimsBrewApp");
            aimsAppCategory = (AimsAppCategory) appBrew.get("AimsAppCategory");
            messagingDetail = (AimsBrewMessagingDetails) appBrew.get("AimsBrewMessaging");
        }

        //Get Form
        BrewApplicationUpdateForm brewAppUpdForm = (BrewApplicationUpdateForm) form;

        //log.debug("\n\n\n This is the provision --> " + aimsBrewApp.getProvision());
        //log.debug("\n\n\n This is the provExpirtyDate --> " + aimsBrewApp.getProvExpiryDate());

        //Common Setup Related Tasks
        ApplicationHelper.setupAction(request, taskname, brewAppUpdForm, aimsApp, aimsAppCategory, AimsConstants.BREW_PLATFORM_ID, dateFormat);

        brewAppUpdForm.setSetupURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.brew.app.setup.url"));
        brewAppUpdForm.setUpdateURL(this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("ManageApplicationForm.brew.app.update.url"));

        brewAppUpdForm.setProgGuideTempFileId(new Long(0));
        brewAppUpdForm.setAppTitleNameTempFileId(new Long(0));
        brewAppUpdForm.setCompanyLogoTempFileId(new Long(0));
        brewAppUpdForm.setTitleShotTempFileId(new Long(0));

        brewAppUpdForm.setHighResActiveTempFileId(new Long(0));
        brewAppUpdForm.setHighResSplashTempFileId(new Long(0));
        brewAppUpdForm.setSplashScreenTempFileId(new Long(0));
        brewAppUpdForm.setActiveScreen1TempFileId(new Long(0));
        brewAppUpdForm.setActiveScreen2TempFileId(new Long(0));
        brewAppUpdForm.setSmallSplashTempFileId(new Long(0));    
        brewAppUpdForm.setSmlActiveScreenTempFileId(new Long(0));
        brewAppUpdForm.setMediaStoreTempFileId(new Long(0));
        brewAppUpdForm.setFlashDemoMovieTempFileId(new Long(0));
        brewAppUpdForm.setDashboardScrImgTempFileId(new Long(0));
        brewAppUpdForm.setAppGifActionTempFileId(new Long(0));
        brewAppUpdForm.setAppActiionFlashTempFileId(new Long(0));

        if ( taskname.equalsIgnoreCase("create") || taskname.equalsIgnoreCase("edit") || taskname.equalsIgnoreCase("clone") || taskname.equalsIgnoreCase("view")){       
        	List  list=DisclaimerManager.getUserGuideAndAirTime();
        	AimsDisclaimers dis=(AimsDisclaimers)list.get(0);
        	brewAppUpdForm.setDisclaimerText(dis.getDisclaimerText().getSubString(1, (int)dis.getDisclaimerText().length()));
        	dis=(AimsDisclaimers)list.get(1);
        	request.setAttribute("AirTimeText", dis.getDisclaimerText().getSubString(1, (int)dis.getDisclaimerText().length()));
        }
        
        //Start of Delete related Task
        if (taskname.equalsIgnoreCase("delete"))
        {
            try
            {
                AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            }
            return mapping.getInputForward();
        }
        //End of Delete related Task

        if (taskname.equalsIgnoreCase("create"))
        {
            brewAppUpdForm.setLanguage("EN");
            session.removeAttribute(ManageApplicationsConstants.SESSION_SELECTED_DEVICES_LIST);
            brewAppUpdForm.setListSelectedDevices(null);
            brewAppUpdForm.setIsConcept(isConcept);
            if (isConcept.equals("Y"))
                brewAppUpdForm.setAppType(AimsConstants.BREW_APP_TYPE_CONCEPT[0]);
            else
                brewAppUpdForm.setAppType(AimsConstants.BREW_APP_TYPE_NEW[0]);

            brewAppUpdForm.setAppTypeFullName(MiscUtils.getAppType(brewAppUpdForm.getAppType()));
            forward = "update";
        }

        if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {
            String[] listSelectedDevice = null;

            brewAppUpdForm.setLanguage(aimsApp.getLanguage());

            //BREW Application Parameters
            brewAppUpdForm.setAppSize(aimsBrewApp.getAppSize());
            brewAppUpdForm.setDeveloperName(aimsBrewApp.getDeveloperName());
            brewAppUpdForm.setPublisherName(aimsBrewApp.getPublisherName());
            brewAppUpdForm.setSellingPoints(aimsBrewApp.getSellingPoints());
            brewAppUpdForm.setPlannedDevStartDate(Utility.convertToString(aimsBrewApp.getPlannedDevStartDate(), dateFormat));
            brewAppUpdForm.setPlannedEntryIntoNstl(Utility.convertToString(aimsBrewApp.getPlannedEntryIntoNstl(), dateFormat));
            brewAppUpdForm.setPlannedCompletionByNstl(Utility.convertToString(aimsBrewApp.getPlannedCompletionByNstl(), dateFormat));
            brewAppUpdForm.setPlannedEntryIntoVzw(Utility.convertToString(aimsBrewApp.getPlannedEntryIntoVzw(), dateFormat));
            brewAppUpdForm.setPlannedCompletionByVzw(Utility.convertToString(aimsBrewApp.getPlannedCompletionByVzw(), dateFormat));
            brewAppUpdForm.setPrizes(aimsBrewApp.getPrizes());
            brewAppUpdForm.setUgcChat(aimsBrewApp.getUgcChat());
            brewAppUpdForm.setChatPrize(aimsBrewApp.getChatPrize());
            brewAppUpdForm.setNetworkUse(aimsBrewApp.getNetworkUse());
            brewAppUpdForm.setSingleMultiPlayer(aimsBrewApp.getSingleMultiPlayer());
            brewAppUpdForm.setEvaluation(aimsBrewApp.getEvaluation());
            brewAppUpdForm.setDeckLaunchDate(Utility.convertToString(aimsBrewApp.getDeckLaunchDate(), dateFormat));
            brewAppUpdForm.setIsLbs(aimsBrewApp.getIsLbs());
            brewAppUpdForm.setLbsAppType(aimsBrewApp.getLbsAppType());

            //set contentRating
            brewAppUpdForm.setContentRating(Utility.ZeroValueReplacement(aimsBrewApp.getContentRating()));
            
            if (messagingDetail != null){
            	brewAppUpdForm.setShortCode(messagingDetail.getShortCode());
            	brewAppUpdForm.setKeyword(messagingDetail.getKeyword());
            	brewAppUpdForm.setAggregator(messagingDetail.getAggregator());
            }
            //set user guide
            brewAppUpdForm.setProductDescription(aimsBrewApp.getProductDescription());
            brewAppUpdForm.setMultiPlayer(aimsBrewApp.getMultiPlayer());
           
            brewAppUpdForm.setUsingApplication(brewClobs.getUsingApplicationStr());
            brewAppUpdForm.setTipsAndTricks(brewClobs.getTipsAndTricksStr());
            brewAppUpdForm.setFaq(brewClobs.getFaqStr());
            brewAppUpdForm.setTroubleshooting(brewClobs.getTroubleshootingStr());
            brewAppUpdForm.setDevCompanyDisclaimer(brewClobs.getDevelopmentCompanyDisclaimerStr());
            brewAppUpdForm.setAdditionalInformation(brewClobs.getAdditionalInformationStr());

            if (aimsBrewApp.getAnticipatedDaps() != null)
                brewAppUpdForm.setAnticipatedDaps(aimsBrewApp.getAnticipatedDaps());
            else
                brewAppUpdForm.setAnticipatedDaps(
                    this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage("BrewApplicationForm.default.anticipated.daps.comments"));

            if (aimsBrewApp.getDeckPlacement() != null)
                brewAppUpdForm.setDeckPlacement(aimsBrewApp.getDeckPlacement().toString());

            if (taskname.equalsIgnoreCase("clone"))
            {
                if ((aimsBrewApp.getAppType() != null)
                    && (aimsBrewApp.getAppType().equals(AimsConstants.BREW_APP_TYPE_CONCEPT[0]))
                    && aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.CONCEPT_ACCEPTED_ID.longValue())
                {
                    //Link this cloned application to it's concept.
                    brewAppUpdForm.setConceptId(aimsApp.getAppsId());
                    brewAppUpdForm.setConceptTitle(aimsApp.getTitle());
                    brewAppUpdForm.setConceptEvaluationDate(Utility.convertToString(aimsBrewApp.getEvaluationDate(), dateFormat));
                    brewAppUpdForm.setAppType(AimsConstants.BREW_APP_TYPE_NEW[0]);
                }
                else
                    //brewAppUpdForm.setAppType(AimsConstants.BREW_APP_TYPE_EXISTING[0]); //Default Value
                    //All Applications (Created) will be considered 'NEW' as per requirements from Verzion.
                    brewAppUpdForm.setAppType(AimsConstants.BREW_APP_TYPE_NEW[0]);
            }
            else
            {
                brewAppUpdForm.setAppType(aimsBrewApp.getAppType());
                brewAppUpdForm.setConceptId(aimsBrewApp.getConceptId());
                brewAppUpdForm.setConceptTitle(aimsBrewApp.getConceptTitle());
                brewAppUpdForm.setConceptEvaluationDate(Utility.convertToString(aimsBrewApp.getConceptEvaluationDate(), dateFormat));
                if (aimsBrewApp.getLbsClientId() != null)
                    brewAppUpdForm.setLbsClientId(aimsBrewApp.getLbsClientId().toString());
                brewAppUpdForm.setLbsSecretKey(aimsBrewApp.getLbsSecretKey());

                if (aimsBrewApp.getLbsAutodeskPhaseId() != null)
                {
                    AimsLbsAutodeskPhase aimsLbsAutodeskPhase =
                        (AimsLbsAutodeskPhase) DBHelper.getInstance().load(AimsLbsAutodeskPhase.class, aimsBrewApp.getLbsAutodeskPhaseId().toString());
                    brewAppUpdForm.setLbsAutodeskPhaseName(aimsLbsAutodeskPhase.getAutodeskPhaseName());
                    brewAppUpdForm.setLbsAutodeskPhaseId(aimsLbsAutodeskPhase.getAutodeskPhaseId());
                }
            }

            if ((brewAppUpdForm.getAppType() != null) && (brewAppUpdForm.getAppType().equals(AimsConstants.BREW_APP_TYPE_CONCEPT[0])))
                isConcept = "Y";
            brewAppUpdForm.setIsConcept(isConcept);

            //if ((taskname.equalsIgnoreCase("view")))
            brewAppUpdForm.setAppTypeFullName(MiscUtils.getAppType(brewAppUpdForm.getAppType()));

            if (aimsBrewApp.getDevices() != null)
            {
                AimsDevic aimsDevice = null;
                Vector deviceIds = new Vector();
                for (Iterator it = aimsBrewApp.getDevices().iterator(); it.hasNext();)
                {
                    aimsDevice = (AimsDevic) it.next();
                    if ((aimsDevice.getStatus() == null) || (aimsDevice.getStatus().equalsIgnoreCase("active")) || !(taskname.equalsIgnoreCase("clone")))
                        deviceIds.add(aimsDevice.getDeviceId().toString());
                }
                listSelectedDevice = new String[deviceIds.size()];
                listSelectedDevice = (String[]) deviceIds.toArray(listSelectedDevice);
            }

            brewAppUpdForm.setListSelectedDevices(listSelectedDevice);
            session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_DEVICES_LIST, listSelectedDevice);

            if (aimsBrewApp.getGeoServices() != null)
            {
                AimsLbsGeoService aimsLbsGeoService = null;
                Vector geoServiceIds = new Vector();
                for (Iterator it = aimsBrewApp.getGeoServices().iterator(); it.hasNext();)
                {
                    aimsLbsGeoService = (AimsLbsGeoService) it.next();
                    if ((aimsLbsGeoService.getStatus() == null)
                        || (aimsLbsGeoService.getStatus().equalsIgnoreCase("A"))
                        || !(taskname.equalsIgnoreCase("clone")))
                        geoServiceIds.add(aimsLbsGeoService.getGeoServiceId().toString());
                }
                brewAppUpdForm.setListSelectedGeoServices(StringFuncs.ConvertListToStringArray(geoServiceIds));
            }

            //Brew Product Information (Read Only)
            session.setAttribute(ManageApplicationsConstants.SESSION_VAR_PRODUCT_INFO, aimsBrewApp.getAppDevicesMap());

            brewAppUpdForm.setProgGuideFileName(aimsBrewApp.getProgGuideFileName());
            brewAppUpdForm.setAppTitleNameFileName(aimsBrewApp.getAppTitleNameFileName());

            brewAppUpdForm.setHighResActiveFileName(aimsBrewApp.getHighResActiveFileName());
            brewAppUpdForm.setHighResSplashFileName(aimsBrewApp.getHighResSplashFileName());
            brewAppUpdForm.setSplashScreenFileName(aimsBrewApp.getSplashScreenFileName());
            brewAppUpdForm.setActiveScreen1FileName(aimsBrewApp.getActiveScreen1FileName());
            brewAppUpdForm.setActiveScreen2FileName(aimsBrewApp.getActiveScreen2FileName());
            brewAppUpdForm.setSmallSplashFileName(aimsBrewApp.getSmallSplashFileName());    
            brewAppUpdForm.setSmlActiveScreenFileName(aimsBrewApp.getSmlActiveScreenFileName());
            brewAppUpdForm.setMediaStoreFileName(aimsBrewApp.getMediaStoreFileName());
            brewAppUpdForm.setFlashDemoMovieFileName(aimsBrewApp.getFlashDemoMovieFileName());
            brewAppUpdForm.setDashboardScrImgFileName(aimsBrewApp.getDashboardScrImgFileName());
            brewAppUpdForm.setAppGifActionFileName(aimsBrewApp.getAppGifActionFileName());
            brewAppUpdForm.setAppActiionFlashFileName(aimsBrewApp.getAppActiionFlashFileName());
            brewAppUpdForm.setUserGuidePdfFileName(aimsBrewApp.getUserGuideFileName());
            brewAppUpdForm.setCompanyLogoFileName(aimsBrewApp.getCompanyLogoFileName());
            brewAppUpdForm.setTitleShotFileName(aimsBrewApp.getTitleShotFileName());
            
            if (AimsConstants.VZW_USERTYPE.equals(currUserType) && "evaluationInfo".equalsIgnoreCase(viewPageToView)){
            	brewAppUpdForm.setHistory(WorkflowManager.getHistory(brewAppUpdForm.getAppsId()));
            }

            if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
            {
                forward = "update";
            }
            else if (taskname.equalsIgnoreCase("view"))
            {
                if (viewPageToView.equals(""))
                    forward = "brewView";
                else if (viewPageToView.equals("processingInfo"))
                {
                    brewAppUpdForm.setCurrentPage("processingInfo");
                    forward = "processInfoView";
                }
                else if (viewPageToView.equals("evaluationInfo"))
                {
                    brewAppUpdForm.setCurrentPage("evaluationInfo");
                    forward = "evaluationInfoView";
                }
                else if (viewPageToView.equals("userGuideView"))
                {
                	brewAppUpdForm.setCurrentPage("userGuideView");
                	forward = "userGuideView";
                }
            }

        }

        //Setting the Application Status for Page Header
        ApplicationsManagerHelper.setApplicationStatus(brewAppUpdForm);

        return mapping.findForward(forward);
    }

}
