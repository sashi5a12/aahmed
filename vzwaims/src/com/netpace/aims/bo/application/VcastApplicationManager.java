package com.netpace.aims.bo.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsVcastApp;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.DBErrorFinder;

/**
 * This	class	is responsible for getting the BO	for	Vcast	Application.
 * It	has	static methods for getting the Vcast Application.
 * @author Fawad Sikandar
 */
public class VcastApplicationManager
{

    static Logger log = Logger.getLogger(VcastApplicationManager.class.getName());

    /*
     * This function fetches the VCAST Application. Called when user wants to View/Edit/Clone the Application. 
     */
    public static HashMap getVcastApp(Long appsId, Long allianceId) throws AimsException, HibernateException
    {
        AimsApp aimsApp = null;
        Session session = null;
        HashMap appVcast = new HashMap();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       apps, vcastapps ")
                .append("   from ")
                .append("       com.netpace.aims.model.application.AimsApp as apps, ")
                .append("       com.netpace.aims.model.application.AimsVcastApp as vcastapps ")
                .append("   where ")
                .append("       apps.appsId = vcastapps.vcastAppsId ")
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
                appVcast.put("AimsApp", (AimsApp) appValues[0]); // AimsApp
                appVcast.put("AimsVcastApp", (AimsVcastApp) appValues[1]); // AimsVcastApp
            }

            if (appVcast.isEmpty())
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

        return appVcast;
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

    public static Collection getLanguages() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastLanguage as language ");
            query.append(" order by language_name ");
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

    public static Collection getFrequencies() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastFrequency as frequency ");
            query.append(" order by frequency_name ");
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

    public static Collection getAudAges() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastAudAge as audAge ");
            query.append(" order by aud_age_id ");
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

    public static Collection getAudEducations() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastAudEducation as audEducation ");
            query.append(" order by aud_education_id ");
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

    public static Collection getAudGenders() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastAudGender as audGender ");
            query.append(" order by aud_gender_id ");
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

    public static Collection getAudIncomes() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastAudIncome as audIncome ");
            query.append(" order by aud_income_id ");
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

    public static Collection getAudRaces() throws HibernateException
    {
        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer query = new StringBuffer();
            query.append("from com.netpace.aims.model.application.AimsVcastAudRace as audRace ");
            query.append(" order by aud_race_id ");
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
     * This method saves the Journal Entry into the database. It encapsulates the whole transaction
     */
    public static void saveJournalEntry(Long appsId, String journalEntry, String journalType, String username) throws Exception
    {
        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            addSpecificJournalEntry(ConOra, appsId, journalEntry, journalType, username);
            tx.commit();
        }

        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();
            throw ex;
        }

        finally
        {
            session.close();
        }
    }

    /*
     * This method calls the Stored Procedure 'AIMS_UTILS.add_journal_entry(?,?,?,?,?)' which saves the Journal Entry.
     */
    public static void addSpecificJournalEntry(java.sql.Connection ConOra, Long appsId, String journalText, String journalType, String username)
        throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*  
            Database Procedure SnapShot :
            
            AIMS_UTILS.add_journal_entry(?,?,?,?,?)
            
            Parameters:
            
            p_apps_id            IN     number,
            p_alliance_id    IN     number,                      
            p_journal_text IN       varchar2,
            p_journal_type IN       varchar2,
            p_created_by     IN     varchar2
            
            */

            statement = ConOra.prepareCall("call AIMS_UTILS.add_journal_entry(?,?,?,?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setLong(2, 0);
            statement.setString(3, journalText);
            statement.setString(4, journalType);
            statement.setString(5, username);
            statement.execute();
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

    /*
     * This method saves/updated the VCAST Application
     */

    public static void saveOrUpdateVcastApplication(
        AimsApp aimsApp,
        AimsVcastApp aimsVcastApp,
        AimsContact aimsContact,
        String userId,
        String userType,
        Long sampleClip1,
        Long sampleClip2,
        Long sampleClip3,
        Long clonedFromAppId,
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

            /*
                *SAVING APP IN DATABASE
            */

            if (aimsApp.getAppsId() == null) //Saving
            {
                session.saveOrUpdate(aimsApp);
                aimsVcastApp.setVcastAppsId(aimsApp.getAppsId());
                session.save(aimsVcastApp);
                newApp = true;
            }
            else //Updating
                {
                session.saveOrUpdate(aimsApp);
                aimsVcastApp.setVcastAppsId(aimsApp.getAppsId());
                session.update(aimsVcastApp);
                newApp = false;
            }

            session.flush();

            /*
                UPDATING   DATABASE FOR IMAGES
            */

            java.sql.Connection ConOra = null;
            ConOra = session.connection();

            //Cloning Images
            if (clonedFromAppId != null)
                TempFilesManager.cloneImages(ConOra, clonedFromAppId, aimsApp.getAppsId(), AimsConstants.VCAST_PLATFORM_ID);

            //Copying Images From Temp Table (if present)     
            TempFilesManager.copyImageFromTemp(ConOra, sampleClip1, aimsApp.getAppsId(), userId, ManageApplicationsConstants.VCAST_SAMPLE_CLIP_1_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, sampleClip2, aimsApp.getAppsId(), userId, ManageApplicationsConstants.VCAST_SAMPLE_CLIP_2_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(ConOra, sampleClip3, aimsApp.getAppsId(), userId, ManageApplicationsConstants.VCAST_SAMPLE_CLIP_3_BLOB_DB_INFO);
            //End Of Copying Images From Temp Table (if present)

            ApplicationsManagerHelper.addJournalEntry(ConOra, aimsApp, userId, userType, newApp);

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
            log.debug("ROLLED BACK  IN saveOrUpdateVcastApplication()");
            e.printStackTrace();
            throw e;
        }

        catch (Exception ex)
        {
            if (tx != null)
                tx.rollback();
            log.debug("ROLLED BACK  IN saveOrUpdateVcastApplication()");
            ex.printStackTrace();
            throw ex;
        }

        finally
        {
            session.close();
            log.debug("SESSION  CLOSED IN   saveOrUpdateVcastApplication()");
        }
    }

}
