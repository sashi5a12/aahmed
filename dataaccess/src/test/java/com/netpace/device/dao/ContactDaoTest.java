package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.VapContact;
import javax.annotation.Resource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ContactDaoTest extends AbstractDaoTest {

    private static final Log log = LogFactory.getLog(ContactDaoTest.class);
    @Resource(name = ContactDao.name)
    ContactDao contactDao;

    @Test
    public void testGetContactById() {
//        Integer contactId = 2;
//        VapContact contact = contactDao.getContactById(contactId);
//        log.info(ToStringBuilder.reflectionToString(contact));
    }
}
