package com.netpace.aims.controller.application;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import org.xml.sax.SAXException;

import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsDashboardAppClob;
import com.netpace.aims.model.application.AimsDashboardApps;
import com.netpace.aims.model.application.AimsDashboardChannelIds;
import com.netpace.aims.model.application.AimsDashboardEmailToAdobe;
import com.netpace.aims.model.application.AimsDashboardFtpsLog;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.FTPS;
import com.netpace.aims.util.GnuPG;
import com.netpace.aims.util.StringFuncs;

/**
 *
 * @struts.action path="/dashboardApplicationUpdate"
 *                name="DashboardApplicationUpdateForm"
 *                scope="request"
 *                input="/application/dashboardApplicationUpdate.jsp" validate="true"
 * @struts.action-forward name="page1" path="/application/dashboardApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/dashboardApplicationUpdate2.jsp"
 * @struts.action-forward name="page3" path="/application/dashboardAppProcessInfo.jsp"
 * @struts.action-forward name="page4" path="/application/dashboardJournal.jsp"
 * @struts.action-forward name="page5" path="/application/dashboardUserGuideUpdate.jsp" 
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="dashboardView" path="/application/dashboardApplicationView.jsp" 
 * @struts.action-forward name="page4View" path="/application/dashboardJournalView.jsp"
 * @author Adnan Ahmed
 */
public class DashboardApplicationUpdateAction extends BaseAction {

    static Logger log = Logger.getLogger(DashboardApplicationUpdateAction.class.getName());
    
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

        boolean isVerizonUser = currUserType.equals(AimsConstants.VZW_USERTYPE);
        boolean isAllianceUser = currUserType.equals(AimsConstants.ALLIANCE_USERTYPE);
        boolean sendMailToAdobe = false;
        boolean initialApprovalWithContentFile=false;

        String forward = "view";
        String taskname = "";

        Object o_param = request.getParameter("task");
        if (o_param != null) {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        AimsApp aimsApp = null;
        AimsDashboardApps dashboardApp = null;
        AimsDashboardChannelIds dashboardChannel=null;
        AimsContact aimsContact = new AimsContact();
        HashMap dashboardAppMap = null;
        AimsDashboardAppClob dashboardClobs=new AimsDashboardAppClob();

        //Get Form
        DashboardApplicationUpdateForm dashboardForm = (DashboardApplicationUpdateForm) form;

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("sendZipFile"))) {
            if (taskname.equalsIgnoreCase("create")) {
                aimsApp = new AimsApp();
                dashboardApp = new AimsDashboardApps();                
            }
            else if (taskname.equalsIgnoreCase("edit") || taskname.equalsIgnoreCase("sendZipFile")){
                try {
                    dashboardAppMap = DashboardApplicationManager.getApp(dashboardForm.getAppsId(), currentUserAllianceId);
                }
                catch (AimsException ae) {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    dashboardForm.setCurrentPage("page1");
                    DashboardApplicationHelper.prePopulateForm(dashboardForm);
                    return mapping.getInputForward();
                }
                aimsApp = (AimsApp) dashboardAppMap.get("AimsApp");
                dashboardApp = (AimsDashboardApps) dashboardAppMap.get("AimsDashboardApp");
                dashboardChannel = (AimsDashboardChannelIds) dashboardAppMap.get("AimsDashboardChannel");
            }
            oldStatus = aimsApp.getAimsLifecyclePhaseId();            

            if (dashboardChannel == null){
            	dashboardChannel=new AimsDashboardChannelIds();
            }
        }
        if(isVerizonUser){
        	if(taskname.equalsIgnoreCase("sendZipFile")){
    			log.debug("Send file to motricity. Start");
				
    			String xmlFolderPath = getServlet().getServletContext().getRealPath(AimsConstants.XML_DIR);
        		sendFilesToMotricity(request, aimsApp, dashboardApp, dashboardChannel, dashboardForm, xmlFolderPath);
    			log.debug("Send file to motricity. End");
        	}
				
        }
        try {
            forward = DashboardApplicationHelper.updateAction(request, taskname, dashboardForm, aimsApp, dashboardApp, dashboardClobs, dashboardChannel, aimsContact, dateFormat);
        }
        catch (AimsException ae) {
            saveErrors(request, DBErrorFinder.populateActionErrors(ae));
            dashboardForm.setCurrentPage("page1");
            DashboardApplicationHelper.prePopulateForm(dashboardForm);
            return mapping.getInputForward();
        }

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit"))) {
            //Phase Indicator

        	//Set Alliance Specific Information
        	if (isAllianceUser){
	            if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
	                aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
	            
	            else if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)) {
	                aimsApp.setSubmittedDate(new Date());
	                aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
	            }
	
				//From Initial Approval -> Content In Review (After providing the content zip file)
				else if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT)
						&& aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.longValue() 
						&& StringUtils.isNotEmpty(dashboardForm.getContentZipFileFileName())){
	                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);
				}
        	}
        	
            //Set VZW Specific Information
            if (isVerizonUser){
            	
            	//Start of process button 
            	if(AimsConstants.AIMS_VZW_PROCESS_FORM.equals(dashboardForm.getAppSubmitType())){
        			
        			//From Submit -> Initial Approval/Denied 
        			if (aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.SUBMISSION_ID.longValue() 
        					&& StringUtils.isNotEmpty(dashboardApp.getInitialApprovalAction())){
                        if (dashboardApp.getInitialApprovalAction().equals(AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0])){
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL);
                            sendMailToAdobe=DashboardApplicationManager.sendEmailToAdobe(dashboardForm.getAimsAllianceId());                                
                        }
                        else if (dashboardApp.getInitialApprovalAction().equals(AimsConstants.DASHBOARD_APP_RADIO_DENY[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_INITIAL_REJECTED);

                        //Content ZIP file is already defined.                            
                        if (dashboardApp.getInitialApprovalAction().equals(AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0])){
                            if (StringUtils.isNotEmpty(dashboardForm.getContentZipFileFileName())){
                                aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);
                                initialApprovalWithContentFile=true;
                			}
                        }
        			}

        			//From Initial Approval -> Content In Review (After providing the content zip file)
    				else if (aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.longValue() 
    						&& StringUtils.isNotEmpty(dashboardForm.getContentZipFileFileName())){
    	                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);
    				}
        			
        			//From Content In Review -> Content Approved
        			else if (aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_CONTENT_IN_REVIEW.longValue() 
        					&& StringUtils.isNotEmpty(dashboardApp.getContentZipApprovalAction())){
                        if (dashboardApp.getContentZipApprovalAction().equals(AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0])){
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_APPROVED);
                            dashboardForm.setIsContentZipFileLock(AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[0]);
                            dashboardApp.setContentZipFileLocked(AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[0]);
                        }
                        else if (dashboardApp.getContentZipApprovalAction().equals(AimsConstants.DASHBOARD_APP_RADIO_DENY[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_REJECTED);
        			}
        			
        			//From Pending Production -> In Production 
        			else if (aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_PENDING_PRODUCTION.longValue() 
        					&& StringUtils.isNotEmpty(dashboardApp.getMoveToProduction())){
                        if (dashboardApp.getMoveToProduction().equals(AimsConstants.DASHBOARD_APP_RADIO_ACCEPT[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_IN_PRODUCTION);
                        else if (dashboardApp.getMoveToProduction().equals(AimsConstants.DASHBOARD_APP_RADIO_REJECT[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CHANNEL_REJECTED);
        			}
        			
        			//From In Production -> Sunset
        			else if (aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_IN_PRODUCTION.longValue() 
        					&& StringUtils.isNotEmpty(dashboardApp.getRemove())){
                        if (dashboardApp.getRemove().equals(AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[0]))
                            aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUNSET_ID);
        			}
        		
            	}//End of process button 
            	
				//From Initial Approval -> Content In Review (VZW user provides the content zip file)
				if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM)
						&& aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.longValue() 
						&& StringUtils.isNotEmpty(dashboardForm.getContentZipFileFileName())){
	                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);
				}
            	
            }

            // New file uploaded after content reject.
            if (oldStatus != null 
            		&& oldStatus.longValue() == AimsConstants.PHASE_CONTENT_REJECTED.longValue()
            		&& "Y".equals(dashboardForm.getIsNewContentZipFileUploaded())){
            	aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);            	
            	dashboardApp.setContentZipApprovalAction(null);
            	dashboardForm.setAimsLifecyclePhaseId(AimsConstants.PHASE_CONTENT_IN_REVIEW);
            	dashboardForm.setContentZipFileAction(null);
				AimsLifecyclePhase aimsPhaseOfApplication = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, AimsConstants.PHASE_CONTENT_IN_REVIEW.toString());
				dashboardForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
            }
            
            //Check if the app is being cloned
            if ((dashboardForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null))
                clonedFromAppId = dashboardForm.getAppsId();

            //Check to see if userType is Alliance; thus allowing BO to check for empty Blobs
            if (isAllianceUser)
                checkForEmptyFiles = true;

            try{
                
            	DashboardApplicationManager.saveOrUpdateDashboardApplication(
                    aimsApp,
                    dashboardApp,
                    dashboardClobs,
                    dashboardChannel,
                    dashboardForm.getListSelectedDevices(),
                    aimsContact,
                    currUser,
                    currUserType,
                    dashboardForm.getClrPubLogoTempFileId(),
                    dashboardForm.getAppTitleNameTempFileId(),
                    dashboardForm.getSplashScreenEpsTempFileId(),
                    dashboardForm.getActiveScreenEpsTempFileId(),                    
                    dashboardForm.getScreenJpegTempFileId(),
                    dashboardForm.getScreenJpeg2TempFileId(),
                    dashboardForm.getScreenJpeg3TempFileId(),
                    dashboardForm.getScreenJpeg4TempFileId(),
                    dashboardForm.getScreenJpeg5TempFileId(),
                    dashboardForm.getFaqDocTempFileId(),
                    dashboardForm.getUserGuideTempFileId(),
                    dashboardForm.getContentZipFileTempFileId(),
                    dashboardForm.getCompanyLogoTempFileId(),
                    dashboardForm.getTitleImageTempFileId(),
                    checkForEmptyFiles,
                    clonedFromAppId);

            }
            catch (AimsException ae) {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                dashboardForm.setCurrentPage("page1");
                DashboardApplicationHelper.prePopulateForm(dashboardForm);
                return mapping.getInputForward();
            }

            //Post Update Tasks

            dashboardForm.setAppsId(aimsApp.getAppsId());
            dashboardForm.setTask("edit");
            dashboardForm.setOriginalTask("edit");
            dashboardForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());

            newStatus = dashboardForm.getAimsLifecyclePhaseId();
            
            if (((oldStatus != null) && (oldStatus.longValue() != newStatus.longValue()))
                || ((oldStatus == null) && (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())))
            {
            	ArrayList eventsList=new ArrayList();
            	
                if (sendMailToAdobe)
               	 	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_EMAIL_TO_ADOBE));
                
                // these events will be fired when initially approving the application and content zip file was already present.
                if (initialApprovalWithContentFile){
                	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_INITIALLY_APPROVED));
                	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_CONTENT_ZIP_FILE_UPLOADED));
                }                              
                
                if (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                    eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_SUBMITTED));

                else if (newStatus.longValue() == AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.longValue())
                    eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_INITIALLY_APPROVED));                   
                
                else if (newStatus.longValue() == AimsConstants.PHASE_INITIAL_REJECTED.longValue())
                    eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_INITIALLY_REJECTED));

                else if (newStatus.longValue() == AimsConstants.PHASE_CONTENT_IN_REVIEW.longValue())
                    eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_CONTENT_IN_REVIEW));                    

                else if (newStatus.longValue() == AimsConstants.PHASE_CONTENT_APPROVED.longValue())
                    eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_CONTENT_APPROVED));

                else if (newStatus.longValue() == AimsConstants.PHASE_CONTENT_REJECTED.longValue())
                	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_CONTENT_REJECTED));

                else if (newStatus.longValue() == AimsConstants.PHASE_IN_PRODUCTION.longValue())
                	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_MOVED_TO_PRODUCTION));
                
                else if (newStatus.longValue() == AimsConstants.PHASE_CHANNEL_REJECTED.longValue())
                	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_CHANNEL_REJECTED));
                
                else if (newStatus.longValue() == AimsConstants.SUNSET_ID.longValue())
                	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_SUNSET));

                if (eventsList.size() > 0)
                {
                    raiseEvent(aimsApp, dashboardForm, eventsList);
                }
            }
            
            // User upload content zip file after initial approval.
            if (newStatus.longValue() != AimsConstants.SAVED_ID.longValue()
            		&& newStatus.longValue() != AimsConstants.SUBMISSION_ID.longValue()
            		&& newStatus.longValue() != AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.longValue()
            		&& StringUtils.isNotEmpty(dashboardForm.getIsNewContentZipFileUploaded())
            		&& "Y".equals(dashboardForm.getIsNewContentZipFileUploaded())) {
            	ArrayList eventsList=new ArrayList();
            	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_CONTENT_ZIP_FILE_UPLOADED));
            	raiseEvent(aimsApp, dashboardForm, eventsList);            	
            }

            //Set Temp File Ids to Zero
            dashboardForm.setClrPubLogoTempFileId(new Long(0));
            dashboardForm.setAppTitleNameTempFileId(new Long(0));
            dashboardForm.setSplashScreenEpsTempFileId(new Long(0));
            dashboardForm.setActiveScreenEpsTempFileId(new Long(0));            
            dashboardForm.setScreenJpegTempFileId(new Long(0));
            dashboardForm.setScreenJpeg2TempFileId(new Long(0));
            dashboardForm.setScreenJpeg3TempFileId(new Long(0));
            dashboardForm.setScreenJpeg4TempFileId(new Long(0));
            dashboardForm.setScreenJpeg5TempFileId(new Long(0));
            dashboardForm.setFaqDocTempFileId(new Long(0));
            dashboardForm.setUserGuideTempFileId(new Long(0));
            dashboardForm.setContentZipFileTempFileId(new Long(0));
            dashboardForm.setCompanyLogoTempFileId(new Long(0));
            dashboardForm.setTitleImageTempFileId(new Long(0));
            dashboardForm.setIsNewContentZipFileUploaded("N");

            if (sendMailToAdobe){
            	log.debug("Saving adobe mail log.....");
            	AimsDashboardEmailToAdobe adobeMailLog=new AimsDashboardEmailToAdobe();
            	adobeMailLog.setAllianceId(dashboardForm.getAimsAllianceId());
            	adobeMailLog.setAppId(aimsApp.getAppsId());
            	adobeMailLog.setCreatedBy(currUser);
            	adobeMailLog.setCreatedDate(new Date());
            	DashboardApplicationManager.saveAdobeMailLog(adobeMailLog);
            }
            if (!newStatus.equals(oldStatus)){
    			try {
    				AimsLifecyclePhase aimsPhaseOfApplication = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, newStatus.toString());
    				dashboardForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
    			} catch (HibernateException e) {
    				log.error(e,e);
    			}
    		}

            ActionMessages messages = new ActionMessages();
            ActionMessage message = null;

            if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                message = new ActionMessage("message.manage.app.saved");
            else if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                message = new ActionMessage("message.manage.app.submitted");            
            else if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                message = new ActionMessage("message.manage.app.saved");
            else if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                message = new ActionMessage("message.manage.app.saved");
            else if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
                message = new ActionMessage("message.manage.app.processed");            

            messages.add(ActionMessages.GLOBAL_MESSAGE, message);

            saveMessages(request, messages);
            forward = dashboardForm.getCurrentPage();
        }

        return mapping.findForward(forward);
    }

	public void sendFilesToMotricity(HttpServletRequest request,
									  AimsApp aimsApp, 
									  AimsDashboardApps dashboardApp,
									  AimsDashboardChannelIds dashboardChannel,
									  DashboardApplicationUpdateForm dashboardForm, 
									  String xmlFolderPath) throws IOException, SAXException, Exception, HibernateException,AimsException {
		
		HttpSession session=request.getSession();
		AimsUser aimsUser=(AimsUser) (session.getAttribute(AimsConstants.AIMS_USER));
		String currUser = aimsUser.getUsername();
        String currUserType = aimsUser.getUserType();

		String contentZipFileTmpLoc=(String)session.getAttribute(AimsConstants.SESSION_CONTENT_ZIP_FILE_TMP_LOCATION);
		String ftpsSendTestFile=(String)session.getAttribute(AimsConstants.SESSION_FTPS_SEND_TEST_FILE);        		
		GnuPG gpg=new GnuPG();
		boolean transfered=false;
		long gpgFileLength=0;
		long xmlFileLength=0;
		StringBuffer fileNameBuffer=new StringBuffer();     
		String contentZipFileName=new String();
		String xmlFileName=new String();
		
		String xmlData=DashboardApplicationHelper.createChannelXml(xmlFolderPath+AimsConstants.DASHBOARD_XML_FILE, dashboardApp, dashboardChannel);

		if (StringUtils.isNotEmpty(ftpsSendTestFile) && "Y".equalsIgnoreCase(ftpsSendTestFile)){
			log.debug("File test mode is enabled.");
			fileNameBuffer.append("Test_Netpace_");
		}
		fileNameBuffer.append("VZWDashboard"+"_");
		fileNameBuffer.append(StringUtils.replace(StringUtils.deleteWhitespace(dashboardForm.getAllianceName()), "_", "")+"_");
		fileNameBuffer.append(StringUtils.deleteWhitespace(aimsApp.getTitle())+"_");
		fileNameBuffer.append(StringUtils.replace(StringUtils.deleteWhitespace(aimsApp.getVersion()), "_", ""));

		fileNameBuffer=new StringBuffer(StringFuncs.replaceInvalidCharacters(fileNameBuffer.toString(), "", AimsConstants.DASHBOARD_INVALID_CHARS));
		
		contentZipFileName = fileNameBuffer + ".zip";
		xmlFileName=fileNameBuffer+".xml";
		
		File contentZipFile=new File(contentZipFileTmpLoc, contentZipFileName);
		File xmlFile=new File(contentZipFileTmpLoc, xmlFileName);
		
		File gpgContentZipFile=null;
		File gpgXmlFile=null;
		if (contentZipFile.exists()){
			contentZipFile.delete();        			        				        	
		}
		contentZipFile.createNewFile();
		
		if (xmlFile.exists()){
			xmlFile.delete();        			        				        	
		}
		xmlFile.createNewFile();

		BufferedOutputStream bos=null;
		BufferedWriter bufferWriter=null;
		try {
			bos=new BufferedOutputStream(new FileOutputStream(contentZipFile));
			byte[] fileData=DashboardApplicationManager.getContentZipFile(aimsApp.getAppsId());
			bos.write(fileData);
			bos.flush();
			
			bufferWriter=new BufferedWriter(new FileWriter(xmlFile));
			bufferWriter.write(xmlData);
			bufferWriter.flush();
		} catch (Exception e){
			log.error(e,e);
			throw e;
		} finally {
			if (bos != null){
				bos.close();
			}
			if (bufferWriter != null){
				bufferWriter.close();
			}
		}
		
		log.debug("wrote zip and xml files on local disk.");
		
		//sign files
		gpg.sign(contentZipFile.getAbsolutePath());
		if (contentZipFile.delete()){
			log.debug("Deleted "+contentZipFileName+" file after signed.");
		}

		gpg.sign(xmlFile.getAbsolutePath());
		if (xmlFile.delete()){
			log.debug("Deleted "+xmlFileName+" file after signed.");
		}
		
		//check and send signed file.
		gpgContentZipFile=new File(contentZipFile.getParent(), contentZipFile.getName()+".gpg");
		gpgXmlFile=new File(xmlFile.getParent(), xmlFile.getName()+".gpg");
		if (gpgContentZipFile != null && gpgContentZipFile.exists() && gpgXmlFile != null && gpgXmlFile.exists()){
			gpgFileLength=gpgContentZipFile.length();
			xmlFileLength=gpgXmlFile.length();
			try {
				transfered=FTPS.sendFilesToMotricity(new File[]{gpgContentZipFile, gpgXmlFile},aimsApp.getAppsId(), aimsApp.getAimsLifecyclePhaseId(), currUser);
			} catch (AimsException ae) {
		        saveErrors(request, DBErrorFinder.populateActionErrors(ae));		            
		    }
			
			log.debug("FTPS transfered: "+transfered);
			if (gpgContentZipFile.delete()){
				log.debug("Deleted signed zip gpg files after transfered.");
			}
			if (gpgXmlFile.delete()){
				log.debug("Deleted signed xml files after transfered.");
			}
			
			if (transfered){
				AimsDashboardFtpsLog ftpsLogForZip=new AimsDashboardFtpsLog();	    			
				AimsDashboardFtpsLog ftpsLogForXml=new AimsDashboardFtpsLog();	    			
				ftpsLogForZip.setAppId(aimsApp.getAppsId());
				ftpsLogForZip.setStatus("Success");
				ftpsLogForZip.setFileName(gpgContentZipFile.getName());
				ftpsLogForZip.setAimsLifecycleId(aimsApp.getAimsLifecyclePhaseId());
				ftpsLogForZip.setDescription("File Size:"+gpgFileLength);
				ftpsLogForZip.setCreatedDate(new Date());
				ftpsLogForZip.setCreatedBy(currUser);

				ftpsLogForXml.setAppId(aimsApp.getAppsId());
				ftpsLogForXml.setStatus("Success");
				ftpsLogForXml.setFileName(gpgXmlFile.getName());
				ftpsLogForXml.setAimsLifecycleId(aimsApp.getAimsLifecyclePhaseId());
				ftpsLogForXml.setDescription("File Size:"+xmlFileLength);
				ftpsLogForXml.setCreatedDate(new Date());
				ftpsLogForXml.setCreatedBy(currUser);
						    			
				DashboardApplicationManager.saveFtpsLog(ftpsLogForZip);
				DashboardApplicationManager.saveFtpsLog(ftpsLogForXml);
				
				//From Content Approved -> Pending Production
				if (aimsApp.getAimsLifecyclePhaseId().longValue()==AimsConstants.PHASE_CONTENT_APPROVED.longValue() 
						&& StringUtils.isEmpty(dashboardApp.getFtpContentZipFile())){
					dashboardApp.setFtpContentZipFile("Y");
					aimsApp.setAimsLifecyclePhaseId(AimsConstants.PHASE_PENDING_PRODUCTION);    				
					AimsLifecyclePhase aimsPhaseOfApplication = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, aimsApp.getAimsLifecyclePhaseId().toString());
					dashboardForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
					dashboardForm.setAimsLifecyclePhaseId(AimsConstants.PHASE_PENDING_PRODUCTION);
					
		        	ArrayList eventsList=new ArrayList();
		        	eventsList.add(EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_STATUS_PENDING_PRODUCTION));
		        	raiseEvent(aimsApp, dashboardForm, eventsList);            	

				}//End status change and notification

				aimsApp.setLastUpdatedBy(currUser);
				aimsApp.setLastUpdatedDate(new Date());
				dashboardForm.setIsContentZipFileLock(AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[0]);
				dashboardApp.setContentZipFileLocked(AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[0]);
				DashboardApplicationManager.updateDashboardApplication(aimsApp, dashboardApp,currUser,currUserType);
				
				ActionMessages messages=new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.dashboard.app.ftps.success"));
				saveMessages(request, messages);				
			}					
		}
		else {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.dashboard.app.gpg.signature"));
			saveErrors(request,errors);
		}//End of checking gpg file existence.
	}

	private void raiseEvent(AimsApp aimsApp,
			DashboardApplicationUpdateForm dashboardForm, ArrayList eventsList)
			throws HibernateException {
		AimsAllianc aimsAllianceForEvent =
		    (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

		AimsUser aimAllianceAdminUser =
		    (AimsUser) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsUser.class, aimsAllianceForEvent.getAimsUserByAdminUserId().toString());

		AimsContact aimAllianceAdminContact =
		    (AimsContact) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());
		
		for (int i = 0; i < eventsList.size(); i++) {
			AimsEventLite aimsEvent = (AimsEventLite)eventsList.get(i);
			log.debug("Firing event: "+aimsEvent.getEventName());
		    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
		    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
		    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());

		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianceForEvent.getVendorId().toString());
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_USER_ID, aimAllianceAdminContact.getEmailAddress());
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME, aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE, aimAllianceAdminContact.getPhone());
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_EMAIL, aimAllianceAdminContact.getEmailAddress());
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CREATE_ACCOUNT, "CREATE ACCOUNT");
		    
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTENT_FILE_NAME, dashboardForm.getContentZipFileFileName());                    
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());                    
		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
		    
		    if (AimsConstants.PHASE_INITIAL_REJECTED.equals(dashboardForm.getAimsLifecyclePhaseId())){
		    	aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMMENTS, dashboardForm.getInitialApprovalNotes());
		    }
		    else if (AimsConstants.PHASE_CONTENT_REJECTED.equals(dashboardForm.getAimsLifecyclePhaseId())){
		    	aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMMENTS, dashboardForm.getContentZipFileNotes());
		    }
		    
		    aimsEvent.raiseEvent(aimsEventObject);
		}
	}
}
