package com.netpace.vic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import com.netpace.vic.dao.EmailLogDAO;
import com.netpace.vic.dto.EmailLog;
import com.netpace.vic.util.ConnectionUtil;

public class EmailLogDAOImpl extends GenericDAOImpl implements EmailLogDAO {

	public void saveEmailLog(EmailLog emailLog) {
		LOGGER.info("Enter saveEmailLog() ...");	    
	    String query = "insert into email_log (`to_addresses`, `subject`, `status`, `created_date`, `created_by`) values(?,?,?,?,?)"; 
        
        Connection connection = null;;
        PreparedStatement preparedStatement = null;
        Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        
        try {
        	connection = super.getConnection();
        	
        	preparedStatement = connection.prepareStatement(query);
        	preparedStatement.setString(1,emailLog.getToAddresses());
        	preparedStatement.setString(2,emailLog.getSubject());
        	preparedStatement.setString(3,emailLog.getStatus());
        	preparedStatement.setTimestamp(4,currentTime);
        	preparedStatement.setString(5,emailLog.getCreatedBy());
        	
        	int status = preparedStatement.executeUpdate();
        	connection.commit();
        	LOGGER.info("Record insert status :" + status);
        } catch(SQLException se) {
        	LOGGER.info(se.toString(),se);
        } catch(Exception e) {
        	LOGGER.info(e.toString(),e);
        } finally {
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }

	}

}
