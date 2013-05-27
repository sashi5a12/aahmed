package com.packtpub.ch11.advice;

import org.hibernate.HibernateException;
import org.springframework.aop.ThrowsAdvice;

import com.packtpub.ch11.exception.ServiceException;
import com.packtpub.ch11.notification.NotificationService;

public class NotificationThrowsAdvice implements ThrowsAdvice {
	NotificationService notificationService;// initialized via IoC

	public void afterThrowing(HibernateException ex) throws Throwable {
		notificationService.notify(ex.getMessage());
		throw new ServiceException(ex);
	}

	public void afterThrowing(java.io.IOException ex) throws Throwable {
	       notificationService.notify(ex.getMessage());
	       throw new ServiceException(ex);
	}
	public void afterThrowing(NullPointerException ex) throws Throwable {
	       notificationService.notify(ex.getMessage());
	       throw new ServiceException(ex);
	}
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
