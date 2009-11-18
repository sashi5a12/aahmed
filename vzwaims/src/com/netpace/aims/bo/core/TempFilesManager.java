package com.netpace.aims.bo.core;

import java.util.Date;
import java.util.Iterator;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.StringType;
import oracle.jdbc.driver.OracleCallableStatement;
import oracle.sql.BLOB;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsTempFile;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.TempFile;

/**
 * This	class	is responsible for handling database related functions for the 
 * table AIMS_TEMP_FILES
 * @author Adnan Makda
 */
public class TempFilesManager
{

    static Logger log = Logger.getLogger(TempFilesManager.class.getName());

    /**
    	* This	method cleans files from AIMS_TEMP_FILES table.
    	* It accepts a String parameter (sessionId)
    	*/
    public static void cleanTempFiles(String sessionId)
    {
        int delCount = 0;
        Session session = null;
        Transaction tx = null;

        try
        {
            session = DBHelper.getInstance().getSession();

            tx = session.beginTransaction();
            delCount =
                session.delete(
                    " from com.netpace.aims.model.core.AimsTempFile as tempFile where tempFile.sessionId = :sessionId ",
                    sessionId,
                    new StringType());
            tx.commit();
        }
        catch (Exception ex)
        {
            try
            {
                if (tx != null)
                    tx.rollback();
            }
            catch (Exception ex1)
            {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        }

        finally
        {
            try
            {
                session.close();
            }
            catch (Exception ex2)
            {
                ex2.printStackTrace();
            }
            log.debug("SESSION CLOSED IN cleanTempFiles()");
        }

    }

    /**
    	* This	method saves file to temp table and destroys the FormFile
    	* It returns a class containing information about the file (Id, Name & Content Type)
    	*/
    public static TempFile saveTempFile(FormFile formFile, String sessionId, String currUser) throws HibernateException, Exception
    {
        return saveTempFile(formFile, sessionId, currUser, "");
    }

    /**
    	* This	method also saves file to temp table and destroys the FormFile with extra parameter comments
    	* It returns a class containing information about the file (Id, Name & Content Type)
    	*/
    public static TempFile saveTempFile(FormFile formFile, String sessionId, String currUser, String comments) throws HibernateException, Exception
    {
        TempFile tempFile = null;

        if ((formFile != null) && (formFile.getFileSize() > 0))
        {
            Session session = null;
            Transaction tx = null;

            try
            {
                session = DBHelper.getInstance().getSession();
                tx = session.beginTransaction();
                byte[] buffer = new byte[1];
                buffer[0] = 1;
                long bytesWrote = 0;

                AimsTempFile aimsTempFile = new AimsTempFile();
                aimsTempFile.setSessionId(sessionId);
                aimsTempFile.setTempFileName(formFile.getFileName());
                aimsTempFile.setTempFileContentType(formFile.getContentType());
                aimsTempFile.setTempFile(Hibernate.createBlob(buffer));
                aimsTempFile.setCreatedBy(currUser);
                aimsTempFile.setCreatedDate(new Date());
                aimsTempFile.setTempFileComments(comments);
                aimsTempFile.setTempFileSize(new Long(formFile.getFileSize()));

                session.saveOrUpdate(aimsTempFile);

                session.flush();
                session.refresh(aimsTempFile, LockMode.UPGRADE);

                bytesWrote = LobUtils.writeToOraBlob((BLOB) aimsTempFile.getTempFile(), formFile.getInputStream());

                session.flush();
                tx.commit();

                tempFile = new TempFile();
                tempFile.setFileId(aimsTempFile.getTempFileId());
                tempFile.setFileName(aimsTempFile.getTempFileName());
                tempFile.setFileContentType(aimsTempFile.getTempFileContentType());
                tempFile.setFileSize(aimsTempFile.getTempFileSize());

                log.debug("bytesWrote: " + bytesWrote);

            }
            catch (HibernateException e)
            {
                if (tx != null)
                    tx.rollback();
                log.debug("ROLLED BACK IN saveTempFile()");
                e.printStackTrace();
                throw e;
            }
            finally
            {
                session.close();
                formFile.destroy(); //Destroy Form File
                log.debug("SESSION CLOSED IN saveTempFile()");
            }
        }

        return tempFile;

    }
    /**
    	* This	method gets record from the database
    	*/

    public static AimsTempFile getTempFile(Long tempFileId, String userId) throws HibernateException
    {

        AimsTempFile tempFile = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();

            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append(" select    tempFile ")
                .append(" from      com.netpace.aims.model.core.AimsTempFile as tempFile ")
                .append(" where     tempFile.tempFileId = :tempFileId ")
                .append(" and       tempFile.createdBy = :userId");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("tempFileId", tempFileId.longValue());
            query.setString("userId", userId);

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                tempFile = (AimsTempFile) it.next();
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

        return tempFile;

    }

    /**
     * returns fileSize in bytes of TempFile
     * @param tempFileId
     * @param userId
     * @return
     * @throws HibernateException
     */
    public static Long getTempFileSize(Long tempFileId, String userId) throws HibernateException
    {
        Long tempFileSize = null;

        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();

            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer
                .append(" select    tempFile.tempFileSize ")
                .append(" from      com.netpace.aims.model.core.AimsTempFile as tempFile ")
                .append(" where     tempFile.tempFileId = :tempFileId ")
                .append(" and       tempFile.createdBy = :userId");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("tempFileId", tempFileId.longValue());
            query.setString("userId", userId);

            for (Iterator it = query.iterate(); it.hasNext();)
            {
                tempFileSize = (Long) it.next();
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
            log.debug("Session closed in TempFilesManager.getTempFileSize()");
        }

        return tempFileSize;

    }

    /**
    	* This	method calls the stored procedure that copies file from the 'temp table'
    	* to the desired table. This stored procedure then deletes the file from the 'temp table'
    	* Parameter String[] dbInfo: Example ManageApplicationsConstants.FAQ_DOC_BLOB_DB_INFO
    	*/

    //Copies Images From AIMS_TEMP_FILES table
    public static void copyImageFromTemp(java.sql.Connection ConOra, Long tempFileId, Long primaryId, String userId, String[] dbInfo) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            if ((tempFileId != null) && (tempFileId.longValue() > 0))
            {
                /*  
                Database Procedure SnapShot :
                
                AIMS_LOB_UTILS.copy_lob_from_temp_table(?,?,?,?,?)
                 
                Parameters:
                 
                p_temp_table_id          IN  number,    -- primary key of the temp table
                p_pk_expr_to_table       IN  varchar2,  -- expression in the form pkid = value
                p_to_table_name          IN  varchar2,  -- name of the table in which the record will be updated
                p_to_table_col_name      IN  varchar2,  -- name of the column of the table in which the record will be updated
                p_user_id  
                */
                statement = ConOra.prepareCall("call AIMS_LOB_UTILS.copy_lob_from_temp_table(?,?,?,?,?)");
                statement.setLong(1, tempFileId.longValue());
                statement.setString(2, dbInfo[2] + " = " + primaryId);
                statement.setString(3, dbInfo[1]);
                statement.setString(4, dbInfo[0]);
                statement.setString(5, userId);
                statement.execute();
            }
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

    //Overloaded function for tables with composite primary keys 
    public static void copyImageFromTemp(java.sql.Connection ConOra, Long tempFileId, Long primaryId, Long[] otherCompositeId, String userId, String[] dbInfo)
        throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            if ((tempFileId != null) && (tempFileId.longValue() > 0))
            {
                statement = ConOra.prepareCall("call AIMS_LOB_UTILS.copy_lob_from_temp_table(?,?,?,?,?)");
                statement.setLong(1, tempFileId.longValue());

                StringBuffer strParamPrimary = new StringBuffer();
                strParamPrimary.append(dbInfo[2]).append(" = ").append(primaryId);

                if (otherCompositeId != null)
                {
                    for (int i = 0; i < otherCompositeId.length; i++)
                    {
                        strParamPrimary.append(" and ").append(dbInfo[3 + i]).append(" = ").append(otherCompositeId[i]);
                    }
                }

                statement.setString(2, strParamPrimary.toString());
                statement.setString(3, dbInfo[1]);
                statement.setString(4, dbInfo[0]);
                statement.setString(5, userId);
                statement.execute();
            }
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

    public static void cloneImages(java.sql.Connection ConOra, Long clonedFromAppId, Long appsId, Long platformId) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            statement = ConOra.prepareCall("call AIMS_LOB_UTILS.clone_images(?,?,?)");
            statement.setLong(1, clonedFromAppId.longValue());
            statement.setLong(2, appsId.longValue());
            statement.setLong(3, platformId.longValue());
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

    public static void checkForEmptyBlobs(java.sql.Connection ConOra, Long appsId) throws AimsException, Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            statement = ConOra.prepareCall("call AIMS_LOB_UTILS.check_uploaded_files(?,?)");
            statement.setLong(1, appsId.longValue());
            statement.registerOutParameter(2, java.sql.Types.VARCHAR);
            statement.execute();

            String resultString = ((OracleCallableStatement) statement).getString(2);

            if ((resultString != null) && (resultString.length() > 0))
            {
                AimsException aimsException = new AimsException("Error");
                AimsException mainAimsException = new AimsException("error.uploading.files");
                Object[] args = { resultString };
                mainAimsException.setMessageArgs(args);
                aimsException.addException(mainAimsException);
                System.out.println("AM: Exception in checkForEmptyBlobs() for appsId: " + appsId + ". Problem Columns are: " + resultString);
                throw aimsException;
            }
        }
        catch (AimsException ax)
        {
            throw ax;
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

    public static void copyLobFromToTable(java.sql.Connection ConOra, String[] fromTableInfo, Long fromTablePrimaryId,
                                             String[] toTableInfo, Long toTablePrimaryId) throws Exception
    {
        java.sql.CallableStatement statement = null;

        try
        {
            /*
                Database Procedure SnapShot :

                AIMS_LOB_UTILS.copy_lob_from_to_table(?,?,?,?,?,?)

                Parameters:

                p_from_table_col_name     IN  varchar2,  -- name of the column of the table which will be selected for update.
                p_from_table_name         IN  varchar2,  -- name of table which record will be selected for update.
                p_pk_expr_from_table      IN  varchar2,  -- expression in the form pkid = value
                p_to_table_col_name       IN  varchar2,  -- name of the column of the table in which the record will be updated
                p_to_table_name           IN  varchar2,  -- name of the table in which the record will be updated
                p_pk_expr_to_table        IN  varchar2   -- expression in the to pkid = value

            */

            statement = ConOra.prepareCall("call AIMS_LOB_UTILS.copy_lob_from_to_table(?,?,?,?,?,?)");
            statement.setString(1, fromTableInfo[0]);//from table columnName
            statement.setString(2, fromTableInfo[1]);//from table name
            statement.setString(3, (fromTableInfo[2]+"="+fromTablePrimaryId.toString()));//from primary expression
            statement.setString(4, toTableInfo[0]);//to table columnName
            statement.setString(5, toTableInfo[1]);//to table name
            statement.setString(6, (toTableInfo[2]+"="+toTablePrimaryId.toString()));//to primary expression

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

}