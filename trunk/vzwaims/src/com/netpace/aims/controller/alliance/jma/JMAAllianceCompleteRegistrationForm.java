package com.netpace.aims.controller.alliance.jma;

import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.alliance.AllianceBusInfoManager;
import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.alliance.JMAAllianceRegistrationManager;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.controller.alliance.AllianceRegistrationForm;
import com.netpace.aims.controller.application.JMAApplicationHelper;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="JMAAllianceCompleteRegistrationForm"
 * @author aqureshi
 *
 */
public class JMAAllianceCompleteRegistrationForm extends BaseValidatorForm {

	static Logger log = Logger.getLogger(JMAAllianceCompleteRegistrationForm.class.getName());
	 /**
	 * JMA Complete registration fields
	 */
    

	private java.lang.Long jmaAllianceId;
	private String status;
    private String shortDescription;
    private String faq;
    private String reasonOfReleation;
    private String contractAgreements;
    private String isOptionToGoWithVZW;
    private String optionToGoWithVZW;
    private String isSalesEngaugementWithVZW;
    private String salesEngaugementWithVZW;
    private String productMenu;
    
    private FormFile winOpportunity;
    private String winOpportunityFileName;
    private String winOpportunityContnetType;
    private java.lang.Long winOpportunityTempFileId;
   
    private String isProductUseVzwVzNt;
    private String isProductCertifiedVZW;
    private String isProductCertifiedODI;
    private String briefDescription;
    private String productInformation;
    private String solutionReside;
    private String isProductRequiredLBS;
    private String isOfferBOBOServices;
    private String isTechincalInfo;
    private String isSalesSupport;
    private String isJmaInfoComplete;
   
    private FormFile productPresentation;
    private String productPresentationFileName;
    private String productPresentationContnetType;
    private java.lang.Long productPresentationTempFileId;
    private Boolean isAccepted;
       
    private Collection allIndustryVerticals;
    private Long[] allianceIndustryVerticals;
    private Long[] allianceTopIndustryVerticals;
    
    private Collection allAllianceWithOtherCarriers;
    private Long[] alliancesWithOtherCarriers;
    private String allianceWithOtherCarriers;
    
    private Collection solutionsReside;
    private String resubmitDescription;
    private String showAcceptRejectButton; 
    
    
    /**
     * Fields for JMA Alliance 24X7 Support 
     */
    private String techEmail;
    private String techPassword;
    private String techFirstName;
    private String techLastName;
    private String techTitle;
    private String techPhone;
    private String techMobile;
    private String techFax;
    
    /**
     * Fields for JMA alliance full sale support
     */
    private String salesEmail;
    private String salesPassword;
    private String salesFirstName;
    private String salesLastName;
    private String salesTitle;
    private String salesPhone;
    private String salesMobile;
    private String salesFax;
    
  
    
    
	public String getShortDescription() {
		return shortDescription;
	}
	public String getFaq() {
		return faq;
	}
	public String getReasonOfReleation() {
		return reasonOfReleation;
	}
	public String getContractAgreements() {
		return contractAgreements;
	}
	public String getIsOptionToGoWithVZW() {
		return isOptionToGoWithVZW;
	}
	public String getOptionToGoWithVZW() {
		return optionToGoWithVZW;
	}
	public String getIsSalesEngaugementWithVZW() {
		return isSalesEngaugementWithVZW;
	}
	public String getSalesEngaugementWithVZW() {
		return salesEngaugementWithVZW;
	}
	public String getProductMenu() {
		return productMenu;
	}
	public FormFile getWinOpportunity() {
		return winOpportunity;
	}
	public String getWinOpportunityFileName() {
		return winOpportunityFileName;
	}
	public String getWinOpportunityContnetType() {
		return winOpportunityContnetType;
	}
	public String getIsProductUseVzwVzNt() {
		return isProductUseVzwVzNt;
	}
	public String getIsProductCertifiedVZW() {
		return isProductCertifiedVZW;
	}
	public String getIsProductCertifiedODI() {
		return isProductCertifiedODI;
	}
	public String getBriefDescription() {
		return briefDescription;
	}
	public String getProductInformation() {
		return productInformation;
	}
	public String getSolutionReside() {
		return solutionReside;
	}
	public String getIsProductRequiredLBS() {
		return isProductRequiredLBS;
	}
	public String getIsOfferBOBOServices() {
		return isOfferBOBOServices;
	}
	public String getIsTechincalInfo() {
		return isTechincalInfo;
	}
	public String getIsSalesSupport() {
		return isSalesSupport;
	}
	public String getTechEmail() {
		return techEmail;
	}
	public String getTechPassword() {
		return techPassword;
	}
	public String getTechFirstName() {
		return techFirstName;
	}
	public String getTechLastName() {
		return techLastName;
	}
	public String getTechTitle() {
		return techTitle;
	}
	public String getTechPhone() {
		return techPhone;
	}
	public String getTechMobile() {
		return techMobile;
	}
	public String getTechFax() {
		return techFax;
	}
	public String getSalesEmail() {
		return salesEmail;
	}
	public String getSalesPassword() {
		return salesPassword;
	}
	public String getSalesFirstName() {
		return salesFirstName;
	}
	public String getSalesLastName() {
		return salesLastName;
	}
	public String getSalesTitle() {
		return salesTitle;
	}
	public String getSalesPhone() {
		return salesPhone;
	}
	public String getSalesMobile() {
		return salesMobile;
	}
	public String getSalesFax() {
		return salesFax;
	}
	public FormFile getProductPresentation() {
		return productPresentation;
	}
	public String getProductPresentationFileName() {
		return productPresentationFileName;
	}
	public String getProductPresentationContnetType() {
		return productPresentationContnetType;
	}
	public Boolean getIsAccepted() {
		return isAccepted;
	}	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public void setFaq(String faq) {
		this.faq = faq;
	}
	public void setReasonOfReleation(String reasonOfReleation) {
		this.reasonOfReleation = reasonOfReleation;
	}
	public void setContractAgreements(String contractAgreements) {
		this.contractAgreements = contractAgreements;
	}
	public void setIsOptionToGoWithVZW(String isOptionToGoWithVZW) {
		this.isOptionToGoWithVZW = isOptionToGoWithVZW;
	}
	public void setOptionToGoWithVZW(String optionToGoWithVZW) {
		this.optionToGoWithVZW = optionToGoWithVZW;
	}
	public void setIsSalesEngaugementWithVZW(String isSalesEngaugementWithVZW) {
		this.isSalesEngaugementWithVZW = isSalesEngaugementWithVZW;
	}
	public void setSalesEngaugementWithVZW(String salesEngaugementWithVZW) {
		this.salesEngaugementWithVZW = salesEngaugementWithVZW;
	}
	public void setProductMenu(String productMenu) {
		this.productMenu = productMenu;
	}
	public void setWinOpportunity(FormFile winOpportunity) {
		this.winOpportunity = winOpportunity;
	}
	public void setWinOpportunityFileName(String winOpportunityFileName) {
		this.winOpportunityFileName = winOpportunityFileName;
	}
	public void setWinOpportunityContnetType(String winOpportunityContnetType) {
		this.winOpportunityContnetType = winOpportunityContnetType;
	}
	public void setIsProductUseVzwVzNt(String isProductUseVzwVzNt) {
		this.isProductUseVzwVzNt = isProductUseVzwVzNt;
	}
	public void setIsProductCertifiedVZW(String isProductCertifiedVZW) {
		this.isProductCertifiedVZW = isProductCertifiedVZW;
	}
	public void setIsProductCertifiedODI(String isProductCertifiedODI) {
		this.isProductCertifiedODI = isProductCertifiedODI;
	}
	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}
	public void setProductInformation(String productInformation) {
		this.productInformation = productInformation;
	}
	public void setSolutionReside(String solutionReside) {
		this.solutionReside = solutionReside;
	}
	public void setIsProductRequiredLBS(String isProductRequiredLBS) {
		this.isProductRequiredLBS = isProductRequiredLBS;
	}
	public void setIsOfferBOBOServices(String isOfferBOBOServices) {
		this.isOfferBOBOServices = isOfferBOBOServices;
	}
	public void setIsTechincalInfo(String isTechincalInfo) {
		this.isTechincalInfo = isTechincalInfo;
	}
	public void setIsSalesSupport(String isSalesSupport) {
		this.isSalesSupport = isSalesSupport;
	}
	public void setTechEmail(String techEmail) {
		this.techEmail = techEmail;
	}
	public void setTechPassword(String techPassword) {
		this.techPassword = techPassword;
	}
	public void setTechFirstName(String techFirstName) {
		this.techFirstName = techFirstName;
	}
	public void setTechLastName(String techLastName) {
		this.techLastName = techLastName;
	}
	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}
	public void setTechPhone(String techPhone) {
		this.techPhone = techPhone;
	}
	public void setTechMobile(String techMobile) {
		this.techMobile = techMobile;
	}
	public void setTechFax(String techFax) {
		this.techFax = techFax;
	}
	public void setSalesEmail(String salesEmail) {
		this.salesEmail = salesEmail;
	}
	public void setSalesPassword(String salesPassword) {
		this.salesPassword = salesPassword;
	}
	public void setSalesFirstName(String salesFirstName) {
		this.salesFirstName = salesFirstName;
	}
	public void setSalesLastName(String salesLastName) {
		this.salesLastName = salesLastName;
	}
	public void setSalesTitle(String salesTitle) {
		this.salesTitle = salesTitle;
	}
	public void setSalesPhone(String salesPhone) {
		this.salesPhone = salesPhone;
	}
	public void setSalesMobile(String salesMobile) {
		this.salesMobile = salesMobile;
	}
	public void setSalesFax(String salesFax) {
		this.salesFax = salesFax;
	}
	public void setProductPresentation(FormFile productPresentation) {
		this.productPresentation = productPresentation;
	}
	public void setProductPresentationFileName(String productPresentationFileName) {
		this.productPresentationFileName = productPresentationFileName;
	}
	public void setProductPresentationContnetType(
			String productPresentationContnetType) {
		this.productPresentationContnetType = productPresentationContnetType;
	}
	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	public Long[] getAllianceIndustryVerticals() {
		return allianceIndustryVerticals;
	}
	public void setAllianceIndustryVerticals(Long[] allianceIndustryVerticals) {
		this.allianceIndustryVerticals = allianceIndustryVerticals;
	}
	public Long[] getAllianceTopIndustryVerticals() {
		return allianceTopIndustryVerticals;
	}
	public void setAllianceTopIndustryVerticals(Long[] allianceTopIndustryVerticals) {
		this.allianceTopIndustryVerticals = allianceTopIndustryVerticals;
	}
	public Collection getAllIndustryVerticals() {
		return allIndustryVerticals;
	}
	public void setAllIndustryVerticals(Collection allIndustryVerticals) {
		this.allIndustryVerticals = allIndustryVerticals;
	}
	public Collection getAllAllianceWithOtherCarriers() {
		return allAllianceWithOtherCarriers;
	}
	public void setAllAllianceWithOtherCarriers(
			Collection allAllianceWithOtherCarriers) {
		this.allAllianceWithOtherCarriers = allAllianceWithOtherCarriers;
	}
	public Long[] getAlliancesWithOtherCarriers() {
		return alliancesWithOtherCarriers;
	}
	public void setAlliancesWithOtherCarriers(Long[] alliancesWithOtherCarriers) {
		this.alliancesWithOtherCarriers = alliancesWithOtherCarriers;
	}
	public String getAllianceWithOtherCarriers() {
		return allianceWithOtherCarriers;
	}
	public void setAllianceWithOtherCarriers(String allianceWithOtherCarriers) {
		this.allianceWithOtherCarriers = allianceWithOtherCarriers;
	}
	public java.lang.Long getProductPresentationTempFileId() {
		return productPresentationTempFileId;
	}
	public void setProductPresentationTempFileId(
			java.lang.Long productPresentationTempFileId) {
		this.productPresentationTempFileId = productPresentationTempFileId;
	}
	
	
	public java.lang.Long getWinOpportunityTempFileId() {
		return winOpportunityTempFileId;
	}
	public void setWinOpportunityTempFileId(java.lang.Long winOpportunityTempFileId) {
		this.winOpportunityTempFileId = winOpportunityTempFileId;
	}
	private Collection getAllIndustryVerticalsList()
    {
        Collection aimsIndVetricalList = null;
        try {
        	aimsIndVetricalList = JMAAllianceRegistrationManager.getAllIndustyVerticals();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return aimsIndVetricalList;
    }
	
	 private Collection getAllAimsCarriers()
	    {
	        Collection allAimsCarriersList = null;
	        try {
	            allAimsCarriersList = AllianceCompInfoManager.getAllAimsCarriers();
	        } catch (HibernateException e) {
	            e.printStackTrace();
	        }
	        return allAimsCarriersList;
	    }
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		this.setAllIndustryVerticals(this.getAllIndustryVerticalsList());
		this.setAllAllianceWithOtherCarriers(this.getAllAimsCarriers());
		
		try {
			solutionsReside=AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_ALLIANCE_SOL_RESIDES);
		} catch (HibernateException e) {
			
			e.printStackTrace();
		}
	}
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
		System.out.println("Validate JMA complete info formform");
        ActionErrors errors = new ActionErrors();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String taskname = request.getParameter("task");
        TempFile tempFile = null;
        String fileSize = "10485760"; //set max file size to 10 mb
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        
        //Loading files
        try
        {
            //Win opportunities
        	 if ((this.winOpportunity != null)
                     && (this.winOpportunity.getFileSize() > 0)
                     && !(this.isValidFileSize(fileSize, this.winOpportunity.getFileSize())))
                 {
                     errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                     this.winOpportunity.destroy();
                 }
                 else
                 {
                     tempFile = TempFilesManager.saveTempFile(this.winOpportunity, sessionId, currUserId);
                     if (tempFile != null)
                     {
                         this.winOpportunityTempFileId = tempFile.getFileId();
                         this.winOpportunityFileName = tempFile.getFileName();
                     }
                 }
        	//Product presentation
            if ((this.productPresentation != null)
                && (this.productPresentation.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.productPresentation.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                this.productPresentation.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.productPresentation, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.productPresentationTempFileId = tempFile.getFileId();
                    this.productPresentationFileName = tempFile.getFileName();
                }
            }

        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN validation() of AllianceBusInfoForm");
        }
        //Validate fields for DB
        this.validateToDBFields(errors);
        
        if(taskname!=null && (taskname.equals("submit") || taskname.equals("resubmit")))
        {
	        if (this.isBlankString(this.shortDescription))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.companyShortDescription"));
	        //else if(!JMAApplicationHelper.wordCountCheck(shortDescription, 200))
	        //	  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.companyShortDescription.wordCount"));
		       
	        if (this.isBlankString(this.faq))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.faq"));
	        if (this.allianceIndustryVerticals==null || this.allianceIndustryVerticals.length==0)
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.industryVerticals"));
	        if (this.isBlankString(this.reasonOfReleation))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.reasonOfRelationWithVZW"));
	        if (this.isBlankString(this.contractAgreements))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.contractualAgreements"));
	        if (this.alliancesWithOtherCarriers==null || this.alliancesWithOtherCarriers.length==0)
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.allianceWithOtherCarriers"));
	        if (this.isOptionToGoWithVZW==null )
		        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.optionToGoWithVerizon"));
	        if (this.productMenu == null || productMenu.length()==0)
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.productMenu"));
	 	   
	        if (this.isSalesEngaugementWithVZW==null)
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesEngagements"));
	        else if (this.isSalesEngaugementWithVZW!=null && this.isSalesEngaugementWithVZW.equals("Y"))
		        	if (this.isBlankString(salesEngaugementWithVZW))
		        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesEngagements"));
	        
	        //producr menu
	        
	        if (((this.winOpportunity == null) || !(this.winOpportunity.getFileSize() > 0))
	                && (this.isBlankString(this.winOpportunityFileName)))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.jam.winWinOpportunities"));
	        else
	        {
	        	if(!isValidFileType(this.winOpportunityFileName, AimsConstants.FILE_TYPE_DOC_AND_PRESENTATION))
	        	{
	        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.jma_alliance_registration.winOpportuinty.file.type"));
	        	}
	        }
	        
	        //if file exist
	        if (!(((this.productPresentation== null) || !(this.productPresentation.getFileSize() > 0))
	                && (this.isBlankString(this.productPresentationFileName))) ){
	        	
	        	if(!isValidFileType(this.productPresentationFileName, AimsConstants.FILE_TYPE_PRESENTATION_MS_APPLE_ADOBE))
	        	{
	        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.jma_alliance_registration.productPresentation.file.type"));
	        	}
		        	
	        }
	        
	        	
	        
	        if (this.isBlankString(this.isProductUseVzwVzNt))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.isProductuseVzwVznt"));
	        if (this.isBlankString(this.isProductCertifiedVZW))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.productCertified"));
	        if (this.isBlankString(this.isProductCertifiedODI))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.certifiedODI"));
	        if (this.isBlankString(this.isProductRequiredLBS))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.requiredLBS"));
	        if (this.isBlankString(this.isOfferBOBOServices))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.offerBOBO"));
	      
	        
	        //if ((this.isAccepted == null) || (!this.isAccepted.booleanValue()))
	        //    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.isAccepted"));

	        if(taskname.equals("resubmit"))
	        {
	        	if (this.isBlankString(this.resubmitDescription))
		            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.resubmitDescription"));
	        }
        }
        
        //validation for 24X7 Technical support
        if(isTechincalInfo!=null && isTechincalInfo.equals("Y"))
        {
	        if (this.isBlankString(this.techEmail))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techEmail"));
	        //if (this.isBlankString(this.techPassword))
	        //    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techPassword"));
	        if (this.isBlankString(this.techFirstName))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techFirstName"));
	        if (this.isBlankString(this.techLastName))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techLastName"));
	        if (this.isBlankString(this.techTitle))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techTitle"));
	        if (this.isBlankString(this.techPhone))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techPhone"));
        }
       
        //validation for 24X7 Sales support
        if(isSalesSupport!=null && isSalesSupport.equals("Y"))
        {
	        if (this.isBlankString(this.salesEmail))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesEmail"));
	        //if (this.isBlankString(this.salesPassword))
	        //    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesPassword"));
	        if (this.isBlankString(this.salesFirstName))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesFirstName"));
	        if (this.isBlankString(this.salesLastName))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesLastName"));
	        if (this.isBlankString(this.salesTitle))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesTitle"));
	        if (this.isBlankString(this.salesPhone))
	        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.salesPhone"));
        }
        
        return errors;
  
    }
	
	public void validateToDBFields(ActionErrors errors)
	{
		 if ((this.shortDescription != null) && (this.shortDescription.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.companyShortDescription"));

		 if ((this.faq != null) && (this.faq.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.faq"));

		 if ((this.reasonOfReleation != null) && (this.reasonOfReleation.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.reasonOfRelationWithVZW"));

		 if ((this.contractAgreements!= null) && (this.contractAgreements.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.contractualAgreements"));

		 if ((this.allianceWithOtherCarriers!= null) && (this.allianceWithOtherCarriers.length() > 200))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.allianceWithOtherCarriers"));

		 if(isOptionToGoWithVZW!=null && isOptionToGoWithVZW.equals("Y"))
			 if ((this.optionToGoWithVZW!= null) && (this.optionToGoWithVZW.length() > 1000))
		            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.optionToGoWithVerizon"));

		 if(isSalesEngaugementWithVZW!=null && isSalesEngaugementWithVZW.equals("Y"))
			 if ((this.salesEngaugementWithVZW!= null) && (this.salesEngaugementWithVZW.length() > 1000))
		            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.salesEngagements"));

		 if ((this.productMenu!= null) && (this.productMenu.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.productMenu"));

		 if ((this.briefDescription!= null) && (this.briefDescription.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.briefDescription"));

		 if ((this.productInformation!= null) && (this.productInformation.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.productInfo"));
		 
		 if ((this.resubmitDescription!= null) && (this.resubmitDescription.length() > 1000))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.length.resubmitDescription"));

	}
	public String getIsJmaInfoComplete() {
		return isJmaInfoComplete;
	}
	public void setIsJmaInfoComplete(String isJmaInfoComplete) {
		this.isJmaInfoComplete = isJmaInfoComplete;
	}
	
	public java.lang.Long getJmaAllianceId() {
		return jmaAllianceId;
	}
	public void setJmaAllianceId(java.lang.Long jmaAllianceId) {
		this.jmaAllianceId = jmaAllianceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Collection getSolutionsReside() {
		return solutionsReside;
	}
	public void setSolutionsReside(Collection solutionsReside) {
		this.solutionsReside = solutionsReside;
	}
	public String getResubmitDescription() {
		return resubmitDescription;
	}
	public void setResubmitDescription(String resubmitDescription) {
		this.resubmitDescription = resubmitDescription;
	}
	public String getShowAcceptRejectButton() {
		return showAcceptRejectButton;
	}
	public void setShowAcceptRejectButton(String showAcceptRejectButton) {
		this.showAcceptRejectButton = showAcceptRejectButton;
	}
	
	
}
