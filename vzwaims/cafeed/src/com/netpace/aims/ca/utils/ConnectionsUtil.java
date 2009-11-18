package com.netpace.aims.ca.utils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class ConnectionsUtil {

    private static Logger log = Logger.getLogger(ConnectionsUtil.class.getName());
    private static ConnectionsUtil instance = null;
    private Connection connection;
    
    private ConnectionsUtil() {
    	instantiateConnection();
    	log.info("Singleton Object Created");
    }

    private void instantiateConnection() {
        log.debug("Loading database connection");        
        CafeedBundle bundle = CafeedBundle.getInstance();	    
		String connectionString = bundle.getProperty("database.connection.url");
	    String driverClass = bundle.getProperty("database.connection.driver");
	    String USER_NAME = bundle.getProperty("database.connection.username");
	    String PASSWORD = bundle.getProperty("database.connection.password");
	    
	    try 
	    {
	       Class.forName(driverClass).newInstance();
	    }
	    catch (Exception ex)
	    {
	       System.out.println("Check classpath. Cannot load db driver: " + driverClass);
	       log.error(ex.getMessage(),ex);
	    }

	    try 
	    {
	    	connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
	    }
	    catch (SQLException e){
	       System.out.println("Driver loaded, but cannot connect to db: " + connectionString);
	       log.error(e.getMessage(),e);
	       e.printStackTrace();
	    }
	  }
  
    /**
	 * This method returns the singleton instance of the Connection.
	 * Care has been taken to make this method thread safe.
	 */
    public static ConnectionsUtil loadConnection() {
        if (instance == null) {
        	log.info("Database connection does not exist, creating one!!!");
            synchronized(ConnectionsUtil.class) {
                if (instance == null) {
                    instance = new ConnectionsUtil();
                    log.info("Database Connection Created Successfully!!!");
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection() {
    	return connection;
    }
    }
