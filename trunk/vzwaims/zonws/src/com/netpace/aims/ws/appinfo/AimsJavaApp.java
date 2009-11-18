package com.netpace.aims.ws.appinfo;

import java.lang.Long;
import java.lang.String;;

public class AimsJavaApp 
{	
	private Long javaAppsId; 
	private Long contractId; 
	private Long contentRatingTypeId; 
	private Long contentTypeId; 
	private Long taxCategoryCodeId;
	private String enterpriseApp; 
	private String enterpriseId; 
	private Long ringNumber;
	
	public Long getContentRatingTypeId() {
		return contentRatingTypeId;
	}
	public void setContentRatingTypeId(Long contentRatingTypeId) {
		this.contentRatingTypeId = contentRatingTypeId;
	}
	public Long getContentTypeId() {
		return contentTypeId;
	}
	public void setContentTypeId(Long contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getEnterpriseApp() {
		return enterpriseApp;
	}
	public void setEnterpriseApp(String enterpriseApp) {
		this.enterpriseApp = enterpriseApp;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Long getJavaAppsId() {
		return javaAppsId;
	}
	public void setJavaAppsId(Long javaAppsId) {
		this.javaAppsId = javaAppsId;
	}
	public Long getRingNumber() {
		return ringNumber;
	}
	public void setRingNumber(Long ringNumber) {
		this.ringNumber = ringNumber;
	}
	public Long getTaxCategoryCodeId() {
		return taxCategoryCodeId;
	}
	public void setTaxCategoryCodeId(Long taxCategoryCodeId) {
		this.taxCategoryCodeId = taxCategoryCodeId;
	}
}
