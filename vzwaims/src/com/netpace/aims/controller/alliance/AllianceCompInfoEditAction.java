package com.netpace.aims.controller.alliance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.events.PlaceHolderHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.ws.ServiceManager;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceCompInfoEdit"                
 *                scope="request" 
 *				  name="AllianceCompInfoForm"
 *				  validate="true"
 *                input="/alliance/allianceCompanyInfoUpdate1.jsp"
 * @struts.action-forward name="view" path="/alliance/allianceCompanyInfoUpdate1.jsp"
 * @struts.action-forward name="userHome" path="/userHome.do"
 * @author Rizwan Qazi
 */

public class AllianceCompInfoEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceCompInfoEditAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");

        HttpSession session = request.getSession();
        AllianceCompInfoForm allianceCompInfoForm = (AllianceCompInfoForm) form;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        ActionErrors errors = new ActionErrors();

        Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();

        String forward = "userHome";

        log.debug("Task : " + taskname);

        if (StringFuncs.NullValueReplacement(taskname).equalsIgnoreCase("edit"))        
        {

            try
            {

            	// code added by Waseem Akram (26 june 2009). This code sets a flag <<callUpdateDeveloper>> to true
            	// if the alliance(companyname, weburl or streetaddress) is updated 
            	//Start
            	boolean callUpdateDeveloper = false; 
            	AimsAllianc alliance = (AimsAllianc) DBHelper.getInstance().getSession().load(AimsAllianc.class, alliance_id);            	
            	if ( 
            			alliance != null && 
            			(
	                		!allianceCompInfoForm.getCompanyName().equals(alliance.getCompanyName())  ||
	                		!allianceCompInfoForm.getWebSiteUrl().equals(alliance.getWebSiteUrl()) ||
	                		!allianceCompInfoForm.getStreetAddress1().equals(alliance.getStreetAddress1()) ||	                		
	                		!allianceCompInfoForm.getCity().equals(alliance.getCity()) ||
	                		!allianceCompInfoForm.getState().equals(alliance.getState()) ||
	                		!allianceCompInfoForm.getZipCode().equals(alliance.getZipCode()) ||
	                		!allianceCompInfoForm.getCountry().equals(alliance.getCountry())
	                	)	
                	)
            	{	
            		callUpdateDeveloper = true;
            	}	
            	//End
            	
            	
            	
                AllianceCompInfoManager.UpdateAllianceCompInfo(
                    alliance_id,
                    allianceCompInfoForm.getCompanyName(),
                    allianceCompInfoForm.getCompanyLegalName(),
                    allianceCompInfoForm.getStateOfInc(),
                    allianceCompInfoForm.getAuthRepName(),
                    allianceCompInfoForm.getStreetAddress1(),
                    allianceCompInfoForm.getCity(),
                    allianceCompInfoForm.getState(),
                    allianceCompInfoForm.getCountry(),
                    allianceCompInfoForm.getZipCode(),                        
                    allianceCompInfoForm.getWebSiteUrl(),
                    allianceCompInfoForm.getPrevYearRevenues(),
                    allianceCompInfoForm.getDbNumber(),
                    allianceCompInfoForm.getIsFinanceInfoPublic(),
                    allianceCompInfoForm.getReasonsForRelationshipWithVZW(),
                    allianceCompInfoForm.getExistingContractsWithVZW(),
                    allianceCompInfoForm.getAllianceWithOtherCarriers(),
                    allianceCompInfoForm.getCommentsAllianceWithOtherCarriers(),
                    allianceCompInfoForm.getOtherIndustryFocus(),
                    allianceCompInfoForm.getAssignedArrIndustryFocus(),
                    allianceCompInfoForm.getAssignedArrRegions(),
                    allianceCompInfoForm.getAssignedArrPlatforms(),
                    user_name,                    
                    allianceCompInfoForm.getArrAlliancesWithOtherCarriers(),
                    allianceCompInfoForm.getRemitTo(),
                    allianceCompInfoForm.getRemitAddress1(),
                    allianceCompInfoForm.getRemitAddress2(),
                    allianceCompInfoForm.getRemitCity(),
                    allianceCompInfoForm.getRemitCountry(),
                    allianceCompInfoForm.getRemitPostalCode(),
                    allianceCompInfoForm.getRemitState()
                        );

                //Start of Event Related Code
                if (alliance_id != null)
                {
                    AimsAllianc aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, alliance_id.toString());
                    if (aimsAlliance.getStatus().equals(AimsConstants.ALLIANCE_STATUS_SUBMITTED))
                    {
                        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SUBMITTED_ALLIANCE_UPDATED);
                        if (aimsEvent != null)
                        {
                            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

                            //Setting Properties
                            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance_id.toString());
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAlliance.getCompanyName());
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                            if (aimsAlliance.getVendorId() != null)
                                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAlliance.getVendorId().toString());
                            aimsEventObject.setProperty(
                                AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                                PlaceHolderHelper.getAllianceAdminName(alliance_id));
                            aimsEventObject.setProperty(
                                AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE,
                                PlaceHolderHelper.getAllianceAdminPhone(alliance_id));
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_INFO_PAGE, "Company Information");

                            aimsEvent.raiseEvent(aimsEventObject);
                        }
                    }
                } //event related code finished
                
                // following code is added by Waseem Akram. This code is Asynch in nature and its purpose
                // is to update the developer.
                if ( callUpdateDeveloper )	
                	ServiceManager.updateDeveloper(alliance_id);                	
            }
            catch (AimsException ae)
            {

                log.debug("ae.getMessageKey() in Action Class : " + ae.getMessageKey());
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));
                saveErrors(request, errors);
                return mapping.getInputForward();
            }

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.edit.success"));
            saveMessages(request, messages);
            forward = "view";

        }

        return mapping.findForward(forward);
    }
}
