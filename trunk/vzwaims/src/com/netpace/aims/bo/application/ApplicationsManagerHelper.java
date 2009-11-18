package com.netpace.aims.bo.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import net.sf.hibernate.*;
import net.sf.hibernate.type.LongType;

import oracle.sql.BLOB;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.application.ApplicationUpdateForm;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.*;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.LobUtils;

/**
 * This	class	is responsible for getting the BO	for	Applications.
 * It	has	static methods for getting the Applications.
 * @author Adnan Makda
 */
public class ApplicationsManagerHelper
{

    public static final String FIELD_COMPANY_NAME = "alliance.companyName";
    public static final String FIELD_PLATFORM_NAME = "platform.platformName";
    public static final String FIELD_TITLE = "application.title";
    public static final String FIELD_VERSION = "application.version";
    public static final String FIELD_CATEGORY_NAME = "category.categoryName";
    public static final String FIELD_BREW_APP_TYPE = "brew.appType";
    public static final String FIELD_CREATE_DATE_NAME = "application.submittedDate";
    public static final String FIELD_PHASE_NAME = "lifecycle.phaseName";
    public static final String FIELD_IF_ON_HOLD = "application.ifOnHold";

    static Logger log = Logger.getLogger(ApplicationsManagerHelper.class.getName());


    public static String getUrlSetupAction(Long platformId)
    {
        if (platformId.toString().equals(AimsConstants.BREW_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.BREW_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.SMS_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.SMS_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.MMS_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.MMS_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.WAP_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.WAP_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.ENTERPRISE_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.ENTERPRISE_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.VCAST_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.VCAST_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.VZAPPZONE_PLATFORM_ID.toString()))
            return ManageApplicationsConstants.VZAPPZONE_APPLICATION_SETUP_URL;

        if (platformId.toString().equals(AimsConstants.DASHBOARD_PLATFORM_ID.toString()))
        	return ManageApplicationsConstants.DASHBOARD_APPLICATION_SETUP_URL;
        if (platformId.toString().equals(AimsConstants.JAVA_PLATFORM_ID.toString()))
        	return ManageApplicationsConstants.JAVA_APPLICATION_SETUP_URL;

        return "";
    }

    public static AimsTempFile getFile(Long appsId, Long allianceId, String[] dbInfo) throws HibernateException
    {
        Session session = null;
        AimsTempFile aimsTempFile = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append(dbInfo[0])
                .append(", ")
                .append(dbInfo[0])
                .append("FileName, ")
                .append(dbInfo[0])
                .append("ContentType ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp as apps, ")
                .append(dbInfo[1])
                .append(", ")
                .append("       com.netpace.aims.model.core.AimsAllianc as alliance ")
                .append("   where ")
                .append("       apps.appsId = ")
                .append(dbInfo[2])
                .append("       and apps.aimsAllianceId = alliance.allianceId ")
                .append("       and apps.appsId = :appsId ");

            //Set 'Alliance Id' if User is 'Alliance User' (allianceId=null).	
            if (allianceId != null)
                queryStringBuffer.append("      and	apps.aimsAllianceId = :allianceId ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());
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

        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }

        return aimsTempFile;
    }

    //Overloaded function	for	tables with	composite	primary	keys
    public static AimsTempFile getFile(Long appsId, Long otherCompositeId, Long allianceId, String[] dbInfo) throws HibernateException
    {
        Session session = null;

        AimsTempFile aimsTempFile = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer.append("	select ").append(dbInfo[0]) //File
            .append("	,	").append(dbInfo[0]) //File	Name
            .append("FileName	,	").append(dbInfo[0]) //File	Content	Type
            .append("ContentType ")
            .append("	from ")
            .append("					com.netpace.aims.model.application.AimsApp	as apps, ")
            .append(dbInfo[1])
            .append("	,	")
            .append("					com.netpace.aims.model.core.AimsAllianc	as alliance	")
            .append("	where	")
            .append("					apps.appsId	=	 ")
            .append(dbInfo[2])
            .append("					and	")
            .append(dbInfo[3])
            .append("								=	:otherCompositeId	")
            .append("					and	apps.aimsAllianceId	=	alliance.allianceId	")
            .append("					and	apps.appsId	=	:appsId	");

            //Set	'Alliance	Id'	if User	is 'Alliance User' (allianceId=null).	
            if (allianceId != null)
                queryStringBuffer.append("				and	apps.aimsAllianceId	=	" + allianceId);

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("appsId", appsId.longValue());
            query.setLong("otherCompositeId", otherCompositeId.longValue());

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsTempFile = new AimsTempFile();
                appValues = (Object[]) it.next();

                log.debug("FILE" + appValues[0]);
                log.debug("FILE_NAME" + appValues[1]);
                log.debug("CONTENT_TYPE" + appValues[2]);

                aimsTempFile.setTempFile((java.sql.Blob) appValues[0]); //File
                aimsTempFile.setTempFileName((String) appValues[1]); //File Name
                aimsTempFile.setTempFileContentType((String) appValues[2]); //File Content Type				
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
            log.debug("SESSION CLOSED	IN getFile()");
        }

        return aimsTempFile;
    }

    //Function for getting misc	files
    public static AimsTempFile getMiscImage(String imageName, String user_type) throws HibernateException
    {
        Session session = null;

        AimsTempFile aimsTempFile = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("select ")
                .append("		miscImage.imageFile, ")
                .append("		miscImage.imageName, ")
                .append("		miscImage.imageContentType ")
                .append("from ")
                .append("		com.netpace.aims.model.content.AimsMiscImage miscImage ")
                .append("where	")
                .append("		miscImage.imageName	=	:imageName ");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setString("imageName", imageName);

            Object[] appValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsTempFile = new AimsTempFile();
                appValues = (Object[]) it.next();

                log.debug("FILE" + appValues[0]);
                log.debug("FILE_NAME" + appValues[1]);
                log.debug("CONTENT_TYPE" + appValues[2]);

                aimsTempFile.setTempFile((java.sql.Blob) appValues[0]); //File
                aimsTempFile.setTempFileName((String) appValues[1]); //File Name
                aimsTempFile.setTempFileContentType((String) appValues[2]); //File Content Type
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
            log.debug("SESSION CLOSED	IN getFile()");
        }

        return aimsTempFile;
    }

    public static void addNewPhases(Session session, Set testingPhaseSet, AimsApp aimsApp, Long platformId, String userType, AimsBrewApp aimsBrewApp)
        throws HibernateException
    {
        try
        {
            //Fill Phase Collection	if Status	is Submit.
            //This is	the	place	where	Testing	Collection is	filled (ONLY ONCE) on	change of	status to	Submit.

            log.debug("aimsApp.getPhases():	" + aimsApp.getPhases());
            if ((aimsApp.getAimsLifecyclePhaseId() == AimsConstants.SUBMISSION_ID) && (userType.equals(AimsConstants.ALLIANCE_USERTYPE)))
            {
                if (platformId == AimsConstants.BREW_PLATFORM_ID)
                {
                    if ((aimsBrewApp != null) && (aimsBrewApp.getDevices() != null))
                    {
                        updateTestPhaseList(session, aimsApp, platformId, aimsBrewApp.getDevices(), aimsApp.getPhases());
                    }
                }
                else
                {
                    AimsDevic aimsDevice = AimsDevicesManager.getDevice(AimsConstants.NON_EXISTANT_DEVICE_ID);
                    Set devicesSet = new HashSet();
                    devicesSet.add(aimsDevice);
                    updateTestPhaseList(session, aimsApp, platformId, devicesSet, aimsApp.getPhases());
                }
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            log.debug("SESSION Will	be Closed	by the calling class for phases");
        }
    }

    public static void updateTestPhaseList(Session session, AimsApp aimsApp, Long platformId, Set aimsDevices, Set currentPhases) throws HibernateException
    {
        AimsDevic aimsDevice = null;
        boolean bPresent = false;
        Set tempSet = new HashSet();

        if (aimsDevices != null)
        {
            for (Iterator it = aimsDevices.iterator(); it.hasNext();)
            {
                aimsDevice = (AimsDevic) it.next();
                for (java.util.Iterator itr = AimsApplicationsManager.getTestingPhases(platformId).iterator(); itr.hasNext();)
                {
                    AimsVzwTestingPhase aimsVzwTestingPhase = (AimsVzwTestingPhase) itr.next();

                    //Check	to see if	the	app_phase	entry	already	exists
                    bPresent = false;
                    if (currentPhases != null)
                    {
                        for (Iterator itre = currentPhases.iterator(); itre.hasNext();)
                        {
                            AimsAppPhase aimsAppPhase = (AimsAppPhase) itre.next();
                            if ((aimsAppPhase.getAimsDevice().equals(aimsDevice)) && (aimsAppPhase.getAimsVzwTestingPhase().equals(aimsVzwTestingPhase)))
                            {
                                session.update(aimsAppPhase);
                                tempSet.add(aimsAppPhase);
                                bPresent = true;
                                break;
                            }
                        }
                    }
                    //Add	if not present
                    if (!(bPresent))
                    {
                        AimsAppPhase aimsAppPhase = new AimsAppPhase();
                        aimsAppPhase.setAppsId(aimsApp.getAppsId());
                        aimsAppPhase.setAimsVzwTestingPhase(aimsVzwTestingPhase);
                        aimsAppPhase.setAimsDevice(aimsDevice);
                        tempSet.add(aimsAppPhase);
                        session.save(aimsAppPhase);
                    }
                }
            }
        }

        //Check	to see if	existing entries in	database does	not	contain	deleted	items				
        if (currentPhases != null)
        {
            for (Iterator it = currentPhases.iterator(); it.hasNext();)
            {
                AimsAppPhase aimsAppPhaseExisting = (AimsAppPhase) it.next();
                bPresent = false;
                for (Iterator itr = tempSet.iterator(); itr.hasNext();)
                {
                    AimsAppPhase aimsAppPhaseNew = (AimsAppPhase) itr.next();
                    if (aimsAppPhaseNew.equals(aimsAppPhaseExisting))
                    {
                        bPresent = true;
                        break;
                    }
                }
                //Delete if	not	present
                if (!(bPresent))
                    session.delete(aimsAppPhaseExisting);
            }
        }
    }

    public static void saveTestPhases(Session session, Set testingPhaseSet, AimsApp aimsApp, String userType) throws HibernateException
    {
        try
        {
            if ((testingPhaseSet != null) && (testingPhaseSet.size() > 0) && (userType.equals(AimsConstants.VZW_USERTYPE)))
            {
                for (java.util.Iterator itr = testingPhaseSet.iterator(); itr.hasNext();)
                {
                    AimsAppPhase aimsAppPhase = (AimsAppPhase) itr.next();
                    aimsAppPhase.setTempFileId(null);
                    session.saveOrUpdate(aimsAppPhase);
                }
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            log.debug("SESSION Will	be Closed	by the calling class for saveTestPhases	method");
        }
    }


    public static void deleteAppNetworkUrls(Long appsId) throws HibernateException
    {
        Session session = null;
        Transaction tx = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            ApplicationsManagerHelper.deleteAppNetworkUrls(session, appsId);
            tx.commit();
        }
        catch (HibernateException he)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            he.printStackTrace();
            throw he;
        }
        finally
        {
            if (session != null)
            {
                session.close();
                log.debug("Session closed in deleteAppNetworkUrls(Long appsId)");
            }
        }
    }

    public static int deleteAppNetworkUrls(Session session, Long appsId) throws HibernateException
    {
        int delCount = 0;
        try
        {
            delCount =
                    session.delete(
                        "from	com.netpace.aims.model.application.AimsAppNetworkUrls	as appNetworkURL	where	appNetworkURL.aimsAppId	=	:appsId",
                       appsId,
                        new LongType());
        }
        catch (HibernateException he)
        {
            he.printStackTrace();
            throw he;
        }
        finally
        {
            log.debug("SESSION Will	be Closed	by the calling class for deleteAppNetworkUrls(Session session, Long appsId)	method");
        }
        return delCount;
    }//end deleteAppNetworkUrls(Session)


    public static void saveAppNetworkUrls(Session session, List appNetworkURLs, Long appsId) throws HibernateException
    {
        try
        {
            if (appNetworkURLs != null)
            {
                //delete old AimsAppNetworkUrls for this application
                int delCount = ApplicationsManagerHelper.deleteAppNetworkUrls(session, appsId);
                if(appNetworkURLs.size() > 0)
                {
                    for (java.util.Iterator itr = appNetworkURLs.iterator(); itr.hasNext();)
                    {
                        AimsAppNetworkUrls appNetworkURL = (AimsAppNetworkUrls) itr.next();
                        appNetworkURL.setAppNetworkUrlId(null);
                        appNetworkURL.setAimsAppId(appsId);
                        session.saveOrUpdate(appNetworkURL);
                    }
                }
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            log.debug("SESSION Will	be Closed	by the calling class for saveAppNetworkUrls(Session session, List appNetworkURLs, Long appsId)	method");
        }
    }//saveAppNetworkUrls(Session)

    public static void saveAppNetworkUrls(List appNetworkURLs, Long appsId) throws HibernateException
    {
        Session session = null;
        Transaction tx = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            ApplicationsManagerHelper.saveAppNetworkUrls(session, appNetworkURLs, appsId);
            tx.commit();
        }
        catch (HibernateException he)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            he.printStackTrace();
            throw he;
        }
        finally
        {
            if (session != null)
            {
                session.close();
                log.debug("Session closed in saveAppNetworkUrls(List appNetworkURLs, Long appsId)");
            }
        }
    }//end saveAppNetworkUrls


    public static void saveCertifications(Session session, Set certificationSet, AimsApp aimsApp, String userType) throws HibernateException
    {
        try
        {
            if ((certificationSet != null) && (userType.equals(AimsConstants.VZW_USERTYPE)))
            {
                int delCount =
                    session.delete(
                        "from	com.netpace.aims.model.application.AimsAppCertification	as appCertification	where	appCertification.appsId	=	:appsId",
                        aimsApp.getAppsId(),
                        new LongType());
                if (certificationSet.size() > 0)
                {
                    for (java.util.Iterator itr = certificationSet.iterator(); itr.hasNext();)
                    {
                        AimsAppCertification aimsAppCertification = (AimsAppCertification) itr.next();
                        session.save(aimsAppCertification);
                    }
                }
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            log.debug("SESSION Will	be Closed	by the calling class for saveCertifications");
        }
    }

    public static void saveTestingPhaseFiles(java.sql.Connection ConOra, Set testingPhaseSet, AimsApp aimsApp, String userId, String userType)
        throws HibernateException, Exception
    {
        if ((testingPhaseSet != null) && (testingPhaseSet.size() > 0) && (userType.equals(AimsConstants.VZW_USERTYPE)))
        {
            for (java.util.Iterator itr = testingPhaseSet.iterator(); itr.hasNext();)
            {
                AimsAppPhase aimsAppPhase = (AimsAppPhase) itr.next();
                try
                {
                    Long tmpLong = new Long(aimsAppPhase.getTempFileId());
                    Long[] compositeId = new Long[2];
                    //Testing	Phase	ID
                    compositeId[0] = aimsAppPhase.getAimsVzwTestingPhase().getTestingPhaseId();
                    //Device ID
                    compositeId[1] = aimsAppPhase.getAimsDevice().getDeviceId();

                    TempFilesManager.copyImageFromTemp(
                        ConOra,
                        tmpLong,
                        aimsApp.getAppsId(),
                        compositeId,
                        userId,
                        ManageApplicationsConstants.RESULTS_FILE_BLOB_DB_INFO);
                }
                catch (NumberFormatException ex)
                {
                    log.debug("\n\nNO	TEMP FILE	ID\n\n");
                }
            }
        }
    }

    //Adds Journal Entry when	application	is updated
    public static void addJournalEntry(java.sql.Connection ConOra, AimsApp aimsApp, String userId, String userType, boolean newApp) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*	
            Database Procedure SnapShot	:
            	
            AIMS_UTILS.add_journal_entry(?,?,?,?,?)
            	 
            Parameters:
            	 
            p_apps_id			 IN		number,
            p_alliance_id	 IN		number,						 
            p_journal_text IN		varchar2,
            p_journal_type IN		varchar2,
            p_created_by	 IN		varchar2
            
            */

            StringBuffer journalText = new StringBuffer();
            journalText.append("Application ");

            if (newApp)
                journalText.append("Created ");
            else
            {
                journalText.append("Updated ");

                // Added by Ahson Imtiaz for different specific messages.
                try
                {
                    AimsAppLite appOld = getAimsAppLite(aimsApp.getAppsId());
                    if (appOld != null)
                    {
                        if (appOld.getAimsLifecyclePhaseId() != null && aimsApp.getAimsLifecyclePhaseId() != null)
                            if (!appOld.getAimsLifecyclePhaseId().equals(aimsApp.getAimsLifecyclePhaseId()))
                            {
                                VOLookupFactory volkup = VOLookupFactory.getInstance();
                                String strOldPhaseName = volkup.getLifecycle(appOld.getAimsLifecyclePhaseId()).getPhaseName();
                                String strNewPhaseName = volkup.getLifecycle(aimsApp.getAimsLifecyclePhaseId()).getPhaseName();
                                journalText.append(" Status Changed from ").append(strOldPhaseName).append(" to ").append(strNewPhaseName);
                            }
                    }
                }
                catch (Exception exp)
                {
                    log.debug("Journal Specific Message Produce Error.");
                    exp.printStackTrace();
                }
            }

            journalText.append(" By ");
            journalText.append(userId);

            statement = ConOra.prepareCall("call AIMS_UTILS.add_journal_entry(?,?,?,?,?)");
            statement.setLong(1, aimsApp.getAppsId().longValue());
            statement.setLong(2, 0);
            statement.setString(3, journalText.toString());
            statement.setString(4, null);
            statement.setString(5, null);
            statement.execute();
            log.debug(" *********--------- Journal Entry Added ----------- *************");

        }

        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            if (statement != null)
                statement.close();
        }

    }
    /* */
    public static AimsAppLite getAimsAppLite(Long appsId) throws HibernateException
    {

        AimsAppLite aimsapp = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery("select from com.netpace.aims.model.application.AimsAppLite	as app where app.appsId = :appsId");
            query.setLong("appsId", appsId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsapp = (AimsAppLite) it.next();
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

        return aimsapp;
    }
    
    public static void setApplicationStatus(ApplicationUpdateForm form)throws HibernateException{
    	if ((form.getAppsId() != null) && (form.getAppsId().longValue() != 0))
        {
    		if (form.getAimsLifecyclePhaseId() != null){
    			AimsLifecyclePhase aimsPhaseOfApplication =(AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, form.getAimsLifecyclePhaseId().toString());
    			form.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
    		}
        }
        else
        {
        	form.setApplicationStatus("NEW");
        }    	
    }
    
    public static void updateApplication(AimsAppLite app, ByteArrayOutputStream boas) throws Exception{
    	log.debug("ApplicationsManagerHelper.updateApplication Start:");
    	Session session=null;
    	Transaction trx=null;
    	StringBuffer journalText = new StringBuffer();
        AimsJournalEntry je = new AimsJournalEntry();
        InputStream in=null;
    	try {
    		AimsAppLite appOld = getAimsAppLite(app.getAppsId());
			
            VOLookupFactory volkup = VOLookupFactory.getInstance();
            String strOldPhaseName = volkup.getLifecycle(appOld.getAimsLifecyclePhaseId()).getPhaseName();
            String strNewPhaseName = volkup.getLifecycle(app.getAimsLifecyclePhaseId()).getPhaseName();

            journalText.append("Application Updated ");			
			journalText.append("Status Changed from ").append(strOldPhaseName).append(" to ").append(strNewPhaseName);
            journalText.append(" By ").append(app.getLastUpdatedBy());
                 
            je.setJournalText(journalText.toString());
            je.setJournalType(AimsConstants.JOURNAL_TYPE_PRIVATE); 
            je.setCreatedBy("system");
            je.setCreatedDate(app.getLastUpdatedDate());
            je.setAimsAppId(app.getAppsId());

			session=DBHelper.getInstance().getSession();			
			trx=session.beginTransaction();

			if (boas != null){
                byte[] buffer = new byte[1];
                long bytesWrote=0; 
                buffer[0] = 1;
                
                in=new ByteArrayInputStream(boas.toByteArray());
                AimsBrewUserGuidePdf pdf=new AimsBrewUserGuidePdf();
                pdf.setBrewAppsId(app.getAppsId());
                pdf.setUserGuideFileName("User_Guide.pdf");
                pdf.setUserGuideContentType("application/pdf");
                pdf.setUserGuide(Hibernate.createBlob(buffer));
                
                session.saveOrUpdate(pdf);

                session.flush();
                session.refresh(pdf, LockMode.UPGRADE);

                bytesWrote = LobUtils.writeToOraBlob((BLOB) pdf.getUserGuide(), in);
                log.debug("bytesWrote= "+bytesWrote );
                session.flush();
			}
			
            session.update(app);            
            session.save(je);
			trx.commit();
		} catch (HibernateException e) {
			log.error(e,e);
			if(trx != null){
				trx.rollback();
			}
		} catch (Exception e) {
			log.error(e,e);
			throw e;
		} finally {
			if (session != null){
				session.close();
			}
			if (boas != null){
				boas.close();
			}
			if (in != null){
				in.close();
			}
		}
		log.debug("ApplicationsManagerHelper.updateApplication End:");
    }
    /* Class Ends */
}
