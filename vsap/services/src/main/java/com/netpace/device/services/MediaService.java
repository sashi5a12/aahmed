package com.netpace.device.services;

import com.netpace.device.entities.VapMedia;

public interface MediaService {
	public void saveMedia(VapMedia media);
	public void deleteMedia(Integer id);
}
