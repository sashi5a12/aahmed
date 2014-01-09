package com.netpace.device.dao.jpa;

import com.netpace.device.dao.PaggedResult;
import com.netpace.device.dao.QueryAndCount;
import com.netpace.device.dao.UserDao;
import com.netpace.device.entities.User;
import com.netpace.device.entities.sort.Sort;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.UserInfo;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = UserDao.name)
public class UserDaoJpaImpl extends GenericDaoJpaImpl<User, Integer> implements UserDao {

    private final static Log log = LogFactory.getLog(UserDaoJpaImpl.class);

    public UserDaoJpaImpl() {
        super(User.class);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
        Query query = entityManager.createQuery("select u from User u where u.userName= :userName and u.active = '1'");
        query.setParameter("userName", userName);
        try {
            User obj = (User) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByEmailAddress(String email) {
        Query query = entityManager.createQuery("select u from User u where u.emailAddress= :email and u.active = '1'");
        query.setParameter("email", email);
        try {
            User obj = (User) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByPRToken(String passwordResetToken) {

        Query query = entityManager.createQuery("select u from User u where u.resetPasswordToken= :resetPasswordToken");
        query.setParameter("resetPasswordToken", passwordResetToken);
        try {
            User obj = (User) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public User update(User entity) {
        return entityManager.merge(entity);
    }

    @Override
    public PaggedResult<User> getUsersList(Sort<User> sort, boolean isAscending, Integer startPosition, Integer pageSize) {
        QueryAndCount queryAndCount = getPaggedData(" from User u ", sort, isAscending, startPosition, pageSize);
        return new PaggedResult<User>(queryAndCount.getQuery().getResultList(), queryAndCount.getCount());
    }

    @Override
    public List<User> getUsersList(Integer start, Integer limit) {
        Query query = entityManager.createNamedQuery("findUsers");
        query.setFirstResult(start);
        query.setMaxResults(limit);

        return query.getResultList();

    }

    @Override
    public Long getUsersCount() {
        Query query = entityManager.createNamedQuery("findUsersCount");

        return (Long) query.getSingleResult();
    }

    @Override
    public User getUserById(Integer userId) {
        Query query = entityManager.createNamedQuery("findUserById");
        query.setParameter("userId", userId);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public User getPartnerUserById(Integer companyId, Integer userId) {
        Query query = entityManager.createNamedQuery("findPartnerUserById");
        query.setParameter("userId", userId);
        query.setParameter("companyId", companyId);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public User getPartnerUserByIdForEdit(Integer companyId, Integer userId) {
        Query query = entityManager.createNamedQuery("findPartnerUserByIdForEdit");
        query.setParameter("userId", userId);
        query.setParameter("companyId", companyId);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }
    
    @Override
    public User getAdminUserByIdForEdit(Integer userId) {
        Query query = entityManager.createNamedQuery("findAdminUserByIdForEdit");
        query.setParameter("userId", userId);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getUsersByCompanyId(Integer companyId) {
        Query query = entityManager.createNamedQuery("findUsersByCompanyId");
        query.setParameter("companyId", companyId);

        return query.getResultList();
    }

    @Override
    public PaggedResult<User> getPaggedUsers(PageModel<UserInfo> pageModel) {

        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" from User user inner join user.userRoles as userroles");
        strQuery.append(" where user.active = '1' and userroles.systemRole.title != 'ROLE_SUPER_ADMIN' ");

        // Append page model query string
        strQuery.append(super.getPageModelQueryString(pageModel));

        Query query = entityManager.createQuery("select count(distinct user.id) " + strQuery.toString());

        super.setPageModelQueryParam(pageModel, query);

        Number number = (Number) query.getSingleResult();

        query = entityManager.createQuery(" select distinct user " + strQuery.toString());

        super.setPageModelQueryParam(pageModel, query);

        query.setFirstResult(pageModel.firstResult());
        query.setMaxResults(pageModel.getPageSize());

        return new PaggedResult(query.getResultList(), number.intValue());
    }

    @Override
    public List<User> getUsersList(PageModel<UserInfo> pageModel) {

        StringBuilder strQuery = new StringBuilder();
        strQuery.append("from User user");

        // Append page model query string
        strQuery.append(super.getPageModelQueryString(pageModel));

        Query query = entityManager.createQuery("select user " + strQuery.toString());

        super.setPageModelQueryParam(pageModel, query);

        return query.getResultList();
    }
    
    @Override
    public User getMPOC(Integer companyId) {
        
        Query query = entityManager.createNamedQuery("findMPOC");
        query.setParameter("companyId", companyId);
        query.setParameter("roleName", VAPConstants.ROLE_MPOC);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public List<String> getUserEmailsByCompanyId(Integer companyId) {
        Query query = entityManager.createQuery("select emailAddress from User user where user.company.id = :companyId");
        query.setParameter("companyId", companyId);

        return query.getResultList();
    }
    
    @Override
    public List<String> getRoleUserEmailsByCompanyId(Integer companyId, Integer systemRoleId) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select user.emailAddress from User user");
        strQuery.append(" inner join user.userRoles userRoles");
        strQuery.append(" inner join userRoles.systemRole systemRole");
        strQuery.append(" where user.company.id = :companyId");
        strQuery.append(" and systemRole.id = :systemRoleId");

        Query query = entityManager.createQuery(strQuery.toString());
        query.setParameter("companyId", companyId);
        query.setParameter("systemRoleId", systemRoleId);

        return query.getResultList();
    }
    
    @Override
    public void updateUserLastLoginDate(Integer userId){
        Query query = entityManager.createQuery("update User user set user.lastLoginDate = :lastLoginDate where user.id = :userId");
        query.setParameter("lastLoginDate", new Timestamp(new Date().getTime()));
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
