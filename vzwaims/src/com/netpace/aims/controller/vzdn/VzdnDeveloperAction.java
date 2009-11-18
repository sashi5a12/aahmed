package com.netpace.aims.controller.vzdn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.bo.roles.AimsSysRolesManager;
import com.netpace.aims.controller.VzdnCredentials;
import com.netpace.aims.controller.login.LoginAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;


/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 * @struts.action path="/vzdnDeveloperAction"
 *                validate="false"
 * @struts.action-forward name="login" path="/login.do"
 * @struts.action-forward name="checkOffer" path="/checkOffer.do"
 */
public class VzdnDeveloperAction extends Action{

	static Logger log = Logger.getLogger(VzdnDeveloperAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

        VzdnCredentials vzdnCredentials = (VzdnCredentials) request.getAttribute("vzdnCredentials");
        String vzdnRequestPath=vzdnCredentials.getRequestURI();
        AimsUser aimsUser = AimsAccountsManager.validateUser(vzdnCredentials.getUserName());

        if(aimsUser!=null){
        	request.setAttribute("aimsUser", aimsUser);
    		request.setAttribute("vzdnCredentials", vzdnCredentials);
        	if(aimsUser.getUserAccountStatus().equals(AimsConstants.ACTIVE)){ //only when user is available in ZOn and is Active
        		this.updateUser(aimsUser, vzdnCredentials);
        		return mapping.findForward("login");
        	}else{
        		return mapping.findForward("checkOffer");
        	}
        }
        
        return null;
    }

	
	private void updateUser(AimsUser user, VzdnCredentials vzdnCredentials) throws Exception{

		Collection sysRoles = AimsSysRolesManager.getAllSysRoles();
    	List roleIdsList = new ArrayList();
    	log.debug("---csvroles in loginaction content: "+vzdnCredentials.getVzdnManagerRoles());
    	log.debug("---sysRoles from db in loginaction content: "+sysRoles.size());
    	Iterator it = sysRoles.iterator();
    	while(it.hasNext()){
    		log.debug("---it.hasnext ");
    		AimsSysRole sysRole = (AimsSysRole)it.next();
    		//log.debug("---toString id : "+sysRole.getVzdnMappingRoleID().toString());
    		if(sysRole.getVzdnMappingRoleID() != null && vzdnCredentials.getVzdnManagerRoles().contains(sysRole.getVzdnMappingRoleID().toString()))
    			roleIdsList.add(sysRole.getRoleId().toString());
    	}
    	if(roleIdsList.size()>0){
    		log.debug("zon related roles found in vzdn user");
    		String [] roleArray = this.getStringArray(roleIdsList); 
    		AimsAccountsManager.UpdateUserRoles(
    				user.getUserId().intValue(),
    				user.getUsername(), 
    				user.getPassword(), 
    				user.getUserAccountStatus(), 
    				user.getAimsContact().getFirstName(),
    				user.getAimsContact().getLastName(), 
    				user.getAimsContact().getEmailAddress(),
    				user.getAimsContact().getTitle(), 
    				user.getAimsContact().getPhone(),
    				user.getAimsContact().getMobile(),
    				user.getAimsContact().getFax(),
    				user.getUsername(),
    				user.getUserType(), 
    				roleArray);
    		log.debug("---roles updated in zon.");
    	}
    
	
}
	
private String [] getStringArray(List vzdnRoles){
    	
    	String [] result = new String [vzdnRoles.size()];
    	for(int i=0; i < vzdnRoles.size(); i++){
    		result[i]=vzdnRoles.get(i).toString();
    	}
    	
    	return result;
    	
    	
    	
    }
	
	
	
}
