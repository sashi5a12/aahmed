package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnDevCareCategories implements java.io.Serializable {

	private Integer categoryId;
	private String categoryName;
	private Integer createdBy;
	private Timestamp createdDate;
	private Integer updatedBy;
	private Timestamp updatedDate;
	private Set<VzdnDevCareSubCategories> subCategories = new HashSet<VzdnDevCareSubCategories>(0);
	private Set<VzdnDevCareIssues> issues = new HashSet<VzdnDevCareIssues>(0);

	/** default constructor */
	public VzdnDevCareCategories() {
	}

	/** minimal constructor */
	public VzdnDevCareCategories(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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

	public Set<VzdnDevCareSubCategories> getSubCategories() {
		return this.subCategories;
	}

	public void setSubCategories(Set<VzdnDevCareSubCategories> subCategories) {
		this.subCategories = subCategories;
	}

	public Set<VzdnDevCareIssues> getIssues() {
		return this.issues;
	}

	public void setIssues(Set<VzdnDevCareIssues> issues) {
		this.issues = issues;
	}

}