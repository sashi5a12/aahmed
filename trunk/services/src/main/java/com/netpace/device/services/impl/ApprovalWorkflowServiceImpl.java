package com.netpace.device.services.impl;

import com.netpace.device.services.ApprovalWorkflowService;
import static com.netpace.device.utils.VAPConstants.VAP_WORKFLOW_VAR_BUSINESS_KEY;
import com.netpace.device.utils.enums.WorkflowType;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.device.vo.WorkItem;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to serve activiti workflow specific tasks
 *
 * @author trafique
 */
@Service("approvalWorkflowService")
public class ApprovalWorkflowServiceImpl implements ApprovalWorkflowService {

    private final static Log log = LogFactory.getLog(ApprovalWorkflowServiceImpl.class);
    @Autowired
    TaskService taskService;
    @Autowired
    RuntimeService runtimeService;

    /**
     * Start approval process
     *
     * @param workflowType
     * @param workflowId
     * @param loggedInUser
     *
     */
    @Override
    public void startApproval(String workflowType, Integer workflowId, Map<String, Object> additionalVars, VAPUserDetails loggedInUser) {

        ProcessInstanceQuery processInstanceQuery;
        ProcessInstance processInstance;
        Map<String, Object> variables;
        WorkflowType enumWorkflowType;

        enumWorkflowType = WorkflowType.getByLabel(workflowType);
        String processKey = enumWorkflowType.getProcessDefinitionId();
        String businessKeyValue = String.valueOf(workflowId);

        log.info("Starting workflow process: workflowType["+workflowType+"], workflowId [" + workflowId + "], loggedUser["+loggedInUser.getUsername()+"]");

        // Query the approval process instance by record id
        processInstanceQuery = runtimeService.createProcessInstanceQuery().processDefinitionId(processKey)
                .processInstanceBusinessKey(businessKeyValue);

        // If process instance not found, start process
        if (processInstanceQuery.count() == 0) {

            variables = new HashMap<String, Object>();
            variables.put(VAP_WORKFLOW_VAR_BUSINESS_KEY, workflowId);
            variables.putAll(additionalVars);
            processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKeyValue, variables);

            log.info("Company workflow process initiated: workflowId [" + workflowId + "], processInstanceId [" + processInstance.getProcessInstanceId() + "]");
        } else {
            processInstance = processInstanceQuery.singleResult();

            log.info("Company workflow process already initiated: workflowId [" + workflowId + "], processInstanceId [" + processInstance.getProcessInstanceId() + "]");
        }
    }

    /**
     * process workflow workitem
     *
     * @param workItemVO
     * @param loggedInUser
     */
    @Override
    public void processWorkitem(WorkItem workItemVO, Map<String, Object> additionalVars, VAPUserDetails loggedInUser) {

        TaskQuery taskQuery;
        Task task;
        String taskId;
        Map<String, Object> variables;

        log.info("Processing workitem: workflowId[" + workItemVO.getWorkflow().getId() + "], title [" + workItemVO.getTitle() + "], decision [" + workItemVO.getDecision() + "]");

        // Query the task
        taskQuery = taskService.createTaskQuery().taskDefinitionKey(workItemVO.getTitle())
                .processVariableValueEquals(VAP_WORKFLOW_VAR_BUSINESS_KEY, workItemVO.getWorkflow().getId());

        // If task found, complete it with response
        if (taskQuery.count() == 1) {
            task = taskQuery.singleResult();
            taskId = task.getId();

            // If no other user working on the task
            if (task.getAssignee() == null) {
                taskService.setAssignee(taskId, loggedInUser.getUsername());
                
                try {
                    variables = new HashMap<String, Object>();
                    variables.putAll(additionalVars);
                    taskService.complete(taskId, variables);

                    log.info("Workitem processed: workflowId [" + workItemVO.getWorkflow().getId() + "], title [" + workItemVO.getTitle() + "], decision [" + workItemVO.getDecision() + "]");
                    
                } catch (Exception e) {
                    // on any exceptional condition release task and throw exception upward
                    taskService.setAssignee(taskId, null);
                    log.error("Rolling back taskAssignee due to unknown error [" + workItemVO.getWorkflow().getId() + "], title [" + workItemVO.getTitle() + "], decision [" + workItemVO.getDecision() + "]", e);
                    throw new RuntimeException(e);
                }
            }else{
                 log.info("Task already under processing: workflowId [" + workItemVO.getWorkflow().getId() + "], title [" + workItemVO.getTitle() + "], decision [" + workItemVO.getDecision() + "]");
            }

        } else {
            log.info("Workitem task not found: workflowId [" + workItemVO.getWorkflow().getId() + "], title [" + workItemVO.getTitle() + "]");
        }
    }
}
