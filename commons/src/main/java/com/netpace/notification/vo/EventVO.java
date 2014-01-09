package com.netpace.notification.vo;

import com.netpace.commons.vo.Record;

public class EventVO extends Record {

    private static final long serialVersionUID = 1L;
    private String eventName;
    private String eventDesc;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
