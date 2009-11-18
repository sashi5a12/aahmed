package com.netpace.aims.bo.reports;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.reports.*;


import net.sf.hibernate.*;

import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;
import java.sql.*;

import oracle.jdbc.driver.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.*;


/**
 * This class is responsible for getting the BO for devices.
 * It has static methods for getting the devices.
 * @author Rizwan Qazi
 */
public class AimsPerfReportManager
{

  static Logger log = Logger.getLogger(AimsPerfReportManager.class.getName());

  /**
   *  This static method gets the devices in the database which are available
   *  to the current user.
   */
  public static AimsPerfReport getReport (int userid) throws HibernateException, SQLException
  {    
	List vzwList = new ArrayList();   
	List vzwAllianceList = new ArrayList();   
	Session session = null;
	Connection ConOra = null;	
	CallableStatement statement = null;
	ResultSet results = null;
	AimsPerfReport perfReportBean = new AimsPerfReport();
	AimsEmployee vzwEmployee = null;
	AimsEmpAlliance vzwEmpAlliance = null;

    try
    {         
		session = DBHelper.getInstance().getSession();

		ConOra = session.connection();
		statement = ConOra.prepareCall("{ call AIMS_REPORTS.staff_performance_report(?,?,?,?,?,?,?,?)}");

		statement.setInt(1,userid);
		statement.setString(2,"");
		statement.setInt(3,0);
		statement.registerOutParameter(4,java.sql.Types.VARCHAR);
		statement.registerOutParameter(5,java.sql.Types.VARCHAR);
		statement.registerOutParameter(6,java.sql.Types.VARCHAR);
		statement.registerOutParameter(7,OracleTypes.CURSOR);
		statement.registerOutParameter(8,OracleTypes.CURSOR);

		//execute the db statement
		statement.execute();

		perfReportBean.setEmployeeName(((OracleCallableStatement) statement).getString(4));
		perfReportBean.setDepartmentName(((OracleCallableStatement) statement).getString(5));		
		perfReportBean.setReportDate(((OracleCallableStatement) statement).getString(6));

		results  = (ResultSet) statement.getObject(7);
		if(results != null )
			results.setFetchSize(100);	//vzwEmpAlliance

		while(results.next())
		{
			vzwEmpAlliance = new AimsEmpAlliance();
			vzwEmpAlliance.setAllianceName(results.getString(1));
			vzwEmpAlliance.setAllianceCreatedDate(results.getString(2));		
			vzwEmpAlliance.setAllianceStatus(results.getString(3));
			vzwEmpAlliance.setCntSubmittedApps(results.getString(4));
			vzwEmpAlliance.setCntLiveApps(results.getString(5));
			vzwEmpAlliance.setPlatformName(results.getString(6));
			vzwEmpAlliance.setAppName(results.getString(7));
			vzwEmpAlliance.setAppCreatedDate(results.getString(8));
			vzwEmpAlliance.setAppStatus(results.getString(9));		
			
			vzwAllianceList.add(vzwEmpAlliance);
		}
		results.close(); 
		results = null;
		
		perfReportBean.setVerizonEmpAlliances(vzwAllianceList);

      
		results  = (ResultSet) statement.getObject(8);
		if(results != null )
			results.setFetchSize(100);	

		while(results.next())
		{
			vzwEmployee = new AimsEmployee();
			vzwEmployee.setEmployeeId(results.getInt(1));
			vzwEmployee.setEmployeeName(results.getString(2));	
			
			vzwList.add(vzwEmployee);
		}
		
		results.close(); 
		results = null;

		perfReportBean.setVerizonEmployees(vzwList);

     }
     catch(SQLException e)
     {
		e.printStackTrace();
		throw e;
     }
     finally
     {
		//ConOra.close();
		session.close();
     }

    return perfReportBean;
  }  



}  