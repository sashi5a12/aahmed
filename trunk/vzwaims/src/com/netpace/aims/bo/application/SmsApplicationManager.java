package	com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.LongType;

import com.netpace.aims.bo.core.*;
import com.netpace.aims.bo.masters.*;
import com.netpace.aims.model.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.util.*;


import org.apache.log4j.Logger;
import org.apache.struts.upload.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import oracle.sql.BLOB;



/**
 * This	class	is responsible for getting the BO	for	SMS Application.
 * It	has	static methods for getting the SMS Application. 
 * @author Adnan Makda
 */
public class SmsApplicationManager
{

	static Logger	log	=	Logger.getLogger(SmsApplicationManager.class.getName());


	/**
	*	 This	static method	updates	a	given	Sms Application.
	*/
	public static	void saveOrUpdateSmsApplication
									(AimsApp aimsApp, AimsSmsApp aimsSmsApp, AimsContact aimsContact, 
           					String userId, String userType, 
										Long userGuide, Long faqDoc,
										Long testPlanResults, Long messageFlow,
										Set certificationSet, Set testingPhaseSet, Long clonedFromAppId
									) throws	AimsException, HibernateException, Exception
	{
		Session	session	=	null;
		Transaction	tx = null;
		boolean newApp = false;
		
		try
		{
			session	=	DBHelper.getInstance().getSession();
			tx = session.beginTransaction();
    	
			//If Aims Contact Id provided zero (0), then create new Contact
			if ( (aimsApp.getAimsContactId() != null) && (aimsApp.getAimsContactId().longValue() == 0) )
			{	
				//If All Non-Nullable Fields provided for Contact, then create new Contact
				if	(	(aimsContact.getFirstName().length() > 0) && (aimsContact.getLastName().length() > 0) && 
							(aimsContact.getEmailAddress().length() > 0) && (aimsContact.getTitle().length() > 0) )						
					aimsApp.setAimsContactId(AimsApplicationsManager.saveOrUpdateContact(aimsContact));
				else
					aimsApp.setAimsContactId(null);
			}

			
			/*
    	*SAVING APP IN DATABASE
    	*/

			if (aimsApp.getAppsId() == null) //Means to Save
    	{
    		session.saveOrUpdate(aimsApp);
    		aimsSmsApp.setSmsAppsId(aimsApp.getAppsId());
				session.save(aimsSmsApp);			
				newApp = true;	
    	}
    	else //Means to Update
    	{
    		session.saveOrUpdate(aimsApp);
    		aimsSmsApp.setSmsAppsId(aimsApp.getAppsId());
				session.update(aimsSmsApp);
				newApp = false;
    	}
			
			ApplicationsManagerHelper.addNewPhases(session, testingPhaseSet, aimsApp, AimsConstants.SMS_PLATFORM_ID, userType, null); 
			ApplicationsManagerHelper.saveTestPhases(session, testingPhaseSet, aimsApp, userType); 
			ApplicationsManagerHelper.saveCertifications(session, certificationSet, aimsApp, userType); 
			
    	session.flush();

    	/*
    	*UPDATING DATABASE FOR IMAGES
    	*/

			java.sql.Connection ConOra = null;	
    	ConOra = session.connection();
			
			//Cloning Images
			if (clonedFromAppId != null)
				TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.SMS_PLATFORM_ID);
			
			//Copying Images From Temp Table (if present)
    	TempFilesManager.copyImageFromTemp(ConOra, userGuide, aimsApp.getAppsId(), userId, 
																							ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, 
																							ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, testPlanResults, aimsApp.getAppsId(), userId, 
																							ManageApplicationsConstants.TEST_PLAN_RESULTS_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, messageFlow, aimsApp.getAppsId(), userId, 
																							ManageApplicationsConstants.MESSAGE_FLOW_BLOB_DB_INFO);
			
			ApplicationsManagerHelper.saveTestingPhaseFiles (ConOra, testingPhaseSet, aimsApp, userId, userType);
			
			ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);
			
			session.flush();
											       
			if (      		
					((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SAVED_ID.longValue())
					||
					(aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue()))
				)
			{
				TempFilesManager.checkForEmptyBlobs(ConOra, aimsApp.getAppsId());
			}
			      
			session.flush();
    	tx.commit();

		}
		
		catch(AimsException ax)
		{
			if (tx!=null)
				tx.rollback();
			throw ax;
		}
		
	  catch(JDBCException je)
		{
			if (tx!=null)
     			tx.rollback();
     
            AimsException aimsException = new AimsException("Error");
			//if (DBErrorFinder.searchUniqueConstraintErrors(je.getMessage(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException))
            //changed je.getMessage() to je.getCause().toString(), making it compatible with oracle.jar
            if (DBErrorFinder.searchUniqueConstraintErrors(je.getCause().toString(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException))
            {
				throw aimsException;
			}					
			else
			{
				je.printStackTrace();
				throw new HibernateException(je);						
			}
		}
		
		catch(HibernateException	e)
		{
			if (tx!=null)
					tx.rollback();
			log.debug("ROLLED BACK IN saveOrUpdateSmsApplication()");
			e.printStackTrace();
			throw	e;
		}
		finally
		{
			session.close();
			//Destroy All Form Files
			log.debug("SESSION CLOSED IN saveOrUpdateSmsApplication()");
		}
	}

	public static	HashMap	getApp(Long appsId, Long allianceId)	throws AimsException, HibernateException
	{
		AimsApp	aimsApp	=	null;
		Session	session	=	null;
		HashMap appSms = new HashMap();

		try
		{
			session	=	DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

    	queryStringBuffer.append(" 	select ")
						 						.append("					apps, smsapps, category	")
						 						.append(" from " )
						 						.append(" 				com.netpace.aims.model.application.AimsApp	as apps, ")
						 						.append(" 				com.netpace.aims.model.application.AimsSmsApp	as smsapps, ")
						 						.append(" 				com.netpace.aims.model.application.AimsAppSubCategory	as subcategory, ")
						 						.append(" 				com.netpace.aims.model.application.AimsAppCategory	as category, ")
						 						.append("					com.netpace.aims.model.core.AimsAllianc as alliance ")
						 						.append("	where ")
						 						.append("					apps.appsId = smsapps.smsAppsId ")
						 						.append("					and	apps.aimsAppSubCategoryId = subcategory.subCategoryId ")
												.append("					and	subcategory.aimsAppCategoryId = category.categoryId ")
												.append("					and apps.aimsAllianceId = alliance.allianceId ")	
												.append("					and	apps.appsId = :appsId" );

			//Set 'Alliance Id' if User is 'Alliance User' (allianceId=null). 
			if (allianceId!=null)
				queryStringBuffer.append("		and apps.aimsAllianceId = " + allianceId);
				
			Query	query	=	session.createQuery(queryStringBuffer.toString());
			query.setLong("appsId",	appsId.longValue());

			Object [] appValues = null;
			for	(Iterator	it = query.iterate();	it.hasNext();)
			{
				appValues  = (Object []) it.next();
				appSms.put("AimsApp",(AimsApp) appValues[0]);						// AimsApp
				appSms.put("AimsSmsApp",(AimsSmsApp) appValues[1]);		// AimsSmsApp
				appSms.put("AimsAppCategory",(AimsAppCategory) appValues[2]);		// AimsAppCategory
			}
			
			if (appSms.isEmpty())
			{
				AimsException aimsException = new AimsException("Error");
				aimsException.addException(new RecordNotFoundException("error.security"));
				throw aimsException;
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw	e;
		}
		finally
		{
			session.close();
			log.debug("SESSION CLOSED IN getApp()");
		}
		
		return appSms;
	}


}	 
