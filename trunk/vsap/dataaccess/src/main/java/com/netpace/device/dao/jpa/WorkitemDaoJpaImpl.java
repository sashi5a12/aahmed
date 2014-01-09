package com.netpace.device.dao.jpa;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.dao.PaggedResult;
import com.netpace.device.dao.WorkitemDao;
import com.netpace.device.entities.Workitem;
import com.netpace.device.utils.enums.ProductStatus;
import com.netpace.device.utils.enums.WorkflowStep;
import com.netpace.device.utils.enums.WorkflowType;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.PageModel;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(value = WorkitemDao.name)
public class WorkitemDaoJpaImpl extends GenericDaoJpaImpl<Workitem, Integer> implements WorkitemDao {

    private final static Log log = LogFactory.getLog(WorkitemDaoJpaImpl.class);

    public WorkitemDaoJpaImpl() {
        super(Workitem.class);
    }

    @Override
    public Workitem getWorkitem(String title, WorkitemStatus status, Integer workflowId) {
        Workitem workitem;
        Query query = entityManager.createNamedQuery("findWorkitemByCriteria");
        query.setParameter("title", title);
        query.setParameter("status", status);
        query.setParameter("workflowId", workflowId);

        try {
            workitem = (Workitem) query.getSingleResult();
        } catch (NoResultException e) {
            workitem = null;
        }

        return workitem;
    }

    @Override
    public PaggedResult<Workitem> getPaggedWorkitems(WorkitemStatus status, String[] userRoles, PageModel pageModel, Integer companyId, boolean isPartner) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" from Workitem workitem JOIN workitem.workflow workflow ");
        strQuery.append("  JOIN workflow.company company LEFT JOIN workflow.product product");
        strQuery.append(" where workitem.active = '1'");
        strQuery.append(" and workitem.status = :status ");
        strQuery.append(" and workitem.title != :offlineNDATitle ");
        strQuery.append(" and company.suspended = :suspended ");
        strQuery.append(" and ( product.productId is null");
        strQuery.append("  or (product.productId is not null and product.status != :deniedStatus) ");
        strQuery.append(" )");
        // Append companyId query string
        if (isPartner) {
            strQuery.append(" and company.id = :companyId ");
        }
        strQuery.append(" and workflow.active = '1' ");
        // Append allowedRoles query string
        strQuery.append(" and " + this.getAllowedRolesQueryString(userRoles));
        // Append page model query string
        strQuery.append(super.getPageModelQueryString(pageModel));

        Query query = entityManager.createQuery("select count(workitem) " + strQuery.toString());
        query.setParameter("status", status);
        query.setParameter("offlineNDATitle", WorkflowStep.OfflineCertificationNDA.toString());
        query.setParameter("suspended", false);
        query.setParameter("deniedStatus", ProductStatus.Denied.toString());
        // set companyId param
        if (isPartner) {
            query.setParameter("companyId", companyId);
        }
        // set allowedRoles query params
        this.setAllowedRolesQueryParam(userRoles, query);
        // set page model query param
        super.setPageModelQueryParam(pageModel, query);
        Number number = (Number) query.getSingleResult();

        query = entityManager.createQuery("select workitem "+ strQuery.toString());
        query.setParameter("status", status);
        query.setParameter("offlineNDATitle", WorkflowStep.OfflineCertificationNDA.toString());
        query.setParameter("suspended", false);
        query.setParameter("deniedStatus", ProductStatus.Denied.toString());
        // set company id param
        if (isPartner) {
            query.setParameter("companyId", companyId);
        }
        // set allowedRoles query params
        this.setAllowedRolesQueryParam(userRoles, query);
        // set page model query param
        super.setPageModelQueryParam(pageModel, query);
        query.setFirstResult(pageModel.firstResult());
        query.setMaxResults(pageModel.getPageSize());

        return new PaggedResult(query.getResultList(), number.intValue());
    }

    @Override
    public List<Workitem> getPartnerWorkitems(Integer companyId, WorkitemStatus status, String[] userRoles) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select workitem from Workitem workitem inner join fetch workitem.workflow workflow inner join workitem.workflow.company company");
        strQuery.append(" where workitem.active = '1'");
        strQuery.append(" and workflow.workflowType = '" + WorkflowType.PartnerWorkflow.getLabel() + "' ");
        strQuery.append(" and company.id = :companyId");
        strQuery.append(" and workitem.status = :status ");
        strQuery.append(" and ( ");
        strQuery.append(" (" + this.getAllowedRolesQueryString(userRoles) + ")");

        /*
         List rolesList = Arrays.asList(userRoles);
         if( rolesList.contains(VAPConstants.ROLE_VERIZON_ADMIN) ){
         strQuery.append(" OR ( company.workFlowSteps = :workFlowStep )");
         }*/
        strQuery.append(" ) ");

        Query query = entityManager.createQuery(strQuery.toString());
        query.setParameter("companyId", companyId);
        query.setParameter("status", status);
        this.setAllowedRolesQueryParam(userRoles, query);
        /*
         if( rolesList.contains(VAPConstants.ROLE_VERIZON_ADMIN) ){
         query.setParameter("workFlowStep", WorkflowStep.OfflineCertificationNDA.toString());
         }*/

        return query.getResultList();
    }

    private String getAllowedRolesQueryString(String[] userRoles) {
        StringBuilder strQuery = new StringBuilder();
        if (userRoles != null && userRoles.length > 0) {
            int i = 0;
            strQuery.append(" ( workitem.allowedRoles like :allowedRole" + i++);
            for (; i < userRoles.length; i++) {
                strQuery.append(" or workitem.allowedRoles like :allowedRole" + i);
            }
            strQuery.append(" )");
        }

        return strQuery.toString();
    }

    private void setAllowedRolesQueryParam(String[] userRoles, Query query) {
        if (userRoles != null && userRoles.length > 0) {
            int i = 0;
            query.setParameter("allowedRole" + i, "%" + userRoles[i++] + "%");
            for (; i < userRoles.length; i++) {
                query.setParameter("allowedRole" + i, "%" + userRoles[i] + "%");
            }
        }

    }

    @Override
    public List<Workitem> getProductWorkItemsByTitle(String title, Integer productId, String op) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select workitem from Workitem workitem ");
        strQuery.append(" where workitem.active = '1'");
        if ("equal".equals(op)) {
            strQuery.append(" and workitem.title=:title ");
        } else if ("like".equals(op)) {
            strQuery.append(" and lower(workitem.title) like :title ");
        } else {
            strQuery.append(" and (workitem.title in (:title)) ");
        }
        strQuery.append(" and workitem.workflow.product.productId = :productId");

        Query query = entityManager.createQuery(strQuery.toString());
        if ("equal".equals(op)) {
            query.setParameter("title", title);
        } else if ("like".equals(op)) {
            query.setParameter("title", "%" + title + "%".toLowerCase());
        } else {
            query.setParameter("title", VAPUtils.getListFromCommaValues(title));
        }
        query.setParameter("productId", productId);

        return query.getResultList();
    }

    @Override
    public List<Workitem> getProductWorkitems(Integer productId, WorkitemStatus status, String[] userRoles) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select workitem from Workitem workitem ");
        strQuery.append(" where workitem.active = '1'");
        if (userRoles != null && userRoles.length > 0) {
            strQuery.append(" and workitem.status=:status ");
            strQuery.append(" and (workitem.allowedRoles like '%" + StringUtils.join(userRoles, "%' or workitem.allowedRoles like '%") + "%') ");
//       		strQuery.append(" and lower(workitem.title) not like '%denied%'");       		
            strQuery.append(" and workitem.title != 'Approved'");
        } else {
            strQuery.append(" and (workitem.status=:status or workitem.title = 'Approved')");
        }
        strQuery.append(" and workitem.workflow.product.productId = :productId");

        Query query = entityManager.createQuery(strQuery.toString());
        query.setParameter("status", status);
        query.setParameter("productId", productId);

        return query.getResultList();
    }

    @Override
    public List<Workitem> getPartnerWorkItemsByTitle(Integer companyId, WorkflowStep title) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select workitem from Workitem workitem ");
        strQuery.append(" where workitem.active = '1'");
        strQuery.append(" and workflow.workflowType = '" + WorkflowType.PartnerWorkflow.getLabel() + "' ");
        strQuery.append(" and workitem.title=:title ");
        strQuery.append(" and workitem.workflow.company.id = :companyId");

        Query query = entityManager.createQuery(strQuery.toString());
        query.setParameter("companyId", companyId);
        query.setParameter("title", title.name());

        return query.getResultList();
    }

    @Override
    public List<Workitem> getDelayedWorkitems(Date olderThanDate) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select workitem from Workitem workitem JOIN workitem.workflow workflow ");
        strQuery.append("  JOIN workflow.company company LEFT JOIN workflow.product product");
        strQuery.append(" where workitem.active = '1'");
        strQuery.append(" and workitem.status = :status ");
        strQuery.append(" and workitem.title != :offlineNDATitle ");
        strQuery.append(" and company.suspended = :suspended ");
        strQuery.append(" and ( product.productId is null");
        strQuery.append("  or (product.productId is not null and product.status != :deniedStatus) ");
        strQuery.append(" )");
        strQuery.append(" and workflow.active = '1' ");
        strQuery.append(" and workitem.startDate < :olderThanDate");
        
        Query query = entityManager.createQuery(strQuery.toString());
        query.setParameter("status", WorkitemStatus.InProgress);
        query.setParameter("offlineNDATitle", WorkflowStep.OfflineCertificationNDA.toString());
        query.setParameter("suspended", false);
        query.setParameter("deniedStatus", ProductStatus.Denied.toString());
        query.setParameter("olderThanDate", new Timestamp(olderThanDate.getTime()));

        return query.getResultList();
    }

}