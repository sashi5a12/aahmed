package com.netpace.aims.workflow;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.netpace.aims.bo.application.AimsAppsManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsNotificationConstants;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.spi.WorkflowEntry;
import java.util.*;

public class WorkflowNotification implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(WorkflowNotification.class);

	public void execute(Map transientVars, Map args, PropertySet ps) {
		logger.debug("WorkflowNotification.execute Start:");

		WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
		String eventId = "", stepId = "", currentState = "", previousState = "", previousStatusId = "", newStatusId = "", recordId = "", comments = "";

		if (args.get("eventId") != null) {
			eventId = (String) args.get("eventId");
		}
		if (args.get("stepId") != null) {
			stepId = (String) args.get("stepId");
		}
		if (args.get("currentState") != null) {
			currentState = (String) args.get("currentState");
		}
		if (args.get("previousState") != null) {
			previousState = (String) args.get("previousState");
		}
		if (args.get("previousStatusId") != null) {
			previousStatusId = (String) args.get("previousStatusId");
		}
		if (args.get("newStatusId") != null) {
			newStatusId = (String) args.get("newStatusId");
		}
		if (transientVars.get("recordId") != null) {
			recordId = (String) transientVars.get("recordId");
		}
		if (transientVars.get("comments") != null) {
			comments = (String) transientVars.get("comments");
		}

		try {
			// AimsModuleWorkflows mod=WorkflowManager.getModuleWorkflowByWorkflowId(entry.getId());
			// AimsApp app=AimsAppsManager.getApp(mod.getModuleRecordId().toString());
			AimsApp app = AimsAppsManager.getApp(recordId);
			AimsUser aimAllianceAdminUser = (AimsUser) DBHelper.getInstance().load(AimsUser.class, app.getAimsAllianc().getAimsUserByAdminUserId().toString());
			AimsContact aimAllianceAdminContact = aimAllianceAdminUser.getAimsContact();
			// AimsContact aimAllianceAdminContact = (AimsContact) DBHelper.getInstance().load(AimsContact.class, aimAllianceAdminUser.getAimsContactId().toString());
			AimsLifecyclePhase oldStatus = new AimsLifecyclePhase();			
			AimsLifecyclePhase newStatus = new AimsLifecyclePhase();

			if (StringUtils.isNotEmpty(previousStatusId)){
				oldStatus = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, previousStatusId);
			}
			if (StringUtils.isNotEmpty(newStatusId)){
				newStatus = (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, newStatusId);
			}
			
			String[] events = eventId.split(",");

			for (int i = 0; i < events.length; i++) {
				AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(events[i]);

				if (aimsEvent != null) {
					
					logger.debug("eventId = "+aimsEvent.getEventId());
					logger.debug("Event name = "+aimsEvent.getEventName());
					AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
					aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, app.getAimsAllianceId().toString());
					aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, app.getAppsId().toString());

					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, app.getTitle());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_VERSION, app.getVersion());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_OLD, oldStatus.getPhaseName());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_STATUS_NEW, newStatus.getPhaseName());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_PREVIOUS_STATE, previousState);
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_CURRENT_STATE, currentState);

					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, app.getAimsAllianc().getCompanyName());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME, aimAllianceAdminContact.getFirstName()+ " "+ aimAllianceAdminContact.getLastName());
					aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_COMMENTS, comments);

					aimsEvent.raiseEvent(aimsEventObject);
				}
			}
			logger.debug("WorkflowNotification.execute End:");
		} catch (Exception e) {
			logger.debug("Exception in Workflow Exit Notification");
			logger.error(e, e);
		}

	}

}
