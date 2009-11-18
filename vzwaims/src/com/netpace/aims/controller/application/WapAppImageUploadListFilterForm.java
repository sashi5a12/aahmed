package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;
import org.apache.log4j.Logger;

/**
 * @struts.form name="WapAppImageUploadListFilterForm"
 */

public class WapAppImageUploadListFilterForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(WapAppImageUploadListFilterForm.class.getName());

    private java.lang.String sortField;
    private java.lang.String sortOrder;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
