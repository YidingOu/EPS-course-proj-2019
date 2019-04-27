package com.ipv.exception;

public class AuthorizationFailedException extends RuntimeException {

	/**
	 * Customized exception for token authorization failed
	 */
	private static final long serialVersionUID = 1L;

	public AuthorizationFailedException() {
	}

	public AuthorizationFailedException(String message) {
		super(message);
	}

	public AuthorizationFailedException(Throwable cause) {
		super(cause);
	}

	public AuthorizationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorizationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
