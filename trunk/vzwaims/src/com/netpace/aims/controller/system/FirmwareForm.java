package com.netpace.aims.controller.system;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.form name="FirmwareForm"
 */
public class FirmwareForm extends BaseValidatorForm {

	private Long firmwareId;
	private Long phoneModel;
	private String firmwareName;
	private Integer mrNumber;
	private String status;
	private String description;
	private String task;
	private Boolean sendMail;
	
	public Long getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(Long phoneModel) {
		this.phoneModel = phoneModel;
	}
	public String getFirmwareName() {
		return firmwareName;
	}
	public void setFirmwareName(String firmwareName) {
		this.firmwareName = firmwareName;
	}
	public Integer getMrNumber() {
		return mrNumber;
	}
	public void setMrNumber(Integer mrNumber) {
		this.mrNumber = mrNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Long getFirmwareId() {
		return firmwareId;
	}
	public void setFirmwareId(Long firmwareId) {
		this.firmwareId = firmwareId;
	}	
	public Boolean getSendMail() {
		return sendMail;
	}
	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (this.phoneModel == null){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.FirmwareForm.required.phoneModel"));
        }
		if (this.isBlankString(this.firmwareName)){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.FirmwareForm.required.firmwareName"));
		}
		if (this.mrNumber == null){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.FirmwareForm.required.mrNumber"));
		}
		if (this.isBlankString(this.status)){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.FirmwareForm.required.status"));
		}
		
		if (!errors.isEmpty() || "sendMail".equals(task)){
			try {
				Collection  devicesList=AimsDevicesManager.getDevices(AimsConstants.VZAPPZONE_PLATFORM_ID, true);
				request.setAttribute("devicesList", devicesList);
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
		return errors;
	}
	
	public void validateToDBFields(ActionErrors errors){
		if (this.isBlankString(this.firmwareName) == false && this.firmwareName.length() > 250){
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.FirmwareForm.length.firmwareName"));
        }
        if (this.isBlankString(this.description) == false && this.description.length() > 255){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.FirmwareForm.length.description"));
        }
    }
	
	
}
