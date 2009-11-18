package com.netpace.aims.bo.masters;

import net.sf.hibernate.*;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.HibernateException;
import com.netpace.aims.model.*;

import org.apache.log4j.Logger;

import java.util.*;

import javax.servlet.ServletException;

import com.netpace.aims.bo.core.*;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
 
/**
 * This class is responsible for getting the BO for industry focus.
 * It has static methods for getting/modifying the industry focus details.
 * @author Rizwan Qazi
 */
public class AimsIndFocusManager
{

  static Logger log = Logger.getLogger(AimsIndFocusManager.class.getName());

  /**
   *  This static method gets the industry foci(focuses) in the database which are available
   *  to the current user.
   */
  public static Collection getIndFocuses () throws HibernateException
  {    
	Collection collection = null;   
    Session session = null;
    try
    {         
      session = DBHelper.getInstance().getSession();
      collection = session.find("from com.netpace.aims.model.masters.AimsIndustryFocu as indfocus order by industry_name");

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
   *  This static method returns a AimsIndustryFocu object for a given industryId (primary_key)     
   */
  public static AimsIndustryFocu getIndFocus(int industryId) throws HibernateException
  {
    
    AimsIndustryFocu indFocus = null;
    Session session = null;
    try
    {
      session = DBHelper.getInstance().getSession();
      Query query = session.createQuery("select from com.netpace.aims.model.masters.AimsIndustryFocu as indfocus where indfocus.industryId = :industryId");
      query.setInteger("industryId", industryId);
    
  
      for (Iterator it = query.iterate(); it.hasNext();) 
      {
        indFocus = (AimsIndustryFocu) it.next();
        log.debug("Programming Language: " + indFocus.toString() );
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

    return indFocus;
  }  
    

  /**
   *  This static method deletes a industry focus represented by a given industryId (primary_key)   
   *  It returns the count (most probably 1) of the number of industry focus deleted.
   */
  public static int deleteIndustryfocus(int industryId) throws IntegrityConstraintException, HibernateException
  {
    
	int delCount = 0;
    Session session = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.masters.AimsIndustryFocu as indfocus where indfocus.industryId = :industryId",
									new Integer(industryId), new IntegerType());
	  
	  tx.commit();
      log.debug("Number of programming languages deleted: " +delCount);
 
     }

	catch(JDBCException je)
	{
		String exMessage = je.getMessage();
		if (exMessage.indexOf("ORA-02292: integrity constraint (AIMS.FK_ALLIANCE_IND_FOCUS_2) violated - child record found") > -1)
		{
			throw new IntegrityConstraintException ();
		} 

		else 
		{
			je.printStackTrace();
			throw new HibernateException(je);
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

    return delCount;
  }

	/**
	*  This static method updates a given industry focus.    
	*/
	public static void saveOrUpdateIndFocus(AimsIndustryFocu indFocus) throws UniqueConstraintException, HibernateException  
	{
		
		Session session = null;
		
		try
		{
		
		  session = DBHelper.getInstance().getSession();
		  Transaction tx = session.beginTransaction();
  
    
		  DBHelper.getInstance().save(indFocus);

		 }

		 catch(JDBCException je)
		 {
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint (AIMS.AK_INDUSTRY_FOCUS_IND_NAME) violated") > -1)
			{
			  throw new UniqueConstraintException ();
			} 

			else 
			{
			  je.printStackTrace();
			  throw new HibernateException(je);
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
	}


}  