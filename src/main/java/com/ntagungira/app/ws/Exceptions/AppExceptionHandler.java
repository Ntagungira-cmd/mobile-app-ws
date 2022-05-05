package com.ntagungira.app.ws.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class AppExceptionHandler {
	@ExceptionHandler(value= {UserServiceException.class})
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest req) {
		return new ResponseEntity<>(ex.getMessage(),new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
