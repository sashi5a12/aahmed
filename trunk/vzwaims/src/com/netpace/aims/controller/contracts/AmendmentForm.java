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
 * @struts.form name="AmendmentForm"
 */
public class AmendmentForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(AmendmentForm.class.getName());
   
	private	 java.lang.String 	amendmentId;
	private	 java.lang.String 	amendmentTitle;
	private	 java.lang.String 	amendmentVersion;
	private	 java.lang.String 	amendmentStatus;
	private	 java.lang.String	amendmentExpiryDate;
    private	 java.lang.String 	platformId;
    private	 java.lang.String 	platformName;
	private  FormFile			amendmentDocument;
	private  java.lang.String   amendmentDocumentFileName;
	private  java.lang.String   amendmentDocumentContentType;
	private  java.lang.String   ifAmendmentNegotiable;
	private  java.lang.String   comments;
    private	 java.lang.String 	taskName;
    
    private  java.lang.String   acceptDeclineFirstName;
    private  java.lang.String   acceptDeclineLastName;

    private  java.util.Collection allPlatforms;
    private  java.util.Collection amendmentContracts;
    
	private	 java.lang.String	amendmentOfferDate;
    

	public String getAmendmentStatus (String key) {
		
		String retString = "";

		HashMap amendmentStatusHashMap = new HashMap();		
		amendmentStatusHashMap.put("O", "Offered");
		amendmentStatusHashMap.put("A", "Accepted");
		amendmentStatusHashMap.put("R", "Rejected");

		if (amendmentStatusHashMap.containsKey(key))
		{
			retString = (String) amendmentStatusHashMap.get(key);
		}

		return retString;
	}

	public  java.lang.String  getAmendmentId() {
        return this.amendmentId;
    }

    public void setAmendmentId( java.lang.String  amendmentId) {
        this.amendmentId = amendmentId;
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

	public  java.lang.String  getComments() {
        return this.comments;
    }

    public void setComments( java.lang.String  comments) {
        this.comments = comments;
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

	public  java.util.Collection  getAllPlatforms() {
        return this.allPlatforms;
    }

    public void setAllPlatforms( java.util.Collection  allPlatforms) {
        this.allPlatforms = allPlatforms;
    }

	public  java.util.Collection  getAmendmentContracts() {
        return this.amendmentContracts;
    }

    public void setAmendmentContracts( java.util.Collection  amendmentContracts) {
        this.amendmentContracts = amendmentContracts;
    }
    
	public java.lang.String getAmendmentOfferDate() {
		return amendmentOfferDate;
	}

	public void setAmendmentOfferDate(java.lang.String amendmentOfferDate) {
		this.amendmentOfferDate = amendmentOfferDate;
	}    

	public void reset (ActionMapping mapping, HttpServletRequest request)
	{
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		Object [] userValues = null;
		Collection AimsAmendment = null;  
        Collection AmendmentContracts = null;
		Long amendment_id = null;

        try
        {
            this.setAllPlatforms(AimsPlatformManager.getPlatforms());            
        }
        catch (HibernateException he)
        {
            log.debug("An exception occured in Contract Form!");
        }

		if (task_name.equalsIgnoreCase("createForm") )
		{
			this.setAmendmentStatus("A");
			this.setIfAmendmentNegotiable("N");
		}

		if (task_name.equalsIgnoreCase("editForm") || task_name.equalsIgnoreCase("editViewForm"))
		{
			amendment_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("amendment_id"), "0"));			
			try
			{
			    AimsAmendment = ContractsManager.getAmendment(amendment_id); 
                AmendmentContracts = AmendmentsManager.getAmendmentContracts(amendment_id);
			}
			catch (HibernateException he)
			{
				log.debug("An exception occured in Amendment Form!");
			}
			for (Iterator iter = AimsAmendment.iterator(); iter.hasNext();) 
			{
				 userValues  = (Object []) iter.next();

				 this.setAmendmentId( ((Long) userValues[0]).toString());
				 this.setAmendmentTitle((String) userValues[1]);
				 this.setAmendmentVersion((String) userValues[2]);
				 this.setAmendmentDocumentFileName((String) userValues[3]);
                 this.setAmendmentStatus(AimsUtils.getContractStatus((String) userValues[4]));
				 this.setIfAmendmentNegotiable((String) userValues[5]);
				 this.setComments((String) userValues[6]);
				 this.setAmendmentExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
				 this.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));
			}
                 this.setAmendmentContracts(AmendmentContracts);
		}
	}

    public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	
    {			
        HttpSession session = request.getSession();
        ActionErrors errors	=	new	ActionErrors();
        Long alliance_id = alliance_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("alliance_id"), "0"));
        Collection AvailableAllianceContracts = null;

		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (task_name.equalsIgnoreCase("edit") || task_name.equalsIgnoreCase("create"))
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
        
        return errors;	
    }



}

