package com.netpace.device.enums;

public enum CompanyValidationBusinessRule implements BusinessRule{
    
    DOMAIN_COMPANY_ALREADY_EXISTS(1, "Company already exists"),
    COMPANY_DOMAIN_IS_BLOCKED(2, "Company domain is blocked."),
    COMPANY_DOES_NOT_EXIST(3, "Company does not exist."),
    COMPANY_NAME_IS_DUPLICATE(4, "Company name is duplicate.");
    
    private int code;
    private String message;
    
    private CompanyValidationBusinessRule(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
    
}
