package com.netpace.aims.controller.application;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.application.ReconcileBrewManager;

/**
 * @struts.form name="ReconcileBrewForm"
 * @author Ahson Imtiaz.
 */
public class ReconcileBrewForm extends com.netpace.aims.controller.BaseValidatorForm
{

    static Logger log = Logger.getLogger(ReconcileBrewForm.class.getName());

    private FormFile brewFile;
    private String[] cmbSelect;
    private Integer[] selectedCombos;
    private Long brewAppsId;
    private Long dataEntryId;
    private Long deviceId;
    private Long brewNstlUploadId;
    private String choseCombineId;

    private Collection dataFiles;

    /* Public Getter and Setter Functions Starts*/

    /*  */
    public void setReconcileCombineId(String[] cmbSelect)
    {
        this.cmbSelect = cmbSelect;
    }

    public String[] getReconcileCombineId()
    {
        return this.cmbSelect;
    }

    public void setSelectedCombo(Integer[] nums)
    {
        this.selectedCombos = nums;
    }

    public Integer[] getSelectedCombo()
    {
        return this.selectedCombos;
    }

    /*		
    		public Long getBrewAppsId()
    		{
    			return brewAppsId;
    		}		
    	
    		public Long getDataEntryId()
    		{
    			return dataEntryId;
    		}
    	
    		public Long getDeviceId()
    		{
    			return deviceId;
    		}
    */
    public void setBrewNstlUploadId(Long Id)
    {
        this.brewNstlUploadId = Id;
    }

    public Long getBrewNstlUploadId()
    {
        return this.brewNstlUploadId;
    }
    /* 
    */
    public void setChoseCombineId(String Id)
    {
        this.choseCombineId = Id;
    }

    public String getChoseCombineId()
    {
        return this.choseCombineId;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        //log.debug("In RESET Reconcile Brew Form Out. " + cmbSelect[0]);

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        if (this.brewNstlUploadId == null || this.brewNstlUploadId.intValue() == 0)
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewForm.selectFile"));

            try
            {
                request.setAttribute("DataFiles", ReconcileBrewManager.getBrewNstlUploadToReconcile());
                request.setAttribute("DataFilesReconciled", ReconcileBrewManager.getBrewNstlUploadReconciled());
            }
            catch (Exception e)
            {
                log.debug("unable to get combo for Brew / Nstl uploaded records");
            }
        }

        return errors;

    }

}
