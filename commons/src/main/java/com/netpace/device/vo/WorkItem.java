package com.netpace.device.vo;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author trafique
 */
public class WorkItem extends Record{
    
    private Integer workitemId;
    private String title;
    private String displayName;
    private String status;
    private List<String> allowedRoles;
    private boolean requireInput;
    private List<String> nextActions;
    private Date startDate;
    private Date endDate;
    private String lifecycleTask;
    private UserInfo assigneeUser;
    private String decision;
    private String commentText;
    private WorkflowVO workflow;
    private Integer companyOfflineCertNdaId;

    public UserInfo getAssigneeUser() {
        return assigneeUser;
    }

    public void setAssigneeUser(UserInfo assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

    public Integer getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(Integer workitemId) {
        this.workitemId = workitemId;
    }

    public String getLifecycleTask() {
        return lifecycleTask;
    }

    public void setLifecycleTask(String lifecycleTask) {
        this.lifecycleTask = lifecycleTask;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRequireInput() {
        return requireInput;
    }

    public void setRequireInput(boolean requireInput) {
        this.requireInput = requireInput;
    }

    public List<String> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(List<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public List<String> getNextActions() {
        return nextActions;
    }

    public void setNextActions(List<String> nextActions) {
        this.nextActions = nextActions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public WorkflowVO getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowVO workflow) {
        this.workflow = workflow;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getCompanyOfflineCertNdaId() {
        return companyOfflineCertNdaId;
    }

    public void setCompanyOfflineCertNdaId(Integer companyOfflineCertNdaId) {
        this.companyOfflineCertNdaId = companyOfflineCertNdaId;
    }
    
    @Override 
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
