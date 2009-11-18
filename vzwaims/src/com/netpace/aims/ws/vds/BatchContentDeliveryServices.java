package com.netpace.aims.ws.vds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;

import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.ConfigEnvProperties;
import com.netpace.aims.ws.ThirdpartyServiceException;
import com.netpace.aims.ws.amdocs.ConnectionFactory;

public class BatchContentDeliveryServices {

	private static Logger log = Logger.getLogger(BatchContentDeliveryServices.class.getName());
	
	/**
	 * Call from command prompt to bulk upload developer to VDS system.
	 * 
	 * @param args
	 */
	public static BulkDevelopersLog bulkCreateDeveloper(Date startDate, Date endDate)
	{
		if( log.isInfoEnabled() ){
			log.info("###################################(BULK LOAD FOR VDS)########################################");
			log.info( "Bulk Load Developer to VDS between : " + startDate + " and " + endDate );
		}	
		Timestamp startTime = new Timestamp(startDate.getTime()); //todo need to change timestamp
		Timestamp endTime = new Timestamp(endDate.getTime());
		
		List<Long> developerIds = BatchContentDeliveryServices.getListDeveloper(startTime, endTime);

		if( log.isInfoEnabled() )
			log.info( "Developer List = " + developerIds.toString() );

		AllianceServices allianceService = null;
		try
		{
			allianceService = new AllianceServices();
		}
		catch (ThirdpartyServiceException e) 
		{
			log.error(e,e);
			//e.printStackTrace();
		}	
		
		BulkDevelopersLog usersLog = new BulkDevelopersLog();
		
		usersLog.setTotal( developerIds.size() );
		
		int success = 0 , fail = 0;
			
			for ( Long allianceId : developerIds )
			{
				
				if( log.isInfoEnabled() )
					log.info( "Start creating developer on VDS system for " + allianceId );
				
				try
				{
					Developer developer = allianceService.getDeveloperForAllianceId(allianceId);
			
					try
					{
						
						String notificationEnabled = ConfigEnvProperties.getInstance().getProperty("com.netpace.aims.ws.allianceService.enable.notification");
						
						allianceService.createDeveloper( developer , notificationEnabled.equals( "true" ) );
						
						usersLog.addSuccessId( "" + allianceId);
						
						success++;
						
						if( log.isInfoEnabled() )
							log.info( "End creating developer on VDS system for " + allianceId );
						
					} catch (Exception e) {
						
						log.error("Error creating developer on VDS System for allianceId = " + allianceId, e);
						//e.printStackTrace();
						
						String resubmitURL = AllianceServices.NO_RESUBMIT_URL;
						
						if ( e instanceof AllianceServicesException)
						{
							resubmitURL = ( (AllianceServicesException) e ).getResubmitURL();
						}
						
						if ( e.getCause() != null )
							usersLog.addFailureId( allianceId + ", "  + e.getCause().getMessage() + ", " + resubmitURL );
						else
							usersLog.addFailureId( allianceId + ", "  + e.getMessage() + ", " + resubmitURL );
						
						fail++;
						
						if( log.isInfoEnabled() )
							log.info( "Error making VDS Developer Create Call to VDS system for " + allianceId );		
					}
				}
				catch (HibernateException e)
				{	
					log.error("Exception occured in the allianceService.getDeveloperForAllianceId method for allianceId = " + allianceId, e);
					//e.printStackTrace();
					
					if ( e.getCause() != null )
						usersLog.addFailureId( allianceId + ", "  + e.getCause().getMessage() + ", " + AllianceServices.NO_RESUBMIT_URL );
					else
						usersLog.addFailureId( allianceId + ", "  + e.getMessage() + ",  " + AllianceServices.NO_RESUBMIT_URL );
					
					fail++;
					
					if( log.isInfoEnabled() )
						log.info( "Error making VDS Developer Create Call to VDS system for " + allianceId );
					
				}
				catch (ThirdpartyServiceException e) 
				{
					log.error("Exception occured in the allianceService.createDeveloper method for allianceId = " + allianceId, e);
					//e.printStackTrace();
					
					if ( e.getCause() != null )
						usersLog.addFailureId( allianceId + ", "  + e.getCause().getMessage() + ", " + AllianceServices.NO_RESUBMIT_URL );
					else
						usersLog.addFailureId( allianceId + ", "  + e.getMessage() + ", " + AllianceServices.NO_RESUBMIT_URL );
					
					fail++;
					
					if( log.isInfoEnabled() )
						log.info( "Error making VDS Developer Create Call to VDS system for " + allianceId );					
				}
			}	
			
			usersLog.setSuccess(success);
			
			usersLog.setFail(fail);
			

			if ( log.isInfoEnabled() )
			{
				log.info( "Total calls to VDS for Developer Create: " + usersLog.getTotal() );
				log.info( "Success: " + usersLog.getSuccess() );
				log.info( "Failure: " + usersLog.getFail() );
				log.info( "Bulk load ended." );
				log.info("##############################################################################################");
			}
			
			return usersLog;
	}

	/**
	 * 
	 * @return
	 */
	private static List<Long> getListDeveloper(Timestamp start, Timestamp end) 
	{
		List<Long> allianceIdLst = new ArrayList<Long>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String query = " select distinct allianceContract.ALLIANCE_ID " +
			" from AIMS_ALLIANCE_CONTRACTS allianceContract, AIMS_CONTRACTS contract " +
			" where allianceContract.CONTRACT_ID = contract.CONTRACT_ID " +
			" and allianceContract.STATUS = ? " +  	//1
			" and contract.PLATFORM_ID = ? " + 		//2
			" and allianceContract.ACCEPT_DECLINE_DATE between ? and  ? "; //3 & 4
		
		try  
		{						
			connection = ConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, AimsConstants.CONTRACT_ACCEPTED);
			preparedStatement.setLong(2, AimsConstants.JAVA_PLATFORM_ID.longValue());
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
					log.debug("connection closed in BatchContentDeliveryServices.getListDeveloper()");
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

}
