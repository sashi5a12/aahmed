package com.netpace.aims.controller.system;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.system.DisclaimerManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.system.AimsDisclaimers;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * @struts.action path="/disclaimerUpdate"
 *                scope="request"
 *	          	  name="DisclaimerForm"
 *                validate="true"
 *                input="/system/disclaimerViewUpdate.jsp"
 * @struts.action-forward name="list" path="/disclaimerSetup.do" redirect="true"
 * @author Adnan Ahmed
 */
public class DisclaimerUpdateAction extends BaseAction {
	private static final Logger log = Logger.getLogger(DisclaimerUpdateAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.debug("DisclaimerUpdate.execute Start:");
		
		if(!checkAccess(request, AimsPrivilegesConstants.MANAGE_DISCLAIMERS, AimsSecurityManager.UPDATE)){
			throw new AimsSecurityException();
		}
		
		DisclaimerForm disclaimerForm=(DisclaimerForm)form;
		String forward="list";
		if (disclaimerForm.getDisclaimerId().longValue() > 0){
			HttpSession session=request.getSession();
			String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();		
			AimsDisclaimers disclaimer=new AimsDisclaimers();
			disclaimer.setDisclaimerId(disclaimerForm.getDisclaimerId());
			disclaimer.setDisclaimerName(disclaimerForm.getDisclaimerName().trim());
			disclaimer.setModifiedBy(currUser);
			disclaimer.setModifiedDate(new Date());
			DisclaimerManager.updateDisclaimer(disclaimer, disclaimerForm.getDisclaimerText().trim());
		}
		log.debug("DisclaimerUpdate.execute End:");
		return mapping.findForward(forward);
	}
}
