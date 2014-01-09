package com.netpace.device.vo.product;

import java.io.Serializable;


public class AttachmentFile implements Serializable {
	
	private static final long serialVersionUID = -2596340661740144092L;
	private Integer mediaId;
	private String fileName;
	private String fileFullName;
	private String fileType;
	private Integer fileSize;
	private String fileExtension;
	private Integer companyId;
	private Integer productId;
	
	public Integer getMediaId() {
		return mediaId;
	}
	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
	}
	public String getFileFullName() {
		return fileFullName;
	}
	public void setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}
