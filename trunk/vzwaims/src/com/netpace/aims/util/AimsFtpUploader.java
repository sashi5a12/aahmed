package com.netpace.aims.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.netpace.aims.bo.core.AimsException;

/**
 * 
 * @author Adnan Ahmed
 * 
 * This class upload file on ftp server.
 */
public class AimsFtpUploader {

	private static final Logger log =Logger.getLogger(AimsFtpUploader.class);
    
	public static boolean uploadFile(File transferFile,String ftpServerAddress, String ftpUser, String ftpPassword, String ftpWorkingDirectory) throws AimsException {

        log.debug("AimFTPUploaderuploadFile Start. FileName: "+transferFile.getName());
        boolean loginStatus = false;
        boolean dirChanged = false;

        FileInputStream transferStream = null;
        FTPClient ftpClient = new FTPClient();

        //general exception for ftp transfer
        AimsException aimsException = new AimsException("Error");
        aimsException.addException(new AimsException("error.wap.app.ftp.transfer"));

        String errorMessage = "";

        boolean transfered = false;
        try {
            ftpClient.connect(ftpServerAddress);
            loginStatus = ftpClient.login(ftpUser,ftpPassword);
            log.debug("Connection to server "+ftpServerAddress+" "+(loginStatus ? "success":"failure"));
            if(loginStatus) {
                dirChanged = ftpClient.changeWorkingDirectory(ftpWorkingDirectory);
                log.debug("change remote directory to "+ftpWorkingDirectory+": "+dirChanged);
                if(dirChanged) {
                    transferStream = new FileInputStream(transferFile);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    transfered = ftpClient.storeFile(transferFile.getName(), transferStream);
                    log.debug(transferFile.getName()+" transfered: "+transfered+" on server: "+ftpServerAddress);
                    if(!transfered) {
                        errorMessage = "File: "+transferFile.getName()+" not transfered to "+(ftpServerAddress+"/"+ftpServerAddress);
                        System.out.println("Error in FTP Transfer: "+errorMessage);
                        sendFTPErrorMail(errorMessage);
                        throw aimsException;
                    }
                }
                else {
                    errorMessage = "Directory: "+ftpWorkingDirectory+" not found in "+ftpServerAddress;
                    System.out.println("Error in FTP Transfer: "+errorMessage);
                    sendFTPErrorMail(errorMessage);
                    throw aimsException;
                }
            }//end loginstatus
            else {
                errorMessage = "FTP Authentication failed. \n\nServer: "+ftpServerAddress+"\nUserID: "+ftpUser+"\nPassword: "+ftpPassword;
                System.out.println("Error in FTP Transfer: "+errorMessage);
                sendFTPErrorMail(errorMessage);
                throw aimsException;
            }
        }//end try
        catch (FileNotFoundException e) {
            System.out.println("Exception: "+transferFile.getName()+" not found in temp directory");
            e.printStackTrace();//zip file not found
            throw aimsException;
        }
        catch (IOException ioe) {
            System.out.println("Exception: Error in connection "+ftpServerAddress);
            ioe.printStackTrace();
            sendFTPErrorMail("Error in connection to : "+ftpServerAddress+"\n\n"+MiscUtils.getExceptionStackTraceInfo(ioe));
            throw aimsException;
        }
        finally {
            try 
            {
                //close stream
                if(transferStream != null ) {
                    transferStream.close();
                }                
            }
            catch (IOException ioe) 
            {
                ioe.printStackTrace();
            }
            finally
            {
                if(ftpClient.isConnected()) {
                    try
                    {
                        //logout. Issues QUIT command
                        ftpClient.logout();
                    }
                    catch(IOException ioex)
                    {
                        ioex.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            //disconnect ftp session
                            ftpClient.disconnect();
                        }
                        catch(IOException ioexc)
                        {
                            ioexc.printStackTrace();
                        }   
                    }
                }
            }
            
        }
        log.debug("AimFTPUploaderuploadFile End. FileName: "+transferFile.getName()+"\t transfered: "+transfered);
        return transfered;
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
