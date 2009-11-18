package com.netpace.aims.controller.alliance;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.util.NameValueBean;
import com.netpace.aims.util.AimsConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceRegistrationSetup"  
 *                name="AllianceRegistrationForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="update" path="/alliance/allianceRegistrationUpdate.jsp"
 * @struts.action-forward name="vzdnloginaction" path="/vzdnLoginAction.do"
 * @author Adnan Makda
 */
public class AllianceRegistrationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceRegistrationSetupAction.class.getName());
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	log.debug("------in allianceregistrationsetupaction");
    	AllianceRegistrationForm allianceRegistrationForm = (AllianceRegistrationForm) form;
    	
    	if( request.getSession().getAttribute(AimsConstants.AIMS_USER) != null){
    		log.debug("------in allianceregistrationsetupaction  session is valid");
	    	ActionErrors errors = new ActionErrors();
	        ActionError error = new ActionError("error.registration.already.exists");
	        errors.add(ActionErrors.GLOBAL_ERROR, error);
	        saveErrors(request, errors);
    	}
    	
    	
    	if(request.getAttribute("vzdnCredentials")!=null){
    		log.debug("------in allianceregistrationsetupaction  vzdn credentials are not null");
    		VzdnCredentials vzdnCredentials = (VzdnCredentials) request.getAttribute("vzdnCredentials");
    		allianceRegistrationForm.setVzdnManagerRoles(vzdnCredentials.getVzdnManagerRoles());
    		allianceRegistrationForm.setLoginId(vzdnCredentials.getUserName());
    		allianceRegistrationForm.setFirstName(vzdnCredentials.getFirstName());
    		allianceRegistrationForm.setLastName(vzdnCredentials.getLastName());
    		allianceRegistrationForm.setTitle(vzdnCredentials.getTitle());
    		allianceRegistrationForm.setPhone(vzdnCredentials.getPhoneNumber() );
    		allianceRegistrationForm.setMobile(vzdnCredentials.getMobileNumber());
    		allianceRegistrationForm.setFax(vzdnCredentials.getFaxNumber());
    		
    		
    		String taskname = request.getParameter("task");
            String forward = "view";
            
            if (taskname.equalsIgnoreCase("createForm")){
                forward = "update";
            }
            return mapping.findForward(forward);
    	}else{
    		log.debug("------in allianceregistrationsetupaction  sending back to vzdnloginaction");
    		request.setAttribute("registrationLink","/allianceRegistrationSetup.do");
    		return mapping.findForward("vzdnloginaction");
    	}

    	
    }
}
