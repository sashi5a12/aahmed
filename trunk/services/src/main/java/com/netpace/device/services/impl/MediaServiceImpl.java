package com.netpace.device.services.impl;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.swing.JApplet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netpace.device.dao.MediaDao;
import com.netpace.device.entities.VapMedia;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.services.MediaService;


@Service(value = "mediaService")
public class MediaServiceImpl implements MediaService {

    private final static Log log = LogFactory.getLog(MediaServiceImpl.class);
   
    @Autowired
    private MediaDao mediaDao;

	public MediaDao getMediaDao() {
		return mediaDao;
	}

	public void setMediaDao(MediaDao mediaDao) {
		this.mediaDao = mediaDao;
	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void saveMedia(VapMedia media) {
		mediaDao.add(media);
	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void deleteMedia(Integer id) {
		log.debug("Delete media id form database: "+id);
		try {
			mediaDao.remove(id);
			log.debug("Media id deleted succesfully.");
		} catch (JpaObjectRetrievalFailureException e){
			throw new RecordNotFoundException("record.not.found");
		} 
		
	}
    
    

}
