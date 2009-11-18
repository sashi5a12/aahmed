package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



/**
 * @struts.form name="MportalAllianceUpdateForm"
 */

public class MportalAllianceUpdateForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(MportalAllianceUpdateForm.class.getName());

    private java.lang.Long allianceId;

    private java.lang.String allianceName;
    private java.lang.Long vendorId;

    private java.lang.String mportalAllianceName;

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        log.debug("======= MportalAllianceUpdateForm.validate() starts");
        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();

        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String sessionId = session.getId();

        String task = request.getParameter("task");

        if ( !StringFuncs.isNullOrEmpty(task) && task.equalsIgnoreCase("save")) {

            if (this.isBlankString(this.mportalAllianceName)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.MportalAllianceUpdateForm.required.mportalAllianceName"));
            }

            if ((this.mportalAllianceName!= null) && (this.mportalAllianceName.length() > 250)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.MportalAllianceUpdateForm.length.mportalAllianceName"));
            }

        }
        log.debug("======= MportalAllianceUpdateForm.validate() ends. \terrors.size= "+errors.size());
        return errors;
    }

    public Long getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Long allianceId) {
        this.allianceId = allianceId;
    }

    public String getAllianceName() {
        return allianceName;
    }

    public void setAllianceName(String allianceName) {
        this.allianceName = allianceName;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getMportalAllianceName() {
        return mportalAllianceName;
    }

    public void setMportalAllianceName(String mportalAllianceName) {
        this.mportalAllianceName = StringFuncs.trim(mportalAllianceName);
    }
}
