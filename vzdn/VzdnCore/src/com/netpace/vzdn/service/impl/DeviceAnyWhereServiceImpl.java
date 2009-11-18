package com.netpace.vzdn.service.impl;

import com.netpace.vzdn.dao.IDeviceAnyWhere;
import com.netpace.vzdn.model.DeviceAnyWhere;
import com.netpace.vzdn.service.DeviceAnyWhereService;

public class DeviceAnyWhereServiceImpl implements DeviceAnyWhereService{

	private IDeviceAnyWhere deviceAnyWhereDao;
	
	public IDeviceAnyWhere getDeviceAnyWhereDao() {
		return deviceAnyWhereDao;
	}
	public void setDeviceAnyWhereDao(IDeviceAnyWhere deviceAnyWhereDao) {
		this.deviceAnyWhereDao = deviceAnyWhereDao;
	}




	public void saveDeviceInfo(DeviceAnyWhere deviceAnyWhere) throws Exception {
		deviceAnyWhereDao.saveDeviceInfo(deviceAnyWhere);
		
	}
	
	
	

}
