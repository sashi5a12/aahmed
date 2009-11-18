package com.netpace.aims.workflow;

import com.netpace.aims.bo.application.AimsBrewPdf;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.BrewApplicationManager;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.spi.WorkflowEntry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Adnan Ahmed
 * @Date 20-Nov-2008
 *
 * Workflow post function class. Update application status.
 */

public class ApplicationStatusEntry implements FunctionProvider {

	private static final Log logger = LogFactory.getLog(ApplicationStatusEntry.class);

	public void execute(Map transientVars, Map args, PropertySet ps) {
		logger.debug("ApplicationStatusEntry.execute Start:");
		ByteArrayOutputStream baos = null;
		try {
			String oldStatus = "", modifiedBy = "", recordId = "", platformId = "", statusRejected = "", rfi="";
			//Long ringNumber = Long.valueOf("0");
			String ringNumber = "";
			String newStatus = "";
			String pendingTaxCategory = "";
			
			Date modifiedDate = null;
			WorkflowEntry entry = (WorkflowEntry) transientVars.get("entry");
			

			if (transientVars.get("recordId") != null) {
				recordId = (String) transientVars.get("recordId");
			}
			if (args.get("oldStatus") != null) {
				oldStatus = (String) args.get("oldStatus");
			}
			if (args.get("platformId") != null) {
				platformId = (String) args.get("platformId");
			}
			if (args.get("pendingTaxCategory") != null) {
				pendingTaxCategory = (String) args.get("pendingTaxCategory");
			}
			if (args.get("ringNumber") != null) {
				//ringNumber =  Long.valueOf((String) args.get("ringNumber"));
				ringNumber = (String) args.get("ringNumber");
			}
			if (args.get("newStatus") != null) {
				newStatus = (String) args.get("newStatus");
			}
			if (transientVars.get("modifiedBy") != null) {
				modifiedBy = (String) transientVars.get("modifiedBy");
			}
			if (transientVars.get("modifiedDate") != null) {
				modifiedDate = (Date) transientVars.get("modifiedDate");
			}
			if (args.get("statusRejected") != null) {
				statusRejected = (String) args.get("statusRejected");
			}
			if (args.get("rfi") != null) {
				rfi = (String) args.get("rfi");
			}

			logger.debug("Update status of platform="+platformId);
			logger.debug("recordId="+recordId);

			if (AimsConstants.BREW_PLATFORM_ID.toString().equals(platformId)) {
				AimsAppLite app = ApplicationsManagerHelper.getAimsAppLite(new Long(recordId));
				app.setLastUpdatedBy(modifiedBy);
				app.setLastUpdatedDate(modifiedDate);
				if (StringUtils.isEmpty(statusRejected)) {
					
					//From Evaluate to Under Review
					if (oldStatus.equals(AimsConstants.EVALUATED_ID.toString())) {						
						app.setAimsLifecyclePhaseId(AimsConstants.PHASE_UNDER_REVIEW);
						logger.debug("From evaluated to under review");
					} 
					
					//From Under Review to Evaluated if request for more info action performed
					else if (oldStatus.equals(AimsConstants.PHASE_UNDER_REVIEW.toString()) && StringUtils.isNotEmpty(rfi) && "true".equals(rfi)) {
						app.setAimsLifecyclePhaseId(AimsConstants.EVALUATED_ID);
						logger.debug("From under review to evaluated (RFI)");
					}

					//From Under Review to Accepted
					else if (oldStatus.equals(AimsConstants.PHASE_UNDER_REVIEW.toString()) && StringUtils.isEmpty(rfi)) {
						app.setAimsLifecyclePhaseId(AimsConstants.ACCEPTANCE_ID);
						HashMap  appMap=BrewApplicationManager.getUserGuideData(new Long(recordId), null);	
						baos = new ByteArrayOutputStream();
						AimsBrewPdf.getneratePdf(appMap, baos);
						logger.debug("From under review to accepted");
					}
				}
				
				//Reject Application
				else if ("true".equals(statusRejected)) {
					app.setAimsLifecyclePhaseId(AimsConstants.PHASE_REJECTED);
					logger.debug("reject application");
				}
				ApplicationsManagerHelper.updateApplication(app, baos);
			}

			// for java life cycle phases

			if (AimsConstants.JAVA_PLATFORM_ID.toString().equals(platformId)) {

				AimsAppLite app = ApplicationsManagerHelper.getAimsAppLite(new Long(recordId));
				app.setLastUpdatedBy(modifiedBy);
				app.setLastUpdatedDate(modifiedDate);
				
				if (StringUtils.isEmpty(statusRejected)) 
				{
					app.setAimsLifecyclePhaseId(new Long(newStatus) );
				}
				else if ("true".equals(statusRejected)) 
				{
					app.setAimsLifecyclePhaseId(AimsConstants.PHASE_JAVA_REJECTED);
					logger.debug("reject application");
				}				
            					
				ApplicationsManagerHelper.updateApplication(app, baos);
			}

			logger.debug("ApplicationStatusEntry.execute End:");
		} catch (Exception ee) {
			logger.debug("Exception occured in ApplicationStatusEntry: "+ ee.toString());
			ee.printStackTrace();
		} finally {
			if (baos != null){
				try {
					baos.close();
				} catch (IOException e) {}
			}
		}
	}
}
