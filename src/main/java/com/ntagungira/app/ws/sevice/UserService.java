package com.ntagungira.app.ws.sevice;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ntagungira.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
}
