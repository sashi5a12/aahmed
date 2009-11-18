package com.netpace.aims.workflow;

import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.model.workflow.AimsModuleWorkflows;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.util.AimsConstants;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.spi.WorkflowEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.opensymphony.workflow.basic.BasicWorkflow;

/**
 * @author Adnan Ahmed
 * @date 20-Nov-2008
 * 
 * Workflow post function class . Change workflow status from started to completed.  
 */
public class WorkflowCompletion implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(WorkflowCompletion.class);

	public void execute(Map transientVars, Map args, PropertySet ps) {
		logger.debug("WorkflowCompletion.execute Start:");
		try {
			String workitemId = "", modifiedBy = "";
			Date modifiedDate = null;
			WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
			WorkflowContext context = (WorkflowContext) transientVars.get("context");
			BasicWorkflow bwf = new BasicWorkflow(context.getCaller());

			if (transientVars.get("workitemId") != null) {
				workitemId = (String) transientVars.get("workitemId");
			}
			if (transientVars.get("modifiedBy") != null) {
				modifiedBy = (String) transientVars.get("modifiedBy");
			}
			if (transientVars.get("modifiedDate") != null) {
				modifiedDate = (Date) transientVars.get("modifiedDate");
			}

			AimsWorkitem workitem = WorkflowManager.getWorkitemById(new Long(workitemId));
			AimsModuleWorkflows module = workitem.getModuleWorkflows();
			module.setStatus(AimsConstants.WORKFLOW_COMPLETED);
			module.setModifiedBy(modifiedBy);
			module.setModifiedDate(modifiedDate);
			logger.debug("Completing workflow of recordId="+ module.getModuleRecordId() + " at step=" + workitem.getStepName());
			WorkflowManager.saveModuleWorkflow(module);
			
			logger.debug("WorkflowCompletion.execute End:");
		} catch (Exception ee) {
			logger.debug("Exception occured in WorkflowCompletion: " + ee.toString());
			logger.error(ee, ee);
		}
	}
}
