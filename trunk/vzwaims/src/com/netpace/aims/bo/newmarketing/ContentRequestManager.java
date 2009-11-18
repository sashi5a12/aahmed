package com.netpace.aims.bo.newmarketing;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.newmarketing.AppContactsDetailsExt;
import com.netpace.aims.controller.newmarketing.ContactsDetailsExt;
import com.netpace.aims.controller.newmarketing.ContentRequestApplicationExt;
import com.netpace.aims.controller.newmarketing.FilesDetail;
import com.netpace.aims.controller.newmarketing.MarketApplicationExt;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.newmarketing.AimsContentReqApp;
import com.netpace.aims.model.newmarketing.AimsContentReqCnt;
import com.netpace.aims.model.newmarketing.AimsCreativeContentFiles;
import com.netpace.aims.model.newmarketing.AimsCreativeCreqFil;
import com.netpace.aims.model.newmarketing.AimsCreativeCrequest;
import com.netpace.aims.util.AimsConstants;
/**
 * Class is responsible for handling the
 * @author Ahson Imtiaz
 * @version 1.0
 * */
public class ContentRequestManager
{

    static Logger log = Logger.getLogger(ContentRequestManager.class.getName());

    /**
     * Return the content request associated with the user.
     * */
    public static Collection getContentRequests(String orderBy, int PAGE_LENGTH, int page_no, Long userId, String strQueryFilter) throws HibernateException
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;

        queryString
            .append("   select distinct creq ")
            .append("   from ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeCrequest creq, ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeContent as content, ")
            .append("       com.netpace.aims.model.newmarketing.AimsContentReqCnt as reqcnt ")
            .append("   where ")
            .append("       creq.crequestId = reqcnt.crequestId ")
            .append("       and reqcnt.creativeContentId = content.creativeContentId ")
            .append("       and creq.userId = :userId ");

        if (strQueryFilter != null && strQueryFilter.length() > 0)
            queryString.append(" AND ").append(strQueryFilter);

        queryString.append(" ORDER BY ").append("upper(").append(orderBy).append(")");

        try
        {
            //Set	Paging Logic and execute Query
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            query.setLong("userId", userId.longValue());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (page_no - 1));
            return query.list();

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

    }

    /**
     * Returns the count of Content Request Submitted by User
     * */
    public static int getRecordCount(Long userId, String strQueryFilter) throws HibernateException
    {
        int rows = 0;
        Collection collection = null;
        Session session = null;
        StringBuffer queryString = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            queryString.append("select creq.userId ").append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeCrequest creq ").append(
                " WHERE creq.userId = ").append(
                userId);

            if (strQueryFilter != null && strQueryFilter.length() > 0)
                queryString.append(" AND ").append(strQueryFilter);

            collection = session.find(queryString.toString());
            log.debug("No of requests	records:	" + collection.size());
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

    /**
     * Method return the listing of Content Request Awaiting Approval. From Verizon Administrator
     * */
    public static Collection getConReqForApprovals(int PAGE_LENGTH, int page_no, String strQueryFilter, Long allianceId) throws HibernateException
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;

        queryString
            .append("   select distinct creq ")
            .append("   from ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeCrequest creq, ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeContent as content, ")
            .append("       com.netpace.aims.model.newmarketing.AimsContentReqCnt as reqcnt ")
            .append("   where ")
            .append("       creq.crequestId = reqcnt.crequestId ")
            .append("       and reqcnt.creativeContentId = content.creativeContentId ")
            .append("       and creq.status <> 'SAVED' ");

        //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
        if (allianceId != null)
        {
            queryString.append(" and content.allianceId = :allianceId");
            queryString.append(" and reqcnt.userToApprove is not null ");
            queryString.append(" and reqcnt.userStatus = 'PENDING' ");
        }

        if (strQueryFilter != null && strQueryFilter.length() > 0)
            queryString.append(" AND ").append(strQueryFilter);
        queryString.append(" ORDER BY creq.submittedDate DESC ");

        try
        {
            //Set	Paging Logic and execute Query
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (page_no - 1));
            return query.list();

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

    }
    /**
     * Returns the count of Content Request Submitted
     * */
    public static int getRecordCountForApprovals(String strQueryFilter, Long allianceId) throws HibernateException
    {
        int rows = 0;
        Collection collection = null;
        Session session = null;
        StringBuffer queryString = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            queryString
                .append("   select distinct creq.userId ")
                .append("   from ")
                .append("       com.netpace.aims.model.newmarketing.AimsCreativeCrequest creq, ")
                .append("       com.netpace.aims.model.newmarketing.AimsCreativeContent as content, ")
                .append("       com.netpace.aims.model.newmarketing.AimsContentReqCnt as reqcnt ")
                .append("   where ")
                .append("       creq.crequestId = reqcnt.crequestId ")
                .append("       and reqcnt.creativeContentId = content.creativeContentId ")
                .append("       and creq.status <> 'SAVED' ");

            //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
            if (allianceId != null)
            {
                queryString.append(" and content.allianceId = :allianceId");
                queryString.append(" and reqcnt.userToApprove is not null ");
                queryString.append(" and reqcnt.userStatus = 'PENDING' ");
            }

            if (strQueryFilter != null && strQueryFilter.length() > 0)
                queryString.append(" AND ").append(strQueryFilter);

            Query query = session.createQuery(queryString.toString());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            collection = query.list();
            log.debug("No of requests records: " + collection.size());
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
    /**
     * */


    public static AimsCreativeCrequest getContentRequestDetail(Long contentRequestId, Long allianceId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("   select distinct creq ")
            .append("   from ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeCrequest creq, ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeContent as content, ")
            .append("       com.netpace.aims.model.newmarketing.AimsContentReqCnt as reqcnt ")
            .append("   where ")
            .append("       creq.crequestId = reqcnt.crequestId ")
            .append("       and reqcnt.creativeContentId = content.creativeContentId ")
            .append("       and creq.crequestId = :contentRequestId ");

        //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
        if (allianceId != null)
        {
            queryString.append(" and content.allianceId = :allianceId");
            queryString.append(" and reqcnt.userToApprove is not null ");
        }

        log.debug(" ************ Query String: " + queryString);

        /*
        queryString.append("select creq ").append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeCrequest  creq ").append(
            " WHERE creq.crequestId = ").append(
            contentRequestId);
        */
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            query.setLong("contentRequestId", contentRequestId.longValue());


            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Iterator it = query.iterate();

            if (it.hasNext())
                return (AimsCreativeCrequest) it.next();

            throw new Exception("Invalid Record Id for Content Request");

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

    }



    public static AimsCreativeCrequest getContentRequestDetail(Long contentRequestId, Long allianceId, Long userId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("   select distinct creq ")
            .append("   from ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeCrequest creq, ")
            .append("       com.netpace.aims.model.newmarketing.AimsCreativeContent as content, ")
            .append("       com.netpace.aims.model.newmarketing.AimsContentReqCnt as reqcnt ")
            .append("   where ")
            .append("       creq.crequestId = reqcnt.crequestId ")
            .append("       and reqcnt.creativeContentId = content.creativeContentId ")
            .append("       and creq.crequestId = :contentRequestId ")
            .append("       and creq.userId = :userId ");

        //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
        if (allianceId != null)
        {
            queryString.append(" and content.allianceId = :allianceId");
            queryString.append(" and reqcnt.userToApprove is not null ");
        }

        log.debug(" ************ Query String: " + queryString);
        log.debug(" ***** User Id: " + userId);
        /*
        queryString.append("select creq ").append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeCrequest  creq ").append(
            " WHERE creq.crequestId = ").append(
            contentRequestId);
        */
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            query.setLong("contentRequestId", contentRequestId.longValue());
            query.setLong("userId", userId.longValue());

            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Iterator it = query.iterate();

            if (it.hasNext())
                return (AimsCreativeCrequest) it.next();

            throw new Exception("Invalid Record Id for Content Request");

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

    }

    public static String getContentByContReqId(Long contentRequestId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("select creq ")
            .append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeCrequest  creq ")
            .append(" WHERE creq.crequestId = ")
            .append(contentRequestId)
            .append(" AND creq.status = 'ACCEPTED'");

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            if (it.hasNext())
            {
                Long contentId = ((AimsCreativeCrequest) it.next()).getCrequestId();
                if (contentId != null)
                    return contentId.toString();
            }

            return null;

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

    }

    /**
     * */
    public static AimsCreativeContentFiles getContentFile(String fileId, String userId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();

        queryString.append("select acc ")
                .append(" FROM  com.netpace.aims.model.newmarketing.AimsCreativeContentFiles acc, ")
                .append(" com.netpace.aims.model.newmarketing.AimsCreativeCrequest accr, ")
                .append(" com.netpace.aims.model.newmarketing.AimsContentReqCnt acrc")
                .append(" WHERE acc.creativeContentId = ")
                .append(fileId)
                .append(" AND acc.creativeContentId = acrc.creativeContentId  ")
                .append(" AND acrc.crequestId = accr.crequestId  ")
                .append(" AND accr.userId =  ")
                .append(userId);

               log.debug(" ****** In getContentFile: " + queryString);

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            if (it.hasNext())
                return (AimsCreativeContentFiles) it.next();

            throw new Exception("Invalid Record Id for Content Request File");

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

    }

    /**
     * */
    public static AimsCreativeCreqFil getContentRequestFile(Long fileId, Long fileType) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();

        queryString.append("select creqfile ");

        if (fileType.intValue() == 2)
        {
            queryString.append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeCreqFil creqfile ");
            queryString.append(" WHERE creqfile.fileId = ").append(fileId);
        }
        else
        {
            queryString.append(" FROM com.netpace.aims.model.core.AimsTempFile creqfile ");
            queryString.append(" WHERE creqfile.tempFileId = ").append(fileId);
        }

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            if (it.hasNext())
                return (AimsCreativeCreqFil) it.next();

            throw new Exception("Invalid Record Id for Content Request File");

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

    }

    /**
     * Method is responsible for saving or updating the related data of content request
     * @param creq AimsContentRequest object
     * @param Long[] Array of applications Ids
     * */
    public static void saveContentRequest(AimsCreativeCrequest creq, Long[] appsIds, ArrayList fd) throws HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;
        StringBuffer sbFilesQuery = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(creq);
            session.flush();
            Connection conn = session.connection();

            sbFilesQuery
                .append("insert into AIMS_CREATIVE_CREQ_FILES (CREQUEST_ID, FILE_ID, FILE_TYPE, FILE_NAME, FILE_OBJECT, COMMENTS) ")
                .append("(SELECT ")
                .append(creq.getCrequestId())
                .append(" , SEQ_PK_CONT_REQ_FILES.nextval, TEMP_FILE_CONTENT_TYPE, TEMP_FILE_NAME, TEMP_FILE, TEMP_FILE_COMMENTS ")
                .append(" FROM AIMS_TEMP_FILES WHERE TEMP_FILE_ID IN ( ");

            if (fd != null)
            {
                Iterator it = fd.iterator();
                while (it.hasNext())
                {
                    FilesDetail fdobj = (FilesDetail) it.next();
                    if (fdobj.getFileType() == 1)
                    {
                        sbFilesQuery.append(fdobj.getFileId());
                        sbFilesQuery.append(",");
                    }
                }
            }
            sbFilesQuery.append(" 0 ) )");
            // Copy the files from temp files table
            conn.createStatement().execute(sbFilesQuery.toString());
            session.delete("FROM com.netpace.aims.model.newmarketing.AimsContentReqCnt conapp WHERE conapp.crequestId = " + creq.getCrequestId());
            session.flush();
            AimsContentReqCnt crapps = null;

            if (appsIds != null)
                for (int iCount = 0; iCount < appsIds.length; iCount++)
                {
                    crapps = new AimsContentReqCnt();
                    crapps.setCreativeContentId(appsIds[iCount]);
                    crapps.setCrequestId(creq.getCrequestId());
                    log.debug("Saving appId : " + appsIds[iCount] + " ---- ");
                    session.save(crapps);
                }
            tx.commit();
        }
        catch (Exception e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    /**
     * Method will return the name of Applications in ArrayList containing object of MarketApplicationExt
     * only applicationId and appTitle attributes are populated
     * @see com.netpace.aims.controller.marketing.MarketApplicationExt
     * */
    public static ArrayList getApplicationsName(Long[] appsIds) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("select app.appsId, app.title , alliance.companyName ")
            .append(" FROM ")
            .append(" com.netpace.aims.model.application.AimsApp app, ")
            .append(" com.netpace.aims.model.core.AimsAllianc alliance ")
            .append(" WHERE app.appsId IN ( ");

        if (appsIds != null)
            for (int iCount = 0; iCount < appsIds.length; iCount++)
            {
                queryString.append(appsIds[iCount]).append(",");
            }
        queryString.append(" 0) AND app.aimsAllianceId = alliance.allianceId ORDER BY app.title ");

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            while (it.hasNext())
            {
                MarketApplicationExt appDetail = new MarketApplicationExt();
                objValues = (Object[]) it.next();
                appDetail.setApplicationId((Long) objValues[0]);
                appDetail.setAppTitle((String) objValues[1]);
                appDetail.setDeveloperName((String) objValues[2]);
                objArray.add(appDetail);
            }

            return objArray;
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

    }

    /**
    * Method will return the name of Applications in ArrayList containing object of MarketApplicationExt
    * only applicationId and appTitle attributes are populated
    * @see com.netpace.aims.controller.marketing.MarketApplicationExt
    * */
    public static ArrayList getContentName(Long[] contentIds) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("select acc.creativeContentId, acc.contentTitle , aa.companyName ")
            .append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeContent acc, com.netpace.aims.model.core.AimsAllianc aa, com.netpace.aims.model.core.AimsUser au  ")
            .append(" WHERE acc.creativeContentId IN ( ");

        if (contentIds != null)
            for (int iCount = 0; iCount < contentIds.length; iCount++)
            {
                queryString.append(contentIds[iCount]).append(",");
            }
        queryString.append(" 0) AND acc.userId= au.userId ").append(" AND aa.allianceId = au.aimsAllianc ORDER BY acc.contentTitle ");

        System.out.println(" Query : " + queryString.toString());
        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            while (it.hasNext())
            {
                MarketApplicationExt appDetail = new MarketApplicationExt();
                objValues = (Object[]) it.next();
                appDetail.setApplicationId((Long) objValues[0]);
                appDetail.setAppTitle((String) objValues[1]);
                appDetail.setDeveloperName((String) objValues[2]);
                objArray.add(appDetail);
            }

            return objArray;
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

    }

    /**
     * Method will return the name of Applications in ArrayList containing object of MarketApplicationExt
     * only applicationId and appTitle attributes are populated
     * @see com.netpace.aims.controller.marketing.MarketApplicationExt
     * */
    public static ArrayList getContentName(Long crequestId, Long allianceId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("select acc.creativeContentId, acc.contentTitle , aa.companyName, acc.contentUsagePermission , acrc.status, acrc.userStatus ")
            .append(" FROM ")
            .append(" com.netpace.aims.model.newmarketing.AimsCreativeContent acc,com.netpace.aims.model.newmarketing.AimsContentReqCnt acrc, com.netpace.aims.model.core.AimsAllianc aa, com.netpace.aims.model.core.AimsUser au  ")
            .append(" WHERE acrc.crequestId = ")
            .append(crequestId)
            .append(" AND ")
            .append(" acrc.creativeContentId = acc.creativeContentId AND ")
            .append(" acc.userId= au.userId AND ")
            .append(" au.aimsAllianc = aa.allianceId ");

        //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
        if (allianceId != null)
        {
            queryString.append(" and aa.allianceId = :allianceId ");
            queryString.append(" and acrc.userToApprove is not null ");
        }

        log.debug(" ***** Query : " + queryString.toString());

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());
            Iterator it = query.iterate();

            while (it.hasNext())
            {
                MarketApplicationExt appDetail = new MarketApplicationExt();
                objValues = (Object[]) it.next();
                appDetail.setApplicationId((Long) objValues[0]);
                appDetail.setAppTitle((String) objValues[1]);
                appDetail.setDeveloperName((String) objValues[2]);
                appDetail.setContentUsagePermission((String) objValues[3]);
                appDetail.setStatus((String) objValues[4]);
                appDetail.setUserStatus((String) objValues[5]);
                objArray.add(appDetail);
            }

            return objArray;
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

    }

    /**
     * Method will return the name of Applications in ArrayList containing object of MarketApplicationExt
     * only applicationId and appTitle attributes are populated
     * @see com.netpace.aims.controller.marketing.MarketApplicationExt
     * */
    public static ArrayList getApplicationsName(Long crequestId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append(" select app.appsId, app.title, alliance.companyName, conapp.status ")
            .append(" FROM ")
            .append(" com.netpace.aims.model.application.AimsApp app, ")
            .append(" com.netpace.aims.model.marketing.AimsContentReqApp conapp, ")
            .append(" com.netpace.aims.model.core.AimsAllianc alliance WHERE conapp.crequestId = ")
            .append(crequestId)
            .append(" AND ")
            .append(" conapp.appsId = app.appsId AND ")
            .append(" app.aimsAllianceId = alliance.allianceId ");

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            while (it.hasNext())
            {
                MarketApplicationExt appDetail = new MarketApplicationExt();
                objValues = (Object[]) it.next();
                appDetail.setApplicationId((Long) objValues[0]);
                appDetail.setAppTitle((String) objValues[1]);
                appDetail.setDeveloperName((String) objValues[2]);
                appDetail.setStatus((String) objValues[3]);
                objArray.add(appDetail);
            }

            return objArray;
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

    }

    /**
     * Method will delete the file stored either in temporary table or the content request file table
     * */
    public static void removeContentReqFile(Long fileId, Long fileType) throws HibernateException
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Transaction tx = null;

        if (fileType.intValue() == 2)
        {
            queryString.append(" FROM com.netpace.aims.model.newmarketing.AimsCreativeCreqFil creqfile ");
            queryString.append(" WHERE creqfile.fileId = ").append(fileId);
        }
        else
        {
            queryString.append(" FROM com.netpace.aims.model.core.AimsTempFile creqfile ");
            queryString.append(" WHERE creqfile.tempFileId = ").append(fileId);
        }

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.delete(queryString.toString());
            tx.commit();
            return;
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;

        }
        finally
        {
            session.close();
        }

    }
    /**
     * Method will return the name of File Ids and File Names associated with the content request
     * @see com.netpace.aims.controller.marketing.FilesDetail
     * */
    public static ArrayList getFilesDetail(Long crequestId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();

        queryString
            .append("select fd.fileId, fd.fileName , fd.comments")
            .append(" FROM ")
            .append(" com.netpace.aims.model.newmarketing.AimsCreativeCreqFil fd ")
            .append(" WHERE fd.crequestId  = ")
            .append(crequestId)
            .append(" ORDER BY fd.fileName ");

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());

            Iterator it = query.iterate();

            while (it.hasNext())
            {
                FilesDetail fd = new FilesDetail();
                objValues = (Object[]) it.next();
                fd.setFileId((Long) objValues[0]);
                fd.setFileName((String) objValues[1]);
                fd.setFileType(2);
                fd.setComments((String) objValues[2]);
                objArray.add(fd);
            }

            return objArray;
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

    }

    /**
     * Method is responsible for deleting the related data of content request
     * */
    public static void deleteContentRequest(Long crequestId) throws HibernateException
    {
        Session session = null;
        Transaction tx = null;
        StringBuffer sbFilesQuery = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.delete("FROM com.netpace.aims.model.newmarketing.AimsContentReqCnt conapp WHERE conapp.crequestId = " + crequestId.toString());
            session.delete("FROM com.netpace.aims.model.newmarketing.AimsCreativeCreqFil confile WHERE confile.crequestId = " + crequestId.toString());
            session.flush();
            session.delete("FROM com.netpace.aims.model.newmarketing.AimsCreativeCrequest conreq WHERE conreq.crequestId = " + crequestId.toString());
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }
    /**
     * Method Update the status of content request and updates the last user updated Id and updated Date
     *
     * */
    public static void updateCRStatus(String status, Long crequestId, Long userId, Long currentUserAllianceId) throws HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;

        AimsCreativeCrequest acr = getContentRequestDetail(crequestId, currentUserAllianceId, userId);
        acr.setStatus(status);
        // Approval or Declined Date
        acr.setApprovalDate(new java.util.Date());
        // Last Updated by
        acr.setApprovedBy(userId);

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(acr);
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    /**
     * Method Update the status of content request and updates the last user updated Id and updated Date
     * Called from VZW Administrator interface
     *
     * */
    public static void updateCreativeCRStatus(String status, Long crequestId, Long userId, Long currentUserAllianceId) throws HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;
        StringBuffer queryString = new StringBuffer();
        String strStatusDef;
        int iStatusToSet = 0;
        int iTotalApps = 0;
        int iAccepted = 0;

        AimsCreativeCrequest acr = getContentRequestDetail(crequestId, currentUserAllianceId, userId);
        // Approval or Declined Date
        acr.setApprovalDate(new java.util.Date());
        // Last Updated by
        acr.setApprovedBy(userId);

        queryString.append(" select acrc.status FROM ").append(" com.netpace.aims.model.newmarketing.AimsContentReqCnt acrc WHERE acrc.crequestId = ").append(
            crequestId);

        try
        {
            session = DBHelper.getInstance().getSession();

            // Decide content Request Status to be
            Query qry = session.createQuery(queryString.toString());
            Iterator it = qry.iterate();
            while (it.hasNext())
            {
                strStatusDef = (String) it.next();
                if (strStatusDef != null)
                {
                    if (iStatusToSet == 0 && strStatusDef.equals("REJECTED"))
                        iStatusToSet = 3; // Declined
                    if (iStatusToSet != 2 && strStatusDef.equals("PENDING"))
                        iStatusToSet = 1; // In Progress
                    else if (strStatusDef.equals("ACCEPTED"))
                    {
                        iStatusToSet = 2; // Partial Approve
                        iAccepted++;
                    }
                }
                else if (iStatusToSet == 3)
                    iStatusToSet = 0;
                iTotalApps++;
            } // If Iteration Ends

            if (iTotalApps == iAccepted)
                acr.setStatus("ACCEPTED");
            else
            {
                switch (iStatusToSet)
                {
                    case 3 :
                        acr.setStatus("DECLINED");
                        break;
                    case 2 :
                        acr.setStatus("PARTIAL APPROVAL");
                        break;
                    case 1 :
                        acr.setStatus("IN PROGRESS");
                        break;
                }
            }
            // Status decision ends

            tx = session.beginTransaction();
            session.saveOrUpdate(acr);
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }
    /**
     * Method will return the name of Applications in ArrayList containing object of MarketApplicationExt
     * only applicationId and appTitle attributes are populated
     * @see com.netpace.aims.controller.newmarketing.MarketApplicationExt
     * */
    public static ArrayList getAllianceAppContactList(Long crequestId, Long allianceId) throws HibernateException, Exception
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList objArray = new ArrayList();
        Long lngAllianceId = null;
        Long lngUserToApprove = null;
        Query subQuery = null;
        Iterator itSub = null;
        ArrayList aContactsList = null;

        queryString
            .append("select aa.allianceId, acc.creativeContentId, acc.contentTitle , aa.companyName, acrc.status, acrc.userStatus, acrc.userToApprove, acc.contentUsagePermission  ")
            .append(" FROM ")
            .append(" com.netpace.aims.model.newmarketing.AimsCreativeContent acc, ")
            .append(" com.netpace.aims.model.newmarketing.AimsContentReqCnt acrc, ")
            .append(" com.netpace.aims.model.core.AimsAllianc aa, ")
            .append(" com.netpace.aims.model.core.AimsUser au  ")
            .append(" WHERE acrc.crequestId = ")
            .append(crequestId)
            .append(" AND ")
            .append(" acrc.creativeContentId = acc.creativeContentId AND ")
            .append(" acc.userId= au.userId AND ")
            .append(" au.aimsAllianc = aa.allianceId ");

        //Set 'Alliance Id' if User is 'Alliance User' (i.e. allianceId != null).
        if (allianceId != null)
        {
            queryString.append(" and aa.allianceId = :allianceId ");
            queryString.append(" and acrc.userToApprove is not null ");
        }

        queryString.append(" ORDER BY acc.contentTitle ");

        try
        {
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            if (allianceId != null)
                query.setLong("allianceId", allianceId.longValue());

            Iterator it = query.iterate();

            while (it.hasNext())
            {
                AppContactsDetailsExt appDetail = new AppContactsDetailsExt();
                objValues = (Object[]) it.next();
                lngAllianceId = (Long) objValues[0];
                appDetail.setDeveloperName((String) objValues[3]);
                appDetail.setApplicationId((Long) objValues[1]);
                appDetail.setAppTitle((String) objValues[2]);
                appDetail.setContentUsagePersmission((String) objValues[7]);
                appDetail.setStatus((String) objValues[4]);
                appDetail.setUserStatus((String) objValues[5]);
                lngUserToApprove = (Long) objValues[6];
                objArray.add(appDetail);
                String subQueryString =
                    " SELECT user.userId, contact.firstName, contact.lastName, contact.title FROM com.netpace.aims.model.core.AimsUser user, com.netpace.aims.model.core.AimsContact contact WHERE user.aimsAllianc = "
                        + lngAllianceId.toString()
                        + " AND contact.contactId = user.aimsContactId ORDER BY contact.lastName , contact.firstName , contact.title ";
                subQuery = session.createQuery(subQueryString);
                itSub = subQuery.iterate();

                aContactsList = new ArrayList();

                while (itSub.hasNext())
                {
                    ContactsDetailsExt acon = new ContactsDetailsExt();
                    objValues = (Object[]) itSub.next();
                    acon.setFirstName((String) objValues[1]);
                    acon.setLastName((String) objValues[2]);
                    acon.setTitle((String) objValues[3]);
                    acon.setUserId((Long) objValues[0]);
                    // Set the user full name if the application request is already forwarded to the alliance user
                    if (lngUserToApprove != null && lngUserToApprove.equals(acon.getUserId()))
                    {
                        appDetail.setAllianceUserNameToApprove(acon.getFirstName() + " " + acon.getLastName());
                        appDetail.setAllianceUserIdToApprove(acon.getUserId().toString());
                    }
                    // Ends Setting
                    aContactsList.add(acon);
                }

                appDetail.setAllianceUsers(aContactsList);

            }

            return objArray;
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

    }

    /**
     * Method Update the status of content request and updates the last user updated Id and updated Date
     *
     * */
    public static void updateRequestedCntStatus(Long crequestId, Long creativeContentId, String strAction, Long userId) throws HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;
        Collection col = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            col =
                session.find(
                    " FROM com.netpace.aims.model.newmarketing.AimsContentReqCnt acrc WHERE acrc.crequestId = "
                        + crequestId
                        + " AND acrc.creativeContentId = "
                        + creativeContentId);
            Iterator it = col.iterator();

            if (it.hasNext())
            {
                AimsContentReqCnt acrc = (AimsContentReqCnt) it.next();
                acrc.setUserToApprove(null);
                if (strAction.equals("N"))
                    acrc.setStatus(null);
                else if (strAction.equals("A"))
                    acrc.setStatus(AimsConstants.MKT_APPLICATION_ACCEPTED);
                else if (strAction.equals("D"))
                    acrc.setStatus(AimsConstants.MKT_APPLICATION_REJECTED);
                else if (strAction.equals("F"))
                {
                    acrc.setStatus(AimsConstants.MKT_APPLICATION_PENDING);
                    acrc.setUserToApprove(userId);
                    acrc.setUserStatus(AimsConstants.MKT_APPLICATION_PENDING);
                }
                session.saveOrUpdate(acrc);
            }
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    /**
     * Method Update the status of content request (for alliance) and updates the last user updated Id and updated Date
     *
     * */
    public static void updateRequestedCntStatusForAlliance(Long crequestId, Long creativeContentId, String strAction, Long userId)
        throws HibernateException, Exception
    {
        Session session = null;
        Transaction tx = null;
        StringBuffer queryString = new StringBuffer();
        AimsContentReqCnt acrc = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            queryString
                .append("   from ")
                .append("       com.netpace.aims.model.newmarketing.AimsContentReqCnt as reqcnt ")
                .append("   where ")
                .append("       reqcnt.crequestId = :crequestId ")
                .append("       and reqcnt.creativeContentId = :creativeContentId ")
                .append("       and reqcnt.userToApprove = :userId");

            Query query = session.createQuery(queryString.toString());
            query.setLong("crequestId", crequestId.longValue());
            query.setLong("creativeContentId", creativeContentId.longValue());
            query.setLong("userId", userId.longValue());

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                log.debug("strAction: " + strAction);
                acrc = (AimsContentReqCnt) it.next();
            }

            if (strAction.equals("A"))
            {
                acrc.setUserStatus(AimsConstants.MKT_APPLICATION_ACCEPTED);
                acrc.setStatus(AimsConstants.MKT_APPLICATION_ACCEPTED);
            }
            else if (strAction.equals("D"))
            {
                acrc.setUserStatus(AimsConstants.MKT_APPLICATION_REJECTED);
                acrc.setStatus(AimsConstants.MKT_APPLICATION_REJECTED);
            }

            session.saveOrUpdate(acrc);

            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    /**
     * Method Update the status of content request application by Alliance User
     *
     * */
    public static void updateAllianceRequestedAppStatus(Long crequestId, Long appsId, String status, Long userId)
        throws HibernateException, AimsSecurityException
    {
        Session session = null;
        Transaction tx = null;
        Collection col = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();

            col =
                session.find(
                    " FROM com.netpace.aims.model.marketing.AimsContentReqApp conapp WHERE conapp.crequestId = "
                        + crequestId
                        + " AND conapp.appsId = "
                        + appsId);
            Iterator it = col.iterator();

            if (it.hasNext())
            {
                AimsContentReqApp conapp = (AimsContentReqApp) it.next();
                if (conapp.getUserToApprove() == null || !conapp.getUserToApprove().equals(userId))
                    throw new AimsSecurityException();
                conapp.setUserStatus(status);
                session.saveOrUpdate(conapp);
            }
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    /**
     * Method return the listing of Content Request Awaiting Approval. From Alliance Users
     * */
    public static Collection getConReqForAllianceApprovals(Long lngUserId, int PAGE_LENGTH, int page_no) throws HibernateException
    {
        Session session = null;
        StringBuffer queryString = new StringBuffer();
        Object[] objValues;
        ArrayList listResults = new ArrayList();

        queryString
            .append("select app.appsId, app.title, creq.crequestId, creq.programName, creq.projectStartDate, creq.submittedDate, creq.status, conapp.userStatus ")
            .append(" FROM com.netpace.aims.model.marketing.AimsContentReqApp conapp, com.netpace.aims.model.marketing.AimsContentRequest creq ")
            .append(" ,com.netpace.aims.model.application.AimsApp app ")
            .append(" WHERE conapp.userToApprove = ")
            .append(lngUserId)
            .append(" AND creq.status <> '")
            .append(AimsConstants.MKT_REQUEST_SAVED)
            .append("' AND creq.crequestId = conapp.crequestId ")
            .append(" AND conapp.appsId = app.appsId ")
            .append(" ORDER BY creq.submittedDate, creq.programName ");

        try
        {
            //Set	Paging Logic and execute Query
            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery(queryString.toString());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (page_no - 1));
            Iterator it = query.iterate();

            while (it.hasNext())
            {
                ContentRequestApplicationExt crae = new ContentRequestApplicationExt();
                objValues = (Object[]) it.next();
                crae.setApplicationId((Long) objValues[0]);
                crae.setAppTitle((String) objValues[1]);
                crae.setCrequestId((Long) objValues[2]);
                crae.setProgramName((String) objValues[3]);
                crae.setProjectStartDate((Date) objValues[4]);
                crae.setDateSubmitted((Date) objValues[5]);
                crae.setStatus((String) objValues[6]);
                crae.setUserStatus((String) objValues[7]);
                listResults.add(crae);
            }
            return listResults;

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

    }
    /**
     * Returns the count of Content Request Submitted For Approval with Alliance Users
     * */
    public static int getRecCountForAllianceApprovals(Long lngUserId) throws HibernateException
    {
        int rows = 0;
        Collection collection = null;
        Session session = null;
        StringBuffer queryString = new StringBuffer();

        try
        {
            session = DBHelper.getInstance().getSession();

            queryString
                .append("select creq.crequestId ")
                .append(" FROM com.netpace.aims.model.marketing.AimsContentReqApp conapp, com.netpace.aims.model.marketing.AimsContentRequest creq ")
                .append(" ,com.netpace.aims.model.application.AimsApp app ")
                .append(" WHERE conapp.userToApprove = ")
                .append(lngUserId)
                .append(" AND creq.status <> '")
                .append(AimsConstants.MKT_REQUEST_SAVED)
                .append("' AND creq.crequestId = conapp.crequestId ")
                .append(" AND conapp.appsId = app.appsId ");

            collection = session.find(queryString.toString());
            log.debug("No of requests	records:	" + collection.size());
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

    // Class Ends
}
