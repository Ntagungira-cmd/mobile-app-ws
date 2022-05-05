package com.ntagungira.app.ws.Exceptions;

public class UserServiceException extends RuntimeException{
	  
	private static final long serialVersionUID = -6150917216175229897L;
	
	public UserServiceException(String message) {
		super(message);
	}
}
