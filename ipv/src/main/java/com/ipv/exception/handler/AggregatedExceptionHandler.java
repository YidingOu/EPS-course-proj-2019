package com.ipv.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ipv.exception.NotFoundException;
import com.ipv.wrapper.ErrorResponseWrapper;

@ControllerAdvice
public class AggregatedExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(NotFoundException exc) {
		
		ErrorResponseWrapper error = new ErrorResponseWrapper(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponseWrapper> handleException(Exception exc) {
		
		ErrorResponseWrapper error = new ErrorResponseWrapper(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
