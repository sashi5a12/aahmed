package com.netpace.aims.controller.system;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.system.DisclaimerManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.system.AimsDisclaimers;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * @struts.action path="/disclaimerSetup"
 *                scope="request"
 *	          	  name="DisclaimerForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/system/disclaimerList.jsp"
 * @struts.action-forward name="edit" path="/system/disclaimerViewUpdate.jsp"
 * @struts.action-forward name="view" path="/system/disclaimerViewUpdate.jsp" 
 * @author Adnan Ahmed
 */
public class DisclaimerSetupAction extends BaseAction {
	private static final Logger log = Logger.getLogger(DisclaimerSetupAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.debug("DisclaimerSetupAction.execute Start:");
		
		DisclaimerForm disclaimerForm=(DisclaimerForm)form;
		String taskName=disclaimerForm.getTaskName();
		
		if (StringUtils.isEmpty(taskName)){
			taskName="list";
		}
		
		if (!checkAccess(request, taskName,AimsPrivilegesConstants.MANAGE_DISCLAIMERS)){
			throw new AimsSecurityException();
		}
		
		if("list".equals(taskName)){
			Collection collection=DisclaimerManager.getDisclaimersList();
			request.setAttribute("disclaimerList", collection);
		}
		else if("edit".equals(taskName) || "view".equals(taskName)){			
			AimsDisclaimers disclaimer=DisclaimerManager.getDisclaimerById(disclaimerForm.getDisclaimerId());
			if (disclaimer.getDisclaimerId() == null){
				Collection collection=DisclaimerManager.getDisclaimersList();
				request.setAttribute("disclaimerList", collection);
				taskName="list";
			}
			else {
				disclaimerForm.setDisclaimerName(disclaimer.getDisclaimerName());
				disclaimerForm.setDisclaimerText(disclaimer.getDisclaimerStr());
			}
		}
		log.debug("DisclaimerSetupAction.execute End: taskName="+taskName);
		return mapping.findForward(taskName);
	}
	
	private static boolean checkAccess(HttpServletRequest request, String taskname, String priKey)
    {
	    
		HashMap map=new HashMap();
		map.put("list", new Integer(AimsSecurityManager.SELECT));
		map.put("view", new Integer(AimsSecurityManager.SELECT));
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
}
