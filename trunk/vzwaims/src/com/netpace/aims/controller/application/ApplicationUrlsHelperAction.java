package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.bo.application.ApplicationsManagerHelper;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.Utility;
import com.netpace.aims.model.application.AimsAppLite;
import com.netpace.aims.model.events.AimsEventLite;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class takes care of misc application url related tasks, like export url, enable disable network usage etc.
 *
 * @struts.action path="/applicationUrlsHelper"
 *                scope="request"
 * @author Sajjad Raza
 */
public class ApplicationUrlsHelperAction extends BaseAction {
    private static Logger log = Logger.getLogger(ApplicationUrlsHelperAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        String appsId = "";
        Long appsIdValue = new Long(0);
        AimsAppLite appLite = null;

        String task = "";
        Object o_param;
        o_param = request.getParameter("appsId");
        if (o_param != null) {
            appsId = request.getParameter("appsId");
            appsIdValue = new Long(appsId);
        }

        o_param = request.getParameter("task");
        if (o_param != null) {
            task = request.getParameter("task");
        }

        try {
            if( appsIdValue.longValue()!=0 && !StringFuncs.isNullOrEmpty(task)) {
                appLite = ApplicationsManagerHelper.getAimsAppLite(appsIdValue);

                if(appLite!=null) {//application found
                    if(task.equalsIgnoreCase("enableDisableNetworkUsage")) {
                        String fwdPath = this.getAppPath(appLite.getAimsPlatformId(), appsIdValue);
                        ActionForward appViewForward = new ActionForward("appViewForward", fwdPath, true);
                        String networkUsageValue = StringFuncs.NullValueReplacement(request.getParameter("networkUsage"));
                        String[] oldAppNetworkURLs = null;
                        int rowsUpdated = 0;

                        //save network usage value
                        if(networkUsageValue.equalsIgnoreCase(AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE) ||
                            networkUsageValue.equalsIgnoreCase(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE)) {

                            oldAppNetworkURLs = AimsApplicationsManager.getAppNetworkUrlValues(appsIdValue);//get old network urls,

                            rowsUpdated = AimsApplicationsManager.saveAimsAppNetworkUsage(appsIdValue, networkUsageValue);
                            log.debug("Network usage set to "+networkUsageValue+", appsId= "+appsIdValue);
                            
                            if(networkUsageValue.equalsIgnoreCase(AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE) && rowsUpdated > 0) {
                                /*** if network usage is disabled, then delete application urls ***/
                                ApplicationsManagerHelper.deleteAppNetworkUrls(appsIdValue);

                                //check if status is not saved, and if app network urls changed, if yes then send notification
                                if( (appLite.getAimsLifecyclePhaseId()!=null)
                                        && (appLite.getAimsLifecyclePhaseId().longValue() != AimsConstants.SAVED_ID.longValue())) {
                                    if(ApplicationHelper.checkAppNetworkUrlsChanged(oldAppNetworkURLs, null)) {
                                        log.debug("Application urls changed. appsId= "+appLite.getAppsId());
                                        String appTitle = appLite.getTitle();
                                        String allianceName = AllianceManager.getAllianceCompanyName(appLite.getAimsAllianceId());
                                        allianceName = StringFuncs.NullValueReplacement(allianceName); //if no alliance found set to blank string

                                        //send notification
                                        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_APPLICATION_URLS_CHANGED);
                                        if (aimsEvent != null) {
                                            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, allianceName);
                                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, appTitle);
                                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()));
                                            aimsEvent.raiseEvent(aimsEventObject);
                                        }
                                    }
                                }//end send notification if urls changed
                            }
                        }
                        return appViewForward;
                    }
                    else if(task.equalsIgnoreCase("exportApplicationUrl")) {
                        //check access for EXPORT_CONTENT_FILTER_URLS
                        if((ApplicationHelper.checkAccess(request, "edit", AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS))) {
                            String urlFileName="";
                            String content="";
                            String [] applicationUrls = null;

                            String currentDate = (new SimpleDateFormat("yyyyMMddHHmm")).format(new Date());
                            String allianceName = AllianceManager.getAllianceCompanyName(appLite.getAimsAllianceId());
                            String appTitle = StringFuncs.NullValueReplacement(appLite.getTitle());
                            String appTitleForFileName = appTitle;
                            
                            appTitleForFileName = StringFuncs.replaceInvalidCharacters(appTitle, "_");

                            urlFileName = appTitleForFileName + "_URLs_"+currentDate+".csv";

                            response.reset();
                            response.setHeader("Content-Disposition", "attachment; filename=" + urlFileName);
                            response.setContentType("plain/text");
                                                                                  
                            appTitle = Utility.replace(appTitle, "\"", "\"\"", Utility.REPLACE_ALL);
                            allianceName = Utility.replace(allianceName, "\"", "\"\"", Utility.REPLACE_ALL);
                                                        
                            String heading="Alliance Name,Application Name,URL\n";
                            String devTitle = "\"" + allianceName + "\"" + "," + "\"" + appTitle + "\"" + ",";
                            
                            //get application urls
                            applicationUrls = AimsApplicationsManager.getAppNetworkUrlValues(appsIdValue);
                            if (applicationUrls!=null && applicationUrls.length>0){
                            	for(int i=0; i<applicationUrls.length; i++){
                                    applicationUrls[i] = Utility.replace(applicationUrls[i], "\"", "\"\"", Utility.REPLACE_ALL);
                                    applicationUrls[i] = devTitle + "\"" + applicationUrls[i] + "\"" ;
                            	}
                            }
                            content = StringFuncs.ConvertArrToString(applicationUrls, "\n");
                            content = heading+content;
                            
                            response.setContentLength(content.length());
                            //write content to response
                            response.getWriter().write(content);
                            return null;
                        }
                        else {
                            //show error
                            /*ActionErrors errors = new ActionErrors();
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.security"));
                            saveErrors(request, errors);*/
                            log.debug("ERROR: Request to Export Application urls and user does not have privilege. appsId= "+appLite.getAppsId());
                            return null;
                        }

                    }
                }
            }
        }
        catch(HibernateException he) {
            System.out.println("HibernateException occured in ApplicationUrlsHelperAction");
            he.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Exception occured in ApplicationUrlsHelperAction");
            e.printStackTrace();
        }
        return null;
    }

    private String getAppPath(Long platformId, Long appsId) {
        String path = "";
        if(platformId.equals(AimsConstants.BREW_PLATFORM_ID)) {
            path = "/brewApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.SMS_PLATFORM_ID)) {
            path = "/smsApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.MMS_PLATFORM_ID)) {
            path = "/mmsApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.WAP_PLATFORM_ID)) {
            path = "/wapApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.ENTERPRISE_PLATFORM_ID)) {
            path = "/entApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.VCAST_PLATFORM_ID)) {
            path = "/vcastApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.DASHBOARD_PLATFORM_ID)) {
        	path = "/dashboardApplicationSetup.do";
        }
        else if(platformId.equals(AimsConstants.JAVA_PLATFORM_ID)) {
        	path = "/javaApplicationSetup.do";
        }

        if(!StringFuncs.isEmpty(path)) {
            //generate query string
            path = path+"?task=edit&appsId="+appsId;
        }
        return path;
    }

    private String getAppPath(HttpServletRequest request, Long platformId, Long appsId) {
        String path = "";
        String contextPath = "/aims";
        MessageResources manageAppBundle = this.getResources(request, ManageApplicationsConstants.RES_BUNDLE);
        if(platformId.equals(AimsConstants.BREW_PLATFORM_ID)) {
            path = manageAppBundle.getMessage("ManageApplicationForm.brew.app.setup.url");
        }
        else if(platformId.equals(AimsConstants.SMS_PLATFORM_ID)) {
            path = manageAppBundle.getMessage("ManageApplicationForm.sms.app.setup.url");
        }
        else if(platformId.equals(AimsConstants.MMS_PLATFORM_ID)) {
            path = manageAppBundle.getMessage("ManageApplicationForm.mms.app.setup.url");
        }
        else if(platformId.equals(AimsConstants.WAP_PLATFORM_ID)) {
            path = manageAppBundle.getMessage("ManageApplicationForm.wap.app.setup.url");
        }
        else if(platformId.equals(AimsConstants.ENTERPRISE_PLATFORM_ID)) {
            path = manageAppBundle.getMessage("ManageApplicationForm.enterprise.app.setup.url");
        }
        else if(platformId.equals(AimsConstants.VCAST_PLATFORM_ID)) {
            path = manageAppBundle.getMessage("ManageApplicationForm.vcast.app.setup.url");
        }

        if(!StringFuncs.isEmpty(path)) {
            if(path.indexOf(contextPath)!=-1) {
                //delete /aims in start of path
                path = path.substring(contextPath.length());
            }
            //generate query string
            path = path+"?task=view&appsId="+appsId;
        }
        return path;
    }
}