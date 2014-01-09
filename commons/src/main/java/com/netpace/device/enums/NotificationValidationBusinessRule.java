package com.netpace.device.enums;

public enum NotificationValidationBusinessRule implements BusinessRule {

    NOTIFICATION_EVENT_NOT_EXISTS(1, "Event does not exists"),
    NOTIFICATION_NOT_EXISTS(2, "Notificatoin does not exist");
    
    private int code;
    private String message;

    private NotificationValidationBusinessRule(int code, String message) {
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
