package com.netpace.device.enums;

public enum UserValidationBusinessRule implements BusinessRule{
    
    EMAIL_USER_EXISTS(1, "User already exists"),
    USERNAME_USER_EXISTS(2, "User already exists"),
    USERNAME_TEMP_USER_EXISTS(3, "User already exists"),
    ACTIVATIONCODE_TEMP_USER_NOT_EXISTS(4, "User does not exists"),
    ACTIVATION_TYPE_ACCOUNT(5, "Activation type account."),
    CHANGE_USERNAME_INVALID_CURRENT_PASSWORD(6, "Invalid password."),
    CHANGE_PASWORD_INVALID_CURRENT_PASSWORD(7, "Invalid password."),
    COMPANY_USER_NOT_EXISTS(8, "Company user does not exist."),
    COMPANY_USER_IS_SAME(9, "Company user is same."),
    USER_NOT_EXISTS(10, "User does not exist."),
    ADMIN_USER_NOT_EXISTS(11, "Company user does not exist."),
    COMPANY_DOMAIN_IS_BLOCKED(12, "Company domain is blocked.");
    
    private int code;
    private String message;
    
    private UserValidationBusinessRule(int code, String message){
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
