package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsCertificationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.system.AimsCertification;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/certificationSetup"
 *                scope="request"
 *	          name="CertificationForm"
 *                validate="false"
 *                input="/system/certificationViewDelete.jsp"
 * @struts.action-forward name="Update" path="/system/certificationUpdate.jsp"
 * @author Fawad Sikandar
 */

public class CertificationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(CertificationSetupAction.class.getName());

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
        HttpSession session = request.getSession();
        String forward = "Update";

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
        {

            CertificationForm aimsCertification = new CertificationForm();
            aimsCertification.setTask(taskname);
            session.setAttribute("aimsCertification", aimsCertification);
            return mapping.findForward(forward);
        }

        if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {

            AimsCertification certification = AimsCertificationManager.getCertification(Integer.parseInt(request.getParameter("certId")));
            CertificationForm aimsCertification = new CertificationForm();
            aimsCertification.setCertId(Utility.convertToString(certification.getCertificationId()));
            aimsCertification.setCertName(certification.getCertificationName());
            aimsCertification.setCertDesc(certification.getCertificationDesc());
            aimsCertification.setCertOrg(certification.getCertificationOrg());
            aimsCertification.setCertUrl(certification.getCertificationUrl());

            aimsCertification.setTask(taskname);
            session.setAttribute("aimsCertification", aimsCertification);

            return mapping.findForward(forward);

        }
        return mapping.findForward(forward);
    }
}
