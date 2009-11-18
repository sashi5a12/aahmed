package com.netpace.aims.bo.alliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.controller.alliance.AllianceApplicationForm;
import com.netpace.aims.controller.alliance.AllianceContractForm;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsJMAAlliance;
import com.netpace.aims.model.masters.AimsIndustryFocu;

/**
 * This class is responsible for getting the BO for alliances.
 * It has static methods for getting, updating, deleting the alliances.
 * @author Rizwan Qazi
 */
public class VZWAllianceManager
{

    static Logger log = Logger.getLogger(VZWAllianceManager.class.getName());

    /**
     *  This static method gets the alliance details of the current alliance   
     */
    public static Collection getAllianceDetails(String order_by, String search_expression, String alliance_type, int PAGE_LENGTH, int pageNo, Long user_id)
        throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select distinct ")
                .append("       alliance.companyName, ")
                .append("       alliance.companyLegalName, ")
                .append("       alliance.createdDate, ")
                .append("       alliance.status, ")
                .append("       vzwAccountManager.firstName, ")
                .append("       vzwAccountManager.lastName, ")
                .append("       vzwAccountManager.emailAddress, ")
                .append("       vzwAccountManager.phone, ")
                .append("       allianceAdminContact.firstName, ")
                .append("       allianceAdminContact.lastName, ")
                .append("       alliance.allianceId, ")
                .append("       alliance.isOnHold, ")
                .append("       allianceAdminContact.emailAddress ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsAllianc alliance, ")
                .append("       com.netpace.aims.model.core.AimsContract contract, ")
                .append("       com.netpace.aims.model.core.AimsAllianceContract allianceContract, ")
                .append("       com.netpace.aims.model.core.AimsUser user, ")
                .append("       com.netpace.aims.model.core.AimsContact vzwAccountManager, ")
                .append("       com.netpace.aims.model.core.AimsUser allianceAdminUser, ")
                .append("       com.netpace.aims.model.core.AimsContact allianceAdminContact ")
                .append("where ")
                .append("       1=1 ");

            if (alliance_type.equalsIgnoreCase("MY"))
            {
                queryStringBuffer.append("      and alliance.vzwAccountManager = ").append(user_id.toString());
            }

            if (alliance_type.equalsIgnoreCase("NEW"))
            {
                queryStringBuffer.append("      and alliance.status = 'S' ");
            }

            if (alliance_type.equalsIgnoreCase("MUSIC_ALL"))
            {
                queryStringBuffer.append("      and alliance.isVcastMusicAlliance = 'Y' ");
            }
            
            if (alliance_type.equalsIgnoreCase("JMA_ALL"))
            {
                queryStringBuffer.append("      and alliance.isJMAAlliance = 'Y' ");
                queryStringBuffer.append("      and alliance.isJmaInfoComplete = 'Y' ");
                queryStringBuffer.append("      and alliance.allianceId NOT IN ( ");
                queryStringBuffer.append("       	  SELECT aac.allianceId ");
                queryStringBuffer.append("       	  FROM ");
                queryStringBuffer.append("       	   com.netpace.aims.model.core.AimsAllianceContract aac, ");
                queryStringBuffer.append("       	   com.netpace.aims.model.core.AimsContract ac  ");
                queryStringBuffer.append("       	     WHERE aac.contractId = ac.contractId ");
                queryStringBuffer.append("       	     AND aac.status = 'ACCEPTED' ");
                queryStringBuffer.append("       	   	 AND ac.status = 'A'	 ");
                queryStringBuffer.append("       	   	 AND ac.platformId = 5  ) ");
            }
            if (alliance_type.equalsIgnoreCase("JMA_Partner"))
            {
                queryStringBuffer.append("      and alliance.isJMAAlliance = 'Y' ");
                queryStringBuffer.append("      and alliance.isJmaInfoComplete = 'Y' ");
                queryStringBuffer.append("      and allianceContract.status = 'ACCEPTED' ");
                queryStringBuffer.append("      and contract.status = 'A' ");
                queryStringBuffer.append("      and contract.platformId = 5 ");
            }

            if (search_expression.length() > 0)
            {
                queryStringBuffer.append(search_expression);
            }

            queryStringBuffer
                .append("       and allianceContract.allianceId (+) = alliance.allianceId  ")
                .append("       and allianceContract.contractId   = contract.contractId (+) ")
                .append("       and alliance.vzwAccountManager = user.userId (+) ")
                .append("       and user.aimsContact.contactId = vzwAccountManager.contactId (+) ")
                .append("       and alliance.aimsUserByAdminUserId = allianceAdminUser.userId (+) ")
                .append("       and allianceAdminUser.aimsContact.contactId = allianceAdminContact.contactId (+) ")
                .append("order by ")
                .append(order_by);

            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (pageNo - 1));
            collection = query.list();

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

        return collection;
    }

    /**
     *  This static method gets the alliance details of the current alliance   
     */
    public static int getAllianceDetailsPageCount(String order_by, String search_expression, String alliance_type, Long user_id) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select distinct ")
                .append("		alliance.companyName,  ")
                .append("		alliance.companyLegalName, ")
                .append("		alliance.createdDate,  ")
                .append("		alliance.status, ")
                .append("		vzwAccountManager.firstName, ")
                .append("		vzwAccountManager.lastName, ")
                .append("		vzwAccountManager.emailAddress, ")
                .append("		vzwAccountManager.phone, ")
                .append("		allianceAdminContact.firstName, ")
                .append("		allianceAdminContact.lastName, ")
                .append("		alliance.allianceId, ")
                .append("		alliance.isOnHold ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance,  ")
                .append("		com.netpace.aims.model.core.AimsContract contract,  ")
                .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract,  ")
                .append("		com.netpace.aims.model.core.AimsUser user,  ")
                .append("		com.netpace.aims.model.core.AimsContact vzwAccountManager, ")
                .append("		com.netpace.aims.model.core.AimsUser allianceAdminUser,  ")
                .append("		com.netpace.aims.model.core.AimsContact allianceAdminContact ")
                .append("where ")
                .append("		1=1 ");

            if (alliance_type.equalsIgnoreCase("MY"))
            {
                queryStringBuffer.append("		and alliance.vzwAccountManager = ").append(user_id.toString());
            }

            if (alliance_type.equalsIgnoreCase("NEW"))
            {
                queryStringBuffer.append("		and alliance.status = 'S' ");
            }

            if (alliance_type.equalsIgnoreCase("MUSIC_ALL"))
            {
                queryStringBuffer.append("      and alliance.isVcastMusicAlliance = 'Y' ");
            }
            
            if (alliance_type.equalsIgnoreCase("JMA_ALL"))
            {
                queryStringBuffer.append("      and alliance.isJMAAlliance = 'Y' ");
                queryStringBuffer.append("      and alliance.isJmaInfoComplete = 'Y' ");
                queryStringBuffer.append("      and alliance.allianceId NOT IN ( ");
                queryStringBuffer.append("       	  SELECT aac.allianceId ");
                queryStringBuffer.append("       	  FROM ");
                queryStringBuffer.append("       	   com.netpace.aims.model.core.AimsAllianceContract aac, ");
                queryStringBuffer.append("       	   com.netpace.aims.model.core.AimsContract ac  ");
                queryStringBuffer.append("       	     WHERE aac.contractId = ac.contractId ");
                queryStringBuffer.append("       	     AND aac.status = 'ACCEPTED' ");
                queryStringBuffer.append("       	   	 AND ac.status = 'A'	 ");
                queryStringBuffer.append("       	   	 AND ac.platformId = 5  ) ");
                
             }
            
            if (alliance_type.equalsIgnoreCase("JMA_Partner"))
            {
                queryStringBuffer.append("      and alliance.isJMAAlliance = 'Y' ");
                queryStringBuffer.append("      and alliance.isJmaInfoComplete = 'Y' ");
                queryStringBuffer.append("      and allianceContract.status = 'ACCEPTED' ");
                queryStringBuffer.append("      and contract.status = 'A' ");
                queryStringBuffer.append("      and contract.platformId = 5 ");
            }

            if (search_expression.length() > 0)
            {
                queryStringBuffer.append(search_expression);
            }

            queryStringBuffer
                .append("		and allianceContract.allianceId (+) = alliance.allianceId  ")
                .append("		and allianceContract.contractId   = contract.contractId (+) ")
                .append("		and alliance.vzwAccountManager = user.userId (+) ")
                .append("		and user.aimsContact.contactId = vzwAccountManager.contactId (+) ")
                .append("		and alliance.aimsUserByAdminUserId = allianceAdminUser.userId (+) ")
                .append("		and allianceAdminUser.aimsContact.contactId = allianceAdminContact.contactId (+) ")
                .append("order by ")
                .append(order_by);

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

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

        return collection.size();
    }

    /**
    *  This static method updates a given alliance's on hold status   
    */
    public static void UpdateAllianceOnHold(Long allianceId, String isOnHold, String currUserName) throws HibernateException
    {

        Session session = null;
        Transaction tx = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            AimsAllianc alliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

            alliance.setIsOnHold(isOnHold);
            alliance.setLastUpdatedBy(currUserName);
            alliance.setLastUpdatedDate(new Date());

            session.update(alliance);

            tx.commit();
        }

        catch (JDBCException je)
        {
            if (tx != null)
            {
                tx.rollback();
            }

            je.printStackTrace();
            throw new HibernateException(je);
        }

        catch (HibernateException e)
        {

            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
            throw e;
        }

        finally
        {
            session.close();
        }
    }

    /**
    *  This static method deletes a given alliance
    */
    public static void DeleteAlliance(Long allianceId, String currUserName) throws AimsException, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();
            tx = session.beginTransaction();

            statement = ConOra.prepareCall("{ call AIMS_ALLIANCES_PKG.delete_alliance(?,?)}");

            statement.setInt(1, allianceId.intValue());
            statement.setString(2, currUserName);

            statement.execute();

            tx.commit();

        }

        catch (java.sql.SQLException sqle)
        {

            String exMessage = sqle.getMessage();
            AimsException ae = new AimsException();

            if (tx != null)
            {
                tx.rollback();
            }

            if (exMessage.indexOf("violated - child record found") > -1)
            {
                ae.setMessageKey("masters.integrity.constraint.violation");
                throw ae;
            }
            else
            {
                sqle.printStackTrace();
                throw new HibernateException(sqle);
            }
        }

        finally
        {
            session.close();
            try
            {
                if (statement != null)
                {
                    statement.close();
                }
            }
            catch (Exception e)
            {}

        }
    }

    public static Collection getJournalEntries(Long alliance_id) throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("select  ")
                .append("from ")
                .append("		com.netpace.aims.model.application.AimsJournalEntry journalEntry ")
                .append("where ")
                .append("		journalEntry.aimsAllianceId = :allianceId ")
                .append("order by ")
                .append("		journalEntry.createdDate desc ");

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            log.debug("getJournalEntries collection.size() " + collection.size());

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

        return collection;
    }

    /**
     *  This static method gets the Alliance module priviliges in the database.
     */
    public static Collection getAlliancePriviliges() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		sysPrivilege.privilegeId, ")
                .append("		sysPrivilege.privilegeName, ")
                .append("		sysPrivilege.privilegeDescription, ")
                .append("		sysPrivilege.privilegeKey, ")
                .append("		sysPrivilege.availableTo,  ")
                .append("		subMenu.subMenuUrl  ")
                .append("from ")
                .append("		com.netpace.aims.model.security.AimsSysPrivilege sysPrivilege, ")
                .append("		com.netpace.aims.model.security.AimsSubMenu subMenu  ")
                .append("where ")
                .append("		subMenu.aimsMenu.menuName = 'Alliance Profile' ")
                .append("		and subMenu.subMenuId = sysPrivilege.subMenuId ")
                .append("order by ")
                .append("		subMenu.sortOrder ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            log.debug("No of records returned: " + collection.size());

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

        return collection;
    }

    /**
     *  This static method gets the Alliance Tools in the database.
     */
    public static Collection getAllianceTools(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		tool.toolId, ")
                .append("		tool.toolName, ")
                .append("		tool.toolDesc, ")
                .append("		tool.toolId, ")
                .append("		tool.status,  ")
                .append("		tool.fileName,  ")
                .append("		tool.url  ")
                .append("from ")
                .append("		com.netpace.aims.model.tools.AimsTool tool, ")
                .append("		com.netpace.aims.model.tools.AimsToolContract toolContract, ")
                .append("		com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                .append("where ")
                .append("		allianceContract.allianceId = :allianceId ")
                .append("		and toolContract.toolId = tool.toolId ")
                .append("		and toolContract.aimsContract.contractId = allianceContract.contractId ")
                .append("order by ")
                .append("		tool.toolName ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            log.debug("No of records returned: " + collection.size());

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

        return collection;
    }

    /**
     *  This static method gets the contract details in the database
     */
    public static Collection getContracts() throws HibernateException
    {
        Collection collection = null;
        Collection AllianceContracts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceContractForm allianceContractForm = null;

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		contract.contractId, ")
                .append("		contract.contractTitle, ")
                .append("		contract.version ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsContract contract ")
                .append("order by ")
                .append("		contract.contractTitle ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                allianceContractForm = new AllianceContractForm();

                userValues = (Object[]) iter.next();

                allianceContractForm.setContractId((Long) userValues[0]);
                allianceContractForm.setContractTitle((String) userValues[1]);
                allianceContractForm.setContractVersion((String) userValues[2]);

                AllianceContracts.add(allianceContractForm);

            }

            log.debug("No of records returned: " + collection.size());

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

        return AllianceContracts;
    }

    /**
     *  This static method gets the contract details in the database which are accepted by 
     *  the current alliance.
     */
    public static Collection getAllianceAcceptedContracts(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceContracts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceContractForm allianceContractForm = null;

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		contract.contractId, ")
                .append("		contract.contractTitle, ")
                .append("		contract.version, ")
                .append("		alliance_contract.vzwContractPresentDate, ")
                .append("		alliance_contract.status,  ")
                .append("		alliance_contract.acceptDeclineDate, ")
                .append("		platform.platformName, ")
                .append("		acceptDeclineContact.firstName, ")
                .append("		acceptDeclineContact.lastName, ")
                .append("		vzwAccountManagerContact.firstName, ")
                .append("		vzwAccountManagerContact.lastName  ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
                .append("		com.netpace.aims.model.core.AimsAllianceContract alliance_contract, ")
                .append("		com.netpace.aims.model.core.AimsContract contract, ")
                .append("		com.netpace.aims.model.core.AimsPlatform platform, ")
                .append("		com.netpace.aims.model.core.AimsUser acceptDeclineUser, ")
                .append("		com.netpace.aims.model.core.AimsContact acceptDeclineContact, ")
                .append("		com.netpace.aims.model.core.AimsUser vzwAccountManagerUser, ")
                .append("		com.netpace.aims.model.core.AimsContact vzwAccountManagerContact  ")
                .append("where ")
                .append("		alliance.allianceId = :alliance_id ")
                .append("		and alliance_contract.status = 'ACCEPTED' ")
                .append("		and alliance.allianceId = alliance_contract.allianceId ")
                .append("		and alliance_contract.contractId = contract.contractId ")
                .append("		and contract.platformId = platform.platformId ")
                .append("		and alliance_contract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("		and alliance.vzwAccountManager = vzwAccountManagerUser.userId (+) ")
                .append("		and vzwAccountManagerUser.aimsContact.contactId = vzwAccountManagerContact.contactId (+) ")
                .append("order by ")
                .append("		platform.platformName, ")
                .append("		contract.contractTitle ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                allianceContractForm = new AllianceContractForm();

                userValues = (Object[]) iter.next();

                allianceContractForm.setContractId((Long) userValues[0]);
                allianceContractForm.setContractTitle((String) userValues[1]);
                allianceContractForm.setContractVersion((String) userValues[2]);
                allianceContractForm.setContractPresentDate((java.util.Date) userValues[3]);
                allianceContractForm.setContractStatus((String) userValues[4]);
                allianceContractForm.setContractAcceptDeclineDate((java.util.Date) userValues[5]);
                allianceContractForm.setContractPlatformName((String) userValues[6]);
                allianceContractForm.setAcceptDeclineFirstName((String) userValues[7]);
                allianceContractForm.setAcceptDeclineLastName((String) userValues[8]);
                allianceContractForm.setVzwAccountManagerFirstName((String) userValues[9]);
                allianceContractForm.setVzwAccountManagerLastName((String) userValues[10]);

                AllianceContracts.add(allianceContractForm);

            }

            log.debug("No of records returned: " + collection.size());

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

        return AllianceContracts;
    }

    /**
     *  This static method gets the application details in the database which are available
     *  to the current alliance.
     */
    public static Collection getAllianceApplications(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceApplications = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceApplicationForm allianceApplicationForm = null;

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		application.appsId, ")
                .append("		application.title, ")
                .append("		application.version, ")
                .append("		platform.platformName, ")
                .append("		phase.phaseName, ")
                .append("		contact.firstName, ")
                .append("		contact.lastName, ")
                .append("		application.createdDate ")
                .append("from ")
                .append("		com.netpace.aims.model.application.AimsApp application, ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance, ")
                .append("		com.netpace.aims.model.application.AimsLifecyclePhase phase, ")
                .append("		com.netpace.aims.model.core.AimsPlatform platform, ")
                .append("		com.netpace.aims.model.core.AimsContact contact, ")
                .append("		com.netpace.aims.model.core.AimsUser user ")
                .append("where ")
                .append("		alliance.allianceId = :alliance_id ")
                .append("		and application.aimsAllianceId = alliance.allianceId ")
                .append("		and application.aimsPlatformId = platform.platformId ")
                .append("		and application.aimsLifecyclePhaseId = phase.phaseId ")
                .append("		and application.aimsUserAppCreatedById = user.userId  ")
                .append("		and user.aimsContact.contactId = contact.contactId  ")
                .append("order by ")
                .append("		platform.platformName, ")
                .append("		application.title ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                allianceApplicationForm = new AllianceApplicationForm();

                userValues = (Object[]) iter.next();

                allianceApplicationForm.setAppsId((Long) userValues[0]);
                allianceApplicationForm.setAppTitle((String) userValues[1]);
                allianceApplicationForm.setAppVersion((String) userValues[2]);
                allianceApplicationForm.setAppPlatformName((String) userValues[3]);
                allianceApplicationForm.setAppPphaseName((String) userValues[4]);
                allianceApplicationForm.setSubmittedFirstName((String) userValues[5]);
                allianceApplicationForm.setSubmittedLastName((String) userValues[6]);
                allianceApplicationForm.setSubmittedDate((java.util.Date) userValues[7]);

                AllianceApplications.add(allianceApplicationForm);
            }

            log.debug("No of records returned: " + collection.size());

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

        return AllianceApplications;
    }
    
    public static Collection getVzwAccountManager(Long vzwAccountManager)throws HibernateException{
    	Session session=null;
    	Collection alliances=null;
    	try{
    		session=DBHelper.getInstance().getSession();
    		Query query=session.createQuery("from AimsAllianc a where a.vzwAccountManager=:id");
    		query.setLong("id", vzwAccountManager.longValue());
    		alliances=query.list();
    	}
    	catch(HibernateException e){
    		e.printStackTrace();
    		throw e;
    	}
    	finally{
    		if (session != null){
    			session.close();
    		}
    	}
    	return alliances;
    }
    
    public static void updateVerticalEmailAddress(Collection collection)throws HibernateException{
    	Session session=null;
        Transaction tx = null;
    	AimsIndustryFocu industryFocous=null;
    	try{
    		session=DBHelper.getInstance().getSession();
    		tx = session.beginTransaction();
    		if(collection!=null){
    			for(Iterator iter = collection.iterator() ; iter.hasNext();){
    				industryFocous=null;
    				industryFocous=(AimsIndustryFocu)iter.next();
    				AimsIndustryFocu savedInfFocus=(AimsIndustryFocu)DBHelper.getInstance().load(AimsIndustryFocu.class, industryFocous.getIndustryId().toString());
    				savedInfFocus.setEmailAddress(industryFocous.getEmailAddress());
    				session.update(savedInfFocus);
    			}
    		}
    		tx.commit();
    	}
    	catch(HibernateException e){
    		 if (tx != null)
             {
                 tx.rollback();
             }
    		e.printStackTrace();
    		throw e;
    	}
    	finally{
    		if (session != null){
    			session.close();
    		}
    	}
    }
}