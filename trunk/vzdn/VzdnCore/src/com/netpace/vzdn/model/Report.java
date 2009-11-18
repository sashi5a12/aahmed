package com.netpace.vzdn.model;

import java.sql.Timestamp;
import java.util.Date;


public class Report {

	private Integer reportId;
	private String displayName;
	private String reportAction;
	private VzdnSysPrivileges privilege;
	private Character pdfExport;
	private Character cvsExport;
	private Character rtfExport;
	private Character htmlExport;
	private String createdBy;
	private Timestamp createdDate;
	private String lastUpdatedBy;
	private Timestamp lastUpdatedDate;
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getReportAction() {
		return reportAction;
	}
	public void setReportAction(String reportAction) {
		this.reportAction = reportAction;
	}
	public VzdnSysPrivileges getPrivilege() {
		return privilege;
	}
	public void setPrivilege(VzdnSysPrivileges privilege) {
		this.privilege = privilege;
	}
	public Character getPdfExport() {
		return pdfExport;
	}
	public void setPdfExport(Character pdfExport) {
		this.pdfExport = pdfExport;
	}
	public Character getCvsExport() {
		return cvsExport;
	}
	public void setCvsExport(Character cvsExport) {
		this.cvsExport = cvsExport;
	}
	public Character getRtfExport() {
		return rtfExport;
	}
	public void setRtfExport(Character rtfExport) {
		this.rtfExport = rtfExport;
	}
	public Character getHtmlExport() {
		return htmlExport;
	}
	public void setHtmlExport(Character htmlExport) {
		this.htmlExport = htmlExport;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
	
	
}
