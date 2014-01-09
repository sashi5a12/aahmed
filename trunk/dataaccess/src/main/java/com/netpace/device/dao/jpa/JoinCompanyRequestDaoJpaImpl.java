package com.netpace.device.dao.jpa;

import com.netpace.device.dao.JoinCompanyRequestDao;
import com.netpace.device.entities.JoinCompanyRequest;
import com.netpace.device.entities.enums.JoinCompanyStatus;
import com.netpace.device.vo.JoinCompanyRequestVO;
import com.netpace.device.vo.PageModel;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(JoinCompanyRequestDao.name)
public class JoinCompanyRequestDaoJpaImpl extends GenericDaoJpaImpl<JoinCompanyRequest, Integer> implements JoinCompanyRequestDao {

    private static final Log log = LogFactory.getLog(JoinCompanyRequestDaoJpaImpl.class);

    public JoinCompanyRequestDaoJpaImpl() {
        super(JoinCompanyRequest.class);
    }

    @Override
    public JoinCompanyRequest getRequestByOfferedUserId(Integer userId) {
        Query query = entityManager.createQuery("select jcr from JoinCompanyRequest jcr where jcr.offerTo.id=:userId");
        query.setParameter("userId", userId);
        return (JoinCompanyRequest) query.getSingleResult();
    }

    @Override
    public List<JoinCompanyRequest> getUserInPendingStatus(String companyDomain, Integer userId) {
        Query query = entityManager.createQuery("select jcr from JoinCompanyRequest jcr  "
                + " where status = :status and jcr.offerTo.companyDomain = :companyDomain "
                + " and jcr.offerTo.id = :userId ");
        query.setParameter("companyDomain", companyDomain);
        query.setParameter("userId", userId);
        query.setParameter("status", JoinCompanyStatus.PENDING);
        return query.getResultList();
    }

    @Override
    public List<JoinCompanyRequest> getUserStatus(String companyDomain, Integer userId) {
        Query query = entityManager.createQuery("select jcr from JoinCompanyRequest jcr  "
                + " where jcr.offerTo.companyDomain = :companyDomain "
                + " and jcr.offerTo.id = :userId ");
        query.setParameter("companyDomain", companyDomain);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JoinCompanyRequest> getAllCompanyUsers(Integer userId) {
        StringBuilder strQuery = new StringBuilder();

        strQuery.append("select joinCompanyRequest from JoinCompanyRequest joinCompanyRequest");
        strQuery.append(" inner join fetch joinCompanyRequest.company c inner join fetch c.mainContact");
        strQuery.append(" where c.mainContact.id = :userId and joinCompanyRequest.active = '1'");

        Query query = entityManager.createQuery(strQuery.toString());

        query.setParameter("userId", userId);

        try {
            List<JoinCompanyRequest> joinCompanyRequests = query.getResultList();
            return joinCompanyRequests;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public JoinCompanyRequest getRequestByOfferId(Integer offerId) {
        try {
            return entityManager.find(JoinCompanyRequest.class, offerId);
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<JoinCompanyRequest> getAllCompanyUsers(PageModel<JoinCompanyRequestVO> pageModel, Integer companyId) {

        StringBuilder strQuery = new StringBuilder();

        strQuery.append(" from JoinCompanyRequest joinCompanyRequest inner join fetch joinCompanyRequest.offerTo offerTo");
        strQuery.append(" inner join joinCompanyRequest.company c inner join c.mainContact mainContact");
        //strQuery.append(" where mainContact.id = :userId and joinCompanyRequest.active = '1'");
        //Line above is replaced with this line below
        strQuery.append(" where c.id = :companyId and joinCompanyRequest.active = '1'");


        // Append page model query string
        strQuery.append(super.getPageModelQueryString(pageModel));

        Query query = entityManager.createQuery("select joinCompanyRequest " + strQuery.toString());
        //Line below is commented by Waseem Akram
        //query.setParameter("userId", userId);
        query.setParameter("companyId", companyId);
        super.setPageModelQueryParam(pageModel, query);

        try {
            return query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public Integer getCompanyIdByOfferedUserId(Integer userId) {

        Query query = entityManager.createQuery(
                "select jcr.company.id from JoinCompanyRequest jcr where jcr.offerTo.id=:userId and jcr.status=:status and active='1'");

        query.setParameter("userId", userId);
        query.setParameter("status", JoinCompanyStatus.PENDING);

        try {
            return ((Integer) query.getSingleResult());
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }
}
