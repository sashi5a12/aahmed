package com.netpace.vzdn.webapp.actions;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.netpace.vzdn.global.VzdnConstants;

public class AttachmentRemoveAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(AttachmentRemoveAction.class);
		
	public String execute(){
		
		HttpSession session = getServletRequest().getSession();
		
		try
		{
			String filePath = getServletRequest().getParameter("fpath");
			
			File file = new File(filePath);
			file.delete();			
			
			Object obj = null;
			
			session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH, obj);
			session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME, obj);
			session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH);
			session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME);			
		
		} catch(Exception exp){
			log.error("Some issue with attachment file.");
			session.setAttribute("ERROR_MSG", "Some issue with attachment file.");			
			return INPUT;
		} 
		
		return SUCCESS;
	}

}
