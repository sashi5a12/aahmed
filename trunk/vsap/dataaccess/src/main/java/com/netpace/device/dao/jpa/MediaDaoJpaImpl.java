package com.netpace.device.dao.jpa;


import com.netpace.device.dao.MediaDao;
import com.netpace.device.entities.VapMedia;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(MediaDao.name)
public class MediaDaoJpaImpl extends GenericDaoJpaImpl<VapMedia, Integer> implements MediaDao {

    private static final Log log = LogFactory.getLog(MediaDaoJpaImpl.class);

    public MediaDaoJpaImpl() {
        super(VapMedia.class);
    }
	
}
