package com.netpace.aims.controller.reports;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.*;

import java.io.*;
import java.sql.*;

import java.util.*;

import com.netpace.aims.model.reports.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.bo.security.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.util.*;



import com.netpace.aims.controller.BaseAction;

/**
 * This class takes care of getting the data for the Application Evaluation Report.
 *
 * @struts.action path="/appEvaluation"                
 *                scope="request" 
 *                input="/reports/report.jsp"               
 * @struts.action-forward name="view" path="/reports/appEvalReport.jsp"
 * @struts.action-forward name="excel" path="/reports/appEvalReportExcel.jsp"
 * @author Rizwan Qazi
 */

public class AppEvaluationAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(AppEvaluationAction.class.getName());
  
    /**
     * This method gets the data.
     * It calls the Application Evaluation Report manager with the userid and gets a 
     * Report BO.
     * 
	 *
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        
        HttpSession session = request.getSession();
				Long currentUserAllianceId = ((AimsUser)session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
				String format_type = StringFuncs.NullValueReplacement(request.getParameter("format_type"));
				String forward = "view";
				
					//CHECK ACCESS
				if (!AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.REPORT_APPLICATION_EVALUATION_FORM, AimsSecurityManager.SELECT))
					throw new AimsSecurityException();
				
				
				AimsApp aimsApp = null;
				AimsBrewApp aimsBrewApp = null;
				AimsAppCategory aimsAppCategory = null;
				HashMap appBrew = null;
				
				AimsAllianc aimsAlliance = new AimsAllianc();
		
				try
				{
			   	appBrew = BrewApplicationManager.getApp(new Long(request.getParameter("appsId")), currentUserAllianceId);			   	
				}
				catch(AimsException ae)
				{
					throw new AimsSecurityException();
				}
				
				aimsApp = (AimsApp) appBrew.get("AimsApp");
				aimsBrewApp = (AimsBrewApp) appBrew.get("AimsBrewApp");
				aimsAppCategory = (AimsAppCategory) appBrew.get("AimsAppCategory");
								
				Collection allianceInfo = AllianceBusInfoManager.getAllianceBusInfo(aimsApp.getAimsAllianceId(),null);
				Object [] userValues = null;
				
				for (Iterator iter = allianceInfo.iterator(); iter.hasNext();) 
				{
					userValues  = (Object []) iter.next();
					aimsAlliance.setCompanyPresentationFileName( (String) userValues[15]);
					aimsAlliance.setCompanyLogoFileName( (String) userValues[17]);					
				}					
				
				
				//START of Replacement of '\n' with '<br/>' for displaying in HTML
				aimsApp.setShortDesc(StringFuncs.string_substitute(aimsApp.getShortDesc(), "\n", "<br/>"));
				aimsApp.setLongDesc(StringFuncs.string_substitute(aimsApp.getLongDesc(), "\n", "<br/>"));
				aimsBrewApp.setAnticipatedDaps(StringFuncs.string_substitute(aimsBrewApp.getAnticipatedDaps(), "\n", "<br/>"));
				
				for(Iterator itr = aimsApp.getPhases().iterator();	itr.hasNext()	;)
				{
					AimsAppPhase aap = (AimsAppPhase)	itr.next();
					aap.setComments(StringFuncs.string_substitute(aap.getComments(), "\n", "<br/>"));
				}
				//END of Replacement of '\n' with '<br/>' for displaying in HTML
				
						
				
				request.setAttribute ("aimsApp", aimsApp);
				request.setAttribute ("aimsBrewApp", aimsBrewApp);
				request.setAttribute ("aimsAppCategory", aimsAppCategory);
				request.setAttribute ("aimsAlliance", aimsAlliance);
		

				if (format_type.equals("excel"))
					forward = "excel";
				else
					forward = "view";       


				return mapping.findForward(forward);
    }
}
