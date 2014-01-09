package com.netpace.device.enums;

public enum AccountRegistrationBusinessRule implements BusinessRule{
    
    ACCOUNT_USERNAME_EXISTS("Account already exists", 1),
    ACCOUNT_EMAIL_EXISTS("Account with email address already exists", 2),
    ACCOUNT_ACTIVATION_CODE_NOT_FOUND("Account activation code not found.", 3),
    ACTIVATION_TYPE_ADMIN("Activation type admin.", 4);
    
    AccountRegistrationBusinessRule(String message,int code){
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
