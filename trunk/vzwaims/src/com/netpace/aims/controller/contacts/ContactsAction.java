package com.netpace.aims.controller.contacts;

import com.netpace.aims.bo.contacts.ContactsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;


/**
 * This class allows the user to proceed to the Add/Update Contacts.
 *
 * @struts.action path="/contacts"
 *                name="ContactForm"
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="viewList" path="/contacts/contactsList.jsp"
 * @author Sajjad Raza
 */
public class ContactsAction extends BaseAction {

    private static Logger log = Logger.getLogger(ContactsAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug("==== start ContactsAction, task= "+taskname);
        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserName = currUser.getUsername();
        String currUserType = currUser.getUserType();
        Long currUserId = currUser.getUserId();
        Long alliance_id = new Long(-1);

        ContactForm contactForm = (ContactForm) form;

        Collection AimsContacts = null;
        int PAGE_LENGTH = currUser.getRowsLength().intValue();
        int pageNo, rows, pmax = 1;
        String forward = "viewList";

        try {
            pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        }
        catch (Exception e) {
            pageNo = 1;
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0) {
            contactForm.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "email"));
        }
        else {
            contactForm.setFilterField(StringFuncs.initializeStringGetParam(contactForm.getFilterField(), "email"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0) {
            contactForm.setFilterText(request.getParameter("filter_text"));
        }

        log.debug("contactForm.getFilterField() : " + contactForm.getFilterField());
        log.debug("contactForm.getFilterText() : " + contactForm.getFilterText());

        if (taskname.equalsIgnoreCase("viewList")) {
            if (currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) {

                alliance_id = currUser.getAimsAllianc();

                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.SELECT))) {
                    throw new AimsSecurityException();
                }

                rows = ContactsManager.getAllAllianceContactsCount(alliance_id, true,
                        getFilterExpression(
                            StringFuncs.initializeStringGetParam(contactForm.getFilterField(), ""),
                            StringFuncs.initializeStringGetParam(contactForm.getFilterText(), "")));

                log.debug("ContactsAction: Number of rows = " + rows);

                pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

                if (pageNo < 1) {
                    pageNo = 1;
                }
                else if (pageNo > pmax) {
                    pageNo = pmax;
                }

                AimsContacts = ContactsManager.getAllAllianceContacts(alliance_id, true, PAGE_LENGTH, pageNo,
                        getFilterExpression(
                            StringFuncs.initializeStringGetParam(contactForm.getFilterField(), ""),
                            StringFuncs.initializeStringGetParam(contactForm.getFilterText(), "")));

                log.debug("ContactsAction: Number of contacts = " + AimsContacts.size());
                request.setAttribute("AimsContacts", AimsContacts);

                request.setAttribute("page_id", new Integer(pageNo));
                request.setAttribute("page_max", new Integer(pmax));

            }
            forward = "viewList";
        }

        request.setAttribute("task", "viewList");

        log.debug("==== end ContactsAction, forward= "+forward);
        return mapping.findForward(forward);
    }

    private String getFilterExpression(String filter_field, String filter_text) {

        StringBuffer expressionBuffer = new StringBuffer("");

        if (filter_text.trim().length() > 0) {
            if (filter_field.equalsIgnoreCase("email")) {
                expressionBuffer.append("and upper(aimsContact.emailAddress) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            if (filter_field.equalsIgnoreCase("firstName")) {
                expressionBuffer.append("and upper(aimsContact.firstName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            if (filter_field.equalsIgnoreCase("lastName")) {
                expressionBuffer.append("and upper(aimsContact.lastName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            if (filter_field.equalsIgnoreCase("createdDate")) {
                if (AimsUtils.isDate(filter_text.trim()))
                {
                    expressionBuffer.append("and trunc(aimsContact.createdDate) = to_date('" + filter_text.trim() + "', 'MM/dd/yyyy')");
                }
                else
                {
                    expressionBuffer.append("and aimsContact.createdDate = to_date('12/31/9999', 'MM/dd/yyyy')");
                }
            }
        }

        return expressionBuffer.toString();
    }//end getFilter Expression
}
