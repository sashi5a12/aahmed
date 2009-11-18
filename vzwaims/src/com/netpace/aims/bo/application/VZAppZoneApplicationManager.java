package com.netpace.aims.bo.application;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.application.VZAppZoneApplicationHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.system.AimsDeviceFirmware;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsVZAppBinaries;
import com.netpace.aims.model.application.AimsVZAppBinaryFirmware;
import com.netpace.aims.model.application.AimsVZAppZoneApp;
import com.netpace.aims.model.application.AimsVzappFirmwarePaidLog;
import com.netpace.aims.model.application.AimsVzappIntertekWsLog;
import com.netpace.aims.model.application.AimsVZAppMPortalXMLLog;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.Utility;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwareInfoVO;
import com.netpace.aims.dataaccess.valueobjects.VZAppBinaryFirmwarePhaseInfoVO;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import net.sf.hibernate.type.Type;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class VZAppZoneApplicationManager {
    private static Logger log = Logger.getLogger(VZAppZoneApplicationManager.class.getName());

    public static void saveOrUpdateVZAppZoneApplication(AimsApp aimsApp,
                                                        AimsVZAppZoneApp vzAppZoneApp,
                                                        String userId,
                                                        String userType,
                                                        Long oldStatus,
                                                        Long clonedFromAppId,
                                                        Long presentation,
                                                        Long contentLandingPage,
                                                        AimsVZAppBinaries vzAppBinary,
                                                        Long binaryId,
                                                        Long binaryFileTempFileId,
                                                        Long previewFileTempFileId,
                                                        Long documentFileTempFileId,
                                                        List newAimsVZAppBinaryFirmwareList,
                                                        List activeAimsVZAppBinaryFirmwareList,
                                                        Map baseTestResultsTempFileMap,
                                                        Map otaTestResultsTempFileMap,
                                                        List journalEntriesToSave)
            throws AimsException, HibernateException, Exception {
        Session session = null;
        Transaction tx = null;
        boolean newApp = false;

        boolean newDevice = false;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            /*
                *SAVING APP IN DATABASE
            */

            if (aimsApp.getAppsId() == null) //Saving
            {
                session.saveOrUpdate(aimsApp);
                vzAppZoneApp.setVzAppZoneAppsId(aimsApp.getAppsId());
                session.save(vzAppZoneApp);
                newApp = true;

            }
            else //Updating
                {
                session.saveOrUpdate(aimsApp);
                //vzAppZoneApp.setVzAppZoneAppsId(aimsApp.getAppsId());
                session.update(vzAppZoneApp);
                newApp = false;
            }

            session.flush();

            Long binaryIdToSave = null;
            //save binary file
            if(vzAppBinary!=null && Utility.ZeroValueReplacement(binaryFileTempFileId).longValue()>0) {
                //binary file uploaded
                vzAppBinary.setVzAppZoneAppsId(vzAppZoneApp.getVzAppZoneAppsId());
                session.save(vzAppBinary);
                binaryIdToSave = vzAppBinary.getBinaryId();
            }
            else if(Utility.ZeroValueReplacement(binaryId).longValue()>0) {
                //binary id selected
                binaryIdToSave = binaryId;
            }
            if(newAimsVZAppBinaryFirmwareList !=null && newAimsVZAppBinaryFirmwareList.size()>0
                    && (Utility.ZeroValueReplacement(binaryIdToSave).longValue()>0)) {
                //if binary file uploaded or selected then save binary firmwares
                AimsVZAppBinaryFirmware binaryFirmware = null;
                for(int binaryFirmwareIndex=0; binaryFirmwareIndex<newAimsVZAppBinaryFirmwareList.size(); binaryFirmwareIndex++) {
                    binaryFirmware = (AimsVZAppBinaryFirmware)newAimsVZAppBinaryFirmwareList.get(binaryFirmwareIndex);
                    binaryFirmware.setBinaryId(binaryIdToSave);
                    binaryFirmware.setVzAppZoneAppsId(vzAppZoneApp.getVzAppZoneAppsId());
                    session.save(binaryFirmware);
                }
            }

            if(activeAimsVZAppBinaryFirmwareList!=null && activeAimsVZAppBinaryFirmwareList.size()>0) {
                AimsVZAppBinaryFirmware activeBinaryFirmware = null;
                for(int activeBinaryFirmwareIndex=0; activeBinaryFirmwareIndex<activeAimsVZAppBinaryFirmwareList.size(); activeBinaryFirmwareIndex++) {
                    activeBinaryFirmware = (AimsVZAppBinaryFirmware)activeAimsVZAppBinaryFirmwareList.get(activeBinaryFirmwareIndex);
                    session.update(activeBinaryFirmware);
                }
            }
            //todo construct journal entry for binary firmware (after submitted state), use journalEntriesForBinary
            session.flush();
            //end binary

            /*
                UPDATING   DATABASE FOR IMAGES
            */
            Connection ConOra = null;
            ConOra = session.connection();

            TempFilesManager.copyImageFromTemp(ConOra, presentation, aimsApp.getAppsId(), userId, ManageApplicationsConstants.VZAPPZONE_PRESENTATION_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, contentLandingPage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.VZAPPZONE_CONTENT_LANDING_PAGE_BLOB_DB_INFO);

            //save binary file
            if(vzAppBinary!=null && Utility.ZeroValueReplacement(binaryFileTempFileId).longValue()>0) {
                TempFilesManager.copyImageFromTemp(ConOra, binaryFileTempFileId, vzAppBinary.getBinaryId(), userId, ManageApplicationsConstants.VZAPPZONE_BINARY_FILE_BLOB_DB_INFO);
                TempFilesManager.copyImageFromTemp(ConOra, previewFileTempFileId, vzAppBinary.getBinaryId(), userId, ManageApplicationsConstants.VZAPPZONE_PREVIEW_FILE_BLOB_DB_INFO);
                TempFilesManager.copyImageFromTemp(ConOra, documentFileTempFileId, vzAppBinary.getBinaryId(), userId, ManageApplicationsConstants.VZAPPZONE_DOCUMENT_FILE_BLOB_DB_INFO);
            }

            //Cloning Images
            if (Utility.ZeroValueReplacement(clonedFromAppId).longValue()>0) {
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.VZAPPZONE_PLATFORM_ID);
            }
            session.flush();

            //save test results file
            if(baseTestResultsTempFileMap !=null && activeAimsVZAppBinaryFirmwareList != null && activeAimsVZAppBinaryFirmwareList.size()>0) {
                AimsVZAppBinaryFirmware binaryFirmwareForResultsFile = null;
                Long tempFileId = null;
                for(int devicePhaseIndex=0; devicePhaseIndex<activeAimsVZAppBinaryFirmwareList.size(); devicePhaseIndex++) {
                    binaryFirmwareForResultsFile = (AimsVZAppBinaryFirmware ) activeAimsVZAppBinaryFirmwareList.get(devicePhaseIndex);
                    if(binaryFirmwareForResultsFile !=null && Utility.ZeroValueReplacement(binaryFirmwareForResultsFile.getBinaryFirmwareId()).longValue()>0) {
                        //temp file ids were saved with respect to binaryfirmwareId in map,
                        // get temp file id from map,
                        tempFileId = (Long) baseTestResultsTempFileMap.get(binaryFirmwareForResultsFile.getBinaryFirmwareId().toString());
                        if(tempFileId!=null && tempFileId.longValue()>0) {
                            TempFilesManager.copyImageFromTemp(ConOra, tempFileId, binaryFirmwareForResultsFile.getBinaryFirmwareId(), userId, ManageApplicationsConstants.VZAPPZONE_BASE_TEST_RESULTS_FILE_BLOB_DB_INFO);
                            log.debug("Results File Saved for "+binaryFirmwareForResultsFile.getBinaryFirmwareId());
                        }
                    }
                }

                session.flush();
            }

            if(otaTestResultsTempFileMap !=null && activeAimsVZAppBinaryFirmwareList != null && activeAimsVZAppBinaryFirmwareList.size()>0) {
                AimsVZAppBinaryFirmware binaryFirmwareForOTAResultsFile = null;
                Long tempFileId = null;
                for(int otaIndex =0; otaIndex <activeAimsVZAppBinaryFirmwareList.size(); otaIndex++) {
                    binaryFirmwareForOTAResultsFile = (AimsVZAppBinaryFirmware ) activeAimsVZAppBinaryFirmwareList.get(otaIndex);
                    if(binaryFirmwareForOTAResultsFile !=null && Utility.ZeroValueReplacement(binaryFirmwareForOTAResultsFile.getBinaryFirmwareId()).longValue()>0) {
                        //temp file ids were saved with respect to binaryfirmwareId in map,
                        // get temp file id from map,
                        tempFileId = (Long) otaTestResultsTempFileMap.get(binaryFirmwareForOTAResultsFile.getBinaryFirmwareId().toString());
                        if(tempFileId!=null && tempFileId.longValue()>0) {
                            TempFilesManager.copyImageFromTemp(ConOra, tempFileId, binaryFirmwareForOTAResultsFile.getBinaryFirmwareId(), userId, ManageApplicationsConstants.VZAPPZONE_OTA_TEST_RESULTS_FILE_BLOB_DB_INFO);
                            log.debug("OTA Results File Saved for "+binaryFirmwareForOTAResultsFile.getBinaryFirmwareId());
                        }
                    }
                }

                session.flush();
            }

            //save test history Files
            if(VZAppZoneApplicationHelper.isStatusEqualOrAboveInitialApproval(oldStatus)) {
                //todo old work: save history files from history list if any
                //todo if submitted--initial approval, set under testing date of binaryfirmwares
            }
            //end save test history files

            //save journal entries
            if(journalEntriesToSave!=null && !journalEntriesToSave.isEmpty()) {
                for (int journalEntriesIndex =0; journalEntriesIndex <journalEntriesToSave.size(); journalEntriesIndex++) {
                    AimsApplicationsManager.addSpecificJournalEntry(ConOra, aimsApp.getAppsId(), (String) journalEntriesToSave.get(journalEntriesIndex), AimsConstants.JOURNAL_TYPE_PUBLIC, null);
                }
            }

            //save journal entries for Status change
            ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);

            tx.commit();
        }
        catch (JDBCException je) {
            if (tx != null)
                tx.rollback();

            AimsException aimsException = new AimsException("Error");
            //if (DBErrorFinder.searchUniqueConstraintErrors(je.getMessage(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS, aimsException)) {
            if (DBErrorFinder.searchUniqueConstraintErrorsWithSchemaName(je.getCause().toString(), ManageApplicationsConstants.UNIQUE_CONSTRAINT_KEYS_DB, aimsException)) {
                throw aimsException;
            }
            else {
                je.printStackTrace();
                throw new HibernateException(je);
            }
        }
        catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK  IN saveOrUpdateVZAppZoneApplication()");
            e.printStackTrace();
            throw e;
        }

        catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK  IN saveOrUpdateVZAppZoneApplication()");
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("SESSION  CLOSED IN   saveOrUpdateVZAppZoneApplication()");
            }
        }//end finally
    }//end saveOrUpdateVZAppZoneApplication


    /**
     *
     * @param appsId
     * @param allianceId
     * @return
     * @throws AimsException
     * @throws HibernateException
     */
    public static HashMap getVZAppZoneApp(Long appsId, Long allianceId)
            throws AimsException, HibernateException {
        Session session = null;
        HashMap appVZApp = new HashMap();

        try {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       apps, vzAppZoneApps ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp as apps, ")
                .append("       com.netpace.aims.model.application.AimsVZAppZoneApp as vzAppZoneApps ")
                .append("   where ")
                .append("       apps.appsId = vzAppZoneApps.vzAppZoneAppsId ")
                .append("       and apps.appsId = :appsId ");

            //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
            if (allianceId != null)
                queryStringBuffer.append("      and apps.aimsAllianceId = :allianceId");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Object[] appValues = null;

            for (Iterator it = query.iterate(); it.hasNext();) {
                appValues = (Object[]) it.next();
                appVZApp.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
                appVZApp.put("AimsVZAppZoneApp", (AimsVZAppZoneApp) appValues[1]); // AimsWapApp
            }

            if (appVZApp.isEmpty()) {
                AimsException aimsException = new AimsException("Error");
                aimsException.addException(new RecordNotFoundException("error.security"));
                throw aimsException;
            }
        }
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppZoneApp()");
            }
        }

        return appVZApp;
    }//end getVZAppZoneApp


    /**
     *
     * @param appsId
     * @param allianceId
     * @param binaryId
     * @param columnName
     * @return
     * @throws HibernateException
     */
    public static AimsTempFile getVZAppBinaryFileFromBinaries(Long appsId, Long allianceId, Long binaryId, String columnName) throws HibernateException {
        Session session = null;
        AimsTempFile aimsTempFile = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("vzAppBinary.").append(columnName).append(", ")
                .append("vzAppBinary.").append(columnName).append("FileName, ")
                .append("vzAppBinary.").append(columnName).append("ContentType ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp as apps, ")
                .append("       com.netpace.aims.model.application.AimsVZAppBinariesFiles as vzAppBinary, ")
                .append("       com.netpace.aims.model.core.AimsAllianc as alliance ")
                .append("   where ")
                .append("       apps.appsId = vzAppBinary.vzAppZoneAppsId")
                .append("       and apps.aimsAllianceId = alliance.allianceId ")
                .append("       and apps.appsId = :appsId ")
                .append("       and vzAppBinary.binaryId = :binaryId ");

            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
            if (allianceId != null)
                queryStringBuffer.append("      and	apps.aimsAllianceId = :allianceId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());
            query.setLong("binaryId", binaryId.longValue());

            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsTempFile = new AimsTempFile();
                appValues = (Object[]) it.next();

                aimsTempFile.setTempFile((java.sql.Blob) appValues[0]); //File
                aimsTempFile.setTempFileName((String) appValues[1]); //File Name
                aimsTempFile.setTempFileContentType((String) appValues[2]); //File Content Type
            }
        }

        catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppBinaryFileFromBinaries()");
            }
        }

        return aimsTempFile;
    }//end getVZAppBinaryFileFromBinaries

    public static AimsTempFile getVZAppBaseTestFile(Long appsId, Long allianceId, Long binaryFirmwareId, String columnName) throws HibernateException {
        Session session = null;
        AimsTempFile aimsTempFile = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("vzAppBinaryFirmware.").append(columnName).append(", ")
                .append("vzAppBinaryFirmware.").append(columnName).append("FileName, ")
                .append("vzAppBinaryFirmware.").append(columnName).append("ContentType ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp as apps, ")
                .append("       com.netpace.aims.model.application.AimsVZAppBinaryFirmwareFiles as vzAppBinaryFirmware, ")
                .append("       com.netpace.aims.model.core.AimsAllianc as alliance ")
                .append("   where ")
                .append("       apps.appsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                .append("       and apps.aimsAllianceId = alliance.allianceId ")
                .append("       and apps.appsId = :appsId ")
                .append("       and vzAppBinaryFirmware.binaryFirmwareId = :binaryFirmwareId ");

            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
            if (allianceId != null)
                queryStringBuffer.append("      and	apps.aimsAllianceId = :allianceId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());
            query.setLong("binaryFirmwareId", binaryFirmwareId.longValue());

            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsTempFile = new AimsTempFile();
                appValues = (Object[]) it.next();

                aimsTempFile.setTempFile((java.sql.Blob) appValues[0]); //File
                aimsTempFile.setTempFileName((String) appValues[1]); //File Name
                aimsTempFile.setTempFileContentType((String) appValues[2]); //File Content Type
            }
        }

        catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppBaseTestFile()");
            }
        }

        return aimsTempFile;
    }//end getVZAppBaseTestFile

    public static void changeApplicationStatus(Session session, AimsApp aimsApp, Long newStatus, String currUser, String currUserType) throws Exception {

        Connection ConOra = null;
        try {

            if(aimsApp !=null) {
                aimsApp.setAimsLifecyclePhaseId(newStatus);
                aimsApp.setLastUpdatedBy(currUser);
                aimsApp.setLastUpdatedDate(new Date());
            }

            session.update(aimsApp);
            //save journal entries for Status change
            ConOra = session.connection();
            ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, currUser, currUserType, false);
        }
        catch (HibernateException e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            log.debug("SESSION Will	be Closed by the calling class for changeApplicationStatus(Session session, Long appsId, Long newStatus, String currUser, String currUserType) method");
        }
    }//end changeApplicationStatus

    public static Long getAllianceIdByBinnaryFirmwareId(Long binnaryFirmwareId)throws HibernateException{
    	Session session=null;
    	Long allianceId=null;
    	StringBuffer sb=new StringBuffer();
    	sb.append(" select app.aimsAllianceId from AimsVZAppBinaryFirmware bf, AimsApp app");
    	sb.append(" where bf.vzAppZoneAppsId = app.appsId ");
    	sb.append(" and bf.binaryFirmwareId = :id ");
    	try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(sb.toString());
			query.setLong("id", binnaryFirmwareId.longValue());
			for (Iterator it = query.iterate(); it.hasNext();){
				allianceId = (Long) it.next();
		    }
		} catch (HibernateException e) {
			log.error(e,e);
			throw e;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return allianceId;
    }

    public static boolean updateFirmwareIsPaid(Long binnaryFirmwareId) throws HibernateException{
    	Session session=null;
    	Transaction trx=null;
    	boolean isUpdated=false;
    	AimsVZAppBinaryFirmware binnaryFirmware=null;
    	try {
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			Query query = session.createQuery("from AimsVZAppBinaryFirmware bf where bf.binaryFirmwareId=:id and bf.isPaid='N'");
			query.setLong("id", binnaryFirmwareId.longValue());
			for (Iterator it = query.iterate(); it.hasNext();){
				binnaryFirmware = (AimsVZAppBinaryFirmware) it.next();
		    }
			if (binnaryFirmware !=null){
				binnaryFirmware.setIsPaid("Y");
				binnaryFirmware.setDatePaid(new Date());
				session.update(binnaryFirmware);
				isUpdated=true;
			}
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null){
				trx.rollback();
			}
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
		return isUpdated;
    }

    public static void savePaidLog(AimsVzappFirmwarePaidLog log)throws HibernateException{
    	Session session=null;
    	Transaction trx=null;
    	try {
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			session.save(log);
			trx.commit();
		} catch (HibernateException e) {
			if (trx != null){
				trx.rollback();
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
		}
    }

    public static void updateAimsVZAppZoneBinaryFirmwares(List aimsVZAppZoneBinaryFirmwaresList)throws HibernateException{
    	Session session=null;
    	Transaction trx=null;
        AimsVZAppBinaryFirmware aimsBinaryFirmware = null;
        try {
            if(aimsVZAppZoneBinaryFirmwaresList!=null && aimsVZAppZoneBinaryFirmwaresList.size()>0) {
                session=DBHelper.getInstance().getSession();
			    trx=session.beginTransaction();
                for(int binaryFirmwareIndex=0; binaryFirmwareIndex<aimsVZAppZoneBinaryFirmwaresList.size(); binaryFirmwareIndex++) {
                    aimsBinaryFirmware = (AimsVZAppBinaryFirmware)aimsVZAppZoneBinaryFirmwaresList.get(binaryFirmwareIndex);
                    if(aimsBinaryFirmware!=null) {
                        session.update(aimsBinaryFirmware);
                    }
                }
                trx.commit();
            }
		} catch (HibernateException he) {
			if (trx != null){
				trx.rollback();
			}
			he.printStackTrace();
			throw he;
		} finally {
			if (session != null){
				session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.updateAimsVZAppZoneBinaryFirmwares()");
            }
		}
    }//end updateAimsVZAppZoneBinaryFirmwares

    public static List getActiveAimsVZAppBinaryFirmwaresByFirmwareIds(Long vzAppZoneAppsId, Long[] firmwareIds) throws HibernateException {
        List aimsVZAppBinaryFirmwareList = null;
        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        if(firmwareIds != null && firmwareIds.length>0) {
            try {
                session=DBHelper.getInstance().getSession();
                queryString = new StringBuffer();
                queryString
                    .append("	SELECT  vzAppBinaryFirmware ")
                    .append("   from com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware ")
                    .append("   where vzAppBinaryFirmware.vzAppZoneAppsId = :vzAppZoneAppsId  ")
                    .append("   and vzAppBinaryFirmware.isActive = :isActive ")
                    .append("   and vzAppBinaryFirmware.firmwareId	in ( :firmwareIds ) ")
                    .append(" order by	vzAppBinaryFirmware.binaryFirmwareId	");

                query = session.createQuery(queryString.toString());
                query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
                query.setString("isActive", AimsConstants.ACTIVE_CHAR);
                query.setParameterList("firmwareIds", firmwareIds);

                aimsVZAppBinaryFirmwareList  = query.list();
            }
            catch (HibernateException he) {
                he.printStackTrace();
                throw he;
            }
            finally {
                if(session!=null) {
                    session.close();
                    log.debug("Session closed in VZAppZoneApplicationManager.getAimsVZAppBinaryFirmwaresByFirmwareIds()");
                }
            }
        }
        return aimsVZAppBinaryFirmwareList;
    }

    public static List getAimsVZAppBinaryFirmwaresByBinaryFirmwareIds(Long vzAppZoneAppsId, Long[] binaryFirmwareIds, String isActive) throws HibernateException {
        List aimsVZAppBinaryFirmwareList = null;
        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        if(binaryFirmwareIds != null && binaryFirmwareIds.length>0) {
            try {
                session=DBHelper.getInstance().getSession();
                queryString = new StringBuffer();
                queryString
                    .append("	SELECT  vzAppBinaryFirmware ")
                    .append("   from com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware ")
                    .append("   where vzAppBinaryFirmware.vzAppZoneAppsId = :vzAppZoneAppsId  ")
                    .append("   and vzAppBinaryFirmware.binaryFirmwareId	in ( :binaryFirmwareIds ) ");
                if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                    queryString.append(" AND     vzAppBinaryFirmware.isActive = :isActive ");
                }
                queryString.append(" order by	vzAppBinaryFirmware.binaryFirmwareId	");

                query = session.createQuery(queryString.toString());
                query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
                query.setParameterList("binaryFirmwareIds", binaryFirmwareIds);
                if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                    query.setString("isActive", isActive);
                }
                aimsVZAppBinaryFirmwareList  = query.list();
            }
            catch (HibernateException he) {
                he.printStackTrace();
                throw he;
            }
            finally {
                if(session!=null) {
                    session.close();
                    log.debug("Session closed in VZAppZoneApplicationManager.getAimsVZAppBinaryFirmwaresByBinaryFirmwareIds()");
                }
            }
        }
        return aimsVZAppBinaryFirmwareList;
    }//end getAimsVZAppBinaryFirmwaresByBinaryFirmwareIds

    public static List getActiveAimsVZAppBinaryFirmwares(Long vzAppZoneAppsId) throws HibernateException {
        List aimsVZAppBinaryFirmwareList = null;
        Query query = null;
        Session session = null;
        StringBuffer queryString = null;

            try {
                session=DBHelper.getInstance().getSession();
                queryString = new StringBuffer();
                queryString
                    .append("	SELECT  vzAppBinaryFirmware ")
                    .append("   from com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware ")
                    .append("   where vzAppBinaryFirmware.vzAppZoneAppsId = :vzAppZoneAppsId  ")
                    .append("   and vzAppBinaryFirmware.isActive = :isActive ")
                    .append(" order by	vzAppBinaryFirmware.binaryFirmwareId	");

                query = session.createQuery(queryString.toString());
                query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
                query.setString("isActive", AimsConstants.ACTIVE_CHAR);

                aimsVZAppBinaryFirmwareList  = query.list();
            }
            catch (HibernateException he) {
                he.printStackTrace();
                throw he;
            }
            finally {
                if(session!=null) {
                    session.close();
                    log.debug("Session closed in VZAppZoneApplicationManager.getActiveAimsVZAppBinaryFirmwares()");
                }
            }

        return aimsVZAppBinaryFirmwareList;
    }

    public static Map getVZAppBinaryFirmwareInfoMap(Long vzAppZoneAppsId, String isActive) throws HibernateException {
        Map vzAppBinaryFirmwareInfoMap = null;
        List vzAppZoneBinaryFirmwareInfoVOList = null;
        List vzAppBinaryIdsList = null;//temp list to store binary ids
        List resultsList = null;
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        Long binaryId = null;

        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString
                .append("   SELECT      vzAppZoneApp.vzAppZoneAppsId, ")
                .append("               device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber, ")
                .append("               binary.binaryId, binary.binaryFileFileName, binary.binaryVersion,")
                .append("               binary.binaryFileSize, binary.previewFileFileName,")
                .append("               vzAppBinaryFirmware.binaryFirmwareId, vzAppBinaryFirmware.status, typeForStatus.typeValue, ")
                .append("               vzAppBinaryFirmware.isPaid, vzAppBinaryFirmware.isActive")
                .append("       FROM    com.netpace.aims.model.masters.AimsDevic device,")
                .append("               com.netpace.aims.model.system.AimsDeviceFirmware firmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaries binary,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppZoneApp vzAppZoneApp, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForStatus ")
                .append("       WHERE   vzAppBinaryFirmware.binaryId = binary.binaryId  ")
                .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                .append("       AND     typeForStatus.typeId = vzAppBinaryFirmware.status")
                .append("       AND     device.deviceId = firmware.deviceId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = :vzAppZoneAppsId");
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                queryString.append(" AND     vzAppBinaryFirmware.isActive = :isActive ");
			}
            queryString.append("   ORDER BY binary.binaryId, device.deviceModel	");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}

            itr = query.iterate();
            if(itr!=null) {
                vzAppBinaryIdsList = new ArrayList();
                resultsList = new ArrayList();
                vzAppBinaryFirmwareInfoMap = new HashMap();
                //add binaryfirmwareInfo in vzAppZoneBinaryFirmwareInfoVOList first, and
                //add vzAppZoneBinaryFirmwareInfoVOList in results list
                //vzAppZoneBinaryFirmwareInfoVOList will have list of binaryfirmwarevos having same binary ids
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();
                    binaryId = (Long)userValues[5];

                    if(!vzAppBinaryIdsList.contains(binaryId)) {//binary id appears first time
                        vzAppZoneBinaryFirmwareInfoVOList = new ArrayList();
                    }

                    binaryFirmwareInfo.setVzAppZoneAppsId((Long)userValues[0]);
                    binaryFirmwareInfo.setPhoneModel((String)userValues[1]);
                    binaryFirmwareInfo.setFirmwareId((Long)userValues[2]);

                    //binaryFirmwareInfo.setFirmwareName((String)userValues[3]);
                    binaryFirmwareInfo.setFirmwareName(Utility.getEllipseString(30,(String)userValues[3]));//max 30 chars for firmware name

                    binaryFirmwareInfo.setMrNumber("MR-"+(Integer)userValues[4]);


                    binaryFirmwareInfo.setBinaryId(binaryId);
                    //binaryFirmwareInfo.setBinaryFileFileName((String)userValues[6]);
                    //binaryFirmwareInfo.setBinaryVersion((String)userValues[7]);
                    if(!StringFuncs.isNullOrEmpty((String)userValues[6])) {
                        binaryFirmwareInfo.setBinaryFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String)userValues[6]));
                    }
                    else {
                        binaryFirmwareInfo.setBinaryFileFileName(null);
                    }

                    binaryFirmwareInfo.setBinaryVersion(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,StringFuncs.NullValueReplacement((String)userValues[7])));
                    binaryFirmwareInfo.setBinaryFileSize((String)userValues[8]);
                    //binaryFirmwareInfo.setPreviewFileFileName((String)userValues[9]);
                    if(!StringFuncs.isNullOrEmpty((String)userValues[9])) {
                        binaryFirmwareInfo.setPreviewFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String)userValues[9]));
                    }
                    else {
                        binaryFirmwareInfo.setPreviewFileFileName(null);
                    }

                    binaryFirmwareInfo.setBinaryFirmwareId((Long)userValues[10]);
                    binaryFirmwareInfo.setBinaryFirmwareStatus((Long)userValues[11]);
                    binaryFirmwareInfo.setBinaryFirmwareStatusValue((String)userValues[12]);
                    binaryFirmwareInfo.setIsPaid((String)userValues[13]);
                    binaryFirmwareInfo.setIsActive((String)userValues[14]);

                    vzAppZoneBinaryFirmwareInfoVOList.add(binaryFirmwareInfo);

                    if(!vzAppBinaryIdsList.contains(binaryId)) {
                        vzAppBinaryIdsList.add(binaryId);
                        resultsList.add(vzAppZoneBinaryFirmwareInfoVOList);//add list of binary firmware info vos in results
                    }

                }//end while
            }//end while
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryFirmwareInfoList", resultsList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryIdsList", vzAppBinaryIdsList);
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getVZAppBinaryFirmwareInfoMap(Long vzAppZoneAppsId, String isActive)");
            }
        }
        return vzAppBinaryFirmwareInfoMap;
    }//end getVZAppBinaryFirmwareInfoMap

    /**
     * returns info with AimsVZAppBinary, BinaryFirmwareInfoList pair
     * vzAppBinaryFirmwareInfoList  -   List of VZAppBinaryFirmwareInfoVO
     * vzAppBinariesList            -   List of AimsVZAppBinaries
     * @param vzAppZoneAppsId
     * @param isActive
     * @param useEllipse
     * @return
     * @throws HibernateException
     */
    public static Map getVZAppBinaryFirmwareInfoMapWithBinaries(Long vzAppZoneAppsId, String isActive, String mrNumberFormat, boolean useEllipse) throws HibernateException {
        Map vzAppBinaryFirmwareInfoMap = null;

        List vzAppZoneBinaryFirmwareInfoVOList = null;
        List vzAppBinariesList = null;

        List vzAppBinaryIdsList = null;//temp list to store binary ids
        List resultsList = null;

        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        AimsVZAppBinaries vzAppBinary = null;


        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString
                .append("   SELECT      binary, ")
                .append("               vzAppZoneApp.vzAppZoneAppsId,")
                .append("               device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber, ")
                .append("               vzAppBinaryFirmware.binaryFirmwareId, vzAppBinaryFirmware.status, typeForStatus.typeValue, ")
                .append("               vzAppBinaryFirmware.isPaid, vzAppBinaryFirmware.isActive, ")
                .append("               device.assetType, typeForAssetType.typeValue ")
                .append("       FROM    com.netpace.aims.model.masters.AimsDevic device,")
                .append("               com.netpace.aims.model.system.AimsDeviceFirmware firmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaries binary,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppZoneApp vzAppZoneApp, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForStatus, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForAssetType ")
                .append("       WHERE   vzAppBinaryFirmware.binaryId = binary.binaryId  ")
                .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                .append("       AND     typeForStatus.typeId = vzAppBinaryFirmware.status ")
                .append("       AND     typeForAssetType.typeId (+) = device.assetType ")
                .append("       AND     device.deviceId = firmware.deviceId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = :vzAppZoneAppsId");
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                queryString.append(" AND     vzAppBinaryFirmware.isActive = :isActive ");
			}
            queryString.append("   ORDER BY binary.binaryId, device.deviceModel	");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}

            itr = query.iterate();
            if(itr!=null) {
                vzAppBinaryIdsList = new ArrayList();
                vzAppBinariesList = new ArrayList();
                resultsList = new ArrayList();
                vzAppBinaryFirmwareInfoMap = new HashMap();
                //add binaryfirmwareInfo in vzAppZoneBinaryFirmwareInfoVOList first, and
                //add vzAppZoneBinaryFirmwareInfoVOList in results list
                //vzAppZoneBinaryFirmwareInfoVOList will have list of binaryfirmwarevos having same binary ids
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();
                    vzAppBinary = (AimsVZAppBinaries)userValues[0];

                    if(!vzAppBinaryIdsList.contains(vzAppBinary.getBinaryId())) {//binary appears first time
                        vzAppZoneBinaryFirmwareInfoVOList = new ArrayList();
                    }

                    binaryFirmwareInfo.setVzAppZoneAppsId((Long)userValues[1]);
                    binaryFirmwareInfo.setPhoneModel((String)userValues[2]);
                    binaryFirmwareInfo.setFirmwareId((Long)userValues[3]);

                    if(useEllipse) {
                        binaryFirmwareInfo.setFirmwareName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                                        StringFuncs.NullValueReplacement((String)userValues[4])));
                    }
                    else {
                        binaryFirmwareInfo.setFirmwareName((String)userValues[4]);
                    }

                    binaryFirmwareInfo.setMrNumber(mrNumberFormat+(Integer)userValues[5]);

                    binaryFirmwareInfo.setBinaryId(vzAppBinary.getBinaryId());

                    binaryFirmwareInfo.setBinaryFileSize(vzAppBinary.getBinaryFileSize());
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(vzAppBinary.getBinaryFileFileName())) {
                            binaryFirmwareInfo.setBinaryFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, vzAppBinary.getBinaryFileFileName()));
                        }
                        else {
                            //set null to avoid nbsp; in html for links
                            binaryFirmwareInfo.setBinaryFileFileName(null);
                        }

                        binaryFirmwareInfo.setBinaryVersion(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                StringFuncs.NullValueReplacement(vzAppBinary.getBinaryVersion())));

                        if(!StringFuncs.isNullOrEmpty(vzAppBinary.getPreviewFileFileName())) {
                            binaryFirmwareInfo.setPreviewFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, vzAppBinary.getPreviewFileFileName()));
                        }
                        else {
                            binaryFirmwareInfo.setPreviewFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwareInfo.setBinaryFileFileName(vzAppBinary.getBinaryFileFileName());
                        binaryFirmwareInfo.setBinaryVersion(vzAppBinary.getBinaryVersion());
                        binaryFirmwareInfo.setPreviewFileFileName(vzAppBinary.getPreviewFileFileName());
                    }
                    binaryFirmwareInfo.setDocumentFileFileName(vzAppBinary.getDocumentFileFileName());

                    binaryFirmwareInfo.setBinaryFirmwareId((Long)userValues[6]);
                    binaryFirmwareInfo.setBinaryFirmwareStatus((Long)userValues[7]);
                    binaryFirmwareInfo.setBinaryFirmwareStatusValue((String)userValues[8]);
                    binaryFirmwareInfo.setIsPaid((String)userValues[9]);
                    binaryFirmwareInfo.setIsActive((String)userValues[10]);

                    binaryFirmwareInfo.setAssetType((Long)userValues[11]);
                    binaryFirmwareInfo.setAssetTypeValue((String)userValues[12]);

                    vzAppZoneBinaryFirmwareInfoVOList.add(binaryFirmwareInfo);

                    if(!vzAppBinaryIdsList.contains(vzAppBinary.getBinaryId())) {
                        vzAppBinaryIdsList.add(vzAppBinary.getBinaryId());
                        vzAppBinariesList.add(vzAppBinary);
                        resultsList.add(vzAppZoneBinaryFirmwareInfoVOList);//add list of binary firmware info vos in results
                    }

                }//end while
            }//end while
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryFirmwareInfoList", resultsList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinariesList", vzAppBinariesList);
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getVZAppBinaryFirmwareInfoMapWithBinaries(Long vzAppZoneAppsId, String isActive)");
            }
        }
        return vzAppBinaryFirmwareInfoMap;
    }//end getVZAppBinaryFirmwareInfoMapWithBinaries

    public static Map getVZAppBinaryFirmwareInfoMapWithBinariesByBinaryIds(Long vzAppZoneAppsId, String isActive,
                                                                Long[] binaryIds, String columnType,
                                                                String mrNumberPrefix,
                                                                boolean useEllipse) throws HibernateException {
        Map vzAppBinaryFirmwareInfoMap = null;

        List vzAppZoneBinaryFirmwareInfoVOList = null;
        List vzAppBinariesList = null;

        List vzAppBinaryIdsList = null;
        List vzAppBinaryFirmwareIdsList = null;
        List resultsList = null;

        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        AimsVZAppBinaries vzAppBinary = null;


        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        boolean getByXMLMoved = false;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString
                .append("   SELECT      binary, ")
                .append("               vzAppZoneApp.vzAppZoneAppsId,")
                .append("               device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber, ")
                .append("               vzAppBinaryFirmware.binaryFirmwareId, vzAppBinaryFirmware.status, typeForStatus.typeValue, ")
                .append("               vzAppBinaryFirmware.isPaid, vzAppBinaryFirmware.isActive, ")
                .append("               device.assetType, typeForAssetType.typeValue, device.mportalDeviceName, ")
                .append("               vzAppBinaryFirmware.otaDeleted, vzAppBinaryFirmware.stageDeleted, vzAppBinaryFirmware.prodDeleted ")
                .append("       FROM    com.netpace.aims.model.masters.AimsDevic device,")
                .append("               com.netpace.aims.model.system.AimsDeviceFirmware firmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaries binary,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppZoneApp vzAppZoneApp, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForStatus, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForAssetType ")
                .append("       WHERE   vzAppBinaryFirmware.binaryId = binary.binaryId  ")
                .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                .append("       AND     typeForStatus.typeId = vzAppBinaryFirmware.status ")
                .append("       AND     typeForAssetType.typeId (+) = device.assetType ")
                .append("       AND     device.deviceId = firmware.deviceId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = :vzAppZoneAppsId");
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                //if device is either active or prodDeleted is Y (device upgrade), then include this device in resultset
                queryString.append(" AND     ( vzAppBinaryFirmware.isActive = :isActive ");
                queryString.append("            OR (vzAppBinaryFirmware.otaDeleted = :otaDeleted)");
                queryString.append("            OR (vzAppBinaryFirmware.prodDeleted = :prodDeleted)");
                queryString.append("          )");
            }
            if(!StringFuncs.isNullOrEmpty(columnType)) {
                if(columnType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)) {
                    queryString.append(" AND     vzAppBinaryFirmware.otaMoved = :moved ");
                    getByXMLMoved = true;
                }
                else if(columnType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE)) {
                    queryString.append(" AND     vzAppBinaryFirmware.stageMoved = :moved ");
                    getByXMLMoved = true;
                }
                else if(columnType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)) {
                    queryString.append(" AND     vzAppBinaryFirmware.prodMoved = :moved ");
                    getByXMLMoved = true;
                }
            }
            //set binary ids in query if found
            if(binaryIds!=null && binaryIds.length>0) {
                queryString.append(" AND     binary.binaryId in ( :binaryIds ) ");
            }

            queryString.append("   ORDER BY binary.binaryId, device.deviceModel	");

            query = session.createQuery(queryString.toString());

            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}

            query.setString("otaDeleted", "Y");
            query.setString("prodDeleted", "Y");

            if(getByXMLMoved) {
                query.setString("moved", "Y");
            }
            if(binaryIds!=null && binaryIds.length>0) {
                query.setParameterList("binaryIds", binaryIds);
            }

            itr = query.iterate();
            if(itr!=null) {
                vzAppBinaryIdsList = new ArrayList();
                vzAppBinariesList = new ArrayList();
                vzAppBinaryFirmwareIdsList = new ArrayList();
                resultsList = new ArrayList();
                vzAppBinaryFirmwareInfoMap = new HashMap();
                //add binaryfirmwareInfo in vzAppZoneBinaryFirmwareInfoVOList first, and
                //add vzAppZoneBinaryFirmwareInfoVOList in results list
                //vzAppZoneBinaryFirmwareInfoVOList will have list of binaryfirmwarevos having same binary ids
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();
                    vzAppBinary = (AimsVZAppBinaries)userValues[0];

                    if(!vzAppBinaryIdsList.contains(vzAppBinary.getBinaryId())) {//binary appears first time
                        vzAppZoneBinaryFirmwareInfoVOList = new ArrayList();
                    }

                    binaryFirmwareInfo.setVzAppZoneAppsId((Long)userValues[1]);
                    binaryFirmwareInfo.setPhoneModel((String)userValues[2]);
                    binaryFirmwareInfo.setFirmwareId((Long)userValues[3]);

                    if(useEllipse) {
                        binaryFirmwareInfo.setFirmwareName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                                        StringFuncs.NullValueReplacement((String)userValues[4])));
                    }
                    else {
                        binaryFirmwareInfo.setFirmwareName((String)userValues[4]);
                    }

                    binaryFirmwareInfo.setMrNumber(mrNumberPrefix+(Integer)userValues[5]);

                    binaryFirmwareInfo.setBinaryId(vzAppBinary.getBinaryId());

                    binaryFirmwareInfo.setBinaryFileSize(vzAppBinary.getBinaryFileSize());
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(vzAppBinary.getBinaryFileFileName())) {
                            binaryFirmwareInfo.setBinaryFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, vzAppBinary.getBinaryFileFileName()));
                        }
                        else {
                            //set null to avoid nbsp; in html for links
                            binaryFirmwareInfo.setBinaryFileFileName(null);
                        }

                        binaryFirmwareInfo.setBinaryVersion(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                StringFuncs.NullValueReplacement(vzAppBinary.getBinaryVersion())));

                        if(!StringFuncs.isNullOrEmpty(vzAppBinary.getPreviewFileFileName())) {
                            binaryFirmwareInfo.setPreviewFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, vzAppBinary.getPreviewFileFileName()));
                        }
                        else {
                            binaryFirmwareInfo.setPreviewFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwareInfo.setBinaryFileFileName(vzAppBinary.getBinaryFileFileName());
                        binaryFirmwareInfo.setBinaryVersion(vzAppBinary.getBinaryVersion());
                        binaryFirmwareInfo.setPreviewFileFileName(vzAppBinary.getPreviewFileFileName());
                    }

                    binaryFirmwareInfo.setBinaryFirmwareId((Long)userValues[6]);
                    vzAppBinaryFirmwareIdsList.add((Long)userValues[6]);

                    binaryFirmwareInfo.setBinaryFirmwareStatus((Long)userValues[7]);
                    binaryFirmwareInfo.setBinaryFirmwareStatusValue((String)userValues[8]);
                    binaryFirmwareInfo.setIsPaid((String)userValues[9]);
                    binaryFirmwareInfo.setIsActive((String)userValues[10]);

                    //set assetType
                    binaryFirmwareInfo.setAssetType((Long)userValues[11]);
                    binaryFirmwareInfo.setAssetTypeValue((String)userValues[12]);
                    binaryFirmwareInfo.setMportalDeviceName((String)userValues[13]);

                    //set deleted flags
                    binaryFirmwareInfo.setOtaDeleted((String)userValues[14]);
                    binaryFirmwareInfo.setStageDeleted((String)userValues[15]);
                    binaryFirmwareInfo.setProdDeleted((String)userValues[16]);

                    vzAppZoneBinaryFirmwareInfoVOList.add(binaryFirmwareInfo);


                    if(!vzAppBinaryIdsList.contains(vzAppBinary.getBinaryId())) {
                        vzAppBinaryIdsList.add(vzAppBinary.getBinaryId());
                        vzAppBinariesList.add(vzAppBinary);
                        resultsList.add(vzAppZoneBinaryFirmwareInfoVOList);//add list of binary firmware info vos in results
                    }

                }//end while
            }
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryFirmwareInfoList", resultsList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinariesList", vzAppBinariesList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryFirmwareIdsList", vzAppBinaryFirmwareIdsList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryIdsList", vzAppBinaryIdsList);

        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getVZAppBinaryFirmwareInfoMapWithBinariesByBinaryIds");
            }
        }
        return vzAppBinaryFirmwareInfoMap;
    }

    //todo merge with getVZAppBinaryFirmwareInfoMapWithBinariesByBinaryIds and getVZAppBinaryFirmwareInfoMapWithBinaries


    public static Map getVZAppBinaryFirmwareInfoMapByIds(Long vzAppZoneAppsId, String isActive,
                                                                Long[] binaryFirmwareIds, String columnType,
                                                                String mrNumberPrefix,
                                                                boolean useEllipse) throws HibernateException {
        Map vzAppBinaryFirmwareInfoMap = null;

        List vzAppZoneBinaryFirmwareInfoVOList = null;
        List vzAppBinariesList = null;

        List vzAppBinaryIdsList = null;
        List vzAppBinaryFirmwareIdsList = null;
        List resultsList = null;

        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        AimsVZAppBinaries vzAppBinary = null;


        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        boolean getByXMLMoved = false;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString
                .append("   SELECT      binary, ")
                .append("               vzAppZoneApp.vzAppZoneAppsId,")
                .append("               device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber, ")
                .append("               vzAppBinaryFirmware.binaryFirmwareId, vzAppBinaryFirmware.status, typeForStatus.typeValue, ")
                .append("               vzAppBinaryFirmware.isPaid, vzAppBinaryFirmware.isActive ")
                .append("       FROM    com.netpace.aims.model.masters.AimsDevic device,")
                .append("               com.netpace.aims.model.system.AimsDeviceFirmware firmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaries binary,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppZoneApp vzAppZoneApp, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForStatus ")
                .append("       WHERE   vzAppBinaryFirmware.binaryId = binary.binaryId  ")
                .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                .append("       AND     typeForStatus.typeId = vzAppBinaryFirmware.status")
                .append("       AND     device.deviceId = firmware.deviceId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = :vzAppZoneAppsId");
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                queryString.append(" AND     vzAppBinaryFirmware.isActive = :isActive ");
			}
            if(!StringFuncs.isNullOrEmpty(columnType)) {
                if(columnType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_OTA)) {
                    queryString.append(" AND     vzAppBinaryFirmware.otaMoved = :moved ");
                    getByXMLMoved = true;
                }
                else if(columnType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_STAGE)) {
                    queryString.append(" AND     vzAppBinaryFirmware.stageMoved = :moved ");
                    getByXMLMoved = true;
                }
                else if(columnType.equals(VZAppZoneApplicationHelper.VZAPPZONE_TYPE_PROD)) {
                    queryString.append(" AND     vzAppBinaryFirmware.prodMoved = :moved ");
                    getByXMLMoved = true;
                }
            }
            queryString.append(" AND     vzAppBinaryFirmware.binaryFirmwareId in ( :binaryFirmwareIds ) ");

            queryString.append("   ORDER BY binary.binaryId, device.deviceModel	");

            query = session.createQuery(queryString.toString());

            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}
            if(getByXMLMoved) {
                query.setString("moved", "Y");
            }
            query.setParameterList("binaryFirmwareIds", binaryFirmwareIds);

            itr = query.iterate();
            if(itr!=null) {
                vzAppBinaryIdsList = new ArrayList();
                vzAppBinariesList = new ArrayList();
                vzAppBinaryFirmwareIdsList = new ArrayList();
                resultsList = new ArrayList();
                vzAppBinaryFirmwareInfoMap = new HashMap();
                //add binaryfirmwareInfo in vzAppZoneBinaryFirmwareInfoVOList first, and
                //add vzAppZoneBinaryFirmwareInfoVOList in results list
                //vzAppZoneBinaryFirmwareInfoVOList will have list of binaryfirmwarevos having same binary ids
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();
                    vzAppBinary = (AimsVZAppBinaries)userValues[0];

                    if(!vzAppBinaryIdsList.contains(vzAppBinary.getBinaryId())) {//binary appears first time
                        vzAppZoneBinaryFirmwareInfoVOList = new ArrayList();
                    }

                    binaryFirmwareInfo.setVzAppZoneAppsId((Long)userValues[1]);
                    binaryFirmwareInfo.setPhoneModel((String)userValues[2]);
                    binaryFirmwareInfo.setFirmwareId((Long)userValues[3]);

                    if(useEllipse) {
                        binaryFirmwareInfo.setFirmwareName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                                        StringFuncs.NullValueReplacement((String)userValues[4])));
                    }
                    else {
                        binaryFirmwareInfo.setFirmwareName((String)userValues[4]);
                    }

                    binaryFirmwareInfo.setMrNumber(mrNumberPrefix+(Integer)userValues[5]);

                    binaryFirmwareInfo.setBinaryId(vzAppBinary.getBinaryId());

                    binaryFirmwareInfo.setBinaryFileSize(vzAppBinary.getBinaryFileSize());
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(vzAppBinary.getBinaryFileFileName())) {
                            binaryFirmwareInfo.setBinaryFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, vzAppBinary.getBinaryFileFileName()));
                        }
                        else {
                            //set null to avoid nbsp; in html for links
                            binaryFirmwareInfo.setBinaryFileFileName(null);
                        }

                        binaryFirmwareInfo.setBinaryVersion(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                StringFuncs.NullValueReplacement(vzAppBinary.getBinaryVersion())));

                        if(!StringFuncs.isNullOrEmpty(vzAppBinary.getPreviewFileFileName())) {
                            binaryFirmwareInfo.setPreviewFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, vzAppBinary.getPreviewFileFileName()));
                        }
                        else {
                            binaryFirmwareInfo.setPreviewFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwareInfo.setBinaryFileFileName(vzAppBinary.getBinaryFileFileName());
                        binaryFirmwareInfo.setBinaryVersion(vzAppBinary.getBinaryVersion());
                        binaryFirmwareInfo.setPreviewFileFileName(vzAppBinary.getPreviewFileFileName());
                    }

                    binaryFirmwareInfo.setBinaryFirmwareId((Long)userValues[6]);
                    vzAppBinaryFirmwareIdsList.add((Long)userValues[6]);

                    binaryFirmwareInfo.setBinaryFirmwareStatus((Long)userValues[7]);
                    binaryFirmwareInfo.setBinaryFirmwareStatusValue((String)userValues[8]);
                    binaryFirmwareInfo.setIsPaid((String)userValues[9]);
                    binaryFirmwareInfo.setIsActive((String)userValues[10]);

                    vzAppZoneBinaryFirmwareInfoVOList.add(binaryFirmwareInfo);


                    if(!vzAppBinaryIdsList.contains(vzAppBinary.getBinaryId())) {
                        vzAppBinaryIdsList.add(vzAppBinary.getBinaryId());
                        vzAppBinariesList.add(vzAppBinary);
                        resultsList.add(vzAppZoneBinaryFirmwareInfoVOList);//add list of binary firmware info vos in results
                    }

                }//end while
            }//end while
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryFirmwareInfoList", resultsList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinariesList", vzAppBinariesList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryFirmwareIdsList", vzAppBinaryFirmwareIdsList);
            vzAppBinaryFirmwareInfoMap.put("vzAppBinaryIdsList", vzAppBinaryIdsList);

        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in getVZAppBinaryFirmwareInfoMapByIds(Long vzAppZoneAppsId, String isActive)");
            }
        }
        return vzAppBinaryFirmwareInfoMap;
    }//end getVZAppBinaryFirmwareInfoMapByIds


    /**
     * returns list of binary firmwares having given firmware ids
     * @param vzAppZoneAppsId
     * @param firmwareIds
     * @param isActive
     * @param mrNumberPrefix
     * @param useEllipse
     * @param ignoreProductionBinaryFirmwares - if this flag is true, then binary firmwares in production are not included in list
     * @return
     * @throws HibernateException
     */
    public static List getVZAppBinaryFirmwareInfoListByFirmwareIds(Long vzAppZoneAppsId,
                                                                   Long [] firmwareIds,
                                                                   String isActive,
                                                                   String mrNumberPrefix,
                                                                   boolean useEllipse,
                                                                   boolean ignoreProductionBinaryFirmwares) throws HibernateException {
        List vzAppZoneBinaryFirmwareInfoVOList = null;
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;
        Long binaryFirmwareStatus = null;
        boolean addBinaryFirmwareInList = true;

        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString
                .append("   SELECT      vzAppZoneApp.vzAppZoneAppsId, ")
                .append("               device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber, ")
                .append("               binary.binaryId, binary.binaryFileFileName, binary.binaryVersion,")
                .append("               binary.binaryFileSize, binary.previewFileFileName,")
                .append("               vzAppBinaryFirmware.binaryFirmwareId, vzAppBinaryFirmware.status, typeForStatus.typeValue, ")
                .append("               vzAppBinaryFirmware.isPaid, vzAppBinaryFirmware.isActive, ")
                .append("               device.assetType, typeForAssetType.typeValue ")
                .append("       FROM    com.netpace.aims.model.masters.AimsDevic device,")
                .append("               com.netpace.aims.model.system.AimsDeviceFirmware firmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaries binary,")
                .append("               com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware,")
                .append("               com.netpace.aims.model.application.AimsVZAppZoneApp vzAppZoneApp, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForStatus, ")
                .append("               com.netpace.aims.model.core.AimsTypes typeForAssetType ")
                .append("       WHERE   vzAppBinaryFirmware.binaryId = binary.binaryId  ")
                .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                .append("       AND     typeForStatus.typeId = vzAppBinaryFirmware.status ")
                .append("       AND     typeForAssetType.typeId (+) = device.assetType ")
                .append("       AND     device.deviceId = firmware.deviceId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                .append("       AND     vzAppZoneApp.vzAppZoneAppsId = :vzAppZoneAppsId")
                .append("       AND     vzAppBinaryFirmware.firmwareId	in ( :firmwareIds ) ");
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                queryString.append(" AND     vzAppBinaryFirmware.isActive = :isActive ");
			}
            queryString.append("   ORDER BY binary.binaryId, device.deviceModel	");
            //queryString.append("   ORDER BY vzAppBinaryFirmware.binaryFirmwareId ");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            query.setParameterList("firmwareIds", firmwareIds);
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}

            itr = query.iterate();
            if(itr!=null) {
                vzAppZoneBinaryFirmwareInfoVOList =  new ArrayList();
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();
                    addBinaryFirmwareInList = true; //by default add binaryFirmware In List

                    binaryFirmwareInfo.setVzAppZoneAppsId((Long)userValues[0]);
                    binaryFirmwareInfo.setPhoneModel((String)userValues[1]);
                    binaryFirmwareInfo.setFirmwareId((Long)userValues[2]);
                    binaryFirmwareInfo.setFirmwareName((String)userValues[3]);
                    //binaryFirmwareInfo.setMrNumber("MR-"+(Integer)userValues[4]);
                    binaryFirmwareInfo.setMrNumber(mrNumberPrefix+(Integer)userValues[4]);

                    binaryFirmwareInfo.setBinaryId((Long)userValues[5]);
                    binaryFirmwareInfo.setBinaryFileFileName((String)userValues[6]);
                    binaryFirmwareInfo.setBinaryVersion((String)userValues[7]);
                    binaryFirmwareInfo.setBinaryFileSize((String)userValues[8]);
                    binaryFirmwareInfo.setPreviewFileFileName((String)userValues[9]);

                    binaryFirmwareInfo.setBinaryFirmwareId((Long)userValues[10]);

                    binaryFirmwareStatus = (Long)userValues[11];
                    binaryFirmwareInfo.setBinaryFirmwareStatus(binaryFirmwareStatus);
                    binaryFirmwareInfo.setBinaryFirmwareStatusValue((String)userValues[12]);

                    binaryFirmwareInfo.setIsPaid((String)userValues[13]);
                    binaryFirmwareInfo.setIsActive((String)userValues[14]);

                    binaryFirmwareInfo.setAssetType((Long)userValues[15]);
                    binaryFirmwareInfo.setAssetTypeValue((String)userValues[16]);

                    if(ignoreProductionBinaryFirmwares
                            && Utility.ZeroValueReplacement(binaryFirmwareStatus).equals(AimsConstants.VZAPPZONE_BINARY_STATUS_IN_PRODUCTION)) {
                        addBinaryFirmwareInList = false;
                    }

                    if(addBinaryFirmwareInList) {
                        vzAppZoneBinaryFirmwareInfoVOList.add(binaryFirmwareInfo);
                    }

                }//end while
            }//end while
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppBinaryFirmwareInfoListByFirmwareIds");
            }
        }
        return vzAppZoneBinaryFirmwareInfoVOList;
    }//end getVZAppBinaryFirmwareInfoListByFirmwareIds

    public static List getVZAppBinaryFirmwareTestInfoList(Long vzAppZoneAppsId, String isActive, String mrNumberFormat, boolean useEllipse) throws HibernateException {
        List vzAppZoneBinaryFirmwareInfoVOList = null;
        VZAppBinaryFirmwareInfoVO binaryFirmwareInfo = null;

        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString.append("   SELECT  ")
                       .append(getBinaryFirmwareBasicColumnsForQuery());
            queryString.append("    FROM    ")
                       .append(getBinaryFirmwareBasicFromSectionForQuery());
            queryString.append("    WHERE   ")
                       .append(getBinaryFirmwareBasicWhereClause(isActive));
            queryString.append("    ORDER BY device.deviceModel, firmware.mrNumber	");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}

            itr = query.iterate();
            if(itr!=null) {
                vzAppZoneBinaryFirmwareInfoVOList =  new ArrayList();
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwareInfo = new VZAppBinaryFirmwareInfoVO();

                    binaryFirmwareInfo.setVzAppZoneAppsId((Long)userValues[0]);
                    binaryFirmwareInfo.setPhoneModel((String)userValues[1]);
                    binaryFirmwareInfo.setFirmwareId((Long)userValues[2]);
                    if(useEllipse) {
                        binaryFirmwareInfo.setFirmwareName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                StringFuncs.NullValueReplacement((String)userValues[3])));
                    }
                    else {
                        binaryFirmwareInfo.setFirmwareName((String)userValues[3]);
                    }
                    //binaryFirmwareInfo.setMrNumber("MR-"+(Integer)userValues[4]);
                    binaryFirmwareInfo.setMrNumber(mrNumberFormat+(Integer)userValues[4]);


                    binaryFirmwareInfo.setBinaryId((Long)userValues[5]);
                    if(useEllipse) {
                        binaryFirmwareInfo.setBinaryFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                    StringFuncs.NullValueReplacement((String)userValues[6])));
                        binaryFirmwareInfo.setBinaryVersion(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                    StringFuncs.NullValueReplacement((String)userValues[7])));
                        binaryFirmwareInfo.setPreviewFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,
                                                                    StringFuncs.NullValueReplacement((String)userValues[9])));
                    }
                    else {
                        binaryFirmwareInfo.setBinaryFileFileName((String)userValues[6]);
                        binaryFirmwareInfo.setBinaryVersion((String)userValues[7]);
                        binaryFirmwareInfo.setPreviewFileFileName((String)userValues[9]);
                    }
                    binaryFirmwareInfo.setBinaryFileSize((String)userValues[8]);
                    binaryFirmwareInfo.setBinaryFirmwareId((Long)userValues[10]);
                    binaryFirmwareInfo.setBinaryFirmwareStatus((Long)userValues[11]);
                    binaryFirmwareInfo.setBinaryFirmwareStatusValue((String)userValues[12]);
                    binaryFirmwareInfo.setIsPaid((String)userValues[13]);
                    binaryFirmwareInfo.setIsActive((String)userValues[14]);

                    vzAppZoneBinaryFirmwareInfoVOList.add(binaryFirmwareInfo);

                }//end while
            }//end while
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppBinaryFirmwareTestInfoList");
            }
        }
        return vzAppZoneBinaryFirmwareInfoVOList;
    }//end getVZAppBinaryFirmwareInfoList

    private static String getBinaryFirmwareBasicColumnsForQuery() {
        StringBuffer basicColumnsBuffer = new StringBuffer();
        basicColumnsBuffer.append("               vzAppZoneApp.vzAppZoneAppsId, ")
                          .append("               device.deviceModel, firmware.firmwareId, firmware.firmwareName, firmware.mrNumber, ")
                          .append("               binary.binaryId, binary.binaryFileFileName, binary.binaryVersion, ")
                          .append("               binary.binaryFileSize, binary.previewFileFileName, ")
                          .append("               vzAppBinaryFirmware.binaryFirmwareId, vzAppBinaryFirmware.status, typeForStatus.typeValue, ")
                          .append("               vzAppBinaryFirmware.isPaid, vzAppBinaryFirmware.isActive ");
        return basicColumnsBuffer.toString();
    }

    private static String getBinaryFirmwareBasicFromSectionForQuery() {
        //note changing names in this section will affect other methods and their dependent methods,
        // like getBinaryFirmwareBasicColumnsForQuery
        StringBuffer fromBuffer = new StringBuffer();
        fromBuffer.append("               com.netpace.aims.model.masters.AimsDevic device,")
                  .append("               com.netpace.aims.model.system.AimsDeviceFirmware firmware,")
                  .append("               com.netpace.aims.model.application.AimsVZAppBinaries binary,")
                  .append("               com.netpace.aims.model.application.AimsVZAppBinaryFirmware vzAppBinaryFirmware,")
                  .append("               com.netpace.aims.model.application.AimsVZAppZoneApp vzAppZoneApp, ")
                  .append("               com.netpace.aims.model.core.AimsTypes typeForStatus ");
        return fromBuffer.toString();
    }

    private static String getBinaryFirmwareBasicWhereClause(String isActive) {
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append("               vzAppBinaryFirmware.binaryId = binary.binaryId  ")
                   .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                   .append("       AND     typeForStatus.typeId = vzAppBinaryFirmware.status")
                   .append("       AND     device.deviceId = firmware.deviceId")
                   .append("       AND     vzAppZoneApp.vzAppZoneAppsId = vzAppBinaryFirmware.vzAppZoneAppsId")
                   .append("       AND     vzAppZoneApp.vzAppZoneAppsId = :vzAppZoneAppsId");
        if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
            whereBuffer.append("       AND     vzAppBinaryFirmware.isActive = :isActive ");
        }
        return whereBuffer.toString();
    }

    public static List getVZAppBinaryFirmwarePhaseInfoList(Long vzAppZoneAppsId, String isActive, String mrNumberFormat, boolean useEllipse) throws HibernateException {

        List vzAppZoneBinaryFirmwarePhaseInfoList = null;
        VZAppBinaryFirmwarePhaseInfoVO binaryFirmwarePhaseInfo = null;

        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;

        String binaryFileFileName = "";
        String binaryVersion = "";
        String previewFileFileName = "";

        String baseResultsFileName = "";
        String otaResultsFileName = "";
        int ellipseLength = 10;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString.append(" SELECT ")
                       .append(getBinaryFirmwareBasicColumnsForQuery())
                       .append(" , ")
                       .append("    vzAppBinaryFirmware.baseTestedDate, vzAppBinaryFirmware.baseTestStatus, ")
                       .append("    vzAppBinaryFirmware.baseComments, vzAppBinaryFirmware.baseResultsFileFileName, ")
                       .append("    vzAppBinaryFirmware.otaTestedDate, vzAppBinaryFirmware.otaTestStatus, ")
                       .append("    vzAppBinaryFirmware.otaComments, vzAppBinaryFirmware.otaResultsFileFileName, ")
                       .append("    vzAppBinaryFirmware.stageMoved, vzAppBinaryFirmware.stageMovedDate, ")
                       .append("    vzAppBinaryFirmware.prodMoved, vzAppBinaryFirmware.prodMovedDate, ")
                       .append("    vzAppBinaryFirmware.lastUpdatedBy, vzAppBinaryFirmware.lastUpdatedDate ");
            queryString.append(" FROM ")
                       .append(getBinaryFirmwareBasicFromSectionForQuery());
            queryString.append(" WHERE ")
                       .append(getBinaryFirmwareBasicWhereClause(isActive))
                       .append("   ORDER BY device.deviceModel, firmware.mrNumber, vzAppBinaryFirmware.binaryFirmwareId ");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());

            if (!StringFuncs.isNullOrEmpty(isActive) && (isActive.equals("Y") || isActive.equals("N"))){
                query.setString("isActive", isActive);
			}

            itr = query.iterate();
            if(itr!=null) {
                vzAppZoneBinaryFirmwarePhaseInfoList =  new ArrayList();
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwarePhaseInfo = new VZAppBinaryFirmwarePhaseInfoVO();

                    binaryFirmwarePhaseInfo.setVzAppZoneAppsId((Long)userValues[0]);
                    binaryFirmwarePhaseInfo.setPhoneModel((String)userValues[1]);
                    binaryFirmwarePhaseInfo.setFirmwareId((Long)userValues[2]);
                    binaryFirmwarePhaseInfo.setFirmwareName((String)userValues[3]);
                    binaryFirmwarePhaseInfo.setMrNumber(mrNumberFormat+(Integer)userValues[4]);


                    binaryFirmwarePhaseInfo.setBinaryId((Long)userValues[5]);

                    //binaryFirmwarePhaseInfo.setBinaryFileFileName();
                    binaryFileFileName = (String)userValues[6];
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(binaryFileFileName)) {
                            binaryFirmwarePhaseInfo.setBinaryFileFileName(Utility.getEllipseString(ellipseLength,binaryFileFileName));
                        }
                        else {
                            binaryFirmwarePhaseInfo.setBinaryFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwarePhaseInfo.setBinaryFileFileName(binaryFileFileName);
                    }

                    binaryVersion = (String)userValues[7];
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(binaryVersion)) {
                            binaryFirmwarePhaseInfo.setBinaryVersion(Utility.getEllipseString(ellipseLength,binaryVersion));
                        }
                        else {
                            binaryFirmwarePhaseInfo.setBinaryVersion(null);
                        }
                    }
                    else {
                        binaryFirmwarePhaseInfo.setBinaryVersion(binaryVersion);
                    }

                    binaryFirmwarePhaseInfo.setBinaryFileSize((String)userValues[8]);

                    previewFileFileName = (String)userValues[9];
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(previewFileFileName)) {
                            binaryFirmwarePhaseInfo.setPreviewFileFileName(Utility.getEllipseString(ellipseLength, previewFileFileName));
                        }
                        else {
                            binaryFirmwarePhaseInfo.setPreviewFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwarePhaseInfo.setPreviewFileFileName(previewFileFileName);
                    }


                    binaryFirmwarePhaseInfo.setBinaryFirmwareId((Long)userValues[10]);
                    binaryFirmwarePhaseInfo.setBinaryFirmwareStatus((Long)userValues[11]);
                    binaryFirmwarePhaseInfo.setBinaryFirmwareStatusValue((String)userValues[12]);
                    binaryFirmwarePhaseInfo.setIsPaid((String)userValues[13]);
                    binaryFirmwarePhaseInfo.setIsActive((String)userValues[14]);

                    binaryFirmwarePhaseInfo.setBaseTestedDate(Utility.convertToString((Date)userValues[15], AimsConstants.DATE_FORMAT));
                    binaryFirmwarePhaseInfo.setBaseTestStatus((String)userValues[16]);
                    binaryFirmwarePhaseInfo.setBaseComments((String)userValues[17]);

                    baseResultsFileName = (String)userValues[18];
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(baseResultsFileName)) {
                            binaryFirmwarePhaseInfo.setBaseResultsFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, baseResultsFileName));
                        }
                        else {
                            binaryFirmwarePhaseInfo.setBaseResultsFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwarePhaseInfo.setBaseResultsFileFileName(baseResultsFileName);
                    }

                    binaryFirmwarePhaseInfo.setOtaTestedDate(Utility.convertToString((Date)userValues[19], AimsConstants.DATE_FORMAT));
                    binaryFirmwarePhaseInfo.setOtaTestStatus((String)userValues[20]);
                    binaryFirmwarePhaseInfo.setOtaComments((String)userValues[21]);

                    otaResultsFileName = (String)userValues[22];
                    if(useEllipse) {
                        if(!StringFuncs.isNullOrEmpty(otaResultsFileName)) {
                            binaryFirmwarePhaseInfo.setOtaResultsFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, otaResultsFileName));
                        }
                        else {
                            binaryFirmwarePhaseInfo.setOtaResultsFileFileName(null);
                        }
                    }
                    else {
                        binaryFirmwarePhaseInfo.setOtaResultsFileFileName(otaResultsFileName);
                    }

                    binaryFirmwarePhaseInfo.setStageMoved((String)userValues[23]);
                    binaryFirmwarePhaseInfo.setStageMovedDate(Utility.convertToString((Date)userValues[24], AimsConstants.DATE_FORMAT));

                    binaryFirmwarePhaseInfo.setProdMoved((String)userValues[25]);
                    binaryFirmwarePhaseInfo.setProdMovedDate(Utility.convertToString((Date)userValues[26], AimsConstants.DATE_FORMAT));

                    binaryFirmwarePhaseInfo.setLastUpdatedBy((String)userValues[27]);
                    binaryFirmwarePhaseInfo.setLastUpdatedDate(Utility.convertToString((Date)userValues[28], AimsConstants.DATE_FORMAT));

                    vzAppZoneBinaryFirmwarePhaseInfoList.add(binaryFirmwarePhaseInfo);

                }//end while
            }//end while
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppBinaryFirmwarePhaseInfoList");
            }
        }
        return vzAppZoneBinaryFirmwarePhaseInfoList;
    }//end getVZAppBinaryFirmwarePhaseInfoList

    public static List getVZAppBinaryFirmwareHistory(Long vzAppZoneAppsId, Long allianceId, Long firmwareId) throws HibernateException {

        List vzAppZoneBinaryFirmwarePhaseInfoList = null;
        VZAppBinaryFirmwarePhaseInfoVO binaryFirmwarePhaseInfo = null;

        Query query = null;
        Session session = null;
        StringBuffer queryString = null;
        Object[] userValues = null;
        Iterator itr = null;
        try {
            session=DBHelper.getInstance().getSession();
            queryString = new StringBuffer();
            queryString.append(" SELECT ");
            queryString.append(getBinaryFirmwareBasicColumnsForQuery());
            queryString.append(" , ")
                       .append("    vzAppBinaryFirmware.baseTestedDate, vzAppBinaryFirmware.baseTestStatus, ")
                       .append("    vzAppBinaryFirmware.baseComments, vzAppBinaryFirmware.baseResultsFileFileName, ")
                       .append("    vzAppBinaryFirmware.otaTestedDate, vzAppBinaryFirmware.otaTestStatus, ")
                       .append("    vzAppBinaryFirmware.otaComments, vzAppBinaryFirmware.otaResultsFileFileName ");
            queryString.append(" FROM ");
            queryString.append(getBinaryFirmwareBasicFromSectionForQuery());
            queryString.append("    , com.netpace.aims.model.application.AimsApp as app, com.netpace.aims.model.core.AimsAllianc as alliance ");
            queryString.append(" WHERE ");
            queryString.append(getBinaryFirmwareBasicWhereClause(null));
            queryString.append("       AND vzAppZoneApp.vzAppZoneAppsId = app.appsId ");
            queryString.append("       AND app.aimsAllianceId = alliance.allianceId ");
            queryString.append("       AND     vzAppBinaryFirmware.firmwareId = :firmwareId ");
            //Set 'Alliance Id' if User is 'Alliance User'
            if (allianceId != null) {
                queryString.append("    and	app.aimsAllianceId = :allianceId ");
            }
            queryString.append("   ORDER BY vzAppBinaryFirmware.binaryFirmwareId ");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            query.setLong("firmwareId", firmwareId.longValue());
             if (allianceId != null) {
                query.setLong("allianceId", allianceId.longValue());
            }

            itr = query.iterate();
            if(itr!=null) {
                vzAppZoneBinaryFirmwarePhaseInfoList =  new ArrayList();
                while(itr.hasNext()) {
                    userValues = (Object [])itr.next();
                    binaryFirmwarePhaseInfo = new VZAppBinaryFirmwarePhaseInfoVO();

                    binaryFirmwarePhaseInfo.setVzAppZoneAppsId((Long)userValues[0]);
                    binaryFirmwarePhaseInfo.setPhoneModel((String)userValues[1]);
                    binaryFirmwarePhaseInfo.setFirmwareId((Long)userValues[2]);
                    binaryFirmwarePhaseInfo.setFirmwareName((String)userValues[3]);
                    binaryFirmwarePhaseInfo.setMrNumber("MR-"+(Integer)userValues[4]);


                    binaryFirmwarePhaseInfo.setBinaryId((Long)userValues[5]);
                    //binaryFirmwarePhaseInfo.setBinaryFileFileName((String)userValues[6]);
                    if(!StringFuncs.isNullOrEmpty((String)userValues[6])) {
                        binaryFirmwarePhaseInfo.setBinaryFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String)userValues[6]));
                    }
                    else {
                        binaryFirmwarePhaseInfo.setBinaryFileFileName(null);
                    }

                    binaryFirmwarePhaseInfo.setBinaryVersion(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN, (String)userValues[7]));

                    binaryFirmwarePhaseInfo.setBinaryFileSize((String)userValues[8]);
                    //binaryFirmwarePhaseInfo.setPreviewFileFileName((String)userValues[9]);
                    if(!StringFuncs.isNullOrEmpty((String)userValues[9])) {
                        binaryFirmwarePhaseInfo.setPreviewFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String)userValues[9]));
                    }
                    else {
                        binaryFirmwarePhaseInfo.setPreviewFileFileName(null);
                    }

                    binaryFirmwarePhaseInfo.setBinaryFirmwareId((Long)userValues[10]);
                    binaryFirmwarePhaseInfo.setBinaryFirmwareStatus((Long)userValues[11]);
                    binaryFirmwarePhaseInfo.setBinaryFirmwareStatusValue((String)userValues[12]);
                    binaryFirmwarePhaseInfo.setIsPaid((String)userValues[13]);
                    binaryFirmwarePhaseInfo.setIsActive((String)userValues[14]);

                    binaryFirmwarePhaseInfo.setBaseTestedDate(Utility.convertToString((Date)userValues[15], AimsConstants.DATE_FORMAT));
                    binaryFirmwarePhaseInfo.setBaseTestStatus((String)userValues[16]);
                    binaryFirmwarePhaseInfo.setBaseComments((String)userValues[17]);
                    //binaryFirmwarePhaseInfo.setBaseResultsFileFileName((String)userValues[18]);
                    if(!StringFuncs.isNullOrEmpty((String)userValues[18])) {
                        binaryFirmwarePhaseInfo.setBaseResultsFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String)userValues[18]));
                    }
                    else {
                        binaryFirmwarePhaseInfo.setBaseResultsFileFileName(null);
                    }

                    binaryFirmwarePhaseInfo.setOtaTestedDate(Utility.convertToString((Date)userValues[19], AimsConstants.DATE_FORMAT));
                    binaryFirmwarePhaseInfo.setOtaTestStatus((String)userValues[20]);
                    binaryFirmwarePhaseInfo.setOtaComments((String)userValues[21]);
                    //binaryFirmwarePhaseInfo.setOtaResultsFileFileName((String)userValues[22]);
                    if(!StringFuncs.isNullOrEmpty((String)userValues[22])) {
                        binaryFirmwarePhaseInfo.setOtaResultsFileFileName(Utility.getEllipseString(AimsConstants.ELLIPSE_STR_LEN,(String)userValues[22]));
                    }
                    else {
                        binaryFirmwarePhaseInfo.setOtaResultsFileFileName(null);
                    }

                    vzAppZoneBinaryFirmwarePhaseInfoList.add(binaryFirmwarePhaseInfo);

                }//end while
            }//end while
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getVZAppBinaryFirmwarePhaseInfoList");
            }
        }
        return vzAppZoneBinaryFirmwarePhaseInfoList;
    }

    public static int deleteAimsVZAppBinaries(Long vzAppZoneAppsId, Long binaryId) throws HibernateException {
        int delCount = 0;
        StringBuffer delQueryBuffer = new StringBuffer();
        Session session = null;
        Transaction tx = null;
        try {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            //delete binaryfirmwares first
            VZAppZoneApplicationManager.deleteAimsVZAppBinaryFirmwaresByBinaryId(session, vzAppZoneAppsId, binaryId);
            delQueryBuffer.append("from com.netpace.aims.model.application.AimsVZAppBinaries as vzAppBinary ")
                          .append(" where vzAppBinary.vzAppZoneAppsId = :vzAppZoneAppsId ")
                          .append(" and vzAppBinary.binaryId = :binaryId ");

            delCount = session.delete(delQueryBuffer.toString(), new Long[]{vzAppZoneAppsId, binaryId}, new Type[]{new LongType(), new LongType()} );
            tx.commit();
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("SESSION closed in deleteAimsVZAppBinaries(Long vzAppZoneAppsId, Long binaryId) method.\t rows Deleted:"+delCount);
            }
        }
        return delCount;
    }//deleteAimsVZAppBinaries

    public static List getAllActiveAimsVZAppBinaryFirmwaresList(Long vzAppZoneAppsId) throws HibernateException {
        List activeAimsVZAppBinaryFirmwaresList = null;
        Query query = null;
        Session	session	=	null;
        try {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            /*
                queryString
                    .append("	SELECT  vzAppBinaryFirmware ")
                    .append("   from com.netpace.aims.model.application.AimsVZAppBinaryFirmware as vzAppBinaryFirmware ")
                    .append("   where vzAppBinaryFirmware.vzAppZoneAppsId	= :vzAppZoneAppsId  ")
                    .append("   and vzAppBinaryFirmware.isActive	= :isActive  ")
                    .append(" order by	vzAppBinaryFirmware.binaryFirmwareId	");
            */

            queryString
                .append("	SELECT  vzAppBinaryFirmware ")
                .append("   from com.netpace.aims.model.application.AimsVZAppBinaryFirmware as vzAppBinaryFirmware, ")
                .append("        com.netpace.aims.model.masters.AimsDevic device,")
                .append("        com.netpace.aims.model.system.AimsDeviceFirmware firmware")
                .append("   where vzAppBinaryFirmware.vzAppZoneAppsId	= :vzAppZoneAppsId  ")
                .append("       AND     vzAppBinaryFirmware.firmwareId = firmware.firmwareId ")
                .append("       AND     device.deviceId = firmware.deviceId")
                .append("   and vzAppBinaryFirmware.isActive	= :isActive  ")
                .append(" order by	device.deviceModel, firmware.mrNumber, vzAppBinaryFirmware.binaryFirmwareId	");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            query.setString("isActive", "Y");

            activeAimsVZAppBinaryFirmwaresList  = query.list();
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getAllActiveAimsVZAppBinaryFirmwaresList(Long vzAppZoneAppsId)");
            }
        }

        return activeAimsVZAppBinaryFirmwaresList;
    }

    public static int deleteAimsVZAppBinaryFirmwaresByBinaryId(Session session, Long vzAppZoneAppsId, Long binaryId) throws HibernateException {
        int delCount = 0;
        StringBuffer delQueryBuffer = new StringBuffer();
        try {
            delQueryBuffer.append("from com.netpace.aims.model.application.AimsVZAppBinaryFirmware as vzAppBinaryFirmware ")
                          .append(" where vzAppBinaryFirmware.vzAppZoneAppsId = :vzAppZoneAppsId ")
                          .append(" and vzAppBinaryFirmware.binaryId = :binaryId ");
            delCount = session.delete(delQueryBuffer.toString(), new Long[]{vzAppZoneAppsId, binaryId}, new Type[]{new LongType(), new LongType()} );
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            log.debug("SESSION Will	be Closed	by the calling class for deleteAimsVZAppBinaryFirmwaresByBinaryId(Session session, Long vzAppZoneAppsId, Long binaryId)	method.\t rows Deleted:"+delCount);
        }
        return delCount;
    }//deleteAimsVZAppBinaries

    public static List getAimsVZAppBinariesListByBinaryIds(Long vzAppZoneAppsId, Long[] binaryIds) throws HibernateException {
        List aimsVZAppBinaries = null;
        Query query = null;
        Session	session	=	null;
        try {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	SELECT  vzAppBinary ")
                .append("   from com.netpace.aims.model.application.AimsVZAppBinaries as vzAppBinary ")
                .append("   where vzAppBinary.vzAppZoneAppsId	= :vzAppZoneAppsId  ")
                .append("   and vzAppBinary.binaryId in ( :binaryIds )");

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            query.setParameterList("binaryIds", binaryIds);

            aimsVZAppBinaries  = query.list();
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getAimsVZAppBinariesListByBinaryIds");
            }
        }

        return aimsVZAppBinaries;
    }//end getAimsVZAppBinariesListByBinaryIds

    public static AimsVZAppBinaries getAimsVZAppBinaryByBinaryId(Long vzAppZoneAppsId, Long binaryId, Long allianceId) throws HibernateException {
        AimsVZAppBinaries aimsVZAppBinary = null;
        Query query = null;
        Session	session	=	null;
        try {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	SELECT  vzAppBinary ")
                .append("   from    com.netpace.aims.model.application.AimsVZAppBinaries as vzAppBinary, ")
                .append("           com.netpace.aims.model.application.AimsApp as app, ")
                .append("           com.netpace.aims.model.core.AimsAllianc as alliance ")
                .append("   where   vzAppBinary.vzAppZoneAppsId	= :vzAppZoneAppsId  ")
                .append("           and app.appsId =  vzAppBinary.vzAppZoneAppsId ")
                .append("           and app.aimsAllianceId = alliance.allianceId ")
                .append("           and vzAppBinary.binaryId =  :binaryId ");
            //Set	'Alliance	Id'	if User	is 'Alliance User' (allianceId=null).
            if (allianceId != null) {
                queryString.append("    and	app.aimsAllianceId	=	" + allianceId);
            }

            query = session.createQuery(queryString.toString());
            query.setLong("vzAppZoneAppsId", vzAppZoneAppsId.longValue());
            query.setLong("binaryId", binaryId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();) {
                aimsVZAppBinary = (AimsVZAppBinaries) it.next();
            }
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getAimsVZAppBinaryByBinaryId");
            }
        }

        return aimsVZAppBinary;
    }//end getAimsVZAppBinaryByBinaryId

    public static void saveIntertekServiceResponse(AimsVzappIntertekWsLog intertekWsLog, String xmlStr) throws HibernateException{
    	Session session=null;
    	Transaction trx=null;
    	ByteArrayInputStream bis=null;
    	long bytesWrote = 0;
    	try {
    		bis=new ByteArrayInputStream(xmlStr.getBytes());
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			intertekWsLog.setSubmitXml(Hibernate.createClob(" "));
			session.save(intertekWsLog);
			session.flush();
			session.refresh(intertekWsLog, LockMode.UPGRADE);

			bytesWrote =LobUtils.writeToOraClob((CLOB) intertekWsLog.getSubmitXml(), bis);
			trx.commit();
		} catch (HibernateException e) {
			log.error(e,e);
			trx.rollback();
		} catch (SQLException e) {
			log.error(e,e);
			trx.rollback();
		} catch (IOException e) {
			log.error(e,e);
			trx.rollback();
		} finally {
			session.close();
			if (bis!=null){
				try {
					bis.close();
				} catch (IOException e) {
					log.error(e,e);
				}
			}
		}
    }

     public static void saveMPortalXMLLog(AimsVZAppMPortalXMLLog mPortalXMLLog, String xmlStr) throws HibernateException{
    	Session session=null;
    	Transaction trx=null;
    	ByteArrayInputStream bis=null;
    	long bytesWrote = 0;
    	try {
    		bis=new ByteArrayInputStream(xmlStr.getBytes());
			session=DBHelper.getInstance().getSession();
			trx=session.beginTransaction();
			mPortalXMLLog.setSubmitXml(Hibernate.createClob(" "));
			session.save(mPortalXMLLog);
			session.flush();
			session.refresh(mPortalXMLLog, LockMode.UPGRADE);

			bytesWrote =LobUtils.writeToOraClob((CLOB) mPortalXMLLog.getSubmitXml(), bis);
			trx.commit();
		} catch (HibernateException e) {
			log.error(e,e);
			trx.rollback();
		} catch (SQLException e) {
			log.error(e,e);
			trx.rollback();
		} catch (IOException e) {
			log.error(e,e);
			trx.rollback();
		} finally {
			session.close();
			if (bis!=null){
				try {
					bis.close();
				} catch (IOException e) {
					log.error(e,e);
				}
			}
		}
    }

    public static HashMap getVzAppAllianceAndContact(Long appId) throws HibernateException{
    	Session session=null;
    	HashMap infoMap=new HashMap();
    	try {
    		StringBuffer sb=new StringBuffer();
    		sb.append(" select app, alliance, contact from AimsApp app, AimsAllianc alliance, AimsUser user, AimsContact contact ");
    		sb.append(" where app.aimsAllianceId = alliance.allianceId ");
    		sb.append(" and alliance.aimsUserByAdminUserId = user.userId ");
    		sb.append(" and user.aimsContactId = contact.contactId ");
    		sb.append(" and app.appsId = :id ");
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(sb.toString());
			query.setLong("id", appId.longValue());
			List list=query.list();
			for (int i=0; i<list.size(); i++){
				Object[] obj=(Object[])list.get(i);
				infoMap.put("app", obj[0]);
				infoMap.put("alliance", obj[1]);
				infoMap.put("contact", obj[2]);
			}
		} catch (HibernateException e) {
			log.error(e,e);
		} finally {
			session.close();
		}
		return infoMap;
    }

    public static HashMap getVzAppAllianceMap(Long appId) throws HibernateException{
    	Session session=null;
    	HashMap infoMap=new HashMap();
    	try {
    		StringBuffer sb=new StringBuffer();
    		sb.append(" select app, vzApp, alliance from AimsApp app, AimsVZAppZoneApp vzApp, AimsAllianc alliance ");
    		sb.append(" where app.aimsAllianceId = alliance.allianceId ");
    		sb.append(" and vzApp.vzAppZoneAppsId = app.appsId ");
    		sb.append(" and app.appsId = :id ");
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(sb.toString());
			query.setLong("id", appId.longValue());
			List list=query.list();
			for (int i=0; i<list.size(); i++){
				Object[] obj=(Object[])list.get(i);
				infoMap.put("app", obj[0]);
				infoMap.put("vzApp", obj[1]);
				infoMap.put("alliance", obj[2]);
			}
		} catch (HibernateException e) {
			log.error(e,e);
		} finally {
			session.close();
		}
		return infoMap;
    }

    public static List getBinaryFirwarebyIds(Long[] ids) throws HibernateException{
    	Session session=null;
    	List binaryFirwares=new ArrayList();
    	try {
    		StringBuffer sb=new StringBuffer();
    		sb.append(" select bf.binaryFirmwareId, d.deviceModel, f.firmwareName, f.mrNumber, d.assetType, typeForAssetType.typeValue");
    		sb.append(" from AimsVZAppBinaryFirmware bf, AimsDeviceFirmware f, AimsDevic d, AimsTypes typeForAssetType");
            sb.append(" where bf.firmwareId = f.firmwareId ");
    		sb.append(" and f.deviceId = d.deviceId ");
            sb.append(" and typeForAssetType.typeId (+) = d.assetType ");//get asset type value for devices
            sb.append(" and bf.binaryFirmwareId in (:ids)");
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(sb.toString());
			query.setParameterList("ids", ids);
			List list=query.list();
			for (int i=0; i<list.size(); i++){
				Object[] o=(Object[])list.get(i);
				VZAppBinaryFirmwareInfoVO vo = new VZAppBinaryFirmwareInfoVO();
				vo.setBinaryFirmwareId((Long)o[0]);
				vo.setPhoneModel((String)o[1]);
				vo.setFirmwareName((String)o[2]);
				vo.setMrNumber((String)"MR-"+o[3]);
                vo.setAssetType((Long)o[4]);    //set asset type and asset type value
                vo.setAssetTypeValue((String)o[5]);
                binaryFirwares.add(vo);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return binaryFirwares;
    }
    public static boolean checkIntertekResubmitStatus(String binaryFirmwareIdsStr) throws HibernateException{
    	Session session=null;
    	boolean flag=true;
    	try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(" select log.submitStatus from AimsVzappIntertekWsLog log where log.submitStatus='Y' and log.binaryFirmwareIds=:ids ");
			query.setString("ids", binaryFirmwareIdsStr);
			List list=query.list();
			if (list.size() > 0){
				flag=false;
			}
		} catch (HibernateException e) {
			log.error(e,e);
		} finally {
			session.close();
		}
		return flag;
    }
    public static boolean checkMPortalResubmitStatus(String binaryFirmwareIdsStr, String binaryIdsStr, String xmlType) throws HibernateException{
    	Session session=null;
    	boolean flag=true;
    	try {
			session=DBHelper.getInstance().getSession();
			Query query=session.createQuery(" select log.submitStatus from AimsVZAppMPortalXMLLog log where log.submitStatus='Y' and log.binaryFirmwareIds=:binaryFirmwareIds and log.binaryIds=:binaryIds and log.xmlType=:xmlType");
			query.setString("binaryFirmwareIds", binaryFirmwareIdsStr);
            query.setString("binaryIds", binaryIdsStr);
            query.setString("xmlType", xmlType);
            List list=query.list();
			if (list.size() > 0){
				flag=false;
			}
		} catch (HibernateException e) {
			log.error(e,e);
		} finally {
			session.close();
		}
		return flag;
    }

    public static void saveBinaryFields(String userId, AimsVZAppBinaries aimsBinary, Long documentFileTempFileId)
                            throws AimsException, HibernateException, Exception {
        Session session = null;
        Transaction tx = null;
        Connection ConOra = null;
        Long binaryId = null;
        try {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            binaryId = aimsBinary.getBinaryId();

            session.update(aimsBinary);
            session.flush();

            ConOra = session.connection();

            TempFilesManager.copyImageFromTemp(ConOra, documentFileTempFileId, binaryId, userId, ManageApplicationsConstants.VZAPPZONE_DOCUMENT_FILE_BLOB_DB_INFO);
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK  IN saveBinaryFields()");
            e.printStackTrace();
            throw e;
        }

        catch (Exception ex) {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK  IN saveBinaryFields()");
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("SESSION  CLOSED IN   saveBinaryFields()");
            }
        }//end finally
    }

    public static AimsContact getAimsContactById(Long contactId) throws HibernateException {
    	AimsContact aimsContace = null;
        Query query = null;
        Session	session	=	null;
        try {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString
                .append("	SELECT  contact ")
                .append("   from    com.netpace.aims.model.core.AimsContact as contact, ")
                .append("   where   contact.contactId	= :contactId  ");
            

            query = session.createQuery(queryString.toString());
            query.setLong("contactId", contactId.longValue());

            List list=query.list();
            if(list!=null && list.size()>0){
            	aimsContace=(AimsContact) list.get(0);
            }
           
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            if(session!=null) {
                session.close();
                log.debug("Session closed in VZAppZoneApplicationManager.getAimsContactById");
            }
        }

        return aimsContace;
    }
}
