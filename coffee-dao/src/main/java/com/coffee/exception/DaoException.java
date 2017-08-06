package com.coffee.exception;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message) {
		super(message);
	}

}
