package com.coffee.exceptions;

public class UtilsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UtilsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UtilsException(Throwable cause) {
		super(cause);
	}

	public UtilsException(String message) {
		super(message);
	}

}
