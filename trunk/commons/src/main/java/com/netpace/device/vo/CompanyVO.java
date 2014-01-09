package com.netpace.device.vo;

import java.util.Date;

public class CompanyVO {

    private Integer id;
    private String name;
    private String workFlowSteps;
    private boolean suspended;
    private Date certNdaAcceptDate;
    private Integer offlineCertNdaId;
    private String offlineCertNdaName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkFlowSteps() {
        return workFlowSteps;
    }

    public void setWorkFlowSteps(String workFlowSteps) {
        this.workFlowSteps = workFlowSteps;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public Date getCertNdaAcceptDate() {
        return certNdaAcceptDate;
    }

    public void setCertNdaAcceptDate(Date certNdaAcceptDate) {
        this.certNdaAcceptDate = certNdaAcceptDate;
    }

    public Integer getOfflineCertNdaId() {
        return offlineCertNdaId;
    }

    public void setOfflineCertNdaId(Integer offlineCertNdaId) {
        this.offlineCertNdaId = offlineCertNdaId;
    }

    public String getOfflineCertNdaName() {
        return offlineCertNdaName;
    }

    public void setOfflineCertNdaName(String offlineCertNdaName) {
        this.offlineCertNdaName = offlineCertNdaName;
    }
    
}
