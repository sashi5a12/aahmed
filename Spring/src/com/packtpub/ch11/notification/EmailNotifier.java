package com.packtpub.ch11.notification;

import com.packtpub.ch11.exception.NotifyException;

public class EmailNotifier implements Notifier {
	public void notify(String text) throws NotifyException {
	       System.out.println("Email Sent service...."+text);
	}
}
