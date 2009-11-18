package com.netpace.aims.controller.rest;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.VZAppZoneApplicationManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.rest.RestManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.model.application.AimsVzappFirmwarePaidLog;
import com.netpace.aims.model.application.AimsWapFtpLog;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.rest.AimsRestUrlLog;
import com.netpace.aims.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 *
 * @struts.action path="/zonRestAPI"
 *                scope="request"  
 * @struts.action-forward name="zonRestResponse" path="/rest/zonRestAPIResponse.jsp"
 * @author Sajjad Raza
 */

public class RestAPIAction extends BaseAction {
    private static Logger log = Logger.getLogger(RestAPIAction.class.getName());
    private static String secretPassword;
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String moduleName = request.getParameter("module");
        String user_id = request.getParameter("user_id");
        Date currentDate = new Date();
        AimsRestUrlLog restURLLog = null;
        
        String forward = "zonRestResponse";

        String queryString = (StringFuncs.isNullOrEmpty(request.getQueryString()) ? "" : ("?"+request.getQueryString()) );
        StringBuffer requestURL = request.getRequestURL().
                                            append(queryString);

        String remoteIPAddress = null;

        if (request.getHeader("X-Forwarded-For") == null)
            remoteIPAddress = request.getRemoteAddr();
        else
            remoteIPAddress = request.getHeader("X-Forwarded-For");
        
        //log rest request url
        restURLLog = new AimsRestUrlLog(requestURL.toString(), remoteIPAddress, "system", currentDate);
        RestManager.saveOrUpdateRestURLLog(restURLLog);

        this.setAckResponse(request, "", "");

        //check url parameters, if missing then call notification
        if( StringFuncs.isNullOrEmpty(moduleName)
            || StringFuncs.isNullOrEmpty(user_id)) {

            //TODO send missing parameter mail
            this.sendAckErrorMail(request, "Incorrect URL",
                    "Incorrect API Call.");
            this.setAckResponse(request, "ERROR", "Invalid API Call!");
        }
        else { //if all parameters found in URL
            //if user is allowed to ack module then change status in log table
            if(RestManager.validateRestUser(new Long(user_id), moduleName)) {
                if(moduleName.equals(AimsConstants.REST_MODULE_WAP_FTP)) {
                    //if module is wapAckFTP then process its status
                    this.processWAPAckFTPStatus(request, new Long(user_id), currentDate);
                }
                if(moduleName.equals(AimsConstants.REST_MODULE_VZAPPZONE_ACK)) {
                	this.processVzAppPaymentRequest(request, user_id, restURLLog.getRestUrlLogId());
                }
                else {
                    this.setAckResponse(request, "ERROR", "Invalid Module!");
                }
            }//end if validate user
            else {
                //if user not validated
                //TODO set invalid user mail
                this.sendAckErrorMail(request, "Incorrect USER ID or module name",
                        "Either User ID: "+user_id+" or module: "+moduleName+" not exists or it is not allowed to change "+moduleName+" module log.");
                this.setAckResponse(request, "ERROR", "Authorization Failed!");
            }

        }
        return mapping.findForward(forward);
    }

    private void processVzAppPaymentRequest(HttpServletRequest request, String userId, Long restUrlLogId)throws Exception{
    	String device_uuid=request.getParameter("device_uuid");
    	String key=request.getParameter("key");
    	
    	if(StringFuncs.isNullOrEmpty(device_uuid) || StringFuncs.isNullOrEmpty(key)) {
    		this.sendAckErrorMail(request, "Incorrect Intertek URL","Incorrect API Call.");
    	    this.setAckResponse(request, "ERROR", "Invalid API Call!");
    	    return;
    	}
    	if (!StringUtils.isNumeric(device_uuid)){
    		this.sendAckErrorMail(request, "Incorrect DeviceUUID","DeviceUUID is not a number.");
    	    this.setAckResponse(request, "ERROR", "Invalid DeviceUUID!");
    	    return;
    	}
    	if (StringFuncs.isNullOrEmpty(secretPassword)){
    		secretPassword=CommonProperties.getInstance().getProperty("vzappzone.secretKey.password");
    	}
    	
    	Long companyId=VZAppZoneApplicationManager.getAllianceIdByBinnaryFirmwareId(new Long(device_uuid));
    	if (companyId == null){
    		this.sendAckErrorMail(request, "CompanyId not found","No alliance is assosicated with DeviceUUID: "+device_uuid);
    	    this.setAckResponse(request, "ERROR", "Invalid CompanyId!");
    	    return;    		
    	}
    	
    	String generatedKey=MiscUtils.getBase64Digest(device_uuid, companyId.toString(), secretPassword);
    	if (!key.equals(generatedKey)){
    		this.sendAckErrorMail(request, "Incorrect Intertek Key","Invalid key was supplied in URL.\nkey: "+key);
    	    this.setAckResponse(request, "ERROR", "Invalid Key!");
    	    return;
    	}
    	
    	boolean flag=VZAppZoneApplicationManager.updateFirmwareIsPaid(new Long(device_uuid));
    	if (flag){    		
    		AimsVzappFirmwarePaidLog log=new AimsVzappFirmwarePaidLog();
    		log.setBinaryFirmwareId(new Long(device_uuid));
    		log.setRestUserId(new Long(userId));
    		log.setRestUrlLogId(restUrlLogId);
    		log.setCreatedDate(new Date());
    		VZAppZoneApplicationManager.savePaidLog(log);
    		this.setAckResponse(request, "OK", "Processed!");
    	}
    	else {
    	    this.setAckResponse(request, "ERROR", "DeviceUUID doesnot exist or duplicate request with previous deviceUUID!");
    	}
    }
    
    private void processWAPAckFTPStatus(HttpServletRequest request,
                                        Long user_id, Date ackDate)  throws Exception {

        String fileID = request.getParameter("filename");
        String ackStatus = request.getParameter("status");
        
        AimsWapFtpLog wapFTPLog = null;

        //if status and fileID is missing in URL, send parameter missing response
        if(StringFuncs.isNullOrEmpty(fileID)
            || StringFuncs.isNullOrEmpty(ackStatus)) {
            this.sendAckErrorMail(request, "Incorrect URL",
                    "Incorrect API Call.");
            this.setAckResponse(request, "ERROR", "Invalid API Call!");
            return;
        }
        
        String remoteIPAddress = null;

        if (request.getHeader("X-Forwarded-For") == null)
            remoteIPAddress = request.getRemoteAddr();
        else
            remoteIPAddress = request.getHeader("X-Forwarded-For");

        // get ftp log by file name and userid
        wapFTPLog = RestManager.getWapFTPLog(fileID);
        if(wapFTPLog != null) {
            if(wapFTPLog.getFileStatus().equalsIgnoreCase(AimsConstants.WAP_FTP_STATUS_PENDING)) {
                // if log status is Pending (P) and given ack status is S or F and set it accordingly
                if(ackStatus.equalsIgnoreCase(AimsConstants.WAP_FTP_STATUS_SUCCESS)
                    || ackStatus.equalsIgnoreCase(AimsConstants.WAP_FTP_STATUS_FAILURE)) {
                    //set status of log
                    wapFTPLog.setRestUserId(user_id);
                    wapFTPLog.setFileStatus(ackStatus);
                    wapFTPLog.setAckIpAddress(remoteIPAddress);
                    wapFTPLog.setDateAckReceived(ackDate);
                    wapFTPLog.setLastUpdatedBy("system");
                    wapFTPLog.setLastUpdatedDate(ackDate);
                    RestManager.saveOrUpdateWapFTPLog(wapFTPLog);

                    if(ackStatus.equalsIgnoreCase(AimsConstants.WAP_FTP_STATUS_FAILURE)) {
                        //get application and alliance name
                        AimsAppLite appLite = ApplicationsManagerHelper.getAimsAppLite(wapFTPLog.getWapApplicationId());
                        String applicationTitle = ((appLite!=null) ? appLite.getTitle() : ""); //if no application found set to blank string
                        String allianceName = AllianceManager.getAllianceCompanyName(wapFTPLog.getAllianceId());
                        allianceName = StringFuncs.isNullOrEmpty(allianceName) ? "" : allianceName; //if no alliance found set to blank string

                        //TODO if status is failure, send notification
                        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_WAP_FTP_FAILURE);
                        if (aimsEvent != null) {
                            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, allianceName);
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, applicationTitle);
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_FTP_FILE_NAME, wapFTPLog.getFtpFileName());
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (ackDate.toString()));
                            aimsEvent.raiseEvent(aimsEventObject);
                        }
                    }//end if status failure
                    log.debug("Status changed in WAP FTP Log to "+ackStatus+", FileID: "+fileID);
                    this.setAckResponse(request, "OK", "Processed!");
                }
                else {
                    //TODO if status is not S or F, send failure message
                    this.setAckResponse(request, "ERROR", "Status can be only 'S' (Success) or 'F' (Failure)!");
                }
            }
            else if(wapFTPLog.getFileStatus().equalsIgnoreCase(AimsConstants.WAP_FTP_STATUS_SUCCESS)
                    || wapFTPLog.getFileStatus().equalsIgnoreCase(AimsConstants.WAP_FTP_STATUS_FAILURE)) {
                //TODO already processed mail
                this.sendAckErrorMail(request, "File ID "+fileID+" already processed ",
                        "File ID "+fileID+" already processed. Request to change its status to: "+ackStatus+".");
                this.setAckResponse(request, "ERROR", "Filename has been already processed!");
            }

        }//end if wapFTPLog not null
        else {
            //else if log not found for this given filename then handle its error
            this.setAckResponse(request, "ERROR", "Filename not found!");
            //TODO File not present mail
            this.sendAckErrorMail(request, "File ID "+fileID+" not present ",
                        "File ID "+fileID+" not present in Wap FTP Log.");
        }
    }

    private void setAckResponse(HttpServletRequest request, String response_status, String response_desc) {
        request.setAttribute("response_status", response_status);
        request.setAttribute("response_desc", response_desc);
    }


    private void sendAckErrorMail(HttpServletRequest request, String subject, String content)
                throws Exception{
        StringBuffer ackErrorEmailSubject = new StringBuffer("Rest API ACK Alert: ");
        StringBuffer ackErrorEmailContent = new StringBuffer(content);

        ackErrorEmailSubject.append(subject);
        ackErrorEmailContent.append(MiscUtils.getRequestInfo(request));
        MailUtils.sendMail(
                    AimsConstants.EMAIL_EXCEPTION_ADMIN,
                    "exceptions@netpace.com",
                    ackErrorEmailSubject.toString(),
                    null,
                    ackErrorEmailContent.toString());
    }

}
