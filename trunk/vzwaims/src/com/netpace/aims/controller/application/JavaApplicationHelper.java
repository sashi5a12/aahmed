package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.GenericException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsJavaAppClob;
import com.netpace.aims.model.application.AimsJavaApps;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.JavaUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

public class JavaApplicationHelper {

	static Logger log = Logger.getLogger(JavaApplicationHelper.class.getName());
	public static String VERSION_CONST_TITLE = "TITLE";
	public static String VERSION_CONST_VERSION = "VERSION";
	public static String VERSION_CONST_SHORT_DESC = "SHORT_DESC";

	public static String VERSION_CONST_LONG_DESC = "LONG_DESC";

	public static String VERSION_CONST_LONG_PRODUCT_NAME = "LONG_PRODUCT_NAME";

	public static String VERSION_CONST_DESC_CONTENT_OFFERING = "DESC_CONTENT_OFFERING";

	public static String VERSION_CONST_CONTENT_TYPE = "CONTENT_TYPE";

	public static String VERSION_CONST_DEMO_URL = "DEMO_URL";

	public static String VERSION_CONST_TEST_URL = "TEST_URL";

	public static String VERSION_CONST_PRODUCTION_URL = "PRODUCTION_URL";

	public static String VERSION_CONST_WEBSITE_URL = "WEBSITE_URL";

	public static String VERSION_CONST_TECH_CONTACT_ID = "TECH_CONTACT_ID";

	public static String VERSION_CONST_IF_PR_RELEASE = "IF_PR_RELEASE";

	public static String VERSION_CONST_SUB_CATEGORY_ID_1 = "SUB_CATEGORY_ID_1";

	public static String VERSION_CONST_SUB_CATEGORY_ID_2 = "SUB_CATEGORY_ID_2";

	public static String VERSION_CONST_SUB_CATEGORY_ID_3 = "SUB_CATEGORY_ID_3";

	public static String VERSION_CONST_LINK_ORDER_1 = "LINK_ORDER_1";

	public static String VERSION_CONST_LINK_ORDER_2 = "LINK_ORDER_2";

	public static String VERSION_CONST_LINK_ORDER_3 = "LINK_ORDER_3";

	public static String VERSION_CONST_SCREEN_SHOT_FILE_NAME = "SCREEN_SHOT_FILE_NAME";

	public static String VERSION_CONST_SCREEN_JPEG_FILE_NAME = "SCREEN_JPEG_FILE_NAME";

	public static String VERSION_CONST_USER_GUIDE_FILE_NAME = "USER_GUIDE_FILE_NAME";

	public static String VERSION_CONST_FAQ_DOC_FILE_NAME = "FAQ_DOC_FILE_NAME";

	public static String VERSION_CONST_PRESENTATION_FILE_NAME = "PRESENTATION_FILE_NAME";

	public static String VERSION_CONST_PRODUCT_LOGO_FILE_NAME = "PRODUCT_LOGO_FILE_NAME";

	public static String VERSION_CONST_APP_IMG_MEDIUM_FILE_NAME = "APP_IMG_MEDIUM_FILE_NAME";

	public static String VERSION_CONST_APP_IMG_POTRAIT_FILE_NAME = "APP_IMG_POTRAIT_FILE_NAME";

	public static String VERSION_CONST_APP_IMG_LANDSCAPE_FILE_NAME = "APP_IMG_LANDSCAPE_FILE_NAME";

	public static String VERSION_CONST_PRODUCT_ICON_FILE_NAME = "PRODUCT_ICON_FILE_NAME";

	public static String VERSION_CONST_VENDOR_PRODUCT_CODE = "VENDOR_PRODUCT_CODE";

	public static String VERSION_CONST_VENDOR_PRODUCT_DISPLAY = "VENDOR_PRODUCT_DISPLAY";

	public static String VERSION_CONST_VZW_RETAIL_PRICE = "VZW_RETAIL_PRICE";

	public static String VERSION_CONST_INITIAL_APPROVAL_NOTES = "INITIAL_APPROVAL_NOTES";

	public static String VERSION_CONST_BUSINESS_APPROVAL_NOTES = "BUSINESS_APPROVAL_NOTES";

	public static String VERSION_CONST_PENDING_DCR_NOTES = "PENDING_DCR_NOTES";

	public static String VERSION_CONST_VENDOR_SPLIT_PERCENTAGE = "VENDOR_SPLIT_PERCENTAGE";

	public static String VERSION_CONST_TAX_CATEGORY_CODE = "TAX_CATEGORY_CODE";

	public static String VERSION_CONST_JAVA_VERSION = "JAVA_VERSION";

	public static String VERSION_CONST_LAUNCH_DATE = "LAUNCH_DATE";

	public static String VERSION_CONST_TEST_EFFECTIVE_DATE = "TEST_EFFECTIVE_DATE";

	public static String VERSION_CONST_LICENSE_TYPES = "LICENSE_TYPES";

	public static String VERSION_CONST_USER_INFO_PARAMS = "USER_INFO_PARAMS";

	public static String VERSION_CONST_VZW_LIVE_DATE = "VZW_LIVE_DATE";

	public static String VERSION_CONST_PAGE_VIEW_RATE = "PAGE_VIEW_RATE";

	public static HashMap getValuesMapForVersion(AimsApp aimsApp,
			AimsJavaApps aimsJavaApp, String dateFormat) throws Exception {
		HashMap values = new HashMap();

		try {
			values.put(VERSION_CONST_TITLE, aimsApp.getTitle());
			values.put(VERSION_CONST_VERSION, aimsApp.getVersion());
			values.put(VERSION_CONST_SHORT_DESC, aimsApp.getShortDesc());
			values.put(VERSION_CONST_LONG_DESC, aimsApp.getLongDesc());
			values.put(VERSION_CONST_TECH_CONTACT_ID, aimsApp
					.getAimsContactId());
			values.put(VERSION_CONST_IF_PR_RELEASE, aimsApp.getIfPrRelease());
			// values.put(VERSION_CONST_SUB_CATEGORY_ID_1,
			// aimsJavaApp.getSubCategoryId1());
			// values.put(VERSION_CONST_SUB_CATEGORY_ID_2,
			// aimsJavaApp.getSubCategoryId2());
			// values.put(VERSION_CONST_SUB_CATEGORY_ID_3,
			// aimsJavaApp.getSubCategoryId3());
			// values.put(VERSION_CONST_LINK_ORDER_1,
			// aimsJavaApp.getLinkOrder1());
			// values.put(VERSION_CONST_LINK_ORDER_2,
			// aimsJavaApp.getLinkOrder2());
			// values.put(VERSION_CONST_LINK_ORDER_3,
			// aimsJavaApp.getLinkOrder3());
			values.put(VERSION_CONST_SCREEN_JPEG_FILE_NAME, aimsApp
					.getScreenJpegFileName());
			values.put(VERSION_CONST_USER_GUIDE_FILE_NAME, aimsApp
					.getUserGuideFileName());
			values.put(VERSION_CONST_FAQ_DOC_FILE_NAME, aimsApp
					.getFaqDocFileName());
			// values.put(VERSION_CONST_PRESENTATION_FILE_NAME,
			// aimsJavaApp.getPresentationFileName());
			// values.put(VERSION_CONST_PRODUCT_LOGO_FILE_NAME,
			// aimsJavaApp.getProductLogoFileName());
			// values.put(VERSION_CONST_APP_IMG_MEDIUM_FILE_NAME,
			// aimsJavaApp.getAppMediumLargeImageFileName());//medium image
			// values.put(VERSION_CONST_APP_IMG_POTRAIT_FILE_NAME,
			// aimsJavaApp.getAppQVGAPotraitImageFileName());//potrait image
			// values.put(VERSION_CONST_APP_IMG_LANDSCAPE_FILE_NAME,
			// aimsJavaApp.getAppQVGALandscapeImageFileName());//landscape image
			// values.put(VERSION_CONST_PRODUCT_ICON_FILE_NAME,
			// aimsJavaApp.getProductIconFileName());
			// values.put(VERSION_CONST_VENDOR_PRODUCT_CODE,
			// aimsJavaApp.getVendorProductCode());
			// values.put(VERSION_CONST_VENDOR_PRODUCT_DISPLAY,
			// aimsJavaApp.getVendorProductDisplay());
			// values.put(VERSION_CONST_VZW_RETAIL_PRICE,
			// aimsJavaApp.getVzwRetailPrice());
			// values.put(VERSION_CONST_INITIAL_APPROVAL_NOTES,
			// aimsJavaApp.getInitialApprovalNotes());
			// values.put(VERSION_CONST_BUSINESS_APPROVAL_NOTES,
			// aimsJavaApp.getBusinessApprovalNotes());
			// values.put(VERSION_CONST_PENDING_DCR_NOTES,
			// aimsJavaApp.getPendingDcrNotes());
			// values.put(VERSION_CONST_VENDOR_SPLIT_PERCENTAGE,
			// aimsJavaApp.getVendorSplitPercentage());
			// values.put(VERSION_CONST_TAX_CATEGORY_CODE,
			// aimsJavaApp.getTaxCategoryCodeId());
			// values.put(VERSION_CONST_JAVA_VERSION,
			// aimsJavaApp.getJavaVersion());
			// values.put(VERSION_CONST_LAUNCH_DATE,
			// Utility.convertToString(aimsJavaApp.getLaunchDate(),
			// dateFormat));
			// values.put(VERSION_CONST_TEST_EFFECTIVE_DATE,
			// Utility.convertToString(aimsJavaApp.getTestEffectiveDate(),
			// dateFormat));
			// values.put(VERSION_CONST_LICENSE_TYPES,
			// getLicenseTypesForVersion(aimsJavaApp.getLicenseTypes()));
			// values.put(VERSION_CONST_VZW_LIVE_DATE,
			// Utility.convertToString(aimsJavaApp.getVzwLiveDate(),
			// dateFormat));
			// values.put(VERSION_CONST_PAGE_VIEW_RATE,
			// aimsJavaApp.getPageViewRate());
		} catch (Exception ex) {
			throw ex;
		}

		return values;
	}

	public static String getLicenseTypesForVersion(Set licenseTypeSet) {
		Vector licenseVector = new Vector();

		String[] abc = StringFuncs.ConvertListToStringArray(licenseVector);
		Arrays.sort(abc);
		return StringFuncs.ConvertArrToString(abc, ",");
	}

	public static void prePopulateForm(JavaApplicationUpdateForm javaForm) {
		if ( log.isDebugEnabled() )
		{
			log.debug("JavaApplicationUpdateForm.prePopulateForm: Start");
			log.debug("Setting list for page: " + javaForm.getCurrentPage());
		}	
		try {
			if (StringUtils.isEmpty(javaForm.getCurrentPage())
					|| "page1".equalsIgnoreCase(javaForm.getCurrentPage())
					|| "view".equalsIgnoreCase(javaForm.getTask())) {
				javaForm.setAllCategories(AimsApplicationsManager
						.getCategories(AimsConstants.JAVA_PLATFORM_ID));
				javaForm.setAllSubCategories(AimsApplicationsManager
						.getSubCategories());

				// javaForm.setAllJavaContracts(JavaApplicationManager.getJavaContracts());
			}
			if ("page2".equalsIgnoreCase(javaForm.getCurrentPage())
					|| "view".equalsIgnoreCase(javaForm.getTask())) {
				javaForm.setAllContacts(AimsApplicationsManager
						.getContacts(javaForm.getAimsAllianceId()));
			}
		} catch (Exception ex) {
			log.debug(ex, ex);
		}
		if ( log.isDebugEnabled() )
			log.debug("JavaApplication.prePopulateForm: End");
	}

	public static String updateAction(HttpServletRequest request,
			String taskname, JavaApplicationUpdateForm javaForm,
			AimsApp aimsApp, AimsJavaApps javaApp, AimsJavaAppClob javaClobs,
			AimsContact aimsContact, String dateFormat) throws AimsException,
			AimsSecurityException {

		if ( log.isDebugEnabled() )
			log.debug("JavaApplicationHelper.updateAction: Start");

		String forward = "view";
		HttpSession session = request.getSession();
		String currUser = ((AimsUser) (session
				.getAttribute(AimsConstants.AIMS_USER))).getUsername();
		String currUserType = ((AimsUser) (session
				.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		Long currUserId = ((AimsUser) (session
				.getAttribute(AimsConstants.AIMS_USER))).getUserId();
		Long currentUserAllianceId = ((AimsUser) session
				.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		boolean isVerizonUser = currUserType.equals(AimsConstants.VZW_USERTYPE);
		boolean isAllianceUser = currUserType
				.equals(AimsConstants.ALLIANCE_USERTYPE);

		// This check access code previously was commented
		// BEGIN
		// CHECK ACCESS
		/*
		 * if (!(ApplicationHelper.checkPlatformAccess(request,
		 * javaForm.getOriginalTask(), AimsConstants.JAVA_PLATFORM_ID))) throw
		 * new AimsSecurityException();
		 * 
		 * if (taskname.equalsIgnoreCase("edit")) if
		 * (!(ApplicationHelper.checkEditAccess(currUserType,
		 * aimsApp.getAimsLifecyclePhaseId()))) throw new
		 * AimsSecurityException();
		 */
		// END OF CHECK ACCESS
		// END
		if ((taskname.equalsIgnoreCase("page1"))
				|| (taskname.equalsIgnoreCase("page2"))
				|| (taskname.equalsIgnoreCase("page3"))
				|| (taskname.equalsIgnoreCase("page4"))
				|| (taskname.equalsIgnoreCase("page5"))
				|| (taskname.equalsIgnoreCase("journal"))
				|| (taskname.equalsIgnoreCase("submitJournal"))
				|| (taskname.equalsIgnoreCase("sendZipFile"))
				|| (taskname.equalsIgnoreCase("submitpage4"))
				|| (taskname.equalsIgnoreCase("page4View"))
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
			else if (taskname.equalsIgnoreCase("page4View"))
				forward = "page4View";

			if (taskname.equalsIgnoreCase("sendZipFile"))
				javaForm.setCurrentPage("page3");
			else if (taskname.equalsIgnoreCase("submitpage4"))
				javaForm.setCurrentPage("page4");
			else if (taskname.equalsIgnoreCase("submitpage4View"))
				javaForm.setCurrentPage("page4");
			else
				javaForm.setCurrentPage(taskname);

			javaForm.setTask(javaForm.getOriginalTask());

			if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {

				// User submitted a journal entry.
				if (taskname.equalsIgnoreCase("submitpage4")
						|| taskname.equalsIgnoreCase("submitpage4View")) {
					try {
						AimsApplicationsManager.saveJournalEntry(javaForm
								.getAppsId(), javaForm.getJournalText(),
								javaForm.getJournalType(), currUser);
					} catch (Exception e) {
						log.error(e, e);
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new GenericException(
								"error.generic.persistance"));
						throw aimsException;
					}
				}

				// Journal Entry page.
				if ("page4".equals(javaForm.getCurrentPage())) {
					try {
						Collection journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request
										.getParameter("appsId")),
										currentUserAllianceId);
						javaForm.setJournalCombinedText(getFormattedJournalEntries(journalEntries));
					} catch (Exception e) {
						log.error(e, e);
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new GenericException(
								"error.generic.database"));
					}
				}
			}
		}

		if (taskname.equalsIgnoreCase("create")
				|| taskname.equalsIgnoreCase("edit")) {
			if (taskname.equalsIgnoreCase("create")) {
				aimsApp.setAimsAllianceId(currentUserAllianceId);
				aimsApp.setAimsUserAppCreatedById(currUserId);
				aimsApp.setIfOnHold("N");
				aimsApp.setCreatedBy(currUser);
				aimsApp.setCreatedDate(new Date());
			}

			aimsApp.setTitle(javaForm.getTitle());
			aimsApp.setVersion(javaForm.getVersion());
			aimsApp.setShortDesc(javaForm.getShortDesc());
			aimsApp.setLongDesc(javaForm.getLongDesc());
			aimsApp.setRingTypeId(javaForm.getRingNumber());
			javaApp.setInfoUrl(javaForm.getInfoURL());
			javaApp.setEnterpriseApp(javaForm.getEnterpriseApp());
			javaApp.setAppKeyword(javaForm.getAppKeyword());
			

			// java app contract
			if ((javaForm.getJavaAppContractId() != null) && (javaForm.getJavaAppContractId().longValue() != 0))
				javaApp.setAimsContractId(javaForm.getJavaAppContractId());
			// java app content type
			if ((javaForm.getContentType() != null) && (javaForm.getContentType().longValue() != 0))
				javaApp.setContentTypeId(javaForm.getContentType());			
			// java app content rating type
			if ((javaForm.getContentRating() != null) && (javaForm.getContentRating().longValue() != 0))
				javaApp.setContentRatingTypeId(javaForm.getContentRating());
			if ((javaForm.getAimsTaxCategoryCodeId()  != null) && (javaForm.getAimsTaxCategoryCodeId().longValue() != 0))
				javaApp.setAimsTaxCategoryCodeId(javaForm.getAimsTaxCategoryCodeId());
			
			
			if ((javaForm.getAppCategory1() != null) && (javaForm.getAppCategory1().longValue() != 0))
				javaApp.setAppCategory1(javaForm.getAppCategory1());
			
			if ((javaForm.getAppSubCategory1() != null) && (javaForm.getAppSubCategory1().longValue() != 0))
				javaApp.setAppSubCategory1(javaForm.getAppSubCategory1());
			
			if ((javaForm.getAppCategory2() != null) && (javaForm.getAppCategory2().longValue() != 0))
				javaApp.setAppCategory2(javaForm.getAppCategory2());
			
			if ((javaForm.getAppSubCategory2() != null) && (javaForm.getAppSubCategory2().longValue() != 0))
				javaApp.setAppSubCategory2(javaForm.getAppSubCategory2());
			
			if ( !StringFuncs.isNullOrEmpty(javaForm.getProjectedLiveDate()) )
				javaApp.setVzwProjectedLiveDate(Utility.convertToDate(javaForm.getProjectedLiveDate(), dateFormat));
			
			if ( !StringFuncs.isNullOrEmpty(javaForm.getEnterpriseId()) )
				javaApp.setEnterpriseId(javaForm.getEnterpriseId());	
			
			if ( !StringFuncs.isNullOrEmpty(javaForm.getInitialApprovalNotes()) )
				javaApp.setNotes(javaForm.getInitialApprovalNotes());

			// aimsApp.setAimsContractId(javaForm.getJavaAppContractId());
			aimsApp.setIfPrRelease(javaForm.getIfPrRelease());

			aimsApp.setLastUpdatedBy(currUser);
			aimsApp.setLastUpdatedDate(new Date());
			aimsApp.setAimsPlatformId(AimsConstants.JAVA_PLATFORM_ID);

			// SubCategory
			if ((javaForm.getAimsAppSubCategoryId() != null) && (javaForm.getAimsAppSubCategoryId().longValue() != 0))
				aimsApp.setAimsAppSubCategoryId(javaForm.getAimsAppSubCategoryId());

			// Contact
			aimsApp.setAimsContactId(javaForm.getAimsContactId());
			if (javaForm.getAimsContactId().longValue() == 0) {
				aimsContact.setFirstName(javaForm.getContactFirstName());
				aimsContact.setLastName(javaForm.getContactLastName());
				aimsContact.setEmailAddress(javaForm.getContactEmail());
				aimsContact.setTitle(javaForm.getContactTitle());
				aimsContact.setPhone(javaForm.getContactPhone());
				aimsContact.setMobile(javaForm.getContactMobile());
				aimsContact
						.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
				aimsContact.setCreatedBy(currUser);
				aimsContact.setCreatedDate(new Date());
				aimsContact.setLastUpdatedBy(currUser);
				aimsContact.setLastUpdatedDate(new Date());
			}

			// Start User Guide

			if (StringUtils.isNotEmpty(javaForm.getProductDescription())) {
				javaApp.setProductDescription(javaForm.getProductDescription()
						.trim());
			}

			if (StringUtils.isNotEmpty(javaForm.getUsingApplication())) {
				javaClobs.setUsingApplicationStr(javaForm.getUsingApplication()
						.trim());
			} else {
				javaClobs.setUsingApplicationStr("");
			}
			if (StringUtils.isNotEmpty(javaForm.getTipsAndTricks())) {
				javaClobs.setTipsAndTricksStr(javaForm.getTipsAndTricks()
						.trim());
			} else {
				javaClobs.setTipsAndTricksStr("");
			}

			if (StringUtils.isNotEmpty(javaForm.getFaq())) {
				javaClobs.setFaqStr(javaForm.getFaq().trim());
			} else {
				javaClobs.setFaqStr("");
			}

			if (StringUtils.isNotEmpty(javaForm.getTroubleshooting())) {
				javaClobs.setTroubleshootingStr(javaForm.getTroubleshooting()
						.trim());
			} else {
				javaClobs.setTroubleshootingStr(javaForm.getTroubleshooting());
			}
			if (StringUtils.isNotEmpty(javaForm.getDevCompanyDisclaimer())) {
				javaClobs.setDevelopmentCompanyDisclaimerStr(javaForm
						.getDevCompanyDisclaimer().trim());
			} else {
				javaClobs.setDevelopmentCompanyDisclaimerStr("");
			}
			if (StringUtils.isNotEmpty(javaForm.getAdditionalInformation())) {
				javaClobs.setAdditionalInformationStr(javaForm
						.getAdditionalInformation().trim());
			} else {
				javaClobs.setAdditionalInformationStr("");
			}

			// End User Guide

			if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
				// Start of check to see if alliance has been accepted
				Object[] userValues = null;
				String allianceStatus = null;
				Collection AimsAlliance = null;

				try {
					AimsAlliance = AllianceManager.getAllianceDetails(aimsApp
							.getAimsAllianceId(), "");
				} catch (Exception ex) {
					AimsException aimsException = new AimsException("Error");
					aimsException.addException(new RecordNotFoundException(
							"error.application.alliance.not.accepted"));
					throw aimsException;
				}

				for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();) {
					userValues = (Object[]) iter.next();
					allianceStatus = (String) userValues[3];
				}

				if (!(javaForm.getAppSubmitType()
						.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))) {
					if (!(allianceStatus
							.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED))) {
						AimsException aimsException = new AimsException("Error");
						aimsException.addException(new RecordNotFoundException(
								"error.application.alliance.not.accepted"));
						throw aimsException;
					}
				}
				// End of check to see if alliance has been accepted

				// check alliance has accepted atleast one active dashboard
				// contract
				/*
				 * if
				 * (dashboardForm.getAppSubmitType().equalsIgnoreCase(AimsConstants
				 * .AIMS_SUBMIT_FORM)) { try {
				 * if(!ApplicationHelper.validateAllianceContract
				 * (currentUserAllianceId, AimsConstants.DASHBOARD_PLATFORM_ID,
				 * currUserType)) { AimsException aimsException = new
				 * AimsException("Error"); aimsException.addException(new
				 * RecordNotFoundException
				 * ("error.dashboard.app.contract.acceptance")); throw
				 * aimsException; } } catch (HibernateException e) {
				 * AimsException aimsException = new AimsException("Error");
				 * aimsException.addException(newRecordNotFoundException(
				 * "error.dashboard.app.contract.acceptance.exception")); throw
				 * aimsException; } }
				 */

			}

			/*
			 * //Set VZW Specific Information if (isVerizonUser) { if
			 * (ApplicationHelper.checkAccess(request, taskname,
			 * AimsPrivilegesConstants.DASHBOARD_INITIAL_APPROVAL)) {
			 * dashboardApp
			 * .setInitialApprovalNotes(dashboardForm.getInitialApprovalNotes
			 * ());dashboardApp.setInitialApprovalAction(dashboardForm.
			 * getInitialApprovalAction()); }
			 * 
			 * if (ApplicationHelper.checkAccess(request, taskname,
			 * AimsPrivilegesConstants.DASHBOARD_CONTENT_ZIP_FILE)) { if
			 * (StringUtils.isNotEmpty(dashboardForm.getChannelId()) &&
			 * StringUtils.isNumeric(dashboardForm.getChannelId())){
			 * dashboardChannelId.setChannelId(new
			 * Long(dashboardForm.getChannelId())); } else {
			 * dashboardChannelId.setChannelId(null); }
			 * dashboardApp.setContentZipNotes
			 * (dashboardForm.getContentZipFileNotes());
			 * dashboardApp.setContentZipApprovalAction
			 * (dashboardForm.getContentZipFileAction());
			 * dashboardApp.setContentZipFileLocked
			 * (dashboardForm.getIsContentZipFileLock()); }
			 * 
			 * if (ApplicationHelper.checkAccess(request, taskname,
			 * AimsPrivilegesConstants.DASHBOARD_TRACKING)) {
			 * dashboardApp.setMoveToProduction
			 * (dashboardForm.getMoveToProduction());
			 * dashboardApp.setRemove(dashboardForm.getRemove()); } }
			 */
		}

		JavaApplicationHelper.prePopulateForm(javaForm);
		if ( log.isDebugEnabled() )
			log.debug("JavaApplicationHelper.updateAction: End");
		return forward;
	}

	private static String getFormattedJournalEntries(Collection journalEntries) {
		StringBuffer journalCombinedText = new StringBuffer();

		if (journalEntries != null) {
			for (Iterator it = journalEntries.iterator(); it.hasNext();) {
				AimsJournalEntry journalEntry = (AimsJournalEntry) it.next();
				if (journalEntry.getJournalType().equalsIgnoreCase(
						AimsConstants.JOURNAL_TYPE_PRIVATE))
					journalCombinedText.append("[Private] ");
				else if (journalEntry.getJournalType().equalsIgnoreCase(
						AimsConstants.JOURNAL_TYPE_PUBLIC))
					journalCombinedText.append("[Public] ");
				journalCombinedText.append(Utility.convertToString(journalEntry
						.getCreatedDate(), AimsConstants.DATE_TIME_FORMAT));
				journalCombinedText.append(" : (");
				journalCombinedText.append(journalEntry.getCreatedBy());
				journalCombinedText.append(")\n");
				journalCombinedText.append(journalEntry.getJournalText());
				journalCombinedText.append("\n\n");
			}
		}
		return journalCombinedText.toString();
	}

	public static void setupAction(HttpServletRequest request, String taskname,
			String viewPageToView, JavaApplicationUpdateForm javaForm,
			AimsApp aimsApp, AimsJavaApps javaApp, AimsJavaAppClob javaClobs,
			AimsAppCategory aimsAppCategory, String dateFormat)
			throws AimsSecurityException {

		if ( log.isDebugEnabled() )
		{
			log.debug("JavaApplicationHelper.setupAction Start:");
			log.debug("taskName: " + taskname);
		}

		HttpSession session = request.getSession();
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		boolean isVerizonUser = currUserType.equals(AimsConstants.VZW_USERTYPE);
		boolean isAllianceUser = currUserType.equals(AimsConstants.ALLIANCE_USERTYPE);

		// CHECK ACCESS
		/*
		 * if (!(ApplicationHelper.checkPlatformAccess(request,
		 * taskname,AimsConstants.JAVA_PLATFORM_ID))) throw new
		 * AimsSecurityException();
		 * 
		 * if (taskname.equalsIgnoreCase("edit")) if
		 * (!(ApplicationHelper.checkEditAccess(currUserType,
		 * aimsApp.getAimsLifecyclePhaseId()))) throw new
		 * AimsSecurityException();
		 * 
		 * if (taskname.equalsIgnoreCase("delete")) if
		 * (!AimsSecurityManager.checkAccess
		 * (request,AimsPrivilegesConstants.MANAGE_JAVA_APPS
		 * ,AimsSecurityManager.DELETE)) throw new AimsSecurityException();
		 */
		// END OF CHECK ACCESS
		javaForm.setCurrentPage("page1");
		javaForm.setOriginalTask(javaForm.getTask());

		// Set Temp File Ids to Zero
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
		javaForm.setEnterpriseApp(AimsConstants.NO_CHAR);

		if (taskname.equalsIgnoreCase("create")) 
		{
			javaForm.setTask("create");
			javaForm.setAppsId(new Long(0));
			javaForm.setLanguage("EN");
			javaForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
			javaForm.setAimsAllianceId(currentUserAllianceId);
		} 
		else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view"))) 
		{
			if (taskname.equalsIgnoreCase("edit"))
				javaForm.setTask("edit");
			else if (taskname.equalsIgnoreCase("view"))
				javaForm.setTask("view");

			// For Cloning....
			if (taskname.equalsIgnoreCase("clone")) 
			{
				javaForm.setAppsId(null);
				javaForm.setCloneFromAppId(aimsApp.getAppsId());
				javaForm.setTitle(null);
				javaForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
				javaForm.setAimsAllianceId(currentUserAllianceId);
				javaForm.setTask("create");
				javaForm.setOriginalTask("create");
				if ( javaForm.getRing3App() )
					javaForm.setAppKeyword( JavaUtils.getAppKeyword()  );				
			} 
			else 
			{
				javaForm.setTitle(javaApp.getTitle());
				javaForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
				javaForm.setAimsAllianceId(aimsApp.getAimsAllianceId());
				javaForm.setSubmittedDate(Utility.convertToString(aimsApp.getSubmittedDate(), dateFormat));
				javaForm.setAppsId(aimsApp.getAppsId());
				javaForm.setAppKeyword(javaApp.getAppKeyword());
				javaForm.setVersion(aimsApp.getVersion());
			}

			if (taskname.equalsIgnoreCase("view") && "journal".equalsIgnoreCase(viewPageToView)) 
			{
				try 
				{
					Collection journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")),currentUserAllianceId);
					
					journalEntries = getJournalEntriesForUser(isAllianceUser,journalEntries);

					javaForm.setJournalCombinedText(getFormattedJournalEntries(journalEntries));
				} 
				catch (Exception e) 
				{
					log.error(e, e);
					AimsException aimsException = new AimsException("Error");
					aimsException.addException(new GenericException("error.generic.database"));
				}
			}

			javaForm.setShortDesc(javaApp.getShortDesc());
			javaForm.setLongDesc(javaApp.getLongDesc());
			javaForm.setInfoURL(javaApp.getInfoUrl());
			javaForm.setProductDescription(javaApp.getProductDescription());

			javaForm.setLanguage(aimsApp.getLanguage());
			javaForm.setJavaAppContractId(javaApp.getAimsContractId());
			javaForm.setContentRating(javaApp.getContentRatingTypeId());
			javaForm.setAimsTaxCategoryCodeId(javaApp.getAimsTaxCategoryCodeId());

			javaForm.setIfPrRelease(aimsApp.getIfPrRelease());
			javaForm.setEnterpriseApp(javaApp.getEnterpriseApp());

			if (aimsAppCategory != null)
				javaForm.setAimsAppCategoryId(aimsAppCategory.getCategoryId());
			
			
			javaForm.setAimsAppSubCategoryId(aimsApp.getAimsAppSubCategoryId());
			
			javaForm.setAppCategory1(javaApp.getAppCategory1());
			javaForm.setAppSubCategory1(javaApp.getAppSubCategory1());

			javaForm.setAppCategory2(javaApp.getAppCategory2());
			javaForm.setAppSubCategory2(javaApp.getAppSubCategory2());
			
			javaForm.setContentType(javaApp.getContentTypeId());
			
			if ( javaApp.getVzwProjectedLiveDate()!=null )
				javaForm.setProjectedLiveDate(Utility.convertToString(javaApp.getVzwProjectedLiveDate(), dateFormat));			
			
			javaForm.setEnterpriseId(javaApp.getEnterpriseId());
			javaForm.setInitialApprovalNotes(javaApp.getNotes());

			// Set File Names
			javaForm.setClrPubLogoFileName(javaApp.getHrPublisherFileName());
			javaForm.setAppTitleNameFileName(javaApp.getChnlTitleIconFileName());
			javaForm.setSplashScreenEpsFileName(aimsApp.getSplashScreenEpsFileName());
			javaForm.setActiveScreenEpsFileName(aimsApp.getActiveScreenEpsFileName());
			javaForm.setScreenJpegFileName(aimsApp.getScreenJpegFileName());
			javaForm.setScreenJpeg2FileName(aimsApp.getScreenJpeg2FileName());
			javaForm.setScreenJpeg3FileName(aimsApp.getScreenJpeg3FileName());
			javaForm.setScreenJpeg4FileName(aimsApp.getScreenJpeg4FileName());
			javaForm.setScreenJpeg5FileName(aimsApp.getScreenJpeg5FileName());
			javaForm.setFaqDocFileName(aimsApp.getFaqDocFileName());
			javaForm.setUserGuideFileName(aimsApp.getUserGuideFileName());
			javaForm.setCompanyLogoFileName(javaApp.getCompanyFileName());
			javaForm.setTitleImageFileName(javaApp.getAppTitleNameFileName() );

			javaForm.setAimsContactId(aimsApp.getAimsContactId());
			// set user guide
			javaForm.setProductDescription(javaApp.getProductDescription());
			javaForm.setUsingApplication(javaClobs.getUsingApplicationStr());
			javaForm.setTipsAndTricks(javaClobs.getTipsAndTricksStr());
			javaForm.setFaq(javaClobs.getFaqStr());
			javaForm.setTroubleshooting(javaClobs.getTroubleshootingStr());
			javaForm.setDevCompanyDisclaimer(javaClobs.getDevelopmentCompanyDisclaimerStr());
			javaForm.setAdditionalInformation(javaClobs.getAdditionalInformationStr());

		}// End of taskName=edit,view check

		if (javaForm.getAppsId() == null
				|| javaForm.getAppsId().longValue() == 0
				|| taskname.equalsIgnoreCase("clone")) {
			javaForm.setApplicationStatus("NEW");
		} else {
			if (javaForm.getAimsLifecyclePhaseId() != null
					&& javaForm.getAimsLifecyclePhaseId().longValue() > 0) {
				try {
					AimsLifecyclePhase aimsPhaseOfApplication = (AimsLifecyclePhase) DBHelper
							.getInstance().load(
									AimsLifecyclePhase.class,
									javaForm.getAimsLifecyclePhaseId()
											.toString());

					javaForm.setApplicationStatus(aimsPhaseOfApplication
							.getPhaseName());
				} catch (HibernateException e) {
					log.error(e, e);
				}
			}
		}

		if (javaForm.getAimsAllianceId() != null
				&& javaForm.getAimsAllianceId().longValue() > 0) {
			try {
				
		        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, javaForm.getAimsAllianceId().toString());
		        javaForm.setAllianceName(aimsAllianceOfApplication.getCompanyName() );
		        javaForm.setVendorId(aimsAllianceOfApplication.getVendorId());
				
			} catch (HibernateException e) {
				log.error(e, e);
			}
		}

		JavaApplicationHelper.prePopulateForm(javaForm);
		if ( log.isDebugEnabled() )
			log.debug("javaApplicationHelper.setupAction End:");
	}

	/** TO get journal entry according to the user type
	 * @param isAllianceUser
	 * @param journalEntries
	 * @return
	 */
	
	private static Collection getJournalEntriesForUser(boolean isAllianceUser,
			Collection journalEntries) {

		if (isAllianceUser) {
			Collection journalEntriesToShow = new ArrayList();
			AimsJournalEntry journalEntry;

			for (Iterator iterator = journalEntries.iterator(); iterator
					.hasNext();) {
				journalEntry = (AimsJournalEntry) iterator.next();
				if (journalEntry.getJournalType().equals(
						AimsConstants.JOURNAL_TYPE_PUBLIC)) {
					journalEntriesToShow.add(journalEntry);
				}
			}

			journalEntries = journalEntriesToShow;
		}
		return journalEntries;
	}

	
	/** to get work flow history according to the user type
	 * 
	 */
	
	public static List<AimsWorkitem> getWorkflowHistory(Long appId, String currentUserType)
			throws HibernateException {

		List<AimsWorkitem> completeHistory = WorkflowManager.getHistory(appId);

		if (currentUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) 
		{
			List<AimsWorkitem> historyToShow = new ArrayList<AimsWorkitem>();
			
			for ( AimsWorkitem workitem : completeHistory )
			{
				if ( AimsConstants.WORKFLOW_TAKEN_ACTION_REQUEST_FOR_INFORMATION.equals(workitem.getActionTaken()) || AimsConstants.WORKFLOW_TAKEN_ACTION_RESUBMIT.equals(workitem.getActionTaken()) ) 
					historyToShow.add(workitem);				
			}
			
			return historyToShow;
		} 
		else 
		{
			List<AimsWorkitem> historyToShow = new ArrayList<AimsWorkitem>();			
			
			for ( AimsWorkitem workitem : completeHistory )
			{
				if ( !AimsConstants.WORKFLOW_STEPNAME_APPROVED.equals(workitem.getStepName()) &&  !AimsConstants.WORKFLOW_STEPNAME_DENIED.equals(workitem.getStepName()) ) 
					historyToShow.add(workitem);					
			}
			return historyToShow;			
		}
	}
}