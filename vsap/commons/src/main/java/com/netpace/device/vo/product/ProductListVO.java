package com.netpace.device.vo.product;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProductListVO implements Serializable {
	
	private static final long serialVersionUID = 7291569296965018460L;
	
	private Integer productId;
    private String status;
    private String productType;
    private String productName;
    private Timestamp submittedDate;
    private String productCategory;
    private String companyName;
    private Boolean allowDelete;
    private Boolean allowEdit;
    private String workFlowSteps;
    
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Timestamp getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Boolean getAllowDelete() {
		return allowDelete;
	}
	public void setAllowDelete(Boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public Boolean getAllowEdit() {
		return allowEdit;
	}
	public void setAllowEdit(Boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	public String getWorkFlowSteps() {
		return workFlowSteps;
	}
	public void setWorkFlowSteps(String workFlowSteps) {
		this.workFlowSteps = workFlowSteps;
	}
	@Override
	public String toString() {
		return "\nProductListVO [productId=" + productId + ", status=" + status
				+ ", productType=" + productType + ", productName="
				+ productName + ", submittedDate=" + submittedDate
				+ ", productCategory=" + productCategory 
				+ ", workFlowSteps=" + workFlowSteps +"]";
	}

    
    
}
