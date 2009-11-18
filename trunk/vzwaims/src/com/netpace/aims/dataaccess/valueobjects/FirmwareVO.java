package com.netpace.aims.dataaccess.valueobjects;

import java.util.List;

public class FirmwareVO {
	String phoneModel;

	/*
		Array of firmware information
		[0] - Long - firmwareId
		[1] - String - "MR-"+mrNo 
	*/
	List firmwareList;
	
	public String getPhoneModel() {
		return phoneModel;
	}
	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
	public List getFirmwareList() {
		return firmwareList;
	}
	public void setFirmwareList(List firmwareList) {
		this.firmwareList = firmwareList;
	}
		
}
