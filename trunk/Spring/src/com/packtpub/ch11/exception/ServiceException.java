package com.packtpub.ch11.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -7034770569161372893L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}