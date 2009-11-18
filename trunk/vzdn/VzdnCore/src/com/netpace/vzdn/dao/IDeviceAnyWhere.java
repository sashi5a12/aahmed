package com.netpace.vzdn.dao;

import java.io.Serializable;

import com.netpace.vzdn.model.DeviceAnyWhere;

public interface IDeviceAnyWhere<T,PK extends Serializable> extends IGenericDAO<T, PK> {
	
	public void saveDeviceInfo(DeviceAnyWhere deviceAnyWhere) throws Exception;

}
