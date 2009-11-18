package com.netpace.aims.controller.application;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.application.ReconcileCatalogManager;

/**
 * @struts.form name="ReconcileCatalogForm"
 * @author Ahson Imtiaz.
 */
public class ReconcileCatalogForm extends com.netpace.aims.controller.BaseValidatorForm
{

    static Logger log = Logger.getLogger(ReconcileCatalogForm.class.getName());

    private String[] cmbSelect;
    private Integer[] selectedCombos;
    private Long catalogId;
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

    public void setCatalogId(Long Id)
    {
        this.catalogId = Id;
    }

    public Long getCatalogId()
    {
        return this.catalogId;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {}

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        ActionErrors errors = new ActionErrors();

        if (this.catalogId == null || this.catalogId.intValue() == 0)
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ReconcileBrewForm.selectFile"));

            try
            {
                request.setAttribute("DataFiles", ReconcileCatalogManager.getCatalogToReconcile());
                request.setAttribute("DataFilesReconciled", ReconcileCatalogManager.getCatalogReconciled());
            }
            catch (Exception e)
            {
                log.debug("unable to get combos for Catalog records");
            }
        }

        return errors;

    }

}
