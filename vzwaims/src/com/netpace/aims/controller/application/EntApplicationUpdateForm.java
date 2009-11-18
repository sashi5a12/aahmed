package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsTypeManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.masters.AimsIndFocusManager;
import com.netpace.aims.model.application.AimsEntAppSolComp;
import com.netpace.aims.model.application.AimsSolutionComponent;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="EntApplicationUpdateForm"
 */
public class EntApplicationUpdateForm extends ApplicationUpdateForm
{

    static Logger log = Logger.getLogger(EntApplicationUpdateForm.class.getName());

    private java.lang.Long allianceSponsor;
    private java.util.Collection allBdsContacts;

    private java.util.Collection allIndustryFocus;
    private java.util.Collection allPlatforms;
    private java.util.Collection allRegions;
    private java.util.Collection allSolutionComponents;
    protected java.util.Collection allCaseStudies;
    private java.lang.Long[] industryFocusId;
    private java.lang.Long[] solutionComponentId;
    private java.lang.Long[] regions;
    private java.lang.Long[] entAppSubCategoryId;

    private String[] targetedMarketRegions;
    private String fortuneCustomers;
    private String customerBenefits;
    private java.lang.String totalEndUsers;
    private java.lang.String noOfUsersAccess;
    private String platformDepMode;
    private String otherIndFocusValue;

    private String custSupportPhone;
    private String custSupportEmail;
    private String custSupportHours;
    protected String csCustomerName;
    protected String csProblemStatement;
    protected String csBusinessBenifit;
    protected String csProductionLaunchDate;
    protected java.lang.Long csTotalEndUsers;
    protected java.lang.Long csNoOfUsersAccess;
    protected java.lang.Long csPlatformId;
    protected String csPlatformName;
    protected java.lang.Long[] csCaseStudyId;

    private FormFile presentationFile;
    private String presentationFileName;
    private java.lang.Long presentationTempFileId;

    protected java.lang.Long[] solCompId;
    protected java.lang.String[] solCompName;
    protected java.lang.String[] solCompDesc;
    
     
    
    
    private java.lang.String isInterestedInBOBO;
    private java.lang.String isInterestedInLBS;
    private FormFile boboLetterOfAuthFile;
    private java.lang.String boboLetterOfAuthFileName;
    private java.lang.String boboLeterOfAuthContentType;
    private java.lang.Long boboLetterOfAuthTempFileId;
    private java.lang.String isBoboAccept;
    private java.lang.String lbsContractFileName;
    private FormFile lbsContractFile;
    private java.lang.String lbsContractContentType;
    private java.lang.Long lbsContractTempFileId;
    private java.lang.String isLbsAccept;
    private java.util.Date acceptDeclineDate;
    private java.lang.Long acceptDelineUserId;
    private java.lang.String createdBy;
    private java.util.Date createdDate;
    private java.util.Date lastUpdatedBy;
    private java.util.Date lastUpdatedDate;
    private java.lang.String isProductExeclusiveToVZW;
    private java.lang.String productExclusiveToVzw;
    private java.lang.String isGoExclusiveWithVZW;
    private java.lang.String isProductUseVzwVzNt;
    private java.lang.String isProductCertifiedVZW;
    private java.lang.String productCerifiedPhase;
    private java.lang.String isProductCertifiedODIProcess;
    private java.lang.String productInformation;
    private java.lang.String isProductRequiedLBS;
    private java.lang.String isOfferBoboServices;
    private java.lang.String briefDescription;
    private java.lang.String solutionReside;
    private java.lang.String additionalInformation;
    private java.lang.String devices;
    private java.lang.String displayTabBOBO="N"; // setting default value
    private java.lang.String displayTabLBS="N"; // setting default value
    private java.lang.String acceptDeclinBoboLbs;
    private java.lang.String resubmitSolution;
    private java.lang.String resubmitSolutionText;
    
    
    private java.util.Date boboPresentDate;
    private java.util.Date boboAcceptDeclineDate;
    private java.lang.String boboAcceptDeclineByEmailId;
    private java.lang.String isLbsAcceptByAlliance;
    private java.util.Date lbsPresentDate;  
    private java.util.Date lbsAcceptDeclineDate;
    private java.lang.String lbsAcceptDeclineByEmailId;
    
    private Collection allProductinfo;
    private Long[] entProductInfo;
    
    private Collection allMarketSegInfo;
    private Long[] entMarketSegInfo;
    private Collection boboContract;
    
    private String applicationStatus;
    
    private java.lang.String isPublished;
    private java.lang.String isFeatured;
    


	public java.lang.String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(java.lang.String isPublished) {
		this.isPublished = isPublished;
	}

	public java.lang.String getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(java.lang.String isFeatured) {
		this.isFeatured = isFeatured;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Collection getBoboContract() {
		return boboContract;
	}

	public void setBoboContract(Collection boboContract) {
		this.boboContract = boboContract;
	}

	public java.lang.String getIsInterestedInBOBO() {
		return isInterestedInBOBO;
	}

	public void setIsInterestedInBOBO(java.lang.String isInterestedInBOBO) {
		this.isInterestedInBOBO = isInterestedInBOBO;
	}

	public java.lang.String getIsInterestedInLBS() {
		return isInterestedInLBS;
	}

	public void setIsInterestedInLBS(java.lang.String isInterestedInLBS) {
		this.isInterestedInLBS = isInterestedInLBS;
	}

	public java.lang.String getBoboLetterOfAuthFileName() {
		return boboLetterOfAuthFileName;
	}

	public void setBoboLetterOfAuthFileName(
			java.lang.String boboLetterOfAuthFileName) {
		this.boboLetterOfAuthFileName = boboLetterOfAuthFileName;
	}

	public java.lang.String getBoboLeterOfAuthContentType() {
		return boboLeterOfAuthContentType;
	}

	public void setBoboLeterOfAuthContentType(
			java.lang.String boboLeterOfAuthContentType) {
		this.boboLeterOfAuthContentType = boboLeterOfAuthContentType;
	}

	public java.lang.String getIsBoboAccept() {
		return isBoboAccept;
	}

	public void setIsBoboAccept(java.lang.String isBoboAccept) {
		this.isBoboAccept = isBoboAccept;
	}

	public java.lang.String getLbsContractFileName() {
		return lbsContractFileName;
	}

	public void setLbsContractFileName(java.lang.String lbsContractFileName) {
		this.lbsContractFileName = lbsContractFileName;
	}

	public java.lang.String getLbsContractContentType() {
		return lbsContractContentType;
	}

	public void setLbsContractContentType(java.lang.String lbsContractContentType) {
		this.lbsContractContentType = lbsContractContentType;
	}

	public java.lang.String getIsLbsAccept() {
		return isLbsAccept;
	}

	public void setIsLbsAccept(java.lang.String isLbsAccept) {
		this.isLbsAccept = isLbsAccept;
	}

	public java.util.Date getAcceptDeclineDate() {
		return acceptDeclineDate;
	}

	public void setAcceptDeclineDate(java.util.Date acceptDeclineDate) {
		this.acceptDeclineDate = acceptDeclineDate;
	}

	public java.lang.Long getAcceptDelineUserId() {
		return acceptDelineUserId;
	}

	public void setAcceptDelineUserId(java.lang.Long acceptDelineUserId) {
		this.acceptDelineUserId = acceptDelineUserId;
	}

	public java.lang.String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(java.lang.String createdBy) {
		this.createdBy = createdBy;
	}

	public java.util.Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(java.util.Date createdDate) {
		this.createdDate = createdDate;
	}

	public java.util.Date getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(java.util.Date lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public java.util.Date getBoboPresentDate() {
		return boboPresentDate;
	}

	public void setBoboPresentDate(java.util.Date boboPresentDate) {
		this.boboPresentDate = boboPresentDate;
	}

	public java.util.Date getBoboAcceptDeclineDate() {
		return boboAcceptDeclineDate;
	}

	public void setBoboAcceptDeclineDate(java.util.Date boboAcceptDeclineDate) {
		this.boboAcceptDeclineDate = boboAcceptDeclineDate;
	}

	public java.lang.String getBoboAcceptDeclineByEmailId() {
		return boboAcceptDeclineByEmailId;
	}

	public void setBoboAcceptDeclineByEmailId(
			java.lang.String boboAcceptDeclineByEmailId) {
		this.boboAcceptDeclineByEmailId = boboAcceptDeclineByEmailId;
	}

	public java.lang.String getIsLbsAcceptByAlliance() {
		return isLbsAcceptByAlliance;
	}

	public void setIsLbsAcceptByAlliance(java.lang.String isLbsAcceptByAlliance) {
		this.isLbsAcceptByAlliance = isLbsAcceptByAlliance;
	}

	public java.util.Date getLbsPresentDate() {
		return lbsPresentDate;
	}

	public void setLbsPresentDate(java.util.Date lbsPresentDate) {
		this.lbsPresentDate = lbsPresentDate;
	}

	public java.util.Date getLbsAcceptDeclineDate() {
		return lbsAcceptDeclineDate;
	}

	public void setLbsAcceptDeclineDate(java.util.Date lbsAcceptDeclineDate) {
		this.lbsAcceptDeclineDate = lbsAcceptDeclineDate;
	}

	public java.lang.String getLbsAcceptDeclineByEmailId() {
		return lbsAcceptDeclineByEmailId;
	}

	public void setLbsAcceptDeclineByEmailId(
			java.lang.String lbsAcceptDeclineByEmailId) {
		this.lbsAcceptDeclineByEmailId = lbsAcceptDeclineByEmailId;
	}

	public java.util.Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(java.util.Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public java.lang.String getIsProductExeclusiveToVZW() {
		return isProductExeclusiveToVZW;
	}

	public void setIsProductExeclusiveToVZW(
			java.lang.String isProductExeclusiveToVZW) {
		this.isProductExeclusiveToVZW = isProductExeclusiveToVZW;
	}

	public java.lang.String getProductExclusiveToVzw() {
		return productExclusiveToVzw;
	}

	public void setProductExclusiveToVzw(java.lang.String productExclusiveToVzw) {
		this.productExclusiveToVzw = productExclusiveToVzw;
	}

	public java.lang.String getIsGoExclusiveWithVZW() {
		return isGoExclusiveWithVZW;
	}

	public void setIsGoExclusiveWithVZW(java.lang.String isGoExclusiveWithVZW) {
		this.isGoExclusiveWithVZW = isGoExclusiveWithVZW;
	}

	public java.lang.String getIsProductUseVzwVzNt() {
		return isProductUseVzwVzNt;
	}

	public void setIsProductUseVzwVzNt(java.lang.String isProductUseVzwVzNt) {
		this.isProductUseVzwVzNt = isProductUseVzwVzNt;
	}

	public java.lang.String getIsProductCertifiedVZW() {
		return isProductCertifiedVZW;
	}

	public void setIsProductCertifiedVZW(java.lang.String isProductCertifiedVZW) {
		this.isProductCertifiedVZW = isProductCertifiedVZW;
	}

	public java.lang.String getProductCerifiedPhase() {
		return productCerifiedPhase;
	}

	public void setProductCerifiedPhase(java.lang.String productCerifiedPhase) {
		this.productCerifiedPhase = productCerifiedPhase;
	}

	public java.lang.String getIsProductCertifiedODIProcess() {
		return isProductCertifiedODIProcess;
	}

	public void setIsProductCertifiedODIProcess(
			java.lang.String isProductCertifiedODIProcess) {
		this.isProductCertifiedODIProcess = isProductCertifiedODIProcess;
	}

	public java.lang.String getProductInformation() {
		return productInformation;
	}

	public void setProductInformation(java.lang.String productInformation) {
		this.productInformation = productInformation;
	}

	public java.lang.String getIsProductRequiedLBS() {
		return isProductRequiedLBS;
	}

	public void setIsProductRequiedLBS(java.lang.String isProductRequiedLBS) {
		this.isProductRequiedLBS = isProductRequiedLBS;
	}

	public java.lang.String getIsOfferBoboServices() {
		return isOfferBoboServices;
	}

	public void setIsOfferBoboServices(java.lang.String isOfferBoboServices) {
		this.isOfferBoboServices = isOfferBoboServices;
	}

	public java.lang.String getBriefDescription() {
		return briefDescription;
	}

	public void setBriefDescription(java.lang.String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public java.lang.String getSolutionReside() {
		return solutionReside;
	}

	public void setSolutionReside(java.lang.String solutionReside) {
		this.solutionReside = solutionReside;
	}

	public java.lang.String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(java.lang.String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public java.lang.String getDevices() {
		return devices;
	}

	public void setDevices(java.lang.String devices) {
		this.devices = devices;
	}

	public java.lang.Long getAllianceSponsor()
    {
        return this.allianceSponsor;
    }

    public void setAllianceSponsor(java.lang.Long allianceSponsor)
    {
        this.allianceSponsor = allianceSponsor;
    }

    public java.util.Collection getAllBdsContacts()
    {
        return this.allBdsContacts;
    }

    public void setAllBdsContacts(java.util.Collection col)
    {
        this.allBdsContacts = col;
    }

    /*  */
    public void setCsCustomerName(String strRR)
    {
        this.csCustomerName = strRR;
    }

    public String getCsCustomerName()
    {
        return this.csCustomerName;
    }

    /*  */
    public void setcsProblemStatement(String strRR)
    {
        this.csProblemStatement = strRR;
    }

    public String getcsProblemStatement()
    {
        return this.csProblemStatement;
    }

    /*  */
    public void setCsBusinessBenifit(String strRR)
    {
        this.csBusinessBenifit = strRR;
    }

    public String getCsBusinessBenifit()
    {
        return this.csBusinessBenifit;
    }

    /*  */
    public void setCsProductionLaunchDate(String strRR)
    {
        this.csProductionLaunchDate = strRR;
    }

    public String getCsProductionLaunchDate()
    {
        return this.csProductionLaunchDate;
    }

    /* */
    public void setCsTotalEndUsers(java.lang.Long lngInput)
    {
        this.csTotalEndUsers = lngInput;
    }

    public java.lang.Long getCsTotalEndUsers()
    {
        return this.csTotalEndUsers;
    }

    /* */
    public void setCsNoOfUsersAccess(java.lang.Long lngInput)
    {
        this.csNoOfUsersAccess = lngInput;
    }

    public java.lang.Long getCsNoOfUsersAccess()
    {
        return this.csNoOfUsersAccess;
    }

    /* */
    public void setCsPlatformId(java.lang.Long lngInput)
    {
        this.csPlatformId = lngInput;
    }

    public java.lang.Long getCsPlatformId()
    {
        return this.csPlatformId;
    }

    /**/
    public void setCsPlatformName(String strInput)
    {
        this.csPlatformName = strInput;
    }

    public String getCsPlatformName()
    {
        return this.csPlatformName;
    }

    /* */
    public void setCsCaseStudyId(java.lang.Long[] lngInput)
    {
        this.csCaseStudyId = lngInput;
    }

    public java.lang.Long[] getCsCaseStudyId()
    {
        return this.csCaseStudyId;
    }

    /*  */
    public void setFortuneCustomers(String strInput)
    {
        this.fortuneCustomers = strInput;
    }

    public String getFortuneCustomers()
    {
        return this.fortuneCustomers;
    }

    /*  */
    public String getCustomerBenefits()
    {
        return customerBenefits;
    }

    public void setCustomerBenefits(String string)
    {
        customerBenefits = string;
    }

    /*  */
    public void setCustSupportPhone(String strInput)
    {
        this.custSupportPhone = strInput;
    }

    public String getCustSupportPhone()
    {
        return this.custSupportPhone;
    }

    /*  */
    public void setCustSupportEmail(String strInput)
    {
        this.custSupportEmail = strInput;
    }

    public String getCustSupportEmail()
    {
        return this.custSupportEmail;
    }

    /*  */
    public void setCustSupportHours(String strInput)
    {
        this.custSupportHours = strInput;
    }

    public String getCustSupportHours()
    {
        return this.custSupportHours;
    }
    /*  */
    public void setTotalEndUsers(java.lang.String lngInput)
    {
        this.totalEndUsers = lngInput;
    }

    public java.lang.String getTotalEndUsers()
    {
        return this.totalEndUsers;
    }

    /*  */
    public void setOtherIndFocusValue(String strInput)
    {
        this.otherIndFocusValue = strInput;
    }

    public String getOtherIndFocusValue()
    {
        return this.otherIndFocusValue;
    }

    /*  */
    public void setNoOfUsersAccess(java.lang.String lngInput)
    {
        this.noOfUsersAccess = lngInput;
    }

    public java.lang.String getNoOfUsersAccess()
    {
        return this.noOfUsersAccess;
    }

    /*  */
    public void setPlatformDepMode(String strInput)
    {
        this.platformDepMode = strInput;
    }

    public String getPlatformDepMode()
    {
        return this.platformDepMode;
    }
    /*  */
    public void setTargetedMarketRegions(String[] strInputs)
    {
        this.targetedMarketRegions = strInputs;
    }

    public String[] getTergetedMarketRegions()
    {
        return this.targetedMarketRegions;
    }

    /*  */
    public void setRegionId(java.lang.Long[] strInputs)
    {
        this.regions = strInputs;
    }

    public java.lang.Long[] getRegionId()
    {
        return this.regions;
    }

    public void setEntAppSubCategoryId(java.lang.Long[] strInputs)
    {
        this.entAppSubCategoryId = strInputs;
    }

    public java.lang.Long[] getEntAppSubCategoryId()
    {
        return this.entAppSubCategoryId;
    }

    public void setSolutionComponentId(java.lang.Long[] lngLoB)
    {
        this.solutionComponentId = lngLoB;
    }

    public java.lang.Long[] getSolutionComponentId()
    {
        return this.solutionComponentId;
    }

    /*  */
    public void setIndustryFocusId(java.lang.Long[] lngLoB)
    {
        this.industryFocusId = lngLoB;
    }

    public java.lang.Long[] getIndustryFocusId()
    {
        return this.industryFocusId;
    }

    public java.util.Collection getAllIndustryFocus()
    {
        return this.allIndustryFocus;
    }

    public java.util.Collection getAllPlatforms()
    {
        return this.allPlatforms;
    }

    public java.util.Collection getAllRegions()
    {
        return this.allRegions;
    }

    public java.util.Collection getAllSolutionComponents()
    {
        return this.allSolutionComponents;
    }

    public void setAllIndustryFocus(java.util.Collection col)
    {
        this.allIndustryFocus = col;
    }

    public void setAllPlatforms(java.util.Collection col)
    {
        this.allPlatforms = col;
    }

    public void setAllRegions(java.util.Collection col)
    {
        this.allRegions = col;
    }

    public void setAllSolutionComponents(java.util.Collection col)
    {
        this.allSolutionComponents = col;
    }

    /*  */
    public void setAllCaseStudies(java.util.Collection col)
    {
        this.allCaseStudies = col;
    }

    public java.util.Collection getAllCaseStudies()
    {
        return this.allCaseStudies;
    }

    public void setPresentationFileName(String strInput)
    {
        this.presentationFileName = strInput;
    }

    public String getPresentationFileName()
    {
        return this.presentationFileName;
    }

    public FormFile getPresentationFile()
    {
        return this.presentationFile;
    }

    public void setPresentationFile(FormFile ffPresentation)
    {
        this.presentationFile = ffPresentation;
    }

    public java.lang.Long getPresentationTempFileId()
    {
        return this.presentationTempFileId;
    }

    public void setPresentationTempFileId(java.lang.Long presentationTempFileId)
    {
        this.presentationTempFileId = presentationTempFileId;
    }

    public java.lang.Long getSolCompId(int index)
    {
        return this.solCompId[index];
    }

    public void setSolCompId(int index, java.lang.Long solCompId)
    {
        this.solCompId[index] = solCompId;
    }

    public void setSolCompIdWhole(java.lang.Long[] solCompId)
    {
        this.solCompId = solCompId;
    }

    public java.lang.String getSolCompName(int index)
    {
        return this.solCompName[index];
    }

    public void setSolCompName(int index, java.lang.String solCompName)
    {
        this.solCompName[index] = solCompName;
    }

    public void setSolCompNameWhole(java.lang.String[] solCompName)
    {
        this.solCompName = solCompName;
    }

    public java.lang.String getSolCompDesc(int index)
    {
        return this.solCompDesc[index];
    }

    public void setSolCompDesc(int index, java.lang.String solCompDesc)
    {
        this.solCompDesc[index] = solCompDesc;
    }

    public void setSolCompDescWhole(java.lang.String[] solCompDesc)
    {
        this.solCompDesc = solCompDesc;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        java.util.HashSet csHS = (java.util.HashSet) request.getSession().getAttribute("AIMS_APP_CASE_STUDIES");

        try
        {
            log.debug("In RESET Entry");
            if (csHS != null)
            {
                this.allCaseStudies = csHS;
                log.debug("Case Study not null");
            }
            else
            {
                this.allCaseStudies = new java.util.HashSet();
                log.debug("Case Study was null");
            }

            super.reset(mapping, request, AimsConstants.ENTERPRISE_PLATFORM_ID);

            this.allBdsContacts = AimsApplicationsManager.getAllianceSponsors();
            this.allIndustryFocus = AimsIndFocusManager.getIndFocuses();
            this.allPlatforms = AimsEntAppsManager.getAimsPlatforms();
            this.allRegions = AimsEntAppsManager.getAimsRegions();
            this.allSolutionComponents = AimsEntAppsManager.getAimsSolutionComponents();

            Set solutionComponentSet = (Set) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP);

            if (allSolutionComponents != null)
            {
                //Fills From Session -> into Array
                this.solCompName = new String[allSolutionComponents.size()];
                this.solCompId = new Long[allSolutionComponents.size()];
                this.solCompDesc = new String[allSolutionComponents.size()];

                int index = 0;
                for (java.util.Iterator itr = allSolutionComponents.iterator(); itr.hasNext();)
                {
                    AimsSolutionComponent asc = (AimsSolutionComponent) itr.next();
                    this.solCompName[index] = asc.getSolutionComponentName();

                    this.solCompId[index] = asc.getSolutionComponentId();
                    if ((solutionComponentSet != null) && (solutionComponentSet.size() > 0))
                    {
                        for (java.util.Iterator itre = solutionComponentSet.iterator(); itre.hasNext();)
                        {
                            AimsEntAppSolComp aesc = (AimsEntAppSolComp) itre.next();
                            if (aesc.getAimsSolutionComponent().equals(asc))
                                this.solCompDesc[index] = aesc.getSolutionDescription();
                        }
                    }
                    index++;
                }
            }

            this.regions = (Long[]) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_REGIONS_LIST);
            this.entAppSubCategoryId = (Long[]) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_ENT_APP_SUB_CAT);
            this.industryFocusId = (Long[]) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_IND_FOCUS_LIST);
            this.solutionComponentId = (Long[]) request.getSession().getAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP_ID_ONLY);

            request.setAttribute("EntApplicationUpdateForm", this);
            
            try {
            	allProductinfo=AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_SOLUTION_PRODUCT_INFO);
            	allMarketSegInfo=AimsTypeManager.getTypesByTypeDefId(AimsConstants.TYPE_DEF_JMA_MARKET_SEGMENT_INFO);
    		} catch (HibernateException e) {
    			log.error("ExceptionOccure while getiing product info",e);
    		}

            log.debug("In RESET Out");
        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET: " + ex);
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {

        HttpSession session = request.getSession();
        ActionErrors errors = new ActionErrors();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String sessionId = session.getId();
        String taskName = (String) session.getAttribute(ManageApplicationsConstants.MANAGE_APP_TASKNAME);
        
        String taskname = request.getParameter("task");
       /* if (taskname.equalsIgnoreCase("create"))
        {
        	Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        	try {
					if(!JMAApplicationHelper.validateAllianceContract(currentUserAllianceId, currUserType)){
						  errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.jma.active.accepted.contract"));

				}
			} catch (HibernateException e) {
				 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.jma.active.accepted.contract"));
				e.printStackTrace();
			}
        }*/

        //VZW User Cannot update the 3 lists below(verticalMarkets, lineOfBusinessId, industryFocusId)
        //Now alliance user can also update, reference bug# 6319
       // if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
       // {
            if (this.solutionComponentId != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP_ID_ONLY, this.solutionComponentId);
            if (this.regions != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_REGIONS_LIST, this.regions);
            if (this.entAppSubCategoryId != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_ENT_APP_SUB_CAT, this.entAppSubCategoryId);
            if (this.industryFocusId != null)
                session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_IND_FOCUS_LIST, this.industryFocusId);

            Set solutionComponentSet = new HashSet();

            if (allSolutionComponents != null)
            {
                if ((solutionComponentId != null) && (solutionComponentId.length > 0))
                {
                    int index = 0;
                    for (java.util.Iterator itr = this.allSolutionComponents.iterator(); itr.hasNext();)
                    {
                        AimsSolutionComponent asc = (AimsSolutionComponent) itr.next();
                        for (int i = 0; i < solutionComponentId.length; i++)
                        {
                            if (asc.getSolutionComponentId().equals(solutionComponentId[i]))
                            {
                                AimsEntAppSolComp aesc = new AimsEntAppSolComp();
                                aesc.setAimsSolutionComponent(asc);
                                aesc.setSolutionDescription(solCompDesc[index]);
                                solutionComponentSet.add(aesc);
                            }
                        }
                        index++;
                    }
                }
            }
            session.setAttribute(ManageApplicationsConstants.SESSION_SELECTED_SOL_COMP, solutionComponentSet);
        //}

        super.saveTempFiles(request, sessionId, currUserId, errors, AimsConstants.ENTERPRISE_PLATFORM_ID); //Calling Parent Class method
        saveTempEntFiles(request, sessionId, currUserId, errors);

        super.validate(mapping, request, errors, AimsConstants.ENTERPRISE_PLATFORM_ID); //Calling Parent Class method

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
            //Common to Save and Submit
        	//Now alliance user can also update, reference bug# 6319
            //if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
           // {
                //Numbers
                if (!this.isBlankString(this.totalEndUsers))
                    if (!this.isNumber(this.totalEndUsers))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.totalEndUsers.long", this.totalEndUsers));

                if (!this.isBlankString(this.noOfUsersAccess))
                    if (!this.isNumber(this.noOfUsersAccess))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.noOfUsersAccess.long", this.noOfUsersAccess));

                //Email Validation
                if (!this.isBlankString(this.custSupportEmail))
                    if (!this.isValidEmail(this.custSupportEmail))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.custSupportEmail.invalid", this.custSupportEmail));

                //File Type Validation(s)
                if (!(this.isBlankString(this.presentationFileName)))
                    if (!(this.isValidFileType(this.presentationFileName, AimsConstants.FILE_TYPE_PRESENTATION)))
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.presentation.file.type"));

            //}
        }

        if ((this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (this.appSubmitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
        	this.validateToDBFields(errors);

        	//Now alliance user can also update, reference bug# 6319
           // if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            //{
                if (this.isBlankString(this.custSupportPhone))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.custSupportPhone"));

                if (this.isBlankString(this.custSupportEmail))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.custSupportEmail"));

                if (this.isBlankString(this.custSupportHours))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.custSupportHours"));

                if (this.industryFocusId == null || this.industryFocusId.length <= 0)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.industryFocus"));

                if (regions == null || regions.length <= 0)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.regions"));

                //if (entAppSubCategoryId == null || entAppSubCategoryId.length <= 0)
                 //   errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.subCategory"));

                if (devices == null || devices.length() <= 0)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.devices"));
                
                //if (solutionComponentId == null || solutionComponentId.length <= 0)
                //    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.solutionComponents"));

                if (this.isBlankString(this.fortuneCustomers))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.fortuneCustomers"));

                if (this.isBlankString(this.totalEndUsers))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.totalEndUsers"));

                if (this.isBlankString(this.noOfUsersAccess))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.noOfUserAccess"));

                if (this.isBlankString(this.platformDepMode))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.platformDefMode"));

                
                
                if (entProductInfo==null || entProductInfo.length==0)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.productInformation"));
               
                if (this.isBlankString(this.briefDescription))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.productBriefDescription"));
               
                if (this.isBlankString(this.isProductExeclusiveToVZW))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.productExclusiveVZW"));
                else if(isProductExeclusiveToVZW.equals("N") && this.isBlankString(productExclusiveToVzw))
                	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.productExclusiveVZW"));
                
                if (this.isBlankString(this.additionalInformation))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.AdditionalInfo"));
                
                if (this.isBlankString(this.isProductExeclusiveToVZW))
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.isOptionToGoExclusiveVZW"));
                
                if (this.isBlankString(this.isProductUseVzwVzNt))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.isProductUseVzwVzNt"));
                
                if (this.isBlankString(this.isProductCertifiedVZW))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.isProductCertifiedVZW"));
                
                if (this.isBlankString(this.isProductCertifiedODIProcess))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.isProductCertifiedODIProcess"));
                
                if (this.isBlankString(this.isProductRequiedLBS))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.requiredLBS"));
                
                if (this.isBlankString(this.isOfferBoboServices))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.offerBOBO"));
                
                if(entMarketSegInfo==null || entMarketSegInfo.length==0 )
                	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.marketSegment.required"));
                
                //File(s) to be uploaded
                if (((this.presentationFile == null) || !(this.presentationFile.getFileSize() > 0)) && (this.isBlankString(this.presentationFileName)))
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.presentation.required"));
                
                //only verizon user can update isFeatured and isPublic property
                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
	                //if isPublished = null and isFeatured checked
                	if(this.isBlankString(this.isPublished) && !this.isBlankString(this.isFeatured))
	                	 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.select.isFeatured",this.title));
                	//if isPublished = N and isFeatured checked
                	else if(!this.isBlankString(this.isPublished) && this.isPublished.equals("N") && !this.isBlankString(this.isFeatured))
	                	 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.select.isFeatured",this.title));
	                	
	                //Check for isFeatured count
	                try
	                {
	                	//If current solution published	
		                if(StringFuncs.NullValueReplacement(this.isFeatured).equals("Y"))
		                {
			                int count= AimsEntAppsManager.getFeaturedSolutionsCount(this.appsId);
			                
			                //If current solution published
			                count++;
			                
			                if(count > AimsConstants.IS_FEATURED_MAX_COUNT)
			                	 errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.isFeature.count.exceed"));
		                }
	                }catch(Exception ex)
	                {
	                	log.error("Error occure while processing validation for isFeatured", ex);
	                	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.isFeatured"));
	                }
                }
            
           // }
        }

        return errors;

    }
    
    public void validateToDBFields(ActionErrors errors)
    {
    	if ((devices != null) && (devices.length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.length.devices"));
    	
    	if ((fortuneCustomers != null) && (fortuneCustomers.length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.length.fourtuneCustomers"));
    	
    	if ((appDeployments != null) && (appDeployments.length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.length.applicationDeployment"));
    	
    	if ((briefDescription != null) && (briefDescription.length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.length.briefDescriptionOfSolution"));
    	
    	if ((productExclusiveToVzw != null) && (productExclusiveToVzw.length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.length.additionalInfo"));

    	if ((additionalInformation != null) && (additionalInformation.length() > 1000))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.entApp.length.productExclusiveToVZW"));

    	
    }

    public void saveTempEntFiles(HttpServletRequest request, String sessionId, String currUserId, ActionErrors errors)
    {
        log.debug("\n\nIn saveTempEntFiles of EntApplicationUpdateForm: \n\n");
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        TempFile tempFile = null;

        try
        {
            if ((this.presentationFile != null)
                && (this.presentationFile.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.presentationFile.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.presentation.fileSize.exceeded"));
                this.presentationFile.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.presentationFile, sessionId, currUserId);
                if (tempFile != null)
                {
                    this.presentationTempFileId = tempFile.getFileId();
                    this.presentationFileName = tempFile.getFileName();
                }
            }
            
            
            
            if ((this.boboLetterOfAuthFile != null)
                    && (this.boboLetterOfAuthFile.getFileSize() > 0)
                    && !(this.isValidFileSize(fileSize, this.boboLetterOfAuthFile.getFileSize())))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.boboLetterOfAuthorization.fileSize.exceeded"));
                    this.boboLetterOfAuthFile.destroy();
                }
                else
                {
                    tempFile = TempFilesManager.saveTempFile(this.boboLetterOfAuthFile, sessionId, currUserId);
                    if (tempFile != null)
                    {
                        this.boboLetterOfAuthTempFileId = tempFile.getFileId();
                        this.boboLetterOfAuthFileName = tempFile.getFileName();
                    }
                }
            
            if ((this.lbsContractFile != null)
                    && (this.lbsContractFile.getFileSize() > 0)
                    && !(this.isValidFileSize(fileSize, this.lbsContractFile.getFileSize())))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.enterprise.app.lbsContractField.fileSize.exceeded"));
                    this.lbsContractFile.destroy();
                }
                else
                {
                    tempFile = TempFilesManager.saveTempFile(this.lbsContractFile, sessionId, currUserId);
                    if (tempFile != null)
                    {
                        this.lbsContractTempFileId = tempFile.getFileId();
                        this.lbsContractFileName = tempFile.getFileName();
                    }
                }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.debug("Error while saving temp files IN saveTempEntFiles() of EntApplicationUpdateForm");
        }
        finally
        {
            log.debug("Finally called IN saveTempEntFiles() of EntApplicationUpdateForm");
        }

    }

	public java.lang.Long getBoboLetterOfAuthTempFileId() {
		return boboLetterOfAuthTempFileId;
	}

	public void setBoboLetterOfAuthTempFileId(
			java.lang.Long boboLetterOfAuthTempFileId) {
		this.boboLetterOfAuthTempFileId = boboLetterOfAuthTempFileId;
	}

	public java.lang.Long getLbsContractTempFileId() {
		return lbsContractTempFileId;
	}

	public void setLbsContractTempFileId(java.lang.Long lbsContractTempFileId) {
		this.lbsContractTempFileId = lbsContractTempFileId;
	}

	public FormFile getBoboLetterOfAuthFile() {
		return boboLetterOfAuthFile;
	}

	public void setBoboLetterOfAuthFile(FormFile boboLetterOfAuthFile) {
		this.boboLetterOfAuthFile = boboLetterOfAuthFile;
	}

	public FormFile getLbsContractFile() {
		return lbsContractFile;
	}

	public void setLbsContractFile(FormFile lbsContractFile) {
		this.lbsContractFile = lbsContractFile;
	}

	public java.lang.String getDisplayTabBOBO() {
		return displayTabBOBO;
	}

	public void setDisplayTabBOBO(java.lang.String displayTabBOBO) {
		this.displayTabBOBO = displayTabBOBO;
	}

	public java.lang.String getDisplayTabLBS() {
		return displayTabLBS;
	}

	public void setDisplayTabLBS(java.lang.String displayTabLBS) {
		this.displayTabLBS = displayTabLBS;
	}

	public java.lang.String getAcceptDeclinBoboLbs() {
		return acceptDeclinBoboLbs;
	}

	public void setAcceptDeclinBoboLbs(java.lang.String acceptDeclinBoboLbs) {
		this.acceptDeclinBoboLbs = acceptDeclinBoboLbs;
	}

	public java.lang.String getResubmitSolution() {
		return resubmitSolution;
	}

	public void setResubmitSolution(java.lang.String resubmitSolution) {
		this.resubmitSolution = resubmitSolution;
	}

	public java.lang.String getResubmitSolutionText() {
		return resubmitSolutionText;
	}

	public void setResubmitSolutionText(java.lang.String resubmitSolutionText) {
		this.resubmitSolutionText = resubmitSolutionText;
	}

	public Collection getAllProductinfo() {
		return allProductinfo;
	}

	public void setAllProductinfo(Collection allProductinfo) {
		this.allProductinfo = allProductinfo;
	}

	public Long[] getEntProductInfo() {
		return entProductInfo;
	}

	public void setEntProductInfo(Long[] entProductInfo) {
		this.entProductInfo = entProductInfo;
	}

	public Collection getAllMarketSegInfo() {
		return allMarketSegInfo;
	}

	public void setAllMarketSegInfo(Collection allMarketSegInfo) {
		this.allMarketSegInfo = allMarketSegInfo;
	}

	public Long[] getEntMarketSegInfo() {
		return entMarketSegInfo;
	}

	public void setEntMarketSegInfo(Long[] entMarketSegInfo) {
		this.entMarketSegInfo = entMarketSegInfo;
	}

}
