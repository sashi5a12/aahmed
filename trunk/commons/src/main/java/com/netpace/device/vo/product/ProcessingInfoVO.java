package com.netpace.device.vo.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.CommentVO;
import com.netpace.device.vo.Record;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;

public class ProcessingInfoVO extends ProductVO{

	private static final long serialVersionUID = 5103956238540740934L;

	private String worklflowStep;
    private String btnType;
    private String sectionName;

    private Integer processId;
    private String productName;
    private Integer workflowId;
    
    private String ringAssociation;
    private String emailText;
    
    private AttachmentFile testing_spreadsheet;
    private AttachmentFile product_label;
    private AttachmentFile sustainability_disclosure;
    private AttachmentFile packaging_label;
    private String aggreeChkbox;
    private String agreementAcceptTitle;

    private String eccn;
    private String ccats;
    private AttachmentFile pdf_sample_product;
    
    private List<WorkItem> workItems;
    
	private String deviceMarketingInputStatus; 
	private String productInfoInputStatus; 
	private String exportComplianceInputStatus; 
	private String uploadPdfInputStatus;  
	
	private List<CommentVO> comments;
	
	public String getBtnType() {
		return btnType;
	}
	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}
	public Integer getProcessId() {
		return processId;
	}
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRingAssociation() {
		return ringAssociation;
	}
	public void setRingAssociation(String ringAssociation) {
		this.ringAssociation = ringAssociation;
	}
	public String getEmailText() {
		return emailText;
	}
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	
	/*public String getDeviceMarketingEmailText() {
		return deviceMarketingEmailText;
	}
	public void setDeviceMarketingEmailText(String deviceMarketingEmailText) {
		this.deviceMarketingEmailText = deviceMarketingEmailText;
	}*/
	public AttachmentFile getTesting_spreadsheet() {
		return testing_spreadsheet;
	}
	public void setTesting_spreadsheet(AttachmentFile testing_spreadsheet) {
		this.testing_spreadsheet = testing_spreadsheet;
	}
	public AttachmentFile getProduct_label() {
		return product_label;
	}
	public void setProduct_label(AttachmentFile product_label) {
		this.product_label = product_label;
	}
	public AttachmentFile getSustainability_disclosure() {
		return sustainability_disclosure;
	}
	public void setSustainability_disclosure(
			AttachmentFile sustainability_disclosure) {
		this.sustainability_disclosure = sustainability_disclosure;
	}
	public AttachmentFile getPackaging_label() {
		return packaging_label;
	}
	public void setPackaging_label(AttachmentFile packaging_label) {
		this.packaging_label = packaging_label;
	}
	public String getAggreeChkbox() {
		return aggreeChkbox;
	}
	public void setAggreeChkbox(String aggreeChkbox) {
		this.aggreeChkbox = aggreeChkbox;
	}
	public String getAgreementAcceptTitle() {
		return agreementAcceptTitle;
	}
	public void setAgreementAcceptTitle(String agreementAcceptTitle) {
		this.agreementAcceptTitle = agreementAcceptTitle;
	}
	public String getEccn() {
		return eccn;
	}
	public void setEccn(String eccn) {
		this.eccn = eccn;
	}
	public String getCcats() {
		return ccats;
	}
	public void setCcats(String ccats) {
		this.ccats = ccats;
	}
	public AttachmentFile getPdf_sample_product() {
		return pdf_sample_product;
	}
	public void setPdf_sample_product(AttachmentFile pdf_sample_product) {
		this.pdf_sample_product = pdf_sample_product;
	}
	public List<WorkItem> getWorkItems() {
		return workItems;
	}
	public void setWorkItems(List<WorkItem> workItems) {
		this.workItems = workItems;
	}
	public String getDeviceMarketingInputStatus() {
		return deviceMarketingInputStatus;
	}
	public void setDeviceMarketingInputStatus(String deviceMarketingInputStatus) {
		this.deviceMarketingInputStatus = deviceMarketingInputStatus;
	}
	public String getProductInfoInputStatus() {
		return productInfoInputStatus;
	}
	public void setProductInfoInputStatus(String productInfoInputStatus) {
		this.productInfoInputStatus = productInfoInputStatus;
	}
	public String getExportComplianceInputStatus() {
		return exportComplianceInputStatus;
	}
	public void setExportComplianceInputStatus(String exportComplianceInputStatus) {
		this.exportComplianceInputStatus = exportComplianceInputStatus;
	}
	public String getUploadPdfInputStatus() {
		return uploadPdfInputStatus;
	}
	public void setUploadPdfInputStatus(String uploadPdfInputStatus) {
		this.uploadPdfInputStatus = uploadPdfInputStatus;
	}
	public Integer getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Integer workflowId) {
		this.workflowId = workflowId;
	}
	public WorkItem getWorkItem(){
		WorkflowVO workflow=new WorkflowVO();
		WorkItem workitem=new WorkItem();
		
		workflow.setId(this.getWorkflowId());
		workitem.setWorkflow(workflow);
		workitem.setTitle(this.getSectionName());
		workitem.setDecision(this.getBtnType());
		workitem.setCommentText(this.getEmailText());
		return workitem;
	}
	public String getWorklflowStep() {
		return worklflowStep;
	}
	public void setWorklflowStep(String worklflowStep) {
		this.worklflowStep = worklflowStep;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public List<CommentVO> getComments() {
		return comments;
	}
	public void setComments(List<CommentVO> comments) {
		this.comments = comments;
	}
	
}
