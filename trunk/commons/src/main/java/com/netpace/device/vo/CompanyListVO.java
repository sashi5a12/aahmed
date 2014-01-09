package com.netpace.device.vo;

import java.sql.Timestamp;

/**
 *
 * @author aahmed
 */
public class CompanyListVO {
   private Integer Id;
   private String name;
   private String poc;
   private Timestamp createdDate;
   private String workflowSteps;
   private Boolean suspend;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoc() {
        return poc;
    }

    public void setPoc(String poc) {
        this.poc = poc;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getWorkflowSteps() {
        return workflowSteps;
    }

    public void setWorkflowSteps(String workflowSteps) {
        this.workflowSteps = workflowSteps;
    }

	public Boolean getSuspend() {
		return suspend;
	}

	public void setSuspend(Boolean suspend) {
		this.suspend = suspend;
	}

	@Override
    public String toString() {
        return "\nCompanyListVO{" + "Id=" + Id + ", name=" + name + ", poc=" + poc + ", createdDate=" + createdDate + ", workflowSteps=" + workflowSteps + ", suspend=" + suspend + '}';
    }
   
   
}
