package com.netpace.aims.dataaccess.valueobjects;

import java.io.Serializable;

public class DashboardChannelVO implements Serializable{
	String channelId;
	String appTile;
	String companyName;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getAppTile() {
		return appTile;
	}
	public void setAppTile(String appTile) {
		this.appTile = appTile;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
