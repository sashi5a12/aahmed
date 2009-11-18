package com.netpace.aims.workflow.vds;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
 * @author Nauman
 * @Date 26-May-2009
 *
 * Workflow post function class. create workitem record in AIMS_WORKITEM after workflow state transition
 */

public class WorkItemEntry implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(WorkItemEntry.class);

	public void execute(Map transientVars, Map args, PropertySet ps) 
	{
		logger.debug("WorkItemEntry.execute Start:");
		try 
		{
			String createdBy = "", workflowDetailId = "", recordId = "";
			Date createdDate = null;
			WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
			WorkflowContext context = (WorkflowContext) transientVars.get("context");
			BasicWorkflow bwf = new BasicWorkflow(context.getCaller());
			AimsModuleWorkflows module = null;
			String comments = null;
			List<Integer> parallelStepIds = null; 
			String actionPageUrl = null;

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
			//get all parallel steps
			if (args.get("parallelStepIds") != null) {
				StringTokenizer st = new StringTokenizer((String) args.get("parallelStepIds"),",");
				parallelStepIds = new ArrayList<Integer>();
				while(st.hasMoreElements()){
					Integer sid = new Integer(st.nextToken());
					parallelStepIds.add(sid);
					
					if(logger.isDebugEnabled())
						logger.debug("parallel stepId="+sid);
				}
			}
			
			//get actionPageUrl
			if (args.get("actionPageUrl") != null) {
				if(logger.isDebugEnabled())
					logger.debug("actionPageUrl="+actionPageUrl);
				
				actionPageUrl = (String)args.get("actionPageUrl");
				actionPageUrl = URLDecoder.decode(actionPageUrl, "UTF-8");
				actionPageUrl = actionPageUrl.replace("{appId}", recordId);
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
					//ignore match steps.
					boolean parallelStep = false;	
					if (parallelStepIds != null && parallelStepIds.size() > 0) {
						for(int c=0; c < parallelStepIds.size(); c++){
							if( parallelStepIds.get(c).intValue() == step.getStepId()){
								parallelStep = true;
								break;
							}
						}
					}
					
					if(parallelStep)
						continue;
					
					if(logger.isDebugEnabled()){
						logger.debug("Current Step ID="+step.getStepId());
						logger.debug("parallel Step="+parallelStep);
						logger.debug("actionPageUrl="+actionPageUrl);
					}
					
					//save work item entry
					workitem.setModuleWorkflows(module);
					workitem.setWorkflowStateId(new Long(step.getStepId()));
					workitem.setStepName(wd.getStep(step.getStepId()).getName());
					workitem.setEntryDate(createdDate);
					//Set action to empty string.
					workitem.setActionTaken(AimsConstants.WORKFLOW_NO_ACTION_TAKEN);
					workitem.setStatus(AimsConstants.WORKFLOW_UNDERWAY);
					workitem.setDescription(wd.getStep(step.getStepId()).getName());
					workitem.setPrivilege(step.getOwner());
					workitem.setCreatedBy(createdBy);
					workitem.setCreatedDate(createdDate);
					workitem.setComments(comments);
					workitem.setActionPageUrl(actionPageUrl);
					
					if(logger.isDebugEnabled()){
						logger.debug("Creating record for recordId="+recordId);
						logger.debug("ModuleWorkflowId"+module.getModuleWorkflowId());
						logger.debug("stepName= "+workitem.getStepName());
						logger.debug("actionPageUrl="+actionPageUrl);
					}
					
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

