package com.netpace.aims.controller.contacts;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityExceptionForPopup;
import com.netpace.aims.bo.security.AimsSecurityManager;
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


/**
 * This class allows the user to proceed to the Add/Update Contacts.
 *
 * @struts.action path="/contactsSetup"
 *                name="ContactForm"
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="contactUpdatePopup" path="/contacts/contactUpdatePopup.jsp"
 * @struts.action-forward name="contactUpdate" path="/contacts/contactUpdate.jsp"
 * @struts.action-forward name="contactView" path="/contacts/contactView.jsp"
 * @struts.action-forward name="listContacts" path="/contacts.do?task=viewList" 
 * @author Sajjad Raza
 */
public class ContactsSetupAction extends BaseAction {

    private static Logger log = Logger.getLogger(ContactsSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug("==== start ContactsSetupAction, task= "+taskname);
        ContactForm contactForm = (ContactForm) form;
        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String currUserType = currUser.getUserType();
		Long currUserId = currUser.getUserId();
		String forward = "";

        if (currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) {
			if (taskname.equalsIgnoreCase("create")) {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT)))
                {
                    throw new AimsSecurityException();
                }
                contactForm.setContactId(new Long(0));
                forward = "contactUpdate";
			}
			else if (taskname.equalsIgnoreCase("edit")) {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.UPDATE))) {
                    throw new AimsSecurityException();
                }
                forward = "contactUpdate";
			}
            else if (taskname.equalsIgnoreCase("view")) {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.SELECT))) {
                    throw new AimsSecurityException();
                }
                forward = "contactView";
			}
        }

		if (taskname.equalsIgnoreCase("createFormPopup")) {
			//Alliance Users
			if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(currUserType)) {
				if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT))) {
					throw new AimsSecurityExceptionForPopup();
				}
            }
            else {
                //for vzw user
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))) {
                    throw new AimsSecurityException();
                }
            }
            
            contactForm.setTask("create");
			request.setAttribute("alliance_id", request.getParameter("alliance_id"));
            request.setAttribute("isPopup", request.getParameter("isPopup"));
            request.setAttribute("cType", request.getParameter("cType"));
            request.setAttribute("parentPageType", request.getParameter("parentPageType"));
            request.setAttribute("parentPath", request.getParameter("parentPath"));
            forward = "contactUpdatePopup";
		}
        log.debug("==== end ContactsSetupAction, forward= "+forward);
        return mapping.findForward(forward);
    }
}
