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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.reports.AimsUserDefReportManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.reports.displayobjects.UDReportCriteriaDO;
import com.netpace.aims.controller.reports.displayobjects.UserDefinedReportDO;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.MiscUtils;


/**
 * This class takes care of getting the data for the User-Defined Report.
 *
 * @struts.action path="/userDefinedReport"
 *                scope="request"
 *                name="UserDefinedReportForm"
 *                input="/reports/userDefinedReport.jsp"
 * @struts.action-forward name="view" path="/reports/userDefinedReport.jsp"
 * @struts.action-forward name="excel" path="/reports/userDefinedReportExcel.jsp"
 * @struts.action-exception key="error.report.recordNotFound" type="com.netpace.aims.bo.core.RecordNotFoundException"
 * @author Fawad Sikandar
 */

public class UserDefinedReportAction extends BaseAction
{

    static Logger log = Logger.getLogger(UserDefinedReportAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {

       String taskname =  request.getParameter("task");
       HttpSession session = request.getSession();
       String[] criteriaValues = request.getParameterValues("criteriaValue");
       String[] columnOperators = request.getParameterValues("columnOperator");
       String[] columnConditions = null;

      if ( columnOperators != null )
      {
        columnConditions = new String[columnOperators.length - 1];
      //  log.debug("columnCondition" + columnConditions.length);

        for (int i = 0; i < columnOperators.length - 1; i++)
        {
          columnConditions[i] = request.getParameter("columnCondition" + (i + 1));
        //  log.debug("Condition is for " + (i + 1) + columnConditions[i]);

        }
      }

       Collection objectList =  new ArrayList();
       Collection columnList = new  ArrayList();
       Collection criteriaList = new ArrayList();
       String forward = "view";
       UserDefinedReportDO  displayObject = null;
       UDReportCriteriaDO criteriaDO = null;

       StringBuffer tableQuery = new  StringBuffer();
       StringBuffer columnQuery = new StringBuffer();
       StringBuffer criteriaQuery = new StringBuffer();


       UserDefinedReportForm userDefinedReport = (UserDefinedReportForm)
                        session.getAttribute("UserDefinedReport") ;

       userDefinedReport.setUserDefObjectList(new ArrayList());

        objectList = userDefinedReport.getObjectsAdded();

        for(Iterator it=objectList.iterator(); it.hasNext(); )
        {
          displayObject = (UserDefinedReportDO) it.next();
          //log.debug("Tables : " + displayObject.getTableName());
          tableQuery.append(displayObject.getTableName());

          if (it.hasNext()) {
               tableQuery.append(",");
           }

        }

        columnList = userDefinedReport.getColumnsAdded();

        for(Iterator it=columnList.iterator(); it.hasNext(); )
        {
            displayObject = (UserDefinedReportDO) it.next();
           // log.debug("Columns : " + displayObject.getColumnName());
            columnQuery.append(displayObject.getColumnName());

            if (it.hasNext()) {
                 columnQuery.append(",");
            }
        }

        criteriaList = userDefinedReport.getCriteriaAdded();

        int i = 0;
        for (Iterator it=criteriaList.iterator(); it.hasNext(); )
             {
                 criteriaDO = (UDReportCriteriaDO) it.next();
                 criteriaDO.setCriteriaValue(criteriaValues[i]);
                 criteriaDO.setColumnOperator(columnOperators[i]);
                 if( it.hasNext())
                  criteriaDO.setColumnCondition(columnConditions[i]);
                 i++;
             }

        int j = 0;
        for(Iterator it=criteriaList.iterator(); it.hasNext(); )
        {
          criteriaDO = (UDReportCriteriaDO) it.next();
        //  log.debug("Criteria : " + criteriaDO.getColumnName());
          criteriaQuery.append(" upper("+ criteriaDO.getColumnName() + ") ");

              if ("!=".equalsIgnoreCase(columnOperators[j] ))
                   criteriaQuery.append(" not like ");
              else
                   criteriaQuery.append(columnOperators[j] +" ");

              if ( MiscUtils.isValidDate(criteriaValues[j], AimsConstants.DATE_FORMAT) )
                   criteriaQuery.append("to_date('" + criteriaValues[j]  + "','" + AimsConstants.DATE_FORMAT + "')" );
              else
                   criteriaQuery.append("'%" + criteriaValues[j].toUpperCase() + "%' " );

             if (it.hasNext()) {
               criteriaQuery.append(columnConditions[j] + " ");
             }
          j++;
        }

        session.setAttribute("operators",columnOperators);
        Collection collection = AimsUserDefReportManager.getUserDefinedReport(tableQuery.toString(), columnQuery.toString(), criteriaQuery.toString());
        userDefinedReport.setCriteriaAdded(criteriaList);
        userDefinedReport.setUserDefObjectList(collection);

        session.setAttribute("UserDefinedReport",userDefinedReport);

        if ( collection.size() > Integer.parseInt(AimsConstants.recordLimit) )
        {
          ActionMessages messages = new ActionMessages();
          messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.report.exceedRecordLimit"));
          log.debug("Collection Size : " + collection.size() + " Limit :  " + AimsConstants.recordLimit );
          saveMessages(request, messages);
          return mapping.getInputForward();
        }


       return mapping.findForward(forward);
    }

 }


