package com.netpace.aims.controller.system;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.core.IntegrityConstraintException;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.system.FirmwareManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsDeviceFirmware;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 *
 * @struts.action path="/firmwareSetup"
 *                name="FirmwareForm"
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="list" path="/system/firmwareList.jsp"
 * @struts.action-forward name="create" path="/system/firmwareCreateUpdate.jsp"
 * @struts.action-forward name="edit" path="/system/firmwareCreateUpdate.jsp"
 */
public class FirmwareSetupAction extends BaseAction {
	private static final Logger log = Logger.getLogger(FirmwareSetupAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, 
								 ActionForm form, 
								 HttpServletRequest request, 
								 HttpServletResponse response) throws Exception{
		
		log.debug("FirmwareSetupAction.execute Start:");
		
		FirmwareForm firmwareForm=(FirmwareForm)form;
		String taskName=firmwareForm.getTask();		
        
        if (StringUtils.isEmpty(taskName)){
			taskName="list";
		}
        log.debug("taskName="+taskName);

        if (!checkAccess(request, taskName,AimsPrivilegesConstants.MANAGE_FIRMWARE)){
			throw new AimsSecurityException();
		}
        
        super.saveToken(request);
		if ("list".equals(taskName)){			
			taskName = listPage(request);
		}
		else if ("delete".equals(taskName)){
			try {
				log.debug("firwareId="+firmwareForm.getFirmwareId());
				
				if(firmwareForm.getFirmwareId() != null && firmwareForm.getFirmwareId().longValue() >0){
					FirmwareManager.deleteFrimware(firmwareForm.getFirmwareId());
					ActionMessages messages = new ActionMessages();
			        ActionMessage message = new ActionMessage("message.delete.success");
			        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			        saveMessages(request, messages);
				}
				
			} 
			catch (IntegrityConstraintException e) {
				ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(e.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
			}
			taskName = listPage(request);
		}
		else if ("create".equals(taskName)){
			setPhoneModels(request);
		}
		else if ("edit".equals(taskName)){
			AimsDeviceFirmware firmware=FirmwareManager.getFirmwareById(firmwareForm.getFirmwareId());
			firmwareForm.setPhoneModel(firmware.getDeviceId());
			firmwareForm.setFirmwareName(firmware.getFirmwareName());
			firmwareForm.setMrNumber(firmware.getMrNumber());
			firmwareForm.setStatus(firmware.getStatus());
			firmwareForm.setDescription(firmware.getDescription());
			setPhoneModels(request);
		}
				
		log.debug("FirmwareSetupAction.execute End:");
		
		return mapping.findForward(taskName);
	}

	private void setPhoneModels(HttpServletRequest request)
			throws HibernateException {
		Collection  devicesList=AimsDevicesManager.getDevices(AimsConstants.VZAPPZONE_PLATFORM_ID, true);
		request.setAttribute("devicesList", devicesList);
	}

	private String listPage(HttpServletRequest request) throws HibernateException {
		String taskName;
		HttpSession session = request.getSession();
		AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
		int pageNo = 1;
		pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

		int rows = FirmwareManager.getFirmwaresCount();
		int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

		if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH))){
		    pageNo = 1;
		    pmax = 1;
		}
		request.setAttribute("FirmwaresList", FirmwareManager.getFirmwaresList(PAGE_LENGTH, pageNo));
		request.setAttribute("page_id", new Integer(pageNo));
		request.setAttribute("page_max", new Integer(pmax));			
		taskName="list";
		return taskName;
	}
	
	private static boolean checkAccess(HttpServletRequest request, String taskname, String priKey)
    {
	    
		HashMap map=new HashMap();
		map.put("list", new Integer(AimsSecurityManager.SELECT));
		map.put("create", new Integer(AimsSecurityManager.INSERT));
		map.put("edit", new Integer(AimsSecurityManager.UPDATE));
		map.put("delete", new Integer(AimsSecurityManager.DELETE));
		
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
}
