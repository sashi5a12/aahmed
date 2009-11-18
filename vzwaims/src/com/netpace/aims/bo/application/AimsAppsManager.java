package com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.*;

import org.apache.log4j.Logger;

import java.util.*;


import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.application.*;
 
/**
 * Decsription here
 * @author Ahson Imtiaz
 */
public class AimsAppsManager
{

  static Logger log = Logger.getLogger(AimsAppsManager.class.getName());

  public static Collection getApps () throws HibernateException
  {    
	Collection collection = null;   
    Session session = null;
    try
    {         
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.application.AimsApp");

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

    return collection;
  }  

  public static AimsApp getApp (String strAppsID) throws HibernateException
  {    
	 AimsApp aApp = null;   
    Session session = null;
    try
    {     
    	log.debug("Starting query for apps.");    
      session = DBHelper.getInstance().getSession();
      aApp = (AimsApp) session.find("from com.netpace.aims.model.application.AimsApp as aapps where aapps.appsId = " + strAppsID).get(0);

	  log.debug("Returned AimsApp Object "); 
      
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

    return aApp;
  }
}  