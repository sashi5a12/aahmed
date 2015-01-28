package com.magikhelper.entities;

public interface Sort<E extends BaseEntity> {

    String getField();

    Sort<E> getDefaultSort();
}
