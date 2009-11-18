package com.netpace.vzdn.model;

import java.util.List;
import java.util.Set;

public class VzdnCredentials {
	
	private String managerRoles;
	private String lastName;
	private String userName;
	private String firstName;
	private String password;
	private String title;
	private int userTypeId;
	
	private String phone;
	private String fax;
	private String mobile;	
	private String gtmCompanyId;
	private String country;
	
	private List<VzdnSysRoles> userRoles;
	private Set<VzdnSysPrivileges> privileges;
	private Set<VzdnSubMenus> communityMenus;
	private Set<VzdnSubMenus> gotoMarketMenus;
	private Set<VzdnSubMenus> managementMenus;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setUserTypeId(int userTypeId){
		this.userTypeId = userTypeId;
	}
	
	public int getUserTypeId(){
		return userTypeId;
	}
	public List<VzdnSysRoles> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<VzdnSysRoles> userRoles) {
		this.userRoles = userRoles;
	}
	public Set<VzdnSysPrivileges> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<VzdnSysPrivileges> privileges) {
		this.privileges = privileges;
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

}
