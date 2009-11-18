package com.netpace.aims.controller.profile;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;

/*****************************
    //commented for accounts cleanup, user can not change password
    //disabled xdoclet struts actions, forwards and forms

     struts.form name="ProfileChangePasswordForm"
     author Adnan Ahmed.
*****************************/

public class ProfileChangePasswordForm extends BaseValidatorForm{
    
	private static final Logger log = Logger.getLogger(ProfileChangePasswordForm.class.getName());	
	    
	private String oldPwd;
	private String newPwd;
	private String confirmPwd;
	
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors	=	new	ActionErrors();
		
		if ( isBlankString(this.oldPwd) ) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.changePassword.required.currentPassword"));
		}
		if ( isBlankString(this.newPwd) ) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.changePassword.required.newPassword"));
		}
		if ( isBlankString(this.confirmPwd) ) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.changePassword.required.confirmPassword"));
		}
		else if (!isBlankString(this.newPwd) && !this.newPwd.equals(this.confirmPwd)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.changePassword.passNotMatch"));
		}
		return errors;
	}
	
	
}
