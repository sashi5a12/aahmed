package com.netpace.device.dao.jpa;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.dao.CompanyDao;
import com.netpace.device.dao.PaggedResult;
import com.netpace.device.entities.Company;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.PartnerUserVO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(CompanyDao.name)
public class CompanyDaoJpaImpl extends GenericDaoJpaImpl<Company, Integer> implements CompanyDao {

    private static final Log log = LogFactory.getLog(CompanyDaoJpaImpl.class);

    public CompanyDaoJpaImpl() {
        super(Company.class);
    }

    @Override
    public Company getCompanyByDomainName(String domainName) {
        Query query = entityManager.createNamedQuery("findCompanyByDomainName");
        query.setParameter("domainName", domainName);
        return (Company) query.getSingleResult();
    }

    @Override
    public Company getRegisteredCompanyByDomainName(String domainName) {
        Query query = entityManager.createQuery("select c from Company c where c.companyDomain= :domainName");
        query.setParameter("domainName", domainName);

        return (Company) query.getSingleResult();
    }

    @Override
    public Company getCompanyByUserId(Integer userId) {
        Query query = entityManager.createQuery("select c from User u JOIN u.company c where u.id= :userId and c.active='1'");
        query.setParameter("userId", userId);
        try {
            Company obj = (Company) query.getSingleResult();
            return obj;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public List<Company> getAllCompanies() {
        Query query = entityManager.createQuery("select c from Company c where c.active='1' ");
        try {
            List<Company> listCompanies = query.getResultList();
            return listCompanies;
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }

    @Override
    public Long getPartnersListCount(PageModel pageModel) {
        Long count = new Long(0);
        StringBuilder qryStr = new StringBuilder();
        List<String> filterValues = null;
        String searchValue=pageModel.getEscapedSearchValue();
        
        if (pageModel.getFilters() != null && pageModel.getFilters().size() > 0) {
            filterValues = VAPUtils.getFilterValueList(pageModel.getFilters());
        }
        log.debug("filterValues: " + filterValues);
        qryStr.append("select count(distinct c.id)");
        qryStr.append("   from Company c , in (c.workFlow) as wf , in (wf.workItems) as wi");
        qryStr.append("   where c.active='1' ");
        qryStr.append("   and wf.workflowType='Partner Workflow'");

        if (filterValues != null && filterValues.size() > 0) {
            qryStr.append(" and (wi.displayTitle in (:filterValuesList)  and wi.status='InProgress')");
        }

        if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
            qryStr.append(" and lower(").append(pageModel.getSearchBy()).append(") like :searchValue ESCAPE '!'");
        }

        Query query = entityManager.createQuery(qryStr.toString());

        if (filterValues != null && filterValues.size() > 0) {
            query.setParameter("filterValuesList", VAPUtils.getFilterValueList(pageModel.getFilters()));
        }

        if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
            query.setParameter("searchValue", "%" + searchValue.toLowerCase().trim() + "%");
        }
        try {
            count = (Long) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return count;
    }

    @Override
    public List<Object[]> getPartnersList(PageModel pageModel) {
        StringBuilder qryStr = new StringBuilder();
        List<String> filterValues = null;
        String searchValue=pageModel.getEscapedSearchValue();
        
        if (pageModel.getFilters() != null && pageModel.getFilters().size() > 0) {
            filterValues = VAPUtils.getFilterValueList(pageModel.getFilters());
        }
        log.debug("filterValues: " + filterValues);
        qryStr.append("select distinct c.id, c.name, user.fullName, c.createdDate, c.workFlowSteps, c.suspended "
                + " from User as user ");
        qryStr.append(" inner join user.userRoles as userroles "
                + " inner join userroles.systemRole as sysroles "
                + " inner join user.company as c, in (c.workFlow) as wf , in (wf.workItems) as wi "
                + " where sysroles.title = :roleName and c.active = '1'");
        qryStr.append(" and wf.workflowType='Partner Workflow'");
        qryStr.append("   ");

        if (filterValues != null && filterValues.size() > 0) {
            qryStr.append(" and (wi.displayTitle in (:filterValuesList)  and wi.status='InProgress')");
        }

        if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
            qryStr.append(" and lower(").append(pageModel.getSearchBy()).append(") like :searchValue ESCAPE '!'");
        }

        qryStr.append("order by lower(").append(pageModel.getSortBy()).append(") ").append(pageModel.isAscending() ? "asc" : "desc");

        Query query = entityManager.createQuery(qryStr.toString());

        query.setParameter("roleName", VAPConstants.ROLE_MPOC);

        if (filterValues != null && filterValues.size() > 0) {
            query.setParameter("filterValuesList", VAPUtils.getFilterValueList(pageModel.getFilters()));
        }

        if (StringUtils.isNotEmpty(pageModel.getSearchBy()) && StringUtils.isNotEmpty(searchValue)) {
            query.setParameter("searchValue", "%" + searchValue.toLowerCase().trim() + "%");
        }

        query.setFirstResult(pageModel.firstResult());
        query.setMaxResults(pageModel.getPageSize());

        List<Object[]> partners = new ArrayList<Object[]>();
        try {
            partners = query.getResultList();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return partners;
    }

    /*
     @Override
     public List<Workflow> getAllCompaniesInWorkflow(){
     Query query = entityManager.createQuery("select w from Workflow w inner join w.company c "
     + " inner join c.mainContact where c.active='1'");
     try {
     List<Workflow> listCompanies = query.getResultList();
     return listCompanies;
     } catch (NoResultException nre) {
     log.debug(nre.getMessage());
     }
     return null;
     }
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getAllPartnerUsersListCount(PageModel<PartnerUserVO> pageModel, Integer companyId) {
        StringBuilder strQuery = new StringBuilder();
        Number count = null;
        List<String> filterValues = null;
        if (pageModel.getFilters() != null && pageModel.getFilters().size() > 0) {
            filterValues = VAPUtils.getFilterValueList(pageModel.getFilters());
        }

        log.debug("filterValues: " + filterValues);

        strQuery.append("SELECT DISTINCT COUNT(u.user_id) ");
        strQuery.append("FROM vap_user u ");
        strQuery.append("LEFT JOIN ");
        strQuery.append("    vap_company_join_offer AS cj ON (u.user_id = cj.offered_to AND cj.`status` LIKE 'PENDING') ");
        strQuery.append("WHERE ((u.is_active = '1') and (cj.company_id = :companyId OR u.company_id = :companyId)) ");

        // Append page model query string
        strQuery.append(super.getPageModelQueryString(pageModel));

        Query query = entityManager.createNativeQuery(strQuery.toString());

        //PARAM1 = companyId
        query.setParameter("companyId", companyId);

        super.setPageModelQueryParam(pageModel, query);
        try {
            count = (Number) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return count.intValue();

    }

    @Override
    @Transactional(readOnly = true)
    public PaggedResult getAllPartnerUsersList(PageModel<PartnerUserVO> pageModel, Integer companyId) {

        StringBuilder strQuery = new StringBuilder();

        strQuery.append("SELECT DISTINCT u.user_id, u.email_address, u.full_name, u.is_active, u.is_enable, u.created_date, cj.offer_id, cj.offered_to, cj.`status` ");
        strQuery.append("FROM vap_user u ");
        strQuery.append("LEFT JOIN vap_company_join_offer AS cj ON (u.user_id = cj.offered_to AND cj.`status` LIKE 'PENDING') ");
        strQuery.append("WHERE ((u.is_active='1') and (cj.company_id = :companyId OR u.company_id = :companyId))");

        // Append page model query string
        strQuery.append(super.getPageModelQueryString(pageModel));

        Query query = entityManager.createNativeQuery(strQuery.toString());

        //PARAM1 = companyId
        query.setParameter("companyId", companyId);

        super.setPageModelQueryParam(pageModel, query);

        query.setFirstResult(pageModel.firstResult());
        query.setMaxResults(pageModel.getPageSize());

        Integer count = getAllPartnerUsersListCount(pageModel, companyId);

        List resultList = query.getResultList();

        return new PaggedResult(resultList, count);
    }

    // Soft delete company by setting is_active to 0 using stored procedure call
    @Override
    @Transactional
    public void deleteCompany(Integer companyId, String username) {

        Query query = entityManager.createNativeQuery("{CALL delete_company(?,?)};");

        //PARAM1 = companyId
        //PARAM2 = username
        query.setParameter(1, companyId);
        query.setParameter(2, username);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyByName(String companyName) {
        Query query = entityManager.createNamedQuery("findCompanyByName");
        query.setParameter("companyName", companyName);
        try {
            return (Company) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(Integer companyId) {
        Query query = entityManager.createNamedQuery("findCompanyById");
        query.setParameter("companyId", companyId);
        try {
            return (Company) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());
        }
        return null;
    }
}
