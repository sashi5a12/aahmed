package com.netpace.aims.controller.alliance.jma;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.alliance.AllianceBusInfoManager;
import com.netpace.aims.bo.alliance.AllianceCompInfoManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="JMAAllianceRegistrationForm"
 * @author aqureshi
 *
 */
public class JMAAllianceRegistrationForm extends BaseValidatorForm {

	/**
	 * JMA registration fields 
	 */
	private String companyName;
	private String webSiteUrl;
	private String firstName;
	private String lastName;
	private String title;
	//private String loginId;
    private String email;
    private String password;
    private String confirmPwd;
    private String phone;
    private Integer yearFounded;
    private String companyAddress;
    private String city;
    private String stateProvince;
    private String zipCode;
    private String country;
    
    //private Boolean isAccepted; //Commented by Muzammil as part of fix for Bug# 7845
    private String fax;
    private String mobile;
    private String userType;
    private String userRoles;
    
    
   
    
    /////////// Getter methods ///////////
    
	public String getCompanyName() {
		return companyName;
	}
	public String getWebSiteUrl() {
		return webSiteUrl;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getTitle() {
		return title;
	}
	/*public String getLoginId() {
		return loginId;
	}*/
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public String getPhone() {
		return phone;
	}
	public Integer getYearFounded() {
		return yearFounded;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public String getCity() {
		return city;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getCountry() {
		return country;
	}
	/*
	 * Commented by Muzammil as part of fix for Bug# 7845
	public Boolean getIsAccepted() {
		return isAccepted;
	}
	*/
	
	/////////// Setter methods ///////////
	
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrl = webSiteUrl;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/*public void setLoginId(String loginId) {
		this.loginId = loginId;
	}*/
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setYearFounded(Integer yearFounded) {
		this.yearFounded = yearFounded;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	/*
	 * Commented by Muzammil as part of fix for Bug# 7845
	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	*/
	
	
	////////// Others method /////////
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //this.setOutsourceDevelopmentPublisherName("Name of Publisher");        
        //System.out.println("\n\n\nI N    R E S E T\n\n\n");
        request.setAttribute("countryList", this.getCountriesList());
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

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

        ActionErrors errors = new ActionErrors();

        HttpSession session = request.getSession();
        String sessionId = session.getId();
       
        request.setAttribute("countryList", this.getCountriesList());
        
        //Check to see if data is compatible with DB fields
        validateToDBFields(errors);

        if (this.isBlankString(this.companyName))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.JMAAllianceRegistrationForm.required.companyName"));
        else
        	companyName=companyName.trim();
        
        if (this.isBlankString(this.webSiteUrl))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.required.webSiteUrl"));





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

       return errors;
    
	}
	
	
	  public void validateToDBFields(ActionErrors errors)
	  {

	        if ((this.companyName != null) && (this.companyName.length() > 250))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.companyName"));

	        if ((this.webSiteUrl != null) && (this.webSiteUrl.length() > 250))
	            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.AllianceRegistrationForm.length.webSiteUrl"));
	  }
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}
	  
	  
}

