package com.netpace.aims.controller.application;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.rest.RestManager;
import com.netpace.aims.bo.webservices.InfoSpaceManager;
import com.netpace.aims.controller.webservices.InfospaceUtils;
import com.netpace.aims.controller.webservices.SubmitDocumentResponse;
import com.netpace.aims.controller.webservices.VZWHandlerStub;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.masters.AimsTaxCategoryCode;
import com.netpace.aims.util.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class containts helper functions for modules related to Managing Applications
 *                                           
 * @author Adnan Makda
 */

public class WapApplicationHelper
{

    static Logger log = Logger.getLogger(WapApplicationHelper.class.getName());

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
    public static String VERSION_CONST_WAP_VERSION = "WAP_VERSION";
    public static String VERSION_CONST_LAUNCH_DATE = "LAUNCH_DATE";
    public static String VERSION_CONST_TEST_EFFECTIVE_DATE = "TEST_EFFECTIVE_DATE";
    public static String VERSION_CONST_LICENSE_TYPES = "LICENSE_TYPES";
    public static String VERSION_CONST_USER_INFO_PARAMS = "USER_INFO_PARAMS";
    public static String VERSION_CONST_VZW_LIVE_DATE = "VZW_LIVE_DATE";
    public static String VERSION_CONST_PAGE_VIEW_RATE = "PAGE_VIEW_RATE";

    public static boolean checkEditAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_PASSED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_FAILED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PUBLICATION_READY_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue()))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    public static boolean checkDeleteAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_INITIAL_APPROVAL_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_INITIAL_DENIED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_ARM_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_PASSED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_FAILED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PUBLICATION_READY_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue()))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    /*
     * This method restricts the LifeCycle Phases that an Alliance User can see.
     */
    public static Long getFilteredApplicationStatus(Long aimsLifecyclePhaseId, String currUserType)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_INITIAL_APPROVAL_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PENDING_ARM_ID.longValue()))
                return AimsConstants.SUBMISSION_ID;
            else if (
                (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_PASSED_ID.longValue())
                    || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_FAILED_ID.longValue())
                    || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_PUBLICATION_READY_ID.longValue()))
                return AimsConstants.PHASE_SUBMITTED_DCR_ID;
            else
                return aimsLifecyclePhaseId;
        }
        else
        {
            return aimsLifecyclePhaseId;
        }
    }

    /*
     * This method along with  getFormattedJournalEntry(AimsJournalEntry journalEntry), gets
     * a list of formatted Journal Entries
     */
    public static String getFormattedJournalEntries(Collection journalEntries)
    {
        StringBuffer journalCombinedText = new StringBuffer();

        if (journalEntries != null)
        {
            for (Iterator it = journalEntries.iterator(); it.hasNext();)
            {
                journalCombinedText.append(getFormattedJournalEntry((AimsJournalEntry) it.next()));
            }
        }
        return journalCombinedText.toString();
    }

    /*
     * This method formats a single AimsJournalEntry
     */
    public static String getFormattedJournalEntry(AimsJournalEntry journalEntry)
    {
        StringBuffer journalCombinedText = new StringBuffer();

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
        return journalCombinedText.toString();
    }

    /*
     * This method gets all the Wap Versions (generated in the 'Pending DCR' status)
     */
    public static Double[] getWapVersionIds(Long wapAppsId) throws Exception
    {
        Double[] doubleIds = null;
        try
        {
            Collection wapAppVersions = WapApplicationManager.getWapAppVersions(wapAppsId);
            if (wapAppVersions != null)
            {
                Vector wapAppOldVersionIds = new Vector();
                doubleIds = new Double[wapAppVersions.size()];
                for (Iterator it = wapAppVersions.iterator(); it.hasNext();)
                {
                    wapAppOldVersionIds.add(new Double((String) it.next()));
                }
                doubleIds = (Double[]) wapAppOldVersionIds.toArray(doubleIds);
                Arrays.sort(doubleIds);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return doubleIds;
    }

    /*
     * This method gets all the values required to make a comparision between: 
     * 1) Different Versions (Generated in 'PENDING DCR' Status) 
     * 2) 'Cloned' and 'Cloned From' Application
     */
    public static HashMap getValuesMapForVersion(AimsApp aimsApp, AimsWapApp aimsWapApp, String dateFormat) throws Exception
    {
        HashMap values = new HashMap();

        try
        {
            values.put(VERSION_CONST_TITLE, aimsApp.getTitle());
            values.put(VERSION_CONST_VERSION, aimsApp.getVersion());
            values.put(VERSION_CONST_SHORT_DESC, aimsApp.getShortDesc());
            values.put(VERSION_CONST_LONG_DESC, aimsApp.getLongDesc());
            values.put(VERSION_CONST_LONG_PRODUCT_NAME, aimsWapApp.getLongProductName());
            values.put(VERSION_CONST_DESC_CONTENT_OFFERING, aimsWapApp.getDescContentOffering());
            values.put(VERSION_CONST_CONTENT_TYPE, aimsWapApp.getContentType());
            values.put(VERSION_CONST_DEMO_URL, aimsWapApp.getDemoUrl());
            values.put(VERSION_CONST_TEST_URL, aimsWapApp.getTestUrl());
            values.put(VERSION_CONST_PRODUCTION_URL, aimsWapApp.getProductionUrl());
            values.put(VERSION_CONST_WEBSITE_URL, aimsWapApp.getWebsiteUrl());
            values.put(VERSION_CONST_TECH_CONTACT_ID, aimsApp.getAimsContactId());
            values.put(VERSION_CONST_IF_PR_RELEASE, aimsApp.getIfPrRelease());
            values.put(VERSION_CONST_SUB_CATEGORY_ID_1, aimsWapApp.getSubCategoryId1());
            values.put(VERSION_CONST_SUB_CATEGORY_ID_2, aimsWapApp.getSubCategoryId2());
            values.put(VERSION_CONST_SUB_CATEGORY_ID_3, aimsWapApp.getSubCategoryId3());
            values.put(VERSION_CONST_LINK_ORDER_1, aimsWapApp.getLinkOrder1());
            values.put(VERSION_CONST_LINK_ORDER_2, aimsWapApp.getLinkOrder2());
            values.put(VERSION_CONST_LINK_ORDER_3, aimsWapApp.getLinkOrder3());
            values.put(VERSION_CONST_SCREEN_JPEG_FILE_NAME, aimsApp.getScreenJpegFileName());
            values.put(VERSION_CONST_USER_GUIDE_FILE_NAME, aimsApp.getUserGuideFileName());
            values.put(VERSION_CONST_FAQ_DOC_FILE_NAME, aimsApp.getFaqDocFileName());
            values.put(VERSION_CONST_PRESENTATION_FILE_NAME, aimsWapApp.getPresentationFileName());
            values.put(VERSION_CONST_PRODUCT_LOGO_FILE_NAME, aimsWapApp.getProductLogoFileName());
            values.put(VERSION_CONST_APP_IMG_MEDIUM_FILE_NAME, aimsWapApp.getAppMediumLargeImageFileName());//medium image
            values.put(VERSION_CONST_APP_IMG_POTRAIT_FILE_NAME, aimsWapApp.getAppQVGAPotraitImageFileName());//potrait image
            values.put(VERSION_CONST_APP_IMG_LANDSCAPE_FILE_NAME, aimsWapApp.getAppQVGALandscapeImageFileName());//landscape image
            values.put(VERSION_CONST_PRODUCT_ICON_FILE_NAME, aimsWapApp.getProductIconFileName());
            values.put(VERSION_CONST_VENDOR_PRODUCT_CODE, aimsWapApp.getVendorProductCode());
            values.put(VERSION_CONST_VENDOR_PRODUCT_DISPLAY, aimsWapApp.getVendorProductDisplay());
            values.put(VERSION_CONST_VZW_RETAIL_PRICE, aimsWapApp.getVzwRetailPrice());
            values.put(VERSION_CONST_INITIAL_APPROVAL_NOTES, aimsWapApp.getInitialApprovalNotes());
            values.put(VERSION_CONST_BUSINESS_APPROVAL_NOTES, aimsWapApp.getBusinessApprovalNotes());
            values.put(VERSION_CONST_PENDING_DCR_NOTES, aimsWapApp.getPendingDcrNotes());
            values.put(VERSION_CONST_VENDOR_SPLIT_PERCENTAGE, aimsWapApp.getVendorSplitPercentage());
            values.put(VERSION_CONST_TAX_CATEGORY_CODE, aimsWapApp.getTaxCategoryCodeId());
            values.put(VERSION_CONST_WAP_VERSION, aimsWapApp.getWapVersion());
            values.put(VERSION_CONST_LAUNCH_DATE, Utility.convertToString(aimsWapApp.getLaunchDate(), dateFormat));
            values.put(VERSION_CONST_TEST_EFFECTIVE_DATE, Utility.convertToString(aimsWapApp.getTestEffectiveDate(), dateFormat));
            values.put(VERSION_CONST_LICENSE_TYPES, getLicenseTypesForVersion(aimsWapApp.getLicenseTypes()));
            values.put(VERSION_CONST_VZW_LIVE_DATE, Utility.convertToString(aimsWapApp.getVzwLiveDate(), dateFormat));
            values.put(VERSION_CONST_PAGE_VIEW_RATE, aimsWapApp.getPageViewRate());
        }
        catch (Exception ex)
        {
            throw ex;
        }

        return values;
    }

    /*
     * This method contains a list of all Lifecycle Phase IDs that are above 'Submitted DCR' Business  
     * Logic Wise.  
     */
    public static boolean isStatusSubmittedDCRAndAbove(Long lifecyclePhaseId)
    {
        boolean returnValue = false;

        if (lifecyclePhaseId != null)
            if ((lifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_PASSED_ID.longValue())
                || (lifecyclePhaseId.longValue() == AimsConstants.PHASE_TESTING_FAILED_ID.longValue())
                || (lifecyclePhaseId.longValue() == AimsConstants.PHASE_PUBLICATION_READY_ID.longValue())
                || (lifecyclePhaseId.longValue() == AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.longValue())
                || (lifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue()))
                returnValue = true;

        return returnValue;
    }

    /*
     * This method constructs the Journal Entry for updated Test
     */
    public static String constructJournalEntryForUpdatedTest(AimsAppPhase aap)
    {
        StringBuffer strValue = new StringBuffer();
        strValue.append("Test: '");
        strValue.append(aap.getAimsVzwTestingPhase().getTestingPhaseName());
        strValue.append("' updated by '");
        strValue.append(aap.getLastUpdatedBy());
        if (aap.getStatus() != null)
        {
            strValue.append("' with status '");
            if (aap.getStatus().equals(AimsConstants.WAP_APP_RADIO_TEST_PASSED[0]))
                strValue.append(AimsConstants.WAP_APP_RADIO_TEST_PASSED[1]);
            else if (aap.getStatus().equals(AimsConstants.WAP_APP_RADIO_TEST_FAILED[0]))
                strValue.append(AimsConstants.WAP_APP_RADIO_TEST_FAILED[1]);
        }
        strValue.append("'\n");
        return strValue.toString();
    }

    /*
     * END END END
     * 
     */

    public static ActionMessages getMessagesOnUpdate(String appSubmitType)
    {
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;

        if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            message = new ActionMessage("message.manage.wap.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            message = new ActionMessage("message.manage.app.saved");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM))
            message = new ActionMessage("message.manage.app.processed");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            message = new ActionMessage("message.manage.wap.app.submitted");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            message = new ActionMessage("message.manage.app.accepted");
        else if (appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
            message = new ActionMessage("message.manage.app.rejected");

        messages.add(ActionMessages.GLOBAL_MESSAGE, message);

        return messages;
    }

    public static String[] getWapAppVersionIds(Collection wapAppVersions)
    {
        String[] returnValue = null;
        if (wapAppVersions != null)
        {
            Vector wapAppOldVersionIds = new Vector();
            for (Iterator it = wapAppVersions.iterator(); it.hasNext();)
            {
                wapAppOldVersionIds.add((String) it.next());
            }
            returnValue = StringFuncs.ConvertListToStringArray(wapAppOldVersionIds);
        }
        return returnValue;
    }

    public static String getLicenseTypesForVersion(Set licenseTypeSet)
    {
        AimsWapLicenseType aimsWapLicenseType = null;
        Vector licenseVector = new Vector();

        for (Iterator it = licenseTypeSet.iterator(); it.hasNext();)
        {
            aimsWapLicenseType = (AimsWapLicenseType) it.next();
            licenseVector.add(aimsWapLicenseType.getLicenseTypeId().toString());
        }

        String[] abc = StringFuncs.ConvertListToStringArray(licenseVector);
        Arrays.sort(abc);
        return StringFuncs.ConvertArrToString(abc, ",");
    }


    public static WapAppVersionBean getWapAppVersionBean(AimsWapAppsVersion aimsWapAppsVersion, MessageResources msgRes)
    {
        WapAppVersionBean wapAppVersionBean = new WapAppVersionBean();
        String oldValue = StringFuncs.NullValueReplacement(aimsWapAppsVersion.getOldValue());
        String newValue = StringFuncs.NullValueReplacement(aimsWapAppsVersion.getNewValue());
        wapAppVersionBean.setOldValue(oldValue);
        wapAppVersionBean.setNewValue(newValue);

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_TITLE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ShortProductName"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_VERSION))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.Version"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_SHORT_DESC))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ShortDescription"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LONG_DESC))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.LongDescription"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LONG_PRODUCT_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.LongProductName"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_DESC_CONTENT_OFFERING))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.DescriptionofContentOffering"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_DEMO_URL))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.WAPDemoURL"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_TEST_URL))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.TestURL"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_PRODUCTION_URL))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ProductionURL"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_WEBSITE_URL))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.WebsiteURL"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_IF_PR_RELEASE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.MarketingRelease"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LINK_ORDER_1))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.LinkOrder"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LINK_ORDER_2))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.LinkOrder"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LINK_ORDER_3))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.LinkOrder"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_SCREEN_JPEG_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ScreenShot"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_USER_GUIDE_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.UserGuide"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_FAQ_DOC_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.FAQ"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_PRESENTATION_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.Presentation"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_PRODUCT_LOGO_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ProductLogoForPC"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_APP_IMG_MEDIUM_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.appMediumLargeImage"));//medium image

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_APP_IMG_POTRAIT_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.appQVGAPotraitImage"));//potrait image

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_APP_IMG_LANDSCAPE_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.appQVGALandscapeImage"));//landscape image

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_PRODUCT_ICON_FILE_NAME))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ProductIconForDevice"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_VENDOR_PRODUCT_CODE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.VendorProductCode"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_VENDOR_PRODUCT_DISPLAY))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.VendorProductDisplay"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_VZW_RETAIL_PRICE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.VZWSuggestedRetailPrice"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_INITIAL_APPROVAL_NOTES))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label2.InitialApprovalDenialNotes"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_BUSINESS_APPROVAL_NOTES))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label2.InitialBusinessApprovalDenialNotes"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_PENDING_DCR_NOTES))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label2.PendingDCRNotes"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_VENDOR_SPLIT_PERCENTAGE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.VendorSplitPercentage"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_WAP_VERSION))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.WAPVersion"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LAUNCH_DATE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ProductLiveDate"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_TEST_EFFECTIVE_DATE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.TestingEffectiveDate"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_VZW_LIVE_DATE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.VzwLiveDate"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_PAGE_VIEW_RATE))
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.PageViewRate"));

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_TAX_CATEGORY_CODE))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.TaxCategoryCode"));
            wapAppVersionBean.setOldValue(getTaxCategoryCodeDesc(oldValue));
            wapAppVersionBean.setNewValue(getTaxCategoryCodeDesc(newValue));
        }

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_SUB_CATEGORY_ID_1))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ApplicationSubcategory"));
            wapAppVersionBean.setOldValue(getCategorySubCategoryName(oldValue));
            wapAppVersionBean.setNewValue(getCategorySubCategoryName(newValue));
        }

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_SUB_CATEGORY_ID_2))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ApplicationSubcategory"));
            wapAppVersionBean.setOldValue(getCategorySubCategoryName(oldValue));
            wapAppVersionBean.setNewValue(getCategorySubCategoryName(newValue));
        }

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_SUB_CATEGORY_ID_3))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ApplicationSubcategory"));
            wapAppVersionBean.setOldValue(getCategorySubCategoryName(oldValue));
            wapAppVersionBean.setNewValue(getCategorySubCategoryName(newValue));
        }

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_TECH_CONTACT_ID))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.24X7TechnicalContact"));
            wapAppVersionBean.setOldValue(getContactName(oldValue));
            wapAppVersionBean.setNewValue(getContactName(newValue));
        }

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_CONTENT_TYPE))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.ContentType"));
            if (oldValue.equals(AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]))
                wapAppVersionBean.setOldValue(AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]);
            else
                wapAppVersionBean.setOldValue(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]);

            if (newValue.equals(AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]))
                wapAppVersionBean.setNewValue(AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]);
            else
                wapAppVersionBean.setNewValue(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]);
        }

        if (aimsWapAppsVersion.getFieldName().equals(WapApplicationHelper.VERSION_CONST_LICENSE_TYPES))
        {
            wapAppVersionBean.setFieldName(msgRes.getMessage("WapApplicationForm.label.LicenseType"));
            wapAppVersionBean.setOldValue(getLicenseTypesForVersion(oldValue));
            wapAppVersionBean.setNewValue(getLicenseTypesForVersion(newValue));
        }
        

        return wapAppVersionBean;
    }

    public static String getTaxCategoryCodeDesc(String taxCategoryCodeId)
    {
        String returnValue = "";
        try
        {
            AimsTaxCategoryCode taxCategoryCode = (AimsTaxCategoryCode) DBHelper.getInstance().load(AimsTaxCategoryCode.class, taxCategoryCodeId);
            returnValue = taxCategoryCode.getTaxCategoryCode() + " - " + taxCategoryCode.getTaxCategoryCodeDesc();
        }
        catch (HibernateException hEx)
        {
            //Handle exception
        }
        catch (Exception ex)
        {
            //Handle exception
        }

        return returnValue;
    }

    public static String getCategorySubCategoryName(String subCategoryId)
    {
        String returnValue = "";
        try
        {
            AimsAppSubCategory subCategory = (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, subCategoryId);
            AimsAppCategory category = (AimsAppCategory) DBHelper.getInstance().load(AimsAppCategory.class, subCategory.getAimsAppCategoryId().toString());
            returnValue = category.getCategoryName() + " -> " + subCategory.getSubCategoryName();
        }
        catch (HibernateException hEx)
        {
            //Handle exception
        }
        catch (Exception ex)
        {
            //Handle exception
        }

        return returnValue;
    }

    public static String getContactName(String contactId)
    {
        String returnValue = "";
        try
        {
            AimsContact aimsContact = (AimsContact) DBHelper.getInstance().load(AimsContact.class, contactId);
            returnValue = aimsContact.getFirstName() + " " + aimsContact.getLastName();
        }
        catch (HibernateException hEx)
        {
            //Handle exception
        }
        catch (Exception ex)
        {
            //Handle exception
        }

        return returnValue;
    }

    public static String getLicenseTypesForVersion(String licenseTypeIds)
    {
        AimsWapLicenseType aimsWapLicenseType = null;
        Vector licenseVector = StringFuncs.ConvertArrToVector(StringFuncs.tokenize(licenseTypeIds, ","));
        Vector licenseNameVector = new Vector();

        for (Iterator it = licenseVector.iterator(); it.hasNext();)
        {
            try
            {
                aimsWapLicenseType = (AimsWapLicenseType) DBHelper.getInstance().load(AimsWapLicenseType.class, (String) it.next());
                licenseNameVector.add(aimsWapLicenseType.getLicenseTypeName());
            }
            catch (HibernateException hEx)
            {
                //Handle exception
            }
            catch (Exception ex)
            {
                //Handle exception
            }
        }
        String[] abc = StringFuncs.ConvertListToStringArray(licenseNameVector);
        Arrays.sort(abc);
        return StringFuncs.ConvertArrToString(abc, ", ");
    }
   

    public static String getPhaseNameForWapApps(Long aimsLifecyclePhaseId, String currUserType)
    {
        String returnValue = "";
        try
        {
            String newPhaseId = getFilteredApplicationStatus(aimsLifecyclePhaseId, currUserType).toString();
            returnValue = ((AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, newPhaseId)).getPhaseName();
        }
        catch (HibernateException hEx)
        {
            //Handle exception
        }
        catch (Exception ex)
        {
            //Handle exception
        }
        return returnValue;
    }

    public static boolean sendDCRToInfoSpace(AimsApp aimsApp, AimsWapApp aimsWapApp, Long aimsLifecyclePhaseId, String theRealXSDPath, String hostName)
    {
        boolean success = true;
        WapInfoSpaceSubmitDCRBean submitDCRBean = new WapInfoSpaceSubmitDCRBean();
        StringBuffer xmlSubmitted = new StringBuffer();
        AimsEventObject aimsEventObject = null;
        AimsAllianc aimsAllianceOfApplication = null;
        AimsEventLite aimsEvent = null;
        Object[] objHostName = { hostName };
        SubmitDocumentResponse res = null;

        try
        {
            //If the DCR has been submitted successfully before, skip the entire function.
            if (!InfoSpaceManager.hasAlreadyBeenSubmittedSuccessfully(aimsApp.getAppsId(), aimsLifecyclePhaseId.toString()))
            {
                ArrayList userProfile = new ArrayList();
                ArrayList initialPortalPlacementCategory = new ArrayList();
                ArrayList initialPortalPlacementSubCategory = new ArrayList();
                ArrayList initialPortalPlacementPosition = new ArrayList();

                AimsUser aimsUser = null;
                AimsContact aimsContact = null;
                aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

                //Setting Action
                if (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                    submitDCRBean.setAction("Update");
                else if (aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue())
                    submitDCRBean.setAction("Remove");
                else
                    return false;

                //Vendor Info
                submitDCRBean.setVendorName(aimsAllianceOfApplication.getCompanyName());
                submitDCRBean.setVendorId(aimsAllianceOfApplication.getVendorId().toString());
                submitDCRBean.setEscalationInstructions(aimsAllianceOfApplication.getEscalationInstructions());

                submitDCRBean.setVendorLogoURL(
                    AimsConstants.INFOSPACE_RESOURCE_URL.format(objHostName)
                        + "?app_res=WapVendorLogo&app_id="
                        + aimsApp.getAppsId().toString()
                        + "&other_info="
                        + URLEncoder.encode(
                            MiscUtils.getBase64Digest(
                                InfospaceUtils.utf8decode(AimsConstants.INFOSPACE_KEY_DIGEST_FOR_URLS),
                                InfospaceUtils.utf8decode(aimsApp.getAppsId().toString()),
                                InfospaceUtils.utf8decode("WapVendorLogo")),
                            "UTF-8"));

                //Vendor Contact Info
                if (aimsAllianceOfApplication.getVzwBusinessContact() != null)
                {
                    aimsContact =
                        (AimsContact) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsContact.class,
                            aimsAllianceOfApplication.getVzwBusinessContact().toString());
                    submitDCRBean.setBusinessContactName(aimsContact.getFirstName() + " " + aimsContact.getLastName());
                    submitDCRBean.setBusinessContactEmail(aimsContact.getEmailAddress());
                    submitDCRBean.setBusinessContactTitle(aimsContact.getTitle());
                    submitDCRBean.setBusinessContactMobilePhone(aimsContact.getMobile());
                    submitDCRBean.setBusinessContactOfficePhone(aimsContact.getPhone());
                }

                if (aimsAllianceOfApplication.getTechContact() != null)
                {
                    aimsContact =
                        (AimsContact) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsContact.class,
                            aimsAllianceOfApplication.getTechContact().toString());
                    submitDCRBean.setEscalationContactName(aimsContact.getFirstName() + " " + aimsContact.getLastName());
                    submitDCRBean.setTechnicalContactName(aimsContact.getFirstName() + " " + aimsContact.getLastName());
                    submitDCRBean.setEscalationContactEmail(aimsContact.getEmailAddress());
                    submitDCRBean.setTechnicalContactEmail(aimsContact.getEmailAddress());
                    submitDCRBean.setEscalationContactTitle(aimsContact.getTitle());
                    submitDCRBean.setTechnicalContactTitle(aimsContact.getTitle());
                    submitDCRBean.setEscalationContactMobilePhone(aimsContact.getMobile());
                    submitDCRBean.setTechnicalContactMobilePhone(aimsContact.getMobile());
                    submitDCRBean.setEscalationContactOfficePhone(aimsContact.getPhone());
                    submitDCRBean.setTechnicalContactOfficePhone(aimsContact.getPhone());
                }

                if (aimsAllianceOfApplication.getVzwAccountManager() != null)
                {
                    aimsUser =
                        (AimsUser) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsUser.class,
                            aimsAllianceOfApplication.getVzwAccountManager().toString());

                    submitDCRBean.setCustomerContactName(aimsUser.getAimsContact().getFirstName() + " " + aimsUser.getAimsContact().getLastName());
                    submitDCRBean.setCustomerContactEmail(aimsUser.getAimsContact().getEmailAddress());
                }

                //Product Info
                submitDCRBean.setVendorProductCode(aimsWapApp.getVendorProductCode());
                submitDCRBean.setVendorProductVersion(aimsApp.getVersion());
                submitDCRBean.setProductPresentationNameSmallScreens(aimsApp.getTitle());
                submitDCRBean.setProductPresentationNameLargeScreens(aimsWapApp.getLongProductName());
                submitDCRBean.setLongDescription(aimsApp.getLongDesc());
                submitDCRBean.setShortDescription(aimsApp.getShortDesc());
                submitDCRBean.setDemoUrl(aimsWapApp.getDemoUrl());
                submitDCRBean.setTestUrl(aimsWapApp.getTestUrl());
                submitDCRBean.setProductionUrl(aimsWapApp.getProductionUrl());
                submitDCRBean.setDesktopUrl(aimsWapApp.getWebsiteUrl());
                if (aimsWapApp.getLaunchDate() != null)
                    submitDCRBean.setAvailabilityDateForProductionURL(Utility.convertToString(aimsWapApp.getLaunchDate(), AimsConstants.DATE_FORMAT_INFOSPACE));
                if (aimsWapApp.getTestEffectiveDate() != null)
                    submitDCRBean.setAvailabilityDateForTestURL(
                        Utility.convertToString(aimsWapApp.getTestEffectiveDate(), AimsConstants.DATE_FORMAT_INFOSPACE));

                if (aimsWapApp.getContentType().equals(AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]))
                    submitDCRBean.setPremiumIndicator("true");
                else
                    submitDCRBean.setPremiumIndicator("false");

                submitDCRBean.setProductIconURL(
                    AimsConstants.INFOSPACE_RESOURCE_URL.format(objHostName)
                        + "?app_res=WapProductIcon&app_id="
                        + aimsApp.getAppsId().toString()
                        + "&other_info="
                        + URLEncoder.encode(
                            MiscUtils.getBase64Digest(
                                InfospaceUtils.utf8decode(AimsConstants.INFOSPACE_KEY_DIGEST_FOR_URLS),
                                InfospaceUtils.utf8decode(aimsApp.getAppsId().toString()),
                                InfospaceUtils.utf8decode("WapProductIcon")),
                            "UTF-8"));

                submitDCRBean.setProductImageURL(
                    AimsConstants.INFOSPACE_RESOURCE_URL.format(objHostName)
                        + "?app_res=WapProductLogo&app_id="
                        + aimsApp.getAppsId().toString()
                        + "&other_info="
                        + URLEncoder.encode(
                            MiscUtils.getBase64Digest(
                                InfospaceUtils.utf8decode(AimsConstants.INFOSPACE_KEY_DIGEST_FOR_URLS),
                                InfospaceUtils.utf8decode(aimsApp.getAppsId().toString()),
                                InfospaceUtils.utf8decode("WapProductLogo")),
                            "UTF-8"));
               

                if (aimsWapApp.getSubCategoryId1() != null)
                {
                    AimsAppSubCategory subCategory =
                        (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsWapApp.getSubCategoryId1().toString());
                    AimsAppCategory category =
                        (AimsAppCategory) DBHelper.getInstance().load(AimsAppCategory.class, subCategory.getAimsAppCategoryId().toString());

                    initialPortalPlacementCategory.add(category.getCategoryName());
                    initialPortalPlacementSubCategory.add(subCategory.getSubCategoryName());
                    initialPortalPlacementPosition.add(aimsWapApp.getLinkOrder1());
                }

                if (aimsWapApp.getSubCategoryId2() != null)
                {
                    AimsAppSubCategory subCategory =
                        (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsWapApp.getSubCategoryId2().toString());
                    AimsAppCategory category =
                        (AimsAppCategory) DBHelper.getInstance().load(AimsAppCategory.class, subCategory.getAimsAppCategoryId().toString());

                    initialPortalPlacementCategory.add(category.getCategoryName());
                    initialPortalPlacementSubCategory.add(subCategory.getSubCategoryName());
                    initialPortalPlacementPosition.add(aimsWapApp.getLinkOrder2());
                }

                if (aimsWapApp.getSubCategoryId3() != null)
                {
                    AimsAppSubCategory subCategory =
                        (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsWapApp.getSubCategoryId3().toString());
                    AimsAppCategory category =
                        (AimsAppCategory) DBHelper.getInstance().load(AimsAppCategory.class, subCategory.getAimsAppCategoryId().toString());

                    initialPortalPlacementCategory.add(category.getCategoryName());
                    initialPortalPlacementSubCategory.add(subCategory.getSubCategoryName());
                    initialPortalPlacementPosition.add(aimsWapApp.getLinkOrder3());
                }

                submitDCRBean.setUserProfile(userProfile);
                submitDCRBean.setInitialPortalPlacementCategory(initialPortalPlacementCategory);
                submitDCRBean.setInitialPortalPlacementSubCategory(initialPortalPlacementSubCategory);
                submitDCRBean.setInitialPortalPlacementPosition(initialPortalPlacementPosition);

                VZWHandlerStub stub = new VZWHandlerStub();
                res = stub.SubmitDocument(submitDCRBean, theRealXSDPath, xmlSubmitted);

                System.out.println("\n\nResponse from Infospace: " + res.getErrorMsg());

                if (!res.getErrorMsg().equalsIgnoreCase("SUCCESS"))
                {
                    throw new Exception("Error in XML. Please check table AIMS_INFOSPACE_LOG");
                }
                else
                {
                    InfoSpaceManager.submitInfoSpaceLog(aimsApp.getAppsId(), aimsLifecyclePhaseId.toString(), "Y", res.getErrorMsg());

                    if (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())
                    {
                        AimsApplicationsManager.saveJournalEntry(
                            aimsApp.getAppsId(),
                            "Contents were successfully pushed to Infospace",
                            AimsConstants.JOURNAL_TYPE_PRIVATE,
                            "system");
                    }

                    aimsEvent = null;
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_SUCCESS_SENDING_DCR_TO_INFOSPACE);
                    if (aimsEvent != null)
                    {
                        aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
                        aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_CONTENT_TYPE,
                            MiscUtils.getWapAppContentType(aimsWapApp.getContentType()));
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_NEW,
                            ((AimsLifecyclePhase) DBHelper
                                .getInstance()
                                .load(com.netpace.aims.model.application.AimsLifecyclePhase.class, aimsLifecyclePhaseId.toString()))
                                .getPhaseName());
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                    //ftp images
                    try
                    {
                        log.debug("Infospace DCR success, sending Wap Images via FTP");
                        WapApplicationHelper.processWapFTPTransfer(aimsWapApp.getWapAppsId(), null, aimsApp, aimsWapApp);
                    }
                    catch(AimsException aimsException) {
                        System.out.println("Problem while sending ftp images (Success DCR)");
                        aimsException.printStackTrace();
                    }
                }
            }
        }
        catch (Exception ex)
        {
            try
            {
                System.out.println("\n\nEXCEPTION FOR INFOSPACE");
                String infoSpaceLogString = null;

                if (res != null)
                    infoSpaceLogString = res.getErrorMsg();
                else
                    infoSpaceLogString = "LOCAL ERROR: Could not connect to the Infospace Web Service";

                System.out.println("\n\naimsApp.getAppsId(): " + aimsApp.getAppsId());
                System.out.println("aimsLifecyclePhaseId.toString(): " + aimsLifecyclePhaseId.toString());
                System.out.println("infoSpaceLogString: " + infoSpaceLogString);

                System.out.println("\n\nSending out emails related to Infospace");

                StringBuffer emailSubject = new StringBuffer("Exception in WapApplicationHelper.sendSubmittedDCRToInfoSpace(), on .... ");
                if (hostName != null)
                    emailSubject.append(hostName);

                MailUtils.sendMail(
                    AimsConstants.EMAIL_EXCEPTION_ADMIN,
                    "exceptions@netpace.com",
                    emailSubject.toString(),
                    null,
                    "Exception Message:\n"
                        + StringFuncs.NullValueReplacement(ex.getMessage())
                        + "\n\nInfospace Message:\n"
                        + StringFuncs.NullValueReplacement(infoSpaceLogString)
                        + "\n\nException Trace:\n"
                        + MiscUtils.getExceptionStackTraceInfo(ex)
                        + "\n\nXML Submitted:\n"
                        + xmlSubmitted.toString());

                aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_APP_ERROR_SENDING_DCR_TO_INFOSPACE);
                if (aimsEvent != null)
                {
                    aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
                    aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_CONTENT_TYPE,
                        MiscUtils.getWapAppContentType(aimsWapApp.getContentType()));
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_NEW,
                        ((AimsLifecyclePhase) DBHelper
                            .getInstance()
                            .load(com.netpace.aims.model.application.AimsLifecyclePhase.class, aimsLifecyclePhaseId.toString()))
                            .getPhaseName());
                    log.debug("\n1111");
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_RESEND_INFOSPACE_URL,
                        AimsConstants.INFOSPACE_RESUBMIT_URL.format(objHostName)
                            + "?p_id="
                            + aimsLifecyclePhaseId.toString()
                            + "&app_id="
                            + aimsApp.getAppsId().toString()
                            + "&other_info="
                            + URLEncoder.encode(
                                MiscUtils.getBase64Digest(
                                    InfospaceUtils.utf8decode(AimsConstants.INFOSPACE_KEY_DIGEST_FOR_URLS),
                                    InfospaceUtils.utf8decode(aimsApp.getAppsId().toString()),
                                    InfospaceUtils.utf8decode(aimsLifecyclePhaseId.toString())),
                                "UTF-8"));
                    log.debug("\n2222\n\n");
                    log.debug(aimsEventObject.getProperty(AimsNotificationConstants.PLACE_HOLDER_RESEND_INFOSPACE_URL));
                    aimsEvent.raiseEvent(aimsEventObject);
                }

                System.out.println("\n\nLogging error information into DB");
                InfoSpaceManager.submitInfoSpaceLog(aimsApp.getAppsId(), aimsLifecyclePhaseId.toString(), "N", infoSpaceLogString);

            }
            catch (Exception xyz)
            {
                try
                {
                    MailUtils.sendMail(
                        AimsConstants.EMAIL_EXCEPTION_ADMIN,
                        "exceptions@netpace.com",
                        AimsConstants.EMAIL_SUBJECT_RARE_EXCEPTION + " .... on " + hostName,
                        null,
                        MiscUtils.getExceptionStackTraceInfo(xyz));
                }
                catch (Exception mailEx)
                {
                    System.out.println("Exception in WAPApplicationHelper while sending email");
                    mailEx.printStackTrace();
                }
            }
            success = false;
        }

        return success;
    }

    public static boolean sendDCRToInfoSpace(
        final AimsApp aimsApp,
        final AimsWapApp aimsWapApp,
        final Long aimsLifecyclePhaseId,
        final String theRealXSDPath,
        final String hostName,
        boolean sync)
    {
        if (sync)
        {
            return WapApplicationHelper.sendDCRToInfoSpace(aimsApp, aimsWapApp, aimsLifecyclePhaseId, theRealXSDPath, hostName);

        }
        else
        {
            new Thread()
            {
                public void run()
                {
                    WapApplicationHelper.sendDCRToInfoSpace(aimsApp, aimsWapApp, aimsLifecyclePhaseId, theRealXSDPath, hostName);
                }
            }
            .start();
            return true;
        }
    }

    public static boolean sendDCRToSCM(AimsApp aimsApp, AimsWapApp aimsWapApp, Long aimsLifecyclePhaseId, String theRealXSDPath, String hostName)
    {
        //Persisting SCM XML in Database (to be transferred via NDM)
        boolean success = true;
        AimsAllianc aimsAllianceOfApplication = null;
        String selectedLicenseName = null;

        Session session = null;
        Transaction tx = null;

        try
        {
            aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());

            for (Iterator it = aimsWapApp.getLicenseTypes().iterator(); it.hasNext();)
            {
                AimsWapLicenseType aimsWapLicenseType = (AimsWapLicenseType) it.next();
                selectedLicenseName = aimsWapLicenseType.getScmLicenseName();
            }

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            AimsScmProdCat scmProdCategory = new AimsScmProdCat();
            scmProdCategory.setProductVersion(aimsApp.getVersion());
            scmProdCategory.setVzwProductCategory(aimsWapApp.getVendorProductDisplay());
            scmProdCategory.setCategorization("PREM_WAP");
            scmProdCategory.setVendorId(aimsAllianceOfApplication.getVendorId().toString());
            scmProdCategory.setVendorName(aimsAllianceOfApplication.getCompanyName());
            scmProdCategory.setDirty("Y");
            scmProdCategory.setVendorProductCode(aimsWapApp.getVendorProductCode());
            scmProdCategory.setDownloadUrl(aimsWapApp.getProductionUrl());
            scmProdCategory.setAppsId(aimsApp.getAppsId());
            scmProdCategory.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            scmProdCategory.setCreatedDate(new Date());
            scmProdCategory.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
            scmProdCategory.setLastUpdatedDate(new Date());

            session.saveOrUpdate(scmProdCategory);

            AimsScmProdCatPricing scmProdCategoryPricing = new AimsScmProdCatPricing();
            scmProdCategoryPricing.setLicenses(selectedLicenseName);
            scmProdCategoryPricing.setScmProdCatId(scmProdCategory.getScmProdCatId());
            scmProdCategoryPricing.setVendorSplitPercentage(new BigDecimal(aimsWapApp.getVendorSplitPercentage()));
            scmProdCategoryPricing.setPurchasePriceValue(new BigDecimal(aimsWapApp.getVzwRetailPrice()));
            scmProdCategoryPricing.setPurchasePriceCurrency("USD");
            scmProdCategoryPricing.setPricePlanType("V");

            session.save(scmProdCategoryPricing);

            //Persisting Repeating Values for DEFAULT
            AimsScmProdCatPricing scmProdCategoryPricingDefault = new AimsScmProdCatPricing();
            scmProdCategoryPricingDefault.setLicenses("Default");
            scmProdCategoryPricingDefault.setScmProdCatId(scmProdCategory.getScmProdCatId());
            scmProdCategoryPricingDefault.setVendorSplitPercentage(new BigDecimal(aimsWapApp.getVendorSplitPercentage()));
            scmProdCategoryPricingDefault.setPurchasePriceValue(new BigDecimal(aimsWapApp.getVzwRetailPrice()));
            scmProdCategoryPricingDefault.setPurchasePriceCurrency("USD");
            scmProdCategoryPricingDefault.setPricePlanType("V");

            session.save(scmProdCategoryPricingDefault);

            tx.commit();

        }

        catch (Exception ex)
        {
            System.out.println("Exception in persisting SCM in Database");
            try
            {
                if (tx != null)
                    tx.rollback();
            }
            catch (Exception ignore)
            {}
            ex.printStackTrace();
        }

        finally
        {
            try
            {
                session.close();
            }
            catch (Exception ignore)
            {}
        }

        return success;
    }


    public static boolean processWapFTPTransfer(Long appsID,
                                             String currUser,
                                             AimsApp aimsApp,
                                             AimsWapApp aimsWapApp
                                             )
            throws AimsException, HibernateException {
        log.debug("start WapApplicationHelper.processWapFTPTransfer.... appsId: "+appsID);
        HashMap appWap = null;
        AimsAllianc allianceOfApplication = null;
        AimsWapFtpLog wapFTPLog = null;

        String localTempZipDir = ConfigEnvProperties.getInstance().getProperty("wap.images.local.temp.dir");
        File zipContainerDirectory = new File(localTempZipDir);
        File imagesZipFile = null;
        String wapZipFileName = null;
        String imageFileName = null;
        Date currentDate = null;
        SimpleDateFormat dateFormat = null;
        currUser = (StringFuncs.isNullOrEmpty(currUser) ? "system" : currUser);

        Long vendorID = null;
        String vendorProductCode = null;
        String appVersion = null;

        boolean zipCreated = false;
        boolean zipTransfered = false;

        if(!zipContainerDirectory.exists()) {
            //if directory does not exists then create it
            zipContainerDirectory.mkdir();
            log.debug("zipContainerDirectory created on path : "+zipContainerDirectory.getAbsolutePath());
        }

        //create zipFile Name: MMDDyyyyHHMM.zip
        currentDate = new Date();
        dateFormat = new SimpleDateFormat("MMddyyyyHHmm");
        wapZipFileName = dateFormat.format(currentDate)+".zip";
        imagesZipFile = new File(zipContainerDirectory, wapZipFileName);

        try {
            //check already created file, if not present then start processing
            if((RestManager.getWapFTPLog(wapZipFileName) == null) && !imagesZipFile.exists()) {
                //create zip file first

                if(aimsApp == null || aimsWapApp==null) {
                    appWap = WapApplicationManager.getWapApp(appsID, null);
                    aimsApp = (AimsApp) appWap.get("AimsApp");
                    aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");
                }


                allianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                vendorID = allianceOfApplication.getVendorId();
                vendorProductCode =  StringFuncs.replaceInvalidCharacters(StringFuncs.NullValueReplacement(aimsWapApp.getVendorProductCode()), "-");
                appVersion =  StringFuncs.replaceInvalidCharacters(StringFuncs.NullValueReplacement(aimsApp.getVersion()), "-");
                //image file name: <vendorID>_<productCode>_<productVersion>
                imageFileName = vendorID+"_"+vendorProductCode+"_"+appVersion;

                //create ZipFile
                zipCreated = WapApplicationHelper.createWapImagesZipFile(imagesZipFile, imageFileName, aimsApp.getAppsId(), aimsApp.getAimsAllianceId());
                if(zipCreated) {
                    //ftp transfer zip file
                    zipTransfered = WapApplicationHelper.wapFTPZipFile(imagesZipFile);
                    if(zipTransfered) {
                        //if file transfered then create WAP FTP Log
                        wapFTPLog = new AimsWapFtpLog();
                        wapFTPLog.setAllianceId(aimsApp.getAimsAllianceId());
                        wapFTPLog.setWapApplicationId(aimsWapApp.getWapAppsId());
                        wapFTPLog.setFtpFileName(wapZipFileName);
                        wapFTPLog.setFtpFileDescription("Zip file name: "+imagesZipFile.getName()+". Size of zip file: "+imagesZipFile.length());
                        wapFTPLog.setDateFileSent(currentDate);
                        wapFTPLog.setFileStatus(AimsConstants.WAP_FTP_STATUS_PENDING);
                        wapFTPLog.setCreatedBy(currUser);
                        wapFTPLog.setCreatedDate(currentDate);
                        wapFTPLog.setLastUpdatedBy(currUser);
                        wapFTPLog.setLastUpdatedDate(currentDate);
                        RestManager.saveOrUpdateWapFTPLog(wapFTPLog);

                        //set ftp flag to y in wapApp if its value is not 'A'
                        /*  TODO Delete this snippet, no need to set wapFTPFlag to Y
                            if( !(StringFuncs.NullValueReplacement(aimsWapApp.getWapFtpFlag()).equalsIgnoreCase(ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED_AGAIN)) ) {
                            aimsWapApp.setWapFtpFlag(ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED);
                            try {
                                WapApplicationManager.updateWapFTPFlag(aimsWapApp.getWapAppsId(), ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED);
                                log.debug("Wap FTP Flag set to : "+ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED+"\t appsID= "+aimsWapApp.getWapAppsId());
                            }
                            catch(SQLException sqle) {
                                System.out.println("Problem in setting wap FTP Flag");
                                sqle.printStackTrace();  //
                            }
                        }*/
                    }
                }
                //delete local zip file here (if required)
            }
            else {
                //show message that file already exists, try after some time
                System.out.println("WapApplicationHelper.processWapFTPTransfer: Error in FTP transfer, File: "+imagesZipFile.getName()+" already exists");
                AimsException aimsException = new AimsException("Error");
                aimsException.addException(new AimsException("error.wap.app.zip.already.present"));
                throw aimsException;
            }
        }//end try
        catch (AimsException ae) {
            throw ae;
        }//end catch AimsException
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        log.debug("end WapApplicationHelper.processWapFTPTransfer.... appsId: "+appsID+"\t zipTransfered: "+zipTransfered);
        return zipTransfered;
    }

    public static boolean createWapImagesZipFile(File imagesZipFile,
                                                 String imageFileName,
                                                 Long wapApplicationID,
                                                 Long allianceID)
            throws AimsException, HibernateException {

        byte[] buf = new byte[1024];// Create a buffer for reading the files
        boolean zipCreated = false;
        ZipOutputStream zipOut = null;
        InputStream imgInput = null;

        //general exception for zip creation
        AimsException aimsException = new AimsException("Error");
        aimsException.addException(new AimsException("error.wap.app.zip.create"));

        String fileExtension = "";
        String fName = "";
        String [] imagesDirNames = new String[] {
                                                    "img_174",
                                                    "img_240",
                                                    "img_320"
                                                };

        //if images not found, then this statement throws aims exception
        AimsTempFile [] wapImageFiles = WapApplicationHelper.getImagesTempFiles(wapApplicationID, allianceID);

        try {
            zipOut = new ZipOutputStream(new FileOutputStream(imagesZipFile));
            // Compress the files
            for (int i=0; i<wapImageFiles.length; i++) {
                try {
                    imgInput = wapImageFiles[i].getTempFile().getBinaryStream();

                    //get extension of filename in lower case
                    fName = wapImageFiles[i].getTempFileName();
                    fileExtension = (fName.substring(fName.lastIndexOf("."))).toLowerCase();

                    // Add ZIP entry to output stream. dirName/filename+extension
                    zipOut.putNextEntry(new ZipEntry(imagesDirNames[i]+"/"+(imageFileName+fileExtension)));
                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = imgInput.read(buf)) > 0) {
                        zipOut.write(buf, 0, len);
                    }
                }//end try
                catch (SQLException sqle) {
                    System.out.println("Problem in getting image file as stream");
                    sqle.printStackTrace();  //
                    throw aimsException;
                }
                catch (IOException e) {
                    System.out.println("Problem in adding zip entry");
                    e.printStackTrace();
                    throw aimsException;
                }
                finally {
                    // Complete the entry
                    try {
                        zipOut.closeEntry();
                        imgInput.close();
                    } catch (IOException ioe) {
                        System.out.println("Error closing zip entry or input stream for image");
                        ioe.printStackTrace();
                    }
                }//end finally
            }//end for
            zipCreated = true;
            log.debug("wap images zip file created for ftp transfer: "+imagesZipFile.getName()+" in directory: "+imagesZipFile.getAbsolutePath());
        }
        catch (FileNotFoundException e) {
            System.out.println(imagesZipFile+" not found in temp directory: "+imagesZipFile.getAbsolutePath());
            e.printStackTrace();//zip file not found
            throw aimsException;
        }

        finally {
            try {
                if(zipOut != null) {
                    zipOut.close();
                }
            }
            catch (IOException e) {
                System.out.println("Error closing zip stream");
                e.printStackTrace();
                System.out.println("deleting zip file: "+imagesZipFile.delete());
                throw aimsException;
            }
        }
        return zipCreated;
    }//end creat wapZip file

     private static AimsTempFile[] getImagesTempFiles(Long wapApplicationID, Long allianceID) throws AimsException, HibernateException {
        //medium, potrait, landscape
        String [][] wapImagesBlobInfo = new String[][] {
                                                        ManageApplicationsConstants.WAP_MEDIUM_IMAGE_BLOB_OBJ_INFO,
                                                        ManageApplicationsConstants.WAP_POTRAIT_IMAGE_BLOB_OBJ_INFO,
                                                        ManageApplicationsConstants.WAP_LANDSCAPE_IMAGE_BLOB_OBJ_INFO
                                                        };
        AimsTempFile [] wapImageFiles = new AimsTempFile[3];
        AimsException aimsImageMissingException = new AimsException("Error");
        aimsImageMissingException.addException(new AimsException("error.wap.app.image.missing"));

        try {
            for (int i=0; i<wapImagesBlobInfo.length; i++) {
                wapImageFiles[i] = ApplicationsManagerHelper.getFile(wapApplicationID, allianceID, wapImagesBlobInfo[i]);
                if(wapImageFiles[i].getTempFile()==null) {
                    log.debug("wap image missing to create zip file, wapAppID= "+wapApplicationID);
                    //if image not found throw image missimg exception
                    throw aimsImageMissingException;
                }
            }
        }
        catch(HibernateException he) {
            System.out.println("Problem in getting image file from database, wapAppID: "+wapApplicationID);
            he.printStackTrace();//problem in getting temp file
            throw he;
        }
        return wapImageFiles;
    }

    public static boolean wapFTPZipFile(File transferFile) throws AimsException {

        log.debug("wapFTPZipFile FTP Start. FileName: "+transferFile.getName());
        boolean loginStatus = false;
        boolean dirChanged = false;

        FileInputStream transferStream = null;
        FTPClient ftpClient = new FTPClient();

        ConfigEnvProperties envProperties = ConfigEnvProperties.getInstance();
        //ftp server config
        String ftpServerAddress = envProperties.getProperty("wap.images.ftp.server.address");
        String ftpUser = envProperties.getProperty("wap.images.ftp.user.name");
        String ftpPassword = envProperties.getProperty("wap.images.ftp.user.password");
        String ftpWorkingDirectory = envProperties.getProperty("wap.images.ftp.working.dir");

        //general exception for ftp transfer
        AimsException aimsException = new AimsException("Error");
        aimsException.addException(new AimsException("error.wap.app.ftp.transfer"));

        String errorMessage = "";

        boolean transfered = false;
        try {
            ftpClient.connect(ftpServerAddress);
            loginStatus = ftpClient.login(ftpUser,ftpPassword);
            log.debug("Connection to server "+ftpServerAddress+" "+(loginStatus ? "success":"failure"));
            if(loginStatus) {
                dirChanged = ftpClient.changeWorkingDirectory(ftpWorkingDirectory);
                log.debug("change remote directory to "+ftpWorkingDirectory+": "+dirChanged);
                if(dirChanged) {
                    transferStream = new FileInputStream(transferFile);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    transfered = ftpClient.storeFile(transferFile.getName(), transferStream);
                    log.debug(transferFile.getName()+" transfered: "+transfered+" on server: "+ftpServerAddress);
                    if(!transfered) {
                        errorMessage = "File: "+transferFile.getName()+" not transfered to "+(ftpServerAddress+"/"+ftpServerAddress);
                        System.out.println("Error in FTP Transfer: "+errorMessage);
                        sendFTPErrorMail(errorMessage);
                        throw aimsException;
                    }
                }
                else {
                    errorMessage = "Directory: "+ftpWorkingDirectory+" not found in "+ftpServerAddress;
                    System.out.println("Error in FTP Transfer: "+errorMessage);
                    sendFTPErrorMail(errorMessage);
                    throw aimsException;
                }
            }//end loginstatus
            else {
                errorMessage = "FTP Authentication failed. \n\nServer: "+ftpServerAddress+"\nUserID: "+ftpUser+"\nPassword: "+ftpPassword;
                System.out.println("Error in FTP Transfer: "+errorMessage);
                sendFTPErrorMail(errorMessage);
                throw aimsException;
            }
        }//end try
        catch (FileNotFoundException e) {
            System.out.println("Exception: "+transferFile.getName()+" not found in temp directory");
            e.printStackTrace();//zip file not found
            throw aimsException;
        }
        catch (IOException ioe) {
            System.out.println("Exception: Error in connection "+ftpServerAddress);
            ioe.printStackTrace();
            sendFTPErrorMail("Error in connection to : "+ftpServerAddress+"\n\n"+MiscUtils.getExceptionStackTraceInfo(ioe));
            throw aimsException;
        }
        finally {
            try 
            {
                //close stream
                if(transferStream != null ) {
                    transferStream.close();
                }                
            }
            catch (IOException ioe) 
            {
                ioe.printStackTrace();
            }
            finally
            {
                if(ftpClient.isConnected()) {
                    try
                    {
                        //logout. Issues QUIT command
                        ftpClient.logout();
                    }
                    catch(IOException ioex)
                    {
                        ioex.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            //disconnect ftp session
                            ftpClient.disconnect();
                        }
                        catch(IOException ioexc)
                        {
                            ioexc.printStackTrace();
                        }   
                    }
                }
            }
            
        }
        log.debug("wapFTPZipFile FTP end. FileName: "+transferFile.getName()+"\t transfered: "+transfered);
        return transfered;
    }

    /**
     * checks whether manual Image upload allowed on this value of wapFTPFlag
     * @param wapFTPFlag
     * @return
     */
    public static boolean isManualImageUploadAllowed(String wapFTPFlag) {
        //wapFTPFlag is empty or equal to 'A' and not equal to 'Y' then return
        boolean isAllowed = false;
        isAllowed = (StringFuncs.isNullOrEmpty(wapFTPFlag)) ||
                    ( wapFTPFlag.equalsIgnoreCase(ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED_AGAIN)
                        && !wapFTPFlag.equalsIgnoreCase(ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED) );
        return isAllowed;
    }

    private static void sendFTPErrorMail(String content) {
        try {
            MailUtils.sendMail(
                AimsConstants.EMAIL_EXCEPTION_ADMIN,
                "exceptions@netpace.com",
                "FTP transfer error",
                null,
                content);            
        }
        catch (Exception mailEx) {
            System.out.println("Exception in WAPApplicationHelper while sending email");
            mailEx.printStackTrace();
        }
    }


}