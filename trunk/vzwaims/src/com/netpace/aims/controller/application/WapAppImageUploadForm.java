package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.bo.core.TempFilesManager;
import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.apache.struts.Globals;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @struts.form name="WapAppImageUploadForm"
 */

public class WapAppImageUploadForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(WapApplicationUpdateForm.class.getName());

    private java.lang.Long appsId;

    private FormFile appMediumLargeImage;//app medium image
    private FormFile appQVGAPotraitImage;//app potrait image
    private FormFile appQVGALandscapeImage;//app landscape image

    private java.lang.String appMediumLargeImageFileName;
    private java.lang.String appQVGAPotraitImageFileName;
    private java.lang.String appQVGALandscapeImageFileName;

    private java.lang.Long appMediumLargeImageTempFileId;
    private java.lang.Long appQVGAPotraitImageTempFileId;
    private java.lang.Long appQVGALandscapeImageTempFileId;

    //Tab Header
    private java.lang.String title;
    private java.lang.String applicationStatus;
    private java.lang.Long aimsLifecyclePhaseId;

    public void reset(ActionMapping mapping, HttpServletRequest request) {

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        String task = request.getParameter("task");
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String sessionId = session.getId();
        //String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        MessageResources defaultBundle =(MessageResources)request.getAttribute(Globals.MESSAGES_KEY);

        String appMediumLargeImageExt = "";
        String appQVGAPotraitImageExt = "";
        String appQVGALandscapeImageExt = "";
        String fileName = "";

        if ( !StringFuncs.isNullOrEmpty(task) && task.equalsIgnoreCase("save")) {
            //Save Files to AIMS_TEMP_FILES table
            saveTempWapFiles(currUserId, sessionId, defaultBundle, errors);

            ////// required field check
            //medium
            if (((this.appMediumLargeImage == null) || !(this.appMediumLargeImage.getFileSize() > 0)) && (this.isBlankString(this.appMediumLargeImageFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.required"));
            //potrait
            if (((this.appQVGAPotraitImage == null) || !(this.appQVGAPotraitImage.getFileSize() > 0)) && (this.isBlankString(this.appQVGAPotraitImageFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.required"));
            //landscape
            if (((this.appQVGALandscapeImage == null) || !(this.appQVGALandscapeImage.getFileSize() > 0)) && (this.isBlankString(this.appQVGALandscapeImageFileName)))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.required"));

            ////// file type check
            //medium image
            if (!(this.isBlankString(this.appMediumLargeImageFileName)))
                if (!(this.isValidFileType(this.appMediumLargeImageFileName, AimsConstants.FILE_TYPE_WAP_FTP_IMAGES)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.file.type"));
            //potrait
            if (!(this.isBlankString(this.appQVGAPotraitImageFileName)))
                if (!(this.isValidFileType(this.appQVGAPotraitImageFileName, AimsConstants.FILE_TYPE_WAP_FTP_IMAGES)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.file.type"));
            //landscape
            if (!(this.isBlankString(this.appQVGALandscapeImageFileName)))
                if (!(this.isValidFileType(this.appQVGALandscapeImageFileName, AimsConstants.FILE_TYPE_WAP_FTP_IMAGES)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.file.type"));

            // Extension check, all images must have same file type
            appMediumLargeImageExt = this.getFileExtension(this.appMediumLargeImage, this.appMediumLargeImageFileName);
            appQVGAPotraitImageExt = this.getFileExtension(this.appQVGAPotraitImage, this.appQVGAPotraitImageFileName);
            appQVGALandscapeImageExt = this.getFileExtension(this.appQVGALandscapeImage, this.appQVGALandscapeImageFileName);
            if( (!appMediumLargeImageExt.equalsIgnoreCase(appQVGAPotraitImageExt))
                    || (!appMediumLargeImageExt.equalsIgnoreCase(appQVGALandscapeImageExt)))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.icons.same.fileType"));
            }
            // end Extension check
        }
        return errors;
    }

    private String getFileExtension(FormFile file, String fileName)
    {
        String ext = "";
        if(file!=null && !StringFuncs.isNullOrEmpty(file.getFileName()))
        {
            fileName = file.getFileName();
            ext = fileName.substring(fileName.lastIndexOf("."));
        }
        if(!StringFuncs.isNullOrEmpty(fileName))
        {
            ext = fileName.substring(fileName.lastIndexOf("."));
        }
        return ext;
    }

    public void saveTempWapFiles(String currUserId, String sessionId, MessageResources defaultBundle, ActionErrors errors)
    {
        TempFile tempFile = null;
        String mediumImageFileSize = defaultBundle.getMessage("file.wap.icon.medium.sizelimit");
        String potraitImageFileSize = defaultBundle.getMessage("file.wap.icon.potrait.sizelimit");
        String landscapeImageFileSize = defaultBundle.getMessage("file.wap.icon.landscape.sizelimit");

        try {
            //3 app images work
            //medium image
            if ((this.appMediumLargeImage != null) && (this.appMediumLargeImage.getFileSize() > 0) && !(this.isValidFileSize(mediumImageFileSize, this.appMediumLargeImage.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appMediumLargeImage.fileSize.exceeded"));
                this.appMediumLargeImage.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.appMediumLargeImage, sessionId, currUserId);
                if (tempFile != null) {
                    this.appMediumLargeImageTempFileId = tempFile.getFileId();
                    this.appMediumLargeImageFileName = tempFile.getFileName();
                }
            }
            // potrait
            if ((this.appQVGAPotraitImage != null) && (this.appQVGAPotraitImage.getFileSize() > 0) && !(this.isValidFileSize(potraitImageFileSize, this.appQVGAPotraitImage.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGAPotraitImage.fileSize.exceeded"));
                this.appQVGAPotraitImage.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.appQVGAPotraitImage, sessionId, currUserId);
                if (tempFile != null) {
                    this.appQVGAPotraitImageTempFileId = tempFile.getFileId();
                    this.appQVGAPotraitImageFileName = tempFile.getFileName();
                }
            }
            //landscape
            if ((this.appQVGALandscapeImage != null) && (this.appQVGALandscapeImage.getFileSize() > 0) && !(this.isValidFileSize(landscapeImageFileSize, this.appQVGALandscapeImage.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.wap.app.appQVGALandscapeImage.fileSize.exceeded"));
                this.appQVGALandscapeImage.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.appQVGALandscapeImage, sessionId, currUserId);
                if (tempFile != null) {
                    this.appQVGALandscapeImageTempFileId = tempFile.getFileId();
                    this.appQVGALandscapeImageFileName = tempFile.getFileName();
                }
            }
            //end 3 app images work

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempWapFiles() of WapApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempWapFiles() of WapApplicationUpdateForm");
        }

    }


    public Long getAppsId() {
        return appsId;
    }

    public void setAppsId(Long appsId) {
        this.appsId = appsId;
    }

    public FormFile getAppMediumLargeImage() {
        return appMediumLargeImage;
    }

    public void setAppMediumLargeImage(FormFile appMediumLargeImage) {
        this.appMediumLargeImage = appMediumLargeImage;
    }

    public FormFile getAppQVGAPotraitImage() {
        return appQVGAPotraitImage;
    }

    public void setAppQVGAPotraitImage(FormFile appQVGAPotraitImage) {
        this.appQVGAPotraitImage = appQVGAPotraitImage;
    }

    public FormFile getAppQVGALandscapeImage() {
        return appQVGALandscapeImage;
    }

    public void setAppQVGALandscapeImage(FormFile appQVGALandscapeImage) {
        this.appQVGALandscapeImage = appQVGALandscapeImage;
    }

    public String getAppMediumLargeImageFileName() {
        return appMediumLargeImageFileName;
    }

    public void setAppMediumLargeImageFileName(String appMediumLargeImageFileName) {
        this.appMediumLargeImageFileName = appMediumLargeImageFileName;
    }

    public String getAppQVGAPotraitImageFileName() {
        return appQVGAPotraitImageFileName;
    }

    public void setAppQVGAPotraitImageFileName(String appQVGAPotraitImageFileName) {
        this.appQVGAPotraitImageFileName = appQVGAPotraitImageFileName;
    }

    public String getAppQVGALandscapeImageFileName() {
        return appQVGALandscapeImageFileName;
    }

    public void setAppQVGALandscapeImageFileName(String appQVGALandscapeImageFileName) {
        this.appQVGALandscapeImageFileName = appQVGALandscapeImageFileName;
    }

    public Long getAppMediumLargeImageTempFileId() {
        return appMediumLargeImageTempFileId;
    }

    public void setAppMediumLargeImageTempFileId(Long appMediumLargeImageTempFileId) {
        this.appMediumLargeImageTempFileId = appMediumLargeImageTempFileId;
    }

    public Long getAppQVGAPotraitImageTempFileId() {
        return appQVGAPotraitImageTempFileId;
    }

    public void setAppQVGAPotraitImageTempFileId(Long appQVGAPotraitImageTempFileId) {
        this.appQVGAPotraitImageTempFileId = appQVGAPotraitImageTempFileId;
    }

    public Long getAppQVGALandscapeImageTempFileId() {
        return appQVGALandscapeImageTempFileId;
    }

    public void setAppQVGALandscapeImageTempFileId(Long appQVGALandscapeImageTempFileId) {
        this.appQVGALandscapeImageTempFileId = appQVGALandscapeImageTempFileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getAimsLifecyclePhaseId() {
        return aimsLifecyclePhaseId;
    }

    public void setAimsLifecyclePhaseId(Long aimsLifecyclePhaseId) {
        this.aimsLifecyclePhaseId = aimsLifecyclePhaseId;
    }
}
