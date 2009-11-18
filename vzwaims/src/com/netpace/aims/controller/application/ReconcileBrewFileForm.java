package com.netpace.aims.controller.application;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 * @struts.form name="ReconcileBrewFileForm"
 * @author Ahson Imtiaz.
 */
public class ReconcileBrewFileForm extends com.netpace.aims.controller.BaseValidatorForm
{

    static Logger log = Logger.getLogger(ReconcileBrewFileForm.class.getName());

    private FormFile brewFile;
    private Date dteCreated;
    private String strComments;
    /* Public Getter and Setter Functions Starts*/

    /*  */
    public void setBrewFile(FormFile ffFile)
    {
        this.brewFile = ffFile;
    }

    public FormFile getBrewFile()
    {
        return this.brewFile;
    }

    public Date getCreatedDate()
    {
        return dteCreated;
    }

    public void setCreatedDate(Date dte)
    {
        dteCreated = dte;
    }

    public String getComments()
    {
        return strComments;
    }

    public void setComments(String strComments)
    {
        this.strComments = strComments;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        log.debug("In RESET Reconcile Brew File Form Out x");
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        if ((this.brewFile == null) || !(this.brewFile.getFileSize() > 0))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewFileForm.fileRequired"));
        else if (!brewFile.getFileName().endsWith(".xls"))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewFileForm.fileFormatInvalid"));
        }
        else
        {
            try
            {
                String strDateString = brewFile.getFileName();
                strDateString = strDateString.substring(strDateString.length() - 14).substring(0, 10);
                log.debug("Date from filename is : " + strDateString);
                SimpleDateFormat df = new SimpleDateFormat("MM.dd.yyyy");
                dteCreated = df.parse(strDateString);
            }
            catch (Exception e)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewFileForm.fileNameDate"));
            }
        }

        return errors;

    }

}
