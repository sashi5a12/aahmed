package com.netpace.aims.workflow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.model.workflow.AimsModuleWorkflows;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.util.AimsConstants;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;

/**
 * @author Adnan Ahmed
 * @Date 20-Nov-2008
 *
 * Workflow post function class. create workitem record in AIMS_WORKITEM after workflow state transition
 */

public class WorkItemEntry implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(WorkItemEntry.class);

	public void execute(Map transientVars, Map args, PropertySet ps) {
		logger.debug("WorkItemEntry.execute Start:");
		try {
			String createdBy = "", workflowDetailId = "", recordId = "";
			Date createdDate = null;
			WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
			WorkflowContext context = (WorkflowContext) transientVars.get("context");
			BasicWorkflow bwf = new BasicWorkflow(context.getCaller());
			AimsModuleWorkflows module = null;
			String comments = null;

			if (transientVars.get("createdBy") != null) {
				createdBy = (String) transientVars.get("createdBy");
			}
			if (transientVars.get("workflowDetailId") != null) {
				workflowDetailId = (String) transientVars.get("workflowDetailId");
			}
			if (transientVars.get("recordId") != null) {
				recordId = (String) transientVars.get("recordId");
			}
			if (transientVars.get("createdDate") != null) {
				createdDate = (Date) transientVars.get("createdDate");
			}
			if (transientVars.get("comments") != null ) {
				comments = (String) transientVars.get("comments");
			}

			WorkflowDescriptor wd = bwf.getWorkflowDescriptor(bwf.getWorkflowName(entry.getId()));
			List steps = bwf.getCurrentSteps(entry.getId());

			if (StringUtils.isNotEmpty(workflowDetailId) && StringUtils.isNotEmpty(recordId)) {
				module = WorkflowManager.getModuleWorkflowByRecordIdAndStatus(new Long(recordId), new Long(workflowDetailId),AimsConstants.WORKFLOW_STARTED);
			}
			
			/*
			 * Create workitem as workflow transition occurs
			 */
			if (module != null) {
				for (int a = 0; a < steps.size(); a++) {
					Step step = (Step) steps.get(a);
					AimsWorkitem workitem = new AimsWorkitem();

					if("Rejected".equals(wd.getStep(step.getStepId()).getName())){
						continue;
					}
					workitem.setModuleWorkflows(module);
					workitem.setWorkflowStateId(new Long(step.getStepId()));
					workitem.setStepName(wd.getStep(step.getStepId()).getName());
					workitem.setEntryDate(createdDate);
					workitem.setActionTaken(AimsConstants.WORKFLOW_NO_ACTION_TAKEN);
					workitem.setStatus(AimsConstants.WORKFLOW_UNDERWAY);
					workitem.setDescription(wd.getStep(step.getStepId()).getName());
					workitem.setPrivilege(step.getOwner());
					workitem.setCreatedBy(createdBy);
					workitem.setCreatedDate(createdDate);
					workitem.setComments(comments);					
					logger.debug("Creating record for recordId="+recordId);
					logger.debug("ModuleWorkflowId"+module.getModuleWorkflowId());
					logger.debug("stepName= "+workitem.getStepName());
					WorkflowManager.saveWorkItem(workitem);
				}
			}
			logger.debug("WorkItemEntry.execute End:");
		} catch (Exception ee) {
			logger.debug("Exception occured in WorkItemEntry: "+ ee.toString());
			logger.error(ee, ee);
		}

	}
}
