package com.netpace.aims.controller.system;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.system.AimsSalesContactManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsSalesContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/salesContactInsertUpdate"
 *                scope="request"
 *	          name="SalesContactForm"
 *                validate="true"
 *                input="/system/salesContactUpdate.jsp"
 * @struts.action-forward name="view" path="/salesContactViewDelete.do?task=view" redirect="true"
 * @struts.action-exception key="error.system.salescontact.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Fawad Sikandar
 */

public class SalesContactInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(SalesContactInsertUpdateAction.class.getName());

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
        HttpSession session = request.getSession();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        SalesContactForm salesContactForm = (SalesContactForm) form;

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname) || AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {
            AimsSalesContact aimsSalesContact = new AimsSalesContact();
            AimsContact aimsContact = new AimsContact();

            aimsSalesContact.setRegionId(Utility.convertToLong(salesContactForm.getRegionId()));

            aimsContact.setFirstName(salesContactForm.getFirstName());
            aimsContact.setLastName(salesContactForm.getLastName());
            aimsContact.setTitle(salesContactForm.getTitle());
            aimsContact.setPhone(salesContactForm.getPhone());
            aimsContact.setEmailAddress(salesContactForm.getEmailAddress());
            aimsContact.setType(AimsConstants.NON_NULLABLE_FIELD_STRING);
            aimsContact.setLastUpdatedBy(currUser);
            aimsContact.setLastUpdatedDate(new Date());
            aimsContact.setCreatedBy(currUser);
            aimsContact.setCreatedDate(new Date());

            aimsSalesContact.setSalesContactId(Utility.convertToLong(salesContactForm.getSalesContactId()));
            aimsSalesContact.setLastUpdatedBy(currUser);
            aimsSalesContact.setLastUpdatedDate(new Date());
            aimsSalesContact.setCreatedBy(currUser);
            aimsSalesContact.setCreatedDate(new Date());

            aimsSalesContact.setAimsContact(aimsContact);
            AimsSalesContactManager.saveOrUpdateSalesContact(aimsSalesContact);

            return mapping.findForward(forward);
        }
        return mapping.findForward(forward);
    }
}
