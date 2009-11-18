package com.netpace.aims.util;

import java.io.File;
import java.util.*;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.ftp4che.*;
import org.ftp4che.util.ftpfile.*;

import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.application.AimsDashboardFtpsLog;

public class FTPS {
	static final Logger log = Logger.getLogger(FTPS.class.getName());
	
	public static boolean sendFilesToMotricity(File[] files, Long appId, Long lifeCycleId, String createdBy)throws AimsException, HibernateException {
		boolean transfer=false;
		AimsException aimsException = new AimsException("Error");
		aimsException.addException(new AimsException("error.dashboard.app.ftps.transfer"));
		
		ConfigEnvProperties envProperties = ConfigEnvProperties.getInstance();
		String ftpsServerAddress = envProperties.getProperty("dashboard.ftps.server.address");
		String ftpsServerPort = envProperties.getProperty("dashboard.ftps.server.port");
		String ftpsUser = envProperties.getProperty("dashboard.ftps.user.name");
		String ftpsPassword = envProperties.getProperty("dashboard.ftps.user.password");
		try {
			transfer=sendFiles(files, ftpsServerAddress, ftpsServerPort, ftpsUser, ftpsPassword);
		} catch (Exception e) {
			AimsDashboardFtpsLog ftpsLog=new AimsDashboardFtpsLog();
			ftpsLog.setAppId(appId);
			ftpsLog.setStatus("Error");
			ftpsLog.setFileName(files[0].getName());
			ftpsLog.setAimsLifecycleId(lifeCycleId);
			ftpsLog.setDescription("File Size:"+files[0].length() + " Error Message"+ e.getMessage());
			ftpsLog.setCreatedDate(new Date());
			ftpsLog.setCreatedBy(createdBy);
			DashboardApplicationManager.saveFtpsLog(ftpsLog);
			log.error(e,e);
			throw aimsException;			
		}
		return transfer;
	}
	private static boolean sendFiles(File[] files, 
									String ftpsServerAddress, 
									String ftpsServerPort, 
									String ftpsUser, 
									String ftpsPassword) throws Exception {
		boolean transfered = false;
		String errorMessage = "";

		Properties pt = new Properties();
		pt.setProperty("connection.host", ftpsServerAddress);
		pt.setProperty("connection.port", ftpsServerPort);
		pt.setProperty("user.login", ftpsUser);
		pt.setProperty("user.password", ftpsPassword);
		pt.setProperty("connection.type", "AUTH_SSL_FTP_CONNECTION");
		pt.setProperty("connection.timeout", "10000");
		pt.setProperty("connection.passive", "true");
		FTPConnection connection = null;

		try {
			connection = FTPConnectionFactory.getInstance(pt);
			connection.connect();
			connection.noOperation();
			log.debug("Connected with " + ftpsServerAddress);
		} catch (Exception e) {
			errorMessage = "Connection failed. \n\nServer: "+ ftpsServerAddress + 
						  "\nPort: " + ftpsServerPort + 
						  "\nUserID: " + ftpsUser + 
						  "\nPassword: " + ftpsPassword;
			System.out.println("Error in FTPS : " + errorMessage);
			sendFTPErrorMail(errorMessage);
			if (connection != null){
				connection.disconnect();
			}			
			throw e;
		}

		try {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				FTPFile fromFile = new FTPFile(file);
				FTPFile toFile = new FTPFile(".", file.getName());
				connection.uploadFile(fromFile, toFile);
			}
			transfered=true;
		} catch (Exception e) {
			errorMessage = "Error occured while uploading the files.";
			System.out.println("Error in FTPS : " + errorMessage);
			sendFTPErrorMail(errorMessage);
			throw e;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
				
		return transfered;
	}

	private static void sendFTPErrorMail(String content) {
		try {
			MailUtils.sendMail(AimsConstants.EMAIL_EXCEPTION_ADMIN,
							   "exceptions@netpace.com", 
							   "FTPS transfer error", 
							   null,
							   content);
		} catch (Exception mailEx) {
			System.out.println("Exception in FTPS while sending email");
			mailEx.printStackTrace();
		}
	}
}
