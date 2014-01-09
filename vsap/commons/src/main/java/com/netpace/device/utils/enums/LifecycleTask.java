package com.netpace.device.utils.enums;

/**
 * enumeration to represent lifecycle user tasks defined in company process
 *
 * @author trafique
 */
public enum LifecycleTask {

    MarketingReviewEmail("Marketing_Review_Email", "Marketing_Review_Email", LifecycleTaskType.ServiceTask, null),
    MarketingReviewCompleteEmail("Marketing_Review_Complete_Email", "Marketing_Review_Complete_Email", LifecycleTaskType.ServiceTask,  null),
    MarketingReviewRFIEmail("Marketing_Review_RFI_Email", "Marketing_Review_RFI_Email", LifecycleTaskType.ServiceTask, null),
    MarketingReviewRFICompleteEmail("Marketing_Review_RFI_Complete_Email", "Marketing_Review_RFI_Complete_Email", LifecycleTaskType.ServiceTask, null),
    LegalReviewEmail("Legal_Review_Email", "Legal_Review_Email", LifecycleTaskType.ServiceTask, null),
    LegalReviewCompleteEmail("Legal_Review_Complete_Email", "Legal_Review_Complete_Email", LifecycleTaskType.ServiceTask, null),
    LegalReviewRFIEmail("Legal_Review_RFI_Email", "Legal_Review_RFI_Email", LifecycleTaskType.ServiceTask, null),
    LegalReviewRFICompleteEmail("Legal_Review_RFI_Complete_Email", "Legal_Review_RFI_Complete_Email", LifecycleTaskType.ServiceTask, null),
    Approve("Approve", "Approve", LifecycleTaskType.ServiceTask, null), Deny("Deny", "Deny", LifecycleTaskType.ServiceTask, null),
    
    MarketingReview("Marketing_Review", "Marketing Review", LifecycleTaskType.UserTask, "marketingDecision"),
    MarketingReviewRFI("Marketing_Review_RFI", "Marketing Review RFI", LifecycleTaskType.UserTask, "marketingDecision"),
    LegalReview("Legal_Review", "Legal Review", LifecycleTaskType.UserTask, "legalDecision"),
    LegalReviewRFI("Legal_Review_RFI", "Legal Review RFI", LifecycleTaskType.UserTask, "legalDecision");
    
    private String id;
    private String name;
    private LifecycleTaskType taskType;
    private String decisionVariable;

    private LifecycleTask(String id, String name, LifecycleTaskType taskType, String decisionVariable) {
        this.id = id;
        this.name = name;
        this.taskType = taskType;
        this.decisionVariable = decisionVariable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecisionVariable() {
        return decisionVariable;
    }

    public void setDecisionVariable(String decisionVariable) {
        this.decisionVariable = decisionVariable;
    }

    public LifecycleTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(LifecycleTaskType taskType) {
        this.taskType = taskType;
    }

    public static LifecycleTask getById(String id) {

        for (LifecycleTask phase : values()) {
            if (phase.getId().equals(id)) {
                return phase;
            }
        }

        return null;
    }
}
