package com.netpace.aims.bo.masters;

import com.netpace.aims.bo.core.IntegrityConstraintException;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.model.masters.*;
import net.sf.hibernate.*;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.*;

import org.apache.log4j.Logger;

import java.util.*;


import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.util.AimsConstants;

/**
 * This class is responsible for getting the BO for devices.
 * It has static methods for getting the devices.
 * @author Rizwan Qazi
 */
public class AimsDevicesManager
{

  static Logger log = Logger.getLogger(AimsDevicesManager.class.getName());

  /**
   *  This static method gets the devices in the database which are available
   *  to the current user.
   */
  public static Collection getDevices () throws HibernateException
  {
	Collection collection = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId != 0 order by device_model");

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
   *  This static method gets the platforms in the database
   */
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

  public static Collection getAllPlatformIds () throws HibernateException
  {
	Collection collection = new ArrayList();
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      String queryStr = "select platform.platformId from com.netpace.aims.model.core.AimsPlatform as platform order by platform.platformName";

      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery(queryStr);
      
      Iterator iter = query.iterate();
      if(iter != null) {
          while (iter.hasNext())
        	  collection.add((Long) iter.next());                                    
          //end while
      }

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

  public static Collection getDevices (boolean filter) throws HibernateException
    {
          Collection collection = null;
      Session session = null;
      try
      {
        session = DBHelper.getInstance().getSession();
        StringBuffer query = new StringBuffer();
        query.append("from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId != 0");

        if ( filter )
           query.append(" and device.markAs is Null");

        query.append(" order by device_model");
        collection = session.find(query.toString());
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

    public static Collection getDevices (Long platformId, boolean filter) throws HibernateException
    {
          Collection collection = null;
          Session session = null;
          try
          {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId != 0 and " + platformId + " in elements(device.platform) ");

            if ( filter )
               query.append(" and device.markAs is Null");

            query.append(" order by device_model");
            collection = session.find(query.toString());
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
    
    public static Collection getDevices (boolean filter, int PAGE_LENGTH, int pageNo) throws HibernateException
      {
            Collection collection = null;
        Session session = null;
        try
        {
          session = DBHelper.getInstance().getSession();
          StringBuffer sbquery = new StringBuffer();
          sbquery.append("from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId != 0");

          if ( filter )
             sbquery.append(" and device.markAs is Null");

          sbquery.append(" order by device_model");
          Query query =  session.createQuery(sbquery.toString());

          query.setMaxResults(PAGE_LENGTH);
          query.setFirstResult(PAGE_LENGTH * (pageNo - 1));
          collection = query.list();

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




    public static int getDevicesCount (boolean filter) throws HibernateException
        {
          int rows = 0;
          Session session = null;
          try
          {
            session = DBHelper.getInstance().getSession();
            StringBuffer sbquery = new StringBuffer();
            sbquery.append("Select count(*) from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId != 0");

            if ( filter )
               sbquery.append(" and device.markAs is Null");

            Query query = session.createQuery(sbquery.toString());

            rows = ( (Integer)query.iterate().next()).intValue();
            log.debug("Rows Counted : "  + rows );

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

          return rows;
        }




  /**
   *  This static method returns a AisDevic object for a given device_id (primary_key)
   */
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


	/**
   *  Overloaded Function
   */
  public static AimsDevic getDevice(Long deviceId) throws HibernateException
  {

    AimsDevic device = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId = :deviceId");
      query.setLong("deviceId", deviceId.longValue());


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


  /**
   *  This static method deletes a device represented by a given device_id (primary_key)
   *  It returns the count (most probably 1) of the number of devices deleted.
   */
  public static int deleteDevice(int deviceId) throws HibernateException,IntegrityConstraintException
  {

	int delCount = 0;
    Session session = null;
    Transaction tx = null;
    
    try
    {

      session = DBHelper.getInstance().getSession();
	  tx = session.beginTransaction();

      session.delete("from AimsDevicePid as pid where pid.deviceId = :deviceId", new Integer(deviceId), new IntegerType());
      delCount = session.delete("from com.netpace.aims.model.masters.AimsDevic as device where device.deviceId = :deviceId",
									new Integer(deviceId), new IntegerType());

	  tx.commit();
      log.debug("Number of devices deleted: " +delCount);

     }
     catch(HibernateException e)
     {
        if (tx != null)
            tx.rollback();
        if(e.getMessage().indexOf("ORA-02292")>0){
        	IntegrityConstraintException ice=new IntegrityConstraintException();
	    	ice.setMessageKey("error.Device.delete");
	    	throw ice;
	    }  
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
	public static void saveOrUpdateDevice(AimsDevic device, String [] platformArr) throws HibernateException
	{

		Session session = null;

		try
		{

		  session = DBHelper.getInstance().getSession();
		  Transaction tx = session.beginTransaction();

		  HashSet platform = new HashSet();
		  for (int i=0; i<platformArr.length; i++) {
			  platform.add((AimsPlatform) session.load(AimsPlatform.class, new Long(platformArr[i])));
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

	public static void saveOrUpdateDevice(AimsDevic device) throws HibernateException, UniqueConstraintException
	{

		Session session = null;
		Transaction tx =null;
		try
		{

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            
            session.saveOrUpdate(device);            
            session.delete("from AimsDevicePid pid where pid.deviceId=:id",device.getDeviceId(),new LongType());
            session.flush();
                        
            if(device.getAimsDevicePids()!=null && device.getAimsDevicePids().isEmpty()==false){
	            Iterator pidItr =device.getAimsDevicePids().iterator();
	            while (pidItr.hasNext()){
	            	AimsDevicePid pid=(AimsDevicePid)pidItr.next();
	            	pid.setDeviceId(device.getDeviceId());
	            	session.saveOrUpdate(pid);            	            
	            }
            }
            tx.commit();
		 }
		 catch(HibernateException e)
		 {
			if(tx != null){
				tx.rollback();
			}
		    if(e.getMessage().indexOf("ORA-00001")>0 && e.getMessage().indexOf("DEVICES_DEVICE_MODEL")>0){
		    	UniqueConstraintException uni=new UniqueConstraintException();
		    	uni.setMessageKey("error.DeviceForm.deviceModel.exists");
		    	System.out.println("Deivce already exists");
		    	throw uni;
		    }
		    else if(e.getMessage().indexOf("ORA-00001")>0 || e.getMessage().indexOf("unique")>0){
		    	UniqueConstraintException uni=new UniqueConstraintException();
		    	uni.setMessageKey("error.DeviceForm.primaryPID.exists");
		    	throw uni;
		    }
		    e.printStackTrace();
		    throw e;
		 }
		 finally
		 {
			 if (session!=null){
				 session.close();
			 }
		 }
	}

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
	}
	
	public static void main(String args[])throws Exception{
		DBHelper dbHelper=DBHelper.getInstance();
		dbHelper.sessionFactory=new Configuration().configure().buildSessionFactory();
		AimsDevic device=new AimsDevic();
		device.setDeviceModel("100");
		AimsDevicesManager.saveOrUpdateDevice(device);
	}
}