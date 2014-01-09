package com.netpace.notification.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NotifAdHocRecipientId implements Serializable {

    private static final long serialVersionUID = -2867795597703540530L;
    private Integer notificationId;
    private String emailAddress;

    public NotifAdHocRecipientId() {
        super();
    }

    public NotifAdHocRecipientId(Integer notificationId, String emailAddress, Timestamp createdDate, String createdBy, Timestamp lastUpdatedDate, String lastUpdatedBy, String rowEnable) {
        this.notificationId = notificationId;
        this.emailAddress = emailAddress;
    }

    @Column(name = "notification_id")
    public Integer getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    @Column(name = "email_address", length = 100)
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result
                + ((notificationId == null) ? 0 : notificationId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NotifAdHocRecipientId other = (NotifAdHocRecipientId) obj;
        if (emailAddress == null) {
            if (other.emailAddress != null) {
                return false;
            }
        } else if (!emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if (notificationId == null) {
            if (other.notificationId != null) {
                return false;
            }
        } else if (!notificationId.equals(other.notificationId)) {
            return false;
        }
        return true;
    }
}
