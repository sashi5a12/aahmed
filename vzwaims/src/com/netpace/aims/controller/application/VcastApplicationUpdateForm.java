package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.VcastApplicationManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="VcastApplicationUpdateForm"
 */
public class VcastApplicationUpdateForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(VcastApplicationUpdateForm.class.getName());

    private java.lang.String tempForRadioIssue; //This property has been added only as a workaround for an issue with Special Characters and Radio Buttons
    private java.lang.String task;
    private java.lang.String appSubmitType;
    private java.lang.String currentPage;
    private java.lang.String orignalTask;

    //Tab Header
    private java.lang.String applicationStatus;
    private java.lang.String allianceName;

    private java.lang.Long appsId;
    private java.lang.Long appOwnerAllianceId;
    private java.lang.String title;
    private java.lang.String shortDesc;
    private java.lang.String longDesc;
    private java.lang.String submittedDate;
    private java.lang.Long aimsUserAppCreatedById;
    private java.lang.Long aimsLifecyclePhaseId;
    private java.util.Collection allCategories;
    private java.util.Collection allSubCategories;

    private java.lang.Long aimsContactId;
    private java.util.Collection allContacts;
    private java.lang.String contactFirstName;
    private java.lang.String contactLastName;
    private java.lang.String contactTitle;
    private java.lang.String contactEmail;
    private java.lang.String contactPhone;
    private java.lang.String contactMobile;

    private java.lang.Long language;
    private java.lang.Long updateFrequency;
    private java.lang.String evaluationComments;
    private java.lang.Long categoryId;
    private java.lang.Long subCategoryId;

    private java.util.Collection allLanguages;
    private java.util.Collection allFrequencies;
    private java.util.Collection allAudAges;
    private java.util.Collection allAudEducations;
    private java.util.Collection allAudGenders;
    private java.util.Collection allAudIncomes;
    private java.util.Collection allAudRaces;

    private String[] listSelectedAudAges;
    private String[] listSelectedAudEducations;
    private String[] listSelectedAudGenders;
    private String[] listSelectedAudIncomes;
    private String[] listSelectedAudRaces;

    //Files
    private FormFile sampleClip1;
    private FormFile sampleClip2;
    private FormFile sampleClip3;
    private java.lang.String sampleClip1FileName;
    private java.lang.String sampleClip2FileName;
    private java.lang.String sampleClip3FileName;
    private java.lang.Long sampleClip1TempFileId;
    private java.lang.Long sampleClip2TempFileId;
    private java.lang.Long sampleClip3TempFileId;

    //Journal
    private java.lang.String journalType;
    private java.lang.String journalText;
    private java.lang.String journalCombinedText;

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        try
        {
            this.allCategories = AimsApplicationsManager.getCategoriesByPlatform(AimsConstants.VCAST_PLATFORM_ID);
            this.allSubCategories = AimsApplicationsManager.getSubCategoriesByPlatform(AimsConstants.VCAST_PLATFORM_ID);

            this.allLanguages = VcastApplicationManager.getLanguages();
            this.allFrequencies = VcastApplicationManager.getFrequencies();
            this.allAudAges = VcastApplicationManager.getAudAges();
            this.allAudEducations = VcastApplicationManager.getAudEducations();
            this.allAudGenders = VcastApplicationManager.getAudGenders();
            this.allAudIncomes = VcastApplicationManager.getAudIncomes();
            this.allAudRaces = VcastApplicationManager.getAudRaces();

            request.setAttribute("VcastApplicationUpdateForm", this);
        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET: " + ex);
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String sessionId = session.getId();
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);

        //This populating of contacts has been moved here from the Reset method.
        //The reason for this is that we need to know the Owner(Alliance) of Application, at time of populating.
        try
        {
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                this.allContacts = AimsApplicationsManager.getContacts(currentUserAllianceId);
            else
                this.allContacts = AimsApplicationsManager.getContacts(this.appOwnerAllianceId);
        }
        catch (Exception ex)
        {}

        //Save Files to AIMS_TEMP_FILES table
        saveTempVcastFiles(currUserId, sessionId, fileSize, errors);
        
        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)))
        {

            //Check to see if data is compatible with DB fields
            validateToDBFields(currUserType, errors);

            if (this.isBlankString(this.title))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.clipTitle.required"));

            if (this.isBlankString(this.shortDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.appShortDesc.required"));

            if (this.isBlankString(this.longDesc))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.appLongDesc.required"));

            //Only For Alliance Users
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Email Validation
                if (!this.isDropDownSelected(this.aimsContactId))
                {
                    if (!this.isBlankString(this.contactEmail))
                        if (!this.isValidEmail(this.contactEmail))
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.appContactEmail.email", this.contactEmail));
                }

                //Start of File Type Validations

                if (!(this.isBlankString(this.sampleClip1FileName)))
                    if (!(this.isValidFileType(this.sampleClip1FileName, AimsConstants.FILE_TYPE_VIDEO_CLIP)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.file.type"));

                if (!(this.isBlankString(this.sampleClip2FileName)))
                    if (!(this.isValidFileType(this.sampleClip2FileName, AimsConstants.FILE_TYPE_VIDEO_CLIP)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.file.type"));

                if (!(this.isBlankString(this.sampleClip3FileName)))
                    if (!(this.isValidFileType(this.sampleClip3FileName, AimsConstants.FILE_TYPE_VIDEO_CLIP)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.file.type"));

                //End of File Type Validations
            }
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_PROCESS_FORM)))
        {

            if (!this.isDropDownSelected(this.language))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.language.required"));

            if (!this.isDropDownSelected(this.updateFrequency))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.updateFrequency.required"));
                
            if (!this.isDropDownSelected(this.subCategoryId))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.category.required"));


            //The following properties will be validated for an can only be updated by an Alliance user and not the Verizon user
            if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            {
                //Start   of Files (NULL) Validation
                if (((this.sampleClip1 == null) || !(this.sampleClip1.getFileSize() > 0)) && (this.isBlankString(this.sampleClip1FileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.required"));
                //End of Files (NULL) Validation                
            }

        }

        return errors;
    }

    public void validateToDBFields(String currUserType, ActionErrors errors)
    {
        if ((this.title != null) && (this.title.length() > 100))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.clipTitle.length"));

        if ((this.shortDesc != null) && (this.shortDesc.length() > 200))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.appShortDesc.length"));

        if ((this.longDesc != null) && (this.longDesc.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.appLongDesc.length"));

        if ((this.evaluationComments != null) && (this.evaluationComments.length() > 500))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.evaluationComments.length"));

        if ((this.contactFirstName != null) && (this.contactFirstName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.contactFirstName.length"));

        if ((this.contactLastName != null) && (this.contactLastName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.contactLastName.length"));

        if ((this.contactTitle != null) && (this.contactTitle.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.contactTitle.length"));

        if ((this.contactEmail != null) && (this.contactEmail.length() > 100))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.contactEmail.length"));

        if ((this.contactPhone != null) && (this.contactPhone.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.contactPhone.length"));

        if ((this.contactMobile != null) && (this.contactMobile.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.contactMobile.length"));

    }

    public void saveTempVcastFiles(String currUserId, String sessionId, String fileSize, ActionErrors errors)
    {
        TempFile tempFile = null;

        try
        {
            if ((this.sampleClip1 != null) && (this.sampleClip1.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.sampleClip1.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.fileSize.exceeded"));
                this.sampleClip1.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.sampleClip1, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.sampleClip1TempFileId = tempFile.getFileId();
                    this.sampleClip1FileName = tempFile.getFileName();
                }
            }

            if ((this.sampleClip2 != null) && (this.sampleClip2.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.sampleClip2.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.fileSize.exceeded"));
                this.sampleClip2.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.sampleClip2, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.sampleClip2TempFileId = tempFile.getFileId();
                    this.sampleClip2FileName = tempFile.getFileName();
                }
            }

            if ((this.sampleClip3 != null) && (this.sampleClip3.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.sampleClip3.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vcast.app.sampleClip.fileSize.exceeded"));
                this.sampleClip3.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.sampleClip3, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.sampleClip3TempFileId = tempFile.getFileId();
                    this.sampleClip3FileName = tempFile.getFileName();
                }
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempVcastFiles() of VcastApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempVcastFiles() of VcastApplicationUpdateForm");
        }

    }

    public java.lang.Long getAimsContactId()
    {
        return aimsContactId;
    }

    public java.lang.Long getAimsLifecyclePhaseId()
    {
        return aimsLifecyclePhaseId;
    }

    public java.lang.Long getAimsUserAppCreatedById()
    {
        return aimsUserAppCreatedById;
    }

    public java.util.Collection getAllCategories()
    {
        return allCategories;
    }

    public java.util.Collection getAllContacts()
    {
        return allContacts;
    }

    public java.util.Collection getAllSubCategories()
    {
        return allSubCategories;
    }

    public java.lang.Long getAppsId()
    {
        return appsId;
    }

    public java.lang.String getAppSubmitType()
    {
        return appSubmitType;
    }

    public java.lang.String getContactEmail()
    {
        return contactEmail;
    }

    public java.lang.String getContactFirstName()
    {
        return contactFirstName;
    }

    public java.lang.String getContactLastName()
    {
        return contactLastName;
    }

    public java.lang.String getContactMobile()
    {
        return contactMobile;
    }

    public java.lang.String getContactPhone()
    {
        return contactPhone;
    }

    public java.lang.String getContactTitle()
    {
        return contactTitle;
    }

    public java.lang.String getCurrentPage()
    {
        return currentPage;
    }

    /**
     * @return
     */
    public java.util.Collection getAllAudAges()
    {
        return allAudAges;
    }

    /**
     * @return
     */
    public java.util.Collection getAllAudEducations()
    {
        return allAudEducations;
    }

    /**
     * @return
     */
    public java.util.Collection getAllAudGenders()
    {
        return allAudGenders;
    }

    /**
     * @return
     */
    public java.util.Collection getAllAudIncomes()
    {
        return allAudIncomes;
    }

    /**
     * @return
     */
    public java.util.Collection getAllAudRaces()
    {
        return allAudRaces;
    }

    /**
     * @return
     */
    public java.lang.String getAllianceName()
    {
        return allianceName;
    }

    /**
     * @return
     */
    public java.lang.String getApplicationStatus()
    {
        return applicationStatus;
    }

    /**
     * @return
     */
    public java.lang.Long getAppOwnerAllianceId()
    {
        return appOwnerAllianceId;
    }

    /**
     * @return
     */
    public java.lang.Long getCategoryId()
    {
        return categoryId;
    }

    /**
     * @return
     */
    public java.lang.String getJournalCombinedText()
    {
        return journalCombinedText;
    }

    /**
     * @return
     */
    public java.lang.String getJournalText()
    {
        return journalText;
    }

    /**
     * @return
     */
    public java.lang.String getJournalType()
    {
        return journalType;
    }

    /**
     * @return
     */
    public java.lang.Long getLanguage()
    {
        return language;
    }

    /**
     * @return
     */
    public java.lang.String getLongDesc()
    {
        return longDesc;
    }

    /**
     * @return
     */
    public java.lang.String getOrignalTask()
    {
        return orignalTask;
    }

    /**
     * @return
     */
    public FormFile getSampleClip1()
    {
        return sampleClip1;
    }

    /**
     * @return
     */
    public java.lang.String getSampleClip1FileName()
    {
        return sampleClip1FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getSampleClip1TempFileId()
    {
        return sampleClip1TempFileId;
    }

    /**
     * @return
     */
    public FormFile getSampleClip2()
    {
        return sampleClip2;
    }

    /**
     * @return
     */
    public java.lang.String getSampleClip2FileName()
    {
        return sampleClip2FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getSampleClip2TempFileId()
    {
        return sampleClip2TempFileId;
    }

    /**
     * @return
     */
    public FormFile getSampleClip3()
    {
        return sampleClip3;
    }

    /**
     * @return
     */
    public java.lang.String getSampleClip3FileName()
    {
        return sampleClip3FileName;
    }

    /**
     * @return
     */
    public java.lang.Long getSampleClip3TempFileId()
    {
        return sampleClip3TempFileId;
    }

    /**
     * @return
     */
    public java.lang.String getShortDesc()
    {
        return shortDesc;
    }

    /**
     * @return
     */
    public java.lang.Long getSubCategoryId()
    {
        return subCategoryId;
    }

    /**
     * @return
     */
    public java.lang.String getSubmittedDate()
    {
        return submittedDate;
    }

    /**
     * @return
     */
    public java.lang.String getTask()
    {
        return task;
    }

    /**
     * @return
     */
    public java.lang.String getTempForRadioIssue()
    {
        return tempForRadioIssue;
    }

    /**
     * @return
     */
    public java.lang.String getTitle()
    {
        return title;
    }

    /**
     * @return
     */
    public java.lang.Long getUpdateFrequency()
    {
        return updateFrequency;
    }

    /**
     * @param long1
     */
    public void setAimsContactId(java.lang.Long long1)
    {
        aimsContactId = long1;
    }

    /**
     * @param long1
     */
    public void setAimsLifecyclePhaseId(java.lang.Long long1)
    {
        aimsLifecyclePhaseId = long1;
    }

    /**
     * @param long1
     */
    public void setAimsUserAppCreatedById(java.lang.Long long1)
    {
        aimsUserAppCreatedById = long1;
    }

    /**
     * @param collection
     */
    public void setAllAudAges(java.util.Collection collection)
    {
        allAudAges = collection;
    }

    /**
     * @param collection
     */
    public void setAllAudEducations(java.util.Collection collection)
    {
        allAudEducations = collection;
    }

    /**
     * @param collection
     */
    public void setAllAudGenders(java.util.Collection collection)
    {
        allAudGenders = collection;
    }

    /**
     * @param collection
     */
    public void setAllAudIncomes(java.util.Collection collection)
    {
        allAudIncomes = collection;
    }

    /**
     * @param collection
     */
    public void setAllAudRaces(java.util.Collection collection)
    {
        allAudRaces = collection;
    }

    /**
     * @param collection
     */
    public void setAllCategories(java.util.Collection collection)
    {
        allCategories = collection;
    }

    /**
     * @param collection
     */
    public void setAllContacts(java.util.Collection collection)
    {
        allContacts = collection;
    }

    /**
     * @param string
     */
    public void setAllianceName(java.lang.String string)
    {
        allianceName = string;
    }

    /**
     * @param collection
     */
    public void setAllSubCategories(java.util.Collection collection)
    {
        allSubCategories = collection;
    }

    /**
     * @param string
     */
    public void setApplicationStatus(java.lang.String string)
    {
        applicationStatus = string;
    }

    /**
     * @param long1
     */
    public void setAppOwnerAllianceId(java.lang.Long long1)
    {
        appOwnerAllianceId = long1;
    }

    /**
     * @param long1
     */
    public void setAppsId(java.lang.Long long1)
    {
        appsId = long1;
    }

    /**
     * @param string
     */
    public void setAppSubmitType(java.lang.String string)
    {
        appSubmitType = string;
    }

    /**
     * @param long1
     */
    public void setCategoryId(java.lang.Long long1)
    {
        categoryId = long1;
    }

    /**
     * @param string
     */
    public void setContactEmail(java.lang.String string)
    {
        contactEmail = string;
    }

    /**
     * @param string
     */
    public void setContactFirstName(java.lang.String string)
    {
        contactFirstName = string;
    }

    /**
     * @param string
     */
    public void setContactLastName(java.lang.String string)
    {
        contactLastName = string;
    }

    /**
     * @param string
     */
    public void setContactMobile(java.lang.String string)
    {
        contactMobile = string;
    }

    /**
     * @param string
     */
    public void setContactPhone(java.lang.String string)
    {
        contactPhone = string;
    }

    /**
     * @param string
     */
    public void setContactTitle(java.lang.String string)
    {
        contactTitle = string;
    }

    /**
     * @param string
     */
    public void setCurrentPage(java.lang.String string)
    {
        currentPage = string;
    }

    /**
     * @param string
     */
    public void setJournalCombinedText(java.lang.String string)
    {
        journalCombinedText = string;
    }

    /**
     * @param string
     */
    public void setJournalText(java.lang.String string)
    {
        journalText = string;
    }

    /**
     * @param string
     */
    public void setJournalType(java.lang.String string)
    {
        journalType = string;
    }

    /**
     * @param long1
     */
    public void setLanguage(java.lang.Long long1)
    {
        language = long1;
    }

    /**
     * @param string
     */
    public void setLongDesc(java.lang.String string)
    {
        longDesc = string;
    }

    /**
     * @param string
     */
    public void setOrignalTask(java.lang.String string)
    {
        orignalTask = string;
    }

    /**
     * @param file
     */
    public void setSampleClip1(FormFile file)
    {
        sampleClip1 = file;
    }

    /**
     * @param string
     */
    public void setSampleClip1FileName(java.lang.String string)
    {
        sampleClip1FileName = string;
    }

    /**
     * @param long1
     */
    public void setSampleClip1TempFileId(java.lang.Long long1)
    {
        sampleClip1TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setSampleClip2(FormFile file)
    {
        sampleClip2 = file;
    }

    /**
     * @param string
     */
    public void setSampleClip2FileName(java.lang.String string)
    {
        sampleClip2FileName = string;
    }

    /**
     * @param long1
     */
    public void setSampleClip2TempFileId(java.lang.Long long1)
    {
        sampleClip2TempFileId = long1;
    }

    /**
     * @param file
     */
    public void setSampleClip3(FormFile file)
    {
        sampleClip3 = file;
    }

    /**
     * @param string
     */
    public void setSampleClip3FileName(java.lang.String string)
    {
        sampleClip3FileName = string;
    }

    /**
     * @param long1
     */
    public void setSampleClip3TempFileId(java.lang.Long long1)
    {
        sampleClip3TempFileId = long1;
    }

    /**
     * @param string
     */
    public void setShortDesc(java.lang.String string)
    {
        shortDesc = string;
    }

    /**
     * @param long1
     */
    public void setSubCategoryId(java.lang.Long long1)
    {
        subCategoryId = long1;
    }

    /**
     * @param string
     */
    public void setSubmittedDate(java.lang.String string)
    {
        submittedDate = string;
    }

    /**
     * @param string
     */
    public void setTask(java.lang.String string)
    {
        task = string;
    }

    /**
     * @param string
     */
    public void setTempForRadioIssue(java.lang.String string)
    {
        tempForRadioIssue = string;
    }

    /**
     * @param string
     */
    public void setTitle(java.lang.String string)
    {
        title = string;
    }

    /**
     * @param long1
     */
    public void setUpdateFrequency(java.lang.Long long1)
    {
        updateFrequency = long1;
    }

    /**
     * @return
     */
    public String[] getListSelectedAudAges()
    {
        return listSelectedAudAges;
    }

    /**
     * @return
     */
    public String[] getListSelectedAudEducations()
    {
        return listSelectedAudEducations;
    }

    /**
     * @return
     */
    public String[] getListSelectedAudGenders()
    {
        return listSelectedAudGenders;
    }

    /**
     * @return
     */
    public String[] getListSelectedAudIncomes()
    {
        return listSelectedAudIncomes;
    }

    /**
     * @return
     */
    public String[] getListSelectedAudRaces()
    {
        return listSelectedAudRaces;
    }

    /**
     * @param strings
     */
    public void setListSelectedAudAges(String[] strings)
    {
        listSelectedAudAges = strings;
    }

    /**
     * @param strings
     */
    public void setListSelectedAudEducations(String[] strings)
    {
        listSelectedAudEducations = strings;
    }

    /**
     * @param strings
     */
    public void setListSelectedAudGenders(String[] strings)
    {
        listSelectedAudGenders = strings;
    }

    /**
     * @param strings
     */
    public void setListSelectedAudIncomes(String[] strings)
    {
        listSelectedAudIncomes = strings;
    }

    /**
     * @param strings
     */
    public void setListSelectedAudRaces(String[] strings)
    {
        listSelectedAudRaces = strings;
    }

    /**
     * @return
     */
    public java.util.Collection getAllFrequencies()
    {
        return allFrequencies;
    }

    /**
     * @return
     */
    public java.util.Collection getAllLanguages()
    {
        return allLanguages;
    }

    /**
     * @param collection
     */
    public void setAllFrequencies(java.util.Collection collection)
    {
        allFrequencies = collection;
    }

    /**
     * @param collection
     */
    public void setAllLanguages(java.util.Collection collection)
    {
        allLanguages = collection;
    }

    /**
     * @return
     */
    public java.lang.String getEvaluationComments()
    {
        return evaluationComments;
    }

    /**
     * @param string
     */
    public void setEvaluationComments(java.lang.String string)
    {
        evaluationComments = string;
    }

}
