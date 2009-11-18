package com.netpace.aims.bo.contracts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import oracle.sql.BLOB;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.controller.alliance.AllianceForm;
import com.netpace.aims.controller.contracts.AmendmentForm;
import com.netpace.aims.controller.contracts.ContractForm;
import com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.contracts.AimsAmendment;
import com.netpace.aims.model.contracts.AimsContractAmendment;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.DBUtils;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;


/**
 * This class is responsible for getting the BO for contracts.
 * It has static methods for getting, updating, deleting the contracts.
 * @author Rizwan Qazi
 */
public class ContractsManager
{

    static Logger log = Logger.getLogger(ContractsManager.class.getName());


  /**
   *  This static method gets the contract details in the database
   */
  public static Collection getContracts (String user_type) throws HibernateException
  {
    Collection collection = null;
    Collection Contracts = new ArrayList();
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    ContractForm contractForm = null;

    try
    {

    queryStringBuffer.append("select  ")
                     .append("		contract.contractId, ")
                     .append("		contract.contractTitle, ")
                     .append("		contract.version, ")
                     .append("		contract.documentFileName,  ")
                     .append("		contract.status,  ")
                     .append("		contract.ifNegotiable,  ")
                     .append("		contract.comments,  ")
                     .append("		contract.expiryDate, ")
                     .append("		platform.platformId, ")
                     .append("		platform.platformName ")
                     .append("from ")
                     .append("		com.netpace.aims.model.core.AimsContract contract, ")
                     .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                     .append("where ")
                     .append("		contract.platformId = platform.platformId (+) ")
                     .append("order by ")
                     .append("		contract.contractTitle ");


        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString());

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
             contractForm = new ContractForm();

             userValues  = (Object []) iter.next();

             contractForm.setContractId( ((Long) userValues[0]).toString());
             contractForm.setContractTitle((String) userValues[1]);
             contractForm.setContractVersion((String) userValues[2]);
             contractForm.setContractDocumentFileName((String) userValues[3]);
             contractForm.setContractStatus(contractForm.getContractStatus((String) userValues[4]));
             contractForm.setIfContractNegotiable((String) userValues[5]);
             contractForm.setComments((String) userValues[6]);
             contractForm.setContractExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
             contractForm.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));
             contractForm.setPlatformName(StringFuncs.NullValueReplacement((String) userValues[9]));

             Contracts.add(contractForm);

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

    return Contracts;
  }

  /**
   *  This static method gets the contract details in the database
   */
  public static Collection getContracts(String user_type,
                                        int PAGE_LENGTH, int pageNo,
                                        String sortBy, String sortOrder,
                                        String[] statuses, String search_expression)
            throws HibernateException {
        log.debug("ContractsManager.getContracts Start:");
        Collection collection = null;
        Collection Contracts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        ContractForm contractForm = null;

        try {

            queryStringBuffer
                    .append("select  ")
                    .append("		contract.contractId, ")
                    .append("		contract.contractTitle, ")
                    .append("		contract.version, ")
                    .append("		contract.documentFileName,  ")
                    .append("		contract.status,  ")
                    .append("		contract.ifNegotiable,  ")
                    .append("		contract.comments,  ")
                    .append("		contract.expiryDate, ")
                    .append("		contract.isClickThroughContract, ")
                    .append("		platform.platformId, ")
                    .append("		platform.platformName ")
                    .append("from ")
                    .append("		com.netpace.aims.model.core.AimsContract contract, ")
                    .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                    .append("where ").append(
                            "		contract.platformId = platform.platformId (+) ");
            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
                queryStringBuffer.append("and  ").append("      contract.status in (:statusId) ");
            }
            if (search_expression.length() > 0) {
                queryStringBuffer.append(search_expression);
            }
            if (sortBy.indexOf("Date")!=-1){
                queryStringBuffer.append("order by ").append(sortBy).append(" ");
            }
            else {
                queryStringBuffer.append("order by ").append("lower("+sortBy+") ");
            }
            queryStringBuffer
                    .append(sortOrder);

            log.debug("query: "+queryStringBuffer.toString());

            session = DBHelper.getInstance().getSession();

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (pageNo - 1));
            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
                query.setParameterList("statusId", statuses);
            }
            collection = query.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();) {
                contractForm = new ContractForm();
                userValues = (Object[]) iter.next();
                contractForm.setContractId(((Long) userValues[0]).toString());                
                contractForm.setContractTitle((String)userValues[1]);
                contractForm.setContractVersion((String) userValues[2]);
                contractForm.setContractDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String) userValues[3]));//max 15 chars
                contractForm.setContractStatus(contractForm.getContractStatus((String) userValues[4]));
                contractForm.setIfContractNegotiable((String) userValues[5]);
                contractForm.setComments((String) userValues[6]);
                contractForm.setContractExpiryDate(Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
                contractForm.setClickThroughContract((String) userValues[8]);
                contractForm.setPlatformId(StringFuncs.initializeLongGetParam(((Long) userValues[9]), null));
                contractForm.setPlatformName(StringFuncs.NullValueReplacement((String) userValues[10]));
                Contracts.add(contractForm);
            }
            log.debug("ContractsManager.getContracts: No of records returned= " + collection.size());
            log.debug("ContractsManager.getContracts End:");
        } catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }

        return Contracts;
    }
  /**
   *  This static method gets the count for the contracts
   */
  public static int getContractsCount (String[] statuses, String search_expression)
                                    throws HibernateException
  {
    int rows = 0;
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    List list = new ArrayList();

    try
    {

    queryStringBuffer.append("select  ")
                     .append("		count(*) ")
                     .append("from ")
                     .append("		com.netpace.aims.model.core.AimsContract contract, ")
                     .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                     .append("where ").append(
                            "		contract.platformId = platform.platformId (+) ");
    if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
        queryStringBuffer.append("  and contract.status in (:statusId) ");
    }
    if (search_expression.length() > 0) {
        queryStringBuffer.append(search_expression);
    }

        session = DBHelper.getInstance().getSession();
        Query query = session.createQuery(queryStringBuffer.toString());
        if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
            query.setParameterList("statusId", statuses);
        }
        list = query.list();
        if (list!=null && list.size()>0){
            rows=new Integer(list.get(0).toString()).intValue();
        }
//        rows = ( (Integer) session.iterate(queryStringBuffer.toString()).next()	).intValue();
        log.debug("ContractsManager.getContractsCount: rows= "+rows);
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

    return rows;
  }



  /**
   *  This static method gets the amendment details in the database
   */
  public static Collection getAmendments () throws HibernateException
  {
    Collection collection = null;
    Collection Amendments = new ArrayList();
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    AmendmentForm amendmentForm = null;

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
                     .append("		platform.platformId ")
                     .append("from ")
                     .append("		com.netpace.aims.model.contracts.AimsAmendment amendment, ")
                     .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                     .append("where ")
                     .append("		amendment.platformId = platform.platformId (+) ")
                     .append("order by ")
                     .append("		amendment.amendmentTitle ");

        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString());

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
             amendmentForm = new AmendmentForm();

             userValues  = (Object []) iter.next();

             amendmentForm.setAmendmentId( ((Long) userValues[0]).toString());
             amendmentForm.setAmendmentTitle((String) userValues[1]);
             amendmentForm.setAmendmentVersion((String) userValues[2]);
             amendmentForm.setAmendmentDocumentFileName((String) userValues[3]);
             amendmentForm.setAmendmentStatus(AimsUtils.getContractStatus((String) userValues[4]));
             amendmentForm.setIfAmendmentNegotiable((String) userValues[5]);
             amendmentForm.setComments((String) userValues[6]);
             amendmentForm.setAmendmentExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
             amendmentForm.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));

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
   *  This static method gets the amendment details in the database
   */
  public static Collection getAmendments (int PAGE_LENGTH, int pageNo) throws HibernateException
  {
    Collection collection = null;
    Collection Amendments = new ArrayList();
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    AmendmentForm amendmentForm = null;

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
                     .append("		platform.platformId ")
                     .append("from ")
                     .append("		com.netpace.aims.model.contracts.AimsAmendment amendment, ")
                     .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                     .append("where ")
                     .append("		amendment.platformId = platform.platformId (+) ")
                     .append("order by ")
                     .append("		amendment.amendmentTitle ");

        session = DBHelper.getInstance().getSession();
        Query	query	=	session.createQuery(queryStringBuffer.toString());
        query.setMaxResults(PAGE_LENGTH);
        query.setFirstResult(PAGE_LENGTH * (pageNo-1));
        collection = query.list();

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
             amendmentForm = new AmendmentForm();

             userValues  = (Object []) iter.next();

             amendmentForm.setAmendmentId( ((Long) userValues[0]).toString());
             amendmentForm.setAmendmentTitle((String) userValues[1]);
             amendmentForm.setAmendmentVersion((String) userValues[2]);
             amendmentForm.setAmendmentDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, (String) userValues[3]));
             amendmentForm.setAmendmentStatus(AimsUtils.getContractStatus((String) userValues[4]));
             amendmentForm.setIfAmendmentNegotiable((String) userValues[5]);
             amendmentForm.setComments((String) userValues[6]);
             amendmentForm.setAmendmentExpiryDate(  Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
             amendmentForm.setPlatformId(  StringFuncs.initializeLongGetParam( ((Long) userValues[8]),null ));

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
   *  This static method gets the contract details in the database
   */
  public static int getAmendmentCount () throws HibernateException
  {
    int rows = 0;
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();


    try
    {

    queryStringBuffer.append("select  ")
                     .append("		count(*) ")
                     .append("from ")
                     .append("		com.netpace.aims.model.contracts.AimsAmendment amendment ");


        session = DBHelper.getInstance().getSession();
        rows = ( (Integer) session.iterate(queryStringBuffer.toString()).next()	).intValue();
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

    return rows;
  }
  /**
   *  This static method gets the contract details in the database for the current amendment
   */
  public static Collection getContract (Long contractId) throws HibernateException
  {
    Collection collection = null;
    Session session = null;
    StringBuffer queryStringBuffer = new StringBuffer();

    try
    {

    queryStringBuffer.append("select  ")
                     .append("		contract.contractId, ")
                     .append("		contract.contractTitle, ")
                     .append("		contract.version, ")
                     .append("		contract.documentFileName,  ")
                     .append("		contract.status,  ")
                     .append("		contract.ifNegotiable,  ")
                     .append("		contract.comments,  ")
                     .append("		contract.expiryDate, ")
                     .append("		platform.platformId, ")
                     .append("		platform.platformName, ")
                     .append("		contract.htmlFileFileName, ")
                     .append("		contract.isClickThroughContract, ")
                     .append("		contract.isBoboContract ")
                     .append("from ")
                     .append("		com.netpace.aims.model.core.AimsContract contract, ")
                     .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                     .append("where ")
                     .append("		contract.contractId = :contractId ")
                     .append("		and contract.platformId = platform.platformId (+) ")
                     .append("order by ")
                     .append("		contract.contractTitle ");

        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), contractId,  new LongType());

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
     * This method returns AimsContract object
     * @param contract_id
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static AimsContract getAimsContract(Long contract_id) throws HibernateException, Exception{
        AimsContract aimsContract = null;
        AimsPlatform aimsPlatform = null;
        Object [] userValues = null;
        Collection contractValues = null;

        try{
            contractValues = ContractsManager.getContract(contract_id);
            if(contractValues!=null && contractValues.size()>0) {
                for (Iterator iter = contractValues.iterator(); iter.hasNext();) {
                    userValues  = (Object []) iter.next();

                    aimsContract = new AimsContract();
                    aimsPlatform = new AimsPlatform();

                    aimsContract.setContractId( ((Long) userValues[0]));
                    aimsContract.setContractTitle((String) userValues[1]);
                    aimsContract.setVersion((String) userValues[2]);
                    aimsContract.setDocumentFileName((String) userValues[3]);
                    aimsContract.setStatus((String) userValues[4]);
                    aimsContract.setIfNegotiable((String) userValues[5]);
                    aimsContract.setComments((String) userValues[6]);
                    aimsContract.setExpiryDate((Date) userValues[7]);

                    aimsContract.setPlatformId((Long) userValues[8]);
                    aimsPlatform.setPlatformId((Long) userValues[8]);
                    aimsPlatform.setPlatformName((String) userValues[9]);
                    aimsContract.setAimsPlatform(aimsPlatform);

                    aimsContract.setHtmlFileFileName(StringFuncs.NullValueReplacement((String) userValues[10]));
                    aimsContract.setIsClickThroughContract((String) userValues[11]);
                }
            }
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        return aimsContract;
    }//end getAimsContract

  /**
   *  This static method gets the amendment details in the database for the current amendment
   */
  public static Collection getAmendment (Long amendmentId) throws HibernateException
  {
    Collection collection = null;
    Session session = null;
    StringBuffer queryStringBuffer = new StringBuffer();

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
                     .append("		amendment.platformId ")
                     .append("from ")
                     .append("		com.netpace.aims.model.contracts.AimsAmendment amendment ")
                     .append("where ")
                     .append("		amendment.amendmentId = :amendmentId ")
                     .append("order by ")
                     .append("		amendment.amendmentTitle ");

        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), amendmentId,  new LongType());

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
   *  This static method gets the amendmentIds for the current contract
   */
  public static Collection getContractAmendmentIds (Long contractId) throws HibernateException
  {
    Collection collection = null;
    Session session = null;
    StringBuffer queryStringBuffer = new StringBuffer();

    try
    {

    queryStringBuffer.append("select  ")
                     .append("		contractAmendment.amendment.amendmentId ")
                     .append("from ")
                     .append("		com.netpace.aims.model.contracts.AimsContractAmendment contractAmendment ")
                     .append("where ")
                     .append("		contractAmendment.contractId = :contractId ");

        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), contractId,  new LongType());

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
  public static Collection getContractAmendmentDetails (Long contractId) throws HibernateException
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
                     .append("		com.netpace.aims.model.contracts.AimsContractAmendment contractAmendment, ")
                     .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                     .append("where ")
                     .append("		contractAmendment.contractId = :contractId ")
                     .append("		and amendment.amendmentId = contractAmendment.amendment.amendmentId ")
                     .append("		and amendment.platformId = platform.platformId (+) ")
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
             amendmentForm.setAmendmentDocumentFileName((String) userValues[3]);
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
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
  public static Object [] getResource (String resourceName, Long pkId, String user_type) throws HibernateException
  {
    Session session = null;
    Collection collection = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    Object [] resourceValues = null;

    try
    {

            queryStringBuffer.append("select ");

        if (resourceName.equalsIgnoreCase("document"))
        {
            queryStringBuffer.append("		objectName.").append(resourceName).append(", ")
                             .append("		objectName.").append(resourceName).append("FileName, ")
                             .append("		objectName.").append(resourceName).append("ContentType ")
                             .append("from ")
                             .append("		com.netpace.aims.model.contracts.AimsAmendment objectName ")
                             .append("where ")
                             .append("		objectName.amendmentId = :pkId ");
        }


        //log.debug("queryStringBuffer:getAllianceResource " + queryStringBuffer);

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
    /**
    *  This static method updates a given contract.
    */
    public static void createUpdateContracts  (
                                                    Long 		contractId,
                                                    String 		contractTitle,
                                                    String 		contractVersion,
                                                    String 		contractStatus,
                                                    String 		contractExpiryDate,
                                                    Long	    contractDocumentTempFileId,
                                                    Long	    contractHtmlDocumentTempFileId,
                                                    String      clickThroughContract,
                                                    String 		ifContractNegotiable,
                                                    String 		comments,
                                                    Long 		platformId,
                                                    String []	amendmentIds,
                                                    String      amendmentTitle,
                                                    String      amendmentVersion,
                                                    String      amendmentStatus,
                                                    String      amendmentExpiryDate,
                                                    FormFile    amendmentDocument,
                                                    String      amendmentComments,
                                                    String      isBoboContract,
													String      currUserName
												) throws AimsException, HibernateException
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
        AimsContract aimsContract = null;
        AimsContractAmendment aimsContractAmendment = null;
        AimsAmendment aimsAmendment = null;

        String CONTRACT_DOCUMENT_BLOB_DB_INFO[] = { "document", "aims_contracts", "contract_id" };
        String CONTRACT_HTML_FILE__BLOB_DB_INFO[] = { "html_file", "aims_contracts", "contract_id" };

        try
        {

            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();

            tx = session.beginTransaction();

            if (contractId.intValue() == 0)
            {
                aimsContract = new AimsContract();
                aimsContract.setCreatedBy(currUserName);
                aimsContract.setCreatedDate(new Date());
            }
            else
            {
                aimsContract = (AimsContract) session.load(AimsContract.class, contractId);
            }

            aimsContract.setContractTitle(contractTitle);
            aimsContract.setVersion(contractVersion);
            aimsContract.setStatus(contractStatus);
            aimsContract.setExpiryDate(Utility.convertToDate(contractExpiryDate, AimsConstants.DATE_FORMAT));
            //aimsContract.setIfNegotiable(ifContractNegotiable);
            aimsContract.setComments(comments);
            
            if (platformId.equals(AimsConstants.ENTERPRISE_PLATFORM_ID))
            {
            	if(isBoboContract!=null)
            	{
            		if(isBoboContract.equals("Y"))
            			aimsContract.setIsBoboContract("Y");
            		else
            			aimsContract.setIsBoboContract("N");
            	}
            	else
            		aimsContract.setIsBoboContract("N");	
            }
			if (platformId.intValue() == 0)
			{
				aimsContract.setPlatformId(null);
			}
			else
			{
				aimsContract.setPlatformId(platformId);
				delCount = session.delete("from com.netpace.aims.model.contracts.AimsContractAmendment as contractAmendment where contractAmendment.contractId = :contractId",
									contractId, new LongType());
			}

            
            aimsContract.setLastUpdatedBy(currUserName);
            aimsContract.setLastUpdatedDate(new Date());

            aimsContract.setIsClickThroughContract(clickThroughContract);

            if (contractId.intValue() == 0)
            {
                //generate contract ref id
                log.debug("ContractsManager.createUpdateContracts: generating contract ref id... ");
                long contractRefId = DBUtils.generateSequenceId(DBUtils.SEQ_CONTRACT_REF_ID, ConOra);
                if(contractRefId!=-1) {
                    aimsContract.setContractRefId(new Long(contractRefId));
                }

                session.save(aimsContract);
            }
            else
            {
                session.update(aimsContract);
            }

            delCount = session.delete("from com.netpace.aims.model.contracts.AimsContractAmendment as contractAmendment where contractAmendment.contractId = :contractId",
                                    aimsContract.getContractId(), new LongType());

            log.debug("*****************amendmentIds************** " + amendmentIds);

            if (amendmentIds != null)
            {
                for (int i=0; i<amendmentIds.length; i++)
                {
                    log.debug("*****************amendmentIds[i]************** " + amendmentIds[i]);
                    aimsContractAmendment = new AimsContractAmendment();
                    aimsContractAmendment.setContractId(aimsContract.getContractId());
                    aimsContractAmendment.setAmendment((AimsAmendment) session.load(AimsAmendment.class, new Long(amendmentIds[i])));
                    session.save(aimsContractAmendment);
                }
            }
            session.flush();
            session.refresh(aimsContract, LockMode.UPGRADE);


            TempFilesManager.copyImageFromTemp(ConOra, contractDocumentTempFileId, aimsContract.getContractId(), currUserName, CONTRACT_DOCUMENT_BLOB_DB_INFO);
            //contract html
            TempFilesManager.copyImageFromTemp(ConOra, contractHtmlDocumentTempFileId, aimsContract.getContractId(), currUserName, CONTRACT_HTML_FILE__BLOB_DB_INFO);

            session.flush();


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

                    //aimsContractAmendment = new AimsContractAmendment();
                    //aimsContractAmendment.setContractId(aimsContract.getContractId());
                    //aimsContractAmendment.setAmendment((AimsAmendment) session.load(AimsAmendment.class, aimsAmendment.getAmendmentId()));
                    //session.save(aimsContractAmendment);

                    session.flush();
                    session.refresh(aimsAmendment, LockMode.UPGRADE);

                    if (amendmentDocumentPresent)
                        bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsAmendment.getDocument(), amendmentDocument.getInputStream());

            }
            // Add the amendments to the contracts associated with alliances

            statement = ConOra.prepareCall("{ call AIMS_ALLIANCE_CONTRACTS_PKG.ins_alliance_cont_amendments(?,?,?)}");

            statement.setInt(1,contractId.intValue());
            statement.setString(2,StringFuncs.ConvertArrToString(amendmentIds, ","));
            statement.setString(3,currUserName);

            statement.execute();


            tx.commit();
         }

        catch(JDBCException je)
        {
            if (tx!=null)
                tx.rollback();

            String exMessage = je.getMessage();
            AimsException ae = new AimsException ();
            if(exMessage.indexOf("ORA-00001")>0 && exMessage.indexOf("CONTRACTS_CONTRACT_TITLE")>0){
                UniqueConstraintException uni=new UniqueConstraintException();
                uni.setMessageKey("error.ContractForm.contractTitle.exists");
                throw uni;
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

            if (statement != null)
            {
                try
                {
                    statement.close();
                    log.debug("ContractsManager.createUpdateContracts: CALLABLE STATEMENT CLOSED");
                }
                catch (Exception ignore)
                {
                    ignore.printStackTrace();
                }
            }
            session.close();
            log.debug("ContractsManager.createUpdateContracts: SESSION CLOSED");

            if (amendmentDocumentPresent){
                amendmentDocument.destroy();
            }


         }
    }

    /**
    *  This static method creates/updates a given amendment.
    */
    public static void createUpdateAmendments  (
                                                    Long 		amendmentId,
                                                    String 		amendmentTitle,
                                                    String 		amendmentVersion,
                                                    String 		amendmentStatus,
                                                    String 		amendmentExpiryDate,
                                                    FormFile	amendmentDocument,
                                                    String 		comments,
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
        AimsAmendment aimsAmendment = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            if (amendmentId.intValue() == 0)
            {
                aimsAmendment = new AimsAmendment();
                aimsAmendment.setCreatedBy(currUserName);
                aimsAmendment.setCreatedDate(new Date());
            }
            else
            {
                aimsAmendment = (AimsAmendment) session.load(AimsAmendment.class, amendmentId);
            }

            aimsAmendment.setAmendmentTitle(amendmentTitle);
            aimsAmendment.setVersion(amendmentVersion);
            aimsAmendment.setStatus(amendmentStatus);
            aimsAmendment.setExpiryDate(Utility.convertToDate(amendmentExpiryDate, AimsConstants.DATE_FORMAT));
            aimsAmendment.setComments(comments);
            aimsAmendment.setLastUpdatedBy(currUserName);
            aimsAmendment.setLastUpdatedDate(new Date());


            if ((amendmentDocument!=null) && (amendmentDocument.getFileSize()>0))
            {
                aimsAmendment.setDocumentFileName(amendmentDocument.getFileName());
                aimsAmendment.setDocumentContentType(amendmentDocument.getContentType());
                aimsAmendment.setDocument(Hibernate.createBlob(buffer));
                amendmentDocumentPresent = true;
            }

            if (amendmentId.intValue() == 0)
            {
                session.save(aimsAmendment);
            }
            else
            {
                session.update(aimsAmendment);
            }

            session.flush();
            session.refresh(aimsAmendment, LockMode.UPGRADE);

            if (amendmentDocumentPresent)
                bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsAmendment.getDocument(), amendmentDocument.getInputStream());

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
            if (amendmentDocumentPresent)
                amendmentDocument.destroy();

         }
    }

  /**
   *  This static method deletes a contract represented by a given contractId (primary_key)
   *  It returns the count (most probably 1) of the number of contracts deleted.
   */
  public static void deleteContract(Long contractId) throws AimsException, HibernateException
  {

    int delCount = 0;
    Session session = null;
    Transaction tx = null;
    try
    {

      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();
      delCount = session.delete("from com.netpace.aims.model.core.AimsContract as contract where contract.contractId = :contractId",
                                    contractId, new LongType());

      tx.commit();

     }
        catch(HibernateException he)
        {
        	//For support of ojdbc14.jar
            //String exMessage = he.getMessage();
        	String exMessage = he.getCause().toString();
        	
        	if ( log.isDebugEnabled() )
        	{
        		log.debug("Exception message e.getMessage() - " + he.getMessage());
        		log.debug("Exception cause e.getCause().toString() - " + he.getCause().toString());        		
        	}
            AimsException ae = new AimsException ();
           
            if (tx!=null)
            {
                tx.rollback();
            }

            if (exMessage.indexOf("violated - child record found") > -1)
            {
                //log.debug("This is inside violated - child record found!");
                ae.setMessageKey("masters.integrity.constraint.violation");
                throw ae;
            } 
            else 
            {
            	log.error(he,he);
                
                throw new HibernateException(he);
            }
        }

        finally
        {
          session.close();
        }

  }

  /**
   *  This static method deletes an amendment represented by a given amendmentId (primary_key)
   *  It returns the count (most probably 1) of the number of amendments deleted.
   */
  public static void deleteAmendment(Long amendmentId) throws AimsException, HibernateException
  {

    int delCount = 0;
    Session session = null;
    Transaction tx = null;
    try
    {

      session = DBHelper.getInstance().getSession();
      tx = session.beginTransaction();
      delCount = session.delete("from com.netpace.aims.model.contracts.AimsAmendment as amendment where amendment.amendmentId = :amendmentId",
                                    amendmentId, new LongType());

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

  /**
   *  This static method gets the alliance details of the current contract
   */
  public static Collection getContractAlliances (Long contractId) throws HibernateException
  {
    Collection collection = null;
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    AllianceForm allianceForm = null;
    Collection Alliances = new ArrayList();


    try
    {

        queryStringBuffer.append("select distinct ")
                         .append("		alliance.allianceId, ")
                         .append("		alliance.companyName, ")
                         .append("		alliance.companyLegalName, ")
                         .append("		alliance.createdDate, ")
                         .append("		alliance.status, ")
                                                 .append("		vzwAccountManager.firstName, ")
                                                 .append("		vzwAccountManager.lastName, ")
                                                 .append("              allianceContract.acceptDeclineDate  ")
                                                 .append("from ")
                         .append("		com.netpace.aims.model.core.AimsAllianc alliance,  ")
                         .append("		com.netpace.aims.model.core.AimsUser user,  ")
                         .append("		com.netpace.aims.model.core.AimsContact vzwAccountManager, ")
                         .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                         .append("where ")
                         .append("		allianceContract.contractId = :contractId ")
                         .append("		and alliance.vzwAccountManager = user.userId (+) ")
                         .append("		and user.aimsContact.contactId = vzwAccountManager.contactId (+) ")
                         .append("		and allianceContract.allianceId = alliance.allianceId ");


        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), contractId,  new LongType());

        log.debug("No of getContractAlliances records returned: " + collection.size() );

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
             allianceForm = new AllianceForm();

             userValues  = (Object []) iter.next();

             allianceForm.setAllianceId( (Long) userValues[0]);
             allianceForm.setCompanyName( (String) userValues[1]);
             allianceForm.setCompanyLegalName( (String) userValues[2]);
             allianceForm.setDateEstablished( (Date) userValues[3]);
             allianceForm.setStatus(allianceForm.getAllianceStatus((String) userValues[4]));
             allianceForm.setVzwAccMgrFirstName( (String) userValues[5]);
             allianceForm.setVzwAccMgrLastName( (String) userValues[6]);
             allianceForm.setAcceptDeclineDate((Date) userValues[7]);

             Alliances.add(allianceForm);
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

    return Alliances;
  }

  
  public static Collection getPlatformsWithAcceptedContracts(Long alliance_id) throws HibernateException
  {
      Collection platforms = new ArrayList();
      Session session = null;
      Object[] userValues = null;
      StringBuffer queryStringBuffer = new StringBuffer();
      Query query = null;
      Iterator iter = null;
      
      try
      {

          queryStringBuffer
              .append("select  ")
              .append("		distinct platform.platformId ")
              .append("from ")
              .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
              .append("		com.netpace.aims.model.core.AimsAllianceContract alliance_contract, ")
              .append("		com.netpace.aims.model.core.AimsContract contract, ")
              .append("		com.netpace.aims.model.core.AimsPlatform platform, ")
              .append("where ")
              .append("		alliance.allianceId = :alliance_id ")
              .append("		and alliance_contract.status = 'ACCEPTED' ")
              .append("		and alliance.allianceId = alliance_contract.allianceId ")
              .append("		and alliance_contract.contractId = contract.contractId ")
              .append("		and contract.platformId = platform.platformId ")
              .append("		and contract.status = :contract_status ");

          session = DBHelper.getInstance().getSession();
          query = session.createQuery(queryStringBuffer.toString());
          query.setLong("alliance_id", alliance_id.longValue());
          query.setString("contract_status", AimsConstants.CONTRACT_STATUS_ACTIVE);

          AimsPlatformVO platformVO; 
          iter = query.iterate();
          if(iter != null) 
          {
              while (iter.hasNext())
              {
            	  platformVO = new AimsPlatformVO((Long) iter.next(), null, null, null);            	  	
            	  	
            	  platforms.add(platformVO);
              }
              //end while
          }

          log.debug("No of records returned: " + platforms.size());

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

      return platforms;
  }//end getPlatformsWithAcceptedContracts
    
  public static Collection getContracts(Long alliance_id, Long platform_id) throws HibernateException
  {
      Collection platforms = new ArrayList();
      Session session = null;
      Object[] userValues = null;
      StringBuffer queryStringBuffer = new StringBuffer();
      Query query = null;
      Iterator iter = null;

      try
      {

          queryStringBuffer
              .append("select  ")
              .append("		distinct platform.platformId ")
              .append("from ")
              .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
              .append("		com.netpace.aims.model.core.AimsAllianceContract alliance_contract, ")
              .append("		com.netpace.aims.model.core.AimsContract contract, ")
              .append("		com.netpace.aims.model.core.AimsPlatform platform ")
              .append("where ")
              .append("		alliance.allianceId = :alliance_id ")
              .append("		and alliance_contract.status = 'ACCEPTED' ")
              .append("		and alliance.allianceId = alliance_contract.allianceId ")
              .append("		and alliance_contract.contractId = contract.contractId ")
              .append("		and contract.platformId = :platform_id ")
              .append("		and contract.status = :contract_status ");


          session = DBHelper.getInstance().getSession();
          query = session.createQuery(queryStringBuffer.toString());
          query.setLong("alliance_id", alliance_id.longValue());
          query.setLong("platform_id", platform_id.longValue());
          query.setString("contract_status", AimsConstants.CONTRACT_STATUS_ACTIVE);

          iter = query.iterate();
          if(iter != null) {
              while (iter.hasNext())
            	  platforms.add((Long) iter.next());
              //end while
          }

          log.debug("No of records returned: " + platforms.size());

      }
      catch (HibernateException e)
      {
          e.printStackTrace();
          log.error(e,e);
          throw e;
      }
      finally
      {
          session.close();
      }

      return platforms;
  }//end getContracts

  public static Collection getClickThroughContracts(Long platform_id) throws HibernateException
  {
      Collection contracts = new ArrayList();
      Session session = null;
      Object[] userValues = null;
      StringBuffer queryStringBuffer = new StringBuffer();
      Query query = null;
      Iterator iter = null;

      try
      {

          queryStringBuffer
              .append("select  ")
              .append("		distinct contract.contractId ")
              .append("from ")
              .append("		com.netpace.aims.model.core.AimsAllianceContract alliance_contract, ")
              .append("		com.netpace.aims.model.core.AimsContract contract ")
              .append("where ")
              .append("		contract.platformId = :platform_id ")
              .append("		and contract.status = :contract_status ")
              .append("		and contract.isClickThroughContract = 'Y' ");

          session = DBHelper.getInstance().getSession();
          query = session.createQuery(queryStringBuffer.toString());
          query.setLong("platform_id", platform_id.longValue());
          query.setString("contract_status", AimsConstants.CONTRACT_STATUS_ACTIVE);
          
          iter = query.iterate();
          if(iter != null) {
              while (iter.hasNext())
            	  contracts.add((Long) iter.next());
              //end while
          }

          log.debug("No of records returned: " + contracts.size());

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

      return contracts;
  }//end getClickThroughContracts

    /**
     * This method returns all contracts of given platform
     * @param platformId
     * @param contractStatus
     * @return
     * @throws HibernateException
     */
    public static Collection getContractsByPlatform(Long platformId, String contractStatus) throws HibernateException {
        Collection contracts = null;
        Session	session	=	null;
        Query query = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        try {
            session = DBHelper.getInstance().getSession();

            queryStringBuffer
                    .append("select     contract ")
                    .append("from ")
                    .append("		    com.netpace.aims.model.core.AimsContract contract ")
                    .append("where ")
                    .append("		    contract.platformId = :platform_id ");
            if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                queryStringBuffer
                    .append("		    and contract.status = :contract_status ");
            }
            queryStringBuffer.append("order by   contract.contractTitle");

            query = session.createQuery(queryStringBuffer.toString());

            query.setLong("platform_id", platformId.longValue());

            if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                query.setString("contract_status", contractStatus);
            }

            contracts = query.list();

            if ( log.isDebugEnabled() ) {
            	log.debug("platform Id : " + platformId);
                log.debug("No. of Contracts : " + contracts.size());
            }

        }
        catch(HibernateException he) {
            log.error(he, he);
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getContractsByPlatform()");
            }
        }
        return contracts;

    }//end getContractsByPlatform
}