package com.netpace.device.listener;

import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.impl.VAPApplicationContext;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_COMMENT;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_DECISION;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_LOGGED_USER_NAME;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author trafique
 */
public class UpdateWorkitemListener extends BaseWorkitemListener {

    private final static Log log = LogFactory.getLog(SendNotificationListener.class);
    private ApprovalService approvalService;

    public UpdateWorkitemListener() {
        this.approvalService = (ApprovalService) VAPApplicationContext.getApplicationContext().getBean("approvalService");
    }

    @Override
    public void execute(DelegateExecution de) throws Exception {
        WorkItem workItem;
        String loggedInUserName;
        Integer workflowId;
        String decision, comment;
        Map<String, Object> variables;

        log.info("UpdateWorkitemListener: title[" + getStep().getExpressionText() + "], status[" + getStatus().getExpressionText() + "], workflowId[" + de.getProcessBusinessKey() + "]");

        variables = de.getVariables();
        // this try catch added to support old workflows
        try{
            loggedInUserName = (String) variables.get(VAP_WORKFLOW_VAR_LOGGED_USER_NAME);
        }catch(ClassCastException cce){
            loggedInUserName = ((VAPUserDetails) variables.get(VAP_WORKFLOW_VAR_LOGGED_USER_NAME)).getUsername();
        }
        workflowId = Integer.valueOf(de.getProcessBusinessKey());
        decision = (String) de.getVariable(VAP_WORKFLOW_VAR_DECISION);
        comment = (String) de.getVariable(VAP_WORKFLOW_VAR_COMMENT);
        
        workItem = new WorkItem();
        workItem.setTitle(getStep().getExpressionText());
        workItem.setStatus(getStatus().getExpressionText());
        workItem.setDecision(decision);
        workItem.setCommentText(comment);
        
        WorkflowVO workflowVO = new WorkflowVO();
        workflowVO.setId(workflowId);
        workItem.setWorkflow(workflowVO);

        approvalService.updateWorkitem(workItem, loggedInUserName);
    }
}
