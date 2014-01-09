package com.netpace.device.entities.sort;

import com.netpace.device.entities.BaseEntity;

public interface Sort<E extends BaseEntity> {
    String getField();
    Sort<E> getDefaultSort();
}
