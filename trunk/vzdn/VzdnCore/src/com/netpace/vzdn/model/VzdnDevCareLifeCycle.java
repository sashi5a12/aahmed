package com.netpace.vzdn.model;

import java.sql.Timestamp;


public class VzdnDevCareLifeCycle implements java.io.Serializable {

	private Integer commentId;
	private VzdnDevCareIssues issue;
	private VzdnUsers user;
	private VzdnTypes status;
	private String comments;
	private Integer createdBy;
	private Timestamp createdDate;
	private Integer updatedBy;
	private Timestamp updatedDate;

	// Constructors

	/** default constructor */
	public VzdnDevCareLifeCycle() {
	}

	public Integer getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public VzdnDevCareIssues getIssue() {
		return this.issue;
	}

	public void setIssue(VzdnDevCareIssues issue) {
		this.issue = issue;
	}

	public VzdnUsers getUser() {
		return this.user;
	}

	public void setUser(VzdnUsers user) {
		this.user = user;
	}

	public VzdnTypes getStatus() {
		return this.status;
	}

	public void setStatus(VzdnTypes status) {
		this.status = status;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

}