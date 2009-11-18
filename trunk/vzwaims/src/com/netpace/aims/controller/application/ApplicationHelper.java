package com.netpace.aims.controller.application;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.*;

import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.util.*;

import java.util.*;

import net.sf.hibernate.HibernateException;

/**
 * This class containts helper functions for modules related to Managing Applications
 *											 
 * @author Adnan Makda
 */

public class ApplicationHelper
{

    static Logger log = Logger.getLogger(ApplicationHelper.class.getName());

    //This method populates the messages collection with Unique Constraints (if any)
    public static boolean searchUniqueConstaintErrors(String exMessage, ActionMessages messages)
    {
        boolean errorFound = false;

        for (int i = 0; i < ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS.length / 2; i++)
        {
            Object[] objs = { ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS[i * 2] };

            if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1)
            {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS[(i * 2) + 1]));
                errorFound = true;
            }
        }

        return errorFound;
    }

    //This method populates the messages collection with Integrity Constraints (if any)
    public static boolean searchIntegrityConstaintErrors(String exMessage, ActionMessages messages)
    {
        boolean errorFound = false;

        for (int i = 0; i < ManageApplicationsConstants.INTEGRITY_CONSTRAINT_KEYS.length / 2; i++)
        {
            Object[] objs = { ManageApplicationsConstants.INTEGRITY_CONSTRAINT_KEYS[i * 2] };

            if (exMessage.indexOf(AimsConstants.INTEGRITY_CONSTRAINT_MESSAGE.format(objs)) > -1)
            {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ManageApplicationsConstants.INTEGRITY_CONSTRAINT_KEYS[(i * 2) + 1]));
                errorFound = true;
            }
        }

        return errorFound;
    }

    public static boolean checkAccess(HttpServletRequest request, String taskname, String PRIV_KEY)
    {
        try
        {
            if (taskname.equalsIgnoreCase("create"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.INSERT))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("edit"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.UPDATE))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("view"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.SELECT))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("delete"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.DELETE))
                    return true;
                else
                    return false;
            }
            else if (taskname.equalsIgnoreCase("clone"))
            {
                if (AimsSecurityManager.checkAccess(request, PRIV_KEY, AimsSecurityManager.INSERT))
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean checkPlatformAccess(HttpServletRequest request, String taskname, Long platformId)
    {
        if (platformId.longValue() == AimsConstants.BREW_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_BREW_APPS);

        if (platformId.longValue() == AimsConstants.ENTERPRISE_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_ENTERPRISE_APPS);

        if (platformId.longValue() == AimsConstants.MMS_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_MMS_APPS);

        if (platformId.longValue() == AimsConstants.SMS_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_SMS_APPS);

        if (platformId.longValue() == AimsConstants.WAP_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_WAP_APPS);

        if (platformId.longValue() == AimsConstants.VCAST_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_VCAST_APPS);

        if (platformId.longValue() == AimsConstants.DASHBOARD_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_DASHBOARD_APPS);

        if (platformId.longValue() == AimsConstants.VZAPPZONE_PLATFORM_ID.longValue())
           return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_VZAPPZONE_APPS);
        
        if (platformId.longValue() == AimsConstants.JAVA_PLATFORM_ID.longValue())
            return checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_JAVA_APP);
        
        return false;
    }

    public static boolean checkEditAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.CONCEPT_ACCEPTED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.CONCEPT_REJECTED_ID.longValue()))
                return false;
            else
                return true;
        }
        else
            return true;
    }
    
    public static boolean checkJavaDeleteAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
    	if (currUserType.equals(AimsConstants.VZW_USERTYPE))
    	{
    		if ( aimsLifecyclePhaseId.equals(AimsConstants.PHASE_JAVA_APPROVED) )
    			return false;
    		else
    			return true;
    	}
    	else  if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
    	{
    		if ( aimsLifecyclePhaseId.equals(AimsConstants.SAVED_ID) )
    			return true;
    		else
    			return false;
    	}
    	return true;
    }
    
    public static boolean checkDeleteAccess(String currUserType, Long aimsLifecyclePhaseId)
    {
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            if ((aimsLifecyclePhaseId.longValue() == AimsConstants.EVALUATED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.ASSIGNED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.TESTING_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.ACCEPTANCE_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.REJECTED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.SUNSET_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.CONCEPT_ACCEPTED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.CONCEPT_REJECTED_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_TEST_PASSED_ID.longValue())                
                ||(aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_OTA_TEST_PASSED_ID.longValue())
                ||(aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_IN_PRODUCTION_ID.longValue())
                || (aimsLifecyclePhaseId.longValue() == AimsConstants.PHASE_IN_PRODUCTION.longValue()))
                return false;
            else
                return true;
        }
        else
            return true;
    }

    /*
        public static Long[] getRestrictedPlatformsForView(HttpServletRequest request)
        {
            Vector platformIdsVector = new Vector();
    
            if (!(checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_BREW_APPS)))
                platformIdsVector.add(AimsConstants.BREW_PLATFORM_ID);
            if (!(checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_ENTERPRISE_APPS)))
                platformIdsVector.add(AimsConstants.ENTERPRISE_PLATFORM_ID);
            if (!(checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_MMS_APPS)))
                platformIdsVector.add(AimsConstants.MMS_PLATFORM_ID);
            if (!(checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_SMS_APPS)))
                platformIdsVector.add(AimsConstants.SMS_PLATFORM_ID);
            if (!(checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_WAP_APPS)))
                platformIdsVector.add(AimsConstants.WAP_PLATFORM_ID);
    
            Long[] platformIds = new Long[platformIdsVector.size()];
            platformIds = (Long[]) platformIdsVector.toArray(platformIds);
    
            return platformIds;
        }
    */
    public static String[] getPermittedPlatformsForView(HttpServletRequest request)
    {
        Vector platformIdsVector = new Vector();

        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_BREW_APPS))
            platformIdsVector.add(AimsConstants.BREW_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_DASHBOARD_APPS))
        	platformIdsVector.add(AimsConstants.DASHBOARD_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_ENTERPRISE_APPS))
            platformIdsVector.add(AimsConstants.ENTERPRISE_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_MMS_APPS))
            platformIdsVector.add(AimsConstants.MMS_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_SMS_APPS))
            platformIdsVector.add(AimsConstants.SMS_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_WAP_APPS))
            platformIdsVector.add(AimsConstants.WAP_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_VCAST_APPS))
            platformIdsVector.add(AimsConstants.VCAST_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_VZAPPZONE_APPS))
            platformIdsVector.add(AimsConstants.VZAPPZONE_PLATFORM_ID.toString());
        if (checkAccess(request, "view", AimsPrivilegesConstants.MANAGE_JAVA_APP))
        	platformIdsVector.add(AimsConstants.JAVA_PLATFORM_ID.toString());        

        String[] platformIds = new String[platformIdsVector.size()];
        platformIds = (String[]) platformIdsVector.toArray(platformIds);

        return platformIds;
    }

    /**
        * This class is called from SetupAction classes. It contains common tasks to all applications
        *
        * @author Adnan Makda
        */
    public static void setupAction(
        HttpServletRequest request,
        String taskname,
        ApplicationUpdateForm applicationUpdateForm,
        AimsApp aimsApp,
        AimsAppCategory aimsAppCategory,
        Long platformId,
        String dateFormat)
        throws AimsSecurityException
    {

        HttpSession session = request.getSession();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        //CHECK ACCESS
        if (!(checkPlatformAccess(request, taskname, platformId)))
            throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("edit"))
            if (!(checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("delete"))
            if (!(checkDeleteAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();
        //END OF CHECK ACCESS

        session.setAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME, taskname);
        applicationUpdateForm.setCurrentPage("page1");

        //Set Temp File Ids to Zero
        applicationUpdateForm.setScreenJpegTempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg2TempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg3TempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg4TempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg5TempFileId(new Long(0));
        applicationUpdateForm.setFlashDemoTempFileId(new Long(0));
        applicationUpdateForm.setUserGuideTempFileId(new Long(0));
        
        /*
        if ((platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue())) {
        	applicationUpdateForm.setSplashScreenEpsTempFileId(new Long(0));
        	applicationUpdateForm.setActiveScreenEpsTempFileId(new Long(0));
        }
        */
        
        applicationUpdateForm.setFaqDocTempFileId(new Long(0));
        applicationUpdateForm.setTestPlanResultsTempFileId(new Long(0));

        if (taskname.equalsIgnoreCase("create"))
        {
            applicationUpdateForm.setTask("create");
            applicationUpdateForm.setAppsId(new Long(0));
            applicationUpdateForm.setIfPrRelease("N");
            applicationUpdateForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
            applicationUpdateForm.setAimsPlatformId(platformId);
            if ((platformId == AimsConstants.SMS_PLATFORM_ID) || (platformId == AimsConstants.MMS_PLATFORM_ID))
                applicationUpdateForm.setVersion("1");

            //Disable Network Usage by default
            applicationUpdateForm.setNetworkUsage(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE);
        }
        else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {
            if (taskname.equalsIgnoreCase("edit"))
                applicationUpdateForm.setTask("edit");
            else if (taskname.equalsIgnoreCase("view"))
                applicationUpdateForm.setTask("view");

            //For Cloning....
            if (taskname.equalsIgnoreCase("clone"))
            {
                aimsApp.setVersion(null);
                applicationUpdateForm.setTitle(null);
                applicationUpdateForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                applicationUpdateForm.setTask("create");
                session.setAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME, "create");
            }
            else
            {
                applicationUpdateForm.setTitle(aimsApp.getTitle());
                applicationUpdateForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
            }

            applicationUpdateForm.setAppsId(aimsApp.getAppsId());
            applicationUpdateForm.setShortDesc(aimsApp.getShortDesc());
            applicationUpdateForm.setLongDesc(aimsApp.getLongDesc());
            applicationUpdateForm.setAppDeployments(aimsApp.getAppDeployments());
            applicationUpdateForm.setIfPrRelease(aimsApp.getIfPrRelease());
            applicationUpdateForm.setAimsPlatformId(platformId);
            applicationUpdateForm.setSubmittedDate(Utility.convertToString(aimsApp.getSubmittedDate(), dateFormat));

            //set network usage of application
            applicationUpdateForm.setNetworkUsage(aimsApp.getNetworkUsage());
            //get AimsAppNetworkUrls
            try
            {
                String[] applicationURLs = AimsApplicationsManager.getAppNetworkUrlValues(aimsApp.getAppsId());
                Long applicationURLsLength = new Long(0);
                applicationUpdateForm.setApplicationURLs(applicationURLs);
                if(applicationURLs!=null && applicationURLs.length>0) {
                    applicationURLsLength = new Long(applicationURLs.length);
                }
                request.setAttribute("applicationURLsLength", applicationURLsLength);
            }
            catch (HibernateException he) {
                he.printStackTrace();
                log.debug("\nError in getting Network URL for application:"+aimsApp.getAppsId()+"\n");
            }

            //export urls if not create or clone
            if(!taskname.equalsIgnoreCase("create") && !taskname.equalsIgnoreCase("clone"))
            {
                //check access for EXPORT_CONTENT_FILTER_URLS
                request.setAttribute("exportUrlAllowed",
                    new Boolean((ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS))) );
            }

            if ((platformId == AimsConstants.SMS_PLATFORM_ID) || (platformId == AimsConstants.MMS_PLATFORM_ID))
                applicationUpdateForm.setVersion("1");
            else
                applicationUpdateForm.setVersion(aimsApp.getVersion());

            if (aimsAppCategory != null)
                applicationUpdateForm.setAimsAppCategoryId(aimsAppCategory.getCategoryId());

            applicationUpdateForm.setAimsAppSubCategoryId(aimsApp.getAimsAppSubCategoryId());
            applicationUpdateForm.setAimsContactId(aimsApp.getAimsContactId());

            //Set File Names
            applicationUpdateForm.setScreenJpegFileName(aimsApp.getScreenJpegFileName());
            applicationUpdateForm.setScreenJpeg2FileName(aimsApp.getScreenJpeg2FileName());
            applicationUpdateForm.setScreenJpeg3FileName(aimsApp.getScreenJpeg3FileName());
            applicationUpdateForm.setScreenJpeg4FileName(aimsApp.getScreenJpeg4FileName());
            applicationUpdateForm.setScreenJpeg5FileName(aimsApp.getScreenJpeg5FileName());
            applicationUpdateForm.setFlashDemoFileName(aimsApp.getFlashDemoFileName());
            applicationUpdateForm.setUserGuideFileName(aimsApp.getUserGuideFileName());
            applicationUpdateForm.setSplashScreenEpsFileName(aimsApp.getSplashScreenEpsFileName());
            applicationUpdateForm.setActiveScreenEpsFileName(aimsApp.getActiveScreenEpsFileName());
            applicationUpdateForm.setFaqDocFileName(aimsApp.getFaqDocFileName());
            applicationUpdateForm.setTestPlanResultsFileName(aimsApp.getTestPlanResultsFileName());

            Collection journalEntries = null;
            StringBuffer journalCombinedText = new StringBuffer();

            try
            {
                journalEntries = AimsApplicationsManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
            }
            catch (Exception ex)
            {
                journalEntries = null;
                log.debug("\n\nError in getting Journal Entries\n\n");
            }

            //Journal Entries 
            if (journalEntries != null)
            {
                for (Iterator it = journalEntries.iterator(); it.hasNext();)
                {
                    AimsJournalEntry aimsJournalEntry = (AimsJournalEntry) it.next();
                    if (aimsJournalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PRIVATE))
                        journalCombinedText.append("[Private] ");
                    else if (aimsJournalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PUBLIC))
                        journalCombinedText.append("[Public] ");
                    journalCombinedText.append(Utility.convertToString(aimsJournalEntry.getCreatedDate(), AimsConstants.DATE_TIME_FORMAT));
                    journalCombinedText.append(" : (");
                    journalCombinedText.append(aimsJournalEntry.getCreatedBy());
                    journalCombinedText.append(")\n");
                    journalCombinedText.append(aimsJournalEntry.getJournalText());
                    journalCombinedText.append("\n\n");
                }
            }
            applicationUpdateForm.setJournalCombinedText(journalCombinedText.toString());

            //Set VZW Specific Information					
            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN))
                {
                    applicationUpdateForm.setAimsVzwAppsContactId(aimsApp.getAimsVzwAppsContactId());
                    applicationUpdateForm.setAimsProductGroupId(aimsApp.getAimsProductGroupId());
                }

                if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION))
                {
                    applicationUpdateForm.setAppPriority(aimsApp.getAppPriority());
                    applicationUpdateForm.setTargetedProductionDate(Utility.convertToString(aimsApp.getTargetedProductionDate(), dateFormat));
                }

                //session.setAttribute(ManageApplicationsConstants.SESSION_VAR_CERTIFICATIONS, aimsApp.getCertifications());
                if ((platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue()))
                	if (ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING))
                		session.setAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES, aimsApp.getPhases());

            }
        }
    }

    /**
        * This class is called from UpdateAction classes. It contains common tasks to all applications
        *
        * @author Adnan Makda
        */
    public static String updateAction(
        HttpServletRequest request,
        String taskname,
        ApplicationUpdateForm applicationUpdateForm,
        AimsApp aimsApp,
        AimsContact aimsContact,
        Long platformId,
        String dateFormat)
        throws AimsException, AimsSecurityException
    {

        String forward = "view";
        HttpSession session = request.getSession();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();

        //CHECK ACCESS
        if (!(checkPlatformAccess(request, (String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME), platformId)))
            throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("edit"))
            if (!(checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();

        //END OF CHECK ACCESS

        if ((taskname.equalsIgnoreCase("addCertificate")) || (taskname.equalsIgnoreCase("removeCertificate")))
        {
            applicationUpdateForm.setTask((String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME));
            forward = "page3";
        }

        if ((taskname.equalsIgnoreCase("page1"))
            || (taskname.equalsIgnoreCase("page2"))
            || (taskname.equalsIgnoreCase("page3"))
            || (taskname.equalsIgnoreCase("page4"))
            || (taskname.equalsIgnoreCase("page5"))
            || (taskname.equalsIgnoreCase("page7"))
            || (taskname.equalsIgnoreCase("pageEntBOBO"))
            || (taskname.equalsIgnoreCase("pageEntLBS"))
            || (taskname.equalsIgnoreCase("submitpage4"))
            || (taskname.equalsIgnoreCase("journal"))
            || (taskname.equalsIgnoreCase("submitJournal"))
            || (taskname.equalsIgnoreCase("lbs_submit_toprod"))
            || (taskname.equalsIgnoreCase("lbs_submit_deprov")))
        {
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
            else if (taskname.equalsIgnoreCase("page7"))
                forward = "page7";    
            else if (taskname.equalsIgnoreCase("pageEntBOBO"))
                forward = "pageEntBOBO";
            else if (taskname.equalsIgnoreCase("pageEntLBS"))
                forward = "pageEntLBS";
            else if (taskname.equalsIgnoreCase("submitpage4"))
                forward = "page4";
            else if (taskname.equalsIgnoreCase("journal"))
                forward = "journalUpdate";
            else if (taskname.equalsIgnoreCase("submitJournal"))
                forward = "journalUpdate";
            else if (taskname.equalsIgnoreCase("lbs_submit_toprod"))
                forward = "page1";
            else if (taskname.equalsIgnoreCase("lbs_submit_deprov"))
                forward = "page1";

            if (taskname.equalsIgnoreCase("submitpage4"))
                applicationUpdateForm.setCurrentPage("page4");
            else if (taskname.equalsIgnoreCase("submitJournal"))
                applicationUpdateForm.setCurrentPage("journal");
            else if (taskname.equalsIgnoreCase("lbs_submit_toprod"))
                applicationUpdateForm.setCurrentPage("page1");
            else if (taskname.equalsIgnoreCase("lbs_submit_deprov"))
                applicationUpdateForm.setCurrentPage("page1");
            else
                applicationUpdateForm.setCurrentPage(taskname);

            applicationUpdateForm.setTask((String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME));

            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if ((taskname.equalsIgnoreCase("submitpage4")) || (taskname.equalsIgnoreCase("submitJournal")))
                {
                    StringBuffer journalCombinedText = new StringBuffer();
                    AimsJournalEntry aimsJournalEntry = new AimsJournalEntry();

                    aimsJournalEntry.setJournalText(applicationUpdateForm.getJournalText());
                    aimsJournalEntry.setJournalType(applicationUpdateForm.getJournalType()); //Temporary AMakda:
                    aimsJournalEntry.setCreatedBy(currUser);
                    aimsJournalEntry.setCreatedDate(new Date());
                    aimsJournalEntry.setAimsAppId(applicationUpdateForm.getAppsId());

                    try
                    {
                        AimsApplicationsManager.saveOrUpdateJournalEntries(aimsJournalEntry);
                    }
                    catch (Exception ex)
                    {
                        log.debug("Error while updating Journal Entries");
                    }
                    if (aimsJournalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PRIVATE))
                        journalCombinedText.append("[Private] ");
                    else if (aimsJournalEntry.getJournalType().equalsIgnoreCase(AimsConstants.JOURNAL_TYPE_PUBLIC))
                        journalCombinedText.append("[Public] ");
                    journalCombinedText.append(Utility.convertToString(aimsJournalEntry.getCreatedDate(), AimsConstants.DATE_TIME_FORMAT));
                    journalCombinedText.append(" : (");
                    journalCombinedText.append(aimsJournalEntry.getCreatedBy());
                    journalCombinedText.append(")\n");
                    journalCombinedText.append(aimsJournalEntry.getJournalText());
                    journalCombinedText.append("\n\n");
                    journalCombinedText.append(applicationUpdateForm.getJournalCombinedText());
                    applicationUpdateForm.setJournalCombinedText(journalCombinedText.toString());
                }
            }
        }

        if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
        {
            if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("clone")))
            {
                aimsApp.setAimsAllianceId(currentUserAllianceId);
                aimsApp.setAimsUserAppCreatedById(currUserId);
                aimsApp.setIfOnHold("N");
                aimsApp.setCreatedBy(currUser);
                aimsApp.setCreatedDate(new Date());
            }

            aimsApp.setTitle(applicationUpdateForm.getTitle());
            aimsApp.setVersion(applicationUpdateForm.getVersion());
            aimsApp.setShortDesc(applicationUpdateForm.getShortDesc());
            aimsApp.setLongDesc(applicationUpdateForm.getLongDesc());
            aimsApp.setLastUpdatedBy(currUser);
            aimsApp.setLastUpdatedDate(new Date());
            aimsApp.setAimsPlatformId(platformId);

            //set network usage (for urls)
            if( ( StringFuncs.isNullOrEmpty(applicationUpdateForm.getNetworkUsage()) )
                || (applicationUpdateForm.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE)) )
            {
                //if network usage is disabled or null then set app urls to null
                aimsApp.setNetworkUsage(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE);
                aimsApp.setAppNetworkURLs(null);
            }
            else if(applicationUpdateForm.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE))
            {
                //if network usage is enabled then create List of AimsAppNetworkUrls
                aimsApp.setNetworkUsage(applicationUpdateForm.getNetworkUsage());
                List networkUrlsList = ApplicationHelper.convertAppNetworkUrlArrayToList(applicationUpdateForm.getApplicationURLs());
                aimsApp.setAppNetworkURLs(networkUrlsList);
            }
            //export urls on edit screen
            if(!taskname.equalsIgnoreCase("create") && !taskname.equalsIgnoreCase("clone"))
            {
                //check access for EXPORT_CONTENT_FILTER_URLS
                request.setAttribute("exportUrlAllowed",
                        new Boolean((ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS))) );
            }

            //The following properties can only be updated by an Alliance user and not the Verizon user
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Start of check to see if alliance has been accepted
                Object[] userValues = null;
                String allianceStatus = null;
                Collection AimsAlliance = null;

                try
                {
                    AimsAlliance = AllianceManager.getAllianceDetails(aimsApp.getAimsAllianceId(), "");
                }
                catch (Exception ex)
                {
                    AimsException aimsException = new AimsException("Error");
                    aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                    throw aimsException;
                }

                for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
                {
                    userValues = (Object[]) iter.next();
                    allianceStatus = (String) userValues[3];
                }

                if (!(applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM)))
                {
                    if (!(allianceStatus.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED)))
                    {
                        AimsException aimsException = new AimsException("Error");
                        aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                        throw aimsException;
                    }
                }
                //End of check to see if alliance has been accepted

                aimsApp.setIfPrRelease(applicationUpdateForm.getIfPrRelease());
                aimsApp.setAppDeployments(applicationUpdateForm.getAppDeployments());

                //Contact
                aimsApp.setAimsContactId(applicationUpdateForm.getAimsContactId());
                if (applicationUpdateForm.getAimsContactId().longValue() == 0)
                {
                    aimsContact.setFirstName(applicationUpdateForm.getContactFirstName());
                    aimsContact.setLastName(applicationUpdateForm.getContactLastName());
                    aimsContact.setEmailAddress(applicationUpdateForm.getContactEmail());
                    aimsContact.setTitle(applicationUpdateForm.getContactTitle());
                    aimsContact.setPhone(applicationUpdateForm.getContactPhone());
                    aimsContact.setMobile(applicationUpdateForm.getContactMobile());
                    aimsContact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
                    aimsContact.setCreatedBy(currUser);
                    aimsContact.setCreatedDate(new Date());
                    aimsContact.setLastUpdatedBy(currUser);
                    aimsContact.setLastUpdatedDate(new Date());
                }

                //SubCategory
                if ((applicationUpdateForm.getAimsAppSubCategoryId() != null) && (applicationUpdateForm.getAimsAppSubCategoryId().longValue() != 0))
                    aimsApp.setAimsAppSubCategoryId(applicationUpdateForm.getAimsAppSubCategoryId());
            }

            //Set VZW Specific Information					
            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                if (checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN))
                {
                    if (applicationUpdateForm.getAimsVzwAppsContactId().longValue() == 0)
                        aimsApp.setAimsVzwAppsContactId(null);
                    else
                        aimsApp.setAimsVzwAppsContactId(applicationUpdateForm.getAimsVzwAppsContactId());

                    if(platformId.longValue() != AimsConstants.ENTERPRISE_PLATFORM_ID.longValue())//JMA application not allowed to save it
                    {
	                    if (applicationUpdateForm.getAimsProductGroupId().longValue() == 0)
	                        aimsApp.setAimsProductGroupId(null);
	                    else
	                        aimsApp.setAimsProductGroupId(applicationUpdateForm.getAimsProductGroupId());
                    }
                }

                if (checkAccess(request, taskname, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION))
                {
                    aimsApp.setAppPriority(applicationUpdateForm.getAppPriority());
                    aimsApp.setTargetedProductionDate(Utility.convertToDate(applicationUpdateForm.getTargetedProductionDate(), dateFormat));
                }
            }

            //Phase Indicator
            //BREW Excluded Here. Code moved to BrewApplicationUpdateAction
            if (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue())
            {
                if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                {
                    aimsApp.setSubmittedDate(new Date());
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
                }
                else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                {
                    //From Submit -> Assigned (Brew Excluded - As Brew is handled by Trigger {Submited->Evaluated->Under Testing})
                    if ((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue())
                        && (aimsApp.getAimsVzwAppsContactId() != null)
                        && (aimsApp.getAimsProductGroupId() != null)
                        && (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue()))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.ASSIGNED_ID);
                    }
                    //From Assigned -> Under Testing 
                    else if (
                        (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.ASSIGNED_ID.longValue())
                            && (aimsApp.getTargetedProductionDate() != null)
                            && (aimsApp.getAppPriority() != null))
                    {
                        aimsApp.setAimsLifecyclePhaseId(AimsConstants.TESTING_ID);
                    }
                }
                else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.ACCEPTANCE_ID);
                else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.REJECTED_ID);
            }

            //Set Apps ID to null if Cloning
            if (taskname.equalsIgnoreCase("clone"))
                aimsApp.setAppsId(null);

        }

        return forward;
    }

    /**
        * This class is called from UpdateAction classes. It contains common tasks to all applications
        *
        * @author Adnan Makda
        */
    public static ActionMessages postUpdateAction(
        HttpServletRequest request,
        String taskname,
        ApplicationUpdateForm applicationUpdateForm,
        AimsApp aimsApp,
        Long platformId,
        String dateFormat)
    {

        HttpSession session = request.getSession();
        applicationUpdateForm.setAppsId(aimsApp.getAppsId());
        session.setAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME, "edit");
        applicationUpdateForm.setTask("edit");
        applicationUpdateForm.setAimsLifecyclePhaseId(AimsApplicationsManager.getPhaseIdAfterUpdate(aimsApp.getAppsId()));

        //Set Temp File Ids to Zero
        applicationUpdateForm.setScreenJpegTempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg2TempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg3TempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg4TempFileId(new Long(0));
        applicationUpdateForm.setScreenJpeg5TempFileId(new Long(0));
        applicationUpdateForm.setFlashDemoTempFileId(new Long(0));
        applicationUpdateForm.setUserGuideTempFileId(new Long(0));
        applicationUpdateForm.setSplashScreenEpsTempFileId(new Long(0));
        applicationUpdateForm.setActiveScreenEpsTempFileId(new Long(0));
        applicationUpdateForm.setFaqDocTempFileId(new Long(0));
        applicationUpdateForm.setTestPlanResultsTempFileId(new Long(0));

        if (applicationUpdateForm.getTestResultFileIdList() != null)
        {
            for (int i = 0; i < applicationUpdateForm.getTestResultFileIdList().length; i++)
            {
                applicationUpdateForm.setTestResultFileId(i, new Long(0));
            }
        }

        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;

        if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM)){
        	if(platformId.equals(AimsConstants.ENTERPRISE_PLATFORM_ID))
        		message = new ActionMessage("message.manage.app.jma.saved");
        	else
        		message = new ActionMessage("message.manage.app.saved");
        }
        else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            message = new ActionMessage("message.manage.app.saved");
        else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            message = new ActionMessage("message.manage.app.saved");
        else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            message = new ActionMessage("message.manage.app.submitted");
        else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            message = new ActionMessage("message.manage.app.accepted");
        else if (applicationUpdateForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
            message = new ActionMessage("message.manage.app.rejected");

        messages.add(ActionMessages.GLOBAL_MESSAGE, message);

        return messages;
    }

    public static List convertAppNetworkUrlArrayToList(String [] urlValues)
    {
        ArrayList networkUrlsList = new ArrayList();
        AimsAppNetworkUrls appNetworkUrl = null;
        if(urlValues != null && urlValues.length>0)
        {
            for(int i=0; i<urlValues.length; i++)
            {
                if(!StringFuncs.isEmpty(urlValues[i]))
                {
                    appNetworkUrl = new AimsAppNetworkUrls();
                    appNetworkUrl.setAppNetworkUrlId(new Long(i));
                    appNetworkUrl.setAppNetworkUrl(urlValues[i]);

                    networkUrlsList.add(appNetworkUrl);
                }
            }
        }
        return networkUrlsList;
    }//end convertAppNetworkUrlArrayToList

    public static boolean checkAppNetworkUrlsChanged(String[] oldAppNetworkUrls, String[] newAppNetworkUrls)
    {
        boolean appNetworkUrlsChanged = false;

        if( (oldAppNetworkUrls == null || oldAppNetworkUrls.length==0)
            && (newAppNetworkUrls != null && newAppNetworkUrls.length>0))
        {   //if old is empty and new list is not empty
            appNetworkUrlsChanged = true;
        }
        else if((oldAppNetworkUrls != null && oldAppNetworkUrls.length>0)
            && (newAppNetworkUrls == null || newAppNetworkUrls.length==0) )
        {
            //if old is not empty and new list is empty
            appNetworkUrlsChanged = true;
        }
        else if((oldAppNetworkUrls == null && newAppNetworkUrls==null)
                || (oldAppNetworkUrls.length==0 && newAppNetworkUrls.length==0)) {
            appNetworkUrlsChanged = false;
        }            
        else if(oldAppNetworkUrls.length != newAppNetworkUrls.length)
        {
            //if length mismatch
            appNetworkUrlsChanged = true;
        }
        else if(oldAppNetworkUrls.length == newAppNetworkUrls.length)
        {
            //if both not empty, start comparision one by one
            int length = newAppNetworkUrls.length;
            for(int i=0; i<length; i++)
            {
                if(oldAppNetworkUrls[i].equals(newAppNetworkUrls[i]))
                {
                    appNetworkUrlsChanged = false;
                }
                else {
                    appNetworkUrlsChanged = true;
                    break;
                }
            }

        }
        return appNetworkUrlsChanged;
    }//end checkAppNetworkUrlsChanged

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
    
    // This method returns true if alliance has accepted atleast one 'active'  Contract
    public static boolean validateAllianceContract(Long aimsAllianceId, Long platformId, String currUserType) throws HibernateException {
        boolean contractPresent = false;
        Collection allianceContracts = null;        
        try {
            allianceContracts = AllianceManager.getAllianceAcceptedActiveContractsByPlatform(aimsAllianceId, platformId, currUserType);
            if(allianceContracts!=null && allianceContracts.size()>0) {
                contractPresent = true;
            }
        }//end try
        catch (HibernateException he) {
            System.out.println("ApplicationHelper.validateAllianceContract: exception occured while getting alliance accepted contracts.");
            log.error(he,he);
            throw he;
        }
        return contractPresent;
    }
    
    
}