package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;

import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsWapAppsVersion;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/wapVersionDifference"
 *                name="WapVersionDifferenceForm"
 *                input="/application/wapApplicationUpdate.jsp"
 *				  validate="false"
 * @struts.action-forward name="view" path="/application/wapVersionDifference.jsp"
 * @author Adnan Makda
 */

public class WapVersionDifferenceAction extends BaseAction
{
    static Logger log = Logger.getLogger(WapVersionDifferenceAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession(false);
        DynaValidatorForm frm = (DynaValidatorForm) form;

        //TODO: Do not allow Alliance to see this page. Can make this a privilege.
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Collection wapAppVersions = null;
        Collection wapAppVersionInfo = null;
        String createdBy = "";
        String createdDate = "";
        Long wapAppsId = new Long(0);
        String oldVersion = "";
        String newVersion = "";
        Long clonedFromId = new Long(0);

        MessageResources msgRes = this.getResources(request, "com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE");
        
        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            throw new AimsSecurityException();            
        
        if (request.getParameter("wapAppsId") != null)
            wapAppsId = new Long((String) request.getParameter("wapAppsId"));

        if (request.getParameter("oldVersion") != null)
            oldVersion = (String) request.getParameter("oldVersion");

        if (request.getParameter("newVersion") != null)
            newVersion = (String) request.getParameter("newVersion");

        if (request.getParameter("clonedFromId") != null)
            clonedFromId = new Long((String) request.getParameter("clonedFromId"));

        if (clonedFromId.longValue() == 0)
            wapAppVersions = WapApplicationManager.getWapAppVersionInformation(wapAppsId, oldVersion, newVersion);
        else
            wapAppVersions = WapApplicationManager.getDifferenceOfClonedApp(wapAppsId, clonedFromId, currentUserAllianceId, dateFormat);

        if (wapAppVersions != null)
        {
            AimsWapAppsVersion aimsWapAppsVersion = null;
            wapAppVersionInfo = new Vector();
            for (Iterator it = wapAppVersions.iterator(); it.hasNext();)
            {
                aimsWapAppsVersion = (AimsWapAppsVersion) it.next();
                WapAppVersionBean wapAppVersionBean = WapApplicationHelper.getWapAppVersionBean(aimsWapAppsVersion, msgRes);
                createdBy = aimsWapAppsVersion.getCreatedBy();
                createdDate = Utility.convertToString(aimsWapAppsVersion.getCreatedDate(),dateFormat);
                wapAppVersionInfo.add(wapAppVersionBean);
            }
        }

        request.setAttribute("wapAppVersionInfo", wapAppVersionInfo);
        
        if (clonedFromId.longValue() == 0)
        {
            request.setAttribute("HeaderInfo", "Differences between Version " + newVersion + " and Version " + oldVersion);
            request.setAttribute("CreationInfo", "New Version Created By: " + createdBy + " On: " + createdDate);
        }
        else
            request.setAttribute("HeaderInfo", "Differences between Applications");

        return mapping.findForward("view");
    }
}
