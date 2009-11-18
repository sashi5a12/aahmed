package com.netpace.aims.controller.contracts;

import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import com.netpace.aims.ws.ServiceManager;
import com.netpace.aims.ws.vds.AllianceServices;
import com.netpace.aims.ws.vds.Developer;
import com.netpace.aims.dataaccess.valueobjects.AllianceContractInfoVO;
import com.netpace.aims.controller.alliance.AllianceContractAcceptRejectForm;

import java.util.Date;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ContractsHelper {

    private static Logger log = Logger.getLogger(ContractsHelper.class.getName());

    public static final String REQUEST_CONTRACT_REQUESTING_PAGE = "REQUEST_CONTRACT_REQUESTED_PAGE";
    public static final String CONTRACT_REQUESTED_PAGE_ALLIANCE_CLICK_THROUGH = "allianceClickThrougContracts";
    public static final String CONTRACT_REQUESTED_PAGE_ALLIANCE_CONTRACTS = "allianceContracts";

    public static void postAcceptRejectContract(Long allianceId, AimsContract aimsContract,
            String allianceContractStatus, String docType) throws HibernateException, Exception 
    {
    	// Following check confirms that first java contract is being accepted by Alliance
    	
    	//
    	/*
    	 	1- Call ServiceManager.createDeveloper() when Alliance Accepts first JAVA Contract
    	 	
			2- Call  partnerOnboarding() web-service from following user actions
				Accepts first JAVA contract.

			3- Call offerCreation() web-service from following user actions
				Accepts any click through JAVA contract (including first one).
				Accepts any offered JAVA contract.
				Accepts any one-off JAVA contract.

    	 */
    	if ( 
    			allianceContractStatus.equalsIgnoreCase("A")
    			&&	aimsContract.getAimsPlatform().getPlatformId().equals(AimsConstants.JAVA_PLATFORM_ID)
    			&& AllianceManager.getAllianceAcceptedContracts(allianceId, AimsConstants.JAVA_PLATFORM_ID).size()==1
    		) 
    	{
    		if (log.isDebugEnabled())
    		{
    			log.debug("First Java contract accepted by Alliance.");
    			log.debug("AllianceId = " + allianceId);
    			log.debug("ContractId = " + aimsContract.getContractId());
    		}
    		ServiceManager.createDeveloper(allianceId);
    		
    		ServiceManager.partnerOnboardingAndofferCreation(allianceId,aimsContract.getContractId());
    	}    	
    	else if ( 
    			allianceContractStatus.equalsIgnoreCase("A")
    			&&	aimsContract.getAimsPlatform().getPlatformId().equals(AimsConstants.JAVA_PLATFORM_ID)
    			&& AllianceManager.getAllianceAcceptedContracts(allianceId, AimsConstants.JAVA_PLATFORM_ID).size()>1
    		)
    	{
    		ServiceManager.offerCreation(allianceId, aimsContract.getContractId());
    	}		    	    
    }
    /*
    public static void postClickThroughContractAccept(Long allianceId, AimsContract aimsContract,
    		String allianceContractStatus, String docType) throws HibernateException, Exception 
    {
    	ServiceManager.offerCreation(aimsContract.getContractId());
    }
    */
    public static void sendNotificationForCotnract(Long allianceId, AimsContract aimsContract,
                                                   String allianceContractStatus, String docType) throws HibernateException, Exception {

        AimsAllianc aimsAllianceForEvent =
            (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, allianceId.toString());

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
            if (allianceContractStatus.equalsIgnoreCase("A"))
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_CONTRACT_ACCEPTED);
            else if (allianceContractStatus.equalsIgnoreCase("R"))
                aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_ENT_CONTRACT_REJECTED);

            if (aimsEvent != null) 
            {
                AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, allianceId.toString());

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
        else 
        {
            // send notification when contract accepted/rejected
            if ("C".equals(docType) || "CT".equals(docType)) 
            {
                AimsEventLite aimsEvent = null;
                if (allianceContractStatus.equalsIgnoreCase("A"))
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_CONTRACT_ACCEPTED);
                else if (allianceContractStatus.equalsIgnoreCase("R"))
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_CONTRACT_REJECTED);

                if (aimsEvent != null) 
                {
                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, allianceId.toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceForEvent.getCompanyName());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, new Date().toString());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_TITLE, aimsContract.getContractTitle());
                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CONTRACT_VERSION, aimsContract.getVersion());
                    aimsEvent.raiseEvent(aimsEventObject);
                }
            }
        }
    }//end notification

    public static String getJournalTextForClickThroughAcceptReject(AimsContract aimsContract, String docType,
                                                                   String allianceContractStatus,
                                                                   String comments,
                                                                   String userName) {
        StringBuffer journalText = new StringBuffer();
        if (docType.equalsIgnoreCase("CT")) {
            journalText.append("Click Through Contract ")
                    .append("\"").append(aimsContract.getContractTitle()).append("\"").append(" ")
                    .append("(Version: ").append(aimsContract.getVersion()).append("), ").append(" ")
                    .append(allianceContractStatus).append(" by ").append(userName)
                    .append(". \nComments: ").append(comments);
        }

        return journalText.toString();
    }

    public static void setupAllianceAcceptRejectForm(HttpServletRequest request,
                                                     AllianceContractAcceptRejectForm contractAcceptRejectForm,
                                                     Long allianceId,
                                                     Long contractId,
                                                     String docType,
                                                     String currUserType) throws HibernateException, Exception {
        //get contract info
        AllianceContractInfoVO allianceContractInfo = null;
        try {
            if(docType.equals("C")) {
                allianceContractInfo = AllianceContractInfoManager.getAllianceContractInfo(allianceId, contractId);
            }
            else if(docType.equals("CT")) {
                //todo use getAimsContract(), instead of getContract, problem in getAimsContract(): ring Number Value is not available  
                Object [] userValues = null;
                Collection contractValues = ContractsManager.getContract(contractId);
                if(contractValues!=null && contractValues.size()>0) {
                    for (Iterator iter = contractValues.iterator(); iter.hasNext();) {
                        userValues  = (Object []) iter.next();
                        allianceContractInfo = new AllianceContractInfoVO();
                        allianceContractInfo.setContractId( ((Long) userValues[0]));
                        allianceContractInfo.setContractTitle((String) userValues[1]);
                        allianceContractInfo.setContractVersion((String) userValues[2]);                        
                        allianceContractInfo.setDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String) userValues[3]));
                        allianceContractInfo.setCompleteDocumentFileName((String) userValues[3]);
                        allianceContractInfo.setContractStatus((String) userValues[4]);
                        //allianceContractInfo.setIfNegotiable((String) userValues[5]);
                        //allianceContractInfo.setComments((String) userValues[6]);
                        allianceContractInfo.setContractExpDate((Date) userValues[7]);
                        //allianceContractInfo.setContractPlatformId((Long) userValues[8]);
                        allianceContractInfo.setContractPlatformName(StringFuncs.NullValueReplacement((String) userValues[9]));
                        //allianceContractInfo.setHTMLDocumentFileName(StringFuncs.NullValueReplacement((String) userValues[10]));
                        allianceContractInfo.setClickThroughContract((String) userValues[11]);
                    }
                }
            }//end click through contract
            request.setAttribute("allianceContractInfo", allianceContractInfo);
            contractAcceptRejectForm.setContractHTML(AllianceContractInfoManager.getAllianceContractHTML(contractId, currUserType));
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }//end setupAllianceAcceptRejectForm
}
