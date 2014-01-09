package com.netpace.device.listener;

import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.impl.VAPApplicationContext;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_LOGGED_USER_NAME;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import com.netpace.device.vo.WorkflowVO;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author trafique
 */
public class AddWorkitemListener extends BaseWorkitemListener {

    private final static Log log = LogFactory.getLog(AddWorkitemListener.class);
    private ApprovalService approvalService;

    public AddWorkitemListener() {
        this.approvalService = (ApprovalService) VAPApplicationContext.getApplicationContext().getBean("approvalService");
    }

    @Override
    public void execute(DelegateExecution de) throws Exception {
        WorkItem workItem;
        Integer workflowId;
        Map<String, Object> variables;
        String loggedInUserName;
        
        log.info("AddWorkitemListener: title[" + getStep().getExpressionText() + "], status[" + getStatus().getExpressionText() + "], workflowId[" + de.getProcessBusinessKey() + "]");

        variables = de.getVariables();
        // this try catch added to support old workflows
        try{
            loggedInUserName = (String) variables.get(VAP_WORKFLOW_VAR_LOGGED_USER_NAME);
        }catch(ClassCastException cce){
            loggedInUserName = ((VAPUserDetails) variables.get(VAP_WORKFLOW_VAR_LOGGED_USER_NAME)).getUsername();
        }
        workflowId = Integer.valueOf(de.getProcessBusinessKey());

        workItem = new WorkItem();
        workItem.setTitle(getStep().getExpressionText());
        workItem.setDisplayName(getTitle()!=null? getTitle().getExpressionText() : "");
        workItem.setStatus(getStatus().getExpressionText());
        workItem.setAllowedRoles(Arrays.asList(StringUtils.split(getAllowedRoles().getExpressionText(), ",")));
        workItem.setRequireInput(Boolean.valueOf(getRequireInput().getExpressionText()));
        workItem.setNextActions(Arrays.asList(StringUtils.split(getNextActions().getExpressionText(), ",")));
        
        WorkflowVO workflowVO = new WorkflowVO();
        workflowVO.setId(workflowId);
        workItem.setWorkflow(workflowVO);
        
        workItem.setStartDate(new Date());

        approvalService.addWorkitem(workItem, loggedInUserName);
    }
}
