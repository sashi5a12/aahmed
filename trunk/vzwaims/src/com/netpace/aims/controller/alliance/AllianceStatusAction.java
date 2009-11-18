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

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.events.PlaceHolderHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsDashboardEmailToAdobe;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceStatus"                
 *                scope="request" 
 *				  name="AllianceForm"
 *				  validate="false"
 *                input="/alliance/allianceStatusView.jsp"  
 * @struts.action-forward name="view" path="/alliance/allianceStatusView.jsp"
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceStatusView.jsp"
 * @struts.action-forward name="delete" path="/allianceStatus.do?task=view" redirect="true"
 * @struts.action-exception key="masters.integrity.constraint.violation" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 * @author Rizwan Qazi
 * @see com.netpace.aims.bo.core.IntegrityConstraintException
 */
public class AllianceStatusAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceStatusAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));
        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        Long alliance_id = null;
        String forward = "";

        Collection AimsAlliance = null;
        Collection AimsAllianceContracts = null;
        Collection AimsAllianceApplications = null;
        Collection AimsAllianceAccountManagers = null;
        Collection AimsAllianceSalesContacts = null;
        AllianceForm allianceForm = (AllianceForm) form;
        Object[] userValues = null;

        if (user_type.equalsIgnoreCase("A"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_STATUS, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }
            alliance_id = user.getAimsAllianc();
            forward = "view";
        }

        if (user_type.equalsIgnoreCase("V"))
        {

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT)))
            {
                throw new AimsSecurityException();
            }

            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
            String alliance_status = StringFuncs.NullValueReplacement(request.getParameter("vzwStatus"));
            if (taskname.equalsIgnoreCase("changeStatus"))
            {

                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE)))
                {
                    throw new AimsSecurityException();
                }
                AllianceManager.changeAllianceStatus(alliance_id, alliance_status, user_name);

                //Start of Event Related Code
                if (alliance_status.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED))
                {
                    AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ALLIANCE_ACCEPTED);

                    if (aimsEvent != null)
                    {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        AimsAllianc aimsAllianceOfApplication =
                            (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, alliance_id.toString());

                        //Setting Properties
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance_id.toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        if (aimsAllianceOfApplication.getVendorId() != null)
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianceOfApplication.getVendorId().toString());
                        aimsEventObject.setProperty(
                            AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                            PlaceHolderHelper.getAllianceAdminName(alliance_id));

                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }
            }
            if (alliance_status.equals(AimsConstants.ALLIANCE_STATUS_REJECTED))
            {
            	try
    			{
            		AimsAllianc aimsAlliance= (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, alliance_id.toString());
    				log.debug("Sending notification for JMA Alliance Rejected");
    				if(StringFuncs.NullValueReplacement(aimsAlliance.getIsJMAAlliance()).equals("Y")){
    				AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.JMA_ALLIANCE_REJECTED);
    				
	    				if (aimsEvent != null) {
	    					AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
	    					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
	    					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMPANY_NAME, aimsAlliance.getCompanyName());	
	    					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ID, aimsAlliance.getAllianceId().toString());
	    					aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsAlliance.getAllianceId().toString());
	    					aimsEvent.raiseEvent(aimsEventObject);
	    				}
    				}
    			}
    			catch(Exception ex)
    			{
    				log.error("Exception occure while sending notification for JMA Alliance Rejected",ex);
    			}
            	
            }
            if (taskname.equalsIgnoreCase("editSCandAM"))
            {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE)))
                {
                    throw new AimsSecurityException();
                }
                AllianceManager.changeSalesContactAccountMgr(alliance_id, new Long(allianceForm.getSelAccManager()), user_name);
            }
            if (taskname.equalsIgnoreCase("sendMailToAdobe"))
            {
            	AimsAllianc aimsAllianceOfApplication =(AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, alliance_id.toString());
            	if (aimsAllianceOfApplication.getStatus() != null && aimsAllianceOfApplication.getStatus().equals("A")){
            		AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_DASHBOARD_APP_EMAIL_TO_ADOBE);
            	            	
	                if (aimsEvent != null)
	                {
	                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();                    
	            		AimsUser aimAllianceAdminUser =(AimsUser) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsUser.class, aimsAllianceOfApplication.getAimsUserByAdminUserId().toString());                    
	            		AimsContact aimAllianceAdminContact = (AimsContact) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());
	                    	        		    
	                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAllianceOfApplication.getVendorId().toString());
	        		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_USER_ID, aimAllianceAdminContact.getEmailAddress());
	        		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME, aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());
	        		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE, aimAllianceAdminContact.getPhone());
	        		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_EMAIL, aimAllianceAdminContact.getEmailAddress());
	        		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
	        		    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CREATE_ACCOUNT, "CREATE ACCOUNT");        		    
	
	                    aimsEvent.raiseEvent(aimsEventObject);
	                    
	                	AimsDashboardEmailToAdobe adobeMailLog=new AimsDashboardEmailToAdobe();
	                	adobeMailLog.setAllianceId(alliance_id);
	                	adobeMailLog.setCreatedBy(user_name);
	                	adobeMailLog.setCreatedDate(new Date());
	                	DashboardApplicationManager.saveAdobeMailLog(adobeMailLog);	                    
	                }
            	}
            }
            forward = "vzwView";
        }

        AimsAlliance = AllianceManager.getAllianceDetails(alliance_id, user_type);

        for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
        {
            userValues = (Object[]) iter.next();

            allianceForm.setAllianceId(alliance_id);
            allianceForm.setCompanyName((String) userValues[0]);
            allianceForm.setCompanyLegalName((String) userValues[1]);
            allianceForm.setDateEstablished((Date) userValues[2]);
            allianceForm.setStatus(allianceForm.getAllianceStatus((String) userValues[3]));

            if (((String) userValues[3]).equalsIgnoreCase("S") || ((String) userValues[3]).equalsIgnoreCase("R"))
            {
                allianceForm.setShowAcceptRejectButton("Y");
            }
            else
            {
                allianceForm.setShowAcceptRejectButton("N");
            }
            allianceForm.setVzwAccMgrFirstName((String) userValues[4]);
            allianceForm.setVzwAccMgrLastName((String) userValues[5]);
            allianceForm.setVzwAccMgrEmailAddress((String) userValues[6]);
            allianceForm.setVzwAccMgrPhone((String) userValues[7]);
            allianceForm.setSalesContactFirstName((String) userValues[8]);
            allianceForm.setSalesContactLastName((String) userValues[9]);
            allianceForm.setSalesContactEmailAddress((String) userValues[10]);
            allianceForm.setSalesContactPhone((String) userValues[11]);
            allianceForm.setSelSalesContact(StringFuncs.initializeLongGetParam((Long) userValues[12], "0"));
            allianceForm.setSelAccManager(StringFuncs.initializeLongGetParam((Long) userValues[13], "0"));
            allianceForm.setAllianceAdminFirstName((String) userValues[14]);
            allianceForm.setAllianceAdminLastName((String) userValues[15]);
            allianceForm.setAllianceAdminEmailAddress((String) userValues[16]);
            allianceForm.setAllianceAdminPhone((String) userValues[17]);

        }

        AimsAllianceContracts = AllianceManager.getAllianceAcceptedContracts(alliance_id, user_type);
        AimsAllianceApplications = AllianceManager.getAllianceApplications(alliance_id, user_type);
        AimsAllianceAccountManagers = AllianceManager.getAllianceAccountManagers(alliance_id, user_type);
        AimsAllianceSalesContacts = AllianceManager.getAllianceSalesContacts(alliance_id, user_type);

        //log.debug("**** No of AimsAllianceAccountManagers records in statusAction returned **** : " + AimsAllianceAccountManagers.size() ); 

        allianceForm.setContracts(AimsAllianceContracts);
        allianceForm.setApplications(AimsAllianceApplications);
        allianceForm.setAccountManagers(AimsAllianceAccountManagers);
        allianceForm.setSalesContacts(AimsAllianceSalesContacts);

        return mapping.findForward(forward);
    }
}
