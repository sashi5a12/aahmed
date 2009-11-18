package com.netpace.aims.ftp.wapoptout;

import org.apache.log4j.Logger;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.cfg.Configuration;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.util.AimsFtpDownloader;
import com.netpace.aims.util.ConfigEnvProperties;

public class FtpWhiteList {
	
	private static Logger log = Logger.getLogger(FtpWhiteList.class.getName());

	public static void main(String[] args) {
		DBHelper dbHelper = null;
		try {
			
			if (log.isDebugEnabled()) {
				log.debug("configuring to download ftp urls ");
			}
			
			ConfigEnvProperties props = ConfigEnvProperties.getInstance();
			Configuration conf = new Configuration();
			dbHelper = DBHelper.getInstance();

			conf.setProperty("hibernate.connection.url", props
					.getProperty("connection.url"));
			conf.setProperty("hibernate.connection.username", props
					.getProperty("connection.username"));
			conf.setProperty("hibernate.connection.password", props
					.getProperty("connection.password"));
			dbHelper.sessionFactory = conf.configure().buildSessionFactory();

			String ftpServerAddress = props.getProperty("wapoptout.whitelist.ftp.server.address");
			String ftpUser = props.getProperty("wapoptout.whitelist.ftp.user.name");
			String ftpPassword = props.getProperty("wapoptout.whitelist.ftp.user.password");
			String ftpWorkingDirectory = props.getProperty("wapoptout.whitelist.ftp.working.dir");
			String remoteFile = props.getProperty("wapoptout.whitelist.remote.filename");
			String tempdir = props.getProperty("wapoptout.whitelist.local.temp.dir");


			System.out.println("Executing ftpDownloader ");

			AimsFtpDownloader.downloadWhiteListUrls(ftpServerAddress, ftpUser,
					ftpPassword, ftpWorkingDirectory, remoteFile,tempdir);

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
		if (log.isDebugEnabled()) {
			log.debug(" Downloading url complete ");
		}
	}

}
