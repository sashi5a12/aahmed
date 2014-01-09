package com.netpace.vic.dao;

import com.netpace.vic.dto.UserApplication;

public interface UserApplicationDAO extends GenericDAO{

	public void saveUserApplication(UserApplication userApplication);
	
}
