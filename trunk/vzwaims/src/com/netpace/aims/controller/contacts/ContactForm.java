package com.netpace.aims.controller.contacts;

import com.netpace.aims.bo.contacts.ContactsManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @struts.form	name="ContactForm"
 */
public class ContactForm extends BaseValidatorForm {

    private static Logger log = Logger.getLogger(ContactForm.class.getName());

    private String task;

    private String filterText;
    private String filterField;

    private String parentPageType;
    private String parentPath;

    private Long contactId;
    private String contactEmailAddress;
    private String contactFirstName;
    private String contactLastName;
    private String contactTitle;
    private String contactPhone;
    private String contactMobile;
    private String contactFax;

    public void	reset(ActionMapping mapping, HttpServletRequest request) {
        log.debug("The RESET is called in ContactForm!");

        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserType = currUser.getUserType();
        String taskname	= StringFuncs.NullValueReplacement(request.getParameter("task"));
        Long reqContactId = new Long(StringFuncs.initializeIntGetParam(request.getParameter("contactId"), 0));

        AimsContact aimsContact = null;

        if (taskname.equalsIgnoreCase("edit") || taskname.equalsIgnoreCase("view") && Utility.ZeroValueReplacement(reqContactId).longValue()>0) {
            try {
                if (AimsConstants.ALLIANCE_USERTYPE.equalsIgnoreCase(currUserType)) {
                    aimsContact = ContactsManager.getAimsContact(reqContactId, currUser.getAimsAllianc());
                }
                else {
                    //vzw
                    aimsContact = ContactsManager.getAimsContact(reqContactId, null);
                }

            }
            catch(HibernateException he) {
                log.error("A hibernate exception occured!");
                he.printStackTrace();
            }
            catch(Exception e) {
                log.error("An exception occured!");
                e.printStackTrace();
            }

            if(aimsContact!=null) {
                this.setContactId(aimsContact.getContactId());
                this.setContactEmailAddress(aimsContact.getEmailAddress());
                this.setContactFirstName(aimsContact.getFirstName());
                this.setContactLastName(aimsContact.getLastName());
                this.setContactTitle(aimsContact.getTitle());
                this.setContactPhone(aimsContact.getPhone());
                this.setContactMobile(aimsContact.getMobile());
                this.setContactFax(aimsContact.getFax());
            }//end check access

        }//end edit form or view
    }//end reset

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
	 	ActionErrors errors = new ActionErrors();
        String taskname	= StringFuncs.NullValueReplacement(request.getParameter("task"));
        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserType = currUser.getUserType();
        Long alliance_id = new Long(0);

        if(taskname.equalsIgnoreCase("create") || taskname.equalsIgnoreCase("edit")) {
            if (this.isBlankString(this.contactEmailAddress)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.emailAddress"));
            }
            else {
                if (!this.isValidEmail(this.contactEmailAddress)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.validate.emailAddress"));
                }
                if(taskname.equalsIgnoreCase("create")) {

                    if(currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) {
                        alliance_id = currUser.getAimsAllianc();
                    }
                    else {
                        alliance_id = new Long(StringFuncs.initializeIntGetParam(request.getParameter("alliance_id"), 0));
                    }

                    if(Utility.ZeroValueReplacement(alliance_id).longValue()>0) {
                        //for alliance contact, check duplicate email for same alliance while creating contact
                        if(!this.validateUniqueEmailForAlliance(this.contactEmailAddress, currUser.getAimsAllianc())) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contact.duplicate.emailAddress", this.contactEmailAddress));
                        }
                    }//end allianceid
                }//end create
            }

            if (this.isBlankString(this.contactFirstName)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.firstName"));
            }
            if (this.isBlankString(this.contactLastName)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.lastName"));
            }

            if (this.isBlankString(this.contactTitle)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.title"));
            }
        }

        return errors;
	}

    private boolean validateUniqueEmailForAlliance(String email, Long allianceId) {
        boolean validated = false;
        AimsContact contact = null;
        try {
            contact = ContactsManager.getAimsContactByEmail(email, allianceId);
            //if email not found then set validated = true
            if(contact == null ) {
                validated =true;
            }
        }
        catch(HibernateException he) {
                log.error("A hibernate exception occured!");
                he.printStackTrace();
            }
            catch(Exception e) {
                log.error("An exception occured!");
                e.printStackTrace();
            }
        return validated;
    }//end validateUniqueEmailForAlliance

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public String getParentPageType() {
        return parentPageType;
    }

    public void setParentPageType(String parentPageType) {
        this.parentPageType = parentPageType;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax;
    }
}
