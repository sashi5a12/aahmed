package com.netpace.aims.controller.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import net.sf.hibernate.HibernateException;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.GenericException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsDashboardAppClob;
import com.netpace.aims.model.application.AimsDashboardApps;
import com.netpace.aims.model.application.AimsDashboardChannelIds;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.model.masters.AimsDevicePid;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.XMLUtils;

public class DashboardApplicationHelper {
	
	static Logger log = Logger.getLogger(DashboardApplicationHelper.class.getName());
	
    public static void prePopulateForm(DashboardApplicationUpdateForm dashboardForm){
    	log.debug("DashboardApplication.setApplicationFormLists: Start");
    	log.debug("Setting list for page: "+dashboardForm.getCurrentPage());
		try {
			if (StringUtils.isEmpty(dashboardForm.getCurrentPage())
					|| "page1".equalsIgnoreCase(dashboardForm.getCurrentPage())
					|| "view".equalsIgnoreCase(dashboardForm.getTask())){
				dashboardForm.setAllCategories(AimsApplicationsManager.getCategories(AimsConstants.DASHBOARD_PLATFORM_ID));
				dashboardForm.setAllSubCategories(AimsApplicationsManager.getSubCategories());
				Collection availableDevices = AimsDevicesManager.getDevices(AimsConstants.DASHBOARD_PLATFORM_ID, true);
				if (availableDevices != null && availableDevices.size() > 0) {
					List list = new ArrayList();
					Iterator itr = availableDevices.iterator();
					while (itr.hasNext()) {
						AimsDevic device = (AimsDevic) itr.next();
						if (device.getAimsDevicePids() != null && device.getAimsDevicePids().size() > 0) {
							AimsDevicePid pid = (AimsDevicePid) device.getAimsDevicePids().iterator().next();
							if (pid != null && pid.getDevicePidId() != null) {
								device.setDeviceModel(device.getDeviceModel() + " ("+ pid.getPid() + ")");
							}
						}
						list.add(device);
					}					
				}
				dashboardForm.setAvailableDevices(availableDevices);
			}
			if ("page2".equalsIgnoreCase(dashboardForm.getCurrentPage())
					|| "view".equalsIgnoreCase(dashboardForm.getTask())){						
				dashboardForm.setAllContacts(AimsApplicationsManager.getContacts(dashboardForm.getAimsAllianceId()));
			}
		} 
		catch (Exception ex) {
			log.debug(ex,ex);
		}
		log.debug("DashboardApplication.setApplicationFormLists: End");
	}

	public static void setupAction(HttpServletRequest request, 
								   String taskname, 
								   String viewPageToView,
								   DashboardApplicationUpdateForm dashboardForm, 
								   AimsApp aimsApp,
								   AimsDashboardApps dashboardApp,
								   AimsDashboardAppClob dashboardClobs,
								   AimsDashboardChannelIds dashboardChannel,
								   AimsAppCategory aimsAppCategory, 
								   String dateFormat) throws AimsSecurityException {

		log.debug("DashboardApplicationHelper.setupAction Start:");
		log.debug("taskName: "+taskname);
		
		HttpSession session = request.getSession();
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        boolean isVerizonUser = currUserType.equals(AimsConstants.VZW_USERTYPE);
        boolean isAllianceUser = currUserType.equals(AimsConstants.ALLIANCE_USERTYPE);

		// CHECK ACCESS
		if (!(ApplicationHelper.checkPlatformAccess(request, taskname, AimsConstants.DASHBOARD_PLATFORM_ID)))
			throw new AimsSecurityException();

		if (taskname.equalsIgnoreCase("edit"))
			if (!(ApplicationHelper.checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
				throw new AimsSecurityException();

		if (taskname.equalsIgnoreCase("delete"))
			if (!AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_DASHBOARD_APPS, AimsSecurityManager.DELETE))
				throw new AimsSecurityException();
		// END OF CHECK ACCESS

		dashboardForm.setCurrentPage("page1");
		dashboardForm.setOriginalTask(dashboardForm.getTask());
		
		// Set Temp File Ids to Zero
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

		if (taskname.equalsIgnoreCase("create")) {
			dashboardForm.setTask("create");
			dashboardForm.setAppsId(new Long(0));
			dashboardForm.setLanguage("EN");
			dashboardForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
			dashboardForm.setAimsAllianceId(currentUserAllianceId);
		} 
		else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view"))) {
			if (taskname.equalsIgnoreCase("edit"))
				dashboardForm.setTask("edit");
			else if (taskname.equalsIgnoreCase("view"))
				dashboardForm.setTask("view");

			// For Cloning....
			if (taskname.equalsIgnoreCase("clone")) {
				dashboardForm.setTitle(null);
				dashboardForm.setChannelVersion(null);				
				dashboardForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
				dashboardForm.setAimsAllianceId(currentUserAllianceId);
				dashboardForm.setTask("create");
				dashboardForm.setOriginalTask("create");
			} else {
				dashboardForm.setTitle(aimsApp.getTitle());
				dashboardForm.setChannelVersion(aimsApp.getVersion());
				dashboardForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
				dashboardForm.setAimsAllianceId(aimsApp.getAimsAllianceId());
				dashboardForm.setSubmittedDate(Utility.convertToString(aimsApp.getSubmittedDate(), dateFormat));
				dashboardForm.setAppsId(aimsApp.getAppsId());
				dashboardForm.setIsContentZipFileLock(dashboardApp.getContentZipFileLocked());
			}
			
			if (taskname.equalsIgnoreCase("view") && "journal".equalsIgnoreCase(viewPageToView)){
                try {
					Collection journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
					dashboardForm.setJournalCombinedText(getFormattedJournalEntries(journalEntries)); 
				} catch (Exception e) {
					log.error(e,e);
					AimsException aimsException = new AimsException("Error");
					aimsException.addException(new   GenericException("error.generic.database"));						
				}					
			}
						
			dashboardForm.setChannelType(dashboardApp.getChannelType());
			dashboardForm.setLanguage(aimsApp.getLanguage());
			dashboardForm.setChannelSize(dashboardApp.getChannelSize());
			dashboardForm.setShortDesc(aimsApp.getShortDesc());
			dashboardForm.setLongDesc(aimsApp.getLongDesc());
			dashboardForm.setVendorProductDisplay(dashboardApp.getVendorProductDisplay());
			dashboardForm.setVzwRetailPrice(dashboardApp.getVzwRetailPrice());
			if (aimsAppCategory != null)
				dashboardForm.setAimsAppCategoryId(aimsAppCategory.getCategoryId());
			dashboardForm.setAimsAppSubCategoryId(aimsApp.getAimsAppSubCategoryId());						

			// Set File Names
			dashboardForm.setClrPubLogoFileName(dashboardApp.getClrPubLogoFileName());
			dashboardForm.setAppTitleNameFileName(dashboardApp.getAppTitleNameFileName());
			dashboardForm.setSplashScreenEpsFileName(aimsApp.getSplashScreenEpsFileName());
			dashboardForm.setActiveScreenEpsFileName(aimsApp.getActiveScreenEpsFileName());
			dashboardForm.setScreenJpegFileName(aimsApp.getScreenJpegFileName());
			dashboardForm.setScreenJpeg2FileName(aimsApp.getScreenJpeg2FileName());
			dashboardForm.setScreenJpeg3FileName(aimsApp.getScreenJpeg3FileName());
			dashboardForm.setScreenJpeg4FileName(aimsApp.getScreenJpeg4FileName());
			dashboardForm.setScreenJpeg5FileName(aimsApp.getScreenJpeg5FileName());
			dashboardForm.setFaqDocFileName(aimsApp.getFaqDocFileName());
			dashboardForm.setUserGuideFileName(aimsApp.getUserGuideFileName());			
	        dashboardForm.setContentZipFileFileName(dashboardApp.getContentZipFileFileName());
	        dashboardForm.setCompanyLogoFileName(dashboardApp.getCompanyLogoFileName());
	        dashboardForm.setTitleImageFileName(dashboardApp.getTitleImageFileName());
	        	        	    
	        dashboardForm.setChannelDeployments(aimsApp.getAppDeployments());
	        dashboardForm.setAimsContactId(aimsApp.getAimsContactId());
	        dashboardForm.setDeveloperName(dashboardApp.getDeveloperName());
	        dashboardForm.setPublisherName(dashboardApp.getPublisherName());
	        dashboardForm.setSellingPoints(dashboardApp.getSellingPoints());
	        dashboardForm.setPlannedDevStartDate(Utility.convertToString(dashboardApp.getPlannedDevStartDate(), dateFormat));
	        
            //set user guide
	        dashboardForm.setProductDescription(dashboardApp.getProductDescription());
	        dashboardForm.setUsingApplication(dashboardClobs.getUsingApplicationStr());
	        dashboardForm.setTipsAndTricks(dashboardClobs.getTipsAndTricksStr());
	        dashboardForm.setFaq(dashboardClobs.getFaqStr());
	        dashboardForm.setTroubleshooting(dashboardClobs.getTroubleshootingStr());
	        dashboardForm.setDevCompanyDisclaimer(dashboardClobs.getDevelopmentCompanyDisclaimerStr());
	        dashboardForm.setAdditionalInformation(dashboardClobs.getAdditionalInformationStr());
	        
	        
	        // Set VZW Specific Information
			if (isVerizonUser) {
				if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.DASHBOARD_INITIAL_APPROVAL)){
					dashboardForm.setInitialApprovalNotes(dashboardApp.getInitialApprovalNotes());
					dashboardForm.setInitialApprovalAction(dashboardApp.getInitialApprovalAction());
				}
				
				if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.DASHBOARD_CONTENT_ZIP_FILE)){
					if (dashboardChannel.getChannelId()!= null){
						dashboardForm.setChannelId(dashboardChannel.getChannelId().toString());
					}
					dashboardForm.setContentZipFileNotes(dashboardApp.getContentZipNotes());										
					dashboardForm.setContentZipFileAction(dashboardApp.getContentZipApprovalAction());
				}
				if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.DASHBOARD_TRACKING)){
					dashboardForm.setMoveToProduction(dashboardApp.getMoveToProduction());
					if (AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[0].equalsIgnoreCase(dashboardApp.getRemove())){
						dashboardForm.setRemove(AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[0]);
					}					
				}				
			}
		}// End of taskName=edit,view check
		
		if (dashboardForm.getAppsId() == null || dashboardForm.getAppsId().longValue() == 0 || taskname.equalsIgnoreCase("clone")){
			dashboardForm.setApplicationStatus("NEW");			
		}
		else {
			if (dashboardForm.getAimsLifecyclePhaseId() != null && dashboardForm.getAimsLifecyclePhaseId().longValue() >0 ){
				try {
					AimsLifecyclePhase aimsPhaseOfApplication = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, dashboardForm.getAimsLifecyclePhaseId().toString());
					dashboardForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
				} catch (HibernateException e) {
					log.error(e,e);
				}
			}
		}
		
		if (dashboardForm.getAimsAllianceId() != null && dashboardForm.getAimsAllianceId().longValue() > 0){
			try {				
				dashboardForm.setAllianceName(AllianceManager.getAllianceCompanyName(dashboardForm.getAimsAllianceId()));
			} catch (HibernateException e) {
				log.error(e,e);
			}
		}
		
		DashboardApplicationHelper.prePopulateForm(dashboardForm);
		log.debug("DashboardApplicationHelper.setupAction End:");
	}
	
    public static String updateAction(HttpServletRequest request, 
    								  String taskname, 
    								  DashboardApplicationUpdateForm dashboardForm,
    								  AimsApp aimsApp, 
    								  AimsDashboardApps dashboardApp,
    								  AimsDashboardAppClob dashboardClobs,
    								  AimsDashboardChannelIds dashboardChannelId,
    								  AimsContact aimsContact, 
    								  String dateFormat) throws AimsException, AimsSecurityException {
    	log.debug("DashboardApplicationHelper.updateAction: Start");
		String forward = "view";
		HttpSession session = request.getSession();
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        boolean isVerizonUser = currUserType.equals(AimsConstants.VZW_USERTYPE);
        boolean isAllianceUser = currUserType.equals(AimsConstants.ALLIANCE_USERTYPE);

		//CHECK ACCESS
		if (!(ApplicationHelper.checkPlatformAccess(request, dashboardForm.getOriginalTask(), AimsConstants.DASHBOARD_PLATFORM_ID)))
			throw new AimsSecurityException();

		if (taskname.equalsIgnoreCase("edit"))
			if (!(ApplicationHelper.checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
				throw new AimsSecurityException();
		//END OF CHECK ACCESS

		if ((taskname.equalsIgnoreCase("page1"))
				|| (taskname.equalsIgnoreCase("page2"))
				|| (taskname.equalsIgnoreCase("page3"))
				|| (taskname.equalsIgnoreCase("page4"))
				|| (taskname.equalsIgnoreCase("page5"))
				|| (taskname.equalsIgnoreCase("journal"))
				|| (taskname.equalsIgnoreCase("submitJournal"))
				|| (taskname.equalsIgnoreCase("sendZipFile"))
				|| (taskname.equalsIgnoreCase("submitpage4"))
				|| (taskname.equalsIgnoreCase("submitpage4View"))) {
			if (taskname.equalsIgnoreCase("page1"))
				forward = "page1";
			else if (taskname.equalsIgnoreCase("page2"))
				forward = "page2";
			else if (taskname.equalsIgnoreCase("page3"))
				forward = "page3";
			else if (taskname.equalsIgnoreCase("page4"))
				forward = "page4";
			else if (taskname.equalsIgnoreCase("page5"))
				forward = "page5";
			else if (taskname.equalsIgnoreCase("sendZipFile"))
				forward = "page3";
			else if (taskname.equalsIgnoreCase("submitpage4"))
				forward = "page4";
			else if (taskname.equalsIgnoreCase("submitpage4View"))
                forward = "page4View";
			
			if (taskname.equalsIgnoreCase("sendZipFile"))
				dashboardForm.setCurrentPage("page3");
			else if (taskname.equalsIgnoreCase("submitpage4"))
            	dashboardForm.setCurrentPage("page4");	
			else if (taskname.equalsIgnoreCase("submitpage4View"))
				dashboardForm.setCurrentPage("page4");				
			else 
				dashboardForm.setCurrentPage(taskname);			
			dashboardForm.setTask(dashboardForm.getOriginalTask());

			if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {

				//User submitted a journal entry. 
				if (taskname.equalsIgnoreCase("submitpage4") || taskname.equalsIgnoreCase("submitpage4View")) {
					try {
						AimsApplicationsManager.saveJournalEntry(
								dashboardForm.getAppsId(),
								dashboardForm.getJournalText(),
								dashboardForm.getJournalType(),
						        currUser);
					} catch (Exception e) {
						log.error(e,e);
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new   GenericException("error.generic.persistance"));
						throw aimsException;
					}					
				}

				//Journal Entry page.
				if ("page4".equals(dashboardForm.getCurrentPage())){
                    try {
						Collection journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
						dashboardForm.setJournalCombinedText(getFormattedJournalEntries(journalEntries)); 
					} catch (Exception e) {
						log.error(e,e);
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new   GenericException("error.generic.database"));						
					}					
				}
			}
		}

		if (taskname.equalsIgnoreCase("create") || taskname.equalsIgnoreCase("edit")) {
			if (taskname.equalsIgnoreCase("create")) {
				aimsApp.setAimsAllianceId(currentUserAllianceId);
				aimsApp.setAimsUserAppCreatedById(currUserId);
				aimsApp.setIfOnHold("N");
				aimsApp.setCreatedBy(currUser);
				aimsApp.setCreatedDate(new Date());
			}

			aimsApp.setTitle(dashboardForm.getTitle());
			aimsApp.setVersion(dashboardForm.getChannelVersion());
			dashboardApp.setChannelType(dashboardForm.getChannelType());
			dashboardApp.setChannelSize(dashboardForm.getChannelSize());
			aimsApp.setLanguage(dashboardForm.getLanguage());
			aimsApp.setShortDesc(dashboardForm.getShortDesc());
			aimsApp.setLongDesc(dashboardForm.getLongDesc());
			aimsApp.setLastUpdatedBy(currUser);
			aimsApp.setLastUpdatedDate(new Date());
			aimsApp.setAimsPlatformId(AimsConstants.DASHBOARD_PLATFORM_ID);
                        
			//SubCategory
			if ((dashboardForm.getAimsAppSubCategoryId() != null)
					&& (dashboardForm.getAimsAppSubCategoryId().longValue() != 0))
				aimsApp.setAimsAppSubCategoryId(dashboardForm.getAimsAppSubCategoryId());
			
			//Contact
			aimsApp.setAimsContactId(dashboardForm.getAimsContactId());
			if (dashboardForm.getAimsContactId().longValue() == 0) {
				aimsContact.setFirstName(dashboardForm.getContactFirstName());
				aimsContact.setLastName(dashboardForm.getContactLastName());
				aimsContact.setEmailAddress(dashboardForm.getContactEmail());
				aimsContact.setTitle(dashboardForm.getContactTitle());
				aimsContact.setPhone(dashboardForm.getContactPhone());
				aimsContact.setMobile(dashboardForm.getContactMobile());
				aimsContact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
				aimsContact.setCreatedBy(currUser);
				aimsContact.setCreatedDate(new Date());
				aimsContact.setLastUpdatedBy(currUser);
				aimsContact.setLastUpdatedDate(new Date());
			}

			dashboardApp.setVendorProductDisplay(dashboardForm.getVendorProductDisplay());
			dashboardApp.setVzwRetailPrice(dashboardForm.getVzwRetailPrice());
			
			aimsApp.setAppDeployments(dashboardForm.getChannelDeployments());			
            dashboardApp.setDeveloperName(dashboardForm.getDeveloperName());
            dashboardApp.setPublisherName(dashboardForm.getPublisherName());
            dashboardApp.setSellingPoints(dashboardForm.getSellingPoints());
            dashboardApp.setPlannedDevStartDate(Utility.convertToDate(dashboardForm.getPlannedDevStartDate(), dateFormat));
			
            //Start User Guide
            if(StringUtils.isNotEmpty(dashboardForm.getProductDescription())){
            	dashboardApp.setProductDescription(dashboardForm.getProductDescription().trim());	
            }
            
            if (StringUtils.isNotEmpty(dashboardForm.getUsingApplication())){
            	dashboardClobs.setUsingApplicationStr(dashboardForm.getUsingApplication().trim());
            }
            else {
            	dashboardClobs.setUsingApplicationStr("");
            }
            if (StringUtils.isNotEmpty(dashboardForm.getTipsAndTricks())){
            	dashboardClobs.setTipsAndTricksStr(dashboardForm.getTipsAndTricks().trim());
            }
            else {
            	dashboardClobs.setTipsAndTricksStr("");
            }
            if(StringUtils.isNotEmpty(dashboardForm.getFaq())){
            	dashboardClobs.setFaqStr(dashboardForm.getFaq().trim());
            }
            else {
            	dashboardClobs.setFaqStr("");
            }
            if (StringUtils.isNotEmpty(dashboardForm.getTroubleshooting())){
            	dashboardClobs.setTroubleshootingStr(dashboardForm.getTroubleshooting().trim());
            }
            else {
            	dashboardClobs.setTroubleshootingStr(dashboardForm.getTroubleshooting());
            }
            if (StringUtils.isNotEmpty(dashboardForm.getDevCompanyDisclaimer())){
            	dashboardClobs.setDevelopmentCompanyDisclaimerStr(dashboardForm.getDevCompanyDisclaimer().trim());
            }
            else {
            	dashboardClobs.setDevelopmentCompanyDisclaimerStr("");
            }
            if (StringUtils.isNotEmpty(dashboardForm.getAdditionalInformation())){
            	dashboardClobs.setAdditionalInformationStr(dashboardForm.getAdditionalInformation().trim());
            }
            else {
            	dashboardClobs.setAdditionalInformationStr("");
            }
                        
            //End User Guide            
            
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
				//Start of check to see if alliance has been accepted
				Object[] userValues = null;
				String allianceStatus = null;
				Collection AimsAlliance = null;

				try {
					AimsAlliance = AllianceManager.getAllianceDetails(aimsApp.getAimsAllianceId(), "");
				} 
				catch (Exception ex) {
					AimsException aimsException = new AimsException("Error");
					aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
					throw aimsException;
				}

				for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();) {
					userValues = (Object[]) iter.next();
					allianceStatus = (String) userValues[3];
				}

				if (!(dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))) {
					if (!(allianceStatus.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED))) {
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
						throw aimsException;
					}
				}
				//End of check to see if alliance has been accepted

				//check alliance has accepted atleast one active dashboard contract
				/*if (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)) {
	                try {
						if(!ApplicationHelper.validateAllianceContract(currentUserAllianceId, AimsConstants.DASHBOARD_PLATFORM_ID, currUserType)) {
							AimsException aimsException = new AimsException("Error");
							aimsException.addException(new RecordNotFoundException("error.dashboard.app.contract.acceptance"));
							throw aimsException;
						}
					} catch (HibernateException e) {
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new RecordNotFoundException("error.dashboard.app.contract.acceptance.exception"));
						throw aimsException;
					}
	            }*/

			}
            
            //Set VZW Specific Information
            if (isVerizonUser){
            	
            	if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.DASHBOARD_INITIAL_APPROVAL)){
            		dashboardApp.setInitialApprovalNotes(dashboardForm.getInitialApprovalNotes());
            		dashboardApp.setInitialApprovalAction(dashboardForm.getInitialApprovalAction());
            	}
            	
            	if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.DASHBOARD_CONTENT_ZIP_FILE)){
            		if (StringUtils.isNotEmpty(dashboardForm.getChannelId()) && StringUtils.isNumeric(dashboardForm.getChannelId())){
            			dashboardChannelId.setChannelId(new Long(dashboardForm.getChannelId()));
            		}
            		else {
            			dashboardChannelId.setChannelId(null);
            		}
	            	dashboardApp.setContentZipNotes(dashboardForm.getContentZipFileNotes());
	            	dashboardApp.setContentZipApprovalAction(dashboardForm.getContentZipFileAction());
	            	dashboardApp.setContentZipFileLocked(dashboardForm.getIsContentZipFileLock());
            	}
            	
            	if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.DASHBOARD_TRACKING)){
            		dashboardApp.setMoveToProduction(dashboardForm.getMoveToProduction());
            		dashboardApp.setRemove(dashboardForm.getRemove());
            	}
            }			

		}
		DashboardApplicationHelper.prePopulateForm(dashboardForm);
		log.debug("DashboardApplicationHelper.updateAction: End");
		return forward;
	}
    
    private static String getFormattedJournalEntries(Collection journalEntries) {
		StringBuffer journalCombinedText = new StringBuffer();

		if (journalEntries != null) {
			for (Iterator it = journalEntries.iterator(); it.hasNext();) {
				AimsJournalEntry journalEntry=(AimsJournalEntry) it.next();
				if (journalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PRIVATE))
					journalCombinedText.append("[Private] ");
				else if (journalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PUBLIC))
					journalCombinedText.append("[Public] ");
				journalCombinedText.append(Utility.convertToString(journalEntry.getCreatedDate(), AimsConstants.DATE_TIME_FORMAT));
				journalCombinedText.append(" : (");
				journalCombinedText.append(journalEntry.getCreatedBy());
				journalCombinedText.append(")\n");
				journalCombinedText.append(journalEntry.getJournalText());
				journalCombinedText.append("\n\n");				
			}
		}
		return journalCombinedText.toString();
	}
    
    public static String createChannelXml(String fileName, AimsDashboardApps dashboardApp, AimsDashboardChannelIds dashboardChannel) throws IOException, SAXException, Exception{
    	String[] devicesName=DashboardApplicationManager.getAppDevicesName(dashboardApp.getDashboardAppsId());
    	Document doc = XMLUtils.loadFile(fileName);
    	Element searchFrom = null;
    	
    	searchFrom = (Element) doc.getElementsByTagName("DashboardChannelData").item(0);
    	String contentType=dashboardApp.getChannelType().equals(AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[0])? AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[1]: AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_PREMIUM[1];
    	if (dashboardChannel.getChannelId() != null){
    		XMLUtils.updateTextNode("ChannelID", searchFrom, dashboardChannel.getChannelId().toString(), 0);	
    	}
    	else {
    		searchFrom.removeChild(searchFrom.getElementsByTagName("ChannelID").item(0));
    	}
    	
        XMLUtils.updateTextNode("ChannelType", searchFrom, contentType, 0);

        searchFrom = (Element) doc.getElementsByTagName("Devices").item(0);
        if (devicesName != null){
	        for (int i=0; i<devicesName.length; i++){
	        	Element deviceEle=doc.createElement("Device");
	        	Text textNode=doc.createTextNode(devicesName[i]);
	        	deviceEle.appendChild(textNode);
	        	searchFrom.appendChild(deviceEle);
	        }
        }
    	return XMLUtils.docToString(doc);
    }
}
