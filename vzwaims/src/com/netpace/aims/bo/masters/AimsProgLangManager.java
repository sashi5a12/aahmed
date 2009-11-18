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
 * This class is responsible for getting the BO for programming languages.
 * It has static methods for getting the programming language.
 * @author Rizwan Qazi
 */
public class AimsProgLangManager
{

  static Logger log = Logger.getLogger(AimsProgLangManager.class.getName());

  /**
   *  This static method gets the programming languages in the database which are available
   *  to the current user.
   */
  public static Collection getProgLangs () throws HibernateException
  {    
	Collection collection = null;   
    Session session = null;
    try
    {         
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.masters.AimsProgrammingLang as proglang order by lang_name");

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

  /**
   *  This static method returns a AimsProgrammingLang object for a given langId (primary_key)     
   */
  public static AimsProgrammingLang getProgLang(int langId) throws HibernateException
  {
    
    AimsProgrammingLang progLang = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.masters.AimsProgrammingLang as progLang where progLang.langId = :langId");
      query.setInteger("langId", langId);
    
  
      for (Iterator it = query.iterate(); it.hasNext();) 
      {
        progLang = (AimsProgrammingLang) it.next();
        log.debug("Programming Language: " + progLang.toString() );
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

    return progLang;
  }  
    

  /**
   *  This static method deletes a programming language represented by a given lang_id (primary_key)   
   *  It returns the count (most probably 1) of the number of programming languages deleted.
   */
  public static int deleteProgLang(int langId) throws HibernateException
  {
    
	int delCount = 0;
    Session session = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.masters.AimsProgrammingLang as proglang where proglang.langId = :langId",
									new Integer(langId), new IntegerType());
	  
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
	*  This static method updates a given device.    
	*/
	public static void saveOrUpdateProgLang(AimsProgrammingLang progLang, String [] platformArr) throws HibernateException
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

		progLang.setPlatform(platform);    
    
		DBHelper.getInstance().save(progLang);

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


	public static void saveOrUpdateDevice(AimsProgrammingLang progLang) throws HibernateException
	{
		
		Session session = null;
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            Transaction tx = session.beginTransaction();


            DBHelper.getInstance().save(progLang);

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

}  