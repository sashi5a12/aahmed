package com.netpace.vic.dao.impl;

import java.sql.Connection;
import javax.sql.DataSource;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.netpace.vic.dao.GenericDAO;
import com.netpace.vic.util.LocalConnectionHelper;
import java.sql.SQLException;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericDAOImpl implements GenericDAO {
    protected static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);
    private static final String environment="prod";
     
    private DataSourcePool source;
    
    static {
        if("dev".equals(environment)==false){             
            try {
                com.mysql.jdbc.Driver driver=new com.mysql.jdbc.Driver();
            } catch (SQLException ex) {
                LOGGER.error(ex.toString(),ex);
            }

            try {               
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (ClassNotFoundException ex) {
                LOGGER.error(ex.toString(), ex);
            } catch (InstantiationException ex) {
                LOGGER.error(ex.toString(), ex);
            } catch (IllegalAccessException ex) {
                LOGGER.error(ex.toString(), ex);
            }
        }
    }
    public void setSource(DataSourcePool source) {
        this.source = source;
    }

    public Connection getConnection() {
        DataSource dataSource = null;
        try {
            if("dev".equals(environment)){
                return LocalConnectionHelper.getConnection();
            }
            else {
                LOGGER.info("DataSourcePool: "+source);
                dataSource = (DataSource) source.getDataSource("vic_datasource");
                LOGGER.info("DataSource: "+dataSource);
                return dataSource.getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
