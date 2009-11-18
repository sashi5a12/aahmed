package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnSysRoles implements java.io.Serializable, Comparable<VzdnSysRoles>{

	private Integer roleId;
	private String roleName;
	private String roleDescription;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private Set<VzdnSysPrivileges> privileges = new HashSet<VzdnSysPrivileges>(0);
	private Set<VzdnUsers> users = new HashSet<VzdnUsers>(0);
	private Set<VzdnReports> reports = new HashSet<VzdnReports>(0);
	private Set<VzdnEventNotifications> notifications = new HashSet<VzdnEventNotifications>(0);
	
	private Integer vzdnRoleMappingId ;

	private VzdnTypes vzdnTypes;
	
	
	
	
	
	
	
	public VzdnTypes getVzdnTypes() {
		return vzdnTypes;
	}

	public void setVzdnTypes(VzdnTypes vzdnTypes) {
		this.vzdnTypes = vzdnTypes;
	}

	/** default constructor */
	public VzdnSysRoles() {
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
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

	public Set<VzdnSysPrivileges> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(Set<VzdnSysPrivileges> privileges) {
		this.privileges = privileges;
	}

	public Set<VzdnUsers> getUsers() {
		return this.users;
	}

	public void setUsers(Set<VzdnUsers> users) {
		this.users = users;
	}

	public Set<VzdnReports> getReports() {
		return this.reports;
	}

	public void setReports(Set<VzdnReports> reports) {
		this.reports = reports;
	}

	public Integer getVzdnRoleMappingId() {
		return vzdnRoleMappingId;
	}

	public void setVzdnRoleMappingId(Integer vzdnRoleMappingId) {
		this.vzdnRoleMappingId = vzdnRoleMappingId;
	}

	public Set<VzdnEventNotifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<VzdnEventNotifications> notifications) {
		this.notifications = notifications;
	}
	
	public int compareTo(VzdnSysRoles role) {
		    //return sortOrder.compareTo(menu.sortOrder);
		return roleName.compareToIgnoreCase(role.getRoleName());
	 }

	

}