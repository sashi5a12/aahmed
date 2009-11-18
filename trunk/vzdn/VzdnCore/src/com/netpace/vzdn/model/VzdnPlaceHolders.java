package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnPlaceHolders implements java.io.Serializable {

	private Integer placeHolderId;
	private String placeHolderDisplayName;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastUpdatedDate;
	private String lastUpdatedBy;
	private Set<VzdnEvents> events = new HashSet<VzdnEvents>(0);

	/** default constructor */
	public VzdnPlaceHolders() {
	}

	public Integer getPlaceHolderId() {
		return this.placeHolderId;
	}

	public void setPlaceHolderId(Integer placeHolderId) {
		this.placeHolderId = placeHolderId;
	}

	public String getPlaceHolderDisplayName() {
		return this.placeHolderDisplayName;
	}

	public void setPlaceHolderDisplayName(String placeHolderDisplayName) {
		this.placeHolderDisplayName = placeHolderDisplayName;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Set<VzdnEvents> getEvents() {
		return this.events;
	}

	public void setEvents(Set<VzdnEvents> events) {
		this.events = events;
	}

}