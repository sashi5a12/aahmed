package com.netpace.aims.ws.appinfo;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.netpace.aims.util.ZonwsProperties;

public class ConnectionFactory {
	private static String PROP_DATASOURCE = "com.netpace.aims.zonws.datasource";
	private static final String dataSourceName = ZonwsProperties.getInstance().getProperty(PROP_DATASOURCE);
	private static Logger log = Logger.getLogger(ConnectionFactory.class.getName());
	
	private ConnectionFactory() {
		
	}

	public static final Connection getConnection() {
		/*
		 * Create a JNDI Initial context to be able to lookup the DataSource
		 * 
		 * In production-level code, this should be cached as an instance or
		 * static variable, as it can be quite expensive to create a JNDI
		 * context.
		 */	   
	    InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} 
		catch (NamingException e){
        	// wrap it in runtime exception coz we cannont do any thing with this exception
			e.printStackTrace();
        	throw new RuntimeException(e);
		}

		/*
		 * Lookup the DataSource, which will be backed by a pool that the
		 * application server provides. DataSource instances are also a good
		 * candidate for caching as an instance variable, as JNDI lookups can be
		 * expensive as well.
		 */
		DataSource ds = null;
		try {
			//ds = (DataSource) ctx.lookup("java:mysqlDs");//comp/env/jdbc/mysqlDs
			
			//Following two lines for tomcat
			//put datasource xml in conf\Catalina\localhost
			Context envContext  = (Context)ctx.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/" + dataSourceName);
			
			//xml for tomcat
			/*
			  <Context reloadable="false">
			      <Manager className="org.apache.catalina.session.PersistentManager" saveOnRestart="false"/>
			          <Resource name="jdbc/mysqlDs" auth="Container" type="javax.sql.DataSource" 
			           username="root" 
			           password="root" 
			           driverClassName="com.mysql.jdbc.Driver"
			           url="jdbc:mysql://localhost:3306/videoventure"
                       factory="org.apache.commons.dbcp.BasicDataSourceFactory"
                       maxActive="100"
                       maxIdle="30"
                       maxWait="60000"
                       removeAbandoned="true"
                       removeAbandonedTimeout="60" />
               </Context>
			 */
		} 
		catch (NamingException e) {
        	// wrap it in runtime exception coz we cannont do any thing with this exception
			e.printStackTrace();
        	throw new RuntimeException(e);
		}

		/*
		 * The following code is what would actually be in your Servlet, JSP or
		 * EJB 'service' method...where you need to work with a JDBC connection.
		 */
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
