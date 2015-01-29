package com.magikhelper.dao.jpa;

import com.magikhelper.dao.GenericReadOnlyDao;
import com.magikhelper.dao.QueryAndCount;
import com.magikhelper.entities.BaseEntity;
import com.magikhelper.entities.Sort;
import com.magikhelper.vo.FilterModel;
import com.magikhelper.vo.FilterVal;
import com.magikhelper.vo.PageModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenericReadOnlyDaoJpaImpl<T extends BaseEntity, PK extends Serializable> extends EntityManagerAware implements GenericReadOnlyDao<T, PK> {

    private static final Log log = LogFactory.getLog(GenericDaoJpaImpl.class);

    protected Class<T> persistenceClass;

    public GenericReadOnlyDaoJpaImpl(final Class<T> persistenceClass) {
        this.persistenceClass = persistenceClass;
    }

    @Override
    public boolean exists(PK id) {
        T obj = entityManager.find(persistenceClass, id);
        return obj != null;
    }

    @Override
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        Query query = entityManager.createNamedQuery(queryName);
        if (queryParams != null) {
            Set<Map.Entry<String, Object>> entries = queryParams.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.getResultList();
    }

    @Override
    public T get(PK id) {
        T entity = entityManager.find(persistenceClass, id);
        if (entity == null) {
            String msg = "Uh oh, '" + persistenceClass.getName() + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }
        return entity;
    }

    @Override
    public T getReference(PK id) {
        T entity = entityManager.getReference(persistenceClass, id);
        if (entity == null) {
            String msg = "Uh oh, object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }
        return entity;
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("select obj from " + persistenceClass.getName() + " obj")
                .getResultList();
    }

    @Override
    public QueryAndCount getPaggedData(String strquery, Sort<T> sort, boolean isAscending, Integer startPosition, Integer pageSize) {

        String countQuery = "select count(x) from " + persistenceClass.getName() + " x where x in (" + strquery + " )";
        log.info("executing count query : " + countQuery);
        Query query = entityManager.createQuery(countQuery);

        Number number = (Number) query.getSingleResult();

        query = entityManager.createQuery(strquery);
        if (sort != null) {
            strquery += " order by " + sort.getField() + " " + (isAscending ? "ASC" : "DESC");
        }
        log.info("executin data query : " + strquery);
        query.setFirstResult(startPosition);
        query.setMaxResults(pageSize);

        return new QueryAndCount(query, number.intValue());
    }

    protected String getSearchCriteriaQueryString(String searchBy, String searchValue) {
        StringBuilder strQuery = new StringBuilder();

        if (StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(searchValue)) {
            strQuery.append(" and " + searchBy + " like :searchValue ESCAPE '!'");
        }

        return strQuery.toString();
    }

    protected void setSearchCriteriaQueryParam(String searchBy, String searchValue, Query query) {
        if (StringUtils.isNotEmpty(searchBy) && StringUtils.isNotEmpty(searchValue)) {
            query.setParameter("searchValue", '%' + searchValue.trim() + '%');
        }
    }

    protected String getFilterCriteriaQueryString(List<FilterModel> filters) {
        StringBuilder filterByClause = new StringBuilder();

        if (filters != null && filters.size() > 0) {
            for (int i = 0; i < filters.size(); i++) {
                FilterModel filterModel = filters.get(i);
                List<String> filterValues = new ArrayList<String>();

                for (FilterVal filterVal : filterModel.getFilterValues()) {
                    if (filterVal.isSelected()) {
                        filterValues.add(filterVal.getTitle());
                    }
                }

                if (filterValues.size() > 0) {
                    filterByClause.append(" and " + filterModel.getFilterBy() + " in :filterValues" + i);
                }
            }
        }

        return filterByClause.toString();
    }

    protected String setFilterCriteriaQueryParam(List<FilterModel> filters, Query query) {
        StringBuilder filterByClause = new StringBuilder();

        if (filters != null && filters.size() > 0) {
            for (int i = 0; i < filters.size(); i++) {
                FilterModel filterModel = filters.get(i);
                List<String> filterValues = new ArrayList<String>();

                for (FilterVal filterVal : filterModel.getFilterValues()) {
                    if (filterVal.isSelected()) {
                        filterValues.add(filterVal.getTitle());
                    }
                }

                if (filterValues.size() > 0) {
                    query.setParameter("filterValues" + i, filterValues);
                }
            }
        }

        return filterByClause.toString();
    }

    protected String getOrderByQueryString(String sortBy, boolean isAscending) {
        StringBuilder orderByClause = new StringBuilder();

        if (StringUtils.isNotEmpty(sortBy)) {
            orderByClause.append(" order by " + sortBy + " " + (isAscending ? "asc" : "desc"));
        }

        return orderByClause.toString();
    }

    protected String getPageModelQueryString(PageModel pageModel) {
        StringBuilder strQuery = new StringBuilder();
        String searchValue = pageModel.getEscapedSearchValue();

        // Append query with search criteria set in model
        strQuery.append(getSearchCriteriaQueryString(pageModel.getSearchBy(), searchValue));

        // Append query with all filters set in model
        strQuery.append(getFilterCriteriaQueryString(pageModel.getFilters()));

        // Append query with sort set in the model
        strQuery.append(getOrderByQueryString(pageModel.getSortBy(), pageModel.isAscending()));

        return strQuery.toString();
    }

    protected void setPageModelQueryParam(PageModel pageModel, Query query) {
        String searchValue = pageModel.getEscapedSearchValue();

        setSearchCriteriaQueryParam(pageModel.getSearchBy(), searchValue, query);
        setFilterCriteriaQueryParam(pageModel.getFilters(), query);

    }

}
