package com.netpace.device.dao;

import com.netpace.device.entities.VapContact;

public interface ContactDao extends GenericDao<VapContact, Integer> {
    public static final String name="contactDao";

    public VapContact getContactById(Integer contactId);
}
