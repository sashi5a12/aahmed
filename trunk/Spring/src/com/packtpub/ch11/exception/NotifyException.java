package com.packtpub.ch11.exception;

public class NotifyException extends RuntimeException {

	public NotifyException() {
	}

	public NotifyException(String message) {
		super(message);
	}

	public NotifyException(Throwable cause) {
		super(cause);
	}

	public NotifyException(String message, Throwable cause) {
		super(message, cause);
	}
}