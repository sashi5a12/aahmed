package com.netpace.aims.controller.contracts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.contracts.AmendmentsManager;
import com.netpace.aims.bo.contracts.ContractOfferManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.system.AimsPlatformManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.alliance.AllianceContractForm;
import com.netpace.aims.controller.alliance.AllianceForm;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 *
 * @struts.action path="/contractOfferEdit"                
 *                scope="request" 
 *				  name="ContractOfferForm"
 *				  validate="true"
 *                input="/contracts/contractsOfferListView.jsp"  
 * @struts.action-forward name="view" path="/alliance/vzwAllianceContractsInfoView.jsp" 
 * @struts.action-forward name="save" path="/alliance/vzwAllianceContractsSaveView.jsp"
 * @struts.action-forward name="allAlliances" path="/vzwAlliance.do?task=view" redirect="true"
 * @author Rizwan Qazi
 */
public class ContractOfferEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(ContractOfferEditAction.class.getName());

    /**
     * This method lets the user View or Delete a user account
     * It calls the Account manager to get a list of applicable users or passes
     * a user_id to delete from the database.
     *
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        log.debug("start ContractOfferEditAction");
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTRACTS);

        String taskname = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (taskname.equalsIgnoreCase("saveEdit") || taskname.equalsIgnoreCase("edit"))
        {
            if (!(AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.UPDATE)))
            {
                throw new AimsSecurityException();
            }
        }

        if (taskname.equalsIgnoreCase("saveCreate") || taskname.equalsIgnoreCase("create"))
        {
            if (!(AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.INSERT)))
            {
                throw new AimsSecurityException();
            }
        }

        if (taskname.equalsIgnoreCase("delete"))
        {
            if (!(AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.DELETE)))
            {
                throw new AimsSecurityException();
            }
        }

        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String user_name = user.getUsername();
        String user_type = user.getUserType();
        Long user_id = user.getUserId();
        ContractOfferForm contractForm = (ContractOfferForm) form;

        Collection AimsAllianceContracts = null;
        Collection AimsContract = null;
        Collection AimsContractAmendments = null;
        Collection AimsPlatforms = null;
        Collection AimsAmendments = null;
        Collection AimsAllianceAvailableAmendments = null;
        Collection AimsAllianceContractAmendmentIds = null;
        AllianceForm allianceForm = new AllianceForm();
        Object[] userValues = null;
        Long alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));

        Collection AimsAlliance = AllianceManager.getAllianceDetails(alliance_id, user_type);

        log.debug("allince_id: "+alliance_id+", task: "+taskname);

        String forward = "allAlliances";//default to all alliances page, if no parameter
        String alliance_status = "";

        if(AimsAlliance!=null && AimsAlliance.size()>0)
        {
            for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();

                allianceForm.setAllianceId(alliance_id);
                allianceForm.setCompanyName((String) userValues[0]);
                allianceForm.setCompanyLegalName((String) userValues[1]);
                allianceForm.setDateEstablished((Date) userValues[2]);
                allianceForm.setStatus((String) userValues[3]);
            }

            alliance_status = StringFuncs.NullValueReplacement((String) userValues[3]);
            if (!(alliance_status.equals("A")))
            {
                throw new AimsSecurityException();
            }
        }

        if (taskname.equalsIgnoreCase("saveEdit"))
        {
            contractForm.setAllianceId(alliance_id.toString());
            contractForm.setSelContractId(StringFuncs.initializeStringGetParam(request.getParameter("contract_id"), "0"));
        }

        if (taskname.equalsIgnoreCase("saveCreate") || taskname.equalsIgnoreCase("saveEdit"))
        {

            try
            {
                Long contract_id = new Long(contractForm.getSelContractId());
                AimsPlatforms = AimsPlatformManager.getPlatforms();
                AimsAmendments = ContractsManager.getAmendments();
                AimsContract = ContractsManager.getContract(contract_id);
                AimsContractAmendments = ContractsManager.getContractAmendmentDetails(contract_id);
                AimsAllianceAvailableAmendments = AmendmentsManager.getAllianceAvailableAmendmentDetails(contract_id);
                AimsAllianceContractAmendmentIds = AmendmentsManager.getAllianceAssignedAmendmentIds(new Long(contractForm.getAllianceId()), contract_id);
            }
            catch (HibernateException he)
            {
                log.debug("An exception occured in Contract Form!");
            }

            for (Iterator iter = AimsContract.iterator(); iter.hasNext();)
            {
                userValues = (Object[]) iter.next();

                contractForm.setContractId(((Long) userValues[0]).toString());
                contractForm.setContractTitle((String) userValues[1]);
                contractForm.setContractVersion((String) userValues[2]);
                contractForm.setContractDocumentFileName((String) userValues[3]);
                contractForm.setContractStatus(AimsUtils.getContractStatus((String) userValues[4]));
                contractForm.setIfContractNegotiable((String) userValues[5]);
                contractForm.setComments((String) userValues[6]);
                contractForm.setContractExpiryDate(Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
                contractForm.setPlatformId(StringFuncs.initializeLongGetParam(((Long) userValues[8]), null));
                contractForm.setPlatformName(StringFuncs.NullValueReplacement((String) userValues[9]));

                contractForm.setContractHtmlDocumentFileName((String) userValues[10]);
                contractForm.setClickThroughContract((String) userValues[11]);                
            }

            contractForm.setContractAmendments(AimsContractAmendments);
            contractForm.setAllianceAvailableAmendments(AimsAllianceAvailableAmendments);

            if (AimsAllianceContractAmendmentIds != null)
            {
                ArrayList amendmentArrayList = new ArrayList();

                for (Iterator iter = AimsAllianceContractAmendmentIds.iterator(); iter.hasNext();)
                {
                    amendmentArrayList.add(((Long) iter.next()).toString());
                }

                String[] amendmentArr = StringFuncs.ConvertListToStringArray(amendmentArrayList);
                contractForm.setAllianceContractAmendmentIds(amendmentArr);
            }

            forward = "save";

        }

        if (taskname.equalsIgnoreCase("create"))
        {

            ContractOfferManager.assignAllianceContracts(
                new Long(contractForm.getContractId()),
                new Long(contractForm.getAllianceId()),
                contractForm.getAllianceContractAmendmentIds(),
                contractForm.getAmendmentTitle(),
                contractForm.getAmendmentVersion(),
                contractForm.getAmendmentStatus(),
                contractForm.getAmendmentExpiryDate(),
                contractForm.getAmendmentDocument(),
                contractForm.getAmendmentComments(),
                user_name);

            AimsContract aimsContract = (AimsContract) DBHelper.getInstance().load(AimsContract.class, contractForm.getContractId().toString());
            AimsAllianc aimsAllianceForEvent =
                (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, contractForm.getAllianceId().toString());
            
            if (aimsContract.getAimsPlatform().getPlatformId().longValue() == AimsConstants.ENTERPRISE_PLATFORM_ID.longValue())
            {
                AimsEventLite aimsEvent = null;
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_CONTRACT_OFFERED);
                if (aimsEvent != null)
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, contractForm.getAllianceId().toString());
                    
                    AimsUser aimAllianceAdminUser =
                        (AimsUser) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsUser.class,
                            aimsAllianceForEvent.getAimsUserByAdminUserId().toString());

                    AimsContact aimAllianceAdminContact =
                        (AimsContact) DBHelper.getInstance().load(
                            com.netpace.aims.model.core.AimsContact.class,
                            aimAllianceAdminUser.getAimsContactId().toString());

                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_TITLE, aimsContract.getContractTitle());
                    if ((aimsContract.getVersion() != null) && (!StringFuncs.isEmpty(aimsContract.getVersion())))
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_VERSION, aimsContract.getVersion());
                    if ((aimsContract.getDocumentFileName() != null) && (!StringFuncs.isEmpty(aimsContract.getDocumentFileName())))
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_DOCUMENT_NAME, aimsContract.getDocumentFileName());
                    aimsEventObject.setProperty(
                        AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                        aimAllianceAdminContact.getFirstName() + " " + aimAllianceAdminContact.getLastName());

                    aimsEvent.raiseEvent(aimsEventObject);
                }
            }
            else { 
	            // send notification when contract offered
	            AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_CONTRACT_OFFERED);
	            if (aimsEvent != null) {
	                AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
	                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, contractForm.getAllianceId().toString());
	                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
	                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, new Date().toString());	                
	                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_TITLE, aimsContract.getContractTitle());
	                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_VERSION, aimsContract.getVersion());
	                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_PLATFORM, AimsUtils.getPlatform(aimsContract.getAimsPlatform().getPlatformId().toString()));
	                aimsEvent.raiseEvent(aimsEventObject);
	            }
            }
            
            AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);
            request.setAttribute("AimsAllianceContracts", AimsAllianceContracts);
            request.setAttribute("alliance_id", alliance_id);
            request.setAttribute("alliance_form", allianceForm);


            //save success message: alliance contract offered
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.ContractOfferForm.offer.success", aimsContract.getContractTitle()));
            saveMessages(request, messages);

            forward = "view";

        }

        if (taskname.equalsIgnoreCase("edit"))
        {

            ContractOfferManager.editAllianceContracts(
                new Long(contractForm.getContractId()),
                new Long(contractForm.getAllianceId()),
                contractForm.getAllianceContractAmendmentIds(),
                contractForm.getAmendmentTitle(),
                contractForm.getAmendmentVersion(),
                contractForm.getAmendmentStatus(),
                contractForm.getAmendmentExpiryDate(),
                contractForm.getAmendmentDocument(),
                contractForm.getAmendmentComments(),
                user_name);

            AimsContract aimsContract = ContractsManager.getAimsContract(new Long(contractForm.getContractId()));

            AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);
            request.setAttribute("AimsAllianceContracts", AimsAllianceContracts);
            request.setAttribute("alliance_id", alliance_id);
            request.setAttribute("alliance_form", allianceForm);

            //save success message: update alliance contract offered
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.ContractOfferForm.edit.success", aimsContract.getContractTitle()));
            saveMessages(request, messages);

            forward = "view";

        }

        if (taskname.equalsIgnoreCase("delete"))
        {
        	AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);
        	Long contractId=new Long(StringFuncs.initializeStringGetParam(request.getParameter("contract_id"), "0"));
            String deletedContractTitle = "";
            AimsJournalEntry aimsJournalEntry=new AimsJournalEntry();
        	for(Iterator itr=AimsAllianceContracts.iterator();itr.hasNext();){
        		AllianceContractForm allianceContractForm=(AllianceContractForm)itr.next();
        		if (allianceContractForm.getContractId().longValue()==contractId.longValue()){
		        	StringBuffer journalText=new StringBuffer();
		        	journalText.append("Contract ")	
		        				.append("\"").append(allianceContractForm.getContractTitle()).append("\"").append(" ")
		        				.append("(Version: ").append(allianceContractForm.getContractVersion()).append(")").append(" ")
		        				.append("(Status: ").append(allianceContractForm.getContractStatus()).append("), ")
		        				.append("Deleted by ").append(user_name);        				        				
		        	aimsJournalEntry.setJournalText(journalText.toString());
                    deletedContractTitle = allianceContractForm.getContractTitle(); //use this variable for success message
                    break;
        		}
        	}
        	
			aimsJournalEntry.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE);
			aimsJournalEntry.setCreatedBy("system");
 	        aimsJournalEntry.setCreatedDate(new Date());         	        
 	        aimsJournalEntry.setAimsAllianceId(new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0")));  	

            ContractOfferManager.deleteAllianceContracts(
                new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0")),
                contractId,
                aimsJournalEntry);

            AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);
            request.setAttribute("AimsAllianceContracts", AimsAllianceContracts);
            request.setAttribute("alliance_id", alliance_id);
            request.setAttribute("alliance_form", allianceForm);

            //save success message: delete alliance contract offered
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.ContractOfferForm.delete.success", deletedContractTitle));
            saveMessages(request, messages);

            forward = "view";

        }
        log.debug("end ContractOfferEditAction, forward: "+forward);
        return mapping.findForward(forward);
    }
}
