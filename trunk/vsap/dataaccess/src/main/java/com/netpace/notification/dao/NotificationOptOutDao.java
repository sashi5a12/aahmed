package com.netpace.notification.dao;

import com.netpace.device.dao.GenericDao;
import com.netpace.notification.entities.NotificationOptOut;

/**
 *
 * @author nraza
 */
public interface NotificationOptOutDao extends GenericDao<NotificationOptOut, Integer> {

    public static final String name = "notificationOptOutDao";
}
