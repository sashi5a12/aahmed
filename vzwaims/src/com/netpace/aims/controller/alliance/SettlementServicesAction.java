package com.netpace.aims.controller.alliance;

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

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.ws.amdocs.SettlementServices;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/settlementServices"
 * @struts.action-forward name="settlementServicesPage" path="/alliance/settlementServices.jsp" 
 * @author Waseem Akram
 */
public class SettlementServicesAction extends BaseAction {
	private static Logger log = Logger.getLogger(AllianceServicesAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) //throws Exception
    {
        if (log.isInfoEnabled())
        	log.info("SettlementServicesAction.execute : START");
		
        ActionErrors errors = new ActionErrors();        
        ActionMessages messages = new ActionMessages();        
        
        String method = request.getParameter("method");
        String contractIdStr = request.getParameter("contractId");
        String allianceIdStr = request.getParameter("allianceId");
        
        String mdataParam = request.getParameter("massagdata");        
        boolean massagdata = false; //false by default         
       
        if ( StringUtils.isEmpty(mdataParam) )
        	mdataParam = "false";
        if ( mdataParam.equalsIgnoreCase("true") )
        	massagdata = true;
        
        if ( StringUtils.isEmpty(method) )
        {
        	addActionError(request, errors, new ActionError("error.java.app.malformed.settlementserviceaction.url"));
        	return mapping.findForward("settlementServicesPage");
        }
        else if ( SettlementServices.METHOD_PARTNER_ONBOARDING_AND_OFFER_CREATION.equals(method) && ( StringUtils.isEmpty(contractIdStr) ) )
        {
        	addActionError(request, errors, new ActionError("error.java.app.malformed.settlementserviceaction.url"));
        	return mapping.findForward("settlementServicesPage");            
        }
        else if ( SettlementServices.METHOD_OFFERCREATION.equals(method) && ( StringUtils.isEmpty(contractIdStr) ) )
        {
        	addActionError(request, errors, new ActionError("error.java.app.malformed.settlementserviceaction.url"));
        	return mapping.findForward("settlementServicesPage");            
        }
        else if ( SettlementServices.METHOD_PARTNER_ONBOARDING.equals(method) && ( StringUtils.isEmpty(allianceIdStr) ) )
        {
        	addActionError(request, errors, new ActionError("error.java.app.malformed.settlementserviceaction.url"));
        	return mapping.findForward("settlementServicesPage");            
        }
        
        Long allianceId = null;
        if ( !StringUtils.isEmpty(allianceIdStr) )
        	allianceId = new Long(allianceIdStr);
        Long contractId = new Long(contractIdStr);
        
        HttpSession session = request.getSession();            
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        
        if ( !currUserType.equals(AimsConstants.VZW_USERTYPE) )
        {
        	//You are not authorized to access this
        	
        	addActionError(request, errors, new ActionError("error.java.app.settlementserviceeaction.unauthorized.action"));        	
            return mapping.findForward("settlementServicesPage");            
        }
        			
        try 
        {
        	SettlementServices settlementServices = new SettlementServices(new Long(allianceId), new Long(contractId));
				if ( method.equalsIgnoreCase(SettlementServices.METHOD_OFFERCREATION) )
					settlementServices.offerCreation(true,massagdata);					
				else if ( method.equalsIgnoreCase(SettlementServices.METHOD_PARTNER_ONBOARDING) )
					settlementServices.partnerOnboarding(true,massagdata);								
				else if ( method.equalsIgnoreCase(SettlementServices.METHOD_PARTNER_ONBOARDING_AND_OFFER_CREATION) )
				{
					if ( allianceId!=null )
					{
						settlementServices.setCombinedCall(true);
						
						settlementServices.partnerOnboarding(true,massagdata);
					}
					settlementServices.offerCreation(true,massagdata);
				}
				else
				{
					addActionError(request, errors, new ActionError("error.java.app.malformed.settlementserviceaction.url"));
					return mapping.findForward("settlementServicesPage");					
				}
		}
        catch (Exception e) 
        {	
        	String expMsg = "";
        	if ( !StringUtils.isEmpty(e.getMessage())  )
        		expMsg = e.getMessage();
        	else if ( e.getCause()!=null && !StringUtils.isEmpty(e.getCause().toString()) )
        		expMsg = e.getCause().toString();
        	        	
        	addActionError(request, errors, new ActionError("error.java.app.malformed.settlementserviceaction.systemerror",expMsg));
        	
			return mapping.findForward("settlementServicesPage");			
		} 
       
			    	
        if (log.isInfoEnabled()) 
        	log.info("SettlementServicesAction.execute : END");
        
        // method called 
        addActionMessage(request, messages, new ActionMessage("error.java.app.malformed.settlementserviceaction.success"));        
        return mapping.findForward("settlementServicesPage");
    }

	private void addActionMessage(HttpServletRequest request, ActionMessages messages, ActionMessage message)
	{
		messages.add(ActionMessages.GLOBAL_MESSAGE, message);
    
        saveMessages(request, messages);        
	}
	
	private void addActionError(HttpServletRequest request, ActionErrors errors, ActionMessage error)
	{
		errors.add(ActionErrors.GLOBAL_ERROR, error);
    	
        saveErrors(request, errors);        
	}
}
