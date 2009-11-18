package com.netpace.vzdn.model;

//import net.sf.hibernate.Criteria;
import org.hibernate.*;
import org.hibernate.classic.Session;

//import net.sf.hibernate.cfg.*;
import org.hibernate.cfg.*;
//import net.sf.hibernate.expression.Order;
import org.hibernate.criterion.Order;

//import net.sf.hibernate.HibernateException;
//import net.sf.hibernate.expression.*;
import org.hibernate.criterion.*;
import java.io.*;
import java.util.*;

import org.hibernate.HibernateException;
//import net.sf.hibernate.HibernateException;

/**
 * This Singleton class has helper method for easy DB access.
 * It just provides some easy wrappers over hibernate classes.
 * This class caches the session factory and the db info and provides accessor 
 * methods to the session. Its the developers responsibility to close the session object 
 * it receives
 * 
 * The session factory for this DBHelper is set at startup by the Aims startup plugin configured to run at system startup.
 * To use this class in test code not running in the frameworkm set the sessionFactory instance with the appropriate db config
 *@author Shahnawaz Bagdadi
 * @version 1.0
 */
public class DBHelper
{
   private static DBHelper instance = null;

   public SessionFactory sessionFactory = null;   
   
   public static final String ASC= "ASC";
   
   public static final String DESC= "DESC";

   /**
    *  Protected constructor for the singleton class
    */
   protected DBHelper()
   {
	  //sessionFactory = SessionFactoryHelper.getSessionFactory();
   }
   
   
   /**
    *   This method uses the session factory to get a new session and starts
    * a transaction using the session. It then fires a saveOrUpdate and saves
    * the object and its associated objects
    * This is a easy access method if you have to persist a simple value object. 
    * For more flexibility and control get the session using getSession and use it for persistence.
    * @exception - the hibernate exception thrown while accessing the db
    * @param obj - the BaseValueObject to be persisted*/
   public void save(BaseValueObject obj) throws HibernateException
   {
    Session sess = sessionFactory.openSession();
     Transaction tx = null;
     try 
     {
         tx = sess.beginTransaction();
         sess.saveOrUpdate(obj);
         tx.commit();
     }
     catch (HibernateException e) 
     {
         if (tx!=null) 
          tx.rollback();
         throw e;
     }
     finally 
     {
         sess.close();
     }
   }
   




    /**
     *  This method returns a page of objects of the class specified.
     *  
     *  IKB: This method uses Criteria queries. Migrating this method to Hibernate 3.0 will require deeper analysis. Commenting for now. 
     *  If we need this method in notifications in VZDN Core, we would then have to see the Hibernate 3.0 Migration guide as to how 
     *  to change this method.
     */
     /*public List getPage(Class clazz, Expression[] filters, Order[] orders, int PAGE_LENGTH, int page_no) throws HibernateException
     {
         List list = null;
         Session session = sessionFactory.openSession();
         try 
         {
            Criteria c = session.createCriteria(clazz) ;

            // add all the filter to the criteria
            if (filters != null)
            {
              for (int i=0; i < filters.length; i++)
              {
               c.add(filters[i]);
              } 
            }
            

            // add sorting to the criteria
            if (orders != null)
            {
              for (int j=0; j < orders.length; j++)
              {
               c.addOrder( orders[j] );
              } 
            }

            // set the page length
            c.setMaxResults(PAGE_LENGTH);  
            c.setFirstResult(PAGE_LENGTH * (page_no-1));  
            list = c.list();      

         }
         catch (HibernateException e) 
         {
             throw e;
         }
         finally 
         {
             session.close();
         }
         return list;
     }
*/

   /**
    *   This method uses the session factory to get a new session and starts
    * a transaction using the session. It then fires a load and retrieves the specified object
    * This is a easy access method if you have to persist a simple value object. 
    * For more flexibility and control get the session using getSession and use it for loading an object.
    *
    * @exception - the hibernate exception thrown while accessing the db
    * @param class - the ValueObject class to be loaded
    * @param pk - the primary key as a String    
    */
   public BaseValueObject load(Class clazz , String pk) throws HibernateException
   {
     Session sess = sessionFactory.openSession();
     BaseValueObject obj = null;
     
     Transaction tx = null;
     try 
     {
         tx = sess.beginTransaction();
         obj = (BaseValueObject)sess.load(clazz, new Long(pk));
         tx.commit();
     }
     catch (HibernateException e) 
     {
         if (tx!=null) 
          tx.rollback();
         throw e;
     }
     finally 
     {
         sess.close();
     }
     return obj;
   }
   
   
   /**
    *  This method returns the light weight session object ..
    *  Its the users responsibility to use the session as appropriate and close it by calling session.close()
    */
   public Session getSession() throws HibernateException
   {
     return sessionFactory.openSession();
   }
   
   /**
    *  This method returns the singleton instance of the DBHelper.
    * @return DBHelper - the singleton instance of the DBHelper.
    */
   public static DBHelper getInstance()
   {
     if (instance == null)
     {
       synchronized(DBHelper.class)
       {
          if (instance == null)
          {
             instance = new DBHelper();
          }
       }
     
     }
     return instance;
   }

}


