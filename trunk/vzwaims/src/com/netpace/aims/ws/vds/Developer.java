package com.netpace.aims.ws.vds;

import java.util.HashMap;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.ws.ThirdpartyServiceException;

public class Developer {
	
	private static Logger log = Logger.getLogger(Developer.class.getName());

	//"developer" struct contains following parameters.
	private String allianceId = null; 			
	private String loginId = null; 			//unique developer login id.
	private String password = null;			//password for the login ID
	private boolean statusEnabled = true; 	//Valid values: enabled and disabled.
	private String developerName = null; 	//developer name
	private String contactName = null;		//Contact name.
	private String companyUrl = null;		//Company URL of the developer.
	private String phoneNumber = null;		//optional - Phone number .
	private String faxNumber = null;		//optional - Fax number .
	private String email = null;			//email.
	private String street1 = null;			//optional - Address string
	private String street2 = null;			//optional - Address string
	private String city = null;				//optional - City string
	private String state = null;			//optional - State string
	private String postalCode = null;		//optional - postal code
	private String countryCode = null;		//optional - country code
	private String[] planIds = null; 		//optional - array of system generated plan ids
	private String[] roleIds = null;		//optional - array of system generated role ids
	
	//Response Code
	//private String responseCode = null;		//Valid values: success and failure.
	
	public Developer(){
	}
	
	public Developer(HashMap params){
		if(params != null){
			loginId 		= (String) params.get(loginId);
			password 		= (String) params.get(password);
			statusEnabled 	= "enabled".equalsIgnoreCase((String)params.get("status"))? true : false;
			developerName 	= (String) params.get(developerName);
			contactName 	= (String) params.get(contactName);
			companyUrl 		= (String) params.get(companyUrl);
			phoneNumber 	= (String) params.get(phoneNumber);
			faxNumber 		= (String) params.get(faxNumber);
			email 			= (String) params.get(email);
			street1			= (String) params.get(street1);
			street2 		= (String) params.get(street2);
			city 			= (String) params.get(city);
			state 			= (String) params.get(state);
			postalCode 		= (String) params.get(postalCode);
			countryCode 	= (String) params.get(countryCode);
		}
	}
	
	
	public String getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}

	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isStatusEnabled() {
		return statusEnabled;
	}
	public void setStatusEnabled(boolean statusEnabled) {
		this.statusEnabled = statusEnabled;
	}
	public String getDeveloperName() {
		return developerName;
	}
	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String[] getPlanIds() {
		return planIds;
	}
	public void setPlanIds(String[] planIds) {
		this.planIds = planIds;
	}
	public String[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	
	public Hashtable<String, String> getRequestParameters() throws ThirdpartyServiceException {
		if ( log.isDebugEnabled() )
			log.debug("Enter getRequestParameters ");
		//HashMap<String, HashMap<String, String>> developer = new HashMap<String, HashMap<String, String>>();
		Hashtable<String, String> params = new Hashtable<String, String>();

		if( StringFuncs.isNullOrEmpty(loginId))
			throw new ThirdpartyServiceException("Login Id is required");
		params.put("loginId", loginId);
		
		if( StringFuncs.isNullOrEmpty(password))
			throw new ThirdpartyServiceException("Password is required");
		params.put("password", password);
		
		if(statusEnabled)
			params.put("status", "enabled");
		else
			params.put("status", "disabled");
		
		if( StringFuncs.isNullOrEmpty(developerName))
			throw new ThirdpartyServiceException("Developer Name is required");
		params.put("developerName", developerName);
		
		if( StringFuncs.isNullOrEmpty(contactName))
			throw new ThirdpartyServiceException("Contact Name is required");
		params.put("contactName", contactName);
		
		if( StringFuncs.isNullOrEmpty(companyUrl))
			throw new ThirdpartyServiceException("Company URL is required");
		params.put("companyUrl", companyUrl);
		
		
		if(StringFuncs.isNullOrEmpty(phoneNumber) == false )
			params.put("phoneNumber", phoneNumber);
		
		if(StringFuncs.isNullOrEmpty(faxNumber) == false )
			params.put("faxNumber", faxNumber);
		
		if( StringFuncs.isNullOrEmpty(email))
			throw new ThirdpartyServiceException("Email is required");
		params.put("email", email);
		
		if(StringFuncs.isNullOrEmpty(street1) == false )
			params.put("street1", street1);
		
		if(StringFuncs.isNullOrEmpty(street2) == false )
			params.put("street2", street2);
		
		if(StringFuncs.isNullOrEmpty(city) == false )
			params.put("city", city);
		
		if(StringFuncs.isNullOrEmpty(state) == false )
			params.put("state", state);
		
		if(StringFuncs.isNullOrEmpty(postalCode) == false )
			params.put("postalCode", postalCode);
		
		if(StringFuncs.isNullOrEmpty(countryCode) == false )
			params.put("countryCode", countryCode);
		
		if(StringFuncs.isNullOrEmpty(street2) == false )
			params.put("street2", street2);
	
		if ( log.isDebugEnabled() )
		{
			log.debug("VDS Call Request Parameters");
			log.debug("		loginId =" + loginId);
			log.debug("		password =" + password);
			log.debug("		status =" + "disabled");
			log.debug("		developerName =" + developerName);
			log.debug("		contactName =" + contactName);
			log.debug("		companyUrl =" + companyUrl);
			log.debug("		phoneNumber =" + phoneNumber);
			log.debug("		faxNumber =" + faxNumber);
			log.debug("		email =" + email);
			log.debug("		street1 =" + street1);
			log.debug("		street2 =" + street2);
			log.debug("		city =" + city);
			log.debug("		state =" + state);
			log.debug("		postalCode =" + postalCode);
			log.debug("		countryCode =" + countryCode);
		}
		
		if ( log.isDebugEnabled() )
			log.debug("Exit getRequestParameters ");
	
		return params;
	}
	
		
	public String toString(){
		StringBuffer sb = new StringBuffer("Developer: { ")
			.append("loginId=").append(loginId)
			.append(", ").append("password=").append(password)
			.append(", ").append("statusEnabled=").append(statusEnabled)
			.append(", ").append("developerName=").append(developerName)
			.append(", ").append("contactName=").append(contactName)
			.append(", ").append("companyUrl=").append(companyUrl)
			.append(", ").append("phoneNumber=").append(phoneNumber)			
			.append(", ").append("faxNumber=").append(faxNumber)
			.append(", ").append("email=").append(email)			
			.append(", ").append("street1=").append(street1)
			.append(", ").append("street2=").append(street2)
			.append(", ").append("city=").append(city)
			.append(", ").append("state=").append(state)			
			.append(", ").append("postalCode=").append(postalCode)
			.append(", ").append("countryCode=").append(countryCode)			
			.append(" }");
		return sb.toString();
	}
	
}
