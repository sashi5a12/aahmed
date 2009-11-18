package com.netpace.aims.controller.wapoptout;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.wapoptout.WapOptoutManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.ftp.wapoptout.FtpExcelFile;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.wapoptout.AimsWapOptout;
import com.netpace.aims.model.wapoptout.AimsWapOptoutUrls;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.WapOptOutComparator;

/**
 * @struts.action path="/wapOptout"
 *                name="WapOptoutForm"
 *                scope="request"
 *                input="/wapoptout/wapOptoutRequest.jsp"
 *        		  validate="true"
 * @struts.action-forward name="request" path="/wapoptout/wapOptoutRequest.jsp"
 * @struts.action-forward name="confirm" path="/wapoptout/wapOptoutConfirm.jsp"
 * @struts.action-forward name="print" path="/wapoptout/wapOptoutPrint.jsp"
 * @struts.action-forward name="printPopup" path="/wapoptout/wapOptoutPrintPopup.jsp"
 * @author Adnan Ahmed.
 */

public class WapOptoutAction extends BaseAction{
	
	private static final Logger log = Logger.getLogger(WapOptoutAction.class.getName());

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		WapOptoutForm optoutForm=(WapOptoutForm)form;
		String forward="request";
		
		if ("submit".equalsIgnoreCase(optoutForm.getTask())){
			super.saveToken(request);
			forward="confirm";
		}
		else if("confirm".equalsIgnoreCase(optoutForm.getTask())){
			if(!super.isTokenValid(request, true)){
				optoutForm=new WapOptoutForm();
				optoutForm.reset(mapping, request);
				request.setAttribute("WapOptoutForm", optoutForm);				
				return mapping.findForward("request");
			}
			AimsWapOptout optout=new AimsWapOptout();
			AimsUser user =null;
			if (request.getSession().getAttribute(AimsConstants.AIMS_USER) != null){
				user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
			}
			
			//Optout Page - Section
			optout.setCompanyName(optoutForm.getCompanyName().trim());
			optout.setReqFirstName(optoutForm.getReqFirstName().trim());
			optout.setReqLastName(optoutForm.getReqLastName().trim());
			optout.setStreetAddress(optoutForm.getStreetAddress().trim());
			optout.setReqEmailAddress(optoutForm.getReqEmailAddress().trim());
			optout.setCity(optoutForm.getCity().trim());
			optout.setState(optoutForm.getState().trim());
			optout.setZipCode(optoutForm.getZipCode().trim());
			optout.setCountry(optoutForm.getCountry().trim());
			optout.setReqPhoneNumber(optoutForm.getReqPhoneNumber().trim());
			
			//Bypass URLs - Section
			optout.setAimsWapOptoutUrlses(new TreeSet(new WapOptOutComparator()));			
			String[] bypassUrls=optoutForm.getBypassUrls();
			for(int i=0;i<bypassUrls.length; i++){
				AimsWapOptoutUrls bypassUrl=new AimsWapOptoutUrls();
				bypassUrl.setBypassUrl(bypassUrls[i].trim());
				bypassUrl.setAimsWapOptout(optout);
				optout.getAimsWapOptoutUrlses().add(bypassUrl);				
			}

			//Administrative Contact - Section
			optout.setAdminCompanyName(optoutForm.getAdminCompanyName().trim());
			optout.setAdminFirstName(optoutForm.getAdminFirstName().trim());
			optout.setAdminLastName(optoutForm.getAdminLastName().trim());
			optout.setAdminPhoneNumber(optoutForm.getAdminPhoneNumber().trim());
			optout.setAdminEmailAddress(optoutForm.getAdminEmailAddress().trim());
			
			Timestamp dateTime=new Timestamp(System.currentTimeMillis());
			optout.setCreatedDate(dateTime);
			optout.setIsDirty("Y");
			if (user != null){
				optout.setCreatedBy(user.getAimsContact().getEmailAddress());
			}
			else {
				optout.setCreatedBy("system");
			}
			
			WapOptoutManager.save(optout);
			optoutForm.setSubmittalNumber(optout.getSubmittalNumber());
			optoutForm.setSubmitDate(Utility.convertToString(dateTime, "MM/dd/yy 'at' hh:mm a z"));
			
            //Requester Notification
            AimsEventLite aimsEvent = null;
            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZW_OPTIMIZED_WAPOPTOUT_EVENT_FOR_REQUESTER);
            if (aimsEvent != null)
            {
                AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();                
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(dateTime)));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMPANY_NAME, optout.getCompanyName());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SUBMITTAL_NUMBER, optout.getSubmittalNumber().toString());
                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ADHOC_EMAILS, optout.getReqEmailAddress());
                aimsEvent.raiseEvent(aimsEventObject);
            }
			
            aimsEvent = null;
            aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VZW_OPTIMIZED_WAPOPTOUT_EVENT_FOR_VERIZON);
            if (aimsEvent != null)
            {
                AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(dateTime)));
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMPANY_NAME, optout.getCompanyName());
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_SUBMITTAL_NUMBER, optout.getSubmittalNumber());
                aimsEvent.raiseEvent(aimsEventObject);
            }            
			
			
			forward="print";
		}
		else if ("printPopup".equalsIgnoreCase(optoutForm.getTask())){
			forward="printPopup";
		}		
		return mapping.findForward(forward);
	}
		
}
