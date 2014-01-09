package com.netpace.device.controllers;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.enums.EventType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.SystemRoleService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.device.vo.WorkItem;
import com.netpace.notification.services.EventService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author trafique
 */
@Controller
public class DelayedWorkitemNotificationController {

    private static final Log log = LogFactory.getLog(DelayedWorkitemNotificationController.class);
    @Autowired
    private EventService eventService;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private SystemRoleService systemRoleService;
    
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TABLE = "workitem.delay.placeholder.html.table";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_THEAD = "workitem.delay.placeholder.html.thead";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TR = "workitem.delay.placeholder.html.tr";
    
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TABLE_TableContent = "{TableContent}";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_WorkItem = "{WorkItem}";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_Type = "{Type}";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_Company = "{Company}";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_Product = "{Product}";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_StartDate = "{StartDate}";
    private final String APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_SubmitDate = "{SubmitDate}";
    
    @RequestMapping("/notify/delayedworkitems.do")
    public @ResponseBody
    String delayedWorkitems(HttpServletRequest request) {
        Integer workitemDelayInDays;
        List<WorkItem> delayedWorkitems;
        Map<String, String> params;
        String message = "error";
        HashMap<String, List<WorkItem>> mapWorkitems = new HashMap<String, List<WorkItem>>();

        log.debug("Notifying Delayed Workitems - Started");

        workitemDelayInDays = applicationPropertiesService.workitemDelayInDays();
        List<SystemRoleVO> allSystemRoles = systemRoleService.getAllSystemRoles();

        if (workitemDelayInDays != null) {
            Date olderThanDate = VAPUtils.getDateOlderThanDays(workitemDelayInDays);
            delayedWorkitems = approvalService.getDelayedWorkitems(olderThanDate);
            
            log.debug("workitemDelayInDays["+workitemDelayInDays+"], olderThanDate["+olderThanDate.toString()+ "], noOfDelayedWorkitems["+delayedWorkitems.size()+"]");
            
            for (WorkItem workItem : delayedWorkitems) {

                for (SystemRoleVO systemRoleVO : allSystemRoles) {
                    String roleName = systemRoleVO.getRoleName();

                    if (workItem.getAllowedRoles().contains(roleName)) {
                        if (mapWorkitems.get(roleName) != null) {
                            mapWorkitems.get(roleName).add(workItem);
                        } else {
                            mapWorkitems.put(roleName, new ArrayList<WorkItem>());
                            mapWorkitems.get(roleName).add(workItem);
                            log.debug("delayed workitems contains items for role ["+roleName+"]");
                        }
                    }
                }
            }

            params = new HashMap<String, String>();
            for (Map.Entry<String, List<WorkItem>> entry : mapWorkitems.entrySet()) {
                String roleName = entry.getKey();
                List<WorkItem> list = entry.getValue();

                if (roleName.equals(VAPConstants.ROLE_PARTNER_USER)) {
                    HashMap<String, List<WorkItem>> mapPartnerWorkitems = new HashMap<String, List<WorkItem>>();

                    for (WorkItem workItem : list) {
                        if (mapPartnerWorkitems.get(String.valueOf(workItem.getWorkflow().getCompanyId())) != null) {
                            mapPartnerWorkitems.get(String.valueOf(workItem.getWorkflow().getCompanyId())).add(workItem);
                        } else {
                            mapPartnerWorkitems.put(String.valueOf(workItem.getWorkflow().getCompanyId()), new ArrayList<WorkItem>());
                            mapPartnerWorkitems.get(String.valueOf(workItem.getWorkflow().getCompanyId())).add(workItem);
                        }
                    }

                    for (Map.Entry<String, List<WorkItem>> entry1 : mapPartnerWorkitems.entrySet()) {
                        String companyId = entry1.getKey();
                        List<WorkItem> list1 = entry1.getValue();

                        params.put(VAPConstants.PLACEHOLDER_WORKITEMS_LIST + roleName + companyId, getWorkitemsListPlaceholder(list1));
                    }
                } else {
                    params.put(VAPConstants.PLACEHOLDER_WORKITEMS_LIST + roleName, getWorkitemsListPlaceholder(list));
                }
            }
            eventService.raiseEvent(EventType.WORKITEM_DELAYED.toString(), params, null, null, null, null);

            message = "Delayed workitems notified successfully: " + delayedWorkitems.size();
        }

        log.debug("Notifying Delayed Workitems - Completed");

        return message;
    }

    protected String getWorkitemsListPlaceholder(List<WorkItem> workitems) {
        StringBuilder strWorkitems = new StringBuilder();
        String strOutput;
        String appPropertyPlaceholderHtmlTable = applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TABLE);
        String appPropertyPlaceholderHtmlThead = applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_THEAD);
        String appPropertyPlaceholderHtmlTr = applicationPropertiesService.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TR);

        strWorkitems.append(appPropertyPlaceholderHtmlThead);
        for (WorkItem workItemVO : workitems) {
            String strRow = StringUtils.replace(appPropertyPlaceholderHtmlTr, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_WorkItem, workItemVO.getDisplayName());
            strRow = StringUtils.replace(strRow, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_Type, workItemVO.getWorkflow().getWorkflowType());
            strRow = StringUtils.replace(strRow, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_Company, StringUtils.defaultString(workItemVO.getWorkflow().getCompanyName()));
            strRow = StringUtils.replace(strRow, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_Product, StringUtils.defaultString(workItemVO.getWorkflow().getProductName()));
            strRow = StringUtils.replace(strRow, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_StartDate, DateFormatUtils.format(workItemVO.getStartDate(), "yyyy-MM-dd"));
            strRow = StringUtils.replace(strRow, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TD_SubmitDate, DateFormatUtils.format(workItemVO.getWorkflow().getStartDate(), "yyyy-MM-dd"));
            strWorkitems.append(strRow);
        }

        strOutput = StringUtils.replace(appPropertyPlaceholderHtmlTable, APP_PROPERTY_WORKITEM_DELAY_PLACEHOLDER_HTML_TABLE_TableContent, strWorkitems.toString());

        return strOutput;
    }
}
