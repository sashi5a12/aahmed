package com.netpace.aims.bo.security;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.alliance.AimsBulletin;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.type.LongType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AimsBulletinManager
{
    public static Long getUnreadBulletin(Long userId)
    {
        Session session = null;
        Connection ConOra = null;
        Transaction tx = null;
        CallableStatement statement = null;
        Long bulletinId = new Long(0);

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_BULLETIN_PKG.check_if_bulletin_viewed(?,?)");
            statement.setLong(1, userId.longValue());
            statement.registerOutParameter(2, java.sql.Types.INTEGER);
            statement.execute();
            bulletinId = new Long(statement.getInt(2));
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
            {}
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (Exception ex2)
            {}
            try
            {
                session.close();
            }
            catch (Exception ex3)
            {}
        }
        return bulletinId;
    }

    /**
     * This method returns bulletin ids in a comma separated string
     * @param userId
     * @return
     */
    public static String getUnreadBulletinIdsStr(Long userId)
    {
        Session session = null;
        Connection ConOra = null;
        Transaction tx = null;
        CallableStatement statement = null;
        String bulletinIdsStr = "";

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_BULLETIN_PKG.get_bulletins_to_show(?,?)");
            statement.setLong(1, userId.longValue());
            statement.registerOutParameter(2, java.sql.Types.VARCHAR);
            statement.execute();
            bulletinIdsStr = statement.getString(2);
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
            {}
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (Exception ex2)
            {}
            try
            {
                session.close();
            }
            catch (Exception ex3)
            {}
        }
        return bulletinIdsStr;
    }//end getUnreadBulletinIdsStr

    public static Object [] getBulletinResource(Long bulletinResourceId) throws HibernateException
    {
        Session session = null;
        Collection collection = null;
        StringBuffer queryStringBuffer = new StringBuffer();
        Object [] resourceValues = null;
        String resourceName = "bulletinResource";
        try
        {
            queryStringBuffer.append("select ");
            queryStringBuffer.append("		aimsBulletinResource.bulletinId, ")
                             .append("		aimsBulletinResource.").append(resourceName).append(", ")
                             .append("		aimsBulletinResource.").append(resourceName).append("FileName, ")
                             .append("		aimsBulletinResource.").append(resourceName).append("ContentType ")
                             .append("from ")
                             .append("		com.netpace.aims.model.alliance.AimsBulletinResources aimsBulletinResource ")
                             .append("where ")
                             .append("		aimsBulletinResource.bulletinResourceId = :bulletinResourceId ");

            //log.debug("getBulletinResource " + queryStringBuffer);
            session = DBHelper.getInstance().getSession();
            collection = session.find(queryStringBuffer.toString(), bulletinResourceId,  new LongType());
            for (Iterator iter = collection.iterator(); iter.hasNext();)
            {
                resourceValues  = (Object []) iter.next();
            }
        }
        catch(HibernateException he)
        {
            he.printStackTrace();
            throw he;
        }
        finally
        {
            if (session != null)
            {
                session.close();
            }
        }
        return resourceValues;
    }

    /**
     * This method sets next bulletin to be shown, which is stored in session
     * @param request
     * @param bulletinIds
     * @throws Exception
     */
    public static void setNextBulletinDetails(HttpServletRequest request, List bulletinIds) throws Exception
    {
        if(bulletinIds!=null && bulletinIds.size()>0)
            {
                Long bulletinId = (Long)bulletinIds.get(0);
                HttpSession session = request.getSession();
                if ( (bulletinId!=null) && (bulletinId.longValue() != 0))
                {
                    AimsBulletin bulletin =  (AimsBulletin) DBHelper.getInstance().load(AimsBulletin.class, bulletinId.toString());
                    session.setAttribute(AimsConstants.SESSION_BULLETIN_TO_READ, bulletinId);                    

                    request.setAttribute(AimsConstants.REQUEST_BULLETIN_HEADER, StringFuncs.NullValueReplacement(bulletin.getBulletinHeader()));
                    request.setAttribute(AimsConstants.REQUEST_BULLETIN_TEXT, LobUtils.clobToString(bulletin.getBulletinText()));
                    request.setAttribute(AimsConstants.REQUEST_BULLETIN_POST_ACTION, StringFuncs.NullValueReplacement(bulletin.getBulletinPostAction()));

                }
                //remove current bulletin from list after setting its attribute in request and session
                bulletinIds.remove(0);
                //check bulletin size to set next bulletins in session
                if(bulletinIds.size()>0)
                {
                    session.setAttribute(AimsConstants.SESSION_BULLETIN_IDS_TO_READ, bulletinIds);
                }
                else
                {
                    //all bulletins are viewed, now remove bulletinIds from session
                    session.removeAttribute(AimsConstants.SESSION_BULLETIN_IDS_TO_READ);
                }
            }
    }//end setNextBulletinDetails

    public static Long updateBulletinCounter(Long userId, Long bulletinId)
    {
        Session session = null;
        Connection ConOra = null;
        Transaction tx = null;
        CallableStatement statement = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_BULLETIN_PKG.update_bulletin_counter(?,?)");
            statement.setLong(1, userId.longValue());
            statement.setLong(2, bulletinId.longValue());
            statement.execute();
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
            {}
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (Exception ex2)
            {}
            try
            {
                session.close();
            }
            catch (Exception ex3)
            {}
        }
        return bulletinId;
    }
}
