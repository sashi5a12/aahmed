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
import com.netpace.aims.controller.reports.displayobjects.GINHistoryReportDO;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of getting the data for the Application Evaluation Report.
 *
 * @struts.action path="/historyReport"
 *                scope="request"
 *                name="GINReportForm"
 *                input="/reports/historyReport.jsp"
 * @struts.action-forward name="view" path="/reports/historyReport.jsp"
 * @struts.action-forward name="excel" path="/reports/historyReportExcel.jsp"
 * @author Fawad Sikandar
 */

public class GINHistoryReportAction extends BaseAction
{

    static Logger log = Logger.getLogger(GINHistoryReportAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

      String forward = "view";
      Object[] values = null;
      String taskname =  request.getParameter("task");
      Collection brewAppList = new  ArrayList();
      Collection ginNumberList = new  ArrayList();
      GINHistoryReportDO displayObject = null;
      GINReportForm reportForm = (GINReportForm) form;

      if ("view".equalsIgnoreCase(taskname) )
      {

        Collection collection = AimsGINReportManager.getGINNumberList();
        Long ginNumber = null;

        for (Iterator iter = collection.iterator(); iter.hasNext(); )
        {

          ginNumber = (Long) iter.next();
          displayObject = new GINHistoryReportDO();
          displayObject.setGinNumber(Utility.convertToString(ginNumber));
          ginNumberList.add(displayObject);
        }
        reportForm.setGinNumberList(ginNumberList);
        request.getSession().setAttribute("reportForm",reportForm);

     } else
     {
       Collection collection =
          AimsGINReportManager.findBrewAppsByGINNumber(Utility.convertToLong(
              reportForm.getGinNumber()), VOLookupFactory.getInstance().getAimsPlatform("BREW").getPlatformId());

       reportForm.setBrewApplicationList(brewAppList);

       for (Iterator iter = collection.iterator(); iter.hasNext(); )
       {
         values = (Object[]) iter.next();
         displayObject = new GINHistoryReportDO();
         displayObject.setApplicationName(Utility.getObjectString(values[0]));
         displayObject.setVersion(Utility.getObjectString(values[1]));
         displayObject.setAllianceName(Utility.getObjectString(values[2]));
         displayObject.setHandset(Utility.getObjectString(values[3]));
         displayObject.setPartNumber(Utility.getObjectString(values[4]));
         displayObject.setGinNumber(Utility.getObjectString(values[5]));
         displayObject.setDaysInQueue("5");
         brewAppList.add(displayObject);
       }
       reportForm.setBrewApplicationList(brewAppList);
       request.setAttribute("ginNumber",reportForm.getGinNumber());
     }

     if( "excel".equalsIgnoreCase(request.getParameter("format")) )
     {
       return mapping.findForward("excel");
     } else
     {
      return mapping.findForward(forward);
     }

   }

}
