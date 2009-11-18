package com.netpace.aims.bo.logincontent;

import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.CallableStatement;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.logincontent.AimsLoginContent;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;

public class AimsLoginContentManager {

    private static Logger log = Logger.getLogger(AimsLoginContentManager.class.getName());

    /**
     * this method sets next login content to show which is stored in session
     * @param request
     * @param loginContentIds
     * @return
     * @throws HibernateException
     */
    public static ActionForward setNextLoginContent(HttpServletRequest request, List loginContentIds)
            throws HibernateException {
        ActionForward forward = null;
        if(loginContentIds !=null && loginContentIds.size()>0)
            {
                Long loginContentId = (Long)loginContentIds.get(0);
                HttpSession session = request.getSession();
                AimsLoginContent loginContent = null;
                try {
                    if ( (loginContentId !=null) && (loginContentId.longValue() != 0)) {
                        loginContent =  (AimsLoginContent) DBHelper.getInstance().load(AimsLoginContent.class, loginContentId.toString());
                        session.setAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW, loginContentId);
                        request.setAttribute(AimsConstants.REQUEST_LOGIN_CONTENT_ERROR_MESSAGE, StringFuncs.NullValueReplacement(loginContent.getErrorMessage()));
                        forward = new ActionForward("loginContentPage", loginContent.getLoginContentAction(), false);
                    }
                    //remove current login content from list after setting its attribute in request and session
                    loginContentIds.remove(0);
                    //check list size to set next login content in session
                    if(loginContentIds.size()>0) {
                        session.setAttribute(AimsConstants.SESSION_LOGIN_CONTENT_IDS_TO_SHOW, loginContentIds);
                    }
                    else {
                        //all loginContent are viewed, now remove loginContentIds from session
                        session.removeAttribute(AimsConstants.SESSION_LOGIN_CONTENT_IDS_TO_SHOW);
                        //session.removeAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW);
                    }
                }
                catch(HibernateException he)
                {
                    System.out.println("HibernateException found: ");
                    he.printStackTrace();
                    throw he;
                }
            }
        return forward;
    }//end setNextLoginContent

    /**
     * checks if given path is present in ignored path of login content
     * @param request
     * @param path
     * @return
     */
    public static boolean checkIngoredPathsForLoginContent(HttpServletRequest request, String path) {
        boolean pathFound = false;
        HttpSession session = request.getSession();
        AimsLoginContent loginContent =  null;
        String ignoredPath = null;

        try {
            if(session.getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW) != null) {
                loginContent = (AimsLoginContent) DBHelper.getInstance().load(AimsLoginContent.class, request.getSession().getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW).toString());
                ignoredPath = StringFuncs.NullValueReplacement(loginContent.getIgnoredPath());
                if(ignoredPath.indexOf(path) != -1) {
                    pathFound = true;
                }
            }
        }
        catch(HibernateException he) {
            he.printStackTrace();
        }
        return pathFound;
    }

    /**
     * Updates the ack date when user (alliance admin) updates its contact info, if not present then add record for that alliance 
     * @param loginContentId
     * @param allianceId
     * @param currUserName
     * @throws HibernateException
     * @throws SQLException
     */
    public static void updateLoginContentAllianceAck(Long loginContentId, Long allianceId, String currUserName) throws HibernateException, SQLException {
        Session session = null;
        Connection ConOra = null;
        Transaction tx = null;
        CallableStatement statement = null;

        try {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_LOGIN_CONTENT_PKG.update_alliance_contact_ack(?,?,?)");
            statement.setLong(1, loginContentId.longValue());
            statement.setLong(2, allianceId.longValue());
            statement.setString(3, currUserName);
            statement.execute();
            tx.commit();
        }
        catch (Exception ex) {
            try {
                if (tx != null)
                    tx.rollback();
            }
            catch (Exception ex1) {
                System.out.println("Exception occured while updating login content alliance ack.");
                ex1.printStackTrace();
            }
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (Exception ex2) {
                System.out.println("Exception occured while closing statement.");
                ex2.printStackTrace();
            }
            try {
                if(session != null)
                    session.close();
            }
            catch (Exception ex3) {
                System.out.println("Exception occured while closing session.");
                ex3.printStackTrace();
            }
        }
    }//end  updateLoginContentUserAck

    /**
     * This method returns login content ids (comma separated) to show
     * @param userId
     * @param allianceId
     * @param contactUpdateDuration
     * @return
     */
    public static String getLoginContentIdsToShow(Long userId, Long allianceId, int contactUpdateDuration) {
        Session session = null;
        Connection ConOra = null;
        Transaction tx = null;
        CallableStatement statement = null;
        String loginContentIdsStr = "";

        try {
            session = DBHelper.getInstance().getSession();
            tx = session.beginTransaction();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_LOGIN_CONTENT_PKG.get_login_content_to_show(?,?,?,?)");
            statement.setLong(1, userId.longValue());
            statement.setLong(2, allianceId.longValue());
            statement.setLong(3, contactUpdateDuration);
            statement.registerOutParameter(4, java.sql.Types.VARCHAR);
            statement.execute();
            loginContentIdsStr = statement.getString(4);
            tx.commit();
        }
        catch (Exception ex) {
            try {
                if (tx != null)
                    tx.rollback();
            }
            catch (Exception ex1) {
                System.out.println("Exception occured while calling: AIMS_LOGIN_CONTENT_PKG.get_login_content_to_show");
                ex1.printStackTrace();
            }
        }
        finally {
            try {
                if (statement != null)
                    statement.close();
            }
            catch (Exception ex2) {
                System.out.println("Exception occured while closing statement.");
                ex2.printStackTrace();
            }
            try {
                if(session != null)
                    session.close();
            }
            catch (Exception ex3) {
                System.out.println("Exception occured while closing session.");
                ex3.printStackTrace();
            }
        }
        return loginContentIdsStr;
    }//end getLoginContentIdsToShow
}
