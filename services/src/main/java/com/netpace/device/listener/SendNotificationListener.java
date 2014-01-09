package com.netpace.device.listener;

import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.impl.VAPApplicationContext;
import java.util.Map;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author trafique
 */
public class SendNotificationListener implements JavaDelegate {

    private final static Log log = LogFactory.getLog(SendNotificationListener.class);
    private ApprovalService approvalService;
    private FixedValue event;
    private FixedValue param;

    public SendNotificationListener() {
        this.approvalService = (ApprovalService) VAPApplicationContext.getApplicationContext().getBean("approvalService");
    }

    @Override
    public void execute(DelegateExecution de) throws Exception {
        Integer workflowId;
        Map<String, Object> variables;

        log.info("SendNotificationListener: event[" + event.getExpressionText() + "]");
        workflowId = Integer.valueOf(de.getProcessBusinessKey());
        variables = de.getVariables();

        approvalService.sendWorkflowNotification(event.getExpressionText(), workflowId, variables);
    }

    public FixedValue getEvent() {
        return event;
    }

    public void setEvent(FixedValue event) {
        this.event = event;
    }

    public FixedValue getParam() {
        return param;
    }

    public void setParam(FixedValue param) {
        this.param = param;
    }
}
