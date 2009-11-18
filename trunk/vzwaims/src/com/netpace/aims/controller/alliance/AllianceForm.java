package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;

import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.model.core.*;

import com.netpace.aims.util.*;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * @struts.form name="AllianceForm"
 */
public class AllianceForm extends BaseValidatorForm {

    static Logger log = Logger.getLogger(AllianceForm.class.getName());

	private	 java.lang.Long	      allianceId;
	private	 java.lang.String 	  companyName;
	private	 java.lang.String 	  companyLegalName;
	private	 java.util.Date 	  dateEstablished;
	private	 java.lang.String 	  status;
	private	 java.lang.String 	  vzwAccMgrFirstName;
	private	 java.lang.String 	  vzwAccMgrLastName;
	private	 java.lang.String 	  vzwAccMgrEmailAddress;
	private	 java.lang.String 	  vzwAccMgrPhone;
	private	 java.lang.String 	  salesContactFirstName;
	private	 java.lang.String 	  salesContactLastName;
	private	 java.lang.String 	  salesContactEmailAddress;
	private	 java.lang.String 	  salesContactPhone;
	private	 java.lang.String 	  allianceAdminFirstName;
	private	 java.lang.String 	  allianceAdmintLastName;
	private	 java.lang.String 	  allianceAdminEmailAddress;
	private	 java.lang.String 	  allianceAdminPhone;
	private  java.util.Collection contracts;
	private  java.util.Collection applications;
    private	 java.lang.String 	  showAcceptRejectButton;
	private  java.util.Collection salesContacts;
	private  java.util.Collection accountManagers;
	private	 java.lang.String 	  selSalesContact;
	private	 java.lang.String 	  selAccManager;

    public   java.lang.String[]   contractIds;
	public   java.lang.String[]   acceptDeclineInitials;
	public   java.lang.String[]   acceptDeclineTitles;

	private	 java.lang.String 	  selDocType; // 'C' Contract 'AA' Alliance Amendment 'CA' Contract Amendment
	private	 java.lang.String 	  selDocId;   // AmendmentId or ContractId
	private	 java.lang.String 	  selDocStatus; // Status 'A' Accept 'R' Reject
	private	 java.lang.String 	  selDocInitial;   // AmendmentId or ContractId
	private	 java.lang.String 	  selDocTitle; // Status 'A' Accept 'R' Reject
    private	 java.lang.String 	  selContractId;
    private java.util.Date acceptDeclineDate; // Status 'A' Accept 'R' Reject

    private String[] selectedFilterCTPlatform=new String[0];

    public String getAllianceStatus (String key) {
		HashMap allianceStatusHashMap = new HashMap();
		String retString = "";
		allianceStatusHashMap.put("A", "Accepted");
		allianceStatusHashMap.put("R", "Rejected");
		allianceStatusHashMap.put("S", "Submitted");
        allianceStatusHashMap.put("U", "Reviewed");
        allianceStatusHashMap.put("D", "Deleted");

		if (allianceStatusHashMap.containsKey(key))
		{
			retString = (String) allianceStatusHashMap.get(key);
		}


		return retString;
	}

	public java.lang.Long getAllianceId() {
        return this.allianceId;
    }

    public void setAllianceId(java.lang.Long allianceId) {
        this.allianceId = allianceId;
    }

	public  java.lang.String  getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName( java.lang.String  companyName) {
        this.companyName = companyName;
    }

	public  java.lang.String  getCompanyLegalName() {
        return this.companyLegalName;
    }

    public void setCompanyLegalName( java.lang.String  companyLegalName) {
        this.companyLegalName = companyLegalName;
    }

	public  java.util.Date  getDateEstablished() {
        return this.dateEstablished;
    }

    public void setDateEstablished( java.util.Date  dateEstablished) {
        this.dateEstablished = dateEstablished;
    }

    public void setStatus( java.lang.String  status) {
        this.status = status;
    }

	public  java.lang.String  getStatus() {
        return this.status;
    }

	public  java.lang.String  getVzwAccMgrFirstName() {
        return this.vzwAccMgrFirstName;
    }

    public void setVzwAccMgrFirstName( java.lang.String  vzwAccMgrFirstName) {
        this.vzwAccMgrFirstName = vzwAccMgrFirstName;
    }

	public  java.lang.String  getVzwAccMgrLastName() {
        return this.vzwAccMgrLastName;
    }

    public void setVzwAccMgrLastName( java.lang.String  vzwAccMgrLastName) {
        this.vzwAccMgrLastName = vzwAccMgrLastName;
    }

	public  java.lang.String  getVzwAccMgrEmailAddress() {
        return this.vzwAccMgrEmailAddress;
    }

    public void setVzwAccMgrEmailAddress( java.lang.String  vzwAccMgrEmailAddress) {
        this.vzwAccMgrEmailAddress = vzwAccMgrEmailAddress;
    }

	public  java.lang.String  getVzwAccMgrPhone() {
        return this.vzwAccMgrPhone;
    }

    public void setVzwAccMgrPhone( java.lang.String  vzwAccMgrPhone) {
        this.vzwAccMgrPhone = vzwAccMgrPhone;
    }

	public  java.lang.String  getSalesContactFirstName() {
        return this.salesContactFirstName;
    }

    public void setSalesContactFirstName( java.lang.String  salesContactFirstName) {
        this.salesContactFirstName = salesContactFirstName;
    }

	public  java.lang.String  getSalesContactLastName() {
        return this.salesContactLastName;
    }

    public void setSalesContactLastName( java.lang.String  salesContactLastName) {
        this.salesContactLastName = salesContactLastName;
    }

	public  java.lang.String  getSalesContactEmailAddress() {
        return this.salesContactEmailAddress;
    }

    public void setSalesContactEmailAddress( java.lang.String  salesContactEmailAddress) {
        this.salesContactEmailAddress = salesContactEmailAddress;
    }

	public  java.lang.String  getSalesContactPhone() {
        return this.salesContactPhone;
    }

    public void setSalesContactPhone( java.lang.String  salesContactPhone) {
        this.salesContactPhone = salesContactPhone;
    }


	public  java.lang.String  getAllianceAdminFirstName() {
        return this.allianceAdminFirstName;
    }

    public void setAllianceAdminFirstName( java.lang.String  allianceAdminFirstName) {
        this.allianceAdminFirstName = allianceAdminFirstName;
    }

	public  java.lang.String  getAllianceAdminLastName() {
        return this.allianceAdmintLastName;
    }

    public void setAllianceAdminLastName( java.lang.String  allianceAdmintLastName) {
        this.allianceAdmintLastName = allianceAdmintLastName;
    }

	public  java.lang.String  getAllianceAdminEmailAddress() {
        return this.allianceAdminEmailAddress;
    }

    public void setAllianceAdminEmailAddress( java.lang.String  allianceAdminEmailAddress) {
        this.allianceAdminEmailAddress = allianceAdminEmailAddress;
    }

	public  java.lang.String  getAllianceAdminPhone() {
        return this.allianceAdminPhone;
    }

    public void setAllianceAdminPhone( java.lang.String  allianceAdminPhone) {
        this.allianceAdminPhone = allianceAdminPhone;
    }

	public  java.util.Collection  getContracts() {
        return this.contracts;
    }

    public void setContracts( java.util.Collection  contracts) {
        this.contracts = contracts;
    }

	public  java.util.Collection  getApplications() {
        return this.applications;
    }

    public void setApplications( java.util.Collection  applications) {
        this.applications = applications;
    }

	public  java.lang.String  getShowAcceptRejectButton() {
        return this.showAcceptRejectButton;
    }

    public void setShowAcceptRejectButton( java.lang.String  showAcceptRejectButton) {
        this.showAcceptRejectButton = showAcceptRejectButton;
    }

	public  java.util.Collection  getSalesContacts() {
        return this.salesContacts;
    }

    public void setSalesContacts( java.util.Collection  salesContacts) {
        this.salesContacts = salesContacts;
    }

	public  java.util.Collection  getAccountManagers() {
        return this.accountManagers;
    }

    public void setAccountManagers( java.util.Collection  accountManagers) {
        this.accountManagers = accountManagers;
    }

	public  java.lang.String  getSelSalesContact() {
        return this.selSalesContact;
    }

    public void setSelSalesContact( java.lang.String  selSalesContact) {
        this.selSalesContact = selSalesContact;
    }

	public  java.lang.String  getSelAccManager() {
        return this.selAccManager;
    }

    public void setSelAccManager( java.lang.String  selAccManager) {
        this.selAccManager = selAccManager;
    }

    public java.lang.String getContractIds(int index)
    {
        return this.contractIds[index];
    }

    public void setContractIds(int index, java.lang.String contractIds)
    {
        this.contractIds[index] = contractIds;
    }

    public java.lang.String getAcceptDeclineInitials(int index)
    {
        return this.acceptDeclineInitials[index];
    }

    public void setAcceptDeclineInitials(int index, java.lang.String acceptDeclineInitials)
    {
        this.acceptDeclineInitials[index] = acceptDeclineInitials;
    }

    public java.lang.String getAcceptDeclineTitles(int index)
    {
        return this.acceptDeclineTitles[index];
    }

    public void setAcceptDeclineTitles(int index, java.lang.String acceptDeclineTitles)
    {
        this.acceptDeclineTitles[index] = acceptDeclineTitles;
    }

	public  java.lang.String  getSelDocType() {
        return this.selDocType;
    }

    public void setSelDocType( java.lang.String  selDocType) {
        this.selDocType = selDocType;
    }


	public  java.lang.String  getSelDocId() {
        return this.selDocId;
    }

    public void setSelDocId( java.lang.String  selDocId) {
        this.selDocId = selDocId;
    }

	public  java.lang.String  getSelDocStatus() {
        return this.selDocStatus;
    }

    public void setSelDocStatus( java.lang.String  selDocStatus) {
        this.selDocStatus = selDocStatus;
    }

	public  java.lang.String  getSelDocInitial() {
        return this.selDocInitial;
    }

    public void setSelDocInitial( java.lang.String  selDocInitial) {
        this.selDocInitial = selDocInitial;
    }

	public  java.lang.String  getSelDocTitle() {
        return this.selDocTitle;
    }

    public void setSelDocTitle( java.lang.String  selDocTitle) {
        this.selDocTitle = selDocTitle;
    }

	public  java.lang.String  getSelContractId() {
        return this.selContractId;
    }

    public void setSelContractId( java.lang.String  selContractId) {
        this.selContractId = selContractId;
    }



	public void reset (ActionMapping mapping, HttpServletRequest request)   {


		System.out.println ("RESET called in AllianceForm");

        this.selectedFilterCTPlatform=this.getAllSelectedFilterCTPlatform();
    }


	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {



		log.debug("\n\nIn Validate of AlliancForm \n\n");

		ActionErrors errors = new ActionErrors();

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (task_name.equalsIgnoreCase("edit") )
        {

        log.debug("task_name in validate of AlliancForm "  + task_name);
        log.debug("selDocTitle in validate of AlliancForm "  + selDocTitle);
        log.debug("selDocInitial in validate of AlliancForm "  + selDocInitial);
        log.debug("selDocType in validate of AlliancForm "  + selDocType);
        log.debug("selDocStatus in validate of AlliancForm "  + selDocStatus);

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();

		Long alliance_id = user.getAimsAllianc();
        Collection AimsAllianceContracts = null;
        AllianceContractForm allianceContractForm  = null;



			if (this.isBlankString(selDocTitle))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocTitle"));
			}

			if (this.isBlankString(selDocInitial))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocInitial"));
			}

			if (this.isBlankString(selDocType))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocType"));
			}

			if (this.isBlankString(selDocStatus))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceForm.required.selDocStatus"));
			}


			try
			{
                AimsAllianceContracts = AllianceManager.getAllianceContractAmendments(alliance_id, user_type);

                this.setContracts(AimsAllianceContracts);


                String [] arrContractIds = new String[AimsAllianceContracts.size()];

                int cntContract = 0;
                for (Iterator iter = AimsAllianceContracts.iterator(); iter.hasNext();)
                {
                     allianceContractForm  = (AllianceContractForm) iter.next();
                     arrContractIds[cntContract] = allianceContractForm.getContractId().toString();
                     cntContract++;
                }
                this.contractIds = arrContractIds;
            }
			catch (HibernateException he)
			{
				log.debug("A hibernate exception occured!");
			}


		}
		return errors;
	}
  public java.util.Date getAcceptDeclineDate() {
    return acceptDeclineDate;
  }
  public void setAcceptDeclineDate(java.util.Date acceptDeclineDate) {
    this.acceptDeclineDate = acceptDeclineDate;
  }

    public String[] getSelectedFilterCTPlatform() {
        return selectedFilterCTPlatform;
    }

    public void setSelectedFilterCTPlatform(String[] selectedFilterCTPlatform) {
        this.selectedFilterCTPlatform = selectedFilterCTPlatform;
    }

    public String[] getAllSelectedFilterCTPlatform(){
    	return new String[]{AimsConstants.FILTER_SHOW_ALL};
    }
}

