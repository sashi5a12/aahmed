package com.netpace.aims.bo.core;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;

import com.netpace.aims.model.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.security.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.alliance.*;

import com.netpace.aims.bo.core.*;
import com.netpace.aims.controller.alliance.*;
import com.netpace.aims.util.*;


import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import oracle.sql.BLOB;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
/**
 * This class is responsible for getting the BO for Case Studies.
 * @author Rizwan Qazi
 */
public class AllianceLobManager
{

	static Logger log = Logger.getLogger(AllianceLobManager.class.getName());

  /**
   *  This static method gets the blob resource.
   */
  public static Object [] getResource (String tableName, String colName, String pkCol, String pkId) throws HibernateException
  { 	
    Session session = null;	
	Collection collection = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Object [] resourceValues = null;

    try
    { 	
		
			queryStringBuffer.append("select ")	
							 .append("		objectName.").append(colName).append(", ")
							 .append("		objectName.").append(colName).append("FileName, ")
							 .append("		objectName.").append(colName).append("ContentType ")
							 .append("from ")						
							 .append("		").append(tableName).append(" objectName ")
							 .append("where ")
							 .append("		objectName.").append(pkCol).append(" = ").append(pkId);
	
		
		
		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), pkId,  new LongType());

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