package com.netpace.notification.dao.jpa;

import com.netpace.device.dao.jpa.GenericDaoJpaImpl;
import com.netpace.notification.dao.NotificationOptOutDao;
import com.netpace.notification.entities.NotificationOptOut;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nraza
 */
@Repository(value = NotificationOptOutDao.name)
public class NotificationOptOutDaoJpaImpl
        extends GenericDaoJpaImpl<NotificationOptOut, Integer>
        implements NotificationOptOutDao {

    public NotificationOptOutDaoJpaImpl() {
        super(NotificationOptOut.class);
    }
}
