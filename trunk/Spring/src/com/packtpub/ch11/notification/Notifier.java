package com.packtpub.ch11.notification;

import com.packtpub.ch11.exception.NotifyException;

public interface Notifier {
	public void notify(String text) throws NotifyException;
}
