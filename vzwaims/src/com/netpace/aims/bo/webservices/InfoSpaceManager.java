package com.netpace.aims.bo.webservices;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import net.sf.hibernate.Session;
import oracle.jdbc.driver.OracleCallableStatement;

import org.apache.log4j.Logger;

import com.netpace.aims.model.DBHelper;

/**
 * This class is responsible for InfoSpace webservice related transactions
 * @author Rizwan Qazi
 */
public class InfoSpaceManager
{

    static Logger log = Logger.getLogger(InfoSpaceManager.class.getName());

    public static void submitInfoSpaceLog(Long appsId, String submitType, String submitStatus, String submitResponse) throws Exception
    {
        CallableStatement statement = null;
        Connection ConOra = null;
        Session session = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();

            statement = ConOra.prepareCall("call AIMS_INFOSPACE_PKG.insert_infospace_log(?,?,?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setString(2, submitType);
            statement.setString(3, submitStatus);
            statement.setString(4, submitResponse);
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

            session.close();
        }
    }

    public static String getInfoSpaceEndPointURL() throws Exception
    {
        Session session = null;
        CallableStatement statement = null;
        Connection ConOra = null;
        String resultString = null;

        try
        {
            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();
            statement = ConOra.prepareCall("call AIMS_INFOSPACE_PKG.get_endpoint_url(?)");
            statement.registerOutParameter(1, java.sql.Types.VARCHAR);
            statement.execute();

            resultString = ((OracleCallableStatement) statement).getString(1);
        }

        catch (SQLException ex)
        {
            throw ex;
        }
        finally
        {
            if (statement != null)
                statement.close();

            session.close();
        }

        return resultString;
    }

    public static boolean hasAlreadyBeenSubmittedSuccessfully(Long appsId, String aimsLifecyclePhaseId)
    {
        CallableStatement statement = null;
        Connection ConOra = null;
        Session session = null;
        boolean returnValue = false;

        try
        {
            session = DBHelper.getInstance().getSession();
            ConOra = session.connection();

            statement = ConOra.prepareCall("call AIMS_INFOSPACE_PKG.check_if_dcr_submitted(?,?,?)");
            statement.setLong(1, appsId.longValue());
            statement.setString(2, aimsLifecyclePhaseId);
            statement.registerOutParameter(3, java.sql.Types.VARCHAR);
            statement.execute();

            String resultString = ((OracleCallableStatement) statement).getString(3);

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
}