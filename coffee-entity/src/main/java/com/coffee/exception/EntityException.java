package com.coffee.exception;

public class EntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	EntityException() {
		super();
	}

	public EntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityException(Throwable cause) {
		super(cause);
	}

	public EntityException(String message) {
		super(message);
	}

}
