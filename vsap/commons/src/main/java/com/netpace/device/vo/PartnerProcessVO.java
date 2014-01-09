package com.netpace.device.vo;

import java.util.List;

public class PartnerProcessVO extends Record {

    private CompanyVO company;
    private List<CommentVO> comments;
    private List<WorkItem> workitems;

    public CompanyVO getCompany() {
        return company;
    }

    public void setCompany(CompanyVO company) {
        this.company = company;
    }

    public List<CommentVO> getComments() {
        return comments;
    }

    public void setComments(List<CommentVO> comments) {
        this.comments = comments;
    }

    public List<WorkItem> getWorkitems() {
        return workitems;
    }

    public void setWorkitems(List<WorkItem> workitems) {
        this.workitems = workitems;
    }
}
