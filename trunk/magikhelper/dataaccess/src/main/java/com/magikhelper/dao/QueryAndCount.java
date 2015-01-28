package com.magikhelper.dao;

import javax.persistence.Query;

public class QueryAndCount {

    private Query query;
    private Integer count;

    public QueryAndCount(Query query, Integer count) {
        this.query = query;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public Query getQuery() {
        return query;
    }

}
