package com.netpace.aims.controller.alliance;

import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.contracts.ContractOfferManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.contracts.ContractsHelper;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/allianceContracts"                
 *                scope="request" 
 *				  name="AllianceForm"
 *				  validate="true"
 *                input="/alliance/allianceContractsInfoView.jsp"  
 * @struts.action-forward name="view" path="/alliance/allianceContractsInfoView.jsp" 
 * @struts.action-forward name="viewAcceptRejectContract" path="/allianceContractAcceptReject.do"
 * @struts.action-forward name="vzwView" path="/alliance/vzwAllianceContractsInfoView.jsp"
 * @author Rizwan Qazi
 */
public class AllianceContractsAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AllianceContractsAction.class.getName());
  
    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
	 *
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug("==== start AllianceContractsAction, taskname= "+taskname);
		HttpSession session = request.getSession(); 

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = null;
		
		AllianceForm allianceForm = (AllianceForm) form;	
		Collection AimsAlliance = null;
		Collection AimsAllianceContracts = null;
		Object [] userValues = null;
        AllianceContractForm allianceContractForm  = null;
		alliance_id = user.getAimsAllianc();

        String forward = "";

        log.debug("Task : " + taskname);
		
		if (user_type.equalsIgnoreCase("A"))
		{		
			alliance_id = user.getAimsAllianc();

            if (taskname.equalsIgnoreCase("view") || taskname.equalsIgnoreCase("viewAcceptRejectContract"))
            {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CONTRACTS, 
                                                                    AimsSecurityManager.SELECT))) 
                {
                    throw new AimsSecurityException(); 
                }
            }

            if (taskname.equalsIgnoreCase("edit"))
            {
                if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CONTRACTS, 
                                                                    AimsSecurityManager.UPDATE))) 
                {
                    throw new AimsSecurityException(); 
                }
            }

            if (taskname.equalsIgnoreCase("view") || taskname.equalsIgnoreCase("edit"))	{


                if (taskname.equalsIgnoreCase("edit"))	{
                    if(StringFuncs.NullValueReplacement(allianceForm.getSelDocType()).equals("AA")) {
                        log.debug("AllianceContractsAction: Going to accept/reject amendment");
                        log.debug("AllianceContractsAction: amendmentId= "+allianceForm.getSelDocId());
                        log.debug("AllianceContractsAction: contractId= "+allianceForm.getSelContractId());
                        log.debug("AllianceContractsAction: status= "+allianceForm.getSelDocStatus());
                        log.debug("AllianceContractsAction: initial= "+allianceForm.getSelDocInitial());
                        log.debug("AllianceContractsAction: title= "+allianceForm.getSelDocTitle());
                        //alliance user can accept/reject amendments from alliance cotnract screen
                        ContractOfferManager.editContractAmendmentStatus(
                                                        new Long(allianceForm.getSelContractId()),
                                                        alliance_id,
                                                        new Long(allianceForm.getSelDocId()),
                                                        allianceForm.getSelDocType(),
                                                        allianceForm.getSelDocStatus(),
                                                        StringFuncs.trim(allianceForm.getSelDocInitial()),
                                                        StringFuncs.trim(allianceForm.getSelDocTitle()),
                                                        null,
                                                        user_id,
                                                        user.getUsername()
                                                                    );
                        //reset form fields after accept/reject amendment
                        allianceForm.setSelDocStatus(null);
                        allianceForm.setSelDocInitial(null);
                        allianceForm.setSelDocTitle(null);
                        log.debug("AllianceContractsAction: end accept/reject amendment");
                    }
                }//end edit
                   /*
                       AimsContract aimsContract = (AimsContract) DBHelper.getInstance().load(AimsContract.class, allianceForm.getSelContractId().toString());

                       AimsAllianc aimsAllianceForEvent =
                           (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, alliance_id.toString());

                       AimsUser aimAllianceAdminUser =
                           (AimsUser) DBHelper.getInstance().load(
                               com.netpace.aims.model.core.AimsUser.class,
                               aimsAllianceForEvent.getAimsUserByAdminUserId().toString());

                       AimsContact aimAllianceAdminContact =
                           (AimsContact) DBHelper.getInstance().load(
                               com.netpace.aims.model.core.AimsContact.class,
                               aimAllianceAdminUser.getAimsContactId().toString());

                       if (aimsContract.getAimsPlatform().getPlatformId().longValue() == AimsConstants.ENTERPRISE_PLATFORM_ID.longValue() )
                       {
                           AimsEventLite aimsEvent = null;
                           if (allianceForm.getSelDocStatus().equalsIgnoreCase("A"))
                               aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_CONTRACT_ACCEPTED);
                           else if (allianceForm.getSelDocStatus().equalsIgnoreCase("R"))
                               aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_APP_STATUS_REJECTED);

                           if (aimsEvent != null)
                           {
                               AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                               aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance_id.toString());

                               aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                               aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
                               aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_TITLE, aimsContract.getContractTitle());
                               if ( (aimsContract.getVersion() != null) && (!StringFuncs.isEmpty(aimsContract.getVersion())) )
                                   aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_VERSION, aimsContract.getVersion());
                               if ( (aimsContract.getDocumentFileName() != null) && (!StringFuncs.isEmpty(aimsContract.getDocumentFileName())) )
                                   aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_DOCUMENT_NAME, aimsContract.getDocumentFileName());
                               aimsEventObject.setProperty(
                                   AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                                   aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());

                               aimsEvent.raiseEvent(aimsEventObject);
                           }
                       }
                       else {
                           // send notification when contract accepted/rejected
                           if ("C".equals(allianceForm.getSelDocType())){
                               AimsEventLite aimsEvent = null;
                               if (allianceForm.getSelDocStatus().equalsIgnoreCase("A"))
                                   aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_CONTRACT_ACCEPTED);
                               else if (allianceForm.getSelDocStatus().equalsIgnoreCase("R"))
                                   aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_CONTRACT_REJECTED);

                               if (aimsEvent != null) {
                                   AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                                   aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance_id.toString());
                                   aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
                                   aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, new Date().toString());
                                   aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_TITLE, aimsContract.getContractTitle());
                                   aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_VERSION, aimsContract.getVersion());
                                   aimsEvent.raiseEvent(aimsEventObject);
                               }
                           }
                       }
                   }//end edit
                   */
                
                AimsAlliance = AllianceManager.getAllianceDetails(alliance_id, user_type);
        
                for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();) 
                {
                     userValues  = (Object []) iter.next();

                     allianceForm.setAllianceId(alliance_id);
                     allianceForm.setCompanyName( (String) userValues[0]);
                     allianceForm.setCompanyLegalName( (String) userValues[1]);
                     allianceForm.setDateEstablished( (Date) userValues[2]);
                     allianceForm.setStatus( (String) userValues[3]);
                     allianceForm.setVzwAccMgrFirstName( (String) userValues[4]);
                     allianceForm.setVzwAccMgrLastName( (String) userValues[5]);
                     allianceForm.setVzwAccMgrEmailAddress( (String) userValues[6]);
                     allianceForm.setVzwAccMgrPhone( (String) userValues[7]);
                     allianceForm.setSalesContactFirstName( (String) userValues[8]);
                     allianceForm.setSalesContactLastName( (String) userValues[9]);
                     allianceForm.setSalesContactEmailAddress( (String) userValues[10]);
                     allianceForm.setSalesContactPhone( (String) userValues[11]);
                     
                }		
                
                AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);			

                allianceForm.setContracts(AimsAllianceContracts);
               

                String [] arrContractIds = new String[AimsAllianceContracts.size()];

                int cntContract = 0;
                for (Iterator iter = AimsAllianceContracts.iterator(); iter.hasNext();) 
                {
                     allianceContractForm  = (AllianceContractForm) iter.next();
                     arrContractIds[cntContract] = allianceContractForm.getContractId().toString();
                     cntContract++;
                }
                allianceForm.contractIds = arrContractIds;

                //set allFilterCTPlatforms and available click through contracts in request to show click through contracts in filter
                request.setAttribute("allCTPlatforms", AimsConstants.FILTER_PLATFORMS);
                request.setAttribute("availableClickThroughContracts", AllianceContractInfoManager.getAvailableClickThroughContracts(alliance_id, AimsConstants.CONTRACT_STATUS_ACTIVE, allianceForm.getSelectedFilterCTPlatform()));

                request.setAttribute("showClickThroughContracts", new Boolean(true));
                forward = "view";
            }
            else if(taskname.equalsIgnoreCase("viewAcceptRejectContract")) {
                request.setAttribute(ContractsHelper.REQUEST_CONTRACT_REQUESTING_PAGE, ContractsHelper.CONTRACT_REQUESTED_PAGE_ALLIANCE_CONTRACTS);
                forward = "viewAcceptRejectContract";
            }

        } 	

		if (user_type.equalsIgnoreCase("V"))
		{		
			
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, 
                                                                AimsSecurityManager.SELECT))) 
            {
                throw new AimsSecurityException(); 
            }
            
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
            //company_name = AllianceManager.getAllianceCompanyName(alliance_id);



            AimsAlliance = AllianceManager.getAllianceDetails(alliance_id, user_type);
    
            for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();) 
            {
                 userValues  = (Object []) iter.next();

                 allianceForm.setAllianceId(alliance_id);
                 allianceForm.setCompanyName( (String) userValues[0]);
                 allianceForm.setCompanyLegalName( (String) userValues[1]);
                 allianceForm.setDateEstablished( (Date) userValues[2]);
                 allianceForm.setStatus( (String) userValues[3]);
            }	
            

            if (taskname.equalsIgnoreCase("view"))	
            {
               		
                AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);			
                request.setAttribute("AimsAllianceContracts", AimsAllianceContracts);
                request.setAttribute("alliance_id", alliance_id);
                request.setAttribute("alliance_form", allianceForm);
            } 

			forward = "vzwView";
		} 
		setFilterPlatformString(request, allianceForm);
        log.debug("==== end AllianceContractsAction, forward: "+forward);
		return mapping.findForward(forward);
    }

    private void setFilterPlatformString(HttpServletRequest request, AllianceForm filterFrm) {
        StringBuffer value = new StringBuffer();
		HashMap platforms = new HashMap();
		platforms.put(AimsConstants.BREW_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_BREW);
		platforms.put(AimsConstants.SMS_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_SMS);
		platforms.put(AimsConstants.MMS_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_MMS);
		platforms.put(AimsConstants.WAP_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_WAP);
		platforms.put(AimsConstants.ENTERPRISE_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_ENTERPRISE);
		platforms.put(AimsConstants.VCAST_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_VCAST);
		platforms.put(AimsConstants.VZAPPZONE_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_VZ_APP_ZON);
		platforms.put(AimsConstants.DASHBOARD_PLATFORM_ID.toString(), AimsConstants.FILTER_LABEL_DASHBOARD);

        for (int i=0; i<filterFrm.getSelectedFilterCTPlatform().length; i++) {
        	if (filterFrm.getSelectedFilterCTPlatform()[i].equalsIgnoreCase("SHOW_ALL")){
        		value=new StringBuffer("Showing All");
        		break;
        	}
        	else if (i != (filterFrm.getSelectedFilterCTPlatform().length-1)){
        		value.append(platforms.get(filterFrm.getSelectedFilterCTPlatform()[i])).append(", ");
        	}
        	else {
        		value.append(platforms.get(filterFrm.getSelectedFilterCTPlatform()[i]));
        	}
        }
        request.setAttribute("filterCTPlatform", value.toString());
    }
}
