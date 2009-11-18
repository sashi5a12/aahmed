package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.AimsConstants;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @struts.form name="AllianceClickThroughContractForm"
 */
public class AllianceClickThroughContractForm extends BaseValidatorForm {

    private static Logger log = Logger.getLogger(AllianceClickThroughContractForm.class.getName());

    private String task;
    private String selDocType;
    private String selDocId;
    private String selContractId;

    private String[] selectedFilterCTPlatform=new String[0];

    public void reset (ActionMapping mapping, HttpServletRequest request)   {

        log.debug("RESET called in AllianceClickThroughContractForm");

        this.selectedFilterCTPlatform = this.getAllSelectedFilterCTPlatform();
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getSelDocType() {
        return selDocType;
    }

    public void setSelDocType(String selDocType) {
        this.selDocType = selDocType;
    }

    public String getSelDocId() {
        return selDocId;
    }

    public void setSelDocId(String selDocId) {
        this.selDocId = selDocId;
    }

    public String getSelContractId() {
        return selContractId;
    }

    public void setSelContractId(String selContractId) {
        this.selContractId = selContractId;
    }

    public String[] getSelectedFilterCTPlatform() {
        return selectedFilterCTPlatform;
    }

    public void setSelectedFilterCTPlatform(String[] selectedFilterCTPlatform) {
        this.selectedFilterCTPlatform = selectedFilterCTPlatform;
    }

    public String[] getAllSelectedFilterCTPlatform(){
    	return new String[]{AimsConstants.FILTER_SHOW_ALL};
    }
}
