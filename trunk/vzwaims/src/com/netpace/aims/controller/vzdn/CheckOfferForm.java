package com.netpace.aims.controller.vzdn;

import java.util.Date;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="CheckOfferForm"
 */
public class CheckOfferForm extends BaseValidatorForm{
	
	private Long offerId;
    private Date offerDate;
    private Date accRejDate;
    private String offerTo;
    private String offerFrom;
    
    private String status;
    private String allianceName;
    private Long allianceId;
    
    
    private String userName;
    private String managerRoles;
    private String userType;
    private String requestURI;
    private String firstName;
    private String lastName;
    private String phone;
    private String fax;
    private String mobile;
    private String title;
    
    
    private String formOfferStatus;
    
	
    
    
    
    public java.lang.Long getOfferId() {
		return offerId;
	}
	public void setOfferId(java.lang.Long offerId) {
		this.offerId = offerId;
	}
	public java.util.Date getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(java.util.Date offerDate) {
		this.offerDate = offerDate;
	}
	public java.util.Date getAccRejDate() {
		return accRejDate;
	}
	public void setAccRejDate(java.util.Date accRejDate) {
		this.accRejDate = accRejDate;
	}
	public java.lang.String getOfferTo() {
		return offerTo;
	}
	public void setOfferTo(java.lang.String offerTo) {
		this.offerTo = offerTo;
	}
	public java.lang.String getOfferFrom() {
		return offerFrom;
	}
	public void setOfferFrom(java.lang.String offerFrom) {
		this.offerFrom = offerFrom;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public String getAllianceName() {
		return allianceName;
	}
	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}
	public Long getAllianceId() {
		return allianceId;
	}
	public void setAllianceId(Long allianceId) {
		this.allianceId = allianceId;
	}
	public String getFormOfferStatus() {
		return formOfferStatus;
	}
	public void setFormOfferStatus(String formOfferStatus) {
		this.formOfferStatus = formOfferStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getManagerRoles() {
		return managerRoles;
	}
	public void setManagerRoles(String managerRoles) {
		this.managerRoles = managerRoles;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    
	
    
    
    
    

}
