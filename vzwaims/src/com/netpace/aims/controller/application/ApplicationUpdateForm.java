package com.netpace.aims.controller.application;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.bo.contacts.ContactsManager;
import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form	name="ApplicationUpdateForm"
 */
public class ApplicationUpdateForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(ApplicationUpdateForm.class.getName());

    protected java.lang.String tempForRadioIssue; //This property has been added only as a workaround for an issue with Special Characters and Radio Buttons

    protected java.lang.Long appsId;
    protected java.lang.String title;
    protected java.lang.String version;
    protected java.lang.String language;
    protected java.lang.String shortDesc;
    protected java.lang.String longDesc;
    protected java.lang.String appDeployments;
    protected java.lang.String ifPrRelease;
    protected java.lang.String appPriority;
    protected java.lang.String submittedDate;

    protected java.lang.String networkUsage;
    protected java.lang.String[] applicationURLs;

    protected java.lang.Long aimsUserAppCreatedById;
    protected java.lang.Long aimsLifecyclePhaseId;
    protected java.lang.String applicationStatus;
    protected java.lang.Long aimsAppCategoryId;
    protected java.util.Collection allCategories;
    protected java.lang.Long aimsAppSubCategoryId;
    protected java.util.Collection allSubCategories;
    protected java.lang.Long aimsAllianceId;
    protected java.lang.Long aimsPlatformId;
    protected java.lang.Long aimsContactId;
    protected java.util.Collection allContacts;
    
    protected java.lang.String contactFirstName;
    protected java.lang.String contactLastName;
    protected java.lang.String contactTitle;
    protected java.lang.String contactEmail;
    protected java.lang.String contactPhone;
    protected java.lang.String contactMobile;

    protected java.lang.String task;
    protected java.lang.String appSubmitType;
    protected java.lang.String currentPage;
    protected java.lang.String setupURL;
    protected java.lang.String updateURL;

    protected FormFile flashDemo;
    protected FormFile userGuide;
    protected FormFile screenJpeg;
    protected FormFile screenJpeg2;
    protected FormFile screenJpeg3;
    protected FormFile screenJpeg4;
    protected FormFile screenJpeg5;
    protected FormFile splashScreenEps;
    protected FormFile activeScreenEps;
    protected FormFile faqDoc;
    protected FormFile testPlanResults;

    protected java.lang.String screenJpegFileName;
    protected java.lang.String screenJpeg2FileName;
    protected java.lang.String screenJpeg3FileName;
    protected java.lang.String screenJpeg4FileName;
    protected java.lang.String screenJpeg5FileName;
    protected java.lang.String activeScreenEpsFileName;
    protected java.lang.String flashDemoFileName;
    protected java.lang.String splashScreenEpsFileName;
    protected java.lang.String userGuideFileName;
    protected java.lang.String faqDocFileName;
    protected java.lang.String testPlanResultsFileName;

    protected java.lang.Long flashDemoTempFileId;
    protected java.lang.Long userGuideTempFileId;
    protected java.lang.Long screenJpegTempFileId;
    protected java.lang.Long screenJpeg2TempFileId;
    protected java.lang.Long screenJpeg3TempFileId;
    protected java.lang.Long screenJpeg4TempFileId;
    protected java.lang.Long screenJpeg5TempFileId;
    protected java.lang.Long splashScreenEpsTempFileId;
    protected java.lang.Long activeScreenEpsTempFileId;
    protected java.lang.Long faqDocTempFileId;
    protected java.lang.Long testPlanResultsTempFileId;

    //VZW	Application	Management Parameters
    protected java.util.Collection allVzwContacts;
    protected java.lang.Long aimsVzwAppsContactId;
    protected java.util.Collection allProductGroups;
    protected java.lang.Long aimsProductGroupId;
    protected java.lang.String targetedProductionDate;
    protected java.util.Collection allCertifications;

    protected java.lang.Long addCertificationId;
    protected java.lang.String addCertificationDate;
    protected java.lang.Long[] removeCertificationId;

    protected java.lang.String[] testedDate;
    protected java.lang.String[] testComments;
    protected java.lang.String[] testStatus;
    protected FormFile[] testResultFile;
    protected java.lang.Long[] testResultFileId;
    protected java.lang.String[] testResultFileName;

    protected java.lang.String journalType;
    protected java.lang.String journalText;
    protected java.lang.String journalCombinedText;

    public java.lang.String getTempForRadioIssue()
    {
        return this.tempForRadioIssue;
    }

    public void setTempForRadioIssue(java.lang.String tempForRadioIssue)
    {
        this.tempForRadioIssue = tempForRadioIssue;
    }

    public java.lang.Long getAppsId()
    {
        return this.appsId;
    }

    public void setAppsId(java.lang.Long appsId)
    {
        this.appsId = appsId;
    }

    public java.lang.String getTitle()
    {
        return this.title;
    }

    public void setTitle(java.lang.String title)
    {
        this.title = title;
    }

    public java.lang.String getLanguage()
    {
        return this.language;
    }

    public void setLanguage(java.lang.String language)
    {
        this.language = language;
    }

    public java.lang.String getVersion()
    {
        return this.version;
    }

    public void setVersion(java.lang.String version)
    {
        this.version = version;
    }

    public java.lang.String getShortDesc()
    {
        return this.shortDesc;
    }

    public void setShortDesc(java.lang.String shortDesc)
    {
        this.shortDesc = shortDesc;
    }

    public java.lang.String getLongDesc()
    {
        return this.longDesc;
    }

    public void setLongDesc(java.lang.String longDesc)
    {
        this.longDesc = longDesc;
    }

    public java.lang.String getAppDeployments()
    {
        return this.appDeployments;
    }

    public void setAppDeployments(java.lang.String appDeployments)
    {
        this.appDeployments = appDeployments;
    }

    public FormFile getTestPlanResults()
    {
        return this.testPlanResults;
    }

    public void setTestPlanResults(FormFile testPlanResults)
    {
        this.testPlanResults = testPlanResults;
    }

    public java.lang.String getIfPrRelease()
    {
        return this.ifPrRelease;
    }

    public void setIfPrRelease(java.lang.String ifPrRelease)
    {
        this.ifPrRelease = ifPrRelease;
    }

    public java.lang.String getAppPriority()
    {
        return this.appPriority;
    }

    public void setAppPriority(java.lang.String appPriority)
    {
        this.appPriority = appPriority;
    }

    public java.lang.String getSubmittedDate()
    {
        return this.submittedDate;
    }

    public void setSubmittedDate(java.lang.String submittedDate)
    {
        this.submittedDate = submittedDate;
    }

    public java.lang.Long getAimsUserAppCreatedById()
    {
        return this.aimsUserAppCreatedById;
    }

    public void setAimsUserAppCreatedById(java.lang.Long aimsUserAppCreatedById)
    {
        this.aimsUserAppCreatedById = aimsUserAppCreatedById;
    }

    public java.lang.Long getAimsLifecyclePhaseId()
    {
        return this.aimsLifecyclePhaseId;
    }

    public void setAimsLifecyclePhaseId(java.lang.Long aimsLifecyclePhaseId)
    {
        this.aimsLifecyclePhaseId = aimsLifecyclePhaseId;
    }
    
    public java.lang.String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(java.lang.String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

    public java.lang.Long getAimsAppCategoryId()
    {
        return this.aimsAppCategoryId;
    }

    public void setAimsAppCategoryId(java.lang.Long aimsAppCategoryId)
    {
        this.aimsAppCategoryId = aimsAppCategoryId;
    }

    public java.util.Collection getAllCategories()
    {
        return this.allCategories;
    }

    public void setAllCategories(java.util.Collection allCategories)
    {
        this.allCategories = allCategories;
    }

    public java.lang.Long getAimsAppSubCategoryId()
    {
        return this.aimsAppSubCategoryId;
    }

    public void setAimsAppSubCategoryId(java.lang.Long aimsAppSubCategoryId)
    {
        this.aimsAppSubCategoryId = aimsAppSubCategoryId;
    }

    public java.util.Collection getAllSubCategories()
    {
        return this.allSubCategories;
    }

    public void setAllSubCategories(java.util.Collection allSubCategories)
    {
        this.allSubCategories = allSubCategories;
    }

    public java.lang.Long getAimsAllianceId()
    {
        return this.aimsAllianceId;
    }

    public void setAimsAllianceId(java.lang.Long aimsAllianceId)
    {
        this.aimsAllianceId = aimsAllianceId;
    }

    public java.lang.Long getAimsPlatformId()
    {
        return this.aimsPlatformId;
    }

    public void setAimsPlatformId(java.lang.Long aimsPlatformId)
    {
        this.aimsPlatformId = aimsPlatformId;
    }

    public java.lang.Long getAimsContactId()
    {
        return this.aimsContactId;
    }

    public void setAimsContactId(java.lang.Long aimsContactId)
    {
        this.aimsContactId = aimsContactId;
    }

    public java.util.Collection getAllContacts()
    {
        return this.allContacts;
    }

    public void setAllContacts(java.util.Collection allContacts)
    {
        this.allContacts = allContacts;
    }

    public java.lang.String getContactFirstName()
    {
        return this.contactFirstName;
    }

    public void setContactFirstName(java.lang.String contactFirstName)
    {
        this.contactFirstName = contactFirstName;
    }

    public java.lang.String getContactLastName()
    {
        return this.contactLastName;
    }

    public void setContactLastName(java.lang.String contactLastName)
    {
        this.contactLastName = contactLastName;
    }

    public java.lang.String getContactTitle()
    {
        return this.contactTitle;
    }

    public void setContactTitle(java.lang.String contactTitle)
    {
        this.contactTitle = contactTitle;
    }

    public java.lang.String getContactEmail()
    {
        return this.contactEmail;
    }

    public void setContactEmail(java.lang.String contactEmail)
    {
        this.contactEmail = contactEmail;
    }

    public java.lang.String getContactPhone()
    {
        return this.contactPhone;
    }

    public void setContactPhone(java.lang.String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    public java.lang.String getContactMobile()
    {
        return this.contactMobile;
    }

    public void setContactMobile(java.lang.String contactMobile)
    {
        this.contactMobile = contactMobile;
    }

    public java.lang.String getTask()
    {
        return this.task;
    }

    public void setTask(java.lang.String task)
    {
        this.task = task;
    }

    public String getAppSubmitType()
    {
        return this.appSubmitType;
    }

    public void setAppSubmitType(String appSubmitType)
    {
        this.appSubmitType = appSubmitType;
    }

    public String getCurrentPage()
    {
        return this.currentPage;
    }

    public void setCurrentPage(String currentPage)
    {
        this.currentPage = currentPage;
    }

    public String getSetupURL()
    {
        return this.setupURL;
    }

    public void setSetupURL(String setupURL)
    {
        this.setupURL = setupURL;
    }

    public String getUpdateURL()
    {
        return this.updateURL;
    }

    public void setUpdateURL(String updateURL)
    {
        this.updateURL = updateURL;
    }

    public FormFile getFlashDemo()
    {
        return this.flashDemo;
    }

    public void setFlashDemo(FormFile flashDemo)
    {
        this.flashDemo = flashDemo;
    }

    public FormFile getUserGuide()
    {
        return this.userGuide;
    }

    public void setUserGuide(FormFile userGuide)
    {
        this.userGuide = userGuide;
    }

    public FormFile getScreenJpeg()
    {
        return this.screenJpeg;
    }

    public void setScreenJpeg(FormFile screenJpeg)
    {
        this.screenJpeg = screenJpeg;
    }

    public FormFile getScreenJpeg2()
    {
        return this.screenJpeg2;
    }

    public void setScreenJpeg2(FormFile screenJpeg2)
    {
        this.screenJpeg2 = screenJpeg2;
    }

    public FormFile getScreenJpeg3()
    {
        return this.screenJpeg3;
    }

    public void setScreenJpeg3(FormFile screenJpeg3)
    {
        this.screenJpeg3 = screenJpeg3;
    }

    public FormFile getScreenJpeg4()
    {
        return this.screenJpeg4;
    }

    public void setScreenJpeg4(FormFile screenJpeg4)
    {
        this.screenJpeg4 = screenJpeg4;
    }

    public FormFile getScreenJpeg5()
    {
        return this.screenJpeg5;
    }

    public void setScreenJpeg5(FormFile screenJpeg5)
    {
        this.screenJpeg5 = screenJpeg5;
    }
    public FormFile getSplashScreenEps()
    {
        return this.splashScreenEps;
    }

    public void setSplashScreenEps(FormFile splashScreenEps)
    {
        this.splashScreenEps = splashScreenEps;
    }

    public FormFile getActiveScreenEps()
    {
        return this.activeScreenEps;
    }

    public void setActiveScreenEps(FormFile activeScreenEps)
    {
        this.activeScreenEps = activeScreenEps;
    }

    public FormFile getFaqDoc()
    {
        return this.faqDoc;
    }

    public void setFaqDoc(FormFile faqDoc)
    {
        this.faqDoc = faqDoc;
    }

    public java.lang.String getScreenJpegFileName()
    {
        return this.screenJpegFileName;
    }

    public void setScreenJpegFileName(java.lang.String screenJpegFileName)
    {
        this.screenJpegFileName = screenJpegFileName;
    }

    public java.lang.String getScreenJpeg2FileName()
    {
        return this.screenJpeg2FileName;
    }

    public void setScreenJpeg2FileName(java.lang.String screenJpeg2FileName)
    {
        this.screenJpeg2FileName = screenJpeg2FileName;
    }

    public java.lang.String getScreenJpeg3FileName()
    {
        return this.screenJpeg3FileName;
    }

    public void setScreenJpeg3FileName(java.lang.String screenJpeg3FileName)
    {
        this.screenJpeg3FileName = screenJpeg3FileName;
    }

    public java.lang.String getScreenJpeg4FileName()
    {
        return this.screenJpeg4FileName;
    }

    public void setScreenJpeg4FileName(java.lang.String screenJpeg4FileName)
    {
        this.screenJpeg4FileName = screenJpeg4FileName;
    }

    public java.lang.String getScreenJpeg5FileName()
    {
        return this.screenJpeg5FileName;
    }

    public void setScreenJpeg5FileName(java.lang.String screenJpeg5FileName)
    {
        this.screenJpeg5FileName = screenJpeg5FileName;
    }

    public java.lang.String getActiveScreenEpsFileName()
    {
        return this.activeScreenEpsFileName;
    }

    public void setActiveScreenEpsFileName(java.lang.String activeScreenEpsFileName)
    {
        this.activeScreenEpsFileName = activeScreenEpsFileName;
    }

    public java.lang.String getFlashDemoFileName()
    {
        return this.flashDemoFileName;
    }

    public void setFlashDemoFileName(java.lang.String flashDemoFileName)
    {
        this.flashDemoFileName = flashDemoFileName;
    }

    public java.lang.String getSplashScreenEpsFileName()
    {
        return this.splashScreenEpsFileName;
    }

    public void setSplashScreenEpsFileName(java.lang.String splashScreenEpsFileName)
    {
        this.splashScreenEpsFileName = splashScreenEpsFileName;
    }

    public java.lang.String getUserGuideFileName()
    {
        return this.userGuideFileName;
    }

    public void setUserGuideFileName(java.lang.String userGuideFileName)
    {
        this.userGuideFileName = userGuideFileName;
    }

    public java.lang.String getFaqDocFileName()
    {
        return this.faqDocFileName;
    }

    public void setFaqDocFileName(java.lang.String faqDocFileName)
    {
        this.faqDocFileName = faqDocFileName;
    }

    public java.lang.String getTestPlanResultsFileName()
    {
        return this.testPlanResultsFileName;
    }

    public void setTestPlanResultsFileName(java.lang.String testPlanResultsFileName)
    {
        this.testPlanResultsFileName = testPlanResultsFileName;
    }

    public java.lang.Long getFlashDemoTempFileId()
    {
        return this.flashDemoTempFileId;
    }

    public void setFlashDemoTempFileId(java.lang.Long flashDemoTempFileId)
    {
        this.flashDemoTempFileId = flashDemoTempFileId;
    }

    public java.lang.Long getUserGuideTempFileId()
    {
        return this.userGuideTempFileId;
    }

    public void setUserGuideTempFileId(java.lang.Long userGuideTempFileId)
    {
        this.userGuideTempFileId = userGuideTempFileId;
    }

    public java.lang.Long getScreenJpegTempFileId()
    {
        return this.screenJpegTempFileId;
    }

    public void setScreenJpegTempFileId(java.lang.Long screenJpegTempFileId)
    {
        this.screenJpegTempFileId = screenJpegTempFileId;
    }

    public java.lang.Long getScreenJpeg2TempFileId()
    {
        return this.screenJpeg2TempFileId;
    }

    public void setScreenJpeg2TempFileId(java.lang.Long screenJpeg2TempFileId)
    {
        this.screenJpeg2TempFileId = screenJpeg2TempFileId;
    }

    public java.lang.Long getScreenJpeg3TempFileId()
    {
        return this.screenJpeg3TempFileId;
    }

    public void setScreenJpeg3TempFileId(java.lang.Long screenJpeg3TempFileId)
    {
        this.screenJpeg3TempFileId = screenJpeg3TempFileId;
    }

    public java.lang.Long getScreenJpeg4TempFileId()
    {
        return this.screenJpeg4TempFileId;
    }

    public void setScreenJpeg4TempFileId(java.lang.Long screenJpeg4TempFileId)
    {
        this.screenJpeg4TempFileId = screenJpeg4TempFileId;
    }

    public java.lang.Long getScreenJpeg5TempFileId()
    {
        return this.screenJpeg5TempFileId;
    }

    public void setScreenJpeg5TempFileId(java.lang.Long screenJpeg5TempFileId)
    {
        this.screenJpeg5TempFileId = screenJpeg5TempFileId;
    }

    public java.lang.Long getSplashScreenEpsTempFileId()
    {
        return this.splashScreenEpsTempFileId;
    }

    public void setSplashScreenEpsTempFileId(java.lang.Long splashScreenEpsTempFileId)
    {
        this.splashScreenEpsTempFileId = splashScreenEpsTempFileId;
    }

    public java.lang.Long getActiveScreenEpsTempFileId()
    {
        return this.activeScreenEpsTempFileId;
    }

    public void setActiveScreenEpsTempFileId(java.lang.Long activeScreenEpsTempFileId)
    {
        this.activeScreenEpsTempFileId = activeScreenEpsTempFileId;
    }

    public java.lang.Long getFaqDocTempFileId()
    {
        return this.faqDocTempFileId;
    }

    public void setFaqDocTempFileId(java.lang.Long faqDocTempFileId)
    {
        this.faqDocTempFileId = faqDocTempFileId;
    }

    public java.lang.Long getTestPlanResultsTempFileId()
    {
        return this.testPlanResultsTempFileId;
    }

    public void setTestPlanResultsTempFileId(java.lang.Long testPlanResultsTempFileId)
    {
        this.testPlanResultsTempFileId = testPlanResultsTempFileId;
    }

    public java.lang.Long getAimsVzwAppsContactId()
    {
        return this.aimsVzwAppsContactId;
    }

    public void setAimsVzwAppsContactId(java.lang.Long aimsVzwAppsContactId)
    {
        this.aimsVzwAppsContactId = aimsVzwAppsContactId;
    }

    public java.lang.Long getAimsProductGroupId()
    {
        return this.aimsProductGroupId;
    }

    public void setAimsProductGroupId(java.lang.Long aimsProductGroupId)
    {
        this.aimsProductGroupId = aimsProductGroupId;
    }

    public java.lang.String getTargetedProductionDate()
    {
        return this.targetedProductionDate;
    }

    public void setTargetedProductionDate(java.lang.String targetedProductionDate)
    {
        this.targetedProductionDate = targetedProductionDate;
    }

    public java.util.Collection getAllVzwContacts()
    {
        return this.allVzwContacts;
    }

    public void setAllVzwContacts(java.util.Collection allVzwContacts)
    {
        this.allVzwContacts = allVzwContacts;
    }

    public java.util.Collection getAllProductGroups()
    {
        return this.allProductGroups;
    }

    public void setAllProductGroups(java.util.Collection allProductGroups)
    {
        this.allProductGroups = allProductGroups;
    }

    public java.util.Collection getAllCertifications()
    {
        return this.allCertifications;
    }

    public void setAllCertifications(java.util.Collection allCertifications)
    {
        this.allCertifications = allCertifications;
    }

    public java.lang.Long getAddCertificationId()
    {
        return this.addCertificationId;
    }

    public void setAddCertificationId(java.lang.Long addCertificationId)
    {
        this.addCertificationId = addCertificationId;
    }

    public java.lang.String getAddCertificationDate()
    {
        return this.addCertificationDate;
    }

    public void setAddCertificationDate(java.lang.String addCertificationDate)
    {
        this.addCertificationDate = addCertificationDate;
    }

    public java.lang.Long[] getRemoveCertificationId()
    {
        return this.removeCertificationId;
    }

    public void setRemoveCertificationId(java.lang.Long[] lngInput)
    {
        this.removeCertificationId = lngInput;
    }

    public java.lang.String getTestedDate(int index)
    {
        return this.testedDate[index];
    }

    public void setTestedDate(int index, java.lang.String testedDate)
    {
        this.testedDate[index] = testedDate;
    }

    public java.lang.String getTestComments(int index)
    {
        return this.testComments[index];
    }

    public void setTestComments(int index, java.lang.String testComments)
    {

        this.testComments[index] = testComments;
    }

    public java.lang.String getTestStatus(int index)
    {
        return this.testStatus[index];
    }

    public void setTestStatus(int index, java.lang.String testStatus)
    {
        this.testStatus[index] = testStatus;
    }

    public FormFile getTestResultFile(int index)
    {
        return this.testResultFile[index];
    }

    public void setTestResultFile(int index, FormFile testResultFile)
    {
        this.testResultFile[index] = testResultFile;
    }

    public java.lang.Long[] getTestResultFileIdList()
    {
        return this.testResultFileId;
    }

    public java.lang.Long getTestResultFileId(int index)
    {
        return this.testResultFileId[index];
    }

    public void setTestResultFileId(int index, java.lang.Long testResultFileId)
    {
        this.testResultFileId[index] = testResultFileId;
    }

    public java.lang.String getTestResultFileName(int index)
    {
        return this.testResultFileName[index];
    }

    public void setTestResultFileName(int index, java.lang.String testResultFileName)
    {
        this.testResultFileName[index] = testResultFileName;
    }

    public java.lang.String getJournalType()
    {
        return this.journalType;
    }

    public void setJournalType(java.lang.String journalType)
    {
        this.journalType = journalType;
    }

    public java.lang.String getJournalText()
    {
        return this.journalText;
    }

    public void setJournalText(java.lang.String journalText)
    {
        this.journalText = journalText;
    }

    public java.lang.String getJournalCombinedText()
    {
        return this.journalCombinedText;
    }

    public void setJournalCombinedText(java.lang.String journalCombinedText)
    {
        this.journalCombinedText = journalCombinedText;
    }

    public String getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(String networkUsage) {
        this.networkUsage = networkUsage;
    }

    public String[] getApplicationURLs() {
        return applicationURLs;
    }

    public void setApplicationURLs(String[] applicationURLs) {
        this.applicationURLs = applicationURLs;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request, Long platformId)
    {

        log.debug("RESET called	in ApplicationUpdateForm ");
        HttpSession session = request.getSession();
        Long appOwnerAllianceId = null;
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String taskName = (String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME);

        if (taskName == null)
        {
            taskName = "edit"; //:AM:	To remove	only to	make it	compatible with	other	apps not synced	yet	to the new design
            log.debug("\n\n\nTemp	AM:	compatibility	working	in reset");
        }

        if (((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE))
        {
            log.debug("\nSetting appOwnerAllianceId for Alliance User");
            appOwnerAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        }
        else if (((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE))
        {
            log.debug("\nSetting appOwnerAllianceId for VZW User");
            if (request.getParameter("appsId") != null)
            {
                appOwnerAllianceId = AimsApplicationsManager.getAllianceIdOfApplication(new Long(request.getParameter("appsId")));
                session.setAttribute(ManageApplicationsConstants.SESSION_VAR_OWNER_ALLIANCE_ID, appOwnerAllianceId);
            }
            if (appOwnerAllianceId == null)
            {
                //This means it is NOT the first hit to this module, and the Session is already populated
                log.debug("\n\nThis means it is NOT the first hit to the page and the Session is populated\n\n");
                appOwnerAllianceId = (Long) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_OWNER_ALLIANCE_ID);
            }
        }
        log.debug("\n\nappOwnerAllianceId: " + appOwnerAllianceId);

        try
        {
            //Checking for Access	to 'Manage App'	and	'Journal'	Tab.
            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {
                //Check for Access to Testing Section
                if (ApplicationHelper.checkAccess(request, taskName, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING)
                		&&(platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue()))
                {
                    Set testPhaseSet = (Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES);

                    if (testPhaseSet != null)
                    {

                        //Fills	From Session ->	into Array
                        this.testedDate = new String[testPhaseSet.size()];
                        this.testStatus = new String[testPhaseSet.size()];
                        this.testComments = new String[testPhaseSet.size()];
                        this.testResultFile = new FormFile[testPhaseSet.size()];
                        this.testResultFileId = new Long[testPhaseSet.size()];
                        this.testResultFileName = new String[testPhaseSet.size()];

                        int index = 0;

                        for (java.util.Iterator itr = testPhaseSet.iterator(); itr.hasNext();)
                        {
                            AimsAppPhase aap = (AimsAppPhase) itr.next();
                            this.testedDate[index] = Utility.convertToString(aap.getTestedDate(), AimsConstants.DATE_FORMAT);
                            this.testStatus[index] = aap.getStatus();
                            this.testComments[index] = aap.getComments();
                            this.testResultFileName[index] = aap.getResultsFileName();
                            try
                            {
                                this.testResultFileId[index] = new Long(aap.getTempFileId());
                            }
                            catch (Exception ex)
                            {
                                this.testResultFileId[index] = new Long(0);
                            }
                            index++;
                        }
                    }
                }

                /****************************** old implementation ***********************/
                //TODO: Commented out by amakda during Merge
                /*
                                           if(platformId.longValue()==AimsConstants.ENTERPRISE_PLATFORM_ID.longValue()){
                                        	this.allVzwContacts = AimsApplicationsManager.getContactsByStatus(null,AimsConstants.USER_STATUS_ACTIVE);
                                        }
                                        else
                                        {
                                        	this.allVzwContacts = AimsApplicationsManager.getContacts(null);
                                        }
                                        */
                //End of TODO
                
                //TODO: amakda. Following 3 lines added by Sajjad
                //this.allVzwContacts = AimsApplicationsManager.getContacts(null);
                //use vzw contacts, get active vzw user contacts for enterprise platform
                //this.allVzwContacts = ContactsManager.getAllVZWContacts();
                /****************************** end old implementation ***********************/

                /********** new implementation after jma-vzdn merge *********/
                if(platformId.longValue()==AimsConstants.ENTERPRISE_PLATFORM_ID.longValue()) {
                    //get active user contacts for enterprise platform
                    this.allVzwContacts = ContactsManager.getAllVZWContacts(AimsConstants.USER_STATUS_ACTIVE);
                }
                else {
                    //get all vzw user contacts for other platforms (both active and deleted)
                    this.allVzwContacts = ContactsManager.getAllVZWContacts(null);
                }
                /********** end new implementation after jma-vzdn merge *********/

                this.allProductGroups = AimsApplicationsManager.getProductGroups();
                //this.allCertifications = AimsApplicationsManager.getCertifications(platformId);
            }
            //End	of Checking	for	Access to	'Manage	App' and 'Journal' Tab.

            this.allCategories = AimsApplicationsManager.getCategories(platformId);
            this.allSubCategories = AimsApplicationsManager.getSubCategories();
            this.allContacts = AimsApplicationsManager.getContacts(appOwnerAllianceId);

            request.setAttribute("ApplicationUpdateForm", this);
        }
        catch (Exception ex)
        {
            log.debug("Exception in	RESET: " + ex);
        }


    }

    public void validate(ActionMapping mapping, HttpServletRequest request, ActionErrors errors)
    {
        validate(mapping, request, errors, new Long(0));
    }

    public void validate(ActionMapping mapping, HttpServletRequest request, ActionErrors errors, Long platformId)
    {
        HttpSession session = request.getSession();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        //Save Certifications	and	Test Phases	Information
        saveCollections(mapping, request, errors, platformId);
        validateCollections(mapping, request, errors, platformId);

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {

            //if (this.isBlankString(this.language))

            //	errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.manage.app.language.required"));
            if (this.isBlankString(this.title))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appTitle.required"));

            if (this.isBlankString(this.shortDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.required"));
            else if (this.shortDesc.length() > 200)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appShortDesc.length"));

            if (this.isBlankString(this.longDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.required"));
            else if (this.longDesc.length() > 500)
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appLongDesc.length"));

            if (platformId.longValue() != AimsConstants.ENTERPRISE_PLATFORM_ID.longValue())
            {
                if (!this.isDropDownSelected(this.aimsAppSubCategoryId))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appSubCategory.required"));
            }

            if ((platformId.longValue() != AimsConstants.ENTERPRISE_PLATFORM_ID.longValue())
                && (platformId.longValue() != AimsConstants.MMS_PLATFORM_ID.longValue())
                && (platformId.longValue() != AimsConstants.SMS_PLATFORM_ID.longValue()))
            {
                if (this.isBlankString(this.version))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appVersion.required"));
            }

            //Network usage validation
                if(!(this.isBlankString(this.networkUsage)) && this.networkUsage.equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE))
                {
                    //application urls validation, if network usage is enabled then user must enter atleast one url
                    if(this.applicationURLs==null || this.applicationURLs.length==0)
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.url.required"));                        
                    }
                    /************** Skip URL patterm validation
                        else
                        {
                            //URL Validation for valid URL pattern
                            for(int i=0; i<this.applicationURLs.length; i++)
                            {
                                if (!(this.isBlankString(this.applicationURLs[i])))
                                    if (!(this.isValidURL(this.applicationURLs[i])))
                                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.url.invalid"));
                            }
                        }
                    ***************/
                }
            //End Network usage validation

            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Email	Validation
                if (!this.isDropDownSelected(this.aimsContactId))
                {
                    if (!this.isBlankString(this.contactEmail))
                        if (!this.isValidEmail(this.contactEmail))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.email", this.contactEmail));
                }

                //Start	of File	Type Validations

                //Taking out validation of Flash Demo field from here to BrewApplicationUpdtaeForm.java (New requirement: Only swf file)
                if (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue())
                {
                    if (!(this.isBlankString(this.flashDemoFileName)))
                        if (!(this.isValidFileType(this.flashDemoFileName, AimsConstants.FILE_TYPE_FLASH)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFlashDemo.file.type"));
                }

                //Taking this particular Validation For BREW out of here and to the BrewApplicationUpdateForm.java
                //In BrewApplicationUpdateForm.java we will check to see if the User Guide uploaded has the postfix '_template.doc'
                if (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue())
                {
                    if (!(this.isBlankString(this.userGuideFileName)))
                        if (!(this.isValidFileType(this.userGuideFileName, AimsConstants.FILE_TYPE_DOC)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appUserGuide.file.type"));

                    if (!(this.isBlankString(this.testPlanResultsFileName)))
                        if (!(this.isValidFileType(this.testPlanResultsFileName, AimsConstants.FILE_TYPE_DOC)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appTestPlan.file.type"));                    
                }

                if (!(this.isBlankString(this.faqDocFileName)))
                    if (!(this.isValidFileType(this.faqDocFileName, AimsConstants.FILE_TYPE_DOC)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFAQ.file.type"));

                if (!(this.isBlankString(this.screenJpegFileName)))
                    if (!(this.isValidFileType(this.screenJpegFileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.file.type"));

                if (!(this.isBlankString(this.screenJpeg2FileName)))
                    if (!(this.isValidFileType(this.screenJpeg2FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.file.type"));

                if (!(this.isBlankString(this.screenJpeg3FileName)))
                    if (!(this.isValidFileType(this.screenJpeg3FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.file.type"));

                if (!(this.isBlankString(this.screenJpeg4FileName)))
                    if (!(this.isValidFileType(this.screenJpeg4FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.file.type"));

                if (!(this.isBlankString(this.screenJpeg5FileName)))
                    if (!(this.isValidFileType(this.screenJpeg5FileName, AimsConstants.FILE_TYPE_SCREEN_SHOT)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.file.type"));
                if (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue()) {
	                if (!(this.isBlankString(this.splashScreenEpsFileName)))
	                    if (!(this.isValidFileType(this.splashScreenEpsFileName, AimsConstants.FILE_TYPE_EPS)))
	                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appSplashScreen.file.type"));
	
	                if (!(this.isBlankString(this.activeScreenEpsFileName)))
	                    if (!(this.isValidFileType(this.activeScreenEpsFileName, AimsConstants.FILE_TYPE_EPS)))
	                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appActiveScreen.file.type"));
                }

                //End of File	Type Validations
            }
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
            //The	following	properties will	be validated for an	can	only be	updated	by an	Alliance user	and	not	the	Verizon	user
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Taking the Validation on Submit For BREW out of here and to the BrewApplicationUpdateForm.java
                if (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue())
                {
                    if ((this.ifPrRelease == null) || (!(this.ifPrRelease.equals("Y"))))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.prRelease.accept"));

                    if (this.isBlankString(this.appDeployments))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appDeployments.required"));

                    if (platformId.longValue() != AimsConstants.ENTERPRISE_PLATFORM_ID.longValue())
                    {
                        //Start	of Files (NULL) Validation

                        if (platformId.longValue() != AimsConstants.SMS_PLATFORM_ID.longValue())
                        {
                            if (((this.flashDemo == null) || !(this.flashDemo.getFileSize() > 0)) && (this.isBlankString(this.flashDemoFileName)))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFlashDemo.required"));

                            if (((this.screenJpeg == null) || !(this.screenJpeg.getFileSize() > 0)) && (this.isBlankString(this.screenJpegFileName)))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.required"));

                            if (((this.splashScreenEps == null) || !(this.splashScreenEps.getFileSize() > 0))
                                && (this.isBlankString(this.splashScreenEpsFileName)))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appSplashScreen.required"));

                            if (((this.activeScreenEps == null) || !(this.activeScreenEps.getFileSize() > 0))
                                && (this.isBlankString(this.activeScreenEpsFileName)))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appActiveScreen.required"));
                        }

                        if (((this.userGuide == null) || !(this.userGuide.getFileSize() > 0)) && (this.isBlankString(this.userGuideFileName)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appUserGuide.required"));

                        if (((this.faqDoc == null) || !(this.faqDoc.getFileSize() > 0)) && (this.isBlankString(this.faqDocFileName)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFAQ.required"));

                        if (((this.testPlanResults == null) || !(this.testPlanResults.getFileSize() > 0))
                            && (this.isBlankString(this.testPlanResultsFileName)))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appTestPlan.required"));

                        //End of Files (NULL) Validation

                        //Contacts
                        if (!this.isDropDownSelected(this.aimsContactId))
                        {
                            if (this.isBlankString(this.contactFirstName))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactFirstName.required"));
                            if (this.isBlankString(this.contactLastName))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactLastName.required"));
                            if (this.isBlankString(this.contactTitle))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactTitle.required"));
                            if (this.isBlankString(this.contactEmail))
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.required"));
                        }
                    }
                }
            }
        }
    }

    public void saveTempFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors, Long platformId)
    {
        log.debug("\n\nIn	saveTempFiles	of ApplicationUpdateForm:	\n\n");
        TempFile tempFile = null;
        HttpSession session = request.getSession();

        String imageFileSize = (String) session.getAttribute(AimsConstants.IMAGE_FILE_SIZE);
        String docFileSize = (String) session.getAttribute(AimsConstants.DOC_FILE_SIZE);
        String epsFileSize = (String) session.getAttribute(AimsConstants.EPS_FILE_SIZE);
        String jpegFileSize = (String) session.getAttribute(AimsConstants.JPEG_FILE_SIZE);
        String flashFileSize = (String) session.getAttribute(AimsConstants.FLASH_FILE_SIZE);
        String presentationFileSize = (String) session.getAttribute(AimsConstants.PRESENTATION_FILE_SIZE);
        String screenShotFileSize = (String) session.getAttribute(AimsConstants.SCREEN_SHOT_FILE_SIZE);
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);

        log.debug("	Image	File	Size : " + imageFileSize);
        log.debug("	Doc	File	Size : " + docFileSize);

        try
        {
            if ((this.screenJpeg != null) && (this.screenJpeg.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.fileSize.exceeded"));
                this.screenJpeg.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpegTempFileId = tempFile.getFileId();
                    this.screenJpegFileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg2 != null) && (this.screenJpeg2.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg2.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.fileSize.exceeded"));
                this.screenJpeg2.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg2, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg2TempFileId = tempFile.getFileId();
                    this.screenJpeg2FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg3 != null) && (this.screenJpeg3.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg3.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.fileSize.exceeded"));
                this.screenJpeg3.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg3, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg3TempFileId = tempFile.getFileId();
                    this.screenJpeg3FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg4 != null) && (this.screenJpeg4.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg4.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.fileSize.exceeded"));
                this.screenJpeg4.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg4, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg4TempFileId = tempFile.getFileId();
                    this.screenJpeg4FileName = tempFile.getFileName();
                }
            }

            if ((this.screenJpeg5 != null) && (this.screenJpeg5.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.screenJpeg5.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.fileSize.exceeded"));
                this.screenJpeg5.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.screenJpeg5, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.screenJpeg5TempFileId = tempFile.getFileId();
                    this.screenJpeg5FileName = tempFile.getFileName();
                }
            }

            if ((this.flashDemo != null) && (this.flashDemo.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.flashDemo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFlashDemo.fileSize.exceeded"));
                this.flashDemo.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.flashDemo, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.flashDemoTempFileId = tempFile.getFileId();
                    this.flashDemoFileName = tempFile.getFileName();
                }
            }

            if (((this.userGuide != null) && (this.userGuide.getFileSize() > 0)) && !(this.isValidFileSize(fileSize, this.userGuide.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appUserGuide.fileSize.exceeded"));
                this.userGuide.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.userGuide, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.userGuideTempFileId = tempFile.getFileId();
                    this.userGuideFileName = tempFile.getFileName();
                }
            }

            if ((this.splashScreenEps != null)
                && (this.splashScreenEps.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.splashScreenEps.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appScreenShot.fileSize.exceeded"));
                this.splashScreenEps.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.splashScreenEps, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.splashScreenEpsTempFileId = tempFile.getFileId();
                    this.splashScreenEpsFileName = tempFile.getFileName();
                }
            }

            if ((this.activeScreenEps != null)
                && (this.activeScreenEps.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.activeScreenEps.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appActiveScreen.fileSize.exceeded"));
                this.activeScreenEps.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.activeScreenEps, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.activeScreenEpsTempFileId = tempFile.getFileId();
                    this.activeScreenEpsFileName = tempFile.getFileName();
                }
            }

            if ((this.faqDoc != null) && (this.faqDoc.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.faqDoc.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appFAO.fileSize.exceeded"));
                this.faqDoc.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.faqDoc, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.faqDocTempFileId = tempFile.getFileId();
                    this.faqDocFileName = tempFile.getFileName();
                }
            }

            if ((this.testPlanResults != null)
                && (this.testPlanResults.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.testPlanResults.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appTestPlan.fileSize.exceeded"));
                this.testPlanResults.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.testPlanResults, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.testPlanResultsTempFileId = tempFile.getFileId();
                    this.testPlanResultsFileName = tempFile.getFileName();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving	temp files IN	saveTempFiles()	of ApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called	IN saveTempFiles() of	ApplicationUpdateForm");
        }
    }

    public void validateCollections(ActionMapping mapping, HttpServletRequest request, ActionErrors errors, Long platformId)
    {
        HttpSession session = request.getSession();
        String taskName = (String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME);
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        
      //JMA cannot see these Section
        if (currUserType.equals(AimsConstants.VZW_USERTYPE) &&(platformId.longValue() != AimsConstants.ENTERPRISE_PLATFORM_ID.longValue()))
        {
            //Put	Testing	Phase	Information	in a Set in	the	Session.
            Set testPhaseSet = (Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES);

            if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
                || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
            {

                //Check for Access to Prioritization Section
                if (ApplicationHelper.checkAccess(request, taskName, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION))
                {
                    //Brew cannot see the Prioritization Section
                    if (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue())
                    {
                        //Start	of Dates Section
                        if (!this.isBlankString(this.targetedProductionDate))
                        {
                            if (!this.isDate(this.targetedProductionDate))
                                errors.add(
                                    ActionErrors.GLOBAL_MESSAGE,
                                    new ActionMessage("error.manage.app.targetedProductionDate.date", this.targetedProductionDate));
                            else if (Utility.convertToDate(this.targetedProductionDate, AimsConstants.DATE_FORMAT).compareTo(new java.util.Date()) < 0)
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.targetedProductionDate.smaller"));
                        }
                    }
                }

                //Check for Access to Testing Section
                if (ApplicationHelper.checkAccess(request, taskName, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING)
                		&& (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue()))
                {
                    //Checking for invalid Dates
                    for (int i = 0; i < this.testedDate.length; i++)
                    {
                        if ((!this.isBlankString(this.testedDate[i])) && (!this.isDate(this.testedDate[i])))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.tested.date", this.testedDate[i]));
                    }

                    if (testPhaseSet != null)
                    {
                        try
                        {
                            for (java.util.Iterator itr = testPhaseSet.iterator(); itr.hasNext();)
                            {
                                AimsAppPhase aap = (AimsAppPhase) itr.next();

                                //If Date	is Not Null	OR if	File is	Not	Null,	then Status	SHOULD BE	Not	Null otherwise Add Error
                                if (((aap.getTestedDate() != null) || (aap.getResultsFileName() != null)) && (aap.getStatus() == null))
                                {
                                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.testStatus.required"));
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            log.debug("\n\nError while validating	collections");
                        }
                    }
                }
            }
        }

    }

    public void saveCollections(ActionMapping mapping, HttpServletRequest request, ActionErrors errors, Long platformId)
    {
        HttpSession session = request.getSession();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String sessionId = session.getId();
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        String taskName = (String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME);
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        if (taskName == null)
        {
            taskName = "edit"; //:AM:	To remove	only to	make it	compatible with	other	apps not synced	yet	to the new design
            log.debug("\n\n\nTemp	AM:	compatibility	working	in saveCollections");
        }

        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
        {
            //Check for Access to Testing Section
            if (ApplicationHelper.checkAccess(request, taskName, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_TESTING)
            		&& (platformId.longValue() != AimsConstants.BREW_PLATFORM_ID.longValue()))
            {
                //Put	Testing	Phase	Information	in a Set in	the	Session.
                Set testPhaseSet = (Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES);

                if (testPhaseSet != null)
                {
                    try
                    {
                        int index = 0;

                        //Fills	From Array ->	into Session
                        for (java.util.Iterator itr = testPhaseSet.iterator(); itr.hasNext();)
                        {
                            TempFile tempFile = null;
                            AimsAppPhase aap = (AimsAppPhase) itr.next();

                            //if (!this.isBlankString(this.testedDate[index]))
                            if (this.isDate(this.testedDate[index]))
                                aap.setTestedDate(Utility.convertToDate(this.testedDate[index], AimsConstants.DATE_FORMAT));

                            aap.setStatus(this.testStatus[index]);
                            aap.setComments(this.testComments[index]);

                            try
                            {
                                if ((this.testResultFile[index] != null)
                                    && (this.testResultFile[index].getFileSize() > 0)
                                    && !(this.isValidFileSize(fileSize, this.testResultFile[index].getFileSize())))
                                {
                                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.testFile.fileSize.exceeded"));
                                }
                                else
                                {
                                    tempFile = TempFilesManager.saveTempFile(this.testResultFile[index], sessionId, currUserId);
                                    if (tempFile != null)
                                    {
                                        aap.setTempFileId(tempFile.getFileId().toString());
                                        aap.setResultsFileName(tempFile.getFileName());
                                        this.testResultFileName[index] = tempFile.getFileName();
                                        this.testResultFileId[index] = tempFile.getFileId();
                                    }
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                                log.debug("Error while saving	temp phase result	file IN	saveTempFiles()	of ApplicationUpdateForm");
                            }
                            finally
                            {
                                log.debug("Finally called	WHILE	saving temp	phase	file of	ApplicationUpdateForm");
                            }
                            index++;
                        }
                        session.setAttribute(ManageApplicationsConstants.SESSION_VAR_PHASES, testPhaseSet);
                    }
                    catch (Exception ex)
                    {}
                }
            }

            if (this.task.equals("addCertificate"))
            {
                if ((!this.isBlankString(addCertificationDate)) && (this.isDate(addCertificationDate)) && (this.isDropDownSelected(addCertificationId)))
                {
                    AimsAppCertification aimsAppCertification = new AimsAppCertification();
                    Set certificationSet = (Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_CERTIFICATIONS);
                    if (certificationSet == null)
                        certificationSet = new java.util.HashSet();
                    aimsAppCertification.setCertificationDate(Utility.convertToDate(addCertificationDate, AimsConstants.DATE_FORMAT));
                    aimsAppCertification.setAppsId(this.appsId);
                    try
                    {
                        aimsAppCertification.setAimsCertification(AimsApplicationsManager.getCertification(addCertificationId));
                    }
                    catch (Exception ex)
                    {
                        log.debug("Funny Error");
                    }
                    certificationSet.add(aimsAppCertification);
                    session.setAttribute(ManageApplicationsConstants.SESSION_VAR_CERTIFICATIONS, certificationSet);
                    addCertificationDate = null;
                }
                else
                { //DISPLAY	 SOME	ERROR	BACK
                }

            }

            if (this.task.equals("removeCertificate"))
            {
                Set certificationSet = (Set) session.getAttribute(ManageApplicationsConstants.SESSION_VAR_CERTIFICATIONS);
                if ((certificationSet != null) && (removeCertificationId != null))
                {
                    for (int iCount = 0; iCount < removeCertificationId.length; iCount++)
                    {
                        for (java.util.Iterator itr = certificationSet.iterator(); itr.hasNext();)
                        {
                            AimsAppCertification aimsAppCertification = (AimsAppCertification) itr.next();
                            if ((aimsAppCertification.getAimsCertification()).getCertificationId().longValue() == removeCertificationId[iCount].longValue())
                            {
                                certificationSet.remove(aimsAppCertification);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
