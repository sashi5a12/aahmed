package com.netpace.aims.controller.alliance.jma;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.BaseActionForm;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.controller.alliance.jma.JMAAllianceRegistrationForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.action path="/jmaAllianceRegistrationSetup"
 * 				  name="JMAAllianceRegistrationForm"
 *                scope="request"
 *                validate="false"
 * @struts.action-forward name="success" path="/alliance/jma/jmaAllianceRegistrationUpdate.jsp"                 	
 * @struts.action-forward name="home" path="/userHome.do"            	
 * @struts.action-forward name="vzdnloginaction" path="/vzdnLoginAction.do"
 * @author aqureshi
 *
 */

public class JMAAllianceRegistrationSetupAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		JMAAllianceRegistrationForm jmaForm = (JMAAllianceRegistrationForm)form;
		
		if( request.getSession().getAttribute(AimsConstants.AIMS_USER) != null){
        	ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.alliance_registration.user_name.exists.zon");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return (mapping.findForward("home"));
		}
		
		if(request.getAttribute("vzdnCredentials")!=null ){
    		VzdnCredentials vzdnCredentials = (VzdnCredentials) request.getAttribute("vzdnCredentials");
    		jmaForm.setUserRoles(vzdnCredentials.getVzdnManagerRoles());
    		jmaForm.setUserType(vzdnCredentials.getVzdnUserType());
    		jmaForm.setEmail(vzdnCredentials.getUserName());
    		jmaForm.setFirstName(vzdnCredentials.getFirstName());
    		jmaForm.setLastName(vzdnCredentials.getLastName());
    		jmaForm.setTitle(vzdnCredentials.getTitle());
    		jmaForm.setPhone(vzdnCredentials.getPhoneNumber());
    		jmaForm.setFax(vzdnCredentials.getFaxNumber());
    		jmaForm.setMobile(vzdnCredentials.getMobileNumber());
    		return mapping.findForward("success");
    	}else{
    			request.setAttribute("registrationLink","/jmaAllianceRegistrationSetup.do");
    			return mapping.findForward("vzdnloginaction");
    	}
	}
	
	

}
