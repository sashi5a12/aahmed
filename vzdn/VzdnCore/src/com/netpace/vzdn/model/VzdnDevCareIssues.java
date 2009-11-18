package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnDevCareIssues implements java.io.Serializable {

	private Integer issueId;
	private VzdnDevCareCategories category;
	private VzdnDevCareSubCategories subCategory;
	private VzdnTypes vzdnTypes;
	private String title;
	private String description;
	private String ticketId;
	private Integer userId;
	private String attachment;
	private String attachmentName;
	private Integer createdBy;
	private Timestamp createdDate;
	private Integer updatedBy;
	private Timestamp updatedDate;
	private String attachmentType;
	private Set<VzdnDevCareLifeCycle> lifeCycles = new HashSet<VzdnDevCareLifeCycle>(0);

	// Constructors

	/** default constructor */
	public VzdnDevCareIssues() {
	}

	/** minimal constructor */
	public VzdnDevCareIssues(String ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getIssueId() {
		return this.issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public VzdnDevCareCategories getCategory() {
		return this.category;
	}

	public void setCategory(VzdnDevCareCategories category) {
		this.category = category;
	}

	public VzdnDevCareSubCategories getSubCategory() {
		return this.subCategory;
	}

	public void setSubCategory(VzdnDevCareSubCategories subCategory) {
		this.subCategory = subCategory;
	}

	public VzdnTypes getVzdnTypes() {
		return this.vzdnTypes;
	}

	public void setVzdnTypes(VzdnTypes vzdnTypes) {
		this.vzdnTypes = vzdnTypes;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTicketId() {
		return this.ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentName() {
		return this.attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
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

	public String getAttachmentType() {
		return this.attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public Set<VzdnDevCareLifeCycle> getLifeCycles() {
		return this.lifeCycles;
	}

	public void setLifeCycles(Set<VzdnDevCareLifeCycle> lifeCycles) {
		this.lifeCycles = lifeCycles;
	}

}