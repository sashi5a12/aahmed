package com.netpace.notification.entities;

import com.netpace.device.entities.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vap_notifications")
@NamedQueries({
    @NamedQuery(name = "findNotificationById", query = "from Notification notification"
    + " where notification.id = :notificationId and notification.active = '1' "),
    @NamedQuery(name = "findNotificationsByEvent", query = "from Notification notification"
    + " where notification.event.eventName = :eventName and notification.active = '1' ")
})
public class Notification extends BaseEntity implements
        java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String title;
    private String description;
    private String emailText;
    private String emailPlainText;
    private Event event;
    private Set<NotificationRole> notificationRoles = new HashSet<NotificationRole>(0);
    private Set<NotifAdHocRecipient> notifAdHocRecipients = new HashSet<NotifAdHocRecipient>(0);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "notification_title", length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "notification_description", length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "email_text")
    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

    @Column(name = "email_plain_text")
    public String getEmailPlainText() {
        return emailPlainText;
    }

    public void setEmailPlainText(String emailPlainText) {
        this.emailPlainText = emailPlainText;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "event_id")
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "notification")
    public Set<NotificationRole> getNotificationRoles() {
        return notificationRoles;
    }

    public void setNotificationRoles(Set<NotificationRole> notificationRoles) {
        this.notificationRoles = notificationRoles;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "notification")
    public Set<NotifAdHocRecipient> getNotifAdHocRecipients() {
        return notifAdHocRecipients;
    }

    public void setNotifAdHocRecipients(Set<NotifAdHocRecipient> notifAdHocRecipients) {
        this.notifAdHocRecipients = notifAdHocRecipients;
    }
}