package com.netpace.aims.ws.appinfo;

public class AimsTaxCategoryCode {
	private Long taxCategoryCodeId;
    private String taxCategoryCode;
    private String taxCategoryCodeDesc;
    
	public String getTaxCategoryCode() {
		return taxCategoryCode;
	}
	public void setTaxCategoryCode(String taxCategoryCode) {
		this.taxCategoryCode = taxCategoryCode;
	}
	public Long getTaxCategoryCodeId() {
		return taxCategoryCodeId;
	}
	public void setTaxCategoryCodeId(Long taxCategoryCodeId) {
		this.taxCategoryCodeId = taxCategoryCodeId;
	}
	public String getTaxCategoryCodeDesc() {
		return taxCategoryCodeDesc;
	}
	public void setTaxCategoryCodeDesc(String taxCategoryCodeDesc) {
		this.taxCategoryCodeDesc = taxCategoryCodeDesc;
	}

	
}
