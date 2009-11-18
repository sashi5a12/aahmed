package com.netpace.aims.ws.amdocs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.netpace.aims.model.alliance.AimsAllianceCarriers;
import com.netpace.aims.model.core.AimsAllianceContract;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.ws.vds.AllianceServices;
import com.netpace.aims.ws.vds.AllianceServicesException;
import com.netpace.aims.ws.vds.BatchContentDeliveryServices;
import com.netpace.aims.ws.vds.BulkDevelopersLog;

public class BatchSettlementServices {

	private static Logger log = Logger.getLogger(BatchSettlementServices.class.getName());
	
	/**
	 * bulk upload partner and offer to Amdocs system. 
	 * @param args
	 */
	public static List<BulkDevelopersLog> bulkUploadPartnerOffer(Date startDate, Date endDate)
	{
		if( log.isInfoEnabled() ){
			log.info("###################################(BULK LOAD FOR AMDOCS)########################################"); 
			log.info( "Bulk Load Partner to Amdocs between " + startDate + ", " + endDate );
		}
		Timestamp startTime = new Timestamp(startDate.getTime()); //todo need to change timestamp
		Timestamp endTime = new Timestamp(endDate.getTime());
		
		BulkDevelopersLog partnerOnBoardingLog = new BulkDevelopersLog();
		
		int success = 0 , fail = 0;
		
		List<Long> developerIds = BatchSettlementServices.getListDeveloper(startTime, endTime);
		
		if( log.isInfoEnabled() )
			log.info( "Partner List = "+ developerIds.toString() );
		
		partnerOnBoardingLog.setTotal( developerIds.size() );
		
		String notificationEnabled = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.allianceService.enable.notification");
		
		for ( Long allianceId : developerIds )
		{
			SettlementServices settlementServices = new SettlementServices(allianceId, null);
			
			if( log.isInfoEnabled() )
				log.info( "Start creating partner on Amdocs system for " + allianceId );
			
			try 
			{
				settlementServices.partnerOnboarding( notificationEnabled.equals( "true" ), true );
				
				partnerOnBoardingLog.addSuccessId( "" + allianceId);
				
				success++;
				
				if( log.isInfoEnabled() )
					log.info( "End creating partner on Amdocs system for " + allianceId );
				
			} 
			catch (Exception e) 
			{
				log.error("Error creating partner on Amdocs system for allianceId = " + allianceId, e);
				//e.printStackTrace();
				
				String resubmitURL = AllianceServices.NO_RESUBMIT_URL;
				
				if ( e instanceof AllianceServicesException)
				{
					resubmitURL = ( (AllianceServicesException) e ).getResubmitURL();
				}
				
				if ( e.getCause() != null )
					partnerOnBoardingLog.addFailureId( allianceId + ", " + e.getCause().getMessage() + ", " + resubmitURL );
				else
					partnerOnBoardingLog.addFailureId( allianceId + ", " + e.getMessage() + ", " + resubmitURL );
				
				fail++;
				
				if( log.isInfoEnabled() )
					log.info( "Error creating partner on Amdocs system for " + allianceId );
			}
		}
		
		partnerOnBoardingLog.setSuccess(success);
		
		partnerOnBoardingLog.setFail(fail);
		
		if( log.isInfoEnabled() )
		{
			log.info( "Total calls to Amdocs for Partner On boarding: " + partnerOnBoardingLog.getTotal() );
			log.info( "Success: " + partnerOnBoardingLog.getSuccess() );
			log.info( "Failure: " + partnerOnBoardingLog.getFail() );
			log.info( "=====================================================================================================");
		}
		

		BulkDevelopersLog offerCreationLog = new BulkDevelopersLog();
		
		success = fail = 0;
		
		List<AimsAllianceContract> aimsContracts = BatchSettlementServices.geListContract(startTime, endTime);
		
		if( log.isInfoEnabled() )
			log.info( "Contract List = "+ aimsContracts.toString() );
		
		offerCreationLog.setTotal( aimsContracts.size() );
		
		for ( AimsAllianceContract contract : aimsContracts )
		{
			SettlementServices settlementServices = new SettlementServices(contract.getAllianceId(), contract.getContractId());
			
			if( log.isInfoEnabled() && contract != null )
				log.info( "Start creating Offer/Contract on Amdocs system for " + contract.getAllianceId() + " & " + contract.getContractId() );
			
			try 
			{
				
				settlementServices.offerCreation( notificationEnabled.equals( "true" ), true );
				
				if ( contract != null )
					offerCreationLog.addSuccessId( "" + contract.getAllianceId() );
				
				success++;
				
				if( log.isInfoEnabled()  && contract != null )
					log.info( "End creating Offer/Contract on Amdocs system for " + contract.getAllianceId() + " & " + contract.getContractId() );
				
			} 
			catch (Exception e) 
			{
				if ( contract != null )
				log.error("Error creating Offer/Contract on Amdocs system for allianceId = " + contract.getContractId(), e);
				//e.printStackTrace();	
				
				String resubmitURL = AllianceServices.NO_RESUBMIT_URL;
				
				if ( e instanceof AllianceServicesException)
				{
					resubmitURL = ( (AllianceServicesException) e ).getResubmitURL();
				}
				
				if ( contract != null )
				{
					if ( e.getCause() != null )
						offerCreationLog.addFailureId( contract.getAllianceId() + ", " + e.getCause().getMessage() + ", " + resubmitURL );
					else
						offerCreationLog.addFailureId( contract.getAllianceId() + ", " + e.getMessage() + ", " + resubmitURL );
				}
				else
					offerCreationLog.addFailureId( "none, none, none" );
				
				fail++;
				
				if( log.isInfoEnabled() && contract != null )
					log.info( "Error creating Offer/Contract on Amdocs system for " + contract.getAllianceId() + " & " + contract.getContractId() );
			}
		}
		
		offerCreationLog.setSuccess(success);
		offerCreationLog.setFail(fail);
		
		List<BulkDevelopersLog> settlementList = new ArrayList<BulkDevelopersLog>();
		
		settlementList.add( partnerOnBoardingLog );
		settlementList.add( offerCreationLog );
		
		if( log.isInfoEnabled() )
		{
			log.info( "Total calls to Amdocs for Offer Creation: " + offerCreationLog.getTotal() );
			log.info( "Success: " + offerCreationLog.getSuccess() );
			log.info( "Failure: " + offerCreationLog.getFail() );
			log.info( "Bulk load ended." );
			log.info("#################################################################################################");
		}
		
		return settlementList;
	}

	
	/**
	 * You can move this to utils class
	 * @return
	 */
	private static List<Long> getListDeveloper(Timestamp start, Timestamp end) 
	{
		List<Long> allianceIdLst = new ArrayList<Long>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String query = "select distinct allianceContract.ALLIANCE_ID " +
					" from AIMS_ALLIANCE_CONTRACTS allianceContract, AIMS_CONTRACTS contract " +
					" where allianceContract.CONTRACT_ID=contract.CONTRACT_ID and allianceContract.STATUS=? " +
					" and contract.PLATFORM_ID=? and allianceContract.ACCEPT_DECLINE_DATE between ? and  ? ";
		
		try  
		{						
			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, AimsConstants.CONTRACT_ACCEPTED);
			preparedStatement.setLong(2, AimsConstants.JAVA_PLATFORM_ID);
			preparedStatement.setTimestamp(3, start);
			preparedStatement.setTimestamp(4, end);
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) 
			{	
				allianceIdLst.add(rs.getLong("ALLIANCE_ID"));
			}

			log.debug("No of records returned: " + allianceIdLst.size());

		} 
		catch (SQLException e) 
		{
			log.error(e,e);
			throw new RuntimeException(e);
		} 
		finally 
		{
			try  
			{			
				if(preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if(connection != null) 
				{
					connection.close();
					log.debug("connection closed in BatchSettlementServices.getListDeveloper()");
					connection = null;
				}
			}
			catch (SQLException e) 	
			{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}
		

		return allianceIdLst;
	}

	
	/**
	 * You can move this to utils class
	 * @return
	 */
	private static List<AimsAllianceContract> geListContract(Timestamp start, Timestamp end){
	
		List<AimsAllianceContract> contractsList = new ArrayList<AimsAllianceContract>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
/*
		String query = "select allianceContract.ALLIANCE_ID, allianceContract.CONTRACT_ID from AIMS_ALLIANCE_CONTRACTS allianceContract " +
				" where allianceContract.STATUS=? and allianceContract.ACCEPT_DECLINE_DATE between ? and  ?";
	*/	
		String query = " select distinct allianceContract.CONTRACT_ID, allianceContract.ALLIANCE_ID " +
				" from AIMS_ALLIANCE_CONTRACTS allianceContract, AIMS_CONTRACTS contract " +
				" where allianceContract.CONTRACT_ID=contract.CONTRACT_ID and allianceContract.STATUS=? " +
				" and contract.PLATFORM_ID=? and allianceContract.ACCEPT_DECLINE_DATE between ? and  ? "; 
		
		try  
		{						
			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, AimsConstants.CONTRACT_ACCEPTED);
			preparedStatement.setLong(2, AimsConstants.JAVA_PLATFORM_ID);
			preparedStatement.setTimestamp(3, start);
			preparedStatement.setTimestamp(4, end);
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) 
			{	
				AimsAllianceContract allianceContract = new AimsAllianceContract();
				
					allianceContract.setContractId(rs.getLong("CONTRACT_ID"));
					allianceContract.setAllianceId(rs.getLong("ALLIANCE_ID"));
					
				contractsList.add(allianceContract);	
			}

			log.debug("No of records returned: " + contractsList.size());

		} 
		catch (SQLException e) 
		{
			log.error(e,e);
			throw new RuntimeException(e);
		} 
		finally 
		{
			try  
			{			
				if(preparedStatement != null) 
				{
					preparedStatement.close();
					preparedStatement = null;
				}
				if(connection != null) 
				{
					connection.close();
					log.debug("connection closed in BatchSettlementServices.geListContract()");
					connection = null;
				}
			}
			catch (SQLException e) 	
			{
				log.error(e,e);
				throw new RuntimeException(e);
			}
		}
		

		return contractsList;
	}
}
