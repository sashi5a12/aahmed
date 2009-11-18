package com.netpace.aims.controller.alliance;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.ws.ServiceManager;

/**
 * This class takes care of action for display of Change Alliance Administrator
 * Form.
 * 
 * @struts.action path="/changeAllianceAdminUpdate" 
 *                name="ChangeAllianceAdminForm"
 *                scope="request" 
 *                input="/alliance/changeAllianceAdmin.jsp"
 *                validate="true"
 * @struts.action-forward name="view" path="/changeAllianceAdmin.do" 
 * @author Adnan Makda
 */
public class ChangeAllianceAdminUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(ChangeAllianceAdminUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws AimsSecurityException, Exception
    {

        // Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.CHANGE_ALLIANCE_ADMIN);

        HttpSession session = request.getSession();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currentUserType = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getUserType();

        String forward = "view";

        ChangeAllianceAdminForm changeAllianceAdminForm = (ChangeAllianceAdminForm) form;

        
        // code added by Waseem Akram (26 june 2009). This code sets a flag <<callUpdateDeveloper>> to true
    	// if the alliance(aimsUserByAdminUserId) is updated 
    	//Start
    	boolean callUpdateDeveloper = false; 
    	AimsAllianc alliance = (AimsAllianc) DBHelper.getInstance().getSession().load(AimsAllianc.class, currentUserAllianceId);            	
    	if ( 
    			alliance != null && 
    			!changeAllianceAdminForm.getAdminUserId().equals(new Long(0)) && 
    			!changeAllianceAdminForm.getAdminUserId().equals(alliance.getAimsUserByAdminUserId()) 
    		)
    	{	
    		callUpdateDeveloper = true;
    	}	
    	//End
        
        
        AllianceManager.changeAllianceAdministrator(currentUserAllianceId, changeAllianceAdminForm.getAdminUserId(),
                currUser);

        //Send Notification
        AimsEventLite aimsEvent = null;

        aimsEvent = EventManagerFactory.getInstance().getEvent(
                AimsNotificationConstants.EVENT_ALLIANCE_ADMINISTRATOR_CHANGED);

        if (aimsEvent != null)
        {
            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, currentUserAllianceId
                    .toString());

            Collection AimsAlliance = AllianceManager.getAllianceDetails(currentUserAllianceId, currentUserType);
            Object[] userValues = null;

            for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date())
                        .toString());

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME,
                        (String) userValues[0]);

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        (String) userValues[14] + " " + (String) userValues[15]);

                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE,
                        (String) userValues[17]);
                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_EMAIL,
                        (String) userValues[16]);
            }
            /*
             AimsAllianc aimsAllianceForEvent = (AimsAllianc) DBHelper.getInstance().load(
             com.netpace.aims.model.core.AimsAllianc.class, currentUserAllianceId.toString());

             AimsUser aimAllianceAdminUser = (AimsUser) DBHelper.getInstance().load(
             com.netpace.aims.model.core.AimsUser.class,
             aimsAllianceForEvent.getAimsUserByAdminUserId().toString());

             AimsContact aimAllianceAdminContact = (AimsContact) DBHelper.getInstance().load(
             com.netpace.aims.model.core.AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());

             aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
             aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent
             .getCompanyName());

             aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
             aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());

             aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE,
             aimAllianceAdminContact.getPhone());
             aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_EMAIL,
             aimAllianceAdminContact.getEmailAddress());

             */

            aimsEvent.raiseEvent(aimsEventObject);
        }

        // following code is added by Waseem Akram. This code is Asynch in nature and its purpose
        // is to update the developer.
        if ( callUpdateDeveloper )	
        	ServiceManager.updateDeveloper(currentUserAllianceId);
        
        ActionMessages messages = new ActionMessages();
        ActionMessage message = null;
        message = new ActionMessage("message.change.alliance.admin.updated");
        messages.add(ActionMessages.GLOBAL_MESSAGE, message);

        saveMessages(request, messages);

        return mapping.findForward(forward);
    }
}
