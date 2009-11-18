package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VzdnUsers implements java.io.Serializable, Comparable<VzdnUsers> {

	private Integer userId;
	private VzdnTypes userType;
	private VzdnTypes statusType;
	private String userName;
	private String password;
	private String title;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String securityQuestion;
	private String securityAnswer;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String faxNumber;
	private String mobileNumber;
	private String newsletterOptOut;
	private Set<VzdnSysRoles> roles = new HashSet<VzdnSysRoles>(0);
	private Set<VzdnEventNotifications> notifications = new HashSet<VzdnEventNotifications>(0);
	private Integer pageSize;
	private String gtmCompanyId;
	private String country;
	
	private Set<VzdnSubMenus> myInfoMenus, communityMenus,gotoMarketMenus,managementMenus;
	
	
	
	
	//new field added
	private String managerOrganization;
	
	/** default constructor */
	public VzdnUsers() {
		userType = new VzdnTypes();
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public VzdnTypes getUserType() {
		return this.userType;
	}

	public void setUserType(VzdnTypes userType) {
		this.userType = userType;
	}

	public VzdnTypes getStatusType() {
		return this.statusType;
	}

	public void setStatusType(VzdnTypes statusType) {
		this.statusType = statusType;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSecurityQuestion() {
		return this.securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return this.securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNewsletterOptOut() {
		return this.newsletterOptOut;
	}

	public void setNewsletterOptOut(String newsletterOptOut) {
		this.newsletterOptOut = newsletterOptOut;
	}

	public Set<VzdnSysRoles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<VzdnSysRoles> roles) {
		this.roles = roles;
	}

	public String getManagerOrganization() {
		return managerOrganization;
	}

	public void setManagerOrganization(String managerOrganization) {
		this.managerOrganization = managerOrganization;
	}

	public Set<VzdnSubMenus> getMyInfoMenus() {
		return myInfoMenus;
	}

	public void setMyInfoMenus(Set<VzdnSubMenus> myInfoMenus) {
		this.myInfoMenus = myInfoMenus;
	}

	public Set<VzdnSubMenus> getCommunityMenus() {
		return communityMenus;
	}

	public void setCommunityMenus(Set<VzdnSubMenus> communityMenus) {
		this.communityMenus = communityMenus;
	}

	public Set<VzdnSubMenus> getGotoMarketMenus() {
		return gotoMarketMenus;
	}

	public void setGotoMarketMenus(Set<VzdnSubMenus> gotoMarketMenus) {
		this.gotoMarketMenus = gotoMarketMenus;
	}

	public Set<VzdnSubMenus> getManagementMenus() {
		return managementMenus;
	}

	public void setManagementMenus(Set<VzdnSubMenus> managementMenus) {
		this.managementMenus = managementMenus;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Set<VzdnEventNotifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<VzdnEventNotifications> notifications) {
		this.notifications = notifications;
	}
	
	public int compareTo(VzdnUsers user) {
		return userName.compareToIgnoreCase(user.getUserName());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final VzdnUsers other = (VzdnUsers) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}