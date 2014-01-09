package com.netpace.device.dao.jpa;

import com.netpace.device.dao.WorkflowDao;
import com.netpace.device.entities.Workflow;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author trafique
 */
@Repository(value = WorkflowDao.name)
public class WorkflowDaoJpaImpl extends GenericDaoJpaImpl<Workflow, Integer> implements WorkflowDao {
    
    private final static Log log = LogFactory.getLog(WorkitemDaoJpaImpl.class);

    public WorkflowDaoJpaImpl() {
        super(Workflow.class);
    }

    public Workflow getWorkflowByCompanyId(Integer companyId){
		log.debug("companyId: "+companyId);
		
		StringBuilder qryStr=new StringBuilder();
		qryStr.append(" from Workflow wf where wf.company.id=:companyId and wf.workflowType='Partner Workflow' ");
		
		Workflow wf=new Workflow();
		Query qry = entityManager.createQuery(qryStr.toString());
		qry.setParameter("companyId", companyId);
		wf=(Workflow)qry.getSingleResult();
		return wf;
    }

    @Override
    public Object[] getNotifParamsByWorkflowId(Integer workflowId) {
        
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select workflow.company.id, workflow.company.name, workflow.company.companyDomain,");
        strQuery.append(" workflow.workflowType, product.productName");
        strQuery.append(" from Workflow workflow left outer join workflow.product product");
        strQuery.append(" where workflow.id = :workflowId");
        
        Query query = entityManager.createQuery(strQuery.toString());
        query.setParameter("workflowId", workflowId);

        List<Object[]> list = query.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

}
