package com.ipv.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ipv.exception.AuthorizationFailedException;
import com.ipv.exception.NotFoundException;
import com.ipv.exception.TokenValidateFailedException;
import com.ipv.util.wrapper.ErrorResponseWrapper;

/**
 * The global exception handler for the backend
 */
//@ControllerAdvice
public class AggregatedExceptionHandler {

	//Not found exception
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(NotFoundException e) {
		ErrorResponseWrapper error = new ErrorResponseWrapper(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	//token fail exception
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(TokenValidateFailedException e) {
		ErrorResponseWrapper error = new ErrorResponseWrapper(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	//token fail exception
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(AuthorizationFailedException e) {
		ErrorResponseWrapper error = new ErrorResponseWrapper(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	//All other exceptions
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(Exception e) {
		ErrorResponseWrapper error = new ErrorResponseWrapper(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
