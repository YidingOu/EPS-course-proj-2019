package com.ipv.exception;

public class TokenValidateFailedException extends RuntimeException {

	/**
	 * Customized exception for token validation failed
	 */
	private static final long serialVersionUID = 1L;

	public TokenValidateFailedException() {
	}

	public TokenValidateFailedException(String message) {
		super(message);
	}

	public TokenValidateFailedException(Throwable cause) {
		super(cause);
	}

	public TokenValidateFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenValidateFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
