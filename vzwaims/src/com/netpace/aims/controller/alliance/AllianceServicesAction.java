package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.ObjectNotFoundException;

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
import com.netpace.aims.ws.ThirdpartyServiceException;
import com.netpace.aims.ws.vds.AllianceServices;
import com.netpace.aims.ws.vds.Developer;

/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/allianceServices"
 * @struts.action-forward name="allianceServicesPage" path="/alliance/allianceService.jsp" 
 * @author Waseem Akram
 */
public class AllianceServicesAction extends BaseAction {
	private static Logger log = Logger.getLogger(AllianceServicesAction.class.getName());
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if (log.isInfoEnabled())
        	log.info("AllianceServicesAction.execute : START");
		
        ActionErrors errors = new ActionErrors();
        
        ActionMessages messages = new ActionMessages();
        
        
        String method = request.getParameter("method");
        String allianceId = request.getParameter("allianceId");
        
        if ( StringUtils.isEmpty(method) || StringUtils.isEmpty(allianceId) )
        {
        	addActionError(request, errors, new ActionError("error.java.app.malformed.allianceaction.url"));
            return mapping.findForward("allianceServicesPage");
        }        
        
        
        HttpSession session = request.getSession();
        //Long currUserId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();            
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        
        if ( !currUserType.equals(AimsConstants.VZW_USERTYPE) )
        {
        	//You are not authorized to access this
        	addActionError(request, errors, new ActionError("error.java.app.allianceaction.unauthorized.action"));
            return mapping.findForward("allianceServicesPage");
        }
        	
        
        			
        	//AimsAllianc alliance = null;
			try 
			{
				AllianceServices allianceService = new AllianceServices();
				Developer developer =  allianceService.getDeveloperForAllianceId(new Long(allianceId));
	
				if ( method.equalsIgnoreCase("create") )
					allianceService.createDeveloper(developer);
				else if ( method.equalsIgnoreCase("update") )
					allianceService.updateDeveloper(developer);
				else if ( method.equalsIgnoreCase("delete") )	
					allianceService. deleteDeveloper(developer);
				else
				{
					addActionError(request, errors, new ActionError("error.java.app.malformed.allianceaction.url"));
		            return mapping.findForward("allianceServicesPage");
				}
				
			} 
			catch (ObjectNotFoundException objectNotFoundException) 
			{
				log.debug("AllianceServicesAction.execute : developer not found");
				objectNotFoundException.printStackTrace();

				addActionError(request, errors, new ActionError("error.java.app.malformed.allianceaction.zerorecord",allianceId));
	            return mapping.findForward("allianceServicesPage");

			} 
			catch (HibernateException e) 
			{
				log.debug("AllianceServicesAction.execute : Error in execution");
				e.printStackTrace();
				
				addActionError(request, errors, new ActionError("error.java.app.malformed.allianceaction.systemerror"));
	            return mapping.findForward("allianceServicesPage");
			}	
			catch (ThirdpartyServiceException e) 
			{
				log.debug("AllianceServicesAction.execute : Error in execution");
				e.printStackTrace();
				
				addActionError(request, errors, new ActionError("error.java.app.malformed.allianceaction.systemerror"));
	            return mapping.findForward("allianceServicesPage");
			}
    	
        if (log.isInfoEnabled()) 
        	log.info("AllianceServicesAction.execute : END");	
        
        
        // method called 
        addActionMessage(request, messages, new ActionMessage("error.java.app.malformed.allianceaction.success"));        
        return mapping.findForward("allianceServicesPage");
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
