package com.netpace.aims.bo.content;

import net.sf.hibernate.*;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.*;

import org.apache.log4j.Logger;

import java.util.*;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.content.AimsFaqTopic;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.system.SystemConstants;

/**
 * This class is responsible for getting the BO for faqs.
 * It has static methods for getting the faqs.
 * @author Fawad Sikandar
 */

public class AimsFAQsManager
{

  static Logger log = Logger.getLogger(AimsFAQsManager.class.getName());

  /**
   *  This static method gets the faq topics in the database
   */
  public static Collection getFAQTopics() throws HibernateException
  {
    Collection collection = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.content.AimsFaqTopic as faqTopic order by last_updated_date");

      log.debug("No of faq topics returned: " + collection.size());

    }
    catch (HibernateException e)
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
     *  This static method gets the faq topics in the database
     */
    public static Collection getFAQTopics(String categoryId, int PAGE_LENGTH, int pageNo) throws HibernateException
    {

       Collection collection = null;
       Session session = null;
       StringBuffer buffer = new StringBuffer();
       try
       {
         session = DBHelper.getInstance().getSession();

         buffer.append("from com.netpace.aims.model.content.AimsFaqTopic as faqTopic ");
         log.debug(" FAQ Category ID : " + categoryId);

         if (!"0".equals(categoryId)  )
           buffer.append(" where faqTopic.aimsFaqCategory.faqCategoryId =" + categoryId);

         buffer.append(" order by last_updated_date");
         Query squery = session.createQuery(buffer.toString());
         squery.setMaxResults(PAGE_LENGTH);
         squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));
         collection = squery.list();

        log.debug("No of faq topics returned: " + collection.size());

      }
      catch (HibernateException e)
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
       *  This static method gets the faq topics in the database
       */
      public static Collection getFAQTopics(String categoryId) throws HibernateException
      {

         Collection collection = null;
         Session session = null;
         StringBuffer buffer = new StringBuffer();
         try
         {
           session = DBHelper.getInstance().getSession();

           buffer.append("from com.netpace.aims.model.content.AimsFaqTopic as faqTopic ");
           log.debug(" FAQ Category ID : " + categoryId);

           if (!"0".equals(categoryId)  )
             buffer.append(" where faqTopic.aimsFaqCategory.faqCategoryId =" + categoryId);

           buffer.append(" order by last_updated_date");
           Query squery = session.createQuery(buffer.toString());
           collection = squery.list();

          log.debug("No of faq topics returned: " + collection.size());

        }
        catch (HibernateException e)
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
   *  This static method gets the faq topics in the database
   */
  public static Collection getFAQTopics(int PAGE_LENGTH, int pageNo) throws HibernateException
  {

     Collection collection = null;
     Session session = null;
     StringBuffer buffer = new StringBuffer();
     try
     {
       session = DBHelper.getInstance().getSession();

       buffer.append("from com.netpace.aims.model.content.AimsFaqTopic as faqTopic order by last_updated_date");

       Query squery = session.createQuery(buffer.toString());
       squery.setMaxResults(PAGE_LENGTH);
       squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));
       collection = squery.list();

      log.debug("No of faq topics returned: " + collection.size());

    }
    catch (HibernateException e)
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
     *  This static method gets the faq topics in the database
     */
    public static int getFAQTopicsCount() throws HibernateException
    {
      int rows = 0;
      Session session = null;
      StringBuffer queryStringBuffer = new StringBuffer();

      try
      {

        queryStringBuffer.append("select count(*) from com.netpace.aims.model.content.AimsFaqTopic as faqTopic");
        session = DBHelper.getInstance().getSession();
        rows = ( (Integer) session.iterate(queryStringBuffer.toString()).next()).
               intValue();
        log.debug("No of rows: " + rows);

      }
      catch (HibernateException e)
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
        *  This static method gets the faq topics in the database
        */
       public static int getFAQTopicsCount(String categoryId) throws HibernateException
       {
         int rows = 0;
         Session session = null;
         StringBuffer queryStringBuffer = new StringBuffer();

         try
         {

           queryStringBuffer.append("select count(*) from com.netpace.aims.model.content.AimsFaqTopic as faqTopic");
           log.debug(" FAQ Category ID : " + categoryId);

           if (!"0".equals(categoryId)  )
           queryStringBuffer.append(" where faqTopic.aimsFaqCategory.faqCategoryId =" + categoryId);

           session = DBHelper.getInstance().getSession();
           rows = ( (Integer) session.iterate(queryStringBuffer.toString()).next()).
                  intValue();
           log.debug("No of rows: " + rows);

         }
         catch (HibernateException e)
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
   *  This static method returns a AimsFaqTopic object for a given Faq_Topic_id (primary_key)
   */
  public static AimsFaqTopic getFAQ(int faqTopicId) throws HibernateException
  {

    AimsFaqTopic faqTopic = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.content.AimsFaqTopic as faqTopic where faqTopic.faqTopicId = :faqTopicId");
      query.setInteger("faqTopicId", faqTopicId);

      for (Iterator it = query.iterate(); it.hasNext(); )
      {
        faqTopic = (AimsFaqTopic) it.next();
        log.debug("FaqTopic: " + faqTopic.toString());
      }
    }
    catch (HibernateException e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
      session.close();
    }

    return faqTopic;
  }

  /**
   *  This static method deletes a faqs topic represented by a given faq_topic_id (primary_key)
   *  It returns the count (most probably 1) of the number of faqs topic deleted.
   */
  public static int deleteFAQTopic(int faqTopicId) throws HibernateException
  {

    int delCount = 0;
    Session session = null;
    try
    {

      session = DBHelper.getInstance().getSession();
      Transaction tx = session.beginTransaction();
      delCount = session.delete("from com.netpace.aims.model.content.AimsFaqTopic as faqTopic where faqTopic.faqTopicId = :faqTopicId",
                                new Integer(faqTopicId), new IntegerType());

      tx.commit();
      log.debug("Number of faqsTopic deleted: " + delCount);

    }
    catch (HibernateException e)
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
   *  This static method updates a given FAQ.
   */


  public static void saveOrUpdateFAQs(AimsFaqTopic faqTopic)  throws
      HibernateException, UniqueConstraintException
  {

    Session session = null;

    try
    {

      session = DBHelper.getInstance().getSession();
      Transaction tx = session.beginTransaction();

      DBHelper.getInstance().save(faqTopic);

    }
    catch(JDBCException je)
                 {
                         String exMessage = je.getMessage();


                         if (DBErrorFinder.searchUniqueConstraintErrors(exMessage,SystemConstants.UNIQUE_CONSTRAINT_KEYS,new UniqueConstraintException()))
                         {
                           throw new UniqueConstraintException ();
                            }

                         else
                         {
                           je.printStackTrace();
                           throw new HibernateException(je);
                         }

             }

    finally
    {
      session.close();
    }
  }

  /**
   *  This static method gets the faq categories in the database
   */

  public static Collection getFAQCategories() throws HibernateException
  {

    Collection collection = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.content.AimsFaqCategory as faqCategory order by Faq_Category_Name");
      log.debug("No of records returned: " + collection.size());

    }
    catch (HibernateException e)
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

}