package com.netpace.aims.controller.application;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This class takes care of action for display of update form of VZAppZone Application.
 *
 * @struts.action path="/vzAppZoneTestResultsHistory"
 *                scope="request"
 * @struts.action-forward name="view" path="/application/vzAppZoneTestResultsHistory.jsp"
 * @author Sajjad Raza
 */
public class VZAppZoneTestResultsHistoryAction extends BaseAction {
    private static Logger log = Logger.getLogger(VZAppZoneTestResultsHistoryAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String forward = "view";
        HttpSession session = request.getSession();
        Long vzAppZoneAppsId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("vzAppZoneAppsId")));
        Long firmwareId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("firmwareId")));
        Long allianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        List vzAppBinaryFirmwareList = null;
        //show history
        vzAppBinaryFirmwareList = VZAppZoneApplicationManager.getVZAppBinaryFirmwareHistory(vzAppZoneAppsId, allianceId, firmwareId);
        request.setAttribute("vzAppBinaryFirmwareList", vzAppBinaryFirmwareList);        
        return mapping.findForward(forward);
    }
}
