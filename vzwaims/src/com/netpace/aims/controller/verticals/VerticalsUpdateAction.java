package com.netpace.aims.controller.verticals;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.VZWAllianceManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.jma.JMAAllianceCompleteRegistrationForm;
import com.netpace.aims.controller.contracts.ContractsSetupAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsIndustryFocu;
import com.netpace.aims.util.AimsConstants;

/**
 * @struts.action path="/verticalsUpdate"
 *                name="VerticalsEmailAddressForm" 	
 *                scope="request"
 *                input="/verticals/verticalsEmailAddress.jsp"
 *                validate="true"
 * @struts.action-forward name="view" path="/verticals/verticalsEmailAddress.jsp"
 * 
 */

public class VerticalsUpdateAction extends BaseAction {
	
	static Logger log = Logger.getLogger(VerticalsSetupAction.class.getName());
	
	 
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
		Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
		Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
		String taskname = request.getParameter("task");
		Long loginContentId = (Long)session.getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);
		
		VerticalsEmailAddressForm verticalsEmailForm=(VerticalsEmailAddressForm) form;
		try{
			if(currUserType.equals(AimsConstants.VZW_USERTYPE)){
				if(taskname.equals("edit")){
					/*for(int i=0;i<verticalsEmailForm.getIndustryId().length;i++){
						for(Iterator iter = verticalsEmailForm.getAllIndustryVerticals().iterator() ; iter.hasNext();){{
							AimsIndustryFocu indFoc=(AimsIndustryFocu)iter.next();
							if(indFoc.getIndustryId().equals(verticalsEmailForm.getIndustryId()[i])){
								indFoc.setEmailAddress(verticalsEmailForm.getEmailAddress()[i]);
							}
						}
					}
				}*/
					VZWAllianceManager.updateVerticalEmailAddress(verticalsEmailForm.getAllIndustryVerticals());
			  }
			}
			//Not a VZW_USERTYPE
			else{
				throw new AimsSecurityException();
			}
		}
		catch(HibernateException hx)
		{
			log.error("VerticalsSetupAction: exception occure while saving verticals email address",hx);
			ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("error.generic.database");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return mapping.findForward("view");
		}
		
		log.debug("VerticalsSetupAction: vertical email addresses save successfully.");
		 ActionMessages messages = new ActionMessages();
         ActionMessage message = new ActionMessage("message.verticalEmailAddress.update.success");
         messages.add(ActionMessages.GLOBAL_MESSAGE, message);
         saveMessages(request, messages);
         
		return mapping.findForward("view");
	}

}
