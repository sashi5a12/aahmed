package com.netpace.aims.bo.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;
import oracle.sql.BLOB;

import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.controller.tools.AimsToolsExt;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.model.tools.AimsTool;
import com.netpace.aims.model.tools.AimsToolFetchXlsLog;
import com.netpace.aims.model.tools.AimsToolFiles;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.LobUtils;

/**
 * This	class is responsible for getting the details for Tools.
 * It has static methods for getting the Tools Details.
 * @author Ahson Imtiaz
 */
public class AimsToolsManager
{

    static Logger log = Logger.getLogger(AimsToolsManager.class.getName());
    private static final String FILE_NAME_BLOB_DB_INFO[] = { "TOOL_FILE", "AIMS_TOOLS", "TOOL_ID" };
    /*
     *Collection will be returned having all tools in the system
     **/
    public static Collection getAimsTools() throws HibernateException
    {

        Session session = null;
        Collection collection = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            collection = session.find("from com.netpace.aims.model.tools.AimsTool tool order by tool.toolName");

            log.debug("No of records returned: " + collection.size());

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

    public static int getAimsToolsCount() throws HibernateException
    {

        Session session = null;
        int rows = 0;

        try
        {

            session = DBHelper.getInstance().getSession();
            Query query = session.createQuery("select count(*) from com.netpace.aims.model.tools.AimsTool tool order by tool.toolName");
            rows = ((Integer) query.iterate().next()).intValue();
            log.debug("Rows Counted : " + rows);

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

    /*
     *Collection will be returned specific tool detail from the system
     **/
    public static AimsTool getAimsTool(java.lang.Long lngToolId) throws HibernateException
    {

        Session session = null;
        Collection collection = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            return (AimsTool) session.find("from com.netpace.aims.model.tools.AimsTool atools where atools.toolId = " + lngToolId.toString()).get(0);

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
    public static AimsToolFiles getAimsToolFiles(java.lang.Long lngToolId) throws HibernateException
    {
    	
    	Session session = null;
    	
    	try
    	{
    		
    		session = DBHelper.getInstance().getSession();
    		Query query=session.createQuery("from com.netpace.aims.model.tools.AimsToolFiles atools where atools.toolId = :id");
    		query.setLong("id", lngToolId.longValue());
    		return (AimsToolFiles) query.list().get(0);
    		
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

    /*
     * This overloaded function is called from ToolsResourceAction, to fetch Tool only if it matches the 
     * criteria, that is used for displaying all the tools. This filter criteria is only for Alliance Users
     */
    public static AimsToolFiles getAimsTool(Long lngToolId, Long allianceId) throws HibernateException
    {
    	AimsToolFiles toolFiles = null;
        Session session = null;
        try
        {        	
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();
            queryStringBuffer
                .append("select tool ")
                .append("from    ")
                .append("       com.netpace.aims.model.tools.AimsToolFiles tool ")
                .append("where ")
                .append("       tool.toolId in ")
                .append("       ( ")
                .append("           select tool.toolId ")
                .append("           from    ")
                .append("               com.netpace.aims.model.tools.AimsToolFiles tool, ")
                .append("               com.netpace.aims.model.tools.AimsToolContract toolContract, ")
                .append("               com.netpace.aims.model.core.AimsAllianceContract allianceContract ")
                .append("           where ")
                .append("               tool.toolId = :toolId ")
                .append("               and allianceContract.allianceId = :allianceId ")
                .append("               and toolContract.toolId = tool.toolId ")
                .append("               and toolContract.aimsContract.contractId = allianceContract.contractId ")
                .append("               and tool.showAll = 'N' ")
                .append("       )")
                .append("       or tool.toolId in ")
                .append("       (")
                .append("           select tool.toolId ")
                .append("           from    ")
                .append("               com.netpace.aims.model.tools.AimsToolFiles tool, ")
                .append("               com.netpace.aims.model.alliance.AimsAlliancePlatform alliancePlatform, ")
                .append("               com.netpace.aims.model.core.AimsAllianc alliance ")
                .append("           where ")
                .append("               tool.toolId = :toolId ")
                .append("               and tool.showAll = 'Y' ")
                .append("               and alliance.allianceId = :allianceId ")
                .append("               and alliance.status = '" + AimsConstants.ALLIANCE_STATUS_ACCEPTED + "'")
                .append("               and alliance.allianceId = alliancePlatform.allianceId ")
                .append("               and alliancePlatform.platforms.platformId in (elements(tool.platform)) ")
                .append("       )")
                .append("       or tool.toolId in ")
                .append("       (")
                .append("           select tool.toolId ")
                .append("           from    ")
                .append("               com.netpace.aims.model.tools.AimsToolFiles tool ")
                .append("           where ")
                .append("               tool.toolId = :toolId ")
                .append("               and tool.showAll = 'Y' ")
                .append("               and 'A' = (select a.status from AimsAllianc a where a.allianceId=:allianceId) ")
                .append("       )");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setLong("allianceId", allianceId.longValue());
            query.setLong("toolId", lngToolId.longValue());
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                toolFiles = (AimsToolFiles) it.next();
            }
        }
        catch (HibernateException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return toolFiles;

    }

    /*
     *
     *  */
    public static int deleteTool(java.lang.Long toolId) throws HibernateException
    {

        int delCount = 0;
        Session session = null;
        try
        {

            session = DBHelper.getInstance().getSession();
            Transaction tx = session.beginTransaction();
            delCount = session.delete("from com.netpace.aims.model.tools.AimsTool as tool where tool.toolId = :toolId", toolId, new LongType());

            tx.commit();
            log.debug("Rows affected by deletion: " + delCount);

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

        return delCount;
    }

    /**
    *  START OF CATEGORIES RELATED METHODS
    */

    public static Collection getCategories() throws HibernateException
    {

        Collection collection = null;
        Session session = null;
        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryString = new StringBuffer();
            queryString.append(" from com.netpace.aims.model.tools.AimsToolCatMaster category order by lower(category.categoryName) ");

            collection = session.find(queryString.toString());

            log.debug("No of records returned: " + collection.size());
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

    /* */
    public static Collection getAimsCategories(java.lang.Long[] lngCategoriesIds) throws HibernateException
    {
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            String strQuery = "from com.netpace.aims.model.tools.AimsToolCatMaster as acat ";

            for (int iCount = 0; lngCategoriesIds != null && iCount < lngCategoriesIds.length; iCount++)
            {
                if (iCount == 0)
                    strQuery += " where acat.categoryId = " + lngCategoriesIds[iCount].toString();
                else
                    strQuery += " OR acat.categoryId = " + lngCategoriesIds[iCount].toString();
            }

            log.debug("*********** Categories Query: " + strQuery);
            return session.find(strQuery);

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

    /*
     ***/

    public static java.lang.Long saveOrUpdate(AimsTool aTool, Long fileNameTempId, String userId) throws HibernateException, AimsException, Exception
    {

        Session session = null;
        java.lang.Long toolId = null;
        Transaction tx = null;

        try
        {

            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(aTool);
            session.flush();            
            java.sql.Connection ConOra = session.connection();
            
            TempFilesManager.copyImageFromTemp(ConOra, fileNameTempId, aTool.getToolId(), userId, FILE_NAME_BLOB_DB_INFO);            
            tx.commit();
            toolId = aTool.getToolId();

        }
        catch (HibernateException e)
        {
            if (tx != null)
                tx.rollback();
            if (e.getMessage().indexOf("ORA-00001:") != -1){
            	AimsException aimsException=new AimsException("error.ToolsForm.toolName.duplicate");
            	throw aimsException;
            }            
            e.printStackTrace();
            throw e;
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

        return toolId;
    }

    public static ArrayList getToolsList(int PAGE_LENGTH, int pageNo) throws HibernateException
    {
        AimsApp aimsApp = null;
        Session session = null;
        ArrayList hsTools = new ArrayList();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer.append(" 	select tool from com.netpace.aims.model.tools.AimsTool as tool order by upper(tool.toolName)");

            Query query = session.createQuery(queryStringBuffer.toString());
            query.setMaxResults(PAGE_LENGTH);
            query.setFirstResult(PAGE_LENGTH * (pageNo - 1));

            Object[] resultValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {            	
                AimsTool atool = (AimsTool) it.next();
                AimsToolsExt aacsex = new AimsToolsExt();
                
                String showAll=atool.getShowAll();
                Set platforms=atool.getPlatform();
                Set contracts=atool.getAimsContracts();
                
                if (("Y".equalsIgnoreCase(showAll)) && (platforms == null || platforms.size()==0)){
                	platforms=new HashSet();
                	AimsPlatform platform=new AimsPlatform();
                	platform.setPlatformName("ALL");
                	platforms.add(platform);
                }
                else if (("N".equalsIgnoreCase(showAll)) && (contracts != null) && (contracts.size()>0)){                	
                	Iterator contractsItr=contracts.iterator();
                	platforms=new HashSet();
                	while(contractsItr.hasNext()){
                		AimsContract contract=(AimsContract)contractsItr.next();
                		AimsPlatform platform=new AimsPlatform();
                		platform.setPlatformId(contract.getAimsPlatform().getPlatformId());
                		platform.setPlatformName(contract.getAimsPlatform().getPlatformName()+"*");
                		platforms.add(platform);
                	}
                }
                aacsex.setPlatforms(platforms);
                
                aacsex.setToolName(atool.getToolName());
                aacsex.setToolId(atool.getToolId());
                aacsex.setCategories(atool.getAimsAppCategories());                
                aacsex.setCreatedDate(atool.getCreatedDate());
                hsTools.add(aacsex);
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
            log.debug("SESSION CLOSED IN AimsToolsManager getToolsList()");
        }

        return hsTools;
    }

    /**/
    public static ArrayList getToolsList() throws HibernateException
    {
        AimsApp aimsApp = null;
        Session session = null;
        ArrayList hsTools = new ArrayList();

        try
        {
            session = DBHelper.getInstance().getSession();
            StringBuffer queryStringBuffer = new StringBuffer();

            queryStringBuffer.append(" 	select ").append("	tool ").append(" from ").append(" com.netpace.aims.model.tools.AimsTool	as tool  ").append(
                "	order by upper(tool.toolName)");

            Query query = session.createQuery(queryStringBuffer.toString());
            Object[] resultValues = null;
            for (Iterator it = query.iterate(); it.hasNext();)
            {
                AimsTool atool = (AimsTool) it.next();
                AimsToolsExt aacsex = new AimsToolsExt();
                aacsex.setToolName(atool.getToolName());
                aacsex.setToolId(atool.getToolId());
                aacsex.setCategories(atool.getAimsAppCategories());
                aacsex.setCreatedDate(atool.getCreatedDate());
                hsTools.add(aacsex);
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
            log.debug("SESSION CLOSED IN AimsToolsManager getToolsList()");
        }

        return hsTools;
    }
    
    public static void updateFetchXlsRecord(byte[] fileData, String fileName) throws HibernateException, IOException, SQLException{
    	log.debug("AimsToolsManager.updateFetchXlsRecord Start: fileName="+fileName);
    	Session session=null;
    	Transaction trx=null;
    	ByteArrayInputStream inputStream1=null;
    	ByteArrayInputStream inputStream2=null;
    	try{
    		inputStream1=new ByteArrayInputStream(fileData);
    		inputStream2=new ByteArrayInputStream(fileData);
    		
            session = DBHelper.getInstance().getSession();
            trx = session.beginTransaction();
            byte[] buffer = new byte[1];
            buffer[0] = 1;
            long bytesWrote = 0;
           
            AimsToolFiles toolFile=(AimsToolFiles)session.load(AimsToolFiles.class, new Long(1));
            toolFile.setFileName(fileName);
            toolFile.setToolFileContentType("application/vnd.ms-excel");
            toolFile.setToolFile(Hibernate.createBlob(buffer));
            session.saveOrUpdate(toolFile);

            session.flush();
            session.refresh(toolFile, LockMode.UPGRADE);

            bytesWrote = LobUtils.writeToOraBlob((BLOB) toolFile.getToolFile(), inputStream1);
            log.debug("bytesWrote: " + bytesWrote);
            
            AimsToolFetchXlsLog xlsLog=new AimsToolFetchXlsLog();
            xlsLog.setDescription("File saved successfully. File name is: "+fileName+" and size is: "+fileData.length);
            xlsLog.setCreatedBy("system");
            xlsLog.setCreatedDate(new Date());
            xlsLog.setFetchFile(Hibernate.createBlob(buffer));            
            session.saveOrUpdate(xlsLog);
            
            session.flush();
            session.refresh(xlsLog, LockMode.UPGRADE);
            
            bytesWrote = LobUtils.writeToOraBlob((BLOB) xlsLog.getFetchFile(), inputStream2);
            log.debug("bytesWrote: " + bytesWrote);
            
            trx.commit();            

        }
        catch (HibernateException e){
            if (trx != null)
                trx.rollback();
            log.error(e,e);
            throw e;
        }
        finally{
            session.close();
            if (inputStream1 != null){
            	inputStream1.close();
            }
            if (inputStream2 != null){
            	inputStream2.close();
            }
        }
        	
    	log.debug("AimsToolsManager.updateFetchXlsRecord End:");
    }
}