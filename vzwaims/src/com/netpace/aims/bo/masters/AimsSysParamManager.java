package com.netpace.aims.bo.masters;

import net.sf.hibernate.*;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.*;

import org.apache.log4j.Logger;

import java.util.*;


import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
 
/**
 * This class is responsible for getting the BO for system parameters.
 * It has static methods for getting the system parameters.
 * @author Rizwan Qazi
 */
public class AimsSysParamManager
{

  static Logger log = Logger.getLogger(AimsDevicesManager.class.getName());

  /**
   *  This static method gets the devices in the database which are available
   *  to the current user.
   */
  public static Collection getSysParameters () throws HibernateException
  {    
	Collection collection = null;   
    Session session = null;
    try
    {         
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.masters.AimsSysParameter as sysparam order by parameter_name");

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


  /**
   *  This static method deletes a system parameter represented by a given parameterId (primary_key)   
   *  It returns the count (most probably 1) of the number of system parameter(s) deleted.
   */
  public static int deleteSysParam(int parameterId) throws HibernateException
  {
    
	int delCount = 0;
    Session session = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.masters.AimsSysParameter as sysparam where sysparam.parameterId = :parameterId",
									new Integer(parameterId), new IntegerType());
	  
	  tx.commit();
      log.debug("Number of programming languages deleted: " +delCount);
 
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

    return delCount;
  }


  /**
   *  This static method returns a AimsSysParameter object for a given parameterId (primary_key)     
   */
  public static AimsSysParameter getSysParam(int parameterId) throws HibernateException
  {
    
    AimsSysParameter sysParam = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.masters.AimsSysParameter as sysparam where sysparam.parameterId = :parameterId");
      query.setInteger("parameterId", parameterId);
    
  
      for (Iterator it = query.iterate(); it.hasNext();) 
      {
        sysParam = (AimsSysParameter) it.next();
        log.debug("System Parameter: " + sysParam.toString() );
      }
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

    return sysParam;
  } 

	/**
	*  This static method adds/updates a given system parameter.    
	*/
	public static void saveOrUpdateSysParam(AimsSysParameter sysParam) throws HibernateException
	{
		
		Session session = null;
		
		try
		{
		
		  session = DBHelper.getInstance().getSession();
		  Transaction tx = session.beginTransaction();
  
    
		  DBHelper.getInstance().save(sysParam);

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
	}

}  