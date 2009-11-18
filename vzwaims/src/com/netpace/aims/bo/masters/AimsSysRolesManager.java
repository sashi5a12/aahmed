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
 * This class is responsible for getting the BO for role.
 * It has static methods for getting the roles information.
 * @author Ahson Imtiaz
 */
public class AimsSysRolesManager
{

  static Logger log = Logger.getLogger(AimsSysRolesManager.class.getName());

  /**
   */
  public static Collection getSysRoles () throws HibernateException
  {    
	Collection collection = null;   
    Session session = null;
    try
    {         
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.security.AimsSysRole as sysrole order by role_name");
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
   */
   /*
  public static Collection getPlatforms () throws HibernateException
  {    
	Collection collection = null;   
    Session session = null;
    try
    {         
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.core.AimsPlatform as platforms order by platform_name");

	  log.debug("No of platforms returned: " + collection.size() ); 
      
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
   */
   /*
  public static AimsDevic getDevice(int deviceId) throws HibernateException
  {
    
    AimsDevic device = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId = :deviceId");
      query.setInteger("deviceId", deviceId);
    
  
      for (Iterator it = query.iterate(); it.hasNext();) 
      {
        device = (AimsDevic) it.next();
        log.debug("Device: " + device.toString() );
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

    return device;
  }  
    */

  /**
   */
  public static int deleteSysRole(int roleId) throws HibernateException
  {
    
	int delCount = 0;
    Session session = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.security.AimsSysRole as sysroles where sysroles.roleId = :roleId",
									new Integer(roleId), new IntegerType());
	  
	  tx.commit();
      log.debug("Number of system role deleted: " +delCount);
 
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
	*  This static method updates a given device.    
	*/
	/*
	public static void saveOrUpdateSysRole(AimsSysRol device, String [] sysRoleArr) throws HibernateException
	{
		
		Session session = null;
		
		try
		{
		
		  session = DBHelper.getInstance().getSession();
		  Transaction tx = session.beginTransaction();

		  HashSet platform = new HashSet();
		  for (int i=0; i<sysRoleArr.length; i++) {		
			  platform.add((AimsSysRol) session.load(AimsSysRol.class, new Long(sysRoleArr[i])));					  
		  }

		device.setPlatform(platform);    
    
		DBHelper.getInstance().save(device);

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
*/
	public static void saveOrUpdateSysRole(AimsSysRol role) throws HibernateException
	{
		
		Session session = null;
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            Transaction tx = session.beginTransaction();


            //DBHelper.getInstance().save(role);

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

/*
	public static HashSet getPlatformSet(String [] platformArr) throws HibernateException
	{
		
		Session session = null;
		
		try
		{
		
            session = DBHelper.getInstance().getSession(); 

            HashSet platformSet = new HashSet();
            for (int i=0; i<platformArr.length; i++) {		
              platformSet.add((AimsPlatform) session.load(AimsPlatform.class, new Long(platformArr[i])));					  
            }            

            return platformSet;
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
	}*/
}  