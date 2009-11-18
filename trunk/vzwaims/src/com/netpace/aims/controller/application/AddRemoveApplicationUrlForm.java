package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.model.core.AimsUser;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @struts.form	name="AddRemoveApplicationUrlForm"
 */
public class AddRemoveApplicationUrlForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(AddRemoveApplicationUrlForm.class.getName());

    private java.lang.Long appsId;
    private java.lang.String networkUsage;
    private java.lang.String[] applicationURLs;
    private java.lang.String task;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        log.debug("RESET called	in AddRemoveApplicationUrlForm ");
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();

        //application urls validation, if network usage is enabled then user must enter atleast one url
        if(this.task.equalsIgnoreCase("save")) {
            if(this.applicationURLs==null || this.applicationURLs.length==0) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.url.required"));                
            }
            /************** Skip URL patterm validation
                else {
                    //URL Validation for valid URL pattern
                    for(int i=0; i<this.applicationURLs.length; i++) {
                        if (!(this.isBlankString(this.applicationURLs[i]))) {
                            if (!(this.isValidURL(this.applicationURLs[i]))) {
                                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.manage.app.network.url.invalid"));
                            }
                        }
                    }
                 }
            **************/
        }
        return errors;
    }

    public Long getAppsId() {
        return appsId;
    }

    public void setAppsId(Long appsId) {
        this.appsId = appsId;
    }

    public String getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(String networkUsage) {
        this.networkUsage = networkUsage;
    }

    public String[] getApplicationURLs() {
        return applicationURLs;
    }

    public void setApplicationURLs(String[] applicationURLs) {
        this.applicationURLs = applicationURLs;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
