package com.netpace.aims.bo.alliance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.Type;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.controller.alliance.AllianceApplicationForm;
import com.netpace.aims.controller.alliance.AllianceContractForm;
import com.netpace.aims.controller.contracts.AmendmentForm;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.core.AimsUserOffer;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.util.Utility;

/**
 * This class is responsible for getting the BO for alliances.
 * It has static methods for getting, updating, deleting the alliances.
 * @author Rizwan Qazi
 */
public class AllianceManager
{

    static Logger log = Logger.getLogger(AllianceManager.class.getName());

    /**
     *  This static method gets the alliance details of the current alliance   
     */
    public static Collection getAllianceDetails(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		alliance.companyName, alliance.companyLegalName, ")
                .append("		alliance.createdDate, alliance.status, ")
                .append("		vzwAccountManager.firstName, vzwAccountManager.lastName, ")
                .append("		vzwAccountManager.emailAddress, vzwAccountManager.phone, ")
                .append("		salesContact.firstName, salesContact.lastName, ")
                .append("		salesContact.emailAddress, salesContact.phone, ")
                .append("		salesContact.contactId, user.userId, ")
                .append("		adminUserContact.firstName, adminUserContact.lastName, ")
                .append("		adminUserContact.emailAddress, adminUserContact.phone, alliance.aimsUserByAdminUserId ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance,  ")
                .append("		com.netpace.aims.model.core.AimsUser user,  ")
                .append("		com.netpace.aims.model.core.AimsContact vzwAccountManager, ")
                .append("		com.netpace.aims.model.core.AimsContact salesContact, ")
                .append("		com.netpace.aims.model.core.AimsUser adminUser,  ")
                .append("		com.netpace.aims.model.core.AimsContact adminUserContact ")
                .append("where ")
                .append("		alliance.allianceId = :allianceId ")
                .append("		and alliance.vzwAccountManager = user.userId (+) ")
                .append("		and user.aimsContact.contactId = vzwAccountManager.contactId (+) ")
                .append("		and alliance.aimsContactBySalesContactId = salesContact.contactId (+) ")
                .append("		and alliance.aimsUserByAdminUserId = adminUser.userId (+) ")
                .append("		and adminUser.aimsContact.contactId = adminUserContact.contactId (+) ");

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
     *  This static method updates the sales contact and account manager of the given alliance.  
     */
    public static void changeSalesContactAccountMgr(Long allianceId, Long accManagerUserId, String userName) throws HibernateException
    {

        Session session = null;
        Transaction tx = null;
        AimsAllianc aimsAlliance = null;
        try
        {

            if (accManagerUserId.intValue() > 0)
            {
                session = DBHelper.getInstance().getSession();
                tx = session.beginTransaction();

                aimsAlliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

                aimsAlliance.setVzwAccountManager(accManagerUserId);
                aimsAlliance.setLastUpdatedBy(userName);
                aimsAlliance.setLastUpdatedDate(new Date());

                session.update(aimsAlliance);
                session.flush();

                tx.commit();
            }

        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (session != null)
                session.close();
        }
    }
    /**
     *  This static method updates the status of the given alliance.  
     */
    public static void changeAllianceStatus(Long allianceId, String allianceStatus, String userName) throws HibernateException
    {

        Session session = null;
        Transaction tx = null;
        AimsAllianc aimsAlliance = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            aimsAlliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

            aimsAlliance.setStatus(allianceStatus);
            aimsAlliance.setLastUpdatedBy(userName);
            aimsAlliance.setLastUpdatedDate(new Date());

            session.update(aimsAlliance);
            session.flush();

            tx.commit();

        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    public static void updateAlliance(AimsAllianc aimsAlliance) throws HibernateException
    {

        Session session = null;
        Transaction tx = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            session.update(aimsAlliance);
            session.flush();

            tx.commit();

        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if(session!=null) {
                session.close();
                log.debug("session closed in AllianceManager.updateAlliance");
            }
        }
    }//end updateAlliance

    /**
     *  This static method gets the Alliance module priviliges in the database.
     */
    public static Collection getAlliancePriviliges() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
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
     *  This static method gets the Verizon users in the database.
     */
    public static Collection getAllianceAccountManagers(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceAccountManagers = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsUser aimsUser = null;
        AimsContact aimsContact = null;

        try
        {
            queryStringBuffer
                .append("select ")
                .append("		accUser.userId , ")
                .append("		accContact.firstName , ")
                .append("		accContact.lastName , ")
                .append("		accContact.emailAddress  ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsUser accUser,  ")
                .append("		com.netpace.aims.model.core.AimsContact accContact ")
                .append("where ")
                .append("		accUser.userType ='V' ")
                .append(" 		and accUser.isAccountManager in ('Y','y')")
                .append("		and accUser.aimsContact.contactId = accContact.contactId (+) ")
                .append("order by ")
                .append("      upper(accContact.firstName), upper (accContact.lastName) ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsUser = new AimsUser();
                aimsContact = new AimsContact();

                userValues = (Object[]) iter.next();

                aimsUser.setUserId((Long) userValues[0]);
                aimsContact.setFirstName((String) userValues[1]);
                aimsContact.setLastName((String) userValues[2]);
                aimsContact.setEmailAddress((String) userValues[3]);

                aimsUser.setAimsContact(aimsContact);
                AllianceAccountManagers.add(aimsUser);
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

        return AllianceAccountManagers;
    }

    /**
     *  This static method gets the VZW sales contacts in the database.
     */
    public static Collection getAllianceSalesContacts(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceSalesContacts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsContact aimsContact = null;

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		salesContact.contactId , ")
                .append("		salesContact.firstName , ")
                .append("		salesContact.lastName , ")
                .append("		salesContact.emailAddress   ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsContact salesContact ")
                .append("where ")
                .append("		salesContact.type ='SC' ")
                .append("order by ")
                .append("		salesContact.firstName, salesContact.lastName");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsContact = new AimsContact();

                userValues = (Object[]) iter.next();

                aimsContact.setContactId((Long) userValues[0]);
                aimsContact.setFirstName((String) userValues[1]);
                aimsContact.setLastName((String) userValues[2]);
                aimsContact.setEmailAddress((String) userValues[3]);

                AllianceSalesContacts.add(aimsContact);

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

        return AllianceSalesContacts;
    }

    /**
     *  This static method gets the Alliance Tools in the database.
     * @throws SQLException 
     */
    public static Collection getAllianceTools(Long alliance_id, String user_type) throws HibernateException, SQLException
    {
        Collection collection = new ArrayList();
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        PreparedStatement prepStmt=null;
        Connection conn=null;
        ResultSet rs=null;

        try
        {
            session = DBHelper.getInstance().getSession();
            conn=session.connection();
            queryStringBuffer
            	.append(" SELECT * FROM (")
            	.append("   SELECT tool.tool_id toolId1, tool.tool_name toolName, tool.tool_desc toolDesc, ")
            	.append("	   			 tool.tool_id toolId2, tool.status toolStatus, tool.tool_file_file_name toolFile,") 
            	.append("				 tool.url toolUrl")
            	.append("     FROM aims_tools tool,")
            	.append("          aims_tool_contracts tc,")
            	.append("          aims_alliance_contracts c,")
            	.append("          aims_alliances a")
            	.append("    WHERE (a.alliance_id = c.alliance_id)")
            	.append("      AND (c.contract_id = tc.contract_id)")
            	.append("      AND (tc.tool_id = tool.tool_id)")
            	.append("      AND (tool.show_all = 'N')")
            	.append("      AND (a.status = 'A')")
            	.append("      AND (c.status = 'ACCEPTED')")
            	.append("      AND (a.alliance_id = ?)")
            	.append("   UNION")            	
            	.append("   SELECT tool.tool_id toolId1, tool.tool_name toolName, tool.tool_desc toolDesc, ")
            	.append("	   			 tool.tool_id toolId2, tool.status toolStatus, tool.tool_file_file_name toolFile,") 
            	.append("				 tool.url toolUrl")
            	.append("           FROM aims_tools tool, aims_tool_platforms tp")
            	.append("          WHERE tool.tool_id = tp.tool_id")
            	.append("            AND tp.platform_id IN (")
            	.append("                   SELECT c.platform_id")
            	.append("                     FROM aims_alliances al, aims_alliance_contracts ac, aims_contracts c")
            	.append("                    WHERE al.alliance_id = ac.alliance_id")
            	.append("                      AND ac.contract_id = c.contract_id")
            	.append("                      AND al.status = 'A'")
            	.append("                      AND ac.status = 'ACCEPTED'")
            	.append("                      AND al.alliance_id = ?)")
            	.append("            AND tool.show_all = 'Y'")
            	.append("   UNION")
            	.append("   SELECT tool.tool_id toolId1, tool.tool_name toolName, tool.tool_desc toolDesc, ")
            	.append("	   			 tool.tool_id toolId2, tool.status toolStatus, tool.tool_file_file_name toolFile,") 
            	.append("				 tool.url toolUrl")
            	.append("           FROM aims_tools tool,")
            	.append("                (SELECT al.status FROM aims_alliances al WHERE al.status = 'A' AND al.alliance_id = ?) al")
            	.append("          WHERE tool.show_all = 'Y'")
            	.append("            AND tool.tool_id NOT IN (SELECT tp.tool_id FROM aims_tool_platforms tp)")
            	.append("            AND al.status = 'A') q order by lower(q.toolName)");
            
            prepStmt=conn.prepareStatement(queryStringBuffer.toString());
            prepStmt.setLong(1, alliance_id.longValue());
            prepStmt.setLong(2, alliance_id.longValue());
            prepStmt.setLong(3, alliance_id.longValue());
            rs=prepStmt.executeQuery();
            
            while(rs.next()){
            	Object[] values=new Object[7];
            	values[0] = rs.getObject("toolId1");
            	values[1] = rs.getObject("toolName");
            	values[2] = rs.getObject("toolDesc");
            	values[3] = rs.getObject("toolId2");
            	values[4] = rs.getObject("toolStatus");
            	values[5] = rs.getObject("toolFile");
            	values[6] = rs.getObject("toolUrl");
            	collection.add(values);
            }

            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
        finally
        {
            session.close();
            if(rs != null){
            	rs.close();
            }
            if(prepStmt != null){
            	prepStmt.close();
            }
        }

        return collection;
    }

    /**
     *  This static method gets the contract details in the database which are available
     *  to the current alliance.
     */
    public static Collection getAllianceContracts(Long alliance_id, String user_type) throws HibernateException
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
                .append("		vzwAccountManagerContact.lastName,  ")
                .append("		contract.documentFileName,  ")
                .append("		alliance_contract.modifiedContDocFileName, ")
                .append("		alliance_contract.allianceContractId  ")
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
                .append("		and alliance.allianceId = alliance_contract.allianceId ")
                .append("		and alliance_contract.contractId = contract.contractId ")
                .append("		and contract.platformId = platform.platformId ")
                .append("		and alliance_contract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("		and alliance.vzwAccountManager = vzwAccountManagerUser.userId  ")
                .append("		and vzwAccountManagerUser.aimsContact.contactId = vzwAccountManagerContact.contactId  ")
                .append("order by ")
                .append("		alliance_contract.vzwContractPresentDate desc, ")
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
                allianceContractForm.setDocumentFileName((String) userValues[11]);
                allianceContractForm.setModifiedContDocFileName((String) userValues[12]);
                allianceContractForm.setAllianceContractId((Long) userValues[13]);

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
    public static Collection getAllianceAcceptedContracts(Long alliance_id, Long platformId) throws HibernateException
    {
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
                .append("		alliance.allianceId = :alliance_id ");
            
            
            if ( platformId!=null )
            	queryStringBuffer.append("		and platform.platformId = :platformId ");
                
            queryStringBuffer.append("		and alliance_contract.status = 'ACCEPTED' ")
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

            Query query = session.createQuery(queryStringBuffer.toString());
				query.setLong("alliance_id", alliance_id);
				
			if ( platformId!=null )
				query.setLong("platformId", platformId);				
			
            for (Iterator iter = query.iterate(); iter.hasNext();)
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
            
            log.debug("Number of contracts selected = " + AllianceContracts.size());
            
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        	if(session!=null) 
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
     *  This static method gets all active contract details in the database which are accepted by
     *  the current alliance.
     */
    public static Collection getAllianceAcceptedActiveContractsByPlatform(Long alliance_id, Long platform_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceContracts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Query query = null;
        Iterator iter = null;
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
                .append("		and contract.status = :contract_status ")
                .append("		and platform.platformId = :platform_Id ")
                .append("		and alliance_contract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("		and alliance.vzwAccountManager = vzwAccountManagerUser.userId (+) ")
                .append("		and vzwAccountManagerUser.aimsContact.contactId = vzwAccountManagerContact.contactId (+) ")
                .append("order by ")
                .append("		contract.contractTitle ");

            session = DBHelper.getInstance().getSession();
            query = session.createQuery(queryStringBuffer.toString());
            query.setLong("alliance_id", alliance_id.longValue());
            query.setString("contract_status", AimsConstants.CONTRACT_STATUS_ACTIVE);
            query.setLong("platform_Id", platform_id.longValue());

            iter = query.iterate();
            if(iter != null) {
                while (iter.hasNext())
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

                }//end while
            }

            log.debug("No of records returned: " + AllianceContracts.size());

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
    }//end getAllianceAcceptedActiveContractsByPlatform
    
    /**
     *  This static method gets all active contract details in the database which are accepted by
     *  the current alliance.
     */
    public static Collection getAllianceAcceptedActiveBOBOContractsByPlatform(Long alliance_id, Long platform_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceContracts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Query query = null;
        Iterator iter = null;
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
                .append("		vzwAccountManagerContact.lastName,  ")
                .append("		contract.documentFileName  ")
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
                .append("		and contract.status = :contract_status ")
                .append("		and contract.isBoboContract = 'Y' ")
                .append("		and platform.platformId = :platform_Id ")
                .append("		and alliance_contract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("		and alliance.vzwAccountManager = vzwAccountManagerUser.userId (+) ")
                .append("		and vzwAccountManagerUser.aimsContact.contactId = vzwAccountManagerContact.contactId (+) ")
                .append("order by ")
                .append("		contract.contractTitle ");

            session = DBHelper.getInstance().getSession();
            query = session.createQuery(queryStringBuffer.toString());
            query.setLong("alliance_id", alliance_id.longValue());
            query.setString("contract_status", AimsConstants.CONTRACT_STATUS_ACTIVE);
            query.setLong("platform_Id", platform_id.longValue());

            iter = query.iterate();
            if(iter != null) {
                while (iter.hasNext())
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
                    allianceContractForm.setDocumentFileName((String) userValues[11]);	
                    AllianceContracts.add(allianceContractForm);

                }//end while
            }

            log.debug("No of records returned: " + AllianceContracts.size());

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
    }//end getAllianceAcceptedActiveContractsByPlatform


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
                .append("		application.submittedDate, ")
                .append("		application.aimsPlatformId, ")
                .append("		application.aimsLifecyclePhaseId ")
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
                allianceApplicationForm.setUrlSetupAction(ApplicationsManagerHelper.getUrlSetupAction((Long) userValues[8]));
                allianceApplicationForm.setPhaseId((Long) userValues[9]);

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

    /**
     *  This static method gets the contract details in the database which are available
     *  to the current alliance along with their amendments
     */
    public static Collection getAllianceContractAmendments(Long alliance_id, String user_type) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceContracts = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AllianceContractForm allianceContractForm = null;
        Long contractId = null;
        Long allianceContractId = null;

        try
        {

            queryStringBuffer
                .append("select  ")
                .append("		contract.contractId, ")
                .append("		contract.contractTitle, ")
                .append("		contract.version, ")
                .append("		contract.documentFileName,  ")
                .append("		allianceContract.vzwContractPresentDate, ")
                .append("		allianceContract.status,  ")
                .append("		allianceContract.acceptDeclineDate, ")
                .append("		allianceContract.allianceContractId, ")
                .append("		allianceContract.contractExpDate, ")
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
                .append("		and allianceContract.contractId = contract.contractId ")
                .append("		and platform.platformId = contract.platformId ")
                .append("		and allianceContract.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("order by ")
                .append("		platform.platformName, contract.contractTitle, contract.version asc ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                allianceContractForm = new AllianceContractForm();

                userValues = (Object[]) iter.next();

                contractId = (Long) userValues[0];
                allianceContractId = (Long) userValues[7];

                allianceContractForm.setContractId(contractId);
                allianceContractForm.setContractTitle((String) userValues[1]);
                allianceContractForm.setContractVersion((String) userValues[2]);
                allianceContractForm.setDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String) userValues[3]));
                allianceContractForm.setContractPresentDate((java.util.Date) userValues[4]);
                allianceContractForm.setContractStatus((String) userValues[5]);
                allianceContractForm.setContractAcceptDeclineDate((java.util.Date) userValues[6]);
                allianceContractForm.setContractExpDate((java.util.Date) userValues[8]);
                allianceContractForm.setAllianceContractId(allianceContractId);
                allianceContractForm.setContractAmendments(getContractAmendments(contractId, session));
                allianceContractForm.setAllianceAmendments(getAllianceAmendments(allianceContractId, session));
                allianceContractForm.setAcceptDeclineFirstName((String) userValues[9]);
                allianceContractForm.setAcceptDeclineLastName((String) userValues[10]);

                allianceContractForm.setContractPlatformName((String) userValues[11]);

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
     *  This static method gets the contract details in the database which are available
     *  to the current alliance along with their amendments
     */
    public static Collection getContractAmendments(Long contractId, Session session) throws HibernateException
    {
        Collection collection = null;
        Collection Amendments = new ArrayList();
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AmendmentForm amendmentForm = null;

        try
        {

            queryStringBuffer
                .append("select distinct ")
                .append("		amendment.amendmentId, ")
                .append("		amendment.amendmentTitle, ")
                .append("		amendment.version, ")
                .append("		amendment.documentFileName,  ")
                .append("		contractAmendment.status,  ")
                .append("		amendment.ifNegotiable,  ")
                .append("		amendment.comments,  ")
                .append("		amendment.expiryDate, ")
                .append("		acceptDeclineContact.firstName, ")
                .append("		acceptDeclineContact.lastName ")
                .append("from ")
                .append("		com.netpace.aims.model.contracts.AimsAmendment amendment, ")
                .append("		com.netpace.aims.model.contracts.AimsContractAmendment contractAmendment, ")
                .append("		com.netpace.aims.model.core.AimsUser acceptDeclineUser, ")
                .append("		com.netpace.aims.model.core.AimsContact acceptDeclineContact ")
                .append("where ")
                .append("		 contractAmendment.contractId = :contractId ")
                .append("		 and contractAmendment.amendment.amendmentId = amendment.amendmentId ")
                .append("		 and contractAmendment.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		 and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("order by ")
                .append("		amendment.amendmentTitle ");

            collection = session.find(queryStringBuffer.toString(), contractId, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                amendmentForm = new AmendmentForm();

                userValues = (Object[]) iter.next();

                amendmentForm.setAmendmentId(((Long) userValues[0]).toString());
                amendmentForm.setAmendmentTitle((String) userValues[1]);
                amendmentForm.setAmendmentVersion((String) userValues[2]);
                amendmentForm.setAmendmentDocumentFileName((String) userValues[3]);
                amendmentForm.setAmendmentStatus(amendmentForm.getAmendmentStatus((String) userValues[4]));
                amendmentForm.setIfAmendmentNegotiable((String) userValues[5]);
                amendmentForm.setComments((String) userValues[6]);
                amendmentForm.setAmendmentExpiryDate(Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
                amendmentForm.setAcceptDeclineFirstName((String) userValues[8]);
                amendmentForm.setAcceptDeclineLastName((String) userValues[9]);

                Amendments.add(amendmentForm);

            }

            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }

        return Amendments;
    }

    /**
     *  This static method gets the contract details in the database which are available
     *  to the current alliance along with their amendments
     */
    public static Collection getAllianceAmendments(Long allianceContractId, Session session) throws HibernateException
    {
        Collection collection = null;
        Collection Amendments = new ArrayList();
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AmendmentForm amendmentForm = null;

        try
        {

            queryStringBuffer
                .append("select distinct ")
                .append("		amendment.amendmentId, ")
                .append("		amendment.amendmentTitle, ")
                .append("		amendment.version, ")
                .append("		amendment.documentFileName,  ")
                .append("		allianceAmendment.status,  ")
                .append("		amendment.ifNegotiable,  ")
                .append("		amendment.comments,  ")
                .append("		amendment.expiryDate, ")
                .append("		acceptDeclineContact.firstName, ")
                .append("		acceptDeclineContact.lastName, ")
                .append("		allianceAmendment.vzwAmendmentPresentDate  ")
                .append("from ")
                .append("		com.netpace.aims.model.contracts.AimsAmendment amendment, ")
                .append("		com.netpace.aims.model.contracts.AimsAllianceContractAmendment allianceAmendment, ")
                .append("		com.netpace.aims.model.core.AimsUser acceptDeclineUser, ")
                .append("		com.netpace.aims.model.core.AimsContact acceptDeclineContact ")
                .append("where ")
                .append("		 allianceAmendment.allianceContractId = :allianceContractId ")
                .append("		 and allianceAmendment.amendment.amendmentId = amendment.amendmentId ")
                .append("		 and allianceAmendment.acceptDeclineUserId = acceptDeclineUser.userId (+) ")
                .append("		 and acceptDeclineUser.aimsContact.contactId = acceptDeclineContact.contactId (+) ")
                .append("order by ")
                .append("		amendment.amendmentTitle ");

            collection = session.find(queryStringBuffer.toString(), allianceContractId, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                amendmentForm = new AmendmentForm();

                userValues = (Object[]) iter.next();

                amendmentForm.setAmendmentId(((Long) userValues[0]).toString());
                amendmentForm.setAmendmentTitle((String) userValues[1]);
                amendmentForm.setAmendmentVersion((String) userValues[2]);
                amendmentForm.setAmendmentDocumentFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, (String) userValues[3]));
                amendmentForm.setAmendmentStatus(amendmentForm.getAmendmentStatus((String) userValues[4]));
                amendmentForm.setIfAmendmentNegotiable((String) userValues[5]);
                amendmentForm.setComments((String) userValues[6]);
                amendmentForm.setAmendmentExpiryDate(Utility.convertToString(((Date) userValues[7]), AimsConstants.DATE_FORMAT));
                amendmentForm.setAcceptDeclineFirstName((String) userValues[8]);
                amendmentForm.setAcceptDeclineLastName((String) userValues[9]);
                amendmentForm.setAmendmentOfferDate(Utility.convertToString(((Date) userValues[10]), AimsConstants.DATE_FORMAT));

                Amendments.add(amendmentForm);

            }

            log.debug("No of records returned: " + collection.size());

        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }

        return Amendments;
    }

    /**
     *  This static method gets the alliance company name.
     */
    public static String getAllianceCompanyName(Long alliance_id) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        String companyName = "";

        try
        {

            queryStringBuffer
                .append("select ")
                .append("		alliance.companyName ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance ")
                .append("where ")
                .append("		alliance.allianceId = :alliance_id ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), alliance_id, new LongType());

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                companyName = (String) iter.next();
            }

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

        return companyName;
    }

    /**
     *  This static method updates the Alliance Administrator of the given alliance.  
     */
    public static void changeAllianceAdministrator(Long allianceId, Long adminUserId, String userName) throws HibernateException
    {

        Session session = null;
        Transaction tx = null;
        AimsAllianc aimsAlliance = null;
        boolean userValidForUpdate = false;

        Collection userList = getAllianceUsersWithAdminRole(allianceId);

        for (Iterator iter = userList.iterator(); iter.hasNext();)
        {
            AimsUser aimsUser = (AimsUser) iter.next();

            if (aimsUser.getUserId().longValue() == adminUserId.longValue())
            {
                userValidForUpdate = true;
                break;
            }
        }

        try
        {
            if (!userValidForUpdate)
            {
                throw new Exception(
                    "User being made the Alliance Administrator is INVALID!");
            }
            else
            {
                session = DBHelper.getInstance().getSession();
                tx = session.beginTransaction();

                aimsAlliance = (AimsAllianc) session.load(AimsAllianc.class, allianceId);

                aimsAlliance.setAimsUserByAdminUserId(adminUserId);
                aimsAlliance.setLastUpdatedBy(userName);
                aimsAlliance.setLastUpdatedDate(new Date());

                session.update(aimsAlliance);
                session.flush();

                tx.commit();
            }
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
            throw new HibernateException(
                ex);
        }
        finally
        {
            if (session != null)
                session.close();
        }

    }

    /**
     *  This static method gets the Alliance Admin users in the database.
     */
    public static Collection getAllianceUsersWithAdminRole(Long allianceId) throws HibernateException
    {
        Collection collection = null;
        Collection AllianceUsersWithAdminRole = new ArrayList();
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsUser aimsUser = null;
        AimsContact aimsContact = null;

        session = DBHelper.getInstance().getSession();

        AimsSysRole sysRole = null;
        Query query = session
            .createQuery("select from com.netpace.aims.model.security.AimsSysRole as sysrole where sysrole.roleName = :roleName");
        query.setString("roleName", AimsConstants.ALLIANCEADMIN_ROLENAME);

        for (Iterator it = query.iterate(); it.hasNext();)
        {
            sysRole = (AimsSysRole) it.next();
        }

        try
        {
            queryStringBuffer
                .append("select ")
                .append("       accUser.userId , ")
                .append("       accContact.firstName , ")
                .append("       accContact.lastName , ")
                .append("       accContact.emailAddress  ")
                .append("from ")
                .append("       com.netpace.aims.model.core.AimsUser accUser,  ")
                .append("       com.netpace.aims.model.core.AimsContact accContact, ")
                .append("       com.netpace.aims.model.core.AimsUserRole accUserRole ")
                .append("where ")
                .append("       accUser.userType ='A' ")
                .append("       and accUser.aimsAllianc = :allianceId ")
                .append("       and accUser.aimsContact.contactId = accContact.contactId ")
                .append("       and accUser.userId = accUserRole.userId ")
                .append("       and accUserRole.roleId = :roleId ")
                .append("       and accUser.userAccountStatus = 'ACTIVE' ")
                .append("order by ")
                .append("      upper(accContact.firstName), upper (accContact.lastName) ");

            query = session.createQuery(queryStringBuffer.toString());
            query.setLong("allianceId", allianceId.longValue());
            query.setLong("roleId", sysRole.getRoleId().longValue());
            collection = query.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                aimsUser = new AimsUser();
                aimsContact = new AimsContact();

                userValues = (Object[]) iter.next();

                aimsUser.setUserId((Long) userValues[0]);
                aimsContact.setFirstName((String) userValues[1]);
                aimsContact.setLastName((String) userValues[2]);
                aimsContact.setEmailAddress((String) userValues[3]);

                aimsUser.setAimsContact(aimsContact);
                AllianceUsersWithAdminRole.add(aimsUser);
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

        return AllianceUsersWithAdminRole;
    }

    /**
     * Get the alliance information corresponding to the vendor Id
     * 
     * @param vendorId
     * @return
     * @throws HibernateException
     * @author mnauman
     */
    
    public static Collection getAllianceFromAllianceId(Long allianceId) throws HibernateException {
	
        Collection collection = null;
        Session session = null;
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        
        if (log.isDebugEnabled())
        	log.debug("Entry getAllianceFromAllianceId() AllianceID : " + allianceId );
        
        try
        {

            queryStringBuffer
                .append("select ")
                .append("		alliance.companyName, alliance.companyLegalName, ")
                .append("		alliance.createdDate, alliance.status, ")
                .append("		alliance.city, alliance.state,  ")
                .append("		alliance.zipCode, alliance.country,   ")
                .append("		vzwAccountManager.firstName, vzwAccountManager.lastName, ")
                .append("		vzwAccountManager.emailAddress, vzwAccountManager.phone, ")
                .append("		vzwAccountManager.fax, vzwAccountManager.phone, ")
                .append("		salesContact.firstName, salesContact.lastName, ")
                .append("		salesContact.emailAddress, salesContact.phone, ")
                .append("		salesContact.contactId, user.userId, ")
                .append("		adminUserContact.firstName, adminUserContact.lastName, ")
                .append("		adminUserContact.emailAddress, adminUserContact.phone, ")
                .append("		alliance.webSiteUrl , alliance.streetAddress1, ")
                .append("		alliance.vendorId ")
                .append("from ")
                .append("		com.netpace.aims.model.core.AimsAllianc alliance,  ")
                .append("		com.netpace.aims.model.core.AimsUser user,  ")
                .append("		com.netpace.aims.model.core.AimsContact vzwAccountManager, ")
                .append("		com.netpace.aims.model.core.AimsContact salesContact, ")
                .append("		com.netpace.aims.model.core.AimsUser adminUser,  ")
                .append("		com.netpace.aims.model.core.AimsContact adminUserContact ")
                .append("where ")
                .append("		alliance.allianceId = :allianceId ")
                .append("		and alliance.vzwAccountManager = user.userId (+) ")
                .append("		and user.aimsContact.contactId = vzwAccountManager.contactId (+) ")
                .append("		and alliance.aimsContactBySalesContactId = salesContact.contactId (+) ")
                .append("		and alliance.aimsUserByAdminUserId = adminUser.userId (+) ")
                .append("		and adminUser.aimsContact.contactId = adminUserContact.contactId (+) ");

            session = DBHelper.getInstance().getSession();

            collection = session.find(queryStringBuffer.toString(), allianceId, new LongType());
            
            if (log.isDebugEnabled()){
	            log.debug("Exit getAllianceFromAllianceId() Records : " + collection.toString());
	            log.debug("No of records returned: " + collection.size());
            }
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

    public static void main(String args[]) throws HibernateException{
    	DBHelper dbHelper=null;
		try {

			ConfigEnvProperties props= ConfigEnvProperties.getInstance();
			Configuration conf =new Configuration();
			dbHelper=DBHelper.getInstance();
			conf.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@xeon:1521:ora9i");
			conf.setProperty("hibernate.connection.username", "aims4");
			conf.setProperty("hibernate.connection.password", "aims4");
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();

			Collection my = AllianceManager.getAllianceFromAllianceId(new Long("14356"));
			
			for (Iterator iterator = my.iterator(); iterator.hasNext();) {
				Object[] info = (Object[]) iterator.next();
				for (int i = 0; i < info.length; i++) {
					System.out.println( ( String.valueOf(i) )+" value  -- > "+ (String.valueOf(info[i])));					
				}
			}
			
			System.out.println("execution complete");
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
//			dbHelper.sessionFactory.close();
		}
    }

    /**
     * This method returns true if given userid is alliance administrator of given alliance
     * @param allianceId
     * @param userId
     * @return
     * @throws HibernateException
     * @throws Exception
     */
    public static boolean isAllianceAdministrator(Long allianceId, Long userId) throws HibernateException, Exception
    {
        boolean isAllianceAdmin = false;
        AimsAllianc aimsAllianceOfApplication = null;
        try
        {
            aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, allianceId.toString());
            isAllianceAdmin = (aimsAllianceOfApplication.getAimsUserByAdminUserId().longValue() == userId.longValue());
        }
        catch(HibernateException he)
        {
            he.printStackTrace();
            throw he;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return isAllianceAdmin;
    }
    
    public static AimsAllianc getAllianceOnAllianceID(Long allainceId)
			throws HibernateException {

		Session session = null;
		AimsAllianc alliance = null;
		try {
			session = DBHelper.getInstance().getSession();
			Query query = session
					.createQuery("select from com.netpace.aims.model.core.AimsAllianc aimsAlliance where "
							+ "aimsAlliance.allianceId=" + allainceId);
			alliance = (AimsAllianc) query.list().get(0);
			return alliance;

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}

    }
    
    /**
     * This method generate and set vendor ID against the provided allianceId 
     * @param allianceId
     * @param ConOra
     * @return generated vendor ID
     * @throws Exception
     */
    public static Long generateVendorId(Long allianceId,Connection ConOra)throws Exception
    {
    	Long vendorId =null;
    	
  
    	CallableStatement statement = null; 
    	try 
    	{
			statement = ConOra.prepareCall("call AIMS_ALLIANCES_PKG.generate_vendor_id(?,?)");
			statement.setLong(1, allianceId.longValue());
			statement.registerOutParameter(2, java.sql.Types.INTEGER);
			statement.execute();
			vendorId = new Long(statement.getInt(2));
		}
         catch (Exception ex)
         {
             log.error("Error occur while setting vendorId for allianceId :"+allianceId, ex);
             throw ex;
         }
         finally
         {
        	 log.debug("Session will be closed by calling method of generateVendorId()");
             try
             {
                 if (statement != null)
                     statement.close();
             }
             catch (Exception ex)
             {}
         }
    	return vendorId;
    }
}