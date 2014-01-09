package com.netpace.device.vo;

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author 
 */
public class CompanyReview extends Record{
    
    private Integer workitemId;
    private Integer companyId;
    private UserInfo reviewUser;
    private String reviewResponse;
    private String commentText;
    private String commentVisibility;
    private Date createdDate;
    private String createdBy;

    public CompanyReview() {
        this.reviewUser = new UserInfo();
    }
    
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getWorkitemId() {
        return workitemId;
    }

    public void setWorkitemId(Integer workitemId) {
        this.workitemId = workitemId;
    }

    public UserInfo getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(UserInfo reviewUser) {
        this.reviewUser = reviewUser;
    }

    public String getCommentVisibility() {
        return commentVisibility;
    }

    public void setCommentVisibility(String commentVisibility) {
        this.commentVisibility = commentVisibility;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(String reviewResponse) {
        this.reviewResponse = reviewResponse;
    }

    @Override 
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
