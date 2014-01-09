package com.netpace.device.utils.enums;

/**
 *
 * @author trafique
 */
public enum WorkflowType {
    
    PartnerWorkflow("Partner Workflow", "VAP_Company_Approval_Process"),
    ProductWorkflow("Product Workflow", "VAP_Product_Approval_Process");
    
    private String label;
    private String processDefinitionId;
    
    private WorkflowType(String label, String processDefinitionId){
        this.label = label;
        this.processDefinitionId = processDefinitionId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }
    
    public static WorkflowType getByLabel(String label){
        for (WorkflowType workflowType : values()) {
            if(workflowType.label.equals(label))
                return workflowType;
        }
        return null;
    }
            
    public static WorkflowType getByProcessDefinitionId(String processDefinitionId){
        for (WorkflowType workflowType : values()) {
            if(workflowType.processDefinitionId.equals(processDefinitionId))
                return workflowType;
        }
        return null;
    }
            
}
