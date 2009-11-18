package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class VzdnReports implements java.io.Serializable {

	private Integer reportId;
	private String name;
	private String description;
	private String reportFile;
	private String pdfExport;
	private String cvsExport;
	private String rtfExport;
	private String htmlExport;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	private Set<VzdnSysRoles> roles = new HashSet<VzdnSysRoles>(0);

	// Constructors

	/** default constructor */
	public VzdnReports() {
	}

	public Integer getReportId() {
		return this.reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReportFile() {
		return this.reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public String getPdfExport() {
		return this.pdfExport;
	}

	public void setPdfExport(String pdfExport) {
		this.pdfExport = pdfExport;
	}

	public String getCvsExport() {
		return this.cvsExport;
	}

	public void setCvsExport(String cvsExport) {
		this.cvsExport = cvsExport;
	}

	public String getRtfExport() {
		return this.rtfExport;
	}

	public void setRtfExport(String rtfExport) {
		this.rtfExport = rtfExport;
	}

	public String getHtmlExport() {
		return this.htmlExport;
	}

	public void setHtmlExport(String htmlExport) {
		this.htmlExport = htmlExport;
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

	public Set<VzdnSysRoles> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<VzdnSysRoles> roles) {
		this.roles = roles;
	}

}