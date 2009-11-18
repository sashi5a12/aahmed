package com.netpace.aims.bo.application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import oracle.jdbc.driver.OracleCallableStatement;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.application.WapApplicationHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppPhase;
import com.netpace.aims.model.application.AimsVzwTestingPhase;
import com.netpace.aims.model.application.AimsWapApp;
import com.netpace.aims.model.application.AimsWapAppsVersion;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;

/**
 * This	class	is responsible for getting the BO	for	Wap	Application.
 * It	has	static methods for getting the Wap Application.
 * @author Fawad Sikandar
 */
public class WapApplicationManager
{

    static Logger log = Logger.getLogger(WapApplicationManager.class.getName());

    /*
     * This function fetches the WAP Application. Called when user wants to View/Edit/Clone the Application.
     */
    public static HashMap getWapApp(Long appsId, Long allianceId) throws AimsException, HibernateException
    {
        Session session = null;
        HashMap appWap = new HashMap();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       apps, wapapps ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp as apps, ")
                .append("       com.netpace.aims.model.application.AimsWapApp as wapapps ")
                .append("   where ")
                .append("       apps.appsId = wapapps.wapAppsId ")
                .append("       and apps.appsId = :appsId ");

            //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
            if (allianceId != null)
                queryStringBuffer.append("      and apps.aimsAllianceId = :allianceId");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Object[] appValues = null;

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                appValues = (Object[]) it.next();
                appWap.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
                appWap.put("AimsWapApp", (AimsWapApp) appValues[1]); // AimsWapApp
            }

            if (appWap.isEmpty())
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
        }

        return appWap;
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

            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).
            if (allianceId != null)
            {
                queryStringBuffer.append("      and app.aimsAllianceId = :allianceId ");
                queryStringBuffer.append("      and journalEntry.journalType = :journalType ");
            }

            queryStringBuffer.append("  order by    journalEntry.createdDate desc ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", applicationId.longValue());
            if (allianceId != null)
            {
                query.setLong("allianceId", allianceId.longValue());
                query.setString("journalType", AimsConstants.JOURNAL_TYPE_PUBLIC);
            }

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
     * This method gets all the Wap Versions (generated in the 'Pending DCR' status)
     */
    public static Collection getWapAppVersions(Long wapAppsId) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer.append(" select   distinct wapAppsVersion.oldVersion ");
            queryStringBuffer.append(" from     com.netpace.aims.model.application.AimsWapAppsVersion wapAppsVersion ");
            queryStringBuffer.append(" where    wapAppsVersion.wapAppsId = :wapAppsId ");
            queryStringBuffer.append(" order    by old_version desc ");
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("wapAppsId", wapAppsId.longValue());
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
            query.append("from com.netpace.aims.model.application.AimsWapLicenseType as licenseType ");
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
                .append("       wapApp.vendorProductCode ")
                .append("from ")
                .append("       com.netpace.aims.model.application.AimsWapApp wapApp, ")
                .append("       com.netpace.aims.model.application.AimsAppLite app, ")
                .append("where  ")
                .append("       app.appsId = wapApp.wapAppsId ")
                .append("       and app.aimsAllianceId =  :allianceId ")
                .append("       and wapApp.vendorProductCode =  :vendorProductCode ");

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
     * This method saves/updated the WAP Application
     */
    public static void saveOrUpdateWapApplication(
        AimsApp aimsApp,
        AimsWapApp aimsWapApp,
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

            HashMap newValuesMap = WapApplicationHelper.getValuesMapForVersion(aimsApp, aimsWapApp, dateFormat);

            //Versions are updated if Application being saved while in Pending DCR Status
            //OR
            //Version is updated when Application moves from 'Pending DCR' to 'Submitted DCR' Status.
            if (((oldStatus.longValue() == aimsApp.getAimsLifecyclePhaseId().longValue())
                && (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_PENDING_DCR_ID.longValue()))
                || ((oldStatus.longValue() != aimsApp.getAimsLifecyclePhaseId().longValue())
                    && (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.PHASE_SUBMITTED_DCR_ID.longValue())))
                aimsWapApp.setPendingDcrVersion(
                    updateVersions(session, oldValuesMap, newValuesMap, aimsWapApp.getPendingDcrVersion(), aimsWapApp.getWapAppsId(), userId));

            /*
                *SAVING APP IN DATABASE
            */

            if (aimsApp.getAppsId() == null) //Saving
            {
                session.saveOrUpdate(aimsApp);
                aimsWapApp.setWapAppsId(aimsApp.getAppsId());
                session.save(aimsWapApp);
                newApp = true;

            }
            else //Updating
                {
                session.saveOrUpdate(aimsApp);
                aimsWapApp.setWapAppsId(aimsApp.getAppsId());
                session.update(aimsWapApp);
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
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.WAP_PLATFORM_ID);

            //Copying Images From Temp Table (if present)
            TempFilesManager.copyImageFromTemp(ConOra, productLogo, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_PRODUCT_LOGO_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, productIcon, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_PRODUCT_ICON_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, screenJpeg, aimsApp.getAppsId(), userId, ManageApplicationsConstants.SCREEN_JPEG_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, userGuide, aimsApp.getAppsId(), userId, ManageApplicationsConstants.USER_GUIDE_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, faqDoc, aimsApp.getAppsId(), userId, ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, presentation, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_PRESENTATION_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appMediumLargeImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_APP_IMG_MEDIUM_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appQVGAPotraitImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_APP_IMG_POTRAIT_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appQVGALandscapeImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_APP_IMG_LANDSCAPE_BLOB_DB_INFO);

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
            log.debug("ROLLED BACK  IN saveOrUpdateWapApplication()");
            e.printStackTrace();
            throw e;
        }

        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK  IN saveOrUpdateWapApplication()");
            ex.printStackTrace();
            throw ex;
        }

        finally
        {
            session.close();
            log.debug("SESSION  CLOSED IN   saveOrUpdateWapApplication()");
        }
    }

    /*
     * This method updates the versions when application is in 'PENDING_DCR'. This method also returns
     * back the updated current version.
     */
    public static String updateVersions(Session session, HashMap oldValuesMap, HashMap newValuesMap, String currentDcrVersion, Long wapAppsId, String currUser)
        throws HibernateException, Exception
    {
        AimsWapAppsVersion aimsWapsAppVersion = null;
        boolean isUpdated = false;
        String updatedVersion = currentDcrVersion;
        String newDcrVersion = (new Double(Double.parseDouble(currentDcrVersion) + 1)).toString();
        Collection versionDifferences = getVersionDifferences(oldValuesMap, newValuesMap, currentDcrVersion, newDcrVersion, wapAppsId);

        for (Iterator it = versionDifferences.iterator(); it.hasNext();)
        {
            aimsWapsAppVersion = (AimsWapAppsVersion) it.next();
            aimsWapsAppVersion.setCreatedBy(currUser);
            session.save(aimsWapsAppVersion);
            isUpdated = true;
        }

        if (isUpdated)
            updatedVersion = newDcrVersion;

        return updatedVersion;
    }

    /*
     * This method returns back a collection of AimsWapAppsVersion specifying the differences
     */
    public static Collection getVersionDifferences(HashMap oldValuesMap, HashMap newValuesMap, String currentDcrVersion, String newDcrVersion, Long wapAppsId)
    {
        AimsWapAppsVersion aimsWapsAppVersion = null;
        Collection versionDifferences = new ArrayList();
        String oldValueStr = "";
        String newValueStr = "";

        for (Iterator it = oldValuesMap.keySet().iterator(); it.hasNext();)
        {
            String key = (String) it.next();
            oldValueStr = "";
            newValueStr = "";

            if (oldValuesMap.get(key) != null)
                oldValueStr = oldValuesMap.get(key).toString();
            if (newValuesMap.get(key) != null)
                newValueStr = newValuesMap.get(key).toString();

            if (!newValueStr.equals(oldValueStr))
            {
                aimsWapsAppVersion = new AimsWapAppsVersion();
                aimsWapsAppVersion.setWapAppsId(wapAppsId);
                aimsWapsAppVersion.setObjectName("TEMP");
                aimsWapsAppVersion.setFieldName(key);
                aimsWapsAppVersion.setOldVersion(currentDcrVersion);
                aimsWapsAppVersion.setNewVersion(newDcrVersion);
                aimsWapsAppVersion.setOldValue(oldValueStr);
                aimsWapsAppVersion.setNewValue(newValueStr);
                aimsWapsAppVersion.setCreatedDate(new Date());
                aimsWapsAppVersion.setCreatedBy("system");
                versionDifferences.add(aimsWapsAppVersion);
            }
        }

        return versionDifferences;
    }

    /*
     * This is the place where Testing Collection is filled (ONLY ONCE) on change of status to Submit.
     */
    public static void addNewPhases(Session session, Long appsId) throws HibernateException
    {
        try
        {
            AimsDevic aimsDevice = AimsDevicesManager.getDevice(AimsConstants.NON_EXISTANT_DEVICE_ID);
            for (java.util.Iterator itr = AimsApplicationsManager.getTestingPhases(AimsConstants.WAP_PLATFORM_ID).iterator(); itr.hasNext();)
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
     * This methods returns a collection of AimsWapAppsVersion based on oldVersion and new Version provided.
     * This is called when the user wants to view the differences between two versions
     */
    public static Collection getWapAppVersionInformation(Long wapAppsId, String oldVersion, String newVersion) throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer.append(" select wapAppsVersion ");
            queryStringBuffer.append(" from com.netpace.aims.model.application.AimsWapAppsVersion wapAppsVersion ");
            queryStringBuffer.append(" where wapAppsVersion.wapAppsId = :wapAppsId ");
            queryStringBuffer.append(" and wapAppsVersion.oldVersion = :oldVersion ");
            queryStringBuffer.append(" and wapAppsVersion.newVersion = :newVersion ");
            queryStringBuffer.append(" order by field_name ");
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("wapAppsId", wapAppsId.longValue());
            query.setString("oldVersion", oldVersion);
            query.setString("newVersion", newVersion);
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

    /*
     * This methods returns a collection of AimsWapAppsVersion based on 'wapAppsId' and 'clonedFromId'.
     * This is called when the user wants to view the differences between the 'cloned' and 'cloned from' application.
     */
    public static Collection getDifferenceOfClonedApp(Long wapAppsId, Long clonedFromId, Long currentUserAllianceId, String dateFormat)
        throws HibernateException, Exception
    {
        Collection collection = null;
        Session session = null;
        AimsApp aimsApp = null;
        AimsWapApp aimsWapApp = null;
        HashMap appWap = null;
        HashMap oldValuesMap = null;
        HashMap newValuesMap = null;
        try
        {
            session = DBHelper.getInstance().getSession();

            try
            {
                appWap = getWapApp(clonedFromId, currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                return collection;
            }
            aimsApp = (AimsApp) appWap.get("AimsApp");
            aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");
            oldValuesMap = WapApplicationHelper.getValuesMapForVersion(aimsApp, aimsWapApp, dateFormat);

            appWap = null;
            try
            {
                appWap = getWapApp(wapAppsId, currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                return collection;
            }

            aimsApp = (AimsApp) appWap.get("AimsApp");
            aimsWapApp = (AimsWapApp) appWap.get("AimsWapApp");
            newValuesMap = WapApplicationHelper.getValuesMapForVersion(aimsApp, aimsWapApp, dateFormat);

            collection = getVersionDifferences(oldValuesMap, newValuesMap, "", "", new Long(0));
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

    public static int getPageCountOfSubmittedDCRApps(StringBuffer queryStringBuffer) throws HibernateException
    {
        int rows = 0;
        Collection collection = null;
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            collection = session.find(queryStringBuffer.toString());
            log.debug("No   of apps returned:   " + collection.size());
            rows = collection.size();
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
        return rows;

    }

    public static Collection getSubmittedDCRApps(StringBuffer queryStringBuffer, int PAGE_LENGTH, int page_no) throws HibernateException
    {
        Session session = null;
        Collection collection = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryStringBuffer.toString());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (page_no - 1));
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

    public static boolean moveToPendingDCR(Long appsId, Long allianceId, String submitType, String username) throws HibernateException
    {
        System.out.println("\n\nApplication Moved to PENDING DCR Status");

        CallableStatement statement = null;
        Connection ConOra = null;
        Session session = null;
        boolean returnValue = false;

        System.out.println("\n\nAm appsId: " + appsId);
        System.out.println("allianceId: " + allianceId);
        System.out.println("submitType: " + submitType);
        System.out.println("username: " + username);

        try
        {
            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();

            statement = ConOra.prepareCall("call AIMS_INFOSPACE_PKG.rollback_infospace_log(?,?,?,?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setLong(2, allianceId.longValue());
            statement.setString(3, submitType);
            statement.setString(4, username);
            statement.registerOutParameter(5, java.sql.Types.VARCHAR);
            statement.execute();

            String resultString = ((OracleCallableStatement) statement).getString(5);

            if ((resultString != null) && (resultString.equals("Y")))
                returnValue = true;
        }
        catch (Exception ex)
        {}
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();

                session.close();
            }
            catch (Exception ignore)
            {}
        }

        return returnValue;

    }

    //*****************************
    //START OF Query Part   for SELECT
    public static StringBuffer getQueryForColumnsToDisplay()
    {
        StringBuffer queryStringBuffer = new StringBuffer();
        queryStringBuffer
            .append("select distinct    ")
            .append("       application.appsId, alliance.companyName, ")
            .append("       application.title, application.submittedDate,   ")
            .append("       lifecycle.phaseId, lifecycle.phaseName, ")
            .append("       alliance.allianceId ");
        return queryStringBuffer;
    }
    //END   OF Query Part   for SELECT
    //********************************

    //********************************
    //START OF Query Part   for FROM and WHERE
    public static StringBuffer getNormalQueryForGetApps()
    {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer
            .append("from ")
            .append("           com.netpace.aims.model.application.AimsAppLite application, ")
            .append("           com.netpace.aims.model.core.AimsPlatform platform, ")
            .append("           com.netpace.aims.model.core.AimsAllianc alliance, ")
            .append("           com.netpace.aims.model.application.AimsLifecyclePhase lifecycle ")
            .append("where  ")
            .append("           application.aimsAllianceId = alliance.allianceId ")
            .append("           and application.aimsPlatformId = platform.platformId ")
            .append("           and application.aimsLifecyclePhaseId = lifecycle.phaseId ")
            .append(" and application.aimsLifecyclePhaseId  = " + AimsConstants.PHASE_SUBMITTED_DCR_ID)
            .append(" and platform.platformId = " + AimsConstants.WAP_PLATFORM_ID.toString())
            .append(" and application.aimsLifecyclePhaseId != " + AimsConstants.SAVED_ID);

        return queryStringBuffer;
    }
    //END   OF Query Part   for FROMand WHERE
    //***************************************

    //START OF Query Part   for WHERE
    //********************************

    public static StringBuffer getFilterQueryForGetApps(String filterField, String fieldText)
    {
        StringBuffer queryStringBuffer = new StringBuffer();

        if ((filterField != null) && (filterField.length() > 0))
        {
            MessageFormat filterString = new MessageFormat(" and upper({0}) like ''%{1}%'' ");
            Object[] objs = { filterField, StringFuncs.sqlEscape(fieldText.toUpperCase())};
            queryStringBuffer.append(filterString.format(objs));
        }

        return queryStringBuffer;
    }
    //END   OF Query Part   for WHERE
    //********************************

    //START OF Query Part   for ORDER   BY
    //********************************
    public static StringBuffer getOrderByQueryForGetApps(String sortField, String sortOrder)
    {
        StringBuffer queryStringBuffer = new StringBuffer();

        MessageFormat orderString = new MessageFormat(" order by {0} {1} ");

        if ((sortField == null) || (sortField.length() < 1))
        {
            sortField = "application.title";
            sortOrder = "asc";
        }

        Object[] objs = { sortField, sortOrder };

        queryStringBuffer.append(orderString.format(objs));
        return queryStringBuffer;
    }

    //END   OF Query Part   for ORDER   BY
    //********************************

    //********************************
    //START OF get applications to image Upload Query Part   for FROM and WHERE
    public static StringBuffer getQueryForImageUploadApps(Long alliance_id)
    {
        StringBuffer queryStringBuffer = new StringBuffer();

        String imageUploadAppPhaseIds = AimsConstants.PHASE_SUBMITTED_DCR_ID + ", " +
                                        AimsConstants.PHASE_TESTING_PASSED_ID +", "  +
                                        AimsConstants.PHASE_PUBLICATION_READY_ID +", "  +
                                        AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID;

        queryStringBuffer
            .append("from ")
            .append("           com.netpace.aims.model.application.AimsAppLite application, ")
            .append("           com.netpace.aims.model.application.AimsWapApp wapApplication, ")
            .append("           com.netpace.aims.model.core.AimsPlatform platform, ")
            .append("           com.netpace.aims.model.core.AimsAllianc alliance, ")
            .append("           com.netpace.aims.model.application.AimsLifecyclePhase lifecycle ")
            .append("where  ")
            .append("           alliance.allianceId = application.aimsAllianceId ")
            .append("           and wapApplication.wapAppsId = application.appsId ")
            .append("           and application.aimsPlatformId = platform.platformId ")
            .append("           and application.aimsLifecyclePhaseId = lifecycle.phaseId ")
            .append(" and application.aimsLifecyclePhaseId  in (" +imageUploadAppPhaseIds+") ")
            .append(" and platform.platformId = " + AimsConstants.WAP_PLATFORM_ID.toString())
            .append(" and application.aimsLifecyclePhaseId != " + AimsConstants.SAVED_ID)
            .append(" and ( (wapApplication.wapFtpFlag is null) ")
            .append("       or ( (wapApplication.wapFtpFlag = '"+ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED_AGAIN+"')")
            .append("               and  (wapApplication.wapFtpFlag != '"+ManageApplicationsConstants.WAP_IMAGES_FTP_TRANSFERED+"')")
            .append("           )");
        if(alliance_id != null ) {
            queryStringBuffer.append(" and alliance.allianceId = "+alliance_id);
        }
        return queryStringBuffer;
    }//end getQueryForImageUploadApps

    public static StringBuffer getMainQueryForImageUploadApps(Long alliance_id, String sortField, String sortOrder)
    {
        StringBuffer mainQuery = new StringBuffer();
        mainQuery.append(WapApplicationManager.getQueryForColumnsToDisplay());
        mainQuery.append(WapApplicationManager.getQueryForImageUploadApps(alliance_id));
        mainQuery.append(WapApplicationManager.getOrderByQueryForGetApps(sortField, sortOrder));
        return mainQuery;
    }


    public static int getPageCountOfImageUploadApps(StringBuffer imageUploadAppsQuery) throws HibernateException
    {
        int rows = 0;
        Session session = null;
        Collection collection = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            collection = session.find(imageUploadAppsQuery.toString());
            log.debug("No   of apps returned:   " + collection.size());
            rows = collection.size();            
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
        return rows;
    }//end getPageCountOfImageUploadApps

    public static Collection getImageUploadApps(StringBuffer imageUploadAppsQuery, int PAGE_LENGTH, int page_no) throws HibernateException
    {
        Session session = null;
        Collection collection = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(imageUploadAppsQuery.toString());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (page_no - 1));
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
    }//end getImageUploadApps

    public static void saveWapLogoImages(AimsApp aimsApp,
                                         String userId,
                                         Long appMediumLargeImage,
                                         Long appQVGAPotraitImage,
                                         Long appQVGALandscapeImage)
            throws Exception, HibernateException
    {

        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;


        try
        {
            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();

            //Copying Images From Temp Table (if present)
            TempFilesManager.copyImageFromTemp(ConOra, appMediumLargeImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_APP_IMG_MEDIUM_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appQVGAPotraitImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_APP_IMG_POTRAIT_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, appQVGALandscapeImage, aimsApp.getAppsId(), userId, ManageApplicationsConstants.WAP_APP_IMG_LANDSCAPE_BLOB_DB_INFO);
        }
        catch (AimsException ax)
        {
            ax.printStackTrace();
            throw ax;
        }

        catch (JDBCException je)
        {
            je.printStackTrace();
        }

        catch (HibernateException he)
        {
            he.printStackTrace();
            throw he;
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
        finally
        {
            session.close();
            log.debug("SESSION  CLOSED IN   saveWapLogoImages()");
        }
    }//end saveWapLogoImages


    /*
    /////// TODO Delete this method, no need to set wapFTPFlag
    public static void updateWapFTPFlag(Long wapAppID, String wapFTPFlag) throws SQLException, HibernateException
    {
        Session session = null;
        Connection conOra = null;
        Statement statement = null;
        String query = "update AIMS_WAP_APPS SET WAP_FTP_FLAG = '"+wapFTPFlag+"' where WAP_APPS_ID= "+wapAppID;

        try
        {
            session = DBHelper.getInstance().getSession();
            conOra = session.connection();
            statement = conOra.createStatement();
            statement.executeUpdate(query);
            conOra.commit();
        }
        catch (SQLException sqle)
        {
            conOra.rollback();
            sqle.printStackTrace();
        }
        finally
        {
            if (statement!=null) {
                    statement.close();
            }
            session.close();
        }
    }//end updateWapFTPFlag
    */
}
