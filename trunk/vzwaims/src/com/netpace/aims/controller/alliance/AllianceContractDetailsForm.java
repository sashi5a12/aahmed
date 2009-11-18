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

import com.netpace.aims.util.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.bo.alliance.*;

import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * @struts.form name="AllianceContractDetailsForm"
 */
public class AllianceContractDetailsForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(AllianceContractDetailsForm.class.getName());	
   
	private	 java.lang.String 	allianceContractId;
	private	 java.lang.String 	companyName;
	private	 java.lang.String 	contractId;
	private	 java.lang.String 	contractTitle;
	private	 java.lang.String 	version;
	private	 java.lang.String 	contractIfNegotiable;
	private	 java.lang.String 	contractStatus;
	private	 java.lang.String 	documentFileName;
	private	 java.lang.String 	documentContentType;
	private	 java.lang.String 	ifAttachToSoln;
	private	 java.lang.String 	platformName;
	private	 java.util.Date 	vzwContractPresentDate;
	private	 java.lang.String 	allianceContractStatus;
	private	 java.lang.String 	modifiedContDocFileName;
	private	 java.lang.String 	modifiedContDocContentType;
	private	 java.lang.String 	allianceContractVzwStatus;
	private	 java.lang.String 	allianceContractIfNegotiable;
	private	 java.util.Date 	acceptDeclineDate;
	private	 java.lang.String 	acceptDeclineFirstName;
	private	 java.lang.String 	acceptDeclineLastName;
	private	 java.util.Date 	contractEffDate;
	private	 java.util.Date 	contractExpDate;
	private	 FormFile			originalContDoc;
	private	 FormFile			modifiedContDoc;
	private	 java.lang.String 	showAcceptRejectButton;
	private	 java.lang.String 	showModifiedContractUpload;
	private	 java.lang.String 	showAtleastOneButton;


	public  java.lang.String  getAllianceContractId() {
        return this.allianceContractId;
    }

    public void setAllianceContractId( java.lang.String  allianceContractId) {
        this.allianceContractId = allianceContractId;
    }

	public  java.lang.String  getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName( java.lang.String  companyName) {
        this.companyName = companyName;
    }

	public  java.lang.String  getContractId() {
        return this.contractId;
    }

    public void setContractId( java.lang.String  contractId) {
        this.contractId = contractId;
    }

	public  java.lang.String  getContractTitle() {
        return this.contractTitle;
    }

    public void setContractTitle( java.lang.String  contractTitle) {
        this.contractTitle = contractTitle;
    }

	public  java.lang.String  getVersion() {
        return this.version;
    }

    public void setVersion( java.lang.String  version) {
        this.version = version;
    }

	public java.lang.String getContractIfNegotiable() {
        return this.contractIfNegotiable;
    }

    public void setContractIfNegotiable(java.lang.String contractIfNegotiable) {
        this.contractIfNegotiable = contractIfNegotiable;
    }

	public  java.lang.String  getContractStatus() {
        return this.contractStatus;
    }

    public void setContractStatus( java.lang.String  contractStatus) {
        this.contractStatus = contractStatus;
    }

	public  java.lang.String  getDocumentFileName() {
        return this.documentFileName;
    }

    public void setDocumentFileName( java.lang.String  documentFileName) {
        this.documentFileName = documentFileName;
    }

	public  java.lang.String  getDocumentContentType() {
        return this.documentContentType;
    }

    public void setDocumentContentType( java.lang.String  documentContentType) {
        this.documentContentType = documentContentType;
    }

	public  java.lang.String  getIfAttachToSoln() {
        return this.ifAttachToSoln;
    }

    public void setIfAttachToSoln( java.lang.String  ifAttachToSoln) {
        this.ifAttachToSoln = ifAttachToSoln;
    }

	public  java.lang.String  getPlatformName() {
        return this.platformName;
    }

    public void setPlatformName( java.lang.String  platformName) {
        this.platformName = platformName;
    }

	public  java.util.Date  getVzwContractPresentDate() {
        return this.vzwContractPresentDate;
    }

    public void setVzwContractPresentDate( java.util.Date  vzwContractPresentDate) {
        this.vzwContractPresentDate = vzwContractPresentDate;
    }

	public  java.lang.String  getAllianceContractStatus() {
        return this.allianceContractStatus;
    }

    public void setAllianceContractStatus( java.lang.String  allianceContractStatus) {
        this.allianceContractStatus = allianceContractStatus;
    }

	public  java.lang.String  getModifiedContDocFileName() {
        return this.modifiedContDocFileName;
    }

    public void setModifiedContDocFileName( java.lang.String  modifiedContDocFileName) {
        this.modifiedContDocFileName = modifiedContDocFileName;
    }

	public  java.lang.String  getModifiedContDocContentType() {
        return this.modifiedContDocContentType;
    }

    public void setModifiedContDocContentType( java.lang.String  modifiedContDocContentType) {
        this.modifiedContDocContentType = modifiedContDocContentType;
    }

	public  java.lang.String  getAllianceContractVzwStatus() {
        return this.allianceContractVzwStatus;
    }

    public void setAllianceContractVzwStatus( java.lang.String  allianceContractVzwStatus) {
        this.allianceContractVzwStatus = allianceContractVzwStatus;
    }

	public  java.lang.String  getAllianceContractIfNegotiable() {
        return this.allianceContractIfNegotiable;
    }

    public void setAllianceContractIfNegotiable( java.lang.String  allianceContractIfNegotiable) {
        this.allianceContractIfNegotiable = allianceContractIfNegotiable;
    }

	public  java.util.Date  getAcceptDeclineDate() {
        return this.acceptDeclineDate;
    }

    public void setAcceptDeclineDate( java.util.Date  acceptDeclineDate) {
        this.acceptDeclineDate = acceptDeclineDate;
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

	public  java.util.Date  getContractEffDate() {
        return this.contractEffDate;
    }

    public void setContractEffDate( java.util.Date  contractEffDate) {
        this.contractEffDate = contractEffDate;
    }

	public  java.util.Date  getContractExpDate() {
        return this.contractExpDate;
    }

    public void setContractExpDate( java.util.Date  contractExpDate) {
        this.contractExpDate = contractExpDate;
    }

	public  FormFile getOriginalContDoc() {
        return this.originalContDoc;
    }

    public void setOriginalContDoc ( FormFile originalContDoc) {
        this.originalContDoc = originalContDoc;
    }


	public  FormFile getModifiedContDoc() {
        return this.modifiedContDoc;
    }

    public void setModifiedContDoc ( FormFile modifiedContDoc) {
        this.modifiedContDoc = modifiedContDoc;
    }

	public  java.lang.String  getShowAcceptRejectButton() {
        return this.showAcceptRejectButton;
    }

    public void setShowAcceptRejectButton( java.lang.String  showAcceptRejectButton) {
        this.showAcceptRejectButton = showAcceptRejectButton;
    }

	public  java.lang.String  getShowModifiedContractUpload() {
        return this.showModifiedContractUpload;
    }

    public void setShowModifiedContractUpload( java.lang.String  showModifiedContractUpload) {
        this.showModifiedContractUpload = showModifiedContractUpload;
    }

	public  java.lang.String  getShowAtleastOneButton() {
        return this.showAtleastOneButton;
    }

    public void setShowAtleastOneButton( java.lang.String  showAtleastOneButton) {
        this.showAtleastOneButton = showAtleastOneButton;
    }

	public void reset (ActionMapping mapping, HttpServletRequest request)
	{
	
		Collection AllianceContractDetails = null;
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		Long alliance_id = user.getAimsAllianc();
		Long contract_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("contractId"), "0"));
		Object [] userValues = null;

		log.debug("Task : " + task_name);		

		log.debug("contract_id : " + contract_id);		

        if (task_name.equalsIgnoreCase("editForm") )
        {			

            try
            {
				log.debug("Inside editForm reset !!!");	

				AllianceContractDetails = AllianceContractInfoManager.getAllianceContractDetails(alliance_id, user_type, contract_id);				
				log.debug("Inside editForm AllianceContractDetails.size() " + AllianceContractDetails.size());	

				for (Iterator iter = AllianceContractDetails.iterator(); iter.hasNext();) 
				{
					userValues  = (Object []) iter.next();
	
					this.setContractId( ((Long) userValues[0]).toString());
					this.setContractTitle( (String) userValues[1]);
					this.setVersion( (String) userValues[2]);
					this.setContractIfNegotiable( (String) userValues[3]);
					this.setContractStatus( (String) userValues[4]);
					this.setDocumentFileName( (String) userValues[5]);
					this.setDocumentContentType( (String) userValues[6]);

					this.setIfAttachToSoln( (String) userValues[7]);
					this.setPlatformName( (String) userValues[8]);
					this.setVzwContractPresentDate( (Date) userValues[9]);
					this.setAllianceContractStatus( (String) userValues[10]);
					this.setModifiedContDocFileName( (String) userValues[11]);
					this.setModifiedContDocContentType( (String) userValues[12]);
					this.setAllianceContractVzwStatus( (String) userValues[13]);

					this.setAllianceContractIfNegotiable( (String) userValues[14]);
					this.setAcceptDeclineDate( (Date) userValues[15]);
					this.setContractEffDate( (Date) userValues[16]);
					this.setContractExpDate( (Date) userValues[17]);
					this.setAcceptDeclineFirstName( (String) userValues[18]);
					this.setAcceptDeclineLastName( (String) userValues[19]);

					this.setCompanyName( (String) userValues[20]);
					this.setAllianceContractId( ((Long) userValues[21]).toString());
                    
                    if (
                        this.getAllianceContractStatus().equalsIgnoreCase(AimsConstants.CONTRACT_ACCEPTED)
                        ||
                        this.getAllianceContractStatus().equalsIgnoreCase(AimsConstants.CONTRACT_DECLINED)                        
                        )
                    {
                        this.setShowAcceptRejectButton("N");
                    }
                    else 
                    {
                        this.setShowAcceptRejectButton("Y");
                    }


                    if (
						(
							this.getAllianceContractStatus().equalsIgnoreCase(AimsConstants.CONTRACT_REQUEST_CHANGE)
							||
							this.getAllianceContractStatus().equalsIgnoreCase(AimsConstants.CONTRACT_OFFERED)
						)                       
						&&
						(
							StringFuncs.NullValueReplacement(this.getAllianceContractIfNegotiable()).equalsIgnoreCase("Y")
						)
                       )
                    {
                        this.setShowModifiedContractUpload("Y");
                    }
                    else 
                    {
                        this.setShowModifiedContractUpload("N");
                    }

					//showAtleastOneButton
                    if (
						(
							this.getAllianceContractStatus().equalsIgnoreCase(AimsConstants.CONTRACT_ACCEPTED)
							||
							this.getAllianceContractStatus().equalsIgnoreCase(AimsConstants.CONTRACT_DECLINED)
						)  
                       )
                    {
                        this.setShowAtleastOneButton("N");
                    }
                    else 
                    {
                        this.setShowAtleastOneButton("Y");
                    }
				}		

            }
            catch (HibernateException he)
            {
                System.out.println("A hibernate exception occured!");
            }            
	

        }
		
	}
			 
    
}

