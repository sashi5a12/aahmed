package com.netpace.vzdn.service;

import java.util.List;

import com.netpace.vzdn.model.VzdnUsers;

public interface UserMgmtService {
	
	public List<VzdnUsers> getAllUsers();
	
	//public void createNewUser(VzdnUsers user);
	
	public void updateUser(VzdnUsers user);
	
	public void removeUser(VzdnUsers user);

	public VzdnUsers getUserById(Integer userId);
}
