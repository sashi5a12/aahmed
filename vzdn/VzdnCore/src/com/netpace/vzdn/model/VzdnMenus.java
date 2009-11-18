package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnMenus implements java.io.Serializable{

	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private String imageName;
	private Integer sortOrder;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private Set<VzdnSubMenus> subMenus = new HashSet<VzdnSubMenus>(0);
	private Set<VzdnSysPrivileges> privileges = new HashSet<VzdnSysPrivileges>(0);

	// Constructors

	/** default constructor */
	public VzdnMenus() {
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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

	public Set<VzdnSubMenus> getSubMenus() {
		return this.subMenus;
	}

	public void setSubMenus(Set<VzdnSubMenus> subMenus) {
		this.subMenus = subMenus;
	}

	public Set<VzdnSysPrivileges> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(Set<VzdnSysPrivileges> privileges) {
		this.privileges = privileges;
	}

}