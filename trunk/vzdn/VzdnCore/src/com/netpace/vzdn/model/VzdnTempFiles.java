package com.netpace.vzdn.model;

import java.sql.Blob;
import java.sql.Timestamp;

public class VzdnTempFiles implements java.io.Serializable {

	private Integer tempFileId;
	private String sessionId;
	private Blob tempFile;
	private String tempFileName;
	private String tempFileContentType;
	private String createdBy;
	private Timestamp createdDate;
	private String tempFileComments;

	/** default constructor */
	public VzdnTempFiles() {
	}

	public Integer getTempFileId() {
		return this.tempFileId;
	}

	public void setTempFileId(Integer tempFileId) {
		this.tempFileId = tempFileId;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Blob getTempFile() {
		return this.tempFile;
	}

	public void setTempFile(Blob tempFile) {
		this.tempFile = tempFile;
	}

	public String getTempFileName() {
		return this.tempFileName;
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	public String getTempFileContentType() {
		return this.tempFileContentType;
	}

	public void setTempFileContentType(String tempFileContentType) {
		this.tempFileContentType = tempFileContentType;
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

	public String getTempFileComments() {
		return this.tempFileComments;
	}

	public void setTempFileComments(String tempFileComments) {
		this.tempFileComments = tempFileComments;
	}

}