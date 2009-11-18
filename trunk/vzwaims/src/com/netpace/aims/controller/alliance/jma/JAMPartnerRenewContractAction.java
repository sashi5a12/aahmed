package com.netpace.aims.controller.alliance.jma;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * @struts.action path="/renewJmaContract"
 * 				  name="JAMPartnerRenewContractForm"	
 * 				  scope="request"
 * 				  validate="false"
 * @struts.action-forward name="success" path="/alliance/jma/jmaNoActiveAcceptedContractPopup.jsp"
 * @author aqureshi
 *
 */
public class JAMPartnerRenewContractAction extends BaseAction {
	
	public static Logger log=Logger.getLogger(JAMPartnerRenewContractAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		log.debug("JAMPartnerRenewContractAction : start");
		JAMPartnerRenewContractForm contractRenewForm=(JAMPartnerRenewContractForm) form;
		AimsAppLite appLite = null;
		//String appId=request.getParameter("appId");
		HttpSession session = request.getSession();
		String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
	       
		try{
		    if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)){
		  	
				if(contractRenewForm.getSendEmail()!=null){
					if(contractRenewForm.getSendEmail().equalsIgnoreCase("Y")){
						log.debug("Sending notification");
						//send notification
						AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_JMA_PARTNER_REQ_RENEW_CONTRACT);
						
						appLite = ApplicationsManagerHelper.getAimsAppLite(new Long(contractRenewForm.getAppId()));
						if (aimsEvent != null && appLite!=null) {
							
							//AimsAllianc alliance = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, StringFuncs.NullValueReplacement((allianceId)));
							
							AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
							aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
							aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, "");
							aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, appLite.getTitle());
		
							//must set these properties
							aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, appLite.getAimsAllianceId()+"");
						    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, appLite.getAppsId()+"");
		
							aimsEvent.raiseEvent(aimsEventObject);
						}
						if(appLite==null)
						{
							log.info("JAMPartnerRenewContractAction: appLite ="+appLite);
						}
		             
						 request.setAttribute("closePopup", "true");
					}
					else if(contractRenewForm.getSendEmail().equalsIgnoreCase("N")){
						 request.setAttribute("closePopup", "true");
					}
					
					
				}
		    }
		    //Not a Alliance user
		    else{
		    	 request.setAttribute("closePopup", "true");
		    }
		}catch(Exception ex)
		{
			log.error("JAMPartnerRenewContractAction: exception occure", ex);
		}
		
		//log.debug("JAMPartnerRenewContractAction : End");
		return mapping.findForward("success");

	}
	

}
