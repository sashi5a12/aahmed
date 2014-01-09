package com.netpace.device.entities;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.*;

@Entity
@Table(name = "vap_media")
public class VapMedia extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3542337756354865039L;

	private Integer mediaId;
	private String fileName;
	private Integer fileLength;
	private String fileType;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    @Column(name="media_id",nullable=false,unique=true)
    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_length")
    public Integer getFileLength() {
        return fileLength;
    }

    public void setFileLength(Integer fileLength) {
        this.fileLength = fileLength;
    }

    @Column(name = "file_type")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    
    @Override
	public String toString() {
		return "VapMedia [mediaId=" + mediaId + ", fileName=" + fileName
				+ ", fileLength=" + fileLength + ", fileType=" + fileType + "]";
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((mediaId == null) ? 0 : mediaId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof VapMedia)) {
            return false;
        }
        VapMedia equalCheck = (VapMedia) obj;
        if ((mediaId == null && equalCheck.mediaId != null) || (mediaId != null && equalCheck.mediaId == null)) {
            return false;
        }
        if (mediaId != null && !mediaId.equals(equalCheck.mediaId)) {
            return false;
        }
        return true;
    }
    
    
}
