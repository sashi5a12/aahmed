package com.netpace.device.vo;

import java.util.Date;
import java.util.Set;
import javax.validation.Valid;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CompanyProfile {
    CompanyRegistration company;
    Set<CompanyReview> reviews;
    
    Long workflowId;
    String workflowPhaseId;
    String workflowPhaseName;
    String isRfi;
    String workflowType;
    UserInfo assigneeUser;
    Date startDate;

    @Valid
    public CompanyRegistration getCompany() {
        return company;
    }

    public void setCompany(CompanyRegistration company) {
        this.company = company;
    }

    // Workflow Process
    public String getWorkflowPhaseId() {
        return workflowPhaseId;
    }
    public void setWorkflowPhaseId(String workflowPhaseId) {
        this.workflowPhaseId = workflowPhaseId;
    }
    public String getWorkflowPhaseName() {
        return workflowPhaseName;
    }
    public void setWorkflowPhaseName(String workflowPhaseName) {
        this.workflowPhaseName = workflowPhaseName;
    }
    public UserInfo getAssigneeUser() {
        return assigneeUser;
    }
    public void setAssigneeUser(UserInfo assigneeUser) {
        this.assigneeUser = assigneeUser;
    }
    public String getIsRfi() {
        return isRfi;
    }
    public void setIsRfi(String isRfi) {
        this.isRfi = isRfi;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Long getWorkflowId() {
        return workflowId;
    }
    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowType() {
        return workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    public Set<CompanyReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<CompanyReview> reviews) {
        this.reviews = reviews;
    }
    
    @Override 
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
