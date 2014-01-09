package com.netpace.device.vo.product;

import java.io.Serializable;

public class FileUploadVO implements Serializable{

	private static final long serialVersionUID = -8906455623545455663L;
	private Integer productId;
	private Integer tab;
	private String fileType;  
	private byte[] data;
	private String filePath;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getTab() {
		return tab;
	}
	public void setTab(Integer tab) {
		this.tab = tab;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
