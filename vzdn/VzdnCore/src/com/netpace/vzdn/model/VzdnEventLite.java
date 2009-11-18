package com.netpace.vzdn.model;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnEventLite extends BaseEvent implements Serializable {

    /** identifier field */
    private java.lang.Long eventId;

    /** nullable persistent field */
    private java.lang.String eventName;

    /** nullable persistent field */
    private java.lang.String eventDesc;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.util.Date lastUpdatedDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** persistent field */
    private Set eventPlaceHolders;

    /** persistent field */
    private Set eventHandlers;

    /** full constructor */
    public VzdnEventLite(java.lang.Long eventId, java.lang.String eventName, java.lang.String eventDesc, java.util.Date createdDate, java.lang.String createdBy, java.util.Date lastUpdatedDate, java.lang.String lastUpdatedBy, Set eventPlaceHolders, Set eventHandlers) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDesc = eventDesc;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.eventPlaceHolders = eventPlaceHolders;
        this.eventHandlers = eventHandlers;
    }

    /** default constructor */
    public VzdnEventLite() {
    }

    /** minimal constructor */
    public VzdnEventLite(java.lang.Long eventId, Set eventPlaceHolders, Set eventHandlers) {
        this.eventId = eventId;
        this.eventPlaceHolders = eventPlaceHolders;
        this.eventHandlers = eventHandlers;
    }

    public java.lang.Long getEventId() {
        return this.eventId;
    }

    public void setEventId(java.lang.Long eventId) {
        this.eventId = eventId;
    }

    public java.lang.String getEventName() {
        return this.eventName;
    }

    public void setEventName(java.lang.String eventName) {
        this.eventName = eventName;
    }

    public java.lang.String getEventDesc() {
        return this.eventDesc;
    }

    public void setEventDesc(java.lang.String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public java.util.Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.util.Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public java.lang.String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public java.util.Set getEventPlaceHolders() {
        return this.eventPlaceHolders;
    }

    public void setEventPlaceHolders(java.util.Set eventPlaceHolders) {
        this.eventPlaceHolders = eventPlaceHolders;
    }

    public java.util.Set getEventHandlers() {
        return this.eventHandlers;
    }

    public void setEventHandlers(java.util.Set eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("eventId", getEventId())
            .append("eventName", getEventName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnEventLite) ) return false;
        VzdnEventLite castOther = (VzdnEventLite) other;
        return new EqualsBuilder()
            .append(this.getEventId(), castOther.getEventId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEventId())
            .toHashCode();
    }

}
