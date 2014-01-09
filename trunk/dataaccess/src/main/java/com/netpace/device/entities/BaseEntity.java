package com.netpace.device.entities;

import com.netpace.device.utils.DateUtils;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

    protected Timestamp createdDate;
    protected String createdBy;
    protected Timestamp lastUpdatedDate;
    protected String lastUpdatedBy;
    protected boolean active;

    @Column(name = "is_active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "updated_by")
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "updated_date")
    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public void populatedAuditFields(String user) {
        setCreatedBy(user);
        setLastUpdatedBy(user);
        setCreatedDate(DateUtils.currentTimeStamp());
        setLastUpdatedDate(DateUtils.currentTimeStamp());
        setActive(true);
    }
    public void populatedAuditFieldsOnUpdate(String user) {
        setLastUpdatedBy(user);
        setLastUpdatedDate(DateUtils.currentTimeStamp());
    }
}
