package com.netpace.aims.controller.alliance;

import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.contracts.ContractOfferManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.contracts.ContractsHelper;
import com.netpace.aims.dataaccess.valueobjects.AllianceContractInfoVO;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsAllianceContract;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * This class takes care of showing/accepting/rejecting both non-click through contracts and click through contracts.
 * @struts.action path="/allianceContractAcceptReject"
 *                scope="request"
 *				  name="AllianceContractAcceptRejectForm"
 *				  validate="true"
 *                input="/alliance/allianceContractAcceptReject.jsp"
 * @struts.action-forward name="viewAcceptRejectContract" path="/alliance/allianceContractAcceptReject.jsp"
 * @struts.action-forward name="allianceClickThroughDetail" path="/alliance/allianceClickThroughDetail.jsp" 
 * @struts.action-forward name="viewAllianceContracts" path="/allianceContracts.do?task=view"
 * @struts.action-forward name="compInfoEditScreen" path="/allianceCompInfoSetup.do?task=editForm"
 * @struts.action-forward name="viewAllianceClickThroughContracts" path="/allianceClickThroughContracts.do"
 * @struts.action-forward name="viewAllianceContractDetail" path="/alliance/allianceContractDetail.jsp"
 * @author Sajjad Raza
 */
public class AllianceContractAcceptRejectAction extends BaseAction {

    //This action will be called from Click Through contracts with task = allianceClickThroughDetail,
    // and from Alliance Contracts with task = viewAcceptRejectContract

    private static Logger log = Logger.getLogger(AllianceContractAcceptRejectAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception  {

        log.debug("========  start AllianceContractAcceptRejectAction");

        String forward = "";

        String taskName =  StringFuncs.NullValueReplacement(request.getParameter("task"));
        HttpSession session = request.getSession();

        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        String currUserName = user.getUsername();
        Long currentUserAllianceId = user.getAimsAllianc();
        String currUserType = user.getUserType();

        AllianceContractAcceptRejectForm contractAcceptRejectForm = (AllianceContractAcceptRejectForm)form;

        Long allianceId = new Long(StringFuncs.initializeStringGetParam(request.getParameter("allianceId"), "0"));

        Long contractId = new Long(StringFuncs.initializeStringGetParam(contractAcceptRejectForm.getSelContractId(), "0"));
        String docType = StringFuncs.NullValueReplacement(contractAcceptRejectForm.getSelDocType());
        Long docId = new Long(StringFuncs.initializeStringGetParam(contractAcceptRejectForm.getSelDocId(), "0"));

        String contractRequestedPage = StringFuncs.NullValueReplacement(contractAcceptRejectForm.getContractRequestedPage());

        if(StringFuncs.isNullOrEmpty(taskName)) {
            taskName = "viewAcceptRejectContract";
        }

        if (currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) 
        {
            allianceId = currentUserAllianceId;

            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CONTRACTS, AimsSecurityManager.SELECT))) {
                throw new AimsSecurityException();
            }

            if(docType.equals("C")) {
                //check this contract is offered to alliance
                if (!AllianceContractInfoManager.validateContract(allianceId, contractId)){
                        throw new AimsSecurityException();
                }
            }
            else if(docType.equals("CT")) {
                //if this contract is valid click through contract
                if (!AllianceContractInfoManager.validateClickThroughContract(allianceId, contractId, AimsConstants.CONTRACT_STATUS_ACTIVE)){
                        throw new AimsSecurityException();
                }
            }
            else if(docType.equals("AA")) {
                //check this amendment is offered to alliance
                if (!AllianceContractInfoManager.validateAmendment(allianceId, docId)){
                        throw new AimsSecurityException();
                }
            }
            else {
                log.debug("AllianceContractAcceptRejectAction: invalid docType");
                throw new AimsSecurityException();
            }
        }
        else if (currUserType.equalsIgnoreCase(AimsConstants.VZW_USERTYPE)) {
            //if current user is vzw
            if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.SELECT))) {
                throw new AimsSecurityException();
            }
        }


        if(taskName.equals("viewAcceptRejectContract") || taskName.equals("allianceClickThroughDetail")) 
        {
            AllianceContractInfoVO allianceContractInfo = null;
            String allianceContractStatus = "";

            //set requested page in form, to redirect after accepting click through contract
            contractAcceptRejectForm.setContractRequestedPage(StringFuncs.NullValueReplacement((String)request.getAttribute(ContractsHelper.REQUEST_CONTRACT_REQUESTING_PAGE)));

            contractAcceptRejectForm.setAllianceId(allianceId);

            ContractsHelper.setupAllianceAcceptRejectForm(request, contractAcceptRejectForm, allianceId, contractId, docType, currUserType);

            allianceContractInfo = (AllianceContractInfoVO)request.getAttribute("allianceContractInfo");
            if(allianceContractInfo!=null) {
                allianceContractStatus = StringFuncs.NullValueReplacement(allianceContractInfo.getAllianceContractStatus());
            }

            //for accepted/rejected contracts show alliance contract details page
            //both click through and normal contracts have docType=C (if accepted/rejected)
            if(docType.equals("C")
                    && (allianceContractStatus.equalsIgnoreCase(AimsConstants.CONTRACT_ACCEPTED)
                            || allianceContractStatus.equalsIgnoreCase(AimsConstants.CONTRACT_REJECTED))) {
                //contract details requested
                log.debug("contract details page is requested, contractId= "+contractId);
                forward = "viewAllianceContractDetail";
            }
            else {
                //show accept/reject page
                log.debug("show accept/reject page is requested, contractId= "+contractId);
                //set forward:  allianceClickThroughDetail  (for click through contract)
                //forward:      viewAcceptRejectContract    (for offered contract)
                forward = taskName;
            }
        }

        if(taskName.equals("edit") && currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) 
        {
            AimsContract aimsContract = ContractsManager.getAimsContract(contractId);

            String allianceContractStatus = "";
            if (contractAcceptRejectForm.getSelDocStatus().equalsIgnoreCase("A")) 
            {
                 allianceContractStatus = AimsConstants.CONTRACT_ACCEPTED;  //"ACCEPTED"
            }
            if (contractAcceptRejectForm.getSelDocStatus().equalsIgnoreCase("R")) 
            {
                 allianceContractStatus = AimsConstants.CONTRACT_REJECTED;  //"REJECTED"
            }

            if(aimsContract!=null && !StringFuncs.isNullOrEmpty(allianceContractStatus)) 
            {
                if(docType.equals("C")) 
                {

                    //security check for allinace to update contract
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_CONTRACTS, AimsSecurityManager.UPDATE))) {
                        throw new AimsSecurityException();
                    }

                  //query to get alliance record
                    AimsAllianc aimsAllianc =  AllianceManager.getAllianceOnAllianceID(allianceId);
                    
                    /**
                     * 	Updated for Issue 7847
                     *  Updated By: Muhammad Naeem Akhtar
                     *  Dated 22-09-2009 Friday
                     *  Updated into If condition 
                     * */
                    if (   aimsContract.getAimsPlatform().getPlatformId().equals(AimsConstants.JAVA_PLATFORM_ID) &&                 			
                 		   isRemitExists(aimsAllianc) && 
                 		   allianceContractStatus.equals(AimsConstants.CONTRACT_ACCEPTED) ) {
                    	
                    	ActionErrors errors = new ActionErrors();
                        ActionError error = null;
                        
                        error = new ActionError("error.AllianceContractAcceptReject.missing.remitInfo");
                   	 	errors.add(ActionErrors.GLOBAL_MESSAGE, error);
                   	 	saveErrors(request, errors);
                   	                 	 
                   	 	return mapping.findForward("compInfoEditScreen");
                    }
                    
                    
                    String acceptRejectComments = StringFuncs.trim(StringFuncs.NullValueReplacement(contractAcceptRejectForm.getAcceptRejectComments()));
                    ContractOfferManager.editContractAmendmentStatus(
                                                    new Long(contractAcceptRejectForm.getSelContractId()),
                                                    allianceId,
                                                    new Long(contractAcceptRejectForm.getSelDocId()),
                                                    contractAcceptRejectForm.getSelDocType(),
                                                    contractAcceptRejectForm.getSelDocStatus(),
                                                    StringFuncs.trim(contractAcceptRejectForm.getSelDocInitial()),
                                                    StringFuncs.trim(contractAcceptRejectForm.getSelDocTitle()),
                                                    acceptRejectComments,
                                                    user.getUserId(),
                                                    user.getUsername()
                                                                );

                    ContractsHelper.sendNotificationForCotnract(allianceId, aimsContract,
                                                                contractAcceptRejectForm.getSelDocStatus(), docType);

                    ContractsHelper.postAcceptRejectContract(allianceId, aimsContract,
                            contractAcceptRejectForm.getSelDocStatus(), docType);
                    
                    //save success message for contract
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.AllianceContract.acceptReject", allianceContractStatus.toLowerCase()));
                    saveMessages(request, messages);

                    forward = "viewAllianceContracts";
                }
                else if(docType.equals("CT")) {

                    //security check for allinace to update click through contract
                    if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CLICK_THROUGH_CONTRACTS, AimsSecurityManager.UPDATE))) {
                        throw new AimsSecurityException();
                    }

                    /**
                     * 	Updated for Issue 7847
                     *  Updated By: Muhammad Naeem Akhtar
                     *  Dated 22-09-2009 Friday
                     *  Updated check for Remit entries for VCAST Contract 
                     * */
                   	// query to get alliance record
                    AimsAllianc aimsAllianc =  AllianceManager.getAllianceOnAllianceID(allianceId);
                    
                    if (   aimsContract.getAimsPlatform().getPlatformId().equals(AimsConstants.JAVA_PLATFORM_ID) && isRemitExists(aimsAllianc)) {
                    	
                    	ActionErrors errors = new ActionErrors();
                        ActionError error = null;
                        
                        error = new ActionError("error.AllianceContractAcceptReject.missing.remitInfo");
                   	 	errors.add(ActionErrors.GLOBAL_MESSAGE, error);
                   	 	saveErrors(request, errors);
                   	                 	 
                   	 	return mapping.findForward("compInfoEditScreen");
                    }
                    /**
                     * 	End Update
                     * */
                    
                    AimsAllianceContract aimsAllianceContract = new AimsAllianceContract();
                    String acceptRejectComments = StringFuncs.trim(StringFuncs.NullValueReplacement(contractAcceptRejectForm.getAcceptRejectComments()));
                    Date currDate = new Date();
                    String journalEntryText = null;

                    aimsAllianceContract.setAllianceId(allianceId);
                    aimsAllianceContract.setContractId(contractId);

                    aimsAllianceContract.setStatus(allianceContractStatus);
                    aimsAllianceContract.setAcceptDeclineDate(currDate );
                    aimsAllianceContract.setAcceptDeclineUserId(user.getUserId());
                    aimsAllianceContract.setAcceptDeclineSignName(StringFuncs.trim(contractAcceptRejectForm.getSelDocInitial()));
                    aimsAllianceContract.setAcceptDeclineSignTitle(StringFuncs.trim(contractAcceptRejectForm.getSelDocTitle().trim()));

                    aimsAllianceContract.setContractExpDate(aimsContract.getExpiryDate());

                    if(!StringFuncs.isNullOrEmpty(acceptRejectComments)) {
                         //set comments if not null or empty
                         aimsAllianceContract.setComments(acceptRejectComments);
                     }

                    aimsAllianceContract.setCreatedBy(currUserName);
                    aimsAllianceContract.setCreatedDate(currDate);

                    journalEntryText = ContractsHelper.getJournalTextForClickThroughAcceptReject(aimsContract, docType,
                                                                                    allianceContractStatus,
                                                                                    acceptRejectComments,
                                                                                    currUserName);

                    AllianceContractInfoManager.saveOrUpdateAimsAllianceContract(aimsAllianceContract, journalEntryText);

                    ContractsHelper.sendNotificationForCotnract(allianceId, aimsContract,
                                                                contractAcceptRejectForm.getSelDocStatus(), docType);

                    ContractsHelper.postAcceptRejectContract(allianceId, aimsContract,
                            contractAcceptRejectForm.getSelDocStatus(), docType);
                    

                    //save success message for click through contract
                    ActionMessages messages = new ActionMessages();
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.AllianceContract.acceptReject", allianceContractStatus.toLowerCase()));
                    saveMessages(request, messages);

                    if(contractRequestedPage.equalsIgnoreCase("allianceClickThrougContracts")) {
                        //if page is requested from click through contracts, then forward control click through contracts after accepting contract
                        forward = "viewAllianceClickThroughContracts";
                    }
                    else {
                        forward = "viewAllianceContracts";
                    }
                }//end click through
            }
        }//end edit

        if(taskName.equalsIgnoreCase("cancel")) {
            if(contractRequestedPage.equalsIgnoreCase("allianceClickThrougContracts")) {
                    forward = "viewAllianceClickThroughContracts";
            }
            else {
                forward = "viewAllianceContracts";
            }
        }

        log.debug("========  end AllianceContractAcceptRejectAction, forward: "+forward);
        return mapping.findForward(forward);
    }
    
    /**
     * 	Updated for Issue 7847
     *  Updated By: Muhammad Naeem Akhtar
     *  Dated 22-09-2009 Friday
     * */
    private boolean isRemitExists(AimsAllianc aimsAllianc){
    	boolean remitExists = StringUtils.isEmpty(aimsAllianc.getRemitTo())
						  		 || StringUtils.isEmpty(aimsAllianc.getRemitAddress1())
						  		 || StringUtils.isEmpty(aimsAllianc.getRemitCountry())
						  		 || StringUtils.isEmpty(aimsAllianc.getRemitState())
						  		 || StringUtils.isEmpty(aimsAllianc.getRemitCity())
						  		 || StringUtils.isEmpty(aimsAllianc.getRemitPostalCode());
    	
    	return remitExists;
    }



}
