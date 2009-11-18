package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.masters.AimsDevicesOnLoanManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="ReconcileDeviceOnLoanFileForm"
 * @author Adnan Makda.
 */
public class ReconcileDeviceOnLoanFileForm extends com.netpace.aims.controller.BaseValidatorForm
{

    static Logger log = Logger.getLogger(ReconcileDeviceOnLoanFileForm.class.getName());

    private FormFile deviceOnLoanFile;
    private java.lang.String deviceOnLoanFileFileName;
    private java.lang.Long deviceOnLoanFileTempFileId;
    private java.util.Collection allDevices;
    private java.lang.Long deviceId;
    private String strComments;

    /* Public Getter and Setter Functions Starts*/

    /*  */
    public String getComments()
    {
        return strComments;
    }

    public void setComments(String strComments)
    {
        this.strComments = strComments;
    }

    /**
     * @return
     */
    public FormFile getDeviceOnLoanFile()
    {
        return deviceOnLoanFile;
    }

    /**
     * @return
     */
    public java.lang.String getDeviceOnLoanFileFileName()
    {
        return deviceOnLoanFileFileName;
    }

    /**
     * @return
     */
    public java.lang.Long getDeviceOnLoanFileTempFileId()
    {
        return deviceOnLoanFileTempFileId;
    }

    /**
     * @param file
     */
    public void setDeviceOnLoanFile(FormFile file)
    {
        deviceOnLoanFile = file;
    }

    /**
     * @param string
     */
    public void setDeviceOnLoanFileFileName(java.lang.String string)
    {
        deviceOnLoanFileFileName = string;
    }

    /**
     * @param long1
     */
    public void setDeviceOnLoanFileTempFileId(java.lang.Long long1)
    {
        deviceOnLoanFileTempFileId = long1;
    }

    /**
     * @return
     */
    public java.util.Collection getAllDevices()
    {
        return allDevices;
    }

    /**
     * @return
     */
    public java.lang.Long getDeviceId()
    {
        return deviceId;
    }

    /**
     * @param collection
     */
    public void setAllDevices(java.util.Collection collection)
    {
        allDevices = collection;
    }

    /**
     * @param long1
     */
    public void setDeviceId(java.lang.Long long1)
    {
        deviceId = long1;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        try
        {
            log.debug("In RESET ReconcileDeviceOnLoanFileForm");
            this.allDevices = AimsDevicesOnLoanManager.getDevices();
        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET: " + ex);
        }
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        HttpSession session = request.getSession();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String sessionId = session.getId();

        TempFile tempFile = null;
        ActionErrors errors = new ActionErrors();

        try
        {

            if ((this.deviceOnLoanFile == null) || !(this.deviceOnLoanFile.getFileSize() > 0))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewFileForm.fileRequired"));
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.deviceOnLoanFile, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.deviceOnLoanFileTempFileId = tempFile.getFileId();
                    this.deviceOnLoanFileFileName = tempFile.getFileName();
                }

                if (!this.isValidFileType(this.deviceOnLoanFileFileName, AimsConstants.FILE_TYPE_XLS))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewFileForm.fileFormatInvalid"));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving files IN validate() of ReconcileDeviceOnLoanFileForm");
        }

        return errors;
    }
}
