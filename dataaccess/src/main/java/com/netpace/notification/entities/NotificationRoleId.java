package com.netpace.notification.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NotificationRoleId implements Serializable {

    private static final long serialVersionUID = 1060133950788629769L;
    private Integer notificationId;
    private Integer roleId;

    public NotificationRoleId() {
        super();
    }

    public NotificationRoleId(Integer notificationId, Integer roleId) {
        this.notificationId = notificationId;
        this.roleId = roleId;
    }

    @Column(name = "notification_id", nullable = false)
    public Integer getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    @Column(name = "role_id", nullable = false)
    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof NotificationRoleId)) {
            return false;
        }
        NotificationRoleId castOther = (NotificationRoleId) other;

        return ((this.getNotificationId() == castOther.getNotificationId()) || (this.getNotificationId() != null && castOther.getNotificationId() != null && this.getNotificationId().equals(castOther.getNotificationId())))
                && ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null && castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getNotificationId() == null ? 0 : this.getNotificationId().hashCode());
        result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
        return result;
    }
}
