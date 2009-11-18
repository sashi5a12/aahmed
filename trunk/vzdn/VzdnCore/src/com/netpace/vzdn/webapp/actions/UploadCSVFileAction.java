package com.netpace.vzdn.webapp.actions;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnNewsletterEmailLog;

public class UploadCSVFileAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(UploadCSVFileAction.class);
	
	private VzdnNewsletterEmailLog newsLetterEmailLog;
	private File uploadcsv;
	private String uploadcsvContentType;
	private String uploadcsvFileName;
	
	private String emailAddress;
	
	
	public VzdnNewsletterEmailLog getNewsLetterEmailLog() {
		return newsLetterEmailLog;
	}

	public void setNewsLetterEmailLog(VzdnNewsletterEmailLog newsLetterEmailLog) {
		this.newsLetterEmailLog = newsLetterEmailLog;
	}

	public File getUploadcsv() {
		return uploadcsv;
	}

	public void setUploadcsv(File uploadcsv) {
		this.uploadcsv = uploadcsv;
	}

	public String getUploadcsvContentType() {
		return uploadcsvContentType;
	}

	public void setUploadcsvContentType(String uploadcsvContentType) {
		this.uploadcsvContentType = uploadcsvContentType;
	}

	public String getUploadcsvFileName() {
		return uploadcsvFileName;
	}

	public void setUploadcsvFileName(String uploadcsvFileName) {
		this.uploadcsvFileName = uploadcsvFileName;
	}	
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String execute(){
		System.out.print("Upload CVS File");
		
		HttpSession session = getServletRequest().getSession(true);
		
		if(uploadcsv != null){
			byte[] cvsFileContentByteArr;		
											
				try{
					cvsFileContentByteArr = readInputStream(new FileInputStream(uploadcsv));
					emailAddress = new String(cvsFileContentByteArr);
					session.setAttribute(VzdnConstants.CSV_FILE_CONTENT, emailAddress);
				} catch(Exception ex){
					ex.printStackTrace();
				}
		}
		session.setAttribute(VzdnConstants.NEWS_LETTER_EMAIL_LOG, newsLetterEmailLog);
		
		return SUCCESS;
	}
	
	
	/** Read an input stream in its entirety into a byte array */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		 int bufSize = 1024 * 1024;
		 byte[] content;
		
		 List<byte[]> parts = new LinkedList();
		 InputStream in = new BufferedInputStream(inputStream);
		
		 byte[] readBuffer = new byte[bufSize];
		 byte[] part = null;
		 int bytesRead = 0;
		
		 // read everyting into a list of byte arrays
		 while ((bytesRead = in.read(readBuffer, 0, bufSize)) != -1) {
			 part = new byte[bytesRead];
			 System.arraycopy(readBuffer, 0, part, 0, bytesRead);
			 parts.add(part);
		 }
		
		 // calculate the total size
		 int totalSize = 0;
		 for (byte[] partBuffer : parts) {
			 totalSize += partBuffer.length;
		 }
		
		 // allocate the array
		 content = new byte[totalSize];
		 int offset = 0;
		 for (byte[] partBuffer : parts) {
			 System.arraycopy(partBuffer, 0, content, offset,
			 partBuffer.length);
			 offset += partBuffer.length;
		 }
		
		 return content;
	}
	
	

}
