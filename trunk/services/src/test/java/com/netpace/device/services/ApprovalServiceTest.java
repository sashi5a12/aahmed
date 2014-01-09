package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 *
 * @author trafique
 */
public class ApprovalServiceTest extends BaseServiceTest {
    
    private static final Log log = LogFactory.getLog(ApprovalServiceTest.class);
    
    @Resource(name = "approvalService")
    ApprovalService approvalService;
    
    @Test
    public void testAll() throws Exception {
        WorkItem workItem = new WorkItem();

        workItem.setTitle("DeviceMarketingReview");
        workItem.setStatus(WorkitemStatus.InProgress.toString());
        workItem.setAllowedRoles(Arrays.asList(StringUtils.split("ROLE_ADMIN", ",")));
        workItem.setRequireInput(false);
        workItem.setNextActions(Arrays.asList(StringUtils.split("Approve,Deny,RFI", ",")));
        
        WorkflowVO workflowVO = new WorkflowVO();
        workflowVO.setId(1);
        workItem.setWorkflow(workflowVO);
        workItem.setStartDate(new Date());
        
        //testAddWorkitem(workItem, loggerUser);
        
        workItem.setStatus(WorkitemStatus.Processed.toString());
        workItem.setEndDate(new Date());
        //testUpdateWorkitem(workItem, "super.admin");
    }
    
    public void testAddWorkitem(WorkItem workItem, String loggedInUserName){
        log.info("testAddWorkitem");
        approvalService.addWorkitem(workItem, loggedInUserName);
    }
    
    public void testUpdateWorkitem(WorkItem workItem, String loggedInUserName){
        log.info("testUpdateWorkitem");
        approvalService.updateWorkitem(workItem, loggedInUserName);
    }
    
}
