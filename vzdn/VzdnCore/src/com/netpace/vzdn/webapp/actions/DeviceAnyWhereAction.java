package com.netpace.vzdn.webapp.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.netpace.vzdn.dao.IDeviceAnyWhere;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.DeviceAnyWhere;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.DeviceAnyWhereService;

public class DeviceAnyWhereAction extends BaseAction{

	private static Logger log = Logger.getLogger(DeviceAnyWhereAction.class);
	
	private String deviceAnyWhereUrl;
	private DeviceAnyWhereService deviceAnyWhereService;
	
	
	public String forward(){
		
		try {
			ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
			String deviceAnyWhereUrlProp = rb.getString("device.anywhere.url");
			if(getServletRequest().getSession().getAttribute(VzdnConstants.LOGGED_IN_USER)!=null){
				log.debug("-----user found for device any where");
				VzdnUsers users = (VzdnUsers) getServletRequest().getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
				deviceAnyWhereUrl=deviceAnyWhereUrlProp+"?email="+users.getUserName();
				DeviceAnyWhere deviceClass = new DeviceAnyWhere();
				deviceClass.setEmailAddress(users.getUserName());
				log.debug("users.getGtmCompanyId() " + users.getGtmCompanyId());
				deviceClass.setGtmCompanyId(users.getGtmCompanyId());
				deviceClass.setDatetime(new Timestamp(new Date().getTime()));
				deviceAnyWhereService.saveDeviceInfo(deviceClass);
				getServletResponse().sendRedirect(deviceAnyWhereUrl);
				log.debug("-----deviceAnyWhereUrl "+deviceAnyWhereUrl);
			}else{
				log.debug("-----user NNOT found for device any where");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public String getDeviceAnyWhereUrl() {
		return deviceAnyWhereUrl;
	}

	public void setDeviceAnyWhereUrl(String deviceAnyWhereUrl) {
		this.deviceAnyWhereUrl = deviceAnyWhereUrl;
	}

	public DeviceAnyWhereService getDeviceAnyWhereService() {
		return deviceAnyWhereService;
	}

	public void setDeviceAnyWhereService(DeviceAnyWhereService deviceAnyWhereService) {
		this.deviceAnyWhereService = deviceAnyWhereService;
	}
	
	
	
	
}
