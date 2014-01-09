package com.netpace.vic.dto;

import com.netpace.vic.util.DateUtils;
import java.io.Serializable;
import java.sql.Timestamp;

public class Base implements Serializable{
    protected Timestamp createdDate;
    protected String createdBy;
    protected Timestamp updatedDate;
    protected String updatedBy;

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    public void populatedAuditFields(String user) {
        setCreatedBy(user);
        setUpdatedBy(user);
        setCreatedDate(DateUtils.currentTimeStamp());
        setUpdatedDate(DateUtils.currentTimeStamp());
    }
    public void populatedAuditFieldsOnUpdate(String user) {
        setUpdatedBy(user);
        setUpdatedDate(DateUtils.currentTimeStamp());
    }    
}
