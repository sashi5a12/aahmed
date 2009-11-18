package com.netpace.aims.controller.accounts;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.StringFuncs;


/*****************************
    //commented for accounts cleanup, user can not change password
    //disabled xdoclet struts actions, forwards and forms
    struts.form name="AccountsChangePasswordForm"
    author Adnan Ahmed.
*****************************/
public class AccountsChangePasswordForm extends BaseValidatorForm{
    
	private static final Logger log = Logger.getLogger(AccountsChangePasswordForm.class.getName());	
	    
	private Long accountId;
	private String newPwd;
	private String confirmPwd;
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		String accountId=StringFuncs.NulltoZeroStringReplacement(request.getParameter("accountId"));		
		this.setAccountId(new Long(accountId));		
	}
		
}
