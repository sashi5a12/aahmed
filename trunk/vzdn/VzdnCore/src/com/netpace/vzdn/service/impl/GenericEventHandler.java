package com.netpace.vzdn.service.impl;

//import com.netpace.aims.model.security.*;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;
import com.netpace.vzdn.model.VzdnUsers;

//import net.sf.hibernate.*;
//import net.sf.hibernate.type.*;

import org.hibernate.*;

import org.hibernate.type.*;
import org.apache.log4j.Logger;

//import oracle.jdbc.driver.*;
import java.sql.*;
import java.util.*;




public class GenericEventHandler extends BaseEventHandler
{
	private static Logger log = Logger.getLogger(GenericEventHandler.class);

     /**
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
 /* public static Collection getAttachments(Long message_id) throws HibernateException
  { 	
    Session session = null;	
	Collection collection = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	

    try
    { 	
		
			queryStringBuffer.append("select ")
							 .append("		attachment.att, ")
							 .append("		attachment.attFileName, ")
							 .append("		attachment.attContentType ")
							 .append("from ")						
							 .append("		com.netpace.vzdn.model.VzdnEmailMessagesAtt attachment ")
							 .append("where ")
							 .append("		attachment.emailMessageId = :message_id ");
		

		//session = DBHelper.getInstance().getSession();
		session = HibernateSessionFactory.getClassicSession();
        collection = session.find(queryStringBuffer.toString(), message_id,  new LongType());

		      
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

    return collection;
  }*/
  
	
  public static Collection getAttachments(Long message_id)throws Exception{
		Session hibernateSession = null;		
		try {
			
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("" +
					"select " + 
			 		" attachment.att, " +
			 		" attachment.attFileName, " +
			 		" attachment.attContentType " +
			 		" from " +						
			 		" com.netpace.vzdn.model.VzdnEmailMessagesAtt attachment " + 
			 		" where " +
			 		" attachment.emailMessageId = :message_id ");			
			searchQuery.setLong("message_id", message_id.longValue());
			List<VzdnEmailMessagesAtt> attachments = searchQuery.list();			
			return attachments;
		} catch (Exception re) {
			log.error(re);
			throw re;
		}
		finally 
		{
			HibernateSessionFactory.closeSession();			
		}
  }

}
