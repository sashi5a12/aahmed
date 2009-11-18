package com.netpace.aims.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.wapoptout.WapOptoutManager;
import com.netpace.aims.model.wapoptout.AimsWapOptoutWhitelistUrl;

/**
 * @author nauman
 * 
 *
 */

public class AimsFtpDownloader {
	
	private static Logger log = Logger.getLogger(AimsFtpDownloader.class.getName());
	
	public static byte[] getFile(String server, String username, String password, String workingFolder, String remoteFile) throws AimsException {

		log.debug("AimsFtpDownloader.getFile Start");
		log.debug("server: " + server);
		log.debug("username: " + username);
		log.debug("password: " + password);
		log.debug("workingFolder: " + workingFolder);
		log.debug("remoteFile: " + remoteFile);
		boolean loginStatus = false;
		boolean dirChanged = false;


		FTPClient ftpClient = new FTPClient();

		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		byte[] data=null;

		String errorMessage = "";

		//general exception for ftp transfer
		AimsException aimsException = new AimsException("Error");
		

		try {
			ftpClient.connect(server);
			loginStatus = ftpClient.login(username, password);

			if (loginStatus) {
				log.debug("Connected to " + server + ".");
				log.debug(ftpClient.getReplyString());
				dirChanged = ftpClient.changeWorkingDirectory(workingFolder);

				if (dirChanged) {
					ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

		            // Use passive mode as default because most of us are behind firewalls these days.
					ftpClient.enterLocalPassiveMode();
					
					boolean fileRetrieved=ftpClient.retrieveFile(remoteFile, outputStream);

					log.debug("  remote file " + remoteFile + "  from server: "	+ server);

					if (fileRetrieved) {
						data=outputStream.toByteArray();
					} 
					else {
						errorMessage = "File: " + remoteFile+ " not downloaded from"	+ (server + "/" + server+" FILE NOT FOUND");
						log.debug("Error in FTP Transfer: "+ errorMessage);
						sendFTPErrorMail(errorMessage);
						throw aimsException;
					}
				} 
				else {
					errorMessage = "Directory: " + workingFolder+ " not found in " + server;
					System.out.println("Error in FTP Transfer: " + errorMessage);
					sendFTPErrorMail(errorMessage);
					throw aimsException;
				}
			} 
			else {
				errorMessage = "FTP Authentication failed. \n\nServer: "
						+ server + "\nUserID: " + username + "\nPassword: "
						+ password;
				log.debug("Error in FTP Transfer: " + errorMessage);
				sendFTPErrorMail(errorMessage);
				throw aimsException;
			}

		} catch (FileNotFoundException e) {
			log.debug("Exception: " + remoteFile +" not found in temp directory");
			log.error(e,e);
			throw aimsException;
		} catch (IOException ioe) {
			log.debug("Exception: Error in connection " + remoteFile);
			log.error(ioe,ioe);
			sendFTPErrorMail("Error in connection to : " + server + "\n\n"+ MiscUtils.getExceptionStackTraceInfo(ioe));
			throw aimsException;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	
			try {
				if (outputStream != null){
					outputStream.close();
				}
			} catch (IOException ioe) {
				log.debug("error closing the streams.");
				log.error(ioe,ioe);
			} 
			
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException ioex) {
					log.error(ioex,ioex);
				} 
				finally {
					try {
						log.info("Disconnect input stream");
						ftpClient.disconnect();
					} catch (IOException ioexc) {
						log.error(ioexc,ioexc);
					}
				}
			}

		}

		log.debug("AimsFtpDownloader.getFile Start. FileName: " + remoteFile + "  downloaded");
		return data;
	}
	public static boolean downloadWhiteListUrls(String server, String username,
			String password, String workingFolder, String remoteFile,String localFilePath)
			throws AimsException {

		log.debug("AimsDownloadWhiteList Start. FileName: " + remoteFile);
		boolean loginStatus = false;
		boolean dirChanged = false;

		boolean downloaded = false;

		FTPClient ftpClient = new FTPClient();

		InputStream inputStream = null;
		DataInputStream dataInputStream=null;
		BufferedReader bufferedReader=null;

		String errorMessage = "";

		//general exception for ftp transfer
		AimsException aimsException = new AimsException("Error");
		aimsException.addException(new AimsException(
				"error.wap.app.ftp.transfer"));

		try {
			ftpClient.connect(server);
			loginStatus = ftpClient.login(username, password);

			if (loginStatus) {
				System.out.println("Connected to " + server + ".");
				System.out.print(ftpClient.getReplyString());
				dirChanged = ftpClient.changeWorkingDirectory(workingFolder);

				if (dirChanged) {

					/*
					 * code to download file and save in specified directory and save
					 */
					
					/*Reason: No need to save file locally when working with FileInputStream*/
//					File file=new File(localFilePath,remoteFile);
//					boolean retrieved = ftpClient.retrieveFile(remoteFile, new FileOutputStream(file));
					
					inputStream = ftpClient.retrieveFileStream(remoteFile);
					StringBuffer inputStreamBuffer=new StringBuffer();

					log.debug("  remote file " + remoteFile + "  from server: "	+ server);

					if (inputStream != null) {
						List urls = new ArrayList();

						dataInputStream = new DataInputStream(inputStream);

						bufferedReader = new BufferedReader(
								new InputStreamReader(inputStream));

						AimsWapOptoutWhitelistUrl url;
						
					    String line;
					    while ((line = bufferedReader.readLine()) != null)   {
							if (!StringFuncs.isNullOrEmpty(line)) {
								/*Reason: Each URL should come on separate line in database instead of one long string*/
								inputStreamBuffer.append(line+"\n");
								url = new AimsWapOptoutWhitelistUrl();
								url.setUrl(line.trim());
								log.info(" URL :" + line + ":");
								urls.add(url);
							}
					    }
					
    					if (urls.size() >= 0) {
							WapOptoutManager.saveWhitelist(urls);
							WapOptoutManager.saveWhitelistData(inputStreamBuffer.toString());
						}

					} else {
						errorMessage = "File: " + remoteFile+ " not downloaded to "	+ (server + "/" + server+" FILE NOT FOUND");
						System.out.println("Error in FTP Transfer: "+ errorMessage);
						sendFTPErrorMail(errorMessage);
						throw aimsException;
					}
				} else {
					errorMessage = "Directory: " + workingFolder+ " not found in " + server;
					System.out.println("Error in FTP Transfer: " + errorMessage);
					sendFTPErrorMail(errorMessage);
					throw aimsException;
				}

			} else {
				errorMessage = "FTP Authentication failed. \n\nServer: "
						+ server + "\nUserID: " + username + "\nPassword: "
						+ password;
				System.out.println("Error in FTP Transfer: " + errorMessage);
				sendFTPErrorMail(errorMessage);
				throw aimsException;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Exception: " + remoteFile
					+ " not found in temp directory");
			e.printStackTrace();//zip file not found
			throw aimsException;
		} catch (IOException ioe) {
			System.out.println("Exception: Error in connection " + remoteFile);
			ioe.printStackTrace();
			sendFTPErrorMail("Error in connection to : " + server + "\n\n"
					+ MiscUtils.getExceptionStackTraceInfo(ioe));
			throw aimsException;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	
			try {
				//close stream
				if (inputStream != null) {
					log.info("Closing input stream");
					inputStream.close();
				}
				if (dataInputStream != null) {
					dataInputStream.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				if (ftpClient.isConnected()) {
					try {
						//logout. Issues QUIT command
						ftpClient.logout();
					} catch (IOException ioex) {
						ioex.printStackTrace();
					} finally {
						try {
							//disconnect ftp session
							log.info("Disconnect input stream");
							ftpClient.disconnect();
						} catch (IOException ioexc) {
							ioexc.printStackTrace();
						}
					}
				}
			}

		}

		log.debug("AimsDownloadWhiteList Start. FileName: " + remoteFile
				+ "  downloaded");
		return downloaded;
	}


	/**
	 * Reads the stream line by line and returns as a list
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static List getUrlsFromInputStream(InputStream inputStream)
			throws IOException {
		
		List lines = new ArrayList();
		DataInputStream in = new DataInputStream(inputStream);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(in));

		AimsWapOptoutWhitelistUrl url;
		while (true) {
			if (!br.ready()) {
				if (log.isDebugEnabled()) {
					log.debug(" reached the end of file ");
				}
				break;
			}
			String line = (br.readLine()).trim();
			if (!StringFuncs.isNullOrEmpty(line)) {
				url = new AimsWapOptoutWhitelistUrl();
				url.setUrl(line);
				if (log.isDebugEnabled()) {
					log.debug("  revieved url :" + line + ":");
				}
				lines.add(url);
			}
		}
		in.close();
		
		return lines;
	}
	
	
    private static void sendFTPErrorMail(String content) {
        try {
            MailUtils.sendMail(
                AimsConstants.EMAIL_EXCEPTION_ADMIN,
                "exceptions@netpace.com",
                "FTP transfer error",
                null,
                content);            
        }
        catch (Exception mailEx) {
            System.out.println("Exception in AimsFtpUploader while sending email");
            mailEx.printStackTrace();
        }
    }

}
