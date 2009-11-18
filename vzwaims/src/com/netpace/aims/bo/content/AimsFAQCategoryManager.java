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
import com.netpace.aims.model.content.AimsFaqCategory;
import com.netpace.aims.bo.core.IntegrityConstraintException;

/**
 * This class is responsible for getting the BO for faq categories.
 * It has static methods for getting the faq categories.
 * @author Fawad Sikandar
 */

public class AimsFAQCategoryManager
{

  static Logger log = Logger.getLogger(AimsFAQCategoryManager.class.getName());

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
      collection = session.find("from com.netpace.aims.model.content.AimsFaqCategory as faqCategory order by faqCategory.faqCategoryName");
      log.debug("No of faq Categories returned: " + collection.size());
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
     *  This static method gets the faq categories in the database
     */
    public static Collection getFAQCategories(int PAGE_LENGTH, int pageNo) throws HibernateException
    {

       Collection collection = null;
       Session session = null;
       StringBuffer buffer = new StringBuffer();
       try
       {
         session = DBHelper.getInstance().getSession();

         buffer.append("from com.netpace.aims.model.content.AimsFaqCategory ");
         Query squery = session.createQuery(buffer.toString());
         squery.setMaxResults(PAGE_LENGTH);
         squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));
         collection = squery.list();

        log.debug("No of faq Categories returned: " + collection.size());

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
     *  This static method gets the faq categories in the database
     */
    public static int getFAQCategoriesCount() throws HibernateException
    {
      int rows = 0;
      Session session = null;
      StringBuffer queryStringBuffer = new StringBuffer();

      try
      {

        queryStringBuffer.append("select count(*) from com.netpace.aims.model.content.AimsFaqCategory");
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
   *  This static method returns a AimsFaqCategory object for a given FaqCategoryId (primary_key)
   */
  public static AimsFaqCategory getFAQCategory(int faqCategoryId) throws HibernateException
  {

    AimsFaqCategory faqCategory = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.content.AimsFaqCategory as faqCategory where faqCategory.faqCategoryId = :faqCategoryId");
      query.setInteger("faqCategoryId", faqCategoryId);

      for (Iterator it = query.iterate(); it.hasNext(); )
      {
        faqCategory = (AimsFaqCategory) it.next();
        log.debug("faqCategory: " + faqCategory.toString());
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

    return faqCategory;
  }

  /**
   *  This static method deletes a faqs Category represented by a given faqCategoryId (primary_key)
   *  It returns the count (most probably 1) of the number of faqs category deleted.
   */
  public static int deleteFAQCategory(int faqCategoryId) throws HibernateException, IntegrityConstraintException
  {

    int delCount = 0;
    Session session = null;
    try
    {

      session = DBHelper.getInstance().getSession();
      Transaction tx = session.beginTransaction();
      delCount = session.delete("from com.netpace.aims.model.content.AimsFaqCategory as faqCategory where faqCategory.faqCategoryId = :faqCategoryId",
                                new Integer(faqCategoryId), new IntegerType());

      tx.commit();
      log.debug("Number of faqCategory deleted: " + delCount);

    }
    catch(JDBCException je)
    {
      String exMessage = je.getMessage();
      if (DBErrorFinder.searchIntegrityConstraintErrors(exMessage,
          SystemConstants.INTEGRITY_CONSTRAINT_KEYS,
          new IntegrityConstraintException())) {
        throw new IntegrityConstraintException();
      }

      else {
        je.printStackTrace();
        throw new HibernateException(je);
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

    return delCount;
  }

  /**
   *  This static method updates a given FAQ Category.
   */


  public static void saveOrUpdateFAQs(AimsFaqCategory faqCategory) throws
      HibernateException, UniqueConstraintException {

    try {
      DBHelper.getInstance().save(faqCategory);
    }
    catch (JDBCException je) {
      String exMessage = je.getMessage();

      if (DBErrorFinder.searchUniqueConstraintErrors(exMessage,
          SystemConstants.UNIQUE_CONSTRAINT_KEYS, new UniqueConstraintException())) {
        throw new UniqueConstraintException();
      }

      else {
        je.printStackTrace();
        throw new HibernateException(je);
      }

    }

    catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    }

  }

}