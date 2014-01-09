package com.netpace.notification.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
import javax.persistence.Table;

import com.netpace.device.entities.BaseEntity;

@Entity
@Table(name = "vap_place_holders")
@NamedQueries({
    @NamedQuery(name = "findPlaceholderById", query = "Select placeholder from Placeholder placeholder"
    + " where placeholder.active = '1' and id = :placeholderId"),
    @NamedQuery(name = "findPlaceholderByName", query = "Select placeholder from Placeholder placeholder"
    + " where placeholder.active = '1' and displayName = :displayName")
})
public class Placeholder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String displayName;
    private Set<Event> events = new HashSet<Event>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_holder_id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "place_holder_display_name", length = 200)
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vap_event_place_holders",
    joinColumns = {
        @JoinColumn(name = "place_holder_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "event_id")})
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

}