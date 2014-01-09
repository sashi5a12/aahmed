package com.netpace.device.dao;

import com.netpace.device.entities.BaseEntity;
import java.util.List;

public class PaggedResult<E extends BaseEntity> {
    List<E> list;
    Integer records;

    public PaggedResult(List<E> list, Integer records) {
        this.list = list;
        this.records = records;
    }

    public List<E> getList() {
        return list;
    }

    public Integer getRecords() {
        return records;
    }
    
}
