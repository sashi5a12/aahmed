package com.netpace.aims.controller.alliance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.alliance.AllianceBusInfoManager;
import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.NameValueBean;

import java.util.Collection;
import java.util.ArrayList;

import net.sf.hibernate.HibernateException;

/**
 * @struts.form name="AllianceRegistrationForm"
 */
public class AllianceRegistrationForm extends BaseValidatorForm
{

    static Logger log = Logger.getLogger(AllianceRegistrationForm.class.getName());

    /** persistent field */
    private java.lang.String companyName;
    /** persistent field */
    private java.lang.String webSiteUrl;

    /** persistent field */
    private java.lang.String firstName;
    /** persistent field */
    private java.lang.String lastName;

    /** persistent field */
    private java.lang.String title;

    /** persistent field */
    private java.lang.String loginId;
    
    private String vzdnManagerRoles;
    private java.lang.String email;

    /** persistent field */
    private java.lang.String password;
    private java.lang.String confirmPwd;

    /** persistent field */
    private java.lang.String phone;
    
    private java.lang.String fax;
    private java.lang.String mobile;
    
    
    /** persistent field */
    private java.lang.Integer yearFounded;

    /** persistent field */
    private java.lang.String companyAddress;
    /** persistent field */
    private java.lang.String stateProvince;

    /** persistent field */
    private java.lang.String zipCode;
    /** persistent field */
    private java.lang.String country;


    /** persistent field, array of childIds */
    private java.lang.Long[] alliancesWithOtherCarriers;
    private java.lang.String allianceWithOtherCarriers;

    /** persistent field */
    private FormFile companyPresentation;
    /** persistent field */
    private java.lang.String companyPresentationFileName;
    /** persistent field */
    private java.lang.String companyPresentationContentType;
    private java.lang.Long companyPresentationTempFileId;

    /** persistent field */
    private FormFile companyLogo;
    /** persistent field */
    private java.lang.String companyLogoFileName;
    /** persistent field */
    private java.lang.String companyLogoContentType;
    private java.lang.Long companyLogoTempFileId;

    /** persistent field, array of childIds */
    private java.lang.Long[] developmentTechnologies;
    /** persistent field */
    private java.lang.String employeesRange;

    /** persistent field */
    private java.lang.String competitiveAdvantages;
    /** persistent field, array of childIds */
    private java.lang.Long[] financing;

    /** persistent field */
    private java.lang.String partner;
    /** persistent field, array of childIds */
    private java.lang.Long[] developments;
    /** persistent field */
    private java.lang.String outsourceDevelopmentPublisherName;

    /** identifier field, not used here */
    private java.lang.Long lineOfBusinessId;

    private String techEmail;
    private String techPassword;
    private String techFirstName;
    private String techLastName;
    private String techTitle;
    private String techPhone;
    private String techMobile;
    private String techFax;
    
    
    private java.lang.String city;    
    
    private String remitTo;
    private String remitAddress1;
    private String remitAddress2;
    private String remitCity;
    private String remitState; 
    private String remitPostalCode;
    private String remitCountry;
    
    public java.lang.String getCity()
    {
        return this.city;
    }

    public void setCity(java.lang.String city)
    {
        this.city = city;
    }
    
    
    public java.lang.Long getLineOfBusinessId()
    {
        return this.lineOfBusinessId;
    }

    public void setLineOfBusinessId(java.lang.Long lineOfBusinessId)
    {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public java.lang.String getCompanyName()
    {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName)
    {
        this.companyName = companyName;
    }

    public java.lang.String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(java.lang.String lastName)
    {
        this.lastName = lastName;
    }

    public java.lang.String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName)
    {
        this.firstName = firstName;
    }

    public java.lang.String getEmail()
    {
        return this.email;
    }

    public void setEmail(java.lang.String email)
    {
        this.email = email;
    }
    public java.lang.String getLoginId()
    {
        return this.loginId;
    }

    public void setLoginId(java.lang.String loginId)
    {
        this.loginId = loginId;
    }

    public java.lang.String getPassword()
    {
        return this.password;
    }

    public void setPassword(java.lang.String password)
    {
        this.password = password;
    }

    public java.lang.String getConfirmPwd()
    {
        return this.confirmPwd;
    }

    public void setConfirmPwd(java.lang.String confirmPwd)
    {
        this.confirmPwd = confirmPwd;
    }
        
    public String getRemitTo() {
		return remitTo;
	}

	public void setRemitTo(String remitTo) {
		this.remitTo = remitTo;
	}

	public String getRemitAddress1() {
		return remitAddress1;
	}

	public void setRemitAddress1(String remitAddress1) {
		this.remitAddress1 = remitAddress1;
	}

	public String getRemitAddress2() {
		return remitAddress2;
	}

	public void setRemitAddress2(String remitAddress2) {
		this.remitAddress2 = remitAddress2;
	}

	public String getRemitCity() {
		return remitCity;
	}

	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}

	public String getRemitState() {
		return remitState;
	}

	public void setRemitState(String remitState) {
		this.remitState = remitState;
	}

	public String getRemitPostalCode() {
		return remitPostalCode;
	}

	public void setRemitPostalCode(String remitPostalCode) {
		this.remitPostalCode = remitPostalCode;
	}

	public String getRemitCountry() {
		return remitCountry;
	}

	public void setRemitCountry(String remitCountry) {
		this.remitCountry = remitCountry;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //this.setOutsourceDevelopmentPublisherName("Name of Publisher");        
        //System.out.println("\n\n\nI N    R E S E T\n\n\n");
        request.setAttribute("countryList", this.getCountriesList());

        request.setAttribute("alliancesWithOtherCarriersList", this.getAllAimsCarriers());
        request.setAttribute("developmentTechnologiesList", this.getDevelopmentTechnologiesList());
        request.setAttribute("financingList", this.getAllAimsFinancingOptions());
        request.setAttribute("numberOfEmployeesList", this.getNumberOfEmployeesList());

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        String fileSize = "10485760"; //set max file size to 10 mb
        String currUserId = "system";
        TempFile tempFile = null;

        request.setAttribute("countryList", this.getCountriesList());

        request.setAttribute("alliancesWithOtherCarriersList", this.getAllAimsCarriers());
        request.setAttribute("developmentTechnologiesList", this.getDevelopmentTechnologiesList());
        request.setAttribute("financingList", this.getAllAimsFinancingOptions());
        //request.setAttribute("developmentList", this.getDevelopmentList());
        request.setAttribute("numberOfEmployeesList", this.getNumberOfEmployeesList());
                
        try
        {
            request.setAttribute("developmentTechnologiesList", AllianceBusInfoManager.getAllDevelopmentTechnologies());

            if ((this.companyLogo != null) && (this.companyLogo.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.companyLogo.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                this.companyLogo.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.companyLogo, sessionId, "system");
                if (tempFile != null)
                {
                    this.companyLogoTempFileId = tempFile.getFileId();
                    this.companyLogoFileName = tempFile.getFileName();
                }
            }
            if ((this.companyPresentation != null)
                && (this.companyPresentation.getFileSize() > 0)
                && !(this.isValidFileSize(fileSize, this.companyPresentation.getFileSize())))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.filesize.note"));
                this.companyPresentation.destroy();
            }
            else
            {
                tempFile = TempFilesManager.saveTempFile(this.companyPresentation, sessionId, "system");
                if (tempFile != null)
                {
                    this.companyPresentationTempFileId = tempFile.getFileId();
                    this.companyPresentationFileName = tempFile.getFileName();
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

        //Check to see if data is compatible with DB fields
        validateToDBFields(errors);

        //minimum length of companyname is 2 chars
        if (this.isBlankString(this.companyName) || this.companyName.trim().length() < 2)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.companyName"));
        if (this.isBlankString(this.webSiteUrl))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.webSiteUrl"));

        /*if (this.isBlankString(this.firstName))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.firstName"));
        if (this.isBlankString(this.lastName))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.lastName"));

        if (this.isBlankString(this.title))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.title"));
*/
        /*if (this.isBlankString(this.loginId))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.loginId"));
        else if (!this.isValidEmail(this.loginId))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.loginId"));
        else if (!this.loginId.equals(this.email))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.loginId.confirm.match"));

        if (this.isBlankString(this.password))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.password"));
        else if (!this.password.equals(this.confirmPwd))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.password.confirm.match"));

        if (this.isBlankString(this.phone))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.phone"));
*/
        if (this.yearFounded == null || this.yearFounded.intValue()==0)
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.yearFounded"));
        }
        else if(!this.isValidYear(this.yearFounded.toString()) )
        {               
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.format.yearFounded"));
        }

        if (this.isBlankString(this.companyAddress))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.companyAddress"));

        if (this.isBlankString(this.city))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.city"));
        if (this.isBlankString(this.stateProvince))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.stateProvince"));

        if (this.isBlankString(this.zipCode))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.zipCode"));
        if (this.isBlankString(this.country))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.country"));


        /*if(this.alliancesWithOtherCarriers == null || this.alliancesWithOtherCarriers.length==0)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.alliancesWithOtherCarriers"));

        if (((this.companyLogo == null) || !(this.companyLogo.getFileSize() > 0)) && (this.isBlankString(this.companyLogoFileName)))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.companyLogo"));

        if (((this.companyPresentation == null) || !(this.companyPresentation.getFileSize() > 0))
                && (this.isBlankString(this.companyPresentationFileName)))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.companyPresentation"));

        if(this.developmentTechnologies == null || this.developmentTechnologies.length==0)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.developmentTechnologies"));
        if (this.isBlankString(this.employeesRange))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.numberOfEmployees"));

        if (this.isBlankString(this.competitiveAdvantages))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.competitiveAdvantages"));
        if(this.financing == null || this.financing.length==0)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.financing"));

        if (this.isBlankString(this.partner))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.partner"));
        if(this.developments == null || this.developments.length==0)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.development"));
*/
        
        if (this.isBlankString(this.techEmail))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techEmail"));
        /*if (this.isBlankString(this.techPassword))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techPassword"));*/
        if (this.isBlankString(this.techFirstName))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techFirstName"));
        if (this.isBlankString(this.techLastName))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techLastName"));
        if (this.isBlankString(this.techTitle))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techTitle"));
        if (this.isBlankString(this.techPhone))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.techPhone"));

        return errors;
    }

    public void validateToDBFields(ActionErrors errors)
    {
        if ((this.companyName != null) && (this.companyName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.companyName"));

        if ((this.webSiteUrl != null) && (this.webSiteUrl.length() > 255))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.webSiteUrl"));
		
		/* TODO: waiting approval from VZ
        if ((this.companyName != null) && !this.isAlphaNumeric(this.companyName))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.companyName"));
        */
		
        if ((this.webSiteUrl != null) && !this.isValidHttpURL(this.webSiteUrl))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.webSiteUrl"));        
        
        if ( !StringUtils.isEmpty(this.webSiteUrl) && (this.webSiteUrl.indexOf("<")!=-1 || this.webSiteUrl.indexOf(">")!=-1) )
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalidchars.webSiteUrl"));
        
        if ( !StringUtils.isEmpty(this.stateProvince) && this.stateProvince.length() > 100 )
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.stateprovince"));
        /*if ((this.firstName != null) && (this.firstName.length() > 100))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.firstName"));

        if ((this.lastName != null) && (this.lastName.length() > 100))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.lastName"));

        if ((this.loginId != null) && (this.loginId.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.loginId"));

        if ((this.password != null) && (this.password.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.password"));

        if ((this.phone != null) && (this.phone.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.phone"));
*/
        if ((this.techEmail != null) && (this.techEmail.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techEmail"));

        /*if ((this.techPassword != null) && (this.techPassword.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techPassword"));*/

        if ((this.techFirstName != null) && (this.techFirstName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techFirstName"));

        if ((this.techLastName != null) && (this.techLastName.length() > 50))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techLastName"));

        if ((this.techTitle != null) && (this.techTitle.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techTitle"));

        if ((this.techPhone != null) && (this.techPhone.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techPhone"));

        if ((this.techMobile != null) && (this.techMobile.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techMobile"));

        if ((this.techFax != null) && (this.techFax.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.techFax"));
        
        
        if ((this.remitTo != null) && (this.remitTo.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitTo"));
        
        
        //////////////////
        //////////////////////
        if ( (this.remitTo != null) && (this.remitTo.indexOf("<")!=-1 || this.remitTo.indexOf(">")!=-1) )
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitTo"));
        
        if ((this.remitAddress1 != null) && (this.remitAddress1.indexOf("<")!=-1 || this.remitAddress1.indexOf(">")!=-1) )
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitAddress1"));
        
        if ((this.remitAddress2 != null) && (this.remitAddress2.indexOf("<")!=-1 || this.remitAddress2.indexOf(">")!=-1))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitAddress2"));
        
        if ((this.remitCity != null) && (this.remitCity.indexOf("<")!=-1 || this.remitCity.indexOf(">")!=-1))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitCity"));
        
        if ((this.remitState != null) && (this.remitState.indexOf("<")!=-1 || this.remitState.indexOf(">")!=-1))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitState"));        
        
        if ((this.remitCountry != null) && (this.remitCountry.indexOf("<")!=-1 || this.remitCountry.indexOf(">")!=-1))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.invalid.remitCountry"));      
        
        //////////////////
        /////////////////
        
        if ((this.remitAddress1 != null) && (this.remitAddress1.length() > 200))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitAddress1"));
        
        if ((this.remitAddress2 != null) && (this.remitAddress2.length() > 200))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitAddress2"));
        
        if ((this.remitCity != null) && (this.remitCity.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitCity"));
        
        if ((this.remitCountry != null) && (this.remitCountry.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitCountry"));
        
        //if ((this.remitPostalCode != null) && (this.remitPostalCode.length() > 5))
        //New checks in the condition below added for Bug Fix# 7850
        //Updated by Waseem. Postal code Field length is increased from 5 to 9 characters  
        if ( !StringUtils.isEmpty(this.remitPostalCode) && this.remitPostalCode.length() > 9 )
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitPostalCode"));
        else if ( !StringUtils.isEmpty(this.remitPostalCode) && !this.isAlphaNumeric((this.remitPostalCode) ) )
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.format.remitPostalCode"));
        		        
        if ((this.remitState != null) && (this.remitState.length() > 50))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.remitState"));
    }
    /**
     * @return
     */
    public java.lang.String getPhone()
    {
        return phone;
    }

    /**
     * @return
     */
    public java.lang.String getWebSiteUrl()
    {
        return webSiteUrl;
    }

    /**
     * @param string
     */
    public void setPhone(java.lang.String string)
    {
        phone = string;
    }

    /**
     * @param string
     */
    public void setWebSiteUrl(java.lang.String string)
    {
        webSiteUrl = string;
    }

    /**
     * 
     * @param yearFounded
     */
    public void setYearFounded(java.lang.Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    /**
     * 
     * @return
     */
    public java.lang.Integer getYearFounded() {
        return yearFounded;
    }

    /**
     * 
     * @param companyAddress
     */
    public void setCompanyAddress(java.lang.String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 
     * @param stateProvince
     */
    public void setStateProvince(java.lang.String stateProvince) {
        this.stateProvince = stateProvince;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getStateProvince() {
        return stateProvince;
    }

    /**
     * 
     * @param zipCode
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getZipCode() {
        return zipCode;
    }

    /**
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getCountry() {
        return country;
    }




    /**
     * 
     * @param employeesRange
     */
    public void setEmployeesRange(java.lang.String employeesRange) {
        this.employeesRange = employeesRange;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getEmployeesRange() {
        return employeesRange;
    }

    /**
     * 
     * @param competitiveAdvantages
     */
    public void setCompetitiveAdvantages(java.lang.String competitiveAdvantages) {
        this.competitiveAdvantages = competitiveAdvantages;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getCompetitiveAdvantages() {
        return competitiveAdvantages;
    }

    /**
     * 
     * @param partner
     */
    public void setPartner(java.lang.String partner) {
        this.partner = partner;
    }

    /**
     * 
     * @return
     */
    public java.lang.String getPartner() {
        return partner;
    }

    public FormFile getCompanyPresentation() {
        return companyPresentation;
    }

    public void setCompanyPresentation(FormFile companyPresentation) {
        this.companyPresentation = companyPresentation;
    }

    public String getCompanyPresentationFileName() {
        return companyPresentationFileName;
    }

    public void setCompanyPresentationFileName(String companyPresentationFileName) {
        this.companyPresentationFileName = companyPresentationFileName;
    }

    public String getCompanyPresentationContentType() {
        return companyPresentationContentType;
    }

    public void setCompanyPresentationContentType(String companyPresentationContentType) {
        this.companyPresentationContentType = companyPresentationContentType;
    }

    public Long getCompanyPresentationTempFileId() {
        return companyPresentationTempFileId;
    }

    public void setCompanyPresentationTempFileId(Long companyPresentationTempFileId) {
        this.companyPresentationTempFileId = companyPresentationTempFileId;
    }

    public FormFile getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(FormFile companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoFileName() {
        return companyLogoFileName;
    }

    public void setCompanyLogoFileName(String companyLogoFileName) {
        this.companyLogoFileName = companyLogoFileName;
    }

    public String getCompanyLogoContentType() {
        return companyLogoContentType;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public Long getCompanyLogoTempFileId() {
        return companyLogoTempFileId;
    }

    public void setCompanyLogoTempFileId(Long companyLogoTempFileId) {
        this.companyLogoTempFileId = companyLogoTempFileId;
    }

    public Long[] getAlliancesWithOtherCarriers() {
        return alliancesWithOtherCarriers;
    }

    public void setAlliancesWithOtherCarriers(Long[] alliancesWithOtherCarriers) {
        this.alliancesWithOtherCarriers = alliancesWithOtherCarriers;
    }

    public Long[] getDevelopmentTechnologies() {
        return developmentTechnologies;
    }

    public void setDevelopmentTechnologies(Long[] developmentTechnologies) {
        this.developmentTechnologies = developmentTechnologies;
    }

    public Long[] getFinancing() {
        return financing;
    }

    public void setFinancing(Long[] financing) {
        this.financing = financing;
    }

    public Long[] getDevelopments() {
        return developments;
    }

    public void setDevelopments(Long[] developments) {
        this.developments = developments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAllianceWithOtherCarriers() {
        return allianceWithOtherCarriers;
    }

    public void setAllianceWithOtherCarriers(String allianceWithOtherCarriers) {
        this.allianceWithOtherCarriers = allianceWithOtherCarriers;
    }

    public String getOutsourceDevelopmentPublisherName() {
        return outsourceDevelopmentPublisherName;
    }

    public void setOutsourceDevelopmentPublisherName(String outsourceDevelopmentPublisherName) {
        this.outsourceDevelopmentPublisherName = outsourceDevelopmentPublisherName;
    }

    public String getTechEmail() {
		return techEmail;
	}

	public void setTechEmail(String techEmail) {
		this.techEmail = StringUtils.trim(techEmail);
	}

	public String getTechPassword() {
		return techPassword;
	}

	public void setTechPassword(String techPassword) {
		this.techPassword = StringUtils.trim(techPassword);
	}

	public String getTechFirstName() {
		return techFirstName;
	}

	public void setTechFirstName(String techFirstName) {
		this.techFirstName = StringUtils.trim(techFirstName);
	}

	public String getTechLastName() {
		return techLastName;
	}

	public void setTechLastName(String techLastName) {
		this.techLastName = StringUtils.trim(techLastName);
	}

	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = StringUtils.trim(techTitle);
	}

	public String getTechPhone() {
		return techPhone;
	}

	public void setTechPhone(String techPhone) {
		this.techPhone = StringUtils.trim(techPhone);
	}

	public String getTechMobile() {
		return techMobile;
	}

	public void setTechMobile(String techMobile) {
		this.techMobile = StringUtils.trim(techMobile);
	}

	public String getTechFax() {
		return techFax;
	}

	public void setTechFax(String techFax) {
		this.techFax = StringUtils.trim(techFax);
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

    private Collection getDevelopmentTechnologiesList()
    {
        Collection developmentTechnologiesList = null;
        try {
            developmentTechnologiesList = AllianceBusInfoManager.getAllDevelopmentTechnologies();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return developmentTechnologiesList;
    }

    private Collection getAllAimsFinancingOptions()
    {
        Collection aimsFinancingOptionsList = null;
        try {
            aimsFinancingOptionsList = AllianceBusInfoManager.getAllAimsFinancingOptions();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return aimsFinancingOptionsList;
    }


    private Collection getNumberOfEmployeesList()
    {
        Collection numberOfEmployeesList = new ArrayList();
        numberOfEmployeesList.add(new NameValueBean("0-19", "0-19"));
        numberOfEmployeesList.add(new NameValueBean("20-49", "20-49"));
        numberOfEmployeesList.add(new NameValueBean("50-99", "50-99"));
        numberOfEmployeesList.add(new NameValueBean("100-199", "100-199"));
        numberOfEmployeesList.add(new NameValueBean("200-499", "200-499"));
        numberOfEmployeesList.add(new NameValueBean("More than 500", "More than 500"));
        return numberOfEmployeesList;
    }

    private Collection getCountriesList()
    {
        Collection countryList = null;
        try {
            countryList = AllianceCompInfoManager.getAllCountries();
        } catch (HibernateException e) {
            e.printStackTrace(); 
        }
        return countryList;
    }

	public String getVzdnManagerRoles() {
		return vzdnManagerRoles;
	}

	public void setVzdnManagerRoles(String vzdnManagerRoles) {
		this.vzdnManagerRoles = vzdnManagerRoles;
	}

	public java.lang.String getFax() {
		return fax;
	}

	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
    
    
    
}
