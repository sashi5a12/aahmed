package com.netpace.aims.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

import com.netpace.aims.utils.BinaryUtility;
import com.netpace.aims.utils.MailUtils;
import com.netpace.aims.utils.ConfigEnvProperties;

public class BinaryServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(BinaryServlet.class.getName());

	private static String dbServer;
	private static String dbPort;	
	private static String dbName;	
	private static String dbURL;
    private static String dbUser;
	private static String dbPassword;
	private static final String PRIVIEW_FILE = "PREVIEW_FILE AS F, PREVIEW_FILE_FILE_NAME AS N, PREVIEW_FILE_CONTENT_TYPE AS T";
	private static final String BINARY_FILE = "BINARY_FILE AS F, BINARY_FILE_FILE_NAME AS N, BINARY_FILE_CONTENT_TYPE AS T";
	private static final String SECRET_KEY_FOR_BINARY_DOWNLOAD= "BINARY_DOWNLOAD_SECRET_KEY";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);		
		ConfigEnvProperties conf = ConfigEnvProperties.getInstance();
		//dbServer = conf.getProperty("db.server");
		//dbPort = conf.getProperty("db.port");
		//dbName = conf.getProperty("db.database");
		//dbUser = conf.getProperty("db.user");
		//dbPassword = conf.getProperty("db.password");
        
        dbURL = conf.getProperty("connection.url");
        dbUser = conf.getProperty("connection.username");
		dbPassword = conf.getProperty("connection.password");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String key= request.getParameter("key");
		long binnaryId=0;
		String columnsInfo = null;
          
        StringBuffer requestURL = request.getRequestURL().append("?"+request.getQueryString());
        log.debug("URL: "+requestURL.toString());
        
		if ((type == null || type.trim().length() == 0) || (id == null || id.trim().length() == 0) || (key == null || key.trim().length() == 0)) {
			saveLog(null, requestURL.toString(), "FAILED", "Invalid URL.");
			return;
		}
		
		try {
			binnaryId=Long.parseLong(id);
		} catch(NumberFormatException nfe){
			saveLog(null, requestURL.toString(), "FAILED", "Binary id was not a number in url.");
			return ;
		}

		if ("preview".equals(type)) {
			columnsInfo = PRIVIEW_FILE;
		} else if ("binary".equals(type)) {
			columnsInfo = BINARY_FILE;
		} else {
			saveLog(null, requestURL.toString(), "FAILED", "Invalid value in type paramerter.");
			return;
		}
		
		long appId=getAppId(binnaryId);
		String downloadKey=BinaryUtility.getBase64Digest(id, String.valueOf(appId), SECRET_KEY_FOR_BINARY_DOWNLOAD);
		if (!key.equals(downloadKey)){
			saveLog(null, requestURL.toString(), "FAILED", "Invalid Key.");
			log.debug("Sending hack mail to ..........."+MailUtils.EMAIL_EXCEPTION_ADMIN+" for URL: "+requestURL);
			StringBuffer emailSubject = new StringBuffer("Hacking Tried in Binary Download, on .... ");
			if (request.getServerName() != null)
				emailSubject.append(request.getServerName());
			MailUtils.sendMailWithHandledExceptions(
						MailUtils.EMAIL_EXCEPTION_ADMIN, 
						"exceptions@netpace.com", 
						emailSubject.toString(), 
						null, 
						BinaryUtility.getRequestInfo(request));
			return;
		}
		ServletOutputStream ou = null;
		try {
			ou = response.getOutputStream();
			response.reset();
			
			Object[] result=getFile(columnsInfo, binnaryId, ou);
			String fileName=(String)result[0];
			String contentType=(String)result[1];
			byte[] content=(byte[])result[2];
			
			if (fileName != null && contentType != null && content != null){
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setContentType(contentType);
				response.setContentLength(content.length);
				ou.write(content);
				log.debug("File downloaded: binnaryId="+binnaryId+" ,fileName="+fileName);
				
				saveLog(new Long(binnaryId), requestURL.toString(), "SUCCEED", "File served successfully.");
			}
			else {
				String msg="Any of one data is null in (FileName/ContentType/Blob); fileName: " + fileName + 
						   " contentType: " + contentType + 
						   " contentLength: " +(content == null?0:content.length);
				
				saveLog(new Long(binnaryId), requestURL.toString(), "FAILED", msg);
			}
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {            
            try{
                if (ou != null){
                    ou.flush();
                    ou.close();
                }
            }
            catch (Exception ex2){ex2.printStackTrace();}
        }
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	public Connection getDBConnection() throws Exception {
		try {
			OracleDataSource ods = new OracleDataSource();
			//ods.setDriverType("thin");
			//ods.setServerName(dbServer);
			//ods.setDatabaseName(dbName);
			//ods.setPortNumber(new Integer(dbPort).intValue());
            ods.setURL(dbURL);
            ods.setUser(dbUser);
			ods.setPassword(dbPassword);
			return ods.getConnection();

		} catch (Exception e) {
			log.error(e,e);
			throw e;
		}
	}

	private void saveLog(Long binaryId, String url, String status, String description){
		Connection conn=null;
		PreparedStatement prepStmt=null;
		String query = "INSERT INTO AIMS_VZAPP_BINARY_DOWNLOAD_LOG VALUES(SEQ_PK_BINARY_DOWNLOAD_LOG_ID.nextval,?,?,?,?,sysdate)";
		try {
			conn=this.getDBConnection();
			prepStmt=conn.prepareStatement(query);
			if (binaryId == null){
				prepStmt.setNull(1, Types.NUMERIC);
			}
			else {
				prepStmt.setLong(1, binaryId.longValue());
			}
			prepStmt.setString(2, url);
			prepStmt.setString(3, status);
			prepStmt.setString(4, description);
			int i=prepStmt.executeUpdate();
			System.out.println(i);
		} catch (Exception e) {
			log.error(e,e);
		} finally {
			if (prepStmt != null){
				try {
					prepStmt.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
		}
	}
	private Object[] getFile(String columnsInfo, long binnaryId, OutputStream os){
		Connection conn=null;
		Object[] result=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		String query="SELECT "+columnsInfo+" FROM AIMS_VZAPP_BINARIES WHERE BINARY_ID = ?";
		Blob blob=null;
		try {
			result=new Object[3];
			conn=getDBConnection();
			prepStmt=conn.prepareStatement(query);
			prepStmt.setLong(1, binnaryId);
			rs=prepStmt.executeQuery();
			while(rs.next()){				
				result[0]=rs.getString("N");
				result[1]=rs.getString("T");
				blob=rs.getBlob("F");
				result[2]= blob.getBytes(1,(int) blob.length());
			}
			
		} catch (SQLException e) {
			log.error(e,e);
		} catch (Exception e) {
			log.error(e,e);
		} finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (prepStmt != null){
				try {
					prepStmt.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}			
		}
		return result;
	}
	private long getAppId(long binnaryId){
		Connection conn=null;
		PreparedStatement prepStmt=null;
		ResultSet rs=null;
		long appId=0;
		String query="SELECT VZAPPZONE_APPS_ID FROM AIMS_VZAPP_BINARIES WHERE BINARY_ID = ?";
		try {
			conn=getDBConnection();
			prepStmt=conn.prepareStatement(query);
			prepStmt.setLong(1, binnaryId);
			rs=prepStmt.executeQuery();
			while(rs.next()){
				appId =rs.getLong("VZAPPZONE_APPS_ID");
			}
			
		} catch (SQLException e) {
			log.error(e,e);
		} catch (Exception e) {
			log.error(e,e);
		} finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (prepStmt != null){
				try {
					prepStmt.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					log.error(e,e);
				}
			}			
		}
		return appId;
	}
	
}
