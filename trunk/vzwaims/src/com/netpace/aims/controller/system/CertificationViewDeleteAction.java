package com.netpace.aims.controller.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @struts.action path="/certificationViewDelete"
 *                scope="request"
 *	          name="CertificationForm"
 *                validate="false"
 *                input="/system/certificationViewDelete.jsp"
 *
 * @struts.action-forward name="view" path="/system/certificationViewDelete.jsp"
 * @struts.action-forward name="delete" path="/certificationViewDelete.do?task=view"
 * @author Fawad Sikandar
 */

public class CertificationViewDeleteAction extends BaseAction
{

    static Logger log = Logger.getLogger(CertificationViewDeleteAction.class.getName());

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

        if (AimsConstants.VIEW_TASK.equalsIgnoreCase(taskname))
        {

            Collection collection = AimsCertificationManager.getCertificationList();
            Collection aimsCertifications = new ArrayList();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                AimsCertification aimsCertification = (AimsCertification) iter.next();

                CertificationForm certificationForm = new CertificationForm();
                certificationForm.setCertId(Utility.convertToString(aimsCertification.getCertificationId()));
                certificationForm.setCertName(aimsCertification.getCertificationName());
                certificationForm.setCertDesc(aimsCertification.getCertificationDesc());
                certificationForm.setCertOrg(aimsCertification.getCertificationOrg());
                certificationForm.setCertUrl(aimsCertification.getCertificationUrl());

                aimsCertifications.add(certificationForm);
            }
            request.getSession().setAttribute("aimsCertifications", aimsCertifications);
            return mapping.findForward(forward);
        }

        if (AimsConstants.DELETE_TASK.equalsIgnoreCase(taskname))
        {

            AimsCertificationManager.deleteCertification(Integer.parseInt(request.getParameter("certId")));

            return mapping.findForward(taskname);
        }

        return mapping.findForward(forward);
    }
}
