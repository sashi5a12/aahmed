package com.netpace.device.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import javax.persistence.*;

import org.hibernate.annotations.Formula;

import com.netpace.device.vo.product.AttachmentFile;
import com.netpace.device.vo.product.ProductInfoVO;

@Entity
@Table(name = "vap_products")
public class VapProduct extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1249211868771695884L;
    private Integer productId;
    private String status;
    private String productType;
    private String productName;
    private Timestamp submittedDate;
    private String submittedBy;
    private String modelNumber;
    private String description;
    private String productCategory;
    private String partNumber;
    private String sampleTracking;
    private String availabilityDate;
    private String targetSegment;
    private String positioningStatement;
    private String deviceNeed;
    private String mainCompetition;
    private String uniqueFunctionality;
    private String vzwExclusive;
    private String connectivityType;
    private String paltformSupported;
    private String cloudSupported;
    private String formRequirements;
    private String productDimensions;
    private String itemIncluded;
    private VapContact contact;
    private Company company;
    private VapProductProcessInfo processingInfo;
    private List<VapComment> comments;
    private List<VapProductSupportedDevice> supportedDevices;
    private Map<String, VapProductAttachment> attachments;
    private List<Workflow> workFlow;
    private String workFlowSteps;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, unique = true)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Column(name = "product_name", length = 250)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "submitted_date")
    public Timestamp getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Timestamp submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Column(name = "submitted_by")
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Column(name = "model_number")
    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    @Column(name = "part_number")
    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @Column(name = "sample_tracking")
    public String getSampleTracking() {
        return sampleTracking;
    }

    public void setSampleTracking(String sampleTracking) {
        this.sampleTracking = sampleTracking;
    }

    @Column(name = "availability_date")
    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    @Column(name = "target_segment")
    public String getTargetSegment() {
        return targetSegment;
    }

    public void setTargetSegment(String targetSegment) {
        this.targetSegment = targetSegment;
    }

    @Column(name = "positioning_statement")
    public String getPositioningStatement() {
        return positioningStatement;
    }

    public void setPositioningStatement(String positioningStatement) {
        this.positioningStatement = positioningStatement;
    }

    @Column(name = "device_need")
    public String getDeviceNeed() {
        return deviceNeed;
    }

    public void setDeviceNeed(String deviceNeed) {
        this.deviceNeed = deviceNeed;
    }

    @Column(name = "main_competition")
    public String getMainCompetition() {
        return mainCompetition;
    }

    public void setMainCompetition(String mainCompetition) {
        this.mainCompetition = mainCompetition;
    }

    @Column(name = "unique_functionality")
    public String getUniqueFunctionality() {
        return uniqueFunctionality;
    }

    public void setUniqueFunctionality(String uniqueFunctionality) {
        this.uniqueFunctionality = uniqueFunctionality;
    }

    @Column(name = "vzw_exclusive")
    public String getVzwExclusive() {
        return vzwExclusive;
    }

    public void setVzwExclusive(String vzwExclusive) {
        this.vzwExclusive = vzwExclusive;
    }

    @Column(name = "connectivity_type")
    public String getConnectivityType() {
        return connectivityType;
    }

    public void setConnectivityType(String connectivityType) {
        this.connectivityType = connectivityType;
    }

    @Column(name = "paltform_supported")
    public String getPaltformSupported() {
        return paltformSupported;
    }

    public void setPaltformSupported(String paltformSupported) {
        this.paltformSupported = paltformSupported;
    }

    @Column(name = "cloud_supported")
    public String getCloudSupported() {
        return cloudSupported;
    }

    public void setCloudSupported(String cloudSupported) {
        this.cloudSupported = cloudSupported;
    }

    @Column(name = "form_requirements")
    public String getFormRequirements() {
        return formRequirements;
    }

    public void setFormRequirements(String formRequirements) {
        this.formRequirements = formRequirements;
    }

    @Column(name = "product_dimensions")
    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    @Column(name = "item_included")
    public String getItemIncluded() {
        return itemIncluded;
    }

    public void setItemIncluded(String itemIncluded) {
        this.itemIncluded = itemIncluded;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_contact_id")
    public VapContact getContact() {
        return contact;
    }

    public void setContact(VapContact contact) {
        this.contact = contact;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "process_id", unique = true)
    public VapProductProcessInfo getProcessingInfo() {
        return processingInfo;
    }

    public void setProcessingInfo(VapProductProcessInfo processingInfo) {
        this.processingInfo = processingInfo;
    }

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public List<VapComment> getComments() {
        return comments;
    }

    public void setComments(List<VapComment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    public List<VapProductSupportedDevice> getSupportedDevices() {
        return supportedDevices;
    }

    public void setSupportedDevices(List<VapProductSupportedDevice> supportedDevices) {
        this.supportedDevices = supportedDevices;
    }

    @OneToMany(mappedBy = "product")
    @MapKey(name = "fileFieldName")
    public Map<String, VapProductAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, VapProductAttachment> attachments) {
        this.attachments = attachments;
    }

    public void addComment(VapComment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<VapComment>();
        }
        this.comments.add(comment);
        comment.setProduct(this);
    }

    public void addSupportedDevice(VapProductSupportedDevice device) {
    	
        if (this.supportedDevices == null) {
            this.supportedDevices = new ArrayList<VapProductSupportedDevice>();
        }
        this.supportedDevices.add(device);
        device.setProduct(this);
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public List<Workflow> getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(List<Workflow> workFlow) {
        this.workFlow = workFlow;
    }

    @Formula(value = "product_work_flow_steps(product_id, '<br/> ' )")
    @Basic(fetch = FetchType.LAZY)
    public String getWorkFlowSteps() {
        return workFlowSteps;
    }

    public void setWorkFlowSteps(String workFlowSteps) {
        this.workFlowSteps = workFlowSteps;
    }

    public void copyFromVo(ProductInfoVO vo) {
        this.setProductId(vo.getId());
        this.setProductType(vo.getProductType());
        this.setProductName(vo.getProductName());
        this.setModelNumber(vo.getModelNumber());
        this.setDescription(vo.getDescription());
        this.setProductCategory(vo.getProductCategory());
        this.setPartNumber(vo.getPartNumber());
        this.setSampleTracking(vo.getSampleTracking());
        this.setAvailabilityDate(vo.getAvailabilityDate());
        this.setVzwExclusive(vo.getVzwExclusive());

        this.setTargetSegment(vo.getTargetSegment());
        this.setPositioningStatement(vo.getPositioningStatement());
        this.setDeviceNeed(vo.getDeviceNeed());
        this.setMainCompetition(vo.getMainCompetition());
        this.setUniqueFunctionality(vo.getUniqueFunctionality());

        this.setConnectivityType(vo.getConnectivityType());
        this.setPaltformSupported(vo.getPaltformSupported());
        this.setCloudSupported(vo.getCloudSupported());
        this.setFormRequirements(vo.getFormRequirements());
        this.setProductDimensions(vo.getProductDimensions());
        this.setItemIncluded(vo.getItemIncluded());
    }

    public void copyProductInfoInVo(ProductInfoVO vo) {
        vo.setId(this.productId);
        vo.setProductStatus(this.getStatus());
        vo.setProductType(this.getProductType());
        vo.setDbProductType(this.getProductType());
        vo.setProductName(this.getProductName());
        vo.setModelNumber(this.getModelNumber());
        vo.setDescription(this.getDescription());
        vo.setProductCategory(this.getProductCategory());
        vo.setPartNumber(this.getPartNumber());
        vo.setSampleTracking(this.getSampleTracking());
        vo.setAvailabilityDate(this.getAvailabilityDate());
        vo.setVzwExclusive(this.getVzwExclusive());

        vo.setTargetSegment(this.getTargetSegment());
        vo.setPositioningStatement(this.getPositioningStatement());
        vo.setDeviceNeed(this.getDeviceNeed());
        vo.setMainCompetition(this.getMainCompetition());
        vo.setUniqueFunctionality(this.getUniqueFunctionality());

        vo.setConnectivityType(this.getConnectivityType());
        vo.setPaltformSupported(this.getPaltformSupported());
        vo.setCloudSupported(this.getCloudSupported());
        vo.setFormRequirements(this.getFormRequirements());
        vo.setProductDimensions(this.getProductDimensions());
        vo.setItemIncluded(this.getItemIncluded());
    }

    public AttachmentFile copyAttachmentInfoInVo(String fileType) {
        AttachmentFile file = new AttachmentFile();
        VapProductAttachment att = this.getAttachments().get(fileType);
        if (att != null) {
            file.setMediaId(att.getMedia().getMediaId());
            file.setFileType(att.getFileFieldName());
            file.setFileFullName(att.getMedia().getFileName());
            if (att.getMedia().getFileName()!=null && att.getMedia().getFileName().length()>15){
            	file.setFileName(att.getMedia().getFileName().substring(0, 14)+"...");
            }
            else {
            	file.setFileName(att.getMedia().getFileName());
            }
            file.setFileSize(att.getMedia().getFileLength());
            file.setFileType(fileType);
        }
        return file;
    }

    public void copyPlatformSupportedDeviceInfoInVo(ProductInfoVO vo) {
        if (this.supportedDevices != null && this.supportedDevices.size() > 0) {
        	ArrayList<String> tmp=new ArrayList<String>();
//            String[] sd = new String[this.supportedDevices.size()];
            for (int i = 0; i < this.supportedDevices.size(); i++) {
            	if(!tmp.contains(this.supportedDevices.get(i).getDeviceName())){
            		tmp.add(this.supportedDevices.get(i).getDeviceName());
            	}
//                sd[i] = this.supportedDevices.get(i).getDeviceName();
            }
//            vo.setSupportedDevices(sd);
            vo.setSupportedDevices(tmp.toArray(new String[tmp.size()]));
        }
    }

    /*public void copyContactInVo(ProductInfoVO vo){
     vo.setContactId(this.getContact().getContactId());
     vo.setName(this.getContact().getName());
     vo.setStreetAddress(this.getContact().getStreetAddress());
     vo.setEmailAddress(this.getContact().getEmailAddress());
     vo.setCity(this.getContact().getCity());
     vo.setPhone(this.getContact().getPhone());
     vo.setPostalCode(this.getContact().getPostalCode());
     vo.setMobile(this.getContact().getMobile());
     vo.setCountry(this.getContact().getCountry());
     }*/
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((productId == null) ? 0 : productId.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        return "VapProduct ["
                + "productId=" + productId
                + ", status=" + status
                + ", productType=" + productType
                + ", productName=" + productName
                + ", productCategory=" + productCategory
                + ", company=" + company
                + ", workFlowSteps=" + workFlowSteps
                + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapProduct)) {
            return false;
        }
        VapProduct equalCheck = (VapProduct) obj;
        if ((productId == null && equalCheck.productId != null) || (productId != null && equalCheck.productId == null)) {
            return false;
        }
        if (productId != null && !productId.equals(equalCheck.productId)) {
            return false;
        }
        return true;
    }
}
