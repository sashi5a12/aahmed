package com.netpace.notification.services.impl;

import com.netpace.device.utils.enums.MessageQueueStatus;
import com.netpace.notification.dao.EmailQueueDao;
import com.netpace.notification.entities.EmailQueue;
import com.netpace.notification.services.EmailQueueService;
import com.netpace.notification.services.EmailService;
import com.netpace.notification.vo.EmailMessageVO;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("emailQueueService")
public class EmailQueueServiceImpl implements EmailQueueService {

    private final static Log log = LogFactory.getLog(EmailQueueServiceImpl.class);
    @Autowired
    private EmailQueueDao emailQueueDao;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public EmailQueue queueEmail(EmailMessageVO emailMessageVO) {
        EmailQueue emailQueue = new EmailQueue();

        log.info("queueing Email: emailMessageVO [" + ToStringBuilder.reflectionToString(emailMessageVO) + "]");

        emailQueue.setTitle(emailMessageVO.getSubject());
        emailQueue.setToAddresses(StringUtils.join(emailMessageVO.getToAddresses(), ","));
        emailQueue.setStatus(MessageQueueStatus.PENDING);
        emailQueue.setEmailMessage(emailMessageVO);
        emailQueue.populatedAuditFields("");
        emailQueueDao.add(emailQueue);

        return emailQueue;
    }

    @Override
    @Transactional
    public int processQueuedEmail(EmailQueue queuedEmail) {
        log.info("Processing Queued Email: emailMessageVO [" + ToStringBuilder.reflectionToString(queuedEmail) + "]");
        try {
            queuedEmail.setStatus(MessageQueueStatus.IN_PROCESS);
            emailQueueDao.update(queuedEmail);

            emailService.sendEmail(queuedEmail.getEmailMessage());

            log.info("Email Sent successfully");

            queuedEmail.populatedAuditFieldsOnUpdate("");
            queuedEmail.setActive(false);
            queuedEmail.setStatus(MessageQueueStatus.SENT);
            emailQueueDao.update(queuedEmail);
            
            // email sent successfully
            return 1;
        } catch (Exception e) {
            log.error("Email Sent failed");

            queuedEmail.populatedAuditFieldsOnUpdate("");
            queuedEmail.setActive(false);
            queuedEmail.setStatus(MessageQueueStatus.FAILED);
            emailQueueDao.update(queuedEmail);
            
            // email sent failed
            return 0;
        }
    }

    @Override
    @Transactional
    public int processQueuedEmails(Integer limit) {
        List<EmailQueue> queuedEmails;
        int successCount = 0;

        log.info("Processing Queued Messages: limit [" + limit + "]");

        queuedEmails = emailQueueDao.getQueuedEmails(limit);
        int i = 0;
        for (; i < queuedEmails.size(); i++) {
            EmailQueue queuedEmail = queuedEmails.get(i);
            successCount += processQueuedEmail(queuedEmail);
        }
        
        log.info("Messages processed: [" + i + "]");
        log.info("Messages Sent Successfully: [" + successCount + "]");
        log.info("Messages Failed: [" + (i-successCount) + "]");
        
        return successCount;
    }

    @Override
    @Transactional
    public void queueAndProcessEmail(EmailMessageVO emailMessageVO) {
        EmailQueue emailQueue;

        log.error("Queue and process email");

        emailQueue = this.queueEmail(emailMessageVO);
        this.processQueuedEmail(emailQueue);
    }
}
