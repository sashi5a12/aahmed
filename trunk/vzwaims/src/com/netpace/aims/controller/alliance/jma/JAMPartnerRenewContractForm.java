package com.netpace.aims.controller.alliance.jma;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="JAMPartnerRenewContractForm"
 * @author aqureshi
 *
 */

public class JAMPartnerRenewContractForm extends BaseValidatorForm {
	
	private String task;
	private String sendEmail; // possible values will be Y and N
	private String appId;

	
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
