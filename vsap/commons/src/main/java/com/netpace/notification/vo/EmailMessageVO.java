package com.netpace.notification.vo;

import com.netpace.commons.vo.Record;
import java.util.List;

public class EmailMessageVO extends Record {
    
    private String fromAddress;
    private List<String> toAddresses;
    private String subject;
    private String content;
    private String plainText;
    private boolean htmlContent;
    
    public EmailMessageVO(){
        super();
        htmlContent = true;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public List<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(boolean htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
}
