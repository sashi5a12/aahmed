package com.netpace.aims.util;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * A utility class that provides some useful methods to be used with database
 * transactions.
 *
 * @author Fawad Sikandar
 */

public class DBUtils
{
    private static Logger log = Logger.getLogger(DBUtils.class.getName());

    public static final String SEQ_CONTRACT_REF_ID = "SEQ_CONTRACT_REF_ID";

  public static String extractString(ResultSet rs, String columnName) throws
      SQLException
  {
    String columnValue = rs.getString(columnName);

    if (rs.wasNull())
    {
      columnValue = null;
    }

    return columnValue;
  }

  public static boolean extractYN(ResultSet rs,
                                  String columnName) throws SQLException
  {
    String columnValue = rs.getString(columnName);

    if (rs.wasNull())
    {
      columnValue = "N";
    }

    return columnValue.trim().equalsIgnoreCase("Y");
  }

  public static java.util.Date extractDate(ResultSet rs, String columnName) throws
      SQLException
  {
    java.util.Date columnValue = rs.getDate(columnName);

    if (rs.wasNull())
    {
      columnValue = null;
    }

    return columnValue;
  }


    /**
     * This method returns generated sequence id of given sequence (supported only for oracle)
     * @param sequenceName  -   Sequence name
     * @param conOra        -   session connection where this sequence id is used
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static long generateSequenceId(String sequenceName, Connection conOra) throws SQLException, Exception {
        long sequenceId = -1;
        Statement seqStmt = null;
        ResultSet seqResultSet = null;
        StringBuffer seqBuffer = new StringBuffer();
        log.debug("start DBUtils.generateSequenceId, sequenceName = "+sequenceName);
        try {
            seqStmt = conOra.createStatement();
            seqBuffer.append("select ").append(sequenceName).append(".nextVal from dual");
            seqResultSet = seqStmt.executeQuery(seqBuffer.toString());
            if(seqResultSet!=null) {
                while(seqResultSet.next()) {
                    sequenceId = seqResultSet.getLong(1);
                    log.debug("DBUtils.generateSequenceId: generated id = "+sequenceId);
                }
            }
            else {
                log.debug("DBUtils.generateSequenceId: resultset for sequence is null");
            }
        }
        catch(SQLException sqle) {
            log.debug("DBUtils.generateSequenceId: SQL Exception occured while generating sequence");
            sqle.printStackTrace();
            throw sqle;
        }
        catch(Exception e) {
            log.debug("DBUtils.generateSequenceId: Exception occured while generating sequence");
            e.printStackTrace();
            throw e;
        }
        finally {
            try {
                if(seqResultSet!=null) {
                    seqResultSet.close();
                    log.debug("DBUtils.generateSequenceId: closed resultset");
                }
                if(seqStmt!=null) {
                    seqStmt.close();
                    log.debug("DBUtils.generateSequenceId: closed statement");
                }
            }//end try
            catch(SQLException sqle2) {
                log.debug("DBUtils.generateSequenceId: SQL Exception occured while closing connection");
                sqle2.printStackTrace();
            }
            catch(Exception e2) {
                e2.printStackTrace();
                log.debug("DBUtils.generateSequenceId: Exception occured while closing connection");
            }
            log.debug("DBUtils.generateSequenceId: connection will be closed by calling method of generateSequenceId()");
        }
        log.debug("end DBUtils.generateSequenceId, sequenceName = "+sequenceName+", returning generated id = "+sequenceId);
        return sequenceId;
    }//end generateSequenceId

}