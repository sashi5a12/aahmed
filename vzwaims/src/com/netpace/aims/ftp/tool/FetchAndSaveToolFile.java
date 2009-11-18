package com.netpace.aims.ftp.tool;

import org.apache.log4j.Logger;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.cfg.Configuration;

import com.netpace.aims.bo.tools.AimsToolsManager;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.util.AimsFtpDownloader;
import com.netpace.aims.util.ConfigEnvProperties;

public class FetchAndSaveToolFile {
	
	private static Logger log = Logger.getLogger(FetchAndSaveToolFile.class.getName());
	
	public static void main(String[] args) {
		log.debug("FetchAndSaveToolFile Start");
		DBHelper dbHelper = null;
		try {					
			ConfigEnvProperties props = ConfigEnvProperties.getInstance();
			Configuration conf = new Configuration();
			dbHelper = DBHelper.getInstance();

			conf.setProperty("hibernate.connection.url", props.getProperty("connection.url"));
			conf.setProperty("hibernate.connection.username", props.getProperty("connection.username"));
			conf.setProperty("hibernate.connection.password", props.getProperty("connection.password"));
			dbHelper.sessionFactory = conf.configure().buildSessionFactory();

			String ftpServerAddress = props.getProperty("tool.excelFile.ftp.server.address");
			String ftpUser = props.getProperty("tool.excelFile.ftp.user.name");
			String ftpPassword = props.getProperty("tool.excelFile.ftp.user.password");
			String ftpWorkingDirectory = props.getProperty("tool.excelFile.ftp.working.dir");
			String remoteFile = props.getProperty("tool.excelFile.remote.filename");
			
			byte[] data = AimsFtpDownloader.getFile(ftpServerAddress, ftpUser, ftpPassword, ftpWorkingDirectory, remoteFile);
			if (data != null && data.length > 0){
				AimsToolsManager.updateFetchXlsRecord(data, remoteFile);			
			}
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		} finally {
			if (dbHelper != null) {
				try {
					dbHelper.sessionFactory.close();
					dbHelper = null;
				} catch (HibernateException he) {
					log.debug("Error occured while closing the session factory");
					log.debug(he, he);
				}
			}
		}
		log.debug("FetchAndSaveToolFile End");
	}
}
