package com.packtpub.ch11.notification;

import com.packtpub.ch11.exception.NotifyException;
import com.packtpub.ch11.exception.ServiceException;

public class NotificationServiceImpl implements NotificationService {
	Notifier notifier;
	boolean retryOnFail;

	public void notify(String message) throws ServiceException {
		try {
			notifier.notify(message);
		} catch (NotifyException e) {
			if (retryOnFail) {
				// notifying again
				notify(message);
			} else {
				throw new ServiceException(e);
			}
		}
	}
	
	public void setNotifier(Notifier notifier) {
	       this.notifier = notifier;
	}
	     public void setRetryOnFail(boolean retryOnFail) {
	       this.retryOnFail = retryOnFail;
	}
}