package com.netpace.aims.bo.application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;

import com.netpace.aims.controller.application.AppProvisionedBean;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.model.masters.AimsSysRol;


/**
 * This class is responsible for getting the BO for devices.
 * It has static methods for getting the devices.
 * @author Rizwan Qazi
 */
public class AppsProvisionedManager
{

  static Logger log = Logger.getLogger(AppsProvisionedManager.class.getName());

  /**
   *  This static method gets the devices in the database which are available
   *  to the current user.
   */
  public static Collection getBrewApps (
  						 Long userId,
  						int pageNumberToView, int pageLength, StringBuffer totalRecords,
						String filterText, String filterField, String sortField) 
  				throws HibernateException, SQLException
  {    
	List vzwList = new ArrayList();   
	List appProvList = new ArrayList();   
	Session session = null;
	Connection ConOra = null;	
	CallableStatement statement = null;
	ResultSet results = null;	
	AppProvisionedBean appProvBean = null;
	


    try
    {         
		session = DBHelper.getInstance().getSession();

		ConOra = session.connection();
		statement = ConOra.prepareCall("{ call AIMS_PROV_APPS_PKG.get_results_cursor(?,?,?,?,?,?,?,?)}");
	
		statement.setLong(1,userId.longValue());
		statement.setInt(2,pageNumberToView);
		statement.setInt(3,pageLength);
		statement.setString(4,filterText);
		statement.setString(5,filterField);	
		statement.setString(6,sortField);			
		statement.registerOutParameter(7,java.sql.Types.INTEGER);
		statement.registerOutParameter(8,OracleTypes.CURSOR);
	
		statement.execute();
		
		totalRecords.append(statement.getInt(7));
		results  = (ResultSet) statement.getObject(8);
		
		
		if(results != null )
			results.setFetchSize(100);

		
		while(results.next())
		{
			log.debug("INSIDE  results.next()");
			appProvBean = new AppProvisionedBean();
			log.debug("COMPANY_NAME " + results.getString(2));
			
			appProvBean.setAppsId(new Long(results.getLong(1)).toString());
			appProvBean.setAllianceName(results.getString(2));
			appProvBean.setAppTitle(results.getString(3));
			appProvBean.setPlatformName(results.getString(4));	
			appProvBean.setAppSubmittedDate(Utility.convertToString(results.getDate(5),AimsConstants.DATE_FORMAT));					
			appProvBean.setAppCategoryName(results.getString(6));
			appProvBean.setAppSubCategoryName(results.getString(7));
			
			appProvList.add(appProvBean);
		}
		results.close(); 
		results = null;		
	
     }
     catch(SQLException e)
     {
		e.printStackTrace();
		throw e;
     }
     finally
     {    	       
        try
        {
            if (statement != null)
                statement.close();    
        }
        catch (Exception ex)
        {}

		session.close();
     }

    return appProvList;
  }  

  /**
   *  This static method gets the devices in the database which are available
   *  to the current user.
   */
  public static List getProvAppFromId (Long appsId) throws HibernateException, SQLException
  {    
	List selectedRoles = new ArrayList();   
	List availableRoles = new ArrayList();		
	
	List selectedRoleIds = new ArrayList();   
	List availableRoleIds = new ArrayList();
	
	List roles = new ArrayList();
	
	AimsSysRol aimSysRole = null;
	
	Session session = null;
	Connection ConOra = null;	
	CallableStatement statement = null;
	ResultSet results = null;	
	Long roleId;

    try
    {         
		session = DBHelper.getInstance().getSession();

		ConOra = session.connection();
		statement = ConOra.prepareCall("{ call AIMS_BREW_PROV_APPS.get_prov_roles_from_apps_id(?,?,?)}");
	
		statement.setLong(1,appsId.longValue());		
		statement.registerOutParameter(2,OracleTypes.CURSOR);
		statement.registerOutParameter(3,OracleTypes.CURSOR);
	
		statement.execute();		
		
		results  = (ResultSet) statement.getObject(2);
		
		
		if(results != null )
			results.setFetchSize(100);

		
		while(results.next())
		{			
			aimSysRole = new AimsSysRol();			
			roleId = new Long(results.getLong(1));
			aimSysRole.setRoleId(roleId);
			aimSysRole.setRoleName(results.getString(2));
			
			selectedRoles.add(aimSysRole);
			selectedRoleIds.add(roleId.toString());
		}
		results.close(); 
		results = null;	
		
		roles.add(0, selectedRoles);
		roles.add(1, selectedRoleIds);
		
		
		results  = (ResultSet) statement.getObject(3);		
		
		if(results != null )
			results.setFetchSize(100);

		
		while(results.next())
		{
			aimSysRole = new AimsSysRol();	
			roleId = new Long(results.getLong(1));
			
			aimSysRole.setRoleId(roleId);
			aimSysRole.setRoleName(results.getString(2));
			
			availableRoles.add(aimSysRole);
			availableRoleIds.add(roleId.toString());
		
		}
		results.close(); 
		results = null;	
		
		roles.add(2, availableRoles);
		roles.add(3, availableRoleIds);
     }
     catch(SQLException e)
     {
		e.printStackTrace();
		throw e;
     }
     finally
     {    	       
        try
        {
            if (statement != null)
                statement.close();    
        }
        catch (Exception ex)
        {}

		session.close();
     }

    return roles;
    
  }  

  /**
   *  This static method gets the devices in the database which are available
   *  to the current user.
   */
  public static List getProvAppRolesFromString (String roleIds) throws HibernateException, SQLException
  {    
	List selectedRoles = new ArrayList();   
	List availableRoles = new ArrayList();	
	List roles = new ArrayList();
	
	AimsSysRol aimSysRole = null;
	
	Session session = null;
	Connection ConOra = null;	
	CallableStatement statement = null;
	ResultSet results = null;	

    try
    {         
		session = DBHelper.getInstance().getSession();

		ConOra = session.connection();
		statement = ConOra.prepareCall("{ call AIMS_BREW_PROV_APPS.get_prov_roles_from_string(?,?,?)}");
	
		statement.setString(1,roleIds);		
		statement.registerOutParameter(2,OracleTypes.CURSOR);
		statement.registerOutParameter(3,OracleTypes.CURSOR);
	
		statement.execute();		
		
		results  = (ResultSet) statement.getObject(2);
		
		
		if(results != null )
			results.setFetchSize(100);

		
		while(results.next())
		{			
			aimSysRole = new AimsSysRol();			
			
			aimSysRole.setRoleId(new Long(results.getLong(1)));
			aimSysRole.setRoleName(results.getString(2));
			
			selectedRoles.add(aimSysRole);
		}
		results.close(); 
		results = null;	
		
		roles.add(selectedRoles);
		results  = (ResultSet) statement.getObject(3);
		
		
		if(results != null )
			results.setFetchSize(100);

		
		while(results.next())
		{
			aimSysRole = new AimsSysRol();			
			
			aimSysRole.setRoleId(new Long(results.getLong(1)));
			aimSysRole.setRoleName(results.getString(2));
			
			availableRoles.add(aimSysRole);
		}
		results.close(); 
		results = null;			
		roles.add(availableRoles);
     }
     catch(SQLException e)
     {
		e.printStackTrace();
		throw e;
     }
     finally
     {    	       
        try
        {
            if (statement != null)
                statement.close();    
        }
        catch (Exception ex)
        {}

		session.close();
     }

    return roles;
  }  

}
