package com.magikhelper.dao;

import com.magikhelper.entities.BaseEntity;
import com.magikhelper.entities.Sort;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

public interface GenericReadOnlyDao<T extends BaseEntity, PK extends Serializable> {

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
     * found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    T getReference(PK id);

    /**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @param options PagingOptions<S extends Sort>
     * @return List of populated objects
     */
    public QueryAndCount getPaggedData(String strquery, Sort<T> sort, boolean sortOrder, Integer startPosition, Integer pageSize);

}
