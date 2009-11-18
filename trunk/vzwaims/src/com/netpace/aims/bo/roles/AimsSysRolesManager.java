package com.netpace.aims.bo.roles;

import net.sf.hibernate.*;
import net.sf.hibernate.type.*;
import net.sf.hibernate.HibernateException;

import com.netpace.aims.model.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.security.*;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.bo.accounts.*;

import com.netpace.aims.util.*;

import org.apache.log4j.Logger;


import oracle.jdbc.driver.*;

import java.util.*;

 
/**
 * This class is responsible for getting the BO for roles.
 * It has static methods for getting the roles.
 * @author Rizwan Qazi
 */
public class AimsSysRolesManager
{

  static Logger log = Logger.getLogger(AimsSysRolesManager.class.getName());

  /**
   *  This static method gets the user accounts in the database which are available
   *  to the current user.
   */
  public static Collection getSysRoles (Long user_id, String user_type) throws HibernateException
  {    
	Collection collection = null;   
	Collection AimsSysRoles = new ArrayList();
    Session session = null;
	Object [] roleValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	AimsSysRole role = null;


    try
    { 
		queryStringBuffer.append("select ")
						 .append("		role.roleId, role.roleName,  ")
						 .append("		role.roleDescription, role.roleType ")
						 .append("from ")
						 .append("		com.netpace.aims.model.security.AimsSysRole role  ")					
						 .append("where ")
						 .append("		role.roleType <> 'A' and role.roleName <> 'SUPERUSER' ")							
                         .append("order by role.roleName ");

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString());

		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 roleValues  = (Object []) iter.next();		
			 role = new AimsSysRole();	

			 role.setRoleId((Long) roleValues[0]);				// roleId
			 role.setRoleName((String) roleValues[1]);			// roleName
			 role.setRoleDescription((String)roleValues[2]);	// roleDescription
			 role.setRoleType((String)roleValues[3]);			// roleType

		     AimsSysRoles.add(role);	
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
   *  This static method gets the role in the database for editing
   */
  public static AimsSysRole getSysRole (Long get_role_id) throws HibernateException
  {    
 
	
    Session session = null;
	Object [] roleValues = null;
	StringBuffer queryStringBuffer = new StringBuffer();
	Collection collection = null;   
	AimsSysRole aimsSysRole = null;

    try
    { 

		queryStringBuffer.append("select ")
						 .append("		role.roleId, role.roleName,  ")
						 .append("		role.roleDescription, role.roleType ")
						 .append("from ")
						 .append("		com.netpace.aims.model.security.AimsSysRole role  ")					
						 .append("where ")
						 .append("		role.roleId = :roleId ");
   

		session = DBHelper.getInstance().getSession();

        collection = session.find(queryStringBuffer.toString(), get_role_id,  new LongType());


		for (Iterator iter = collection.iterator(); iter.hasNext();) 
		{
			 roleValues  = (Object []) iter.next();		
			 aimsSysRole = new AimsSysRole();
			 
			 aimsSysRole.setRoleId((Long) roleValues[0]);				// roleId
			 aimsSysRole.setRoleName((String) roleValues[1]);			// roleName
			 aimsSysRole.setRoleDescription((String)roleValues[2]);	// roleDescription
			 aimsSysRole.setRoleType((String)roleValues[3]);			// roleType		

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

    return aimsSysRole;
  }  


  /**
   *  This static method gets the give role id from the database 
   */

  public static int deleteSysRole(int roleId, String currUserName) throws IntegrityConstraintException, HibernateException
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
        statement = ConOra.prepareCall("{ call AIMS_ROLES.delete_role(?,?,?)}");

        statement.setInt(1,roleId);
        statement.setString(2,currUserName);
        statement.registerOutParameter(3,java.sql.Types.VARCHAR);

        statement.execute();

        String result_string = ((OracleCallableStatement) statement).getString(3);	  
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
      session.close();
     }

    return delCount;
  }

  /**
   *  This static method returns a collection of type AimsRolePrivilege for a given role id     
   */
  public static Collection getRoleAssignedPrivileges (Long role_id) throws HibernateException
  {    
	Session session = null;
	Collection AimsRolePrivileges = new ArrayList();
	StringBuffer queryStringBuffer = new StringBuffer();

    try
    { 

        session = DBHelper.getInstance().getSession();

        queryStringBuffer.append("from ")					
						 .append("		com.netpace.aims.model.security.AimsRolePrivilege rolePrivilege  ")						
						 .append("where ")
						 .append("		rolePrivilege.roleId = :roleId ");	 
		
		AimsRolePrivileges = session.find(queryStringBuffer.toString(), role_id, new LongType());

     	log.debug("No of roles returned: " + AimsRolePrivileges.size() ); 
      
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
   *  This static method returns a collection of type AimsSysPrivilege for a given user_type     
   */
  public static Collection getRoleAvailableSysPrivileges (String role_type) throws HibernateException
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
                         .append("		sysPrivilege.privilegeId, sysPrivilege.privilegeName  ")          
                         .append("from ")             
						 .append("		com.netpace.aims.model.security.AimsSysPrivilege sysPrivilege  ")
                         .append("where ");

		if (role_type.equalsIgnoreCase("AA"))
		{
			queryStringBuffer.append("		sysPrivilege.availableTo <> 'V' ");  
		}
		if (role_type.equalsIgnoreCase("V"))
		{
			queryStringBuffer.append("		sysPrivilege.availableTo <> 'A' ");  
		}		
		if (role_type.equalsIgnoreCase("VA"))
		{
			queryStringBuffer.append("		sysPrivilege.availableTo <> 'A' ");  
		}		
			
        queryStringBuffer.append("order by sysPrivilege.privilegeId "); 
		// changed the order by on advice from pasha on Dec 08, 2003

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
	*  This static method updates a given role    
	*/
	public static void UpdateRolePrivilege(
											   int roleId,
											String roleName, 
											String roleDescription,
											String roleType,
											String currUserName,
											String currUserType,												
											String [] selects,
											String [] creates,
											String [] updates,
											String [] deletes
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

            ConOra = session.connection();
            statement = ConOra.prepareCall("{ call AIMS_ROLES.update_role(?,?,?,?,?,?,?,?,?,?)}");

            statement.setInt(1,roleId);
            statement.setString(2,roleName);
            statement.setString(3,roleDescription);
            statement.setString(4,roleType);
            statement.setString(5,StringFuncs.ConvertArrToString(selects, ","));
            statement.setString(6,StringFuncs.ConvertArrToString(creates, ","));
            statement.setString(7,StringFuncs.ConvertArrToString(updates, ","));
            statement.setString(8,StringFuncs.ConvertArrToString(deletes, ","));
            statement.setString(9,currUserName);
            statement.registerOutParameter(10,java.sql.Types.VARCHAR);
            
            statement.execute();

            String result_string = ((OracleCallableStatement) statement).getString(10);

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
	*  This static method creates a new role with privileges   
	*/
	public static void CreateRolePrivilege(
											String roleName, 
											String roleDescription,
											String roleType,
											String currUserName,
											String currUserType,												
											String [] selects,
											String [] creates,
											String [] updates,
											String [] deletes
                                          ) throws UniqueConstraintException, HibernateException
	{
		
		Session session = null;
        Transaction tx = null;
		
		try
		{
		
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();


            Set roleSet = new HashSet();
			AimsSysRole aimsRole = new AimsSysRole ();
			aimsRole.setRoleName(roleName);
			aimsRole.setRoleDescription(roleDescription);
			aimsRole.setRoleType(roleType);
			aimsRole.setCreatedBy(currUserName);
            aimsRole.setCreatedDate(new Date());
			aimsRole.setLastUpdatedBy(currUserName);
			aimsRole.setLastUpdatedDate(new Date());			

			session.save(aimsRole);

			roleSet.add(aimsRole);			

			Set rolePrivilegeSet = new HashSet();

            Vector selectVector = StringFuncs.ConvertArrToVector(selects);
            Vector createVector = StringFuncs.ConvertArrToVector(creates);
            Vector updateVector = StringFuncs.ConvertArrToVector(updates);
            Vector deleteVector = StringFuncs.ConvertArrToVector(deletes);

            Collection sysPrivileges = AimsAccountsManager.getUserTypeSysPrivileges(new Long("0"), "V");

            Vector allVector = new Vector();

            for (Iterator iter = sysPrivileges.iterator(); iter.hasNext();) 
           {
                allVector.add(((AimsSysPrivilege) iter.next()).getPrivilegeId().toString());
           }

            String select_string = "";
            String create_string = "";
            String update_string = "";
            String delete_string = "";

            AimsRolePrivilege rolePrivilege = null;

            for (int i = 0; i < allVector.size(); i++) 
           {
                if (selectVector.contains(allVector.elementAt(i)))
                {
                    select_string = "Y";
                } else
                {
                    select_string = null;
                }

                if (createVector.contains(allVector.elementAt(i)))
                {
                    create_string = "Y";
                }else
                {
                    create_string = null;
                }

                if (updateVector.contains(allVector.elementAt(i)))
                {
                    update_string = "Y";
                }else
                {
                    update_string = null;
                }

                if (deleteVector.contains(allVector.elementAt(i)))
                {
                    delete_string = "Y";
                }else
                {
                    delete_string = null;
                }
                      
				// if atlease one checkbox is checked then only save it
				if (select_string != null || create_string != null || update_string != null || delete_string != null)
				{
						
					rolePrivilege = new AimsRolePrivilege (); 
					rolePrivilege.setRoleId(aimsRole.getRoleId());
					rolePrivilege.setIfSelectAllowed(select_string);
					rolePrivilege.setIfCreateAllowed(create_string);
					rolePrivilege.setIfUpdateAllowed(update_string);
					rolePrivilege.setIfDeleteAllowed(delete_string);
					rolePrivilege.setPrivilege((AimsSysPrivilege) session.load(AimsSysPrivilege.class, new Long((String)allVector.elementAt(i))));
					
					session.save(rolePrivilege);
					rolePrivilegeSet.add(rolePrivilege);
				}
           }

            aimsRole.setRolePrivileges(rolePrivilegeSet);

			session.save(aimsRole);

            tx.commit();
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

	public static Collection getAllSysRoles() throws HibernateException
	  {    
		Collection collection = null;   
		Collection AimsSysRoles = new ArrayList();
	    Session session = null;
		Object [] roleValues = null;
		StringBuffer queryStringBuffer = new StringBuffer();
		AimsSysRole role = null;


	    try
	    { 
			queryStringBuffer.append("select ")
							 .append("		role.roleId, role.roleName,  ")
							 .append("		role.roleDescription, role.roleType, role.vzdnMappingRoleID ")
							 .append("from ")
							 .append("		com.netpace.aims.model.security.AimsSysRole role  ")					
	                         .append("order by role.roleName ");

			session = DBHelper.getInstance().getSession();

	        collection = session.find(queryStringBuffer.toString());

			for (Iterator iter = collection.iterator(); iter.hasNext();) 
			{
				 roleValues  = (Object []) iter.next();		
				 role = new AimsSysRole();	

				 role.setRoleId((Long) roleValues[0]);				// roleId
				 role.setRoleName((String) roleValues[1]);			// roleName
				 role.setRoleDescription((String)roleValues[2]);	// roleDescription
				 role.setRoleType((String)roleValues[3]);			// roleType
				 role.setVzdnMappingRoleID((Long)roleValues[4]);			// roleType
				 log.debug("---in dao : vsdz mapping rol : "+role.getVzdnMappingRoleID());	
			     AimsSysRoles.add(role);	
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
	
	
	public static Collection getAllSysRolesWOTrx(Session session) throws HibernateException
	  {    
		Collection collection = null;   
		Collection AimsSysRoles = new ArrayList();
	    //Session session = null;
		Object [] roleValues = null;
		StringBuffer queryStringBuffer = new StringBuffer();
		AimsSysRole role = null;


	    try
	    { 
			queryStringBuffer.append("select ")
							 .append("		role.roleId, role.roleName,  ")
							 .append("		role.roleDescription, role.roleType, role.vzdnMappingRoleID ")
							 .append("from ")
							 .append("		com.netpace.aims.model.security.AimsSysRole role  ")					
	                         .append("order by role.roleName ");

			//session = DBHelper.getInstance().getSession();

	        collection = session.find(queryStringBuffer.toString());

			for (Iterator iter = collection.iterator(); iter.hasNext();) 
			{
				 roleValues  = (Object []) iter.next();		
				 role = new AimsSysRole();	

				 role.setRoleId((Long) roleValues[0]);				// roleId
				 role.setRoleName((String) roleValues[1]);			// roleName
				 role.setRoleDescription((String)roleValues[2]);	// roleDescription
				 role.setRoleType((String)roleValues[3]);			// roleType
				 role.setVzdnMappingRoleID((Long)roleValues[4]);			// roleType
				 log.debug("---in dao : vsdz mapping rol : "+role.getVzdnMappingRoleID());	
			     AimsSysRoles.add(role);	
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
			//session.close();
		}

	    return AimsSysRoles;
	  }
}  