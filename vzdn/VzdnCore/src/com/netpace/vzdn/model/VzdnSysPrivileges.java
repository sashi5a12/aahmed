package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnSysPrivileges implements java.io.Serializable, Comparable<VzdnSysPrivileges> {

	private Integer privilegeId;
	private VzdnSubMenus subMenu;
	private VzdnMenus menu;
	private String privilegeName;
	private String privilegeDescription;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private String privilegeKey;
	private String availableTo;
	private Set<VzdnSysRoles> roles = new HashSet<VzdnSysRoles>(0);

	/** default constructor */
	public VzdnSysPrivileges() {
	}

	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public VzdnSubMenus getSubMenu() {
		return this.subMenu;
	}

	public void setSubMenu(VzdnSubMenus subMenu) {
		this.subMenu = subMenu;
	}

	public VzdnMenus getMenu() {
		return this.menu;
	}

	public void setMenu(VzdnMenus menu) {
		this.menu = menu;
	}

	public String getPrivilegeName() {
		return this.privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeDescription() {
		return this.privilegeDescription;
	}

	public void setPrivilegeDescription(String privilegeDescription) {
		this.privilegeDescription = privilegeDescription;
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

	public String getPrivilegeKey() {
		return this.privilegeKey;
	}

	public void setPrivilegeKey(String privilegeKey) {
		this.privilegeKey = privilegeKey;
	}

	public String getAvailableTo() {
		return this.availableTo;
	}

	public void setAvailableTo(String availableTo) {
		this.availableTo = availableTo;
	}

	public Set<VzdnSysRoles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<VzdnSysRoles> roles) {
		this.roles = roles;
	}
	
	public int compareTo(VzdnSysPrivileges pvlg) {
		return privilegeKey.compareToIgnoreCase(pvlg.getPrivilegeKey());
	}

}