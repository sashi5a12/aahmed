package com.netpace.aims.controller.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.system.FirmwareManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.system.AimsDeviceFirmware;
import com.netpace.aims.model.system.AimsVzappFirmwareEmailLog;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.CommonProperties;
import com.netpace.aims.util.MailUtils;

/**
 *
 * @struts.action path="/firmwareCreateUpdate"
 *                name="FirmwareForm"
 *                scope="request"
 *				  validate="true"
 *				  input="/system/firmwareCreateUpdate.jsp"
 * @struts.action-forward name="list" path="/firmwareSetup.do?task=list"
 * @struts.action-forward name="edit" path="/system/firmwareCreateUpdate.jsp"
 */
public class FirmwareCreateUpdateAction extends BaseAction {
	private static final Logger log = Logger.getLogger(FirmwareCreateUpdateAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, 
								 ActionForm form, 
								 HttpServletRequest request, 
								 HttpServletResponse response) throws Exception{
		
		log.debug("FirmwareCreateUpdateAction.execute Start:");
		
		String forward="list";
		FirmwareForm firmwareForm=(FirmwareForm)form;
		HttpSession session = request.getSession();
		AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		String taskName=firmwareForm.getTask();		
        
        log.debug("taskName="+taskName);
        
        if (!"sendMail".equalsIgnoreCase(firmwareForm.getTask())){
            if(!super.isTokenValid(request, true)){        	
                log.debug("Duplicate request.");
                return mapping.findForward("list");
            }
        	
	        if (!checkAccess(request, taskName,AimsPrivilegesConstants.MANAGE_FIRMWARE)){
				throw new AimsSecurityException();
			}
        }
        if ("create".equals(taskName)){
        	AimsDeviceFirmware firmware=new AimsDeviceFirmware();
			firmware.setDeviceId(firmwareForm.getPhoneModel());
			firmware.setFirmwareName(firmwareForm.getFirmwareName().trim());
			firmware.setMrNumber(firmwareForm.getMrNumber());
			firmware.setStatus(firmwareForm.getStatus());
			if (StringUtils.isNotEmpty(firmwareForm.getDescription())){
				firmware.setDescription(firmwareForm.getDescription().trim());
			}
			firmware.setCreatedBy(user.getUsername());
			firmware.setCreatedDate(new Date());
			try {
				FirmwareManager.saveOrUpdate(firmware);				
				if (firmwareForm.getSendMail() != null && firmwareForm.getSendMail().booleanValue()){
					firmwareForm.setFirmwareId(firmware.getFirmwareId());
					sendMaidToVzappZonDevelopers(firmwareForm,user.getUsername());
				}
		        saveMessages(request, "message.create.success");		        
			}
			catch (UniqueConstraintException e) {
                saveErrors(request, e.getMessageKey());
			}			
        }
        else if ("edit".equals(taskName)){
        	AimsDeviceFirmware firmware=new AimsDeviceFirmware();
        	firmware.setFirmwareId(firmwareForm.getFirmwareId());
			firmware.setDeviceId(firmwareForm.getPhoneModel());
			firmware.setFirmwareName(firmwareForm.getFirmwareName().trim());
			firmware.setMrNumber(firmwareForm.getMrNumber());
			firmware.setStatus(firmwareForm.getStatus());
			if (StringUtils.isNotEmpty(firmwareForm.getDescription())){
				firmware.setDescription(firmwareForm.getDescription().trim());
			}
			firmware.setLastUpdatedBy(user.getUsername());
			firmware.setLastUpdatedDate(new Date());
			try {
				FirmwareManager.saveOrUpdate(firmware);
		        saveMessages(request, "message.edit.success");				
			}
			catch (UniqueConstraintException e) {
                saveErrors(request, e.getMessageKey());
			}
        }
        else if ("sendMail".equals(firmwareForm.getTask())){
        	sendMaidToVzappZonDevelopers(firmwareForm,user.getUsername());
	        saveMessages(request, "message.FirmwareForm.sendMailToAlliance");
        	forward="edit";
        }
        log.debug("FirmwareCreateUpdateAction.execute End:");
		return mapping.findForward(forward);
	}
	private void saveMessages(HttpServletRequest request, String messageKey){
		ActionMessages messages = new ActionMessages();
        ActionMessage message = new ActionMessage(messageKey);
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
        saveMessages(request, messages);
	}
	private void saveErrors(HttpServletRequest request, String errorKey){
		ActionErrors errors = new ActionErrors();
        ActionError error = new ActionError(errorKey);
        errors.add(ActionErrors.GLOBAL_ERROR, error);
        saveErrors(request, errors);
	}
	private boolean checkAccess(HttpServletRequest request, String taskname, String priKey){	    
		HashMap map=new HashMap();
		map.put("create", new Integer(AimsSecurityManager.INSERT));
		map.put("edit", new Integer(AimsSecurityManager.UPDATE));
		
		try {
			Integer accessKey=(Integer)map.get(taskname);
			if (AimsSecurityManager.checkAccess(request, priKey, accessKey.intValue()))
                return true;
            else
                return false;
			
		}catch (Exception e){
			return false;
		}		
    }
	private void sendMaidToVzappZonDevelopers(FirmwareForm form, String createdBy)throws Exception{
		String phoneModel=AimsDevicesManager.getDevice(form.getPhoneModel()).getDeviceModel();
		AimsDeviceFirmware firmware=FirmwareManager.getFirmwareById(form.getFirmwareId());		
		List list=FirmwareManager.getVzAppZoneDevelopersForEmail(firmware.getFirmwareId());
		Date date=new Date();
		AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZAPPZONE_FIRMWARE);
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] userInfo = (Object[]) list.get(i);
				if (aimsEvent != null) {
					AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, date);
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DEVICE_NAME, phoneModel);
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FIRMWARE_NAME, firmware.getFirmwareName());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MAINTENANCE_RELEASE_NUMBER, firmware.getMrNumber());
					aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ADHOC_EMAILS, userInfo[1]);
					aimsEvent.raiseEvent(aimsEventObject);

					AimsVzappFirmwareEmailLog emailLog = new AimsVzappFirmwareEmailLog();
					emailLog.setFirmwareId(firmware.getFirmwareId());
					emailLog.setUserId((Long) userInfo[0]);
					emailLog.setCreatedDate(date);
					emailLog.setCreatedBy(createdBy);
					FirmwareManager.saveEmailLog(emailLog);
				}
			}
		}
	}
}
