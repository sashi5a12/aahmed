package com.netpace.device.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import com.netpace.device.vo.product.ProcessingInfoVO;

@Entity
@Table(name = "vap_product_process_info")
public class VapProductProcessInfo extends BaseEntity implements Serializable {  
    
	private static final long serialVersionUID = 5890974803482900364L;

	private Integer processId;
	private String ringAssociation;
	private String eccn;
	private String ccats;
	private String agreementAcceptTitle;
	private String deviceMarketingInputStatus; 
	private String productInfoInputStatus; 
	private String exportComplianceInputStatus; 
	private String uploadPdfInputStatus;   

//    VapProduct vapProduct;
    
    public VapProductProcessInfo(String ringAssociation, String eccn, String ccats) {
		this.ringAssociation = ringAssociation;
		this.eccn = eccn;
		this.ccats = ccats;
	}

    
	public VapProductProcessInfo() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id", nullable = false, unique = true)        
	public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    @Column(name = "ring_association")
    public String getRingAssociation() {
        return ringAssociation;
    }

    public void setRingAssociation(String ringAssociation) {
        this.ringAssociation = ringAssociation;
    }

    @Column(name = "eccn")
    public String getEccn() {
        return eccn;
    }

    public void setEccn(String eccn) {
        this.eccn = eccn;
    }

    @Column(name = "ccats")
    public String getCcats() {
        return ccats;
    }

    public void setCcats(String ccats) {
        this.ccats = ccats;
    }

    @Column(name = "agreement_accept_title")
    public String getAgreementAcceptTitle() {
		return agreementAcceptTitle;
	}

	public void setAgreementAcceptTitle(String agreementAcceptTitle) {
		this.agreementAcceptTitle = agreementAcceptTitle;
	}

	/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public VapProduct getVapProduct() {
		return vapProduct;
	}

	public void setVapProduct(VapProduct vapProduct) {
		this.vapProduct = vapProduct;
	}
*/
   
	public void copyProcessingInfoInVo(ProcessingInfoVO vo){
		vo.setProcessId(this.processId);
		vo.setRingAssociation(this.ringAssociation);
		vo.setEccn(this.eccn);
		vo.setCcats(this.ccats);
		vo.setAgreementAcceptTitle(this.agreementAcceptTitle);
		vo.setDeviceMarketingInputStatus(this.deviceMarketingInputStatus); 
		vo.setProductInfoInputStatus(this.productInfoInputStatus); 
		vo.setExportComplianceInputStatus(this.exportComplianceInputStatus); 
		vo.setUploadPdfInputStatus(this.uploadPdfInputStatus);  
	}

	public void copyProcessingInfoFromVo(ProcessingInfoVO vo){
//		this.setProcessId(vo.getId());
		if (StringUtils.isNotEmpty(vo.getRingAssociation()))
			this.setRingAssociation(vo.getRingAssociation());
		
		if (StringUtils.isNotEmpty(vo.getAgreementAcceptTitle()))
			this.setAgreementAcceptTitle(vo.getAgreementAcceptTitle());

		if (StringUtils.isNotEmpty(vo.getEccn()))
			this.setEccn(vo.getEccn());
		
		if (StringUtils.isNotEmpty(vo.getCcats()))
			this.setCcats(vo.getCcats());

	}

    @Column(name = "device_marketing_input_status")
    public String getDeviceMarketingInputStatus() {
        return deviceMarketingInputStatus;
    }

    public void setDeviceMarketingInputStatus(String deviceMarketingInputStatus) {
        this.deviceMarketingInputStatus = deviceMarketingInputStatus;
    }

    @Column(name = "product_info_input_status")
    public String getProductInfoInputStatus() {
        return productInfoInputStatus;
    }

    public void setProductInfoInputStatus(String productInfoInputStatus) {
        this.productInfoInputStatus = productInfoInputStatus;
    }

    @Column(name = "export_compliance_input_status")
    public String getExportComplianceInputStatus() {
        return exportComplianceInputStatus;
    }

    public void setExportComplianceInputStatus(String exportComplianceInputStatus) {
        this.exportComplianceInputStatus = exportComplianceInputStatus;
    }

    @Column(name = "upload_pdf_input_status")
    public String getUploadPdfInputStatus() {
        return uploadPdfInputStatus;
    }

    public void setUploadPdfInputStatus(String uploadPdfInputStatus) {
        this.uploadPdfInputStatus = uploadPdfInputStatus;
    }

	@Override
    public String toString() {

        StringBuilder buffer = new StringBuilder();

        buffer.append("processId=[").append(processId).append("] ");
        buffer.append("ringAssociation=[").append(ringAssociation).append("] ");
        buffer.append("eccn=[").append(eccn).append("] ");
        buffer.append("ccats=[").append(ccats).append("] ");

        return buffer.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((processId == null) ? 0 : processId.hashCode()));
        return result;
    }

    @Override    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapProductProcessInfo)) {
            return false;
        }
        VapProductProcessInfo equalCheck = (VapProductProcessInfo) obj;
        if ((processId == null && equalCheck.processId != null) || (processId != null && equalCheck.processId == null)) {
            return false;
        }
        if (processId != null && !processId.equals(equalCheck.processId)) {
            return false;
        }
        return true;
    }
}
