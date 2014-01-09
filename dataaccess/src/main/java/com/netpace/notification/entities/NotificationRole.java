package com.netpace.notification.entities;

import com.netpace.device.entities.BaseEntity;
import com.netpace.device.entities.SystemRole;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vap_notification_roles")
public class NotificationRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;
    private NotificationRoleId id;
    private SystemRole systemRole;
    private Notification notification;

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "notificationId", column =
        @Column(name = "notification_id", nullable = false)),
        @AttributeOverride(name = "roleId", column =
        @Column(name = "role_id", nullable = false))})
    public NotificationRoleId getId() {
        return this.id;
    }

    public void setId(NotificationRoleId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false, insertable = false, updatable = false)
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NotificationRole other = (NotificationRole) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}