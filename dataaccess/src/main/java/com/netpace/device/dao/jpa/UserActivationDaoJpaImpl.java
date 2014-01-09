package com.netpace.device.dao.jpa;

import com.netpace.device.dao.UserActivationDao;
import com.netpace.device.entities.UserActivation;
import com.netpace.device.utils.DateUtils;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(UserActivationDao.name)
public class UserActivationDaoJpaImpl extends GenericDaoJpaImpl<UserActivation, Long> implements UserActivationDao {

    public UserActivationDaoJpaImpl() {
        super(UserActivation.class);
    }
    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public UserActivation search(String userName) {
        Query query = entityManager.createQuery("select u from UserActivation u where u.userName= :userName");
        query.setParameter("userName", userName);
        try {
            UserActivation obj = (UserActivation) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        } 

        return null;
    }

    @Override
    public UserActivation searchByEmailAddress(String email) {
        Query query = entityManager.createQuery("select u from UserActivation u where u.emailAddress= :email");
        query.setParameter("email", email);
        try {
            UserActivation obj = (UserActivation) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        } 

        return null;
    }

    @Override
    public UserActivation searchByActivationCode(String activationCode) {
        Query query = entityManager.createQuery("select u from UserActivation u where u.activationCode= :activationCode");
        query.setParameter("activationCode", activationCode);
        try {
            UserActivation obj = (UserActivation) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        } 

        return null;
    }
    
    @Override
    public UserActivation searchByActivationCodeAndType(String activationCode, String activationType) {
        Query query = entityManager.createQuery("select u from UserActivation u"
                + " where u.activationCode= :activationCode"
                + " and u.activationType= :activationType"
                );
        query.setParameter("activationCode", activationCode);
        query.setParameter("activationType", activationType);
        try {
            UserActivation obj = (UserActivation) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        } 
        return null;
    }

    @Override
    public void removeExpiredRecords() {
        removeExpiredRecords(5);
    }
    
    @Override
    public void removeExpiredRecords(Integer noOfDays) {
        Date expiry = DateUtils.addDaysToCurrentDate(noOfDays * -1);
        Timestamp expiryDate = new Timestamp(expiry.getTime());
        
        Query query = entityManager.createQuery("delete from UserActivation u where u.createdDate <= :expiryDate");
        query.setParameter("expiryDate",expiryDate);
        int recordsDeleted = query.executeUpdate();
        log.debug("Expired records count is "+recordsDeleted);
    }
    
    @Override
    public void removeDuplicateInviteUserRecord(String emailAddress, String activationType) {
        Query query = entityManager.createQuery("delete from UserActivation u where u.emailAddress = :emailAddress and u.activationType = :activationType");
        query.setParameter("emailAddress", emailAddress);
        query.setParameter("activationType", activationType);
        
        int recordsDeleted = query.executeUpdate();
        log.debug("Removed duplicate invite user record count is " + recordsDeleted);
    }
    
    @Override
    public void removeDuplicateInviteUserRecord(String emailAddress) {
        Query query = entityManager.createQuery("delete from UserActivation u where u.emailAddress = :emailAddress");
        query.setParameter("emailAddress", emailAddress);
        
        int recordsDeleted = query.executeUpdate();
        log.debug("Removed duplicate invite partner user record count is " + recordsDeleted);
    }
}
