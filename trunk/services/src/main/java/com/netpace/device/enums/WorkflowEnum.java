/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.enums;

/**
 *
 * @author Hamza Ghayas
 */
public enum WorkflowEnum {
    
    MARKETING_REVIEW("Marketing_Review","Marketing Review"),
    EMAIL_MR("Email_MR","Email Task"),
    LEGAL_REVIEW("Legal_Review","Legal Review"),
    EMAIL_LR("Email_LR","Email Task"),
    DENY("Deny","Deny"),
    APPROVE("Approve","Approve"),
    LR_RFI("LR_RFI","RFI"),
    MR_RFI("MR_RFI","RFI"),
    LR_RFI_RESPONSE("LR_RFI_Response","RFI Response Task"),
    MR_RFI_RESPONSE("MR_RFI_Response","RFI Response Task");
   
    private WorkflowEnum(String processId,String processName){
        this.processId = processId;
        this.processName = processName;
    }
    
    private String processId;
    private String processName;

    public String getProcessId() {
        return processId;
    }
    
    public String getProcessName() {
        return processName;
    }
}
