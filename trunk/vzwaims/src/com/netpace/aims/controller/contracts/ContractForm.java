package com.netpace.aims.controller.contracts;

import com.netpace.aims.bo.contracts.AmendmentsManager;
import com.netpace.aims.bo.contracts.ContractsManager;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.system.AimsPlatformManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsTypes;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.Utility;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @struts.form name="ContractForm"
 */
public class ContractForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(ContractForm.class.getName());

	private	 java.lang.String 	contractId;
	private	 java.lang.String 	contractTitle;
	private	 java.lang.String 	contractVersion;
	private	 java.lang.String 	contractStatus;
	private	 java.lang.String	contractExpiryDate;
	private	 java.lang.String 	contractPlatformName;
	private  FormFile			contractDocument;
	private  java.lang.String   contractDocumentFileName;
	private  java.lang.String   contractDocumentContentType;
    private  java.lang.Long     contractDocumentTempFileId;
    private  FormFile           contractHtmlDocument;
	private  java.lang.String   contractHtmlDocumentFileName;
	private  java.lang.String   contractHtmlDocumentContentType;
    private  java.lang.Long     contractHtmlDocumentTempFileId;
    private  java.lang.String   ifContractNegotiable;
	private  java.lang.String   comments;
    private	 java.lang.String 	platformId;
    private	 java.lang.String 	platformName;
    private	 java.lang.String 	taskName;
	private  java.util.Collection allPlatforms;
	private  java.util.Collection allAmendments;
    private  java.util.Collection contractAmendments;
    private  java.util.Collection contractAlliances;
    private  java.util.Collection contractAllianceAmendments;
	private	 java.lang.String []  amendmentIds;

	private	 java.lang.String 	amendmentTitle;
	private	 java.lang.String 	amendmentVersion;
	private	 java.lang.String 	amendmentStatus;
	private	 java.lang.String	amendmentExpiryDate;
	private  FormFile			amendmentDocument;
	private  java.lang.String   amendmentDocumentFileName;
	private  java.lang.String   amendmentDocumentContentType;
    private  java.lang.String   ifAmendmentNegotiable;
    private  java.lang.String   amendmentComments;
    private  java.lang.String isBoboContract;

    private  java.lang.String   clickThroughContract;

    private java.lang.String filterText;
    private java.lang.String filterField;

    private final String[][] filterStatus= {{AimsConstants.FILTER_SHOW_ALL,AimsConstants.FILTER_LABEL_SHOW_ALL},
    										{AimsConstants.CONTRACT_STATUS_ACTIVE,AimsConstants.FILTER_LABEL_ACTIVE},
    										{AimsConstants.CONTRACT_STATUS_EXPIRED,AimsConstants.FILTER_LABEL_EXPIRED}};
    private String[] selectedFilterValue=new String[0];
    
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

    public Long getContractDocumentTempFileId() {
        return contractDocumentTempFileId;
    }

    public void setContractDocumentTempFileId(Long contractDocumentTempFileId) {
        this.contractDocumentTempFileId = contractDocumentTempFileId;
    }

    public FormFile getContractHtmlDocument() {
		return contractHtmlDocument;
	}

	public void setContractHtmlDocument(FormFile contractHtmlDocument) {
		this.contractHtmlDocument = contractHtmlDocument;
	}

	public java.lang.String getContractHtmlDocumentFileName() {
		return contractHtmlDocumentFileName;
	}

	public void setContractHtmlDocumentFileName(
			java.lang.String contractHtmlDocumentFileName) {
		this.contractHtmlDocumentFileName = contractHtmlDocumentFileName;
	}

	public java.lang.String getContractHtmlDocumentContentType() {
		return contractHtmlDocumentContentType;
	}

	public void setContractHtmlDocumentContentType(
			java.lang.String contractHtmlDocumentContentType) {
		this.contractHtmlDocumentContentType = contractHtmlDocumentContentType;
	}

    public void setContractDocumentContentType( java.lang.String  contractDocumentContentType) {
        this.contractDocumentContentType = contractDocumentContentType;
    }

    public Long getContractHtmlDocumentTempFileId() {
        return contractHtmlDocumentTempFileId;
    }

    public void setContractHtmlDocumentTempFileId(Long contractHtmlDocumentTempFileId) {
        this.contractHtmlDocumentTempFileId = contractHtmlDocumentTempFileId;
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

	public  java.util.Collection  getContractAlliances() {
        return this.contractAlliances;
    }

    public void setContractAlliances( java.util.Collection  contractAlliances) {
        this.contractAlliances = contractAlliances;
    }

	public  java.util.Collection  getContractAllianceAmendments() {
        return this.contractAllianceAmendments;
    }

    public void setContractAllianceAmendments( java.util.Collection  contractAllianceAmendments) {
        this.contractAllianceAmendments = contractAllianceAmendments;
    }

	public  java.lang.String []  getAmendmentIds() {
        return this.amendmentIds;
    }

    public void setAmendmentIds( java.lang.String [] amendmentIds) {
        this.amendmentIds = amendmentIds;
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

	public String[][] getFilterStatus() {
		return filterStatus;
	}

	public String[] getSelectedFilterValue() {
		return selectedFilterValue;
	}

	public void setSelectedFilterValue(String[] selectedFilterValue) {
		this.selectedFilterValue = selectedFilterValue;
	}

    public String getClickThroughContract() {
        return clickThroughContract;
    }

    public void setClickThroughContract(String clickThroughContract) {
        this.clickThroughContract = clickThroughContract;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public void reset (ActionMapping mapping, HttpServletRequest request)
	{
		log.debug("ContractForm.reset Strat:");
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		Object [] userValues = null;
		Collection AimsContract = null;
        Collection AimsContractAmendments = null;
        Collection AimsContractAlliances = null;
        Collection AimsContractAllianceAmendments = null;
		Long contract_id = null;
        Long alliance_id = null;
    	selectedFilterValue=new String[]{AimsConstants.CONTRACT_STATUS_ACTIVE};
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
            log.debug("Inside Contract Form editForm (contract_id) => " + contract_id);
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
                this.setContractHtmlDocumentFileName(StringFuncs.NullValueReplacement((String) userValues[10]));
                this.setClickThroughContract((String) userValues[11]);
                this.setIsBoboContract(StringFuncs.NullValueReplacement((String) userValues[12]));

            }
            //set temp file ids to 0
            this.contractDocumentTempFileId = new Long(0);
            this.contractHtmlDocumentTempFileId= new Long(0);

            this.setContractAmendments(AimsContractAmendments);

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
            alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
			try
			{
			    AimsContract = ContractsManager.getContract(contract_id);
                AimsContractAmendments = ContractsManager.getContractAmendmentDetails(contract_id);
                AimsContractAlliances = ContractsManager.getContractAlliances(contract_id);
                AimsContractAllianceAmendments = AmendmentsManager.getAllianceContractAmendmentDetails(alliance_id, contract_id);
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
                                 this.setContractStatus(AimsUtils.getContractStatus((String) userValues[4]));
				 this.setIfContractNegotiable((String) userValues[5]);
				 this.setComments((String) userValues[6]);
				 this.setContractExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
				 this.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));
                 this.setPlatformName(StringFuncs.NullValueReplacement((String) userValues[9]));
                 this.setContractHtmlDocumentFileName(StringFuncs.NullValueReplacement((String) userValues[10]));
                 this.setClickThroughContract((String) userValues[11]);
                 this.setIsBoboContract(StringFuncs.NullValueReplacement((String) userValues[12]));
            }                        


                 this.setContractAmendments(AimsContractAmendments);
                 this.setContractAlliances(AimsContractAlliances);
                 this.setContractAllianceAmendments(AimsContractAllianceAmendments);

                //set temp file ids to 0
                this.contractDocumentTempFileId = new Long(0);
                this.contractHtmlDocumentTempFileId= new Long(0);

                 log.debug("AimsContractAlliances.size()!"  + AimsContractAlliances.size());
		}
		log.debug("ContractForm.reset End:");
	}

    public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)
    {
    	log.debug("ContractForm.validate Start:");

        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);

        String task_name = "";
    	if((this.getTaskName() != null) && (this.getTaskName() != ""))
		{
			if(this.getTaskName().equalsIgnoreCase("createForm"))
			{
				 task_name = "create";
			}
			else if(this.getTaskName().equalsIgnoreCase("editForm"))
			{
				 task_name = "edit";
			}
			else if(this.getTaskName().equalsIgnoreCase("view"))
			{
				 task_name = "view";
			}
		}		
        HttpSession session = request.getSession();
        ActionErrors errors	=	new	ActionErrors();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        Long alliance_id = alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        Collection AvailableAllianceContracts = null;

		//String task_name = this.getTaskName(); // StringFuncs.NullValueReplacement(request.getParameter("task"));


        if (task_name.equalsIgnoreCase("edit") || task_name.equalsIgnoreCase("create"))
        {
            this.saveTempContractFiles(currUser, session.getId(), fileSize, errors);

            if (this.isBlankString(this.getContractTitle()))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.ContractForm.contractTitle"));
            }

            if (this.isBlankString(this.getContractVersion()))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.ContractForm.contractVersion"));
            }

            if (this.isBlankString(this.getContractStatus()))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.ContractForm.contractStatus"));
            }

			if (!this.isDate(this.getContractExpiryDate()))
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.valid.ContractForm.contractExpiryDate"));
			}

			if ( ((this.getContractDocument() == null) || !(this.getContractDocument().getFileSize()>0)) && (this.isBlankString(this.getContractDocumentFileName())) )
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContractForm.contractDocument"));
			}

            if ( ((this.getContractHtmlDocument() == null) || !(this.getContractHtmlDocument().getFileSize()>0)) && (this.isBlankString(this.getContractHtmlDocumentFileName())) )
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContractForm.contractHtmlDocument"));
			}
			else if((this.getContractHtmlDocument() != null) && !isBlankString(this.getContractHtmlDocument().getFileName()) && !isValidFileType(this.getContractHtmlDocument().getFileName(), AimsConstants.FILE_TYPE_HTML_ONLY))
			{
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContractForm.contractHtmlDocument.file.type"));
			}			

			if ( (this.getPlatformId() == null) || (!this.isDropDownSelected(new Long(this.getPlatformId()))) )
			{
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.ContractForm.Platform"));
			}

            if (this.isBlankString(this.clickThroughContract))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.ContractForm.clickThroughContract"));
            }
            else if(!(this.clickThroughContract.equals("Y") || this.clickThroughContract.equals("N"))) {
                //value of click through contract must be Y or N
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.required.ContractForm.clickThroughContract"));
            }

            if (!this.isBlankString(this.comments) && this.comments.length()>500)
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.ContractForm.comments.length"));
            }

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

                if (!this.isBlankString(this.amendmentComments) && this.amendmentComments.length()>500)
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,	new	ActionMessage("error.AmendmentForm.comments.length"));
                }

            }


            try
            {
                this.setAllPlatforms(AimsPlatformManager.getPlatforms());
                this.setAllAmendments(ContractsManager.getAmendments());
            }
            catch (HibernateException he)
            {
                log.debug("An exception occured in Contract Form!");
            }

        }
        
        return errors;
    }

	public java.lang.String getIsBoboContract() {
		return isBoboContract;
	}

	public void setIsBoboContract(java.lang.String isBoboContract) {
		this.isBoboContract = isBoboContract;
	}

    public void saveTempContractFiles(String currUserId, String sessionId, String fileSize, ActionErrors errors) {
        TempFile tempFile = null;
        try {

            if ((this.contractDocument != null) && (this.contractDocument.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.contractDocument.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ContractForm.contractDocument.fileSize.exceeded"));
                this.contractDocument.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.contractDocument, sessionId, currUserId);
                if (tempFile != null) {
                    this.contractDocumentTempFileId = tempFile.getFileId();
                    this.contractDocumentFileName = tempFile.getFileName();
                }
            }

            if ((this.contractHtmlDocument != null) && (this.contractHtmlDocument.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.contractHtmlDocument.getFileSize()))) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ContractForm.contractHTMLDocument.fileSize.exceeded"));
                this.contractHtmlDocument.destroy();
            }
            else {
                tempFile = TempFilesManager.saveTempFile(this.contractHtmlDocument, sessionId, currUserId);
                if (tempFile != null) {
                    this.contractHtmlDocumentTempFileId = tempFile.getFileId();
                    this.contractHtmlDocumentFileName = tempFile.getFileName();
                }
            }

        }//end try
        catch (Exception ex) {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempContractFiles() of ContractForm");
        }
        finally {
            log.debug("Finally called IN saveTempContractFiles() of ContractForm");
        }
    }//end saveTempContractFiles


}

