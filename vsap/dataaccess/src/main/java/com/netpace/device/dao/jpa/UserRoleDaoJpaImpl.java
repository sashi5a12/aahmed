package com.netpace.device.dao.jpa;

import com.netpace.device.dao.UserRoleDao;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserRole;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(value = UserRoleDao.name)
public class UserRoleDaoJpaImpl extends GenericDaoJpaImpl<UserRole, Integer> implements UserRoleDao {

    private final static Log log = LogFactory.getLog(UserRoleDaoJpaImpl.class);

    public UserRoleDaoJpaImpl() {
        super(UserRole.class);
    }

    @Override
    public List<UserRole> getUserRoleByUserId(Integer userId) {
        Query query = entityManager.createNamedQuery("findUserRoleByUserId");
        query.setParameter("userid", userId);
        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }


}
