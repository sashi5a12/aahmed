package com.netpace.aims.bo.application;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.type.LongType;

import oracle.sql.CLOB;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.dataaccess.valueobjects.DashboardChannelVO;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.model.application.AimsDashboardAppClob;
import com.netpace.aims.model.application.AimsDashboardApps;
import com.netpace.aims.model.application.AimsDashboardAppsDevices;
import com.netpace.aims.model.application.AimsDashboardChannelIds;
import com.netpace.aims.model.application.AimsDashboardEmailToAdobe;
import com.netpace.aims.model.application.AimsDashboardFtpsLog;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.LobUtils;

public class DashboardApplicationManager {
	private static final Logger log = Logger.getLogger(DashboardApplicationManager.class.getName());

	public static void saveOrUpdateDashboardApplication(
			AimsApp aimsApp,
			AimsDashboardApps dashboardApp, 
			AimsDashboardAppClob dashboardClobs,
			AimsDashboardChannelIds dashboardChannel,
			String[] devices,
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
			Long userGuide,
			Long contentZipFile,
			Long companyLogoFile,
			Long titleImageFile,
			boolean checkForEmptyFiles, 
			Long clonedFromAppId) throws AimsException, HibernateException, Exception {
		log.debug("DashboardApplicationManager.saveOrUpdateDashboardApplication: Start");
		
		Session session = null;
		Transaction tx = null;
		boolean newApp = false;
		java.sql.Connection ConOra = null;
		java.sql.CallableStatement statement = null;
		java.sql.PreparedStatement prepStmt =  null;
        java.sql.ResultSet rs = null;
        
		try {
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
				session.saveOrUpdate(aimsApp);
				dashboardApp.setDashboardAppsId(aimsApp.getAppsId());
				session.save(dashboardApp);
				newApp = true;
			} 
			else {
				session.saveOrUpdate(aimsApp);
				dashboardApp.setDashboardAppsId(aimsApp.getAppsId());
				session.update(dashboardApp);
				newApp = false;
			}

			session.delete("from AimsDashboardChannelIds id where id.dashboardAppsId="+dashboardApp.getDashboardAppsId());
			
			if (dashboardChannel.getChannelId() != null && dashboardChannel.getChannelId().longValue()>0){
				dashboardChannel.setDashboardAppsId(dashboardApp.getDashboardAppsId());
				session.save(dashboardChannel);
			}
			
			if (devices != null && devices.length > 0){
				session.delete("from AimsDashboardAppsDevices d where d.dashboardAppsId=:id", dashboardApp.getDashboardAppsId(), new LongType());
				for(int i=0; i<devices.length; i++){
					AimsDashboardAppsDevices dashboardDevice=new AimsDashboardAppsDevices();
					AimsDevic device=new AimsDevic();
					device.setDeviceId(new Long(devices[i]));
					dashboardDevice.setAimsDevices(device);
					dashboardDevice.setDashboardAppsId(dashboardApp.getDashboardAppsId());
					session.save(dashboardDevice);
				}
			}

			session.flush();

			ConOra = session.connection();

			//Save/Update user guide clobs.
            if (dashboardClobs != null){
    			long bytesWrote = 0;
    			StringBuffer strQuery=new StringBuffer();
    			strQuery.append("update aims_dashboard_apps ");
    			strQuery.append("set using_application=EMPTY_CLOB(), tips_and_tricks=EMPTY_CLOB(), faq=EMPTY_CLOB(), troubleshooting=EMPTY_CLOB(), ");
    			strQuery.append("development_company_disclaimer=EMPTY_CLOB(), additional_information=EMPTY_CLOB() WHERE dashboard_apps_id=?");
    			prepStmt = ConOra.prepareStatement(strQuery.toString());
    			prepStmt.setLong(1, dashboardApp.getDashboardAppsId().longValue());
    			prepStmt.executeUpdate();    			
    			prepStmt.close();
    			
    			prepStmt=ConOra.prepareStatement("select using_application, tips_and_tricks, faq, troubleshooting, development_company_disclaimer, additional_information from aims_dashboard_apps where dashboard_apps_id = ? for update");
    			prepStmt.setLong(1, dashboardApp.getDashboardAppsId().longValue());
    			rs = prepStmt.executeQuery();    			
    			rs.next();
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("using_application"), dashboardClobs.getUsingApplicationStr());
    			log.debug("Using Application bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("tips_and_tricks"), dashboardClobs.getTipsAndTricksStr());
    			log.debug("Tip and tricks bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("faq"), dashboardClobs.getFaqStr());
    			log.debug("Faq bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("troubleshooting"), dashboardClobs.getTroubleshootingStr());
    			log.debug("Troubleshooting bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("development_company_disclaimer"), dashboardClobs.getDevelopmentCompanyDisclaimerStr());
    			log.debug("Dev. Company Disclaimer bytesWrote="+bytesWrote);
    			
    			bytesWrote = LobUtils.writeToOraClob((CLOB)rs.getClob("additional_information"), dashboardClobs.getAdditionalInformationStr());
    			log.debug("Additional Information bytesWrote="+bytesWrote);    			    			
            }               
            
			// Cloning Images
			if (clonedFromAppId != null) {
				TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.DASHBOARD_PLATFORM_ID);
				System.out.println("AM: Application being cloned from appsId:"
						+ clonedFromAppId + ". New App Info is AppsId: "
						+ aimsApp.getAppsId() + " and title: "
						+ aimsApp.getTitle());
			}

			// Copying Images From Temp Table (if present)
			TempFilesManager.copyImageFromTemp(ConOra, clrPubLogo, aimsApp.getAppsId(), userId, ManageApplicationsConstants.DASHBOARD_CLR_PUB_LOGO_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, appTitleName, aimsApp.getAppsId(), userId, ManageApplicationsConstants.DASHBOARD_APP_TITLE_NAME_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, splashScreenEps, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SPLASH_SCREEN_EPS_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, activeScreenEps, aimsApp.getAppsId(), userId, ManageApplicationsConstants.ACTIVE_SCREEN_EPS_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg2, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_2_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg3, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_3_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg4, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_4_BLOB_DB_INFO);

			TempFilesManager.copyImageFromTemp(ConOra, screenJpeg5, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_5_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, userGuide, aimsApp.getAppsId(), userId, ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);			
			
			TempFilesManager.copyImageFromTemp(ConOra, contentZipFile, aimsApp.getAppsId(), userId, ManageApplicationsConstants.DASHBOARD_CONTENT_ZIP_FILE_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, companyLogoFile, aimsApp.getAppsId(), userId, ManageApplicationsConstants.DASHBOARD_COMPANY_LOGO_BLOB_DB_INFO);
			
			TempFilesManager.copyImageFromTemp(ConOra, titleImageFile, aimsApp.getAppsId(), userId, ManageApplicationsConstants.DASHBOARD_TITLE_IMAGE_BLOB_DB_INFO);

			ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);

			session.flush();

			if (checkForEmptyFiles) {
				System.out
						.println("AM: Calling checkForEmptyBlobs() for appsId: "
								+ aimsApp.getAppsId()
								+ " and title: "
								+ aimsApp.getTitle()
								+ " which is updated_by: "
								+ aimsApp.getLastUpdatedBy()
								+ " at time: "
								+ aimsApp.getLastUpdatedDate());
				TempFilesManager
						.checkForEmptyBlobs(ConOra, aimsApp.getAppsId());
			}

			session.flush();
			tx.commit();
		}

		catch (AimsException ax) {
			if (tx != null)
				tx.rollback();
			throw ax;
		}

		catch (JDBCException je) {
			if (tx != null)
				tx.rollback();

			AimsException aimsException = new AimsException("Error");
			//if (DBErrorFinder.searchUniqueConstraintErrors(je.getMessage(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException)) {
            //changed je.getMessage() to je.getCause().toString(), making it compatible with oracle.jar
            if (DBErrorFinder.searchUniqueConstraintErrors(je.getCause().toString(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException)) {
                throw aimsException;
			} else {
				log.error(je,je);
				throw new HibernateException(je);
			}
		}

		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.debug("ROLLED BACK IN saveOrUpdateDashboardApplication()");
			log.error(e,e);
			throw e;
		} finally {

			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ignore) {
				}
			}
			if (rs != null){
            	rs.close();
            }
            if (prepStmt != null){
            	prepStmt.close();
            }  
			session.close();
			log.debug("SESSION CLOSED IN saveOrUpdateDashboardApplication()");
		}
		log.debug("DashboardApplicationManager.saveOrUpdateDashboardApplication: End");
	}
	
	public static String[] getAppDevices(Long appsId) throws AimsException, HibernateException {
		Session session = null;
		String[] deviceIds = null;

		try {
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer
					.append(" select d.aimsDevices.deviceId ")
					.append(" 	from AimsDashboardAppsDevices as d ")
					.append(" where d.dashboardAppsId = :id ");

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setLong("id", appsId.longValue());			
			
			List resultValues = query.list();
			
			if (resultValues != null && resultValues.size()>0){
				deviceIds=new String[resultValues.size()];
				for (int i=0; i<resultValues.size(); i++){
					deviceIds[i]=resultValues.get(i).toString();
				}
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
			log.debug("SESSION CLOSED IN getApp()");
		}

		return deviceIds;
	}

	public static String[] getAppDevicesName(Long appsId) throws AimsException, HibernateException {
		Session session = null;
		String[] devicesName = null;

		try {
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer
					.append(" select d.aimsDevices.deviceModel ")
					.append(" 	from AimsDashboardAppsDevices as d ")
					.append(" where d.dashboardAppsId = :id ");

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setLong("id", appsId.longValue());			
			
			List resultValues = query.list();
			
			if (resultValues != null && resultValues.size()>0){
				devicesName=new String[resultValues.size()];
				for (int i=0; i<resultValues.size(); i++){
					devicesName[i]=resultValues.get(i).toString();
				}
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
			log.debug("SESSION CLOSED IN getApp()");
		}

		return devicesName;
	}	
    public static HashMap getApp(Long appsId, Long allianceId) throws AimsException, HibernateException {
		Session session = null;
		HashMap appBrew = new HashMap();

		try {
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer
					.append(" select ")
					.append("               apps, dashboardApp, category, channel	")
					.append(" from ")
					.append("               AimsApp	as apps, ")
					.append("               AimsDashboardApps	as dashboardApp, ")
					.append("               AimsAppSubCategory	as subcategory, ")
					.append("               AimsAppCategory	as category, ")
					.append("               AimsAllianc as alliance, ")
					.append("               AimsDashboardChannelIds	as channel ")					
					.append(" where ")
					.append("               apps.appsId = dashboardApp.dashboardAppsId ")
					.append("               and	apps.aimsAppSubCategoryId = subcategory.subCategoryId ")
					.append("               and	subcategory.aimsAppCategoryId = category.categoryId ")
					.append("               and	dashboardApp.dashboardAppsId = channel.dashboardAppsId (+) ")
					.append("               and apps.aimsAllianceId = alliance.allianceId ")
					.append("               and	apps.appsId = :appsId ");

			//Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
			if (allianceId != null)
				queryStringBuffer.append("				and apps.aimsAllianceId = "+ allianceId);

			Query query = session.createQuery(queryStringBuffer.toString());
			query.setLong("appsId", appsId.longValue());

			Object[] appValues = null;
			for (Iterator it = query.iterate(); it.hasNext();) {
				appValues = (Object[]) it.next();
				appBrew.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
				appBrew.put("AimsDashboardApp", (AimsDashboardApps) appValues[1]); // AimsDashboardApp
				appBrew.put("AimsAppCategory", (AimsAppCategory) appValues[2]); // AimsAppCategory
				appBrew.put("AimsDashboardChannel", (AimsDashboardChannelIds) appValues[3]); // AimsDashboardChannelIds
			}
			if (appBrew.isEmpty()) {
				AimsException aimsException = new AimsException("Error");
				aimsException.addException(new RecordNotFoundException("error.security"));
				throw aimsException;
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
			log.debug("SESSION CLOSED IN getApp()");
		}

		return appBrew;
	}
    
    public static AimsDashboardAppClob getClobs(Long dashboardId) throws Exception{
    	log.debug("DashboardApplicationManager.getClobs start: dashboardId = "+dashboardId);
        Session session = null;
        AimsDashboardAppClob clobs = new AimsDashboardAppClob(); 
        try{
        	String queryStr="from AimsDashboardAppClob a where a.dashboardAppsId = ?";
        	session = DBHelper.getInstance().getSession();
        	
        	Collection collection=session.find(queryStr, dashboardId, new LongType());
        	for (Iterator iter = collection.iterator(); iter.hasNext();){
        		clobs=(AimsDashboardAppClob)iter.next();
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
    	log.debug("DashboardApplicationManager.getClobs end:");
    	return clobs;
    }
    
    public static void updateDashboardApplication(AimsApp aimsApp, AimsDashboardApps dashboardApp, String userId, String userType)throws AimsException, HibernateException, Exception {
		Session session = null;
		Transaction tx = null;
	 	Connection ConOra = null;
		try {
			session = DBHelper.getInstance().getSession();
			ConOra = session.connection();
			tx = session.beginTransaction();
			session.update(aimsApp);
			dashboardApp.setDashboardAppsId(aimsApp.getAppsId());
			session.update(dashboardApp);
			ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, false);
			session.flush();			
			tx.commit();
		} 
		catch (JDBCException je) {
			if (tx != null)
				tx.rollback();
			AimsException aimsException = new AimsException("Error");
			if (DBErrorFinder.searchUniqueConstraintErrors(je.getMessage(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException)) {
				throw aimsException;
			} else {
				je.printStackTrace();
				throw new HibernateException(je);
			}
		}
		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.debug("ROLLED BACK IN updateDashboardApplication()");
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			log.debug("SESSION CLOSED IN updateDashboardApplication()");
		}
	}
    
    public static boolean sendEmailToAdobe(Long allianceId) throws AimsException, HibernateException {
		Session session = null;
		boolean sendMail=false;
		try {
			session = DBHelper.getInstance().getSession();
			StringBuffer queryStringBuffer = new StringBuffer();

			queryStringBuffer.append("from AimsDashboardEmailToAdobe a where a.allianceId=:id");
			Query query = session.createQuery(queryStringBuffer.toString());
			query.setLong("id", allianceId.longValue());

			List list=query.list();
			if (list.size() == 0 ){
				sendMail=true;
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			session.close();
		}

		return sendMail;
	}

    public static void saveAdobeMailLog(AimsDashboardEmailToAdobe adobeMailLog) throws AimsException, HibernateException {
		Session session = null;
		Transaction trx=null;
		try {
			session = DBHelper.getInstance().getSession();
			trx=session.beginTransaction();			
			session.save(adobeMailLog);
			trx.commit();
		} catch (HibernateException e) {
			log.error(e,e);
			trx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
    public static byte[] getContentZipFile(Long appId) throws HibernateException{
    	log.debug("DashboardApplication.getContentZipFile: Start appId= "+appId);
    	Session session=null;
    	byte[] contentZipFile=null;
    	try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery("select f.contentZipFile from AimsDashboardFiles f where f.dashboardAppsId = :id");
			query.setLong("id", appId.longValue());
			Blob blob=(Blob)query.list().get(0);
			contentZipFile=blob.getBytes(1, (int)blob.length());
		} catch (HibernateException e) {
			log.error(e,e);
		} catch (SQLException e) {
			log.error(e,e);
		} finally {
			session.close();
		}
		log.debug("DashboardApplication.getContentZipFile: End");
		return contentZipFile;
    }
    
    public static void saveFtpsLog(AimsDashboardFtpsLog ftpsLog) throws AimsException, HibernateException {
		Session session = null;
		Transaction trx=null;
		try {
			session = DBHelper.getInstance().getSession();
			trx=session.beginTransaction();			
			session.save(ftpsLog);
			trx.commit();
		} catch (HibernateException e) {
			log.error(e,e);
			trx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

    public static boolean isChannelIdExist(Integer channelId, Long appId) throws HibernateException{
    	boolean isExists=true;
    	Session session=null;
    	try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery("from AimsDashboardChannelIds id where id.channelId=:id and (id.dashboardAppsId != :appId or id.dashboardAppsId is null)");
			query.setInteger("id", channelId.intValue());
			query.setLong("appId", appId.longValue());
			List list=query.list();
			if (list == null || list.size()==0){
				return false;
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;			
		} finally {
			if (session != null){
				session.close();
			}
		}
    	return isExists;
    }
    
    public static List getChannels() throws HibernateException{
    	List list=null;
    	List channels=new ArrayList();
    	Session session=null;
    	try {
    		StringBuffer queryStr=new StringBuffer();
    		queryStr.append("SELECT channel.channelId, channel.appTitle, channel.companyName, app.title, al.companyName");
    		queryStr.append("  FROM AimsDashboardChannelIds channel,");
    		queryStr.append("       AimsDashboardApps dashboard,");
    		queryStr.append("       AimsApp app,");
    		queryStr.append("       AimsAllianc al");
    		queryStr.append(" WHERE channel.dashboardAppsId = dashboard.dashboardAppsId (+)");
    		queryStr.append("   AND dashboard.dashboardAppsId = app.appsId (+)");
    		queryStr.append("   AND app.aimsAllianceId = al.allianceId (+) order by channel.channelId");
    		
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(queryStr.toString());
			list=query.list();
			if (list != null && list.size()>0){
				for(int i=0; i<list.size(); i++){
					DashboardChannelVO vo=new DashboardChannelVO();
					Object[] results=(Object[])list.get(i);
					if (results[3]==null && results[4]==null){
						vo.setChannelId(results[0].toString()+" *");
						vo.setAppTile(results[1]==null?"":results[1].toString());
						vo.setCompanyName(results[2]==null?"":results[2].toString());
					}
					else {
						vo.setChannelId(results[0].toString());
						vo.setAppTile(results[3].toString());
						vo.setCompanyName(results[4].toString());
					}
					channels.add(vo);
				}
			}
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
    	return channels;
    }
    public static void main(String args[])throws HibernateException{		
		DBHelper dbHelper=null;
		try {
			Configuration conf =new Configuration();			
			conf.setProperty("hibernate.connection.url", "jdbc:oracle:thin:@xeon:1521:ora9i");
			conf.setProperty("hibernate.connection.username", "aims");
			conf.setProperty("hibernate.connection.password", "aims");
			dbHelper=DBHelper.getInstance();
			dbHelper.sessionFactory=conf.configure().buildSessionFactory();
			List list=DashboardApplicationManager.getChannels();
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbHelper != null){
				dbHelper.sessionFactory.close();
			}
		}
	}
}
