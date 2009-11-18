package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnEvents implements java.io.Serializable {

	// Fields

	private Integer eventId;
	private String eventName;
	private String eventDesc;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastUpdatedDate;
	private String lastUpdatedBy;
	private Set<VzdnEventHandlers> handlers = new HashSet<VzdnEventHandlers>(0);
	private Set<VzdnPlaceHolders> placeHolders = new HashSet<VzdnPlaceHolders>(0);
	private Set<VzdnEventNotifications> notifications = new HashSet<VzdnEventNotifications>(0);

	// Constructors

	/** default constructor */
	public VzdnEvents() {
	}

	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return this.eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventDesc() {
		return this.eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
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

	public Set<VzdnEventHandlers> getHandlers() {
		return this.handlers;
	}

	public void setHandlers(Set<VzdnEventHandlers> handlers) {
		this.handlers = handlers;
	}

	public Set<VzdnPlaceHolders> getPlaceHolders() {
		return this.placeHolders;
	}

	public void setPlaceHolders(Set<VzdnPlaceHolders> placeHolders) {
		this.placeHolders = placeHolders;
	}

	public Set<VzdnEventNotifications> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<VzdnEventNotifications> notifications) {
		this.notifications = notifications;
	}	

}