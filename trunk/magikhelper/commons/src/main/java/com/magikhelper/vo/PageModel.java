package com.magikhelper.vo;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PageModel<E> extends Record {

    private String searchBy;
    private String searchValue;
    private String searchType;
    private String sortBy;
    private boolean ascending;
    private List<FilterModel> filters;
    private String page;
    private int pageSize;
  
    
    private List<E> records;
    private Integer totalRecords;

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterModel> filters) {
        this.filters = filters;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
    	if (!StringUtils.isNumeric(page)){
    		this.page = "0";
    	}
    	else {    		
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public int firstResult() {
    	if (StringUtils.isEmpty(this.page) || !StringUtils.isNumeric(this.page)){
    		this.page="0";
    	}
        return Integer.parseInt(this.page) * this.pageSize;
    }
    
    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

   public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
    
	public String getEscapedSearchValue(){
		String str=this.searchValue;
		if (StringUtils.isNotEmpty(str)){
			str=str.replace("%", "!%").replace("_", "!_");
        }
		return str;
	}
    
}
