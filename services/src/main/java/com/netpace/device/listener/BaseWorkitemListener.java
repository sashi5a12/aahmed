package com.netpace.device.listener;

import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.impl.VAPApplicationContext;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.el.FixedValue;

/**
 *
 * @author trafique
 */
public abstract class BaseWorkitemListener implements JavaDelegate {

    private FixedValue step;
    private FixedValue status;
    private FixedValue title;
    private FixedValue allowedRoles;
    private FixedValue requireInput;
    private FixedValue nextActions;

    public FixedValue getStep() {
        return step;
    }

    public void setStep(FixedValue step) {
        this.step = step;
    }

    public FixedValue getStatus() {
        return status;
    }

    public void setStatus(FixedValue status) {
        this.status = status;
    }

    public FixedValue getTitle() {
        return title;
    }

    public void setTitle(FixedValue title) {
        this.title = title;
    }

    public FixedValue getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(FixedValue allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    public FixedValue getRequireInput() {
        return requireInput;
    }

    public void setRequireInput(FixedValue requireInput) {
        this.requireInput = requireInput;
    }

    public FixedValue getNextActions() {
        return nextActions;
    }

    public void setNextActions(FixedValue nextActions) {
        this.nextActions = nextActions;
    }

    public ApprovalService getApprovalService() {
        ApprovalService approvalService = (ApprovalService) VAPApplicationContext.getApplicationContext().getBean("approvalService");
        return approvalService;
    }
}
