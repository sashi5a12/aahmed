package com.netpace.vzdn.webapp.actions;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnNewsletterEmailLog;

public class UploadAttachmentFileAction extends BaseAction{

	private static Logger log = Logger.getLogger(UploadAttachmentFileAction.class);
		
	private VzdnNewsletterEmailLog newsLetterEmailLog;
	private File uploadattachment;
	private String uploadattachmentContentType;
	private String uploadattachmentFileName;
	
	public VzdnNewsletterEmailLog getNewsLetterEmailLog() {
		return newsLetterEmailLog;
	}

	public void setNewsLetterEmailLog(VzdnNewsletterEmailLog newsLetterEmailLog) {
		this.newsLetterEmailLog = newsLetterEmailLog;
	}

	public File getUploadattachment() {
		return uploadattachment;
	}

	public void setUploadattachment(File uploadattachment) {
		this.uploadattachment = uploadattachment;
	}

	public String getUploadattachmentContentType() {
		return uploadattachmentContentType;
	}

	public void setUploadattachmentContentType(String uploadattachmentContentType) {
		this.uploadattachmentContentType = uploadattachmentContentType;
	}

	public String getUploadattachmentFileName() {
		return uploadattachmentFileName;
	}

	public void setUploadattachmentFileName(String uploadattachmentFileName) {
		this.uploadattachmentFileName = uploadattachmentFileName;
	}


	public String execute(){
		System.out.print("Upload Attachment File");
		HttpSession session = getServletRequest().getSession(true);
		
		String fileName = uploadattachmentFileName.substring(0, uploadattachmentFileName.indexOf(".")); 
		String fileExt = uploadattachmentFileName.substring(uploadattachmentFileName.indexOf("."), uploadattachmentFileName.length());
		
		float attachmentLength = uploadattachment.length() / VzdnConstants.FILE_SIZE_DIVIDER;
		float allowedFileSize = Float.parseFloat(getText("newsletter.attachment.allowed.mb.filesize"));
		
		if(attachmentLength > allowedFileSize){			
			session.setAttribute("ERROR_MSG", getText("newsletter.attachment.upload.errormsg"));
			uploadattachment.delete();
			return INPUT;
		}	
		
		try{			
			String context = getServletRequest().getContextPath();
			log.debug("context: "+context);
			long salt = System.currentTimeMillis();
			//String actualPath = context + "\\" + fileName + "_" + salt + fileExt;
			String actualPath = context + "/" + fileName + fileExt;
			log.debug("actualPath: "+actualPath);
			FileUtils.copyFile(this.uploadattachment, new File(actualPath));
			
			log.debug("context: "+context);
			if(uploadattachment != null){
				session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH, actualPath);
				session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME, uploadattachmentFileName);			
				session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE, uploadattachmentContentType);
			}
			session.setAttribute(VzdnConstants.NEWS_LETTER_EMAIL_LOG, newsLetterEmailLog);
		
		} catch(IOException ex){
			log.error(ex,ex);
			log.info("Some Problem With Attachment Upload" + ex.getCause());			
			session.setAttribute("ERROR_MSG", "Some Problem With Attachment Upload");
			return INPUT;
			
		} catch(Exception ex){
			log.error(ex,ex);
			log.info("Some Problem With Attachment Upload" + ex.getCause());			
			session.setAttribute("ERROR_MSG", "Some Problem With Attachment Upload");
			return INPUT;
		}
			
		return SUCCESS;
	}
	
}