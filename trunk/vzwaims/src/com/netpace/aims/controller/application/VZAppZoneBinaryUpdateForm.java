package com.netpace.aims.controller.application;

import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.TempFile;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @struts.form name="VZAppZoneBinaryUpdateForm"
 */

public class VZAppZoneBinaryUpdateForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(VZAppZoneBinaryUpdateForm.class.getName());

    private java.lang.Long appsId;

    private java.lang.Long binaryId;

    private java.lang.String binaryFileFileName;

    private java.lang.String binaryVersion;
    private java.lang.String binaryFileSize;

    private java.lang.String previewFileFileName;

    private FormFile documentFile;
    private java.lang.String documentFileFileName;
    private java.lang.Long documentFileTempFileId;


    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        log.debug("======= VZAppZoneBinaryUpdateForm.validate() starts");
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();

        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String sessionId = session.getId();
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        String task = request.getParameter("task");

        if ( !StringFuncs.isNullOrEmpty(task) && task.equalsIgnoreCase("save")) {
            //save tempFiles
            this.saveTempBinaryFiles(currUserId, sessionId, fileSize, errors);
            //if document file is also required while uploading binary
            if (((this.documentFile == null) || !(this.documentFile.getFileSize() > 0)) && (this.isBlankString(this.documentFileFileName))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.documentFile.file.required"));
            }

            //document ext check
            if (!(this.isBlankString(this.documentFileFileName))) {
                if (!(this.isValidFileType(this.documentFileFileName, AimsConstants.FILE_TYPE_ZIP))) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.documentFile.file.type"));
                }
            }
        }
        log.debug("======= VZAppZoneBinaryUpdateForm.validate() ends. \terrors.size= "+errors.size());
        return errors;
    }

    public void saveTempBinaryFiles(String currUserId, String sessionId, String fileSize, ActionErrors errors) {
        TempFile tempFile = null;
        try {

            if ((this.documentFile != null) && (this.documentFile.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.documentFile.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.vzAppZone.app.device.documentFile.fileSize.exceeded"));
                this.documentFile.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.documentFile, sessionId, currUserId);
                if (tempFile != null) {
                    this.documentFileTempFileId = tempFile.getFileId();
                    this.documentFileFileName = tempFile.getFileName();
                }
            }
        }//end try
        catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempBinaryFiles() of VZAppZoneBinaryUpdateForm");
        }
        finally {
            log.debug("Finally called IN saveTempBinaryFiles() of VZAppZoneBinaryUpdateForm");
        }
    }//end saveTempBinaryFiles

    public Long getAppsId() {
        return appsId;
    }

    public void setAppsId(Long appsId) {
        this.appsId = appsId;
    }

    public Long getBinaryId() {
        return binaryId;
    }

    public void setBinaryId(Long binaryId) {
        this.binaryId = binaryId;
    }

    public String getBinaryFileFileName() {
        return binaryFileFileName;
    }

    public void setBinaryFileFileName(String binaryFileFileName) {
        this.binaryFileFileName = binaryFileFileName;
    }

    public String getBinaryVersion() {
        return binaryVersion;
    }

    public void setBinaryVersion(String binaryVersion) {
        this.binaryVersion = binaryVersion;
    }

    public String getBinaryFileSize() {
        return binaryFileSize;
    }

    public void setBinaryFileSize(String binaryFileSize) {
        this.binaryFileSize = binaryFileSize;
    }

    public String getPreviewFileFileName() {
        return previewFileFileName;
    }

    public void setPreviewFileFileName(String previewFileFileName) {
        this.previewFileFileName = previewFileFileName;
    }

    public FormFile getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(FormFile documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentFileFileName() {
        return documentFileFileName;
    }

    public void setDocumentFileFileName(String documentFileFileName) {
        this.documentFileFileName = documentFileFileName;
    }

    public Long getDocumentFileTempFileId() {
        return documentFileTempFileId;
    }

    public void setDocumentFileTempFileId(Long documentFileTempFileId) {
        this.documentFileTempFileId = documentFileTempFileId;
    }

}
