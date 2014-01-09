package com.netpace.notification.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.netpace.device.entities.BaseEntity;

@Entity
@Table(name = "vap_events")
@NamedQueries({
    @NamedQuery(name = "findEventById", query = "select event from Event event"
    + " where event.active = '1' and event.id = :eventId"),
    @NamedQuery(name = "findEventsByName", query = "select event from Event event"
        + " where event.active = '1' and event.eventName = :eventName"),
    @NamedQuery(name = "findAllEvents", query = "Select event from Event event"
    + " where event.active = '1' ORDER BY event.eventName ")
})
public class Event extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String eventName;
    private String eventDesc;
    private Set<Notification> notifications;
    private Set<Placeholder> placeholders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "event_name", length = 50)
    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Column(name = "event_desc", length = 200)
    public String getEventDesc() {
        return this.eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vap_event_place_holders",
    joinColumns = {
        @JoinColumn(name = "event_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "place_holder_id")})
    public Set<Placeholder> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Set<Placeholder> placeholders) {
        this.placeholders = placeholders;
    }
}