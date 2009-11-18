package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.AimsConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @struts.form name="AllianceJournalEntryUpdateForm"
 */
public class AllianceJournalEntryUpdateForm extends BaseValidatorForm {

    static Logger log = Logger.getLogger(AllianceJournalEntryUpdateForm.class.getName());
		
    private java.lang.Long journalId;
    private java.lang.String journalText;
    private java.lang.String journalCombinedText;
    private java.lang.String journalType;
    private java.lang.String createdBy;
    private java.lang.String createdDate;
    private java.lang.Long aimsAppId;
    private java.lang.Long aimsAllianceId;
    private java.lang.String companyName;
    private java.lang.String task;


    public java.lang.Long getJournalId() {
        return this.journalId;
    }

    public void setJournalId(java.lang.Long journalId) {
        this.journalId = journalId;
    }

    public java.lang.String getJournalText() {
        return this.journalText;
    }

    public void setJournalText(java.lang.String journalText) {
        this.journalText = journalText;
    }

		public java.lang.String getJournalCombinedText() {
        return this.journalCombinedText;
    }

    public void setJournalCombinedText(java.lang.String journalCombinedText) {
        this.journalCombinedText = journalCombinedText;
    }
    
    public java.lang.String getJournalType() {
        return this.journalType;
    }

    public void setJournalType(java.lang.String journalType) {
        this.journalType = journalType;
    }

    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public java.lang.String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.lang.String createdDate) {
        this.createdDate = createdDate;
    }

    public java.lang.Long getAimsAppId() {
        return this.aimsAppId;
    }

    public void setAimsAppId(java.lang.Long aimsAppId) {
        this.aimsAppId = aimsAppId;
    }

    public java.lang.Long getAimsAllianceId() {
        return this.aimsAllianceId;
    }

    public void setAimsAllianceId(java.lang.Long aimsAllianceId) {
        this.aimsAllianceId = aimsAllianceId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public  java.lang.String  getTask() {
        return this.task;
    }

    public void setTask( java.lang.String  task) {
        this.task = task;
    }


		public void reset (ActionMapping mapping, HttpServletRequest request)   {
			
			log.debug("\n\nIn Reset of AllianceJournalEntryUpdateForm: \n\n");	
		}		

	
		public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	
		{			
			HttpSession session = request.getSession();
			ActionErrors errors	=	new	ActionErrors();
			
			if (this.isBlankString(this.journalText)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.journal.text.required"));
            }
            else if(this.journalText.length()>2000){
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.journal.text.length"));
            }
            if(this.isBlankString(this.journalType)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.journal.type.required"));
            }
            else if(!this.journalType.equals(AimsConstants.JOURNAL_TYPE_PRIVATE)
                    && !this.journalType.equals(AimsConstants.JOURNAL_TYPE_PUBLIC)){
                //journal type must be private or public
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.journal.type.required"));
            }
            return errors;
		}

}

