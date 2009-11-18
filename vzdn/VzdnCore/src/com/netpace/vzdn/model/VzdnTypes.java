package com.netpace.vzdn.model;

import java.sql.Timestamp;

public class VzdnTypes implements java.io.Serializable {

	private Integer typeId;
	private VzdnTypeDefs vzdnTypeDefs;
	private String typeValue;
	private String description;
	private Integer sortOrder;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;

	/** default constructor */
	public VzdnTypes() {
	}

	/** minimal constructor */
	public VzdnTypes(VzdnTypeDefs vzdnTypeDefs, String typeValue,
			Integer sortOrder) {
		this.vzdnTypeDefs = vzdnTypeDefs;
		this.typeValue = typeValue;
		this.sortOrder = sortOrder;
	}
	
	public VzdnTypes(Integer typeId){
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public VzdnTypeDefs getVzdnTypeDefs() {
		return this.vzdnTypeDefs;
	}

	public void setVzdnTypeDefs(VzdnTypeDefs vzdnTypeDefs) {
		this.vzdnTypeDefs = vzdnTypeDefs;
	}

	public String getTypeValue() {
		return this.typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}