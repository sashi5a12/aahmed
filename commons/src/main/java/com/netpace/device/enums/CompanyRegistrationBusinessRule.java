package com.netpace.device.enums;

public enum CompanyRegistrationBusinessRule implements BusinessRule{
    
    
    COMPANY_ALREADY_EXISTS("Company with domain name already exists", 11),
    INVALID_COUNTRY("Invalid country configuration", 12);
    
    
    CompanyRegistrationBusinessRule(String message,int code){
        this.message = message;
        this.code = code;
    }
    private String message;
    private int code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
    
}
