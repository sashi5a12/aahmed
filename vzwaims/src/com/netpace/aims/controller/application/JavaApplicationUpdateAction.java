package com.netpace.aims.controller.application;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.JavaApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.WorkitemVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsJavaAppClob;
import com.netpace.aims.model.application.AimsJavaApps;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.workflow.AimsModuleWorkflows;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;

/**
 *
 * @struts.action path="/javaApplicationUpdate"
 *                name="javaApplicationUpdateForm"
 *                scope="request"
 *                input="/application/javaApplicationUpdate.jsp" validate="true"
 * @struts.action-forward name="page1" path="/application/javaApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/javaApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/javaAppProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/javaJournal.jsp"
 * @struts.action-forward name="page5" path="/application/javaUserGuideUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="javaView" path="/application/javaApplicationView.jsp"
 * @struts.action-forward name="page4View" path="/application/javaJournalView.jsp"
 * @struts.action-forward name="worklist" path="/worklist.do"  
 * @author Waseem Akram
 */
public class JavaApplicationUpdateAction extends BaseAction {

    static Logger log = Logger.getLogger(JavaApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        AimsUser currAimsUser = (AimsUser)session.getAttribute(AimsConstants.AIMS_USER);
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        
        Long appUserAllianceId = null;
        // Get Form
        JavaApplicationUpdateForm javaForm = (JavaApplicationUpdateForm) form;
        if ( javaForm!=null && javaForm.getAimsAllianceId()!=null )
        	appUserAllianceId = javaForm.getAimsAllianceId();
        else
        	appUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        
        Long clonedFromAppId = null;
        boolean checkForEmptyFiles = false;
        Long oldStatus = null;
        

        boolean isVerizonUser = currAimsUser.getUserType().equals(AimsConstants.VZW_USERTYPE);
        boolean isAllianceUser = currAimsUser.getUserType().equals(AimsConstants.ALLIANCE_USERTYPE);
        
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;
        
        String forward = "view";
        String taskname = "";

        if ( StringUtils.isEmpty(javaForm.getAppSubmitType()) )
        {        	
        	return mapping.findForward(forward);
        }
        
        Object o_param = request.getParameter("task");
        if (o_param != null) {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        //CHECK Platform ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, javaForm.getOriginalTask(), AimsConstants.JAVA_PLATFORM_ID)))
            throw new AimsSecurityException();

        AimsApp aimsApp = null;
        AimsJavaApps javaApp = null;
        AimsContact aimsContact = new AimsContact();
        HashMap javaAppMap = null;
        AimsJavaAppClob javaClobs=new AimsJavaAppClob();

        

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")) )
        {
            if (taskname.equalsIgnoreCase("create")) {
                aimsApp = new AimsApp();
                javaApp = new AimsJavaApps();
            }
            else if (taskname.equalsIgnoreCase("edit") )
            {
                try
                {
                    javaAppMap = JavaApplicationManager.getJavaApp(javaForm.getAppsId());
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    javaForm.setCurrentPage("page1");
                    JavaApplicationHelper.prePopulateForm(javaForm);
                    return mapping.getInputForward();
                }
                aimsApp = (AimsApp) javaAppMap.get("AimsApp");
                javaApp = (AimsJavaApps) javaAppMap.get("AimsJavaApp");

                javaForm.setRingNumber(aimsApp.getRingTypeId());
            }
            oldStatus = aimsApp.getAimsLifecyclePhaseId();
            if ( aimsApp.getAimsAllianceId()!=null )
            	appUserAllianceId = aimsApp.getAimsAllianceId();            
        }        

        try {
            forward = JavaApplicationHelper.updateAction(request, taskname, javaForm, aimsApp, javaApp, javaClobs, aimsContact, dateFormat);
        }
        catch (  AimsException ae) {
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            javaForm.setCurrentPage("page1");
            JavaApplicationHelper.prePopulateForm(javaForm);
            return mapping.getInputForward();
        }
        
        
        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")))
        {
            //Phase Indicator


        	//Set Alliance Specific Information
        	if (isAllianceUser)
        	{
	            if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
	                aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);

	            else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
	            {
	            	//check alliance has accepted at least one active vcast apps contract
	                if(!ApplicationHelper.validateAllianceContract(aimsApp.getAimsAllianceId(), AimsConstants.JAVA_PLATFORM_ID, currUserType))
	                {
	                    AimsException aimsException = new AimsException("Error");
	                    aimsException.addException(new RecordNotFoundException("error.java.app.contract.acceptance"));
	                    saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
	                    return mapping.getInputForward();
	                }
	                aimsApp.setSubmittedDate(new Date());
	                aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
	            }

				//From Initial Approval -> Content In Review (After providing the content zip file)
				else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT)
						&& aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.longValue()
						)
				{
	                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);
				}				
        	}
        	
            // New file uploaded after content reject.
            if (oldStatus != null
            		&& oldStatus.longValue() == AimsConstants.PHASE_CONTENT_REJECTED.longValue()
            		)

            {
            	aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);

            	javaForm.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);

            	AimsLifecyclePhase aimsPhaseOfApplication = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, AimsConstants.PHASE_CONTENT_IN_REVIEW.toString());

				javaForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
            }

            //Check if the app is being cloned
            if ((javaForm.getCloneFromAppId().longValue() != 0) && (aimsApp.getAppsId() == null))
                clonedFromAppId = javaForm.getCloneFromAppId();

            //Check to see if userType is Alliance; thus allowing BO to check for empty Blobs
            if (isAllianceUser)
                checkForEmptyFiles = true;

            try
            {
            	AimsContract latestAcceptedContract = null;
            	if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM) )
            	{
            		latestAcceptedContract = JavaApplicationManager.getLatestAcceptedContract(appUserAllianceId, AimsConstants.JAVA_PLATFORM_ID);
            
            		javaApp.setAimsContractId(latestAcceptedContract.getContractId());
            	}
            	
            	JavaApplicationManager.saveOrUpdateJavaApplication(
                    aimsApp,
                    javaApp,
                    javaClobs,
                    aimsContact,
                    currAimsUser.getUsername(),
                    currAimsUser.getUserType(),
                    javaForm.getClrPubLogoTempFileId(),
                    javaForm.getAppTitleNameTempFileId(),
                    javaForm.getSplashScreenEpsTempFileId(),
                    javaForm.getActiveScreenEpsTempFileId(),
                    javaForm.getScreenJpegTempFileId(),
                    javaForm.getScreenJpeg2TempFileId(),
                    javaForm.getScreenJpeg3TempFileId(),
                    javaForm.getScreenJpeg4TempFileId(),
                    javaForm.getScreenJpeg5TempFileId(),
                    javaForm.getFaqDocTempFileId(),
                    javaForm.getCompanyLogoTempFileId(),
                    javaForm.getTitleImageTempFileId(),
                    checkForEmptyFiles,
                    clonedFromAppId
                    );


            	if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM) )
            		JavaApplicationManager.initiateWorkflow(currAimsUser.getUsername(), aimsApp, aimsApp.getRingTypeId());

            	javaForm.setAppsId(aimsApp.getAppsId());
            	
            	javaForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
        		ApplicationsManagerHelper.setApplicationStatus(javaForm);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                javaForm.setCurrentPage("page1");
                JavaApplicationHelper.prePopulateForm(javaForm);
                return mapping.getInputForward();
            }            

            javaForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());       	
        	
            //Post Update Tasks
            javaForm.setAppsId(aimsApp.getAppsId());
            javaForm.setTask("edit");
            javaForm.setOriginalTask("edit");

            //Set Temp File Ids to Zero

            javaForm.setClrPubLogoTempFileId(new Long(0));
            javaForm.setAppTitleNameTempFileId(new Long(0));
            javaForm.setSplashScreenEpsTempFileId(new Long(0));
            javaForm.setActiveScreenEpsTempFileId(new Long(0));
            javaForm.setScreenJpegTempFileId(new Long(0));
            javaForm.setScreenJpeg2TempFileId(new Long(0));
            javaForm.setScreenJpeg3TempFileId(new Long(0));
            javaForm.setScreenJpeg4TempFileId(new Long(0));
            javaForm.setScreenJpeg5TempFileId(new Long(0));
            javaForm.setFaqDocTempFileId(new Long(0));
            javaForm.setUserGuideTempFileId(new Long(0));
            javaForm.setCompanyLogoTempFileId(new Long(0));
            javaForm.setTitleImageTempFileId(new Long(0));
            javaForm.setIsNewContentZipFileUploaded("N");
            

            if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                message = new ActionMessage("message.manage.app.saved");
            else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                message = new ActionMessage("message.manage.app.submitted");
            else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                message = new ActionMessage("message.manage.app.saved");
            else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                message = new ActionMessage("message.manage.app.saved");
            else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
                message = new ActionMessage("message.manage.app.processed");
            else if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_JAVA_RESUBMIT))
                message = new ActionMessage("message.manage.app.resubmitted");
            
            messages.add(ActionMessages.GLOBAL_MESSAGE, message);

            saveMessages(request, messages);
            forward = javaForm.getCurrentPage();
        }        
        
        if (isAllianceUser)
    	{
        	if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_JAVA_RESUBMIT) )
        	{
        		this.processTasksForResubmit(aimsApp, javaApp,  currAimsUser, new Date(), javaForm.getComments());
        		
        		Long updatedPhaseId = AimsApplicationsManager.getPhaseIdAfterUpdate(javaForm.getAppsId());
        		
        		javaForm.setAimsLifecyclePhaseId(updatedPhaseId);

        		ApplicationsManagerHelper.setApplicationStatus(javaForm);
        	}
    	}	
        
        if (isVerizonUser)
    	{
    		if (javaForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_EXECUTE_ACTION))
    		{
    			this.processTasksForResubmit(javaForm, aimsApp, javaApp,  new Date(), javaForm.getActionComments(), javaForm.getSelectedAction(), javaForm.getWiWorkitemId() );
        		
    			Long updatedPhaseId = AimsApplicationsManager.getPhaseIdAfterUpdate(javaForm.getAppsId());
    			
    			javaForm.setAimsLifecyclePhaseId(updatedPhaseId);

        		ApplicationsManagerHelper.setApplicationStatus(javaForm);
        		
        		messages = new ActionMessages();
        		message = new ActionMessage("message.worklist.success.action");
        		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
        		saveMessages(request, messages);
        		
        		forward = "worklist";
    		}
    	}
        
        if(javaForm.getCurrentPage().equalsIgnoreCase("page4")) {
            //only vzw user can view workflow history
        	javaForm.setHistory( JavaApplicationHelper.getWorkflowHistory(javaForm.getAppsId(),currAimsUser.getUserType() ));
        }

        javaForm.setShowExecute(false);
        if(javaForm.getCurrentPage().equalsIgnoreCase("page3")) {
            //only vzw user can view workflow history
            if(AimsConstants.VZW_USERTYPE.equals(currAimsUser.getUserType())) 
            {
                List workItemsList = WorkflowManager.getCurrentWorkItemsForApplication(  javaForm.getAppsId(), AimsConstants.JAVA_PLATFORM_ID, currAimsUser, javaForm.getStepToValidate(), false);
                
                if ( !workItemsList.isEmpty() )
                {
	                WorkitemVO workitemVO = (WorkitemVO)workItemsList.get(0);
	                
	                	javaForm.setWiTitle(workitemVO.getWorkItem());
	                	javaForm.setWiStartDate(workitemVO.getStartDate());
	                	javaForm.setWiActions(workitemVO.getActions());
	                	javaForm.setWiWorkitemId(workitemVO.getWorkitemId());
	                
	                	javaForm.setShowExecute(true);
	                //workitemId
	                	/*
	                if(workItemsList!=null) {
	                	javaForm.setWorkItemsList(workItemsList);	
	                }
	                */
                }
                else
                	javaForm.setWiTitle(null);
            }
        }


        return mapping.findForward(forward);
    }

    private void processTasksForResubmit(JavaApplicationUpdateForm javaForm, AimsApp aimsApp, AimsJavaApps javaApp, Date currDate, String comments, Long actionId, Long workItemId) throws WorkflowException, HibernateException
    {
		Long appsId = aimsApp.getAppsId();

		Long workflowDetailId = null;
		if ( aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_2_ID) )
			workflowDetailId = AimsConstants.WORKFLOW_JAVA_ONDECK_APP ;
		else if ( aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_3_ID) )
			workflowDetailId = AimsConstants.WORKFLOW_JAVA_OFFDECK_APP;

		if (actionId > 0)
		{
			BasicWorkflow wf = new BasicWorkflow("User");
			AimsModuleWorkflows module = null;
			HashMap hs = new HashMap();
			long workflowId = 0;
			CommonProperties commonProps = CommonProperties.getInstance();

			try
			{
				//AimsConstants.WORKFLOW_JAVA_OFFDECK_APP
				module = WorkflowManager.getModuleWorkflowByRecordIdAndStatus(
								appsId,
								workflowDetailId,
								AimsConstants.WORKFLOW_STARTED);
				if (module != null)
				{
					//workflowId = module.getModuleWorkflowId().longValue();
					workflowId = module.getWorkflowId().getId().longValue();
					log.debug("......Workflow Id = "+workflowId);
					hs.put("workflowDetailId", String.valueOf(module.getWorkflowDetail().getWorkflowDetailId()));
					hs.put("recordId", String.valueOf(appsId));
					hs.put("aId", String.valueOf(actionId));
					hs.put("createdBy", "system");
					hs.put("createdDate", currDate);
					hs.put("modifiedBy", "system");
					hs.put("modifiedDate", currDate);
					hs.put("comments", comments);
					hs.put("workitemId", String.valueOf(workItemId) );					
			
					if ( log.isDebugEnabled() )
						log.debug("...... executing workflow action:" + actionId + ", appsId: " + appsId);
					wf.doAction(workflowId, actionId.intValue(), hs);
					if ( log.isDebugEnabled() )
						log.debug("...... execution of workflow action:" + actionId + ", appsId: " + appsId + " completed");
				}
				
				String rfiContStandActionStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.ondeck.workflow.rfiContStand.action")));
				javaForm.setLockContentRating(false);
				if ( actionId.toString().equalsIgnoreCase(rfiContStandActionStr) )
					javaForm.setLockContentRating(true);
			}
			catch (WorkflowException we) 
			{
				log.error("WorkflowException found in processTasksForResubmit()");
				we.printStackTrace();
				throw we;
			} 
			catch (HibernateException he) 
			{
				log.error("HibernateException found in processTasksForResubmit()");
				he.printStackTrace();
				throw he;
			}
			catch(Throwable t){
				log.error("Workflow ERROR", t);
				t.printStackTrace();
				//throw t;
			}
		}//end actionId

    }
    
    private void processTasksForResubmit(AimsApp aimsApp, AimsJavaApps javaApp, AimsUser currUser, Date currDate, String comments) throws WorkflowException, HibernateException
    {
		boolean executeWorkflowActionOnResubmit = false;
		String actionIdStr = null;
		String stepIdStr = null;
		int actionId = -1;

		CommonProperties commonProps = CommonProperties.getInstance();
		Long appsId = aimsApp.getAppsId();

		Long workflowDetailId = null;
		if ( aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_2_ID) )
			workflowDetailId = AimsConstants.WORKFLOW_JAVA_ONDECK_APP ;
		else if ( aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_3_ID) )
			workflowDetailId = AimsConstants.WORKFLOW_JAVA_OFFDECK_APP;

		if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_JAVA_RFI_CONTENT_PROG.longValue() ))
	    {
			//RFI CONTENT PROG --> ITIALLY APPROVED
			log.debug("...... moving application from RFI CONTENT PROG to INITIALLY APPROVED (Resubmit application). appsId: " + appsId);
			aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_SUBMITTED);
			// workflow tasks
			executeWorkflowActionOnResubmit = true;
			//actionIdStr = commonProps.getProperty("java.rfi.resubmit.contentProg.action");
			actionIdStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.rfi.resubmit.contentProg.action")));
			//stepIdStr = commonProps.getProperty("java.rfi.resubmit.contentProg.step");
			stepIdStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.rfi.resubmit.contentProg.step")));
			

			if ( log.isDebugEnabled() )
			{
				log.debug("starting workflow for java app once again");
				log.debug("From phase id = " + AimsConstants.PHASE_JAVA_RFI_CONTENT_PROG.toString());
			}			
			
	    }
	    else if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_JAVA_RFI_LEGAL_CONTENT.longValue() ))
	    {
	    	aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_CONTENT_APPROVED);
			// workflow tasks
			executeWorkflowActionOnResubmit = true;
			//actionIdStr = commonProps.getProperty("java.rfi.resubmit.legalContent.action");
			actionIdStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.rfi.resubmit.legalContent.action")));
			//stepIdStr = commonProps.getProperty("java.rfi.resubmit.legalContent.step");
			stepIdStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.rfi.resubmit.legalContent.step")));

			if ( log.isDebugEnabled() )
			{
				log.debug("starting workflow for java app once again");
				log.debug("From phase id = " + AimsConstants.PHASE_JAVA_RFI_LEGAL_CONTENT.toString());
			}

			aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_LEGAL_APPROVED);
	    }
	    else if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_JAVA_RFI_TAX_REVIEW.longValue() ))
	    {
	    	if ( aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_2_ID) )
	    		aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_LEGAL_APPROVED);
			else if ( aimsApp.getRingTypeId().equals(AimsConstants.CONTRACT_RING_3_ID) )
				aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_CONTENT_APPROVED);


			// workflow tasks
			executeWorkflowActionOnResubmit = true;
			//actionIdStr = commonProps.getProperty("java.rfi.resubmit.taxReview.action");
			actionIdStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.rfi.resubmit.taxReview.action")));
			//stepIdStr = commonProps.getProperty("java.rfi.resubmit.taxReview.step");
			stepIdStr = StringFuncs.trim(StringFuncs.NullValueReplacement(commonProps.getProperty("java.rfi.resubmit.taxReview.step")));

			if ( log.isDebugEnabled() )
			{
				log.debug("starting workflow for java app once again");
				log.debug("From phase id = " + AimsConstants.PHASE_JAVA_RFI_TAX_REVIEW.toString());
			}

			aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_LEGAL_APPROVED);
	    }

		if (executeWorkflowActionOnResubmit)
		{
			if (StringUtils.isNotEmpty(actionIdStr) && StringUtils.isNumeric(actionIdStr))
			{
				try
				{
					actionId = Integer.parseInt(actionIdStr);
					if (actionId > 0)
					{
						BasicWorkflow wf = new BasicWorkflow("User");
						AimsModuleWorkflows module = null;
						HashMap hs = new HashMap();
						WorkitemVO workItem = null;
						List workItemsList = null;
						long workflowId = 0;

						try
						{
							//AimsConstants.WORKFLOW_JAVA_OFFDECK_APP
							module = WorkflowManager.getModuleWorkflowByRecordIdAndStatus(
											appsId,
											workflowDetailId,
											AimsConstants.WORKFLOW_STARTED);
							if (module != null)
							{
								//workflowId = module.getModuleWorkflowId().longValue();
								workflowId = module.getWorkflowId().getId().longValue();
								log.debug("......Workflow Id = "+workflowId);
								hs.put("workflowDetailId", String.valueOf(module.getWorkflowDetail().getWorkflowDetailId()));
								hs.put("recordId", String.valueOf(appsId));
								hs.put("aId", String.valueOf(actionId));
								hs.put("createdBy", "system");
								hs.put("createdDate", currDate);
								hs.put("modifiedBy", "system");
								hs.put("modifiedDate", currDate);
								hs.put("comments", comments);
								
								//hs.put("workitemId",String.valueOf(vo.getWorkitemId()));
								workItemsList = WorkflowManager.getCurrentWorkItemsForApplication(appsId, AimsConstants.JAVA_PLATFORM_ID, currUser, new Long(stepIdStr), false);
								if(workItemsList!=null && workItemsList.size()>0) 
								{
									for(int workItemIdx = 0; workItemIdx<workItemsList.size(); workItemIdx++) 
									{
										workItem = (WorkitemVO)workItemsList.get(workItemIdx);//get last workitem
									}
									hs.put("workitemId", workItem.getWorkitemId().toString());
									if ( log.isDebugEnabled() )
										log.debug("workitemId = "+workItem.getWorkitemId());
								}
								
								if ( log.isDebugEnabled() )
									log.debug("...... executing workflow action:" + actionId + ", appsId: " + appsId);
								wf.doAction(workflowId, actionId, hs);
								if ( log.isDebugEnabled() )
									log.debug("...... execution of workflow action:" + actionId + ", appsId: " + appsId + " completed");
							}
						} 
						catch (WorkflowException we) 
						{
							log.error("WorkflowException found in processTasksForResubmit()");
							we.printStackTrace();
							throw we;
						} 
						catch (HibernateException he) 
						{
							log.error("HibernateException found in processTasksForResubmit()");
							he.printStackTrace();
							throw he;
						}
						catch(Throwable t){
							log.error("Workflow ERROR", t);
							t.printStackTrace();
							//throw t;
						}
					}//end actionId
					
					/**
					 * TODO : Waseem
					 * WORK AROUND TILL WE WRITE A METHOD TO GET ALL OPEN WORKITEM FOR APP_ID
					 */
					if(actionId == 451)
					{
						actionId = 551;
						stepIdStr = "550";
					}
					else if ( actionId == 551)
					{
						actionId = 451;
						stepIdStr = "450";
					}
					else						
						actionId = 0;
					
					if (actionId > 0 ) 
					{
						BasicWorkflow wf = new BasicWorkflow("User");
						AimsModuleWorkflows module = null;
						HashMap hs = new HashMap();
						WorkitemVO workItem = null;
						List workItemsList = null;
						long workflowId = 0;

						try 
						{
							//AimsConstants.WORKFLOW_JAVA_OFFDECK_APP
							module = WorkflowManager.getModuleWorkflowByRecordIdAndStatus(
											appsId,
											workflowDetailId,
											AimsConstants.WORKFLOW_STARTED);
							if (module != null) 
							{
								//workflowId = module.getModuleWorkflowId().longValue();
								workflowId = module.getWorkflowId().getId().longValue();
								log.debug("......Workflow Id = "+workflowId);
								hs.put("workflowDetailId", String.valueOf(module.getWorkflowDetail().getWorkflowDetailId()));
								hs.put("recordId", String.valueOf(appsId));
								hs.put("aId", String.valueOf(actionId));
								hs.put("createdBy", "system");
								hs.put("createdDate", currDate);
								hs.put("modifiedBy", "system");
								hs.put("modifiedDate", currDate);
								hs.put("comments", comments);
								
								//hs.put("workitemId",String.valueOf(vo.getWorkitemId()));
								workItemsList = WorkflowManager.getCurrentWorkItemsForApplication(appsId, AimsConstants.JAVA_PLATFORM_ID, currUser, new Long(stepIdStr), false);
								if(workItemsList!=null && workItemsList.size()>0) 
								{
									for(int workItemIdx = 0; workItemIdx<workItemsList.size(); workItemIdx++) 
									{
										workItem = (WorkitemVO)workItemsList.get(workItemIdx);//get last workitem
									}
									hs.put("workitemId", workItem.getWorkitemId().toString());
									if ( log.isDebugEnabled() )
										log.debug("workitemId = "+workItem.getWorkitemId());
								}
								
								if ( log.isDebugEnabled() )
									log.debug("...... executing workflow action:" + actionId + ", appsId: " + appsId);
								wf.doAction(workflowId, actionId, hs);
								if ( log.isDebugEnabled() )
									log.debug("...... execution of workflow action:" + actionId + ", appsId: " + appsId + " completed");
							}
						} 
						catch (WorkflowException we) 
						{
							log.error("WorkflowException found in processTasksForResubmit()");
							we.printStackTrace();
							throw we;
						} 
						catch (HibernateException he) 
						{
							log.error("HibernateException found in processTasksForResubmit()");
							he.printStackTrace();
							throw he;
						}
						catch(Throwable t){
							log.error("Workflow ERROR", t);
							t.printStackTrace();
							//throw t;
						}
					}//end actionId

					
				} 
				catch (NumberFormatException nfe) 
				{
					log.error("NumberFormatException while parsing contentProg action in processTasksForResubmit");
					nfe.printStackTrace();
				}
			}//end if not null action
		}//end executeWorkflow
		
	}//end processTasksForResubmit
	
}