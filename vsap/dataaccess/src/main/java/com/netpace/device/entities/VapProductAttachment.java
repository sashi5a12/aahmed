package com.netpace.device.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 */
@Entity
@Table(name = "vap_product_attachments")
public class VapProductAttachment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5477199642737555704L;

	private Integer attachmentId;
	private String fileFieldName;
	private Integer tab;
	private VapMedia media;
	private VapProduct product;
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id", nullable = false, unique = true)    
    public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

    @Column(name = "file_field_name")
	public String getFileFieldName() {
		return fileFieldName;
	}

	public void setFileFieldName(String fileFieldName) {
		this.fileFieldName = fileFieldName;
	}

	@Column(name = "tab")
    public Integer getTab() {
		return tab;
	}

	public void setTab(Integer tab) {
		this.tab = tab;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_id")
	public VapMedia getMedia() {
		return media;
	}

	public void setMedia(VapMedia media) {
		this.media = media;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")	
	public VapProduct getProduct() {
		return product;
	}

	public void setProduct(VapProduct product) {
		this.product = product;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((attachmentId == null) ? 0 : attachmentId.hashCode()));
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapProductAttachment)) {
            return false;
        }
        VapProductAttachment equalCheck = (VapProductAttachment) obj;
        if ((attachmentId == null && equalCheck.attachmentId != null) || (attachmentId != null && equalCheck.attachmentId == null)) {
            return false;
        }
        if (attachmentId != null && !attachmentId.equals(equalCheck.attachmentId)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "VapProductAttachment [attachmentId=" + attachmentId + ", fileFieldName=" + fileFieldName +  "]";
	}
    
    
}
