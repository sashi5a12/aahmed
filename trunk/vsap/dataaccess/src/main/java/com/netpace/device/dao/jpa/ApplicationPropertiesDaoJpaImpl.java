/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.dao.jpa;

import com.netpace.device.dao.ApplicationPropertiesDao;
import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wakram
 */
@Repository(ApplicationPropertiesDao.name)
public class ApplicationPropertiesDaoJpaImpl extends GenericDaoJpaImpl<ApplicationProperties, Integer> implements ApplicationPropertiesDao {

    private static final Log log = LogFactory.getLog(ApplicationPropertiesDaoJpaImpl.class);

    public ApplicationPropertiesDaoJpaImpl() {
        super(ApplicationProperties.class);
    }

    @Override
    public List<ApplicationProperties> getPropertiesByType(ApplicationPropertyType type) {
        log.info("Load aplication properties of type "+type);
        Query query = entityManager.createQuery("from ApplicationProperties ap where ap.type=:type order by sortOrder asc");
        query.setParameter("type", type);
        log.info("application properties loading done");
        return query.getResultList();
    }

    @Override
    public ApplicationProperties getPropertiesByTypeAndKey(ApplicationPropertyType type, String key) {
        log.info("get aplication property by type and key ("+type +": "+key+")");
        Query query = entityManager.createQuery("from ApplicationProperties ap where ap.type=:type and ap.name=:key ");
        query.setParameter("type", type);
        query.setParameter("key", key);
        log.info("application properties loading done");
        return (ApplicationProperties)query.getSingleResult();
    }

    @Override
    public List<ApplicationProperties> getSortedProperties() {
        log.info("Get all aplication properties sorted in ascending manner");
        Query query = entityManager.createQuery("from ApplicationProperties ap order by ap.type, ap.sortOrder");
        return query.getResultList();
    }
    
    @Override
    public void probeHit(){
    	Query query=entityManager.createNativeQuery("Select 1");
    	Object count=query.getSingleResult();
    }
}