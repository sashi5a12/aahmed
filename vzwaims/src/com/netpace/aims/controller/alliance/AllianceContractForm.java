package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.Date;
import java.util.Set;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @struts.form name="AllianceContractForm"
 */
public class AllianceContractForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(AllianceContractForm.class.getName());
   
	private	 java.lang.Long 	contractId;
	private	 java.lang.Long 	allianceContractId;
	private	 java.lang.String 	contractTitle;
	private	 java.lang.String 	contractVersion;
	private	 java.util.Date		contractPresentDate;
	private	 java.lang.String 	contractStatus;
	private	 java.util.Date		contractAcceptDeclineDate;
    private  java.util.Date     contractExpDate;
	private	 java.lang.String 	contractPlatformName;
	private	 java.lang.String 	acceptDeclineFirstName;
	private	 java.lang.String 	acceptDeclineLastName;
	private	 java.lang.String 	vzwAccountManagerFirstName;
	private	 java.lang.String 	vzwAccountManagerLastName;
	private  java.lang.String   documentFileName;
	private  java.lang.String   modifiedContDocFileName;

	private	 java.util.Collection	contractAmendments;
	private	 java.util.Collection	allianceAmendments;


    public  java.lang.Long  getContractId() {
        return this.contractId;
    }

    public void setContractId( java.lang.Long  contractId) {
        this.contractId = contractId;
    }

	public  java.lang.Long  getAllianceContractId() {
        return this.allianceContractId;
    }

    public void setAllianceContractId( java.lang.Long  allianceContractId) {
        this.allianceContractId = allianceContractId;
    }

	public  java.lang.String  getContractTitle() {
        return this.contractTitle;
    }

    public void setContractTitle( java.lang.String  contractTitle) {
        this.contractTitle = contractTitle;
    }

	public  java.lang.String  getContractVersion() {
        return this.contractVersion;
    }

    public void setContractVersion( java.lang.String  contractVersion) {
        this.contractVersion = contractVersion;
    }

	public  java.util.Date getContractPresentDate() {
        return this.contractPresentDate;
    }

    public void setContractPresentDate( java.util.Date contractPresentDate) {
        this.contractPresentDate = contractPresentDate;
    }

    public java.util.Date getContractExpDate() {
        return this.contractExpDate;
    }

    public void setContractExpDate(java.util.Date contractExpDate) {
        this.contractExpDate = contractExpDate;
    }

	public  java.lang.String  getContractStatus() {
        return this.contractStatus;
    }

    public void setContractStatus( java.lang.String  contractStatus) {
        this.contractStatus = contractStatus;
    }

	public  java.util.Date getContractAcceptDeclineDate() {
        return this.contractAcceptDeclineDate;
    }

    public void setContractAcceptDeclineDate( java.util.Date contractAcceptDeclineDate) {
        this.contractAcceptDeclineDate = contractAcceptDeclineDate;
    }

	public  java.lang.String  getContractPlatformName() {
        return this.contractPlatformName;
    }

    public void setContractPlatformName( java.lang.String  contractPlatformName) {
        this.contractPlatformName = contractPlatformName;
    }

	public  java.lang.String  getAcceptDeclineFirstName() {
        return this.acceptDeclineFirstName;
    }

    public void setAcceptDeclineFirstName( java.lang.String  acceptDeclineFirstName) {
        this.acceptDeclineFirstName = acceptDeclineFirstName;
    }

	public  java.lang.String  getAcceptDeclineLastName() {
        return this.acceptDeclineLastName;
    }

    public void setAcceptDeclineLastName( java.lang.String  acceptDeclineLastName) {
        this.acceptDeclineLastName = acceptDeclineLastName;
    }

	public  java.lang.String  getVzwAccountManagerFirstName() {
        return this.vzwAccountManagerFirstName;
    }

    public void setVzwAccountManagerFirstName( java.lang.String  vzwAccountManagerFirstName) {
        this.vzwAccountManagerFirstName = vzwAccountManagerFirstName;
    }

	public  java.lang.String  getVzwAccountManagerLastName() {
        return this.vzwAccountManagerLastName;
    }

    public void setVzwAccountManagerLastName( java.lang.String  vzwAccountManagerLastName) {
        this.vzwAccountManagerLastName = vzwAccountManagerLastName;
    }


	public  java.lang.String  getDocumentFileName() {
        return this.documentFileName;
    }

    public void setDocumentFileName( java.lang.String  documentFileName) {
        this.documentFileName = documentFileName;
    }

	public  java.lang.String  getModifiedContDocFileName() {
        return this.modifiedContDocFileName;
    }

    public void setModifiedContDocFileName( java.lang.String  modifiedContDocFileName) {
        this.modifiedContDocFileName = modifiedContDocFileName;
    }
				
	public  java.util.Collection  getContractAmendments() {
        return this.contractAmendments;
    }

    public void setContractAmendments( java.util.Collection  contractAmendments) {
        this.contractAmendments = contractAmendments;
    } 
    
	public  java.util.Collection  getAllianceAmendments() {
        return this.allianceAmendments;
    }

    public void setAllianceAmendments( java.util.Collection  allianceAmendments) {
        this.allianceAmendments = allianceAmendments;
    }
    
}

