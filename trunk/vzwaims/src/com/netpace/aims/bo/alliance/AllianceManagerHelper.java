package com.netpace.aims.bo.alliance;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.HibernateException;

import com.netpace.aims.model.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.security.*;

import com.netpace.aims.controller.alliance.*;
import com.netpace.aims.controller.contracts.*;
import com.netpace.aims.util.*;

import org.apache.log4j.Logger;

import java.util.*;
import java.text.*;
import java.sql.*;
 
/**
 * This class is responsible for getting the BO for alliances.
 * It has static methods for getting, updating, deleting the alliances.
 * @author Rizwan Qazi
 */

public class AllianceManagerHelper
{

	static Logger log = Logger.getLogger(AllianceManagerHelper.class.getName());

  /**
   *  This static method gets the alliance details of the current alliance   
   */
	//Adds Journal Entry when application is updated
	public static void addJournalEntry(java.sql.Connection ConOra, Long allianceId,
      										String userId, String userType, String entityName, 
                                            String typeOfTransaction) throws Exception
  {
		java.sql.CallableStatement statement = null;
      	
			try
     	{
	   	
            StringBuffer journalText = new StringBuffer();
            journalText.append("Alliance ");
            
            if (typeOfTransaction.equals("I"))
                journalText.append("Created ");

            if (typeOfTransaction.equals("U"))
                journalText.append("Updated ");

            if (typeOfTransaction.equals("D"))
                journalText.append("Deleted ");
            
            journalText.append("By ");
            journalText.append(userId);
            
            statement = ConOra.prepareCall("{ call AIMS_UTILS.add_journal_entry(?,?,?,?,?)}");
            statement.setInt(1, 0);
            statement.setLong(2, allianceId.longValue());
            statement.setString(3, journalText.toString());
            statement.setString(4, "PR");
            statement.setString(5, userId);
            statement.execute(); 
                
               
    	}
    
      catch (Exception ex)
      {
      	throw ex;
      } 
      
      finally
      {
      	if (statement != null)
      	{
            statement.close();
      	}
      }
  }

  /**
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
  public static Object [] getMiscImage (String imageName, String user_type) throws HibernateException
  { 	
    Session session = null;	
	Collection collection = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Object [] resourceValues = null;

    try
    { 	
		
			queryStringBuffer.append("select ")
						     .append("		miscImage.imageFile, ")
			                 .append("		miscImage.imageName, ")
			                 .append("		miscImage.imageContentType ")
			                 .append("from ")						
							 .append("		com.netpace.aims.model.content.AimsMiscImage miscImage ")
							 .append("where ")
							 .append("		miscImage.imageName = :imageName ");
		

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), imageName,  new StringType());

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{			
			resourceValues  = (Object []) iter.next();
		}
		      
	}
	catch(HibernateException e)
	{
		e.printStackTrace();
		throw e;
	}
	finally
	{	
		if (session != null)		
			session.close();		
	}

    return resourceValues;
  } 
  
}  