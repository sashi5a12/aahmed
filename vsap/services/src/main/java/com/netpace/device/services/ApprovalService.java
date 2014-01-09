package com.netpace.device.services;

import com.netpace.device.utils.enums.WorkflowType;
import com.netpace.device.vo.CommentVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trafique
 */
public interface ApprovalService {

    public PageModel<WorkItem> getPagedList(VAPUserDetails loggedInUser, PageModel<WorkItem> pageModel);

    public void processWorklist(List<WorkItem> workItems, VAPUserDetails loggedInUser);

    public void startApprovalProcess(WorkflowType workflowType, Integer recordId, VAPUserDetails loggedUser);

    public void addWorkitem(WorkItem workItem, String loggedUserName);

    public void updateWorkitem(WorkItem workItem, String loggedUserName);

    public void addWorkflowComment(CommentVO commentVO, VAPUserDetails loggedInUser);

    public void sendWorkflowNotification(String eventTitle, Integer workflowId, Map<String, Object> variables);

    public List<CommentVO> getPartnerComments(Integer companyId, VAPUserDetails loggedInUser);

    public List<CommentVO> getProductComments(Integer productId, VAPUserDetails loggedInUser);

    public List<WorkItem> getPartnerWorkitems(Integer companyId, VAPUserDetails loggedInUser);

    public void processWorkitem(WorkItem workItem, VAPUserDetails loggedInUser);

    public void acceptAgreement(WorkItem workItem, VAPUserDetails loggedInUser);

    public void submitAgreement(WorkItem workItem, VAPUserDetails loggedInUser);

    public WorkflowVO getWorkflow(Integer workflowId);
    
    public List<WorkItem> getDelayedWorkitems(Date olderThanDate);
}
