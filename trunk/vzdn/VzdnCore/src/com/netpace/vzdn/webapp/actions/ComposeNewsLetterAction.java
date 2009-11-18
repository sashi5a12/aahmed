package com.netpace.vzdn.webapp.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.Ostermiller.util.ExcelCSVParser;
import com.netpace.vzdn.exceptions.NotLoggedInException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnNewsletterEmailLog;
import com.netpace.vzdn.model.VzdnNewsletterRecieverLog;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.security.VzdnSecurityException;
import com.netpace.vzdn.security.VzdnSecurityManager;
import com.netpace.vzdn.service.INewsLetterService;
import com.netpace.vzdn.util.ConfigEnvProperties;
import com.netpace.vzdn.util.MailUtils;
import com.netpace.vzdn.util.StringFuncs;
import com.netpace.vzdn.util.VzdnNotificationConstants;

public class ComposeNewsLetterAction extends BaseAction {
	
	private static Logger log = Logger.getLogger(ComposeNewsLetterAction.class);
	
	private VzdnNewsletterEmailLog newsLetterEmailLog;
	private INewsLetterService newsLetterService;
	private String uploadedFilePath;
	private String uploadedFileName;
	private String cvsFileContent;	
	
	public INewsLetterService getNewsLetterService() {
		return newsLetterService;
	}

	public void setNewsLetterService(INewsLetterService newsLetterService) {
		this.newsLetterService = newsLetterService;
	}

	public String getUploadedFilePath() {
		return uploadedFilePath;
	}

	public void setUploadedFilePath(String uploadedFilePath) {
		this.uploadedFilePath = uploadedFilePath;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public VzdnNewsletterEmailLog getNewsLetterEmailLog() {
		return newsLetterEmailLog;
	}

	public void setNewsLetterEmailLog(VzdnNewsletterEmailLog newsLetterEmailLog) {
		this.newsLetterEmailLog = newsLetterEmailLog;
	}

	public String execute() throws VzdnSecurityException, NotLoggedInException{		
		HttpSession session = getServletRequest().getSession();
		String redirected = getServletRequest().getParameter("redirectTo");
		Object newsLetterobj = null;
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		List emailAddressesList = new ArrayList();
		
		try{
			loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
			Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);

			if (null == privileges)
				throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");			
			pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGER_NEWSLETTER);
				
			if(redirected != null){
				this.uploadedFilePath = (String) session.getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH);
				this.uploadedFileName = (String) session.getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME);
				newsLetterobj = session.getAttribute(VzdnConstants.NEWS_LETTER_EMAIL_LOG);						
			}
					
			if(newsLetterobj != null ){
				newsLetterEmailLog = (VzdnNewsletterEmailLog) newsLetterobj;
			} else {
				newsLetterEmailLog = new VzdnNewsletterEmailLog();
			}	
			
			if(redirected != null && redirected.equalsIgnoreCase("Home_CSV")){
				this.cvsFileContent = (String) session.getAttribute(VzdnConstants.CSV_FILE_CONTENT);
				
				StringBuffer buffer = new StringBuffer();
				String[] emailAddressList = null;
				
				if(newsLetterEmailLog.getEmailAddresses() != null && !newsLetterEmailLog.getEmailAddresses().equals("")){
					buffer.append(newsLetterEmailLog.getEmailAddresses());
					buffer.append(";");
				}
				
				if(cvsFileContent != null){
					
					String[][] values = ExcelCSVParser.parse(cvsFileContent);
					for (int i=0; i<values.length; i++){
					    for (int j=0; j<values[i].length; j++){
					    	emailAddressesList.add(values[i][j]);					        
					    }					 
					}
					
					emailAddressList = (String[]) emailAddressesList.toArray(new String[0]);
					Arrays.sort(emailAddressList);
					for(int index=0; index<emailAddressList.length; index++){
						if(emailAddressList[index] != null && !emailAddressList[index].equals("")){
							buffer.append(emailAddressList[index]);				
							buffer.append("; ");
						}
					}
					
				}
				
				newsLetterEmailLog.setEmailAddresses(buffer.toString());
			}
			
			String errorMsg = (String) session.getAttribute("ERROR_MSG");
			if(errorMsg != null){
				addActionError(errorMsg);
				this.addFieldError("", errorMsg);
				session.setAttribute("ERROR_MSG", null);
				session.removeAttribute("ERROR_MSG");
			}
		} catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			
			se.printStackTrace();
			throw se;
		}
			
		return INPUT;
	}
	
	public String saveAndSendEmail() throws VzdnSecurityException, NotLoggedInException, IOException{
		
		VzdnUsers loggedInUser = null;
		VzdnSysPrivileges pvlg = null;
		byte[] attachmentFileContent = null;
		HttpSession session = getServletRequest().getSession();
		String redirected = getServletRequest().getParameter("redirectTo");
		String attachmentFilePath = null;
		String fileName = null;
		File attachedFile = null;
		FileInputStream fis = null;
				
		attachmentFilePath = (String) session.getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH);
		fileName = (String) session.getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME);
		
		try {
				//TODO open these line of Code in real environment				
				loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
				Hashtable<String,VzdnSysPrivileges> privileges = (Hashtable<String, VzdnSysPrivileges>)getSession().getAttribute(VzdnConstants.VZDN_USER_PRIVILEGES);
				
				if (null == privileges)
					throw new NotLoggedInException("User Not Logged In: Forwarding to login page.");			
				pvlg = VzdnSecurityManager.getPrivilege(privileges, VzdnConstants.MANAGER_NEWSLETTER);
				
				boolean validated = validateInputs();
				if(!validated){					
					this.beforeErrorRedirect();
					return INPUT;
				}
				
				if(attachmentFilePath != null){			
					attachedFile = new File(attachmentFilePath);
					fis = new FileInputStream(attachedFile);
					attachmentFileContent = readInputStream(fis);					
				}
				
				//parse email list and get subscribed emails
				String[] emailAddress = newsLetterEmailLog.getEmailAddresses().split(";");				
				String[] subscribedUsers = newsLetterService.getVerifySubscribedUsers(emailAddress);				 
				
				//send email code here
				for(int index=0; index<subscribedUsers.length; index++){
					if(subscribedUsers[index] != null && !subscribedUsers[index].trim().equals("")){
						try{
							MailUtils.sendNewsLetterEmailAsync(emailAddress[index].trim(),
									VzdnNotificationConstants.FROM_ADDRESS, 
									newsLetterEmailLog.getEmailSubject(),
									null,
									null,
									newsLetterEmailLog.getEmailContent(),
									fileName,
									attachedFile);
						} catch(Exception ex){		
							log.info("Some Problem with generating Newsletter.  " + ex.getCause());
							addFieldError("", "Some Problem with generating Newsletter.  " + ex.getMessage());
							addActionError("Some Problem with generating Newsletter.  " + ex.getMessage() );
							this.beforeErrorRedirect();
							return INPUT;
						}
						
					}					
				}

				//Add data for VZDN_newslettere_log
				newsLetterEmailLog.setUserId(loggedInUser.getUserId());
				//newsLetterEmailLog.setUserId(new Integer(1));
				newsLetterEmailLog.setEmailDateTime(new Timestamp(System.currentTimeMillis()));
				newsLetterEmailLog.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				newsLetterEmailLog.setCreatedBy(loggedInUser.getUserName());
				//newsLetterEmailLog.setCreatedBy("Naeem");
				
				if(subscribedUsers.length > 0){
					Set<VzdnNewsletterRecieverLog> emailRecievers = new HashSet<VzdnNewsletterRecieverLog>();
					for(int index=0; index<subscribedUsers.length; index++){
						if(subscribedUsers[index] != null && !subscribedUsers[index].trim().equals("")){
							VzdnNewsletterRecieverLog newsletterRecieverLog =  new VzdnNewsletterRecieverLog(subscribedUsers[index].trim());
							newsletterRecieverLog.setVzdnNewsletterEmailLog(newsLetterEmailLog);								
							emailRecievers.add(newsletterRecieverLog);
						}
					}
					newsLetterEmailLog.setRecieversList(emailRecievers);
					
					// Save into DB
					newsLetterService.saveNewsLetter(newsLetterEmailLog, attachmentFileContent);					
				}
				
				//Remove Session Attributes
				Object obj = null;
				
				session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH, obj);
				session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME, obj);
				session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE, obj);
				session.setAttribute(VzdnConstants.CSV_FILE_CONTENT, obj);			
				
				session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH);
				session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME);
				session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE);
				session.removeAttribute(VzdnConstants.CSV_FILE_CONTENT);
				
			
		} catch(VzdnSecurityException se){
			if(null != loggedInUser && null != pvlg)		
				log.info("User " +  loggedInUser.getUserName() + " tried to access privilege " + pvlg.getPrivilegeName());
			
			se.printStackTrace();
			throw se;
			
		} catch(FileNotFoundException exp){			
			log.info("Attachment File Not Found" + exp.getCause());	
			addFieldError("", "Attachment File Not Found");
			addActionError("Attachment File Not Found ");
			this.beforeErrorRedirect();
			return INPUT;
		} catch(IOException exp){			
			log.info("I/O Error." + exp.getStackTrace());	
			addFieldError("", "I/O Error.");
			addActionError("I/O Error. ");
			this.beforeErrorRedirect();
			return INPUT;
		} catch(HibernateException exp){
			log.error("Some Problem with generating Newsletter " + exp.getStackTrace());
			sendExceptionEmailToAdmin(exp);
			return "SEND";
		} catch(Exception exp){
			log.error(exp.getStackTrace());
			sendExceptionEmailToAdmin(exp);
			return "SEND";
		} finally{
			if(fis != null){
				fis.close();
				//attachedFile.delete();					
			}			
		}		
		
		return "SEND";
	}
	
	public String cancelNewsLetter(){
		
		Object obj = null;		
		HttpSession session = getServletRequest().getSession();
		session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH, obj);
		session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME, obj);
		session.setAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE, obj);
		session.setAttribute(VzdnConstants.CSV_FILE_CONTENT, obj);	
		session.setAttribute(VzdnConstants.NEWS_LETTER_EMAIL_LOG, obj);
		
		session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH);
		session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME);
		session.removeAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_CONTENT_TYPE);
		session.removeAttribute(VzdnConstants.CSV_FILE_CONTENT);
		session.removeAttribute(VzdnConstants.NEWS_LETTER_EMAIL_LOG);
	
		return "CANCEL";
	}
	
	public boolean validateInputs(){
				
		boolean validated = true;
		
		if(null == newsLetterEmailLog.getEmailSubject() || "".equals(newsLetterEmailLog.getEmailSubject())){
			validated = false;
			addFieldError("newsLetterEmailLog.emailSubject","Email Subject is a required field!");
		}			
		
		if(null == newsLetterEmailLog.getEmailContent() || "".equals(newsLetterEmailLog.getEmailContent())){
			validated = false;
			addFieldError("newsLetterEmailLog.emailContent","Email Content is a required field!");	
		}		
		
		if(null == newsLetterEmailLog.getEmailAddresses() || "".equals(newsLetterEmailLog.getEmailAddresses())){
			validated = false;
			addFieldError("newsLetterEmailLog.emailAddresses","Email Address is a required field!");
		}
		
		if(containsInvalidCharacters(newsLetterEmailLog.getEmailAddresses())){
			validated = false;		
			addFieldError("newsLetterEmailLog.emailAddresses","Invalid Comma Separated String ");
		}
		
		String[] emails = StringFuncs.tokenize(newsLetterEmailLog.getEmailAddresses(), ";");
		for(int i=0; i<emails.length; i++){
			if(null != emails[i] && !emails[i].trim().equals("")){
				if(!StringFuncs.isEmail(emails[i].trim()))
				{
					addFieldError("newsLetterEmailLog.emailAddresses","Invalid Email [" + emails[i] + "]" );
					validated = false;
					break;
				}
			}			
		}
		
		if(!validated){
			return false;
		} return true;
		
	}
	
	private void beforeErrorRedirect(){
		HttpSession session = getServletRequest().getSession();
		this.uploadedFilePath = (String) session.getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_PATH);
		this.uploadedFileName = (String) session.getAttribute(VzdnConstants.ATTACHMENT_FILE_UPLOAD_NAME);
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
	
	public boolean containsInvalidCharacters(String csv){
		char[] INVALID_EMAIL_SEPARATORS = {':','\'','\"','[',']','{','}','=',
													'(',')','*','&','^','%','$','#','!',
													'~','`','<','>','?','/','|','\\'
												};		
		boolean invalidSeparator=false;
		for(char c : INVALID_EMAIL_SEPARATORS){			
			if(csv.contains(Character.toString(c))){
				invalidSeparator = true;
				break;
			}
		}
		return invalidSeparator;
	}
	
	private void sendExceptionEmailToAdmin(Exception exception){
		
		StackTraceElement stackTraceElements[] = exception.getStackTrace();
		
		StringBuffer buffer = new StringBuffer("");
		buffer.append("ERROR INFORMATION: \n");
		buffer.append("Error Occured On: " + new Date() );
		buffer.append("\n\n");
		buffer.append("Following Error/Exception has accured after newsletter email generation. \n\n");
		buffer.append("Error Message: " + exception.getMessage());		
		buffer.append("\n-------------------------------------------------------------------------\n");
		
		for(StackTraceElement ste : stackTraceElements){			
			buffer.append(ste.toString() + "\n");			
		}
		
		buffer.append("\n\n\n");
		
		
		ConfigEnvProperties conf=ConfigEnvProperties.getInstance();        		
		String redirectEmailToAdmin = conf.getProperty("mailUtils.redirectionEmailAddress");
		
		try{
			MailUtils.sendMail(redirectEmailToAdmin,
					VzdnNotificationConstants.FROM_ADDRESS, 
					"VZDN Newsletter Exception",
					null,
					buffer.toString());
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}

