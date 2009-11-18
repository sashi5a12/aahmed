package com.netpace.aims.controller.application;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.GenericException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsBrewApp;
import com.netpace.aims.model.application.AimsBrewAppClob;
import com.netpace.aims.model.application.AimsBrewMessagingDetails;
import com.netpace.aims.model.application.AimsLbsAutodeskPhase;
import com.netpace.aims.model.application.AimsLbsGeoService;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsBrewAppsHistory;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsTypes;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/brewApplicationUpdate"
 *                name="BrewApplicationUpdateForm"
 *                scope="request"
 *                input="/application/brewApplicationUpdate.jsp"
 *				  			validate="true"
 * @struts.action-forward name="page1" path="/application/brewApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/brewApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/appProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/journal.jsp"
 * @struts.action-forward name="page5" path="/application/brewEvaluationUpdate.jsp"
 * @struts.action-forward name="page7" path="/application/brewUserGuideUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="brewView" path="/application/brewApplicationView.jsp"
 * @struts.action-forward name="journalUpdate" path="/application/journalUpdate.jsp"
 * @author Adnan Makda
 */
public class BrewApplicationUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(BrewApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        Long clonedFromAppId = null;
        boolean checkForEmptyFiles = false;
        Long oldStatus = null;
        Long newStatus = null;
        Long oldAutodeskStatus = null;
        Long newAutodeskStatus = null;
        String oldEvaluation = null;
        String oldDeckLaunchDate = null;
        String [] oldAppNetworkURLs = null;
        boolean initializeWorkflow=false;

        List brewAppHistoryList = new ArrayList();
        AimsBrewAppClob brewClobs=new AimsBrewAppClob();
        
        boolean isVerizonUser = currUserType.equals(AimsConstants.VZW_USERTYPE);
        boolean isAllianceUser = currUserType.equals(AimsConstants.ALLIANCE_USERTYPE);

        String forward = "view";
        String taskname = "";

        String xmlFolderPath = getServlet().getServletContext().getRealPath(AimsConstants.AUTODESK_XML_DIR);
        String hostName = request.getServerName();

        Object o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        AimsApp aimsApp = null;
        AimsBrewApp aimsBrewApp = null;
        AimsContact aimsContact = new AimsContact();
        HashMap appBrew = null;
        AimsBrewMessagingDetails messagingDetail=null;
        
        //Get Form
        BrewApplicationUpdateForm brewAppUpdForm = (BrewApplicationUpdateForm) form;        

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("lbs_submit_toprod")))
        {
            if (taskname.equalsIgnoreCase("create"))
            {
                aimsApp = new AimsApp();
                aimsBrewApp = new AimsBrewApp();
                messagingDetail = new AimsBrewMessagingDetails();
                messagingDetail.setCreatedBy(currUser);
                messagingDetail.setCreatedDate(new Date());
            }
            else if (
                (taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("lbs_submit_toprod")) || (taskname.equalsIgnoreCase("lbs_submit_deprov")))
            {
                try
                {
                    appBrew = BrewApplicationManager.getApp(brewAppUpdForm.getAppsId(), currentUserAllianceId);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    return mapping.getInputForward();
                }
                aimsApp = (AimsApp) appBrew.get("AimsApp");
                aimsBrewApp = (AimsBrewApp) appBrew.get("AimsBrewApp");
                if (appBrew.get("AimsBrewMessaging") == null){
                	messagingDetail = new AimsBrewMessagingDetails();
                    messagingDetail.setCreatedBy(currUser);
                    messagingDetail.setCreatedDate(new Date());
                }
                else{ 
                	messagingDetail = (AimsBrewMessagingDetails) appBrew.get("AimsBrewMessaging");
                }
            }
            oldStatus = aimsApp.getAimsLifecyclePhaseId();
            oldAutodeskStatus = aimsBrewApp.getLbsAutodeskPhaseId();
            oldEvaluation = aimsBrewApp.getEvaluation();
            oldDeckLaunchDate = Utility.convertToString(aimsBrewApp.getDeckLaunchDate(), dateFormat);

            //get old network urls,
            if(aimsApp.getAppsId()!=null)
            {                
                oldAppNetworkURLs = AimsApplicationsManager.getAppNetworkUrlValues( aimsApp.getAppsId() );
            }
        }

        Date validationDateForZonAutomation=(Date)session.getAttribute(AimsConstants.VALIDATION_DATE_FOR_ZON_AUTOMATION);
        
        //Only Verizon Users can move LBS Apps to Autodesk
        if ((isVerizonUser) && (AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0].equals(brewAppUpdForm.getIsLbs())))
        {
            if ((taskname.equalsIgnoreCase("lbs_submit_toprod")))
            {
                if (taskname.equalsIgnoreCase("lbs_submit_toprod"))
                    aimsBrewApp.setLbsAutodeskPhaseId(AimsConstants.LBS_STATUS_PRODUCTION);
                else if (taskname.equalsIgnoreCase("lbs_submit_deprov"))
                    aimsBrewApp.setLbsAutodeskPhaseId(AimsConstants.LBS_STATUS_DEPROVISIONED);

                aimsApp.setLastUpdatedBy(currUser);
                aimsApp.setLastUpdatedDate(new Date());
                BrewApplicationManager.updateBrewApplication(aimsApp, aimsBrewApp);
                AimsLbsAutodeskPhase aimsLbsAutodeskPhase =
                    (AimsLbsAutodeskPhase) DBHelper.getInstance().load(AimsLbsAutodeskPhase.class, aimsBrewApp.getLbsAutodeskPhaseId().toString());
                brewAppUpdForm.setLbsAutodeskPhaseName(aimsLbsAutodeskPhase.getAutodeskPhaseName());
                brewAppUpdForm.setLbsAutodeskPhaseId(aimsLbsAutodeskPhase.getAutodeskPhaseId());

                if ((taskname.equalsIgnoreCase("lbs_submit_toprod")) || (taskname.equalsIgnoreCase("lbs_submit_deprov")))
                {
                    if (taskname.equalsIgnoreCase("lbs_submit_toprod"))
                    {
                        //One Additional Step when Provisioning on PROD, and that is De-Provisioning on Stage
                        //Commenting it out on 09/07/2007 on request of Autodesk . No need to deprov on Stage when provisioning on PROD
                        /*
                        BrewApplicationHelper.sendDCRToAutodesk(
                            aimsApp,
                            aimsBrewApp,
                            AimsConstants.LBS_STATUS_DEPROVISIONED_ON_STAGE,
                            xmlFolderPath,
                            hostName,
                            false);
                        */
                    }
                    BrewApplicationHelper.sendDCRToAutodesk(aimsApp, aimsBrewApp, aimsBrewApp.getLbsAutodeskPhaseId(), xmlFolderPath, hostName, false);
                }
            }
        }

        //Common Update Related Tasks
        try
        {
            forward = ApplicationHelper.updateAction(request, taskname, brewAppUpdForm, aimsApp, aimsContact, AimsConstants.BREW_PLATFORM_ID, dateFormat);
        }
        catch (AimsException ae)
        {
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            return mapping.getInputForward();
        }

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")))
        {
            //Phase Indicator
        	//Set "save" status
            if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
            
            //Set submit status. 
            else if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            {
                //check alliance has accepted atleast one active brew contract
                if(!ApplicationHelper.validateAllianceContract(aimsApp.getAimsAllianceId(), AimsConstants.BREW_PLATFORM_ID, currUserType))
                {
                    AimsException aimsException = new AimsException("Error");
                    aimsException.addException(new RecordNotFoundException("error.brew.app.contract.acceptance"));
                    saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                    return mapping.getInputForward();
                }

                aimsApp.setSubmittedDate(new Date());
                aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
                if (AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0].equals(brewAppUpdForm.getIsLbs()))
                    aimsBrewApp.setLbsAutodeskPhaseId(AimsConstants.LBS_STATUS_NOT_SUBMITTED);
            }
            
            
            else if ((brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM)) && (isVerizonUser))
            {
            	//when the evaluation value is changing.
                if (!StringFuncs.NullValueReplacement(oldEvaluation).equals(StringFuncs.NullValueReplacement(brewAppUpdForm.getEvaluation())))
                {
                	//Set "Not Accepted" status if evaluation selected "Not Accepted" and application was in Submit, Testing, Evaluated.
                    if (((oldStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                        	|| (oldStatus.longValue() == AimsConstants.TESTING_ID.longValue())
                        	|| (oldStatus.longValue() == AimsConstants.EVALUATED_ID.longValue()))
                        && (brewAppUpdForm.getEvaluation().equals(AimsConstants.BREW_EVALUATION_NOT_ACCEPTED[0])))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.REJECTED_ID);
                    }
                    
                    //Set "Sunset" status if evaluation selected "Sunset" and application was in testing, accept
                    else if (((oldStatus.longValue() == AimsConstants.TESTING_ID.longValue()) || (oldStatus.longValue() == AimsConstants.ACCEPTANCE_ID.longValue()))
                            	&& (brewAppUpdForm.getEvaluation().equals(AimsConstants.BREW_EVALUATION_SUNSET[0])))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUNSET_ID);
                    }
                    
                    //Set evaluation if application was in sumbitted and user select "Accepted Featured" or "Accepted General"
                    else if ((oldStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                    			&& ((brewAppUpdForm.getEvaluation().equals(AimsConstants.BREW_EVALUATION_ACCEPTED_FEATURED[0]))
                    					|| (brewAppUpdForm.getEvaluation().equals(AimsConstants.BREW_EVALUATION_ACCEPTED_GENERAL[0]))))
                    {
                        
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.EVALUATED_ID);
                        if (aimsApp.getSubmittedDate().after(validationDateForZonAutomation)){
                        	initializeWorkflow=true;
                        }
                        
                        //Setting LBS Autodesk Phase ID to 'Staging'
                        if ((AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0].equals(brewAppUpdForm.getIsLbs()))
                            && (aimsBrewApp.getLbsAutodeskPhaseId() != null)
                            && (aimsBrewApp.getLbsAutodeskPhaseId().longValue() == AimsConstants.LBS_STATUS_NOT_SUBMITTED.longValue()))
                        {
                            aimsBrewApp.setLbsAutodeskPhaseId(AimsConstants.LBS_STATUS_STAGING);
                            aimsBrewApp.setLbsSecretKey(StringFuncs.asHex(MiscUtils.generate128bitkey()));
                        }
                    }
                    
                    //Set "Evaluated" if the application was in "Not Accepted" and user selected "Accepter Featured"/"Accepted General"
                    else if ((oldStatus.longValue() == AimsConstants.REJECTED_ID.longValue())
                    		&& ((brewAppUpdForm.getEvaluation().equals(AimsConstants.BREW_EVALUATION_ACCEPTED_FEATURED[0]))
                    				|| (brewAppUpdForm.getEvaluation().equals(AimsConstants.BREW_EVALUATION_ACCEPTED_GENERAL[0]))))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.EVALUATED_ID);
                        if (aimsApp.getSubmittedDate().after(validationDateForZonAutomation)){
                        	initializeWorkflow=true;
                        }
                    }

                }// End of evaluation check  
                
                if (aimsApp.getSubmittedDate().before(validationDateForZonAutomation)){
					if (!StringFuncs.NullValueReplacement(oldDeckLaunchDate).equals(StringFuncs.NullValueReplacement(brewAppUpdForm.getDeckLaunchDate())))
	                {
	                    if (oldStatus.longValue() == AimsConstants.EVALUATED_ID.longValue())
	                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.TESTING_ID);
	                }
                }
            }// End of isVerizon user check
            else if ((brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM)) && (isVerizonUser))
                aimsApp.setAimsLifecyclePhaseId(AimsConstants.ACCEPTANCE_ID);
            else if ((brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)) && (isVerizonUser))
                aimsApp.setAimsLifecyclePhaseId(AimsConstants.REJECTED_ID);
            
            //End of Phase Indicator

            //set content Rating
            if (!(brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))) {
                if(brewAppUpdForm.getContentRating() != null) {
                    Long oldContentRating = Utility.ZeroValueReplacement(aimsBrewApp.getContentRating());
                    Long oldStatusValue=Utility.ZeroValueReplacement(oldStatus);
                    //if old value of content rating is different from new value then make history
                    // or make history when application is moving from saved to submitted state, 
                    if((oldContentRating.longValue() != brewAppUpdForm.getContentRating().longValue())
                            || (oldStatusValue.longValue() == AimsConstants.SAVED_ID.longValue()  
                            		&& brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)
                            		&& brewAppUpdForm.getContentRating().longValue()!=0)) {
                        log.debug("Content Rating changed for Brew App, brewAppId: "+aimsBrewApp.getBrewAppsId());                        
                        AimsBrewAppsHistory brewAppHistory = new AimsBrewAppsHistory();
                        brewAppHistory.setColumnName(AimsConstants.COLUMN_BREW_CONTENT_RATING);
                        if (Utility.ZeroValueReplacement(brewAppUpdForm.getContentRating()).longValue()>0){
                        	AimsTypes aimsType = (AimsTypes)(DBHelper.getInstance().load(AimsTypes.class, brewAppUpdForm.getContentRating().toString()));
                        	brewAppHistory.setColumnValue(aimsType.getTypeValue());
                        }
                        else {
                        	brewAppHistory.setColumnValue("[No Rating Selected]");
                        }
                        brewAppHistory.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                        brewAppHistory.setCreatedDate(new Date());
                        brewAppHistoryList.add(brewAppHistory);
                    }
                }
            }//end submitType
                        
            messagingDetail.setShortCode(brewAppUpdForm.getShortCode().trim());
            messagingDetail.setKeyword(brewAppUpdForm.getKeyword().trim());
            messagingDetail.setAggregator(brewAppUpdForm.getAggregator());
            messagingDetail.setModifiedBy(currUser);
            messagingDetail.setModifiedDate(new Date());
            
            if(Utility.ZeroValueReplacement(brewAppUpdForm.getContentRating()).longValue()>0)
            {
                aimsBrewApp.setContentRating(brewAppUpdForm.getContentRating());
            }
            else
            {
                //if content rating is not selected (in saved status), set it to null
                aimsBrewApp.setContentRating(null);
            }

            //Start User Guide
            if(StringUtils.isNotEmpty(brewAppUpdForm.getProductDescription())){
            	aimsBrewApp.setProductDescription(brewAppUpdForm.getProductDescription().trim());	
            }
            
            aimsBrewApp.setMultiPlayer(brewAppUpdForm.getMultiPlayer());
            
            if (StringUtils.isNotEmpty(brewAppUpdForm.getUsingApplication())){
            	brewClobs.setUsingApplicationStr(brewAppUpdForm.getUsingApplication().trim());
            }
            else {
            	brewClobs.setUsingApplicationStr("");
            }
            if (StringUtils.isNotEmpty(brewAppUpdForm.getTipsAndTricks())){
            	brewClobs.setTipsAndTricksStr(brewAppUpdForm.getTipsAndTricks().trim());
            }
            else {
            	brewClobs.setTipsAndTricksStr("");
            }
            if(StringUtils.isNotEmpty(brewAppUpdForm.getFaq())){
            	brewClobs.setFaqStr(brewAppUpdForm.getFaq().trim());
            }
            else {
            	brewClobs.setFaqStr("");
            }
            if (StringUtils.isNotEmpty(brewAppUpdForm.getTroubleshooting())){
            	brewClobs.setTroubleshootingStr(brewAppUpdForm.getTroubleshooting().trim());
            }
            else {
            	brewClobs.setTroubleshootingStr(brewAppUpdForm.getTroubleshooting());
            }
            if (StringUtils.isNotEmpty(brewAppUpdForm.getDevCompanyDisclaimer())){
            	brewClobs.setDevelopmentCompanyDisclaimerStr(brewAppUpdForm.getDevCompanyDisclaimer().trim());
            }
            else {
            	brewClobs.setDevelopmentCompanyDisclaimerStr("");
            }
            if (StringUtils.isNotEmpty(brewAppUpdForm.getAdditionalInformation())){
            	brewClobs.setAdditionalInformationStr(brewAppUpdForm.getAdditionalInformation().trim());
            }
            else {
            	brewClobs.setAdditionalInformationStr("");
            }
                        
            //End User Guide
            aimsBrewApp.setLbsAppType(brewAppUpdForm.getLbsAppType());

            //'IsLBS' Flag and Geo Services can be updated by Verizon Users, OR Alliance Users only when they are Saving or Submitting the application
            if ((brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                || (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                || (isVerizonUser))
            {
                aimsBrewApp.setIsLbs(brewAppUpdForm.getIsLbs());
                if (brewAppUpdForm.getListSelectedGeoServices() != null)
                {
                    Set geoServiceSet = new HashSet();
                    for (int i = 0; i < brewAppUpdForm.getListSelectedGeoServices().length; i++)
                    {
                        geoServiceSet.add(
                            (AimsLbsGeoService) DBHelper.getInstance().load(AimsLbsGeoService.class, brewAppUpdForm.getListSelectedGeoServices()[i]));
                    }
                    //Latest Addition for LBS: Throw an error/exception if user has selected both 'Mobile Intiated' as well as 'Network Initiated' geoservices
                    if (BrewApplicationHelper.checkGeoServiceInitiation(geoServiceSet).longValue()
                        == BrewApplicationHelper.GEO_SERVICE_INITIATED_FROM_BOTH.longValue())
                    {
                        AimsException aimsException = new AimsException("Error");
                        aimsException.addException(new GenericException("error.brew.app.lbsGeoService.initiation"));
                        saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                        return mapping.getInputForward();
                    }
                    aimsBrewApp.setGeoServices(geoServiceSet);
                }
            }

            //The following properties can only be updated by an Alliance user and not the Verizon user
            if (isAllianceUser)
            {
                aimsApp.setLanguage(brewAppUpdForm.getLanguage());
                aimsBrewApp.setAppSize(brewAppUpdForm.getAppSize());
                aimsBrewApp.setAppType(brewAppUpdForm.getAppType());
                aimsBrewApp.setDeveloperName(brewAppUpdForm.getDeveloperName());
                aimsBrewApp.setPublisherName(brewAppUpdForm.getPublisherName());
                aimsBrewApp.setSellingPoints(brewAppUpdForm.getSellingPoints());
                aimsBrewApp.setPlannedDevStartDate(Utility.convertToDate(brewAppUpdForm.getPlannedDevStartDate(), dateFormat));
                aimsBrewApp.setPlannedEntryIntoNstl(Utility.convertToDate(brewAppUpdForm.getPlannedEntryIntoNstl(), dateFormat));
                aimsBrewApp.setPlannedCompletionByNstl(Utility.convertToDate(brewAppUpdForm.getPlannedCompletionByNstl(), dateFormat));
                aimsBrewApp.setPrizes(brewAppUpdForm.getPrizes());
                aimsBrewApp.setUgcChat(brewAppUpdForm.getUgcChat());
                aimsBrewApp.setChatPrize(brewAppUpdForm.getChatPrize());
                aimsBrewApp.setNetworkUse(brewAppUpdForm.getNetworkUse());
                aimsBrewApp.setSingleMultiPlayer(brewAppUpdForm.getSingleMultiPlayer());

                aimsBrewApp.setConceptId(brewAppUpdForm.getConceptId());
                aimsBrewApp.setConceptTitle(brewAppUpdForm.getConceptTitle());
                aimsBrewApp.setConceptEvaluationDate(Utility.convertToDate(brewAppUpdForm.getConceptEvaluationDate(), dateFormat));

                //Only Update Devices and Geo Services, if not null and If the Alliance User if Saving or Submitting the  application
                if ((brewAppUpdForm.getListSelectedDevices() != null)
                    && ((brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                        || (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))))
                {
                    //Devices
                    Set deviceSet = new HashSet();
                    for (int i = 0; i < brewAppUpdForm.getListSelectedDevices().length; i++)
                    {
                        deviceSet.add((AimsDevic) DBHelper.getInstance().load(AimsDevic.class, brewAppUpdForm.getListSelectedDevices()[i]));
                    }

                    aimsBrewApp.setDevices(deviceSet);
                }
            }

            java.util.Set cHS = null;

            //Set VZW Specific Information
            if (isVerizonUser)
            {

                boolean accessBrewInfo = ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_BREW_RELATED_INFO);
                boolean accessBrewEvalInfo = ApplicationHelper.checkAccess(request, taskname, ManageApplicationsConstants.PRIV_BREW_EVALUATION_INFO);

                if (accessBrewInfo || accessBrewEvalInfo)
                {
                    aimsBrewApp.setPlannedEntryIntoNstl(Utility.convertToDate(brewAppUpdForm.getPlannedEntryIntoNstl(), dateFormat));
                    aimsBrewApp.setPlannedCompletionByNstl(Utility.convertToDate(brewAppUpdForm.getPlannedCompletionByNstl(), dateFormat));
                    aimsBrewApp.setPlannedEntryIntoVzw(Utility.convertToDate(brewAppUpdForm.getPlannedEntryIntoVzw(), dateFormat));
                    aimsBrewApp.setPlannedCompletionByVzw(Utility.convertToDate(brewAppUpdForm.getPlannedCompletionByVzw(), dateFormat));
                    aimsBrewApp.setAnticipatedDaps(brewAppUpdForm.getAnticipatedDaps());
                    aimsBrewApp.setEvaluation(brewAppUpdForm.getEvaluation());
                    aimsBrewApp.setDeckLaunchDate(Utility.convertToDate(brewAppUpdForm.getDeckLaunchDate(), dateFormat));
                    aimsBrewApp.setDeckPlacement(StringFuncs.returnLongOrNull(brewAppUpdForm.getDeckPlacement()));

                    if (accessBrewEvalInfo)
                    {
                        aimsBrewApp.setDeveloperName(brewAppUpdForm.getDeveloperName());
                        aimsBrewApp.setPublisherName(brewAppUpdForm.getPublisherName());
                        aimsBrewApp.setSellingPoints(brewAppUpdForm.getSellingPoints());
                        aimsBrewApp.setPlannedDevStartDate(Utility.convertToDate(brewAppUpdForm.getPlannedDevStartDate(), dateFormat));
                        aimsBrewApp.setPrizes(brewAppUpdForm.getPrizes());
                        aimsBrewApp.setNetworkUse(brewAppUpdForm.getNetworkUse());
                        aimsBrewApp.setSingleMultiPlayer(brewAppUpdForm.getSingleMultiPlayer());
                    }                    

                }
                
            }

            //Check if the app is being cloned
            if ((brewAppUpdForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null))
                clonedFromAppId = brewAppUpdForm.getAppsId();

            //Check to see if userType is Alliance; thus allowing BO to check for empty Blobs
            if (isAllianceUser)
                checkForEmptyFiles = true;

            try
            {
                BrewApplicationManager.saveOrUpdateBrewApplication(
                    aimsApp,
                    aimsBrewApp,
                    aimsContact,
                    messagingDetail,
                    currUser,
                    currUserType,
                    brewAppUpdForm.getScreenJpegTempFileId(),
                    brewAppUpdForm.getScreenJpeg2TempFileId(),
                    brewAppUpdForm.getScreenJpeg3TempFileId(),
                    brewAppUpdForm.getScreenJpeg4TempFileId(),
                    brewAppUpdForm.getScreenJpeg5TempFileId(),
                    brewAppUpdForm.getFlashDemoTempFileId(),
                    brewAppUpdForm.getUserGuideTempFileId(),
                    brewAppUpdForm.getFaqDocTempFileId(),
                    brewAppUpdForm.getProgGuideTempFileId(),
                    brewAppUpdForm.getAppTitleNameTempFileId(),
                    brewAppUpdForm.getCompanyLogoTempFileId(),
                    brewAppUpdForm.getTitleShotTempFileId(),
                    brewAppUpdForm.getHighResSplashTempFileId(),
                    brewAppUpdForm.getHighResActiveTempFileId(),
                    brewAppUpdForm.getSplashScreenTempFileId(),
                    brewAppUpdForm.getSmallSplashTempFileId(),
                    brewAppUpdForm.getActiveScreen1TempFileId(),
                    brewAppUpdForm.getActiveScreen2TempFileId(),
                    brewAppUpdForm.getSmlActiveScreenTempFileId(),
                    brewAppUpdForm.getAppActiionFlashTempFileId(),
                    brewAppUpdForm.getAppGifActionTempFileId(),
                    brewAppUpdForm.getMediaStoreTempFileId(),
                    brewAppUpdForm.getFlashDemoMovieTempFileId(),
                    brewAppUpdForm.getDashboardScrImgTempFileId(),
                    checkForEmptyFiles,
                    cHS,
                    clonedFromAppId,                    
                    brewAppHistoryList,
                    brewClobs);
                if (initializeWorkflow){
                	log.debug("Initiating workflow for app: "+aimsApp.getTitle());
                	WorkflowManager.initiateWorkflow(aimsApp.getAppsId(), AimsConstants.WORKFLOW_BREW_APP, currUser, new Date());
                }

            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            //Post Update Tasks

            session.setAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME, "edit");
            brewAppUpdForm.setAppsId(aimsApp.getAppsId());
            brewAppUpdForm.setTask("edit");
            brewAppUpdForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
            brewAppUpdForm.setLbsSecretKey(aimsBrewApp.getLbsSecretKey());          

            if (aimsBrewApp.getLbsClientId() != null)
                brewAppUpdForm.setLbsClientId(aimsBrewApp.getLbsClientId().toString());

            if (aimsBrewApp.getLbsAutodeskPhaseId() != null)
            {
                AimsLbsAutodeskPhase aimsLbsAutodeskPhase =
                    (AimsLbsAutodeskPhase) DBHelper.getInstance().load(AimsLbsAutodeskPhase.class, aimsBrewApp.getLbsAutodeskPhaseId().toString());
                brewAppUpdForm.setLbsAutodeskPhaseName(aimsLbsAutodeskPhase.getAutodeskPhaseName());
                brewAppUpdForm.setLbsAutodeskPhaseId(aimsLbsAutodeskPhase.getAutodeskPhaseId());
            }

            newStatus = brewAppUpdForm.getAimsLifecyclePhaseId();
            newAutodeskStatus = brewAppUpdForm.getLbsAutodeskPhaseId();            
            
            System.out.println("oldAutodeskStatus: " + oldAutodeskStatus);
            System.out.println("newAutodeskStatus: " + newAutodeskStatus);

            if ((oldAutodeskStatus != null) && (oldAutodeskStatus.longValue() != newAutodeskStatus.longValue()))
            {
                if (newAutodeskStatus.longValue() == AimsConstants.LBS_STATUS_STAGING.longValue())
                {
                    System.out.println("From None to Staging");
                    BrewApplicationHelper.sendDCRToAutodesk(aimsApp, aimsBrewApp, AimsConstants.LBS_STATUS_STAGING, xmlFolderPath, hostName, false);
                }
                if (newAutodeskStatus.longValue() == AimsConstants.LBS_STATUS_DEPROVISIONED.longValue())
                {
                    System.out.println("From Prod to DeProvision");
                    //BrewApplicationHelper.sendDCRToAutodesk(aimsApp, aimsBrewApp, AimsConstants.LBS_STATUS_DEPROVISIONED, xmlFolderPath, hostName, false);
                }
            }

            if (((oldStatus != null) && (oldStatus.longValue() != newStatus.longValue()))
                || ((oldStatus == null) && (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())))
            {
                AimsEventLite aimsEvent = null;

                if (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                {
                    if ((aimsBrewApp.getIsLbs() != null) && (aimsBrewApp.getIsLbs().equals("Y")))
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_LBS_APP_STATUS_SUBMITTED);
                    else
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_APP_STATUS_SUBMITTED);
                }
                else if (newStatus.longValue() == AimsConstants.EVALUATED_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_APP_STATUS_EVALUATED);

                else if (newStatus.longValue() == AimsConstants.TESTING_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_APP_STATUS_UNDER_TESTING);

                else if (newStatus.longValue() == AimsConstants.ACCEPTANCE_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_APP_STATUS_ACCEPTED);

                else if (newStatus.longValue() == AimsConstants.REJECTED_ID.longValue())
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_APP_STATUS_REJECTED);
                else
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_BREW_APP_STATUS_CHANGE);

                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

                    AimsAllianc aimsAllianceForEvent =
                        (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

                    AimsUser aimAllianceAdminUser =
                        (AimsUser) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsUser.class,
                            aimsAllianceForEvent.getAimsUserByAdminUserId().toString());

                    AimsContact aimAllianceAdminContact =
                        (AimsContact) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsContact.class,
                            aimAllianceAdminUser.getAimsContactId().toString());

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());

                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_NEW,
                        ((AimsLifecyclePhase) DBHelper.getInstance().load(com.netpace.aims.model.application.AimsLifecyclePhase.class, newStatus.toString()))
                            .getPhaseName());

                    if ((aimsApp.getVersion() != null) && (!StringFuncs.isEmpty(aimsApp.getVersion())))
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, aimsApp.getVersion());

                    if (oldStatus != null)
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_OLD,
                            ((AimsLifecyclePhase) DBHelper
                                .getInstance()
                                .load(com.netpace.aims.model.application.AimsLifecyclePhase.class, oldStatus.toString()))
                                .getPhaseName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }
            }

            //check if status is not saved, and if app network urls changed, if yes then send notification
            if( (newStatus!=null) && (newStatus.longValue() != AimsConstants.SAVED_ID.longValue()) )
            {
                boolean urlsChangedInSavedState = false;
                String [] newAppNetworkURLs = brewAppUpdForm.getApplicationURLs();
                //check if status is changed from saved to another status,
                // and if application has network urls then set urlsChangedInSavedState flag to true
                if(oldStatus != null && oldStatus.longValue() == AimsConstants.SAVED_ID.longValue())
                {
                    if(newAppNetworkURLs!=null && newAppNetworkURLs.length>0)
                    {
                        urlsChangedInSavedState = true;
                    }
                }
                if( urlsChangedInSavedState ||
                        (ApplicationHelper.checkAppNetworkUrlsChanged(oldAppNetworkURLs, newAppNetworkURLs)) )
                {
                    log.debug("Application urls changed. appsId= "+aimsApp.getAppsId());
                    String appTitle = aimsApp.getTitle();
                    String allianceName = AllianceManager.getAllianceCompanyName(aimsApp.getAimsAllianceId());
                    allianceName = StringFuncs.NullValueReplacement(allianceName); //if no alliance found set to blank string

                    //send notification
                    AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_APPLICATION_URLS_CHANGED);
                    if (aimsEvent != null) {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, allianceName);
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, appTitle);
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()));
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }
            }//end send notification if urls changed

            //Set Temp File Ids to Zero
            brewAppUpdForm.setScreenJpegTempFileId(new Long(0));
            brewAppUpdForm.setScreenJpeg2TempFileId(new Long(0));
            brewAppUpdForm.setScreenJpeg3TempFileId(new Long(0));
            brewAppUpdForm.setScreenJpeg4TempFileId(new Long(0));
            brewAppUpdForm.setScreenJpeg5TempFileId(new Long(0));
            brewAppUpdForm.setFlashDemoTempFileId(new Long(0));
            brewAppUpdForm.setUserGuideTempFileId(new Long(0));
            brewAppUpdForm.setFaqDocTempFileId(new Long(0));
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
            
            if (brewAppUpdForm.getTestResultFileIdList() != null)
                for (int i = 0; i < brewAppUpdForm.getTestResultFileIdList().length; i++)
                    brewAppUpdForm.setTestResultFileId(i, new Long(0));

            ActionMessages messages = new ActionMessages();
            ActionMessage message = null;

            if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                message = new ActionMessage("message.manage.app.saved");
            else if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                message = new ActionMessage("message.manage.app.saved");
            else if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                message = new ActionMessage("message.manage.app.saved");
            else if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                message = new ActionMessage("message.manage.app.submitted");
            else if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
                message = new ActionMessage("message.manage.app.accepted");
            else if (brewAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
                message = new ActionMessage("message.manage.app.rejected");

            messages.add(ActionMessages.GLOBAL_MESSAGE, message);

            saveMessages(request, messages);
            forward = brewAppUpdForm.getCurrentPage();
        }

        if (AimsConstants.VZW_USERTYPE.equals(currUserType) && "page5".equalsIgnoreCase(forward)){
        	brewAppUpdForm.setHistory(WorkflowManager.getHistory(brewAppUpdForm.getAppsId()));
        }

        //Setting the Application Status for Page Header
        ApplicationsManagerHelper.setApplicationStatus(brewAppUpdForm);
        
        return mapping.findForward(forward);
    }
}
