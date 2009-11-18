package com.netpace.aims.bo.contracts;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;
import net.sf.hibernate.HibernateException;

import com.netpace.aims.model.*;
import com.netpace.aims.model.contracts.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.security.*;
import com.netpace.aims.controller.alliance.*;
import com.netpace.aims.controller.contracts.*;
import com.netpace.aims.util.*;
import com.netpace.aims.bo.core.*;

import com.netpace.aims.controller.contracts.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import oracle.sql.BLOB;



 
/**
 * This class is responsible for getting the BO for amendments.
 * It has static methods for getting, updating, deleting the amendments.
 * @author Rizwan Qazi
 */
public class AmendmentsManager
{

	static Logger log = Logger.getLogger(AmendmentsManager.class.getName());


  /**
   *  This static method gets the alliance details of the current contract   
   */
  public static Collection getAmendmentContracts (Long amendmentId) throws HibernateException
  {    
	Collection collection = null;
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
    ContractForm contractForm = null;
    Collection Contracts = new ArrayList();


    try
    { 
		
	queryStringBuffer.append("select distinct ")
					 .append("		contract.contractId, ")
					 .append("		contract.contractTitle, ")
					 .append("		contract.version, ")
					 .append("		contract.documentFileName,  ")  
					 .append("		contract.status  ")
					 .append("from ")
					 .append("		com.netpace.aims.model.core.AimsContract contract, ")
					 .append("		com.netpace.aims.model.contracts.AimsContractAmendment contractAmendment ")
					 .append("where ")
					 .append("		contractAmendment.amendment.amendmentId = :amendmentId  ")
					 .append("		and contractAmendment.contractId = contract.contractId  ")					
					 .append("order by ")
					 .append("		contract.contractTitle ");
		

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), amendmentId,  new LongType());

		log.debug("No of getContractAlliances records returned: " + collection.size() ); 

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 contractForm = new ContractForm();

			 userValues  = (Object []) iter.next();

             contractForm.setContractId( ((Long) userValues[0]).toString());
             contractForm.setContractTitle( (String) userValues[1]);
             contractForm.setContractVersion( (String) userValues[2]);
             contractForm.setContractDocumentFileName( (String) userValues[3]);
             contractForm.setContractStatus(contractForm.getContractStatus((String) userValues[4]));
           			 
			 Contracts.add(contractForm);
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

    return Contracts;
  }

  /**
   *  This static method gets the amendment details in the database for the current contract
   */
  public static Collection getAllianceAvailableAmendmentDetails (Long contractId) throws HibernateException
  {    
	Collection collection = null;	
    Collection Amendments = new ArrayList();
    Session session = null;	
	StringBuffer queryStringBuffer = new StringBuffer();
    AmendmentForm amendmentForm = null;
    Object [] userValues = null;

    try
    { 
		
	queryStringBuffer.append("select  ")
					 .append("		amendment.amendmentId, ")
					 .append("		amendment.amendmentTitle, ")
					 .append("		amendment.version, ")
					 .append("		amendment.documentFileName,  ")  
					 .append("		amendment.status,  ")
					 .append("		amendment.ifNegotiable,  ")
					 .append("		amendment.comments,  ")
					 .append("		amendment.expiryDate, ")
					 .append("		platform.platformId, ")
					 .append("		platform.platformName ")
					 .append("from ")
					 .append("		com.netpace.aims.model.contracts.AimsAmendment amendment, ")		
					 .append("		com.netpace.aims.model.core.AimsPlatform platform ")
					 .append("where ")
					 .append("		amendment.platformId = platform.platformId (+) ")	
                     .append("		and amendment.amendmentId not in  ")	
                     .append("		(  ")
	                 .append("          select  ")
					 .append("		        contractAmendment.amendment.amendmentId ")
					 .append("          from ")
					 .append("		        com.netpace.aims.model.contracts.AimsContractAmendment contractAmendment ")
					 .append("          where ")
					 .append("		        contractAmendment.contractId = :contractId  ")
                     .append("		)  ")
					 .append("order by ")
					 .append("		amendment.amendmentTitle ");


		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), contractId,  new LongType());

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 amendmentForm = new AmendmentForm();

			 userValues  = (Object []) iter.next();

			 amendmentForm.setAmendmentId( ((Long) userValues[0]).toString());
			 amendmentForm.setAmendmentTitle((String) userValues[1]);
			 amendmentForm.setAmendmentVersion((String) userValues[2]);
			 amendmentForm.setAmendmentDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, (String) userValues[3]));	
			 amendmentForm.setAmendmentStatus(amendmentForm.getAmendmentStatus((String) userValues[4]));
			 amendmentForm.setIfAmendmentNegotiable((String) userValues[5]);
			 amendmentForm.setComments((String) userValues[6]);
			 amendmentForm.setAmendmentExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
			 amendmentForm.setPlatformId( StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));
             amendmentForm.setPlatformName((String) userValues[9]);

			 Amendments.add(amendmentForm);
	 
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

    return Amendments;
  } 

 
  /**
   *  This static method gets the amendmentIds for the current alliance
   */
  public static Collection getAllianceAssignedAmendmentIds (Long allianceId, Long contractId) throws HibernateException
  {    
	Collection collection = null;	
    Session session = null;	
	StringBuffer queryStringBuffer = new StringBuffer();
	Object [] parameterValues = null;
	Type [] parameterTypes = null;


    try
    { 
		
	queryStringBuffer.append("select distinct ")
					 .append("		allianceContractAmendment.amendment.amendmentId ")
					 .append("from ")
					 .append("		com.netpace.aims.model.contracts.AimsAllianceContractAmendment allianceContractAmendment, ")	
					 .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract ")        
					 .append("where ")
					 .append("		allianceContract.allianceId = :allianceId ")
					 .append("		and allianceContract.contractId = :contractId ")
					 .append("		and allianceContract.allianceContractId = allianceContractAmendment.allianceContractId ");

    parameterValues = new Object [2];
    parameterTypes =  new Type [2]; 
    
    parameterValues[0] = allianceId;
    parameterValues[1] = contractId;

    parameterTypes[0] = new LongType();
    parameterTypes[1] = new LongType();	

	session = DBHelper.getInstance().getSession(); 

    collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);
      
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
   *  This static method gets the amendment details in the database for the current contract
   */
  public static Collection getAllianceContractAmendmentDetails (Long allianceId, Long contractId) throws HibernateException
  {    
	Collection collection = null;	
    Collection Amendments = new ArrayList();
    Session session = null;	
	StringBuffer queryStringBuffer = new StringBuffer();
    AmendmentForm amendmentForm = null;
    Object [] userValues = null;
    Object [] parameterValues = null;
	Type [] parameterTypes = null;

    try
    { 

    parameterValues = new Object [2];
    parameterTypes =  new Type [2]; 
    
    parameterValues[0] = allianceId;
    parameterValues[1] = contractId;

    parameterTypes[0] = new LongType();
    parameterTypes[1] = new LongType();	
		
	queryStringBuffer.append("select distinct ")
					 .append("		amendment.amendmentId, ")
					 .append("		amendment.amendmentTitle, ")
					 .append("		amendment.version, ")
					 .append("		amendment.documentFileName,  ")  
					 .append("		amendment.status,  ")
					 .append("		amendment.ifNegotiable,  ")
					 .append("		amendment.comments,  ")
					 .append("		amendment.expiryDate ")
					 .append("from ")
					 .append("		com.netpace.aims.model.contracts.AimsAmendment amendment, ")	
					 .append("		com.netpace.aims.model.contracts.AimsAllianceContractAmendment allContAmendment, ")
					 .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract, ")
					 .append("where ")
					 .append("		 allianceContract.allianceId = :allianceId ")
					 .append("		 and allianceContract.contractId = :contractId ")
                     .append("		 and allContAmendment.allianceContractId = allianceContract.allianceContractId  ")	
                     .append("		 and allContAmendment.amendment.amendmentId = amendment.amendmentId  ")
					 .append("order by ")
					 .append("		amendment.amendmentTitle ");


		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 amendmentForm = new AmendmentForm();

			 userValues  = (Object []) iter.next();

			 amendmentForm.setAmendmentId( ((Long) userValues[0]).toString());
			 amendmentForm.setAmendmentTitle((String) userValues[1]);
			 amendmentForm.setAmendmentVersion((String) userValues[2]);
			 amendmentForm.setAmendmentDocumentFileName((String) userValues[3]);	
			 amendmentForm.setAmendmentStatus(amendmentForm.getAmendmentStatus((String) userValues[4]));
			 amendmentForm.setIfAmendmentNegotiable((String) userValues[5]);
			 amendmentForm.setComments((String) userValues[6]);
			 amendmentForm.setAmendmentExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));


			 Amendments.add(amendmentForm);
	 
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

    return Amendments;
  } 

}  