package com.netpace.aims.controller.contacts;

import org.apache.struts.action.*;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.DBHelper;

import com.netpace.aims.bo.security.*;
import com.netpace.aims.bo.masters.*;

import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.bo.accounts.*;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.alliance.AllianceContactInfoManager;
import com.netpace.aims.bo.contacts.ContactsManager;

import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class takes care of action to save/update AimsContacts.
 *
 * @struts.action path="/contactEdit"
 *                scope="request"
 *				  name="ContactForm"
 *				  validate="false"
 *                input="/contacts/contactUpdate.jsp"
 * @struts.action-forward name="contactUpdate" path="/contacts/contactUpdate.jsp"
 * @struts.action-forward name="listContacts" path="/contacts.do?task=viewList" redirect="true
 * @struts.action-forward name="error" path="/contacts/contactUpdate.jsp"
 * @struts.action-forward name="errorPopup" path="/contacts/contactUpdatePopup.jsp"
 * @struts.action-forward name="closePopup" path="/common/closePopupWindow.jsp"
 * @author Sajjad Raza
 */
public class ContactEditAction extends BaseAction
{

    private static Logger log = Logger.getLogger(ContactEditAction.class.getName());


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String taskname =  request.getParameter("task");
        log.debug("==== start ContactEditAction, task= "+taskname);

        String isPopup=request.getParameter("isPopup");
        String type=request.getParameter("cType");

        String parentPageType = StringFuncs.NullValueReplacement(request.getParameter("parentPageType"));
        String parentPath = StringFuncs.NullValueReplacement(request.getParameter("parentPath"));

        String forward = "";
        ContactForm contactForm = (ContactForm) form;
        ActionErrors errors=new ActionErrors();

        HttpSession session = request.getSession();
		AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserType = currUser.getUserType();
        String currUserName = currUser.getUsername();
        Date currDate = new Date();

        AimsContact aimsContact = null;
        Long contactId = Utility.ZeroValueReplacement(contactForm.getContactId());

        Long allianceId = new Long(0);
        AimsAllianc aimsAlliance = null;
        if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(currUserType)){
        	allianceId = currUser.getAimsAllianc();
        }
        else {
        	allianceId = new Long(StringFuncs.initializeIntGetParam(request.getParameter("alliance_id"), 0));
        	request.setAttribute("alliance_id", allianceId);
        }

        if(Utility.ZeroValueReplacement(allianceId).longValue()>0) {
            try {
                aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, allianceId.toString());
            }
            catch(Exception e) {
                log.error("ContactEditAction: exception caught while loading alliance, allianceId= "+allianceId);
                e.printStackTrace();
            }
        }
        else {
            aimsAlliance = null;
            log.debug("ContactEditAction: alliance id is null");
        }

        if (StringUtils.isNotEmpty(type)){
    		request.setAttribute("cType", type);
    	}
    	if(StringUtils.isNotEmpty(isPopup)){
			request.setAttribute("isPopup", isPopup);
    	}

        if(StringUtils.isNotEmpty(parentPageType)) {
            request.setAttribute("parentPageType", parentPageType);
        }

        if(StringUtils.isNotEmpty(parentPath)) {
            request.setAttribute("parentPath", parentPath);
        }

        //start validation
        errors.clear();
    	errors = contactForm.validate(mapping, request);
        if(!errors.isEmpty()){
            log.debug("errors found in ContactForm.validate size= "+errors.size());
            super.saveErrors(request, errors);
    		if (StringUtils.isNotEmpty(isPopup) && "true".equalsIgnoreCase(isPopup)){
    			return mapping.findForward("errorPopup");
    		}
    		else {
    			return mapping.findForward("error");
    		}
    	}
        //end validation

        if (taskname.equalsIgnoreCase("create")) {

            if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(currUserType)) {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT))) {
                    throw new AimsSecurityException();
                }
                if(aimsAlliance==null) {
                    //for alliance user, aimsAlliance must be available to set alliance id while creating contact
                    throw new AimsSecurityException();
                }
            }
            else {
                //for vzw user
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))) {
                    throw new AimsSecurityException();
                }
            }

            aimsContact = new AimsContact();
            aimsContact.setEmailAddress(contactForm.getContactEmailAddress());
            aimsContact.setFirstName(contactForm.getContactFirstName());
            aimsContact.setLastName(contactForm.getContactLastName());
            aimsContact.setTitle(contactForm.getContactTitle());
            aimsContact.setPhone(contactForm.getContactPhone());
            aimsContact.setMobile(contactForm.getContactMobile());
            aimsContact.setFax(contactForm.getContactFax());
            aimsContact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);//alliance contact type

            if(aimsAlliance!=null) {
                aimsContact.setAlliance(aimsAlliance );
            }

            aimsContact.setCreatedBy(currUserName);
            aimsContact.setCreatedDate(currDate);
            aimsContact.setLastUpdatedBy(currUserName);
            aimsContact.setLastUpdatedDate(currDate);

            ContactsManager.saveOrUpdateAimsContact(aimsContact);

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.create.success"));
            saveMessages(request, messages);
            contactForm.setTask("edit");
            contactForm.setContactId(aimsContact.getContactId());
            forward = "contactUpdate";
        }//end create
        else if (taskname.equalsIgnoreCase("edit")) {
            if(currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.UPDATE))) {
                    throw new AimsSecurityException();
                }
            }
            else {
                //for vzw user
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.UPDATE))) {
                    throw new AimsSecurityException();
                }
            }

            aimsContact = ContactsManager.getAimsContact(contactId, allianceId);

            if(aimsContact == null) {
                throw new AimsSecurityException();
            }

            aimsContact.setFirstName(contactForm.getContactFirstName());
            aimsContact.setLastName(contactForm.getContactLastName());
            aimsContact.setTitle(contactForm.getContactTitle());
            aimsContact.setPhone(contactForm.getContactPhone());
            aimsContact.setMobile(contactForm.getContactMobile());
            aimsContact.setFax(contactForm.getContactFax());
            aimsContact.setLastUpdatedBy(currUserName);
            aimsContact.setLastUpdatedDate(currDate);

            ContactsManager.saveOrUpdateAimsContact(aimsContact);

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.edit.success"));
            saveMessages(request, messages);

            forward = "contactUpdate";
        }//end edit
        else if(taskname.equalsIgnoreCase("cancel")) {
            forward = "listContacts";
        }

        if (StringUtils.isNotEmpty(isPopup) && "true".equalsIgnoreCase(isPopup)){
            if(parentPageType.equals(AimsConstants.PAGE_TYPE_ALLIANCE_CONTACT_UPDATE)) {
                request.setAttribute("CLOSE", AimsConstants.PAGE_TYPE_ALLIANCE_CONTACT_UPDATE);
                request.setAttribute("contactId", aimsContact.getContactId().toString());
            }
            //if parentPageType is alliance contact info update (login content), change close value
            if(parentPageType.equals(AimsConstants.PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE)) {
                request.setAttribute("CLOSE", AimsConstants.PAGE_TYPE_LOGIN_ALLIANCE_CONTACT_UPDATE);
                request.setAttribute("contactId", aimsContact.getContactId().toString());
            }

            forward = "closePopup";
        }

        log.debug("==== end ContactEditAction, forward= "+forward);
        return mapping.findForward(forward);
    }
}
