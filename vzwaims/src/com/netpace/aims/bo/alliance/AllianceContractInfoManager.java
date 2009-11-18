package com.netpace.aims.bo.alliance;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.dataaccess.valueobjects.AllianceContractInfoVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsJournalEntry;
import com.netpace.aims.model.core.AimsAllianceContract;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
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

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


/**
 * This class is responsible for getting the BO for alliance contracts.
 * It has static methods for getting, updating, deleting the alliance contracts.
 * @author Rizwan Qazi
 */
public class AllianceContractInfoManager
{

    static Logger log = Logger.getLogger(AllianceContractInfoManager.class.getName());


  /**
   *  This static method gets the contract details in the database which are available
   *  to the current alliance.
   */
  public static Collection getAllianceContractDetails (Long alliance_id, String user_type,
                                                       Long contract_id) throws HibernateException
  {
    Collection collection = null;
    Session session = null;
    Object [] userValues = null;
    StringBuffer queryStringBuffer = new StringBuffer();

    try
    {

        queryStringBuffer.append("select ")
                         .append("		contract.contractId, ")
                         .append("		contract.contractTitle, ")
                         .append("		contract.version, ")
                         .append("		contract.ifNegotiable, ")
                         .append("		contract.status, ")
                         .append("		contract.documentFileName, ")
                         .append("		contract.documentContentType, ")
                         .append("		contract.ifAttachToSoln, ")
                         .append("		platform.platformName, ")
                         .append("		allianceContract.vzwContractPresentDate, ")
                         .append("		allianceContract.status, ")
                         .append("		allianceContract.modifiedContDocFileName, ")
                         .append("		allianceContract.modifiedContDocContentType, ")
                         .append("		allianceContract.vzwStatus, ")
                         .append("		allianceContract.ifNegotiable, ")
                         .append("		allianceContract.acceptDeclineDate, ")
                         .append("		allianceContract.contractEffDate, ")
                         .append("		allianceContract.contractExpDate, ")
                         .append("		acceptDeclineContact.firstName, ")
                         .append("		acceptDeclineContact.lastName, ")
                         .append("		alliance.companyName, ")
                         .append("		allianceContract.allianceContractId ")
                         .append("from ")
                         .append("		com.netpace.aims.model.core.AimsContract contract, ")
                         .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract, ")
                         .append("		com.netpace.aims.model.core.AimsPlatform platform, ")
                         .append("		com.netpace.aims.model.core.AimsUser acceptDeclineUser, ")
                         .append("		com.netpace.aims.model.core.AimsContact acceptDeclineContact, ")
                         .append("		com.netpace.aims.model.core.AimsAllianc alliance ")
                         .append("where ")
                         .append("		allianceContract.contractId = :contract_id ")
                         .append("		and allianceContract.contractId = contract.contractId ")
                         .append("		and allianceContract.allianceId = alliance.allianceId ")
                         .append("		and contract.platformId = platform.platformId ")
                         .append("		and allianceContract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                         .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                         .append("order by  ")
                         .append("		contract.contractTitle  ");


        session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), contract_id,  new LongType());

        log.debug("No of AllianceContractDetails records returned: " + collection.size() );

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
   *  This static method gets the industry focus from an array on Ind Focus ids.
   */
  public static Object [] getAllianceResource (String resourceName, Long pkId, String user_type) throws HibernateException
  {
    Session session = null;
    Collection collection = null;
    StringBuffer queryStringBuffer = new StringBuffer();
    Object [] resourceValues = null;

    try
    {

            queryStringBuffer.append("select ");

        if (resourceName.equalsIgnoreCase("document") || resourceName.equalsIgnoreCase("htmlFile"))
        {
            queryStringBuffer.append("		objectName.").append(resourceName).append(", ")
                             .append("		objectName.").append(resourceName).append("FileName, ")
                             .append("		objectName.").append(resourceName).append("ContentType ")
                             .append("from ")
                             .append("		com.netpace.aims.model.core.AimsContract objectName ")
                             .append("where ")
                             .append("		objectName.contractId = :pkId ");
        }


        if (resourceName.equalsIgnoreCase("modifiedContDoc"))
        {
            queryStringBuffer.append("		objectName.").append(resourceName).append(", ")
                             .append("		objectName.").append(resourceName).append("FileName, ")
                             .append("		objectName.").append(resourceName).append("ContentType ")
                             .append("from ")
                             .append("		com.netpace.aims.model.core.AimsAllianceContract objectName ")
                             .append("where ")
                             .append("		objectName.allianceContractId = :pkId ");
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
    *  This static method updates a given alliance's contract info.
    */
    public static void UpdateAllianceContractInfo
                                                (
                                                    Long 		allianceId,
                                                    Long 		allianceContractId,
                                                    String 		allianceContractStatus,
                                                    FormFile 	modifiedContractDoc,
                                                    Long        currUserId

                                                ) throws AimsException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        int delCount = 0;
        byte[] buffer = new byte[1];
        buffer[0] = 1;
        long bytesWrote = 0;
        boolean modifiedContractDocPresent = false;


        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            AimsAllianceContract allianceContract = (AimsAllianceContract) session.load
                                                         (AimsAllianceContract.class, allianceContractId);

            allianceContract.setStatus(allianceContractStatus);

            if (allianceContractStatus.equalsIgnoreCase(AimsConstants.CONTRACT_ACCEPTED)
                    || allianceContractStatus.equalsIgnoreCase(AimsConstants.CONTRACT_DECLINED))
            {
                allianceContract.setAcceptDeclineUserId(currUserId);
                allianceContract.setAcceptDeclineDate(new Date());
            }

            if ((modifiedContractDoc!=null) && (modifiedContractDoc.getFileSize()>0))
            {
                allianceContract.setModifiedContDocFileName(modifiedContractDoc.getFileName());
                allianceContract.setModifiedContDocContentType(modifiedContractDoc.getContentType());
                allianceContract.setModifiedContDoc(Hibernate.createBlob(buffer));
                modifiedContractDocPresent = true;
            }

            session.update(allianceContract);

            session.flush();
            session.refresh(allianceContract, LockMode.UPGRADE);

            if (modifiedContractDocPresent)
            {
                bytesWrote = LobUtils.writeToOraBlob((BLOB) allianceContract.getModifiedContDoc(), modifiedContractDoc.getInputStream());
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
            if (modifiedContractDocPresent)
            {
                modifiedContractDoc.destroy();
            }


         }
    }
    public static boolean validateContract(Long allianceId, Long contractId)throws HibernateException{
        Session session=null;
        StringBuffer qryStr=new StringBuffer();
        boolean flag=false;
        try {
            session=DBHelper.getInstance().getSession();

            qryStr
                .append("select ac.allianceId from AimsAllianceContract ac")
                .append(" where ac.allianceId = :aId and ac.contractId = :cId ");

            Query query=session.createQuery(qryStr.toString());
            query.setLong("aId", allianceId.longValue());
            query.setLong("cId", contractId.longValue());

            Collection list=query.list();
            if (list!=null && list.size()>0){
                flag=true;
            }
        }
        catch (HibernateException e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }

    public static boolean validateAmendment(Long allianceId, Long amendmentId)throws HibernateException,SQLException{
        Session session=null;
        StringBuffer qryStr=new StringBuffer();
        java.sql.Connection conOra = null;
        java.sql.PreparedStatement statement = null;
        java.sql.ResultSet results = null;
        long value=0;
        boolean flag=false;
        try {
            session=DBHelper.getInstance().getSession();

            qryStr
                .append("SELECT c.alliance_id allianceId")
                .append(" FROM aims_alliance_contract_amends a, aims_alliance_contracts c ")
                .append(" WHERE c.alliance_contract_id = a.alliance_contract_id ")
                .append(" AND a.addendum_id = ? ")
                .append(" AND c.alliance_id = ? ");
            conOra=session.connection();
            statement=conOra.prepareStatement(qryStr.toString());
            statement.setLong(1, amendmentId.longValue());
            statement.setLong(2, allianceId.longValue());
            results=statement.executeQuery();
            while (results.next()) {
                value=results.getLong("allianceId");
            }
            if (value==allianceId.longValue()){
                flag=true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw e;
        }
        catch (HibernateException e){
            e.printStackTrace();
            throw e;
        }
        finally {
            if (results !=null) {
                try {
                    results.close();
                }
                catch(Exception ignore){
                }
            }
            if (statement!=null) {
                try{
                    statement.close();
                }
                catch (Exception ignore){
                }
            }
            if (session !=null){
                session.close();
            }
        }
        return flag;
    }


    /**
     * This method returns AllianceContractInfoVO, which represents alliance contract information
     * @param alliance_id
     * @param contract_id
     * @return
     * @throws HibernateException
     */
    public static AllianceContractInfoVO getAllianceContractInfo(Long alliance_id,
                                                                 Long contract_id) throws HibernateException, Exception {
        Collection collection = null;

        Session session = null;
        Query query = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceContractInfoVO allianceContractInfo = null;
        Long contractId = null;

        try {

            queryStringBuffer
                .append("select  ")
                .append("		contract.contractId, ")
                .append("		contract.contractTitle, ")
                .append("		contract.version, ")
                .append("		contract.documentFileName,  ")
                .append("		contract.isClickThroughContract, ")
                .append("		allianceContract.vzwContractPresentDate, ")
                .append("		allianceContract.status,  ")
                .append("		allianceContract.acceptDeclineDate, ")
                .append("		allianceContract.allianceContractId, ")
                .append("		allianceContract.acceptDeclineSignName, ")
                .append("		allianceContract.acceptDeclineSignTitle, ")
                .append("		allianceContract.contractExpDate, ")
                .append("		allianceContract.comments, ")
                .append("		acceptDeclineContact.firstName, ")
                .append("		acceptDeclineContact.lastName, ")
                .append("		platform.platformName ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsContract contract, ")
                .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract, ")
                .append("		com.netpace.aims.model.core.AimsUser acceptDeclineUser, ")
                .append("		com.netpace.aims.model.core.AimsContact acceptDeclineContact, ")
                .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                .append("where ")
                .append("		allianceContract.allianceId = :alliance_id ")
                .append("		and contract.contractId = :contract_id ")
                .append("		and platform.platformId = contract.platformId ")
                .append("		and allianceContract.contractId = contract.contractId ")
                .append("		and allianceContract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("order by ")
                .append("		contract.contractId ");

            session = DBHelper.getInstance().getSession();
            query = session.createQuery(queryStringBuffer.toString());

            query.setLong("alliance_id", alliance_id.longValue());
            query.setLong("contract_id", contract_id.longValue());

            collection = query.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                allianceContractInfo = new AllianceContractInfoVO();

                userValues = (Object[]) iter.next();

                contractId = (Long) userValues[0];

                allianceContractInfo.setContractId(contractId);
                allianceContractInfo.setContractTitle((String) userValues[1]);
                allianceContractInfo.setContractVersion((String) userValues[2]);
                allianceContractInfo.setDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String) userValues[3]));
                allianceContractInfo.setCompleteDocumentFileName((String) userValues[3]);
                allianceContractInfo.setClickThroughContract((String) userValues[4]);                
                allianceContractInfo.setAllianceContractPresentDate((java.util.Date) userValues[5]);//offer date
                allianceContractInfo.setAllianceContractStatus((String) userValues[6]);
                allianceContractInfo.setAllianceContractAcceptDeclineDate((java.util.Date) userValues[7]);
                allianceContractInfo.setAllianceContractId((Long) userValues[8]);
                allianceContractInfo.setAcceptDeclineSignName((String) userValues[9]);
                allianceContractInfo.setAcceptDeclineSignTitle((String) userValues[10]);
                allianceContractInfo.setAllianceContractExpDate((java.util.Date) userValues[11]);
                allianceContractInfo.setAllianceContractComments((String) userValues[12]);
                //allianceContractInfo.setContractAmendments(getContractAmendments(contractId, session));
                //allianceContractInfo.setAllianceAmendments(getAllianceAmendments(allianceContractId, session));
                allianceContractInfo.setAcceptDeclineFirstName((String) userValues[13]);
                allianceContractInfo.setAcceptDeclineLastName((String) userValues[14]);
                allianceContractInfo.setContractPlatformName((String) userValues[15]);
            }
        }
        catch (HibernateException he) {
            log.error("Hibernate Exception occured in AllianceContractInfoManager.getAimsAllianceContract");
            he.printStackTrace();
            throw he;
        }
        catch (Exception e) {
            log.error("Exception occured in AllianceContractInfoManager.getAimsAllianceContract");
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                log.debug("Session closed in AllianceContractInfoManager.getAimsAllianceContract");
                session.close();
            }
        }

        return allianceContractInfo;
    }

    /**
     * This method returns HTML content saved in HTML document field of contract
     * @param contractId
     * @param currUserType
     * @return
     * @throws Exception
     */
    public static String getAllianceContractHTML(Long contractId, String currUserType) throws SQLException, Exception {
        Object[] resourceValues = null;
        StringBuffer contractHTMLBuffer = new StringBuffer();
        Blob contractHTMLDocument = null;

        String charset = "UTF-8";

        long htmlBlobSize = -1;
        byte [] blobBytes = null;

        //get contract html
        resourceValues = AllianceContractInfoManager.getAllianceResource("htmlFile", contractId, currUserType);
        if(resourceValues!=null) {
            contractHTMLDocument = (Blob)resourceValues[0];
            if(contractHTMLDocument!=null) {
                try {
                    htmlBlobSize = contractHTMLDocument.length();
                    blobBytes = contractHTMLDocument.getBytes(1, (int)htmlBlobSize);
                    log.debug("reading contract html document "+resourceValues[1]+", size= "+htmlBlobSize);
                    contractHTMLBuffer.append(new String(blobBytes, charset)); //read html content in UTF-8 format
                }
                catch(SQLException sqle) {
                    sqle.printStackTrace();
                    throw sqle;
                }
                catch(Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                contractHTMLDocument = null;//set Blob reference to null
            }
        }
        return contractHTMLBuffer.toString();
    }//end getAllianceContractHTML

    /**
     * This method returns all available click through contracts for given alliance
     * if alliance has accepted a contract of a particular platform, then its click through contract will not be visible
     * @param allianceId
     * @return
     * @throws HibernateException
     */
    public static Collection getAvailableClickThroughContracts(Long allianceId,
                                                               String contractStatus,
                                                               String[] filterPlatformValues)
                                                                throws HibernateException, Exception {
        Collection collection = null;
        Collection availableClickThroughContracts = new ArrayList();
        Session session = null;
        Object [] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceContractInfoVO contractInfo = null;
        Query query = null;

        try {

        queryStringBuffer.append("select  distinct")
                         .append("		contract.contractId, ")
                         .append("		contract.contractTitle, ")
                         .append("		contract.version, ")
                         .append("		contract.documentFileName,  ")
                         .append("		contract.expiryDate, ")
                         .append("		platform.platformName ")
                         .append("from ")
                         .append("		com.netpace.aims.model.core.AimsContract contract, ")
                         .append("		com.netpace.aims.model.core.AimsPlatform platform ")
                         .append("where ")
                         .append("		contract.isClickThroughContract = :isClickThroughContract ");
         if(filterPlatformValues!=null && filterPlatformValues.length>0 && filterPlatformValues[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
            queryStringBuffer.append("and  ").append("      contract.platformId in (:platformIds) ");
         }
         if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                        queryStringBuffer.append("		and contract.status = :status ");
         }

            queryStringBuffer.append("		and contract.platformId = platform.platformId (+) ")
                         .append("		and contract.contractId not in ")
                             .append("		( ")
                             .append("          select  ")
                             .append("		        allianceContract.contractId ")
                             .append("          from  ")
                             .append("		        com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                             .append("          where  ")
                             .append("		        allianceContract.allianceId = :allianceId ")
                             .append("		) ");

            //if alliance has accepted a contract of a particular platform, then do not add this contract in resultset
            queryStringBuffer.append("		and contract.platformId not in ( ")
                    .append("                   select distinct contract2.platformId")
                    .append("                              from com.netpace.aims.model.core.AimsAllianceContract allianceContract2, ")
                    .append("                                   com.netpace.aims.model.core.AimsContract contract2 ")
                    .append("                             where allianceContract2.allianceId = :allianceId2 ")
                    .append("                               and contract2.contractId = allianceContract2.contractId ")
                    .append("                               and allianceContract2.status = :allianceContractStatus2 ) ");

            queryStringBuffer.append("order by ")
                         .append("		platform.platformName, contract.contractTitle, contract.version ");

            session = DBHelper.getInstance().getSession();

            query = session.createQuery(queryStringBuffer.toString());

            query.setString("isClickThroughContract", AimsConstants.YES_CHAR);

            if(filterPlatformValues!=null && filterPlatformValues.length>0 && filterPlatformValues[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
                query.setParameterList("platformIds", filterPlatformValues);
            }
            if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                query.setString("status", contractStatus);
            }
            query.setLong("allianceId", allianceId.longValue());

            query.setLong("allianceId2", allianceId.longValue());
            query.setString("allianceContractStatus2", AimsConstants.CONTRACT_ACCEPTED);

            collection = query.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();) {
                 contractInfo = new AllianceContractInfoVO();

                 userValues  = (Object []) iter.next();

                 contractInfo.setContractId(((Long) userValues[0]));
                 contractInfo.setContractTitle((String) userValues[1]);
                 contractInfo.setContractVersion((String) userValues[2]);
                 contractInfo.setDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, (String) userValues[3]));
                 contractInfo.setContractExpDate(((Date) userValues[4]));
                 contractInfo.setContractPlatformName((String) userValues[5]);

                 availableClickThroughContracts.add(contractInfo);

            }

            log.debug("AllianceContractInfoManager.getAvailableClickThroughContracts(): No of records returned: " + collection.size() );

        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in AllianceContractInfoManager.getAvailableClickThroughContracts()");
            }
        }

        return availableClickThroughContracts;
    }//end getAvailableClickThroughContracts

    public static void saveOrUpdateAimsAllianceContract(AimsAllianceContract aimsAllianceContract,
                                                        String journalEntryText)throws HibernateException{
        Session session=null;
        Transaction trx=null;
        AimsJournalEntry aimsJournalEntry = null;
        try {
            session=DBHelper.getInstance().getSession();
            trx=session.beginTransaction();
            session.saveOrUpdate(aimsAllianceContract);

            if(!StringFuncs.isNullOrEmpty(journalEntryText)) {
                aimsJournalEntry  = new AimsJournalEntry();
                aimsJournalEntry.setJournalText(journalEntryText);
                aimsJournalEntry.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE);
                aimsJournalEntry.setCreatedBy("system");
                aimsJournalEntry.setCreatedDate(new Date());
                aimsJournalEntry.setAimsAllianceId(aimsAllianceContract.getAllianceId());
                session.save(aimsJournalEntry);
            }

            log.debug("AllianceContractInfoManager.saveAimsAllianceContract(): AimsAllianceContract saved/updated successfully, id= "+aimsAllianceContract.getAllianceContractId());
            trx.commit();
        } catch (HibernateException e) {
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
                log.debug("Session closed in AllianceContractInfoManager.saveAimsAllianceContract()");
            }
        }
    }

    /**
     * returns true if given contractId is Click through and not present in AimsAllianceContracts
     * @param allianceId
     * @param contractId
     * @param contractStatus
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static boolean validateClickThroughContract (Long allianceId, Long contractId,
                                                        String contractStatus) throws HibernateException, Exception {
        Collection collection = null;

        Session session = null;

        StringBuffer queryStringBuffer = new StringBuffer();
        Query query = null;

        boolean validated = false;

        try {

        queryStringBuffer.append("select ")
                         .append("		contract.contractId, ")
                         .append("		contract.contractTitle, ")
                         .append("		contract.version ")
                         .append("from ")
                         .append("		com.netpace.aims.model.core.AimsContract contract, ")
                         .append("where ")
                         .append("		contract.isClickThroughContract = 'Y' ")
                         .append("		and contract.contractId = :contractId ");
         if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                        queryStringBuffer.append("		and contract.status = :status ");
         }

        queryStringBuffer.append("		and contract.contractId not in ")
                             .append("		( ")
                             .append("          select  ")
                             .append("		        allianceContract.contractId ")
                             .append("          from  ")
                             .append("		        com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                             .append("          where  ")
                             .append("		        allianceContract.allianceId = :allianceId ")
                             .append("		) ")
                         .append("order by ")
                         .append("		contract.contractId ");

            session = DBHelper.getInstance().getSession();

            query = session.createQuery(queryStringBuffer.toString());

            query.setLong("contractId", contractId.longValue());
            if(!StringFuncs.isNullOrEmpty(contractStatus)) {
                query.setString("status", contractStatus);
            }
            query.setLong("allianceId", allianceId.longValue());

            collection = query.list();

            if(collection!=null && collection.size()>0) {
                validated = true;
            }

            log.debug("AllianceContractInfoManager.validateClickThroughContract(): No of records returned: " + collection.size() );

        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in AllianceContractInfoManager.validateClickThroughContract()");
            }
        }

        return validated;
    }//end validateClickThroughContract
}  