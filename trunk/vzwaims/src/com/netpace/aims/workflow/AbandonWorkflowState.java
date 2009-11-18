package com.netpace.aims.workflow;

import com.netpace.aims.bo.workflow.WorkflowManager;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.spi.WorkflowEntry;

import java.util.Map;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbandonWorkflowState implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(AbandonWorkflowState.class);

	public void execute(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
		
		logger.debug("AbondonWorkflowState.execute Start:");
		
		String stateId = "", recordId="";
		WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");

		if (args.get("stateId") != null) {
			stateId = (String) args.get("stateId");
		}
		if (transientVars.get("recordId") != null){
			recordId = (String)transientVars.get("recordId");
		}
		if (StringUtils.isNotEmpty(stateId) && StringUtils.isNumeric(stateId)){
			try {
				WorkflowManager.updateWorkitemByStateId(new Long(stateId), new Long(recordId));
			} catch (HibernateException e) {
				logger.error(e,e);
			}
		}
		logger.debug("AbondonWorkflowState.execute End:");
	}
}
