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

import com.netpace.aims.bo.system.AimsSalesContactManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.model.system.AimsSalesContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/salesContactViewDelete"
 *                scope="request"
 *	          name="SalesContactForm"
 *                validate="false"
 *                input="/system/salesContactViewDelete.jsp"
 *
 * @struts.action-forward name="view" path="/system/salesContactViewDelete.jsp"
 * @struts.action-forward name="delete" path="/salesContactViewDelete.do?task=view"
 * @author Fawad Sikandar
 */

public class SalesContactViewDeleteAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesContactViewDeleteAction.class.getName());

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
        String forward = "view";

        if (AimsConstants.VIEW_TASK.equalsIgnoreCase(taskname))
        {

            Collection collection = AimsSalesContactManager.getSalesContactList();
            Collection aimsSalesContacts = new ArrayList();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                AimsSalesContact aimsSalesContact = (AimsSalesContact) iter.next();

                SalesContactForm salesContactForm = new SalesContactForm();
                salesContactForm.setSalesContactId(Utility.convertToString(aimsSalesContact.getSalesContactId()));
                salesContactForm.setSalesContactName(aimsSalesContact.getAimsContact().getFirstName() + " " + aimsSalesContact.getAimsContact().getLastName());
                salesContactForm.setTitle(aimsSalesContact.getAimsContact().getTitle());
                salesContactForm.setRegionName(VOLookupFactory.getInstance().getAimsRegion(aimsSalesContact.getRegionId()).getRegionName());

                aimsSalesContacts.add(salesContactForm);
            }
            request.setAttribute("aimsSalesContacts", aimsSalesContacts);
            return mapping.findForward(forward);
        }

        if (AimsConstants.DELETE_TASK.equalsIgnoreCase(taskname))
        {

            AimsSalesContactManager.deleteSalesContact(Integer.parseInt(request.getParameter("salesContactId")));

            return mapping.findForward(taskname);
        }

        return mapping.findForward(forward);
    }
}
