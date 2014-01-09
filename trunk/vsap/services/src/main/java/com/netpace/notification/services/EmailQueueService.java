package com.netpace.notification.services;

import com.netpace.notification.entities.EmailQueue;
import com.netpace.notification.vo.EmailMessageVO;

public interface EmailQueueService {

    public EmailQueue queueEmail(EmailMessageVO emailMessageVO);

    public int processQueuedEmail(EmailQueue emailQueue);

    public int processQueuedEmails(Integer limit);

    public void queueAndProcessEmail(EmailMessageVO emailMessageVO);
}
