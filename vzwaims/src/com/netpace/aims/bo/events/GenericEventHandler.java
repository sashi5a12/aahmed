package com.netpace.aims.bo.events;

import com.netpace.aims.model.events.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.security.*;
import com.netpace.aims.util.*;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;
import org.apache.log4j.Logger;

import oracle.jdbc.driver.*;
import java.sql.*;
import java.util.*;




public class GenericEventHandler extends BaseEventHandler
{
     /**
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
  public static Collection getAttachments(Long message_id) throws HibernateException
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
							 .append("		com.netpace.aims.model.events.AimsEmailMessagesAtt attachment ")
							 .append("where ")
							 .append("		attachment.emailMessageId = :message_id ");
		

		session = DBHelper.getInstance().getSession();

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
  } 

}
