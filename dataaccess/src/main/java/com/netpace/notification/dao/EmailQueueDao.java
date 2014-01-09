package com.netpace.notification.dao;

import com.netpace.device.dao.GenericDao;
import com.netpace.notification.entities.EmailQueue;
import java.util.List;

public interface EmailQueueDao extends GenericDao<EmailQueue, Integer> {

    public static final String name = "emailQueueDao";

    public List<EmailQueue> getQueuedEmails(Integer limit);
}