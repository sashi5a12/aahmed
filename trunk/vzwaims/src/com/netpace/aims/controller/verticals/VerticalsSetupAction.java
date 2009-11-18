package com.netpace.aims.controller.verticals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.VZWAllianceManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.jma.JMAAllianceCompleteRegistrationForm;
import com.netpace.aims.controller.contracts.ContractsSetupAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.action path="/verticalsSetup"
 *                name="VerticalsEmailAddressForm" 	
 *                scope="request"
 *                validate="false"
 * @struts.action-forward name="view" path="/verticals/verticalsEmailAddress.jsp"
 */

public class VerticalsSetupAction extends BaseAction {
	
	static Logger log = Logger.getLogger(VerticalsSetupAction.class.getName());
	
	 
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		VerticalsEmailAddressForm verticalsEmailForm=(VerticalsEmailAddressForm) form;
		
		if(currUserType.equals(AimsConstants.VZW_USERTYPE)){
			log.debug("VerticalsSetupAction");
		}
		else{
			throw new AimsSecurityException();
		}
		return mapping.findForward("view");
	}

}
