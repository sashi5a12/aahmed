package com.magikhelper.vo;

import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FilterModel extends Record{

    private String filterBy;
    private List<FilterVal> filterValues;

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public List<FilterVal> getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(List<FilterVal> filterValues) {
        this.filterValues = filterValues;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
