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
import net.sf.hibernate.type.LongType;


/**
 * This class is responsible for getting the BO for GIN Reports.
 * It has static methods for getting the devices.
 * @author Fawad Sikandar
 */
public class AimsGINReportManager
{

  static Logger log = Logger.getLogger(AimsGINReportManager.class.getName());


  public static Collection findAimsBrewAppsByPhaseId(Long phaseId, Long platformId) throws HibernateException, SQLException
  {
    Collection aimsBrewApps = null;
    Session session = null;
    Object[] obj = {platformId,phaseId};
    LongType[] longType = { new LongType() , new LongType()};
    try
    {
      session = DBHelper.getInstance().getSession();
      StringBuffer query= new StringBuffer();

      query.append("select  ")
          .append("		app.title, ")
          .append("		app.version, ")
          .append("		alliance.companyName, ")
          .append("		brewAppDev.partNumber, ")
          .append("		brewAppDev.partNumber,  ")
          .append("		app.targetedProductionDate  ")
          .append("from ")
          .append("		com.netpace.aims.model.application.AimsApp as app, ")
          .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
          .append("		com.netpace.aims.model.application.AimsBrewAppDevice brewAppDev ")
          .append("where ")
          .append("		app.aimsPlatformId = :platformId")
          .append("		and app.aimsLifecyclePhaseId = :phaseId")
          .append("		and app.aimsAllianceId = alliance.allianceId (+) ")
          .append("		and app.appsId = brewAppDev.brewAppsId (+) ")
          .append("order by ")
          .append("		app.title ");

      aimsBrewApps = session.find(query.toString(),obj, longType);
      log.debug("No of records returned: " + aimsBrewApps.size() );

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

     return aimsBrewApps;
  }


  public static Collection getGINCertifiedAppList() throws HibernateException, SQLException
   {
     Collection aimsBrewApps = null;
     Session session = null;

     try
     {
       session = DBHelper.getInstance().getSession();
       StringBuffer query= new StringBuffer();

       query.append("select  ")
           .append("		brewApps.applicationName, ")
           .append("		brewApps.version, ")
           .append("		brewApps.developerName, ")
           .append("		brewApps.handset, ")
           .append("		brewApps.partnum,  ")
           .append("		brewApps.ginNumber  ")
           .append("from ")
           .append("		com.netpace.aims.model.application.AimsBrewNstlData as brewApps ")
           .append("order by ")
           .append("		brewApps.applicationName ");

       aimsBrewApps = session.find(query.toString());
       log.debug("No of records returned: " + aimsBrewApps.size() );

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

      return aimsBrewApps;
   }



   public static Collection findLiveAppsByPlatformId(Long platformId) throws HibernateException, SQLException
   {
     Collection aimsBrewApps = null;
     Session session = null;
     try
     {
       session = DBHelper.getInstance().getSession();
       StringBuffer query= new StringBuffer();

       query.append("select  ")
           .append("		app.title, ")
           .append("		app.version, ")
           .append("		alliance.companyName, ")
           .append("		brewAppDev.partNumber, ")
           .append("		brewAppDev.partNumber,  ")
           .append("		app.targetedProductionDate  ")
           .append("from ")
           .append("		com.netpace.aims.model.application.AimsApp as app, ")
           .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
           .append("		com.netpace.aims.model.application.AimsBrewAppDevice brewAppDev ")
           .append("where ")
           .append("		app.aimsPlatformId = :platformId")
           .append("		and app.ginNumber = (select max(app.ginNumber) from com.netpace.aims.model.application.AimsApp as app)")
           .append("		and app.aimsAllianceId = alliance.allianceId (+) ")
           .append("		and app.appsId = brewAppDev.brewAppsId (+) ")
           .append("order by ")
           .append("		app.title ");

       aimsBrewApps = session.find(query.toString(),platformId, new LongType());
       log.debug("No of records returned: " + aimsBrewApps.size() );

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

      return aimsBrewApps;
   }



   public static Collection getGINNumberList() throws HibernateException, SQLException
     {
       Collection aimsBrewApps = null;
       Session session = null;

       try
       {
         session = DBHelper.getInstance().getSession();
         StringBuffer query= new StringBuffer();

         query.append("select distinct ")
             .append("		app.ginNumber ")
             .append("from ")
             .append("		com.netpace.aims.model.application.AimsApp as app ")
             .append("where app.ginNumber is not null ")
             .append("order by ")
             .append("		app.ginNumber ");

         aimsBrewApps = session.find(query.toString());
         log.debug("No of records returned: " + aimsBrewApps.size() );

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

        return aimsBrewApps;
     }


     public static Collection findBrewAppsByGINNumber(Long GINNumber, Long platformId) throws HibernateException, SQLException
      {
        Collection aimsBrewApps = null;
        Session session = null;
        Object[] obj = {platformId,GINNumber};
        LongType[] longType = { new LongType() , new LongType()};
        try
        {
          session = DBHelper.getInstance().getSession();
          StringBuffer query= new StringBuffer();

          query.append("select  ")
              .append("		app.title, ")
              .append("		app.version, ")
              .append("		alliance.companyName, ")
              .append("		brewAppDev.partNumber, ")
              .append("		brewAppDev.partNumber,  ")
              .append("		app.ginNumber  ")
              .append("from ")
              .append("		com.netpace.aims.model.application.AimsApp as app, ")
              .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
              .append("		com.netpace.aims.model.application.AimsBrewAppDevice brewAppDev ")
              .append("where ")
              .append("		app.aimsPlatformId = :platformId")
              .append("		and app.ginNumber = :GINNumber")
              .append("		and app.aimsAllianceId = alliance.allianceId (+) ")
              .append("		and app.appsId = brewAppDev.brewAppsId (+) ")
              .append("order by ")
              .append("		app.title ");

          aimsBrewApps = session.find(query.toString(),obj, longType);
          log.debug("No of records returned: " + aimsBrewApps.size() );

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

         return aimsBrewApps;
      }

  public static Collection findAimsAppPhasesByAppId(Long appsId) throws HibernateException, SQLException
  {
    Collection aimsAppPhases = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      StringBuffer query= new StringBuffer();

      query.append("from com.netpace.aims.model.core.AimsAppPhas as aimsAppsPhas");
      query.append(" where aimsAppsPhas.appsId =" + appsId);

      aimsAppPhases = session.find(query.toString());
      log.debug("No of records returned: " + aimsAppPhases.size() );

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

     return aimsAppPhases;
  }


  public static AimsAllianc getAllianceByAllianceId(String allianceId)
                               throws HibernateException {

   return (AimsAllianc)DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, allianceId);


  }
}
