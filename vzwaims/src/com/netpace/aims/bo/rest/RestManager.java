package com.netpace.aims.bo.rest;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsWapFtpLog;
import com.netpace.aims.model.rest.AimsRestModules;
import com.netpace.aims.model.rest.AimsRestUsers;
import com.netpace.aims.model.rest.AimsRestUrlLog;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class RestManager {

    private static Logger log = Logger.getLogger(RestManager.class.getName());

    public static boolean validateRestUser(Long userID, String moduleName)
            throws Exception {
        boolean validated = false;
        AimsRestUsers restUser = null;
        Collection userModules = null;

        //get user
        restUser = RestManager.getRestUser(userID);
        if(restUser != null ) {
            //if user found then get user modules
            userModules = RestManager.getRestUserModulesByModuleName(userID, moduleName);
            if(userModules!=null && userModules.size()>0 ) {
                //if module found, user is allowed to change status of file in this module
                validated = true;
            }
        }
        return validated;
    }

    public static Collection getRestUserModulesByModuleName(Long userID, String moduleName)
        throws HibernateException {

        Collection userModules = new ArrayList();        
        Object[] userValues = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsRestModules restModule = null;
        Session session = null;
        Query query = null;
        try {
            session = DBHelper.getInstance().getSession();
            queryStringBuffer
                .append("select ")
                .append("       restModule.moduleId, restModule.moduleName, restModule.moduleDescription ")
                .append("from ")
                .append("       com.netpace.aims.model.rest.AimsRestModuleUsers moduleUser, ")
                .append("       com.netpace.aims.model.rest.AimsRestModules restModule ")
                .append("where ")
                .append("       moduleUser.userId = :userId ")
                .append("       and moduleUser.modules.moduleId = restModule.moduleId ")
                .append("       and moduleUser.modules.moduleName = :moduleName ")
                .append("order by  ")
                .append("       restModule.moduleId  ");

            session = DBHelper.getInstance().getSession();
            query = session.createQuery(queryStringBuffer.toString());
            query.setLong("userId", userID.longValue());
            query.setString("moduleName", moduleName);


            for (Iterator it = query.iterate(); it.hasNext();) {
                userValues = (Object [])it.next();
                restModule =   new AimsRestModules();
                restModule.setModuleId((Long)userValues[0]);
                restModule.setModuleName((String)userValues[1]);
                restModule.setModuleDescription((String)userValues[2]);
                userModules.add(restModule);
            }
        }
        catch (HibernateException he) {
            he.printStackTrace();
        }
        finally {
            session.close();
        }
        return userModules;
    }

    public static AimsRestUsers getRestUser (Long userID)
            throws HibernateException {
        AimsRestUsers restUser = null;
        Session session = null;
        Query query = null;
        try {
            session = DBHelper.getInstance().getSession();
            query =
                session.createQuery(
                    "select from com.netpace.aims.model.rest.AimsRestUsers as restUser where restUser.userId = :userId");
            query.setLong("userId", userID.longValue());
            for (Iterator it = query.iterate(); it.hasNext();) {
                restUser = (AimsRestUsers) it.next();
                log.debug("Rest User: " + restUser.toString());
            }
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            session.close();
        }
        return restUser;
    }


    public static AimsWapFtpLog getWapFTPLog(String ftpFileName)
                    throws HibernateException {
        StringBuffer queryStringBuffer = new StringBuffer();
        AimsWapFtpLog wapFTPLog = null;
        Session session = null;
        Query query = null;
        try {
            queryStringBuffer.append("select from com.netpace.aims.model.application.AimsWapFtpLog as wapFTPLog ");
            queryStringBuffer.append(" where wapFTPLog.ftpFileName = :ftpFileName ");

            session = DBHelper.getInstance().getSession();
            query = session.createQuery(queryStringBuffer.toString());
            query.setString("ftpFileName", ftpFileName);
            for (Iterator it = query.iterate(); it.hasNext();) {
                //return last FTP Log value
                wapFTPLog = (AimsWapFtpLog) it.next();
            }
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw he;
        }
        finally {
            session.close();
        }
        return wapFTPLog;
    }

    /**
	*	 This	static method	save or update	a	given	wap ftp log.
	*/
	public static	void saveOrUpdateWapFTPLog(AimsWapFtpLog wapFTPLog)
                throws	HibernateException
	{
		Session	session	=	null;
        Transaction	tx = null;
        try
		{
			session	=	DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(wapFTPLog);
            tx.commit();
        }
		catch(HibernateException he)
		{
            tx.rollback();
            he.printStackTrace();
			throw he;
		}
		finally
		{
            session.close();
		}
	}

    /**
	*	 This	static method	save or update	a	given	Rest URL log object.
	*/
	public static	void saveOrUpdateRestURLLog(AimsRestUrlLog restURLLog)
                throws	HibernateException
	{
		Session	session	=	null;
        Transaction	tx = null;
        try
		{
			session	=	DBHelper.getInstance().getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(restURLLog);
            tx.commit();
        }
		catch(HibernateException he)
		{
            tx.rollback();
            he.printStackTrace();
			throw he;
		}
		finally
		{
            session.close();
		}
	}
}
