package com.netpace.aims.workflow;

import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.util.AimsConstants;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.WorkflowEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.opensymphony.workflow.basic.BasicWorkflow;
/**
 * @author Adnan Ahmed
 * @Date 20-Nov-2008
 *
 * Workflow pre-function class.	Update workitem status from underway to completed.
 */
public class WorkflowStatusEntry implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(WorkflowStatusEntry.class);

	public void execute(Map transientVars, Map args, PropertySet ps) {
		logger.debug("WorkflowStatusEntry.execute Start:");
		try {
			String workitemId = "", actionId = "", comments = "", modifiedBy = "";
			Date modifiedDate = null;
			WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
			WorkflowContext context = (WorkflowContext) transientVars.get("context");
			BasicWorkflow bwf = new BasicWorkflow(context.getCaller());

			if (transientVars.get("aId") != null) {
				actionId = (String) transientVars.get("aId");
			}
			if (transientVars.get("workitemId") != null) {
				workitemId = (String) transientVars.get("workitemId");
			}
			if (transientVars.get("comments") != null) {
				comments = (String) transientVars.get("comments");
			}
			if (transientVars.get("modifiedBy") != null) {
				modifiedBy = (String) transientVars.get("modifiedBy");
			}
			if (transientVars.get("modifiedDate") != null) {
				modifiedDate = (Date) transientVars.get("modifiedDate");
			}

			WorkflowDescriptor wd = bwf.getWorkflowDescriptor(bwf.getWorkflowName(entry.getId()));
			AimsWorkitem workitem = WorkflowManager.getWorkitemById(new Long(workitemId));

			workitem.setActionTaken(wd.getAction(Integer.parseInt(actionId)).getName());
			workitem.setStatus(AimsConstants.WORKFLOW_COMPLETED);
			workitem.setExitDate(modifiedDate);
			workitem.setComments(comments);
			workitem.setModifiedBy(modifiedBy);
			workitem.setModifiedDate(modifiedDate);
			logger.debug("going to update status of workitem ="+workitemId);
			WorkflowManager.saveWorkItem(workitem);
			logger.debug("WorkflowStatusEntry.execute End:");
		} catch (Exception ee) {
			logger.debug("Exception occured in WorkflowStatusEntry: "+ ee.toString());
			logger.error(ee.getMessage(), ee);
		}
	}
}
