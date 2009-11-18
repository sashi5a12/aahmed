package com.netpace.aims.dataaccess.valueobjects;

import java.util.Vector;

import org.apache.commons.lang.builder.EqualsBuilder;

public class WorkitemVO {
	private long rownum;
	private Long workitemId;
	private Long workflowId;
	private Long recordId;
	private String workItem;
	private String type;
    private String detail;
	private String company;
	private String startDate;
	private Vector actions;
	private String comments;
	private String selectedAction;
	private Long platformId;
	private Long stateId;
	private Long workFlowDetailId;
	
	private String workItemDispTitle;
	
	private Boolean validateStep;

    public long getRownum() {
		return rownum;
	}
	public void setRownum(long rownum) {
		this.rownum = rownum;
	}
	public Long getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(Long workitemId) {
		this.workitemId = workitemId;
	}
	public Long getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public String getWorkItem() {
		return workItem;
	}
	public void setWorkItem(String workItem) {
		this.workItem = workItem;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Vector getActions() {
		return actions;
	}
	public void setActions(Vector actions) {
		this.actions = actions;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSelectedAction() {
		return selectedAction;
	}
	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }    

    public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}	
	public Long getWorkFlowDetailId() {
		return workFlowDetailId;
	}
	public void setWorkFlowDetailId(Long workFlowDetailId) {
		this.workFlowDetailId = workFlowDetailId;
	}	
	public Boolean getValidateStep() {
		return validateStep;
	}
	public void setValidateStep(Boolean validateStep) {
		this.validateStep = validateStep;
	}	
	public String getWorkItemDispTitle() {
		return workItemDispTitle;
	}
	public void setWorkItemDispTitle(String workItemDispTitle) {
		this.workItemDispTitle = workItemDispTitle;
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((recordId == null) ? 0 : recordId.hashCode());
		result = prime * result + (int) (rownum ^ (rownum >>> 32));
		result = prime * result
				+ ((workflowId == null) ? 0 : workflowId.hashCode());
		result = prime * result
				+ ((workitemId == null) ? 0 : workitemId.hashCode());
		return result;
	}
	
	public boolean equals(Object other) {
		 if ( !(other instanceof WorkitemVO) ) return false;
		 WorkitemVO castOther = (WorkitemVO) other;
	        return new EqualsBuilder()
	        		.append(this.getRecordId(), castOther.getRecordId())
	        		.append(String.valueOf(this.getRownum()), String.valueOf(castOther.getRownum()))
	        		.append(this.getWorkflowId(), castOther.getWorkflowId())
	        		.append(this.getWorkitemId(), castOther.getWorkitemId())
	        		.isEquals();
	}
	public String toString(){
	    final String TAB = "    ";
	
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("WorkitemVO ( ")
	        .append("rownum = ").append(this.rownum).append(TAB)
	        .append("detail = ").append(this.detail).append(TAB)
	        .append("workitemId = ").append(this.workitemId).append(TAB)
	        .append("workflowId = ").append(this.workflowId).append(TAB)
	        .append("recordId = ").append(this.recordId).append(TAB)
	        .append("comments = ").append(this.comments).append(TAB)
	        .append("selectedAction = ").append(this.selectedAction).append(TAB)
            .append("platformId = ").append(this.platformId).append(TAB)
            .append(" )");
	    
	    return retValue.toString();
	}
	
		
}