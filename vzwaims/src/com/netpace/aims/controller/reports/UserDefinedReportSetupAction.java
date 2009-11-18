package com.netpace.aims.controller.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.reports.displayobjects.UDReportCriteriaDO;
import com.netpace.aims.controller.reports.displayobjects.UserDefinedReportDO;
import com.netpace.aims.dataaccess.valueobjects.AimsReportObjectColumnVO;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MiscUtils;
import com.netpace.aims.util.Utility;



/**
 * This class takes care of getting the data for the User-Defined Report.
 *
 * @struts.action path="/userDefinedReportSetup"
 *                scope="request"
 *                name="UserDefinedReportForm"
 *                input="/reports/userDefinedReportSetup.jsp"
 * @struts.action-forward name="view" path="/reports/userDefinedReportSetup.jsp"
 * @struts.action-forward name="excel" path="/reports/userDefinedReportExcel.jsp"
 * @struts.action-exception key="error.report.recordNotFound" type="com.netpace.aims.bo.core.RecordNotFoundException"
 * @author Fawad Sikandar
 */

public class UserDefinedReportSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(UserDefinedReportSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

       String taskname =  request.getParameter("task");
       String item  = request.getParameter("item");
       String objectValue = request.getParameter("objectValue");
       String retainValues = request.getParameter("retainValues");
       log.debug("Object Value = "  + objectValue);
       String[] removableObjects = request.getParameterValues("objectValues");
       String[] removableColumns = request.getParameterValues("columnValues");
      // String[] columnDisplayNames = request.getParameterValues("columnDisplayName");
       String[] criteriaValues = request.getParameterValues("criteriaValue");
       String[] columnOperators = request.getParameterValues("columnOperator");
       String[] columnConditions = request.getParameterValues("columnCondition");

       String columnValue = request.getParameter("objectValue");
       String criteriaId = request.getParameter("criteriaId");

       String cmd = request.getParameter("cmd");
       String dateFormat = this.getResources(request).getMessage("date.format");
       String forward = "view";

       HttpSession session = request.getSession();
       Collection collection = new ArrayList();
       Collection objectList = null;
       Collection columnList = null;
       Collection criteriaList = null;
       Collection operatorList = new ArrayList();
       Collection displayObjectList = null;
       UserDefinedReportDO displayObject = null;
       UDReportCriteriaDO  criteriaDO = null;
       UDReportCriteriaDO  operatorDO = null;

       UserDefinedReportForm userDefinedReport = (UserDefinedReportForm)
            session.getAttribute("UserDefinedReport") ;

       if ( userDefinedReport == null || "N".equalsIgnoreCase(retainValues) ) {

         userDefinedReport = (UserDefinedReportForm) form;
         userDefinedReport.setObjectsAdded(new  ArrayList());
         userDefinedReport.setColumnsAdded(new ArrayList());
         userDefinedReport.setCriteriaAdded(new ArrayList());
       }

       if ("view".equalsIgnoreCase(taskname) ) {

          userDefinedReport.setReportObjectList(
                        VOLookupFactory.getInstance().getAimsReportObjectList());

      }

    if ("reportObject".equalsIgnoreCase(item))
    {
      objectList = (Collection) userDefinedReport.getObjectsAdded();

      if ("add".equalsIgnoreCase(cmd))
      {
        if (!MiscUtils.contains(objectValue, objectList, true))
        {
          displayObject = new UserDefinedReportDO();
          displayObject.setReportObjectID(objectValue);
          displayObject.setTableName(VOLookupFactory.getInstance().
                                     getReportObject(Long.valueOf(objectValue)).
                                     getTableName());
          displayObject.setTableDisplayName(VOLookupFactory.getInstance().
                                            getReportObject(Long.valueOf(
              objectValue)).getTableDisplayName());
          objectList.add(displayObject);

          userDefinedReport.setObjectsAdded(objectList);
        }
      }

      if ("remove".equalsIgnoreCase(cmd))
      {

        // log.debug("Objects to be removed : " + removeableObjects.length);
        Collection objects = new ArrayList();

        for (Iterator it = objectList.iterator(); it.hasNext(); )
        {
          displayObject = (UserDefinedReportDO) it.next();
          if (!MiscUtils.linearSearch(displayObject.getReportObjectID(), removableObjects))

            objects.add(displayObject);
        }
        userDefinedReport.setObjectsAdded(objects);

      }
      AimsReportObjectColumnVO[] columnVOList
                        = VOLookupFactory.getInstance().
                                   getAimsReportObjectColumnList();


     for (Iterator it = userDefinedReport.getObjectsAdded().iterator(); it.hasNext(); )
     {
        displayObject = (UserDefinedReportDO) it.next();
        for (int i = 0 ; i < columnVOList.length; i ++  )
       {
         if (columnVOList[i].getReportObjectId().equals(
                      Utility.convertToLong(displayObject.getReportObjectID())) )
            collection.add(columnVOList[i]);
        }
         userDefinedReport.setReportColumnList
              ( (AimsReportObjectColumnVO[])
                     collection.toArray(new AimsReportObjectColumnVO[0]));
       }

       if (userDefinedReport.getObjectsAdded().isEmpty() )
       {
         userDefinedReport.setReportColumnList(null);
         userDefinedReport.setColumnsAdded(new ArrayList());
         userDefinedReport.setCriteriaAdded(new ArrayList());
       }
    }
       if ("reportColumn".equalsIgnoreCase(item))
       {
         columnList = (Collection) userDefinedReport.getColumnsAdded();

         if ("add".equalsIgnoreCase(cmd) )
         {
              if (! MiscUtils.contains(columnValue, columnList, false) )
               {
                 displayObject = new UserDefinedReportDO();
                 displayObject.setReportObjectColumnId(objectValue);
                 displayObject.setColumnName(VOLookupFactory.getInstance().getReportObjectColumn(Long.valueOf(objectValue)).getColumnName());
                 displayObject.setColumnDisplayName(VOLookupFactory.getInstance().getReportObjectColumn(Long.valueOf(objectValue)).getColumnDisplayName());
                 columnList.add(displayObject);
                 userDefinedReport.setColumnsAdded(columnList);
               }
           }

            if ("remove".equalsIgnoreCase(cmd) )
            {
              Collection objects = new ArrayList();
              for(Iterator it=columnList.iterator(); it.hasNext(); )
              {
                displayObject =(UserDefinedReportDO)it.next();
                if(!MiscUtils.linearSearch(displayObject.getReportObjectColumnId(), removableColumns))
                      objects.add(displayObject);
              }
              userDefinedReport.setColumnsAdded(objects);
           }
       }

       if ("reportCriteria".equalsIgnoreCase(item))
       {
         criteriaList = (Collection) userDefinedReport.getCriteriaAdded();

         if ("add".equalsIgnoreCase(cmd) )
         {
           criteriaDO = new UDReportCriteriaDO();
           criteriaDO.setColumnName(VOLookupFactory.getInstance().getReportObjectColumn(Long.valueOf(objectValue)).getColumnName());
           criteriaDO.setColumnDisplayName(VOLookupFactory.getInstance().getReportObjectColumn(Long.valueOf(objectValue)).getColumnDisplayName());
           session.setAttribute("criteriaKey", session.getAttribute("criteriaKey") == null ? "1" :
                            String.valueOf(Integer.parseInt( (String) session.getAttribute("criteriaKey")) + 1));
           criteriaDO.setCriteriaId( (String) session.getAttribute("criteriaKey"));
           criteriaDO.setCriteriaValue("");
           criteriaDO.setColumnOperator("");
           criteriaDO.setColumnCondition("");
           criteriaList.add(criteriaDO);
           userDefinedReport.setCriteriaAdded(criteriaList);
           }

          if ("remove".equalsIgnoreCase(cmd) )
          {

              Collection objects = new ArrayList();
              for(Iterator it=criteriaList.iterator(); it.hasNext(); )
              {
                criteriaDO =(UDReportCriteriaDO)it.next();
                if(!criteriaDO.getCriteriaId().equals(criteriaId))
                      objects.add(criteriaDO);
              }
              userDefinedReport.setCriteriaAdded(objects);
           }

          for  (int i=0 ; i< AimsConstants.comparisonOperators.length ; i++ )
          {
              operatorDO = new  UDReportCriteriaDO();
              operatorDO.setColumnOperatorName(AimsConstants.comparisonOperators[i]);
              operatorDO.setColumnOperator(AimsConstants.comparisonOperators[i]);
              operatorList.add(operatorDO);
           }
            userDefinedReport.setColumnOperatorList(operatorList);
            session.setAttribute("CriteriaSize",
                                 "" + userDefinedReport.getCriteriaAdded().size());
       }


       columnOperators = (String[]) session.getAttribute("operators");
       if (columnOperators != null)
       {
         String[] selectecOp = new String[userDefinedReport.getCriteriaAdded().
                               size()];
         for (int i = 0; i < columnOperators.length && i < userDefinedReport.getCriteriaAdded().size() ; i++)
         {
           selectecOp[i] = columnOperators[i];
         }
         session.setAttribute("operators", selectecOp);
       }

       session.setAttribute("UserDefinedReport", userDefinedReport);
       return mapping.findForward(forward);
    }

}