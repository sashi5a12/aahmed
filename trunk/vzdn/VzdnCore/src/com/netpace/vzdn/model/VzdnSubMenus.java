package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


public class VzdnSubMenus implements java.io.Serializable, Comparable<VzdnSubMenus> {

	private Integer subMenuId;
	private VzdnMenus vzdnMenus;
	private String subMenuName;
	private String subMenuUrl;
	private String imageName;
	private Integer sortOrder;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private Set<VzdnSysPrivileges> privileges = new HashSet<VzdnSysPrivileges>(0);

	/** default constructor */
	public VzdnSubMenus() {
	}

	public Integer getSubMenuId() {
		return this.subMenuId;
	}

	public void setSubMenuId(Integer subMenuId) {
		this.subMenuId = subMenuId;
	}

	public VzdnMenus getVzdnMenus() {
		return this.vzdnMenus;
	}

	public void setVzdnMenus(VzdnMenus vzdnMenus) {
		this.vzdnMenus = vzdnMenus;
	}

	public String getSubMenuName() {
		return this.subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}

	public String getSubMenuUrl() {
		return this.subMenuUrl;
	}

	public void setSubMenuUrl(String subMenuUrl) {
		this.subMenuUrl = subMenuUrl;
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

	public Set<VzdnSysPrivileges> getPrivileges() {
		return this.privileges;
	}

	public void setPrivileges(Set<VzdnSysPrivileges> privileges) {
		this.privileges = privileges;
	}
	
	 public int compareTo(VzdnSubMenus menu) {
		    return sortOrder.compareTo(menu.sortOrder);		    
	 }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((subMenuId == null) ? 0 : subMenuId.hashCode());
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
		final VzdnSubMenus other = (VzdnSubMenus) obj;
		if (subMenuId == null) {
			if (other.subMenuId != null)
				return false;
		} else if (!subMenuId.equals(other.subMenuId))
			return false;
		return true;
	}

}