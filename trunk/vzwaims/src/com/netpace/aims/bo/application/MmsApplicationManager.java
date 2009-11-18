package	com.netpace.aims.bo.application;

import net.sf.hibernate.*;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.type.LongType;
import com.netpace.aims.model.*;
import com.netpace.aims.bo.core.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;

import org.apache.log4j.Logger;
import org.apache.struts.upload.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import oracle.sql.BLOB;

import com.netpace.aims.model.core.*;


/**
 * This	class	is responsible for getting the BO	for	MMS Application.
 * It	has	static methods for getting the MMS Application.
 * @author Adnan Makda
 */
public class MmsApplicationManager
{

	static Logger	log	=	Logger.getLogger(MmsApplicationManager.class.getName());


	/**
	*	 This	static method	updates	a	given	Mms Application.
	*/
	public static	void saveOrUpdateMmsApplication
									(AimsApp aimsApp, AimsMmsApp aimsMmsApp, AimsContact aimsContact, 
           					String userId, String userType, 
										Long screenJpeg, Long flashDemo,
										Long userGuide, Long splashScreenEps,
										Long activeScreenEps, Long faqDoc,
										Long testPlanResults, Long sampleContentFile,
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
    		aimsMmsApp.setMmsAppsId(aimsApp.getAppsId());
				session.save(aimsMmsApp);
				newApp = true;
    	}
    	else //Means to Update
    	{
    		session.saveOrUpdate(aimsApp);
    		aimsMmsApp.setMmsAppsId(aimsApp.getAppsId());
				session.update(aimsMmsApp);
				newApp = false;
    	}
    	
    	ApplicationsManagerHelper.addNewPhases(session, testingPhaseSet, aimsApp, AimsConstants.MMS_PLATFORM_ID, userType, null); 
    	
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
				TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.MMS_PLATFORM_ID);
			
			//Copying Images From Temp Table (if present)
    	TempFilesManager.copyImageFromTemp(ConOra, screenJpeg, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.SCREEN_JPEG_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, flashDemo, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.FLASH_DEMO_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, userGuide, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, splashScreenEps, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.SPLASH_SCREEN_EPS_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, activeScreenEps, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.ACTIVE_SCREEN_EPS_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);
      
      TempFilesManager.copyImageFromTemp(ConOra, testPlanResults, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.TEST_PLAN_RESULTS_BLOB_DB_INFO);
      TempFilesManager.copyImageFromTemp(ConOra, sampleContentFile, aimsApp.getAppsId(), userId, 
																										ManageApplicationsConstants.SAMPLE_CONTENT_BLOB_DB_INFO);
      

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
			log.debug("ROLLED BACK IN saveOrUpdateMmsApplication()");
			e.printStackTrace();
			throw	e;
		}
		finally
		{
			session.close();
			//Destroy All Form Files
			
			log.debug("SESSION CLOSED IN saveOrUpdateMmsApplication()");
		}
	}

	public static	HashMap	getApp(Long appsId, Long allianceId)	throws AimsException, HibernateException
	{
		AimsApp	aimsApp	=	null;
		Session	session	=	null;
		HashMap appMms = new HashMap();

		try
		{
			session	=	DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

    	queryStringBuffer.append(" 	select ")
						 						.append("					apps, mmsapps, category	")
						 						.append(" from " )
						 						.append(" 				com.netpace.aims.model.application.AimsApp	as apps, ")
						 						.append(" 				com.netpace.aims.model.application.AimsMmsApp	as mmsapps, ")
						 						.append(" 				com.netpace.aims.model.application.AimsAppSubCategory	as subcategory, ")
						 						.append(" 				com.netpace.aims.model.application.AimsAppCategory	as category, ")
						 						.append("					com.netpace.aims.model.core.AimsAllianc as alliance ")
						 						.append("	where ")
						 						.append("					apps.appsId = mmsapps.mmsAppsId ")
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
				log.debug("AimsApp" + (AimsApp) appValues[0]);
				appMms.put("AimsApp",(AimsApp) appValues[0]);						// AimsApp
				log.debug("AimsMmsApp" + (AimsMmsApp) appValues[1]);
				appMms.put("AimsMmsApp",(AimsMmsApp) appValues[1]);		// AimsMmsApp
				log.debug("AimsAppCategory" + (AimsAppCategory) appValues[2]);
				appMms.put("AimsAppCategory",(AimsAppCategory) appValues[2]);		// AimsAppCategory
			}
			
			if (appMms.isEmpty())
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
		
		return appMms;
	}

}	 
