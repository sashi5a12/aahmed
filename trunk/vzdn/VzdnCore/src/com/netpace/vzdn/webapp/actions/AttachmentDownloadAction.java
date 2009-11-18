package com.netpace.vzdn.webapp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.netpace.vzdn.global.VzdnConstants;

public class AttachmentDownloadAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(AttachmentDownloadAction.class);
	
	private InputStream inputStream;	
	private String description;
	private long size;
	
	private String contentType;
	private String contentDisposition; 
	private String contentLength;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public String getContentLength() {
		return contentLength;
	}
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	} 
	
	
	public String execute(){	
		HttpSession session = getServletRequest().getSession();
		try
		{
			String filePath = getServletRequest().getParameter("fpath");
			String contentType = (String) getServletRequest().getSession().getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE);
			
			File file = new File(filePath);
			inputStream = new FileInputStream(file);
			this.setDescription(contentType);			
			
			this.setContentDisposition("filename=\"" + file.getName());
			this.setContentLength("");
			this.setContentType(contentType);
			
		} catch(FileNotFoundException exp){
			log.info("Attachment File Not Found" + exp.getCause());			
			session.setAttribute("ERROR_MSG", "Attachment File Not Found");
			return INPUT;
		} catch(IOException exp){
			log.info("Attachment File Problem" + exp.getCause());	
			session.setAttribute("ERROR_MSG", "Attachment File Problem");			
			return INPUT;
		} catch(Exception exp){
			log.error("Some issue with attachment file.");
			session.setAttribute("ERROR_MSG", "Some issue with attachment file.");			
			return INPUT;
		}
		return "download";
	}

}
