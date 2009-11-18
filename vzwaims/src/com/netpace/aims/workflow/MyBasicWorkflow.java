package com.netpace.aims.workflow;

import com.opensymphony.workflow.basic.BasicWorkflowContext;

/**
 * Customized BasicWorkflow class created by Hassan 
 */

public class MyBasicWorkflow extends MyAbstractWorkflow{

	public MyBasicWorkflow(String caller) {
        super.context = new BasicWorkflowContext(caller);
    }
}
