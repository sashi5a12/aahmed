package com.netpace.aims.bo.alliance;

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
public class AllianceSpotlightsManager
{

	static Logger log = Logger.getLogger(AllianceSpotlightsManager.class.getName());

  /**
   *  This static method gets the spotlight types from the database  
   */
  public static Collection getSpotlightTypes (Long alliance_id, String user_type) throws HibernateException
  {    
	Collection collection = null;
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	AimsSpotlightType spotLight = null;
	Collection AimsSpotLightTypes = new ArrayList();

    try
    { 
        /*
		
		queryStringBuffer.append("select distinct ")
						 .append("		spotlight.spotlightTypeId, ")
						 .append("		spotlight.spotlightTypeName, ")
						 .append("		spotlight.spotlightTypeDesc ")
						 .append("from ")
						 .append("		com.netpace.aims.model.masters.AimsSpotlightType spotlight, ")
						 .append("		com.netpace.aims.model.alliance.AimsAllianceSpotlight allianceSpotlight ")
						 .append("where ")
						 .append("		allianceSpotlight.allianceId = :alliance_id ")
						 .append("		and allianceSpotlight.spotLightTypeId = spotlight.spotlightTypeId ")
						 .append("order by ")				
						 .append("		spotlight.spotlightTypeName ");  
		*/

		queryStringBuffer.append("select distinct ")
						 .append("		spotlight.spotlightTypeId, ")
						 .append("		spotlight.spotlightTypeName, ")
						 .append("		spotlight.spotlightTypeDesc, ")
                         .append("		spotlight.imageName ")
						 .append("from ")
						 .append("		com.netpace.aims.model.masters.AimsSpotlightType spotlight ")
						 .append("order by ")				
						 .append("		spotlight.spotlightTypeName ");  

		session = DBHelper.getInstance().getSession();
        collection = session.find(queryStringBuffer.toString());
        //collection = session.find(queryStringBuffer.toString(), alliance_id,  new LongType());

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 userValues  = (Object []) iter.next();		
			 spotLight = new AimsSpotlightType();		

			 spotLight.setSpotlightTypeId((Long) userValues[0]);
			 spotLight.setSpotlightTypeName((String) userValues[1]);
			 spotLight.setSpotlightTypeDesc((String)userValues[2]);
             spotLight.setImageName((String)userValues[3]);
			 
			 AimsSpotLightTypes.add(spotLight);	
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

    return AimsSpotLightTypes;
  } 	


  /**
   *  This static method gets the spotlight records from the database  
   */
  public static Collection getSpotLightRecords (Long alliance_id, String user_type, 
														Long spotLightTypeId) throws HibernateException
  {    
	Collection collection = null;
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	AllianceSpotLightForm spotLight = null;
	Collection AimsSpotLightRecords = new ArrayList();
	Object [] parameterValues = null;
    Type [] parameterTypes = null;

    try
    { 
		

        parameterValues = new Object [2];
        parameterTypes =  new Type [2]; 
        
        parameterValues[0] = spotLightTypeId;
        parameterValues[1] = alliance_id;
       
        parameterTypes[0] = new LongType();
        parameterTypes[1] = new LongType();

	    queryStringBuffer.append("select ")
						 .append("		allianceSpotlight.spotlightId, ")
						 .append("		allianceSpotlight.spotlightName, ")	
						 .append("		allianceSpotlight.spotlightDesc, ")	
						 .append("		allianceSpotlight.allianceId, ")	
						 .append("		allianceSpotlight.createdBy, ")	
						 .append("		allianceSpotlight.createdDate, ")	
						 .append("		allianceSpotlight.spotlightFileFileName, ")	
						 .append("		allianceSpotlight.spotlightFileContentType, ")	
						 .append("		allianceSpotlight.status ")	
						 .append("from ")
						 .append("		com.netpace.aims.model.alliance.AimsAllianceSpotlight allianceSpotlight ")
						 .append("where ")
						 .append("		allianceSpotlight.spotLightTypeId = :spotLightTypeId ")
						 .append("		and allianceSpotlight.allianceId = :alliance_id ")
						 .append("order by  ")
						 .append("		allianceSpotlight.spotlightName  ");
		

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);
		

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 userValues  = (Object []) iter.next();		
			 spotLight = new AllianceSpotLightForm();		

			 spotLight.setSpotlightId((Long) userValues[0]);
			 spotLight.setSpotlightName((String) userValues[1]);
			 spotLight.setSpotlightDesc((String)userValues[2]);

			 spotLight.setAllianceId((Long) userValues[3]);
			 spotLight.setCreatedBy((String) userValues[4]);
			 spotLight.setCreatedDate((Date)userValues[5]);

			 spotLight.setSpotlightFileFileName((String) userValues[6]);
			 spotLight.setSpotlightFileContentType((String) userValues[7]);
			 spotLight.setStatus((String)userValues[8]);
			 
			 AimsSpotLightRecords.add(spotLight);	
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

    return AimsSpotLightRecords;
  } 	


  /**
   *  This static method gets the case study details of the current case study
   */
  public static Collection getSpotLightInfo (Long spotLightId) throws HibernateException
  {    
	Collection collection = null;
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();

    try
    { 
		
	    queryStringBuffer.append("select ")			
						 .append("		allianceSpotlight.spotlightName, ")	
						 .append("		allianceSpotlight.spotlightDesc, ")			
						 .append("		allianceSpotlight.spotlightFileFileName, ")	
						 .append("		allianceSpotlight.spotlightFileContentType, ")	
						 .append("		allianceSpotlight.status ")	
						 .append("from ")
						 .append("		com.netpace.aims.model.alliance.AimsAllianceSpotlight allianceSpotlight ")
						 .append("where ")
						 .append("		allianceSpotlight.spotlightId = :spotLightId ");
		

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), spotLightId,  new LongType());

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
   *  This static method deletes a spotlight represented by a given spotlightId (primary_key)   
   *  It returns the count (most probably 1) of the number of case studie deleted.
   */
  public static int deleteSpotLight(Long spotlightId) throws HibernateException
  {
    
	int delCount = 0;
    Session session = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  Transaction tx = session.beginTransaction();
	  delCount = session.delete("from com.netpace.aims.model.alliance.AimsAllianceSpotlight as spotlight where spotlight.spotlightId = :spotlightId",
									spotlightId, new LongType());
	  
	  tx.commit();      
 
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
   *  This static method updates the status of the give spotlight.  
   */
  public static void changeSpotLightStatus(Long spotLightId, String spotLightStatus, Long userId) throws HibernateException
  {
    	
    Session session = null;
    Transaction tx = null;
    AimsAllianceSpotlight aimsSpotLight = null;
    try
    {
	
      session = DBHelper.getInstance().getSession();
	  tx = session.beginTransaction();

	  aimsSpotLight = (AimsAllianceSpotlight) session.load
						     							(AimsAllianceSpotlight.class, spotLightId);

      aimsSpotLight.setStatus(spotLightStatus);	
      

	  session.update(aimsSpotLight);
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
	*  This static method updates a given alliance's spotlight info. 
	*/
	public static void UpdateSpotLight  
									   (
											Long 		spotLightId,
											Long		spotLightTypeId,
											String 		spotLightName,
											String 		spotLightDescription,											
											FormFile 	spotLightDoc,   
											String      currUserId,
											Long        allianceId

										) throws AimsException, HibernateException
	{
		
		Session session = null;
        Transaction tx = null;
        int delCount = 0;
		byte[] buffer = new byte[1];
    	buffer[0] = 1;
    	long bytesWrote = 0;
		boolean spotLightDocPresent = false; 				
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
           
			AimsAllianceSpotlight aimsSpotLight = (AimsAllianceSpotlight) session.load
						     							(AimsAllianceSpotlight.class, spotLightId);
			
			aimsSpotLight.setSpotLightTypeId(spotLightTypeId);
            aimsSpotLight.setSpotlightName(spotLightName);
			aimsSpotLight.setSpotlightDesc(spotLightDescription);			
			aimsSpotLight.setStatus(AimsConstants.NEW_CASE_STUDY);	
			aimsSpotLight.setAllianceId(allianceId);
			aimsSpotLight.setCreatedDate(new Date());
			aimsSpotLight.setCreatedBy(currUserId);

			if ((spotLightDoc!=null) && (spotLightDoc.getFileSize()>0))
			{
				aimsSpotLight.setSpotlightFileFileName(spotLightDoc.getFileName());
				aimsSpotLight.setSpotlightFileContentType(spotLightDoc.getContentType());
				aimsSpotLight.setSpotlightFile(Hibernate.createBlob(buffer));
				spotLightDocPresent = true;
			}

            session.update(aimsSpotLight);

			session.flush();
			session.refresh(aimsSpotLight, LockMode.UPGRADE);

			if (spotLightDocPresent) 
			{
				bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsSpotLight.getSpotlightFile(), 
											spotLightDoc.getInputStream());
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
			if (spotLightDocPresent) 
			{
				spotLightDoc.destroy();
			}
		 }
	}


  /**
	*  This static method creates a new spot light.
	*/
	public static void CreateSpotLight  
									   (			
											Long		spotLightTypeId,
											String 		spotLightName,
											String 		spotLightDescription,											
											FormFile 	spotLightDoc,   
											String      currUserId,
											Long        allianceId

										) throws AimsException, HibernateException
	{
		
		Session session = null;
        Transaction tx = null;
        int delCount = 0;
		byte[] buffer = new byte[1];
    	buffer[0] = 1;
    	long bytesWrote = 0;
		boolean spotLightDocPresent = false; 				
		
		try
		{

			log.debug("IN AllianceSpotlightManager spotLightTypeId : " +  spotLightTypeId );
			session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
           
			AimsAllianceSpotlight aimsSpotLight = new AimsAllianceSpotlight();
			
			aimsSpotLight.setSpotLightTypeId(spotLightTypeId);
            aimsSpotLight.setSpotlightName(spotLightName);
			aimsSpotLight.setSpotlightDesc(spotLightDescription);			
			aimsSpotLight.setStatus(AimsConstants.NEW_CASE_STUDY);	
			aimsSpotLight.setAllianceId(allianceId);
			aimsSpotLight.setCreatedDate(new Date());
			aimsSpotLight.setCreatedBy(currUserId);

			if ((spotLightDoc!=null) && (spotLightDoc.getFileSize()>0))
			{
				aimsSpotLight.setSpotlightFileFileName(spotLightDoc.getFileName());
				aimsSpotLight.setSpotlightFileContentType(spotLightDoc.getContentType());
				aimsSpotLight.setSpotlightFile(Hibernate.createBlob(buffer));
				spotLightDocPresent = true;
			}

            session.save(aimsSpotLight);

			session.flush();
			session.refresh(aimsSpotLight, LockMode.UPGRADE);

			if (spotLightDocPresent) 
			{
				bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsSpotLight.getSpotlightFile(), 
											spotLightDoc.getInputStream());
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
			if (spotLightDocPresent) 
			{
				spotLightDoc.destroy();
			}


		 }
	}

  /**
   *  This static method gets the blob resource for a given spotlight.
   */
  public static Object [] getSpotLightResource (String resourceName, Long pkId, String user_type) throws HibernateException
  { 	
    Session session = null;	
	Collection collection = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Object [] resourceValues = null;

    try
    { 	
		
			queryStringBuffer.append("select ")	
							 .append("		objectName.").append(resourceName).append(", ")
							 .append("		objectName.").append(resourceName).append("FileName, ")
							 .append("		objectName.").append(resourceName).append("ContentType ")
							 .append("from ")						
							 .append("		com.netpace.aims.model.alliance.AimsAllianceSpotlight objectName ")
							 .append("where ")
							 .append("		objectName.spotlightId = :pkId ");
	
		
		
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