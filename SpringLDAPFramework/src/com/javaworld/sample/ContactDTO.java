package com.javaworld.sample;

public class ContactDTO {
	String firstName;
	String lastName;
	String cn;
	String mail;
	String title;
	String phoneNumber;
	String faxNumber;
	String mobileNumber;
	String userType;
	String gtmCompanyId;
	String country;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getGtmCompanyId() {
		return gtmCompanyId;
	}
	public void setGtmCompanyId(String gtmCompanyId) {
		this.gtmCompanyId = gtmCompanyId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String toString() {
		StringBuffer contactDTOStr = new StringBuffer("Person=[");
		contactDTOStr.append("firstName="+firstName);
		contactDTOStr.append(", lastName="+lastName);
		contactDTOStr.append(", cn="+cn);
		contactDTOStr.append(", mail="+mail);
		contactDTOStr.append(", title="+title);
		contactDTOStr.append(", phoneNumber="+phoneNumber);
		contactDTOStr.append(", faxNumber="+faxNumber);
		contactDTOStr.append(", mobileNumber="+mobileNumber);
		contactDTOStr.append(", userType="+userType);
		contactDTOStr.append(", gtmCompanyId="+gtmCompanyId);
		contactDTOStr.append(", country="+country);		
		contactDTOStr.append(" ]");
		return contactDTOStr.toString();
	}
	
	
	
}
