package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.model.core.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * @struts.form name="VZWAllianceForm"
 */
public class VZWAllianceForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(VZWAllianceForm.class.getName());
   
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
	private	 java.lang.String 	  allianceAdminLastName;
    private	 java.lang.String 	  allianceAdminEmailAddress;
    private	 java.lang.String 	  isOnHold;
	private  java.util.Collection contracts;
	private  java.util.Collection applications;

	private	 java.lang.String 	  dateEstablishedFrom;
	private	 java.lang.String 	  dateEstablishedTo;
	private	 java.lang.String 	  vzwAccMgrName;
	private	 java.lang.String 	  allianceAdminName;
    private	 java.lang.String 	  allianceName;
	private	 java.lang.String 	  contractId;

    private  java.lang.String     filterField;
    private  java.lang.String     filterText;		
    private  java.lang.String     sortField;	
    private  java.lang.String     allianceType;
    private	 java.lang.String 	  showAcceptRejectButton;


	public String getAllianceStatus (String key) {
		HashMap allianceStatusHashMap = new HashMap();
		String retString = "";
		allianceStatusHashMap.put("A", "Accepted");
		allianceStatusHashMap.put("S", "Submitted");
		allianceStatusHashMap.put("R", "Rejected");
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

	public java.lang.String getContractId() {
        return this.contractId;
    }

    public void setContractId(java.lang.String contractId) {
        this.contractId = contractId;
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

	public  java.lang.String  getDateEstablishedFrom() {
        return this.dateEstablishedFrom;
    }

    public void setDateEstablishedFrom( java.lang.String  dateEstablishedFrom) {
        this.dateEstablishedFrom = dateEstablishedFrom;
    }

	public  java.lang.String  getDateEstablishedTo() {
        return this.dateEstablishedTo;
    }

    public void setDateEstablishedTo( java.lang.String  dateEstablishedTo) {
        this.dateEstablishedTo = dateEstablishedTo;
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

	public  java.lang.String  getVzwAccMgrName() {
        return this.vzwAccMgrName;
    }

    public void setVzwAccMgrName( java.lang.String  vzwAccMgrName) {
        this.vzwAccMgrName = vzwAccMgrName;
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
        return this.allianceAdminLastName;
    }

    public void setAllianceAdminLastName( java.lang.String  allianceAdminLastName) {
        this.allianceAdminLastName = allianceAdminLastName;
    }

	public  java.lang.String  getAllianceAdminEmailAddress() {
        return this.allianceAdminEmailAddress;
    }

    public void setAllianceAdminEmailAddress( java.lang.String  allianceAdminEmailAddress) {
        this.allianceAdminEmailAddress = allianceAdminEmailAddress;
    }

	public  java.lang.String  getAllianceAdminName() {
        return this.allianceAdminName;
    }

    public void setAllianceAdminName( java.lang.String  allianceAdminName) {
        this.allianceAdminName = allianceAdminName;
    }


	public  java.lang.String  getAllianceName() {
        return this.allianceName;
    }

    public void setAllianceName( java.lang.String  allianceName) {
        this.allianceName = allianceName;
    }

	public java.lang.String getIsOnHold() {
        return this.isOnHold;
    }

    public void setIsOnHold(java.lang.String isOnHold) {
        this.isOnHold = isOnHold;
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

    public java.lang.String getFilterField() {
        return this.filterField;
    }

    public void setFilterField(java.lang.String filterField) {
        this.filterField = filterField;
    }

    public java.lang.String getFilterText() {
        return this.filterText;
    }

    public void setFilterText(java.lang.String filterText) {
        this.filterText = filterText;
    }

    public java.lang.String getSortField() {
        return this.sortField;
    }

    public void setSortField(java.lang.String sortField) {
        this.sortField = sortField;
    }
    
    public java.lang.String getAllianceType() {
        return this.allianceType;
    }

    public void setAllianceType(java.lang.String allianceType) {
        this.allianceType = allianceType;
    }

	public  java.lang.String  getShowAcceptRejectButton() {
        return this.showAcceptRejectButton;
    }

    public void setShowAcceptRejectButton( java.lang.String  showAcceptRejectButton) {
        this.showAcceptRejectButton = showAcceptRejectButton;
    }


	public void reset (ActionMapping mapping, HttpServletRequest request)   {
		
		log.debug ("RESET called in VZWAllianceForm");

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();

        if (task_name.equalsIgnoreCase("searchView") )
        {			

            try
            {
				this.contracts = VZWAllianceManager.getContracts();	
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }            
	

        }
	}


	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		

		ActionErrors errors = new ActionErrors();

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (task_name.equalsIgnoreCase("search") )
        {

			if ((!this.isBlankString(dateEstablishedFrom)) && (!this.isDate(this.dateEstablishedFrom)) )
			{				
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.valid.vzwAllianceSearch.dateEstablishedFrom"));
			}

			if ((!this.isBlankString(dateEstablishedTo)) && (!this.isDate(this.dateEstablishedTo)) )
			{				
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.valid.vzwAllianceSearch.dateEstablishedTo"));
			}

			if ( (this.isDate(this.dateEstablishedTo) && this.isDate(this.dateEstablishedFrom)) && (StringFuncs.getDate(dateEstablishedTo).before(StringFuncs.getDate(dateEstablishedFrom))) )
			{				
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.valid.vzwAllianceSearch.dateFromToOrder"));
			}
		

            try
            {
                this.contracts = VZWAllianceManager.getContracts();	
            }
            catch (HibernateException he)
            {
                log.debug("A hibernate exception occured!");
            }

        }


		return errors;
	}
				
    
    
}

