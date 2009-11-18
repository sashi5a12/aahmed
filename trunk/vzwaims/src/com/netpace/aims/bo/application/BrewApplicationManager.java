package com.netpace.aims.bo.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;

import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.workflow.WorkflowManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsAppFiles;
import com.netpace.aims.model.application.AimsBrewApp;
import com.netpace.aims.model.application.AimsBrewAppClob;
import com.netpace.aims.model.application.AimsBrewAppsHistory;
import com.netpace.aims.model.application.AimsBrewFiles;
import com.netpace.aims.model.application.AimsBrewMessagingDetails;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * This	class	is responsible for getting the BO	for	Brew Application.
 * It	has	static methods for getting the Brew Application.
 * @author Adnan Makda
 */
public class BrewApplicationManager
{

    static Logger log = Logger.getLogger(BrewApplicationManager.class.getName());

    /**
    *	 This	static method	updates	a	given	Brew Application.
    */
    public static void saveOrUpdateBrewApplication(
        AimsApp aimsApp,
        AimsBrewApp aimsBrewApp,
        AimsContact aimsContact,
        AimsBrewMessagingDetails messagingDetails,
        String userId,
        String userType,
        Long screenJpeg,
        Long screenJpeg2,
        Long screenJpeg3,
        Long screenJpeg4,
        Long screenJpeg5,
        Long flashDemo,
        Long userGuide,
        Long faqDoc,
        Long progGuide,
        Long appTitleName,
        Long companyLogo,
        Long titleShot,
        Long highResSplash,
        Long highResActive,
        Long splashScreen,
        Long smallSplash,
        Long activeScreen1,
        Long activeScreen2,
        Long smlActiveScreen,
        Long appActiionFlash,
        Long appGifAction,
        Long mediaStore,
        Long flashDemoMovie,
        Long dashboardScrImg,
        boolean checkForEmptyFiles,
        Set certificationSet,
        Long clonedFromAppId,
        List brewAppHistoryList,
        AimsBrewAppClob brewClobs)
        throws AimsException, HibernateException, Exception
    {
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

            //If Aims Contact Id provided zero (0), then create new Contact
            if ((aimsApp.getAimsContactId() != null) && (aimsApp.getAimsContactId().longValue() == 0))
            {
                //If All Non-Nullable Fields provided for Contact, then create new Contact
                if ((aimsContact.getFirstName().length() > 0)
                    && (aimsContact.getLastName().length() > 0)
                    && (aimsContact.getEmailAddress().length() > 0)
                    && (aimsContact.getTitle().length() > 0))
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
                aimsBrewApp.setBrewAppsId(aimsApp.getAppsId());
                session.save(aimsBrewApp);
                newApp = true;
            }
            else //Means to Update
                {
                session.saveOrUpdate(aimsApp);
                aimsBrewApp.setBrewAppsId(aimsApp.getAppsId());
                session.update(aimsBrewApp);
                newApp = false;
            }

            ApplicationsManagerHelper.saveCertifications(session, certificationSet, aimsApp, userType);

            //if network usage is enabled, then save network urls, if disabled then delete old urls
            if(StringFuncs.isNullOrEmpty(aimsApp.getNetworkUsage()) || aimsApp.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE))
            {
                ApplicationsManagerHelper.deleteAppNetworkUrls(session, aimsApp.getAppsId());
            }
            else if(aimsApp.getNetworkUsage().equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE))
            {
                ApplicationsManagerHelper.saveAppNetworkUrls(session, aimsApp.getAppNetworkURLs(), aimsApp.getAppsId());
            }


            if(brewAppHistoryList != null && brewAppHistoryList.size()>0)
            {
                for(int brewHistoryListIndex=0; brewHistoryListIndex<brewAppHistoryList.size(); brewHistoryListIndex++)
                {
                    AimsBrewAppsHistory brewAppHistory = (AimsBrewAppsHistory) brewAppHistoryList.get(brewHistoryListIndex);
                    brewAppHistory.setBrewAppsId(aimsBrewApp.getBrewAppsId());
                    session.save(brewAppHistory);
                    log.debug("brew history saved brewAppsId: "+brewAppHistory.getBrewAppsId()+"\tvalue: "+brewAppHistory.getColumnValue());
                }
            }
            
            if(messagingDetails != null){
            	if (StringUtils.isNotEmpty(messagingDetails.getShortCode()) && StringUtils.isNotEmpty(messagingDetails.getKeyword())){
            		messagingDetails.setBrewAppId(aimsBrewApp.getBrewAppsId());
            		session.saveOrUpdate(messagingDetails);
            	}
            }

            session.flush();                        
            ConOra = session.connection();
            
            //Save/Update user guide clobs.
            if (brewClobs != null){
    			long bytesWrote = 0;
    			StringBuffer strQuery=new StringBuffer();
    			strQuery.append("update aims_brew_apps ");
    			strQuery.append("set using_application=EMPTY_CLOB(), tips_and_tricks=EMPTY_CLOB(), faq=EMPTY_CLOB(), troubleshooting=EMPTY_CLOB(), ");
    			strQuery.append("development_company_disclaimer=EMPTY_CLOB(), additional_information=EMPTY_CLOB() WHERE brew_apps_id=?");
    			prepStmt = ConOra.prepareStatement(strQuery.toString());
    			prepStmt.setLong(1, aimsBrewApp.getBrewAppsId().longValue());
    			prepStmt.executeUpdate();    			
    			prepStmt.close();
    			
    			prepStmt=ConOra.prepareStatement("select using_application, tips_and_tricks, faq, troubleshooting, development_company_disclaimer, additional_information from aims_brew_apps where brew_apps_id = ? for update");
    			prepStmt.setLong(1, aimsBrewApp.getBrewAppsId().longValue());
    			rs = prepStmt.executeQuery();    			
    			rs.next();
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("using_application"), brewClobs.getUsingApplicationStr());
    			log.debug("Using Application bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("tips_and_tricks"), brewClobs.getTipsAndTricksStr());
    			log.debug("Tip and tricks bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("faq"), brewClobs.getFaqStr());
    			log.debug("Faq bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("troubleshooting"), brewClobs.getTroubleshootingStr());
    			log.debug("Troubleshooting bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("development_company_disclaimer"), brewClobs.getDevelopmentCompanyDisclaimerStr());
    			log.debug("Dev. Company Disclaimer bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("additional_information"), brewClobs.getAdditionalInformationStr());
    			log.debug("Additional Information bytesWrote="+bytesWrote);    			    			
            }                                   

            //Cloning Images
            if (clonedFromAppId != null)
            {
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.BREW_PLATFORM_ID);
                System.out.println(
                    "AM: Application being cloned from appsId:"
                        + clonedFromAppId
                        + ". New App Info is AppsId: "
                        + aimsApp.getAppsId()
                        + " and title: "
                        + aimsApp.getTitle());
            }

            //Copying Images From Temp Table (if present)
            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg2, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_2_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg3, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_3_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg4, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_4_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg5, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_5_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, flashDemo, aimsApp.getAppsId(), userId, ManageApplicationsConstants.FLASH_DEMO_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, userGuide, aimsApp.getAppsId(), userId, ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);            

            TempFilesManager.copyImageFromTemp(ConOra, progGuide, aimsApp.getAppsId(), userId, ManageApplicationsConstants.PROG_GUIDE_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, appTitleName, aimsApp.getAppsId(), userId, ManageApplicationsConstants.APP_TITLE_NAME_BLOB_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, companyLogo, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_COMPANY_LOGO_BLOB_DB_INFO);
            
            TempFilesManager.copyImageFromTemp(ConOra, titleShot, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_TITLE_SHOT_BLOB_DB_INFO);
          
            TempFilesManager.copyImageFromTemp(ConOra, highResSplash, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_HIGH_RES_SPLASH_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, highResActive, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_HIGH_RES_ACTIVE_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, splashScreen, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_SPLASH_SCREEN_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, smallSplash, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_SMALL_SPLASH_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, activeScreen1, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_ACTIVE_SCREEN_1_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, activeScreen2, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_ACTIVE_SCREEN_2_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, smlActiveScreen, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_SML_ACTIVE_SCREEN_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appActiionFlash, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_APP_ACTIION_FLASH_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appGifAction, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_APP_GIF_ACTION_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, mediaStore, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_MEDIA_STORE_DB_INFO);

            TempFilesManager.copyImageFromTemp(ConOra, flashDemoMovie, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_FLASH_DEMO_MOVIE_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, dashboardScrImg, aimsApp.getAppsId(), userId, ManageApplicationsConstants.BREW_DASHBOARD_SCR_IMG_DB_INFO);
            
            //Generate Client ID for LBS Brew Apps if secret key is already generated in Action Class
            if ((aimsBrewApp.getLbsClientId() == null) && (!StringFuncs.isNullOrEmpty(aimsBrewApp.getLbsSecretKey())))
            {
                //Update the 'LBS Client ID' via the sequence (SEQ_LBS_CLIENT_IDS)
                statement = null;
                try
                {
                    statement = ConOra.prepareCall("call AIMS_AUTODESK_PKG.add_lbs_client_id(?,?)");
                    statement.setLong(1, aimsBrewApp.getBrewAppsId().longValue());
                    statement.registerOutParameter(2, java.sql.Types.INTEGER);
                    statement.execute();
                    aimsBrewApp.setLbsClientId(new Long(statement.getInt(2)));
                }
                catch (Exception ex)
                {}
            }
            //End of generating Client ID for LBS Brew Apps

            ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);

            session.flush();

            if (checkForEmptyFiles)
            {
                System.out.println(
                    "AM: Calling checkForEmptyBlobs() for appsId: "
                        + aimsApp.getAppsId()
                        + " and title: "
                        + aimsApp.getTitle()
                        + " which is updated_by: "
                        + aimsApp.getLastUpdatedBy()
                        + " at time: "
                        + aimsApp.getLastUpdatedDate());
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

        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK IN saveOrUpdateBrewApplication()");
            e.printStackTrace();
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
                {}
            }
            if (rs != null){
            	rs.close();
            }
            if (prepStmt != null){
            	prepStmt.close();
            }            
            session.close();
            log.debug("SESSION CLOSED IN saveOrUpdateBrewApplication()");
        }
    }
    
    public static AimsBrewAppClob getBrewClob(Long brewAppsId) throws Exception{
    	log.debug("BrewApplicationManager.getBrewClob start: brewAppsId = "+brewAppsId);
        Session session = null;
        AimsBrewAppClob brewClobs = new AimsBrewAppClob(); 
        try{
        	String queryStr="from AimsBrewAppClob a where a.brewAppsId = ?";
        	session = DBHelper.getInstance().getSession();
        	
        	Collection collection=session.find(queryStr, brewAppsId, new LongType());
        	for (Iterator iter = collection.iterator(); iter.hasNext();){
        		brewClobs=(AimsBrewAppClob)iter.next();
			}
        	
        	if (brewClobs != null){
        		brewClobs.setUsingApplicationStr(null);
        		brewClobs.setTipsAndTricksStr(null);
        		brewClobs.setFaqStr(null);
        		brewClobs.setTroubleshootingStr(null);
        		brewClobs.setDevelopmentCompanyDisclaimerStr(null);
        		brewClobs.setAdditionalInformationStr(null);
        		
	        	if (brewClobs.getUsingApplication() != null){
	        		brewClobs.setUsingApplicationStr(brewClobs.getUsingApplication().getSubString(1, (int)brewClobs.getUsingApplication().length()));
	        		brewClobs.setUsingApplication(null);
	        	}
	        	if (brewClobs.getTipsAndTricks() != null){
	        		brewClobs.setTipsAndTricksStr(brewClobs.getTipsAndTricks().getSubString(1, (int)brewClobs.getTipsAndTricks().length()));
	        		brewClobs.setTipsAndTricks(null);
	        	}
	        	if (brewClobs.getFaq() != null){
	        		brewClobs.setFaqStr(brewClobs.getFaq().getSubString(1, (int)brewClobs.getFaq().length()));
	        		brewClobs.setFaq(null);
	        	}
	        	if (brewClobs.getTroubleshooting() != null){
	        		brewClobs.setTroubleshootingStr(brewClobs.getTroubleshooting().getSubString(1, (int)brewClobs.getTroubleshooting().length()));
	        		brewClobs.setTroubleshooting(null);
	        	}
	        	if (brewClobs.getDevelopmentCompanyDisclaimer() != null){
	        		brewClobs.setDevelopmentCompanyDisclaimerStr(brewClobs.getDevelopmentCompanyDisclaimer().getSubString(1, (int)brewClobs.getDevelopmentCompanyDisclaimer().length()));
	        		brewClobs.setDevelopmentCompanyDisclaimer(null);
	        	}
	        	if (brewClobs.getAdditionalInformation() != null){
	        		brewClobs.setAdditionalInformationStr(brewClobs.getAdditionalInformation().getSubString(1, (int)brewClobs.getAdditionalInformation().length()));
	        		brewClobs.setAdditionalInformation(null);
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
    	log.debug("BrewApplicationManager.getBrewClob end:");
    	return brewClobs;
    }
    public static HashMap getApp(Long appsId, Long allianceId) throws AimsException, HibernateException
    {
        Session session = null;
        HashMap appBrew = new HashMap();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append(" select ")
                .append("               apps, brewapps, category, messaging	")
                .append(" from ")
                .append("               com.netpace.aims.model.application.AimsApp	as apps, ")
                .append("               com.netpace.aims.model.application.AimsBrewApp	as brewapps, ")
                .append("               com.netpace.aims.model.application.AimsAppSubCategory	as subcategory, ")
                .append("               com.netpace.aims.model.application.AimsAppCategory	as category, ")
                .append("               com.netpace.aims.model.application.AimsBrewMessagingDetails	as messaging, ")
                .append("               com.netpace.aims.model.core.AimsAllianc as alliance ")
                .append(" where ")
                .append("               apps.appsId = brewapps.brewAppsId ")
                .append("               and	apps.aimsAppSubCategoryId = subcategory.subCategoryId ")
                .append("               and	subcategory.aimsAppCategoryId = category.categoryId ")
                .append("               and	brewapps.brewAppsId = messaging.brewAppId (+) ")
                .append("               and apps.aimsAllianceId = alliance.allianceId ")
                .append("               and	apps.appsId = :appsId ");

            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
            if (allianceId != null)
                queryStringBuffer.append("				and apps.aimsAllianceId = " + allianceId);

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                appValues = (Object[]) it.next();
                log.debug("AimsApp" + (AimsApp) appValues[0]);
                appBrew.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
                log.debug("AimsBrewApp" + (AimsBrewApp) appValues[1]);
                appBrew.put("AimsBrewApp", (AimsBrewApp) appValues[1]); // AimsBrewApp
                log.debug("AimsAppCategory" + (AimsAppCategory) appValues[2]);
                appBrew.put("AimsAppCategory", (AimsAppCategory) appValues[2]); // AimsAppCategory
                log.debug("AimsBrewMessaging" + (AimsBrewMessagingDetails) appValues[3]);
                appBrew.put("AimsBrewMessaging", (AimsBrewMessagingDetails) appValues[3]); // AimsBrewMessagingDetails
            }
            if (appBrew.isEmpty())
            {
                AimsException aimsException = new AimsException("Error");
                aimsException.addException(new RecordNotFoundException("error.security"));
                throw aimsException;
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {

            session.close();
            log.debug("SESSION CLOSED IN getApp()");

        }

        return appBrew;
    }

    public static Collection getGeoServices() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsLbsGeoService as geoService ");
            query.append(" order by initiated_from, geo_service_name ");
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

    public static void updateBrewApplication(AimsApp aimsApp, AimsBrewApp aimsBrewApp) throws AimsException, HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(aimsApp);
            aimsBrewApp.setBrewAppsId(aimsApp.getAppsId());
            session.update(aimsBrewApp);
            session.flush();
            tx.commit();

        }
        catch (JDBCException je)
        {
            if (tx != null)
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

        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK IN updateBrewApplication()");
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
            log.debug("SESSION CLOSED IN updateBrewApplication()");
        }
    }
    
    public static HashMap getUserGuideData(Long appsId, Long allianceId)throws HibernateException, SQLException{
    	log.debug("BrewApplicationManager.getUserGuideData Start: appsId= "+appsId);
    	HashMap appMap=new HashMap();
    	Session session=null;
    	PreparedStatement prepStmt=null;
    	ResultSet rs=null;
    	AimsApp app=new AimsApp();
    	AimsBrewApp brewApp=new AimsBrewApp();
    	AimsAppFiles appFiles=new AimsAppFiles();
    	AimsBrewFiles brewFiles=new AimsBrewFiles();
    	AimsBrewAppClob brewClobs=new AimsBrewAppClob();
    	try {
    		session=DBHelper.getInstance().getSession();
    		StringBuffer sb=new StringBuffer();
    		sb.append("SELECT app.title,");
    		sb.append("		app.screen_jpeg, app.screen_jpeg_2,");
    		sb.append("		app.screen_jpeg_3, app.screen_jpeg_4,");
    		sb.append("		app.screen_jpeg_5, ");
    		sb.append("		brew.company_logo, brew.title_shot,");
    		sb.append("		brew.product_description, brew.using_application,");
    		sb.append("		brew.tips_and_tricks, brew.faq, brew.troubleshooting, ");
    		sb.append("		brew.development_company_disclaimer, brew.additional_information");
    		sb.append("	FROM aims_apps app, aims_brew_apps brew");
    		sb.append("	WHERE app.apps_id = brew.brew_apps_id (+)");
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
    			brewFiles.setCompanyLogo(rs.getBlob("company_logo"));										//company logo
    			brewFiles.setTitleShot(rs.getBlob("title_shot"));											//Title shot
    			brewApp.setProductDescription(rs.getString("product_description")); 						//product description   			
    			brewClobs.setUsingApplication(rs.getClob("using_application"));								//using application
    			brewClobs.setTipsAndTricks(rs.getClob("tips_and_tricks"));									//tips and tricks
    			brewClobs.setFaq(rs.getClob("faq"));														//faq
    			brewClobs.setTroubleshooting(rs.getClob("troubleshooting"));								//troubleshooting
    			brewClobs.setDevelopmentCompanyDisclaimer(rs.getClob("development_company_disclaimer"));	//company disclaimer	
    			brewClobs.setAdditionalInformation(rs.getClob("additional_information"));					//additional information
    			
        		appMap.put("AimsApp", app);
        		appMap.put("AimsAppFiles", appFiles);
        		appMap.put("AimsBrewApp", brewApp);
        		appMap.put("AimsBrewFiles", brewFiles);
        		appMap.put("AimsBrewClobs", brewClobs);
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
    	log.debug("BrewApplicationManager.getUserGuideData End:");
    	return appMap;
    }
    
    public static List getAggregators() throws HibernateException{
    	List list = new ArrayList();
        Session session = null;
        try{
            session = DBHelper.getInstance().getSession();
            Query query=session.createQuery("from AimsTypes t where t.typeDefId=8 order by lower(t.typeValue)");
            list=query.list();            
        }
        catch (HibernateException e){
            log.error(e,e);
            throw e;
        }
        finally{
            session.close();
        }

        return list;
    }
    
    public static boolean isShortCodeExists(String shortCode, Long appId) throws HibernateException{
    	log.debug("BrewApplicationManager.isShortCodeExists Start: ");
    	log.debug("ShortCode= "+shortCode);
    	boolean flag=false;
    	Session session=null;    	
    	try {
			session=DBHelper.getInstance().getSession();			
			StringBuffer sb=new StringBuffer();
			sb.append("from AimsBrewMessagingDetails md where md.shortCode=:sc ");
			if (appId != null && appId.longValue() > 0){
				sb.append(" and (md.brewAppId != :appId or md.brewAppId is null)");
			}
			Query query=session.createQuery(sb.toString());
			query.setString("sc", shortCode.trim());
			
			if (appId != null && appId.longValue() > 0){
				query.setLong("appId", appId.longValue());
			}
			List list=query.list();
			if (list != null && list.size()>0){
				flag=true;
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
    	log.debug("BrewApplicationManager.isShortCodeExists End: ");
    	return flag;
    }

    public static boolean isKeywordExists(String keyword, Long appId) throws HibernateException{
    	log.debug("BrewApplicationManager.isKeywordExists Start: ");
    	log.debug("keyword= "+keyword);
    	boolean flag=false;
    	Session session=null;
    	try {
    		session=DBHelper.getInstance().getSession();
			StringBuffer sb=new StringBuffer();
			sb.append("from AimsBrewMessagingDetails md where md.keyword=:k ");
			if (appId != null && appId.longValue() > 0){
				sb.append(" and (md.brewAppId != :appId or md.brewAppId is null)");				
			}

    		Query query=session.createQuery(sb.toString());
    		query.setString("k", keyword.trim());
			if (appId != null && appId.longValue() > 0){
				query.setLong("appId", appId.longValue());
			}
    		List list=query.list();
    		if (list != null && list.size()>0){
    			flag=true;
    		}
    	} catch (HibernateException e) {
    		log.error(e,e);
    		throw e;
    	} finally {
    		if (session != null){
    			session.close();
    		}
    	}
    	log.debug("BrewApplicationManager.isKeywordExists End: ");
    	return flag;
    }
}
