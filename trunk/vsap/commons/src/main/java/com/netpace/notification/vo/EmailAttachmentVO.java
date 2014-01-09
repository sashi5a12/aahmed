package com.netpace.notification.vo;

import com.netpace.device.vo.Record;

public class EmailAttachmentVO extends Record{
    
    private String attachmentName;
    private byte[] attachmentData;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public byte[] getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(byte[] attachmentData) {
        this.attachmentData = attachmentData;
    }

}
