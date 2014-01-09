package com.netpace.vic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import com.netpace.vic.dao.UserApplicationDAO;
import com.netpace.vic.dto.UserApplication;
import com.netpace.vic.util.ConnectionUtil;

public class UserApplicationDAOImpl extends GenericDAOImpl implements UserApplicationDAO {

	public void saveUserApplication(UserApplication userApplication) {
		LOGGER.info("Enter saveUserApplication() ...");	    
	    String query = "insert into user_application (`company_name`, `website`, `stage_of_company`, `number_of_employees`, `revenue_generation`, "+
	    		       "`reason_for_enagaging`, `product_idea`, `project_development_stage`, `connectivity_utilization`, `product_data_speed`, `program_immediate_help`, "+
	    		       "`vertical_industry`, `existing_partnerships`, `type_of_finiancing`, `project_business_model`, `target_customers`, `product_usage`, `existing_sales_channels`, "+
	    		       "`companies_working_same_space`, `one_year_market_sales`, `contact_name`, `contact_position`, `contact_email`, `contact_phone`, `contact_salesforce_identifier`, "+
	    		       "`contact_verizon_sales_rep`, `application_submission_date`) " +
	    		       " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        Connection connection = null;;
        PreparedStatement preparedStatement = null;
                
        try {
        	connection = super.getConnection();
        	preparedStatement = connection.prepareStatement(query);
        	preparedStatement.setString(1,userApplication.getCompanyName());
        	preparedStatement.setString(2,userApplication.getWebsite());
        	preparedStatement.setString(3,userApplication.getStageOfCompany());
        	preparedStatement.setString(4,userApplication.getNumberOfEmployees());
        	preparedStatement.setString(5,userApplication.getRevenueGeneration());
        	preparedStatement.setString(6,userApplication.getReasonForEnagaging());
        	preparedStatement.setString(7,userApplication.getProductIdea());
        	preparedStatement.setString(8,userApplication.getProjectDevelopmentStage());
        	preparedStatement.setString(9,userApplication.getConnectivityUtilization());
        	preparedStatement.setString(10,userApplication.getProductDataSpeed());
        	preparedStatement.setString(11,userApplication.getProgramImmediateHelp());
        	preparedStatement.setString(12,userApplication.getVerticalIndustry());
        	preparedStatement.setString(13,userApplication.getExistingPartnerships());
        	preparedStatement.setString(14,userApplication.getTypeOfFiniancing());
        	preparedStatement.setString(15,userApplication.getProjectBusinessModel());
        	preparedStatement.setString(16,userApplication.getTargetCustomers());
        	preparedStatement.setString(17,userApplication.getProductUsage());
        	preparedStatement.setString(18,userApplication.getExistingSalesChannels());
        	preparedStatement.setString(19,userApplication.getCompaniesWorkingSameSpace());
        	preparedStatement.setString(20,userApplication.getOneYearMarketSales());
        	preparedStatement.setString(21,userApplication.getContactName());
        	preparedStatement.setString(22,userApplication.getContactPosition());
        	preparedStatement.setString(23,userApplication.getContactEmail());
        	preparedStatement.setString(24,userApplication.getContactPhone());
        	preparedStatement.setString(25,userApplication.getContactSalesforceIdentifier());
        	preparedStatement.setString(26,userApplication.getContactVerizonSalesRep());
        	Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        	preparedStatement.setTimestamp(27,currentTime);
        	
        	int status = preparedStatement.executeUpdate();
        	LOGGER.info("Record insert status :" + status);
                connection.commit();
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
