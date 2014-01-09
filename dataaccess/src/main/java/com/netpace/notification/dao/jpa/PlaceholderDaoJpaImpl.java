package com.netpace.notification.dao.jpa;

import com.netpace.device.dao.jpa.GenericDaoJpaImpl;
import com.netpace.notification.dao.PlaceholderDao;
import com.netpace.notification.entities.Placeholder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(value = PlaceholderDao.name)
public class PlaceholderDaoJpaImpl extends GenericDaoJpaImpl<Placeholder, Integer> implements PlaceholderDao {

    private static final Log log = LogFactory.getLog(PlaceholderDaoJpaImpl.class);

    public PlaceholderDaoJpaImpl() {
        super(Placeholder.class);
    }

    @Override
    public Placeholder getPlaceholderById(Integer placeholderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("placeholderId", placeholderId);

        List<Placeholder> list = findByNamedQuery("findPlaceholderById", map);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }
    
    @Override
    public Placeholder getPlaceholderByName(String displayName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("displayName", displayName);

        List<Placeholder> list = findByNamedQuery("findPlaceholderByName", map);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<Placeholder> getPlaceHoldersList(List<Integer> placeHoldersIds) {
        Query query = entityManager.createQuery("select ph from Placeholder ph where ph.id in :placeHolderIds ");
        query.setParameter("placeHolderIds", placeHoldersIds);

        List<Placeholder> list = (List<Placeholder>) query.getResultList();

        return list;
    }

}
