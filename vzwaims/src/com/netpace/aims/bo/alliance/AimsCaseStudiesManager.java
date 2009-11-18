package com.netpace.aims.bo.alliance;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;
import net.sf.hibernate.type.IntegerType;
import net.sf.hibernate.HibernateException;

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
public class AimsCaseStudiesManager
{

	static Logger log = Logger.getLogger(AimsCaseStudiesManager.class.getName());

  /**
   *  This static method gets the company information details of the current alliance   
   */
  public static Collection getAllianceCaseStudies (Long alliance_id, String user_type) throws HibernateException
  {    
	Collection collection = null;
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	AllianceCaseStudyForm caseStudy = null;
	Collection AimsCaseStudies = new ArrayList();

    try
    { 
		
		queryStringBuffer.append("select  ")
						 .append("		casestudy.caseStudyId, ")
						 .append("		casestudy.caseStudyName, ")
						 .append("		casestudy.caseStudyDesc, ")
						 .append("		casestudy.status, ")
						 .append("		casestudy.createdDate,  ")   
						 .append("		casestudy.acceptDeclineDate, ")
						 .append("		casestudy.comments, ")
						 .append("		casestudy.caseStudyDocFileName, ")
						 .append("		casestudy.caseStudyDocContentType, ")
						 .append("		casestudy.appsId, ")
						 .append("		casestudy.allianceId,  ")   
						 .append("		acceptDeclineContact.firstName, ")
						 .append("		acceptDeclineContact.lastName, ")
						 .append("		createdContact.firstName, ")
						 .append("		createdContact.lastName  ")   
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsCaseStudy casestudy, ")
						 .append("		com.netpace.aims.model.core.AimsUser acceptDeclineUser, ")
						 .append("		com.netpace.aims.model.core.AimsContact acceptDeclineContact, ")
						 .append("		com.netpace.aims.model.core.AimsUser createdUser, ")
						 .append("		com.netpace.aims.model.core.AimsContact createdContact  ")
						 .append("where ")
						 .append("		casestudy.allianceId = :alliance_id ")
						 .append("		and casestudy.acceptDeclineUserId = acceptDeclineUser.userId (+) ") 
						 .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
						 .append("		and casestudy.createdUserId = createdUser.userId (+) ")
						 .append("		and createdUser.aimsContact.contactId = createdContact.contactId (+) ")   
						 .append("order by ")				
						 .append("		casestudy.caseStudyName ");  
		

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), alliance_id,  new LongType());

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 userValues  = (Object []) iter.next();		
			 caseStudy = new AllianceCaseStudyForm();		

			 caseStudy.setCaseStudyId((Long) userValues[0]);
			 caseStudy.setCaseStudyName((String) userValues[1]);
			 caseStudy.setCaseStudyDesc((String)userValues[2]);
			 caseStudy.setStatus((String)userValues[3]);
			 caseStudy.setCreatedDate((Date) userValues[4]);
			 caseStudy.setAcceptDeclineDate((Date) userValues[5]);
			 caseStudy.setComments((String) userValues[6]);
			 caseStudy.setCaseStudyDocFileName((String) userValues[7]);
			 caseStudy.setCaseStudyDocContentType((String) userValues[8]);
			 caseStudy.setAppsId((Long) userValues[9]);
			 caseStudy.setAllianceId((Long) userValues[10]);
			 caseStudy.setAcceptDeclineContactFirstName((String) userValues[11]);
			 caseStudy.setAcceptDeclineContactLastName((String) userValues[12]);
			 caseStudy.setCreatedContactFirstName((String) userValues[13]);
			 caseStudy.setCreatedContactLastName((String) userValues[14]);

			 AimsCaseStudies.add(caseStudy);	
		}

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

    return AimsCaseStudies;
  } 	

  /**
   *  This static method deletes a case study represented by a given caseStudyId (primary_key)   
   *  It returns the count (most probably 1) of the number of case studie deleted.
   */
  public static int deleteCaseStudy(int caseStudyId) throws HibernateException
  {
    
	int delCount = 0;
    Session session = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.core.AimsCaseStudy as casestudy where casestudy.caseStudyId = :caseStudyId",
									new Integer(caseStudyId), new IntegerType());
	  
	  tx.commit();
      log.debug("Number of case studies deleted: " + delCount);
 
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
   *  This static method deletes a case study represented by a given caseStudyId (primary_key)   
   *  It returns the count (most probably 1) of the number of case studie deleted.
   */
  public static void changeCaseStudiesStatus(Long caseStudyId, String caseStudyStatus, Long userId) throws HibernateException
  {
    	
    Session session = null;
    Transaction tx = null;
    AimsCaseStudy aimsCaseStudy = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  tx = session.beginTransaction();
	  aimsCaseStudy = (AimsCaseStudy) session.load
						     							(AimsCaseStudy.class, caseStudyId);
      aimsCaseStudy.setStatus(caseStudyStatus);	
      aimsCaseStudy.setAcceptDeclineUserId(userId);	
      aimsCaseStudy.setAcceptDeclineDate(new Date());	

	  session.update(aimsCaseStudy);
      session.flush();
	  
	  tx.commit();    
 
     }
     catch(HibernateException e)
     {
        if (tx!=null) 
            tx.rollback();
        e.printStackTrace();
        throw e;
     }
     finally
     {
      session.close();
     }
  }

  /**
   *  This static method gets the case study details of the current case study
   */
  public static Collection getCaseStudy (Long caseStudyId) throws HibernateException
  {    
	Collection collection = null;
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();

    try
    { 
		
		queryStringBuffer.append("select  ")
						 .append("		casestudy.caseStudyId, ")
						 .append("		casestudy.caseStudyName, ")
						 .append("		casestudy.caseStudyDesc, ")
						 .append("		casestudy.status, ")
						 .append("		casestudy.createdDate,  ")   
						 .append("		casestudy.acceptDeclineDate, ")
						 .append("		casestudy.comments, ")
						 .append("		casestudy.caseStudyDocFileName, ")
						 .append("		casestudy.caseStudyDocContentType, ")
						 .append("		casestudy.appsId, ")
						 .append("		casestudy.allianceId  ")   
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsCaseStudy casestudy ")
						 .append("where ")
						 .append("		casestudy.caseStudyId = :caseStudyId ")	
						 .append("order by ")				
						 .append("		casestudy.caseStudyName ");  
		

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), caseStudyId,  new LongType());

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
	*  This static method updates a given alliance's contract info.   
	*/
	public static void UpdateCaseStudy  
									   (
											Long 		caseStudyId,
											String 		caseStudyName,
											String 		caseStudyDescription,
											String 		caseStudyComments,
											FormFile 	caseStudyDoc,   
											Long        currUserId

										) throws AimsException, HibernateException
	{
		
		Session session = null;
        Transaction tx = null;
        int delCount = 0;
		byte[] buffer = new byte[1];
    	buffer[0] = 1;
    	long bytesWrote = 0;
		boolean caseStudyDocPresent = false; 				
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
           
			AimsCaseStudy aimsCaseStudy = (AimsCaseStudy) session.load
						     							(AimsCaseStudy.class, caseStudyId);

            aimsCaseStudy.setCaseStudyName(caseStudyName);
			aimsCaseStudy.setCaseStudyDesc(caseStudyDescription);
			aimsCaseStudy.setComments(caseStudyComments);	

			if ((caseStudyDoc!=null) && (caseStudyDoc.getFileSize()>0))
			{
				aimsCaseStudy.setCaseStudyDocFileName(caseStudyDoc.getFileName());
				aimsCaseStudy.setCaseStudyDocContentType(caseStudyDoc.getContentType());
				aimsCaseStudy.setCaseStudyDoc(Hibernate.createBlob(buffer));
				caseStudyDocPresent = true;
			}

            session.update(aimsCaseStudy);

			session.flush();
			session.refresh(aimsCaseStudy, LockMode.UPGRADE);

			if (caseStudyDocPresent) 
			{
				bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsCaseStudy.getCaseStudyDoc(), caseStudyDoc.getInputStream());
			}
			
            tx.commit();
		 }

		catch(JDBCException je)
		{
			if (tx!=null)
                tx.rollback();

            String exMessage = je.getMessage();
			AimsException ae = new AimsException ();						
						
			for (int i=0;i<AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS.length/2;i++)
			{
				Object[] objs = {AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[i*2]};
				
				if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1) 
				{						
					ae.setMessageKey(AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[(i*2)+1]);
					
					log.debug("ae GETMESSAGEKEY: " + ae.getMessageKey());
					throw ae;
				} else {

					je.printStackTrace();
					throw new HibernateException(je);

				}
			}				


		}


		 catch(HibernateException e)
		 {
						  
				if (tx!=null) 
				{
					
					tx.rollback();
				}								
				e.printStackTrace();
				throw e;
		 }

		catch(Exception ex)
		{
				if (tx!=null) 
				{
					
					tx.rollback();
				}	
				ex.printStackTrace();				
		}

		 finally
		 {

			session.close();
			if (caseStudyDocPresent) 
			{
				caseStudyDoc.destroy();
			}


		 }
	}


	/**
	*  This static method creates a new case study.
	*/
	public static void CreateCaseStudy  
									   (										
											String 		caseStudyName,
											String 		caseStudyDescription,
											String 		caseStudyComments,
											FormFile 	caseStudyDoc,   
											Long        currUserId,
											Long        allianceId

										) throws AimsException, HibernateException
	{
		
		Session session = null;
        Transaction tx = null;
        int delCount = 0;
		byte[] buffer = new byte[1];
    	buffer[0] = 1;
    	long bytesWrote = 0;
		boolean caseStudyDocPresent = false; 				
		
		try
		{
			log.debug("Inside start of CreateCaseStudy Manager " );
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
           
			AimsCaseStudy aimsCaseStudy = new AimsCaseStudy();

            aimsCaseStudy.setCaseStudyName(caseStudyName);
			aimsCaseStudy.setCaseStudyDesc(caseStudyDescription);
			aimsCaseStudy.setComments(caseStudyComments);	
			aimsCaseStudy.setStatus(AimsConstants.NEW_CASE_STUDY);	
			aimsCaseStudy.setAllianceId(allianceId);
			aimsCaseStudy.setCreatedDate(new Date());
			aimsCaseStudy.setCreatedUserId(currUserId);

			if ((caseStudyDoc!=null) && (caseStudyDoc.getFileSize()>0))
			{
				aimsCaseStudy.setCaseStudyDocFileName(caseStudyDoc.getFileName());
				aimsCaseStudy.setCaseStudyDocContentType(caseStudyDoc.getContentType());
				aimsCaseStudy.setCaseStudyDoc(Hibernate.createBlob(buffer));
				caseStudyDocPresent = true;
			}

            session.save(aimsCaseStudy);

			session.flush();
			session.refresh(aimsCaseStudy, LockMode.UPGRADE);

			if (caseStudyDocPresent) 
			{
				bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsCaseStudy.getCaseStudyDoc(), caseStudyDoc.getInputStream());
			}
			
            tx.commit();
			log.debug("Inside end of CreateCaseStudy Manager " );
		 }

		catch(JDBCException je)
		{
            if (tx!=null)
                tx.rollback();

            String exMessage = je.getMessage();
			AimsException ae = new AimsException ();						
						
			for (int i=0;i<AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS.length/2;i++)
			{
				Object[] objs = {AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[i*2]};
				
				if (exMessage.indexOf(AimsConstants.UNIQUE_CONSTRAINT_MESSAGE.format(objs)) > -1) 
				{						
					ae.setMessageKey(AimsConstants.AIMS_ALLIANCE_COMPANY_INFO_UNIQUE_KEYS[(i*2)+1]);
					
					log.debug("ae GETMESSAGEKEY: " + ae.getMessageKey());
					throw ae;
				} else {

					je.printStackTrace();
					throw new HibernateException(je);

				}
			}				


		}


		 catch(HibernateException e)
		 {
						  
				if (tx!=null) 
				{
					
					tx.rollback();
				}								
				e.printStackTrace();
				throw e;
		 }

		catch(Exception ex)
		{
				if (tx!=null) 
				{
					
					tx.rollback();
				}	
				ex.printStackTrace();				
		}

		 finally
		 {

			session.close();
			if (caseStudyDocPresent) 
			{
				caseStudyDoc.destroy();
			}


		 }
	}

  /**
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
  public static Object [] getCaseStudyResource (String resourceName, Long pkId, String user_type) throws HibernateException
  { 	
    Session session = null;	
	Collection collection = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Object [] resourceValues = null;

    try
    { 	
		
			queryStringBuffer.append("select ");

		if (resourceName.equalsIgnoreCase("caseStudyDoc"))
		{
			queryStringBuffer.append("		objectName.").append(resourceName).append(", ")
							 .append("		objectName.").append(resourceName).append("FileName, ")
							 .append("		objectName.").append(resourceName).append("ContentType ")
							 .append("from ")						
							 .append("		com.netpace.aims.model.core.AimsCaseStudy objectName ")
							 .append("where ")
							 .append("		objectName.caseStudyId = :pkId ");
		}
		
		
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