package com.netpace.device.entities.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum RegistrationStatus {

    TERMS_AND_CONDITIONS("Terms and Conditions",true,false), NDA_PREWORK("NDA Prework",true,false),SUBMIT_REVIEW("Submit and Review",true,false),
    MARKETING_REVIEW("Device Marketing Review",false,true), MARKETING_RFI("Device Marketing RFI",false,true),
    LEGAL_REVIEW("Legal Review",false,true), LEGAL_RFI("Legal RFI",false,true), 
    DENIED("Registration denied",false,false), APPROVED("Company Approved",false,false);
    
    private RegistrationStatus(String description,boolean companyRegistrationInProgress,boolean approvalInProgress) {
        this.description = description;
        this.companyRegistrationInProgress= companyRegistrationInProgress;
        this.approvalInProgress=approvalInProgress;
    }
    
    
    private String description;
    private boolean companyRegistrationInProgress;
    private boolean approvalInProgress;
    

    public String getDescription() {
        return description;
    }

    public boolean isCompanyRegistrationInProgress() {
        return companyRegistrationInProgress;
    }

    public boolean isApprovalInProgress() {
        return approvalInProgress;
    }
    
    public boolean isApproved(){
        return this.equals(APPROVED);
    }
    
    public boolean isDenied(){
        return this.equals(DENIED);
    }
    private static List<RegistrationStatus> registeredCompanyStatuses;
    static{
        List<RegistrationStatus> list = new ArrayList<RegistrationStatus>();
        for(RegistrationStatus status : values()){
            if(status.isApprovalInProgress()||status.isApproved()){
                list.add(status);
            }
        }
        registeredCompanyStatuses = Collections.unmodifiableList(list);
    }
    public static List<RegistrationStatus> registeredCompanyStatuses(){
        return registeredCompanyStatuses;
    }
}
