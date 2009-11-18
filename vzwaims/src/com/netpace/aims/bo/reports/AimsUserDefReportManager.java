package com.netpace.aims.bo.reports;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.reports.*;


import net.sf.hibernate.*;

import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;
import java.sql.*;

import oracle.jdbc.driver.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.*;
import java.util.Collection;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.controller.reports.UserDefinedReportForm;
import java.util.Date;
import com.netpace.aims.util.Utility;
import com.netpace.aims.controller.alliance.AllianceContractForm;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import com.netpace.aims.controller.reports.displayobjects.UDReportCriteriaDO;
import com.netpace.aims.util.DBUtils;
import com.netpace.aims.util.StringFuncs;


/**
 * This class is responsible for getting the BO for User-Defined Reports.
 * It has static methods for getting the user-defined objects.
 * @author Fawad Sikandar
 */

public class AimsUserDefReportManager
{

  static Logger log = Logger.getLogger(AimsUserDefReportManager.class.getName());





  public static Collection getUserDefinedReport(String tabeleQuery, String columnQuery, String criteriaQuery) throws RecordNotFoundException, HibernateException
  {
    Collection collection = new ArrayList();
    Collection columnValues = null;
    UDReportCriteriaDO  UDReportDO = new UDReportCriteriaDO();
    UDReportCriteriaDO  displayObject = new UDReportCriteriaDO();
    Session session = null;
    try
    {
      StringBuffer queryString = new StringBuffer();
      session = DBHelper.getInstance().getSession();
      Query query = null;
      Connection conn = session.connection();
      Statement stmt = conn.createStatement();

      queryString.append("select distinct " + columnQuery)
                  .append(" from ")
                  .append(tabeleQuery);

              if (! StringFuncs.isEmpty(criteriaQuery) )
              {
                queryString.append(" where ")
                           .append(criteriaQuery);
              }

      log.debug("****** Query Created **** :" + queryString);
      ResultSet rs =  stmt.executeQuery(queryString.toString());


      ResultSetMetaData rsmd = rs.getMetaData();
      int length = rsmd.getColumnCount();
 //     log.debug("Column Count : " + length);

//      for (int i = 1; i <= length; i++)
//      {
//         log.debug (" " + rsmd.getTableName(i) + "."
//                           + rsmd.getColumnName(i) + " ["
//                           + rsmd.getColumnLabel(i) + "]"
//                           + (rsmd.getColumnType(i) == Types.TIMESTAMP) + " "
//                           + (rsmd.getColumnType(i) == Types.DATE) + " "
//                           + (rsmd.getColumnType(i) == Types.VARCHAR) + " ["
//                           + rsmd.getColumnTypeName(i).equalsIgnoreCase("VarChar2")+ "]"
//                           + rsmd.getColumnClassName(i));
//      }

       while(rs.next()) {
         UDReportDO = new UDReportCriteriaDO();
          columnValues = new ArrayList();
          for (int i = 1; i <= length; i++)
         {
           displayObject = new  UDReportCriteriaDO();

           if (rsmd.getColumnType(i) == Types.VARCHAR)
           {

 //            log.debug(" Value :  " + rs.getString(rsmd.getColumnName(i)));
             displayObject.setRecordValue(DBUtils.extractString(rs,
                 rsmd.getColumnName(i)));

           }else if (rsmd.getColumnType(i) == Types.TIMESTAMP)
           {
//             log.debug(" Value :  " + rs.getDate(rsmd.getColumnName(i)));
             displayObject.setRecordValue(Utility.convertToString(DBUtils.extractDate(rs,
                  rsmd.getColumnName(i)),AimsConstants.DATE_FORMAT));

           }
           columnValues.add(displayObject);
         }
         UDReportDO.setColumnValues(columnValues);
         collection.add(UDReportDO);
       }

      log.debug("No of Results: " + collection.size() );

      if (collection.isEmpty() )
         throw new RecordNotFoundException("error.report.recordNotFound");
     }

     catch(SQLException e)
     {
           e.printStackTrace();
           throw new RecordNotFoundException("error.report.recordNotFound");
      }
    catch(HibernateException e)
    {
          e.printStackTrace();
          throw e;
     }
     finally
     {
          session.close();
     }

     return collection;
  }


/*
  public static Collection getUserDefinedReport(String allianceFilter, String allianceInput, String logicalFilter, String applicationFilter, String applicationInput) throws RecordNotFoundException, HibernateException, SQLException
  {
    Collection collection = new ArrayList();
    AimsAllianc alliance = null;
    UserDefinedReportForm reportObject = null;
    Collection allianceContracts = new ArrayList();
    Session session = null;
    try
    {
      StringBuffer queryString = new StringBuffer();
      session = DBHelper.getInstance().getSession();
      Query query = null;

      queryString.append("select alliance.companyName, alliance.allianceRegDate,")
                 .append(" alliance.status, user.username, contact.firstName,")
                 .append(" contact.lastName, alliance.allianceId")
                 .append(" from")
                 .append(" com.netpace.aims.model.core.AimsAllianc alliance,")
                 .append(" com.netpace.aims.model.core.AimsUser user,")
                 .append(" com.netpace.aims.model.core.AimsContact contact")
                 .append(" where");

     if (allianceFilter.equalsIgnoreCase("allianceName") && allianceInput != null  )
     {
       queryString.append(" upper(alliance.companyName) like '%" + allianceInput.toUpperCase() + "%'")
                  .append(" and alliance.vzwAccountManager = user.userId(+) ")
                  .append(" and alliance.aimsContactBySalesContactId = contact.contactId(+) ");

       query = session.createQuery(queryString.toString());
     }

     if (allianceFilter.equalsIgnoreCase("allianceRegDate") && allianceInput != null  )
     {
      queryString.append(" alliance.allianceRegDate = :allianceRegDate")
                 .append(" and alliance.vzwAccountManager = user.userId(+) ")
                 .append(" and alliance.aimsContactBySalesContactId = contact.contactId(+) ");
       query = session.createQuery(queryString.toString());
       query.setDate(allianceFilter, Utility.convertToDate(allianceInput,"MM/dd/yyyy"));

     }

     if (allianceFilter.equalsIgnoreCase("allianceStatus") && allianceInput != null  )
       {
        queryString.append(" upper(alliance.status) like '%" + allianceInput.toUpperCase() + "%'")
                   .append(" and alliance.vzwAccountManager = user.userId(+) ")
                   .append(" and alliance.aimsContactBySalesContactId = contact.contactId(+) ");
        query = session.createQuery(queryString.toString());
       }

       if (allianceFilter.equalsIgnoreCase("accountManager") && allianceInput != null)
       {
         queryString.append(" upper(user.username) like '%" + allianceInput.toUpperCase() + "%'")
                    .append(" and user.userId = alliance.vzwAccountManager(+) ")
                    .append(" and contact.contactId(+) = alliance.aimsContactBySalesContactId ");
         query = session.createQuery(queryString.toString());
       }



      Object[] searchValues = null;

      for	(Iterator	it = query.iterate();	it.hasNext();)
      {
               searchValues = (Object[]) it.next();
               reportObject = new UserDefinedReportForm();
               reportObject.setAllianceName((String)searchValues[0]);
               reportObject.setAllianceRegDate(Utility.convertToString((Date)searchValues[1],"MM/dd/yyyy"));
               reportObject.setAllianceStatus((String)searchValues[2]);
               reportObject.setAccountManager((String)searchValues[3]);
               reportObject.setSalesContact((String)searchValues[4]);

//             reportObject.setSalesContact((String)searchValues[5]  + " " + (String)searchValues[4]);
               log.debug("Alliance :: "	+ reportObject.getAllianceName() );
               log.debug("Contracts :: "	+ searchValues[6] );
//               reportObject.setApplicationTitle((String)searchValues[7]);
//               reportObject.setAppPlatform((String)searchValues[8]);

               if ( searchValues[6] != null) {
                 reportObject.setContractList(getAllianceContracts((Long)searchValues[6]));
                 reportObject.setAppList(getApplicationList((Long)searchValues[6]));

//               for ( Iterator	iter =    reportObject.getContractList().iterator();	iter.hasNext(); ) {
//
//                 AllianceContractForm allianceContract
//                                        = (AllianceContractForm) iter.next();
//                 log.debug("Contract Title " + allianceContract.getContractTitle() );
//
//
//                }
               }
                else {
                  reportObject.setContractList(new ArrayList());
                  reportObject.setAppList(new ArrayList());
                }

              collection.add(reportObject);

      }

      if (searchValues == null )
         throw new RecordNotFoundException("error.system.recordNotFound");

     }
     catch(HibernateException e)
     {
      e.printStackTrace();
      throw e;
     }
     finally
     {
      session.close();
     }

     return collection;
  }
*/

  /**
    *  This static method gets the contract for the specified alliance if any.
    *
    */
   private static Collection getAllianceContracts(Long allianceId) throws HibernateException
   {
         Collection collection = null;
         Collection AllianceContracts = new ArrayList();
         Session session = null;
         Object [] userValues = null;
         StringBuffer queryStringBuffer = new StringBuffer();
         AllianceContractForm allianceContractForm = null;


     try
     {

         queryStringBuffer.append("select  ")
                                          .append("		alliance.allianceId, ")
                                          .append("		contract.contractTitle ")
                                          .append("from ")
                                          .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
                                          .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract, ")
                                          .append("		com.netpace.aims.model.core.AimsContract contract, ")
                                          .append("where ")
                                          .append("		alliance.allianceId = :allianceId ")
                                          .append("		and alliance.allianceId = allianceContract.allianceId ")
                                          .append("		and allianceContract.contractId = contract.contractId ");


                 session = DBHelper.getInstance().getSession();

                collection = session.find(queryStringBuffer.toString(), allianceId,  new LongType());


                 for (Iterator iter = collection.iterator(); iter.hasNext();)
                 {
                          allianceContractForm = new AllianceContractForm();
                          userValues  = (Object []) iter.next();
                          allianceContractForm.setContractId((Long) userValues[0]);
                          allianceContractForm.setContractTitle((String) userValues[1]);
                          AllianceContracts.add(allianceContractForm);
                  }

                 log.debug("No of records returned: " + collection.size() );

         }
         catch(HibernateException e)
         {
                 e.printStackTrace();
                 throw e;
         }
         finally
         {
                 session.close();
         }

     return AllianceContracts;
   }

 private static Collection getApplicationList(Long allianceId)  throws HibernateException
 {


    Collection collection = null;
    Collection AllianceContracts = new ArrayList();
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    AllianceContractForm allianceContractForm = null;


try
{

      queryStringBuffer.append("from ")
                 .append("com.netpace.aims.model.application.AimsApp app, ")
                 .append("where ")
                 .append("app.aimsAllianceId = :allianceId ");

       session = DBHelper.getInstance().getSession();
       collection = session.find(queryStringBuffer.toString(), allianceId,  new LongType());

//        for (Iterator iter = collection.iterator(); iter.hasNext();)
//        {
//
//        }
        log.debug("No of applications : " + collection.size() );

}
catch(HibernateException e)
{
        e.printStackTrace();
        throw e;
}
finally
{
        session.close();
}

return collection;


  }


  public static	Collection getReportObjectList()	throws HibernateException
  {
          Collection collection	=	null;
          Session	session	=	null;
          try
          {
               session = DBHelper.getInstance().getSession();
               collection = session.find("from com.netpace.aims.model.reports.AimsReportObject reportObject order by reportObject.tableDisplayName");
          }
          catch(HibernateException e)
          {
                  e.printStackTrace();
                  throw	e;
          }
          finally
          {
            session.close();
          }
          return collection;
  }

  public static	Collection getReportObjectColumnList()	throws HibernateException
  {
          Collection collection	=	null;
          Session	session	=	null;
          try
          {
               session = DBHelper.getInstance().getSession();
               collection = session.find("from com.netpace.aims.model.reports.AimsReportObjectColumn reportObjectCol order by reportObjectCol.columnDisplayName");
          }
          catch(HibernateException e)
          {
                  e.printStackTrace();
                  throw	e;
          }
          finally
          {
            session.close();
          }
          return collection;
  }


}
