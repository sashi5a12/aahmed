package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsSalesContactManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.system.AimsSalesContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/salesContactSetup"
 *                scope="request"
 *	          name="SalesContactForm"
 *                validate="false"
 *                input="/system/salesContactViewDelete.jsp"
 * @struts.action-forward name="Update" path="/system/salesContactUpdate.jsp"
 *
 * @author Fawad Sikandar
 */

public class SalesContactSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesContactSetupAction.class.getName());

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
        checkAccess(request, AimsPrivilegesConstants.SALES_CONTACTS);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession();
        String forward = "Update";

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
        {

            SalesContactForm aimsSalesContact = new SalesContactForm();
            aimsSalesContact.setRegionList(VOLookupFactory.getInstance().getAimsRegionList());
            aimsSalesContact.setTask(taskname);
            session.setAttribute("aimsSalesContact", aimsSalesContact);

            return mapping.findForward(forward);
        }

        if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {

            AimsSalesContact salesContact = AimsSalesContactManager.getSalesContact(Integer.parseInt(request.getParameter("salesContactId")));
            SalesContactForm aimsSalesContact = new SalesContactForm();
            AimsContact aimsContact = salesContact.getAimsContact();
            aimsSalesContact.setSalesContactId(Utility.convertToString(salesContact.getSalesContactId()));
            aimsSalesContact.setFirstName(aimsContact.getFirstName());
            aimsSalesContact.setLastName(aimsContact.getLastName());
            aimsSalesContact.setTitle(salesContact.getAimsContact().getTitle());
            aimsSalesContact.setRegionList(VOLookupFactory.getInstance().getAimsRegionList());
            aimsSalesContact.setRegionId(Utility.convertToString(salesContact.getRegionId()));
            aimsSalesContact.setEmailAddress(aimsContact.getEmailAddress());
            aimsSalesContact.setPhone(aimsContact.getPhone());
            aimsSalesContact.setTask(taskname);
            session.setAttribute("regionId", Utility.convertToString(salesContact.getRegionId()));
            session.setAttribute("aimsSalesContact", aimsSalesContact);

            return mapping.findForward(forward);

        }
        return mapping.findForward(forward);
    }
}
