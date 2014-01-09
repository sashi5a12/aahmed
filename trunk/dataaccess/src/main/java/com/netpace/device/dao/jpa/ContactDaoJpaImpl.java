package com.netpace.device.dao.jpa;

import com.netpace.device.dao.ContactDao;
import com.netpace.device.entities.VapContact;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(ContactDao.name)
public class ContactDaoJpaImpl extends GenericDaoJpaImpl<VapContact, Integer> implements ContactDao {

    private static final Log log = LogFactory.getLog(ContactDaoJpaImpl.class);

    public ContactDaoJpaImpl() {
        super(VapContact.class);
    }

    @Override
    public VapContact getContactById(Integer contactId) {

        Query query = entityManager.createNamedQuery("findContactById");

        query.setParameter("contactId", contactId);

        return (VapContact) query.getSingleResult();
    }
}
