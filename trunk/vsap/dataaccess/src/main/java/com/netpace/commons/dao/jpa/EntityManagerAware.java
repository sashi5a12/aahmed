package com.netpace.commons.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerAware {

    @PersistenceContext
    protected EntityManager entityManager;
}