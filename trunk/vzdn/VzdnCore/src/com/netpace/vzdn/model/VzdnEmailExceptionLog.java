package com.netpace.vzdn.model;

import java.io.Serializable;
import java.sql.Blob;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class VzdnEmailExceptionLog extends BaseValueObject implements Serializable {

    /** identifier field */
    private java.lang.Long emailExceptionId;

    /** nullable persistent field */
    private Blob emailTo;

    /** nullable persistent field */
    private java.lang.String emailFrom;

    /** nullable persistent field */
    private java.lang.String emailSubject;

    /** nullable persistent field */
    private Blob emailCc;

    /** nullable persistent field */
    private Blob emailBcc;

    /** nullable persistent field */
    private Blob emailBody;

    /** nullable persistent field */
    private java.lang.Long emailAttachments;

    /** nullable persistent field */
    private Blob exceptionMessage;

    /** nullable persistent field */
    private Blob exceptionTrace;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** full constructor */
    public VzdnEmailExceptionLog(Blob emailTo, java.lang.String emailFrom, java.lang.String emailSubject, Blob emailCc, Blob emailBcc, Blob emailBody, java.lang.Long emailAttachments, Blob exceptionMessage, Blob exceptionTrace, java.util.Date createdDate) {
        this.emailTo = emailTo;
        this.emailFrom = emailFrom;
        this.emailSubject = emailSubject;
        this.emailCc = emailCc;
        this.emailBcc = emailBcc;
        this.emailBody = emailBody;
        this.emailAttachments = emailAttachments;
        this.exceptionMessage = exceptionMessage;
        this.exceptionTrace = exceptionTrace;
        this.createdDate = createdDate;
    }

    /** default constructor */
    public VzdnEmailExceptionLog() {
    }

    public java.lang.Long getEmailExceptionId() {
        return this.emailExceptionId;
    }

    public void setEmailExceptionId(java.lang.Long emailExceptionId) {
        this.emailExceptionId = emailExceptionId;
    }

    public Blob getEmailTo() {
        return this.emailTo;
    }

    public void setEmailTo(Blob emailTo) {
        this.emailTo = emailTo;
    }

    public java.lang.String getEmailFrom() {
        return this.emailFrom;
    }

    public void setEmailFrom(java.lang.String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public java.lang.String getEmailSubject() {
        return this.emailSubject;
    }

    public void setEmailSubject(java.lang.String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public Blob getEmailCc() {
        return this.emailCc;
    }

    public void setEmailCc(Blob emailCc) {
        this.emailCc = emailCc;
    }

    public Blob getEmailBcc() {
        return this.emailBcc;
    }

    public void setEmailBcc(Blob emailBcc) {
        this.emailBcc = emailBcc;
    }

    public Blob getEmailBody() {
        return this.emailBody;
    }

    public void setEmailBody(Blob emailBody) {
        this.emailBody = emailBody;
    }

    public java.lang.Long getEmailAttachments() {
        return this.emailAttachments;
    }

    public void setEmailAttachments(java.lang.Long emailAttachments) {
        this.emailAttachments = emailAttachments;
    }

    public Blob getExceptionMessage() {
        return this.exceptionMessage;
    }

    public void setExceptionMessage(Blob exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Blob getExceptionTrace() {
        return this.exceptionTrace;
    }

    public void setExceptionTrace(Blob exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }

    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("emailExceptionId", getEmailExceptionId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof VzdnEmailExceptionLog) ) return false;
        VzdnEmailExceptionLog castOther = (VzdnEmailExceptionLog) other;
        return new EqualsBuilder()
            .append(this.getEmailExceptionId(), castOther.getEmailExceptionId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getEmailExceptionId())
            .toHashCode();
    }

}
