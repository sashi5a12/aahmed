package com.netpace.device.dao;

import com.netpace.device.entities.Workflow;

/**
 *
 * @author trafique
 */
public interface WorkflowDao extends GenericDao<Workflow, Integer> {

    public static final String name = "workflowDao";
    
    public Workflow getWorkflowByCompanyId(Integer companyId);
    
    public Object[] getNotifParamsByWorkflowId(Integer workflowId);
}
