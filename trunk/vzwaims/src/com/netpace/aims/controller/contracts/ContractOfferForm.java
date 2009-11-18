package com.netpace.aims.controller.contracts;

import com.netpace.aims.controller.BaseValidatorForm;

import com.netpace.aims.util.*;
import com.netpace.aims.bo.system.*;
import com.netpace.aims.bo.contracts.*;

import org.apache.struts.action.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import java.util.*;

import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import org.apache.log4j.Logger;

/**
 * @struts.form name="ContractOfferForm"
 */
public class ContractOfferForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(ContractOfferForm.class.getName());

    private	 java.lang.String 	allianceContractId;
	private	 java.lang.String 	contractId;
	private	 java.lang.String 	allianceId;
	private	 java.lang.String 	contractTitle;
	private	 java.lang.String 	contractVersion;
	private	 java.lang.String 	contractStatus;
	private	 java.lang.String	contractExpiryDate;
	private	 java.lang.String 	contractPlatformName;
	private  FormFile			contractDocument;
	private  java.lang.String   contractDocumentFileName;
	private  java.lang.String   contractDocumentContentType;
    private  java.lang.String   numContractAmendments;
	private  java.lang.String   ifContractNegotiable;
	private  java.lang.String   comments;
    private	 java.lang.String 	platformId;
    private	 java.lang.String 	platformName;
    private	 java.lang.String 	taskName;
    private	 java.lang.String 	selContractId;
	private  java.util.Collection allPlatforms;
	private  java.util.Collection allAmendments;
    private  java.util.Collection contractAmendments;
    private  java.util.Collection allianceAvailableAmendments;
	private	 java.lang.String []  amendmentIds;
    private  java.lang.String []  allianceContractAmendmentIds;

	private	 java.lang.String 	amendmentTitle;
	private	 java.lang.String 	amendmentVersion;
	private	 java.lang.String 	amendmentStatus;
	private	 java.lang.String	amendmentExpiryDate;
	private  FormFile			amendmentDocument;
	private  java.lang.String   amendmentDocumentFileName;
	private  java.lang.String   amendmentDocumentContentType;
    private  java.lang.String   ifAmendmentNegotiable;
    private  java.lang.String   amendmentComments;

    private  java.lang.String contractHtmlDocumentFileName;
    private  java.lang.String clickThroughContract;

    public String getContractStatus (String key) {
		
		String retString = "";

		HashMap contractStatusHashMap = new HashMap();		
		contractStatusHashMap.put("A", "Active");
		contractStatusHashMap.put("H", "On Hold");
		contractStatusHashMap.put("E", "Expired");

		if (contractStatusHashMap.containsKey(key))
		{
			retString = (String) contractStatusHashMap.get(key);
		}

		return retString;
	}


	public  java.lang.String  getAllianceContractId() {
        return this.allianceContractId;
    }

    public void setAllianceContractId( java.lang.String  allianceContractId) {
        this.allianceContractId = allianceContractId;
    }

	public  java.lang.String  getContractId() {
        return this.contractId;
    }

    public void setContractId( java.lang.String  contractId) {
        this.contractId = contractId;
    }

	public  java.lang.String  getAllianceId() {
        return this.allianceId;
    }

    public void setAllianceId( java.lang.String  allianceId) {
        this.allianceId = allianceId;
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
        this.contractVersion = contractVersion;;
    }

	public java.lang.String getContractStatus() {
        return this.contractStatus;
    }

    public void setContractStatus(java.lang.String contractStatus) {
        this.contractStatus = contractStatus;
    }

	public  java.lang.String getContractExpiryDate() {
        return this.contractExpiryDate;
    }

    public void setContractExpiryDate( java.lang.String contractExpiryDate) {
        this.contractExpiryDate = contractExpiryDate;
    }

	public FormFile getContractDocument() {
        return this.contractDocument;
    }

    public void setContractDocument(FormFile contractDocument) {
        this.contractDocument = contractDocument;
    }

	public  java.lang.String  getContractDocumentFileName() {
        return this.contractDocumentFileName;
    }

    public void setContractDocumentFileName( java.lang.String  contractDocumentFileName) {
        this.contractDocumentFileName = contractDocumentFileName;
    }

	public  java.lang.String  getContractDocumentContentType() {
        return this.contractDocumentContentType;
    }

    public void setContractDocumentContentType( java.lang.String  contractDocumentContentType) {
        this.contractDocumentContentType = contractDocumentContentType;
    }

	public  java.lang.String  getIfContractNegotiable() {
        return this.ifContractNegotiable;
    }

    public void setIfContractNegotiable( java.lang.String  ifContractNegotiable) {
        this.ifContractNegotiable = ifContractNegotiable;
    }

	public  java.lang.String  getComments() {
        return this.comments;
    }

    public void setComments( java.lang.String  comments) {
        this.comments = comments;
    }

	public  java.lang.String  getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId( java.lang.String  platformId) {
        this.platformId = platformId;
    }

	public  java.lang.String  getPlatformName() {
        return this.platformName;
    }

    public void setPlatformName( java.lang.String  platformName) {
        this.platformName = platformName;
    }

	public  java.lang.String  getTaskName() {
        return this.taskName;
    }

    public void setTaskName( java.lang.String  taskName) {
        this.taskName = taskName;
    } 

	public  java.lang.String  getNumContractAmendments() {
        return this.numContractAmendments;
    }

    public void setNumContractAmendments( java.lang.String  numContractAmendments) {
        this.numContractAmendments = numContractAmendments;
    }

	public  java.util.Collection  getAllPlatforms() {
        return this.allPlatforms;
    }

    public void setAllPlatforms( java.util.Collection  allPlatforms) {
        this.allPlatforms = allPlatforms;
    }

	public  java.util.Collection  getAllAmendments() {
        return this.allAmendments;
    }

    public void setAllAmendments( java.util.Collection  allAmendments) {
        this.allAmendments = allAmendments;
    }

	public  java.util.Collection  getContractAmendments() {
        return this.contractAmendments;
    }

    public void setContractAmendments( java.util.Collection  contractAmendments) {
        this.contractAmendments = contractAmendments;
    }

	public  java.util.Collection  getAllianceAvailableAmendments() {
        return this.allianceAvailableAmendments;
    }

    public void setAllianceAvailableAmendments( java.util.Collection  allianceAvailableAmendments) {
        this.allianceAvailableAmendments = allianceAvailableAmendments;
    }

	public  java.lang.String []  getAmendmentIds() {
        return this.amendmentIds;
    }

    public void setAmendmentIds( java.lang.String [] amendmentIds) {
        this.amendmentIds = amendmentIds;
    }

	public  java.lang.String []  getAllianceContractAmendmentIds() {
        return this.allianceContractAmendmentIds;
    }

    public void setAllianceContractAmendmentIds( java.lang.String [] allianceContractAmendmentIds) {
        this.allianceContractAmendmentIds = allianceContractAmendmentIds;
    }

	public  java.lang.String  getSelContractId() {
        return this.selContractId;
    }

    public void setSelContractId( java.lang.String  selContractId) {
        this.selContractId = selContractId;
    }



	public  java.lang.String  getAmendmentTitle() {
        return this.amendmentTitle;
    }

    public void setAmendmentTitle( java.lang.String  amendmentTitle) {
        this.amendmentTitle = amendmentTitle;
    }

	public  java.lang.String  getAmendmentVersion() {
        return this.amendmentVersion;
    }

    public void setAmendmentVersion( java.lang.String  amendmentVersion) {
        this.amendmentVersion = amendmentVersion;;
    }

	public java.lang.String getAmendmentStatus() {
        return this.amendmentStatus;
    }

    public void setAmendmentStatus(java.lang.String amendmentStatus) {
        this.amendmentStatus = amendmentStatus;
    }

	public  java.lang.String getAmendmentExpiryDate() {
        return this.amendmentExpiryDate;
    }

    public void setAmendmentExpiryDate( java.lang.String amendmentExpiryDate) {
        this.amendmentExpiryDate = amendmentExpiryDate;
    }


	public FormFile getAmendmentDocument() {
        return this.amendmentDocument;
    }

    public void setAmendmentDocument(FormFile amendmentDocument) {
        this.amendmentDocument = amendmentDocument;
    }

	public  java.lang.String  getAmendmentDocumentFileName() {
        return this.amendmentDocumentFileName;
    }

    public void setAmendmentDocumentFileName( java.lang.String  amendmentDocumentFileName) {
        this.amendmentDocumentFileName = amendmentDocumentFileName;
    }

	public  java.lang.String  getAmendmentDocumentContentType() {
        return this.amendmentDocumentContentType;
    }

    public void setAmendmentDocumentContentType( java.lang.String  amendmentDocumentContentType) {
        this.amendmentDocumentContentType = amendmentDocumentContentType;
    }

	public  java.lang.String  getIfAmendmentNegotiable() {
        return this.ifAmendmentNegotiable;
    }

    public void setIfAmendmentNegotiable( java.lang.String  ifAmendmentNegotiable) {
        this.ifAmendmentNegotiable = ifAmendmentNegotiable;
    }

	public  java.lang.String  getAmendmentComments() {
        return this.amendmentComments;
    }

    public void setAmendmentComments( java.lang.String  amendmentComments) {
        this.amendmentComments = amendmentComments;
    }

    public String getContractHtmlDocumentFileName() {
        return contractHtmlDocumentFileName;
    }

    public void setContractHtmlDocumentFileName(String contractHtmlDocumentFileName) {
        this.contractHtmlDocumentFileName = contractHtmlDocumentFileName;
    }

    public String getClickThroughContract() {
        return clickThroughContract;
    }

    public void setClickThroughContract(String clickThroughContract) {
        this.clickThroughContract = clickThroughContract;
    }

    public void reset (ActionMapping mapping, HttpServletRequest request)
	{
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		Object [] userValues = null;
		Collection AimsContract = null;
        Collection AimsContractAmendments = null;
		Long contract_id = null;
        try
        {
            this.setAllPlatforms(AimsPlatformManager.getPlatforms());
            this.setAllAmendments(ContractsManager.getAmendments());
        }
        catch (HibernateException he)
        {
            log.debug("An exception occured in Contract Form!");
        }

		if (task_name.equalsIgnoreCase("createForm") )
		{
			this.setContractStatus("A");
			this.setIfContractNegotiable("N");
		}
		
		if (task_name.equalsIgnoreCase("editForm"))
		{
			contract_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("contract_id"), "0"));			
			try
			{
			    AimsContract = ContractsManager.getContract(contract_id);               
                AimsContractAmendments = ContractsManager.getContractAmendmentIds(contract_id);                
			}
			catch (HibernateException he)
			{
				log.debug("An exception occured in Contract Form!");
			}
			for (Iterator iter = AimsContract.iterator(); iter.hasNext();) 
			{
				 userValues  = (Object []) iter.next();

				 this.setContractId( ((Long) userValues[0]).toString());
				 this.setContractTitle((String) userValues[1]);
				 this.setContractVersion((String) userValues[2]);
				 this.setContractDocumentFileName((String) userValues[3]);	
				 this.setContractStatus((String) userValues[4]);
				 this.setIfContractNegotiable((String) userValues[5]);
				 this.setComments((String) userValues[6]);
				 this.setContractExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
				 this.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));
                 this.setPlatformName(StringFuncs.NullValueReplacement((String) userValues[9]));

                 this.setContractHtmlDocumentFileName((String) userValues[10]);
                 this.setClickThroughContract((String) userValues[11]);
            }

            if (AimsContractAmendments != null)
            {
                ArrayList amendmentArrayList = new ArrayList();

                for (Iterator iter = AimsContractAmendments.iterator(); iter.hasNext();) 
                {
                    amendmentArrayList.add(((Long)iter.next()).toString());                    
                }

                String [] amendmentArr = StringFuncs.ConvertListToStringArray(amendmentArrayList);
                this.setAmendmentIds(amendmentArr);
            }


		}

		if (task_name.equalsIgnoreCase("editViewForm") || task_name.equalsIgnoreCase("allianceViewForm"))
		{
			contract_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("contract_id"), "0"));			
			try
			{
			    AimsContract = ContractsManager.getContract(contract_id); 
                AimsContractAmendments = ContractsManager.getContractAmendmentDetails(contract_id);                
			}
			catch (HibernateException he)
			{
				log.debug("An exception occured in Contract Form!");
			}
			for (Iterator iter = AimsContract.iterator(); iter.hasNext();) 
			{
				 userValues  = (Object []) iter.next();

				 this.setContractId( ((Long) userValues[0]).toString());
				 this.setContractTitle((String) userValues[1]);
				 this.setContractVersion((String) userValues[2]);
				 this.setContractDocumentFileName((String) userValues[3]);	
				 this.setContractStatus((String) userValues[4]);
				 this.setIfContractNegotiable((String) userValues[5]);
				 this.setComments((String) userValues[6]);
				 this.setContractExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
				 this.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));
                 this.setPlatformName(StringFuncs.NullValueReplacement((String) userValues[9]));

                 this.setContractHtmlDocumentFileName((String) userValues[10]);
                 this.setClickThroughContract((String) userValues[11]);                 
            }

                 this.setContractAmendments(AimsContractAmendments);

		}
	}

    public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	
    {			
        HttpSession session = request.getSession();
        ActionErrors errors	=	new	ActionErrors();
        Long alliance_id = alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        Collection AvailableAllianceContracts = null;

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
        log.debug(" ******** task_name *********** " + task_name);

        if (task_name.equalsIgnoreCase("saveCreate") )
        {
        
            if (this.isBlankString(this.getSelContractId()))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.ContractOfferForm.selContractId")); 
            }

			try
			{
			AvailableAllianceContracts = ContractOfferManager.getAllianceAvailableContracts(alliance_id, AimsConstants.CONTRACT_STATUS_ACTIVE);
			request.setAttribute("AvailableAllianceContracts", AvailableAllianceContracts);
			}
			
			catch (HibernateException he)
			{
				log.debug("A hibernate exception occured!");
			} 
        }
        

        if (task_name.equalsIgnoreCase("edit") || task_name.equalsIgnoreCase("create"))
        {
            //mapping.setInput("/alliance/vzwAllianceContractsSaveView.jsp");
        
            if ( 
                    (!this.isBlankString(this.getAmendmentTitle())) || 
                    (!this.isBlankString(this.getAmendmentVersion())) || 
                    (!this.isBlankString(this.getAmendmentExpiryDate())) || 
                    (this.getAmendmentDocument().getFileSize()>0 ) ||
                    (!this.isBlankString(this.getAmendmentDocumentFileName()))
                )
            {            
                if (this.isBlankString(this.getAmendmentTitle()))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.AmendmentForm.amendmentTitle")); 
                }

                if (this.isBlankString(this.getAmendmentVersion()))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.AmendmentForm.amendmentVersion")); 
                }

                if (this.isBlankString(this.getAmendmentStatus()))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.AmendmentForm.amendmentStatus")); 
                }

                if (!this.isDate(this.getAmendmentExpiryDate()))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.valid.AmendmentForm.amendmentExpiryDate"));
                }
                
                if ( ((this.getAmendmentDocument() == null) || !(this.getAmendmentDocument().getFileSize()>0)) && (this.isBlankString(this.getAmendmentDocumentFileName())) )
                {				
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.AmendmentForm.amendmentDocument"));
                }
            }

        }       
    
   
        return errors;
    }
    
}

