package com.ipv.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ipv.exception.NotFoundException;
import com.ipv.util.wrapper.ErrorResponseWrapper;

/**
 * The global exception handler for the backend
 * The more handler will be added later
 */
@ControllerAdvice
public class AggregatedExceptionHandler {
	
	//Not found exception
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(NotFoundException e) {
		ErrorResponseWrapper error = new ErrorResponseWrapper(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	//All other exceptions
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(Exception e) {
		ErrorResponseWrapper error = new ErrorResponseWrapper(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
