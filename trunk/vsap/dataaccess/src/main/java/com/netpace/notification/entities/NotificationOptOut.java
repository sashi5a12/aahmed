/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.notification.entities;

import com.netpace.device.entities.BaseEntity;
import com.netpace.device.entities.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Wakram
 */
@Entity
@Table(name="vap_notification_optout")
public class NotificationOptOut extends BaseEntity implements Serializable  {

    private Integer id;
    private Notification notification;
    private User user;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_optout_id", unique=true, nullable=false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="notification_id")
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
