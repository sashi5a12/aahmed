package com.netpace.aims.workflow;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.workflow.AimsWorkitem;
import com.netpace.aims.util.AimsConstants;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.loader.WorkflowDescriptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.*;

public class WorkflowJournalEntry implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(WorkflowJournalEntry.class);

	public void execute(Map transientVars, Map args, PropertySet ps) {

		String jText1 = "", jText2 = "", actionId = "", actionName = "", fromState = "", toStates = "", createdBy="", workitemId="", actionEntry="", recordId="";
        Date createdDate=null;
		try {

			WorkflowDescriptor descriptor = (WorkflowDescriptor) transientVars.get("descriptor");

			if (transientVars.get("aId") != null) {
				actionId = (String) transientVars.get("aId");
				actionName = descriptor.getAction(Integer.parseInt(actionId)).getName();
			}
			if (transientVars.get("createdBy") != null) {
				createdBy = (String) transientVars.get("createdBy");
			}
			if (transientVars.get("workitemId") != null) {
				workitemId = (String) transientVars.get("workitemId");
			}
			if (transientVars.get("recordId") != null) {
				recordId = (String) transientVars.get("recordId");
			}
			if (transientVars.get("createdDate") != null) {
				createdDate = (Date) transientVars.get("createdDate");
			}
			if (args.get("fromState") != null) {
				fromState = (String) args.get("fromState");
			}
			if (args.get("toStates") != null) {
				toStates = (String) args.get("toStates");
			}
			if (args.get("actionEntry") != null) {
				actionEntry = (String) args.get("actionEntry");
			}
			
			logger.debug("workitemId= "+workitemId);
			logger.debug("recordId= "+recordId);
			logger.debug("fromState= "+fromState);
			logger.debug("toStates= "+toStates);
			logger.debug("actionEntry= "+actionEntry);
								
			AimsWorkitem wi=new AimsWorkitem();
			if (StringUtils.isNotEmpty(workitemId)){
				wi=WorkflowManager.getWorkitemById(new Long(workitemId));
			}
			List journalEntries=new ArrayList();
			if (StringUtils.isNotEmpty(toStates)){
				jText1 = "Workflow state changed from "+fromState+" to "+toStates;
	            AimsJournalEntry stateChanged = new AimsJournalEntry();
	            stateChanged.setJournalText(jText1);
	            stateChanged.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE); 
	            stateChanged.setCreatedBy(createdBy);
	            stateChanged.setCreatedDate(createdDate);
	            stateChanged.setAimsAppId(new Long(recordId));
	            journalEntries.add(stateChanged);
			}
            
			if (StringUtils.isEmpty(actionEntry) && StringUtils.isNotEmpty(wi.getStepName())){
				jText2 = actionName+" on "+wi.getStepName()+" taken";
				AimsJournalEntry actionTaken = new AimsJournalEntry();
				actionTaken.setJournalText(jText2);
				actionTaken.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE); 
				actionTaken.setCreatedBy(createdBy);
				actionTaken.setCreatedDate(createdDate);
				actionTaken.setAimsAppId(new Long(recordId));
				journalEntries.add(actionTaken);
			}
			
			if (journalEntries.size() > 0){
				AimsApplicationsManager.saveJournalEntries(journalEntries);
			}
                                    
		} catch (Exception e) {
			logger.debug("Exception in Workflow Journal Entry " + e);
			logger.error(e,e);
		}

	}
	
	
	
}
