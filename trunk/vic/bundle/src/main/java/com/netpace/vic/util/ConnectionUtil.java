package com.netpace.vic.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.commons.datasource.poolservice.DataSourcePool;

@Component
public class ConnectionUtil implements GenericConnection {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
		
    private DataSourcePool source;
    private static DataSource dataSource = null;
    private static String environment="prod";

    // Returns a connection using the configured DataSourcePool
    public Connection getConnection() {
        try {
            // Inject the DataSourcePool right here!
            if("dev".equals(environment)){
                return LocalConnectionHelper.getConnection();
            }
            else {
        	    if (source == null) 
        	    	LOGGER.error("SOURCE NULL");
                if (dataSource == null) {
                    dataSource = (DataSource) source.getDataSource("vic_datasource");
                }
                return dataSource.getConnection();
            }
        } catch (Exception e) {
        	LOGGER.error(e.toString());
        }
        return null;
    }

    public static void close(Object c) {
        if (c == null) {
            return;
        }
        try {
            if (c instanceof Connection) {
                ((Connection) c).close();
            } else if (c instanceof Statement) {
                ((Statement) c).close();
            } else if (c instanceof PreparedStatement) {
                ((PreparedStatement) c).close();
            } else if (c instanceof ResultSet) {
                ((ResultSet) c).close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
