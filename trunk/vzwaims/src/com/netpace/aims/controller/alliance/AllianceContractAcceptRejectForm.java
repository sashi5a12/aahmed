package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.controller.contracts.ContractsHelper;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.bo.alliance.AllianceContractInfoManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.dataaccess.valueobjects.AllianceContractInfoVO;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

/**
* @struts.form name="AllianceContractAcceptRejectForm"
*/
public class AllianceContractAcceptRejectForm extends BaseValidatorForm {
    private static Logger log = Logger.getLogger(AllianceContractAcceptRejectForm.class.getName());

    private String selDocType; // 'C' Contract 'AA' Alliance Amendment 'CA' Contract Amendment
    private String selDocId;   // AmendmentId or ContractId
    private String selDocStatus; // Status 'A' Accept 'R' Reject
    private String selDocInitial;   // initial
    private String selDocTitle; // title
    private String selContractId;

    private String acceptRejectComments;

    private String contractHTML;

    private Long allianceId;

    private String contractRequestedPage;

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        log.debug("\n\nIn Validate of AllianceContractAcceptRejectForm \n\n");

        AimsUser currUser = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        String currUserType = currUser.getUserType();
        Long currentUserAllianceId = currUser.getAimsAllianc();
        Long contractAllianceId = this.allianceId;

        ActionErrors errors = new ActionErrors();

        String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        Long contractId = new Long(StringFuncs.initializeStringGetParam(this.selContractId, "0"));

        AimsContract aimsContract = null;

        if (task_name.equalsIgnoreCase("edit") ) 
        {
            if (currUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE)) 
            {
                contractAllianceId = currentUserAllianceId;
            }

            try 
            {
                aimsContract = ContractsManager.getAimsContract(contractId);
            }
            catch (HibernateException he) 
            {
                log.debug("Hibernate exception occured in AllianceContractAcceptRejectForm.validate()");
                he.printStackTrace();
            }
            catch(Exception e) 
            {
                log.debug("Exception occured in AllianceContractAcceptRejectForm.validate()");
                e.printStackTrace();
            }

            if (this.isBlankString(this.selDocInitial)) 
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocInitial"));
            }
            if (this.isBlankString(this.selDocTitle)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocTitle"));
            }

            if (this.isBlankString(this.selDocType)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocType"));
            }

            if (this.isBlankString(this.selDocStatus)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocStatus"));
            }
            else 
            {
                //check click through contract value from database instead of selDocType
                //alliance can only accept click through contracts, i.e., alliance can not reject click through contract
                if(aimsContract!=null && StringFuncs.NullValueReplacement(aimsContract.getIsClickThroughContract()).equalsIgnoreCase("Y")) {
                    if(!this.selDocStatus.equals("A")) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocStatus"));
                    }
                }
                else if(! (this.selDocStatus.equals("A") || this.selDocStatus.equals("R"))) {
                    //alliance can either accept or reject non-click-through contracts
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocStatus"));
                }

                if(this.selDocStatus.equals("R")) {
                    //comments are required for rejected contract
                    if(this.isBlankString(this.acceptRejectComments)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.acceptRejectComments"));
                    }
                }
            }//end else outer

            ///////////////////////// db fields validation
            if(!this.isBlankString(this.selDocInitial) && (this.selDocTitle.length()>100)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceContract.length.acceptRejectInitial"));
            }
            if(!this.isBlankString(this.selDocTitle) && (this.selDocTitle.length()>100)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceContract.length.acceptRejectTitle"));
            }
            //comments size check, but comments are optional
            if(!this.isBlankString(this.acceptRejectComments) && (this.acceptRejectComments.length()>200)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceContract.length.acceptRejectComments"));
            }
        }//end edit

        if(errors.size()>0) {
            try {
                //errors found setup form values
                ContractsHelper.setupAllianceAcceptRejectForm(request, this, contractAllianceId, contractId, selDocType, currUserType);
            }
            catch (HibernateException he) {
                log.debug("Hibernate exception occured in AllianceContractAcceptRejectForm.validate()");
                he.printStackTrace();
            }
            catch(Exception e) {
                log.debug("Exception occured in AllianceContractAcceptRejectForm.validate()");
                e.printStackTrace();
            }
        }//end errors

        return errors;
    }


    public String getSelDocType() {
        return selDocType;
    }

    public void setSelDocType(String selDocType) {
        this.selDocType = selDocType;
    }

    public String getSelDocId() {
        return selDocId;
    }

    public void setSelDocId(String selDocId) {
        this.selDocId = selDocId;
    }

    public String getSelDocStatus() {
        return selDocStatus;
    }

    public void setSelDocStatus(String selDocStatus) {
        this.selDocStatus = selDocStatus;
    }

    public String getSelDocInitial() {
        return selDocInitial;
    }

    public void setSelDocInitial(String selDocInitial) {
        this.selDocInitial = selDocInitial;
    }

    public String getSelDocTitle() {
        return selDocTitle;
    }

    public void setSelDocTitle(String selDocTitle) {
        this.selDocTitle = selDocTitle;
    }

    public String getSelContractId() {
        return selContractId;
    }

    public void setSelContractId(String selContractId) {
        this.selContractId = selContractId;
    }

    public String getAcceptRejectComments() {
        return acceptRejectComments;
    }

    public void setAcceptRejectComments(String acceptRejectComments) {
        this.acceptRejectComments = acceptRejectComments;
    }

    public String getContractHTML() {
        return contractHTML;
    }

    public void setContractHTML(String contractHTML) {
        this.contractHTML = contractHTML;
    }

    public Long getAllianceId() {
        return allianceId;
    }

    public void setAllianceId(Long allianceId) {
        this.allianceId = allianceId;
    }

    public String getContractRequestedPage() {
        return contractRequestedPage;
    }

    public void setContractRequestedPage(String contractRequestedPage) {
        this.contractRequestedPage = contractRequestedPage;
    }
}