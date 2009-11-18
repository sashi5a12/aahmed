package com.netpace.aims.bo.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.StringType;
import net.sf.hibernate.type.Type;
import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.controller.application.JavaApplicationHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsAppFiles;
import com.netpace.aims.model.application.AimsAppPhase;
import com.netpace.aims.model.application.AimsJavaAppClob;
import com.netpace.aims.model.application.AimsJavaApps;
import com.netpace.aims.model.application.AimsJavaFiles;
import com.netpace.aims.model.application.AimsVzwTestingPhase;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.DBUtils;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * This	class	is responsible for getting the BO	for	Wap	Application.
 * It	has	static methods for getting the Wap Application.
 * @author Fawad Sikandar
 */
public class JavaApplicationManager
{

    static Logger log = Logger.getLogger(JavaApplicationManager.class.getName());

    
    public static AimsContract getLatestAcceptedContract(Long allianceId, Long platformId) throws HibernateException 
    {
    	if ( log.isDebugEnabled() )
    		log.debug("Enter: getLatestAcceptedContract");    	
    	
        List<AimsContract> contracts = null;
        AimsContract aimsContract = null;
        Session	session	=	null;
        StringBuffer queryStringBuffer = new StringBuffer();
        
        try 
        {
            session = DBHelper.getInstance().getSession();
            
            queryStringBuffer.append("select aimsContract ")
            .append("from ")
            .append("		com.netpace.aims.model.core.AimsContract aimsContract, ")
            .append("		com.netpace.aims.model.core.AimsAllianceContract aimsAllianceContract ")
            .append("where ")
            .append("		aimsContract.contractId = aimsAllianceContract.contractId ")
            .append("		and aimsAllianceContract.allianceId = :allianceId ")
            .append("		and aimsContract.platformId = :platformId ")
            .append("		and aimsAllianceContract.status = 'ACCEPTED' ")
            .append("		and aimsContract.status = :contract_status ")
            .append("		order by aimsAllianceContract.acceptDeclineDate desc ");

            Object[] argsVal = {
            			allianceId,
            			platformId,
            			AimsConstants.CONTRACT_STATUS_ACTIVE
            		};
            
            Type[] argsType = {
        			new LongType(),
        			new LongType(),
        			new StringType()
        		};
            
            contracts = session.find(queryStringBuffer.toString(), argsVal,  argsType);
            aimsContract = contracts.get(0);            

            if ( log.isDebugEnabled() )
            {
            	log.debug("Total Number of accepted contracts for alliance : " +allianceId+ " : " + contracts.size());
            	log.debug("Contract id of latest contract accepted is : " + aimsContract.getContractId());            	
            }
        }
        catch(HibernateException he) 
        {
            he.printStackTrace();
            throw	he;
        }
        finally 
        {
            if(session!=null) 
            {
                session.close();
                if ( log.isDebugEnabled() )
                	log.debug("Session closed in getContracts()");
            }
        }
        
        if ( log.isDebugEnabled() )
    		log.debug("Exit: getLatestAcceptedContract");
        
        return aimsContract;
    }
    
    public static Collection getContracts(Long allianceId) throws HibernateException {
        Collection contracts = null;
        Session	session	=	null;
        StringBuffer queryStringBuffer = new StringBuffer();
        try {
            session = DBHelper.getInstance().getSession();
            
            queryStringBuffer.append("select aimsContract ")
            .append("from ")
            .append("		com.netpace.aims.model.core.AimsContract aimsContract, ")
            .append("		com.netpace.aims.model.core.AimsAllianceContract aimsAllianceContract ")
            .append("where ")
            .append("		aimsContract.contractId = aimsAllianceContract.contractId ")
            .append("		and aimsAllianceContract.allianceId = :allianceId ")
            .append("		and aimsContract.platformId = :platformId ")
            .append("		and aimsAllianceContract.status = 'ACCEPTED' ")
            .append("		and aimsContract.status = :contract_status ");

            Object[] argsVal = {
            			allianceId,
            			AimsConstants.JAVA_PLATFORM_ID,
            			AimsConstants.CONTRACT_STATUS_ACTIVE
            		};
            
            Type[] argsType = {
        			new LongType(),
        			new LongType(),
        			new StringType()
        		};
            
            contracts = session.find(queryStringBuffer.toString(), argsVal,  argsType);

            if ( log.isDebugEnabled() )
            {
            	log.debug("No. of Contracts : " + contracts.size());
            	log.debug("&....alliance Id : " + allianceId);
            }	
            
        }
        catch(HibernateException he) {
            he.printStackTrace();
            throw	he;
        }
        finally {
            if(session!=null) {
                session.close();
                if ( log.isDebugEnabled() )
                	log.debug("Session closed in getContracts()");
            }
        }
        return contracts;

    }//end getTypesByTypeDefId

    /*
     * This function fetches the JAVA Application. Called when user wants to View/Edit/Clone the Application.
     */
    public static HashMap getJavaApp(Long appsId) throws AimsException, HibernateException
    {        
    		Session session = null;
    		HashMap appJava = new HashMap();

    		try {
    			session = DBHelper.getInstance().getSession();
    			StringBuffer queryStringBuffer = new StringBuffer();

    			queryStringBuffer
    					.append(" select ")
    					.append("               apps, javaApp ")
    					.append(" from ")
    					.append("               AimsApp	as apps, ")
    					.append("               AimsJavaApps	as javaApp, ")
    					.append("               AimsAllianc as alliance ")    										
    					.append(" where ")
    					.append("               apps.appsId = javaApp.javaAppsId ")    					    					
    					.append("               and apps.aimsAllianceId = alliance.allianceId ")
    					.append("               and	apps.appsId = :appsId ");
    			
    			Query query = session.createQuery(queryStringBuffer.toString());
    			query.setLong("appsId", appsId.longValue());

    			Object[] appValues = null;
    			for (Iterator it = query.iterate(); it.hasNext();) {
    				appValues = (Object[]) it.next();
    				appJava.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
    				appJava.put("AimsJavaApp", (AimsJavaApps) appValues[1]); // AimsDashboardApp    				    				
    			}
    			if (appJava.isEmpty()) {
    				AimsException aimsException = new AimsException("Error");
    				aimsException.addException(new RecordNotFoundException("error.security"));
    				throw aimsException;
    			}   			
    			
    			queryStringBuffer = new StringBuffer();
    			queryStringBuffer
				.append(" select category	")
				.append(" from ")
				.append("               AimsApp	as apps, ")
				.append("               AimsAppSubCategory	as subcategory, ")
				.append("               AimsAppCategory	as category ")
				.append(" where ")
				.append("               apps.aimsAppSubCategoryId = subcategory.subCategoryId ")
				.append("               and	subcategory.aimsAppCategoryId = category.categoryId ")    					
				.append("               and	apps.appsId = :appsId ");
		
    			query = session.createQuery(queryStringBuffer.toString());
    			query.setLong("appsId", appsId.longValue());

    			for (Iterator it = query.iterate(); it.hasNext();) {
    					 
    					appJava.put("AimsAppCategory", (AimsAppCategory) it.next()); // AimsAppCategory    				
    			}
    			
    		} catch (HibernateException e) {
    			log.error(e,e);
    			throw e;
    		} finally {
    			session.close();
    			if ( log.isDebugEnabled() )
    				log.debug("SESSION CLOSED IN getApp()");
    		}

    		return appJava;    	    
      }
    
    public static void saveOrUpdateJavaApplication(
			AimsApp aimsApp,
			AimsJavaApps javaApp,
			AimsJavaAppClob javaClobs,
			AimsContact aimsContact, 
			String userId,
			String userType,
			Long clrPubLogo,
			Long appTitleName,
			Long splashScreenEps,
			Long activeScreenEps,
			Long screenJpeg, 
			Long screenJpeg2,
			Long screenJpeg3,
			Long screenJpeg4, 
			Long screenJpeg5,
			Long faqDoc,
			Long companyLogoFile,
			Long titleImageFile,
			boolean checkForEmptyFiles, 
			Long clonedFromAppId) throws AimsException, HibernateException, Exception {
		
    	if ( log.isDebugEnabled() )
    		log.debug("JavaApplicationManager.saveOrUpdateJavaApplication: Start");
		
		Session session = null;
		Transaction tx = null;
		boolean newApp = false;
		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		java.sql.PreparedStatement prepStmt =  null;
        java.sql.ResultSet rs = null;
        
		try 
		{
			session = DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			// If Aims Contact Id provided zero (0), then create new Contact
			if ((aimsApp.getAimsContactId() != null) && (aimsApp.getAimsContactId().longValue() == 0)) {
				// If All Non-Nullable Fields provided for Contact, then create new Contact
				if ((aimsContact.getFirstName().length() > 0)
						&& (aimsContact.getLastName().length() > 0)
						&& (aimsContact.getEmailAddress().length() > 0)
						&& (aimsContact.getTitle().length() > 0))
					aimsApp.setAimsContactId(AimsApplicationsManager.saveOrUpdateContact(aimsContact));
				else
					aimsApp.setAimsContactId(null);
			}

			//SAVING APP IN DATABASE
			if (aimsApp.getAppsId() == null) {
				javaApp.setAppRefId(DBUtils.generateSequenceId("SEQ_APP_REF_ID", session.connection()));
				
				session.saveOrUpdate(aimsApp);
				javaApp.setJavaAppsId(aimsApp.getAppsId());
				session.save(javaApp);
				newApp = true;
			} 
			else {
				session.saveOrUpdate(aimsApp);
				javaApp.setJavaAppsId(aimsApp.getAppsId());
				session.update(javaApp);
				newApp = false;
			}

			session.flush();

			ConOra = session.connection();
			
			updateAimsJavaApps(aimsApp.getAppsId(), aimsApp.getTitle(), aimsApp.getShortDesc(), aimsApp.getLongDesc(), ConOra);
			//Save/Update user guide clobs.
			
            if (javaClobs != null){
    			long bytesWrote = 0;
    			StringBuffer strQuery=new StringBuffer();
    			strQuery.append("update aims_java_apps ");
    			strQuery.append("set using_application=EMPTY_CLOB(), tips_and_tricks=EMPTY_CLOB(), faq=EMPTY_CLOB(), troubleshooting=EMPTY_CLOB(), ");
    			strQuery.append("development_company_disclaimer=EMPTY_CLOB(), additional_information=EMPTY_CLOB() WHERE java_apps_id=?");
    			prepStmt = ConOra.prepareStatement(strQuery.toString());
    			prepStmt.setLong(1, javaApp.getJavaAppsId().longValue());
    			prepStmt.executeUpdate();    			
    			prepStmt.close();
    			
    			prepStmt=ConOra.prepareStatement("select using_application, tips_and_tricks, faq, troubleshooting, development_company_disclaimer, additional_information from aims_java_apps where java_apps_id = ? for update");
    			prepStmt.setLong(1, javaApp.getJavaAppsId().longValue());
    			rs = prepStmt.executeQuery();    			
    			rs.next();
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("using_application"), javaClobs.getUsingApplicationStr());
    			if ( log.isDebugEnabled() )
    				log.debug("Using Application bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("tips_and_tricks"), javaClobs.getTipsAndTricksStr());
    			if ( log.isDebugEnabled() )
    				log.debug("Tip and tricks bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("faq"), javaClobs.getFaqStr());
    			if ( log.isDebugEnabled() )
    				log.debug("Faq bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("troubleshooting"), javaClobs.getTroubleshootingStr());
    			if ( log.isDebugEnabled() )
    				log.debug("Troubleshooting bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("development_company_disclaimer"), javaClobs.getDevelopmentCompanyDisclaimerStr());
    			if ( log.isDebugEnabled() )
    				log.debug("Dev. Company Disclaimer bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("additional_information"), javaClobs.getAdditionalInformationStr());
    			if ( log.isDebugEnabled() )
    				log.debug("Additional Information bytesWrote="+bytesWrote);    			    			
            }               
			

            //Cloning Images
            if (clonedFromAppId != null)
            {
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.JAVA_PLATFORM_ID);                
            }
			
			// Copying Images From Temp Table (if present)
			TempFilesManager.copyImageFromTemp(ConOra, clrPubLogo, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_HR_PUBLISHER_LOGO_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, appTitleName, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_CHNL_TITLE_ICON_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, splashScreenEps, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SPLASH_SCREEN_EPS_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, activeScreenEps, aimsApp.getAppsId(), userId, ManageApplicationsConstants.ACTIVE_SCREEN_EPS_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg2, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_2_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg3, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_3_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg4, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_4_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg5, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_5_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, companyLogoFile, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_COMPANY_LOGO_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, titleImageFile, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_APP_TITLE_NAME_BLOB_DB_INFO);

			ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);

			session.flush();

			tx.commit();
		}

		catch (AimsException ax) 
		{
			if (tx != null)
				tx.rollback();
			throw ax;
		}

		catch (JDBCException je) 
		{
			if (tx != null)
				tx.rollback();

			AimsException aimsException = new AimsException("Error");
			if (DBErrorFinder.searchUniqueConstraintErrors(je.getCause().toString(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException)) 
			{
				throw aimsException;
			} 
			else 
			{
				log.error(je,je);
				throw new HibernateException(je);
			}
		}

		catch (HibernateException e) 
		{
			if (tx != null)
				tx.rollback();
			if ( log.isDebugEnabled() )
				log.debug("ROLLED BACK IN saveOrUpdateJavaApplication()");
			log.error(e,e);
			throw e;
		} 
		finally 
		{

			if (statement != null) 
			{
				try 
				{
					statement.close();
				} 
				catch (Exception ignore) 
				{
				}
			}
			if (rs != null)
			{
            	rs.close();
            }
            if (prepStmt != null)
            {
            	prepStmt.close();
            }  
			session.close();
			if ( log.isDebugEnabled() )
				log.debug("SESSION CLOSED IN saveOrUpdateJavaApplication()");
		}
		if ( log.isDebugEnabled() )
			log.debug("JavaApplicationManager.saveOrUpdateJavaApplication: End");
	}
    
    public static void updateAimsJavaApps(Long appId, String title, String sDesc, String lDesc, java.sql.Connection ConOra) throws Exception
    {
    	OraclePreparedStatement pstmt = null;
    	try 
    	{
    		if ( log.isDebugEnabled() )
    			log.debug(" Updating title, short_desc and long_desc in aims_java_apps for " + appId);
			
			String query = "update AIMS_JAVA_APPS javaApp set title=?, short_desc=?, long_desc=? where JAVA_APPS_ID=?";
			
			if ( log.isDebugEnabled() )
				log.debug("SQL = " + query);
			
			pstmt = (OraclePreparedStatement) ConOra.prepareStatement(query);
			
			if ( log.isDebugEnabled() )
				log.debug("Test String ="+title);
			 
			pstmt.setFormOfUse(1, OraclePreparedStatement.FORM_NCHAR);
			pstmt.setString(1, title);
			
			 
			pstmt.setFormOfUse(2, OraclePreparedStatement.FORM_NCHAR);
			pstmt.setString( 2, sDesc);
			
			pstmt.setFormOfUse(3, OraclePreparedStatement.FORM_NCHAR);
			pstmt.setString(3, lDesc);
			
			pstmt.setLong(4, appId);
			
			int i = pstmt.executeUpdate();
			
			if ( log.isDebugEnabled() )
				log.debug("Number of records updated = " + i);
			
    	} 
    	catch (Exception e) 
    	{
    		log.debug("Exception occured while updating aims_java_apps(title, short_desc, long_desc) with unicode strings");
    		e.printStackTrace();
    		throw e;
			
		}
    	finally
        {
            if (pstmt != null)
            	pstmt.close();
        }
		
    }
    
    /*
     * Method to get Journal Entries
     */
    public static Collection getJournalEntries(Long applicationId, Long allianceId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       journalEntry ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsJournalEntry as journalEntry, ")
                .append("       com.netpace.aims.model.application.AimsAppLite as app ")
                .append("   where ")
                .append("       journalEntry.aimsAppId = app.appsId ")
                .append("       and journalEntry.aimsAppId = :appsId");


            //mnauman@netpace : have filtered the results as we needed latter
            
            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
//            if (allianceId != null)
//            {
//                queryStringBuffer.append("      and app.aimsAllianceId = :allianceId ");
//                queryStringBuffer.append("      and journalEntry.journalType = :journalType ");
//            }

            queryStringBuffer.append("  order by    journalEntry.createdDate desc ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", applicationId.longValue());
//            if (allianceId != null)
//            {
//                query.setLong("allianceId", allianceId.longValue());
//                query.setString("journalType", AimsConstants.JOURNAL_TYPE_PUBLIC);
//            }

            collection = query.list();

        }
        catch (HibernateException e)
        {
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    /*
     * This method gets all the Java Versions (generated in the 'Pending DCR' status)
     */
    public static Collection getJavaAppVersions(Long javaAppsId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer.append(" select   distinct javaAppsVersion.oldVersion ");
            queryStringBuffer.append(" from     com.netpace.aims.model.application.AimsJavaAppsVersion javaAppsVersion ");
            queryStringBuffer.append(" where    javaAppsVersion.javaAppsId = :javaAppsId ");
            queryStringBuffer.append(" order    by old_version desc ");
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("javaAppsId", javaAppsId.longValue());
            collection = query.list();
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
        return collection;
    }

    public static Collection getLicenseTypes() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsJavaLicenseType as licenseType ");
            query.append(" order by license_type_name ");
            collection = session.find(query.toString());
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    public static Collection getTaxCategoryCodes() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.masters.AimsTaxCategoryCode as taxCategoryCode ");
            query.append(" order by tax_category_code_id ");
            collection = session.find(query.toString());
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return collection;
    }

    /*
     * This method validates the Business Logic, that no two applications of the same Alliance (Content Provider)
     * can have the same Vendor Product Code.
     */
    public static boolean checkUniqueVendorProductCode(String vendorProductCode, Long allianceId, Long appsId) throws HibernateException
    {
        Session session = null;
        boolean uniqueValue = true;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("select ")
                .append("       javaApp.vendorProductCode ")
                .append("from ")
                .append("       com.netpace.aims.model.application.AimsJavaApp javaApp, ")
                .append("       com.netpace.aims.model.application.AimsAppLite app, ")
                .append("where  ")
                .append("       app.appsId = javaApp.javaAppsId ")
                .append("       and app.aimsAllianceId =  :allianceId ")
                .append("       and javaApp.vendorProductCode =  :vendorProductCode ");

            if (appsId != null)
                queryStringBuffer.append(" and app.appsId != :appsId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setString("vendorProductCode", vendorProductCode);
            query.setLong("allianceId", allianceId.longValue());
            if (appsId != null)
                query.setLong("appsId", appsId.longValue());

            if (query.list().size() > 0)
                uniqueValue = false;
        }

        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return uniqueValue;
    }

    /*
     * This method saves/updated the JAVA Application
     */    
    public static void saveOrUpdateJavaApplication(
        AimsApp aimsApp,
        AimsJavaApps aimsJavaApp,
        AimsContact aimsContact,
        String userId,
        String userType,
        Long productLogo,
        Long productIcon,
        Long screenJpeg,
        Long userGuide,
        Long faqDoc,
        Long presentation,
        Long appMediumLargeImage,
        Long appQVGAPotraitImage,
        Long appQVGALandscapeImage,
        Set testingPhaseSet,
        HashMap testResultFileIdsInfo,
        Long clonedFromAppId,
        HashMap oldValuesMap,
        Long oldStatus,
        Set testUpdatedJournalEntry,
        String dateFormat)
        throws AimsException, HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;
        boolean newApp = false;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            //If aimsContact is not NULL, AND All Non-Nullable Fields have been provided for Contact, then create new Contact.
            if ((aimsContact != null)
                && (aimsContact.getFirstName().length() > 0)
                && (aimsContact.getLastName().length() > 0)
                && (aimsContact.getEmailAddress().length() > 0)
                && (aimsContact.getTitle().length() > 0))
                aimsApp.setAimsContactId(AimsApplicationsManager.saveOrUpdateContact(aimsContact));

            HashMap newValuesMap = JavaApplicationHelper.getValuesMapForVersion(aimsApp, aimsJavaApp, dateFormat);

            //Versions are updated if Application being saved while in Pending DCR Status
            //OR
            //Version is updated when Application moves from 'Pending DCR' to 'Submitted DCR' Status.
            if (((oldStatus.longValue() == aimsApp.getAimsLifecyclePhaseId().longValue())
                && (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue()))
                || ((oldStatus.longValue() != aimsApp.getAimsLifecyclePhaseId().longValue())
                    && (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())))
                /*
            	aimsJavaApp.setPendingDcrVersion(
                    updateVersions(session, oldValuesMap, newValuesMap, aimsJavaApp.getPendingDcrVersion(), aimsJavaApp.getJavaAppsId(), userId));
*/
            /*
                *SAVING APP IN DATABASE
            */

            if (aimsApp.getAppsId() == null) //Saving
            {
                session.saveOrUpdate(aimsApp);
                aimsJavaApp.setJavaAppsId(aimsApp.getAppsId());
                session.save(aimsJavaApp);
                newApp = true;

            }
            else //Updating
                {
                session.saveOrUpdate(aimsApp);
                aimsJavaApp.setJavaAppsId(aimsApp.getAppsId());
                session.update(aimsJavaApp);
                newApp = false;
            }

            if ((userType.equals(AimsConstants.ALLIANCE_USERTYPE))
                && (oldStatus.longValue() != aimsApp.getAimsLifecyclePhaseId().longValue())
                && (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue()))
                addNewPhases(session, aimsApp.getAppsId());

            if ((userType.equals(AimsConstants.VZW_USERTYPE)) && (testingPhaseSet != null))
                for (java.util.Iterator itr = testingPhaseSet.iterator(); itr.hasNext();)
                    session.saveOrUpdate((AimsAppPhase) itr.next());

            session.flush();

            //if network usage is enabled, then save network urls, if disabled then delete old urls
                if(StringFuncs.isNullOrEmpty(aimsApp.getNetworkUsage()) || aimsApp.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE))
                {
                    ApplicationsManagerHelper.deleteAppNetworkUrls(session, aimsApp.getAppsId());
                }
                else if(aimsApp.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE))
                {
                    ApplicationsManagerHelper.saveAppNetworkUrls(session, aimsApp.getAppNetworkURLs(), aimsApp.getAppsId());
                }
            //end savenetwork urls

            /*
                UPDATING   DATABASE FOR IMAGES
            */

            java.sql.Connection ConOra = null;
            ConOra = session.connection();

            //Cloning Images
            if (clonedFromAppId != null)
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.JAVA_PLATFORM_ID);

            //Copying Images From Temp Table (if present)
            TempFilesManager.copyImageFromTemp(ConOra, productLogo, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_PRODUCT_LOGO_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, productIcon, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_PRODUCT_ICON_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, userGuide, aimsApp.getAppsId(), userId, ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, presentation, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_PRESENTATION_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appMediumLargeImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_APP_IMG_MEDIUM_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appQVGAPotraitImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_APP_IMG_POTRAIT_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appQVGALandscapeImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.JAVA_APP_IMG_LANDSCAPE_BLOB_DB_INFO);

            for (Iterator it = testResultFileIdsInfo.keySet().iterator(); it.hasNext();)
            {
                String key = (String) it.next();

                TempFilesManager.copyImageFromTemp(
                    ConOra,
                    new Long(key),
                    aimsApp.getAppsId(),
                    (Long[]) testResultFileIdsInfo.get(key),
                    userId,
                    ManageApplicationsConstants.RESULTS_FILE_BLOB_DB_INFO);
            }
            //End Of Copying Images From Temp Table (if present)

            ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);

            for (java.util.Iterator itr = testUpdatedJournalEntry.iterator(); itr.hasNext();)
                AimsApplicationsManager.addSpecificJournalEntry(ConOra, aimsApp.getAppsId(), (String) itr.next(), AimsConstants.JOURNAL_TYPE_PRIVATE, null);

            session.flush();

            if (((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SAVED_ID.longValue())
                || (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue())))
            {
                TempFilesManager.checkForEmptyBlobs(ConOra, aimsApp.getAppsId());
            }

            session.flush();
            tx.commit();

        }

        catch (AimsException ax)
        {
            if (tx != null)
                tx.rollback();
            throw ax;
        }

        catch (JDBCException je)
        {
            if (tx != null)
                tx.rollback();

            AimsException aimsException = new AimsException("Error");
            if (DBErrorFinder.searchUniqueConstraintErrors(je.getMessage(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException))
            {
                throw aimsException;
            }
            else
            {
                je.printStackTrace();
                throw new HibernateException(je);
            }
        }

        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            if ( log.isDebugEnabled() )
            	log.debug("ROLLED BACK  IN saveOrUpdateJavaApplication()");
            e.printStackTrace();
            throw e;
        }

        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();
            if ( log.isDebugEnabled() )
            	log.debug("ROLLED BACK  IN saveOrUpdateJavaApplication()");
            ex.printStackTrace();
            throw ex;
        }

        finally
        {
            session.close();
            if ( log.isDebugEnabled() )
            	log.debug("SESSION  CLOSED IN   saveOrUpdateJavaApplication()");
        }
    }

    /*
     * This is the place where Testing Collection is filled (ONLY ONCE) on change of status to Submit.
     */
    
    public static void addNewPhases(Session session, Long appsId) throws HibernateException
    {
        try
        {
            AimsDevic aimsDevice = AimsDevicesManager.getDevice(AimsConstants.NON_EXISTANT_DEVICE_ID);
            for (java.util.Iterator itr = AimsApplicationsManager.getTestingPhases(AimsConstants.JAVA_PLATFORM_ID).iterator(); itr.hasNext();)
            {
                AimsVzwTestingPhase aimsVzwTestingPhase = (AimsVzwTestingPhase) itr.next();
                AimsAppPhase aimsAppPhase = new AimsAppPhase();
                aimsAppPhase.setAppsId(appsId);
                aimsAppPhase.setAimsVzwTestingPhase(aimsVzwTestingPhase);
                aimsAppPhase.setAimsDevice(aimsDevice);
                aimsAppPhase.setCreatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                aimsAppPhase.setCreatedDate(new Date());
                aimsAppPhase.setLastUpdatedBy(AimsConstants.RECORD_CREATED_BY_SYSTEM);
                aimsAppPhase.setLastUpdatedDate(new Date());
                session.save(aimsAppPhase);
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {}
    }   
    
    /*
     * Created by Waseem Akram
     * This method is used by ApplicationInfo Webservice
     * Takes application keyword as input which is unique column in database
     * Returns a hashmap which contains AimsApp and AimsJavaApp object
     */
    public static HashMap javaAppByKeyword(String keyword) throws AimsException, HibernateException
    {
    	if ( log.isDebugEnabled() )
    		log.debug("JavaApplicationManager.javaAppByKeyword: Start");
    	Session session = null;
		HashMap appJava = new HashMap();
		
		try 
		{
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer
			.append(" select ")
			.append("               apps, javaApp, alliance	")
			.append(" from ")
			.append("               AimsApp	as apps, ")
			.append("               AimsJavaApps	as javaApp, ")
			.append("               AimsAllianc as alliance ")
			.append(" where ")
			.append("               apps.appsId = javaApp.javaAppsId ")
			.append("               and apps.aimsAllianceId = alliance.allianceId ")
			.append("               and	javaApp.appKeyword = :keyword ");

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setString("keyword", keyword);

			Object[] appValues = null;
			Iterator it = query.iterate();
			if ( it.hasNext() ) 
			{
				appValues = (Object[]) it.next();
				
				appJava.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
				appJava.put("AimsJavaApp", (AimsJavaApps) appValues[1]); // AimsJavaApp				
				appJava.put("AimsAlliance", (AimsAllianc) appValues[2]); // AimsAllianc
			}
			
			if (appJava.isEmpty())
				throw new RecordNotFoundException();				
			
		} 
		catch (HibernateException e) 
		{
			log.error(e,e);
			throw e;
		} 
		finally 
		{
			session.close();
			log.debug("SESSION CLOSED IN javaAppByKeyword()");
		}
		if ( log.isDebugEnabled() )
			log.debug("JavaApplicationManager.javaAppByKeyword: End");
		return appJava;
	}

    public static AimsJavaAppClob getClobs(Long javaId) throws Exception
    {
    	if ( log.isDebugEnabled() )
    		log.debug("JavaApplicationManager.getClobs start: javaId = " + javaId);
        Session session = null;
        AimsJavaAppClob clobs = new AimsJavaAppClob(); 
        try{
        	String queryStr="from AimsJavaAppClob a where a.javaAppsId = ?";
        	session = DBHelper.getInstance().getSession();
        	
        	Collection collection=session.find(queryStr, javaId, new LongType());
        	for (Iterator iter = collection.iterator(); iter.hasNext();){
        		clobs=(AimsJavaAppClob)iter.next();
			}
        	
        	if (clobs != null){
        		clobs.setUsingApplicationStr(null);
        		clobs.setTipsAndTricksStr(null);
        		clobs.setFaqStr(null);
        		clobs.setTroubleshootingStr(null);
        		clobs.setDevelopmentCompanyDisclaimerStr(null);
        		clobs.setAdditionalInformationStr(null);
        		
	        	if (clobs.getUsingApplication() != null){
	        		clobs.setUsingApplicationStr(clobs.getUsingApplication().getSubString(1, (int)clobs.getUsingApplication().length()));
	        		clobs.setUsingApplication(null);
	        	}
	        	if (clobs.getTipsAndTricks() != null){
	        		clobs.setTipsAndTricksStr(clobs.getTipsAndTricks().getSubString(1, (int)clobs.getTipsAndTricks().length()));
	        		clobs.setTipsAndTricks(null);
	        	}
	        	if (clobs.getFaq() != null){
	        		clobs.setFaqStr(clobs.getFaq().getSubString(1, (int)clobs.getFaq().length()));
	        		clobs.setFaq(null);
	        	}
	        	if (clobs.getTroubleshooting() != null){
	        		clobs.setTroubleshootingStr(clobs.getTroubleshooting().getSubString(1, (int)clobs.getTroubleshooting().length()));
	        		clobs.setTroubleshooting(null);
	        	}
	        	if (clobs.getDevelopmentCompanyDisclaimer() != null){
	        		clobs.setDevelopmentCompanyDisclaimerStr(clobs.getDevelopmentCompanyDisclaimer().getSubString(1, (int)clobs.getDevelopmentCompanyDisclaimer().length()));
	        		clobs.setDevelopmentCompanyDisclaimer(null);
	        	}
	        	if (clobs.getAdditionalInformation() != null){
	        		clobs.setAdditionalInformationStr(clobs.getAdditionalInformation().getSubString(1, (int)clobs.getAdditionalInformation().length()));
	        		clobs.setAdditionalInformation(null);
	        	}
        	}
        }
    	catch (HibernateException e){
            log.error(e,e);
            throw e;
        }
    	catch (SQLException e){
    		log.error(e,e);
    		throw e;
    	}
        finally{
            session.close();
        }
        if ( log.isDebugEnabled() )
        	log.debug("JavaApplicationManager.getClobs end:");
    	return clobs;
    }
   
    /** Returns the application against provided key word and if not found, returns null
     * @param keyword
     * @return
     * @throws HibernateException
     * @author mnauman 
     */
    
	public static HashMap getApplicationWithKeyword(String keyword, Long appId) throws HibernateException {
		Session session = null;
		HashMap appJava = new HashMap();

		try {
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer
					.append(" select ")
					.append("               apps, javaApp	")
					.append(" from ")
					.append("               AimsApp	as apps, ")
					.append("               AimsJavaApps	as javaApp ")
					.append(" where ")
					.append("               apps.appsId = javaApp.javaAppsId ")
					.append("               and	lower(javaApp.appKeyword) = :keyword ")
					.append("               and	apps.aimsLifecyclePhaseId !="+AimsConstants.SAVED_ID + " " )
					.append("               and	apps.aimsLifecyclePhaseId !="+AimsConstants.PHASE_JAVA_REJECTED + " " );
			if ( appId!=null && appId!=0)
				queryStringBuffer.append("               and	apps.appsId != :appId ");

			//Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
//			if (allianceId != null)
//				queryStringBuffer.append("				and apps.aimsAllianceId = "+ allianceId);

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setString("keyword", keyword.toLowerCase());
			//query.setLong("aimsLifecyclePhaseId", AimsConstants.SAVED_ID);
			if ( appId!=null && appId!=0 )
				query.setLong("appId", appId);

			Object[] appValues = null;
			for (Iterator it = query.iterate(); it.hasNext();) {
				appValues = (Object[]) it.next();
				appJava.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
				appJava.put("AimsJavaApp", (AimsJavaApps) appValues[1]); // AimsDashboardApp				    				
			}
			if (appJava.isEmpty()) {
				return null;
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
			if ( log.isDebugEnabled() )
				log.debug("SESSION CLOSED IN getApp()");
		}

		return appJava;
	}

    /** To initialize the work flow for java application
     * 
     * @param currUser
     * @param aimsApp
     * @param ringNumber
     * @throws HibernateException
     * @throws AimsException
     */
	
	public static void initiateWorkflow(String currUser, AimsApp aimsApp,Long ringNumber) throws HibernateException, AimsException 
	{
		if(ringNumber.equals(AimsConstants.CONTRACT_RING_2_ID)  )
		{
			if ( log.isDebugEnabled() )
				log.debug("Initiating Java onDeck workflow for app: "+ aimsApp.getTitle());
			
			WorkflowManager.initiateWorkflow(aimsApp.getAppsId(),AimsConstants.WORKFLOW_JAVA_ONDECK_APP, currUser,new Date());
		}
		else if (ringNumber.equals(AimsConstants.CONTRACT_RING_3_ID) ) 
		{
			if ( log.isDebugEnabled() )
				log.debug("Initiating Java onDeck workflow for app: "+ aimsApp.getTitle());
			
			WorkflowManager.initiateWorkflow(aimsApp.getAppsId(),AimsConstants.WORKFLOW_JAVA_OFFDECK_APP, currUser,new Date());
		}
	}
	
	
	
	
    public static HashMap getUserGuideData(Long appsId, Long allianceId)throws HibernateException, SQLException{
    	if ( log.isDebugEnabled() )
    		log.debug("JavaApplicationManager.getUserGuideData Start: appsId= "+appsId);
    	HashMap appMap=new HashMap();
    	Session session=null;
    	PreparedStatement prepStmt=null;
    	ResultSet rs=null;
    	AimsApp app=new AimsApp();
    	AimsJavaApps javaApp=new AimsJavaApps();
    	AimsAppFiles appFiles=new AimsAppFiles();
    	AimsJavaFiles javaFiles = new AimsJavaFiles();
    	AimsJavaAppClob javaClobs = new AimsJavaAppClob(); 

    	try {
    		session=DBHelper.getInstance().getSession();
    		StringBuffer sb=new StringBuffer();
    		sb.append("SELECT app.title,");
    		sb.append("		app.screen_jpeg, app.screen_jpeg_2,");
    		sb.append("		app.screen_jpeg_3, app.screen_jpeg_4,");
    		sb.append("		app.screen_jpeg_5, ");
    		sb.append("		java.company_logo, java.APP_TITLE_NAME,");
    		sb.append("		java.product_description, java.using_application,");
    		sb.append("		java.tips_and_tricks, java.faq, java.troubleshooting, ");
    		sb.append("		java.DEVELOPMENT_COMPANY_DISCLAIMER, java.ADDITIONAL_INFORMATION");
    		sb.append("	FROM aims_apps app, aims_java_apps java");
    		sb.append("	WHERE app.apps_id = java.java_apps_id (+)");
    		sb.append("	AND app.apps_id = ? ");
    		if (allianceId != null){
    			sb.append(" AND app.alliance_id=?");
    		}

    		Connection con=session.connection();
    		prepStmt=con.prepareStatement(sb.toString());
    		prepStmt.setLong(1, appsId.longValue());
    		if (allianceId != null){
    			prepStmt.setLong(2, allianceId.longValue());
    		}
    		rs=prepStmt.executeQuery();
    		while(rs.next()){
    			app.setTitle(rs.getString("title"));														//Title
    			appFiles.setScreenJpeg(rs.getBlob("screen_jpeg"));											//Screen Shot
    			appFiles.setScreenJpeg2(rs.getBlob("screen_jpeg_2"));
    			appFiles.setScreenJpeg3(rs.getBlob("screen_jpeg_3"));
    			appFiles.setScreenJpeg4(rs.getBlob("screen_jpeg_4"));
    			appFiles.setScreenJpeg5(rs.getBlob("screen_jpeg_5"));    			
    			javaFiles.setCompanyLogo(rs.getBlob("company_logo"));										//company logo
    			javaFiles.setTitleImage(rs.getBlob("APP_TITLE_NAME"));											//Title shot
    			javaApp.setProductDescription(rs.getString("product_description")); 						//product description   			
    			javaClobs.setUsingApplication(rs.getClob("using_application"));								//using application
    			javaClobs.setTipsAndTricks(rs.getClob("tips_and_tricks"));									//tips and tricks
    			javaClobs.setFaq(rs.getClob("faq"));														//faq
    			javaClobs.setTroubleshooting(rs.getClob("troubleshooting"));								//troubleshooting
    			javaClobs.setDevelopmentCompanyDisclaimer(rs.getClob("DEVELOPMENT_COMPANY_DISCLAIMER"));	//company disclaimer	
    			javaClobs.setAdditionalInformation(rs.getClob("additional_information"));					//additional information
    			
        		appMap.put("AimsApp", app);
        		appMap.put("AimsAppFiles", appFiles);
        		appMap.put("AimsJavaApp", javaApp);
        		appMap.put("AimsJavaFiles", javaFiles);
        		appMap.put("AimsJavaClobs", javaClobs);
    		}
    	}
    	catch(HibernateException e){
    		log.debug(e,e);
    		throw e;
    	}
    	finally{
    		if (session != null){
    			session.close();
    		}
    		if (rs != null){
    			rs.close();
    		}
    		if (prepStmt != null){
    			prepStmt.close();
    		}
    	}
    	if ( log.isDebugEnabled() )
    		log.debug("JavaApplicationManager.getUserGuideData End:");
    	return appMap;
    }
	
    
}