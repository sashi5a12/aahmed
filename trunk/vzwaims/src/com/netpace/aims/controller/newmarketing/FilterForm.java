package com.netpace.aims.controller.newmarketing;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="FilterForm"
 */
public class FilterForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(FilterForm.class.getName());

    /** identifier field */
    private java.lang.String filterString;
    private java.lang.String filterField;
    private java.lang.String filterText;
    private java.lang.String sortField;
    private java.lang.String sortOrder;
    private java.lang.String typeOfView;

    private Collection allFilters;

    public java.lang.String getFilterString()
    {
        return this.filterString;
    }

    public void setFilterString(java.lang.String filterString)
    {
        this.filterString = filterString;
    }

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

    public java.lang.String getTypeOfView()
    {
        return this.typeOfView;
    }

    public void setTypeOfView(java.lang.String typeOfView)
    {
        this.typeOfView = typeOfView;
    }

    public Collection getAllFilters()
    {
        return this.allFilters;
    }

    public void setAllFilters(Collection allFilters)
    {
        this.allFilters = allFilters;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        System.out.println("\n\n\nI N    R E S E T\n\n\n");
    }

}
