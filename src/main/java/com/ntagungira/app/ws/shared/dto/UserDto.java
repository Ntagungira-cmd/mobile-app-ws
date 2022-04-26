package com.ntagungira.app.ws.shared.dto;

import java.io.Serializable;

public class UserDto implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String emailVerificationToken;
	private Boolean emailVerificationStatus=false;
}
