package com.magikhelper.dao;

import com.magikhelper.entities.BaseEntity;
import java.io.Serializable;

public interface GenericDao<T extends BaseEntity, PK extends Serializable> extends GenericReadOnlyDao<T, PK> {

    /**
     * Generic method to insert detached object.
     *
     * @param object the object to insert
     * @return the persisted object
     */
    void add(T entity);

    /**
     * Generic method to update an object.
     *
     * @param object the object to update
     * @return the persisted object
     */
    T update(T entity);

    /**
     * Generic method to delete an object based on class and id
     *
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    void flush();
}
