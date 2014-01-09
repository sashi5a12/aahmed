package com.netpace.device.handler;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * handle all workflow user task delegation
 *
 * @author trafique
 */
public class WorkflowEventHandler implements JavaDelegate {

    private final static Log log = LogFactory.getLog(WorkflowEventHandler.class);
    
    /**
     * Handle all service tasks events thrown from activiti workflow
     * 
     * @param de
     * @throws Exception 
     */
    @Override
    public void execute(DelegateExecution de) throws Exception {
    }
    
}
