package com.netpace.device.services;

import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import java.util.Map;

/**
 *
 * @author trafique
 */
public interface ApprovalWorkflowService {

    public void startApproval(String workflowType, Integer workflowId, Map<String, Object> additionalVars, VAPUserDetails loggedInUser);

    public void processWorkitem(WorkItem workItemVO, Map<String, Object> additionalVars, VAPUserDetails loggedInUser);
}
