package com.netpace.vic.util;

import java.util.List;
import org.apache.commons.lang.StringUtils;

public class PageModel<E> {

    private String page;
    private int pageSize;

    private List<E> records;
    private Integer totalRecords;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        if (!StringUtils.isNumeric(page)) {
            this.page = "0";
        } else {
            this.page = page;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<E> getRecords() {
        return records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int firstResult() {
        if (StringUtils.isEmpty(this.page) || !StringUtils.isNumeric(this.page)) {
            this.page = "0";
        }
        return Integer.parseInt(this.page) * this.pageSize;
    }

}
