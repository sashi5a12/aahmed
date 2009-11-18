package com.netpace.vzdn.webapp.actions;

import java.io.File;

import javax.servlet.http.HttpSession;

import com.netpace.vzdn.global.VzdnConstants;

public class AttachmentFileUploadAction extends BaseAction {
	

	
	private File uploadfile;
	private String uploadfileFileName;
	private String uploadfileConteneType;
	
		
	public File getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}

	public String getUploadfileFileName() {
		return uploadfileFileName;
	}

	public void setUploadfileFileName(String uploadfileFileName) {
		this.uploadfileFileName = uploadfileFileName;
	}
	
	public String getUploadfileConteneType() {
		return uploadfileConteneType;
	}
	
	public void setUploadfileConteneType(String uploadfileConteneType) {
		this.uploadfileConteneType = uploadfileConteneType;
	}

	public String execute(){
		System.out.println("Upload File action");
		if(uploadfile != null){
			HttpSession session = getServletRequest().getSession();
			session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH, uploadfile);
			session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME, uploadfileFileName);
		}
		
		return SUCCESS;
	}

}
