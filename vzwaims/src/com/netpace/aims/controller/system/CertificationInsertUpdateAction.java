package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsCertificationManager;
import com.netpace.aims.bo.system.SystemConstants;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsCertification;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/certificationInsertUpdate"
 *                scope="request"
 *	          name="CertificationForm"
 *                validate="true"
 *                input="/system/certificationUpdate.jsp"
 * @struts.action-forward name="view" path="/certificationViewDelete.do?task=view" redirect="true"
 * @author Fawad Sikandar
 */

public class CertificationInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(CertificationInsertUpdateAction.class.getName());

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a
     * User object if the user is valid. It then moves the user to his logged in page.
     *
     * If the username and password does not match it throws
     * a InvalidUserException.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.CERTIFICATIONS);

        String taskname = request.getParameter("task");
        String forward = "view";
        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        CertificationForm certificationForm = (CertificationForm) form;

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname) || AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {

            AimsCertification aimsCertification = new AimsCertification();
            aimsCertification.setCertificationId(Utility.convertToLong(certificationForm.getCertId()));
            aimsCertification.setCertificationDesc(certificationForm.getCertDesc());
            aimsCertification.setCertificationName(certificationForm.getCertName());
            aimsCertification.setCertificationOrg(certificationForm.getCertOrg());
            aimsCertification.setCertificationUrl(certificationForm.getCertUrl());

            if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
            {
                aimsCertification.setCreatedBy(currUser);
                aimsCertification.setCreatedDate(SystemConstants.currentDate);
            }
            if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
            {
                aimsCertification.setCreatedBy(certificationForm.getCreatedBy());
                aimsCertification.setCreatedDate(Utility.convertToDate(certificationForm.getCreatedDate(), dateFormat));
            }

            aimsCertification.setLastUpdatedBy(currUser);
            aimsCertification.setLastUpdatedDate(SystemConstants.currentDate);
            AimsCertificationManager.saveOrUpdateCertification(aimsCertification);

            return mapping.findForward(forward);
        }
        return mapping.findForward(forward);
    }
}
