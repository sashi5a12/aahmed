package com.magikhelper.dao.jpa;

import com.magikhelper.dao.GenericDao;
import com.magikhelper.entities.BaseEntity;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenericDaoJpaImpl<T extends BaseEntity, PK extends Serializable> extends GenericReadOnlyDaoJpaImpl<T, PK> implements GenericDao<T, PK> {

    private static final Log log = LogFactory.getLog(GenericDaoJpaImpl.class);

    public GenericDaoJpaImpl(Class<T> persistenceClass) {
        super(persistenceClass);
    }

    @Override
    public void add(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T update(T entity) {
        if (!entityManager.contains(entity)) {
            String message = "Cannot update detached entity " + persistenceClass.getName();
            log.warn(message);
            throw new IllegalArgumentException(message);
        }
        return entityManager.merge(entity);
    }

    @Override
    public void remove(PK id) {
        entityManager.remove(this.get(id));
    }

    @Override
    public void flush() {
        entityManager.flush();
    }
}
