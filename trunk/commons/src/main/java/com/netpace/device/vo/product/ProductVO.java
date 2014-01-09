package com.netpace.device.vo.product;

import com.netpace.device.vo.Record;

public class ProductVO extends Record{

	private static final long serialVersionUID = 3159165441969726761L;
	
	private Integer companyId;
	private String companyName;
    private String worklflowStep;
    private String productStatus;
    private String productType;
    
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWorklflowStep() {
		return worklflowStep;
	}
	public void setWorklflowStep(String worklflowStep) {
		this.worklflowStep = worklflowStep;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}
