package com.netpace.aims.ws.amdocs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.netpace.aims.util.ConfigEnvProperties;

public class ConnectionFactory {
	private static String PROP_DATASOURCE = "com.netpace.aims.amdocs.datasource";
	private static final String dataSourceName = ConfigEnvProperties.getInstance().getProperty(PROP_DATASOURCE);
	private static Logger log = Logger.getLogger(ConnectionFactory.class.getName());
	
	private ConnectionFactory() {
		
	}
	/*
	public static final Connection getConnection() {
		
		Connection conn = null;

        try
        {
            String userName = "aims_prod";
            String password = "aims_prod";
            String url = "jdbc:oracle:thin:@xeon:1521:ora9i";
            Class.forName ("oracle.jdbc.driver.OracleDriver").newInstance ();
            
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
        }
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server");
            e.printStackTrace();
        }
        
        return conn;
        
	}
	*/

	public static final Connection getConnection() {
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} 
		catch (NamingException e){
        	// wrap it in runtime exception coz we cannont do any thing with this exception
			e.printStackTrace();
        	throw new RuntimeException(e);
		}

		DataSource ds = null;
		try {
			//ds = (DataSource) ctx.lookup("java:mysqlDs");//comp/env/jdbc/mysqlDs
			
			//Following two lines for tomcat
			//put datasource xml in conf\Catalina\localhost
			Context envContext  = (Context)ctx.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/" + dataSourceName);
		} 
		catch (NamingException e) {
        	// wrap it in runtime exception coz we cannont do any thing with this exception
			e.printStackTrace();
        	throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = ds.getConnection();
		} 
		catch (SQLException e){			
        	// wrap it in runtime exception coz we cannont do any thing with this exception
			e.printStackTrace();
        	throw new RuntimeException(e);
		}

		return conn;
	}
	
	public static final String getDataSourceName(){
		return dataSourceName;
	}

}
