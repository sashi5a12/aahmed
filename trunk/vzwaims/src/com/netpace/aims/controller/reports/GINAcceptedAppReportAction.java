package com.netpace.aims.controller.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.reports.AimsGINReportManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.reports.displayobjects.GINAcceptedAppReportDO;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of getting the data for the Application Evaluation Report.
 *
 * @struts.action path="/acceptedAppReport"
 *                scope="request"
 *                name="GINReportForm"
 *                input="/reports/acceptedAppReport.jsp"
 * @struts.action-forward name="view" path="/reports/acceptedAppReport.jsp"
 * @struts.action-forward name="excel" path="/reports/acceptedAppReportExcel.jsp"
 * @author Fawad Sikandar
 */

public class GINAcceptedAppReportAction extends BaseAction
{

    static Logger log = Logger.getLogger(GINAcceptedAppReportAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

      String forward = "view";
      Object[] values = null;
      Collection brewAppList = new  ArrayList();
      GINAcceptedAppReportDO displayObject = null;
      GINReportForm reportForm = (GINReportForm) form;
      String phaseName = request.getParameter("phaseName");

      Long phaseId = getLifeCyclePhaseId(phaseName);

     Collection collection = AimsGINReportManager.
                         findAimsBrewAppsByPhaseId(phaseId , VOLookupFactory.getInstance().getAimsPlatform("BREW").getPlatformId());
     reportForm.setBrewApplicationList(brewAppList);

     for ( Iterator iter = collection.iterator() ; iter.hasNext() ; )
     {
          values  =   (Object[])iter.next();
          displayObject = new GINAcceptedAppReportDO();
          displayObject.setApplicationName(Utility.getObjectString(values[0]));
          displayObject.setVersion(Utility.getObjectString(values[1]));
          displayObject.setAllianceName(Utility.getObjectString(values[2]));
          displayObject.setHandset(AimsConstants.HANDSET);
          displayObject.setPartNumber(Utility.getObjectString(values[3]));
          displayObject.setLaunchDate(Utility.getObjectDate(values[5]));
          displayObject.setDaysInQueue("5");
          brewAppList.add(displayObject);
     }
     reportForm.setBrewApplicationList(brewAppList);


     if( "excel".equalsIgnoreCase(request.getParameter("format")) ) {
       return mapping.findForward("excel");
    } else {
      return mapping.findForward(forward);
    }

    }

    private Long getLifeCyclePhaseId(String phaseName) {

       Long phaseId = new Long("0");

      if ( AimsConstants.CERTIFICATION.equalsIgnoreCase(phaseName) )
         phaseId = AimsConstants.CERTIFICATION_ID;
       if (AimsConstants.TESTING.equalsIgnoreCase(phaseName) )
         phaseId = AimsConstants.TESTING_ID;
       if (AimsConstants.ACCEPTANCE.equalsIgnoreCase(phaseName) )
         phaseId = AimsConstants.ASSIGNED_ID;

      return phaseId;
    }

}
