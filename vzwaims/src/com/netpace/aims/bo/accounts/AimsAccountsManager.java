package com.netpace.aims.bo.accounts;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.IntegrityConstraintException;
import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.bo.security.InvalidLoginException;
import com.netpace.aims.bo.vzdn.AimsUserOfferManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsRolePrivilege;
import com.netpace.aims.model.security.AimsSysPrivilege;
import com.netpace.aims.model.security.AimsSysRole;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.type.Type;
import oracle.jdbc.driver.OracleCallableStatement;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;





/**
 * This class is responsible for getting the BO for user accounts.
 * @author Rizwan Qazi
 */
public class AimsAccountsManager
{

  static Logger log = Logger.getLogger(AimsAccountsManager.class.getName());

  /**
   *  This static method gets the user accounts in the database which are available
   *  to the current user.
   */
  public static Collection getAccounts (Long user_id, String user_type, Long alliance_id) throws HibernateException
  {
	Collection collection = null;
	Collection AimsAccounts = new ArrayList();
    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	AimsUser user = null;
	AimsContact contact = null;
	Object [] parameterValues = null;
	Type [] parameterTypes = null;

    try
    {

		parameterValues = new Object [3];
		parameterTypes =  new Type [3];

		parameterValues[0] = user_type;
		parameterValues[1] = user_id;
		parameterValues[2] = alliance_id;

		parameterTypes[0] = new StringType();
		parameterTypes[1] = new LongType();
		parameterTypes[2] = new LongType();

		queryStringBuffer.append("select ")
						 .append("		user.userId, user.username, user.userAccountStatus, ")
						 .append("		user.userSessionStatus, user.createdBy, user.createdDate,  ")
						 .append("		contact.firstName, contact.lastName  ")
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsUser user  ")
						 .append("		inner join user.aimsContact contact ")
						 .append("where ")
						 .append("		user.userType = :userType ")
                         .append("		and user.userId <> :userId ")
			             .append("		and user.aimsAllianc = :allianceId ")
						 .append("order by contact.firstName, contact.lastName ");


		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

		for (Iterator iter = collection.iterator(); iter.hasNext();)
		{
			 userValues  = (Object []) iter.next();
			 user = new AimsUser();
			 contact = new AimsContact();

			 user.setUserId((Long) userValues[0]);				// userId
			 user.setUsername((String) userValues[1]);			// userName
			 user.setUserAccountStatus((String)userValues[2]);	// userAccountStatus
			 user.setUserSessionStatus((String)userValues[3]);	// userSessionStatus
			 user.setCreatedBy((String) userValues[4]);			// createdBy
			 user.setCreatedDate((Date) userValues[5]);		    // createdDate

			 contact.setFirstName((String) userValues[6]);		// firstName
			 contact.setLastName((String) userValues[7]);		// lastName

			 user.setAimsContact(contact);

			 AimsAccounts.add(user);
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

    return AimsAccounts;
  }



   /**
    *  This static method gets the user accounts in the database which are available
    *  to the current user. Result will not include users which are permanently deleted if hidePermanentlyDeletedUsers parameter is true
    */
    public static Collection getAccounts (String search_expression, Long user_id,
		   								String user_type, Long alliance_id, int PAGE_LENGTH, 
		   								int pageNo, String[] statuses, boolean hidePermanentlyDeletedUsers) throws HibernateException
    {
        Collection collection = null;
        Collection AimsAccounts = new ArrayList();
        Session session = null;
        Query squery = null;
        Object [] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsUser user = null;
        AimsContact contact = null;

        try
        {

            queryStringBuffer.append("select ")
                            .append("		user.userId, user.username, user.userAccountStatus, ")
                            .append("		user.userSessionStatus, user.createdBy, user.createdDate,  ")
                            .append("		contact.firstName, contact.lastName  ")
                            .append("from ")
                            .append("		com.netpace.aims.model.core.AimsUser user  ")
                            .append("		inner join user.aimsContact contact ")
                            .append("where ")
                            .append("		user.userType = :userType ")
                            .append("		and user.userId != :userId ")
                            .append("		and user.aimsAllianc = :allianceId ");

            if(hidePermanentlyDeletedUsers)
            {   //do not add Permanently Deleted Users in resultset
                queryStringBuffer
                            .append("       and (user.permanentlyDeleted is null or user.permanentlyDeleted not in ('Y', 'y') ) ");
            }
            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false)
            {
                queryStringBuffer
                            .append("		and user.userAccountStatus in (:statusId) ");
            }

            if (search_expression!=null && search_expression.length() > 0)
            {
                queryStringBuffer.append(search_expression);
            }

            queryStringBuffer.append(" order by lower(contact.firstName), lower(contact.lastName) ");

            session = DBHelper.getInstance().getSession();
            squery = session.createQuery(queryStringBuffer.toString());
            squery.setMaxResults(PAGE_LENGTH);
            squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));

            squery.setString("userType", user_type);
            squery.setLong("userId", Utility.ZeroValueReplacement(user_id).longValue());
            squery.setLong("allianceId", Utility.ZeroValueReplacement(alliance_id).longValue());

            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false)
            {
                squery.setParameterList("statusId", statuses);
            }

            collection = squery.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                userValues  = (Object []) iter.next();
                user = new AimsUser();
                contact = new AimsContact();

                user.setUserId((Long) userValues[0]);				// userId
                user.setUsername((String) userValues[1]);			// userName
                user.setUserAccountStatus((String)userValues[2]);	// userAccountStatus
                user.setUserSessionStatus((String)userValues[3]);	// userSessionStatus
                user.setCreatedBy((String) userValues[4]);			// createdBy
                user.setCreatedDate((Date) userValues[5]);		    // createdDate

                contact.setFirstName((String) userValues[6]);		// firstName
                contact.setLastName((String) userValues[7]);		// lastName

                user.setAimsContact(contact);

                //get roles of this user and set userRolesSet
                Collection<AimsSysRole> userRoleValues = AimsAccountsManager.getAssignedRoles(user.getUserId(), user_type);
                if(userRoleValues!=null && userRoleValues.size()>0)
                {
                    //for alliance user only one role will be available, add this role in userRolesSet
                    HashSet userRolesSet = new HashSet();
                    for ( AimsSysRole sysRole : userRoleValues )
                    {
                        userRolesSet.add(sysRole);
                    }
                    user.setRoles(userRolesSet);
                }

                AimsAccounts.add(user);
            }

            log.debug("No of records returned in getAccounts: " + collection.size() );
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
        return AimsAccounts;
   }//end getAccounts

   /**
      *  This static method gets the user accounts in the database which are available
      *  to the current user. Result will not include users which are permanently deleted if hidePermanentlyDeletedUsers parameter is true
      */
    public static int getAccountsCount (String search_expression, Long user_id, String user_type, Long alliance_id,
                                         String[] statuses, boolean hidePermanentlyDeletedUsers) throws HibernateException
    {
        Session session = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        int rows = 0;
        Query squery = null;
        try
        {

            queryStringBuffer.append("select ")
                                .append("		count(*) ")
                                .append("from ")
                                .append("		com.netpace.aims.model.core.AimsUser user  ")
                                .append("		inner join user.aimsContact contact ")
                                .append("where ")
                                .append("		user.userType = :userType ")
                                .append("		and user.userId != :userId ")
                                .append("		and user.aimsAllianc = :allianceId ");

            if(hidePermanentlyDeletedUsers)
            {   //do not add Permanently Deleted Users in resultset
                    queryStringBuffer
                                .append("       and (user.permanentlyDeleted is null or user.permanentlyDeleted not in ('Y', 'y') ) ");
            }
            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false)
            {
                    queryStringBuffer
                            .append("           and user.userAccountStatus in (:statusId) ");
            }

            if (search_expression!=null && search_expression.length() > 0)
            {
                    queryStringBuffer.append(search_expression);
            }

            session = DBHelper.getInstance().getSession();
            squery = session.createQuery(queryStringBuffer.toString());

            squery.setString("userType", user_type);
            squery.setLong("userId", Utility.ZeroValueReplacement(user_id).longValue());
            squery.setLong("allianceId", Utility.ZeroValueReplacement(alliance_id).longValue());

            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false)
            {
                squery.setParameterList("statusId", statuses);
            }
            rows = ( (Integer)squery.iterate().next()).intValue();
            log.debug("No of rows in getAccountsCount: " + rows);
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
    }//end getAccountsCount


    /**
     *  this action is modified to include the user him self when we go in to Account Managers page.
     *  the action is /aims/accounts.do?task=listAccountManager 
    * */
     public static Collection getAccountsByStatus(Long user_id, 
    		 									String user_type, 
    		 									String userAccountStatus, 
    		 									boolean includeAccountManager)throws HibernateException {
		Collection collection = null;
		Collection aimsAccounts = new ArrayList();
		Session session = null;
		Object[] userValues = null;
		StringBuffer queryStringBuffer = new StringBuffer();
		AimsUser user = null;
		AimsContact contact = null;
		Object[] parameterValues = null;
		Type[] parameterTypes = null;

		try {
			/*parameterValues = new Object[3];
			parameterTypes = new Type[3];*/

            parameterValues = new Object[2];
			parameterTypes = new Type[2];

            parameterValues[0] = user_type;
			/*parameterValues[1] = user_id;*/
			parameterValues[1] = userAccountStatus;

			parameterTypes[0] = new StringType();
			/*parameterTypes[1] = new LongType();*/
			parameterTypes[1] = new StringType();

            /*.append("and user.userId <> :userId ")*/
            queryStringBuffer
					.append("select ")
					.append("		user.userId, user.username, user.userAccountStatus, ")
					.append("		contact.firstName, contact.lastName  ")
					.append("from ")
					.append("		com.netpace.aims.model.core.AimsUser user  ")
					.append("		inner join user.aimsContact contact ")
					.append("where ")
					.append("		user.userType = :userType ")
					.append("		and user.userAccountStatus = :status");

            if (includeAccountManager==false){
				queryStringBuffer.append(" and (user.isAccountManager in ('N','n') or user.isAccountManager is null)");
			}
			queryStringBuffer
					.append(" order by lower(user.username)");

			session = DBHelper.getInstance().getSession();

			collection = session.find(queryStringBuffer.toString(),parameterValues, parameterTypes);

			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				userValues = (Object[]) iter.next();
				user = new AimsUser();
				contact = new AimsContact();
				
				user.setUserId((Long) userValues[0]); // userId
				user.setUsername((String) userValues[1]); // userName
				user.setUserAccountStatus((String) userValues[2]); // userAccountStatus								
				contact.setFirstName((String) userValues[3]); // firstName
				contact.setLastName((String) userValues[4]); // lastName
				user.setAimsContact(contact);
				aimsAccounts.add(user);
			}

			log.debug("No of records returned in getAccountsByStatus: " + collection.size());

		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}

		return aimsAccounts;
	}     

  /**
	 * This static method gets the user accounts in the database which are
	 * available to the current user.
	 */
  public static Collection getVZWAccounts ( Long user_id, String user_type) throws HibernateException
  {
	Collection collection = null;
	Collection AimsAccounts = new ArrayList();
        Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	AimsUser user = null;
	AimsContact contact = null;
	Object [] parameterValues = null;
	Type [] parameterTypes = null;

    try
    {
		parameterValues = new Object [2];
		parameterTypes =  new Type [2];

		parameterValues[0] = user_type;
		parameterValues[1] = user_id;

		parameterTypes[0] = new StringType();
		parameterTypes[1] = new LongType();


		queryStringBuffer.append("select ")
						 .append("		user.userId, user.username, user.userAccountStatus, ")
						 .append("		user.userSessionStatus, user.createdBy, user.createdDate,  ")
						 .append("		contact.firstName, contact.lastName  ")
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsUser user  ")
						 .append("		inner join user.aimsContact contact ")
						 .append("where ")
						 .append("		user.userType = :userType ")
                                                 .append("		and user.userId <> :userId ");

     						queryStringBuffer.append(" order by contact.firstName, contact.lastName ");


        session = DBHelper.getInstance().getSession();
        
        collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

		for (Iterator iter = collection.iterator(); iter.hasNext();)
		{
			 userValues  = (Object []) iter.next();
			 user = new AimsUser();
			 contact = new AimsContact();

			 user.setUserId((Long) userValues[0]);				// userId
			 user.setUsername((String) userValues[1]);			// userName
			 user.setUserAccountStatus((String)userValues[2]);	// userAccountStatus
			 user.setUserSessionStatus((String)userValues[3]);	// userSessionStatus
			 user.setCreatedBy((String) userValues[4]);			// createdBy
			 user.setCreatedDate((Date) userValues[5]);		    // createdDate

			 contact.setFirstName((String) userValues[6]);		// firstName
			 contact.setLastName((String) userValues[7]);		// lastName

			 user.setAimsContact(contact);

			 AimsAccounts.add(user);
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

    return AimsAccounts;
  }





  /**
   *  This static method gets the user accounts in the database which are available
   *  to the current user.
   */
  public static AimsUser getAccount (Long get_user_id) throws HibernateException
  {


    Session session = null;
	Object [] userValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Collection collection = null;
	AimsUser aimsUser = null;
	AimsContact contact = null;

    try
    {

		queryStringBuffer.append("select ")
						 .append("		user.userId, user.username, user.userAccountStatus, ")
						 .append("		user.password, ")
						 .append("		user.userSessionStatus, user.createdBy, user.createdDate,  ")
						 .append("		contact.firstName, contact.lastName,  ")
					     .append("		contact.emailAddress, contact.title, ")
					     .append("		contact.phone, contact.mobile, contact.fax, ")
                         .append("		user.userType, user.aimsAllianc ")
                         .append("from ")
						 .append("		com.netpace.aims.model.core.AimsUser user  ")
						 .append("		inner join user.aimsContact contact ")
						 .append("where ")
						 .append("		user.userId = :userId ");


		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), get_user_id,  new LongType());


		for (Iterator iter = collection.iterator(); iter.hasNext();)
		{
			 userValues  = (Object []) iter.next();
			 aimsUser = new AimsUser();
			 contact = new AimsContact();

			 aimsUser.setUserId((Long) userValues[0]);				// userId
			 aimsUser.setUsername((String) userValues[1]);			// userName
			 aimsUser.setUserAccountStatus((String)userValues[2]);	// userAccountStatus
			 aimsUser.setPassword((String)userValues[3]);			// password
			 aimsUser.setUserSessionStatus((String)userValues[4]);	// userSessionStatus
			 aimsUser.setCreatedBy((String) userValues[5]);			// createdBy
			 aimsUser.setCreatedDate((Date) userValues[6]);		    // createdDate
			 
			 contact.setFirstName((String) userValues[7]);			// firstName
			 contact.setLastName((String) userValues[8]);			// lastName
			 contact.setEmailAddress((String) userValues[9]);		// emailAddress
             contact.setTitle((String) userValues[10]);		        // title
             contact.setPhone((String) userValues[11]);		        // phone
             contact.setMobile((String) userValues[12]);		    // mobile
             contact.setFax((String) userValues[13]);		        // fax
             
             aimsUser.setUserType((String) userValues[14]);         //userType
             aimsUser.setAimsAllianc((Long) userValues[15]);         //alliance id of user, if exists

             aimsUser.setAimsContact(contact);

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

    return aimsUser;
  }



  /**
    *  This static method gets the user accounts in the database which are available
    *  to the current user.
    *  this action is modified by hkhan@netpace.com
    *  for a modified requirment   
    */
    public static Collection getVZWAccounts (String search_expression,
		   									Long user_id, 
		   									String user_type, int PAGE_LENGTH, 
		   									int pageNo, 
		   									String[] statuses,
		   									boolean includeAccountManager
                                            ) throws HibernateException
    {
        Collection collection = null;
        Collection AimsAccounts = new ArrayList();
        Session session = null;
        Query squery = null;
        Object [] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsUser user = null;
        AimsContact contact = null;

        try
        {
            /*.append(" and user.userId != '" + Utility.convertToString(user_id) + "' ")*/

            queryStringBuffer
                            .append("select ")
                            .append("		user.userId, user.username, user.userAccountStatus, ")
                            .append("		user.userSessionStatus, user.createdBy, user.createdDate,  ")
                            .append("		contact.firstName, contact.lastName  ")
                            .append("from ")
                            .append("		com.netpace.aims.model.core.AimsUser user  ")
                            .append("		inner join user.aimsContact contact ")
                            .append("where ")
                            .append("		user.userType = :userType ");

            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL) == false)
            {
                queryStringBuffer
                            .append("		and user.userAccountStatus in (:statusId) ");
            }
            if (includeAccountManager)
            {
                queryStringBuffer
                        .append("           and user.isAccountManager in ('Y','y')");
            }
            if (search_expression!=null && search_expression.length() > 0)
            {
                queryStringBuffer
                        .append(search_expression);
            }

            queryStringBuffer.append(" order by lower(contact.firstName), lower(contact.lastName) ");

            session = DBHelper.getInstance().getSession();
	
            squery = session.createQuery(queryStringBuffer.toString());
            squery.setMaxResults(PAGE_LENGTH);
            squery.setFirstResult(PAGE_LENGTH * (pageNo - 1));

            squery.setString("userType", user_type);

            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL) == false)
            {
                squery.setParameterList("statusId", statuses);
            }

            collection = squery.list();

            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                userValues  = (Object []) iter.next();
                user = new AimsUser();
                contact = new AimsContact();

                user.setUserId((Long) userValues[0]);				// userId
                user.setUsername((String) userValues[1]);			// userName
                user.setUserAccountStatus((String)userValues[2]);	// userAccountStatus
                user.setUserSessionStatus((String)userValues[3]);	// userSessionStatus
                user.setCreatedBy((String) userValues[4]);			// createdBy
                user.setCreatedDate((Date) userValues[5]);		    // createdDate

                contact.setFirstName((String) userValues[6]);		// firstName
                contact.setLastName((String) userValues[7]);		// lastName

                user.setAimsContact(contact);

                AimsAccounts.add(user);
            }

            log.debug("No of records returned in getVZWAccounts: " + collection.size() );

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
        return AimsAccounts;
    }//end getVZWAccounts

   /**
       *  This static method gets the user accounts in the database which are available
       *  to the current user.
       */
    public static int getVZWAccountsCount (String search_expression,
                                           Long user_id,
                                           String user_type,
                                           String[] statuses,
                                           boolean isAccountManager
                                        ) throws HibernateException
    {
        Session session = null;
        Query squery = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        int rows = 0 ;

        try
        {
            queryStringBuffer
                        .append("select ")
                        .append("	count(*) ")
                        .append("from ")
                        .append("		com.netpace.aims.model.core.AimsUser user  ")
                        .append("		inner join user.aimsContact contact ")
                        .append("where ")
                        .append("		user.userType = :userType ")
                        .append("		and user.userId != :userId  ");

            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
                queryStringBuffer
                        .append("		and user.userAccountStatus in (:statusId) ");
            }
            if (isAccountManager){
                queryStringBuffer
                        .append("       and user.isAccountManager in ('Y','y')");
            }
            if (search_expression!=null && search_expression.length() > 0) {
                queryStringBuffer.append(search_expression);
            }

            session = DBHelper.getInstance().getSession();
            squery = session.createQuery(queryStringBuffer.toString());

            squery.setString("userType", user_type);
            squery.setLong("userId", Utility.ZeroValueReplacement(user_id).longValue());

            if(statuses!=null && statuses.length>0 && statuses[0].equalsIgnoreCase(AimsConstants.FILTER_SHOW_ALL)==false){
                squery.setParameterList("statusId", statuses);
            }

            rows = ( (Integer)squery.iterate().next()).intValue();
            log.debug("No of rows in getVZWAccountsCount: " + rows);

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
    }//end getVZWAccountsCount



    /**
   *  This static method deletes a user account represented by a given userid (primary_key)
   *  It returns the count (most probably 1) of the number of industry focus deleted.
   */
  public static int deleteAccount(int accountId, String currUserName) throws IntegrityConstraintException, HibernateException
  {

    java.sql.Connection ConOra = null;
    java.sql.CallableStatement statement = null;
    java.sql.ResultSet results = null;
	int delCount = 0;
    Session session = null;
    Transaction tx = null;
    try
    {

        session = DBHelper.getInstance().getSession();
	    tx = session.beginTransaction();

        ConOra = session.connection();
        statement = ConOra.prepareCall("{ call AIMS_ACCOUNTS.delete_account(?,?,?)}");

        statement.setInt(1,accountId);
        statement.setString(2,currUserName);
        statement.registerOutParameter(3,java.sql.Types.VARCHAR);

        statement.execute();
        statement.close();
        
        statement = ConOra.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

        statement.setInt(1,accountId);
        statement.setString(2,"D");            
        
        statement.execute();

        //String result_string = ((OracleCallableStatement) statement).getString(3);
	    tx.commit();




     }

    catch(java.sql.SQLException je)
    {
            if (tx!=null)
            {

                tx.rollback();
            }
            String exMessage = je.getMessage();
            if (exMessage.indexOf("violated - child record found") > -1)
            {
                throw new IntegrityConstraintException ();
            }

            else
            {
                je.printStackTrace();
                throw new HibernateException(je);
            }


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
        if (statement!=null)
        {
            try
            {
                statement.close();
            }
            catch (Exception ignore)
            {
            }
        }			
        session.close();
	}

    return delCount;
  }

  /**
   *  This static method returns a collection of type AimsSysPrivilege for a given user_type
   */
  public static Collection getUserTypeSysPrivileges (Long user_id, String user_type) throws HibernateException
  {
	Collection collection = null;
	Collection AimsSysPrivileges = new ArrayList();
    Session session = null;

	Object [] sysPrivilegeValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();

	AimsSysPrivilege sysPrivilege = null;


    try
    {

        session = DBHelper.getInstance().getSession();
        queryStringBuffer.append("select ")
                         .append("		sysPrivileges.privilegeId, sysPrivileges.privilegeName  ")
                         .append("from ")
                         .append("		com.netpace.aims.model.security.AimsSysPrivilege sysPrivileges  ")
                         .append("where ");
        if (user_type.equalsIgnoreCase("V"))
        {
            queryStringBuffer.append("		sysPrivileges.availableTo <> 'A' ");
        }
        if (user_type.equalsIgnoreCase("A"))
        {
            queryStringBuffer.append("		sysPrivileges.availableTo <> 'V' ");
        }

        queryStringBuffer.append("order by sysPrivileges.privilegeId ");

        collection = session.find(queryStringBuffer.toString());

        for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                 sysPrivilegeValues  = (Object []) iter.next();

                 sysPrivilege = new AimsSysPrivilege();

                 sysPrivilege.setPrivilegeId((Long) sysPrivilegeValues[0]);
                 sysPrivilege.setPrivilegeName((String) sysPrivilegeValues[1]);

                 AimsSysPrivileges.add(sysPrivilege);
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

    return AimsSysPrivileges;
  }

  /**
   *  This static method returns a collection of roles of type AimsSysRole for a given user_type
   */
  public static Collection getAllAvailableRoles (Long user_id, String user_type) throws HibernateException
  {
	Collection collection = null;
	Collection AimsSysRoles = new ArrayList();
    Session session = null;

	Object [] sysRoleValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
    Object [] parameterValues = null;
    Type [] parameterTypes = null;

	AimsSysRole sysRole = null;


    try
    {

        session = DBHelper.getInstance().getSession();

        queryStringBuffer.append("select ")
                         .append("		sysRole.roleId, sysRole.roleName  ")
                         .append("from ")
                         .append("		com.netpace.aims.model.security.AimsSysRole sysRole  ")
                         .append("where ")
                         .append("		sysRole.roleType = :userType ")
						 .append("		and sysRole.roleName <> 'SUPERUSER' ")
                         .append("order by sysRole.roleName ");

        collection = session.find(queryStringBuffer.toString(), user_type, new StringType());

        for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                 sysRoleValues  = (Object []) iter.next();

                 sysRole = new AimsSysRole();

                 sysRole.setRoleId((Long) sysRoleValues[0]);
                 sysRole.setRoleName((String) sysRoleValues[1]);

                 AimsSysRoles.add(sysRole);
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

    return AimsSysRoles;
  }

  /**
   *  This static method returns a collection of roles of type AimsSysRole for a given user.
   *  It does not get roles which are already assigned to the user.
   */
  public static Collection getAvailableRoles (Long user_id, String user_type) throws HibernateException
  {
	Collection collection = null;
	Collection AimsSysRoles = new ArrayList();
    Session session = null;

	Object [] sysRoleValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
    Object [] parameterValues = null;
    Type [] parameterTypes = null;

	AimsSysRole sysRole = null;


    try
    {

        session = DBHelper.getInstance().getSession();
		parameterValues = new Object [2];
		parameterTypes =  new Type [2];

		parameterValues[0] = user_type;
		parameterValues[1] = user_id;


		parameterTypes[0] = new StringType();
		parameterTypes[1] = new LongType();

        queryStringBuffer.append("select ")
                         .append("		sysRole.roleId, sysRole.roleName  ")
                         .append("from ")
                         .append("		com.netpace.aims.model.security.AimsSysRole sysRole  ")
                         .append("where ")
                         .append("		sysRole.roleType = :userType ")
						 .append("		and sysRole.roleName <> 'SUPERUSER' ")
                         .append("		and sysRole.roleId not in ( ")
		                 .append("select ")
						 .append("		inner_role.roleId ")
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsUser user ")
						 .append("		inner join user.roles inner_role ")
						 .append("where ")
						 .append("		user.userId = :user_id ")
                         .append("		                          ) ")
                         .append("order by sysRole.roleName ");

        collection = session.find(queryStringBuffer.toString(), parameterValues, parameterTypes);

        for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                 sysRoleValues  = (Object []) iter.next();

                 sysRole = new AimsSysRole();

                 sysRole.setRoleId((Long) sysRoleValues[0]);
                 sysRole.setRoleName((String) sysRoleValues[1]);

                 AimsSysRoles.add(sysRole);
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

    return AimsSysRoles;
  }

  /**
   *  This static method returns a collection of roles of type AimsSysRole assingned to a given user_id
   */
  public static Collection getAssignedRoles (Long user_id, String user_type) throws HibernateException
  {
	Collection collection = null;
	Collection AimsSysRoles = new ArrayList();
    Session session = null;

	Object [] sysRoleValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();

	AimsSysRole sysRole = null;


    try
    {

        session = DBHelper.getInstance().getSession();

		queryStringBuffer.append("select ")
						 .append("		role.roleId, role.roleName ")
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsUser user ")
						 .append("		inner join user.roles role ")
						 .append("where ")
						 .append("		user.userId = :user_id ");

        collection = session.find(queryStringBuffer.toString(), user_id, new LongType());

        for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                 sysRoleValues  = (Object []) iter.next();

                 sysRole = new AimsSysRole();

                 sysRole.setRoleId((Long) sysRoleValues[0]);
                 sysRole.setRoleName((String) sysRoleValues[1]);

                 AimsSysRoles.add(sysRole);
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

    return AimsSysRoles;
  }

  /**
   *  This static method returns a collection of type AimsRolePrivilege for a given user
   */
  public static Collection getUserRolePrivileges (Long get_user_id) throws HibernateException
  {
	Collection AimsRolePrivileges = new HashSet();
    Session session = null;

	StringBuffer queryStringBuffer = new StringBuffer();

	AimsRolePrivilege rolePrivilege = null;
	AimsSysRole role = null;
	Long roleId = null;


    try
    {

        session = DBHelper.getInstance().getSession();

		queryStringBuffer.append("select ")
						 .append("		elements(user.roles) ")
						 .append("from ")
						 .append("		com.netpace.aims.model.core.AimsUser user  ")
						 .append("where ")
						 .append("		user.userId = :userId ");

		Collection collection = session.find(queryStringBuffer.toString(), get_user_id, new LongType());

        for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                 role  = (AimsSysRole) iter.next();
            }
        if (role != null)
        {
            roleId = role.getRoleId();
            log.debug("roleId returned: " +roleId );

            AimsRolePrivileges = role.getRolePrivileges();
        }

		    log.debug("No of roles returned: " + collection.size() );

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

    return AimsRolePrivileges;
  }

	/**
	*  This static method updates a given contact, user, user privilege, role privilege
	*/
	public static AimsUser saveOrUpdateUserRolePrivilege(
                                                        String username,
                                                        String password,
                                                        String accountStatus,
                                                        String firstName,
                                                        String lastName,
                                                        String emailAddress,
														String title,
														String phone,
														String mobile,
														String fax,
													    String currUserName,
                                                        String currUserType,
														Long currAllianceId,
                                                        Long aimsRoleId
                                                    ) throws UniqueConstraintException, HibernateException
	{
		log.debug("************************---saveOrUpdateUserRolePrivilege method called");

		Session session = null;
        Transaction tx = null;

		try
		{

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            //Check that the email does not already exists
            Query userQry= session.createQuery("from AimsUser u where lower(u.username)=:email");            
            userQry.setString("email", username.toLowerCase());
            List list=userQry.list();
            if (list != null && list.size()>0){
            	UniqueConstraintException uce = new UniqueConstraintException();
            	throw uce;
            }

            AimsContact	contact	= new	AimsContact();
			contact.setCreatedBy(currUserName);
			contact.setCreatedDate(new Date());
			contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setEmailAddress(emailAddress);
            contact.setTitle(title);
            contact.setPhone(phone);
            contact.setMobile(mobile);
            contact.setFax(fax);
            contact.setType("AU");
            contact.setLastUpdatedBy(currUserName);
            contact.setLastUpdatedDate(new Date());

            session.saveOrUpdate(contact);

            AimsUser aimsUser = new AimsUser();
            aimsUser.setUsername(username);
            aimsUser.setPassword(password);
            aimsUser.setUserAccountStatus(accountStatus);
            aimsUser.setUserType(currUserType);
            aimsUser.setAimsContact(contact);
			aimsUser.setCreatedBy(currUserName);
			aimsUser.setAimsAllianc(currAllianceId);
            aimsUser.setCreatedDate(new Date());
			aimsUser.setLastUpdatedBy(currUserName);
			aimsUser.setLastUpdatedDate(new Date());
            aimsUser.setNotificationType("E");
            aimsUser.setRowsLength(new Long(10));

            if ( (aimsRoleId != null) && (aimsRoleId.longValue() != 0) )
			{
                Set roleSet = new HashSet();
			    roleSet.add(getRole(aimsRoleId));
                aimsUser.setRoles(roleSet);
                session.save(aimsUser);
            }
						
            session.save(aimsUser);
			tx.commit();
			return aimsUser;
		 }

        catch(JDBCException je)
        {
                if (tx!=null)
                {

                    tx.rollback();
                }
                String exMessage = je.getMessage();
                if (exMessage.indexOf("ORA-00001: unique constraint") > -1)
                {
                    je.printStackTrace();
                    throw new UniqueConstraintException ();

                }

                else
                {
                    je.printStackTrace();
                    throw new HibernateException(je);
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

		 finally
		 {
		  session.close();
		 }
	}


	/**
	*  This static method updates a given contact, user, user privilege, role privilege
	*/
	public static void UpdateUserRolePrivilege(
                                                           int userId,
                                                        String username,
                                                        String password,
                                                        String accountStatus,
                                                        String firstName,
                                                        String lastName,
                                                        String emailAddress,
                                                        String title,
                                                        String phone,
                                                        String mobile,
                                                        String fax,
                                                        String currUserName,
                                                        String currUserType,
														Long currAllianceId,
                                                        Long aimsRoleId,
                                                        boolean revokeOffersByUser
                                                    ) throws UniqueConstraintException, HibernateException, Exception
	{
		log.debug("************************---UpdateUserRolePrivilege method called");
		Session session = null;
        Transaction tx = null;

        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;
        java.sql.ResultSet results = null;

		try
		{

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
		
            
            ConOra = session.connection();
            statement = ConOra.prepareCall("{ call AIMS_ACCOUNTS.update_account(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						
            statement.setInt(1,userId);
            statement.setString(2,username);
            statement.setString(3,password);
            statement.setString(4,accountStatus);
            statement.setString(5,firstName);
            statement.setString(6,lastName);
            statement.setString(7,emailAddress);
            statement.setString(8,title);
            statement.setString(9,phone);
            statement.setString(10,mobile);
            statement.setString(11,fax);
            statement.setString(12,aimsRoleId.toString());
            statement.setString(13,currUserName);
            statement.registerOutParameter(14,java.sql.Types.VARCHAR);
						
            statement.execute();

            if(revokeOffersByUser)
            {
                AimsUserOfferManager.revokeOffersByUser(username, session);
            }

            String result_string = ((OracleCallableStatement) statement).getString(14);
						
            tx.commit();
		 }
				
        catch(java.sql.SQLException je)
        {
                if (tx!=null)
                {

                    tx.rollback();
                }
                String exMessage = je.getMessage();
                if (exMessage.indexOf("ORA-00001: unique constraint") > -1)
                {
                    je.printStackTrace();
                    throw new UniqueConstraintException ();
                }

                else
                {
                    je.printStackTrace();
                    throw new HibernateException(je);
                }


        }
			
		 catch(HibernateException he)
		 {

				if (tx != null)
				{

					tx.rollback();
				}
				he.printStackTrace();
				throw he;
		 }

        catch(Exception e)
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
            if (statement != null)
            {

               try
               {
               	    statement.close();
               }
               catch (Exception ignore)
               {
                   ignore.printStackTrace();
               }
                
            }
		    session.close();
            log.debug("Session closed in AimsAccountsManager.UpdateUserRolePrivilege()");
         }
	}


	/**
	*  This static method updates a given contact, user, user roles
	*/
	public static void UpdateUserRoles(
                                                           int userId,
                                                        String username,
                                                        String password,
                                                        String accountStatus,
                                                        String firstName,
                                                        String lastName,
                                                        String emailAddress,
                                                        String title,
                                                        String phone,
                                                        String mobile,
                                                        String fax,
                                                        String currUserName,
                                                        String currUserType,
                                                        String [] selectedRoles
                                                    ) throws UniqueConstraintException, HibernateException
	{
		log.debug("************************---UpdateUserRoles method called");
        Session session = null;
        Transaction tx = null;

        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;
        java.sql.ResultSet results = null;

		try
		{

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            ConOra = session.connection();
            statement = ConOra.prepareCall("{ call AIMS_ACCOUNTS.update_vzw_account(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

            statement.setInt(1,userId);
            statement.setString(2,username);
            statement.setString(3,password);
            statement.setString(4,accountStatus);
            statement.setString(5,firstName);
            statement.setString(6,lastName);
            statement.setString(7,emailAddress);
            statement.setString(8,title);
            statement.setString(9,phone);
            statement.setString(10,mobile);
            statement.setString(11,fax);
            statement.setString(12, StringFuncs.ConvertArrToString(selectedRoles, ","));
            statement.setString(13,currUserName);
            statement.registerOutParameter(14,java.sql.Types.VARCHAR);

            statement.execute();

            String result_string = ((OracleCallableStatement) statement).getString(14);           
            statement.close();

            tx.commit();

            statement = ConOra.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

            statement.setInt(1, userId);
            if (AimsConstants.USER_STATUS_DELETED.equals(accountStatus) || AimsConstants.USER_STATUS_ONHOLD.equals(accountStatus)){
            	statement.setString(2,"D");
            }
            else {
            	statement.setString(2,"U");
            }
                        
            
            statement.execute();

            tx.commit();
		 }

        catch(java.sql.SQLException je)
        {
                if (tx!=null)
                {

                    tx.rollback();
                }
                String exMessage = je.getMessage();
                if (exMessage.indexOf("ORA-00001: unique constraint") > -1)
                {
                    je.printStackTrace();
                    throw new UniqueConstraintException ();
                }

                else
                {
                    je.printStackTrace();
                    throw new HibernateException(je);
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

		 finally
		 {
            if (statement!=null)
            {
                try
                {
                	statement.close();
                }
                catch (Exception ignore)
                {
                }
                
            }			  
          session.close();
		 }
	}
	
	
	public static void UpdateUserRolesWithPermanentDelete(int userId, String username,
			String password, String accountStatus, String firstName,
			String lastName, String emailAddress, String title, String phone,
			String mobile, String fax, String currUserName,
			String currUserType, String[] selectedRoles,String permanentDelete)
			throws UniqueConstraintException, HibernateException {
		log.debug("************************---UpdateUserRolesWithPermanentDelete method called");
		Session session = null;
		Transaction tx = null;

		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		java.sql.ResultSet results = null;

		try {

			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			ConOra = session.connection();
			statement = ConOra
					.prepareCall("{ call AIMS_ACCOUNTS.update_vzw_account(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			log.debug("in manager final user email : "+userId+ "for "+username);
			statement.setInt(1, userId);
			statement.setString(2, username);
			statement.setString(3, password);
			statement.setString(4, accountStatus);
			statement.setString(5, firstName);
			statement.setString(6, lastName);
			statement.setString(7, emailAddress);
			statement.setString(8, title);
			statement.setString(9, phone);
			statement.setString(10, mobile);
			statement.setString(11, fax);
			statement.setString(12, StringFuncs.ConvertArrToString(selectedRoles, ","));
			statement.setString(13, currUserName);
			statement.setString(14, permanentDelete);
			statement.registerOutParameter(15, java.sql.Types.VARCHAR);

			statement.execute();

			String result_string = ((OracleCallableStatement) statement)
					.getString(15);
			statement.close();

			tx.commit();
			log.debug("trx commiting -----");
			statement = ConOra
					.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

			statement.setInt(1, userId);
			if (AimsConstants.USER_STATUS_DELETED.equals(accountStatus)
					|| AimsConstants.USER_STATUS_ONHOLD.equals(accountStatus)) {
				statement.setString(2, "D");
			} else {
				statement.setString(2, "U");
			}

			statement.execute();

			tx.commit();
		}

		catch (java.sql.SQLException je) {
			if (tx != null) {

				tx.rollback();
			}
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
				je.printStackTrace();
				throw new UniqueConstraintException();
			}

			else {
				je.printStackTrace();
				throw new HibernateException(je);
			}

		}

		catch (HibernateException e) {

			if (tx != null) {

				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}

		finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ignore) {
				}

			}
			session.close();
			log.debug("session closed -----");
		}
	}
	
	
	public static void UpdateUserRolesWithoutTransactionCommit(int userId, String username,
			String password, String accountStatus, String firstName,
			String lastName, String emailAddress, String title, String phone,
			String mobile, String fax, String currUserName,
			String currUserType, String[] selectedRoles, Session session)
			throws UniqueConstraintException, HibernateException {
		log.debug("************************---UpdateUserRolesWithoutTransactionCommit method called");
		
		

		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		java.sql.ResultSet results = null;

		try {

			session = DBHelper.getInstance().getSession();
			

			ConOra = session.connection();
			statement = ConOra
					.prepareCall("{ call AIMS_ACCOUNTS.update_vzw_account(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			statement.setInt(1, userId);
			statement.setString(2, username);
			statement.setString(3, password);
			statement.setString(4, accountStatus);
			statement.setString(5, firstName);
			statement.setString(6, lastName);
			statement.setString(7, emailAddress);
			statement.setString(8, title);
			statement.setString(9, phone);
			statement.setString(10, mobile);
			statement.setString(11, fax);
			statement.setString(12, StringFuncs.ConvertArrToString(
					selectedRoles, ","));
			statement.setString(13, currUserName);
			statement.registerOutParameter(14, java.sql.Types.VARCHAR);

			statement.execute();

			String result_string = ((OracleCallableStatement) statement)
					.getString(14);
			statement.close();



			statement = ConOra
					.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

			statement.setInt(1, userId);
			if (AimsConstants.USER_STATUS_DELETED.equals(accountStatus)
					|| AimsConstants.USER_STATUS_ONHOLD.equals(accountStatus)) {
				statement.setString(2, "D");
			} else {
				statement.setString(2, "U");
			}

			statement.execute();


		}

		catch (java.sql.SQLException je) {
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
				je.printStackTrace();
				throw new UniqueConstraintException();
			}

			else {
				je.printStackTrace();
				throw new HibernateException(je);
			}

		}

		catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}

		finally {
			if (statement != null) {
				try {
					statement.close();
					log.debug("closing the statement");
				} catch (Exception ignore) {
					ignore.printStackTrace();
				}

			}
			//session.flush();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void changePassword(Long userId, String newPassword) throws HibernateException, AimsException{

		Session session = null;
		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		Transaction tx = null;

		try {
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			AimsUser user=(AimsUser)session.load(AimsUser.class, userId);

			user.setPassword(newPassword);
			session.saveOrUpdate(user);
			tx.commit();

			ConOra = session.connection();
			statement = ConOra.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

			statement.setLong(1, user.getUserId().longValue());
			statement.setString(2, "U");
			statement.execute();

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}			
			e.printStackTrace();
			throw e;
		} catch (java.sql.SQLException sqle) {
			if (tx != null) {
				tx.rollback();
			}
			else {
				sqle.printStackTrace();
				throw new HibernateException(sqle);
			}

		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ignore) {
				}
			}
			session.close();
		}
	}
  

	/**
	*  This static method updates a given contact, user, user roles
	*/
	public static void CreatUserRoles(

                                        String username,
                                        String password,
                                        String accountStatus,
                                        String firstName,
                                        String lastName,
                                        String emailAddress,
                                        String title,
										String phone,
										String mobile,
										String fax,
                                        String currUserName,
                                        String currUserType,
                                        String [] selectedRoles
                                    ) throws UniqueConstraintException, HibernateException
	{

		Session session = null;
        Transaction tx = null;

        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;
        java.sql.ResultSet results = null;

		try
		{

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            //Check that the email does not already exists
            Query userQry= session.createQuery("from AimsUser u where lower(u.username)=:email");            
            userQry.setString("email", username.toLowerCase());
            List list=userQry.list();
            if (list != null && list.size()>0){
            	UniqueConstraintException uce = new UniqueConstraintException();
            	throw uce;
            }
            
            AimsContact contact = new AimsContact();
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setEmailAddress(emailAddress);
            contact.setTitle(title);
            contact.setPhone(phone);
            contact.setMobile(mobile);
            contact.setFax(fax);
            contact.setType("AU");
            contact.setCreatedBy(currUserName);
            contact.setCreatedDate(new Date());
            contact.setLastUpdatedBy(currUserName);
            contact.setLastUpdatedDate(new Date());

            session.saveOrUpdate(contact);


            AimsUser aimsUser = new AimsUser();
            aimsUser.setUsername(username);
            aimsUser.setPassword(password);
            aimsUser.setUserAccountStatus(accountStatus);
            aimsUser.setUserType(currUserType);
            aimsUser.setAimsContact(contact);
			aimsUser.setCreatedBy(currUserName);
            aimsUser.setCreatedDate(new Date());
			aimsUser.setLastUpdatedBy(currUserName);
			aimsUser.setLastUpdatedDate(new Date());
            aimsUser.setIfBlocked("N");
            aimsUser.setNotificationType("E");
            aimsUser.setRowsLength(new Long(10));

            if (selectedRoles != null)
            {
                Set roleSet = new HashSet();
                AimsSysRole aimsRole = null;

                for (int i=0; i<selectedRoles.length; i++)
                {
                    aimsRole = (AimsSysRole) session.load(AimsSysRole.class, new Long(selectedRoles[i]));
                    roleSet.add(aimsRole);
                }

                aimsUser.setRoles(roleSet);
            }

            session.save(aimsUser);
            tx.commit();
            
            //During creation user can select hold/delete.
            if (AimsConstants.USER_STATUS_ACTIVE.equals(accountStatus)){ 
	            ConOra = session.connection();
	            statement = ConOra.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");
	
	            statement.setLong(1,aimsUser.getUserId().longValue());
	            statement.setString(2,"I");            
	            
	            statement.execute();
	          
	            tx.commit();
            }
		 }

        catch(JDBCException je)
        {
                if (tx!=null)
                {

                    tx.rollback();
                }
                String exMessage = je.getMessage();
                if (exMessage.indexOf("ORA-00001: unique constraint") > -1)
                {
                    je.printStackTrace();
                    throw new UniqueConstraintException ();
                }

                else
                {
                    je.printStackTrace();
                    throw new HibernateException(je);
                }


        }

        catch(java.sql.SQLException sqle)
        {
                if (tx!=null)
                {

                    tx.rollback();
                }
                String exMessage = sqle.getMessage();
                if (exMessage.indexOf("ORA-00001: unique constraint") > -1)
                {
                    sqle.printStackTrace();
                    throw new UniqueConstraintException ();
                }

                else
                {
                    sqle.printStackTrace();
                    throw new HibernateException(sqle);
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

		 finally
		 {
            if (statement!=null)
            {
                try
                {
                	statement.close();
                }
                catch (Exception ignore)
                {
                }
            }		  
            session.close();
            log.debug("session is closing");
		 }
	}

	
	public static void CreatUserRolesWithoutTransaction(

	String username, String password, String accountStatus, String firstName,
			String lastName, String emailAddress, String title, String phone,
			String mobile, String fax, String currUserName,
			String currUserType, String[] selectedRoles, Long aimsAllianc )
			throws UniqueConstraintException, HibernateException {

		

		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		java.sql.ResultSet results = null;
		Session	session	=	null;
		Transaction tx = null;
		try {

			session	=	DBHelper.getInstance().getSession();
			tx= session.beginTransaction();
			// Check that the email does not already exists
			Query userQry = session
					.createQuery("from AimsUser u where lower(u.username)=:email");
			userQry.setString("email", username.toLowerCase());
			List list = userQry.list();
			if (list != null && list.size() > 0) {
				UniqueConstraintException uce = new UniqueConstraintException();
				throw uce;
			}

			AimsContact contact = new AimsContact();
			contact.setFirstName(firstName);
			contact.setLastName(lastName);
			contact.setEmailAddress(emailAddress);
			contact.setTitle(title);
			contact.setPhone(phone);
			contact.setMobile(mobile);
			contact.setFax(fax);
			contact.setType("AU");
			contact.setCreatedBy(currUserName);
			contact.setCreatedDate(new Date());
			contact.setLastUpdatedBy(currUserName);
			contact.setLastUpdatedDate(new Date());
			AimsAllianc allaince = new AimsAllianc();
			allaince.setAllianceId(aimsAllianc);
			contact.setAlliance(allaince);

			session.saveOrUpdate(contact);

			AimsUser aimsUser = new AimsUser();
			aimsUser.setUsername(username);
			aimsUser.setPassword(password);
			aimsUser.setUserAccountStatus(accountStatus);
			aimsUser.setUserType(currUserType);
			aimsUser.setAimsContact(contact);
			aimsUser.setCreatedBy(currUserName);
			aimsUser.setCreatedDate(new Date());
			aimsUser.setLastUpdatedBy(currUserName);
			aimsUser.setLastUpdatedDate(new Date());
			aimsUser.setIfBlocked("N");
			aimsUser.setNotificationType("E");
			aimsUser.setRowsLength(new Long(10));
			aimsUser.setAimsAllianc(aimsAllianc);

			if (selectedRoles != null) {
				Set roleSet = new HashSet();
				AimsSysRole aimsRole = null;

				for (int i = 0; i < selectedRoles.length; i++) {
					aimsRole = (AimsSysRole) session.load(AimsSysRole.class,
							new Long(selectedRoles[i]));
					roleSet.add(aimsRole);
				}

				aimsUser.setRoles(roleSet);
			}

			session.save(aimsUser);
			tx.commit();

			// During creation user can select hold/delete.
			if (AimsConstants.USER_STATUS_ACTIVE.equals(accountStatus)) {
				ConOra = session.connection();
				statement = ConOra
						.prepareCall("{ call AIMS_OPEN_REPORTS.sync_report_account(?,?)}");

				statement.setLong(1, aimsUser.getUserId().longValue());
				statement.setString(2, "I");

				statement.execute();


			}
		}

		catch (JDBCException je) {
			je.printStackTrace();
			String exMessage = je.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
				je.printStackTrace();
				throw new UniqueConstraintException();
			}

			else {
				je.printStackTrace();
				throw new HibernateException(je);
			}

		}

		catch (java.sql.SQLException sqle) {
			sqle.printStackTrace();
			String exMessage = sqle.getMessage();
			if (exMessage.indexOf("ORA-00001: unique constraint") > -1) {
				sqle.printStackTrace();
				throw new UniqueConstraintException();
			}

			else {
				sqle.printStackTrace();
				throw new HibernateException(sqle);
			}

		}

		catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}

		finally {
			if (statement != null) {
				try {
					log.debug("---closing statement");
					statement.close();
				} catch (Exception ignore) {ignore.printStackTrace();
				}
			}
				session.close();
		}
	}
	
	
	
	
	
	public static	Collection	getAllianceRoles(Long allianceId)	throws HibernateException
	{		
		Collection collection	=	null;		
		Session	session	=	null;
		try
		{					
			session	=	DBHelper.getInstance().getSession();
			StringBuffer queryString = new StringBuffer();
			
			if (allianceId!=null)
			{
				queryString.append ("		select	distinct role ")
									.append	("		from		com.netpace.aims.model.security.AimsSysRole	role	")
									.append	("		where		")
									.append ("				role.roleId	!= 1 ")
									.append ("				and	role.roleType like '%A%'	"	);
									
				queryString.append ("		order by role.roleName ");
			
				Query	query	=	session.createQuery(queryString.toString());
							
				collection = query.list();
				log.debug("No	of records returned: " + collection.size() );				
			}
		}	
		catch(HibernateException e)
		{			
			e.printStackTrace();
			throw	e;
		}
		finally
		{
			session.close();
		}

		return collection;
	}	 

	public static	AimsContact	getContact(Long	contactId)	throws HibernateException
	{
		
		AimsContact	contact	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Query	query	=	session.createQuery("select	from com.netpace.aims.model.core.AimsContact as	contact	where	contact.contactId	=	:contactId");
			query.setLong("contactId", contactId.longValue());
		
			for	(Iterator	it = query.iterate();	it.hasNext();) 
			{
				contact	=	(AimsContact)	it.next();
			}
		 }
		 catch(HibernateException	e)
		 {
			e.printStackTrace();
			throw	e;
		 }
		 finally
		 {
			session.close();
		 }

		return contact;
	}	 
	
	public static	AimsSysRole	getRole(Long	roleId)	throws HibernateException
	{
		
		AimsSysRole	role	=	null;
		Session	session	=	null;
		try
		{
			session	=	DBHelper.getInstance().getSession();
			Query	query	=	session.createQuery("select	from com.netpace.aims.model.security.AimsSysRole as	role	where	role.roleId	=	:roleId");
			query.setLong("roleId", roleId.longValue());
		
			for	(Iterator	it = query.iterate();	it.hasNext();) 
			{
				role	=	(AimsSysRole)	it.next();
			}
		 }
		 catch(HibernateException	e)
		 {
			e.printStackTrace();
			throw	e;
		 }
		 finally
		 {
			session.close();
		 }

		return role;
	}
	
	public static void setAccountManager(Long userId, boolean isActive) throws HibernateException {
		Session session = null;
		Transaction trx=null;
		try {
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			AimsUser user=(AimsUser)session.load(AimsUser.class, userId);
			if (isActive){
				user.setIsAccountManager("Y");
			}
			else {
				user.setIsAccountManager("N");
			}
			session.update(user);
			trx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (trx !=null){
				trx.rollback();
			}
			throw e;
		} finally {
			if(session !=null){
				session.close();
			}
		}
	}

    /**
     * checks whether current user has access to change information of dbAccountUser
     * @param request
     * @param currUser
     * @param dbAccountUser
     * @return
     */
    public static boolean checkCurrentUserAccessPrivilegeForUser(HttpServletRequest request, AimsUser currUser, AimsUser dbAccountUser) {
        String dbAccountUserType = StringFuncs.NullValueReplacement(dbAccountUser.getUserType());
        boolean allowed = false;

        //vzw user can change vzw users only and alliance user can change alliance users (users of same alliance)
        //user type must be same
        if(dbAccountUserType.equals(currUser.getUserType()))
        {
            if(dbAccountUserType.equalsIgnoreCase(AimsConstants.VZW_USERTYPE))
            {
                //if userType is verizon and has access to change users
                if ((AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.USERS, AimsSecurityManager.UPDATE)))
                {
                    allowed = true;
                }
            }
            else if(dbAccountUserType.equalsIgnoreCase(AimsConstants.ALLIANCE_USERTYPE))
            {
                //if userType is alliance and has access to change alliance users
                if ((AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALLIANCE_USERS, AimsSecurityManager.UPDATE)))
                {
                    //if current user alliance id matches db account alliance id
                    //as alliance user can only change same alliance users
                    if((Utility.ZeroValueReplacement(dbAccountUser.getAimsAllianc()).longValue()>0)
                            && (Utility.ZeroValueReplacement(currUser.getAimsAllianc()).longValue()>0)
                            && (dbAccountUser.getAimsAllianc().longValue() == currUser.getAimsAllianc().longValue()))
                    {
                        allowed = true;
                    }
                }
            }
        }
        return allowed;
    }//end checkUserAccessPrivilege
    
    public static AimsUser validateUser(String username) throws InvalidLoginException, HibernateException
    {

        AimsUser user = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query =
                session.createQuery(
                    "select from com.netpace.aims.model.core.AimsUser as user where lower(user.username) = :username  and user.userAccountStatus = :useraccountstatus");
            query.setString("username", username.toLowerCase().trim());
            //query.setString("password", password);
            query.setString("useraccountstatus", AimsConstants.USER_STATUS_ACTIVE);

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                user = (AimsUser) it.next();
                log.debug("User: " + user.toString());
            }

            //TODO Remove this hack once the User->UserRoles->Roles->RolePribvileges brings in the desired data.
            //Getting the roles to set them back in correctly.
            if (user != null)
            {
                Set roles = user.getRoles();
                AimsSysRole tempRole = null;
                AimsSysRole role = null;
                HashSet newSet = new HashSet();
                Iterator rolesIter = roles.iterator();
                while (rolesIter.hasNext())
                {
                    role = (AimsSysRole) rolesIter.next();
                    try
                    {
                        tempRole = (AimsSysRole) DBHelper.getInstance().load(com.netpace.aims.model.security.AimsSysRole.class, role.getRoleId().toString());
                    }
                    catch (Exception ex){}
                    newSet.add(tempRole);
                }
                //Setting the correct set of Roles back in
                user.setRoles(newSet);
            }
        }catch (HibernateException e){
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
        
        return user;
    }
    
    
    public static AimsUser validateAnyUser(String username) throws InvalidLoginException, HibernateException
    {

        AimsUser user = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query =
                session.createQuery(
                    "select from com.netpace.aims.model.core.AimsUser as user where lower(user.username) = :username");
            query.setString("username", username.toLowerCase().trim());
            //query.setString("password", password);
            

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                user = (AimsUser) it.next();
                log.debug("User: " + user.toString());
            }

            //TODO Remove this hack once the User->UserRoles->Roles->RolePribvileges brings in the desired data.
            //Getting the roles to set them back in correctly.
            if (user != null)
            {
                Set roles = user.getRoles();
                AimsSysRole tempRole = null;
                AimsSysRole role = null;
                HashSet newSet = new HashSet();
                Iterator rolesIter = roles.iterator();
                while (rolesIter.hasNext())
                {
                    role = (AimsSysRole) rolesIter.next();
                    try
                    {
                        tempRole = (AimsSysRole) DBHelper.getInstance().load(com.netpace.aims.model.security.AimsSysRole.class, role.getRoleId().toString());
                    }
                    catch (Exception ex){}
                    newSet.add(tempRole);
                }
                //Setting the correct set of Roles back in
                user.setRoles(newSet);
            }
        }catch (HibernateException e){
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
        
        return user;
    }
    

}