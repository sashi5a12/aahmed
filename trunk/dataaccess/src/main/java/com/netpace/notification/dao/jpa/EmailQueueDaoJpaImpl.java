package com.netpace.notification.dao.jpa;

import com.netpace.device.dao.jpa.GenericDaoJpaImpl;
import com.netpace.notification.dao.EmailQueueDao;
import com.netpace.notification.entities.EmailQueue;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(value = EmailQueueDao.name)
public class EmailQueueDaoJpaImpl extends GenericDaoJpaImpl<EmailQueue, Integer> implements EmailQueueDao {

    private static final Log log = LogFactory.getLog(EmailQueueDaoJpaImpl.class);

    public EmailQueueDaoJpaImpl() {
        super(EmailQueue.class);
    }

    @Override
    public List<EmailQueue> getQueuedEmails(Integer limit) {
        Query query = entityManager.createNamedQuery("findQueuedEmails");
        query.setMaxResults(limit);
        List<EmailQueue> list = query.getResultList();

        return list;
    }
}
