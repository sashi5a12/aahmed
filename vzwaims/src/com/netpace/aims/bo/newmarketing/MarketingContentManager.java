package com.netpace.aims.bo.newmarketing;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.JDBCException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.newmarketing.MarketingContentHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.model.newmarketing.AimsCreativeContent;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;

/**
 * This	class	is responsible for getting the BO	for	Wap	Application.
 * It	has	static methods for getting the Wap Application.
 * @author Fawad Sikandar
 */
public class MarketingContentManager
{

    static Logger log = Logger.getLogger(MarketingContentManager.class.getName());

    public static final String FIELD_CONTENT_TITLE = "mktContent.contentTitle";
    public static final String FIELD_APPLICATION_TITLE = "mktContent.applicationTitle";
    public static final String FIELD_STATUS = "mktContent.status";
    public static final String FIELD_SUBMITTED_DATE = "mktContent.submittedDate";
    public static final String FIELD_APPROVAL_DATE = "mktContent.approvalDate";
    public static final String FIELD_EXPIRY_DATE = "mktContent.expiryDate";
    public static final String FIELD_COMPANY_NAME = "alliance.companyName";

    /*
     * This function fetches the WAP Application. Called when user wants to View/Edit/Clone the Application. 
     */
    public static HashMap getMarketingContent(Long creativeContentId, Long allianceId) throws AimsException, HibernateException
    {
        Session session = null;
        AimsCreativeContent aimsCreativeContent = null;
        HashMap mktCont = new HashMap();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select ")
                .append("       mktContent ")
                .append("   from ")
                .append("       com.netpace.aims.model.newmarketing.AimsCreativeContent as mktContent ")
                .append("   where ")
                .append("       mktContent.creativeContentId = :creativeContentId ");

            //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).   
            if (allianceId != null)
                queryStringBuffer.append("      and mktContent.allianceId = :allianceId");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("creativeContentId", creativeContentId.longValue());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                aimsCreativeContent = (AimsCreativeContent) it.next();
                mktCont.put("AimsCreativeContent", aimsCreativeContent);
            }

            if (mktCont.isEmpty())
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

        return mktCont;
    }

    //  Function to Delete Marketing Content
    public static int deleteMarketingContent(Long creativeContentId, String user) throws AimsException, Exception
    {
        int delCount = 0;
        Session session = null;
        Transaction tx = null;
        java.sql.Connection ConOra = null;
        java.sql.CallableStatement statement = null;

        try
        {
            /*    
            Database Procedure SnapShot   :
            AIMS_UTILS.delete_content_with_relations(?,?)
                   
            Parameters:
            p_content_id        IN       number,
            p_curr_user_name    IN       varchar2
            */

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();

            statement = ConOra.prepareCall("call AIMS_UTILS.delete_content_with_relations(?,?)");
            statement.setLong(1, creativeContentId.longValue());
            statement.setString(2, user);
            statement.execute();

            tx.commit();
        }

        catch (Exception ex)
        {
            AimsException aimsException = new AimsException("Error");

            if (tx != null)
                tx.rollback();

            if (DBErrorFinder.isIntegrityConstraintError(ex.getMessage(), aimsException))
            {
                throw aimsException;
            }
            else
            {
                ex.printStackTrace();
                throw ex;
            }
        }

        finally
        {
            if (statement != null)
                statement.close();
            session.close();
            log.debug("SESSION CLOSED IN deleteApp()");
        }

        return delCount;
    }

    /*
     * This method saves/updated the WAP Application
     */
    public static void saveOrUpdateMarketingContent(
        AimsCreativeContent aimsCreativeContent,
        String userId,
        String userType,
        Long publisherLogo,
        Long appTitleGraphic,
        Long splashScreen,
        Long activeScreen,
        Long screenJpeg1,
        Long screenJpeg2,
        Long screenJpeg3,
        Long screenJpeg4,
        Long screenJpeg5,
        Long videoFile,
        Long appLogoBwSmall,
        Long appLogoBwLarge,
        Long appLogoClrsmall,
        Long appLogoClrlarge,
        String dateFormat)
        throws AimsException, HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            /*
                *SAVING APP IN DATABASE
            */

            session.saveOrUpdate(aimsCreativeContent);
            session.flush();

            /*
                UPDATING   DATABASE FOR IMAGES
            */

            java.sql.Connection ConOra = null;
            ConOra = session.connection();

            //Copying Images From Temp Table (if present)     

            TempFilesManager.copyImageFromTemp(
                ConOra,
                publisherLogo,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_PUBLISHER_LOGO_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                appTitleGraphic,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_APP_TITLE_GRAPHIC_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                splashScreen,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_SPLASH_SCREEN_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                activeScreen,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_ACTIVE_SCREEN_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                screenJpeg1,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_SCREEN_JPEG_1_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                screenJpeg2,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_SCREEN_JPEG_2_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                screenJpeg3,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_SCREEN_JPEG_3_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                screenJpeg4,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_SCREEN_JPEG_4_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                screenJpeg5,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_SCREEN_JPEG_5_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                videoFile,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_VIDEO_FILE_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                appLogoBwSmall,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_APP_LOGO_BW_SMALL_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                appLogoBwLarge,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_APP_LOGO_BW_LARGE_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                appLogoClrsmall,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_APP_LOGO_CLRSMALL_BLOB_DB_INFO);
            TempFilesManager.copyImageFromTemp(
                ConOra,
                appLogoClrlarge,
                aimsCreativeContent.getCreativeContentId(),
                userId,
                MarketingContentHelper.CONTENT_APP_LOGO_CLRLARGE_BLOB_DB_INFO);

            //End Of Copying Images From Temp Table (if present)

            session.flush();

            /*
            if (((aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SAVED_ID.longValue())
                || (aimsApp.getAimsLifecyclePhaseId().longValue() == AimsConstants.SUBMISSION_ID.longValue())))
            {
                TempFilesManager.checkForEmptyBlobs(ConOra, aimsApp.getAppsId());
            }
            */

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

    //********************************
    //START OF Query Part for FROM
    public static StringBuffer getNormalQueryForGetApps()
    {
        StringBuffer queryStringBuffer = new StringBuffer();

        queryStringBuffer
            .append("from ")
            .append("           com.netpace.aims.model.newmarketing.AimsCreativeContent mktContent, ")
            .append("           com.netpace.aims.model.core.AimsAllianc alliance ")
            .append("where  ")
            .append("           mktContent.allianceId = alliance.allianceId ");

        return queryStringBuffer;
    }
    //END OF Query Part for FROM
    //********************************

    //*****************************
    //START OF Query Part   for SELECT
    public static StringBuffer getQueryForColumnsToDisplay()
    {
        StringBuffer queryStringBuffer = new StringBuffer();
        queryStringBuffer
            .append("select distinct    ")
            .append("       mktContent.creativeContentId, mktContent.contentTitle, mktContent.contentDescription, ")
            .append("       mktContent.applicationTitle, mktContent.status, mktContent.submittedDate, ")
            .append("       mktContent.approvalDate, mktContent.expiryDate, ")
            .append("       alliance.companyName, alliance.allianceId ");
        return queryStringBuffer;
    }
    //END   OF Query Part   for SELECT
    //********************************

    //START OF Query Part   for WHERE
    //********************************  
    public static StringBuffer getAllianceCheckQueryForGetApps(Long allianceId)
    {
        StringBuffer queryStringBuffer = new StringBuffer();

        if (allianceId != null)
            queryStringBuffer.append(" and alliance.allianceId = " + allianceId);
        else
            queryStringBuffer.append(" and mktContent.status != 'SAVED'");

        return queryStringBuffer;
    }

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
            sortField = FIELD_CONTENT_TITLE;
            sortOrder = "asc";
        }

        Object[] objs = { sortField, sortOrder };

        queryStringBuffer.append(orderString.format(objs));
        return queryStringBuffer;
    }

    //END   OF Query Part   for ORDER   BY
    //********************************

    public static int getPageCount(StringBuffer queryStringBuffer) throws HibernateException
    {
        int rows = 0;
        Collection collection = null;
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            collection = session.find(queryStringBuffer.toString());
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

    public static Collection getRecords(StringBuffer queryStringBuffer, int PAGE_LENGTH, int page_no) throws HibernateException
    {
        Session session = null;
        Collection collection = null;

        try
        {
            //Set   Paging Logic and execute Query  
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

    public static AimsTempFile getResourceFile(Long creativeContentId, Long allianceId, String propertyInfo) throws HibernateException
    {
        Session session = null;
        AimsTempFile aimsTempFile = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append("   select mktContent.")
                .append(propertyInfo)
                .append(", mktContent.")
                .append(propertyInfo)
                .append("FileName, mktContent.")
                .append(propertyInfo)
                .append("ContentType ")
                .append("   from ")
                .append("       com.netpace.aims.model.newmarketing.AimsCreativeContentFiles as mktContent ")
                .append("   where ")
                .append("       mktContent.creativeContentId = :creativeContentId ");

            //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).   
            if (allianceId != null)
                queryStringBuffer.append("      and mktContent.allianceId = :allianceId");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("creativeContentId", creativeContentId.longValue());
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

}
