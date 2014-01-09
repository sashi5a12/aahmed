package com.netpace.device.dao.query;

import com.netpace.device.dao.UtilitiesDao;
import com.netpace.device.dao.jpa.EntityManagerAware;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

@Repository(value=UtilitiesDao.name)
public class UtilitiesDaoNativeQueryImpl extends EntityManagerAware implements UtilitiesDao{
    
    private static final Log log = LogFactory.getLog(UtilitiesDaoNativeQueryImpl.class);
    
    private static final String menuQuery="SELECT DISTINCT vap_main_menu.name, vap_sub_menu.item_name,vap_sub_menu.url FROM vap_user_role INNER JOIN vap_user ON (vap_user_role.user_id = vap_user.user_id) "
            + "INNER JOIN vap_system_role ON (vap_user_role.role_id = vap_system_role.role_id) INNER JOIN vap_sub_menu ON (vap_sub_menu.system_role_id = vap_system_role.role_id) "
            + "INNER JOIN vap_main_menu ON (vap_sub_menu.menu_id = vap_main_menu.id) where vap_user.user_id=:userId";

    private static final String blockedDomainQuery="SELECT domain_name FROM vap_blocked_domain WHERE domain_name=:domainName and is_active=:active";
    
    @Override
    @NotNull
    public List<Object[]> getMenuInfo(Integer userId) {
        Query query = entityManager.createNativeQuery(menuQuery);
        query.setParameter("userId", userId);
        return query.getResultList();
       
    }
    
    @Override
    public Object findBlockedDomain(String domainName) {
        Query query = entityManager.createNativeQuery(blockedDomainQuery);
        query.setParameter("domainName", domainName);
        query.setParameter("active", "1");
        try {
            return query.getSingleResult();
        } catch(NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }
}
