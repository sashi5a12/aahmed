package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.Workflow;
import javax.annotation.Resource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author trafique
 */
public class WorkflowDaoTest extends AbstractDaoTest{
    
    protected final Log log = LogFactory.getLog(getClass());
    @Resource(name=WorkflowDao.name)
    WorkflowDao workflowDao;
    
    @Test
    public void testAll(){
        //testGetNotifParamsByWorkflowId(113);
    }    
    
    /**
     * Test of getWorkflowByCompanyId method, of class WorkflowDao.
     */
    public void testGetWorkflowByCompanyId(Integer companyId) {
        log.info("getWorkflowByCompanyId");
        Workflow result = workflowDao.getWorkflowByCompanyId(companyId);
    }

    /**
     * Test of getNotifParamsByWorkflowId method, of class WorkflowDao.
     */
    public void testGetNotifParamsByWorkflowId(Integer workflowId) {
        log.info("getNotifParamsByWorkflowId");
        Object[] result = workflowDao.getNotifParamsByWorkflowId(workflowId);
        log.info(ToStringBuilder.reflectionToString(result));
    }

}
