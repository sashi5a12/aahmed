package com.magikhelper.commons.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerAware {

    @PersistenceContext
    protected EntityManager entityManager;
}
