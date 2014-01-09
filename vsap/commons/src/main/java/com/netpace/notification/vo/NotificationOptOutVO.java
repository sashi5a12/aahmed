package com.netpace.notification.vo;

import com.netpace.commons.vo.Record;

/**
 *
 * @author nraza
 */
public class NotificationOptOutVO extends Record {

    private Integer notificationId;
    private String title;
    private String description;
    private Integer notificationOptOutId;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNotificationOptOutId() {
        return notificationOptOutId;
    }

    public void setNotificationOptOutId(Integer notificationOptOutId) {
        this.notificationOptOutId = notificationOptOutId;
    }
}
