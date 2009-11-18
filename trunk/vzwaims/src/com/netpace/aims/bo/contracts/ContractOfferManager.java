package com.netpace.aims.bo.contracts;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.contracts.ContractOfferForm;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.contracts.AimsAllianceContractAmendment;
import com.netpace.aims.model.contracts.AimsAmendment;
import com.netpace.aims.model.contracts.AimsContractAmendment;
import com.netpace.aims.model.core.AimsAllianceContract;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.Type;
import oracle.sql.BLOB;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


 
/**    
 * This class is responsible for getting the BO for contracts.
 * It has static methods for getting, updating, deleting the contracts.
 * @author Rizwan Qazi
 */
public class ContractOfferManager
{

	static Logger log = Logger.getLogger(ContractOfferManager.class.getName());


  /**
   *  This static method returns all available contracts which can be offered to alliance
   */
  public static Collection getAllianceAvailableContracts(Long allianceId, String contractStatus) throws HibernateException {
		Collection Contracts = new ArrayList();
		Session session = null;

		StringBuffer queryStringBuffer = new StringBuffer();

		ContractOfferForm contractForm = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		Connection conOra = null;

        int parameterIndex = 1;

        try {
			queryStringBuffer
					.append("SELECT    c.contract_id,   ")
					.append("		   c.contract_title,")
					.append("		   c.VERSION,")
					.append("		   c.document_file_name,")
					.append("		   c.status,")
					.append("		   c.if_negotiable,")
					.append("		   c.comments,")
					.append("          c.expiry_date, ")
					.append("          (SELECT COUNT (*) FROM aims_contract_amendments a WHERE a.contract_id = c.contract_id) cnt ")
					.append("       FROM        aims_contracts c ")
					.append("       WHERE       ((c.is_click_through_contract IS NULL) OR (c.is_click_through_contract != ?) )");
            if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                queryStringBuffer.append("           and c.status = ? ");
            }
			queryStringBuffer
					.append("       AND (c.contract_id NOT IN ")
					.append("				(SELECT ac.contract_id FROM aims_alliance_contracts ac WHERE (ac.alliance_id = ? )))")
					.append("       ORDER BY c.contract_title");

			session = DBHelper.getInstance().getSession();
			conOra = session.connection();

			pstmt = conOra.prepareStatement(queryStringBuffer.toString());

			log.debug("query: " + queryStringBuffer.toString());
            log.debug("query param is_click_through_contract: "+AimsConstants.YES_CHAR);
            pstmt.setString(parameterIndex++, AimsConstants.YES_CHAR);

            if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                log.debug("query param contractStatus: "+contractStatus);
                pstmt.setString(parameterIndex++, contractStatus);
            }
            log.debug("allianceId: "+allianceId);
            pstmt.setLong(parameterIndex++, allianceId.longValue());

			rst = pstmt.executeQuery();

			if (rst != null) {
				while (rst.next()) {
					contractForm = new ContractOfferForm();

					contractForm.setContractId((String.valueOf(rst.getLong("contract_id"))));
					contractForm.setContractTitle(rst.getString("contract_title"));
					contractForm.setContractVersion(rst.getString("VERSION"));
					contractForm.setContractDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, rst.getString("document_file_name")));
					contractForm.setContractStatus(contractForm.getContractStatus(rst.getString("status")));
					contractForm.setIfContractNegotiable(rst.getString("if_negotiable"));
					contractForm.setComments(rst.getString("comments"));
					contractForm.setContractExpiryDate(Utility.convertToString(rst.getDate("expiry_date"), AimsConstants.DATE_FORMAT));
					contractForm.setNumContractAmendments(String.valueOf(rst.getInt("cnt")));
					Contracts.add(contractForm);

				}
			}

			log.debug("No of records returned: " + Contracts.size());

		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} catch (SQLException sqle) {
			log.error(sqle,sqle);
		} finally {
			try {
				if (pstmt != null) {
					try {
						pstmt.close();
					}catch (Exception ep){
                        log.error(ep, ep);
                    }
                }
                if (rst != null) {
					try {
						rst.close();
					}catch (Exception er){
                        log.error(er, er);
                    }
                }
                session.close();
                log.debug("SESSION closed in ContractOfferManager.getAllianceAvailableContracts");
            }
            catch (Exception e2) {
				log.error(e2,e2);
			}
        }

		return Contracts;
	}//end getAllianceAvailableContracts


  /**
   *  This static method gets the contract details in the database
   */
  public static Long getAllianceContractId (Long allianceId, Long contractId, Session session) throws HibernateException
  {    
	
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
    Long allianceContractId = null;
    Collection collection = null;
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

        queryStringBuffer.append("select  ")
                         .append("		allianceContract.allianceContractId ")
                         .append("from ")
                         .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                         .append("where ")	
                         .append("		allianceContract.allianceId = :allianceId ")
                         .append("		and allianceContract.contractId = :contractId ");

        collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

        for (Iterator iter = collection.iterator(); iter.hasNext();) 
        {
        allianceContractId = (Long) iter.next();
        log.debug("allianceContractId: " + allianceContractId );
        }

	}
	catch(HibernateException e)
	{
		e.printStackTrace();
		throw e;
	}
	
    return allianceContractId;
  } 

	/**
	*  This static method updates a given contract.
	*/
	public static void assignAllianceContracts  (
                                                    Long 		contractId,
                                                    Long  		allianceId,  
                                                    String []   contractAmendmentIds,                                                    
                                                    String      amendmentTitle,
                                                    String      amendmentVersion,
                                                    String      amendmentStatus,
                                                    String      amendmentExpiryDate,
                                                    FormFile    amendmentDocument,                                                            
                                                    String      amendmentComments,        
													String      currUserName
												) throws AimsException, HibernateException, Exception
	{
		
		Session session = null;
        Transaction tx = null;   
		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;  
		int delCount = 0;
		byte[] buffer = new byte[1];
    	buffer[0] = 1;
    	long bytesWrote = 0;
		boolean amendmentDocumentPresent = false; 
		AimsAllianceContract allianceContract = null;
        AimsAllianceContractAmendment allianceContractAmendment = null;
		AimsContract aimsContract = null;
        AimsAmendment aimsAmendment = null; 
        Long allianceContractId = null;
        AimsJournalEntry aimsJournalEntry  = new AimsJournalEntry();
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            
            aimsContract = (AimsContract) session.load(AimsContract.class, contractId);

            allianceContract = new AimsAllianceContract();
            allianceContract.setAllianceId(allianceId);
            allianceContract.setContractId(contractId);
            allianceContract.setVzwContractPresentDate(new Date());
            allianceContract.setContractExpDate(aimsContract.getExpiryDate());
            allianceContract.setStatus(AimsConstants.CONTRACT_OFFERED);
            allianceContract.setCreatedBy(currUserName); 
            allianceContract.setCreatedDate(new Date()); 
		 
		    session.save(allianceContract);
		    session.flush();

            allianceContractId = allianceContract.getAllianceContractId();
            
			ConOra = session.connection();
			statement = ConOra.prepareCall("{ call AIMS_ALLIANCE_CONTRACTS_PKG.ins_cont_level_amend_for_alncs(?,?,?)}");

			statement.setInt(1,contractId.intValue());	
			statement.setInt(2,allianceContractId.intValue());
			statement.setString(3,currUserName);          
        
			statement.execute();            
          
            if ((amendmentDocument!=null) && (amendmentDocument.getFileSize()>0))
                    {		
                    aimsAmendment = new AimsAmendment();
                    aimsAmendment.setCreatedBy(currUserName); 
                    aimsAmendment.setCreatedDate(new Date()); 
                    aimsAmendment.setAmendmentTitle(amendmentTitle);
                    aimsAmendment.setVersion(amendmentVersion);
                    aimsAmendment.setStatus(amendmentStatus);
                    aimsAmendment.setExpiryDate(Utility.convertToDate(amendmentExpiryDate, AimsConstants.DATE_FORMAT));            
                    aimsAmendment.setComments(amendmentComments);        
                    aimsAmendment.setLastUpdatedBy(currUserName); 
                    aimsAmendment.setLastUpdatedDate(new Date()); 		
                    aimsAmendment.setDocumentFileName(amendmentDocument.getFileName());
                    aimsAmendment.setDocumentContentType(amendmentDocument.getContentType());
                    aimsAmendment.setDocument(Hibernate.createBlob(buffer));
                    amendmentDocumentPresent = true;

                    session.save(aimsAmendment); 
                    
                    allianceContractAmendment = new AimsAllianceContractAmendment();
                    allianceContractAmendment.setAllianceContractId(allianceContractId);
                    allianceContractAmendment.setAmendment((AimsAmendment) session.load(AimsAmendment.class, aimsAmendment.getAmendmentId()));				
                    session.save(allianceContractAmendment);

log.debug("***** allianceContractId in ContractOfferManager *****" + allianceContractId);
log.debug("***** amendmentId in ContractOfferManager *****" + aimsAmendment.getAmendmentId());

                    session.flush();
                    session.refresh(aimsAmendment, LockMode.UPGRADE);

                    if (amendmentDocumentPresent) 
                        bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsAmendment.getDocument(), amendmentDocument.getInputStream());
                    
			}
           
            if (contractAmendmentIds != null)
            {
                for (int i=0; i<contractAmendmentIds.length; i++)
                {
                    allianceContractAmendment = new AimsAllianceContractAmendment();
                    allianceContractAmendment.setAllianceContractId(allianceContractId);
                    allianceContractAmendment.setAmendment((AimsAmendment) session.load(AimsAmendment.class, new Long(contractAmendmentIds[i])));				
                    
                    //Added by Adnan Makda.
                    allianceContractAmendment.setVzwAmendmentPresentDate(new Date());
                    allianceContractAmendment.setStatus("O");
                    allianceContractAmendment.setAmendmentExpDate(Utility.convertToDate(amendmentExpiryDate, AimsConstants.DATE_FORMAT));
                    allianceContractAmendment.setCreatedBy(currUserName); 
            				allianceContractAmendment.setCreatedDate(new Date()); 
            				//End of Addition by Adnan Makda                    
                    
                    session.save(allianceContractAmendment);
                }
            }
            
            //Save offered journal entry.
            StringBuffer journalText=new StringBuffer();
            journalText.append("Contract  ")	
						.append("\"").append(aimsContract.getContractTitle()).append("\"").append(" ")
						.append("(Version: ").append(aimsContract.getVersion()).append("),").append(" ")
						.append("Offered by").append(" ")
						.append(currUserName);
            
			aimsJournalEntry.setJournalText(journalText.toString());
			aimsJournalEntry.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE);
			aimsJournalEntry.setCreatedBy("system");
 	        aimsJournalEntry.setCreatedDate(new Date());         	        
 	        aimsJournalEntry.setAimsAllianceId(allianceId);  	
			session.save(aimsJournalEntry);
			
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
                throw ex;				
		}

		 finally
		 {

			session.close();
		 }
	}

	/**
	*  This static method updates a given contract.
	*/
	public static void editAllianceContracts  (
                                                    Long 		contractId,
                                                    Long  		allianceId,  
                                                    String []   contractAmendmentIds,                                                    
                                                    String      amendmentTitle,
                                                    String      amendmentVersion,
                                                    String      amendmentStatus,
                                                    String      amendmentExpiryDate,
                                                    FormFile    amendmentDocument,                                                            
                                                    String      amendmentComments,        
													String      currUserName
												) throws AimsException, HibernateException
	{
		
		Session session = null;
        Transaction tx = null;   
		int delCount = 0;
		byte[] buffer = new byte[1];
    	buffer[0] = 1;
    	long bytesWrote = 0;
		boolean amendmentDocumentPresent = false; 
		AimsAllianceContract allianceContract = null;
        AimsAllianceContractAmendment allianceContractAmendment = null;
		AimsContract aimsContract = null;
        AimsAmendment aimsAmendment = null; 
        Long allianceContractId = null;
        StringBuffer queryStringBuffer = new StringBuffer();
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            allianceContractId = getAllianceContractId(allianceId, contractId, session);
            
            log.debug("***** allianceContractId in ContractOfferManager *****" + allianceContractId);

            queryStringBuffer.append("      from com.netpace.aims.model.contracts.AimsAllianceContractAmendment as allContAmendment where allContAmendment.allianceContractId = :allianceContractId ");            
      
            //delCount = session.delete(queryStringBuffer.toString(), allianceContractId,  new LongType());               

            if (contractAmendmentIds != null)
            {
                for (int i=0; i<contractAmendmentIds.length; i++)
                {
                	
					try 
					{	                	
                    allianceContractAmendment = new AimsAllianceContractAmendment();
                    allianceContractAmendment.setAllianceContractId(allianceContractId);
                    allianceContractAmendment.setAmendment((AimsAmendment) session.load(AimsAmendment.class, new Long(contractAmendmentIds[i])));
					allianceContractAmendment.setVzwAmendmentPresentDate(new Date());
					allianceContractAmendment.setStatus("O");
					// ignore duplicate records					
                    session.save(allianceContractAmendment);
                    session.flush();
					}
					catch (Exception ignore)
					{
						//System.out.println("\n\n\n This is the FIRST ignore exception!!!");
						//ignore.printStackTrace();
						//continue;
					}
                }
            }
       

            if ((amendmentDocument!=null) && (amendmentDocument.getFileSize()>0))
                    {		
                    aimsAmendment = new AimsAmendment();
                    aimsAmendment.setCreatedBy(currUserName); 
                    aimsAmendment.setCreatedDate(new Date()); 
                    aimsAmendment.setAmendmentTitle(amendmentTitle);
                    aimsAmendment.setVersion(amendmentVersion);
                    aimsAmendment.setStatus(amendmentStatus);
                    aimsAmendment.setExpiryDate(Utility.convertToDate(amendmentExpiryDate, AimsConstants.DATE_FORMAT));            
                    aimsAmendment.setComments(amendmentComments);        
                    aimsAmendment.setLastUpdatedBy(currUserName); 
                    aimsAmendment.setLastUpdatedDate(new Date()); 		
                    aimsAmendment.setDocumentFileName(amendmentDocument.getFileName());
                    aimsAmendment.setDocumentContentType(amendmentDocument.getContentType());
                    aimsAmendment.setDocument(Hibernate.createBlob(buffer));
                    amendmentDocumentPresent = true;

                    session.save(aimsAmendment);
                    
                    session.flush();
                    session.refresh(aimsAmendment, LockMode.UPGRADE);

                    if (amendmentDocumentPresent) 
                        bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsAmendment.getDocument(), amendmentDocument.getInputStream());

                    session.update(aimsAmendment);

log.debug("***** allianceContractId in ContractOfferManager *****" + allianceContractId);
log.debug("***** amendmentId in ContractOfferManager *****" + aimsAmendment.getAmendmentId());
					try 
					{
                    allianceContractAmendment = new AimsAllianceContractAmendment();
                    allianceContractAmendment.setAllianceContractId(allianceContractId);
                    allianceContractAmendment.setAmendment((AimsAmendment) session.load(AimsAmendment.class, aimsAmendment.getAmendmentId()));
					allianceContractAmendment.setVzwAmendmentPresentDate(new Date());
                    allianceContractAmendment.setStatus("O");                    					
					session.save(allianceContractAmendment);
					session.flush();
					
					}
					catch (Exception ignore)
					{
												
					}                    			
           

                                        
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
		 }
	}


	/**
	*  This static method updates a given contract.
	*/
	public static void editContractAmendmentStatus (
                                                    Long 		contractId,
                                                    Long  		allianceId,  
                                                    Long        recordId,
                                                    String      docType,
                                                    String      docStatus,
                                                    String      docInitial,
                                                    String      docTitle,
                                                    String      comments,
                                                    Long        currUserId,
													String 		userName
												) throws AimsException, HibernateException, Exception
	{
		
		Session session = null;
        Transaction tx = null;
        Long allianceContractId = null;
        AimsAllianceContractAmendment allianceAmendment = null;
        AimsContractAmendment contractAmendment = null;
        AimsAllianceContract aimsAllianceContract = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Object [] parameterValues = null;
        Type [] parameterTypes = null;
        Collection collection = null;
        AimsContract aimsContract=null;
		AimsAmendment aimsAmendment=null;
		try
		{
		
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            allianceContractId = getAllianceContractId(allianceId, contractId, session);
            
            log.debug("***** allianceContractId in editContractAmendmentStatus *****" + allianceContractId);

            if (docType.equalsIgnoreCase("C"))
            {
                 String contStatus = "";
                 if (docStatus.equalsIgnoreCase("A"))
                 {
                     contStatus = "ACCEPTED";
                 }
                 if (docStatus.equalsIgnoreCase("R"))
                 {
                     contStatus = "REJECTED";
                 }

                 aimsAllianceContract = (AimsAllianceContract) session.load(AimsAllianceContract.class, allianceContractId);

                 aimsAllianceContract.setStatus(contStatus);
                 aimsAllianceContract.setAcceptDeclineUserId(currUserId);
                 aimsAllianceContract.setAcceptDeclineDate(new Date());
                 aimsAllianceContract.setAcceptDeclineSignName(docInitial);
                 aimsAllianceContract.setAcceptDeclineSignTitle(docTitle);

                 if(!StringFuncs.isNullOrEmpty(comments)) {
                     //set comments if not null or empty
                     aimsAllianceContract.setComments(comments);
                 }
            }
       
            if (docType.equalsIgnoreCase("CA"))
            {
                parameterValues = new Object [2];
                parameterTypes =  new Type [2]; 
                
                parameterValues[0] = contractId;
                parameterValues[1] = recordId;
               
                parameterTypes[0] = new LongType();
                parameterTypes[1] = new LongType();    
                
                queryStringBuffer.append("select  ")
                                 .append("		contractAmendment ") 
                                 .append("from  ")         
                                 .append("		com.netpace.aims.model.contracts.AimsContractAmendment contractAmendment ")                         
                                 .append("where ")
                                 .append("		contractAmendment.contractId = :contractId ")	
                                 .append("		and contractAmendment.amendment.amendmentId = :amendmentId "); 
                
               
                collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

                for (Iterator iter = collection.iterator(); iter.hasNext();) 
                {   
                     contractAmendment  = (AimsContractAmendment) iter.next();
                }
                    
                 contractAmendment.setStatus(docStatus);
                 contractAmendment.setAcceptDeclineUserId(currUserId);
                 contractAmendment.setAcceptDeclineDate(new Date());
                 contractAmendment.setAcceptDeclineSignName(docInitial);
                 contractAmendment.setAcceptDeclineSignTitle(docTitle);                
            }

            if (docType.equalsIgnoreCase("AA"))
            {
                parameterValues = new Object [2];
                parameterTypes =  new Type [2]; 
                
                parameterValues[0] = allianceContractId;
                parameterValues[1] = recordId;
               
                parameterTypes[0] = new LongType();
                parameterTypes[1] = new LongType();    
                
                queryStringBuffer.append("select  ")
                                 .append("		allianceAmendment ") 
                                 .append("from  ")         
                                 .append("		com.netpace.aims.model.contracts.AimsAllianceContractAmendment allianceAmendment ")                         
                                 .append("where ")
                                 .append("		allianceAmendment.allianceContractId = :allianceContractId ")	
                                 .append("		and allianceAmendment.amendment.amendmentId = :amendmentId "); 
                
               
                collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

                for (Iterator iter = collection.iterator(); iter.hasNext();) 
                {   
                     allianceAmendment  = (AimsAllianceContractAmendment) iter.next();
                }
                 allianceAmendment.setStatus(docStatus);
                 allianceAmendment.setAcceptDeclineUserId(currUserId);
                 allianceAmendment.setAcceptDeclineDate(new Date());
                 allianceAmendment.setAcceptDeclineSignName(docInitial);
                 allianceAmendment.setAcceptDeclineSignTitle(docTitle);  
            }

            //Save acceptance/rejection journal entry.
            if(docType.equalsIgnoreCase("C") || docType.equalsIgnoreCase("AA")){            
	        	StringBuffer journalText=new StringBuffer();
	        	if (docType.equalsIgnoreCase("AA")) {
	        		aimsAmendment = (AimsAmendment) session.load(AimsAmendment.class, recordId);
	            	journalText.append("Amendment ")	
	            			.append("\"").append(aimsAmendment.getAmendmentTitle()).append("\"").append(" ")
	            			.append("(Version: ").append(aimsAmendment.getVersion()).append("), ").append(" ");
	        		
	        	}
	        	else if (docType.equalsIgnoreCase("C")) {
	        		aimsContract = (AimsContract) session.load(AimsContract.class, contractId);
		        	journalText.append("Contract ")	
							.append("\"").append(aimsContract.getContractTitle()).append("\"").append(" ")
							.append("(Version: ").append(aimsContract.getVersion()).append("), ").append(" ");
	        	}
	        	
	            if (docStatus.equalsIgnoreCase("A")){
	            	journalText.append("Accepted by ").append(userName);
	            }
	            else if (docStatus.equalsIgnoreCase("R")){            	
	            	journalText.append("Rejected by ").append(userName);
	            }
                if(docType.equalsIgnoreCase("C")) {
                    journalText.append(". \nComments: ").append(StringFuncs.NullValueReplacement(comments));
                }

                AimsJournalEntry aimsJournalEntry  = new AimsJournalEntry();
				aimsJournalEntry.setJournalText(journalText.toString());
				aimsJournalEntry.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE);
				aimsJournalEntry.setCreatedBy("system");
	 	        aimsJournalEntry.setCreatedDate(new Date());         	        
	 	        aimsJournalEntry.setAimsAllianceId(allianceId);  	
				session.save(aimsJournalEntry);
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
                throw ex;			
		}

		 finally
		 {

			session.close();
		 }
	}


  /**
   *  This static method deletes a contract represented by a given contractId (primary_key)   
   *  It returns the count (most probably 1) of the number of contracts deleted.
   */
  public static void deleteAllianceContracts(Long allianceId, Long contractId, AimsJournalEntry aimsJournalEntry) throws AimsException, HibernateException
  {
    
    log.debug("The value of alliance_id in deleteAllianceContracts is " + allianceId);
    log.debug("The value of contract_id in deleteAllianceContracts is " + contractId);
	int delCount = 0;
    Session session = null;
    Transaction tx = null;
    StringBuffer queryStringBuffer = new StringBuffer();
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

        session = DBHelper.getInstance().getSession();
        tx = session.beginTransaction();

        queryStringBuffer.append("      from com.netpace.aims.model.contracts.AimsAllianceContractAmendment as allContAmendment where allContAmendment.allianceContractId in ")
                         .append(" (  ")
                         .append("select  ")
                         .append("		allianceContract.allianceContractId ")
                         .append("from ")
                         .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                         .append("where ")	
                         .append("		allianceContract.allianceId = :allianceId ")
                         .append("		and allianceContract.contractId = :contractId ")
                         .append(" )  ");

              
	  
	    delCount = session.delete(queryStringBuffer.toString(), parameterValues, parameterTypes);

        queryStringBuffer = new StringBuffer();

        queryStringBuffer.append("      from com.netpace.aims.model.core.AimsAllianceContract as allianceContract where  ")            
                         .append("		allianceContract.allianceId = :allianceId ")
                         .append("		and allianceContract.contractId = :contractId ");
                     
        delCount = session.delete(queryStringBuffer.toString(), parameterValues, parameterTypes);
        session.save(aimsJournalEntry);
	    tx.commit();      
 
     }
        catch(HibernateException he)
        {
            //log.debug("This is inside the delete of Contracts Manager!");
			String exMessage = he.getMessage();
			AimsException ae = new AimsException ();

           // log.debug("******** exMessage ************ " + exMessage);

            if (tx!=null) 
            {                        
                tx.rollback();
            }

            if (exMessage.indexOf("violated - child record found") > -1) 
            {	
                //log.debug("This is inside violated - child record found!");
                ae.setMessageKey("masters.integrity.constraint.violation");  
                throw ae;
            } else {
                he.printStackTrace();
                throw new HibernateException(he);
            }
        }

		finally
		{
		  session.close();
		}
    
  }

}  