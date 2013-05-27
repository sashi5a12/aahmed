package com.packtpub.ch11.notification;

import com.packtpub.ch11.exception.ServiceException;

public interface NotificationService {
	public void notify(String message) throws ServiceException;
}