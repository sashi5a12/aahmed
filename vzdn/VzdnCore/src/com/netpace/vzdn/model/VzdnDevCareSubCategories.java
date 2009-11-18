package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnDevCareSubCategories implements java.io.Serializable {

	private Integer subCategoryId;
	private VzdnDevCareCategories vzdnDevCareCategories;
	private String subCategoryName;
	private Integer createdBy;
	private Integer updatedBy;
	private Timestamp updatedDate;
	private Timestamp createdDate;
	private Set<VzdnDevCareIssues> issues = new HashSet<VzdnDevCareIssues>(0);

	/** default constructor */
	public VzdnDevCareSubCategories() {
	}

	/** minimal constructor */
	public VzdnDevCareSubCategories(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Integer getSubCategoryId() {
		return this.subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public VzdnDevCareCategories getVzdnDevCareCategories() {
		return this.vzdnDevCareCategories;
	}

	public void setVzdnDevCareCategories(
			VzdnDevCareCategories vzdnDevCareCategories) {
		this.vzdnDevCareCategories = vzdnDevCareCategories;
	}

	public String getSubCategoryName() {
		return this.subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Set<VzdnDevCareIssues> getIssues() {
		return this.issues;
	}

	public void setIssues(Set<VzdnDevCareIssues> issues) {
		this.issues = issues;
	}

}