package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnTypeDefs implements java.io.Serializable {

	private Integer typeDefId;
	private String typeName;
	private String typeDescription;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private Set<VzdnTypes> vzdnTypes = new HashSet<VzdnTypes>(0);

	/** default constructor */
	public VzdnTypeDefs() {
	}

	/** minimal constructor */
	public VzdnTypeDefs(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeDefId() {
		return this.typeDefId;
	}

	public void setTypeDefId(Integer typeDefId) {
		this.typeDefId = typeDefId;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
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

	public Set<VzdnTypes> getVzdnTypes() {
		return this.vzdnTypes;
	}

	public void setVzdnTypes(Set<VzdnTypes> vzdnTypes) {
		this.vzdnTypes = vzdnTypes;
	}

}