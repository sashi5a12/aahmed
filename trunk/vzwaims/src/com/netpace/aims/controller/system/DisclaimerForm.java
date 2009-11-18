package com.netpace.aims.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;
/**
 * @struts.form name="DisclaimerForm"
 */
public class DisclaimerForm extends BaseValidatorForm {
	
	private String taskName;
	private Long disclaimerId;
	private String disclaimerName;
	private String disclaimerText;
	private int disclaimerTextLength; 
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getDisclaimerId() {
		return disclaimerId;
	}
	public void setDisclaimerId(Long disclaimerId) {
		this.disclaimerId = disclaimerId;
	}
	public String getDisclaimerName() {
		return disclaimerName;
	}
	public void setDisclaimerName(String disclaimerName) {
		this.disclaimerName = disclaimerName;
	}
	public String getDisclaimerText() {
		return disclaimerText;
	}
	public void setDisclaimerText(String disclaimerText) {
		this.disclaimerText = disclaimerText;
	}	
	public int getDisclaimerTextLength() {
		return disclaimerTextLength;
	}
	public void setDisclaimerTextLength(int disclaimerTextLength) {
		this.disclaimerTextLength = disclaimerTextLength;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors=new ActionErrors();
		validateToDBFields(errors);
		if (isBlankString(this.disclaimerName)){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("DisclaimerForm.error.required.disclaimerName"));
		}
		if (isBlankString(this.disclaimerText)){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("DisclaimerForm.error.required.disclaimerText"));
		}
		return errors;
	}

	private void validateToDBFields(ActionErrors errors){
		if ((this.disclaimerName != null) && (this.disclaimerName.length() > 100)){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("DisclaimerForm.error.disclaimerName.length"));
		}
		if (disclaimerTextLength > 1000){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("DisclaimerForm.error.disclaimerText.length"));
		}
	}
}
