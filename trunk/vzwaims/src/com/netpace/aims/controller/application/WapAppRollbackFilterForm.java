package com.netpace.aims.controller.application;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="WapAppRollbackFilterForm"
 */
public class WapAppRollbackFilterForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(WapAppRollbackFilterForm.class.getName());

    /** identifier field */
    private java.lang.String filterField;
    private java.lang.String filterText;
    private java.lang.String sortField;
    private java.lang.String sortOrder;
    
    public java.lang.String getFilterField()
    {
        return this.filterField;
    }

    public void setFilterField(java.lang.String filterField)
    {
        this.filterField = filterField;
    }

    public java.lang.String getFilterText()
    {
        return this.filterText;
    }

    public void setFilterText(java.lang.String filterText)
    {
        this.filterText = filterText;
    }

    public java.lang.String getSortField()
    {
        return this.sortField;
    }

    public void setSortField(java.lang.String sortField)
    {
        this.sortField = sortField;
    }

    public java.lang.String getSortOrder()
    {
        return this.sortOrder;
    }

    public void setSortOrder(java.lang.String sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        System.out.println("\n\n\nI N    R E S E T\n\n\n");

    }

}
